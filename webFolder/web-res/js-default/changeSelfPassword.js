function submitForm(){
	if($("#ff").form('validate')) {
		var data ={};
		data.oldPassword = $("#oldPassword").val();
		data.password = $("#password").val();
		data = JSON.stringify(data);
		jqueryAjaxData("StaffManageController", "changePassword", data, finishChangePassword);
	}
}

function finishChangePassword(data) {
	var ret = eval(data);
	if(ret.state === 1) {
		showMessage("成功","密码修改成功！","show",function(){
			clearForm()
		});
	}
}

function clearForm(){
	//$('#ff').form('clear');
	$('#ff').form('load',{
		password:"",
		oldPassword:"",
		confirmPassword:""
    });
}