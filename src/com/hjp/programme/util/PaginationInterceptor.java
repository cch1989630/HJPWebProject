package com.hjp.programme.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.log4j.Logger;

@Intercepts(@Signature(args = {java.sql.Connection.class}, method = "prepare", type = StatementHandler.class))  
public class PaginationInterceptor implements Interceptor {
	/** 
     * LOGGER 日志对象 
     */  
    private final static Logger LOGGER = Logger.getLogger(PaginationInterceptor.class);
	//存储是哪个数据库，在paginationSql方法好进行判断  
    //是在mybatis的核心配置文件mybatis-config.xml中配置的  
    private String databaseType;
    
    public Object intercept(Invocation invocation) throws Throwable {  
        Object target = invocation.getTarget();  
        RoutingStatementHandler routingStatementHandler = (RoutingStatementHandler)target;  
        //使用工具类得到PreparedStatementHandler  
        StatementHandler statementHandler = (StatementHandler) ReflectClazz.getFieldValue(routingStatementHandler, "delegate");  
        //这个对象封装了SQL语句  
        BoundSql boundSql = statementHandler.getBoundSql();  
        //封装的参数的类型  
        Object parameObject = boundSql.getParameterObject();
        //这里表示如果是分页，必须使用Page这个类来封住参数和传递参数。  
        if(parameObject instanceof Page<?>){
            //将sql全部转换为大写  
            String sql = boundSql.getSql(); 
              
            //通过工具类，得到MappedStatement对象  
            //工具类是通过http://haohaoxuexi.iteye.com/blog/1851081这篇博客改造过来的  
            MappedStatement mappedStatement =  
                    (MappedStatement)ReflectClazz.getFieldValue(statementHandler,"mappedStatement");  
            //得到通过注解得到参数  
            Connection connection = (Connection)invocation.getArgs()[0];
            //得到封装的参数Page  
            Page<?> page = (Page<?>)parameObject;  
            
            if(page.getCountRecord() < 0){
            	//得到计算总记录数的sql。  
                String countSql = "SELECT count(*) FROM ("+sql+") new_table";
            	//查询总记录数  
                setTotalRecord(page,mappedStatement,connection,countSql); 
        	}
            
            //将SQL语句进行封装。  
            String changeValue = paginationSql(sql,page);  
            //改变BoundSql对象的私有属性sql的值  
            changePrivateAttributeValue(boundSql, "sql", changeValue);  
        }else{  
        	//LOGGER.debug("not page sql");
        }  
        // 将执行权交给下一个拦截器
        return invocation.proceed();
    }  
    /** 
     * 判断是哪个数据库 
     * 将查询语句的组装成分页语句 
     * @param sql 
     * @return 
     */  
    private String paginationSql(String sql,Page<?> page) {  
        if(this.databaseType.equalsIgnoreCase("MySql")){  
            //MySql数据库的分页  
            return mySqlPaginationSql(sql,page.getStartIndex(),page.getPageSize());  
        }else{  
            return oraclePaginationSql(sql,page);  
        }  
    }  
  
    /** 
     * Oracle数据分页 
     * @param sql 
     * @param startIndex 
     * @param pageSize 
     * @return 
     */  
    private String oraclePaginationSql(String sql, Page<?> page) {  
        /* 
         *  
            SELECT * FROM  
            ( 
            SELECT A.*, ROWNUM RN  
            FROM (SELECT * FROM tab_cus) A  
            WHERE ROWNUM <= 6 
            ) 
            WHERE RN >= 2 
            下面是拼装sql语句 
         */  
        StringBuffer sb = new StringBuffer();  
        sb.append(" SELECT * FROM  ");  
        sb.append(" (");  
        sb.append(" SELECT AAA.*, ROWNUM RN  ");  
        sb.append(" FROM (");  
        sb.append(sql);  
        sb.append(") AAA ");  
        sb.append(" WHERE ROWNUM < "+page.getEndIndex());  
        sb.append(" )");  
        sb.append(" WHERE RN >= "+page.getStartIndex());  
        String resultSql = sb.toString();  
        return resultSql;  
    }  
    /** 
     * MySQL数据库的分页 
     * @param sql 
     * @return 
     */  
    private String mySqlPaginationSql(String sql,int startIndex,int pageSize) {  
        StringBuffer sb = new StringBuffer();  
        sb.append(sql);  
        sb.append(" limit "+startIndex+","+pageSize);  
        return sb.toString();  
    }  
    /** 
     * 查询总记录数的方法，下面就是Mybatis怎么样 
     * 执行SQL语句 
     * @param page 封装了参数 
     * @param mappedStatement  
     * @param connection 链接 
     * @param countSql sql语句 
     */  
    private void setTotalRecord(Page<?> page,    
               MappedStatement mappedStatement, Connection connection,String countSql) {    
           //获取对应的BoundSql，这个BoundSql其实跟我们利用StatementHandler获取到的BoundSql是同一个对象。    
           //delegate里面的boundSql也是通过mappedStatement.getBoundSql(paramObj)方法获取到的。    
           BoundSql boundSql = mappedStatement.getBoundSql(page);    
           //获取到我们自己写在Mapper映射语句中对应的Sql语句    
//         String sql = boundSql.getSql();    
           //通过查询Sql语句获取到对应的计算总记录数的sql语句    
//         String countSql = this.getCountSql(sql);    
           //通过BoundSql获取对应的参数映射    
           List<ParameterMapping> parameterMappings =   
                   boundSql.getParameterMappings();    
           //利用Configuration、查询记录数的Sql语句countSql、  
            //参数映射关系parameterMappings和参数对象page建立查询记录数对应的BoundSql对象。    
           BoundSql countBoundSql =   
                   new BoundSql(mappedStatement.getConfiguration(), countSql, parameterMappings, page); 
           //添加下面的三行，避免list取不到数据
           MetaObject countBsObject = SystemMetaObject.forObject(countBoundSql);
           MetaObject boundSqlObject = SystemMetaObject.forObject(boundSql);
           countBsObject.setValue("metaParameters", boundSqlObject.getValue("metaParameters"));
           
           //通过mappedStatement、参数对象page和BoundSql对象countBoundSql建立一个用于设定参数的ParameterHandler对象    
           ParameterHandler parameterHandler =   
                   new DefaultParameterHandler(mappedStatement, page, countBoundSql);    
           //通过connection建立一个countSql对应的PreparedStatement对象。    
           PreparedStatement pstmt = null;    
           ResultSet rs = null;    
           try {    
               pstmt = connection.prepareStatement(countSql);     //System.out.println("SQL:"+countSql);
               //通过parameterHandler给PreparedStatement对象设置参数    
               parameterHandler.setParameters(pstmt);    
               //之后就是执行获取总记录数的Sql语句和获取结果了。    
               rs = pstmt.executeQuery();    
               if (rs.next()) {    
                  int totalRecord = rs.getInt(1);    
                  //给当前的参数page对象设置总记录数    
                  page.setCountRecord(totalRecord);   
               }    
           } catch (SQLException e) {    
               e.printStackTrace();    
           } finally {    
               try {    
                  if (rs != null)    
                      rs.close();    
                   if (pstmt != null)    
                      pstmt.close();    
               } catch (SQLException e) {    
                  e.printStackTrace();    
               }    
           }    
        }    
      
    public Object plugin(Object target) {  
        if(target instanceof RoutingStatementHandler){  
            //这里是一Mybatis自带的动态代理方法，这样就会调用上面的intercept。  
            //当然也可以自己通过,下面的方法。来实现  
            //其实内部就是下面的方法  
            //Proxy.newProxyInstance(loader, interfaces, h);  
            return Plugin.wrap(target, this);  
        }  
        return target;  
    }  
    /** 
     * 读取mybatis-config.xml配置文件的信息 
     */  
    public void setProperties(Properties properties) {  
        //得到数据库类型  
        this.databaseType = properties.getProperty("databaseType"); 
    }  
    /** 
     * 改变某个类的私有属性的方法 
     * @param target 类的实力对象 
     * @param attributeName 要改变的属性名 
     * @param changeValue 该表属性的值 
     */  
    private static void changePrivateAttributeValue(Object target,  
            String attributeName, Object changeValue) {  
        //下面也是Mybatis自带的方法，但是那篇博客的方法  
        //也可以实现  
        ObjectFactory objectFactory = new DefaultObjectFactory();  
        ObjectWrapperFactory objectWrapperFactory = new DefaultObjectWrapperFactory();  
        MetaObject metaObject = MetaObject.forObject(target,objectFactory, objectWrapperFactory) ;  
        metaObject.setValue(attributeName, changeValue);  
    }  

}
