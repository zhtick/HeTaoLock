package com.example.hetaolock;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class AboutActivity extends Activity
{
	ImageButton button;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		button = (ImageButton)findViewById(R.id.back);
		button.setOnClickListener(new BackOnClickListener());
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.about, menu);
		return true;
	}
	public class BackOnClickListener implements OnClickListener
	{
		@Override
		public void onClick(View arg0)
		{
			finish();
		}
	}
}
