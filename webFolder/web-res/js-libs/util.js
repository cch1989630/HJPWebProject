function showMessage(title, msg, showType, callBack, timeOut) {
	if (showType === "show") {
		$.messager.alert({  
            title: title,  
            msg: msg,  
            showType: 'show',
            fn:function() {
            	if (typeof callBack === "function") {
               		callBack();
            	}
            }
        });
	} else if (showType === "slide") {
		$.messager.alert({  
            title: title,  
            msg: msg,  
            timeout: timeOut,  
            showType: 'slide'  
        }); 
	} else if (showType === "fade") {
		$.messager.alert({  
            title: title,  
            msg: msg,  
            timeout: timeOut,  
            showType: 'fade'  
        });  
	}
	
}

function load() {  
    $("<div class=\"datagrid-mask\"></div>").css({ display: "block", width: "100%", height: $(window).height() }).appendTo("body");  
    $("<div class=\"datagrid-mask-msg\"></div>").html("正在加载，请稍候。。。").appendTo("body").css({ display: "block", left: ($(document.body).outerWidth(true) - 190) / 2, top: ($(window).height() - 45) / 2 });  
} 

function disLoad() {  
    $(".datagrid-mask").remove();  
    $(".datagrid-mask-msg").remove();  
}

/**
 * 通过封装调用的ajax类，来统一管理ajax回调发生的异常等信息
 * 该方法主要用在无跳转的ajax调用
 * @param beanName		方法所在spring Bean名称
 * @param functionName		方法名
 * @param data		json数据
 * @param successfn		成功返回调用方法
 * @return
 */
function jqueryAjaxData(beanName, functionName, data, successfn) {
	data = (data==null || data=="" || typeof(data)=="undefined")? {"date": new Date().getTime()} : data;
	load();
	$.ajax({
        type: "post",
        data: {
			data:data,
			beanName:beanName,
			functionName:functionName
		},
        url: "ajaxNotUrl.do",
        dataType: "json",
        success: function(d){
            successfn(d);
        },
		error:function(XMLHttpRequest, textStatus, errorThrown) {
        	switch (XMLHttpRequest.status){  
	            case(500):  
	            	showMessage("错误",XMLHttpRequest.responseText,"show");
	                break;  
	            case(408):  
	            	showMessage("错误","请求超时","show");
	                break;  
	            default:  
	            	showMessage("错误","亲！系统出错了！工程师马上到！","show");
	        }  
		},
		complete: function(){
			disLoad();
		}
    });
}

/**
 * 下面的两个方法用于给时间控件设置初始化的时间，即当前时间
 * @param date
 * @returns {String}
 */
function myformatter(date){
    var y = date.getFullYear();
    var m = date.getMonth()+1;
    var d = date.getDate();
    var h = date.getHours();
    var min = date.getMinutes();
    var s = date.getSeconds();
    return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d) + ' ' + (h<10?('0'+h):h) + ':' + (min<10?('0'+min):min) + ':' + (s<10?('0'+s):s);
}

function beginTimeFormatter(date) {
	var y = date.getFullYear();
    var m = date.getMonth()+1;
    var d = date.getDate();
    return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d) + ' 00:00:00' ;
}

function endTimeFormatter(date) {
	var y = date.getFullYear();
    var m = date.getMonth()+1;
    var d = date.getDate();
    return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d) + ' 23:59:59' ;
}

function formatTime(date) {
	var y = date.getFullYear();
    var m = date.getMonth()+1;
    var d = date.getDate();
    return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
}

function changeParser(s) {
	if (!s) return new Date();
    var ss = (s.split(' '));
    var dd = ss[0].split('-')
    var y = parseInt(dd[0],10);
    var m = parseInt(dd[1],10);
    var d = parseInt(dd[2],10);
    var mm = ss[1].split(':');
    var h = parseInt(mm[0],10);
    var min = parseInt(mm[1],10);
    var s = parseInt(mm[2],10);
	    if (!isNaN(y) && !isNaN(m) && !isNaN(d) && !isNaN(h) && !isNaN(min) && !isNaN(s)){
	        return new Date(y,m-1,d,h,min,s);
	    }
}

function myparser(s){
	if (!s) return new Date();
    var ss = (s.split(' '));
    var dd = ss[0].split('-')
    var y = parseInt(dd[0],10);
    var m = parseInt(dd[1],10);
    var d = parseInt(dd[2],10);
    var mm = ss[1].split(':');
    var h = parseInt(mm[0],10);
    var min = parseInt(mm[1],10);
    var s = parseInt(mm[2],10);
    if (!isNaN(y) && !isNaN(m) && !isNaN(d) && !isNaN(h) && !isNaN(min) && !isNaN(s)){
        return new Date(y,m-1,d,h,min,s);
    } else {
        return new Date();
    }
}

function setparser(s){
    if (!s) return new Date();
    var ss = (s.split(' '));
    var dd = ss[0].split('-')
    var y = parseInt(dd[0],10);
    var m = parseInt(dd[1],10);
    var d = parseInt(dd[2],10);
    var mm = ss[1].split(':');
    var h = parseInt(mm[0],10);
    var min = parseInt(mm[1],10);
    var s = parseInt(mm[2],10);
    if (!isNaN(y) && !isNaN(m) && !isNaN(d) && !isNaN(h) && !isNaN(min) && !isNaN(s)){
        return new Date(y,m-1,d,h,min,s);
    } else {
        return new Date();
    }
}

function getCurrTime() {
	var curr_time = new Date();
	var strDate = curr_time.getFullYear()+"-";
	strDate += curr_time.getMonth()+1+"-";
	strDate += curr_time.getDate()+" ";
	strDate += curr_time.getHours()+":";
	strDate += curr_time.getMinutes()+":";
	strDate += curr_time.getSeconds();
	return strDate;
}

$(document).ready(function () {
	var curr_time = new Date();
	var strDate = curr_time.getFullYear()+"-";
	strDate += curr_time.getMonth()+1+"-";
	strDate += curr_time.getDate()+" ";
	strDate += curr_time.getHours()+":";
	strDate += curr_time.getMinutes()+":";
	strDate += curr_time.getSeconds();
	$("#createTime").datebox("setValue", strDate);
	
});


$.extend($.fn.validatebox.defaults.rules, {    
    /*必须和某个字段相等*/  
    equalTo: {  
        validator:function(value,param){  
            return $(param[0]).val() == value;  
        },  
        message:'两次密码不匹配'  
    }  
}); 

//增加返回选择了那个选项的conbobox
$.extend($.fn.combobox.methods, {
    selectedIndex: function (jq, index) {
        if (!index)
            index = 0;
        var data = $(jq).combobox('options').data;
        var vf = $(jq).combobox('options').valueField;
        $(jq).combobox('setValue', eval('data[index].' + vf));
    }
});