package cn.liuyb.app.sync.json.data;

import org.codehaus.jackson.annotate.JsonTypeName;

import cn.liuyb.app.sync.json.Data;

@JsonTypeName("login_info")
public class LoginInfo implements Data {

	private static final long serialVersionUID = -6405203116581339043L;
	private String username;
    private String password;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}