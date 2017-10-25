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

	//获取屏幕大小
	public static int getScreenSize(Context context) {
		DisplayMetrics metric = context.getResources().getDisplayMetrics();
		Log.i("fansiqi", metric.widthPixels + "");
		Log.i("fansiqi", metric.heightPixels + "");
		int width = metric.widthPixels; // 屏幕宽度（像素）
		int height = metric.heightPixels; // 屏幕高度（像素）
		float density = metric.density; // 屏幕密度（0.75 / 1.0 / 1.5）
		Log.i("fansiqi", "density:" + density + "");
		double diagonalPixels = Math.sqrt(Math.pow(width, 2)
				+ Math.pow(height, 2));
		double screenSize = diagonalPixels / (160 * density);

		//Log.i("fansiqi", "screenSize:" + Math.round((float) screenSize) + "");

		return Math.round((float) screenSize);
	}

	//获取字体大小
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


	//获取字体大小
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
		float density = metric.density; // 屏幕密度（0.75 / 1.0 / 1.5）
		return density;
	}

	//获取dp宽度
	public static int getWidth(Context context, int type) {

		int screenSize = getScreenSize(context);
		DisplayMetrics metric = context.getResources().getDisplayMetrics();
		int width = metric.widthPixels; // 屏幕宽度（像素）
		float density = metric.density; // 屏幕密度（0.75 / 1.0 / 1.5）

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
	 * 将px值转换为dip或dp值，保证尺寸大小不变
	 * 
	 * @param pxValue
	 * @param scale
	 *            （DisplayMetrics类中属性density）
	 * @return
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 将dip或dp值转换为px值，保证尺寸大小不变
	 * 
	 * @param dipValue
	 * @param scale
	 *            （DisplayMetrics类中属性density）
	 * @return
	 */
	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	/**
	 * 将px值转换为sp值，保证文字大小不变
	 * 
	 * @param pxValue
	 * @param fontScale
	 *            （DisplayMetrics类中属性scaledDensity）
	 * @return
	 */
	public static int px2sp(Context context, float pxValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (pxValue / fontScale + 0.5f);
	}

	/**
	 * 将sp值转换为px值，保证文字大小不变
	 * 
	 * @param spValue
	 * @param fontScale
	 *            （DisplayMetrics类中属性scaledDensity）
	 * @return
	 */
	public static int sp2px(Context context, float spValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}


	/**
	 * 保存文件到本地
	 */
	public static void saveFiles(String filepath,List<Stroke> singleWord){
		String xmlpath = filepath;

		File file = new File(xmlpath);
		if (!file.getParentFile().exists())
		{// 文件不存在
			file.getParentFile().mkdirs();// 创建文件
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


	// -------进行view书写框的快照截图
	public static Bitmap shot(View m, int width)
	{
		View view = m;// ----需要进行截图的书写框
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap b1 = view.getDrawingCache();// 获取书写框绘制缓存
		// ------------------
		Bitmap b = Bitmap.createBitmap(b1, 0, 0, width, width);// 对书写框进行保存
		view.destroyDrawingCache();
		// ------------------
		return b; // 返回bitmap格式的截图信息
	}

	// -----进行bitmap格式图片的保存，要求保存成png格式图片 在本地文件夹中 需要修改时可以进行修改保存路径
	public static void tosave(Bitmap bit, String localPath) throws IOException
	{
		File f = new File(localPath);

		// String localDir=localPath.substring(0,localPath.lastIndexOf("/")+1);
		// File dir=new File(localDir);
		// if(dir.exists()==false){
		// dir.mkdir();
		// }

		if (!f.getParentFile().exists())
		{// 文件不存在
			f.getParentFile().mkdirs();// 创建文件
		}

		if (f.exists())
		{
			f.delete();
		} else
		{
			f.createNewFile();
		}

		FileOutputStream out = new FileOutputStream(f);
		bit.compress(Bitmap.CompressFormat.PNG, 90, out);// 图片保存方式
		out.flush();
		out.close();
	}

	/**
	 * 屏幕上的原始点集
	 * @param orientedStrokes
	 * @return  除以屏幕大小
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
	
	
	// 图片回收
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
