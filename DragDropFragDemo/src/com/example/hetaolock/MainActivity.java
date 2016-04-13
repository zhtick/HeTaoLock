package com.example.hetaolock;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import otherElement.WiperSwitch;
import otherElement.WiperSwitch.OnChangedListener;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.androidbook.drag.drop.demo.Dot;
import com.androidbook.drag.drop.demo.DropZone.OnArticleSelectedListener;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
//import otherElement.MenuLeftFragment;

public class MainActivity extends SlidingFragmentActivity implements OnChangedListener,OnArticleSelectedListener{
	private Dot Key;
	private Button open_left,open_bt;//打开左滑菜单和蓝牙的按钮
	private ImageButton open_lock,my_key,location,cycling;//按钮开锁，我的钥匙，位置，骑行
	private BluetoothReceiver receiver;  
    private BluetoothAdapter bluetoothAdapter;  
    private List<String> devices;
    private List<BluetoothDevice> deviceList;
    private BluetoothSocket socket;
    private BluetoothDevice device;
    private boolean isConnect;
    private String ANDROID_ID;
    private byte[] bytes2 = new byte[250]; //接收单片机发送来的数据，用于测试
    UUID uuid;
    private BluetoothSocket mmSocket;    
    private BluetoothDevice mmDevice;   
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        open_left=(Button)findViewById(R.id.open_left);
        open_lock=(ImageButton)findViewById(R.id.btn_tab_bottom_open);
        my_key=(ImageButton)findViewById(R.id.btn_tab_bottom_key);//我的钥匙按钮
        location=(ImageButton)findViewById(R.id.btn_tab_bottom_position);//位置按钮
        cycling=(ImageButton)findViewById(R.id.btn_tab_bottom_cycling);//骑行按钮
        open_left.setOnClickListener(new Listener());
        my_key.setOnClickListener(new MyKeyListener());//跳转到我的钥匙页面
        initRightMenu();   
        /*搜索并连接蓝牙HC-06*/
        ANDROID_ID = Settings.System.getString(getContentResolver(), Settings.System.ANDROID_ID);//获取手机唯一的ID号，作为连接蓝牙密钥
        uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); //与51单片机连接的UUID（通用接口ID） 
        deviceList = new ArrayList<BluetoothDevice>();
        devices = new ArrayList<String>();   
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        receiver = new BluetoothReceiver();
        registerReceiver(receiver, filter);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        search();
        bluetoothAdapter.startDiscovery();
      
        
    }
    private void initRightMenu()
	{
		
		Fragment leftMenuFragment = new MenuLeftFragment();
		setBehindContentView(R.layout.left_menu_frame);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.id_left_menu_frame, leftMenuFragment).commit();
		SlidingMenu menu = getSlidingMenu();
		menu.setMode(SlidingMenu.LEFT);
		// 设置触摸屏幕的模式
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		menu.setShadowWidthRes(R.dimen.shadow_width);
		menu.setShadowDrawable(R.drawable.shadow);
		// 设置滑动菜单视图的宽度
		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		// 设置渐入渐出效果的值
		menu.setFadeDegree(0.35f);
		// menu.setBehindScrollScale(1.0f);
		menu.setSecondaryShadowDrawable(R.drawable.shadow);
		//设置右边（二级）侧滑菜单
	}
	public void showLeftMenu(View view)
	{
		getSlidingMenu().showMenu();
	}

	@Override
	public void OnChanged(WiperSwitch wiperSwitch, boolean checkState)
	{
		// TODO Auto-generated method stub
		
	}
	/*
	 * 打开左滑菜单按钮的监听器
	 */
	public class Listener implements OnClickListener{
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			if(open_left==arg0){
			getSlidingMenu().showMenu();
			}
			if(open_lock==arg0){
				Intent intent=new Intent();
			}
		}
		
	}
	public class BluetoothListener implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			open_bt.setVisibility(BIND_ABOVE_CLIENT);
			
		}
       
}
	public class MyKeyListener implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent=new Intent(MainActivity.this,MyKeyActivity.class);
			startActivity(intent);
			finish();
		}
		
	}
	/*滑动钥匙，向蓝牙发送数据*/
	@Override
	public void onArticleSelected(int state) {
		// TODO Auto-generated method stub
		if(state==0)
		{

			// TODO Auto-generated method stub
			if (isConnect) {
	             //  数据发送
	               try {
	                 OutputStream outStream1 = mmSocket.getOutputStream();
	                 outStream1.write(getHexBytes("0f010d0e"));
	                 outStream1.write(getHexBytes("0f010d0e"));
	                 outStream1.write(getHexBytes("0f010d0e"));
	                 outStream1.write(getHexBytes("0f010d0e"));
	                 outStream1.write(getHexBytes("0f010d0e"));
	                 System.out.println("开锁数据发送成功！");
	             } catch (IOException e) {
	                 //setState(WRITE_FAILED);
	                 Log.e("TAG", e.toString());
	             }
	               try {
	                   Thread.sleep(2000);//括号里面的5000代表5000毫秒，也就是5秒，可以该成你需要的时间
	               } 
	               catch (InterruptedException e) {
	                   e.printStackTrace();
	               }
	               try {
		                 OutputStream outStream1 = mmSocket.getOutputStream();
		                 outStream1.write(getHexBytes("0f020d0e"));
		                 outStream1.write(getHexBytes("0f020d0e"));
		                 outStream1.write(getHexBytes("0f020d0e"));
		                 outStream1.write(getHexBytes("0f020d0e"));
		                 outStream1.write(getHexBytes("0f020d0e"));
		                 System.out.println("上锁数据发送成功！");
		             } catch (IOException e) {
		                 //setState(WRITE_FAILED);
		                 Log.e("TAG", e.toString());
		             }
	        	 /*************数据接收*************/
	             /* 接收 1、on 开锁 2、of 上锁 3、ti 绑定 4、re 解绑
	              * try {
	                 InputStream inputStream = mmSocket.getInputStream();
	                 int data;
	                 int i=0;
	                 while (true) {
	                     try {
	                         data = inputStream.read();
	                         System.out.println((byte)data);
	                         bytes2[i] = (byte)data;
	                         i++;
	                         if(i>=17){                    
	                        	 String str = new String(bytes2,"GBK");
	                             System.out.println(str);
	                             i=0;
	                             break;                            
	                         }                                     
	                     } catch (IOException e) {
	                         Log.e("TAG", e.toString());
	                         break;
	                     }
	                 }
	                 
	             } catch (IOException e) {
	                 Log.e("TAG", e.toString());
	             }*/
	         }
			else{
     			Toast.makeText(MainActivity.this, "蓝牙未连接！",Toast.LENGTH_LONG).show();				
			}
		
		}
		
	}
	
	/*************打开蓝牙&&设置可见性*************/
    private void search() {  
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();  
        if (!adapter.isEnabled()) {  
            adapter.enable();  
        }  
        Intent enable = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);  
        enable.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 200); //200为蓝牙设备可见时间  
        startActivity(enable);   
    }
    
    private class BluetoothReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			//Intent intent = new Intent();
			// TODO Auto-generated method stub
			String action = intent.getAction();
			if(BluetoothDevice.ACTION_FOUND.equals(action)){
				device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
			    System.out.println(device.getName());
			    if(device.getName().equals("HC-06"))
			    {		    	
			    	ConnectThread thread = new ConnectThread(device); 
			    	thread.run();
			    }
			}
			
		}
    	
    }
    /**********连接蓝牙并发送数据********/
    private class ConnectThread extends Thread {    
         
     
     public ConnectThread(BluetoothDevice device) {    
         // Use a temporary object that is later assigned to mmSocket,    
         // because mmSocket is final  
         BluetoothSocket tmp = null;    
         mmDevice = device;   
         // Get a BluetoothSocket to connect with the given BluetoothDevice    
         try {    
             // MY_UUID is the app's UUID string, also used by the server code    
             tmp = device.createRfcommSocketToServiceRecord(uuid);
             System.out.println("进入这里2");
         } catch (IOException e) {
        	 
         }    
         mmSocket = tmp;    
     }   
     	public void run() {
     		bluetoothAdapter.cancelDiscovery();
         // Cancel discovery because it will slow down the connection  
     		if(!isConnect){
         try {    
             // Connect the device through the socket. This will block    
             // until it succeeds or throws an exception    
             mmSocket.connect(); 
             isConnect=true;
             System.out.println("成功连接！");
  			Toast.makeText(MainActivity.this, "蓝牙连接成功！",Toast.LENGTH_LONG).show();

         } catch (IOException connectException) {    
             // Unable to connect; close the socket and get out    
             try {    
                 mmSocket.close();
                 System.out.println("连接失败！");
       			Toast.makeText(MainActivity.this, "蓝牙连接失败！",Toast.LENGTH_LONG).show();

             } catch (IOException closeException) { }    
             return;    
         }   
     	}
     		else
     		{
     			Toast.makeText(MainActivity.this, "蓝牙已经连接！",Toast.LENGTH_LONG).show();
     		}
         // Do work to manage the connection (in a separate thread)    
         //manageConnectedSocket(mmSocket);
     	
       /* if (mmSocket != null) {
             try {
            	 mmSocket.close();
             } catch (IOException e) {
                 Log.e("TAG", e.toString());
             }
         }

     }   */
     /** Will cancel an in-progress connection, and close the socket */   
    /*public void cancel() {    
         try {    
             mmSocket.close();    
         } catch (IOException e) { }*/  
     }    
 }   

    /**********字符串转换16进制*********/
    private byte[] getHexBytes(String message) {  
        int len = message.length()/2;  
        char[] chars = message.toCharArray();  
        String[] hexStr = new String[len];  
        byte[] bytes = new byte[len];  
        for (int i = 0, j = 0; j < len; i+=2, j++) {  
            hexStr[j] = "" + chars[i]+chars[i+1];
            if(Integer.parseInt(hexStr[j], 16)>=128)
            bytes[j] = (byte) (Integer.parseInt(hexStr[j], 16)/2); 
            else
            bytes[j] = (byte) Integer.parseInt(hexStr[j], 16);
        }  
        return bytes;  
    } 	
 
    /**********字符串转换16进制*********/
    /*private byte[] getHexBytes(String message) {  
        int len = message.length();  
        char[] chars = message.toCharArray();  
        String[] hexStr = new String[len];  
        byte[] bytes = new byte[len];  
        for (int i = 0, j = 0; j < len; i++, j++) {  
            hexStr[j] = "0" + chars[i];
            bytes[j] = (byte) Integer.parseInt(hexStr[j], 16);  
        }  
        return bytes;  
    } */
    
    /**********16进制转字符串***********/
    public static String bytesToHexString(byte[] bytes) {
        String result = "";
        for (int i = 0; i < bytes.length; i++) {
            String hexString = Integer.toHexString(bytes[i] & 0xFF);
            if (hexString.length() == 1) {
                hexString = '0' + hexString;
            }
            result += hexString.toUpperCase();
        }
        return result;
    }
}
