package cn.liuyb.app.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import cn.liuyb.app.common.utils.Constants;
import cn.liuyb.app.common.utils.CookieUtils;
import cn.liuyb.app.common.utils.Slf4jLogUtils;
import cn.liuyb.app.domain.User;
import cn.liuyb.app.service.UserService;
import cn.liuyb.app.service.impl.CurrentUser;

public class AuthenticationInterceptor extends HandlerInterceptorAdapter {

    private static Logger logger = Slf4jLogUtils.getLogger(AuthenticationInterceptor.class);
    
    @Autowired
    private UserService userService;
    
    /**
     * 控制层拦截器 
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    	logger.debug("preHandle for handler - {} , URI is {}", handler.getClass().getSimpleName(), request.getRequestURI());
        if(handler instanceof ResourceHttpRequestHandler){
        	return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Long userId = CookieUtils.getUserId(request);
        if (userId != null) {
        	if (userService.findById(userId) == null) {
        		userId = null;
        	}
        }
        
        CurrentUser.setUserId(userId);
        if (handlerMethod.getBean() instanceof NoNeedLoginController) {
            return true;
        } else if (userId == null) {
            logger.debug("Can't find user from request's cookie, redirect to home.");
            response.sendRedirect(CookieUtils.getContextPath(request) + "portal/");
            return false;
        }
        if (handlerMethod.getBean() instanceof NeedLoginController) {
        	User user = userService.findById(userId);
            if(user == null) {
                logger.debug("Can't find user in database, redirect to home.");
                response.sendRedirect(CookieUtils.getContextPath(request) + "portal/");
                return false;
            }else{
            	Object obj = request.getSession().getAttribute(Constants.LOGINED);
            	if(obj==null){
            		//做退出登录操作
            		CookieUtils.cleanLoginCookie(request, response);
                    CurrentUser.setUserId(null);
                    response.sendRedirect(CookieUtils.getContextPath(request) + "portal/");
                    return false;
            	}
            }
            return true;
        }
        
    	return true;
    }
}
