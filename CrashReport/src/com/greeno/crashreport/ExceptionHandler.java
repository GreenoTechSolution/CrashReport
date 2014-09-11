/**
* @author Ramya.D
* @Project Crash Report
* @Date 21.08.2014
* Copyright (C) 2014, Greeno Tech Solutions Pvt. Ltd.
*/

package com.greeno.crashreport;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

import com.greeno.Data.CrashReportData;
import com.greeno.Util.ApplicationInformation;
import com.greeno.model.CrashReportDB_Factory;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;

public class ExceptionHandler implements java.lang.Thread.UncaughtExceptionHandler {
	private final Activity myContext;
	private final String LINE_SEPARATOR = "\n";
	private String appName = "";
	ApplicationInformation applicationInformation = new ApplicationInformation();
	
	
	public ExceptionHandler(Activity context){//, String appName) {
		this.myContext = context;
		

	}

	public void uncaughtException(Thread thread, Throwable exception) {
		
		StringWriter stackTrace = new StringWriter();
		exception.printStackTrace(new PrintWriter(stackTrace));
		StringBuilder errorReport = new StringBuilder();
		String message = exception.getMessage();
		errorReport.append(message);
		errorReport.append("************ CAUSE OF ERROR ************\n\n");
		errorReport.append(stackTrace.toString());
		
		errorReport.append("\n************ DEVICE INFORMATION ***********\n");
		errorReport.append("Brand: ");
		errorReport.append(Build.BRAND);
		errorReport.append(LINE_SEPARATOR);
		errorReport.append("Device: ");
		errorReport.append(Build.DEVICE);
		errorReport.append(LINE_SEPARATOR);
		errorReport.append("Model: ");
		errorReport.append(Build.MODEL);
		errorReport.append(LINE_SEPARATOR);
		errorReport.append("Id: ");
		errorReport.append(Build.ID);
		errorReport.append(LINE_SEPARATOR);
		errorReport.append("Product: ");
		errorReport.append(Build.PRODUCT);
		errorReport.append(LINE_SEPARATOR);
		errorReport.append("\n************ FIRMWARE ************\n");
		errorReport.append("SDK: ");
		errorReport.append(Build.VERSION.SDK);
		errorReport.append(LINE_SEPARATOR);
		errorReport.append("Release: ");
		errorReport.append(Build.VERSION.RELEASE);
		errorReport.append(LINE_SEPARATOR);
		errorReport.append("Incremental: ");
		errorReport.append(Build.VERSION.INCREMENTAL);
		errorReport.append(LINE_SEPARATOR);
		System.out.println("===errorReport=="+errorReport.toString());
		
		
		String  versionName = "", applicationName = "";
		String isInternetConnect ="", typeOfInternet = ""; 
		 
		int versionCode = 0;
		double latitude = 0.0, longitude = 0.0;
		long currentDate = 0;
		ApplicationInfo applicationInfo = null ;
		

		Date date = new Date();
		GPSTracker gps = new GPSTracker(myContext);
		
		try {
			versionName = myContext.getPackageManager()
				    .getPackageInfo(myContext.getPackageName(), 0).versionName;
			versionCode = myContext.getPackageManager()
				    .getPackageInfo(myContext.getPackageName(), 0).versionCode;
			applicationName = applicationInformation.getApplicationName(myContext);//, applicationInfo);
			
			isInternetConnect = ""+applicationInformation.isInternetConnection(myContext);
			typeOfInternet = applicationInformation.getSystemConnectivityStatus(myContext);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		currentDate = date.getTime();
		
		
		 
        // check if GPS enabled     
        if(gps.canGetLocation()){
             
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
             
            // \n is for new line
//            Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();    
        }else{
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
//            gps.showSettingsAlert();
        }
		System.out.println("===== isInternetConnect ====="+isInternetConnect);
		System.out.println("====== latitude =="+latitude);
		System.out.println("====== longitude =="+longitude);
		CrashReportData crashReportData = new CrashReportData();
		CrashReportDB_Factory crashReportDB_Factory = new CrashReportDB_Factory(myContext);
		
		crashReportData.setCurrentDate(currentDate);
		crashReportData.setGeoLocation(latitude+":"+longitude);
		crashReportData.setAppName(applicationName);
		crashReportData.setAppVersionName(versionName);
		crashReportData.setAppVersionCode(""+versionCode);
		crashReportData.setDeviceBrand(""+Build.BRAND);
		crashReportData.setDeviceOSVersion(Build.VERSION.RELEASE);
		crashReportData.setDeviceModel(Build.MODEL);
		crashReportData.setDeviceSDKNo(Build.VERSION.SDK);
		crashReportData.setStackTrace(""+stackTrace.toString());
		
		crashReportData.setIsInternetAvailable(isInternetConnect);
		crashReportData.setTypeOfInternet(typeOfInternet);
		System.out.println("===== isInternetConnect crashReportData ====="+crashReportData.getIsInternetAvailable());
		crashReportDB_Factory.onCreate(applicationName);
		crashReportDB_Factory.onSave(crashReportData);
		

		Intent intent = new Intent(myContext, CrashReportActivity.class);
		intent.putExtra("currentDate", currentDate);
		intent.setFlags (Intent.FLAG_ACTIVITY_CLEAR_TOP);
		myContext.startActivity(intent);
		
		android.os.Process.killProcess(android.os.Process.myPid());
		System.exit(10);
	}
	
	public void startService(Context context)
	{
		
	}
	
}	
