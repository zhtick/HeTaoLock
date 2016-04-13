package com.androidbook.drag.drop.demo;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hetaolock.R;

public class Palette extends Fragment {
	private Dot Key;
	private View v;
	@Override
	public View onCreateView(LayoutInflater inflater,
			ViewGroup container, Bundle icicle) {
	    v = inflater.inflate(R.layout.palette, container, false);
	    setupViews();
		return v;
	}
	private void setupViews() {
		Key = (Dot) v.findViewById(R.id.dot1);
		System.out.println(222222);
		Key.setImageResource(R.drawable.clicked);
		}
}
