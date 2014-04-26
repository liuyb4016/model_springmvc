package cn.liuyb.app.common.utils;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

public class ServletResponseUtils {
	/**
	 * 捕获 {@link HttpServletResponse#sendError(int, String)}抛出的异常并打印到控制台
	 * @param resp
	 * @param sc
	 * @param msg
	 */
	public static void sendError(HttpServletResponse resp, int sc, String msg) {
		try {
			resp.sendError(sc, msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 捕获 {@link HttpServletResponse#sendError(int)}抛出的异常并打印到控制台
	 * @param resp
	 * @param sc
	 * @param msg
	 */
	public static void sendError(HttpServletResponse resp, int sc) {
		try {
			resp.sendError(sc);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
