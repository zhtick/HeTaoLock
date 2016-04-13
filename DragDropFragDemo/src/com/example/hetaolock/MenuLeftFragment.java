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
	private List<String> mDatas = Arrays.asList("�ҵĺ���", "����Կ��", "��ϵ����", "����");
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
		 * ��ʼ����Ƭ�Լ�����
		 */
		photo = (ImageView) mView.findViewById(R.id.people_photo);
		name = (TextView) mView.findViewById(R.id.people_name);
		name.setText("��������");
		name.setTextSize(20);
		photo.setImageResource(R.drawable.defaultphoto);
		// ��ʼ��ListView
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
			/***** �ҵĺ��� *****/
			case 0:
				/***** ����Կ�� *****/
				break;
			case 1:

				/***** ��ϵ���� *****/
				break;
			case 2:
				/***** �������� *****/
				break;
			case 3:

				Intent intent1 = new Intent();
				intent1.setClass(getActivity(), AboutActivity.class);// �����ΪҪ��ת��Activity
				startActivity(intent1);
				break;
			default:
				break;
			}
		}
	}
}