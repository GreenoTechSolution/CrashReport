/**
* @author Ramya.D
* @Project Crash Report
* @Date 21.08.2014
* Copyright (C) 2014, Greeno Tech Solutions Pvt. Ltd.
*/

package com.greeno.Util;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.greeno.Data.CrashReportData;

public class ApplicationInformation {

	final int CONN_WAIT_TIME = 40000;
	final int CONN_DATA_WAIT_TIME = 40000;
//	public static CrashReportData crashReportData;
	XmlCreator xmlCreator=new XmlCreator();
	
	/* This method return the Application Name ;
	 * Input - Context */
	public String getApplicationName(Context myContext) throws NameNotFoundException{//,ApplicationInfo info){
		
		ApplicationInfo applicationInfo = myContext.getPackageManager()
			    .getPackageInfo(myContext.getPackageName(), 0).applicationInfo;
        PackageManager p = myContext.getPackageManager();
        String label = p.getApplicationLabel(applicationInfo).toString();
        return label;
    }
	
	
	/* This method will communicate with server and will return the response as String */
	public synchronized String executeMultiPartRequest(Context context, CrashReportData crashReportData) throws Exception {
		
		String strResponse = null;
		String requestXML = xmlCreator.getRequestXML(crashReportData);
		
		HttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams,
				CONN_WAIT_TIME);
		HttpConnectionParams.setSoTimeout(httpParams, CONN_DATA_WAIT_TIME);
		DefaultHttpClient httpClient = new DefaultHttpClient(httpParams);
		HttpPost httppost = new HttpPost("http://rainbowagri.com/RainbowDev/REST/WebService/crashReportInfo");

		httppost.addHeader("Accept", "application/xml");
		httppost.addHeader("Content-Type", "application/xml");

		try {

			StringEntity entity = new StringEntity(requestXML, "UTF-8");
			entity.setContentType("application/xml");
			httppost.setEntity(entity);
			HttpResponse httpResponse = httpClient.execute(httppost);
			BasicResponseHandler responseHandler = new BasicResponseHandler();

			if (httpResponse != null) {
				try {
					strResponse = responseHandler
							.handleResponse(httpResponse);
				}

				catch (HttpResponseException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				strResponse = null;
			}
			Log.e("WCFTEST", "WCFTEST ********** Response" + strResponse);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return strResponse;
	}

	public boolean isInternetConnection(Context context) {
		boolean connected = false;
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
				.getState() == NetworkInfo.State.CONNECTED
				|| connectivityManager.getNetworkInfo(
						ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
			// we are connected to a network
			connected = true;
		} else
			connected = false;

		return connected;
	}
	
	public boolean isWifiConnected(Context context)
	{
		boolean wifi = false;
		ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		
		if(mWifi.isConnected())
			wifi = true;
		return wifi;
	}
	
	public static String getSystemConnectivityStatus(Context context)
	{
		String connection = "No Connection";
		ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkActive = cm.getActiveNetworkInfo();
		
		if(networkActive != null)
		{
			if(networkActive.getType() == ConnectivityManager.TYPE_WIFI)
				connection = "Wifi";
			if(networkActive.getType() == ConnectivityManager.TYPE_MOBILE)
			{
				if(connection.equals("No Connection"))
					connection = "Mobile Data";
				else
					connection = connection+":"+ "Mobile Data";
			}
				
		}
	
		return connection;
	}

}
