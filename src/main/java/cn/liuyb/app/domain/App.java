package cn.liuyb.app.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import cn.liuyb.app.common.domain.BaseEntity;

@Entity
@NamedQueries({
	@NamedQuery(name="App.findByPackageName",
			query="SELECT a FROM App a WHERE a.packageName=:packageName")
})
public class App extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1867154720397445043L;
	private String packageName;
	private Integer packSize;
	private String title;
	private String fileName;
	private String iconName;
	private String fileMd5;
	private Integer versionCode;
	private String versionName;
	private Integer downloadCount;
	@Temporal(TemporalType.TIMESTAMP)
	private Date ctrateTime;
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateTime;
	
	public Integer getPackSize() {
		return packSize;
	}
	public void setPackSize(Integer packSize) {
		this.packSize = packSize;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getIconName() {
		return iconName;
	}
	public void setIconName(String iconName) {
		this.iconName = iconName;
	}
	public String getFileMd5() {
		return fileMd5;
	}
	public void setFileMd5(String fileMd5) {
		this.fileMd5 = fileMd5;
	}
	public Integer getVersionCode() {
		return versionCode;
	}
	public void setVersionCode(Integer versionCode) {
		this.versionCode = versionCode;
	}
	public String getVersionName() {
		return versionName;
	}
	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}
	public Integer getDownloadCount() {
		return downloadCount;
	}
	public void setDownloadCount(Integer downloadCount) {
		this.downloadCount = downloadCount;
	}
	public Date getCtrateTime() {
		return ctrateTime;
	}
	public void setCtrateTime(Date ctrateTime) {
		this.ctrateTime = ctrateTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
