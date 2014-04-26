package cn.liuyb.app.common.utils;

import java.util.HashMap;
import java.util.Map;

public class FileUtil {

	// 定义允许上传的文件扩展名
	private static HashMap<String, String> extMap = new HashMap<String, String>();
	private static String TYPE_DEFAULT = "default";
	static {
		extMap.put("image", "gif,jpg,jpeg,png,bmp");
		extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
		extMap.put("file", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2,apk");
	}

	/**
	 * 获取文件对应的文件夹
	 * 
	 * @param fileSuffix
	 * @return
	 */
	public static String getFolderName(String fileSuffix) {
		for (Map.Entry<String, String> entry : extMap.entrySet()) {
			if(entry.getValue().contains(fileSuffix)){
				return entry.getKey();
			}
		}
		return TYPE_DEFAULT;
	}

	/**
	 * 获取文件的后缀名
	 * @param fileName
	 * @return
	 */
	public static String getFileSuffix(String fileName) {
		int indx = fileName.lastIndexOf('.');
		return fileName.substring(indx + 1).toLowerCase();
	}

	
	/**
	 * 获取文件的名称
	 * @param fileName
	 * @return
	 */
	public static String getFileName(String fileName) {
		int indx = fileName.lastIndexOf('.');
		return fileName.substring(0, indx);
	}
	
	/**
	 * 检查文件后缀名是否符合要求
	 * @param fileSuffix
	 * @return
	 */
	public static boolean checkFileSuffixIsLegal(String fileSuffix){
		for (Map.Entry<String, String> entry : extMap.entrySet()) {
			if(entry.getValue().contains(fileSuffix)){
				return true;
			}
		}
		return false;
	}
	
	
	
	
}
