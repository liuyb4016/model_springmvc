package cn.liuyb.app.sync.json.data;

import org.codehaus.jackson.annotate.JsonTypeName;

import cn.liuyb.app.sync.json.Data;
@JsonTypeName("file_info")
public class FileInfo implements Data {

	private static final long serialVersionUID = 4057206248011305614L;
	
	private String md5;
	private String fileName;
	private Long folderId;
	
	
	private Boolean md5IsExist;
	private Boolean fileNameIsExist;
	
	private String folderName;
	private String downloadUrl;
	
	
	
	public String getFolderName() {
		return folderName;
	}
	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}
	public String getDownloadUrl() {
		return downloadUrl;
	}
	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}
	public Boolean getMd5IsExist() {
		return md5IsExist;
	}
	public void setMd5IsExist(Boolean md5IsExist) {
		this.md5IsExist = md5IsExist;
	}
	public Boolean getFileNameIsExist() {
		return fileNameIsExist;
	}
	public void setFileNameIsExist(Boolean fileNameIsExist) {
		this.fileNameIsExist = fileNameIsExist;
	}
	public String getMd5() {
		return md5;
	}
	public void setMd5(String md5) {
		this.md5 = md5;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public Long getFolderId() {
		return folderId;
	}
	public void setFolderId(Long folderId) {
		this.folderId = folderId;
	}
	
}
