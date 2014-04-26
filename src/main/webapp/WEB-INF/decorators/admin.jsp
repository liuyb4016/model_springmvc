<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ include file="includes.jsp"%>
<title><decorator:title default="应用管理平台" /></title>
<link href="resources/styles/layout.css" rel="stylesheet" type="text/css" />
<link href="resources/styles/admin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="resources/js/jquery-1.7.2.min.js" ></script>
<script type='text/javascript' src='resources/js/jquery-ui.min.js'></script>
<script type="text/javascript" src="resources/js/json2.js"></script>
<script type="text/javascript" src="resources/js/content.js" charset="UTF-8"></script>
<script type="text/javascript" src="resources/js/artDialog.min.js"></script>
<script type="text/javascript" src="resources/js/enhance.js"></script>	
<script type='text/javascript' src='resources/js/excanvas.js'></script>
<script type='text/javascript' src='resources/js/jquery.wysiwyg.js'></script>
<script type='text/javascript' src='resources/js/visualize.jQuery.js'></script>
<script type="text/javascript" src='resources/js/functions.js'></script>
<decorator:head />
</head>
<body id="homepage">
	<div id="header">
    	<a href="portal/" title="" class="logo" ><!-- <img src="resources/images/logo.png" alt=""/> --></a>
        <div class="tit"><img src="resources/images/tit.png" alt=""/></div>
    	<div class="loginarea">
            <span><a href="portal/logout">退出</a></span>
            <!-- <span><a href="portal/contactus">联系我们</a></span> -->
        </div>
    </div>
        
    <!-- Top Breadcrumb Start -->
    <div id="breadcrumb">
    	<span style="display: inline-block;" id="headTitle">应用管理平台</span>
    	<span><input type="button" onclick="refreshPortal()" value="发布修改" title="将后台所修改的全部内容更新到前台，请务必在后台更新完内容后操作一次"/></span>
    	<span id="dynamicTime" style=" display: inline-block; float:right; margin-right: 20px;"></span>
    </div>
    <!-- Top Breadcrumb End -->
     
    <!-- Right Side/Main Content Start -->
    <div id="rightside">
        
        <!-- Alternative Content Box Start -->
         <div class="contentcontainer"> 
            	<decorator:body />
        </div>
        <!-- Alternative Content Box End -->
          
    </div>
    <!-- Right Side/Main Content End -->
    
        <!-- Left Dark Bar Start -->
    <div id="leftside">
    	<div class="user">
        	<img src="resources/images/avatar.png" width="20" height="20" class="hoverimg" alt="Avatar" />
            <p id="user_loginfo"></p>
        </div>
        <ul id="nav">
        
        </ul>

    </div>
<script type="text/javascript">
function addFunction() {
	$("#nav > li > a.collapsed + ul").slideToggle("medium");
	$("#nav > li > a").click(function() {
		$(this).toggleClass('expanded').toggleClass('collapsed').parent().find('> ul').slideToggle('medium');
	});
}
showMenu(checkLoginStat());
function refreshPortal(){
	var url = "portal/updateAppMemcached";
	$.ajax({
        url: url,
        type: "GET",
        data: {rnd:new Date().getTime() },
        async:false,
        success: function(result) {
        	if (result) {
				alert('缓存数据修改成功！');
        	} else {
				alert('缓存数据修改成功！');
        	}
        },
        error:function(result){
        	alert('缓存数据修改失败！');
        }
	});
}

function show() 
{ 
	var now=new Date();
	var minutes=now.getMinutes(); 
	var seconds=now.getSeconds(); 
	var d=new Array("星期日","星期一","星期二","星期三","星期四","星期五","星期六");
	if(minutes<=9) 
		minutes="0"+minutes 
	if(seconds<=9) 
		seconds="0"+seconds;
	$("#dynamicTime").text(" "+now.getFullYear()+"年"+(now.getMonth()+1)+"月"+now.getDate()+"日"+" "+now.getHours()+":"+minutes+":"+seconds+" "+d[now.getDay()]);
	setTimeout("show()",1000); 
}
show();
</script>
</body>
</html>
