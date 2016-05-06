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

function printBalance() {
	var row = $('#dg').datagrid('getSelected');
	if (row) {
		printCardConsume(row.cardId, row.cardTypeName, row.cost, row.costCardBalance, row.costTime, row.merchantName);
	}
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

function printCardConsume(cardId, cardTypeName, cost, cardBalance, costTime, merchantName){
	var LODOP = getLodop();
	if ((LODOP==null)||(typeof(LODOP.VERSION)=="undefined")) {
		return;
	}
	
	LODOP.PRINT_INIT("贵宾卡消费"); 
	LODOP.SET_PRINT_MODE("CREATE_CUSTOM_PAGE_NAME", "tag");
	LODOP.SET_PRINT_STYLE("FontSize",9)
	//LODOP.SET_PRINT_PAGESIZE(1,54,80,"CREATE_CUSTOM_PAGE_NAME");
	LODOP.ADD_PRINT_TEXT("6mm", "0.5mm", "54mm", "6mm", "卡号：" + cardId);
	LODOP.ADD_PRINT_TEXT("12mm", "0.5mm", "54mm", "6mm", "卡类型：" + cardTypeName);
	LODOP.ADD_PRINT_TEXT("18mm", "0.5mm", "54mm", "6mm", "消费金额（元）：" + cost);
	LODOP.ADD_PRINT_TEXT("24mm", "0.5mm", "54mm", "6mm", "卡余额（元）：" + cardBalance);
	LODOP.ADD_PRINT_TEXT("30mm", "0.5mm", "54mm", "6mm", "消费日期：" + costTime);
	LODOP.ADD_PRINT_TEXT("36mm", "0.5mm", "54mm", "6mm", "消费门店：" + merchantName);
	LODOP.ADD_PRINT_TEXT("42mm", "0.5mm", "54mm", "6mm", "客户签字：");
	LODOP.ADD_PRINT_TEXT("50mm", "0.5mm", "54mm", "6mm", "-----------------------------");
	LODOP.PRINT();
}