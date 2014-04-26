package cn.liuyb.app.sync.json;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


public final class CmdConstants {

    public static class Cmds {
    	
    	public static final String USER_LOGIN = "user login";//用户登录
        public static final String APP_UPLOAD = "app upload";//应用上传
        
        public static final String QUERY_FOLDER = "query folder";//用户文件夹查询
        public static final String CHECK_FILE = "check file";//检查文件夹md5和文件名
        public static final String FILE_UPLOAD = "file upload";//用户上传文件
        
        
        
        
        
        
    }
    
  //不需要token的cmd
    private static final Set<String> cmdsIgnoreToken = getCmdsIgnoreToken();
    public static Set<String> getCmdsIgnoreToken() {
        HashSet<String> cmds = new HashSet<String>();
        cmds.add(Cmds.USER_LOGIN);
        return Collections.unmodifiableSet(cmds);
    }

    public static boolean isCmdNeedToken(String cmd) {
    	return !cmdsIgnoreToken.contains(cmd);
    }
}
