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
	    onBeforeEdit: function(index,row){
	    	//console.info(row);
	    	//$('#dg').datagrid('selectRow', index).datagrid('beginEdit', index);
	    	// 得到单元格对象,index指哪一行,field跟定义列的那个一样
	    	var rowEdit = $('#dg').datagrid('getSelected');
	    	//var $input = cellEdit.target; // 得到文本框对象
	    	//$input.val('aaa'); // 设值
	    	//$input.prop('readonly',true); 
	    	//$('#dg').datagrid('unselectAll');
	    	if (rowEdit.isMonth === "1") {
	    		$('#dg').datagrid('cancelEdit',{index:index});
			}
	    },
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
