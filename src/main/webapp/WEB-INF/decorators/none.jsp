<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<%@ include file="includes.jsp"%>
	<title><decorator:title default="应用管理平台" /></title>
	<link href="resources/styles/jquery-ui/jquery.ui.resizable.css" rel="stylesheet" type="text/css" />
	<link href="resources/styles/skins/chrome.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="resources/js/jquery-1.7.2.min.js" ></script>
	<script type="text/javascript" src="resources/js/content.js" charset="UTF-8"></script>
	<decorator:head />
<style type="text/css">
body {
	text-align: left;
	font:12px/1.8 "微软雅黑", Tahoma, Geneva, Arial, Helvetica, sans-serif;
	
	background: #FFF;
}
table {
	border-collapse: collapse;
	border-spacing: 0;
}
</style>
</head>

<body>
	<decorator:body />
</body>
</html>
