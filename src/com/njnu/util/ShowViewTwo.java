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
import android.util.DisplayMetrics;
import android.view.View;

import com.njnu.struct.Point;
import com.njnu.struct.Stroke;

/**
 * 用于显示和处理汉字的view
 *
 */
public class ShowViewTwo extends View {
	
	private String word;
	private int fontCode=0;
	//屏幕的宽高
	private DisplayMetrics dm = super.getResources().getDisplayMetrics();
	private int screenWidth = dm.widthPixels ;
	private int screenHeight=dm.heightPixels;
	private int viewWidth= screenWidth/2;
	private int viewHeight=screenHeight/3;
    public Bitmap bmp;
    
    private Paint paint=new Paint();
    private Canvas canvas;

  	private ViewUtil viewUtil = new ViewUtil();
    public ShowViewTwo(Context context) {
		super(context);
		
	}
    
    public ShowViewTwo(Context context,String word) {
		super(context);
		this.word=word;
		// TODO Auto-generated constructor stub
		this.bmp=Bitmap.createBitmap(viewWidth, viewHeight, Config.ARGB_8888);
		canvas=new Canvas(bmp);
		canvas.drawColor(Color.WHITE);
	}
    	
	//印刷体
	public ShowViewTwo(Context context, String word,List<Stroke> strokes) {
		super(context);
		this.word = word;
		this.bmp=Bitmap.createBitmap(600, 600, Config.ARGB_8888);
		canvas=new Canvas(bmp);
		canvas.drawColor(Color.WHITE);
//		Paint paint1=new Paint(paint);
//		paint1.setAntiAlias(true);
//		paint1.setColor(Color.BLACK);
//		paint1.setStrokeCap(Cap.ROUND);
//		paint1.setStrokeJoin(Join.ROUND);
//		paint1.setStrokeWidth(5);
		paint = viewUtil.newPaint(Color.BLACK, 10);	
		List<Point> points =new ArrayList<Point>();
		for(int i=0;i<strokes.size();i++){
			points = new ArrayList<Point>(); 
			points = strokes.get(i).getPoints();
			viewUtil.DrawStroke(canvas, paint, 1, points, viewWidth);
		}	
	}
	@Override
	protected void onDraw(Canvas canvas) {		
		super.onDraw(canvas);
		canvas.drawBitmap(bmp, 0, 0, null);
	
	}
			
}
