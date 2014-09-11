/**
* @author Ramya.D
* @Project Crash Report
* @Date 21.08.2014
* Copyright (C) 2014, Greeno Tech Solutions Pvt. Ltd.
*/

package com.greeno.crashreport;

import java.util.ArrayList;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.greeno.Data.CrashReportData;
import com.greeno.Util.ApplicationInformation;
import com.greeno.Util.XmlParser;
import com.greeno.model.CrashReportDB_Factory;

public class UploadCrashReport{
	
	static CrashReportDB_Factory crashReportDB_Factory;
	static ApplicationInformation applicationInformation;
	static Activity baseContext;
	CrashReportData crashReportDataGobal;
	public static void startService(final Activity context)
	{
		baseContext = context;
		crashReportDB_Factory = new CrashReportDB_Factory(context);
		applicationInformation = new ApplicationInformation();
		
		
         Thread background1 = new Thread(new Runnable() {
             
             private final HttpClient Client = new DefaultHttpClient();
             
             // After call for background.start this run method call
             public synchronized void run() {
                 try {
             		try {
            			int count = crashReportDB_Factory.getNumberOfAvailableList(applicationInformation.getApplicationName(context));

            			if(count>0)
            			{

            				ArrayList<CrashReportData> allUnSyncList = new ArrayList<CrashReportData>();
            				allUnSyncList = crashReportDB_Factory.getCrashReport(applicationInformation.getApplicationName(context));
            				for(int i=0; i<allUnSyncList.size() ;i++)
            				{
            				
            					CrashReportData crashReportData = allUnSyncList.get(i);

            					String response = null;
            					if(applicationInformation.isInternetConnection(context))
            						response = applicationInformation.executeMultiPartRequest(context, crashReportData);
            					String status = "";

            					if (response != null) {
            	         			
                     				/** Parse the response */
                     				XmlParser mXmlParser = new XmlParser();
                     				Document doc = mXmlParser.getDomElement(response);
                     				NodeList nl = doc.getElementsByTagName("responseCrashReport");

                     				if (nl.getLength() != 0) {

                     					for (int j = 0; j < nl.getLength(); j++) {

                     						Element element = (Element) nl.item(j);
                     						status = mXmlParser.getValue(element, "status");
                     						if (status.equals("success")) {
                                 				CrashReportDB_Factory crashReportDB_Factory = new CrashReportDB_Factory(baseContext);

                                 				try {
                                 					crashReportDB_Factory.deleteCrashReport(applicationInformation.getApplicationName(baseContext), crashReportData.getCurrentDate());
                                 				} catch (NameNotFoundException e) {
                                 					// TODO Auto-generated catch block
                                 					e.printStackTrace();
                                 				}

                                 			}
                     					}

                     				}

                     			}

                     			else {

                     			}
                     			
            				}
            			}
            		} catch (NameNotFoundException e) {
            			// TODO Auto-generated catch block
            			e.printStackTrace();
            		}catch (Exception e) {
            			// TODO Auto-generated catch block
            			e.printStackTrace();
            		}	

                 } catch (Throwable t) {
                     // just end the background thread
                     Log.i("Animation", "Thread  exception " + t);
                 }
             }

             private void threadMsg(String msg) {

                 if (!msg.equals(null) && !msg.equals("")) {
                     Message msgObj = handler.obtainMessage();
                     Bundle b = new Bundle();
                     b.putString("message", msg);
                     msgObj.setData(b);
                     handler.sendMessage(msgObj);
                 }
             }

             // Define the Handler that receives messages from the thread and update the progress
             private final Handler handler = new Handler() {

                 public void handleMessage(Message msg) {
                      
                     String responseMessage = msg.getData().getString("message");
                     String status = "";
 
                 }
             };

         });
         // Start Thread
         background1.start();
		
	}
}
