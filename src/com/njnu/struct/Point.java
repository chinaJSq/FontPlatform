package com.njnu.struct;

import java.io.Serializable;

public class Point implements Serializable{
	private static final long serialVersionUID = 1L;
	private int id;
	public double x;
	public double y;
	private double arCos;   //与P0点的角度  
	public long time;
	public Point(double dw, double dh) {
		// TODO Auto-generated constructor stub
		this.x = dw;
		this.y = dh;
	}
	public Point() {
		// TODO Auto-generated constructor stu
	}
	public int getId(){
		return id;
	}
	public void setId(int id){
		this.id=id;
	}
	public double getX(){
		return x;
	}
	public void setX(double x){
		this.x=x;
	}
	public double getY(){
		return y;
	}
	public void setY(double y){
		this.y=y;
	}
	public long getTime(){
		return time;
	}
	public void setTime(long time){
		this.time=time;
	}
	
	public double getArCos() {  
        return arCos;  
    }  
    public void setArCos(double arCos) {  
        this.arCos = arCos;  
    }  
	
	
}
