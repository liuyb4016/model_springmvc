package cn.liuyb.app.domain;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.liuyb.app.common.domain.BaseEntity;

@Entity
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@NamedQueries({ 
				//获取用户的文件夹下的文件夹
		@NamedQuery(name = "VirtualFolder.findByUserIdAndFatherId",
				query = "SELECT v From VirtualFolder v WHERE v.userId = :userId AND v.fatherId = :fatherId ORDER BY v.id ASC"),
				//获取用户的文件夹下的文件夹个数
		@NamedQuery(name = "VirtualFolder.countByUserIdAndFatherId",
				query = "SELECT COUNT(v) From VirtualFolder v WHERE v.userId = :userId AND v.fatherId = :fatherId"),
				//查询  用户文件夹下文件夹名称
		@NamedQuery(name = "VirtualFolder.countByFatherIdAndFolderName",
				query = "SELECT COUNT(v) From VirtualFolder v WHERE v.userId = :userId AND v.fatherId = :fatherId AND v.folderName like (:folderName)"),
		@NamedQuery(name = "VirtualFolder.countByUFAndFolderName",
				query = "SELECT COUNT(v) From VirtualFolder v WHERE v.userId = :userId AND v.fatherId = :fatherId AND v.folderName=:folderName)"),
		@NamedQuery(name = "VirtualFolder.findByFatherIdAndFolderName",
				query = "SELECT v From VirtualFolder v WHERE v.userId = :userId AND v.fatherId = :fatherId AND v.folderName  like (:folderName) ORDER BY v.id ASC"),
		@NamedQuery(name = "VirtualFolder.findByFatherId",
				query = "SELECT v From VirtualFolder v WHERE  v.fatherId = :fatherId"),
		@NamedQuery(name = "VirtualFolder.delByIds",
				query = "DELETE   VirtualFolder v WHERE  v.id IN (:ids)"),
})
@JsonIgnoreProperties("files")
public class VirtualFolder extends BaseEntity{

	private static final long serialVersionUID = -1982165017494854317L;

	public static final long ROOT_FOLDER_ID = 0L;
	
	private String folderName;
	private Date createTime;
	private Date updateTime;
	private Date lastUploadTime;
	private Long fatherId=0L;

	
	@Transient
	private Integer folderSize; 
	private Long userId;

	public String getFolderName() {
		return folderName;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getLastUploadTime() {
		return lastUploadTime;
	}

	public void setLastUploadTime(Date lastUploadTime) {
		this.lastUploadTime = lastUploadTime;
	}

	public Long getFatherId() {
		return fatherId;
	}

	public void setFatherId(Long fatherId) {
		this.fatherId = fatherId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getFolderSize() {
		return folderSize;
	}

	public void setFolderSize(Integer folderSize) {
		this.folderSize = folderSize;
	}
	
}	

