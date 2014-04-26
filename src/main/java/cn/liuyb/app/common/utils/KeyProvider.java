package cn.liuyb.app.common.utils;

public class KeyProvider {
    public static final KeyProvider INSTANCE = new KeyProvider();
    private KeyProvider() {}
    
    public String getTokenKey() {
        return "wi$Th　yTb";
    }
    
    public String getVerifyCodeKey() {
    	return "Wi$Th　yTb";
    }
    
    public String getRandomPassKey() {
    	return "wI$Th　yTb";
    }
}
