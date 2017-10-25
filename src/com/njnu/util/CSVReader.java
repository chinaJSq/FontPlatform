package com.njnu.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import android.content.Context;
import android.content.ContextWrapper;


public class CSVReader {
	Context context = null;

	public CSVReader()
	{
		context = new ContextWrapper(null);
	}
		
	public  ArrayList<String> readWordInclude(String  word)
	{
		ArrayList<String> str = new ArrayList<String>();
		InputStream wordInclude=this.getClass().getClassLoader()
				.getResourceAsStream(word);
			BufferedReader br;
			try {
				br = new BufferedReader(new InputStreamReader(
						wordInclude, "UTF-8"));
				 // ��ȡֱ�����һ�� 
		          String line = ""; 
		          while ((line = br.readLine()) != null) { 
		              // ��һ�����ݷָ�ɶ���ֶ� 
		             // String st =line.substring(1,line.length()); 
		              String st =line; 
		                  // ÿһ�еĶ���ֶ���TAB������ʾ 
		              str.add(st);
		             // System.out.println(); 
		          } 
		          br.close(); 
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			
//		Log.i("WordSpellTerns", all.size() + "");
		return str;
	}
	
}
