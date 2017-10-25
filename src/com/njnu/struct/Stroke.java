package com.njnu.struct;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Stroke implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String strokeCode; // ������
	private ArrayList<SubStroke> substrokes = new ArrayList<SubStroke>(); // �ʻ��������ıʶμ���
	private ArrayList<Point> simple_points = new ArrayList<Point>(); // ���㼯
	private Point centerPoint;// ���ĵ�
	private Boolean isShortStroke = false;

	private int strokeID; // �ʻ�ID
	private int position; // �ʻ���ʮ�������λ�� 11-44
	private ArrayList<String> strings = new ArrayList<String>(); // ����������Ϣ
	private List<Point> points = new ArrayList<Point>(); // ԭʼ�㼯

	public Boolean getIsShortStroke() {
		return isShortStroke;
	}

	public void setIsShortStroke(Boolean isShortStroke) {
		this.isShortStroke = isShortStroke;
	}

	public ArrayList<SubStroke> getSubstrokes() {
		return substrokes;
	}

	public void setSubstrokes(ArrayList<SubStroke> substrokes) {
		this.substrokes = substrokes;
	}

	public Point getCenterPoint() {
		return centerPoint;
	}

	public void setCenterPoint(Point centerPoint) {
		this.centerPoint = centerPoint;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public ArrayList<String> getStrings() {
		return strings;
	}

	public void setStrings(ArrayList<String> strings) {
		this.strings = strings;
	}

	public String getStrokeCode() {
		return strokeCode;
	}

	public void setStrokeCode(String strokeCode) {
		this.strokeCode = strokeCode;
	}

	public List<Point> getPoints() {
		return points;
	}

	public void setPoints(List<Point> points) {
		this.points = points;
	}

	public int getStrokeID() {
		return strokeID;
	}

	public void setStrokeID(int strokeID) {
		this.strokeID = strokeID;
	}

	public ArrayList<Point> getSimple_points() {
		return simple_points;
	}

	public void setSimple_points(ArrayList<Point> simple_points) {
		this.simple_points = simple_points;
	}

	public void removeLastSub() {
		if (substrokes.size() > 1 && simple_points.size() > 2) {
			substrokes.remove(substrokes.size() - 1);
			simple_points.remove(simple_points.size() - 1);
		}
	}
}