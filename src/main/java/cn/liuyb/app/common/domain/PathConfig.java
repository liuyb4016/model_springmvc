package cn.liuyb.app.common.domain;



public class PathConfig {

	private String apprepoPath;
	private String fileSyPath;
	private String sysHttpPath;
	public static PathConfig INSTANCE;
	
	public String getApprepoPath() {
		return apprepoPath;
	}

	public void setApprepoPath(String apprepoPath) {
		this.apprepoPath = apprepoPath;
	}

	public String getFileSyPath() {
		return fileSyPath;
	}

	public void setFileSyPath(String fileSyPath) {
		this.fileSyPath = fileSyPath;
	}

	public String getSysHttpPath() {
		return sysHttpPath;
	}

	public void setSysHttpPath(String sysHttpPath) {
		this.sysHttpPath = sysHttpPath;
	}
	
}
