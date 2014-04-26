package cn.liuyb.app.common.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.servlet.ServletContext;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import cn.liuyb.app.common.domain.PathConfig;
import cn.liuyb.app.domain.App;

public class SystemPathUtil {

	private static final Logger logger = Slf4jLogUtils.getLogger(SystemPathUtil.class);

	public static void main(String[] args) {
		System.out.println("/mnt/nfs/mdesk/apprepo/"+hashCodePNToPath("cn.ctcare.android")+"cn.ctcare.android");
		System.out.println("/mnt/nfs/mdesk/apprepo/"+hashCodePNToPath("com.TMEye")+"com.TMEye");
		System.out.println("/mnt/nfs/mdesk/apprepo/"+hashCodePNToPath("com.akazam.android.wlandialer")+"com.akazam.android.wlandialer");
		System.out.println("/mnt/nfs/mdesk/apprepo/"+hashCodePNToPath("com.chinatelecom.bestpayclient")+"com.chinatelecom.bestpayclient");
		System.out.println("/mnt/nfs/mdesk/apprepo/"+hashCodePNToPath("com.corp21cn.mail189")+"com.corp21cn.mail189");
		System.out.println("/mnt/nfs/mdesk/apprepo/"+hashCodePNToPath("com.lectek.android.ecp")+"com.lectek.android.ecp");
		System.out.println("/mnt/nfs/mdesk/apprepo/"+hashCodePNToPath("com.pdager")+"com.pdager");
		System.out.println("/mnt/nfs/mdesk/apprepo/"+hashCodePNToPath("com.cn21.ecloud")+"com.cn21.ecloud");
	}

	public static String getAppRepoPath() {
		return PathConfig.INSTANCE.getApprepoPath();
	}
	
	public static String getAppResPath(String packageName, Integer verCode) {
		checkUserInputPath(packageName);

		// modified by liuyunbin
		// 由于备份文件上传的文件夹下创建的文件夹数量超过了3.2W多，那么文件夹的创建就不成功了。
		// 现在使用的方式是分解包名成路径+包名、
		// e.g. apk： com.eshore.mdesk
		// path: ((com.eshore.mdesk).hashCode)%3000/com.eshore.mdesk/版本
		String headPath = hashCodePNToPath(packageName);

		return getAppRepoPath() + headPath + packageName + File.separator + verCode + File.separator;
		// end
	}

	/**
	 * 
	 * @param res
	 *            rquest resource type 'download' 'icon'
	 * @param id
	 *            appID
	 * @return
	 */
	public static String getAppResUrl(String res, Long id) {
		StringBuilder builder = new StringBuilder("/portal/app/");
		builder.append(res);
		builder.append("/");
		builder.append(id);
		builder.append("/");
		return builder.toString();
	}


	public static String getTempPath(ServletContext context) {
		return context.getRealPath("/temp/");
	}

	public static String getFileExtension(String url) {
		if (url == null)
			return null;
		int indx = url.lastIndexOf('.');
		if (indx < 0)
			return null;
		return url.substring(indx).toLowerCase();
	}

	private static File getNoRepeatApkFile(File dir, String packageName) {
		checkUserInputPath(packageName);
		// don't touch the pakcageNanem.apk it is reserved for app download
		// from the app store
		File file = new File(dir, packageName + ".apk");
		return file;
	}

	public static File getNoRepeatApkFile(String packageName, Integer verCode) {
		String appFolderPath = getAppResPath(packageName, verCode);
		File folder = new File(appFolderPath);
		boolean createDir = false;
		if (!folder.exists()) {
			createDir = folder.mkdirs();
		}

		if (!createDir) {
			logger.error("create the directory {} fail", appFolderPath);
		}

		return SystemPathUtil.getNoRepeatApkFile(folder, packageName);
	}

	public static String getApptPath(ServletContext context, String osName) {
		String filename = "";
		if (osName.equals(MAC_OS)) { // mac osx
			filename = "osx_aapt";
		} else if (osName.startsWith(WINDOW_OS)) { // windows
			filename = "win_aapt.exe";
		} else {
			filename = "aapt";
		}
		return context.getRealPath("/WEB-INF/aapt/" + filename);
	}

	public static boolean getApkInfoByAppt(App app, File file, ServletContext context) {
		Properties prop = System.getProperties();
		String osName = prop.getProperty("os.name", "");
		String aapt = getApptPath(context, osName);
		logger.info("aapt path = {}", aapt);

		InputStream in = null;
		Process proc = null;
		try {
			String cmd = aapt + " d badging " + file.getCanonicalPath();
			logger.info("aapt cmd = {}", cmd);
			proc = Runtime.getRuntime().exec(cmd);
			in = proc.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line = null;
			while ((line = br.readLine()) != null) {
				logger.debug("aapt dump {}", line);
				Matcher ma = PACKAGE_PATTERN.matcher(line);
				if (ma.find()) {
					app.setPackageName(ma.group(1));
					Integer verCode = null;
					try {
						verCode = Integer.valueOf(ma.group(2));
					} catch (Exception e) {
						logger.debug("vercode not define {}", line);
					}

					app.setVersionCode(verCode);
					app.setVersionName(ma.group(3));
					return true;
				}
			}

		} catch (IOException e) {
			logger.error("aapt error", e);
		} catch (Exception e) {
			logger.error("aapt error", e);
		} finally {
			IOUtils.closeQuietly(in);
			if (proc != null)
				proc.destroy();
		}

		return false;

	}
	
	public static boolean getApkInfo(App app, File file, ServletContext context) {

		if (getApkInfoByAppt(app, file, context)) {
			InputStream in = null;
			try {
				in = new FileInputStream(file);
				String md5 = MD5.getMD5(in);
				app.setFileMd5(md5);
				logger.debug("【获取APP成功,计算MD5值存入】=" + md5);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				IOUtils.closeQuietly(in);
			}

			app.setPackSize(new Integer((int) file.length()));
			return true;
		}

		return false;
	}
	
	public static boolean getUploadApkInfoByAppt(App app, File file, ServletContext context) {
		Properties prop = System.getProperties();
		String osName = prop.getProperty("os.name", "");
		String aapt = getApptPath(context, osName);
		logger.info("aapt path = {}", aapt);

		InputStream in = null;
		Process proc = null;
		try {
			String cmd = aapt + " d badging " + file.getCanonicalPath();
			logger.info("aapt cmd = {}", cmd);
			proc = Runtime.getRuntime().exec(cmd);
			in = proc.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(in, "utf-8"));
			String line = null;
			int foundCount = 0;
			boolean isSerTitle = false;
			while ((line = br.readLine()) != null) {
				logger.debug("aapt dump {}", line);
				Matcher ma = PACKAGE_PATTERN.matcher(line);
				if (ma.find()) {
					app.setPackageName(ma.group(1));
					Integer verCode = null;
					try {
						verCode = Integer.valueOf(ma.group(2));
					} catch (Exception e) {
						logger.debug("vercode not define {}", line);
					}

					app.setVersionCode(verCode);
					app.setVersionName(ma.group(3));
					++foundCount;
					if (foundCount == 3)
						return true;
				} else {
					ma = DEFALUT_APP_PATTERN.matcher(line);
					if (ma.find()) {
						if(!isSerTitle){
							app.setTitle(ma.group(1));
						}
						++foundCount;
						if (foundCount == 3)
							return true;
					}else{
						ma = ZH_CN_APP_PATTERN.matcher(line);
						if (ma.find()) {
							app.setTitle(ma.group(1));
							isSerTitle = true;
							++foundCount;
							if (foundCount == 3)
								return true;
						}
					}
				}
			}
			return true;
		} catch (IOException e) {
			logger.error("aapt error", e);
		} catch (Exception e) {
			logger.error("aapt error", e);
		} finally {
			IOUtils.closeQuietly(in);
			if (proc != null)
				proc.destroy();
		}

		return false;

	}
	
	
	@SuppressWarnings("rawtypes")
	public static void reProduceAppIcon(App app, ServletContext context){
		Properties prop = System.getProperties();
		String osName = prop.getProperty("os.name", "");
		String aapt = getApptPath(context, osName);
		String apkFilePath = getAppResPath(app.getPackageName(), app.getVersionCode())+app.getFileName();
		String iconFilePath = getAppResPath(app.getPackageName(), app.getVersionCode());
		logger.debug("reProduceAppIcon apkFilePath:{}, iconFilePath:{}",apkFilePath,iconFilePath);
		InputStream in = null;
		Process proc = null;
		String iconName = null;
		ZipFile zipFile = null;
	    byte b[] = new byte [1024];
	    int length; 
		try {
			String cmd = aapt + " d badging " + apkFilePath;
			proc = Runtime.getRuntime().exec(cmd);
			in = proc.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line = null;
			while ((line = br.readLine()) != null) {
				Matcher ma = DEFALUT_APP_PATTERN.matcher(line);
				if (ma.find()) {
					// ma.group(1) name
					// ma.group(2) icon
			          zipFile = new ZipFile( new File(apkFilePath));       
			          Enumeration enumeration = zipFile.entries();
			          ZipEntry zipEntry = null ;
			          while (enumeration.hasMoreElements()) {
			             zipEntry = (ZipEntry) enumeration.nextElement();  
			             iconName = "res/drawable-hdpi"+ma.group(2).substring(ma.group(2).lastIndexOf("/"));
			             if (!zipEntry.isDirectory() && iconName.equals(zipEntry.getName())){
			            	 app.setIconName(ma.group(2).substring(ma.group(2).lastIndexOf("/")+1,ma.group(2).length()));
			            	 iconFilePath = iconFilePath+ma.group(2).substring(ma.group(2).lastIndexOf("/")+1,ma.group(2).length());
			            	 OutputStream outputStream = new FileOutputStream(iconFilePath);
		                     InputStream inputStream = zipFile.getInputStream(zipEntry); 
		                     while ((length = inputStream.read(b)) > 0){
		                        outputStream.write(b, 0, length);
		                     }
		                     outputStream.flush();
		                     outputStream.close();
		                     return ;
			             }else if(!zipEntry.isDirectory() && ma.group(2).equals(zipEntry.getName())){
			            	 app.setIconName(ma.group(2).substring(ma.group(2).lastIndexOf("/")+1,ma.group(2).length()));
			            	 iconFilePath = iconFilePath+ma.group(2).substring(ma.group(2).lastIndexOf("/")+1,ma.group(2).length());
		                     OutputStream outputStream = new FileOutputStream(iconFilePath);
		                     InputStream inputStream = zipFile.getInputStream(zipEntry); 
		                     while ((length = inputStream.read(b)) > 0){
		                        outputStream.write(b, 0, length);
		                     }
		                     outputStream.flush();
		                     outputStream.close();
		                     return ;
			             }
			          }
				}
			}

		} catch (IOException e) {
			logger.error("reProduceAppIcon error", e);
		} catch (Exception e) {
			logger.error("reProduceAppIcon error", e);
		} finally {
			try {
				IOUtils.closeQuietly(in);
			} catch (Exception e) {
				logger.error("reProduceAppIcon error", e);
			}
			try {
				if (proc != null)
					proc.destroy();
			} catch (Exception e) {
				logger.error("reProduceAppIcon error", e);
			}
		}
	}
	
	public static boolean getUploadApkInfo(App app, File file, ServletContext context) {

		if (getUploadApkInfoByAppt(app, file, context)) {
			InputStream in = null;
			try {
				in = new FileInputStream(file);
				String md5 = MD5.getMD5(in);
				app.setFileMd5(md5);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				IOUtils.closeQuietly(in);
			}

			app.setPackSize(new Integer((int) file.length()));
			return true;
		}

		return false;
	}
	
	public static File getNoRepeatUploadApkFile(File dir, String name) {
		int i = 1;
		// don't touch the pakcageNanem.apk it is reserved for app download
		// from the app store
		File file = new File(dir, name + "(" + i + ")" + ".apk");
		while (file.exists()) {
			i++;
			String newFileName = name + "(" + i + ")" + ".apk";
			file = new File(dir, newFileName);
		}
		return file;
	}

	public static final String MAC_OS = "Mac OS X";
	public static final String WINDOW_OS = "Windows";

	public static final Pattern PACKAGE_PATTERN = Pattern
			.compile("^package: name='(.*?)' versionCode='(.*?)' versionName='(.*?)'$");
	public static final Pattern DEFALUT_APP_PATTERN = Pattern.compile("^application: label='(.*?)' icon='(.*?)'$");
	public static final Pattern ZH_CN_APP_PATTERN = Pattern.compile("^application-label-zh_CN:'(.*?)'$");
	public static final Pattern SDK_PATTERN = Pattern.compile("^sdkVersion:'(.*?)'$");


	// 防止用户输入的路径包含特殊字符，造成安全漏洞
	public static void checkUserInputPath(String path) {
		if (path != null && path.indexOf("..") >= 0) {
			logger.warn("an invalid package found {}", path);
			throw new RuntimeException("an invalid package found");
		}
	}

	// 防止用户输入的路径包含特殊字符，造成安全漏洞
	public static String replaceUserInputPath(String path) {
		if (path != null && path.indexOf("..") >= 0) {
			logger.warn("an invalid package found, {} try to change it.", path);
			path = path.replace("..", "");
		}
		return path;
	}

	/**
	 * 
	 * @Title: splitPackageNameToPath
	 * @Description: 通过apk包名分解成文件路径
	 * @param @param packageName
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public static String hashCodePNToPath(String packageName) {
		int a = packageName.hashCode() % 3000;
		if (a < 0) {
			a = -a;
		}
		//return a + File.separator;
		return a + "/";
	}

	
	public static String getFileSystemPath(String suffixFolder){
		return PathConfig.INSTANCE.getFileSyPath()+suffixFolder+File.separator;
	}
	
	
	
	
	public static String doTransmitResources(MultipartFile multipartFile, String iFileName,
			String filePath) {
		if (multipartFile.isEmpty()) {
			return null;
		}
		if (SystemPathUtil.getFileExtension(multipartFile.getOriginalFilename()) == null) {
			return null;
		}
		String filename = iFileName
				+ SystemPathUtil.getFileExtension(multipartFile.getOriginalFilename());
		File f_FilePath = new File(filePath);
		if (!f_FilePath.exists()) {
			f_FilePath.mkdirs();
		}
		File screenshotFile = new File(filePath + filename);
		try {
			multipartFile.transferTo(screenshotFile);
			return filename;
		} catch (IllegalStateException e) {
			logger.error("move {} file error", iFileName, e);
		} catch (IOException e) {
			logger.error("move {} file error", iFileName, e);
		}
		return null;
	}
	
	
	
	
	public static String getSysHttpPath() {
		return PathConfig.INSTANCE.getSysHttpPath();
	}
	
	
	
	public static String getFileDownUrl(Long userId,Long fileId) {
		return PathConfig.INSTANCE.getSysHttpPath()+"portal/mupload/dl/"+userId+"/"+fileId;
	}
	
	
	
	
	
	
	
}
