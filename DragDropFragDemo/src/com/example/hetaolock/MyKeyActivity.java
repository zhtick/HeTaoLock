package com.example.hetaolock;
import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ListView;

import com.upc.adapter.MyKeyListAdapter;
public class MyKeyActivity extends Activity {
	private ArrayList<String> name;
	private ArrayList<String> start_time;
	private ArrayList<String> stop_time;//接收到的钥匙列表信息
	private ImageButton open,my_key,location,cycling;
    private String phone_number;
	private ListView mykey_list;
	private MyKeyListAdapter adapter=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mykey);
		open=(ImageButton)findViewById(R.id.btn_tab_bottom_open);
		open.setImageResource(R.drawable.recent_chat_showright_normal);
		my_key=(ImageButton)findViewById(R.id.btn_tab_bottom_key);
		my_key.setImageResource(R.drawable.tab_find_frd_pressed);
		location=(ImageButton)findViewById(R.id.btn_tab_bottom_position);
		cycling=(ImageButton)findViewById(R.id.btn_tab_bottom_cycling);
		mykey_list=(ListView)findViewById(R.id.my_key_list);
		adapter=new MyKeyListAdapter(this,name,start_time,stop_time);
		
	}

}
