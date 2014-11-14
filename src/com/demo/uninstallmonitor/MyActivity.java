package com.demo.uninstallmonitor;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class MyActivity extends Activity {
	/**
     */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		Toast.makeText(this, "monitor start at application oncreate(),see logcat", Toast.LENGTH_LONG).show();

	}

}