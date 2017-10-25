package com.njnu.util;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonUtil {

	public static String changeNumToString(int num){
		if(num<10000){
			return String.valueOf(num)+"次下载";
		}else{
			float wannum=num/10000.0f;
			DecimalFormat df = new DecimalFormat("0.00");
			return String.valueOf(df.format((wannum)))+"万次下载";
		}
	}
	
	
	/** 
	 * 将时间戳转为代表"距现在多久之前"的字符串 
	 * @param timeStr   时间戳 
	 * @return 
	 */  
	public static String getStandardDate(String timeStr) {  
	  
	    StringBuffer sb = new StringBuffer();  
	  
	    long t = Long.parseLong(timeStr);  
	    long time = System.currentTimeMillis() - (t*1000);  
	    long mill = (long) Math.ceil(time /1000);//秒前  
	  
	    long minute = (long) Math.ceil(time/60/1000.0f);// 分钟前    
	    long hour = (long) Math.ceil(time/60/60/1000.0f);// 小时    
	    long day = (long) Math.ceil(time/24/60/60/1000.0f);// 天前  
	  
	    if (day - 1 > 0) {  
	        sb.append(day + "天");  
	    } else if (hour - 1 > 0) {  
	        if (hour >= 24) {  
	            sb.append("1天");  
	        } else {  
	            sb.append(hour + "小时");  
	        }  
	    } else if (minute - 1 > 0) {  
	        if (minute == 60) {  
	            sb.append("1小时");  
	        } else {  
	            sb.append(minute + "分钟");  
	        }  
	    } else if (mill - 1 > 0) {  
	        if (mill == 60) {  
	            sb.append("1分钟");  
	        } else {  
	            sb.append(mill + "秒");  
	        }  
	    } else {  
	        sb.append("刚刚");  
	    }  
	    if (!sb.toString().equals("刚刚")) {  
	        sb.append("前");  
	    }  
	    return sb.toString();  
	}
	
	
	/** 
	 * 将时间戳转为代表"距现在多久之前"的字符串 
	 * @param timeStr   时间戳 
	 * @return 
	 */  
	public static String getStandardDate(long t) {  
	  
	    StringBuffer sb = new StringBuffer();  
	  
	    long time = System.currentTimeMillis() - (t*1000);  
	    long mill = (long) Math.ceil(time /1000);//秒前  
	  
	    long minute = (long) Math.ceil(time/60/1000.0f);// 分钟前    
	    long hour = (long) Math.ceil(time/60/60/1000.0f);// 小时    
	    long day = (long) Math.ceil(time/24/60/60/1000.0f);// 天前  
	  
	    if (day - 1 > 0) {  
	        sb.append(day + "天");  
	    } else if (hour - 1 > 0) {  
	        if (hour >= 24) {  
	            sb.append("1天");  
	        } else {  
	            sb.append(hour + "小时");  
	        }  
	    } else if (minute - 1 > 0) {  
	        if (minute == 60) {  
	            sb.append("1小时");  
	        } else {  
	            sb.append(minute + "分钟");  
	        }  
	    } else if (mill - 1 > 0) {  
	        if (mill == 60) {  
	            sb.append("1分钟");  
	        } else {  
	            sb.append(mill + "秒");  
	        }  
	    } else {  
	        sb.append("刚刚");  
	    }  
	    if (!sb.toString().equals("刚刚")) {  
	        sb.append("前");  
	    }  
	    return sb.toString();  
	}
	
	
	/*时间戳转换成字符窜*/
	public static String getDateToString(long time) {
		Date d = new Date(time);
		SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		return sf.format(d);
	}
	
	
	public static String getCurrentDate() {
		Date d = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		return sf.format(d);
	}
	
	
	/*将字符串转为时间戳*/
	public static long getStringToDate(String time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		try{
			date = sdf.parse(time);
		} catch(ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date.getTime();
	}
}
