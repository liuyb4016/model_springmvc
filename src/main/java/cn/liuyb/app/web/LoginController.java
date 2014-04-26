package cn.liuyb.app.web;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.liuyb.app.common.cache.MemCached;
import cn.liuyb.app.common.utils.BeanUtils;
import cn.liuyb.app.common.utils.Constants;
import cn.liuyb.app.common.utils.CookieUtils;
import cn.liuyb.app.common.utils.MethodDirections;
import cn.liuyb.app.common.utils.MyFileUtils;
import cn.liuyb.app.common.utils.Slf4jLogUtils;
import cn.liuyb.app.common.utils.SystemPathUtil;
import cn.liuyb.app.dao.AppDao;
import cn.liuyb.app.domain.App;
import cn.liuyb.app.domain.User;
import cn.liuyb.app.service.AppService;
import cn.liuyb.app.service.UserService;
import cn.liuyb.app.service.impl.CurrentUser;

@Controller
public class LoginController implements NoNeedLoginController {

    private static Logger logger = Slf4jLogUtils.getLogger(LoginController.class);
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private AppService appService;
    
    @MethodDirections(value = "查询：平台入口")
    @RequestMapping(method = RequestMethod.GET)
    public String setupForm(User user,ModelMap model) {
    	Long userId = CurrentUser.getUserId();
    	if (userId == null){
    		return "withy/login";
    	}else{
    		User userV = userService.findById(userId);
    		if (userV != null){
    			model.addAttribute("showMessage", "应用管理平台");
 	            return "withy/welcome";
    		}else{
    			return "withy/login";
    		}
    	}
    }
    
    @MethodDirections(value = "查询：登录平台")
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String processSubmitGet(@Valid User user, BindingResult errors, HttpServletRequest request, HttpServletResponse response,Model model) {
    	 return "redirect:/portal";       
    }
    
    
    @MethodDirections(value = "查询：登录平台")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String processSubmit(@Valid User user, BindingResult errors, HttpServletRequest request, HttpServletResponse response,Model model) {

        if (errors.hasErrors()) {
            return "withy/login";
        } else{
        	String username = user.getUsername();
        	String password =  Constants.getObfuscatePass(user.getPassword());
			User userV = userService.loginUser(username,password);
			if(userV != null){
				if(1!=userV.getStatus()){
					errors.reject("100", "该用户名已被锁定");
		        	return "withy/login";
				}
				logger.debug("User {} logged in successfully.", user.getUsername());
	            CookieUtils.setLoginCookie(userV.getId(), request, response);
	            request.getSession().setAttribute(Constants.LOGINED, userV.getId());
	            model.addAttribute("showMessage", "应用管理平台");
	           return "withy/welcome";
	        } else {
	            errors.reject("100", "用户名或密码错误");
	        	return "withy/login";
	        }
        }
        
    }
    
    @MethodDirections(value = "查询：检测是否登录")
    @RequestMapping(value = "/checklogin", method = RequestMethod.GET)
    public @ResponseBody boolean checkLoginStatus() {
        
    	Long userId = CurrentUser.getUserId();
    	boolean stat = false;
    	if (userId != null){
    		User user = userService.findById(userId);
    		if (user != null){
    			stat = true;
    		}
    	}
    	return stat;
    }
    
    @MethodDirections(value = "查询：退出平台")
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request, HttpServletResponse response) {
	    CookieUtils.cleanLoginCookie(request, response);
        CurrentUser.setUserId(null);
        return "redirect:/portal";       
    }
    
    @MethodDirections(value = "查询：无权限页面")
    @RequestMapping(value = "/noPermission", method = RequestMethod.GET)
    public String noPermission(HttpServletRequest request, HttpServletResponse response,Model model){
        return "withy/noPermission";   
    }
    
  /*  @MethodDirections(value = "修改：更新管理员密码")
	@RequestMapping(value="/updatepw/{id}/{password}")
	public @ResponseBody int addOrUpdateUserAdminPassWord(@PathVariable("password")String password,
			@PathVariable("id")Long id) throws IllegalAccessException {
		
		User user = userService.findById(id);
		user.setPassword(Constants.getObfuscatePass(password));
		user.setUpdateDate(new Date());
		userService.update(user);
		return 1;
	}*/
    
    @RequestMapping(value="/icon/{id}", method = RequestMethod.GET)
    public void getAppIcon(@PathVariable("id") Long id, HttpServletRequest request, 
			HttpServletResponse response, OutputStream out) throws IOException{

		App app = appService.findFmById(id);
		if(app==null){
			return ;
		}
		String filePath = SystemPathUtil.getAppResPath(app.getPackageName(), app.getVersionCode())+app.getIconName();
		logger.debug(filePath);
		MimetypesFileTypeMap mime = new MimetypesFileTypeMap();
		String mimeType = mime.getContentType(app.getIconName());
		response.setContentType(mimeType);  
		
		BeanUtils.copyInputFileToOutput(filePath, out);
		//MyFileUtils.breakPointDownload(new File(filePath), request, response);
	}
    
    @RequestMapping(value="/download/{id}", method = RequestMethod.GET)
	public void downloadApk(@PathVariable("id") Long id, HttpServletRequest request, 
			HttpServletResponse response, OutputStream out) throws IOException{
		
		App app = appService.findFmById(id);
		if(app==null){
			return ;
		}
		String filePath = SystemPathUtil.getAppResPath(app.getPackageName(), app.getVersionCode())+app.getFileName();
		logger.debug("app down message appId={}, appName={},version={},userId={} downDate={}", new Object[]{app.getId(),app.getTitle(),app.getVersionName(),CurrentUser.getUserId(),new Date()});
		
		//BeanUtils.copyInputFileToOutput(filePath, out);
		MyFileUtils.breakPointDownload(new File(filePath), request, response,null);
	}
    
    @MethodDirections(value = "修改：根据应用packageName清除缓存")
	@RequestMapping(value = "/updateMById", method = RequestMethod.GET)
    public @ResponseBody Object[] updateMById(Long appId) {
    	if(appId==null){
    		return null;
    	}
		App app = appService.findById(appId);
		if(app!=null){
			//桌面主题 列表      
			MemCached.INSTANCE.set(AppDao.APP+appId,app);
			//下载次数
			//MemCached.INSTANCE.set(AppDao.APP_COUNT+app.getPackageName(),app.getDownloadCount());
			return new Object[]{true,app};
		}else{
			return new Object[]{false,appId};
		}
    }
    @MethodDirections(value = "修改：更新所有的应用缓存")
	@RequestMapping(value = "/updateAppMemcached", method = RequestMethod.GET)
    public @ResponseBody Object[] updateAppMemcached() {
		List<App> listApp = appService.findAll();
		int appcount = 0;
		for(App app:listApp){
			MemCached.INSTANCE.set(AppDao.APP+app.getId(),app);
    		appcount++;
		}
		System.gc();
		logger.debug("======updateAppMemcached======app.size="+appcount);
		return new Object[]{true};
    }
}
