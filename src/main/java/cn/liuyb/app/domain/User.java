package cn.liuyb.app.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Transient;

import cn.liuyb.app.common.domain.BaseEntity;
/**
 * 用户信息
 * @author Administrator
 *
 */
@Entity
@NamedQueries({
		@NamedQuery(name = "User.findByUsername", 
				query = "SELECT u From User u where u.name like :name or u.username like :username order by u.updateDate desc "),
		@NamedQuery(name = "User.findByUsernameV", 
				query = "SELECT u From User u where u.username =:username "),
		@NamedQuery(name = "User.findByUsernameNotSelf", 
				query = "SELECT u From User u where u.username =:username and u.id<>:id"),
		@NamedQuery(name = "User.countByUsername", 
				query = "SELECT COUNT(u) From User u where u.name like :name or u.username like :username "),
		@NamedQuery(name = "User.loginUser", 
				query = "SELECT u From User u where u.username=:username and u.password=:password ")				
})
public class User extends BaseEntity {

	private static final long serialVersionUID = -1591685848660375009L;
	private String name;
	private String username;
	private String password;
	private String birthday;
	private String email;
    private String phone;
    //1 默认为正常   0被锁
    private int status = 1;
    private Date regDate;
    private Date loginDate;
    private Date updateDate;
    @Transient
    private String vcode;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
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
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getRegDate() {
		return regDate;
	}
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	public Date getLoginDate() {
		return loginDate;
	}
	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public String getVcode() {
		return vcode;
	}
	public void setVcode(String vcode) {
		this.vcode = vcode;
	}
    
}
