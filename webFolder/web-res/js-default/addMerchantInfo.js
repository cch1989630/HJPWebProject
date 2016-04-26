function queryMerchantInfo() {
	if($("#ff").form('validate')) {
		var merchantId = $('#merchantId').val();
		var merchantName = $('#merchantName').val();
		//查询参数直接添加在queryParams中      
        var queryParams = $('#dg').datagrid('options').queryParams;  
        queryParams.merchantId = merchantId;
        queryParams.merchantName = merchantName;
        $('#dg').datagrid('options').queryParams = queryParams;  
        $("#dg").datagrid('reload');
	}
}

var dataType;
function newMerchant() {
	$('#dlg').dialog('open').dialog('center').dialog('setTitle', '新增分店部门');
	$('#selectMerchantId').textbox({
		disabled : false,
	});
	$('#fm').form('clear');
	dataType = 'save';
}
function editMerchant() {
	var row = $('#dg').datagrid('getSelected');
	if (row) {
		$('#dlg').dialog('open').dialog('center').dialog('setTitle', '修改分店部门');
		$('#selectMerchantId').textbox({
			disabled : true,
		});
		$('#fm').form('load', {
			merchantId:row.merchantId,
			merchantName:row.merchantName
		});
		dataType = "edit";
	}
}
function submitMerchantInfo() {
	var data ={};
	var submitFunction = "saveMerchantInfo";
	data.selectMerchantName = $('#selectMerchantName').textbox('getValue');
	data.selectMerchantId = $('#selectMerchantId').textbox('getValue');
	if (dataType === "edit") {
		submitFunction = "updateMerchantInfo";
	}
	data = JSON.stringify(data);
	jqueryAjaxData("MerchantManageController", submitFunction, data, finishSubmitMerchantInfo);
}

function finishSubmitMerchantInfo(data) {
	var ret = eval(data);
	if(ret.state === 1) {
		$('#dlg').dialog('close');
		$('#dg').datagrid('reload');
	}
}