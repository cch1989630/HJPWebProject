var dataType;
function newUser() {
	$('#dlg').dialog('open').dialog('center').dialog('setTitle', '新增分店部门');
	$('#fm').form('clear');
	dataType = 'save';
}
function editUser() {
	var row = $('#dg').datagrid('getSelected');
	if (row) {
		$('#dlg').dialog('open').dialog('center').dialog('setTitle', '修改分店部门');
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
	data.selectMerchantName = $("#merchantName").val();
	if (dataType === "edit") {
		data.selectMerchantId = $("#merchantId").val();
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