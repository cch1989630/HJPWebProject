function queryMemberCardBalance() {
	if($("#ff").form('validate')) {
		var cardId = $('#cardId').val();
		var costMerchantId = $('#costMerchantId').combobox('getValue');
		var costTime = $('#costTime').datebox('getValue');
		var cost = $('#cost').val();
		//查询参数直接添加在queryParams中      
        var queryParams = $('#dg').datagrid('options').queryParams;  
        queryParams.cardId = cardId;
        queryParams.costMerchantId = costMerchantId;
        queryParams.costTime = costTime;
        queryParams.cost = cost;
        $('#dg').datagrid('options').queryParams = queryParams;  
        $("#dg").datagrid('reload');
	}
}

function editFinanceConsume() {
	var row = $('#dg').datagrid('getSelected');
	if (row) {
		$('#dlg').dialog('open').dialog('center').dialog('setTitle', '修改会员卡消费');
		$('#fm').form('load', {
			cardId1:row.cardId,
			staffName:row.staffName,
			cost1:row.cost,
			costCardBalance:row.costCardBalance,
			merchantName:row.merchantName
		});
		$('#costTime1').datebox('setValue', row.costTime);
		$('#editTime').datebox('setValue', getCurrTime());
		$("#editStaffName").textbox('setValue', $("#loginStaffName").val());
		$("#oldCardId").val(row.cardId);
		$("#costId").val(row.costId);
	}
}

function submitFinanceConsume() {
	if($("#ff").form('validate')) {
		var data ={};
		data.cardId = $("#cardId1").textbox("getValue");
		data.oldCardId = $("#oldCardId").val();
		data.cost = $("#cost1").numberbox("getValue");
		data.costCardBalance = $("#costCardBalance").numberbox("getValue");
		data.costId = $("#costId").val();
		data = JSON.stringify(data);
		jqueryAjaxData("FinanceController", "updateFinanceConsume", data, finishSubmitFinanceConsume);
	}
}

function finishSubmitFinanceConsume(data) {
	var ret = eval(data);
	if(ret.state === 1) {
		showMessage("成功","修改成功","show");
		$("#dg").datagrid('reload');
		$('#dlg').dialog('close');
	}
}

$(document).ready(function () {
	$("#cost1").numberbox({
		onChange:function(newValue,oldValue){
			var changeValue = parseFloat(newValue) - parseFloat(oldValue);
			var costCardBalance = parseFloat($("#costCardBalance").val());
			$("#costCardBalance").textbox('setValue',costCardBalance - changeValue);
		},
	});
	
});
