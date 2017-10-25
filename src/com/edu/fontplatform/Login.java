package com.edu.fontplatform;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.edu.common.ExpApp;


public class Login extends Activity {
	public  String result_school = "";
	public  String result_chooseMode = "";
	private String result_grade = "";
	private String result_class = "";
	private String userInfo;
	
	private List<String> schoolList = new ArrayList<String>();
	private Spinner schoolSpinner = null;
	private ArrayAdapter<String> schoolAdapter;
	private List<String> chooseModeList = new ArrayList<String>();
	private Spinner chooseModeSpinner = null;
	private ArrayAdapter<String> chooseModeAdapter;
	
	private List<String> gradeList = new ArrayList<String>();
	private Spinner gradeSpinner = null;
	private ArrayAdapter<String> gradeAdapter;
	
	private List<String> classList = new ArrayList<String>();
	private Spinner classSpinner = null;
	private ArrayAdapter<String> classAdapter;
	
	private EditText stuNumET=null;
	private EditText stuNameET=null;
	private Button loginBtn=null;
	
	private RadioButton rbMale=null;
	private RadioButton rbFemale=null;
	
	private ExpApp app;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);//取消标题
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.login);
//		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//强制为横屏
		app = (ExpApp) this.getApplication();
		
		stuNumET=(EditText)findViewById(R.id.stuNumEt);				
		stuNameET=(EditText)findViewById(R.id.stuNameEt);	
		rbMale=(RadioButton)findViewById(R.id.male);
        rbFemale=(RadioButton)findViewById(R.id.female);
        result_school=getSchoolInfo();
        result_grade=getGradeInfo();
        result_class=getClassInfo();
        
        result_chooseMode = chooseModeInfo();
		loginBtn=(Button)findViewById(R.id.loginBtn);		
		loginBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String stuNum=stuNumET.getText().toString();
				String stuName=stuNameET.getText().toString();
				String sGender="";
				if(rbMale.isChecked())
					sGender=rbMale.getText().toString();
				if(rbFemale.isChecked())
					sGender=rbFemale.getText().toString();
				userInfo="学校："+result_school+"年级："+result_grade+"班级："+result_class+"学号："+stuNum+"姓名："+stuName+"性别："+sGender;
				System.out.println(userInfo);
				if(!"".equals(stuNum)&&!"".equals(stuName)){
				Intent it = new Intent(Login.this,
						MainActivity.class);
				app.setStuSchool(result_school);
				app.setStuGrade(result_grade);
				app.setStuClass(result_class);
				app.setMode(result_chooseMode);
				app.setStuNum(stuNum);
				app.setStuName(stuName);
				app.setStuGender(sGender);
				startActivity(it);
				}else{
					Toast.makeText(Login.this, "登录信息不全", Toast.LENGTH_LONG).show();
				}
				
			}
			
		});

		
		//给上述方法加return语句，通过返回值记录选项值
		
	}
	private String getSchoolInfo()
	{	schoolList.add("新疆双语学校");
		schoolList.add("南湖一小");
		schoolList.add("鼓楼小学");
		schoolList.add("琅琊路小学");

		schoolSpinner = (Spinner) this.findViewById(R.id.schoolSpinner);		
		schoolAdapter = new ArrayAdapter<String>(this, R.layout.myspinner,
				schoolList);// 使用自定义的spinner样式
		schoolAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		schoolSpinner.setAdapter(schoolAdapter);
		
		schoolSpinner
				.setOnItemSelectedListener(new Spinner.OnItemSelectedListener()
				{

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3)
					{					
						
//						userInfo = null;
						result_school = schoolAdapter.getItem(arg2);						
//						userInfo = result_school + result_grade + result_class;						
//						btnEvent();
						arg0.setVisibility(View.VISIBLE);
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0)
					{
						arg0.setVisibility(View.VISIBLE);
					}

				});
		schoolSpinner.setOnTouchListener(new Spinner.OnTouchListener()
		{
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1)
			{
				return false;
			}
		});

		schoolSpinner
				.setOnFocusChangeListener(new Spinner.OnFocusChangeListener()
				{

					@Override
					public void onFocusChange(View v, boolean hasFocus)
					{
					}
				});
		return result_school;

	}
	
	private String getGradeInfo()
	{
		gradeList.add("三年级");
		gradeList.add("二年级");
		gradeList.add("一年级");
		gradeList.add("四年级");
		gradeList.add("五年级");
		gradeList.add("六年级");
		

		gradeSpinner = (Spinner) this.findViewById(R.id.gradeSpinner);		
		gradeAdapter = new ArrayAdapter<String>(this, R.layout.myspinner,
				gradeList);// 使用自定义的spinner样式
		gradeAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		gradeSpinner.setAdapter(gradeAdapter);
		
		gradeSpinner
				.setOnItemSelectedListener(new Spinner.OnItemSelectedListener()
				{

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3)
					{					
						
//						userInfo = null;
						result_grade = gradeAdapter.getItem(arg2);						
//						userInfo = result_school + result_grade + result_class;						
//						btnEvent();
						arg0.setVisibility(View.VISIBLE);
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0)
					{
						arg0.setVisibility(View.VISIBLE);
					}

				});
		gradeSpinner.setOnTouchListener(new Spinner.OnTouchListener()
		{
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1)
			{
				return false;
			}
		});

		gradeSpinner
				.setOnFocusChangeListener(new Spinner.OnFocusChangeListener()
				{

					@Override
					public void onFocusChange(View v, boolean hasFocus)
					{
					}
				});
		return result_grade;

	}
	
	private String getClassInfo()
	{
		classList.add("(1)班");
		classList.add("(2)班");
		classList.add("(3)班");
		classList.add("(4)班");
		classList.add("(5)班");
		classList.add("(6)班");
		classList.add("(7)班");
		classList.add("(8)班");
		classList.add("(9)班");
		

		classSpinner = (Spinner) this.findViewById(R.id.classSpinner);		
		classAdapter = new ArrayAdapter<String>(this, R.layout.myspinner,
				classList);// 使用自定义的spinner样式
		classAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		classSpinner.setAdapter(classAdapter);
		
		classSpinner
				.setOnItemSelectedListener(new Spinner.OnItemSelectedListener()
				{

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3)
					{											
//						userInfo = null;
						result_class = classAdapter.getItem(arg2);						
//						userInfo = result_school + result_grade + result_class;						
//						btnEvent();
						arg0.setVisibility(View.VISIBLE);
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0)
					{
						arg0.setVisibility(View.VISIBLE);
					}

				});
		classSpinner.setOnTouchListener(new Spinner.OnTouchListener()
		{
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1)
			{
				return false;
			}
		});

		classSpinner
				.setOnFocusChangeListener(new Spinner.OnFocusChangeListener()
				{

					@Override
					public void onFocusChange(View v, boolean hasFocus)
					{
					}
				});
		return result_class;

	}
	private String chooseModeInfo()
	{	chooseModeList.add("42字");
	    chooseModeList.add("490字");
	    chooseModeList.add("32笔画");

	    chooseModeSpinner = (Spinner) this.findViewById(R.id.sp_chooseMode);		
		chooseModeAdapter = new ArrayAdapter<String>(this, R.layout.myspinner,
				chooseModeList);// 使用自定义的spinner样式
		chooseModeAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		chooseModeSpinner.setAdapter(chooseModeAdapter);
		
		chooseModeSpinner
				.setOnItemSelectedListener(new Spinner.OnItemSelectedListener()
				{

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3)
					{					
						
//						userInfo = null;
						result_chooseMode = chooseModeAdapter.getItem(arg2);						
//						userInfo = result_school + result_grade + result_class;						
//						btnEvent();
						arg0.setVisibility(View.VISIBLE);
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0)
					{
						arg0.setVisibility(View.VISIBLE);
					}

				});
		chooseModeSpinner.setOnTouchListener(new Spinner.OnTouchListener()
		{
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1)
			{
				return false;
			}
		});

		chooseModeSpinner
				.setOnFocusChangeListener(new Spinner.OnFocusChangeListener()
				{

					@Override
					public void onFocusChange(View v, boolean hasFocus)
					{
					}
				});
		return result_chooseMode;

	}
	@Override
	protected void onNewIntent(Intent intent)
	{
		// TODO Auto-generated method stub
		super.onNewIntent(intent);
		if ((Intent.FLAG_ACTIVITY_CLEAR_TOP & intent.getFlags()) != 0)
		{
			finish();
		}
	}

}
