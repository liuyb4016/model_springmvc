package cn.liuyb.app.common.utils;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

public class FormatUtils {
	/*
	 * 将流量数据转化为相应单位大小显示
	 */
	public static String formatSize(Object sizeParam){
		DecimalFormat df = new DecimalFormat("0.00");
		double resultSize = 0d;
		if (sizeParam == null){
			resultSize = 0l;
		} else {
			resultSize = (Long)sizeParam;
		}
		String unit = " B";
		if (resultSize >= 1024){
			resultSize = (double)resultSize / 1024;
			unit = " KB";
		}else{
			return df.format(resultSize)+unit;
		}
		
		if (resultSize >= 1024){
			resultSize = (double)resultSize / 1024;
			unit = " MB";
		}else{
			return df.format(resultSize)+unit;
		}
		
		if (resultSize >= 1024){
			resultSize = (double)resultSize / 1024;
			unit = " GB";
		}else{
			return df.format(resultSize)+unit;
		}
		return df.format(resultSize)+unit;
	}
	public static String antiXss(Object str){
		if(str==null){
			return "";
		}
		return str+"";
	}

	public static String antiXss(Object str,String targetStr){
		if(str==null){
			return targetStr;
		}
		return str+"";
	}
	
	public static Date getLastDayOfMonth(Date date) {
	   Calendar lastDate = Calendar.getInstance();
	   lastDate.setTime(date);
	   lastDate.set(Calendar.DATE,1);//设为当前月的1号
	   lastDate.add(Calendar.MONTH,1);//加一个月，变为下月的1号
	   lastDate.add(Calendar.DATE,-1);//减去一天，变为当月最后一天
	   
	   return lastDate.getTime();  
	}
}
