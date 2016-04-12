function submitForm(){
	//手动校验form的数据问题
	if($("#ff").form('validate')) {
		var data ={};
		data.selectChildMerchantId = $('#childMerchantId').combobox('getValue');
		data.selectStaffId = $("#staffId").val();
		data.staffName = $("#staffName").val();
		data.createTime = $("#createTime").val();
		data.password = $("#password").val();
		data.createTime = $("#createTime").val();
		data = JSON.stringify(data);
		jqueryAjaxData("StaffManageController", "addStaff", data, finishAddStaff);
	}
	//$('#ff').form('submit');
}

function finishAddStaff(data) {
	var ret = eval(data);
	if(ret.state === 1) {
		showMessage("成功","新增用户成功","show",function(){
			$('#ff').form('clear');
		});
	}
}

function clearForm(){
	$('#ff').form('clear');
}