package com.njnu.struct;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Godric on 2014/11/22.
 */
public class SubStroke implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Point> subPoints = new ArrayList<Point>(); // 笔段包括的点集
	private ArrayList<Point> trimPoints = new ArrayList<Point>(); // 严格拉直后的点集
	private int subCode;
	private int subOrderID;

	public SubStroke()
	{
		super();
	}

	public SubStroke(List<Point> subPoints)
	{
		super();
		this.subPoints = subPoints;
	}

	public int getSubCode()
	{
		return subCode;
	}

	public void setSubCode(int subCode)
	{
		this.subCode = subCode;
	}

	public int getSubOrderID()
	{
		return subOrderID;
	}

	public void setSubOrderID(int subOrderID)
	{
		this.subOrderID = subOrderID;
	}

	public List<Point> getSubPoints()
	{
		return subPoints;
	}

	public void setSubPoints(List<Point> subPoints)
	{
		this.subPoints = subPoints;
	}

	public void addPoint(Point point)
	{
		subPoints.add(point);
	}

	public Point getStartPoint()
	{
		return subPoints.get(0);
	}

	public Point getEndPoint()
	{
		return subPoints.get(subPoints.size() - 1);
	}

	public ArrayList<Point> getTrimPoints()
	{
		return trimPoints;
	}

	public void setTrimPoints(ArrayList<Point> trimPoints)
	{
		this.trimPoints = trimPoints;
	}

	public void combine(SubStroke sub)
	{
		List<Point> points = sub.getSubPoints();
		if (points.size() != 0)
			points.remove(points.size() - 1);
		subPoints.addAll(points);
	}

	public static SubStroke combine(SubStroke fSub, SubStroke lSub)
	{
		SubStroke sub = new SubStroke();
		List<Point> points = fSub.getSubPoints();
		if (points.size() != 0)
			points.remove(points.size() - 1);

		points.addAll(lSub.getSubPoints());
		sub.setSubPoints(points);
		return sub;
	}
}