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
    <script type="text/javascript" src="web-res/js-default/cardType.js"></script>
</head>
<body>
    <table id="dg" title="贵宾卡类型统计" class="easyui-datagrid" style="width:80%;height:90%"
            url="queryCardTypeNumber.do"
            toolbar="#toolbar" pagination="true"
            rownumbers="true" fitColumns="true" singleSelect="true">
        <thead>
            <tr>
                <th field="cardTypeCode" width="50">卡类型编码</th>
                <th field="cardTypeName" width="50">卡类型名称</th>
                <th field="stateName" width="50">状态</th>
                <th field="cardTypeBalance" width="50">会员卡金额</th>
                <th field="cardNumber" width="50">持卡会员数</th>
            </tr>
        </thead>
    </table>
    <div id="toolbar">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newUser()">新增会员类型</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editUser()">修改会员类型</a>
    	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destoryCardType()" style="width:90px">删除</a>
    </div>
    
    <div id="dlg" class="easyui-dialog" style="width:500px;height:300px;padding:10px 20px"
            closed="true" buttons="#dlg-buttons">
        <div class="ftitle">贵宾卡类型</div>
        <input id="oldCardTypeCode" name="oldCardTypeCode" style="display:none"/>
        <form id="fm" method="post">
        	<table cellpadding="5">
        		<tr>
        			<td>编码:</td>
        			<td><input id="cardTypeCode" name="cardTypeCode" class="easyui-textbox" required="true"></td>
        		</tr>
        		<tr>
        			<td>贵宾卡类型:</td>
        			<td><input id="cardTypeName" name="cardTypeName" class="easyui-textbox" required="true"></td>
        		</tr>
        		<tr>
        			<td>贵宾卡金额:</td>
        			<td><input id="cardTypeBalance" name="cardTypeBalance" class="easyui-textbox" required="true"></td>
        		</tr>
        	</table>
        </form>
    </div>
    <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="submitCardType()" style="width:90px">保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')" style="width:90px">取消</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destoryCardType()" style="width:90px">删除</a>
    </div>
    <style type="text/css">
        #fm{
            margin:0;
            padding:10px 30px;
        }
        .ftitle{
            font-size:14px;
            font-weight:bold;
            padding:5px 0;
            margin-bottom:10px;
            border-bottom:1px solid #ccc;
        }
        .fitem{
            margin-bottom:5px;
        }
        .fitem label{
            display:inline-block;
            width:80px;
        }
        .fitem input{
            width:160px;
        }
    </style>
</body>
</html>