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
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.greeno.Data.CrashReportData;
import com.greeno.Util.ApplicationInformation;
import com.greeno.Util.XmlParser;
import com.greeno.model.CrashReportDB_Factory;

public class CrashReportActivity extends Activity {
	LinearLayout layout;
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

        crashReportDB_Factory = new CrashReportDB_Factory(getBaseContext());
        progressBar = new ProgressDialog(this);
		progressBar.setCancelable(true);
		progressBar.setProgressStyle(ProgressDialog.THEME_HOLO_DARK);
		System.out.println("======== Entering CrashReportActivity ==");
        try {
			crashReportData = crashReportDB_Factory.getReportByCurrentDate(applicationInformation.getApplicationName(getBaseContext()), crashReportDB_Factory.getLastDataCurrentDate(applicationInformation.getApplicationName(getBaseContext())));
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        openAlertDialog();
        
 /*       
        layout = new LinearLayout(this);
        LinearLayout layoutSub = new LinearLayout(this);
        
        TextView textViewHeading = new TextView(this);
        TextView textViewContent = new TextView(this);
        okButtonCrash = new Button(this);
        cancelButtonCrash = new Button(this);
        
        Display display = getWindowManager().getDefaultDisplay();
		int screenWidth = display.getWidth();
		int screenHeight = display.getHeight(); 
		System.out.println("screenWidth="+screenWidth+";screenHeight="+screenHeight);		
		int alertBoxWidth = (screenWidth * 1)/3;
		int alertBoxHeight = (screenHeight * 1)/3;
        
        
        layout.setBackgroundColor(Color.parseColor("#FFFFFF"));
        layout.setOrientation(LinearLayout.VERTICAL);
//        LinearLayout.LayoutParams paramsHead = new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        layout.setLayoutParams(paramsHead);
//	
        textViewHeading.setText(" ALERT ");
        textViewHeading.setPadding(5, 20, 5, 20);
        textViewHeading.setTextColor(Color.parseColor("#FFFFFF"));
        textViewHeading.setBackgroundColor(Color.parseColor("#007fff"));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,(alertBoxHeight*1)/4);//50);
//            params.weight = 1.0f;
            params.gravity=Gravity.CENTER;

        textViewHeading.setLayoutParams(params);
        textViewHeading.setTextSize(16);
        
        

        
        
        LinearLayout.LayoutParams paramsSub = new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT,  (alertBoxHeight*2)/4);//100);
//            params.weight = 1.0f;
        paramsSub.gravity=Gravity.CENTER;
        textViewContent.setLayoutParams(paramsSub);
        textViewContent.setText("Looks like something went wrong, Sorry for the inconvenience. We will analyze the issue.");
        textViewContent.setPadding(5, 10, 5, 20);
        textViewContent.setTextColor(Color.parseColor("#000000"));
        textViewContent.setTextSize(14);
        textViewContent.setGravity(Gravity.CENTER);
        

        LinearLayout.LayoutParams paramsLayoutSub = new LinearLayout.LayoutParams(
        		alertBoxWidth, (alertBoxHeight*1)/4);//
//      LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        paramsLayoutSub.gravity=Gravity.CENTER_HORIZONTAL;
        layoutSub.setLayoutParams(paramsLayoutSub);
        layoutSub.setOrientation(LinearLayout.HORIZONTAL);
        layoutSub.setBackgroundColor(Color.parseColor("#FFFFFF"));
            
        
        
        LinearLayout.LayoutParams paramsOkButtonCrash = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);//(alertBoxWidth, alertBoxHeight);//(150, 70);
        paramsOkButtonCrash.weight = 1.0f;
        paramsOkButtonCrash.gravity=Gravity.CENTER;
        paramsOkButtonCrash.bottomMargin = 10;
        paramsOkButtonCrash.rightMargin = 20;
        okButtonCrash.setLayoutParams(paramsOkButtonCrash);
        okButtonCrash.setText("OK");
        okButtonCrash.setBackgroundColor(Color.parseColor("#007FFF"));
        
        
        LinearLayout.LayoutParams paramsCancelButtonCrash = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);//(150, 70);
        paramsCancelButtonCrash.weight = 1.0f;
        paramsCancelButtonCrash.gravity=Gravity.CENTER;
        paramsCancelButtonCrash.bottomMargin = 10;
        paramsCancelButtonCrash.leftMargin = 20;
        cancelButtonCrash.setLayoutParams(paramsCancelButtonCrash);
        cancelButtonCrash.setText("Cancel");
        cancelButtonCrash.setBackgroundColor(Color.parseColor("#007FFF"));
    		    
        layout.addView(textViewHeading);
        layout.addView(textViewContent);
        
        layoutSub.setOrientation(LinearLayout.HORIZONTAL);
        layoutSub.addView(okButtonCrash);
        layoutSub.addView(cancelButtonCrash);
        layout.addView(layoutSub);
        
        
//        setContentView(layout);
//        setContentView(R.layout.floatingactivity);
        //** If user clicks OK, Send details to Server 
       okButtonCrash.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
					new loadClass(CrashReportActivity.this,(View)findViewById(
						android.R.id.content).getRootView()).execute();
			}
		});
        
       //** If user clicks CANCEL, close the application 
       cancelButtonCrash.setOnClickListener(new OnClickListener() {			
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
*/        
    }
    

	private void openAlertDialog() {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder = new AlertDialog.Builder(
				CrashReportActivity.this);
		builder.setCancelable(true);
		builder.setTitle("ALERT");
		builder.setMessage("Looks like something went wrong, Sorry for the inconvenience. We will analyze the issue.");
		builder.setInverseBackgroundForced(true);
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

				new loadClass(CrashReportActivity.this,(View)findViewById(
						android.R.id.content).getRootView()).execute();
				
//				CrashReportActivity.this.finish();
				dialog.dismiss();

			}
		});
		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				try {
					crashReportDB_Factory.deleteCrashReport(applicationInformation.getApplicationName(getBaseContext()), crashReportData.getCurrentDate());
				} catch (NameNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				CrashReportActivity.this.finish();
				dialog.dismiss();
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
		
	 
		Button b = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
		if (b != null) {
//			b.setBackgroundResource(R.drawable.button_press);
//			b.setTextColor(getResources().getColor(R.color.textcolor));
		}
		Button bq = alert.getButton(DialogInterface.BUTTON_POSITIVE);
		if (bq != null) {
//			bq.setBackgroundResource(R.drawable.button_press);
//			bq.setTextColor(getResources().getColor(R.color.textcolor));
		}
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
