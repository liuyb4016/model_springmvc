 /* type 请求类型
 * page 请求第几页
 * step 每页显示的数据数
 * dataType 不同的请求类型有不同的含义
 * q queryString
 * afterCallBack 函数执行结束之后执行的callback
 * begin,end for visit log
 */

var refreshData = {type:null, page:null, step:null, dataType:null,
		afterCallBack:null,	q:null, begin:null, end:null, viewmode:null};

function refreshCurrentPage(viewmode){
	refreshList(refreshData.type, refreshData.page, refreshData.step, 
			refreshData.dataType, refreshData.afterCallBack,
			refreshData.q, refreshData.begin, refreshData.end, viewmode!=null?viewmode:refreshData.viewmode, false);
}

function refreshPage(page, step){
	refreshList(refreshData.type, page, step, 
			refreshData.dataType, refreshData.afterCallBack,
			refreshData.q, refreshData.begin, refreshData.end, refreshData.viewmode, true);
}

function getStepFromCookie(defStep){
	var step = getCookie("pagestep");
	if (step == null || step.length == 0)
		step = defStep;
	setStep(step);
	return step;
}

function setStep(step){
	var expire = 30*24*3600*1000; //一个月
	setCookie("pagestep", step, expire);
}

function refreshList(type, page, step, dataType, afterCallBack, q, begin, end, viewmode, asyncmode,nameQuery) {
	refreshData = {type:type, page:page, step:step, dataType:dataType, 
			afterCallBack:afterCallBack, q:q, begin:begin, end:end, viewmode:viewmode};
	
	if (asyncmode == null) {
		asyncmode = true;
	}
	var url = "portal/"+type;
	
	if (q != null){
		url += "/query/";
	}else{
		url += "/page/";
	}
	url += step+"/"+page;
	if (dataType != null){
		for (var i=0; i<dataType.length; ++i) {
			url += ("/"+dataType[i]);
		}
		
	}

	var buttonCount = 10;
	var callback;
	if (type == "appmanage"){
		callback = appmanage_callback;	
		buttonCount = 15;
	}else if (type == "mobilefile"){//2014-01-21
		callback = mobilefile_callback;	
		buttonCount = 15;
	}
		
	function afterPageButton(){
		$(".pagebutton").click(function(){
    		var curpage = $(this).text();
    		refreshList(type, curpage, step, dataType, afterCallBack, q, begin, end,viewmode,null,nameQuery);
    	})
	}
	function showPage(showpage){
		refreshList(type, showpage, step, dataType, afterCallBack, q, begin, end,viewmode,null,nameQuery);
	}

	$.ajax({
        type: "GET",
        contentType: "application/json; charset=utf-8",
        data: {q:q, begin:begin, end:end, nameQuery:nameQuery},
        url: url, 
		async: asyncmode, 
        cache: false,
		dataType: "json",
		
        success: function(data){
		
			$("#head_checker").attr("checked", false);
			// 页面底部的跳转按钮
			var pageCount = data[0];
			v_pageCount = pageCount;
			addPageButton(pageCount, page, buttonCount, afterPageButton, showPage);
			setCurPage(page);
        	// 列表
        	$(".contentlist").remove();
			var html = callback(data, dataType);
			$(".table_head").after(html);
			
			// html 添加完成后的操作 可以做事件绑定等
			if (afterCallBack != null){
				afterCallBack();
			}

		}
    });

}

var _step = 10;
var _start = 0;
var _persist = null;
var _dt = null;
var _callback = null;
var _afterCall = null;

function doCallBack() {
	if (_persist == null) return;
	
	var subData = new Array();
	for (var i=_start,j=0; j<_step&&i<_persist[1].length; ++j,++i) {
		subData[j] = _persist[1][i];
	}
	
	if (subData.length == 0) {
		if (_afterCall != null){
			_afterCall();
		}
	} else {
		var sub = new Array();
		sub[0] = _persist[0];
		sub[1] = subData;
		var html = _callback(sub, _dt);
		_start += _step;
		$(".table_foot").before(html);
		setTimeout("doCallBack()", 10);
	}
	
}

function asyncCallBack(data, dataType, callback, afterCall) {
	_persist = data;
	_start = 0;
	_afterCall = afterCall;
	_callback = callback;
	_dt = dataType;
	
	doCallBack();
}

function isImageFile(mimeType) {
	// folder
	if (mimeType == null) {
		return false;
	}else if( (mimeType == "image/bmp") ||
			  (mimeType == "image/gif") ||
			  (mimeType == "image/jpeg") ||
			  (mimeType == "image/png") ){
		return true;
	}else{
		return false;
	}
}

function addPageButton(pageCount, page, buttonCount, call_back, show_call){
	
	$("#pagego").empty();
	$("#pagego").hide();
	if (pageCount > 0) {
		$("#pagego").show();
	}
	
	var startpos = Math.floor((page-1)/buttonCount)*buttonCount;
	if (startpos > 0){
		var firstStr = "<li class=\"text\"><a id=\"firstbutton\">首页</a></li>";
		$("#pagego").append(firstStr);
		
		var str = "<li class=\"text\"><a id=\"prevbutton\">上一页</a></li>";
		$("#pagego").append(str);
	}

	var i;
	for (i=startpos,j=0; i<pageCount&&j<buttonCount; ++i,++j) {
		var str = "<li ";
		if (i==page-1) {
			str += "class=\"page\"";
		}
		str += "><a class=\"pagebutton\">";
		str += (i+1);
		str += "</a></li>";
		$("#pagego").append(str);
	}
	
	if ((pageCount-startpos) > buttonCount){
		var str = "<li class=\"text\"><a id=\"nextbutton\">下一页</a></li>";
		$("#pagego").append(str);

		var lastStr = "<li class=\"text\"><a id=\"lastbutton\">末页</a></li>";
		$("#pagego").append(lastStr);
	}
	
	if(pageCount>buttonCount){
		var goPageStr = "<li class=\"text\">&nbsp;共"+pageCount+"页&nbsp;转到&nbsp;<input type=\"text\" id=\"gopage\" style=\"width:25px;\" /></li>" +
				"<li><a id=\"gobutton\">Go</a></li>";
		$("#pagego").append(goPageStr);
	}
	if (call_back != null){
		call_back();
	}
	
	$("#prevbutton").click(function(){
		show_call((startpos-buttonCount)<0?0:(startpos-buttonCount)+1);
		//show_call((startpos-1)<0?0:(startpos-1));
	})
	$("#nextbutton").click(function(){
		show_call(i+1);
		//show_call(getCurPage()+1);
	})
	$("#firstbutton").click(function(){
		show_call(1);
	})
	$("#lastbutton").click(function(){
		show_call(pageCount);
	})
	$("#gobutton").click(function(){
		show_call($("#gopage").val());
	})
	
}

var v_pageCount = 0;
function getVPageCount(){
	return v_pageCount;
}

function setVPageCount(page){
	v_pageCount = page;
}

var currentPage = 0;
function getCurPage(){
	return currentPage;
}

function setCurPage(page){
	currentPage = page;
}

function formatSize(size) {

	if (size == null)
		size = 0;
	var unit = " B";
	if (size >= 1024){
		size /= 1024;
		unit = " KB";
	}else{
		return size+unit;
	}
	
	if (size >= 1024){
		size /= 1024;
		unit = " MB";
	}else{
		return size.toFixed(2)+unit;
	}
	
	if (size >= 1024){
		size /= 1024;
		unit = " GB";
	}else{
		return size.toFixed(2)+unit;
	}
	return size.toFixed(2)+unit;
	//	return null;
}

function getSelectedUser(){
	var ids = new Array();
	var i=0;
	$(".item_checker").each(function(){
		if ($(this).attr("checked")){
			var id = $(this).attr("mid");
			if (id != null){
				ids[i] = id;
				++i;
			}
			
		}
	})
	
	return ids;
}

function formatDuration(duration) {
	var text = "";

	var sec = duration;
	var min = 0;
	var hour = 0;
	var day = 0;

	if (sec < 60) {
		text = sec+"秒";
		return text;
	} else{
		min = Math.floor(sec/60).toFixed(0);
		sec = sec%60;
	}
	
	if (min < 60) {
		text = min+"分"+sec+"秒";
		return text;
	} else {
		hour = Math.floor(min/60).toFixed(0);
		min = min%60;
	}
	
	if (hour < 24) {
		text = hour+"时"+min+"分"+sec+"秒";
		return text;
	} else {
		day = Math.floor(hour/24).toFixed(0);
		hour = hour%24;
		text = day+"天"+hour+"时"+min+"分"+sec+"秒";
	}
	return text;
}

function formatString(string, def) {
	if (typeof string == "string") {
		string = string.replace(/\u0000/g, '');
	} else if (string == null) {
		if (def == null)
			return "";
		else
			return def;
	}
	
	return string;
}

function resizeHandler(offset) {

	if (offset == null) offset = 1;
	$(".thwithsep").each(function(i){
		var self = $(this);
		var obj = $(this).children().eq(0);

		var tobj = $(".col"+(i+offset));
		obj = obj.add(tobj);

		$(this).resizable({
			handles: "e",
			resize: function(event, ui)
			{
				obj.css("width",ui.size.width+"px");
			}
		});
	})

}

function getDialog(type) {
	var dlg = art.dialog.list[type];
	if (dlg == null) {
		for (var i in art.dialog.list) {
			var iframe = art.dialog.list[i].iframe;
			if (iframe != null) {
				dlg = iframe.contentWindow.art.dialog.list[type];
			}
			
		}
		
	}
	
	return dlg;
}

function alert(content, func) {
	art.dialog({
		title:"提示",
		skin: 'chrome',
	    content: content,
	    yesFn: function(){
	    	return true;
	    },
	    yesText: "确定",
	    noFn: null,
	    closeFn:function(){
	    	if (func != null)
	    		func();
	    	return true;
	    }
	});
}

function confirm(content, func) {
	art.dialog({
		title:"提示",
		skin: 'chrome',
		content: content,
		yesFn: function(){
			this.hide();
				func();
			return true;
		},
		noFn: function(){
			return true;
		},
		yesText: '确定',
		noText: '取消'
	});
}

function download(url){
	window.open(url,"_self");
}

function toast(msg) {
	art.dialog({
		title:false,
	    lock: true,
	    skin: 'chrome',
	    content: msg,
	    time: 1.5,
	    yesFn: null,
	    noFn: null
	});
}

function toastConfirm(msg) {
	art.dialog({
		title:"提示",
		skin: 'chrome',
		content: msg,
		yesFn: function(){
			return true;
		},
		yesText: '关闭',
		closeFn: function(){
			return true;
		}
	});
}


function showMenu(isLogin) {
	if(isLogin){
		var content = '';
			content += "<li><a class=\"expanded heading\">修改密码</a><ul class=\"navigation\">";
			content += "<li class=\"dis\" id='updateselfpwd' ><a href='portal/updateselfpwd'>修改密码</a></li>";
			content += "</ul></li>"
			
			content += "<li><a class=\"expanded heading\">应用管理</a><ul class=\"navigation\">";
			content += "<li class=\"dis\" id='appupload' ><a href='portal/appupload'>应用上传</a></li>";
			content += "<li class=\"dis\" id='appmanage' ><a href='portal/appmanage'>应用管理</a></li>";
			
			content += "</ul></li>";
				
			//2014-01-21:文件管理
			content += "<li><a class=\"expanded heading\">文件管理</a><ul class=\"navigation\">";
			content += "<li class=\"dis\" id='mobilefile' ><a href='portal/mobilefile'>文件管理</a></li>";
			content += "</ul></li>";
		$("#nav").html('');
		$("#nav").html(content);
		if(typeof setSelectNav != 'undefined' &&setSelectNav instanceof Function) setSelectNav();
		if(typeof addFunction != 'undefined' &&addFunction instanceof Function) addFunction();
	}
}

function checkLoginStat() {
	var ret = false;
	jQuery.ajax({
        type: "GET",
        url: "portal/checklogin?rnd="+new Date().getTime(),
        async: false,
        success: function(msg) {
        	ret = msg;
        }
    });
	
	return ret;
}

$(function(){
    
	var isLogin = checkLoginStat();
	if (isLogin){
		$(".dis").removeClass("dis");
		$("#reglink").attr("title", "登出");
		$("#reglink").attr("href", "portal/logout");
		$("#reglink").text("登出");
		$("#user_loginfo").text("您好，"+getUserName());
	}
	
	$.ajaxSetup({accepts:{json: "application/json;charset=UTF-8"}});
	
	$("#head_checker").click(function(){
		if($('#head_checker').attr("checked")){
			$(".item_checker").attr("checked",true);
		}else{
			$(".item_checker").attr("checked",false);
		}
	});
	
	$("#querybar input").keydown(function(event){
		  if (event.which == 13){
			  $("#querybar button").click();
		  }
		});
	
})

function getUserName() {
	var ret = "";
	$.ajax({
        type: "GET",
        contentType: "application/text; charset=utf-8",
        url: "portal/ajaxcontrol/getusername", 
        cache: false,
        async: false,
        success: function(data){
        	ret = data;
        }
	});	
	return ret;
}

function getBaseUrl() {
    var head = document.getElementsByTagName("head")[0]
    var base = head.getElementsByTagName("base")[0];
    if (base) {
        return base.href;
    } else {
        return "undefine";
    }
}
Date.prototype.format = function(format) {
	 var o = {
	 "M+" : this.getMonth()+1, // month
	 "d+" : this.getDate(),    // day
	 "h+" : this.getHours(),   // hour
	 "m+" : this.getMinutes(), // minute
	 "s+" : this.getSeconds(), // second
	 "q+" : Math.floor((this.getMonth()+3)/3),  // quarter
	 "S" : this.getMilliseconds() // millisecond
	 }
	 if(/(y+)/.test(format)) format=format.replace(RegExp.$1,
	 (this.getFullYear()+"").substr(4 - RegExp.$1.length));
	 for(var k in o)if(new RegExp("("+ k +")").test(format))
	 format = format.replace(RegExp.$1,
	 RegExp.$1.length==1 ? o[k] :
	 ("00"+ o[k]).substr((""+ o[k]).length));
	 return format;
	}

function millsecondToDate(millisecond, format) {
	if (millisecond == null)
		return "";
    var d = new Date;
    d.setTime(millisecond);
    return d.format(format);
}

function selectNav(id) {
	$(".sel").removeClass("sel").addClass("dis");
	$("#"+id+" a").addClass("sel");
	var v1 = $("#"+id+" a").text();
    var v2 = $("#"+id).parent().parent().find(".expanded").text();
	$("#headTitle").text(v2+">>"+v1);
}

function setCookie(c_name,value, milli){
var exdate=new Date();
exdate.setTime(milli+exdate.getTime());
document.cookie=c_name+ "=" +escape(value)+
((milli==null) ? "" : ";expires="+exdate.toGMTString())+";path=/;";
}

function delCookie(c_name) {
	var exdate=new Date();

	document.cookie=c_name+"=;expires="+exdate.toGMTString()+";path=/;";
}

function getCookie(c_name){
if (document.cookie.length>0)
  {
  c_start=document.cookie.indexOf(c_name + "=")
  if (c_start!=-1)
    { 
    c_start=c_start + c_name.length+1 
    c_end=document.cookie.indexOf(";",c_start)
    if (c_end==-1) c_end=document.cookie.length
    return unescape(document.cookie.substring(c_start,c_end))
    } 
  }
return ""
}

function checkPhoneNumber(phoneNumber) {
	if (/(\+[0-9]+[\- \.]*)?(\([0-9]+\)[\- \.]*)?([0-9][0-9\- \.][0-9\- \.]+[0-9])/.test(phoneNumber)){
		return true;
	}
	return false;
}

function checkVerify(verify) {
	var ret = false;
	jQuery.ajax({
        type: "GET",
        cache: false,
        url: "portal/ajaxcontrol/checkverify",
        async: false,
        data: "vcode="+verify,
        success: function(msg) {
        	ret = msg;
        }
    });
	
	return ret;
}

function refreshVerify(id) {
	var imgurl = "portal/ajaxcontrol/verifycode?rnd="+new Date().getTime();
	$("#"+id).attr("src", imgurl);
}

function antiXss(string) {
	if (typeof string == "string") {
		string = string.replace(/&/g, "&amp;"); //&
		string = string.replace(/'/g, "&#x27;"); //'
		string = string.replace(/"/g, "&quot;"); //"
		string = string.replace(/</g, "&lt;"); //<
		string = string.replace(/>/g, "&gt;"); //>
		string = string.replace(/:/g, "&#58;"); //:
		string = string.replace(/\//g, "&#x2F;"); ///
	}
	return string;
}
function checkPwd(pass) {
    if(pass=="") {
        return -1; //密码不能为空
    } else if (!(/^[^ ]+$/.test(pass))) {
    	return -3; //密码不能包含空格
    } /*else if (/^[^a-z]+$/.test(pass)) {
    	return -4; //密码中没有小写字母
    } else if (/^[^A-Z]+$/.test(pass)) {
    	return -5; //密码中没有大写字母
    } else if (/^[^0-9]+$/.test(pass)) {
    	return -6; //密码中没有数字
    } */else if(pass.length>20 || pass.length<6){
        //密码长度为6-20
        return -2;
    }

    return 1;
}

function checkEmail(email) {
	if(email==""){
		return false;
	}else if (/\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*/.test(email)) {
		return true;
	}

	return false;
}

function isEmpty(str){
   if(str != null && str.length > 0){
        return false;
    }
    return true;   
}


function equals(str1, str2){
    if(str1 == str2){  
        return true;   
    }
    return false;   
}   
  
  
function equalsIgnoreCase(str1, str2)   
{   
    if(str1.toUpperCase() == str2.toUpperCase())   
    {   
        return true;   
    }   
    return false;   
}

function isFloat(str)   
{   
    if(/^(-?\d+)(\.\d+)?$/.test(str))   
    {   
        return true;   
    }   
    return false;   
}   
