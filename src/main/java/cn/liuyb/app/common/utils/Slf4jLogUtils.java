package cn.liuyb.app.common.utils;

import org.owasp.esapi.ESAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Slf4jLogUtils {
    @SuppressWarnings("unchecked")
    public static <T> T cleanLogMessage(T obj) {
        if (obj instanceof String) {
            String msg = (String)obj; 
            String clean = msg.replace('\n', '_').replace('\r', '_');
            if (ESAPI.securityConfiguration().getLogEncodingRequired()) {
                clean = ESAPI.encoder().encodeForHTML(msg);
                if (!msg.equals(clean)) {
                    clean += " (Encoded)";
                }
            }
            return (T)clean;
        } else {
            return obj;
        }
              
    }
    
    public static Object[] cleanLogMessage(Object[] array) {
        Object[] result = new Object[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = cleanLogMessage(array[i]);
        }
        return  result;     
    }
    
    public static Logger getLogger(Class<?> clazz) {
        return new LoggerWrapper(LoggerFactory.getLogger(clazz));
        
    }
    
    public static Logger getLogger(String name) {
        return new LoggerWrapper(LoggerFactory.getLogger(name));
        
    }
    
}
