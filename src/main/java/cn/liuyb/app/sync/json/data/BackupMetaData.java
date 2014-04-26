package cn.liuyb.app.sync.json.data;

import java.util.List;

import org.codehaus.jackson.annotate.JsonTypeName;

import cn.liuyb.app.sync.json.Data;

@JsonTypeName("backup")
public class BackupMetaData implements Data{
    /**
	 *
	 */
	private static final long serialVersionUID = -6785859600625128930L;
	private String zipFileName;

    private List<BackupEntry> fileList;

    private String md5;
    private Long fileLength;

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	public Long getFileLength() {
		return fileLength;
	}

	public void setFileLength(Long fileLength) {
		this.fileLength = fileLength;
	}

	public String getZipFileName() {
        return zipFileName;
    }

    public void setZipFileName(String zipFileName) {
        this.zipFileName = zipFileName;
    }

    public List<BackupEntry> getFileList() {
        return fileList;
    }

    public void setFileList(List<BackupEntry> fileList) {
        this.fileList = fileList;
//        refreshState();
    }
/*
	@JsonIgnore
	public void refreshState() {
		if(fileList != null) {
			for(BackupEntry entry : fileList) {
				String type = entry.getType();
				if(SmsInfo.MIME_TYPE.equals(type)) {
					smsBackup = true;
				} else if (MmsInfo.MMS_MIME_TYPE.equals(type)) {
					mmsBackup = true;
				} else if (CallLogInfo.MIME_TYPE.equals(type)) {
					callLogBackup = true;
				} else if (ContactInfo.MIME_TYPE.equals(type)) {
					contactBackup = true;
				} else
					fileBackup = true;
			}
		}
	}*/
}
