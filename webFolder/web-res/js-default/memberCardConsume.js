function queryMemberCardBalance() {
	if($("#ff").form('validate')) {
		var beginTime = $('#beginTime').datebox('getValue');
		var endTime = $('#endTime').datebox('getValue');
		var cardId = $('#cardId').val();
		//查询参数直接添加在queryParams中      
        var queryParams = $('#dg').datagrid('options').queryParams;  
        queryParams.beginTime = beginTime;
        queryParams.endTime = endTime;
        queryParams.cardId = cardId;
        $('#dg').datagrid('options').queryParams = queryParams;  
        $("#dg").datagrid('reload');
	}
}

function exportExcl() {
	var beginTime = $('#beginTime').datebox('getValue');
	var endTime = $('#endTime').datebox('getValue');
	var cardId = $('#cardId').val();
	window.location.href = "exportMemberConsume.do?beginTime=" + beginTime + "&endTime=" + endTime + "&cardId=" + cardId;
}

$(document).ready(function () {
	$('#dg').datagrid({
		onLoadSuccess: function(data){
			//console.info(data);
			$('#allCost').textbox('setValue',data.allCost);
			if (data.cardTypeCode != undefined) {
				$('#cardTypeCode').combobox('setValue',data.cardTypeCode);
				$('#openTime').datebox('setValue',data.createTime);
				$('#hodeCardName').textbox('setValue',data.hodeCardName);
			}
			
		}
	});
});