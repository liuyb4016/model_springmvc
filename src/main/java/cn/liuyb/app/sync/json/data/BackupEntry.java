package cn.liuyb.app.sync.json.data;

import org.codehaus.jackson.annotate.JsonTypeName;

import cn.liuyb.app.sync.json.Data;

@JsonTypeName("backup_entry")
public class BackupEntry implements Data {
	private static final long serialVersionUID = -2608441179049163407L;

	private String file;// 备份文件在备份包中的相对路径
    private String packageName;// apk包名
    private Integer versionCode; // apk vercode版本
    private String filemd5;//文件md5
    public BackupEntry(){}

    public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public Integer getVersionCode() {
		return versionCode;
	}
	public void setVersionCode(Integer versionCode) {
		this.versionCode = versionCode;
	}
	public String getFilemd5() {
		return filemd5;
	}
	public void setFilemd5(String filemd5) {
		this.filemd5 = filemd5;
	}
    
}
