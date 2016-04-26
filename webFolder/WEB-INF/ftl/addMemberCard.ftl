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
    <script type="text/javascript" src="web-res/js-default/addMemberCard.js"></script>
</head>
<style>
	.base{width: 200px; height: 30px;}
</style>
<body>
	<table id="dg" title="贵宾卡统计" class="easyui-datagrid" style="width:80%;height:90%"
            url="queryCardByType.do"
            toolbar="#toolbar" pagination="true" pageList = "[15,30,45]" pageSize = "15"
            rownumbers="true" fitColumns="true" singleSelect="true">
        <thead>
            <tr>
                <th field="cardId" width="50">卡号</th>
                <th field="cardTypeName" width="50">卡类型名称</th>
                <th field="hodeCardName" width="50">持卡人</th>
                <th field="hodeCardPhone" width="50">持卡联系方式</th>
                <th field="createTime" width="50">开卡日期</th>
                <th field="cardBalance" width="50">余额</th>
                <th field="cardTypeCode" width="50" hidden="true">卡类型</th>
            </tr>
        </thead>
    </table>
    <div id="toolbar">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newMemberCard()">新增贵宾卡</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editMemberCard()">修改贵宾卡</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destoryMemberCard()" style="width:90px">删除贵宾卡</a>
    </div>
    
    <div id="dlg" class="easyui-dialog" style="width:600px;height:400px;padding:10px 20px"
            closed="true" buttons="#dlg-buttons">
        <div class="ftitle">贵宾卡</div>
        <input id="oldCardId" style="display:none"/>
        <form id="ff" method="post">
		    <table cellpadding="5">
		    	<tr>
		    		<td>卡号</td>
		    		<td><input class="easyui-textbox" name="cardId" type="text" id="cardId" data-options="required:true"></input></td>
		    		<td>持卡人</td>
		    		<td><input class="easyui-textbox" name="hodeCardName" type="text" id="hodeCardName" data-options="required:true"></input></td>
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
                    			required:true,
                    			editable:false">
                    	</input>
                    </td>
		    		<td>持卡人联系方式</td>
		    		<td><input class="easyui-textbox" name="hodeCardPhone" type="text" id="hodeCardPhone" data-options="required:true"></input></td>
		    	</tr>
		    	<tr>
		    		<td>卡余额</td>
		    		<td><input class="easyui-numberbox" name="cardBalance" type="text" id="cardBalance" data-options="required:true,disabled:true"></input></td>
		    		<td>开卡日期</td>
		    		<td><input id="memberCreateTime" name="memberCreateTime" class="easyui-datebox" data-options="disabled:true,formatter:myformatter,parser:setparser"></input></td>
		    	</tr>
		    </table>
		</form>
    </div>
    <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="submitMemberCard()" style="width:90px">保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')" style="width:90px">取消</a>
    </div>
</body>