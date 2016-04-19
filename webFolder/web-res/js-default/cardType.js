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
			cardTypeCode:row.cardTypeCode
		});
		dataType = "edit";
	}
}
function submitCardType() {
	var data ={};
	var submitFunction = "saveCardType";
	data.cardTypeBalance = $("#cardTypeBalance").val();
	if (dataType === "edit") {
		data.cardTypeCode = $("#cardTypeCode").val();
		submitFunction = "updateCardType";
	}
	data = JSON.stringify(data);
	jqueryAjaxData("CardManageController", submitFunction, data, finishSubmitCardType);
}

function finishSubmitCardType(data) {
	var ret = eval(data);
	if(ret.state === 1) {
		$('#dlg').dialog('close');
		$('#dg').datagrid('reload');
	}
}

function destroyUser() {
	var row = $('#dg').datagrid('getSelected');
	if (row) {
		$.messager.confirm('Confirm',
				'Are you sure you want to destroy this user?', function(r) {
					if (r) {
						$.post('destroy_user.php', {
							id : row.id
						}, function(result) {
							if (result.success) {
								$('#dg').datagrid('reload'); // reload the
																// user data
							} else {
								$.messager.show({ // show error message
									title : 'Error',
									msg : result.errorMsg
								});
							}
						}, 'json');
					}
				});
	}
}