package cn.liuyb.app.sync.handler;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.liuyb.app.common.utils.Constants;
import cn.liuyb.app.common.utils.Slf4jLogUtils;
import cn.liuyb.app.common.utils.TokenUtils;
import cn.liuyb.app.domain.User;
import cn.liuyb.app.service.UserService;
import cn.liuyb.app.sync.json.CmdConstants;
import cn.liuyb.app.sync.json.Request;
import cn.liuyb.app.sync.json.Response;
import cn.liuyb.app.sync.json.ResponseHelper;
import cn.liuyb.app.sync.json.data.LoginInfo;

@Component
public class UserLoginHandler extends AbstractHttpServletRequestAwareHandler implements CmdHandler  {

    private Logger logger = Slf4jLogUtils.getLogger(UserLoginHandler.class);
    
    @Autowired
    private UserService userService;
    
    public Response handle(Request request){
        LoginInfo loginInfo = (LoginInfo)request.getData();
        
        if(loginInfo==null){
        	return ResponseHelper.createInvalidLoginInfoResponse(request);
        }
        if(loginInfo.getPassword()==null ||"".equals(loginInfo.getPassword().trim())){
        	return ResponseHelper.createInvalidLoginInfoResponse(request);
        }
        if(loginInfo.getUsername()==null ||"".equals(loginInfo.getUsername().trim())){
        	return ResponseHelper.createInvalidLoginInfoResponse(request);
        }
        
        Response response = ResponseHelper.createSuccessResponse(request);
       
        String password =  Constants.getObfuscatePass(loginInfo.getPassword());
		
		User user = userService.loginUser(loginInfo.getUsername(), password);
		if(user != null) {
		    logger.info("user {} login successful from api", loginInfo.getUsername());
		    response.setToken(TokenUtils.generateToken(user.getId()));
		} else {
		    logger.info("user {} login failed from api", loginInfo.getUsername());
		    response = ResponseHelper.createInvalidLoginInfoResponse(request);
		}
        
        return response;
    }
    
    public String getCmd() {
        return CmdConstants.Cmds.USER_LOGIN;
    }
   
}
