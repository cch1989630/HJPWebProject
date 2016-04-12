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

$(document).ready(function () {
	$('#dg').edatagrid({
	    updateUrl: 'updateFinanceConsume.do',
	    onEdit: function(index,row) {
	    	//console.info(row);
	    },
	    onSuccess: function(index,row) {
	    	//console.info(row);
	    	//后台更新成功后需要刷新页面显示修改的结果
	    	$("#dg").datagrid('reload');
	    	/*
	    	var row = $('#dg').datagrid("selectRow", index).datagrid("getSelected");
	    	row.editStaffId = $("#loginStaffId").val();
	    	row.editStaffName = $("#loginStaffName").val();
	    	row.editTime = getCurrTime();
	    	$('#dg').datagrid('updateRow', row);
	    	*/
	    }
	});
	
});
