package com.njnu.util;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonUtil {

	public static String changeNumToString(int num){
		if(num<10000){
			return String.valueOf(num)+"������";
		}else{
			float wannum=num/10000.0f;
			DecimalFormat df = new DecimalFormat("0.00");
			return String.valueOf(df.format((wannum)))+"�������";
		}
	}
	
	
	/** 
	 * ��ʱ���תΪ����"�����ڶ��֮ǰ"���ַ��� 
	 * @param timeStr   ʱ��� 
	 * @return 
	 */  
	public static String getStandardDate(String timeStr) {  
	  
	    StringBuffer sb = new StringBuffer();  
	  
	    long t = Long.parseLong(timeStr);  
	    long time = System.currentTimeMillis() - (t*1000);  
	    long mill = (long) Math.ceil(time /1000);//��ǰ  
	  
	    long minute = (long) Math.ceil(time/60/1000.0f);// ����ǰ    
	    long hour = (long) Math.ceil(time/60/60/1000.0f);// Сʱ    
	    long day = (long) Math.ceil(time/24/60/60/1000.0f);// ��ǰ  
	  
	    if (day - 1 > 0) {  
	        sb.append(day + "��");  
	    } else if (hour - 1 > 0) {  
	        if (hour >= 24) {  
	            sb.append("1��");  
	        } else {  
	            sb.append(hour + "Сʱ");  
	        }  
	    } else if (minute - 1 > 0) {  
	        if (minute == 60) {  
	            sb.append("1Сʱ");  
	        } else {  
	            sb.append(minute + "����");  
	        }  
	    } else if (mill - 1 > 0) {  
	        if (mill == 60) {  
	            sb.append("1����");  
	        } else {  
	            sb.append(mill + "��");  
	        }  
	    } else {  
	        sb.append("�ո�");  
	    }  
	    if (!sb.toString().equals("�ո�")) {  
	        sb.append("ǰ");  
	    }  
	    return sb.toString();  
	}
	
	
	/** 
	 * ��ʱ���תΪ����"�����ڶ��֮ǰ"���ַ��� 
	 * @param timeStr   ʱ��� 
	 * @return 
	 */  
	public static String getStandardDate(long t) {  
	  
	    StringBuffer sb = new StringBuffer();  
	  
	    long time = System.currentTimeMillis() - (t*1000);  
	    long mill = (long) Math.ceil(time /1000);//��ǰ  
	  
	    long minute = (long) Math.ceil(time/60/1000.0f);// ����ǰ    
	    long hour = (long) Math.ceil(time/60/60/1000.0f);// Сʱ    
	    long day = (long) Math.ceil(time/24/60/60/1000.0f);// ��ǰ  
	  
	    if (day - 1 > 0) {  
	        sb.append(day + "��");  
	    } else if (hour - 1 > 0) {  
	        if (hour >= 24) {  
	            sb.append("1��");  
	        } else {  
	            sb.append(hour + "Сʱ");  
	        }  
	    } else if (minute - 1 > 0) {  
	        if (minute == 60) {  
	            sb.append("1Сʱ");  
	        } else {  
	            sb.append(minute + "����");  
	        }  
	    } else if (mill - 1 > 0) {  
	        if (mill == 60) {  
	            sb.append("1����");  
	        } else {  
	            sb.append(mill + "��");  
	        }  
	    } else {  
	        sb.append("�ո�");  
	    }  
	    if (!sb.toString().equals("�ո�")) {  
	        sb.append("ǰ");  
	    }  
	    return sb.toString();  
	}
	
	
	/*ʱ���ת�����ַ���*/
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
	
	
	/*���ַ���תΪʱ���*/
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
