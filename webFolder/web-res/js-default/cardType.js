var dataType;
function newUser() {
	$('#dlg').dialog('open').dialog('center').dialog('setTitle', '新贵宾卡类型');
	$('#fm').form('clear');
	dataType = 'save';
}
function editUser() {
	var row = $('#dg').datagrid('getSelected');
	if (row) {
		$('#dlg').dialog('open').dialog('center').dialog('setTitle', '修改贵宾卡类型');
		$('#fm').form('load', {
			cardTypeName:row.cardTypeName,
			cardTypeCode:row.cardTypeCode,
			cardTypeBalance:row.cardTypeBalance
		});
		$("#oldCardTypeCode").val(row.cardTypeCode);
		dataType = "edit";
	}
}
function submitCardType() {
	if($("#fm").form('validate')) {
		var data ={};
		var submitFunction = "saveCardType";
		data.cardTypeBalance = $("#cardTypeBalance").val();
		data.cardTypeCode = $("#cardTypeCode").val();
		data.cardTypeName = $("#cardTypeName").val();
		
		if (dataType === "edit") {
			data.cardTypeCode = $("#oldCardTypeCode").val();
			data.newCardTypeCode = $("#cardTypeCode").val();
			submitFunction = "updateCardType";
		}
		data = JSON.stringify(data);
		jqueryAjaxData("CardManageController", submitFunction, data, finishSubmitCardType);
	}
}

function finishSubmitCardType(data) {
	var ret = eval(data);
	if(ret.state === 1) {
		$('#dlg').dialog('close');
		$('#dg').datagrid('reload');
	}
}

function destoryCardType() {
	var row = $('#dg').datagrid('getSelected');
	if (row) {
		$.messager.confirm('提示', '你确定删除该贵宾卡类型么？', function(r) {
			if (r) {
				var data ={};
				data.cardTypeCode = row.cardTypeCode;
				data = JSON.stringify(data);
				jqueryAjaxData("CardManageController", "deleteCardType", data, finishDeleteCardType);
			}
		});
	}
}

function finishDeleteCardType(data) {
	var ret = eval(data);
	var message = "删除贵宾卡类型成功";
	if(ret.state === 1) {
		showMessage("成功",message,"show");
		$("#dg").datagrid('reload');
	}
}