package cn.liuyb.app.sync.json;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

public class ResponseHelper {
    public static Response createSuccessResponse(Request request) {
        return createResponse(request, ReturnCode.SUCCESS, null);
    }

    public static Response createResponse(Request request) {
        return createResponse(request, ReturnCode.NEVER_USED_CODE, null);
    }

    public static Response createResponse(Request request, int rcd, String rm) {
        Response response = new Response();
        response.setCmd(request.getCmd());
        if (rcd != ReturnCode.NEVER_USED_CODE) response.setRcd(rcd);
        if (rm != null) response.setRm(rm);
        return response;
    }

    public static Response createBusinessErrorResponse(Request request, String rm) {
        return createResponse(request, ReturnCode.BUSINESS_ERROR, rm);
    }

    public static Response createExceptionResponse(Request request, Exception e) {
        return createResponse(request, ReturnCode.EXCEPTION, e.getMessage());
    }

    public static Response createConstraintViolationErrorResponse(Request request, ConstraintViolationException e) {
        StringBuilder sb = new StringBuilder();
        for (ConstraintViolation<?> cv : e.getConstraintViolations()) {
            if (sb.length() > 0) sb.append(", ");
            sb.append(cv.getMessage());
        }
        return createResponse(request, ReturnCode.CONSTRAINT_VIOLATION_ERROR, sb.toString());
    }

    public static Response createInvalidLoginInfoResponse(Request request) {
        return createResponse(request, ReturnCode.INVALID_LOGIN_INFO, "无效的用户名或者密码！");
    }
    
    public static Response createNotTelecomCodeResponse(Request request) {
        return createResponse(request, ReturnCode.NOT_TELECOM_CODE, "登陆失败，因为您不是电信用户！");
    }
    
    public static Response createInvalidServerCmdResponse(Request request) {
        return createResponse(request, ReturnCode.INVALID_SERVER_CMD, "svrcmdid无效,或者结果返回超时。");
    }

    public static Response createInvalidTokenResponse(Request request) {
        return createResponse(request, ReturnCode.INVALID_TOKEN, "请重新登录！");
    }

    public static Response createInvalidCmdDataResponse(Request request) {
    	return createResponse(request, ReturnCode.INVALID_CMD_DATA, "请求数据不合法！");
    }
    
    public static Response createErrorImsiResponse(Request request) {
        return createResponse(request, ReturnCode.ERROR_IMSI_CODE, "imsi为空或者imsi错误！");
    }
    
    public static Response createUdbErrorResponse(Request request) {
        return createResponse(request, ReturnCode.UDB_TOKEN_INVALID, "异网手机登录失败");
    }
    
    public static Response createLongPollingResponse() {
        Response response = new Response();
        response.setCmd("server cmd request");
        response.setRcd(ReturnCode.SUCCESS);
        return response;
    }
}
