package cn.liuyb.app.sync.handler;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import cn.liuyb.app.sync.json.Request;
import cn.liuyb.app.sync.json.Response;

public interface MultipartCmdHandler<T extends Request> extends CmdHandler {

    public abstract Response handle(T request, Map<String, MultipartFile> files);

}
