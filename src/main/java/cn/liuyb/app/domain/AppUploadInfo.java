package cn.liuyb.app.domain;

import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

public class AppUploadInfo {
	
	@NotNull
	private MultipartFile apk;

	public MultipartFile getApk() {
		return apk;
	}

	public void setApk(MultipartFile apk) {
		this.apk = apk;
	}
}
