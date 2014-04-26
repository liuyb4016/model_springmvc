package cn.liuyb.app.sync.json.data;

import org.codehaus.jackson.annotate.JsonTypeName;

import cn.liuyb.app.sync.json.Data;
@JsonTypeName("app_upload_req")
public class AppUploadReq implements Data {

	private static final long serialVersionUID = 6178987978374896666L;

	private String packageName;
	private String fileName;
	private long fileSize;
	private String fileMD5;
	
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public long getFileSize() {
		return fileSize;
	}
	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}
	public String getFileMD5() {
		return fileMD5;
	}
	public void setFileMD5(String fileMD5) {
		this.fileMD5 = fileMD5;
	}
}
