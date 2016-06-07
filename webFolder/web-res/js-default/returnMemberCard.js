function queryReturnCard() {
	if($("#ff").form('validate')) {
		var cardId = $('#cardId').val();
		var cardTypeCode = $('#cardTypeCode').combobox('getValue');
		var returnBeginTime = $('#returnBeginTime').datebox('getValue');
		var returnEndTime = $('#returnEndTime').datebox('getValue');
		var queryParams = $('#dg').datagrid('options').queryParams;  
        queryParams.cardId = cardId;
        queryParams.cardTypeCode = cardTypeCode;
        queryParams.returnBeginTime = returnBeginTime;
        queryParams.returnEndTime = returnEndTime;
        $('#dg').datagrid('options').queryParams = queryParams;  
        $("#dg").datagrid('reload');
	}
}

function exportExcl() {
	var cardId = $('#cardId').val();
	var cardTypeCode = $('#cardTypeCode').combobox('getValue');
	var returnBeginTime = $('#returnBeginTime').datebox('getValue');
	var returnEndTime = $('#returnEndTime').datebox('getValue');
	window.location.href = "exportReturnCard.do?returnBeginTime=" + returnBeginTime + "&returnEndTime=" + returnEndTime
	+ "&cardId=" + cardId + "&cardTypeCode=" + cardTypeCode;
}