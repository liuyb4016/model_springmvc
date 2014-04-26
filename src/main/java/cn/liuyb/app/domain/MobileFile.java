package cn.liuyb.app.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.liuyb.app.common.domain.BaseEntity;

@Entity
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@NamedQueries({
	//用户文件夹下的文件 倒序
    @NamedQuery(name = "MobileFile.findMobileFileByUserIdAndFolderIdDESC",
            query = "SELECT m FROM MobileFile m WHERE m.userId = :userId AND m.folderId = :folderId ORDER BY m.uploadTime desc "),
          //用户文件夹下的文件数据
	@NamedQuery(name = "MobileFile.countMobileFileByUserIdAndFolderId",
            query = "SELECT COUNT(m) FROM MobileFile m WHERE m.userId = :userId AND m.folderId = :folderId"),
	        //用户文件 按名字查询    同一文件夹下不能重复名称 DD
	@NamedQuery(name = "MobileFile.findMobileFileByUserIdAndVirtualFolderFileName",
	        query = "SELECT m FROM MobileFile m WHERE m.userId = :userId AND m.folderId = :folderId AND m.nickName like (:nickName) ORDER BY m.uploadTime desc"),
          //用户文件  查询
	@NamedQuery(name = "MobileFile.findMobileFileByUserIdAndQueryString",
	        query = "SELECT m FROM MobileFile m WHERE m.userId = :userId AND m.nickName LIKE :nickName ORDER BY m.uploadTime desc "),
	      //用户文件 查询  数量
	@NamedQuery(name = "MobileFile.countMobileFileByUserIdAndQueryString",
	        query = "SELECT COUNT(m) FROM MobileFile m WHERE m.userId = :userId AND m.nickName LIKE :nickName "),
	        //用户总文件大小
	@NamedQuery(name = "MobileFile.getAllFileSizeByUserId", 
			query = "SELECT SUM(m.fileSize) FROM MobileFile m WHERE m.userId = :userId "),
    @NamedQuery(name = "MobileFile.countByUserIdAndVirtualFolderFileName",
    		query = "SELECT count(m) FROM MobileFile m WHERE m.userId = :userId AND m.folderId = :folderId AND m.nickName like (:nickName)"),
	@NamedQuery(name = "MobileFile.countUVirtualName",
    		query = "SELECT count(m) FROM MobileFile m WHERE m.userId = :userId AND m.folderId = :folderId AND m.nickName =:nickName"),
	@NamedQuery(name = "MobileFile.getMobileFileByMd5",
    		query = "SELECT m FROM MobileFile m WHERE m.md5=:md5"),
	@NamedQuery(name = "MobileFile.delByFolderIds",
    		query = "DELETE  MobileFile m WHERE m.folderId IN (:folderIds)"),
	@NamedQuery(name = "MobileFile.delByIds",
    		query = "DELETE  MobileFile m WHERE m.id IN (:ids)"),
	@NamedQuery(name = "MobileFile.countSizeByFolderId",
    		query = "SELECT sum(m.fileSize)  FROM MobileFile m WHERE m.folderId IN (:folderIds)"),
	@NamedQuery(name = "MobileFile.countUVirtualNameSuff",
    		query = "SELECT count(m) FROM MobileFile m WHERE m.userId = :userId AND m.folderId = :folderId AND m.nickName =:nickName and m.suffix=:suffix"),
    		@NamedQuery(name = "MobileFile.countByUFMd5",
    		query = "SELECT count(m) FROM MobileFile m WHERE m.userId = :userId AND m.folderId = :folderId AND m.md5 =:md5"),
	
})
@JsonIgnoreProperties(value = "folder")
public class MobileFile extends BaseEntity {

	private static final long serialVersionUID = 3208306796767234911L;

	public static final long TOTAL_MAX_SIZE = 1935*1024*1024L;
    
    public static final long SINGLE_MAX_SIZE = 1024*1024*1024L;
    
    /**
     * 文件  md5值
     */
    private String md5;
    /**
     * 下载名称  显示名称
     */
    private String nickName;
    
    /**
     * 后缀名
     */
    private String suffix;
    /**
     * 文件类型
     */
    private String mimeType;
    /**
     * 文件大小
     */
    private Long fileSize;
    /**
     * 上传时间
     */
    private Date uploadTime;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 上传时间
     */
    private Date updateTime;
    /**
     * 所属文件夹id
     */
    private Long folderId;
    /**
     * 所属用户
     */
    private Long userId;
    
	public String getMd5() {
		return md5;
	}
	public void setMd5(String md5) {
		this.md5 = md5;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getMimeType() {
		return mimeType;
	}
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
	public Long getFileSize() {
		return fileSize;
	}
	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}
	public Date getUploadTime() {
		return uploadTime;
	}
	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
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
	public Long getFolderId() {
		return folderId;
	}
	public void setFolderId(Long folderId) {
		this.folderId = folderId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getSuffix() {
		return suffix;
	}
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	
	
	
}
