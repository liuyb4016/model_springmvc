package cn.liuyb.app.common.utils;

import org.codehaus.jackson.map.ObjectMapper;

public class JacksonUtils {
    private static ObjectMapper mapper = new ObjectMapper();
    public static ObjectMapper getObjectMapper() {
        //TODO:
        //mapper.registerSubtypes(classes)
        return mapper;
    }
}
