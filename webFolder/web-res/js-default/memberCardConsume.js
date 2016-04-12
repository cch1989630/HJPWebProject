function queryMemberCardBalance() {
	if($("#ff").form('validate')) {
		var beginTime = $('#beginTime').datebox('getValue');
		var endTime = $('#endTime').datebox('getValue');
		var cardId = $('#cardId').val();
		var hodeCardName = $('#hodeCardName').val();
		var cardTypeCode = $('#cardTypeCode').combobox('getValue');
		var openTime = $('#openTime').datebox('getValue');
		//查询参数直接添加在queryParams中      
        var queryParams = $('#dg').datagrid('options').queryParams;  
        queryParams.beginTime = beginTime;
        queryParams.endTime = endTime;
        queryParams.cardId = cardId;
        queryParams.hodeCardName = hodeCardName;
        queryParams.cardTypeCode = cardTypeCode;
        queryParams.openTime = openTime;
        $('#dg').datagrid('options').queryParams = queryParams;  
        $("#dg").datagrid('reload');
	}
}