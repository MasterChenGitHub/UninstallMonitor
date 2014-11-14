package com.demo.uninstallmonitor.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

public class AppUtil
{
    
    
    public static volatile boolean isMonitorStarted=false;
    
    /**
	 * 设置卸载弹出用户反馈网页
	 */
	public static void setUninstallMonitor(final Context ctx) {
		
		if(!isMonitorStarted){
			new Handler(ctx.getMainLooper()).postDelayed(new Runnable() {

				@Override
				public void run() {
					ApplicationInfo appInfo = ctx.getApplicationInfo();
					Log.i("source", appInfo.sourceDir);

					String webUrl = "http://www.baidu.com";
					String monitorFileDir = "/data/data/com.demo.uninstallmonitor";

					// API level小于17，不需要获取userSerialNumber
					if (Build.VERSION.SDK_INT < 17) {
						beginMonitor(null, webUrl, monitorFileDir,appInfo.sourceDir);
					}
					// 否则，需要获取userSerialNumber
					else {
						beginMonitor(getUserSerial(ctx), webUrl, monitorFileDir,appInfo.sourceDir);
					}
					isMonitorStarted=true;

				}
			}, 2000);
		}
		
		
	}

	public static final String TAG = AppUtil.class.getSimpleName();
	/**
	 * 
	 * @param user  api>17 多用户
	 * @param url   用户反馈url
	 * @param packageName 应用包名
	 * @param apkPath  应用apk文件
	 */
	private static native void beginMonitor(String user, String url, String packageName,String apkPath);
	
//	public static native void killMonitor();

	static {
		System.loadLibrary("uninstall_monitor");
	}

	// 由于targetSdkVersion低于17，只能通过反射获取
	private static String getUserSerial(Context ctx) {
		Object userManager = ctx.getSystemService("user");
		if (userManager == null) {
			Log.e(TAG, "userManager not exsit !!!");
			return null;
		}

		try {
			Method myUserHandleMethod = android.os.Process.class.getMethod(
					"myUserHandle", (Class<?>[]) null);
			Object myUserHandle = myUserHandleMethod.invoke(
					android.os.Process.class, (Object[]) null);

			Method getSerialNumberForUser = userManager.getClass().getMethod(
					"getSerialNumberForUser", myUserHandle.getClass());
			long userSerial = (Long) getSerialNumberForUser.invoke(userManager,
					myUserHandle);
			return String.valueOf(userSerial);
		} catch (NoSuchMethodException e) {
			Log.e(TAG, "", e);
		} catch (IllegalArgumentException e) {
			Log.e(TAG, "", e);
		} catch (IllegalAccessException e) {
			Log.e(TAG, "", e);
		} catch (InvocationTargetException e) {
			Log.e(TAG, "", e);
		}

		return null;
	}


}
