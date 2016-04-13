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
	private Button open_left,open_bt;//���󻬲˵��������İ�ť
	private ImageButton open_lock,my_key,location,cycling;//��ť�������ҵ�Կ�ף�λ�ã�����
	private BluetoothReceiver receiver;  
    private BluetoothAdapter bluetoothAdapter;  
    private List<String> devices;
    private List<BluetoothDevice> deviceList;
    private BluetoothSocket socket;
    private BluetoothDevice device;
    private boolean isConnect;
    private String ANDROID_ID;
    private byte[] bytes2 = new byte[250]; //���յ�Ƭ�������������ݣ����ڲ���
    UUID uuid;
    private BluetoothSocket mmSocket;    
    private BluetoothDevice mmDevice;   
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        open_left=(Button)findViewById(R.id.open_left);
        open_lock=(ImageButton)findViewById(R.id.btn_tab_bottom_open);
        my_key=(ImageButton)findViewById(R.id.btn_tab_bottom_key);//�ҵ�Կ�װ�ť
        location=(ImageButton)findViewById(R.id.btn_tab_bottom_position);//λ�ð�ť
        cycling=(ImageButton)findViewById(R.id.btn_tab_bottom_cycling);//���а�ť
        open_left.setOnClickListener(new Listener());
        my_key.setOnClickListener(new MyKeyListener());//��ת���ҵ�Կ��ҳ��
        initRightMenu();   
        /*��������������HC-06*/
        ANDROID_ID = Settings.System.getString(getContentResolver(), Settings.System.ANDROID_ID);//��ȡ�ֻ�Ψһ��ID�ţ���Ϊ����������Կ
        uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); //��51��Ƭ�����ӵ�UUID��ͨ�ýӿ�ID�� 
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
		// ���ô�����Ļ��ģʽ
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		menu.setShadowWidthRes(R.dimen.shadow_width);
		menu.setShadowDrawable(R.drawable.shadow);
		// ���û����˵���ͼ�Ŀ��
		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		// ���ý��뽥��Ч����ֵ
		menu.setFadeDegree(0.35f);
		// menu.setBehindScrollScale(1.0f);
		menu.setSecondaryShadowDrawable(R.drawable.shadow);
		//�����ұߣ��������໬�˵�
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
	 * ���󻬲˵���ť�ļ�����
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
	/*����Կ�ף���������������*/
	@Override
	public void onArticleSelected(int state) {
		// TODO Auto-generated method stub
		if(state==0)
		{

			// TODO Auto-generated method stub
			if (isConnect) {
	             //  ���ݷ���
	               try {
	                 OutputStream outStream1 = mmSocket.getOutputStream();
	                 outStream1.write(getHexBytes("0f010d0e"));
	                 outStream1.write(getHexBytes("0f010d0e"));
	                 outStream1.write(getHexBytes("0f010d0e"));
	                 outStream1.write(getHexBytes("0f010d0e"));
	                 outStream1.write(getHexBytes("0f010d0e"));
	                 System.out.println("�������ݷ��ͳɹ���");
	             } catch (IOException e) {
	                 //setState(WRITE_FAILED);
	                 Log.e("TAG", e.toString());
	             }
	               try {
	                   Thread.sleep(2000);//���������5000����5000���룬Ҳ����5�룬���Ըó�����Ҫ��ʱ��
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
		                 System.out.println("�������ݷ��ͳɹ���");
		             } catch (IOException e) {
		                 //setState(WRITE_FAILED);
		                 Log.e("TAG", e.toString());
		             }
	        	 /*************���ݽ���*************/
	             /* ���� 1��on ���� 2��of ���� 3��ti �� 4��re ���
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
     			Toast.makeText(MainActivity.this, "����δ���ӣ�",Toast.LENGTH_LONG).show();				
			}
		
		}
		
	}
	
	/*************������&&���ÿɼ���*************/
    private void search() {  
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();  
        if (!adapter.isEnabled()) {  
            adapter.enable();  
        }  
        Intent enable = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);  
        enable.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 200); //200Ϊ�����豸�ɼ�ʱ��  
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
    /**********������������������********/
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
             System.out.println("��������2");
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
             System.out.println("�ɹ����ӣ�");
  			Toast.makeText(MainActivity.this, "�������ӳɹ���",Toast.LENGTH_LONG).show();

         } catch (IOException connectException) {    
             // Unable to connect; close the socket and get out    
             try {    
                 mmSocket.close();
                 System.out.println("����ʧ�ܣ�");
       			Toast.makeText(MainActivity.this, "��������ʧ�ܣ�",Toast.LENGTH_LONG).show();

             } catch (IOException closeException) { }    
             return;    
         }   
     	}
     		else
     		{
     			Toast.makeText(MainActivity.this, "�����Ѿ����ӣ�",Toast.LENGTH_LONG).show();
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

    /**********�ַ���ת��16����*********/
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
 
    /**********�ַ���ת��16����*********/
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
    
    /**********16����ת�ַ���***********/
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
