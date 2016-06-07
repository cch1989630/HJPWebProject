function queryCardByType() {
	if($("#ff").form('validate')) {
		var cardTypeCode = $('#queryCardTypeCode').combobox('getValue');
		var openBeginTime = $('#openBeginTime').datebox('getValue');
		var openEndTime = $('#openEndTime').datebox('getValue');
		//查询参数直接添加在queryParams中      
        var queryParams = $('#dg').datagrid('options').queryParams;  
        queryParams.cardTypeCode = cardTypeCode;
        queryParams.openBeginTime = openBeginTime;
        queryParams.openEndTime = openEndTime;
        $('#dg').datagrid('options').queryParams = queryParams;  
        $("#dg").datagrid('reload');
	}
}

function returnMemberCard() {
	var row = $('#dg').datagrid('getSelected');
	if (row) {
		$('#dlg').dialog('open').dialog('center').dialog('setTitle', '修改贵宾卡');
		$('#fm').form('load', {
			cardId:row.cardId,
			hodeCardName:row.hodeCardName,
			hodeCardPhone:row.hodeCardPhone,
			cardBalance:row.cardBalance
		});
		$('#cardTypeCode').combobox('setValue', row.cardTypeCode);
		$('#memberCreateTime').datebox('setValue', row.createTime);
		$('#returnTime').datebox('setValue', getCurrTime());
	}
}

function submitReturnCard() {
	var cardBalance = parseFloat($('#cardBalance').val());
	var message = "确认退卡么？";
	if (cardBalance != 0) {
		message = $('#cardId').val() + "卡号，卡余额为" + $('#cardBalance').val() + "；退卡后，卡余额将清为‘0’！"
	}
	
	$.messager.confirm('提示', message, function(r) {
		if (r) {
			var data ={};
			data.cardId = $('#cardId').val();
			data.returnTime = $('#memberCreateTime').datebox('getValue');
			data = JSON.stringify(data);
			jqueryAjaxData("CardManageController", "returnMemberCard", data, finishReturnMemberCard);
		}
	});
}

function finishReturnMemberCard(data) {
	var ret = eval(data);
	var message = "退卡成功";
	if(ret.state === 1) {
		showMessage("成功",message,"show");
		$('#dlg').dialog('close');
		$("#dg").datagrid('reload');
	}
}

function exportExcl() {
	var cardTypeCode = $('#queryCardTypeCode').combobox('getValue');
	var openBeginTime = $('#openBeginTime').datebox('getValue');
	var openEndTime = $('#openEndTime').datebox('getValue');
	window.location.href = "exportMainConsumeByType.do?cardTypeCode=" + cardTypeCode + "&openBeginTime=" + openBeginTime
		+ "&openEndTime=" + openEndTime;
}

$(document).ready(function () {
	$('#dg').datagrid({
		onLoadSuccess: function(data){
			//console.info(data);
			$('#allCost').textbox('setValue',data.allCost);
		}
	});
});