function queryCardByType() {
	if($("#ff").form('validate')) {
		var cardTypeCode = $('#cardTypeCode').combobox('getValue');
		//查询参数直接添加在queryParams中      
        var queryParams = $('#dg').datagrid('options').queryParams;  
        queryParams.cardTypeCode = cardTypeCode;
        $('#dg').datagrid('options').queryParams = queryParams;  
        $("#dg").datagrid('reload');
	}
}