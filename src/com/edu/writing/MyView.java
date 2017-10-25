package com.edu.writing;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.edu.common.CommonMethod;
import com.edu.fontplatform.R;
import com.njnu.struct.Point;
import com.njnu.struct.Stroke;
import com.njnu.util.BitmapUtil;
import com.njnu.util.ViewUtil;

public class MyView extends View
{
	private Canvas canvas;
	private Canvas transpcanvas;
	private Paint paint;
	private Paint redPaint;
	private Bitmap bitmap;
	private Bitmap background;
	private GridViewAdapter adapter;

	private Bitmap transpbitmap;
	// double x;
	// double y;
	double x[] = new double[1024];
	double y[] = new double[1024];
	int num = 0;
	private float border = 10;// 用于画线
	private float tBorder = border;// 用于设置前一笔的宽度
	private float fBorder = border;// 用于设置起始笔的宽度
	private Stroke stroke =new Stroke();
	private ArrayList<Point> points = new ArrayList<Point>();
	private List<Stroke> strokes = new ArrayList<Stroke>();
	private ViewUtil vu = new ViewUtil();
	// private ArrayList<Point> arrayPoint = new ArrayList<Point>();
	private Point fPoint = new Point();// 按下的点
	private Point lPoint = new Point();// 松开的点
	private List<ArrayList<Point>> mylastStroke;

	private ArrayList<Stroke> tempStrokes = new ArrayList<Stroke>();
	// private ArrayList<Point> tempArrayPoints = new ArrayList<Point>();
	private ArrayList<Stroke> expertStrokes=new ArrayList<Stroke>();

	int StrokeOrderID = 0;

	int height = 0;
	int multiple = 1;
	
	private int type=0;

	Context context;
	private int show_method = 1;

	private final static int miaoHong = 1;
	private final static int dianTiShi = 2;
	private final static int beiLin = 3;
	private final static int duiLin = 4;
	private final static int FLASH = 5;

	private int p_times = 6; // 闪的次数
	private int p_on = 6; // 闪的持续
	private int p_end = 6; // 闪的持续


	int width = 0;
	private final static int Delay = 5;
	int resBackgroundId=R.drawable.bktianzige;
	//选择笔画类型
	private int flag =0;
	private float mLastX=0,mLastY=0;
	private Path mPath = new Path();
	private Paint p =new Paint();
	public MyView(Context context, AttributeSet attrs)
	{
		super(context, attrs);

		this.context = context;
		this.width = CommonMethod.getWidth(context, 1);
		paint = vu.newPaint(Color.BLACK, 10);	
		redPaint = new Paint();
		int strokeborder=CommonMethod.dip2px(context, 10);
		redPaint = vu.newPaint(redPaint, true, strokeborder);
		redPaint.setARGB(255, 255, 104, 104);
		fPoint.setX(-1);
		lPoint.setX(-2);

		width = CommonMethod.dip2px(context, 150);
		background = BitmapUtil.decodeSampledBitmapFromResource(context.getResources(),resBackgroundId, width, width);		
		transpbitmap = Bitmap.createBitmap(width, width,Bitmap.Config.ARGB_4444);		
		canvas = new Canvas();
		canvas.setBitmap(background);		
		transpcanvas = new Canvas();
		transpcanvas.setBitmap(transpbitmap);

		p.setAntiAlias(true);
		p.setColor(Color.BLACK);
		p.setStrokeCap(Cap.ROUND);
		p.setStrokeJoin(Join.ROUND);
		p.setStyle(Style.STROKE); //设置画笔为空心  
		p.setStrokeWidth(border);
		
	}

	public void setWidth(int width) {
		this.width = width;
		background = BitmapUtil.decodeSampledBitmapFromResource(context.getResources(),resBackgroundId, width, width);		
		transpbitmap = Bitmap.createBitmap(width, width,Bitmap.Config.ARGB_4444);
		canvas.setBitmap(background);
		transpcanvas.setBitmap(transpbitmap);	
		invalidate();
	}


	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		getParent().requestDisallowInterceptTouchEvent(true);
		if (event.getAction() == MotionEvent.ACTION_DOWN)
		{
			if(flag==0){
				x[num] = event.getX();
				y[num] = event.getY();

				Long time = System.nanoTime();
				Point point = new Point();
				point.setX(x[num]);
				point.setY(y[num]);
				point.setTime(time);
				points.clear();
				points.add(point);
			}
			if(flag==1){			
				mPath.moveTo(event.getX(),event.getY());
				x[num] = event.getX();
				y[num] = event.getY();

				Long time = System.nanoTime();
				Point point = new Point();
				point.setX(x[num]);
				point.setY(y[num]);
				point.setTime(time);
				points.clear();
				points.add(point);
			}

			invalidate();
		}

		if (event.getAction() == MotionEvent.ACTION_MOVE)
		{
			if(flag==0){

				long time = System.nanoTime();
				Point point = new Point();
				point.setTime(time);
				num++;
				x[num] = event.getX();
				y[num] = event.getY();
				point.setX(x[num]);
				point.setY(y[num]);
				points.add(point);
				if (num >= Delay)
				{
					float temp = vu.timeBorder(x[num - Delay], y[num - Delay], x[num - Delay + 1], y[num - Delay + 1], width) + tBorder;
					if (num <= Delay * 2)
					{
						if (fBorder > temp)
							border = fBorder - width / 250f;
						else if (fBorder < temp)
							border = fBorder + (temp - fBorder) / Delay;
						else
							border = fBorder;
						// Log.i("11", "border=" + border + ";fBorder=" + fBorder);
					} else
					{
						if (fBorder > temp)
							border = fBorder - width / 250f;
						else if (fBorder < temp)
							border = fBorder + width / 1000f;
						else
							border = fBorder;
						// Log.i("12", "border=" + border + ";fBorder=" + fBorder);
					}
					if (border < 2)
						border = 2;
					paint.setStrokeWidth(border);
					vu.DrawLine(canvas, fBorder, border, paint, (float) x[num - Delay], (float) y[num - Delay], (float) x[num - Delay + 1], (float) y[num - Delay + 1]);
					fBorder = border;
				} else
				{

				}
			}
			if(flag==1){
				mPath.quadTo(mLastX, mLastY,( event.getX()+mLastX)/2,(  event.getY()+mLastY)/2);
				long time = System.nanoTime();
				Point point = new Point();
				point.setTime(time);
				num++;
				x[num] = event.getX();
				y[num] = event.getY();
				point.setX(x[num]);
				point.setY(y[num]);
				points.add(point);
			}
			invalidate();
		}

		if (event.getAction() == MotionEvent.ACTION_UP)
		{
			if(flag==0){
				if (show_method == dianTiShi)
				{
					p_times = 2;
					p_on = 2;
					p_end = 2;
				}

				if (num > Delay + 1)
				{
					vu.DrawLastPoints(canvas, paint, tBorder, fBorder, points, width);
				} else if (num > 1)
				{
					for (int i = 0; i < num; i++)
						canvas.drawLine((float) x[i], (float) y[i], (float) x[i + 1], (float) y[i + 1], paint);
				} else
				{
					canvas.drawPoint((float) x[num], (float) y[num], paint);
				}
				for (int i = 0; i < x.length; i++)
				{
					x[i] = 0f;
					y[i] = 0f;

				}
				num = 0;
				for (int i = 0; i < x.length; i++)
				{
					x[i] = 0f;
					y[i] = 0f;

				}
				if (points.size() > 3)
				{	
					stroke.setStrokeID(StrokeOrderID);
					stroke.setPoints(points);
					strokes.add(stroke);
					StrokeOrderID++;
				}
				invalidate();	
				points = new ArrayList<Point>();
				stroke = new Stroke();
				tempStrokes.clear();
			}
			if(flag==1){
				mPath.lineTo(event.getX(), event.getY());
				
				if (points.size() > 3)
				{	
					stroke.setStrokeID(StrokeOrderID);
					stroke.setPoints(points);
					strokes.add(stroke);
					StrokeOrderID++;
				}
				invalidate();	
				points = new ArrayList<Point>();
				stroke = new Stroke();
				tempStrokes.clear();
				num = 0;
			}
			adapter.notifyDataSetChanged();
		
		}
		mLastX = event.getX();
		mLastY = event.getY();
		return true;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public void setPaint(int strokewidth) {
		border=strokewidth;
		tBorder=strokewidth;
		fBorder=strokewidth;
		invalidate();
	}

	public void setPaint(int strokewidth,int resBackgroundId,int type) {
		updatePaint(type);
		this.type=type;
		border=strokewidth;
		tBorder=strokewidth;
		fBorder=strokewidth;
		this.resBackgroundId = resBackgroundId;
		background = BitmapUtil.decodeSampledBitmapFromResource(context.getResources(),resBackgroundId, width, width);		
		canvas.setBitmap(background);
		invalidate();

	}

	/**
	 * 删除指定笔画
	 * @return
	 */
	public int removeCertainStroke(int index)
	{

		if (strokes.isEmpty() || strokes.size() == 0)
		{
			return 1;
		}

		tempStrokes.add(strokes.get(index));
		strokes.remove(index);

		drawSubBlack(strokes);

		invalidate();

		return 0;
	}

	/**
	 * 删除上一笔画
	 * @return
	 */
	public int removeOneStroke()
	{

		if (strokes.isEmpty() || strokes.size() == 0)
		{
			return 1;
		}

		tempStrokes.add(strokes.get(strokes.size() - 1));
		strokes.remove(strokes.size() - 1);

		drawSubBlack(strokes);

		invalidate();

		return 0;
	}

	/**
	 * 增加一笔画
	 * @return
	 */
	public int addOneStroke()
	{

		// 还未书写
		if ((strokes.isEmpty() || strokes.size() == 0) && tempStrokes.isEmpty() && tempStrokes.size() == 0)
		{
			return 1;
		}

		// 已经到了可回显的最后一笔
		if (tempStrokes.isEmpty() || tempStrokes.size() == 0)
		{
			drawSubBlack(strokes);
			invalidate();
			return 2;
		} else
		{
			int nowPosition = tempStrokes.size() - 1;
			strokes.add(tempStrokes.get(nowPosition));
			tempStrokes.remove(nowPosition);

		}

		drawSubBlack(strokes);
		invalidate();

		return 0;
	}

	@Override
	public void onDraw(Canvas c)
	{
		c.drawBitmap(background, 0, 0, null);
		c.drawBitmap(transpbitmap, 0,0, null);
		switch(show_method){
		case dianTiShi:
			if (p_times > 0 && strokes.size()<expertStrokes.size())
			{
				Point p = expertStrokes.get(strokes.size())
						.getPoints().get(0);
				c.drawPoint((float) p.getX(), (float) p.getY(), redPaint);

				p_on = 6;
				p_times--;
			} else if (p_on > 0)
			{
				p_on--;
			} else if (p_end > 0)
			{
				p_times = 6;
				p_end--;
			}

			break;
		}
		if(flag==1){
			p.setStrokeWidth(border);
			canvas.drawPath(mPath, p);
		}
	}


	public void setExpertStroke(ArrayList<Stroke> expertStrokes){
		this.expertStrokes=expertStrokes;
	}

	public void drawDianTiShi(int size,int flag){

		if (p_times > 0 && strokes.size()<expertStrokes.size())
		{
			Point p = expertStrokes.get(strokes.size())
					.getPoints().get(0);
			canvas.drawPoint((float) p.getX(), (float) p.getY(), redPaint);

			p_on = 6;
			p_times--;
		} else if (p_on > 0)
		{
			p_on--;
		} else if (p_end > 0)
		{
			p_times = 6;
			p_end--;
		}
		set_method(flag);
		invalidate();
	}


	public void resert()
	{
		if (show_method == dianTiShi)
		{
			p_times = 6; // 闪的次数
			p_on = 6; // 闪的持续
			p_end = 6; // 闪的持续
		}
	}	

	public void set_method(int i)
	{
		this.show_method = i;
	}

	public int get_method()
	{
		return show_method;
	}


	public void screenClear()
	{
		strokes.clear();
		tempStrokes.clear();
//		int resBackgroundId=R.drawable.tianzige;
		background = BitmapUtil.decodeSampledBitmapFromResource(context.getResources(),resBackgroundId, width, width);		
		transpbitmap = Bitmap.createBitmap(width, width,Bitmap.Config.ARGB_4444);

		canvas = new Canvas();
		canvas.setBitmap(background);

		transpcanvas = new Canvas();
		transpcanvas.setBitmap(transpbitmap);
		mPath =new Path();
		invalidate();
	}


	public void screenOnlyClear()
	{
		background = BitmapUtil.decodeSampledBitmapFromResource(context.getResources(),resBackgroundId, width, width);		
		transpbitmap = Bitmap.createBitmap(width, width,Bitmap.Config.ARGB_4444);

		canvas = new Canvas();
		canvas.setBitmap(background);

		transpcanvas = new Canvas();
		transpcanvas.setBitmap(transpbitmap);

		invalidate();
	}


	public void getBitmap(int resBackgroundId){
		//压缩，用于节省BITMAP内存空间--解决BUG的关键步骤
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inSampleSize = 2; //这个的值压缩的倍数（2的整数倍），数值越小，压缩率越小，图片越清晰
		// 获取资源图片
		InputStream is = context.getResources().openRawResource(resBackgroundId);
		//返回原图解码之后的bitmap对象
		bitmap = BitmapFactory.decodeStream(is, null, opts);
	}

	public void screenRedClear(ArrayList<Stroke> red)
	{
		strokes.clear();
		background = BitmapUtil.decodeSampledBitmapFromResource(context.getResources(),resBackgroundId, width, width);		
		transpbitmap = Bitmap.createBitmap(width, width,Bitmap.Config.ARGB_4444);

		canvas = new Canvas();
		canvas.setBitmap(background);

		transpcanvas = new Canvas();
		transpcanvas.setBitmap(transpbitmap);

		//resert();

		drawExpertRed(red);
		invalidate();
	}

	public void screenRedClearOnly(ArrayList<Stroke> red)
	{
		background = BitmapUtil.decodeSampledBitmapFromResource(context.getResources(),resBackgroundId, width, width);		
		transpbitmap = Bitmap.createBitmap(width, width,Bitmap.Config.ARGB_4444);

		canvas = new Canvas();
		canvas.setBitmap(background);

		transpcanvas = new Canvas();
		transpcanvas.setBitmap(transpbitmap);

		Paint pPaint = vu.newPaint(Color.RED, 10);
		pPaint.setARGB(255, 255, 104, 104);
		for (Stroke aPoints : red)
		{
			vu.DrawStroke(canvas, pPaint, tBorder, aPoints.getPoints(), width);
		}
		invalidate();
	}

	public void screenClearOnly()
	{
		background = BitmapUtil.decodeSampledBitmapFromResource(context.getResources(),resBackgroundId, width, width);		
		transpbitmap = Bitmap.createBitmap(width, width,Bitmap.Config.ARGB_4444);

		canvas = new Canvas();
		canvas.setBitmap(background);

		transpcanvas = new Canvas();
		transpcanvas.setBitmap(transpbitmap);

		mPath = new Path();
		//resert();
		invalidate();
	}

	public List<Stroke> getStrokes()
	{
		return strokes;
	}

	public void setStrokes(List<Stroke> strokes) {
		this.strokes = strokes;
	}

	// 笔段的轮廓
	public void drawSubBlack(List<Stroke> newStrokes)
	{
		//Paint pPaint = vu.newPaint(Color.BLACK, 10);
		if(type==0){
			screenClearOnly();
			for (Stroke tstroke : newStrokes)
			{
				vu.DrawStroke(canvas, paint, tBorder, tstroke.getPoints(), width);
			}
		}else{
			Paint p = new Paint();
			p.setAntiAlias(true);
			p.setColor(Color.BLACK);
			p.setStrokeCap(Cap.ROUND);
			p.setStrokeJoin(Join.ROUND);
			p.setStyle(Style.STROKE); //设置画笔为空心  
			p.setStrokeWidth(border);
			p.setStrokeWidth(border);
			
			for(int j=0;j<newStrokes.size();j++){
				
				
				for(int n=0;n<newStrokes.get(j).getPoints().size()-1;n++){
//					mPath.lineTo((float)newStrokes.get(j).getPoints().get(newStrokes.get(j).getPoints().size()-1).getX(),(float)newStrokes.get(j).getPoints().get(newStrokes.get(j).getPoints().size()-1).getY());
					canvas.drawLine(
							(float)newStrokes.get(j).getPoints().get(n).getX(),
							(float)newStrokes.get(j).getPoints().get(n).getY(),
							(float)newStrokes.get(j).getPoints().get(n+1).getX(), 
							(float)newStrokes.get(j).getPoints().get(n+1).getY(), 
							p);
				}
				
				
				
			}
		}
		
	}

	public void drawCorner(ArrayList<ArrayList<Point>> ap)
	{
		this.mylastStroke = ap;
	}

	// 续笔后复显
	public void drawXubi(List<Stroke> newStrokes)
	{
		screenClearOnly();
		for (Stroke s : newStrokes)
		{
			List<Point> points = s.getPoints();
			Point fPoint = points.get(0);
			for (int i = 1; i < points.size(); i++)
			{
				Point p = points.get(i);
				canvas.drawLine((float) fPoint.getX(), (float) fPoint.getY(), (float) p.getX(), (float) p.getY(), paint);// 画线
				fPoint = p;
			}
		}
	}

	public void drawExpertRed(List<Stroke> severalStroke)
	{
		screenClear();
		Paint pPaint = vu.newPaint(Color.RED, 10);
		pPaint.setARGB(255, 255, 104, 104);
		if (severalStroke != null)
			for (Stroke aPoints : severalStroke)
			{
				vu.DrawStroke(canvas, pPaint, tBorder, aPoints.getPoints(), width);
				adapter.notifyDataSetChanged();
			}
		
	}

	public int getResBackgroundId() {
		return resBackgroundId;
	}

	public void setResBackgroundId(int resBackgroundId) {
		this.resBackgroundId = resBackgroundId;
		//setViewBackground();
	}


	public void destroyBitmap(){
		if(background!=null && background.isRecycled()==false){
			background.recycle();
		}

		if(transpbitmap!=null && transpbitmap.isRecycled()==false){
			transpbitmap.recycle();
		}
	}


	int time = 0, subtime = 0;
	private MyRunable myRunable;
	private Handler handler = new Handler();
	private ViewUtil viewUtil = new ViewUtil();

	public void replay(List<Stroke> Strokes)
	{
		screenOnlyClear();
		myRunable = new MyRunable(Strokes);
		handler.postDelayed(myRunable, 20);
	}

	public void stopReplay()
	{
		screenOnlyClear();
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

			Paint pPaint = viewUtil.newPaint(Color.BLACK, 4);
			viewUtil.reshowSubStroke(canvas, Strokes.get(time).getPoints(), subtime, pPaint, multiple, 0, width);
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
	//更改书写模式
	public void updatePaint(int i){
		flag = i;
		screenClear();
	}

	public GridViewAdapter getAdapter() {
		return adapter;
	}

	public void setAdapter(GridViewAdapter adapter) {
		this.adapter = adapter;
	}
	

}
