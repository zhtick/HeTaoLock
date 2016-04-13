package com.upc.adapter;

import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.hetaolock.R;

/**
 * 我的钥匙列表的适配器
 * 
 * @author zn
 * 
 */
public class MyKeyListAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private ArrayList<String> name;
	private ArrayList<String> start_time;
	private ArrayList<String> stop_time;

	public MyKeyListAdapter(Context context, ArrayList<String> name,
			ArrayList<String> start_time, ArrayList<String> stop_time) {
		this.name = name;
		this.start_time = start_time;
		this.stop_time = stop_time;
		this.mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return name.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return name.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		TextView user_name = null;
		TextView user_start_time = null;
		TextView user_stop_time = null;
		if (arg1 == null) {
			arg1 = mInflater.inflate(R.layout.mykey_list, null);
			user_name = (TextView) arg1.findViewById(R.id.sharer_name);
			user_start_time = (TextView) arg1.findViewById(R.id.start_time);
			user_stop_time = (TextView) arg1.findViewById(R.id.stop_time);
			arg1.setTag(new Holder(user_name, user_start_time, user_stop_time));
		} else {
			Holder holder = (Holder) arg1.getTag();
			user_name = holder.name;
			user_start_time = holder.start_time;
			user_stop_time = holder.stop_time;
		}
		user_name.setText(name.get(arg0));
		user_start_time.setText(start_time.get(arg0));
		user_stop_time.setText(stop_time.get(arg0));
		return null;
	}

	private final class Holder {
		public TextView name, start_time, stop_time;
		public Holder(TextView name, TextView start_time, TextView stop_time) {
			this.name = name;
			this.start_time = start_time;
			this.stop_time = stop_time;
		}
	}
}
