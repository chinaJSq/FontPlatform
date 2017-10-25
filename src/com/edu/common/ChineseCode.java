package com.edu.common;
import java.io.UnsupportedEncodingException;
public class ChineseCode {
	
	public String byteToHexString(byte b){
		byte[] bt ={ b}; //�ֽ�����	
		return byteToHexString(bt);
	}
	public String byteToHexString(byte[] bt){//��ȡ���ֵ�16���Ʊ�ʾ
		String hexUpper = "";
		for(int i=0;i<bt.length;i++){
			String hex = Integer.toHexString(bt[i] & 0xFF);//ת��Ϊ16����
			if(hex.length()==1){
				hex = '0' +hex; //������1λ�ģ���ǰ�油0		
			}
			hexUpper +=hex.toUpperCase();//ת��Ϊ��д			
		}
		return hexUpper;
	}
	public  String chineseCode(String chinese){//����ת��Ϊ��λ��
		byte[] bt ;
		String code = "";
		try {
			bt = chinese.getBytes("GB2312");//��GB2312����Ϊ�ֽ�����
			for(int i =0;i<bt.length;i++){
				int a = Integer.parseInt(byteToHexString(bt[i]),16);
				code +=(a- 0x80- 0x20)+"";//�����λ��
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
		   String code =new ChineseCode().chineseCode("��");
			//String hex = Integer.toHexString(Integer.parseInt(code));//ת��Ϊ16����
		   System.out.println(code);
	   }
}
