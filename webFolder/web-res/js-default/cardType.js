var url;
function newUser() {
	$('#dlg').dialog('open').dialog('center').dialog('setTitle', '新会员卡类型');
	$('#fm').form('clear');
	url = 'save_user.php';
}
function editUser() {
	var row = $('#dg').datagrid('getSelected');
	if (row) {
		$('#dlg').dialog('open').dialog('center').dialog('setTitle',
				'Edit User');
		$('#fm').form('load', row);
		url = 'update_user.php?id=' + row.id;
	}
}
function saveUser() {
	var data ={};
	data.cardTypeName = $("#cardTypeName").val();
	data = JSON.stringify(data);
	jqueryAjaxData("CardManageController", "saveCardType", data, finishSaveCardType);
}

function finishSaveCardType(data) {
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