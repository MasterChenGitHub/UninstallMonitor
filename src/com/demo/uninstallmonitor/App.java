package com.demo.uninstallmonitor;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class App extends Application {

	static final String TAG="App";
	@Override
	public void onCreate() {
		super.onCreate();
		  UninstallMonitorService.start(this);
	        Log.i("uninstall_monitor", "application start service");
		
	SharedPreferences sPreferences=	PreferenceManager.getDefaultSharedPreferences(this);
	sPreferences.edit().putBoolean("installed", true).apply();
		  
	}

    
    
}
