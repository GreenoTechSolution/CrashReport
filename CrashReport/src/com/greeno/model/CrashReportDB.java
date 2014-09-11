/**
* @author Ramya.D
* @Project Crash Report
* @Date 21.08.2014
* Copyright (C) 2014, Greeno Tech Solutions Pvt. Ltd.
*/

package com.greeno.model;

import java.util.ArrayList;

import com.greeno.Data.CrashReportData;

public interface CrashReportDB {

	public void onCreate(String applicationName);
	public void onSave(CrashReportData crashReportData);
	public ArrayList<CrashReportData> getCrashReport(String applicationName);
	public int getNumberOfAvailableList(String applicationName);
	public CrashReportData getReportByCurrentDate(String applicationName, long currentDate1);
}
