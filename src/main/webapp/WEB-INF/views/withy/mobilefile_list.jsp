<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="../includes.jsp"%>
<%
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<!DOCTYPE table PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="decorator" content="admin" />
<title>文件管理</title>
<link href="resources/styles/jquery-ui/jquery.ui.resizable.css" rel="stylesheet" type="text/css">
<link href="resources/styles/skins/chrome.css" rel="stylesheet" type="text/css" />
<link href="resources/js/mupload/uploadify.css" rel="stylesheet" type="text/css" />
<style type="text/css">

.col32 *{
	vertical-align:middle;
}
.word_content{
	word-wrap:break-word;white-space:normal;width:98%;
}
a.underline:hover{
text-decoration:underline;
color:blue;
}
</style>
</head>
<body>
	<div class="headings altheading">
		<h2>文件路径：<span style="color: blue;" id='folderPath'>
		<a href="javascript:void(0);" mid='0'>/根目录</a>
		
		</span></h2>
	</div>
	
	<div class="extrabottom"  >
		文件名:<input type='text' id='q' value=""/>
		<span   ><button id='searchUserIn' class='btn'  >查询</button></span>
		<span   ><button id='newFolder' class='btn' onclick='newFolder()' >新建文件夹</button></span>
		<span   ><button id='uploadFiles' class='btn'  title="上传文件到当前目录">上传文件</button></span>
		<span   ><button onclick='goBack()' class='btn'  title="返回上一级">返回上一级</button></span>
		<span   ><button onclick='delGroup()' class='btn'  title="批量删除文件">批量删除文件</button></span>
    </div>	
	
	<div class="contentbox">
		<table width="100%" id="sorttable">
			<tr class="table_head" >
            	<th width="40px" class="thwithsep"><div>&nbsp;<input type="checkbox"    id='delHeadCheckBox'  /></div></th>
            	<th width="40px" class="thwithsep"><div>&nbsp;序号</div></th>
                <th width="200px" class="thwithsep"><div>名称</div></th>
                <th width="80px" class="thwithsep"><div>文件大小</div></th>
                <th width="350px" class="thwithsep"><div>下载地址</div></th>
                <th width="80px" class="thwithsep"><div>更新时间</div></th>
				<th width="200px" class="thwithsep"><div>操作</div></th>
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
<script type="text/javascript" src="resources/js/mupload/jquery.uploadify.min.js"></script>

<script type="text/javascript">
var page = 1;
var step = 10;
var folderId=0;
var TYPEFOLDER='folder';
var TYPEFILE='file';
var downUrl="<%=basePath%>"+"portal/mupload/download/";
$(function(){
	refreshTableList($("#q").val());
	
	$("#searchUserIn").click(function(){
		refreshTableList($("#q").val());
	});
	
	
	$("#folderPath a").die().live('click',function(){
		$(this).nextAll().remove();
		folderId=$(this).attr("mid");
		refreshTableList($("#q").val());
	})
	
	//双击文件夹时，拼接路径
	$("tr[name^=folder]").die().live("dblclick",function(){
		var fid=$(this).attr('id');
		var folname=$("#name"+fid).text();
		html="<a href='javascript:void(0);' mid='"+fid+"'>/"+folname+"</a>";
		$("#folderPath").append(html);
		folderId=getFolderId();
		refreshTableList($("#q").val());
	});
	
})

function goIntoFolder(id,name){
	html="<a href='javascript:void(0);' mid='"+id+"'>/"+name+"</a>";
	$("#folderPath").append(html);
	folderId=getFolderId();
	refreshTableList($("#q").val());
}



$("#delHeadCheckBox").click(function(){
	if($("#delHeadCheckBox").attr("checked")){
		$("[name='delCheckBox']").attr("checked",true);
	}else{
		$("[name='delCheckBox']").attr("checked",false);
	}
})

function delGroup() {
	var apps = new Array();
	var i = 0;
	$("input[name='delCheckBox']:checkbox").each(function(){
		if($(this).attr("checked")){
			apps[i]=$(this).val();
			i++;
		}
	});
	if(apps.length<1){
		alert("请先选择要删除的文件!");
		return;
	}
	confirm("确定删除所选文件吗？", function() {
		
		var url = "portal/mobilefile/delGroup";
		$.ajax({
	        url: url,
	        type: "GET",
	        data: {ids:apps,rnd:new Date().getTime() },
	        async:false,
	        success: function(result) {
		     if (result==true){
		    	 alert("批量删除文件成功");
		    	 refreshTableList($("#q").val());
		     } else if (result==false){
		    	 alert("批量删除文件失败");
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



function goBack(){
	if(getFolderId()>0){
		$("#folderPath a:last").remove();
		folderId=getFolderId();
		refreshTableList($("#q").val());
	}else{
		alert("当前目录已经是根目录");
	}
	
}


function newFolder(){
	var content="";
	content += "<div>文件夹<span style='color:red;'>(*)</span>:<input type='text' id='fname'  name='fname'/></div>";
	var url="portal/mobilefile/addfolder";
	art.dialog({
		title:"新建文件夹",
		skin: 'chrome',
	    lock: true,
	    content: content,
	    yesFn: function() {
	    	var fname=$("#fname").val();
	    	if($.trim(fname).length<1){
	    		alert("文件夹名称不能为空,请填写!");
	    		return false;
	    	}else if(fname.length>20){
	    		alert("文件夹名称过长,请填写20个字符之内!");
	    		return false;
	    	}
	    	folderId=getFolderId();
	    	var isSuccess=true;
	    	$.ajax({
	            url: url,
	            type: "GET",
	            dataType: "json",
	            data:{fname:fname,folderId:folderId},
	            async:false,
	            cache:false,
	            success: function(result) {
	            	if (result==1) {
	            		alert("添加["+fname+"]文件夹成功!");
	            		refreshCurrentPage();
	            	} else if (result==-1){
	            		alert("添加["+fname+"]文件夹失败!");
	            	}else if (result==0){
	            		alert("["+fname+"]文件夹已经存在!");
	            		isSuccess= false;
	            	}else if (result==-2){
	            		alert("文件夹不能为空,请填写!");
	            	}
	            },
	            error: function() {
	            	alert("你没有权限操作此功能");
	            }
	    	});
	    	return isSuccess;
	    },
	    yesText: "确定",
	    noFn: function(){return true;},
	    noText: "取消"
	});
}


function rename(type,id){
	var url="portal/mobilefile/find/"+type+"/"+id;
	var fname="";
	$.ajax({
        url: url,
        type: "GET",
        data: {rnd:new Date() },
        async:false,
        success: function(data) {
        	if(data!=null){
	        	if(type==TYPEFOLDER){
	        		fname=data.folderName;
	        	}else{
	        		fname=data.nickName;
	        	}
        	}
        },
        error: function() {
        	alert("你没有权限操作此功能");
        }
	});
	
	
	var content="";
	var title='文件夹名称';
	if(type!=TYPEFOLDER){
		title='文件名';
	}
	content += "<div>"+title+"<span style='color:red;'>(*)</span>:<input type='text' id='fname' value='"+fname+"'  name='fname'/></div>";
	var url="portal/mobilefile/update/"+type+"/"+id;
	art.dialog({
		title:"修改"+title,
		skin: 'chrome',
	    lock: true,
	    content: content,
	    yesFn: function() {
	    	var fname=$("#fname").val();
	    	if($.trim(fname).length<1){
	    		alert(title+"不能为空,请填写!");
	    		return false;
	    	}else if(fname.length>20){
	    		alert(title+"过长,请填写20个字符之内!");
	    		return false;
	    	}
	    	var isSuccess=true;
	    	$.ajax({
	            url: url,
	            type: "GET",
	            dataType: "json",
	            data:{fname:fname},
	            async:false,
	            cache:false,
	            success: function(result) {
	            	if (result==1) {
	            		alert("修改["+fname+"]"+title+"成功!");
	            		refreshCurrentPage();
	            	} else if (result==-1){
	            		alert("修改["+fname+"]"+title+"失败!");
	            	}else if (result==-2){
	            		alert(title+"不能为空,请填写!");
	            	} else if (result==0){
	            		alert("["+fname+"]已经存在!");
	            		isSuccess=false;
	            	}
	            },
	            error: function() {
	            	alert("你没有权限操作此功能");
	            }
	    	});
	    	return isSuccess;
	    },
	    yesText: "确定",
	    noFn: function(){return true;},
	    noText: "取消"
	});
}



function del(type,id){
	var title="确定要删除该文件夹吗?请谨慎删除！！";
	var afterTitle="成功删除该文件夹";
	if(type!=TYPEFOLDER){
		title="确定要删除该文件吗?请谨慎删除！！";
		afterTitle="成功删除该文件";
	}
	confirm(title,function(){
		var url = "portal/mobilefile/del/"+type+"/"+id;
		$.ajax({
	        url: url,
	        type: "GET",
	        data: {rnd:new Date().getTime() },
	        async:false,
	        success: function(result) {
	        	if (result==true) {
					alert(afterTitle);
					refreshCurrentPage();
	        	} else if(result==false) {
					alert(afterTitle);
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








function getFolderId(){
	return $("#folderPath a:last").attr("mid");
}


function refreshTableList(q){
	refreshList("mobilefile", page, step,[folderId],null,q);
}


function mobilefile_callback(data,dataType){
	var html = "";
	var folderItems = data[1];
	var fileItems = data[2];
	var count = 0;
	if(folderItems!=null){
		$.each(folderItems, function(i,item){
			count +=1 ;
			if(count%2==0){
				html += "<tr class='contentlist' bgColor='#F2F2F2'  onMouseOver='this.bgColor=\"#CCFF99\";' onMouseOut='this.bgColor=\"#F2F2F2\";'  id='"+item.id+"' name='folder_'"+item.id+">";
			}else{
				html += "<tr class='contentlist'  onMouseOver='this.bgColor=\"#CCFF99\";' onMouseOut='this.bgColor=\"#ffffff\";' id='"+item.id+"' name='folder_'"+item.id+">";
			}
// 			html += "<tr class='contentlist' onMouseOver='this.style.backgroundColor=\"#F2F2F2\";' onMouseOut='this.style.backgroundColor=\"#ffffff\";'  id='"+item.id+"' name='folder_'"+item.id+">";
			html += "<td><input type=\"checkbox\" value="+antiXss(item.id)+"  name='delCheckBoxFolder' disabled='disabled'/></td>"
			html += "<td><div style='width:40px;overflow:hidden;' class='col0'>&nbsp;"+count+"</div></td>";
			html += "<td><div style='width:200px;overflow:hidden;' class='col32' id='name"+item.id
			+"' title='"+antiXss(item.folderName)
			+"'><a class='underline' onclick=\"goIntoFolder("+item.id+",'"+item.folderName+"')\" href='javascript:void(0);'><img src='resources/images/folder.png' width='30px' heigth='30px'>"
			+antiXss(item.folderName)+"</a></div></td>";
			html += "<td><div style='width:80px;overflow:hidden;' class='col2'>"+formatSize(antiXss(item.folderSize))+"</div></td>";
			html += "<td><div style='width:350px;overflow:hidden;' class='word_content'></div></td>";
			html += "<td><div style='width:80px;overflow:hidden;' class='col2'>"+millsecondToDate(antiXss(item.updateTime),"yyyy-MM-dd")+"</div></td>";
			
			html += "<td class='function'><div style='width:200px;overflow:hidden;' class='col8'>" ;
			html += "<a style='margin-right:5px;color:blue;' title='重命名' href='javascript:void(0)' onclick='rename(\"folder\","+antiXss(item.id)+");return false;'>重命名</a>"
// 			html += "<a style='margin-right:5px;' title='上传文件' href='javascript:void(0)' onclick='uploadFiles("+antiXss(item.id)+");return false;'>上传文件</a>"
			html += "<a style='margin-right:5px;color:red;' title='删除' href='javascript:void(0)' onclick='del(\"folder\","+antiXss(item.id)+");return false;'>删除</a>";
			html += "</div></td>";
			
			html+="</tr>";
		});
	}
	
	if(fileItems!=null){
		$.each(fileItems, function(i,item){
			count +=1 ;
			if(count%2==0){
				html += "<tr class='contentlist' bgColor='#F2F2F2'  onMouseOver='this.bgColor=\"#CCFF99\";' onMouseOut='this.bgColor=\"#F2F2F2\";'  id='file"+item.id+"'>";
			}else{
				html += "<tr class='contentlist' onMouseOver='this.bgColor=\"#CCFF99\";' onMouseOut='this.bgColor=\"#ffffff\";'  id='file"+item.id+"'>";
			}
// 			html += "<tr class='contentlist' onMouseOver='this.style.backgroundColor=\"#F2F2F2\";' onMouseOut='this.style.backgroundColor=\"#ffffff\";'  id='file"+item.id+"'>";
			html += "<td><input type=\"checkbox\" value="+antiXss(item.id)+"  name='delCheckBox'/></td>"
			html += "<td><div style='width:40px;overflow:hidden;' class='col0'>&nbsp;"+count+"</div></td>";
			html += "<td><div style='width:200px;overflow:hidden;' class='col32' title='"+antiXss(item.nickName)+"."+item.suffix+"'><img src='resources/images/file.png' width='30px' heigth='30px'>"
			+ antiXss(item.nickName)+"."+item.suffix+"</div></td>";
// 			+"<a  target='_blank' href='portal/mupload/download/"+antiXss(item.id)+"'>"+antiXss(item.nickName)+"</a></div></td>";
			html += "<td><div style='width:80px;overflow:hidden;' class='col2'>"+formatSize(antiXss(item.fileSize))+"</div></td>";
			var fdownurl=downUrl+item.id;
			html += "<td><div style='width:350px;overflow:hidden;' class='word_content' title='"+fdownurl+"'>"+fdownurl+"</div></td>";
			html += "<td><div style='width:80px;overflow:hidden;' class='col2'>"+millsecondToDate(antiXss(item.updateTime),"yyyy-MM-dd")+"</div></td>";
			
			html += "<td class='function'><div style='width:200px;overflow:hidden;' class='col8'>" ;
			html += "<a style='margin-right:5px;color:blue;' title='重命名' href='javascript:void(0)' onclick='rename(\"file\","+antiXss(item.id)+");return false;'>重命名</a>"
			html += "<a style='margin-right:5px;color:red;' title='删除' href='javascript:void(0)' onclick='del(\"file\","+antiXss(item.id)+");return false;'>删除</a>";
			html += "<a style='margin-right:5px;color:green;' title='下载'   href='portal/mupload/download/"+antiXss(item.id)+"'>下载</a>";
// 			html += "<a style='margin-right:5px;' title='复制下载链接' onclick=\"testclip('copy"+antiXss(item.id)+"')\" id='copy"+antiXss(item.id)+"'  herf='javascript:void(0);' class='copy' mid='"+antiXss(item.id)+"'>复制下载链接</a>";
			html += "</div></td>";
			
			html+="</tr>";
			
		});
		
	}
	
	
	if(html==""){
		html = "<tr class='contentlist'>";
		html+="<td colspan='5' style='text-align:center;'><span style='color: red;font-size: 20px;'>暂无数据!</span></td>";
		html+="</tr>";
	}
	
	return html;
}

 


function setSelectNav(){
	selectNav("mobilefile");
}
</script>


 <script type="text/javascript"> 
 var userId="${userId}";
 if(userId==null||userId==undefined||userId==''){
	 userId=0;
 }
 
 $(function() {
	 $("#uploadFiles").click(function(){
		 var html="<input type=\"file\" name=\"mupload1\" id=\"mupload\"  />";
		 art.dialog({
				title:"选择上传文件",
				skin: 'chrome',
			    lock: true,
			    content: html,
			    closeFn:function(){
			    },
			    id:'updialog'
		});
		 folderId=getFolderId();
		 muploadInit(folderId,userId)
	 })
	 
		 
		
 });    
 
 

 
 
 function muploadInit(fid,userIdVal){
	 var errorFileList=new Array();
	 var i=0;
	 var totalfiles=0;
	 var uploadsSuccessful=0;
	 var isSuccess=false;
	 $('#mupload').uploadify({
			'buttonText':'选择上传的文件',
			'auto':true,
			'fileSizeLimit' : 0,//文件的极限大小，以字节为单位，0为不限制。1MB:1*1024*1024
		 	'swf':'resources/js/mupload/uploadify.swf',
		 	'multi':true,
		 	'uploader' : 'mupload',
		 	'formData':{'folderId' : fid,'userId':userIdVal},
		 	'onDialogClose' : function(swfuploadifyQueue) {//当文件选择对话框关闭时触发
		 		totalfiles=swfuploadifyQueue.filesSelected;
		 	},
		 	'onUploadError':function(file,errorCode,erorMsg,errorString){//上传文件出错是触发（每个出错文件触发一次）
		 		errorFileList[i]=file.name+"文件上传异常:"+errorString;
		 		i++;
		 	},
		 	'onUploadSuccess':function(file,data,respone){//上传完成时触发（每个文件触发一次）
		 		var josnMsg= eval("(" + data + ")");
		 		if(josnMsg.succode==0){
		 			uploadsSuccessful++;
		 		}else if(josnMsg.succode==1){
		 			errorFileList[i]=file.name+"文件的文件名已经存在,请修改名称再上传!" ;
			 		i++;
		 		}else if(josnMsg.succode==-1){
		 			errorFileList[i]=file.name+"文件上传异常,请稍后再试!" ;
			 		i++;
		 		}
		 		
		 	},
		 	'onQueueComplete':function(stats) {//当队列中的所有文件全部完成上传时触发
		 		var tips="";
		 		var index=1;
		 		if(totalfiles!=null&&totalfiles!=undefined&&totalfiles>0){
		 			tips+=index+".上传总文件数:"+ totalfiles+";<br>";
		 			index++;
		 			if(uploadsSuccessful>0){
		 				tips+=index+".上传成功的文件数:"+uploadsSuccessful+";<br>";
		 				index++;
		 				isSuccess=true;
		 			}
		 			if(stats.uploadsErrored>0){
		 				tips+=index+".上传失败的文件数:"+stats.uploadsErrored+";<br>";
		 				index++;
		 			}
		 			
		 			if( errorFileList.length>0){
		 				tips+=index+".上传失败的文件信息:<br>";
		 				for(var eri=0,len= errorFileList.length;eri<len;eri++){
		 					tips+= errorFileList[eri]+"<br>";
		 				}
		 			}
		 			
		 			if(isSuccess){
		 				art.dialog ( {'id':'updialog'}).close();
			 			alert(tips);
				 		refreshTableList($("#q").val());
		 			}else{
	 				    errorFileList=new Array();
	 				    i=0;
	 				    totalfiles=0;
	 				    uploadsSuccessful=0;
	 				    isSuccess=false;
		 				alert(tips);
		 			}
		 			
		 			
		 		} else{
		 			alert("还没有选择上传文件,请选择!");
		 		}
		 	}
	 });
	 
 }
 
 
 
 
 
 
 </script> 
</body>
</html>        