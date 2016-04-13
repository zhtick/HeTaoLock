package com.example.hetaolock;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class WelcomeActivity extends Activity {
	//定义视图
	private View view1, view2, view3;
	private Button bn;
	private ViewPager viewPager;
	private String cache_path="/sdcard/cache/";
	Intent intent;
	//圆点图标数组
	private ImageView[] img;
	//视图列表
	ArrayList<View> viewList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);
		File cache=new File (cache_path);
		//定义选项卡、
		LayoutInflater lf = getLayoutInflater().from(this);	
		view1 = lf.inflate(R.layout.demo1, null);
		System.out.println("文件创建成功");	
		view1 = lf.inflate(R.layout.demo1, null);
		view2 = lf.inflate(R.layout.demo2, null);
		view3 = lf.inflate(R.layout.demo3, null);
		System.out.println("文件创建");
		viewPager=(ViewPager) findViewById(R.id.viewpager);
		viewList = new ArrayList<View>();// 将要分页显示的View装入数组中
		if(cache.exists()){
		viewList.add(view3);}
		else{
		viewList.add(view1);
		viewList.add(view2);
		viewList.add(view3);}
		 img = new ImageView[viewList.size()];
		 LinearLayout layout = (LinearLayout) findViewById(R.id.viewGroup);
	        for (int i = 0; i < viewList.size(); i++) {
	            img[i] = new ImageView(WelcomeActivity.this);
	            if (0 == i) {
	                img[i].setBackgroundResource(R.drawable.dot1);
	            } else {
	                img[i].setBackgroundResource(R.drawable.dot2);
	            }
	            img[i].setPadding(0, 0, 20, 0);
	            layout.addView(img[i]);
	        }
		//定义适配器
		PagerAdapter pager=new PagerAdapter() {
			
			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				// TODO Auto-generated method stub
				return arg0 == arg1;  
			}
			
			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return viewList.size();  
			}

			@Override
			public void destroyItem(ViewGroup container, int position, Object object) {
				// TODO Auto-generated method stub
				container.removeView(viewList.get(position));
				
			}

			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				container.addView(viewList.get(position));
				
				return viewList.get(position);
			}
			
		};
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				 for (int i = 0; i < viewList.size(); i++) {
		                if (arg0 == i) {
		                    img[i].setBackgroundResource(R.drawable.dot1);
		                } else {
		                    img[i].setBackgroundResource(R.drawable.dot2);
		                }
		            }
				 if(arg0==viewList.size()-1){
						bn=(Button) findViewById(R.id.bn1);
						bn.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								intent =new Intent(WelcomeActivity.this,MainActivity.class);
								WelcomeActivity.this.startActivity(intent);
								finish();
							}
						});
					}
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		viewPager.setAdapter(pager);
	}
		
	

}
