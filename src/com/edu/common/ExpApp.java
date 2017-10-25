package com.edu.common;

import org.xutils.x;

import android.app.Application;

public class ExpApp extends Application {
private  String  stuSchool=null;
private  String stuGrade=null;
private  String stuClass=null;
private  String stuNum=null;
private  String stuName=null;
private  String stuGender=null;
private String Mode =null;

    public void onCreate() {  
    // TODO Auto-generated method stub  
        super.onCreate();  
        x.Ext.init(this);//Xutils≥ı ºªØ
    } 
    
    public void setStuSchool(String school){
    	this.stuSchool=school;
    }
    public  String getStuSchool() {
	    return stuSchool;
    }
    
    public void setStuGrade(String grade){
    	this.stuGrade=grade;
    }
    public  String getStuGrade() {
	    return stuGrade;
    }
    
    public void setStuClass(String stuClass){
    	this.stuClass=stuClass;
    }
    public  String getStuClass() {
	    return stuClass;
    }
    
    public void setStuNum(String StuNum){
    	this.stuNum=StuNum;
    }
    public  String getStuNum() {
	    return stuNum;
    }
    
    public void setStuName(String StuName){
    	this.stuName=StuName;
    }
    public  String getStuName() {
	    return stuName;
    }
    
    public void setStuGender(String StuGender){
    	this.stuGender=StuGender;
    }
    public  String getStuGender() {
	    return stuGender;
    }

	public String getMode() {
		return Mode;
	}

	public void setMode(String mode) {
		Mode = mode;
	}
    
    
    

}
