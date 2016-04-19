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
    <script type="text/javascript" src="web-res/js-default/memberCardConsume.js"></script>
</head>
<body>
	<div class="easyui-panel" style="width:100%;height:30%" title="搜索">
		<div style="padding:10px 60px 20px 60px">
		    <form id="ff" method="post">
		    	<table cellpadding="5">
		    		<tr>
		    			<td>卡号</td>
		    			<td><input class="easyui-textbox" type="text" id="cardId" data-options=""></input></td>
		    			<td>持卡人</td>
		    			<td><input class="easyui-textbox" type="text" id="hodeCardName" data-options="disabled:true"></input></td>
		    			<td>卡类型</td>
		    			<td><input class="easyui-combobox" 
            					id="cardTypeCode"
            					data-options="
                    				url:'queryCardType.do',
                    				method:'get',
                    				valueField:'id',
                    				textField:'text',
                    				panelHeight:'auto',
                    				disabled:true">
                    		</input>
                    	</td>
                    	<td><a href="#" onclick="queryMemberCardBalance()" class="easyui-linkbutton" data-options="iconCls:'icon-search'" style="width:80px">搜索</a></td>
                    	<td><a href="#" onclick="exportExcl()" class="easyui-linkbutton" data-options="iconCls:'icon-save'" style="width:80px">导出</a></td>
		    		</tr>
		    		<tr>
		    			<td>消费日期(起)</td>
		    			<td><input id="beginTime" class="easyui-datebox" data-options="formatter:beginTimeFormatter,parser:changeParser"></input></td>
		    			<td>消费日期(止)</td>
		    			<td><input id="endTime" class="easyui-datebox" data-options="formatter:endTimeFormatter,parser:changeParser"></input></td>
		    			<td>开卡日期</td>
		    			<td><input id="openTime" class="easyui-datebox" data-options="formatter:formatTime,parser:changeParser,disabled:true"></input></td>
		    		</tr>
		    		<tr>
		    			<td>总计</td>
		    			<td><input class="easyui-textbox" type="text" id="allCost" data-options="disabled:true"></input></td>
		    		</tr>
		    	</table>
		    </form>
		</div>
	</div>
    <table id="dg" title="贵宾卡消费信息" class="easyui-datagrid" style="width:100%;height:70%"
            url="queryMemberCardBalance.do"
            pagination="true" pageList = "[15,30,45]" pageSize = "15"
            rownumbers="true" fitColumns="true" singleSelect="true">
        <thead>
            <tr>
                <th field="cardId" width="50">卡号</th>
                <th field="hodeCardName" width="50">持卡人</th>
                <th field="cardTypeName" width="50">卡类型</th>
                <th field="cost" width="50">消费金额</th>
                <th field="merchantName" width="50">消费门店</th>
                <th field="staffName" width="50">操作员</th>
                <th field="tradeStaffId" width="50">操作员编号</th>
                <th field="costTime" width="50">消费日期</th>
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