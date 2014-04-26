<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ include file="../includes.jsp"%>
<!DOCTYPE table PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="resources/styles/skins/chrome.css" rel="stylesheet" type="text/css" />
<meta name="decorator" content="admin" />
<title>修改密码</title>
</head>
<body class="right noborder">
	<div class="extrabottom">
					<table class="rightform">
						<tr>
							<td><font color='red'>*</font>原密码：<input name="oldpwd" id='oldpwd' type='password'/></td>
						</tr>
						<tr>
							<td><font color='red'>*</font>新密码：<input name="newpwd" id='newpwd' type='password'/></td>
						</tr>
						<tr>
							<td><font color='red'>*</font>新密码：<input name="newpwd1" id='newpwd1' type='password'/>(<font color='red'>再输入一次新密码</font>)</td>
						</tr>
						<tr>
							<td><input id="updatepwd" type="button" class="btn"  onclick='javascript:tosubmit();' value="修改密码"/> 
							</td>
						</tr>
		
					</table>
	</div>
	
<script type="text/javascript">
function setSelectNav(){
	selectNav("updateselfpwd");
}

function tosubmit(){
	var oldpwd = $("#oldpwd").val();
	var newpwd = $("#newpwd").val();
	var newpwd1 = $("#newpwd1").val();
	if(oldpwd==''){
		alert('原始密码不能为空，请重新输入');
		return false;
	}
	if(newpwd1==newpwd){
		if (isEmpty($.trim(newpwd))){
			var chekcpwd = checkPwd($.trim(newpwd));
			if(chekcpwd==-1){
				$("#newpwd").focus();
	    		$("#errormsg").text("新密码  不能为空");
	    		return false;
			}else if(chekcpwd==-2){
				$("#newpwd").focus();
	    		$("#errormsg").text("新密码  应设置为长度：6-20位");
	    		return false;
			}else if(chekcpwd==-3){
				$("#newpwd").focus();
	    		$("#errormsg").text("新密码  不能包含空格");
	    		return false;
			}
		}
		var url = "portal/updateselfpwd/saveselfpwd/"+oldpwd+"/"+newpwd+"/"+newpwd1;
		$.ajax({
	        type: "GET",
	        contentType: "application/json; charset=utf-8",
	        url: url, 
	        cache: false,
			dataType: "json",
	        success: function(data){
				if('6'==data){
					alertV('修改密码成功,请重新登录再使用！',function(){
						window.location.reload();
					});
				}else if('2'==data){
					alert('原始密码输入不能为空');
				}else if('3'==data){
					alert('输入的两次新密码不能为空');
				}else if('4'==data){
					alert('输入的两次新密码不一致，请重新输入');
				}else if('5'==data){
					alert('原始密码输入不正确，请重新输入');
				}else{
					alert('修改密码失败，请重试');
				}
	        },error: function() {
	        	alert("你没有权限操作此功能");
	        }
		});	
	}else{
		alert('输入的两次新密码不一致，请重新输入');
	}
	
	return false;
}

function alertV(content, func) {
	art.dialog({
		title:"提示",
		skin: 'chrome',
		content: content,
		yesFn: function(){
			this.hide();
				func();
			return true;
		},
		yesText: '确定'
	});
}
</script>
</body>
</html>        