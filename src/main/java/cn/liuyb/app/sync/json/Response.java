package cn.liuyb.app.sync.json;

import java.io.Serializable;

public class Response implements Serializable {
    
    private static final long serialVersionUID = 7015731184314121850L;

    private String cmd;
    private Integer rcd;
    private String rm;
    private String token;
    private Data data;
    private String nextreq;
    private Long skip;//代表服务器端此文件已经上传的大小。
    
    public Long getSkip() {
		return skip;
	}

	public void setSkip(Long skip) {
		this.skip = skip;
	}
	public String getNextreq() {
		return nextreq;
	}

	public void setNextreq(String nextreq) {
		this.nextreq = nextreq;
	}

	public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public Integer getRcd() {
        return rcd;
    }

    public void setRcd(Integer rcd) {
        this.rcd = rcd;
    }

    public String getRm() {
        return rm;
    }

    public void setRm(String rm) {
        this.rm = rm;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

	public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Response [cmd=" + cmd + ", rcd=" + rcd + ", rm=" + rm + ", token=" + token + ", nextreq=" + nextreq + ", data=" + data + "]";
    }

}
