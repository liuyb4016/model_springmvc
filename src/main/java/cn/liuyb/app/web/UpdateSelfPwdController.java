package cn.liuyb.app.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.liuyb.app.common.utils.Constants;
import cn.liuyb.app.common.utils.CookieUtils;
import cn.liuyb.app.common.utils.MethodDirections;
import cn.liuyb.app.common.utils.Slf4jLogUtils;
import cn.liuyb.app.domain.User;
import cn.liuyb.app.service.UserService;
import cn.liuyb.app.service.impl.CurrentUser;

@Controller
@RequestMapping("/updateselfpwd")
public class UpdateSelfPwdController implements NeedLoginController {
	
	private static final Logger logger = Slf4jLogUtils.getLogger(UpdateSelfPwdController.class);
	
	
	@Autowired
	private UserService userService;
	
	@MethodDirections(value = "修改：进入修改密码")
	@RequestMapping(method=RequestMethod.GET)
	public String list() {
		return "withy/updateselfpwd";
	}
	
	@MethodDirections(value = "修改：修改密码")
	@RequestMapping(value="/saveselfpwd/{oldpwd}/{newpwd}/{newpwd1}", method = RequestMethod.GET)
    public @ResponseBody Object[] searchByType(@PathVariable("oldpwd") String oldpwd, 
    		@PathVariable("newpwd") String newpwd, @PathVariable("newpwd1") String newpwd1,HttpServletRequest request, HttpServletResponse response) throws IllegalAccessException {

		String result = "0";
		Long userId = CurrentUser.getUserId();
		if(oldpwd==null || "".equals(oldpwd)){
			result = "2";
		}else if(newpwd1==null || "".equals(newpwd1)||newpwd==null || "".equals(newpwd)){
			result = "3";
		}else if(!newpwd1.equals(newpwd)){
			result = "4";
		}else{
			User user = userService.findById(userId);
			String password = Constants.getObfuscatePass(oldpwd);
			if(!password.equals(user.getPassword())){
				result = "5";
			}else{
				user.setPassword(Constants.getObfuscatePass(newpwd));
				userService.update(user);
				result = "6";
				CookieUtils.cleanLoginCookie(request, response);
			    CurrentUser.setUserId(null);
				logger.info("====user update self ====="+userId);
			}
		}
		return new Object[]{result };
	}
}