package com.edu.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import com.njnu.struct.Point;
import com.njnu.struct.Stroke;
import com.njnu.util.SaveToXML;

public class CommonMethod {

	//��ȡ��Ļ��С
	public static int getScreenSize(Context context) {
		DisplayMetrics metric = context.getResources().getDisplayMetrics();
		Log.i("fansiqi", metric.widthPixels + "");
		Log.i("fansiqi", metric.heightPixels + "");
		int width = metric.widthPixels; // ��Ļ��ȣ����أ�
		int height = metric.heightPixels; // ��Ļ�߶ȣ����أ�
		float density = metric.density; // ��Ļ�ܶȣ�0.75 / 1.0 / 1.5��
		Log.i("fansiqi", "density:" + density + "");
		double diagonalPixels = Math.sqrt(Math.pow(width, 2)
				+ Math.pow(height, 2));
		double screenSize = diagonalPixels / (160 * density);

		//Log.i("fansiqi", "screenSize:" + Math.round((float) screenSize) + "");

		return Math.round((float) screenSize);
	}

	//��ȡ�����С
	public static int getTextSize(Context context) {

		int screenSize = getScreenSize(context);
		int sp2px = 20;

		if (screenSize >= 9) {
			sp2px = 22;
		} else if (screenSize <= 5) {
			sp2px = 16;
		}

		return sp2px;

	}


	//��ȡ�����С
	public static int getTabTextSize(Context context) {

		int screenSize = getScreenSize(context);
		int sp2px = 20;

		if (screenSize >= 9) {
			sp2px = sp2px(context, 20);
		} else if (screenSize <= 5) {
			sp2px = sp2px(context, 16);
		}

		return sp2px;

	}

	public static float getDensity(Context context){
		DisplayMetrics metric = context.getResources().getDisplayMetrics();
		float density = metric.density; // ��Ļ�ܶȣ�0.75 / 1.0 / 1.5��
		return density;
	}

	//��ȡdp���
	public static int getWidth(Context context, int type) {

		int screenSize = getScreenSize(context);
		DisplayMetrics metric = context.getResources().getDisplayMetrics();
		int width = metric.widthPixels; // ��Ļ��ȣ����أ�
		float density = metric.density; // ��Ļ�ܶȣ�0.75 / 1.0 / 1.5��

		int dip2px = dip2px(context, 320);

		if (type == 1) {
			if (screenSize >= 9) {
				dip2px = dip2px(context, 320);
			}else if (screenSize >= 6) {
				dip2px = dip2px(context, 320);
			}else if (screenSize == 5) {
				if(density==4.0){
					dip2px = dip2px(context, 344);
				}else if(density==2.75){
					dip2px = dip2px(context, 344);
				}else{
					dip2px = dip2px(context, 320);
				}

			}else if (screenSize<=4){
				if(density>=3.0){
					dip2px = dip2px(context, 320);
				}else if(density==1.5 && width>480){
					dip2px = dip2px(context, 326);
				}else{
					dip2px = dip2px(context, 300);
				}				
			}
		} else if (type == 2) {
			if (screenSize >= 9) {
				dip2px = dip2px(context, 150);
			} else if (screenSize >= 4) {
				//---wang changed in 2016-01-31 for adapter the evalue page view
				if(width<600)
					dip2px = dip2px(context, 130);
				else
					//---wang changed in 2016-01-31 for adapter the evalue page view
					dip2px = dip2px(context, 150);
			}else if (screenSize < 4) {
				dip2px = dip2px(context, 110);
			}
		} else if (type == 3) {
			if (screenSize >= 9) {
				dip2px = dip2px(context, 200);
			} else if (screenSize >= 5) {
				if(density>=3.0){
					dip2px = dip2px(context, 120);
				}else{
					dip2px = dip2px(context, 120);
				}		

			}else if (screenSize < 5) {
				if(density>=3.0){
					dip2px = dip2px(context, 120);
				}else{
					dip2px = dip2px(context, 100);
				}		
			}
		}else {
			if (screenSize >= 9) {
				dip2px = dip2px(context, 500);
			} else if (screenSize <= 5) {
				dip2px = dip2px(context, 320);
			}
		}

		return dip2px;

	}


	/**
	 * ��pxֵת��Ϊdip��dpֵ����֤�ߴ��С����
	 * 
	 * @param pxValue
	 * @param scale
	 *            ��DisplayMetrics��������density��
	 * @return
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * ��dip��dpֵת��Ϊpxֵ����֤�ߴ��С����
	 * 
	 * @param dipValue
	 * @param scale
	 *            ��DisplayMetrics��������density��
	 * @return
	 */
	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	/**
	 * ��pxֵת��Ϊspֵ����֤���ִ�С����
	 * 
	 * @param pxValue
	 * @param fontScale
	 *            ��DisplayMetrics��������scaledDensity��
	 * @return
	 */
	public static int px2sp(Context context, float pxValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (pxValue / fontScale + 0.5f);
	}

	/**
	 * ��spֵת��Ϊpxֵ����֤���ִ�С����
	 * 
	 * @param spValue
	 * @param fontScale
	 *            ��DisplayMetrics��������scaledDensity��
	 * @return
	 */
	public static int sp2px(Context context, float spValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}


	/**
	 * �����ļ�������
	 */
	public static void saveFiles(String filepath,List<Stroke> singleWord){
		String xmlpath = filepath;

		File file = new File(xmlpath);
		if (!file.getParentFile().exists())
		{// �ļ�������
			file.getParentFile().mkdirs();// �����ļ�
		}

		if (file.exists())
		{
			file.delete();
		}

		OutputStream output;
		try
		{
			output = new FileOutputStream(file);
			new SaveToXML(output, singleWord).saveStrokes("");
			output.close();

		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}


	// -------����view��д��Ŀ��ս�ͼ
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

	// -----����bitmap��ʽͼƬ�ı��棬Ҫ�󱣴��png��ʽͼƬ �ڱ����ļ����� ��Ҫ�޸�ʱ���Խ����޸ı���·��
	public static void tosave(Bitmap bit, String localPath) throws IOException
	{
		File f = new File(localPath);

		// String localDir=localPath.substring(0,localPath.lastIndexOf("/")+1);
		// File dir=new File(localDir);
		// if(dir.exists()==false){
		// dir.mkdir();
		// }

		if (!f.getParentFile().exists())
		{// �ļ�������
			f.getParentFile().mkdirs();// �����ļ�
		}

		if (f.exists())
		{
			f.delete();
		} else
		{
			f.createNewFile();
		}

		FileOutputStream out = new FileOutputStream(f);
		bit.compress(Bitmap.CompressFormat.PNG, 90, out);// ͼƬ���淽ʽ
		out.flush();
		out.close();
	}

	/**
	 * ��Ļ�ϵ�ԭʼ�㼯
	 * @param orientedStrokes
	 * @return  ������Ļ��С
	 */
	public static List<Stroke> changeUserStrokeToOriented(List<Stroke> userStrokes,double screenWidth){

		List<Stroke> orientedStrokes=new ArrayList<Stroke>();

		for(Stroke one:userStrokes){
			List<Point> arrayPoints=new ArrayList<Point>();
			for(Point p:one.getPoints()){
				Point newOne=new Point();
				newOne.setX(p.getX()/screenWidth);
				newOne.setY(p.getY()/screenWidth);
				newOne.setTime(p.getTime());
				arrayPoints.add(newOne);
			}

			Stroke newStroke=new Stroke();
			newStroke.setPoints(arrayPoints);
			orientedStrokes.add(newStroke);
		}

		return orientedStrokes;
	}
	
	
	// ͼƬ����
	public static Bitmap readBitMap(InputStream input)
	{
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Bitmap.Config.RGB_565;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		return BitmapFactory.decodeStream(input, null, opt);
	}

	public static List<Stroke> getSame(Context context,List<Stroke> strokes) {
		// TODO Auto-generated method stub
		int size = dip2px(context, 200);
		List<Stroke> strokes21 = new ArrayList<Stroke>();
		Stroke stroke;
		List<Point> points2 ;
		List<Point> points ;
		Point point;

		for(int i=0;i<strokes.size();i++){
			stroke = new Stroke();
			points =new ArrayList<Point>();
			points2 =new ArrayList<Point>();
			points = strokes.get(i).getPoints();
			for(int j = 0;j<points.size();j++){						
				point =new Point();
				point.setX(points.get(j).getX()*size);
				point.setY(points.get(j).getY()*size);
				points2.add(point);
			}
			stroke.setPoints(points2);		
			strokes21.add(stroke);
		}				
		return strokes21;
	}


	

	
}
