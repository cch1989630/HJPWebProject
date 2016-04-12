package com.hjp.programme.util;

import java.lang.reflect.Field;

public class ReflectUtil {
	 /** 
     * 利用反射获取指定对象里面的指定属性 
     * @param obj 目标对象 
     * @param fieldName 目标属性 
     * @return 目标字段 
     */  
    public static Field getField(Object obj, String fieldName) {  
        Field field = null;  
       for (Class<?> clazz=obj.getClass(); clazz != Object.class; clazz=clazz.getSuperclass()) {  
           try {  
               field = clazz.getDeclaredField(fieldName);  
               break;  
           } catch (NoSuchFieldException e) {  
               //这里不用做处理，子类没有该字段可能对应的父类有，都没有就返回null。  
           }  
        }  
        return field;  
    }  
}
