<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="../includes.jsp"%>
<!DOCTYPE table PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="decorator" content="admin" />
<title>应用上传</title>
<link href="resources/styles/skins/chrome.css" rel="stylesheet" type="text/css" />
<style type="text/css">
.ifile {position:absolute;opacity:0;filter:alpha(opacity=0);}
</style>
</head>
<body class="right noborder">
<form:form action="portal/appupload" modelAttribute="uploadinfo" enctype="multipart/form-data" method="post" onsubmit="return checkForm();">
	<div class="headings altheading">
		<h2>应用上传</h2>
	</div>
	<div class="contentboxEmpty">
					<table class="rightform">
						<tr>
							<td><form:errors class="errormsg"></form:errors></td>
						</tr>
						<tr>
							<td>
								<label><span style="color: red">*</span>应用文件：</label>
								<spring:bind path="uploadinfo.apk">
								<input type="file" name="${status.expression}"/>
								</spring:bind>
							</td>
						</tr>
						<tr>
							<td>
								<input type="submit" class="btn" value="提交"/>
								<input type="reset" class="btn" value="重置"/>
							</td>
						</tr>
		
					</table>
	</div>
</form:form>
<script type="text/javascript" src="resources/js/artDialog.min.js"></script>
<script type="text/javascript">
function checkForm() {
	$(".errormsg").hide();
	if($("input[name='apk']").val() == ''){
		$("input[name='apk']").after("<span class='errormsg'>请选择应用文件</span>");
		$("input[name='apk']").focus();
		return false;
	}
	return true;
}

$(function(){
	<c:if test="${uploaded != null && uploaded}">
		toastConfirm("上传成功");
	</c:if>
	<c:if test="${resultB != null && resultB}">
		toastConfirm("上传失败或上传应用版本低于已存在应用版本，请重新填写");
	</c:if>
})
function setSelectNav(){
	selectNav("appupload");
}
</script>
</body>
</html>        