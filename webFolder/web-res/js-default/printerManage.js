function submitForm(){
	if($("#ff").form('validate')) {
		var data ={};
		data.printerName = $("#printerName").val();
		data = JSON.stringify(data);
		jqueryAjaxData("MerchantManageController", "saveMerchantPrinter", data, finishSaveMerchantPrinter);
	}
}

function finishSaveMerchantPrinter(data) {
	var ret = eval(data);
	if(ret.state === 1) {
		showMessage("成功","打印机设置成功！","show");
	}
}

function testPrint() {
	printTestInfo();
}

function printTestInfo(){
	var LODOP = getLodop();
	if ((LODOP==null)||(typeof(LODOP.VERSION)=="undefined")) {
		return;
	}
	
	LODOP.PRINT_INIT("打印机测试"); 
	LODOP.SET_PRINT_MODE("CREATE_CUSTOM_PAGE_NAME", "tag");
	LODOP.SET_PRINT_STYLE("FontSize",9)
	if ($("#printerName").val() !="") {
		LODOP.SET_PRINTER_INDEXA($("#printerName").val());
	}
	//LODOP.SET_PRINT_PAGESIZE(1,54,80,"CREATE_CUSTOM_PAGE_NAME");
	LODOP.ADD_PRINT_TEXT("6mm", "0.5mm", "54mm", "6mm", "打印机测试信息");
	LODOP.ADD_PRINT_TEXT("12mm", "0.5mm", "54mm", "6mm", "打印机测试信息");
	LODOP.ADD_PRINT_TEXT("18mm", "0.5mm", "54mm", "6mm", "打印机测试信息");
	LODOP.ADD_PRINT_TEXT("24mm", "0.5mm", "54mm", "6mm", "打印机测试信息");
	LODOP.ADD_PRINT_TEXT("30mm", "0.5mm", "54mm", "6mm", "打印机测试信息");
	LODOP.ADD_PRINT_TEXT("36mm", "0.5mm", "54mm", "6mm", "打印机测试信息");
	LODOP.ADD_PRINT_TEXT("42mm", "0.5mm", "54mm", "6mm", "打印机测试信息");
	LODOP.ADD_PRINT_TEXT("50mm", "0.5mm", "54mm", "6mm", "打印机测试信息");
	LODOP.PRINT();
}