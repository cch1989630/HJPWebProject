function queryStaffInfo() {
	if($("#ff").form('validate')) {
		var staffId = $("#staffId").val();
		//查询参数直接添加在queryParams中      
        var queryParams = $('#dg').datagrid('options').queryParams;  
        queryParams.queryStaffId = staffId;
        $('#dg').datagrid('options').queryParams = queryParams;  
        $("#dg").datagrid('reload');
	}
}