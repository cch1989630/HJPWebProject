$(document).ready(function () {
	$("#cardId").textbox({onClickButton:function(){
		queryCardInfo();
    }});
});

function queryCardInfo() {
	var cardId = $("#cardId").val();
	if (cardId === "") {
		showMessage("错误","您没有输入会员卡号","show");
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
		jqueryAjaxData("CardManageController", "checkOutBalance", data, finishCheckOutBalance);
	}
}

function finishCheckOutBalance(data) {
	var ret = eval(data);
	if(ret.state === 1) {
		showMessage("成功","会员卡消费成功！","show",function(){
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
