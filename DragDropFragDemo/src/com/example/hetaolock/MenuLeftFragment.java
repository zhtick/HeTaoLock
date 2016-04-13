package com.example.hetaolock;

import java.util.Arrays;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MenuLeftFragment extends Fragment
{
	private View mView;
	private ListView myListView;
	private List<String> mDatas = Arrays.asList("我的核桃", "分享钥匙", "联系我们", "关于");
	private ListAdapter mAdapter;
	private ImageView photo;
	private TextView name;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		if (mView == null)
		{
			initView(inflater, container);
		}
		return mView;
	}

	private void initView(LayoutInflater inflater, ViewGroup container)
	{
		mView = inflater.inflate(R.layout.left_menu, container, false);
		/**
		 * 初始化照片以及姓名
		 */
		photo = (ImageView) mView.findViewById(R.id.people_photo);
		name = (TextView) mView.findViewById(R.id.people_name);
		name.setText("隔壁老王");
		name.setTextSize(20);
		photo.setImageResource(R.drawable.defaultphoto);
		// 初始化ListView
		myListView = (ListView) mView.findViewById(R.id.id_listview_categories);
		mAdapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, mDatas);
		myListView.setAdapter(mAdapter);
		myListView.setOnItemClickListener(new LeftMenuItemClickListener());

	}

	class LeftMenuItemClickListener implements OnItemClickListener
	{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3)
		{
			// TODO Auto-generated method stub
			switch (arg2)
			{
			/***** 我的核桃 *****/
			case 0:
				/***** 分享钥匙 *****/
				break;
			case 1:

				/***** 联系我们 *****/
				break;
			case 2:
				/***** 关于我们 *****/
				break;
			case 3:

				Intent intent1 = new Intent();
				intent1.setClass(getActivity(), AboutActivity.class);// 这里改为要跳转的Activity
				startActivity(intent1);
				break;
			default:
				break;
			}
		}
	}
}