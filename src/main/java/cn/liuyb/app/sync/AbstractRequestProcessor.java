package cn.liuyb.app.sync;

import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;

import cn.liuyb.app.common.utils.Slf4jLogUtils;
import cn.liuyb.app.sync.json.Request;
import cn.liuyb.app.sync.json.Response;
import cn.liuyb.app.sync.json.ResponseHelper;

public abstract class AbstractRequestProcessor {
    protected Logger logger = Slf4jLogUtils.getLogger(getClass());
    public Response handleException(Request request, Exception e) {
        logger.error(e.getMessage(), e);
        return ResponseHelper.createExceptionResponse(request, e);
    }
    
    public Response handleConstraintViolationException(Request request, ConstraintViolationException e) {
        logger.error(e.getMessage(), e);
        return ResponseHelper.createConstraintViolationErrorResponse(request, e);
    }
}
