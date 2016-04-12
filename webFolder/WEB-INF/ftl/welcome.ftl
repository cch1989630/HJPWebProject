<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>管理端</title>
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <link rel="stylesheet" type="text/css" href="web-res/css-libs/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="web-res/css-libs/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="web-res/css-libs/demo.css">
    <script type="text/javascript" src="web-res/js-libs/jquery.min.js"></script>
    <script type="text/javascript" src="web-res/js-libs/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="web-res/js-libs/util.js"></script>
    <script type="text/javascript" src="web-res/js-default/welcome.js"></script>
    <style>
        .footer {
            width: 100%;
            text-align: center;
            line-height: 35px;
        }
 
        .top-bg {
            background-color: #d8e4fe;
            height: 80px;
        }
        
        .accordion .accordion-body{
        	padding:0;
        }
        
       .panel-body.accordion-body li{
        	list-style:none;
        }
        
        .panel-body.accordion-body li{
        	display:block;
        	width:100%;
        	height:30px;
        	line-height:30px;
        	background-color:#DDD;
        	text-align:center;
			 color: #444;
			  background: #fafafa;
			  background-repeat: repeat-x;
			  border: 1px solid #bbb;
			  background: -webkit-linear-gradient(top,#ffffff 0,#eeeeee 100%);
			  background: -moz-linear-gradient(top,#ffffff 0,#eeeeee 100%);
			  background: -o-linear-gradient(top,#ffffff 0,#eeeeee 100%);
			  background: linear-gradient(to bottom,#ffffff 0,#eeeeee 100%);
			  background-repeat: repeat-x;
			  filter: progid:DXImageTransform.Microsoft.gradient(startColorstr=#ffffff,endColorstr=#eeeeee,GradientType=0);
			  -moz-border-radius: 5px 5px 5px 5px;
			  -webkit-border-radius: 5px 5px 5px 5px;
			  border-radius: 5px 5px 5px 5px;
			  cursor:pointer;
			  text-decoration:none;
			  margin-bottom:5px;
        }
        
        .panel-body.accordion-body a{
        	display:block;
        	text-decoration:none;
        }
        
        .panel-body.accordion-body li.hover{
        	 background: #eaf2ff;
			  color: #000000;
			  border: 1px solid #b7d2ff;
			  filter: none;
        }
 
    </style>
</head>
 
<body class="easyui-layout">
    <div region="south" border="true" split="true" style="overflow: hidden; height: 40px;">
        <div class="footer">版权所有：花津浦</a></div>
    </div>
    <div region="west" split="true" title="导航菜单" style="width: 200px;">
    	<div id="aa" class="easyui-accordion" style="position: absolute; top: 27px; left: 0px; right: 0px; bottom: 0px;">
			<div title="门店会员卡管理管理" iconcls="icon-save" style="overflow: auto; padding: 10px;">
		    	<li><a target="mainFrame" copyhref="cardConsume.do">会员卡消费</a></li>
		    	<li><a target="mainFrame" copyhref="childMerchantConsume.do">门店消费查询</a></li>
		    	<li><a target="mainFrame" copyhref="memberCardConsume.do">会员卡消费查询</a></li>
		    	<li><a target="mainFrame" copyhref="changeSelfPassword.do">修改密码</a></li>
		    	<li><a href="j_spring_security_logout" iconCls="icon-cancel">退出</a></li>
		 	</div>
		 	<div title="总部会员卡管理管理" iconcls="icon-save" style="overflow: auto; padding: 10px;">
		    	<li><a target="mainFrame" copyhref="addMemberCard.do">会员卡新增</a></li>
		    	<li><a target="mainFrame" copyhref="cardType.do">卡类型新增</a></li>
		    	<li><a target="mainFrame" copyhref="addSaff.do">新增用户</a></li>
		    	<li><a target="mainFrame" copyhref="mainConsume.do">会员卡消费查询加强</a></li>
		    	<li><a target="mainFrame" copyhref="mainMemberCardType.do">会员卡分类查询</a></li>
		    	<li><a target="mainFrame" copyhref="queryStaffPassword.do">操作员密码展示</a></li>
		    	<li><a href="j_spring_security_logout" iconCls="icon-cancel">退出</a></li>
		 	</div>
		 	<div title="财务会员卡管理管理" iconcls="icon-save" style="overflow: auto; padding: 10px;">
		    	<li><a target="mainFrame" copyhref="financeConsume.do">会员卡数据修改</a></li>
		    	<li><a target="mainFrame" copyhref="financeMonth.do">月结</a></li>
		    	<li><a href="j_spring_security_logout" iconCls="icon-cancel">退出</a></li>
		 	</div>
		</div>
    </div>
    <div id="mainPanle" region="center" style="overflow: hidden;">
    	<div id="tabs" class="easyui-tabs" fit="true" border="false">
            <div title="欢迎使用" style="padding: 20px; overflow: hidden;" id="home">
                <h1>欢迎使用花津浦会员卡管理系统!</h1>
            </div>
        </div>
    </div>
</body>
</html>