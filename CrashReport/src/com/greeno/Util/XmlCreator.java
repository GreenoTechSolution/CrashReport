/**
* @author Ramya.D
* @Project Crash Report
* @Date 21.08.2014
* Copyright (C) 2014, Greeno Tech Solutions Pvt. Ltd.
*/

package com.greeno.Util;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.greeno.Data.CrashReportData;


public class XmlCreator {

	/**
	 * Serializes the user authentication details (Login information) as String
	 * in XML format Serializing means preparing in the required format
	 * 
	 * @param mUser
	 * @return xmlString
	 */
	public String getRequestXML(CrashReportData crashReportData)
	{
		String requestXML = "";

//		crashReportData.setGeoLocation(" ");
		System.out.println("=== crashReportData.getIsInternetAvailable() ======"+crashReportData.getIsInternetAvailable());
		try {
			DocumentBuilderFactory	documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.newDocument();
			
			Element rootElement = document.createElement("crashInfo");
			document.appendChild(rootElement);
			
			Element currentDateElement = document.createElement("currentDate");
			rootElement.appendChild(currentDateElement);
			currentDateElement.appendChild(document.createTextNode(""+crashReportData.getCurrentDate()));
			
			Element geoLocationElement = document.createElement("geoLocation");
			rootElement.appendChild(geoLocationElement);
			geoLocationElement.appendChild(document.createTextNode(crashReportData.getGeoLocation()));
			
			Element appNameElement = document.createElement("appName");
			rootElement.appendChild(appNameElement);
			appNameElement.appendChild(document.createTextNode(crashReportData.getAppName()));
			
			Element appVersionNameElement = document.createElement("appVersionName");
			rootElement.appendChild(appVersionNameElement);
			appVersionNameElement.appendChild(document.createTextNode(crashReportData.getAppVersionName()));
			
			Element appVersionCodeElement = document.createElement("appVersionCode");
			rootElement.appendChild(appVersionCodeElement);
			appVersionCodeElement.appendChild(document.createTextNode(crashReportData.getAppVersionCode()));
			
			Element deviceBrandNameElement = document.createElement("deviceBrandName");
			rootElement.appendChild(deviceBrandNameElement);
			deviceBrandNameElement.appendChild(document.createTextNode(crashReportData.getDeviceBrand()));
			
			Element deviceOSversionElement = document.createElement("deviceOSversion");
			rootElement.appendChild(deviceOSversionElement);
			deviceOSversionElement.appendChild(document.createTextNode(crashReportData.getDeviceOSVersion()));
			
			Element deviceModelNameElement = document.createElement("deviceModelName");
			rootElement.appendChild(deviceModelNameElement);
			deviceModelNameElement.appendChild(document.createTextNode(crashReportData.getDeviceModel()));
			
			Element deviceSDKnoElement = document.createElement("deviceSDKno");
			rootElement.appendChild(deviceSDKnoElement);
			deviceSDKnoElement.appendChild(document.createTextNode(crashReportData.getDeviceSDKNo()));
			
			Element stackTraceElement = document.createElement("stackTrace");
			rootElement.appendChild(stackTraceElement);
			stackTraceElement.appendChild(document.createTextNode(crashReportData.getStackTrace()));
			
			Element isInternetAvailableElement = document.createElement("isInternetAvailable");
			rootElement.appendChild(isInternetAvailableElement);
			isInternetAvailableElement.appendChild(document.createTextNode(crashReportData.getIsInternetAvailable()));
			
			Element typeOfInternetElement = document.createElement("typeOfInternet");
			rootElement.appendChild(typeOfInternetElement);
			typeOfInternetElement.appendChild(document.createTextNode(crashReportData.getTypeOfInternet()));
			
			Element mobileNumberElement = document.createElement("mobileNumber");
			rootElement.appendChild(mobileNumberElement);
			mobileNumberElement.appendChild(document.createTextNode(crashReportData.getMobileNumber()));
		
			
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer;
			transformer = factory.newTransformer();
		
			Properties outFormat = new Properties();
			outFormat.setProperty(OutputKeys.INDENT, "yes");
			outFormat.setProperty(OutputKeys.METHOD, "xml");
			outFormat.setProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
			outFormat.setProperty(OutputKeys.VERSION, "1.0");
			outFormat.setProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.setOutputProperties(outFormat);
			DOMSource domSource = new DOMSource(document.getDocumentElement());
			OutputStream output = new ByteArrayOutputStream();
			StreamResult result = new StreamResult(output);
			transformer.transform(domSource, result);
			requestXML = output.toString();
			
			
			
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}catch (TransformerConfigurationException e) {
			e.printStackTrace();
		}catch (TransformerException e) {
			e.printStackTrace();
		}
		System.out.println("requestXML=="+requestXML);
		return requestXML;
	}

}
