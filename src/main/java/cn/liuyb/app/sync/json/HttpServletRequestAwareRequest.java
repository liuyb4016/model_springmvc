package cn.liuyb.app.sync.json;

import javax.servlet.http.HttpServletRequest;

public class HttpServletRequestAwareRequest extends Request {

	private static final String CLIENT_VERSION = "TVersion";
	private HttpServletRequest httpServletRequest;
	
	public HttpServletRequestAwareRequest(Request request) {
		this.setCmd(request.getCmd());
		this.setCmdtype(request.getCmdtype());
		this.setData(request.getData());
		this.setToken(request.getToken());
		this.setAppId(request.getAppId());
		this.setAppSecretKey(request.getAppSecretKey());
	}

	public void setHttpServletRequest(HttpServletRequest httpServletRequest) {
		this.httpServletRequest = httpServletRequest;
	}

	public HttpServletRequest getHttpServletRequest() {
		return httpServletRequest;
	}
	
	public String getCientVersion() {
		if (httpServletRequest != null) {
			return httpServletRequest.getHeader(CLIENT_VERSION);
		}
		return null;
	}
	
	public static String getCientVersion(HttpServletRequest httpServletRequest) {
		if (httpServletRequest != null) {
			return httpServletRequest.getHeader(CLIENT_VERSION);
		}
		return null;
	}
}
