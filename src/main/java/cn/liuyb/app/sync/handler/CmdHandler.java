package cn.liuyb.app.sync.handler;

import cn.liuyb.app.sync.json.Request;
import cn.liuyb.app.sync.json.Response;

public interface CmdHandler extends Handler{
    public Response handle(Request request);
}
