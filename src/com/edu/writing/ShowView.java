package com.edu.writing;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.edu.common.CommonMethod;
import com.edu.fontplatform.R;
import com.njnu.struct.Point;
import com.njnu.struct.Stroke;
import com.njnu.struct.SubStroke;
import com.njnu.util.BitmapUtil;
import com.njnu.util.ViewUtil;


public class ShowView extends View
{
	private Canvas canvas;
	private Paint paint;
	private Bitmap background;
	double x;
	double y;
	int num = 0;

	private Context context;
	private ViewUtil viewUtil = new ViewUtil();
	private double leftheight = 0;
	private double multiple = 1;
	private int viewWidth = 500;

	private Handler handler = new Handler();
	private MyRunable myRunable;
	int resBackgroundId=R.drawable.bktianzige;

	public ShowView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		this.context = context;
		viewWidth = CommonMethod.dip2px(context, 300);
		setViewBackground();
	}
	
	public void setViewBackground(){		
		background = BitmapUtil.decodeSampledBitmapFromResource(context.getResources(),resBackgroundId,viewWidth, viewWidth);			
		canvas = new Canvas();
		canvas.setBitmap(background);
		paint = viewUtil.newPaint(Color.BLACK, 10);
		invalidate();
	}


	public double getLeftheight()
	{
		return leftheight;
	}

	public void setLeftheight(double leftheight)
	{
		this.leftheight = leftheight;
	}

	public double getMultiple()
	{
		return multiple;
	}

	public void setMultiple(double multiple)
	{
		this.multiple = multiple;
	}

	public int getViewWidth()
	{
		return viewWidth;
	}

	public void setViewWidth(int viewWidth)
	{
		this.viewWidth = viewWidth;
	}

	@Override
	public void onDraw(Canvas c)
	{
		c.drawBitmap(background, 0, 0, null);
	}

	public void screenClear()
	{

		background = BitmapUtil.decodeSampledBitmapFromResource(context.getResources(),resBackgroundId,viewWidth, viewWidth);		
		canvas = new Canvas();
		canvas.setBitmap(background);
		invalidate();
	}
	
	
	public void screenRedClear()
	{

		background = BitmapUtil.decodeSampledBitmapFromResource(context.getResources(),R.drawable.bktianzige,viewWidth, viewWidth);		
		canvas = new Canvas();
		canvas.setBitmap(background);
		invalidate();
	}

	public void screenClearOnly()
	{
		background = BitmapUtil.decodeSampledBitmapFromResource(context.getResources(),resBackgroundId,viewWidth, viewWidth);		
		canvas = new Canvas();
		canvas.setBitmap(background);
		invalidate();

	}

	// 笔段的轮廓
	public void drawSubOutline(ArrayList<Stroke> newStrokes)
	{
		Paint pPaint = viewUtil.newPaint(Color.RED, 10);
		screenClearOnly();
		for (Stroke stroke : newStrokes)
		{
			ArrayList<SubStroke> subs = stroke.getSubstrokes();
			for (SubStroke sub : subs)
			{
				canvas.drawLine((float) sub.getStartPoint().getX(), (float) sub.getStartPoint().getY(), (float) sub.getEndPoint().getX(), (float) sub.getEndPoint().getY(), paint);// 画线
				canvas.drawPoint((float) sub.getStartPoint().getX(), (float) sub.getStartPoint().getY(), pPaint);
				canvas.drawPoint((float) sub.getEndPoint().getX(), (float) sub.getEndPoint().getY(), pPaint);
			}
		}
	}

	public void drawExpertUserStroke(List<Stroke> severalStroke, List<Stroke> redStroke)
	{
		screenClear();

		Paint pPaint = viewUtil.newPaint(Color.RED, 10);
		if (redStroke != null)
			for (Stroke aPoints : redStroke)
			{
				viewUtil.DrawStroke(canvas, pPaint, 1, aPoints.getPoints(), viewWidth);
			}
		if (severalStroke != null)
			for (Stroke aPoints : severalStroke)
			{
				viewUtil.DrawStroke(canvas, paint, 1, aPoints.getPoints(), viewWidth);
			}
	}

	public void drawBlackWord(List<Stroke> severalStroke)
	{
		screenClear();
		if (severalStroke != null)
			for (Stroke aPoints : severalStroke)
			{
				viewUtil.DrawStroke(canvas, paint, 1, aPoints.getPoints(), viewWidth);
			}
	}
	
	public void drawColorWord(List<Stroke> severalStroke)
	{
		screenRedClear();
//		Paint paint = viewUtil.newPaint(context.getResources().getColor(R.color.blue), 10);
		Paint paint = viewUtil.newPaint(Color.RED, 10);
		if (severalStroke != null)
			for (Stroke aPoints : severalStroke)
			{
				viewUtil.DrawStroke(canvas, paint, 1, aPoints.getPoints(), viewWidth);
			}
	}
	

	public void drawExpertRed(List<Stroke> severalStroke)
	{
		screenClear();
		Paint pPaint = viewUtil.newPaint(Color.RED, 10);
		if (severalStroke != null)
			for (Stroke aPoints : severalStroke)
			{
				viewUtil.DrawStroke(canvas, pPaint, 1, aPoints.getPoints(), viewWidth);
			}
	}

	int time = 0, subtime = 0;

	public void replay(List<Stroke> Strokes)
	{
		screenClear();
		myRunable = new MyRunable(Strokes);
		handler.postDelayed(myRunable, 20);
	}

	public void stopReplay()
	{
		screenClear();
		handler.removeCallbacks(myRunable);// 停止切换
		time = 0;
		subtime = 0;
	}

	public void stopReplayBeilin()
	{
		handler.removeCallbacks(myRunable);// 停止切换
		time = 0;
		subtime = 0;
	}

	class MyRunable implements Runnable
	{
		List<Stroke> Strokes;

		public MyRunable(List<Stroke> Strokes)
		{
			this.Strokes = Strokes;
		}

		@Override
		public void run()
		{

			if (subtime == Strokes.get(time).getPoints().size() - 1)
			{
				subtime = 0;

				if (time < Strokes.size() - 1)
				{
					time++;
				} else
				{
					time = 0;
					screenClearOnly();
				}

			}

			Paint pPaint = viewUtil.newPaint(Color.RED, 4);
			viewUtil.reshowSubStroke(canvas, Strokes.get(time).getPoints(), subtime, pPaint, multiple, leftheight, viewWidth);
			invalidate();

			subtime++;

			if (time == Strokes.size() - 1 && subtime == Strokes.get(time).getPoints().size() - 1)
			{
				handler.postDelayed(myRunable, 1500);
			} else
			{
				handler.postDelayed(myRunable, 20);
			}

		}
	}

	// ===================================显示工整性问题的代码===================================================================================

	public void drawWord(List<Stroke> severalStroke, List<Stroke> redStroke)
	{
		Paint pPaint = viewUtil.newPaint(Color.RED, 10);
		screenClear();

		if (severalStroke != null)
			for (Stroke aPoints : severalStroke)
			{
				viewUtil.DrawStroke(canvas, paint, 1, aPoints.getPoints(), viewWidth);
			}

		if (redStroke != null)
			for (Stroke aPoints : redStroke)
			{
				viewUtil.DrawStroke(canvas, pPaint, 1, aPoints.getPoints(), viewWidth);
			}
		
		//drawConvexHull(redStroke,pPaint);//显示凸壳
	}
	
//	public void drawConvexHull(List<Stroke> redStroke,Paint pPaint){
//		ArrayList<Point> points=new ArrayList<Point>();
//		for(Stroke bPoints:redStroke){
//			for(Point one:bPoints.getPoints()){
//				points.add(one);
//			}
//		}
//		
//		Point[] word=new Point[points.size()];
//		int num=0;
//		for(Point p:points){
//			word[num]=p;
//			num++;
//		}
//		
//		
//		Graham g=new Graham();		
//		Point[] convex=g.Graham_scan(word,points.size());
//		
//		int convexnum=convex.length;
//		
//		for(int i=0;i<convex.length-1;i++){
//			canvas.drawLine((float)convex[i].x, (float) convex[i].y, (float) convex[i+1].x, (float) convex[i+1].y, pPaint);
//		}
//		
//		canvas.drawLine((float)convex[convex.length-1].x, (float) convex[convex.length-1].y, (float) convex[0].x, (float) convex[0].y, pPaint);
//		
//		Log.e("点集个数为：",convexnum+"");
// 	}

	public void drawMarkedStroke(ArrayList<Stroke> strokes, ArrayList<Stroke> redStrokes)
	{
		Paint pPaint = viewUtil.newPaint(Color.RED, 6);
		screenClear();
		if (strokes.size() != 0)
			for (Stroke stroke : strokes)
				viewUtil.DrawStroke(canvas, paint, 1, stroke.getPoints(), viewWidth);
		if (redStrokes.size() != 0)
			for (Stroke stroke : redStrokes)
				viewUtil.DrawStroke(canvas, pPaint, 1, stroke.getPoints(), viewWidth);
	}

	public void drawComp(List<Stroke> severalStroke, List<ArrayList<ArrayList<Point>>> redStroke)
	{
		Paint pPaint = viewUtil.newPaint(Color.RED, 6);
		screenClear();

		if (severalStroke != null)
			for (Stroke aPoints : severalStroke)
			{
				viewUtil.DrawStroke(canvas, paint, 1, aPoints.getPoints(), viewWidth);
			}

		if (redStroke != null)
		{
			for (int i = 0; i < redStroke.size(); i++)
			{
				for (ArrayList<Point> aPoints : redStroke.get(i))
				{
					viewUtil.DrawStroke(canvas, pPaint, 1, aPoints, viewWidth);
				}
			}
		}
	}


	
	public void drawCompStroke(List<Stroke> severalStroke, ArrayList<Stroke> redComp, Stroke redStroke)
	{
		Paint pPaint = viewUtil.newPaint(Color.RED, 6);
		screenClear();

		if (severalStroke != null)
			for (Stroke aPoints : severalStroke)
			{
				viewUtil.DrawStroke(canvas, paint, 1, aPoints.getPoints(), viewWidth);
			}

		if (redComp != null)
			for (Stroke aPoints : redComp)
			{
				viewUtil.DrawStroke(canvas, pPaint, 1, aPoints.getPoints(), viewWidth);
			}
		if (redStroke != null)
			viewUtil.DrawStroke(canvas, pPaint, 1, redStroke.getPoints(), viewWidth);

	}

	public void drawStrokes(List<Stroke> severalStroke, ArrayList<ArrayList<Point>> redStroke)
	{
		Paint pPaint = viewUtil.newPaint(Color.RED, 6);
		screenClear();

		if (severalStroke != null)
			for (Stroke aPoints : severalStroke)
			{
				viewUtil.DrawStroke(canvas, paint, 1, aPoints.getPoints(), viewWidth);
			}

		if (redStroke != null)
			for (ArrayList<Point> aPoints : redStroke)
			{
				viewUtil.DrawStroke(canvas, pPaint, 1, aPoints, viewWidth);
			}

	}

	public void drawStroke(List<Stroke> severalStroke, ArrayList<Point> redStroke)
	{
		Paint pPaint = viewUtil.newPaint(Color.RED, 6);
		screenClear();

		if (severalStroke != null)
			for (Stroke aPoints : severalStroke)
			{
				viewUtil.DrawStroke(canvas, paint, 1, aPoints.getPoints(), viewWidth);
			}

		if (redStroke != null)
			viewUtil.DrawStroke(canvas, pPaint, 1, redStroke, viewWidth);

	}

	public int getResBackgroundId() {
		return resBackgroundId;
	}

	public void setResBackgroundId(int resBackgroundId) {
		this.resBackgroundId = resBackgroundId;
	}
	
	public void destroyBitmap(){
		if(background!=null && background.isRecycled()==false){
			background.recycle();
		}
	}

}
