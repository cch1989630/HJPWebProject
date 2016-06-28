var dataType;

function queryMemberCard() {
	if($("#fq").form('validate')) {
		var cardId = $('#queryCardId').val();
		var queryParams = $('#dg').datagrid('options').queryParams;  
		queryParams.cardId = cardId;
		$('#dg').datagrid('options').queryParams = queryParams;  
        $("#dg").datagrid('reload');
	}
}

function newMemberCard() {
	$('#dlg').dialog('open').dialog('center').dialog('setTitle', '新增贵宾卡');
	$('#ff').form('clear');
	$('#memberCreateTime').datebox('setValue', getCurrTime());
	dataType = 'save';
}
function editMemberCard() {
	var row = $('#dg').datagrid('getSelected');
	if (row) {
		$('#dlg').dialog('open').dialog('center').dialog('setTitle', '修改贵宾卡');
		$('#ff').form('load', {
			cardId:row.cardId,
			hodeCardName:row.hodeCardName,
			hodeCardPhone:row.hodeCardPhone,
			cardBalance:row.cardBalance
		});
		$('#cardTypeCode').combobox('setValue', row.cardTypeCode);
		$('#memberCreateTime').datebox('setValue', row.createTime);
		$('#oldCardId').val(row.cardId);
		dataType = "edit";
	}
}

function destoryMemberCard() {
	var row = $('#dg').datagrid('getSelected');
	if (row) {
		$.messager.confirm('提示', '你确定删除该贵宾卡么？', function(r) {
			if (r) {
				var data ={};
				data.cardId = row.cardId;
				data = JSON.stringify(data);
				jqueryAjaxData("CardManageController", "deleteMemberCard", data, finishDeleteMemberCard);
			}
		});
	}
}

function finishDeleteMemberCard(data) {
	var ret = eval(data);
	var message = "删除贵宾卡成功";
	if(ret.state === 1) {
		showMessage("成功",message,"show");
		$("#dg").datagrid('reload');
	}
}

function submitMemberCard() {
	var submitFunction = "addMemberCard";
	//手动校验form的数据问题
	if($("#ff").form('validate')) {
		var data ={};
		data.cardId = $("#cardId").val();
		data.hodeCardName = $("#hodeCardName").val();
		data.cardTypeCode = $('#cardTypeCode').combobox('getValue');
		data.hodeCardPhone = $("#hodeCardPhone").val();
		data.cardBalance = $("#cardBalance").val();
		data.createTime = $('#memberCreateTime').datebox('getValue');
		
		if (dataType === "edit") {
			data.newCardId= $("#cardId").val();
			data.cardId = $("#oldCardId").val()
			submitFunction = "updateMemberCard";
		}
		data = JSON.stringify(data);
		jqueryAjaxData("CardManageController", submitFunction, data, finishSubmitMemberCard);
	}
}

function finishSubmitMemberCard(data) {
	var ret = eval(data);
	var message = "新增贵宾卡成功";
	if (dataType === "edit") {
		message = "修改贵宾卡成功";
	}
	if(ret.state === 1) {
		showMessage("成功",message,"show",function(){
			
		});
		$('#ff').form('clear');
		$("#createTime").datebox("setValue", getCurrTime());
		$('#dlg').dialog('close');
		$("#dg").datagrid('reload');
	}
}

function clearForm(){
	$('#ff').form('clear');
}

$(document).ready(function () {
	if ($("#menuRoleCode").val() === 'ROLE_MANAGER') {
		$("#cardBalance").numberbox({
			disabled : false,
		});
	}
	
	$('#cardTypeCode').combobox({
		onChange: function(newValue,oldValue){
			var dataArray = $('#cardTypeCode').combobox('getData');
			var cardTypeBalance = "";
			for (var i = 0; i < dataArray.length; i++) {
				if (newValue === dataArray[i].id) {
					cardTypeBalance = dataArray[i].cardTypeBalance;
				}
			}
			//$('#cardTypeCode').combobox('setValue', cardTypeBalance);
			if (dataType === 'save') {
				$("#cardBalance").numberbox("setValue", cardTypeBalance);
			}
			//console.info(newValue);
		}
	});
});
