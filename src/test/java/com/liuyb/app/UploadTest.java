package com.liuyb.app;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;

import cn.liuyb.app.common.utils.Slf4jLogUtils;


public class UploadTest {
	
	private static Logger logger = Slf4jLogUtils.getLogger(UploadTest.class);
	
	/**
	 * 查找用户的文件夹列表
	 * @throws Exception
	 */
	@Test
	public void queryFolder() throws Exception{
		String uploadPath = "http://localhost:8080/xhrs/service/request/";
		uploadPath = "http://218.30.99.180/service/request/";
		String params = "{\"cmd\":\"query folder\",\"token\":\"1:1393907409921:mpirJRIDNPnT8fqmKJ0IOg==\" }";
		String resultStr = sendPostRequest(uploadPath, params, "UTF-8");
		logger.info("queryFolder===="+resultStr);
	}
	
	
	/**
	 * 检查文件是否已经存在
	 * @throws Exception
	 */
	@Test
	public void checkFile() throws Exception{
		String uploadPath = "http://localhost:8080/xhrs/service/request/";
		uploadPath = "http://218.30.99.180/service/request/";
		String params = "{\"cmd\": \"check file\",\"token\": \"1:1393907409921:mpirJRIDNPnT8fqmKJ0IOg==\",\"data\": {\"dt\": \"file_info\",\"md5\":\"c21c3acf7bc39c8eba854b2893598c54\",\"fileName\": \"ce.apk\",\"folderId\": 1 }}";
		String resultStr = sendPostRequest(uploadPath, params, "UTF-8");
		logger.info("queryFolder===="+resultStr);
	}
	
	/**
	 * 用户上传文件：
	 * 1.假如通过md5接口验证文件已经存在，可以不用上传文件，否则必须要上传文件
	 * @throws Exception
	 */
	@Test
	public void uploadFile() throws Exception{
		String uploadPath = "http://localhost:8080/xhrs/service/request/multipart/";
		uploadPath = "http://218.30.99.180/service/request/multipart/";
		String params = "{\"cmd\":\"file upload\",\"data\":{\"dt\":\"file_info\",\"fileName\":\"yun3.apk\",\"folderId\":0,\"md5\":\"e0a17b36b28cfa12b9670e816895d31d\"},\"token\":\"1:1385540335905:c9rj2mJX6l1eq+J6JQshaw==\"}";
		String temPath = "d:\\temp\\";
		String fileName = java.util.UUID.randomUUID().toString()+".txt";
		string2File(params,temPath+fileName);
		Map<String, File> files = new HashMap<String, File>();
		String path = "D:\\yun.apk";
		files.put("appFile", new File(path));
		files.put("params", new File(temPath+fileName));
		String resultStr = post(uploadPath, files, "params");
		logger.info("uploadApp===="+resultStr);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 上传App
	 * @throws Exception 
	 */
	@Test
	public void uploadAppTest() throws Exception{
		String uploadPath = "http://218.30.99.180/service/request/multipart/";
		//String uploadPath = "http://localhost/service/request/multipart/";
		String params = "{\"cmd\":\"app upload\",\"cmdtype\":\"\",\"data\":{\"dt\":\"app_upload_req\",\"fileName\":\"telecom.mdesk.widgetprovider.apk\",\"fileSize\":123456,\"fileMD5\":\"1F3870BE274F6C49B3E31A0C672891232\"},\"token\":\"1:1385543773283:9uOaV277hvPqVbF5nwqihQ==\"}";
		String path = "D:\\telecom.mdesk.widgetprovider.apk";
		String temPath = "d:\\temp\\";
		String fileName = java.util.UUID.randomUUID().toString()+".txt";
		string2File(params,temPath+fileName);
		Map<String, File> files = new HashMap<String, File>();
		files.put("appFile", new File(path));
		files.put("params", new File(temPath+fileName));
		String resultStr = post(uploadPath, files, "params");
		logger.info("uploadApp===="+resultStr);
	}
	
	/**
	 * 登录接口请求
	 * @return 
	 * @throws Exception 
	 */
	@Test
	public void login() throws Exception{
		String uploadPath = "http://218.30.99.180/service/request/";
		//String uploadPath = "http://localhost/service/request/";
		String params = "{\"cmd\":\"user login\",\"cmdtype\":\"\",\"data\":{\"dt\":\"login_info\",\"username\":\"admin\",\"password\":\"1234567\"}}";
		String resultStr = sendPostRequest(uploadPath, params, "UTF-8");
		logger.info("uploadApp===="+resultStr);
	}
	
	/**
	 * 登录接口请求
	 * @return 
	 * @throws Exception 
	 */
	@Test
	public void postDate() {
		for(int i=0;i<2;i++){
			new Thread1().start();
		}
	}
	
	/**
	 * 登录接口请求
	 * @return 
	 * @throws Exception 
	 */
	@Test
	public void postDate1() {
		for(int i=0;i<1000;i++){
					try{
						long start =System.currentTimeMillis();
						String uploadPath = "http://fancy.189.cn/service/request/";
						//String uploadPath = "http://localhost/service/request/";
						String params = "{\"cmd\":\"get app plugin\",\"cmdtype\": \"\",\"data\":{\"dt\":\"list_param\",\"limit\":16,\"skip\":0}}";
						String resultStr = sendPostRequest(uploadPath, params, "UTF-8");
						logger.info("uploadApp====needtime="+(System.currentTimeMillis()-start)+"====="+resultStr);
					}catch(Exception e){
						logger.error("e=================="+e.getMessage());
					}
		}
	}
	
	class Thread1  extends Thread
	{
	   public void run(){
		   try{
				long start =System.currentTimeMillis();
				String uploadPath = "http://fancy.189.cn/service/request/";
				//String uploadPath = "http://localhost/service/request/";
				String params = "{\"cmd\":\"get app plugin\",\"cmdtype\": \"\",\"data\":{\"dt\":\"list_param\",\"limit\":16,\"skip\":0}}";
				String resultStr = sendPostRequest(uploadPath, params, "UTF-8");
				logger.info("uploadApp====needtime="+(System.currentTimeMillis()-start)+"====="+resultStr);
			}catch(Exception e){
				logger.error("e=================="+e.getMessage());
			}
	   }
	}
	
	
	/**
	 * 普通接口请求
	 * @param path
	 * @param params
	 * @param encoding
	 * @return
	 * @throws Exception
	 */
	public  static String sendPostRequest(String path, String params, String encoding) throws Exception{
		URL url = new URL(path);
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		conn.setRequestMethod("POST");
		conn.setConnectTimeout(60*1000);
		conn.setDoOutput(true);//能过post方式提交数据，必须要允许输出
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setRequestProperty("Connection", "keep-alive");
        OutputStream os = conn.getOutputStream();
		os.write(params.getBytes());
		os.flush();
        
		if(conn.getResponseCode()==200){
			InputStream in = conn.getInputStream();
			String result = convertStreamToString(in);
			return result;
		}
		return null;
	}
	
	/**
	 * App上传请求
	 * @param actionUrl
	 * @param files
	 * @param fixedFileName
	 * @return
	 * @throws Exception
	 */
	public static String post(String actionUrl,Map<String, File> files, String fixedFileName) throws Exception {

		String BOUNDARY = java.util.UUID.randomUUID().toString();
		String PREFIX = "--", LINEND = "\r\n";
		String MULTIPART_FROM_DATA = "multipart/form-data";
		String CHARSET = "UTF-8";

		URL uri = new URL(actionUrl);
		HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
		conn.setReadTimeout(60 * 1000); // 缓存的最长时间
		conn.setConnectTimeout(60*1000); 
		conn.setDoInput(true);// 允许输入
		conn.setDoOutput(true);// 允许输出
		conn.setUseCaches(false); // 不允许使用缓存
		conn.setRequestMethod("POST");
		conn.setRequestProperty("connection", "keep-alive");
		conn.setRequestProperty("Charsert", "UTF-8");
		conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA
				+ ";boundary=" + BOUNDARY);
		DataOutputStream outStream = new DataOutputStream(conn
				.getOutputStream());

		InputStream in = null;
		String blogId = null;
		// 发送文件数据
		if (files != null){
			for (Map.Entry<String, File> file : files.entrySet()) {
				StringBuilder sb1 = new StringBuilder();
				sb1.append(PREFIX);
				sb1.append(BOUNDARY);
				sb1.append(LINEND);
				sb1.append("Content-Disposition: form-data; name=\"" + file.getKey() + "\"; filename=\""
						+  file.getKey() + "\"" + LINEND);  //此处固定为albumUploadFile
				sb1.append("Content-Type: application/octet-stream; charset="
						+ CHARSET + LINEND);
				sb1.append(LINEND);
				outStream.write(sb1.toString().getBytes());

				InputStream is = new FileInputStream(file.getValue());
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = is.read(buffer)) != -1) {
					outStream.write(buffer, 0, len);
				}

				is.close();
				outStream.write(LINEND.getBytes());
			}

			// 请求结束标志
			byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
			outStream.write(end_data);
			outStream.flush();
			// 得到响应码
			int res = conn.getResponseCode();
			if (res == 200) {
				in = conn.getInputStream();
				String result = convertStreamToString(in);
				return result;

			}
			outStream.close(); 
			conn.disconnect();
		}
		return blogId;
	}
	
	/**
	 * 流转字符串
	 * @param in
	 * @return
	 * @throws IOException
	 */
	private static String convertStreamToString(InputStream in) throws IOException{
		ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
		byte[] buff = new byte[100]; // buff用于存放循环读取的临时数据
		int rc = 0;
		while ((rc = in.read(buff, 0, 100)) > 0) {
			swapStream.write(buff, 0, rc);
		}

		return swapStream.toString();
	}

	/**
	 * 将字符串转成文件
	 * @param res
	 * @param filePath
	 * @return
	 */
	public static boolean string2File(String res, String filePath) {
		boolean flag = true;
		BufferedReader bufferedReader = null;
		BufferedWriter bufferedWriter = null;
		try {
			File distFile = new File(filePath);
			if (!distFile.getParentFile().exists())
				distFile.getParentFile().mkdirs();
			bufferedReader = new BufferedReader(new StringReader(res));
			bufferedWriter = new BufferedWriter(new FileWriter(distFile));
			char buf[] = new char[1024]; // 字符缓冲区
			int len;
			while ((len = bufferedReader.read(buf)) != -1) {
				bufferedWriter.write(buf, 0, len);
			}
			bufferedWriter.flush();
			bufferedReader.close();
			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
			flag = false;
			return flag;
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return flag;
	}
}
