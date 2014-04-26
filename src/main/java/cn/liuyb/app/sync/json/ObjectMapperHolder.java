package cn.liuyb.app.sync.json;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import cn.liuyb.app.sync.json.data.AppUploadReq;
import cn.liuyb.app.sync.json.data.Array;
import cn.liuyb.app.sync.json.data.FileInfo;
import cn.liuyb.app.sync.json.data.FolderInfo;
import cn.liuyb.app.sync.json.data.LoginInfo;

public class ObjectMapperHolder {
    private static ObjectMapperHolder instance = new ObjectMapperHolder();
    private ObjectMapper mapper;
    private ObjectMapperHolder(){
        mapper = createMapper();
    }

    public static ObjectMapperHolder getInstance() {
        return instance;
    }

    public ObjectMapper getMapper() {
        return mapper;
    }

    public ObjectMapper getNewMapper() {
        //对于Spring, 单例无法工作
        return createMapper();
    }

    private ObjectMapper createMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        SerializationConfig config = mapper.getSerializationConfig();
        config.setSerializationInclusion(Inclusion.NON_NULL);
        mapper.registerSubtypes(
        		LoginInfo.class,
        		AppUploadReq.class,
        		Array.class,
        		FolderInfo.class,
        		FileInfo.class
        		);

        return mapper;
    }
}
