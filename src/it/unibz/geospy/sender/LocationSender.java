package it.unibz.geospy.sender;

import it.unibz.geospy.datamanager.LocationObject;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.ksoap2.serialization.PropertyInfo;

import android.content.Context;
import android.widget.Toast;

public class LocationSender {
	
	private static final String NAMESPACE = "http://geospy";
											//"http://10.7.153.98:8080/geospy_web/";
	private static String URL="http://10.7.153.98:8080/geospy_web/services/StoreLocationService?wsdl";
							//"http://192.168.1.68:7001/HelloWebService/HelloWSService?WSDL"; 
	private static final String METHOD_NAME = "storeLocation";
	private static final String SOAP_ACTION =  "http://10.7.153.98:8080/geospy_web/storeLocation";
	
	public static String soapTest(Context pContext) {
		PropertyInfo propInfo=new PropertyInfo();
		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		
		propInfo.name="dainiusjocas";
		propInfo.type=PropertyInfo.STRING_CLASS;
		request.addProperty(propInfo);  
		request.addProperty("name of the property", "value of the property");
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11); 
		envelope.setOutputSoapObject(request);
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			SoapPrimitive  resultsRequestSOAP = (SoapPrimitive) envelope.getResponse();
			Toast.makeText(pContext, resultsRequestSOAP.toString(), Toast.LENGTH_LONG).show();
		} catch(Exception e) {
			Toast.makeText(pContext, "Problems", Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
		return "moo";
	}
	
	public static boolean sendLocation(LocationObject pLocationObject) {
//		PropertyInfo propInfo=new PropertyInfo();
		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
//		propInfo.name="dainiusjocas";
//		propInfo.type=PropertyInfo.STRING_CLASS;
//		propInfo.type=PropertyInfo.
//		request.addProperty(propInfo);  
		request.addProperty("imei", pLocationObject.getIMEI());
		request.addProperty("latitude", pLocationObject.getLatitude());
		request.addProperty("longitude", pLocationObject.getLongitude());
		request.addProperty("timestamp", pLocationObject.getTimestamp());
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11); 
		envelope.setOutputSoapObject(request);
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			SoapPrimitive  resultsRequestSOAP = (SoapPrimitive) envelope.getResponse();
//			Toast.makeText(pContext, resultsRequestSOAP.toString(), Toast.LENGTH_LONG).show();
		} catch(Exception e) {
//			Toast.makeText(pContext, "Problems", Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
		return true;
	}

}
