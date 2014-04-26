package cn.liuyb.app.common.utils;


public class Constants {
	
	public static String PASS_OBFUSCATE = "tbmis!!$%stbmis";
	
	public static String getObfuscatePass(String pass) {
		String password = String.format(PASS_OBFUSCATE, pass);
		return MD5.getMD5(password, "utf-8");
	}

	public static Long DEPARTMENT = 1L;//用户部门
	public static Long POSITION = 2L;//考勤状态
	public static Long ATTENTANCE_STATUS = 3L;//考勤状态
	public static Long AFTER_SALE_TYPE = 4L;//售后类型
	public static Long SALE_TERRACE = 5L;//销售平台
	public static Long CANCLE_GOODS_TYPE = 6L;//退货类型
	public static Long GOODS_TYPE = 7L;//货品状态
	
	public static String LOGINED = "LOGINED";
}
