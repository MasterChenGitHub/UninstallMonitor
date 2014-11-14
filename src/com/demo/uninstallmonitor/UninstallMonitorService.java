package com.demo.uninstallmonitor;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.demo.uninstallmonitor.util.AppUtil;

public class UninstallMonitorService extends Service {

	static final String TAG=UninstallMonitorService.class.getSimpleName();
	
	public static void start(Context ctx){
		ctx.startService(new Intent(ctx,UninstallMonitorService.class));
	}
	
	
	private void log(String msg){
		Log.i(TAG, msg);
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		log("oncreate");
		AppUtil.setUninstallMonitor(getApplicationContext());
		
		
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		log("onstartcommand");
		return START_STICKY;
	}
	

}
