package cn.liuyb.app.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;

public class RequestSenderUtil {
	private static final Logger logger = Slf4jLogUtils
			.getLogger(RequestSenderUtil.class);

	public static String sendRequest(String urlPath)
			throws Exception {
		URL url = null;
		try {
			url = new URL(urlPath);
		} catch (MalformedURLException e) {
			logger.error("RequestSenderUtil request error", e);
			return null;
		}
		logger.debug("RequestSenderUtil request url = {}",urlPath);
		try {
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			conn.setRequestMethod("GET");
			conn.setDoOutput(true);

			int responseCode = conn.getResponseCode();
			if (responseCode == 200) {
				String responseStr = null;
				InputStream responseStream = null;
				StringWriter buffWriter = null;
				Reader responseReader = null;
				try {
					responseStream = conn.getInputStream();
					responseReader = new InputStreamReader(responseStream,"UTF-8");
					buffWriter = new StringWriter();
					char[] buf = new char[100];
					int length = 0;
					while ((length = responseReader.read(buf)) > 0)
						buffWriter.write(buf, 0, length);
				} finally {
					IOUtils.closeQuietly(responseStream);
					IOUtils.closeQuietly(responseReader);
				}
				responseStr = buffWriter.toString();
				logger.debug("RequestSenderUtil response = {}", responseStr);
				return responseStr;
			}

			logger.error("RequestSenderUtil response {} error code = {}", urlPath,Integer.valueOf(responseCode));
			return null;
		} catch (ConnectException e) {
			logger.error("RequestSenderUtil request error", e);
		} catch (IOException e) {
			logger.error("RequestSenderUtil request  error", e);
		} catch (Exception e) {
			logger.error("RequestSenderUtil request  error", e);
		}
		return null;
	}
}