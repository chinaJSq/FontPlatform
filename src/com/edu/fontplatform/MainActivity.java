package com.edu.fontplatform;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.xutils.x;
import org.xutils.common.Callback;
import org.xutils.ex.HttpException;
import org.xutils.http.RequestParams;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.database.SQLException;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.edu.common.ChineseCode;
import com.edu.common.ExpApp;
import com.edu.common.WriteWordList;
import com.edu.common.ZipUtils;
import com.edu.writing.GridViewAdapter;
import com.edu.writing.GridViewAdapter.ButtonListener;
import com.edu.writing.MyView;
import com.njnu.struct.Stroke;
import com.njnu.util.xmlPaser;


public class MainActivity extends Activity implements ButtonListener,OnClickListener {

	private GridView gv_writing;
	private GridViewAdapter adapter;
	public List<String> arrayList= new ArrayList<String>(); 
	public List<Integer>  numlist = new ArrayList<Integer>();
	public Button clean,next,nextgroup,commit,pass;
	public MyView writeView;
	private ImageView imgview;
	private List<Stroke> strokes = new ArrayList<Stroke>();
	private List<Stroke> strokes2 = new ArrayList<Stroke>();
	private String s;
	private int strokenum =0;
	private int pos=0;
	private String[] WriteWord457;
	private int[] Writenum457;
	private int n=0;
	private ExpApp app;
	private int num = 0;
	private String path;
	private TextView tv_now;
	private ProgressDialog dialog = null;
	private  File file=null;
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//强制为横屏
        app = (ExpApp) this.getApplication();
        path =  Environment.getExternalStorageDirectory()+File.separator+"FontPlatform"+File.separator;
        initView();
        
        file=new File(Environment.getExternalStorageDirectory()+File.separator+"FontPlatform"+File.separator
				+app.getStuGrade()+app.getStuClass()+File.separator+app.getStuNum()+File.separator+app.getMode()+File.separator);
       if(!file.exists()){
    	   file.mkdirs();
       }
        if(file.listFiles()!=null){
        	n = file.listFiles().length/2+2;
        }
        if("42字".equals(app.getMode())){
        	WriteWord457 = WriteWordList.WriteWord42 ;
        	Writenum457 = WriteWordList.WriteStrokeNum42;
        }
        if("490字".equals(app.getMode())){
        	WriteWord457 = WriteWordList.WriteWord490 ;
        	Writenum457 = WriteWordList.WriteStrokeNum490;
        }
        if("32笔画".equals(app.getMode())){
        	WriteWord457 = WriteWordList.WriteWord32 ;
        	Writenum457 = WriteWordList.WriteStrokeNum32;
        }
        if(n<WriteWord457.length-11){
        	for(int i=n;i<(n+4);i++){
        	
        		arrayList.add(WriteWord457[i]);
        		numlist.add(Writenum457[i]);
        	}
        }else{
        	for(int i=n;i<WriteWord457.length;i++){
            	
        		arrayList.add(WriteWord457[i]);
        		numlist.add(Writenum457[i]);
        	}
        	
        }
        adapter = new GridViewAdapter(arrayList,numlist, MainActivity.this);
        gv_writing.setAdapter(adapter);      
        adapter.setButtonListener(this);
        gv_writing.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub

				strokes = new ArrayList<Stroke>();
				strokes = writeView.getStrokes();
				
//				if(strokes.size()==strokenum){//正确性判断
					if(num < 4){
						num = num+1;
					}else{
						num=3;
					}
					try {
						xmlPaser.save(s, strokes, app.getStuGrade()+app.getStuClass(), app.getStuNum(),app.getMode());
						Bitmap b =  xmlPaser.shot(writeView, writeView.getWidth());
						xmlPaser.savePic(b,s, app.getStuGrade()+app.getStuClass(), app.getStuNum(),app.getMode());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					pos = position;
					imgview.setVisibility(View.VISIBLE);
					adapter.notifyDataSetChanged();
//				}else{
//					showOpenDialog("书写错误，请重新书写");
//				}
			}
		});
        tv_now.setText("当前进度 "+(n/4+1)+"/"+(WriteWord457.length/4+1));
    }

	private void initView() {
		gv_writing =(GridView) findViewById(R.id.gv_writing);
		clean = (Button) findViewById(R.id.bt_clean);
		next = (Button) findViewById(R.id.bt_next);
		nextgroup = (Button) findViewById(R.id.bt_group);
		commit = (Button) findViewById(R.id.bt_commit);
		pass = (Button) findViewById(R.id.bt_pass);
		tv_now = (TextView) findViewById(R.id.tv_now);
		
		
		clean.setOnClickListener(this);
		next.setOnClickListener(this);
		nextgroup.setOnClickListener(this);
		commit.setOnClickListener(this);
		pass.setOnClickListener(this);
		
	}

	@Override
	public void onView(MyView view, String name,int strokenum ,int postation) {
		// TODO Auto-generated method stub
		if(postation==pos){
			writeView = view;
			s = name;
//			s = new ChineseCode().chineseCode(s);
			this.strokenum = strokenum;
			writeView.setAdapter(adapter);
		}
	}

	@Override
	public void onImgVisable(ImageView imgageview,int postation) {
		// TODO Auto-generated method stub
//			imgview.setVisibility(View.VISIBLE);
		imgageview.setVisibility(View.VISIBLE);
		if(postation==pos){
			imgview = imgageview;
			imgview.setVisibility(View.GONE);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bt_clean:
			
			strokes = writeView.getStrokes();
			writeView.screenClear();
			System.out.println(s);
			
			break;
		case R.id.bt_next:
			
			finish();
			
			break;
		case R.id.bt_commit:
			
			File[] files = file.listFiles();
			if(files.length<WriteWord457.length-1){
				showOpenDialog("书写完成才能提交哦");
			}else{
				dialog = new ProgressDialog(this);
				dialog.setTitle("提示信息");
				dialog.setMessage("数据加载中，请稍后...");
				dialog.setCanceledOnTouchOutside(false);//点击外部不消失
				dialog.show();
				uploadData();				
			}
			
			break;
			
		case R.id.bt_group:
			if(num == 3||num>3){
				strokes = new ArrayList<Stroke>();
				strokes = writeView.getStrokes();
//				if(strokes.size()==strokenum){	
					
				try {
					xmlPaser.save(s, strokes, app.getStuGrade()+app.getStuClass(), app.getStuNum(),app.getMode());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				num=0;
				if(n<WriteWord457.length-11){
				pos = 0;
				n = n+4;
				tv_now.setText("当前进度 "+(n/4+1)+"/"+(WriteWord457.length/4+1));
				arrayList = new ArrayList<String>();
				numlist = new ArrayList<Integer>();
				if(n>(WriteWord457.length-12)){
					for(int i=n;i<WriteWord457.length;i++){
						arrayList.add(WriteWord457[i]);  
						numlist.add(Writenum457[i]);
					}
				}else{
					for(int i=n;i<(n+4);i++){
						arrayList.add(WriteWord457[i]);
						numlist.add(Writenum457[i]);
					}
				}
				adapter();
				adapter.notifyDataSetChanged();
				}else{
					showOpenDialog("后面没有了");
				}
				
//				}else{
//					showOpenDialog("书写错误请重新书写！");
//				}
				
			}else{
				showOpenDialog("请先把本组书写完成");
			}
			break;
		case R.id.bt_pass:
			num = 3;
			
			if(n>3||n<WriteWord457.length-1){
				
				pos = 0;
				n = n-4;
				tv_now.setText("当前进度 "+(n/4+1)+"/"+(WriteWord457.length/4+1));
				arrayList = new ArrayList<String>();
				numlist = new ArrayList<Integer>();
				for(int i=n;i<(n+4);i++){
		        	arrayList.add(WriteWord457[i]);  
		        	numlist.add(Writenum457[i]);
		        }
				adapter();
				adapter.notifyDataSetChanged();
			}else{
				showOpenDialog("前面没有了");
			}
			break;

		default:
			break;
		}
	}
	private void uploadData() {
		// TODO Auto-generated method stub
		File destFile=new File(Environment.getExternalStorageDirectory()+File.separator+"FontPlatform"+File.separator
				+app.getStuGrade()+app.getStuClass()+File.separator+app.getStuNum()+File.separator+app.getStuNum()+".zip");
		if(destFile.exists()){
			destFile.delete(); 
		}
		File[] files=destFile.getParentFile().listFiles();			
		try {
			ZipUtils.zipFiles(files, destFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		//showToast("打包成功~");	
		//上传	
		uploadUserWords(destFile.getAbsolutePath());
	}

	private void uploadUserWords(String absolutePath) {
		// TODO Auto-generated method stub
		String uploadUrl ="http://222.192.6.54/CharacterDemo1.0/uploadTest";
		RequestParams params = new RequestParams(uploadUrl);
		params.setMultipart(true);
		// 只包含字符串参数时默认使用BodyParamsEntity，;		
		params.addBodyParameter("msg", absolutePath.substring(absolutePath.lastIndexOf(File.separator)+1)); 
		params.addBodyParameter(absolutePath.replace("/", ""), new File(absolutePath));   
		params.setCacheMaxAge(1000 * 90);
		params.setConnectTimeout(1000 * 90);
		x.http().post(params, new Callback.CommonCallback<String>() {

			@Override
			public void onCancelled(CancelledException arg0) {

			}

			@Override
			public void onError(Throwable ex, boolean arg1) {
				if (ex instanceof HttpException) { // 网络错误
					HttpException httpEx = (HttpException) ex;
					int responseCode = httpEx.getCode();
					String responseMsg = httpEx.getMessage();
					String errorResult = httpEx.getResult();
					Toast.makeText(MainActivity.this,errorResult, 2000).show();
					System.out.println(errorResult);
				}else if(ex instanceof SQLException){ // 其他错误
					SQLException httpEx = (SQLException) ex;
					String responseMsg = httpEx.getMessage();
					Toast.makeText(MainActivity.this,responseMsg, 2000).show();
					System.out.println(responseMsg);
				}else{
					ex.printStackTrace();
					Toast.makeText(MainActivity.this,ex.toString(), 2000).show();
					System.out.println(ex.toString());
				}
			}

			@Override
			public void onFinished() {
				
			}

			@Override
			public void onSuccess(String arg0) {
				dialog.cancel();
				Toast.makeText(MainActivity.this, "上传成功", 2000).show();
			}
		});
	}

	public void adapter(){
		 	adapter = new GridViewAdapter(arrayList,numlist, MainActivity.this);
	        gv_writing.setAdapter(adapter);      
	        adapter.setButtonListener(this);
	        gv_writing.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
								
					strokes = new ArrayList<Stroke>();
					strokes = writeView.getStrokes();
//					if(strokes.size()==strokenum){
						if(num < 4){
							num = num+1;
						}else{
							num=3;
						}
					try {						
						xmlPaser.save(s, strokes, app.getStuGrade()+app.getStuClass(), app.getStuNum(),app.getMode());
						Bitmap b =  xmlPaser.shot(writeView, writeView.getWidth());
						xmlPaser.savePic(b,s, app.getStuGrade()+app.getStuClass(), app.getStuNum(),app.getMode());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					pos = position;
					imgview.setVisibility(View.VISIBLE);
					adapter.notifyDataSetChanged();
//					}else{
//						showOpenDialog("书写错误，请重新书写");
//					}
				}
				
			});
	}
	/**
	 * 协议显示信息
	 * @param info 需要显示的内容
	 */
	public void showOpenDialog(String info){
		AlertDialog.Builder builder = new Builder(this);
		builder.setMessage(info);
		builder.setTitle("提示");
		builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {	
			
			}
		});
		
		builder.create().show();
	}

}
