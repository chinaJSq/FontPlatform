package com.edu.writing;

import java.util.List;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.edu.fontplatform.R;

public class GridViewAdapter extends BaseAdapter {
	    	public List<String>  arrayList  ;
	    	public List<Integer>  numlist  ;
	    	public Context context;
	    	private LayoutInflater inflater;
	    	private ButtonListener mButtonListener;
	    	
			public GridViewAdapter(List<String> arrayList, List<Integer>  numlist,Context context) {
				super();
				this.arrayList = arrayList;
				this.context = context;
				this.numlist = numlist;
				this.inflater=LayoutInflater.from(context);
			}
			@Override
			public int getCount() {
				// �����Ҿͷ���10�ˣ�Ҳ����һ����10��������
				return arrayList.size();
			}
			@Override
			public Object getItem(int arg0) {
				return arg0;
			}
			@Override
			public long getItemId(int position) {
				return position;
			}
			@Override
			public View getView(final int position, View convertView, ViewGroup parent) {
				// position����λ�ô�0��ʼ��convertView��Spinner,ListView��ÿһ��Ҫ��ʾ��view
				//ͨ��return ��viewҲ����convertView
				//parent���Ǹ������ˣ�Ҳ����Spinner,ListView,GridView��.
				
				ViewHolder holder;				
				if (convertView==null) {
					
					convertView  =  inflater.inflate(R.layout.gridview_item, null);
					holder=new ViewHolder();
					holder.tv= (TextView) convertView.findViewById(R.id.tv_info);
					holder.myview = (MyView) convertView.findViewById(R.id.my_view);
					holder.imgview = (ImageView) convertView.findViewById(R.id.imgview);
					convertView.setTag(holder);
				}else {
					holder=(ViewHolder) convertView.getTag();
				}
				
				if (mButtonListener != null) {
					mButtonListener.onImgVisable(holder.imgview,position);
					mButtonListener.onView(holder.myview, arrayList.get(position),numlist.get(position),position);
				}
				
//				holder.imgview.setOnClickListener(new OnClickListener() {
//					
//					@Override
//					public void onClick(View v) {
//						// TODO Auto-generated method stub
//						Toast t=	Toast.makeText(context, "��Ҫ����,���������"+"\n\n"+"Don't click on me, click on the following words", Toast.LENGTH_SHORT);
//						t.setGravity(Gravity.CENTER, 0, 0);
//						t.show();
//					}
//				});
				holder.imgview.setOnTouchListener(new OnTouchListener() {
					
					@Override
					public boolean onTouch(View v, MotionEvent event) {
						// TODO Auto-generated method stub
						return true;
					}
				});
				holder.tv.setText(arrayList.get(position));
				return convertView;
	    }

	class ViewHolder
	{
		public MyView myview;
		public TextView tv;
		public ImageView imgview;
	}
	 /**
	 * ���ð�ť�ļ����¼�
	 */
	public interface ButtonListener {
		public void onView(MyView view,String name,int strokenum,int position);
		public void onImgVisable(ImageView imgview,int position);

	}
	public void setButtonListener(ButtonListener l) {
		mButtonListener = l;
	}
	
}
