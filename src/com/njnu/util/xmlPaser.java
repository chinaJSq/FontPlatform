package com.njnu.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Xml;
import android.view.View;

import com.njnu.struct.Point;
import com.njnu.struct.Stroke;

public class xmlPaser {
    public static List<Stroke> getStrokes(InputStream xml) throws Exception {        
        List<Stroke> strokes = new ArrayList<Stroke>();
        List<Point> points = new ArrayList<Point>();
        Point point = null;
        Stroke stroke = null;
    	strokes = new ArrayList<Stroke>();
    	points = new ArrayList<Point>();
        XmlPullParser pullParser = Xml.newPullParser();
        pullParser.setInput(xml, "UTF-8"); //ΪPull����������Ҫ������XML����        
        int event = pullParser.getEventType();
        
        while (event != XmlPullParser.END_DOCUMENT){
            
            switch (event) {
            
            case XmlPullParser.START_DOCUMENT:
                   
                break;    
            case XmlPullParser.START_TAG:    
                if ("stroke".equals(pullParser.getName())){
                  //  int id = Integer.valueOf(pullParser.getAttributeValue(0));
                	points = new ArrayList<Point>();
                    stroke = new Stroke();
                //    stroke.setStrokeID(id);
                }
                if ("point".equals(pullParser.getName())){
                  //  int id = Integer.valueOf(pullParser.getAttributeValue(0));
                    point = new Point();
                    //point.setId(id);
                }
                if ("x".equals(pullParser.getName())){
                    String x = pullParser.nextText();
                    point.setX(Double.parseDouble(x));
                }                                
                if ("y".equals(pullParser.getName())){
                    String y = pullParser.nextText();
                    point.setY(Double.parseDouble(y));
                }
                if ("time".equals(pullParser.getName())){
                    String time = pullParser.nextText();
                    point.setTime( Long.parseLong(time));
                }
                break;
                
            case XmlPullParser.END_TAG:
                if ("point".equals(pullParser.getName())){
                	points.add(point);
                }
                if ("stroke".equals(pullParser.getName())){
                	stroke.setPoints(points);
                	strokes.add(stroke);
                }
                break;
                
            }
            
            event = pullParser.next();
        }
        
        
        return strokes;
    }
  /**
   * ������д��
   * @param m
   * @param width
   * @return
   */
  	public static Bitmap shot(View m, int width)
  	{
  		View view = m;// ----��Ҫ���н�ͼ����д��
  		view.setDrawingCacheEnabled(true);
  		view.buildDrawingCache();
  		Bitmap b1 = view.getDrawingCache();// ��ȡ��д����ƻ���
  		// ------------------
  		Bitmap b = Bitmap.createBitmap(b1, 0, 0, width, width);// ����д����б���
  		view.destroyDrawingCache();
  		// ------------------
  		return b; // ����bitmap��ʽ�Ľ�ͼ��Ϣ
  	}
  	
  /**
   * 
   * @param b
   * @param strFileName
   * @param s
   */
  	public static void savePic(Bitmap b,String name,String classname,String no,String mode){ 
  		File file = new File(Environment.getExternalStorageDirectory()+File.separator+"FontPlatform"+File.separator
				+classname+ File.separator + no + File.separator  + mode + File.separator + name + ".png"); // Ҫ����ļ���·��
  		if (!file.getParentFile().exists())
  		{ // �ļ�������
  			file.getParentFile().mkdirs(); // �����ļ���
  		}
  		FileOutputStream fos = null; 
  		try { 
  			fos = new FileOutputStream(file); 
  			if (null != fos) { 
  				b.compress(Bitmap.CompressFormat.PNG, 90, fos); 
  				fos.flush(); 
  				fos.close(); 
  			} 
  		} catch (FileNotFoundException e) { 
  			e.printStackTrace(); 
  		} catch (IOException e) { 
  			e.printStackTrace(); 
  		} 
  	} 
    
    /**
     * �������ݵ�xml�ļ���
     * @param persons
     * @param out
     * @throws Exception
     */
    public static void save(String name,List<Stroke> strokes,String classname,String no,String mode ) throws Exception {
    	OutputStream output = null;
		try
		{
			if (!Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED))
			{
				return; // ���ص�����ı����ô�
			}
			// ������SD���еĸ�ʽΪ��StrokeSplit/ӡˢ��/bai.xml
			File file = new File(Environment.getExternalStorageDirectory()+File.separator+"FontPlatform"+File.separator
				+classname+ File.separator	+ no + File.separator+ mode + File.separator + name + ".xml"); // Ҫ����ļ���·��
			if (!file.getParentFile().exists())
			{ // �ļ�������
				file.getParentFile().mkdirs(); // �����ļ���
			}
			output = new FileOutputStream(file);
		}catch(Exception e){
			System.out.println(e);
		}
        XmlSerializer serializer = Xml.newSerializer();
        serializer.setOutput(output, "UTF-8");
        serializer.startDocument("UTF-8", true);
        serializer.startTag(null, "property");  
        for (Stroke stroke : strokes) {
         serializer.startTag(null, "stroke");       	
        serializer.attribute(null, "strokesId", stroke.getStrokeID()+"");            
        List <Point> points = new ArrayList<Point>();
        points = stroke.getPoints();
        for (Point point : points) {
            serializer.startTag(null, "point");            
            serializer.attribute(null, "id",point.getId()+"");            
            serializer.startTag(null, "x");            
            serializer.text(point.getX()+"");
            serializer.endTag(null, "x");            
            serializer.startTag(null, "y");            
            serializer.text(point.getY()+"");
            serializer.endTag(null, "y");         
            serializer.startTag(null, "time");   
            serializer.text(point.getTime()+"");
            serializer.endTag(null, "time");       
            serializer.endTag(null, "point");
        }        
        serializer.endTag(null, "stroke");       
        }
        serializer.endTag(null, "property");
        
        
        
        serializer.endDocument();
        output.flush();
        output.close();
    }
}
