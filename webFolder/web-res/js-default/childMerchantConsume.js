function queryMemberCardBalance() {
	if($("#ff").form('validate')) {
		var beginTime = $('#beginTime').datebox('getValue');
		var endTime = $('#endTime').datebox('getValue');
		//查询参数直接添加在queryParams中      
        var queryParams = $('#dg').datagrid('options').queryParams;  
        queryParams.beginTime = beginTime;
        queryParams.endTime = endTime;
        $('#dg').datagrid('options').queryParams = queryParams;  
        $("#dg").datagrid('reload');
	}
}

function exportExcl() {
	var beginTime = $('#beginTime').datebox('getValue');
	var endTime = $('#endTime').datebox('getValue');
	window.location.href = "exportChildConsume.do?beginTime=" + beginTime + "&endTime=" + endTime;
}

$(document).ready(function () {
	$('#dg').datagrid({
		onLoadSuccess: function(data){
			//console.info(data);
			$('#allCost').textbox('setValue',data.allCost);
		}
	});
});