function queryMemberCardBalance() {
	if($("#ff").form('validate')) {
		var beginTime = $('#beginTime').datebox('getValue');
		var endTime = $('#endTime').datebox('getValue');
		//查询参数直接添加在queryParams中      
        var queryParams = $('#dg').datagrid('options').queryParams;  
        queryParams.endTime = endTime;
        queryParams.beginTime = beginTime;
        $('#dg').datagrid('options').queryParams = queryParams;  
        $("#dg").datagrid('reload');
	}
}

function monthCheckOut() {
	var data ={};
	data.beginTime = $('#beginTime').datebox('getValue');
	data.endTime = $('#endTime').datebox('getValue');
	data = JSON.stringify(data);
	jqueryAjaxData("FinanceController", "monthCheckOut", data, finishMonthCheckOut);
}

function finishMonthCheckOut(data) {
	var ret = eval(data);
	if(ret.state === 1) {
		showMessage("成功","月结成功！","show",function(){
			$('#dg').datagrid('reload');
		});
	}
}