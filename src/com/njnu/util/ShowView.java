package com.njnu.util;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.util.DisplayMetrics;
import android.view.View;

import com.njnu.struct.Point;
import com.njnu.struct.Stroke;

/**
 * 用于显示和处理汉字的view
 *
 */
public class ShowView extends View {

	private String word;
	private int fontCode=0;
	//屏幕的宽高
	private DisplayMetrics dm = super.getResources().getDisplayMetrics();
	private int screenWidth = dm.widthPixels ;
	private int screenHeight=dm.heightPixels;
	private int viewWidth= 100;
	private int viewHeight=100;
	//	private int viewWidth= screenWidth/2;
	//	private int viewHeight=screenHeight/3;
	public Bitmap bmp;

	private Paint paint=new Paint();
	private Canvas canvas;
	private Path mPath = new Path();
	private float mLastX=0,mLastY=0;
	int border =2;

	private ViewUtil viewUtil = new ViewUtil();
	public ShowView(Context context) {
		super(context);

	}

	public ShowView(Context context,String word) {
		super(context);
		this.word=word;
		// TODO Auto-generated constructor stub
		this.bmp=Bitmap.createBitmap(viewWidth, viewHeight, Config.ARGB_8888);
		canvas=new Canvas(bmp);
		canvas.drawColor(Color.WHITE);
	}

	public Bitmap getBmp() {
		return bmp;
	}

	public void setBmp(Bitmap bmp) {
		this.bmp = bmp;
	}

	//印刷体
	public ShowView(Context context, String word,List<Stroke> strokes,int type) {
		super(context);
		this.word = word;
		this.bmp=Bitmap.createBitmap(100, 100, Config.ARGB_8888);
		canvas=new Canvas(bmp);
		canvas.drawColor(Color.WHITE);
		//		Paint paint1=new Paint(paint);
		//		paint1.setAntiAlias(true);
		//		paint1.setColor(Color.BLACK);
		//		paint1.setStrokeCap(Cap.ROUND);
		//		paint1.setStrokeJoin(Join.ROUND);
		//		paint1.setStrokeWidth(5);
		paint = viewUtil.newPaint(Color.BLACK, 10);	
		List<Point> newpoints =new ArrayList<Point>();
		for(int i=0;i<strokes.size();i++){
			newpoints = new ArrayList<Point>(); 
			List<Point> points = strokes.get(i).getPoints();

			for(Point p:points){
				Point a=new Point();
				a.setX(p.getX()*100);
				a.setY(p.getY()*100);
				a.setTime(p.getTime());
				newpoints.add(a);	
			}
			if(type ==0){			
				viewUtil.DrawStroke(canvas, paint, 3, newpoints, 100);
			}else{
				Paint p = new Paint();
				p.setAntiAlias(true);
				p.setColor(Color.BLACK);
				p.setStrokeCap(Cap.ROUND);
				p.setStrokeJoin(Join.ROUND);
				p.setStyle(Style.STROKE); //设置画笔为空心  
				p.setStrokeWidth(border);
				p.setStrokeWidth(border);


				for(int n=0;n<newpoints.size()-1;n++){
					//						mPath.lineTo((float)strokes.get(j).getPoints().get(strokes.get(j).getPoints().size()-1).getX()*100,(float)strokes.get(j).getPoints().get(strokes.get(j).getPoints().size()-1).getY()*100);
					canvas.drawLine(
							(float)newpoints.get(n).getX(),
							(float)newpoints.get(n).getY(),
							(float)newpoints.get(n+1).getX(), 
							(float)newpoints.get(n+1).getY(), 
							p);
				}



			}
			//			viewUtil.DrawStroke(canvas, paint, 1, newpoints, viewWidth*3/2);
		}	
	}

	@Override
	protected void onDraw(Canvas canvas) {		
		super.onDraw(canvas);
		canvas.drawBitmap(bmp, 0, 0, null);

	}


}
