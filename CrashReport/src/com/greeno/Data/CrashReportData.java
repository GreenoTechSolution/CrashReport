/**
* @author Ramya.D
* @Project Crash Report
* @Date 21.08.2014
* Copyright (C) 2014, Greeno Tech Solutions Pvt. Ltd.
*/

package com.greeno.Data;

public class CrashReportData {
	
	private long currentDate;
	private String geoLocation;
	private String appName;
	private String appVersionName;
	private String appVersionCode;
	private String deviceBrand;
	private String deviceOSVersion;
	private String deviceModel;
	private String deviceSDKNo;
	private String stackTrace;
	
	private String isInternetAvailable;
	private String typeOfInternet ;
	
	private String mobileNumber;
	
	
	public long getCurrentDate() {
		return currentDate;
	}
	public void setCurrentDate(long currentDate) {
		this.currentDate = currentDate;
	}
	public String getGeoLocation() {
		return geoLocation;
	}
	public void setGeoLocation(String geoLocation) {
		this.geoLocation = geoLocation;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getAppVersionName() {
		return appVersionName;
	}
	public void setAppVersionName(String appVersionName) {
		this.appVersionName = appVersionName;
	}
	public String getAppVersionCode() {
		return appVersionCode;
	}
	public void setAppVersionCode(String appVersionCode) {
		this.appVersionCode = appVersionCode;
	}
	public String getDeviceBrand() {
		return deviceBrand;
	}
	public void setDeviceBrand(String deviceBrand) {
		this.deviceBrand = deviceBrand;
	}
	public String getDeviceOSVersion() {
		return deviceOSVersion;
	}
	public void setDeviceOSVersion(String deviceOSVersion) {
		this.deviceOSVersion = deviceOSVersion;
	}
	public String getDeviceModel() {
		return deviceModel;
	}
	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}
	public String getDeviceSDKNo() {
		return deviceSDKNo;
	}
	public void setDeviceSDKNo(String deviceSDKNo) {
		this.deviceSDKNo = deviceSDKNo;
	}
	public String getStackTrace() {
		return stackTrace;
	}
	public void setStackTrace(String stackTrace) {
		this.stackTrace = stackTrace;
	}
	public String getIsInternetAvailable() {
		return isInternetAvailable;
	}
	public void setIsInternetAvailable(String isInternetAvailable) {
		this.isInternetAvailable = isInternetAvailable;
	}
	public String getTypeOfInternet() {
		return typeOfInternet;
	}
	public void setTypeOfInternet(String typeOfInternet) {
		this.typeOfInternet = typeOfInternet;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	
	
}
