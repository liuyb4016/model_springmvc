package cn.liuyb.app.sync.json.data;

import org.codehaus.jackson.annotate.JsonTypeName;

import cn.liuyb.app.sync.json.Data;
@JsonTypeName("folder_info")
public class FolderInfo implements Data {

	private static final long serialVersionUID = 7268667500764967112L;
	
	private Long pFolderId;
	private String pFolderName;
	private Long folderId;
	private String folderName;
	public Long getpFolderId() {
		return pFolderId;
	}
	public void setpFolderId(Long pFolderId) {
		this.pFolderId = pFolderId;
	}
	public String getpFolderName() {
		return pFolderName;
	}
	public void setpFolderName(String pFolderName) {
		this.pFolderName = pFolderName;
	}
	public Long getFolderId() {
		return folderId;
	}
	public void setFolderId(Long folderId) {
		this.folderId = folderId;
	}
	public String getFolderName() {
		return folderName;
	}
	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}
	
	
	
	
}
