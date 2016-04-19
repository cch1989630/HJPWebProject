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
    <script type="text/javascript" src="web-res/js-libs/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="web-res/js-libs/LodopFuncs.js"></script>
    <script type="text/javascript" src="web-res/js-default/cardConsume.js"></script>
</head>
<style>
	.base{width: 200px; height: 30px;}
</style>
<body>
	<div class="easyui-panel" style="width:80%">
		<div style="padding:10px 60px 20px 60px">
		    <form id="ff" method="post">
		    	<table cellpadding="5">
		    		<tr>
		    			<td>卡号</td>
		    			<td><input class="easyui-textbox" id="cardId" name="cardId" style="width: 230px;" data-options="required:true,buttonText:'查询',buttonIcon:'icon-search',prompt:'查询'""></td>
		    			<td>消费金额（元）</td>
		    			<td><input class="easyui-numberbox" id="cost" type="text" name="cost" data-options="required:true"></input></td>
		    		</tr>
		    		<tr>
		    			<td>卡类型</td>
		    			<td><input class="easyui-combobox" 
            					id="cardTypeCode"
            					name="cardTypeCode"
            					data-options="
                    				url:'queryCardType.do',
                    				method:'get',
                    				valueField:'id',
                    				textField:'text',
                    				panelHeight:'auto',
                    				disabled:true">
                    		</input>
                    	</td>
		    			<td>卡余额（元）</td>
		    			<td><input id="cardBalance" class="easyui-numberbox" type="text" name="cardBalance" data-options="disabled:true"></input></td>
		    		</tr>
		    		<tr>
		    			<td>持卡人</td>
		    			<td><input id="hodeCardName" class="easyui-textbox" type="text" name="hodeCardName" data-options="disabled:true"></input></td>
		    			<td>持卡人联系方式</td>
		    			<td><input id="hodeCardPhone" class="easyui-textbox" type="text" name="hodeCardPhone" data-options="disabled:true"></input></td>
		    		</tr>
		    		<tr>
		    			<td>开卡日期</td>
		    			<td><input class="easyui-textbox" type="text" name="createTime" data-options="disabled:true"></input></td>
		    			<td>消费日期</td>
		    			<td><input id="costTime" class="easyui-datebox" data-options="disabled:true,formatter:myformatter,parser:myparser"></input></td>
		    		</tr>
		    		<tr>
		    			<td>门店</td>
		    			<td><input class="easyui-textbox" id="merchantName" value="${merchantName}" data-merchantid="${merchantId}" type="text" name="merchantId" data-options="disabled:true"></input></td>
		    			<td>操作员</td>
		    			<td><input id="staffId" value="${staffId}" class="easyui-textbox" type="text" name="staffId" data-options="disabled:true"></input></td>
		    		</tr>
		    	</table>
		    </form>
		    <div style="text-align:center;padding:5px">
		    
		    	<a href="javascript:void(0)" iconCls="icon-ok" style="width:10%;height:32px" class="easyui-linkbutton" onclick="submitForm()">提交</a>
		    	<a href="javascript:void(0)" iconCls="icon-cancel" style="width:10%;height:32px" class="easyui-linkbutton" onclick="clearForm()">取消</a>
		    </div>
		</div>
	</div>
</body>