package com.njnu.util;

import java.io.OutputStream;
import java.util.List;

import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import com.njnu.struct.Stroke;


public class SaveToXML {
	private OutputStream output=null;
	private List<Stroke> saveStrokes=null;

	public SaveToXML(OutputStream output,List<Stroke> saveStrokes) 
	{
		this.output=output;
		this.saveStrokes=saveStrokes;
	}	
	
	public void saveStrokes(String userInfo) throws Exception
	{
		XmlPullParserFactory factory=XmlPullParserFactory.newInstance();
		XmlSerializer xs = factory.newSerializer();
		xs.setOutput(this.output, "UTF-8");
		xs.startDocument("UTF-8", true);
		xs.startTag(null, "property");
		xs.startTag(null, "userInfo");
		xs.text(userInfo);
		xs.endTag(null, "userInfo");
		int id=1;
		
		for(int i=0;i<saveStrokes.size();i++){
			xs.startTag(null, "stroke");
			xs.attribute(null, "strokeOrderID",id+"");
			id++;		
			for(int j=0;j<saveStrokes.get(i).getPoints().size();j++){
				xs.startTag(null,"point");
				xs.startTag(null, "x");
				xs.text(saveStrokes.get(i).getPoints().get(j).getX()+"");
				xs.endTag(null, "x");
				xs.startTag(null, "y");
				xs.text(saveStrokes.get(i).getPoints().get(j).getY()+"");
				xs.endTag(null,"y");
				xs.startTag(null, "time");
				xs.text(saveStrokes.get(i).getPoints().get(j).getTime()+"");
				xs.endTag(null,"time");
				xs.endTag(null,"point");
			}
			
			xs.endTag(null,"stroke");
		}
		
		
		xs.endTag(null,"property");
		xs.endDocument();
		xs.flush();
		
	}

}
