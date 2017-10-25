package com.edu.common;
import java.io.UnsupportedEncodingException;
public class ChineseCode {
	
	public String byteToHexString(byte b){
		byte[] bt ={ b}; //字节数组	
		return byteToHexString(bt);
	}
	public String byteToHexString(byte[] bt){//获取汉字的16进制表示
		String hexUpper = "";
		for(int i=0;i<bt.length;i++){
			String hex = Integer.toHexString(bt[i] & 0xFF);//转化为16进制
			if(hex.length()==1){
				hex = '0' +hex; //长度是1位的，在前面补0		
			}
			hexUpper +=hex.toUpperCase();//转换为大写			
		}
		return hexUpper;
	}
	public  String chineseCode(String chinese){//汉字转化为区位码
		byte[] bt ;
		String code = "";
		try {
			bt = chinese.getBytes("GB2312");//用GB2312编码为字节数组
			for(int i =0;i<bt.length;i++){
				int a = Integer.parseInt(byteToHexString(bt[i]),16);
				code +=(a- 0x80- 0x20)+"";//获得区位码
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		 StringBuffer s =new StringBuffer();
		   if(code.length()==3){
			   char c1= code.charAt(0);
			   char c2= code.charAt(1);
			   char c3= code.charAt(2);
			
			 s.append(c1);
			 s.append(c2);
			 s.append('0');
			 s.append(c3);
			 code= s.toString();
		   }
		
		return code;	
	}
	   public static void main(String[] args) {  
		   String code =new ChineseCode().chineseCode("天");
			//String hex = Integer.toHexString(Integer.parseInt(code));//转化为16进制
		   System.out.println(code);
	   }
}
