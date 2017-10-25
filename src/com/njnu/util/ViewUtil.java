package com.njnu.util;

import java.util.ArrayList;
import java.util.List;

import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Join;

import com.njnu.struct.Point;

public class ViewUtil
{

	private final static int Delay = 5;

	public ViewUtil()
	{
	}

	public float getLength(double fx, double fy, double sx, double sy)
	{
		float temp = (float) Math.sqrt((fx - sx) * (fx - sx) + (fy - sy) * (fy - sy));
		return temp;
	}

	public Paint newPaint(int color, float border)
	{
		Paint p = new Paint();
		//画笔抗锯齿
		p.setAntiAlias(true);
		p.setColor(color);
		//当画笔样式为STROKE或FILL_OR_STROKE时，设置笔刷的图形样式，如圆形样式  
		p.setStrokeCap(Paint.Cap.ROUND);
		p.setStrokeWidth(border);
		//设置绘制时各图形的结合方式，如平滑效果等  
		p.setStrokeJoin(Join.ROUND);
		// BlurMaskFilter BMF = new BlurMaskFilter(3, BlurMaskFilter.Blur.NORMAL);
		// p.setMaskFilter(BMF);

		// paint.setAntiAlias(true);
		// paint.setColor(Color.BLACK);
		// paint.setStrokeCap(Paint.Cap.ROUND);
		// paint.setStrokeWidth(border);
		return p;
	}
	public Paint newPaint(Paint paint, Boolean Flag, float border)
	{
		if (Flag)
		{
			paint.setAntiAlias(true);
			paint.setColor(Color.BLACK);
			paint.setStrokeCap(Paint.Cap.ROUND);
			paint.setStrokeWidth(border);
			paint.setStrokeJoin(Join.ROUND);
			BlurMaskFilter BMF = new BlurMaskFilter(3, BlurMaskFilter.Blur.NORMAL);
		    //设置MaskFilter，可以用不同的MaskFilter实现滤镜的效果，如滤化，立体等       *   
			paint.setMaskFilter(BMF);

		} else
		{
			paint.setAntiAlias(true);
			paint.setColor(Color.BLACK);
			paint.setStrokeCap(Paint.Cap.ROUND);
			paint.setStrokeWidth(border);
		}
		return paint;
	}
	public List<Double> lengthCom(List<Point> sList, List<Point> tList)
	{
		Point s = new Point();
		double l = 0;
		// 标准XML文件处理
		List<Double> slength = new ArrayList<Double>();
		for (Point p : sList)
		{
			if (p.getX() == -1)
				continue;
			else if (p.getX() == -2)
			{
				slength.add(l);
				l = 0;
			} else if (s.getX() > 0)
			{
				l += getLength(s.getX(), s.getY(), p.getX(), p.getY());
			}
			s = p;
		}
		int index = 0;
		double slongStroke = 0;
		for (int d = 0; d < slength.size(); d++)
		{
			if (slongStroke < slength.get(d))
				slongStroke = slength.get(d);
		}
		index = slength.indexOf(slongStroke);
		// System.out.println("longStroke = "+longStroke);
		// System.out.println("index = "+index);
		for (int d = 0; d < slength.size(); d++)
		{
			slength.set(d, slength.get(d) / slongStroke);
			// System.out.println(length.get(d));
		}
		// 用户XML文件处理
		Point t = new Point();
		l = 0;
		List<Double> tlength = new ArrayList<Double>();
		for (Point p : tList)
		{
			if (p.getX() == -1)
				continue;
			else if (p.getX() == -2)
			{
				tlength.add(l);
				l = 0;
			} else if (t.getX() > 0)
			{
				l += getLength(t.getX(), t.getY(), p.getX(), p.getY());
			}
			t = p;
		}
		double tlongStroke = tlength.get(index);
		for (int d = 0; d < tlength.size(); d++)
		{
			tlength.set(d, tlength.get(d) / tlongStroke);
		}
		List<Double> result = new ArrayList<Double>();
		for (int d = 0; d < slength.size(); d++)
		{
			result.add(slength.get(d) - tlength.get(d));
		}
		return result;
	}

	public List<Point> ranPoint(List<Point> originalPoint)
	{
		List<Point> randomPoint = new ArrayList<Point>();
		int strokeNum = 0;
		for (Point mp : originalPoint)
		{
			if (mp.getX() == -1)
				strokeNum++;
		}
		int ranNum = (int) (Math.random() * strokeNum);
		for (Point mp : originalPoint)
		{
			if (mp.getX() == -1)
			{
				if (ranNum == 0)
					continue;
				randomPoint.add(mp);
			} else if (mp.getX() == -2)
			{
				ranNum--;
				if (ranNum == 0)
					continue;
				randomPoint.add(mp);
			} else
			{
				if (ranNum == 0)
					continue;
				randomPoint.add(mp);
			}
		}
		return randomPoint;
	}

	/**
	 * 以List的形式保存一笔画
	 */
	public List<List<Point>> getallStroke(List<Point> mp)
	{
		List<List<Point>> allstroke = new ArrayList<List<Point>>();
		List<Point> stroke = null;
		for (Point p : mp)
		{
			if (p.getX() == -1)
			{
				stroke = new ArrayList<Point>();
			} else if (p.getX() == -2)
			{
				allstroke.add(stroke);
				stroke = null;
			} else
			{
				stroke.add(p);
			}
		}
		return allstroke;
	}

	public int turnNum(List<Point> originalPoint)
	{
		int flag = 0;
		if (originalPoint.get(originalPoint.size() - 1).getX() < 0)
		{
			int size = originalPoint.size() - 2;
			for (int i = 0; i < 5; i++)
			{
				double x1 = originalPoint.get(size - i).getX() - originalPoint.get(size - i - 1).getX();
				double x2 = originalPoint.get(size - i - 1).getX() - originalPoint.get(size - i - 2).getX();
				double y1 = originalPoint.get(size - i).getY() - originalPoint.get(size - i - 1).getY();
				double y2 = originalPoint.get(size - i - 1).getY() - originalPoint.get(size - i - 2).getY();
				if (x1 * x2 < 0 || y1 * y2 < 0)
					flag = i + 2;
			}
		} else
		{
			int size = originalPoint.size() - 1;
			for (int i = 0; i < 5; i++)
			{
				double x1 = originalPoint.get(size - i).getX() - originalPoint.get(size - i - 1).getX();
				double x2 = originalPoint.get(size - i - 1).getX() - originalPoint.get(size - i - 2).getX();
				double y1 = originalPoint.get(size - i).getY() - originalPoint.get(size - i - 1).getY();
				double y2 = originalPoint.get(size - i - 1).getY() - originalPoint.get(size - i - 2).getY();
				if (x1 * x2 < 0 || y1 * y2 < 0)
					flag = i + 2;
			}
		}
		return flag;
	}

	// 最后Delay个点书写效果
	public void DrawLastPoints(Canvas canvas, Paint paint, float tBorder, float fBorder, List<Point> stroke, int screenWidth)
	{
		int size = stroke.size();
		int turn = turnNum(stroke);
		int flag = 1;
		float border = fBorder;
		float tempF = fBorder;
		double x = stroke.get(size - 6).getX();
		double y = stroke.get(size - 6).getY();
		if (turn == 0)
		{
			// Log.i("=0", "````");
			for (int i = size - 5; i < size; i++)
			{
				float temp = timeBorder(x, y, stroke.get(i).getX(), stroke.get(i).getY(), screenWidth) + tBorder;
				if (fBorder > temp || flag >= 2)
				{
					border = fBorder * (size - i - 1) / 5;
					flag++;
				} else if (border < temp)
					border = border + screenWidth / 1000;
				if (border < 2)
					border = 2;
				paint.setStrokeWidth(border);
				// canvas.drawLine((float) x, (float) y,
				// (float) stroke.get(i).getX(), (float) stroke.get(i).getY(),
				// paint);
				DrawLine(canvas, tempF, border, paint, (float) x, (float) y, (float) stroke.get(i).getX(), (float) stroke.get(i).getY());
				tempF = border;
				x = stroke.get(i).getX();
				y = stroke.get(i).getY();
			}
		} else
		{
			// Log.i("!=0", "`````");
			for (int i = stroke.size() - 5; i < stroke.size() - turn; i++)
			{
				float temp = timeBorder(x, y, stroke.get(i).getX(), stroke.get(i).getY(), screenWidth) + tBorder;
				if (fBorder > temp)
					border = fBorder - screenWidth / 250;
				else if (fBorder < temp)
					border = fBorder + screenWidth / 1000;
				if (border < 2)
					border = 2;
				// paint.setStrokeWidth(border);
				// canvas.drawLine((float) x, (float) y,
				// (float) stroke.get(i).getX(), (float) stroke.get(i).getY(),
				// paint);
				DrawLine(canvas, fBorder, border, paint, (float) x, (float) y, (float) stroke.get(i).getX(), (float) stroke.get(i).getY());
				fBorder = border;
				x = stroke.get(i).getX();
				y = stroke.get(i).getY();
			}
			tempF = fBorder;
			for (int i = stroke.size() - turn; i < stroke.size(); i++)
			{
				float temp = timeBorder(x, y, stroke.get(i).getX(), stroke.get(i).getY(), screenWidth) + tBorder;
				if (fBorder > temp)
					border = fBorder * (size - i) / turn;
				else if (fBorder < temp)
					border = border + screenWidth / 1000;
				else
					border = fBorder;
				if (border < 2)
					border = 2;
				// paint.setStrokeWidth(border);
				// canvas.drawLine((float) x, (float) y,
				// (float) stroke.get(i).getX(), (float) stroke.get(i).getY(),
				// paint);
				DrawLine(canvas, tempF, border, paint, (float) x, (float) y, (float) stroke.get(i).getX(), (float) stroke.get(i).getY());
				tempF = border;
				x = stroke.get(i).getX();
				y = stroke.get(i).getY();
			}
		}
	}

	// 回显一笔
	public void DrawStroke(Canvas canvas, Paint paint, float tBorder, List<Point> stroke, int screenWidth)
	{
		int size = stroke.size();
		if (size > 0)
		{
			double x = stroke.get(0).getX();
			double y = stroke.get(0).getY();
			float border = 1;
			float fBorder = 1;
			if (size > Delay + 1)
			{
				if (size <= 2 * Delay)
				{
					for (int i = 1; i < size - Delay; i++)
					{
						float temp = timeBorder(x, y, stroke.get(i).getX(), stroke.get(i).getY(), screenWidth) + tBorder;
						if (fBorder > temp)
							border = fBorder - screenWidth / 250f;
						else if (fBorder < temp)
							border = fBorder + (temp - fBorder) / Delay;
						else
							border = fBorder;

						if (border < 2)
							border = 2;
						paint.setStrokeWidth(border);
						// Log.i("21", "border=" + border + ";fBorder=" + fBorder);
						canvas.drawLine((float) x, (float) y, (float) stroke.get(i).getX(), (float) stroke.get(i).getY(), paint);
						fBorder = border;
						x = stroke.get(i).getX();
						y = stroke.get(i).getY();
					}
					DrawLastPoints(canvas, paint, tBorder, fBorder, stroke, screenWidth);
				} else
				{
					for (int i = 1; i <= Delay + 1; i++)
					{
						float temp = timeBorder(x, y, stroke.get(i).getX(), stroke.get(i).getY(), screenWidth) + tBorder;
						if (fBorder > temp)
							border = fBorder - screenWidth / 250f;
						else if (fBorder < temp)
							border = fBorder + (temp - fBorder) / Delay;
						else
							border = fBorder;
						if (border < 2)
							border = 2;
						// Log.i("22", "border=" + border + ";fBorder=" + fBorder);
						paint.setStrokeWidth(border);
						canvas.drawLine((float) x, (float) y, (float) stroke.get(i).getX(), (float) stroke.get(i).getY(), paint);
						fBorder = border;
						x = stroke.get(i).getX();
						y = stroke.get(i).getY();
					}
					for (int i = Delay + 2; i < size - Delay; i++)
					{
						float temp = timeBorder(x, y, stroke.get(i).getX(), stroke.get(i).getY(), screenWidth) + tBorder;
						if (fBorder > temp)
							border = fBorder - screenWidth / 250f;
						else if (fBorder < temp)
							border = fBorder + screenWidth / 1000f;
						else
							border = fBorder;
						if (border < 2)
							border = 2;
						// Log.i("23", "border=" + border + ";fBorder=" + fBorder);
						paint.setStrokeWidth(border);
						canvas.drawLine((float) x, (float) y, (float) stroke.get(i).getX(), (float) stroke.get(i).getY(), paint);
						fBorder = border;
						x = stroke.get(i).getX();
						y = stroke.get(i).getY();
					}
					DrawLastPoints(canvas, paint, tBorder, fBorder, stroke, screenWidth);
				}
			} else if (size > 1)
			{
				for (int i = 1; i < size; i++)
				{
					canvas.drawLine((float) x, (float) y, (float) stroke.get(i).getX(), (float) stroke.get(i).getY(), paint);
					x = stroke.get(i).getX();
					y = stroke.get(i).getY();
				}
			} else
			{
				canvas.drawPoint((float) x, (float) y, paint);
			}
		}
	}

	public void reshowSubStroke(Canvas canvas, List<Point> stroke, int subtime, Paint paint, double multiple, double leftheight, int screenWidth)
	{
		int size = stroke.size();
		if (size > 0)
		{
			double x = stroke.get(0).getX();
			double y = stroke.get(0).getY();
			float border = 1;
			float fBorder = 1;
			if (size > Delay + 1)
			{
				if (size <= 2 * Delay)
				{
					for (int i = 1; i < size - Delay; i++)
					{
						float temp = timeBorder(x, y, stroke.get(i).getX(), stroke.get(i).getY(), screenWidth);
						if (fBorder > temp)
							border = fBorder - screenWidth / 250f;
						else if (fBorder < temp)
							border = fBorder + (temp - fBorder) / Delay;
						else
							border = fBorder;

						if (border < 2)
							border = 2;
						paint.setStrokeWidth(border);
						// Log.i("21", "border=" + border + ";fBorder=" + fBorder);
						if (i == subtime + 1)
						{
							canvas.drawLine((float) (x * multiple + leftheight), (float) (y * multiple + leftheight), (float) (stroke.get(i).getX() * multiple + leftheight), (float) (stroke.get(i)
									.getY() * multiple + leftheight), paint);
							return;
						}
						// canvas.drawLine((float) x, (float) y, (float) stroke.get(i).getX(), (float) stroke.get(i).getY(), paint);
						fBorder = border;
						x = stroke.get(i).getX();
						y = stroke.get(i).getY();
					}
					redrawLastPoints(canvas, stroke, subtime, paint, multiple, leftheight, screenWidth, fBorder);
				} else
				{
					for (int i = 1; i <= Delay + 1; i++)
					{
						float temp = timeBorder(x, y, stroke.get(i).getX(), stroke.get(i).getY(), screenWidth);
						if (fBorder > temp)
							border = fBorder - screenWidth / 250f;
						else if (fBorder < temp)
							border = fBorder + (temp - fBorder) / Delay;
						else
							border = fBorder;
						if (border < 2)
							border = 2;
						// Log.i("22", "border=" + border + ";fBorder=" + fBorder);
						paint.setStrokeWidth(border);
						if (i == subtime + 1)
						{
							canvas.drawLine((float) (x * multiple + leftheight), (float) (y * multiple + leftheight), (float) (stroke.get(i).getX() * multiple + leftheight), (float) (stroke.get(i)
									.getY() * multiple + leftheight), paint);
							return;
						}
						// canvas.drawLine((float) x, (float) y, (float) stroke.get(i).getX(), (float) stroke.get(i).getY(), paint);
						fBorder = border;
						x = stroke.get(i).getX();
						y = stroke.get(i).getY();
					}
					for (int i = Delay + 2; i < size - Delay; i++)
					{
						float temp = timeBorder(x, y, stroke.get(i).getX(), stroke.get(i).getY(), screenWidth);
						if (fBorder > temp)
							border = fBorder - screenWidth / 250f;
						else if (fBorder < temp)
							border = fBorder + screenWidth / 1000f;
						else
							border = fBorder;
						if (border < 2)
							border = 2;
						// Log.i("23", "border=" + border + ";fBorder=" + fBorder);
						paint.setStrokeWidth(border);
						if (i == subtime + 1)
						{
							canvas.drawLine((float) (x * multiple + leftheight), (float) (y * multiple + leftheight), (float) (stroke.get(i).getX() * multiple + leftheight), (float) (stroke.get(i)
									.getY() * multiple + leftheight), paint);
							return;
						}
						// canvas.drawLine((float) x, (float) y, (float) stroke.get(i).getX(), (float) stroke.get(i).getY(), paint);
						fBorder = border;
						x = stroke.get(i).getX();
						y = stroke.get(i).getY();
					}
					redrawLastPoints(canvas, stroke, subtime, paint, multiple, leftheight, screenWidth, fBorder);
				}
			} else if (size > 1)
			{
				for (int i = 1; i < size; i++)
				{
					canvas.drawLine((float) x, (float) y, (float) stroke.get(i).getX(), (float) stroke.get(i).getY(), paint);
					x = stroke.get(i).getX();
					y = stroke.get(i).getY();
				}
			} else
			{
				canvas.drawPoint((float) x, (float) y, paint);
			}
		}
	}

	// 最后Delay个点书写效果
	public void redrawLastPoints(Canvas canvas, List<Point> stroke, int subtime, Paint paint, double multiple, double leftheight, int screenWidth, float fBorder)
	{
		int size = stroke.size();
		int turn = turnNum(stroke);
		int flag = 1;
		float border = fBorder;
		float tempF = fBorder;
		double x = stroke.get(size - 6).getX();
		double y = stroke.get(size - 6).getY();
		if (turn == 0)
		{
			// Log.i("=0", "````");
			for (int i = size - 5; i < size; i++)
			{
				float temp = timeBorder(x, y, stroke.get(i).getX(), stroke.get(i).getY(), screenWidth);
				if (fBorder > temp || flag >= 2)
				{
					border = fBorder * (size - i - 1) / 5;
					flag++;
				} else if (border < temp)
					border = border + screenWidth / 1000;
				if (border < 2)
					border = 2;
				paint.setStrokeWidth(border);
				// canvas.drawLine((float) x, (float) y,
				// (float) stroke.get(i).getX(), (float) stroke.get(i).getY(),
				// paint);
				if (i == subtime + 1)
				{
					DrawLine(canvas, tempF, border, paint, (float) (x * multiple + leftheight), (float) (y * multiple + leftheight), (float) (stroke.get(i).getX() * multiple + leftheight),
							(float) (stroke.get(i).getY() * multiple + leftheight));
				}
//					canvas.drawLine((float) (x * multiple + leftheight), (float) (y * multiple + leftheight), (float) (stroke.get(i).getX() * multiple + leftheight), (float) (stroke.get(i).getY()
//							* multiple + leftheight), paint);
				DrawLine(canvas, tempF, border, paint, (float) x, (float) y, (float) stroke.get(i).getX(), (float) stroke.get(i).getY());
				tempF = border;
				x = stroke.get(i).getX();
				y = stroke.get(i).getY();
			}
		} else
		{
			// Log.i("!=0", "`````");
			for (int i = stroke.size() - 5; i < stroke.size() - turn; i++)
			{
				float temp = timeBorder(x, y, stroke.get(i).getX(), stroke.get(i).getY(), screenWidth);
				if (fBorder > temp)
					border = fBorder - screenWidth / 250;
				else if (fBorder < temp)
					border = fBorder + screenWidth / 1000;
				if (border < 2)
					border = 2;
				// paint.setStrokeWidth(border);
				// canvas.drawLine((float) x, (float) y,
				// (float) stroke.get(i).getX(), (float) stroke.get(i).getY(),
				// paint);
				if (i == subtime + 1)
				{
					DrawLine(canvas, tempF, border, paint, (float) (x * multiple + leftheight), (float) (y * multiple + leftheight), (float) (stroke.get(i).getX() * multiple + leftheight),
							(float) (stroke.get(i).getY() * multiple + leftheight));
				}
//				DrawLine(canvas, fBorder, border, paint, (float) x, (float) y, (float) stroke.get(i).getX(), (float) stroke.get(i).getY());
				fBorder = border;
				x = stroke.get(i).getX();
				y = stroke.get(i).getY();
			}
			tempF = fBorder;
			for (int i = stroke.size() - turn; i < stroke.size(); i++)
			{
				float temp = timeBorder(x, y, stroke.get(i).getX(), stroke.get(i).getY(), screenWidth);
				if (fBorder > temp)
					border = fBorder * (size - i) / turn;
				else if (fBorder < temp)
					border = border + screenWidth / 1000;
				else
					border = fBorder;
				if (border < 2)
					border = 2;
				// paint.setStrokeWidth(border);
				// canvas.drawLine((float) x, (float) y,
				// (float) stroke.get(i).getX(), (float) stroke.get(i).getY(),
				// paint);
				if (i == subtime + 1)
				{
					DrawLine(canvas, tempF, border, paint, (float) (x * multiple + leftheight), (float) (y * multiple + leftheight), (float) (stroke.get(i).getX() * multiple + leftheight),
							(float) (stroke.get(i).getY() * multiple + leftheight));
				}
//				DrawLine(canvas, tempF, border, paint, (float) x, (float) y, (float) stroke.get(i).getX(), (float) stroke.get(i).getY());
				tempF = border;
				x = stroke.get(i).getX();
				y = stroke.get(i).getY();
			}
		}
	}

	// 粗细控制
	public float timeBorder(double fx, double fy, double sx, double sy, int screenWidth)
	{
		double MAX_LENGTH = screenWidth / 28;
		double l = (double) Math.sqrt((fx - sx) * (fx - sx) + (fy - sy) * (fy - sy));
		float temp = 2;
		if (l > MAX_LENGTH)
		{
			temp = screenWidth / 250;
		} else
		{
			temp = (int) (MAX_LENGTH - l);
		}
		return temp;
	}

	public float DrawLine(Canvas canvas, float fBorder, float border, Paint paint, float pointX_X, float pointX_Y, float pointY_X, float pointY_Y)
	{
		// if (border < 2)
		// border = 2;
		float sBorder = 0;
		int D_value = Math.abs((int) (fBorder - border));
		if (D_value >= 2)
		{
			float add_border = (border - fBorder) / D_value;
			float add_x = (pointY_X - pointX_X) / D_value;
			float add_y = (pointY_Y - pointX_Y) / D_value;
			float startX = pointX_X;
			float startY = pointX_Y;
			sBorder = fBorder + add_border;
			for (; D_value > 0; D_value--)
			{
				float stopX = startX + add_x;
				float stopY = startY + add_y;
				paint.setStrokeWidth(sBorder);
				// Log.i("stroke", "" + sBorder);
				canvas.drawLine(startX, startY, stopX, stopY, paint);
				sBorder += add_border;
				startX = stopX;
				startY = stopY;
			}
		} else
		{
			canvas.drawLine(pointX_X, pointX_Y, pointY_X, pointY_Y, paint);
		}
		return sBorder;
	}

	public void DrawSplitStroke(Canvas canvas, Paint paint, float tBorder, List<Point> stroke, int screenWidth, int id)
	{
		int size = stroke.size();
		if (size > 0)
		{
			double x = stroke.get(0).getX();
			double y = stroke.get(0).getY();
			float border = 1;
			float fBorder = 1;
			if (size > Delay)
			{
				if (size <= 2 * Delay)
				{
					for (int i = 1; i < size - Delay; i++)
					{
						if (i == id)
							return;
						float temp = timeBorder(x, y, stroke.get(i).getX(), stroke.get(i).getY(), screenWidth) + tBorder;
						if (fBorder > temp)
							border = fBorder - screenWidth / 250f;
						else if (fBorder < temp)
							border = fBorder + (temp - fBorder) / Delay;
						else
							border = fBorder;

						if (border < 2)
							border = 2;
						paint.setStrokeWidth(border);
						canvas.drawLine((float) x, (float) y, (float) stroke.get(i).getX(), (float) stroke.get(i).getY(), paint);
						fBorder = border;
						x = stroke.get(i).getX();
						y = stroke.get(i).getY();
					}
					DrawLastPoints(canvas, paint, tBorder, fBorder, stroke, screenWidth);
				} else
				{
					for (int i = 1; i <= Delay + 1; i++)
					{
						if (i == id)
							return;
						float temp = timeBorder(x, y, stroke.get(i).getX(), stroke.get(i).getY(), screenWidth) + tBorder;
						if (fBorder > temp)
							border = fBorder - screenWidth / 250f;
						else if (fBorder < temp)
							border = fBorder + (temp - fBorder) / Delay;
						else
							border = fBorder;
						if (border < 2)
							border = 2;
						paint.setStrokeWidth(border);
						canvas.drawLine((float) x, (float) y, (float) stroke.get(i).getX(), (float) stroke.get(i).getY(), paint);
						fBorder = border;
						x = stroke.get(i).getX();
						y = stroke.get(i).getY();
					}
					for (int i = Delay + 2; i < size - Delay; i++)
					{
						if (i == id)
							return;
						float temp = timeBorder(x, y, stroke.get(i).getX(), stroke.get(i).getY(), screenWidth) + tBorder;
						if (fBorder > temp)
							border = fBorder - screenWidth / 250f;
						else if (fBorder < temp)
							border = fBorder + screenWidth / 1000f;
						else
							border = fBorder;
						if (border < 2)
							border = 2;
						paint.setStrokeWidth(border);
						canvas.drawLine((float) x, (float) y, (float) stroke.get(i).getX(), (float) stroke.get(i).getY(), paint);
						fBorder = border;
						x = stroke.get(i).getX();
						y = stroke.get(i).getY();
					}
					DrawLastPoints(canvas, paint, tBorder, fBorder, stroke, screenWidth);
				}
			} else if (size > 1)
			{
				for (int i = 1; i < size; i++)
				{
					if (i == id)
						break;
					canvas.drawLine((float) x, (float) y, (float) stroke.get(i).getX(), (float) stroke.get(i).getY(), paint);
					x = stroke.get(i).getX();
					y = stroke.get(i).getY();
				}
			} else
			{
				canvas.drawPoint((float) x, (float) y, paint);
			}
		}
	}

}
