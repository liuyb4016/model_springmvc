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

$(function(){
	$("#username").attr("value", getCookie("username"));
	$("#username").focus();

	$("#login").click( function(){
		var name_input = $("#username");
		var pass_input = $("#password");
		var verify_input = $("#vcode");
		var username = name_input.attr("value");

		$(".errormsg").remove();
		if (username.length == 0){
			name_input.after("<span class=\"errormsg\"> 请输入您的用户名？</span>");
			name_input.focus();
			return false;
		}else if (username.length < 5 || username.length > 20){
			name_input.after("<span class=\"errormsg\"> 用户名长度必须在5和20之间？</span>");
			name_input.focus();
			return false;
		}else if($("#remem_name").attr("checked")){
			setCookie("username", username);
		}
		
		if (pass_input.attr("value").length == 0){
			pass_input.after("<span class=\"errormsg\"> 请输入您的密码</span>");
			pass_input.focus();
			return false;
		}
		
		if (verify_input.attr("value").length == 0){
			$("#verify").after("<span class=\"errormsg\"> 请输入验证码？</span>");
			verify_input.focus();
			return false;
		}else {
			if (checkVerify(verify_input.attr("value")) != true) {
	    		$("#verify").after("<span class=\"errormsg\">验证码输入错误，看不清？点击图片换一个。</span>");
	    		verify_input.focus();
	    		//刷新验证码
	    		refreshVerify("verifyimg");
	    		return false;
			}
		}
		
	});
	
	$("#verifyimg").click(function(){
		refreshVerify("verifyimg");
	})

})

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
