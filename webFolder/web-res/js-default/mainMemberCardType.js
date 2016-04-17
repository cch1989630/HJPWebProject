function queryCardByType() {
	if($("#ff").form('validate')) {
		var cardTypeCode = $('#cardTypeCode').combobox('getValue');
		//查询参数直接添加在queryParams中      
        var queryParams = $('#dg').datagrid('options').queryParams;  
        queryParams.cardTypeCode = cardTypeCode;
        $('#dg').datagrid('options').queryParams = queryParams;  
        $("#dg").datagrid('reload');
	}
}

function exportExcl() {
	var cardTypeCode = $('#cardTypeCode').combobox('getValue');
	window.location.href = "exportMainConsumeByType.do?cardTypeCode=" + cardTypeCode;
}

$(document).ready(function () {
	$('#dg').datagrid({
		onLoadSuccess: function(data){
			//console.info(data);
			$('#allCost').textbox('setValue',data.allCost);
		}
	});
});