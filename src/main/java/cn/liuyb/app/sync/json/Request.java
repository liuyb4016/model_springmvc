package cn.liuyb.app.sync.json;



/**
 * {
 *  cmd:xxx
 *  cmdtype:xxx
 *  token:xxx
 *  data: {
 *      ????         
 *      }
 * }
 * @author luosen
 *
 */
public class Request {
    String cmd;
    String cmdtype;
    String token; // token 是可选的
    Data data; // data支持的类型：基本数据类型及对应包装类，Collection、Map的实现类，JavaBean
    String realIp; // 用户IP 是可选的
    String tag;//验证成功tag
    String appId;
    String appSecretKey;
    
    /**
	 * @return the tag
	 */
	public String getTag() {
		return tag;
	}

	/**
	 * @param tag the tag to set
	 */
	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getCmdtype() {
        return cmdtype;
    }

    public  void setCmdtype(String cmdtype) {
        this.cmdtype = cmdtype;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getToken() {
        return token;
    }

    public  void setToken(String token) {
        this.token = token;
    }

    public String getRealIp() {
		return realIp;
	}

	public void setRealIp(String realIp) {
		this.realIp = realIp;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppSecretKey() {
		return appSecretKey;
	}

	public void setAppSecretKey(String appSecretKey) {
		this.appSecretKey = appSecretKey;
	}
	@Override
    public String toString() {
        return "Request [cmd=" + cmd + ", cmdtype=" + cmdtype + ", token="
                + token + ", realIp=" + realIp + ", data=" + data +  ", tag=" + tag +"]";
    }
}
