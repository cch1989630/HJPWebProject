function subForm() {
	var username = $("#j_username").val();
	if(username == ''){
		showMessage("错误","用户名不能为空","show");
		return false;
	}
	var password = $("#j_password").val();
	if(password == ''){
		showMessage("错误","密码不能为空","show");
		return false;
	}
	
	$.ajax({
		type : "POST",
		dataType : "JSON",// 返回json格式的数据
		url : "checkUserExist.do",// 要访问的后台地址
		data : "username="+username,// 要发送的数据
		cache : false,
		success : function(response) {
			var ret = eval(response);
			if (ret.status == '1') { 
				document.getElementById("busiForm").submit();
			} else {
				showMessage("错误",ret.message,"show");
			}
		}
	});
}

$(document).ready(function () {
	var error = $("#error").val();
	if (error != "") {
		showMessage("错误",error,"show");
	}
});

