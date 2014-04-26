package cn.liuyb.app.common.utils;

import java.util.List;


public class ExcelEasyUtil {
	
	private StringBuffer templateHead = new StringBuffer(
		"<html>"+  
		"<head>"+  
		"<title>Excel</title>" +
		"<meta http-equiv='content-type' content='text/html; charset=UTF-8' />"+  
		"</head>"+  
		"<body>"+  
		  "<table border='1' >"  
		  );
	private StringBuffer templateTail = new StringBuffer(
		  "</table>"+  
		"</body>"+
		"</html>"
		);
	
	public String getText(List<List<String>> sheelData){
		for(List<String> l : sheelData){
			templateHead.append("<tr>");
			for(String s : l){
				templateHead.append("<td>"+s+"</td>");
			}
			templateHead.append("</tr>");
		}
		templateHead.append(templateTail);
		return new String(templateHead);
	}
	
}
