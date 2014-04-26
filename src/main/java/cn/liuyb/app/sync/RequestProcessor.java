package cn.liuyb.app.sync;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import cn.liuyb.app.common.utils.Slf4jLogUtils;
import cn.liuyb.app.common.utils.TokenUtils;
import cn.liuyb.app.service.impl.CurrentUser;
import cn.liuyb.app.sync.handler.CmdHandler;
import cn.liuyb.app.sync.handler.CmdHandlerManager;
import cn.liuyb.app.sync.handler.HttpServletRequestAwareHandler;
import cn.liuyb.app.sync.handler.MultipartCmdHandler;
import cn.liuyb.app.sync.json.CmdConstants;
import cn.liuyb.app.sync.json.HttpServletRequestAwareRequest;
import cn.liuyb.app.sync.json.ObjectMapperHolder;
import cn.liuyb.app.sync.json.Request;
import cn.liuyb.app.sync.json.Response;
import cn.liuyb.app.sync.json.ResponseHelper;
import cn.liuyb.app.web.NoNeedLoginController;

@Controller
@RequestMapping(value = "/request")
public class RequestProcessor extends AbstractRequestProcessor implements
		NoNeedLoginController {

	@Autowired
	private CmdHandlerManager cmdHandlerManager;

	private static final Logger logger = Slf4jLogUtils.getLogger(RequestProcessor.class);

	@RequestMapping(headers = "Content-Type!=multipart/form-data;*")
	public @ResponseBody Response process(@RequestBody Request request,
			HttpServletRequest sevletRequest, HttpServletResponse response) {
		logger.debug("process(), {}", request);
		response.addHeader("handle-cmd", request.getCmd());
		Long userId = TokenUtils.getUserIdFromToken(request.getToken());
		CurrentUser.setUserId(userId);

		if ((userId == null) && CmdConstants.isCmdNeedToken(request.getCmd())) {
			return ResponseHelper.createInvalidTokenResponse(request);
		}

		CmdHandler handler = resolveHandler(request);
		if (handler == null) {
			return ResponseHelper.createBusinessErrorResponse(request, "无效的请求:"
					+ request.getCmd());
		}

		try {
			Response resultResponse = null;
			if (handler instanceof HttpServletRequestAwareHandler) {
				HttpServletRequestAwareRequest newRequest = new HttpServletRequestAwareRequest(
						request);
				newRequest.setHttpServletRequest(sevletRequest);
				resultResponse = handler.handle(newRequest);
			} else {
				resultResponse = handler.handle(request);
			}

			return resultResponse;

		} catch (ConstraintViolationException e) {
			return handleConstraintViolationException(request, e);
		} catch (Exception e) {
			e.printStackTrace();
			return handleException(request, e);
		}
	}

	// @RequestMapping(value = "/multipart", method={RequestMethod.GET,RequestMethod.POST})
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/multipart")
	public @ResponseBody
	Response process(DefaultMultipartHttpServletRequest httpReq,
			HttpServletResponse response) throws IOException {
		logger.debug("processMultipart(), {}", httpReq);
		ObjectMapper mapper = ObjectMapperHolder.getInstance().getMapper();

		// get json request
		MultipartFile file = httpReq.getFile("params");
		if (file == null) {
			// TODO 忽略非法请求
		}
		InputStream in = file.getInputStream();
		Request jsonReq = null;
		try {
			jsonReq = mapper.readValue(in, Request.class);
		} finally {
			IOUtils.closeQuietly(in);
		}
		response.addHeader("handle-cmd", jsonReq.getCmd());

		Long userId = TokenUtils.getUserIdFromToken(jsonReq.getToken());
		CurrentUser.setUserId(userId);
		if ((userId == null) && CmdConstants.isCmdNeedToken(jsonReq.getCmd())) {
			return ResponseHelper.createInvalidTokenResponse(jsonReq);
		}
		// get handler
		CmdHandler handler = resolveHandler(jsonReq);
		if (handler == null) {
			return ResponseHelper.createBusinessErrorResponse(jsonReq, "无效的请求:"
					+ jsonReq.getCmd());
		}
		MultipartCmdHandler<Request> multipartHandler = (MultipartCmdHandler<Request>) handler;

		Map<String, MultipartFile> multipartFiles = new HashMap<String, MultipartFile>(
				httpReq.getFileMap());
		multipartFiles.remove("params");
		jsonReq.setRealIp(httpReq.getHeader("X-Real-IP"));
		try {
			return multipartHandler.handle(jsonReq, multipartFiles);
		} catch (ConstraintViolationException e) {
			return handleConstraintViolationException(jsonReq, e);
		} catch (Exception e) {
			return handleException(jsonReq, e);
		}
	}

	private CmdHandler resolveHandler(Request request) {
		return cmdHandlerManager.getCmdHandler(request.getCmd());
	}

}
