/**
* @author Ramya.D
* @Project Crash Report
* @Date 21.08.2014
* Copyright (C) 2014, Greeno Tech Solutions Pvt. Ltd.
*/

package com.greeno.crashreport;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

import com.greeno.Data.CrashReportData;
import com.greeno.Util.ApplicationInformation;
import com.greeno.Util.XmlParser;
import com.greeno.model.CrashReportDB_Factory;

public class CrashReportActivity extends Activity {
	Button okButtonCrash, cancelButtonCrash ;
	ProgressDialog progressBar;
	CrashReportData crashReportData = new CrashReportData();
	ApplicationInformation applicationInformation = new ApplicationInformation();
	CrashReportDB_Factory crashReportDB_Factory;
	String response = null;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setFinishOnTouchOutside (false);
        setContentView(R.layout.floatingactivity);
        okButtonCrash = (Button)findViewById(R.id.okButtonCrash);
        cancelButtonCrash = (Button)findViewById(R.id.cancelButtonCrash);
       
        
        crashReportDB_Factory = new CrashReportDB_Factory(getBaseContext());
        progressBar = new ProgressDialog(this);
		progressBar.setCancelable(true);
		progressBar.setProgressStyle(ProgressDialog.THEME_HOLO_DARK);
        
//        Bundle bundle = getIntent().getExtras();
       
        try {
			crashReportData = crashReportDB_Factory.getReportByCurrentDate(applicationInformation.getApplicationName(getBaseContext()), crashReportDB_Factory.getLastDataCurrentDate(applicationInformation.getApplicationName(getBaseContext())));
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        /** If user clicks OK, Send details to Server */
       ((Button)findViewById(R.id.okButtonCrash)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
					new loadClass(CrashReportActivity.this,(View)findViewById(
						android.R.id.content).getRootView()).execute();
			}
		});
        
       /** If user clicks CANCEL, close the application */
       ((Button)findViewById(R.id.cancelButtonCrash)).setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {	
				try {
					crashReportDB_Factory.deleteCrashReport(applicationInformation.getApplicationName(getBaseContext()), crashReportData.getCurrentDate());
				} catch (NameNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				android.os.Process.killProcess(android.os.Process.myPid());
				System.exit(10);
				System.exit(0);
			}
		});
        
    }
    
	/**
	 * Sub class to access the web service and to retrieve the response
	 */
	private class loadClass extends AsyncTask<String, Void, String> {

		View view;

		public loadClass(Activity activity, View mRootView) {
			this.view = mRootView;
		}

		@Override
		protected void onPreExecute() {

			super.onPreExecute();
			progressBar.setMessage("Submitting report ....");
			progressBar.setIndeterminate(false);
			progressBar.setCancelable(false);
			progressBar.show();

		}

		@Override
		protected String doInBackground(String... params) {

			try {
				/** Send Crash Report details to Server */
				response = applicationInformation.executeMultiPartRequest(getBaseContext(), crashReportData);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return response;
		}

		@Override
		protected void onPostExecute(String success) {
			super.onPostExecute(success);
			progressBar.dismiss();
			String status = "";
			if (response != null) {
				progressBar.setMessage("Download completed");
				progressBar.dismiss();

				/** Parse the response */
				XmlParser mXmlParser = new XmlParser();
				Document doc = mXmlParser.getDomElement(response);
				NodeList nl = doc.getElementsByTagName("responseCrashReport");

				if (nl.getLength() == 0);
				else {
					for (int i = 0; i < nl.getLength(); i++) {

						Element element = (Element) nl.item(i);
						status = mXmlParser.getValue(element, "status");
						
						if (status.equals("success")) {
							CrashReportDB_Factory crashReportDB_Factory = new CrashReportDB_Factory(getBaseContext());
							try {
								/** Delete the uploaded details from Local DB */
								crashReportDB_Factory.deleteCrashReport(applicationInformation.getApplicationName(getBaseContext()), crashReportData.getCurrentDate());
							} catch (NameNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							System.exit(0);
						}
						else 
						{
							System.exit(0);
						}

					}
				}
			}

			else {
				System.exit(0);
			}
			
		}

	}    
} 
