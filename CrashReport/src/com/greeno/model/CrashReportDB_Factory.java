/**
* @author Ramya.D
* @Project Crash Report
* @Date 21.08.2014
* Copyright (C) 2014, Greeno Tech Solutions Pvt. Ltd.
*/

package com.greeno.model;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.greeno.Data.CrashReportData;

public class CrashReportDB_Factory extends SQLiteOpenHelper implements CrashReportDB{

	public static final String DATABASE_NAME = "CrashReport";
	public static final int DATABASE_VERSION = 1;
	public String TABLE_NAME="CrashReport_"; 
	
	
	public String currentDate = "currentDate";
	public String geoLocation = "geoLocation";
	public String appName = "appName";
	public String appVersionName = "appVersionName";
	public String appVersionCode = "appVersionCode";
	
	public String deviceBrand = "deviceBrand";
	public String deviceOSVersion = "deviceOSVersion";
	public String deviceModel = "deviceModel";
	public String deviceSDKNo = "deviceSDKNo";
	public String stackTrace = "stackTrace";
	public String isInternetAvailble = "isInternetAvailble";
	public String typeOfInternet = "typeOfInternet";
	
	public String mobileNumber = "mobileNumber";
	
	public CrashReportDB_Factory(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	
	// Create a Table
	public void onCreate(String applicationName)
	{
		
		TABLE_NAME="CrashReport_" + applicationName;
		
		String createQuery = "CREATE TABLE IF NOT EXISTS "+TABLE_NAME+"("
							+currentDate+" BIGINT UNIQUE , "+geoLocation+" TEXT, "+appName+" TEXT, "
							+appVersionName+" TEXT, "+appVersionCode+" TEXT, "+deviceBrand+" TEXT, "
							+deviceOSVersion+" TEXT, "+deviceModel+" TEXT, "+deviceSDKNo+" TEXT, "
							+stackTrace+" TEXT, "+isInternetAvailble+" TEXT, "+typeOfInternet+" TEXT , "+mobileNumber+" TEXT "+     " )"+"";
//		System.out.println("1 createQuery="+createQuery);
		SQLiteDatabase db=this.getReadableDatabase();
		db.execSQL(createQuery);
		db.close();
//		System.out.println("2 createQuery="+createQuery);
	}
	
	// Insert value in Table
	public void onSave(CrashReportData crashReportData)
	{
		
		if((crashReportData.getCurrentDate() -getLastDataCurrentDate(crashReportData.getAppName()))>10000)
		{
		 
			TABLE_NAME="CrashReport_" + crashReportData.getAppName();
			String insertQuery = "insert into "+TABLE_NAME+"("+currentDate+" , "+geoLocation+", "+appName+", "
					+appVersionName+", "+appVersionCode+", "+deviceBrand+", "
					+deviceOSVersion+", "+deviceModel+", "+deviceSDKNo+" , "
					+stackTrace+","+isInternetAvailble+","+typeOfInternet+","+mobileNumber+") values("+crashReportData.getCurrentDate()+",'"+crashReportData.getGeoLocation()+"','"+crashReportData.getAppName()+"','"
					+crashReportData.getAppVersionName()+"','"+crashReportData.getAppVersionCode()+"','"+crashReportData.getDeviceBrand()+"','"
					+crashReportData.getDeviceOSVersion()+"','"+crashReportData.getDeviceModel()+"','"				
					+crashReportData.getDeviceSDKNo()+"','"+crashReportData.getStackTrace()+"','"
					+crashReportData.getIsInternetAvailable()+"','"+crashReportData.getTypeOfInternet()+"','"
					+crashReportData.getMobileNumber()+"'"
					+")"; 
			
			System.out.println("=== insertQuery =="+insertQuery);
			SQLiteDatabase db = this.getWritableDatabase();
			db.execSQL(insertQuery);
			db.close();
		}
		else;
	}
	
	// Get the all the list of Crash Report for a specified Application
	public ArrayList<CrashReportData> getCrashReport(String applicationName)
	{
		
		TABLE_NAME="CrashReport_" + applicationName;
		ArrayList<CrashReportData>  crashReportList = new ArrayList<CrashReportData>();

		try{
			
		String selectQuery  = "select * from "+TABLE_NAME+" where "+appName+"='"+applicationName+"'";
		
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor getList = db.rawQuery(selectQuery, null);
		
		while(getList.moveToNext())
		{
			CrashReportData crashReportData = new CrashReportData();
			crashReportData.setCurrentDate(getList.getLong(0));
			crashReportData.setGeoLocation(getList.getString(1));
			crashReportData.setAppName(getList.getString(2));
			crashReportData.setAppVersionName(getList.getString(3));
			crashReportData.setAppVersionCode(getList.getString(4));
			crashReportData.setDeviceBrand(getList.getString(5));
			crashReportData.setDeviceOSVersion(getList.getString(6));
			crashReportData.setDeviceModel(getList.getString(7));
			crashReportData.setDeviceSDKNo(getList.getString(8));
			crashReportData.setStackTrace(getList.getString(9));
			crashReportData.setIsInternetAvailable(getList.getString(10));
			crashReportData.setTypeOfInternet(getList.getString(11));
			
			crashReportData.setMobileNumber(getList.getString(getList.getColumnIndex(mobileNumber)));
			crashReportList.add(crashReportData);
		}
		db.close();
		getList.close();
		}
		catch(Exception e)
		{
			return crashReportList;
		}
		
		return crashReportList;
	}
	
	
	// Get a count Crash Report for a specified Application
	public int getNumberOfAvailableList(String applicationName)
	{
		
		int count = 0;
		
		try
		{
			TABLE_NAME="CrashReport_" + applicationName;
			String selectQuery  = "select * from "+TABLE_NAME+" where "+appName+"='"+applicationName+"'";
			
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor getList = db.rawQuery(selectQuery, null);
			
			count = getList.getCount();
			
			db.close();
			getList.close();
		}
		catch(Exception e)
		{
			onCreate(applicationName);
			return count;
		}


		return count;
	}
	
	// Current Date is unique; So get a List by App name and Current date
	public CrashReportData getReportByCurrentDate(String applicationName, long currentDate1)
	{

		CrashReportData crashReportData = new CrashReportData();
		TABLE_NAME="CrashReport_" + applicationName;
		String selectQuery  = "select * from "+TABLE_NAME+" where "+appName+"='"+applicationName+"' and "+currentDate+"="+currentDate1;
		
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor getList = db.rawQuery(selectQuery, null);
		
		while(getList.moveToNext())
		{		
			crashReportData.setCurrentDate(getList.getLong(0));
			crashReportData.setGeoLocation(getList.getString(1));
			crashReportData.setAppName(getList.getString(2));
			crashReportData.setAppVersionName(getList.getString(3));
			crashReportData.setAppVersionCode(getList.getString(4));
			crashReportData.setDeviceBrand(getList.getString(5));
			crashReportData.setDeviceOSVersion(getList.getString(6));
			crashReportData.setDeviceModel(getList.getString(7));
			crashReportData.setDeviceSDKNo(getList.getString(8));
			crashReportData.setStackTrace(getList.getString(9));
			
			crashReportData.setIsInternetAvailable(getList.getString(10));
			crashReportData.setTypeOfInternet(getList.getString(11));
			
			crashReportData.setMobileNumber(getList.getString(getList.getColumnIndex(mobileNumber)));
			
		}
		db.close();
		getList.close();
		
		return crashReportData;
	}
	
	// Delete a Record from Crash Report table based on App Name and Current Date
    public void deleteCrashReport(String applicationName, long currentDate1)
    {
    	TABLE_NAME="CrashReport_" + applicationName;
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(TABLE_NAME, appName+"='"+applicationName+"' and "+currentDate+"="+currentDate1, null);
        db.close();
    }
	
    
	public long getLastDataCurrentDate(String applicationName)//, long currentDate1)
	{
		boolean isAllow = false;
		long currentDateInTable = 0;
		
		System.out.println("== applicationName ="+applicationName+";");
//		CrashReportData crashReportData = new CrashReportData();
		TABLE_NAME="CrashReport_" + applicationName;
		String selectQuery  = "select MAX("+currentDate+") from "+TABLE_NAME+" where "+appName+"='"+applicationName+"'";
		
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor getList = db.rawQuery(selectQuery, null);
		while(getList.moveToNext())
		{		
			currentDateInTable = getList.getLong(0);
			System.out.println("isAllow="+isAllow);
		}
		db.close();
		getList.close();
	
		return currentDateInTable;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}


}
