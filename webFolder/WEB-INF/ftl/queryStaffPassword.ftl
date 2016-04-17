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
    <script type="text/javascript" src="web-res/js-default/queryStaffPassword.js"></script>
</head>
<body>
	<div class="easyui-panel" style="width:100%;height:20%" title="搜索">
		<div style="padding:10px 60px 20px 60px">
		    <form id="ff" method="post">
		    	<table cellpadding="5">
		    		<tr>
		    			<td>操作员编号</td>
		    			<td><input class="easyui-textbox" type="text" id="staffId"></input></td>
		    			<td><a href="#" onclick="queryStaffInfo()" class="easyui-linkbutton" data-options="iconCls:'icon-search'" style="width:80px">搜索</a></td>
		    		</tr>
		    	</table>
		    </form>
		</div>
	</div>
    <table id="dg" title="用户密码展示" class="easyui-datagrid" style="width:100%;height:80%"
            url="queryStaffInfo.do"
            pagination="true"
            rownumbers="true" fitColumns="true" singleSelect="true">
        <thead>
            <tr>
                <th field="merchantName" width="50">所属门店</th>
                <th field="staffId" width="50">操作员编号</th>
                <th field="staffName" width="50">操作员名称</th>
                <th field="clearPassword" width="50">操作员密码</th>
                <th field="createTime" width="50">创建时间</th>
            </tr>
        </thead>
    </table>
    <div id="dlg" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px"
            closed="true" buttons="#dlg-buttons">
        <div class="ftitle">贵宾卡类型</div>
        <form id="fm" method="post" novalidate>
            <div class="fitem">
                <label>贵宾卡类型名称:</label>
                <input id="cardTypeName" class="easyui-textbox" required="true">
            </div>
        </form>
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