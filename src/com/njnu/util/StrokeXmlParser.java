package com.njnu.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.njnu.struct.Point;
import com.njnu.struct.Stroke;

import android.util.Xml;

public class StrokeXmlParser {

	private static final String ns = null;
	private InputStream input = null;
	
	public StrokeXmlParser(InputStream input) 
	{
		this.input=input;

	}
	
	public String getUserInfo() throws XmlPullParserException, IOException{
		String info="";
		try
		{
			XmlPullParser parser = Xml.newPullParser();
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false); 
			parser.setInput(input, null);
			parser.nextTag();
			
			parser.require(XmlPullParser.START_TAG, ns, "property");
			while (parser.next() != XmlPullParser.END_TAG)
			{
				if (parser.getEventType() != XmlPullParser.START_TAG)
				{
					continue;
				}
				String name = parser.getName();
				if (name.equals("userInfo"))
				{
					info=readText(parser);
				} else
				{
					skip(parser);
				}
			}
				
			return info;			
		}catch(Exception e){
			return "";
		}finally{
			input.close();
		}	
		
	}
	
	public List<Stroke> getStrokes() throws XmlPullParserException, IOException{
		List<Stroke> strokes=new ArrayList<Stroke>();//返回Stroke集合
		String info="";
		try
		{
			XmlPullParser parser = Xml.newPullParser(); //创建�?个xml 的解析器
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false); //指定命名空间 
			parser.setInput(input, null);
			parser.nextTag();
			
			parser.require(XmlPullParser.START_TAG, ns, "property");
			while (parser.next() != XmlPullParser.END_TAG)
			{
				if (parser.getEventType() != XmlPullParser.START_TAG)
				{
					continue;
				}
				String name = parser.getName();
				if (name.equals("userInfo"))
				{
					info=readText(parser);
				}else if (name.equals("stroke"))
				{
					strokes.add(readStroke(parser));
				} else
				{
					skip(parser);
				}
			}
			
			return strokes;
			
		}finally{
			input.close();
		}	
		
	}
	
	
	private Stroke readStroke(XmlPullParser parser) throws XmlPullParserException, IOException{
		
		Stroke stroke=new Stroke();
		ArrayList<Point> points=new ArrayList<Point>();
		
		parser.require(XmlPullParser.START_TAG, ns, "stroke");
		String strokeID = parser.getAttributeValue(null, "strokeOrderID"); 
		stroke.setStrokeID(Integer.parseInt(strokeID));
		while (parser.next() != XmlPullParser.END_TAG)
		{
			if (parser.getEventType() != XmlPullParser.START_TAG)
			{
				continue;
			}
			
			String name = parser.getName();
			if (name.equals("point"))
			{
				points.add(readPoint(parser));
			} else
			{
				skip(parser);
			}
		}	
		
		stroke.setPoints(points);
		
		return stroke;
	}
	
	
	private Point readPoint(XmlPullParser parser) throws XmlPullParserException, IOException{

		Point point=new Point();
		double x=0.0;
		double y=0.0;
		long time=0;
		parser.require(XmlPullParser.START_TAG, ns, "point");

		while (parser.next() != XmlPullParser.END_TAG)
		{
			if (parser.getEventType() != XmlPullParser.START_TAG)
			{
				continue;
			}

			String name = parser.getName();
			if (name.equals("x"))
			{
				x=Double.parseDouble(readText(parser));
			}else if(name.equals("y"))
			{
				y=Double.parseDouble(readText(parser));
			}else if(name.equals("time"))
			{
				time=Long.parseLong(readText(parser));
			}else
			{
				skip(parser);
			}
		}	
		
		point.setX(x);point.setY(y);point.setTime(time);
		
		return point;
	}
	
	
	private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
	    String result = "";
	    if (parser.next() == XmlPullParser.TEXT) 
	    { 
	        result = parser.getText(); //读取文本内容
	        parser.nextTag();
	    }
	    return result;
	}
	
	
	private void skip(XmlPullParser parser) throws XmlPullParserException, IOException
	{
		if (parser.getEventType() != XmlPullParser.START_TAG)
		{
			throw new IllegalStateException();
		}
		int depth = 1;
		while (depth != 0)
		{
			switch (parser.next())
			{
			case XmlPullParser.END_TAG:
				depth--;
				break;
			case XmlPullParser.START_TAG:
				depth++;
				break;
			}
		}
	}

}
