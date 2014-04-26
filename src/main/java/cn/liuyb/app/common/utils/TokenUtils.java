package cn.liuyb.app.common.utils;

import java.io.UnsupportedEncodingException;

public class TokenUtils {
    private static final String SEP = ":";
    //private static final Long LOGIN_TIME_OUT = 10*60*1000L;
    private static String tokenKey = KeyProvider.INSTANCE.getTokenKey();
    public static String generateToken(Long userId) {
        String info = userId + SEP + System.currentTimeMillis();
        String token = info + SEP + MD5.getMD5(getUTF8(info + tokenKey));
        return token;
    }
    
    public static Long getUserIdFromToken(String token) {
        if (token == null) return null;
        int lastSep = token.lastIndexOf(SEP);
        if (lastSep == -1) {
        	return null;
        }
        String md5 = token.substring(lastSep + 1);
        String info = token.substring(0, lastSep);
       /* String[] times = info.split(":");
        Long time1 = Long.valueOf(times[1]);
        if(System.currentTimeMillis()-time1>LOGIN_TIME_OUT){
        	return null;
        }*/
        if (md5.equals(MD5.getMD5(getUTF8(info + tokenKey)))) {
            String userId = token.substring(0, token.indexOf(SEP));
            return Long.parseLong(userId);
        }
        return null;
    }
    
    private static byte[] getUTF8(String data) {
        try {
            return data.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }
}
