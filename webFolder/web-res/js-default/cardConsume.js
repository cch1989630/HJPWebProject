$(document).ready(function () {
	$("#cardId").textbox({onClickButton:function(){
		queryCardInfo();
    }});
	
	$("#cost").textbox({
		onChange:function(newValue,oldValue){
			var cost = $("#cost").val();
			var cardBalance = $("#cardBalance").val();
			if (cardBalance === "" && cost != "") {
				showMessage("错误","请先查询贵宾卡信息！","show");
				return;
			}
			if (parseInt(cost) > parseInt(cardBalance)) {
				showMessage("错误","卡余额不足，请重新输入！","show");
				$("#cost").numberbox("setValue", cardBalance);
				return;
			}
			$('#cardBalance').textbox('setValue',$("#cardBalance").val()-cost)
		}
	});
});

function queryCardInfo() {
	var cardId = $("#cardId").val();
	if (cardId === "") {
		showMessage("错误","您没有输入贵宾卡号","show");
	} else {
		var data ={};
		data.cardId = cardId;
		data = JSON.stringify(data);
		jqueryAjaxData("CardManageController", "queryCardInfo", data, finishQueryCardInfo);
	}
}

function finishQueryCardInfo(data) {
	var ret = eval(data);
	if(ret.state === 1) {
		$('#ff').form('load',{
			cardBalance:ret.cardBalance,
			hodeCardName:ret.hodeCardName,
			hodeCardPhone:ret.hodeCardPhone,
			createTime:ret.createTime
        });
		
		$('#cardTypeCode').combobox('setValue',ret.cardTypeCode);
		
		var curr_time = new Date();
		var strDate = curr_time.getFullYear()+"-";
		strDate += curr_time.getMonth()+1+"-";
		strDate += curr_time.getDate()+"-";
		strDate += curr_time.getHours()+":";
		strDate += curr_time.getMinutes()+":";
		strDate += curr_time.getSeconds();
		$("#costTime").datebox("setValue", strDate);
	}
}

function submitForm(){
	if($("#ff").form('validate')) {
		var cost = $("#cost").val();
		var cardBalance = $("#cardBalance").val();
		if (cardBalance === "") {
			showMessage("错误","请先查询贵宾卡信息！","show");
			return;
		}
		if (parseInt(cost) > parseInt(cardBalance)) {
			showMessage("错误","卡余额不足，请重新输入！","show");
			$("#cost").numberbox("setValue", cardBalance);
			return;
		}
		var data ={};
		data.cost = $("#cost").val();
		data.cardId = $("#cardId").val();
		data.costTime = $("#costTime").val();
		data.cardBalance = $("#cardBalance").val();
		data = JSON.stringify(data);
		var message = "卡号为" + $("#cardId").val() + $('#cardTypeCode').combobox('getText') + ",本次消费金额为" + $("#cost").val() + 
			"元,本次消费后余额为" + $("#cardBalance").val() + "元,请确认！";
		$.messager.confirm("操作提示", message, function (info) {  
            if (info) {  
                //alert("确定");
            	jqueryAjaxData("CardManageController", "checkOutBalance", data, finishCheckOutBalance);
            } else {  
                //alert("取消");  
            }  
        });
		
		
	}
}

function finishCheckOutBalance(data) {
	var ret = eval(data);
	if(ret.state === 1) {
		showMessage("成功","贵宾卡消费成功！","show",function(){
			clearForm()
		});
	}
}

function clearForm(){
	//$('#ff').form('clear');
	$('#ff').form('load',{
		cardBalance:"",
		hodeCardName:"",
		hodeCardPhone:"",
		createTime:"",
		cost:"",
		cardId:""
    });
	
	$('#cardTypeCode').combobox('setValue',"");
	$("#costTime").datebox("setValue", "");
}
