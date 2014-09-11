/**
* @author Ramya.D
* @Project Crash Report
* @Date 21.08.2014
* Copyright (C) 2014, Greeno Tech Solutions Pvt. Ltd.
*/

package com.greeno.crashreport;

import android.app.Activity;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;

import com.greeno.Util.ApplicationInformation;
import com.greeno.model.CrashReportDB_Factory;

public class MainActivity extends Activity {

	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
		
		UploadCrashReport.startService(this);
		setContentView(R.layout.floatingactivity);
		ApplicationInformation applicationInformation = new ApplicationInformation();
		CrashReportDB_Factory crashReportDB_Factory = new CrashReportDB_Factory(this);
		try {
			int count = crashReportDB_Factory.getNumberOfAvailableList(applicationInformation.getApplicationName(this));
			
			System.out.println("= count  ; in main ac=="+count);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		ArrayList arrayList = new ArrayList();
//		System.out.println(arrayList.get(0));
		
		int a = 10/0;
	}
}
