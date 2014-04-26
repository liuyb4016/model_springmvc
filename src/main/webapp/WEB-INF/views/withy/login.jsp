<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ include file="../includes.jsp"%>
<html>
<head>
<meta name="decorator" content="adminlogin" />
<title>应用管理平台</title>
<style type="text/css">
body {font-family: "宋体",Arial,Lucida,Verdana,Helvetica,sans-serif;font-size: 12px;color: #333;line-height: 150%;background: url(resources/images/loginbg.gif) 0 0 repeat-x;}
a:hover {color: #C00;text-decoration: underline;}
a:link, a:visited {color: #333;text-decoration: none;}
a:link, a:visited {color: #333;text-decoration: none;}
#loginDiv{width:449px;margin:100px auto 0;}
.loginlogo{width:449px; height:120px; /* background:url(resources/images/loginlogo.png) center 0 no-repeat; */}
#loginDiv .mt{background: url(resources/images/crumbbg.gif) 0 0 repeat-x;color:#7dbf0d;height:33px; padding:0 20px; border:#a6d0e7 1px solid; border-bottom:none;border-top-left-radius: 10px; border-top-right-radius: 10px; -moz-border-radius-topright: 10px;  -moz-border-radius-topleft: 10px;}
#loginDiv h2{color:#7dbf0d;font-size: 14px;line-height:14px;}
#loginDiv .mc{padding:20px;background:#f7f7f7;border:#a6d0e7 1px solid; border-top:none;border-bottom-left-radius: 10px; border-bottom-right-radius: 10px; -moz-border-radius-bottomright: 10px; -moz-border-radius-bottomleft: 10px;}
#loginDiv .item{overflow:hidden;padding:10px 0;zoom:1;margin-left:60px;}
#loginDiv span{float:left;margin:3px 5px 0 0;}
#loginDiv .text{background:url(resources/images/short.jpg) 0 -215px no-repeat;height:28px;line-height:28px;width:201px;padding:0 3px;border:none;float:left;}
#loginDiv .checkbox{float:left;margin:5px 5px 0 0;*margin:-2px 5px 0 0;_margin:2px 5px 0 0;}
#loginDiv .itema{margin-left:114px;}
#loginDiv .itemb{paddding-top:20px;margin-left:87px;}
#loginDiv a{width:92px;text-align:center;height:33px;line-height:33px;float:right;margin-left:30px;margin-right:30px;font-weight:bold;}
#loginDiv .replay{background:url(resources/images/short.jpg) -112px -248px no-repeat;}
#loginDiv .login{background:url(resources/images/short.jpg) 0 -248px no-repeat;color:#ffffff;}
#loginDiv .mb{height:23px;overflow:hidden;}
#loginDiv .border{border-top:1px solid #d1d1d1;overflow:hidden;height:5px;line-height:5px;0}
</style>
</head>
<body>
<div id="loginDiv">
  <div class="loginlogo"></div>
  <div class="mt"><h2>欢迎登录应用管理平台</h2></div>
  <div class="mc">
		<form:form action="portal/login" modelAttribute="user" method="post" id="form">
          <div class="item"><span>用户名</span>
          		<form:input class="text" path="username"/> <form:errors path="username" class="errormsg"/>
          		</div>
          <div class="item"><span>密&nbsp;&nbsp;码</span>
            <form:password  class="text" path="password"/> <form:errors path="password" class="errormsg"/>
            </div>
          <div class="item"><span>验证码</span>
                    <form:input path="vcode" class="text" maxlength="10" style="width:112px;margin-right:10x;"/>
                    <span class="verify" id="verify">
                    <img src="portal/ajaxcontrol/verifycode" id="verifyimg" title="看不清？点击刷新！" style="border:1px solid #CCC;cursor:pointer;float:left;margin-left:2px;margin-top:-3px;"" />
                    </span>
                    <span id="vcodeErrorMsg" class="errormsg"/></span>
         			
         </div>         
         <div class="border"></div>
          <div class="item itemb">
          	<span><input type="checkbox" class="inp_check" id="remem_name" checked="checked"/>记住帐号<a href="javascript:$('#form').submit();" class="login" id="login">登&nbsp;&nbsp;录</a> </span>
		  </div>
      	<div id="error_message" style="color:#ff0000"><form:errors></form:errors></div>
		</form:form>
  </div>
  <div class="mb"></div>
</div>
<script type="text/javascript" src="resources/js/login.js" charset="UTF-8">
</script>
</body>
</html>
