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
<title>应用管理</title>
<link href="resources/styles/jquery-ui/jquery.ui.resizable.css" rel="stylesheet" type="text/css">
<link href="resources/styles/skins/chrome.css" rel="stylesheet" type="text/css" />

</head>
<body>
	<div class="headings altheading">
		<h2>应用管理</h2>
	</div>
	<div class="contentbox">
		<table width="100%" id="sorttable">
			<tr class="table_head">
            	<th width="40px" class="thwithsep"><div>序号</div></th>
                <th width="50px" class="thwithsep"><div>图标</div></th>
                <th width="80px" class="thwithsep"><div>应用名</div></th>
                <th width="70px" class="thwithsep"><div>大小</div></th>
                <th width="470px" class="thwithsep"><div>下载地址</div></th>
                <th width="80px" class="thwithsep"><div>版本名称</div></th>
				<th width="80px" class="thwithsep"><div>更新时间</div></th>
				<th width="60px" class="thwithsep"><div>操作</div></th>
			</tr>
		</table>
		<ul class="pagination" id="pagego" style="display: none;"></ul>
	</div>

<script type="text/javascript" src="resources/js/jquery-ui/jquery.ui.core.min.js"></script>
<script type="text/javascript" src="resources/js/jquery-ui/jquery.ui.widget.min.js"></script>
<script type="text/javascript" src="resources/js/jquery-ui/jquery.ui.mouse.min.js"></script>
<script type="text/javascript" src="resources/js/jquery-ui/jquery.ui.resizable.min.js"></script>
<script type="text/javascript" src="resources/js/jquery-ui/jquery.ui.sortable.min.js"></script>
<script type="text/javascript" src="resources/js/artDialog.min.js"></script>

<script type="text/javascript">
var page = 1;
var step = 300;

function deleteApp(id){
	confirm('确定删除此应用吗?请谨慎删除！！',function(){
		if(id<=0){
			alert("删除应用参数传递错误!");
			return false;
		}
		var url = "portal/appmanage/deleteApp/"+id;

		$.ajax({
	        url: url,
	        type: "GET",
	        data: {rnd:new Date().getTime() },
	        async:false,
	        success: function(result) {
	        	if (result==1) {
					alert('删除应用信息成功！');
					refreshCurrentPage();
	        	} else if(result==-4) {
					alert('您未登录，请重新登录再操作！');
	        	}else if(result==-3) {
					alert('修改数据异常，请重新操作！');
	        	}else if(result==-1) {
					alert('传递的参数不正确,请重新操作！');
	        	}else if(result==-5) {
					alert('删除的App信息不存在,请重新刷新后操作！');
	        	}else{
	        		alert("你没有权限操作此功能");
	        	}
	        },
	        error: function() {
            	alert("你没有权限操作此功能");
            }
		});
		
	});
}

function appmanage_callback(data,dataType){
	var items = data[1];
	var html = "";
	var start = (getCurPage()-1)*300;
	$.each(items, function(i,item){
		html += "<tr class='contentlist'>";
		var count = start+i+1;
		html += "<td><div style='width:40px;overflow:hidden;' class='col0'>"+count+"</div></td>";
		
		html += "<td><div style='width:50px;overflow:hidden;' class='col1'><dl class='app'>";
		html += "<dt class='appicon' >";
		html += "<img src='portal/icon/"+antiXss(item.id)+"' /></dt>";
		html += "<dt></dt></dl></div></td>";
		html += "<td><div style='width:80px;overflow:hidden;' class='col2'>"+antiXss(item.title)+"</div></td>";
		html += "<td><div style='width:70px;overflow:hidden;' class='col2'>"+formatSize(antiXss(item.packSize))+"</div></td>";
		html += "<td><div style='width:460px;overflow:hidden;' class='col2'>"+getBaseUrl()+"portal/download/"+antiXss(item.id)+"</div></td>";
		html += "<td><div style='width:80px;overflow:hidden;' class='col2'>"+antiXss(item.versionName)+"</div></td>";
		html += "<td><div style='width:80px;overflow:hidden;' class='col2'>"+millsecondToDate(antiXss(item.updateTime),"yyyy-MM-dd")+"</div></td>";
		html += "<td class='function'><div style='width:60px;overflow:hidden;' class='col8'>" +
				"<a style='margin-right:5px;' title='删除应用' href='javascript:void(0)' onclick='deleteApp("+antiXss(item.id)+");return false;'>删除</a>"
		html += "<a style='margin-right:5px;' title='下载应用' href='portal/download/"+antiXss(item.id)+"' >下载</a>";
		html += "</div></td></tr>";
	});
	return html;
}

$(function(){
	refreshList("appmanage", page, step);
})
function setSelectNav(){
	selectNav("appmanage");
}
</script>
</body>
</html>        