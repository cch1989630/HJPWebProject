<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>花津浦</title>
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <link rel="stylesheet" type="text/css" href="web-res/css-libs/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="web-res/css-libs/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="web-res/css-libs/demo.css">
    <script type="text/javascript" src="web-res/js-libs/jquery.min.js"></script>
    <script type="text/javascript" src="web-res/js-libs/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="web-res/js-libs/util.js"></script>
    <script type="text/javascript" src="web-res/js-libs/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="web-res/js-default/login.js"></script>
</head>

<style>
	//.panel{margin:0 20% 0 20%};
</style>
<body>
    <h2 style="text-align:center;font-size:200%;">花津浦贵宾卡管理系统</h2>
    <input id="error" value="${error!''}" style="display:none"/>
    <div style="margin:20px 0;"></div>
    <form id="busiForm" action="j_spring_security_check" method="post" >
	    <div style="margin:0 0 0 30%">
		    <div class="easyui-panel" title="登录" style="width:60%;padding:30px 60px;">
		        <div style="margin-bottom:20px">
		            <div>工号:</div>
		            <#if username??>
		            	<input class="easyui-textbox" id="j_username" value="${username}" name="j_username" style="width:100%;height:32px">
		            <#else>
		            	<input class="easyui-textbox" id="j_username" name="j_username" style="width:100%;height:32px">
		            </#if>
		        </div>
		        <div style="margin-bottom:20px">
		            <div>密码:</div>
		            <input class="easyui-textbox" type="password" id="j_password" name="j_password" style="width:100%;height:32px">
		        </div>
		        <div>
		            <a href="#" class="easyui-linkbutton" onclick="return subForm()" iconCls="icon-ok" style="width:100%;height:32px">登录</a>
		            <input type="submit" hidden style="display:none"/>
		        </div>
		    </div>
	    </form>
    </div>
</body>
</html>