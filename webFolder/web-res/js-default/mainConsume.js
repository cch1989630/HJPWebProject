function queryMemberCardBalance() {
	if($("#ff").form('validate')) {
		var beginTime = $('#beginTime').datebox('getValue');
		var endTime = $('#endTime').datebox('getValue');
		var cardId = $('#cardId').val();
		var costMerchantId = $('#costMerchantId').combobox('getValue');
		var cardTypeCode = $('#cardTypeCode').combobox('getValue');
		var openBeginTime = $('#openBeginTime').datebox('getValue');
		var openEndTime = $('#openEndTime').datebox('getValue');
		var cardBalanceBegin = $('#cardBalanceBegin').val();
		var cardBalanceEnd = $('#cardBalanceEnd').val();
		//查询参数直接添加在queryParams中      
        var queryParams = $('#dg').datagrid('options').queryParams;  
        queryParams.beginTime = beginTime;
        queryParams.endTime = endTime;
        queryParams.cardId = cardId;
        queryParams.cardTypeCode = cardTypeCode;
        queryParams.costMerchantId = costMerchantId;
        queryParams.openBeginTime = openBeginTime;
        queryParams.openEndTime = openEndTime;
        queryParams.cardBalanceBegin = cardBalanceBegin;
        queryParams.cardBalanceEnd = cardBalanceEnd;
        $('#dg').datagrid('options').queryParams = queryParams;  
        $("#dg").datagrid('reload');
	}
}

function exportExcl() {
	var beginTime = $('#beginTime').datebox('getValue');
	var endTime = $('#endTime').datebox('getValue');
	var cardId = $('#cardId').val();
	var costMerchantId = $('#costMerchantId').combobox('getValue');
	var cardTypeCode = $('#cardTypeCode').combobox('getValue');
	var openBeginTime = $('#openBeginTime').datebox('getValue');
	var openEndTime = $('#openEndTime').datebox('getValue');
	var cardBalanceBegin = $('#cardBalanceBegin').val();
	var cardBalanceEnd = $('#cardBalanceEnd').val();
	window.location.href = "exportMainConsume.do?beginTime=" + beginTime + "&endTime=" + endTime
		+ "&cardId=" + cardId + "&costMerchantId=" + costMerchantId + "&cardTypeCode=" + cardTypeCode
		+ "&openBeginTime=" + openBeginTime + "&openEndTime=" + openEndTime + "&cardBalanceBegin=" + cardBalanceBegin
		+ "&cardBalanceEnd=" + cardBalanceEnd;
}

$(document).ready(function () {
	$('#dg').datagrid({
		onLoadSuccess: function(data){
			//console.info(data);
			$('#allCost').textbox('setValue',data.allCost);
		}
	});
});