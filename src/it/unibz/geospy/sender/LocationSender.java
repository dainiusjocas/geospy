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
	/**
	 * Information about SOAP service that is used.
	 * TODO(dainius) these parameters will change, because now server is on my 
	 * laptop and in UNIBZ network
	 */
	private static final String NAMESPACE = "http://geospy";
	private static String URL= 
			"http://10.7.153.98:8080/geospy_web/services/StoreLocationService?wsdl";
	private static final String METHOD_NAME = "storeLocation";
	private static final String SOAP_ACTION = 
			"http://10.7.153.98:8080/geospy_web/storeLocation";
	
	/**
	 * Send SOAP message to the server with location information.
	 * @param pLocationObject
	 * @return
	 */
	public static boolean sendLocation(LocationObject pLocationObject) {
		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME); 
		request.addProperty("imei", pLocationObject.getIMEI());
		request.addProperty("latitude", pLocationObject.getLatitude());
		request.addProperty("longitude", pLocationObject.getLongitude());
		request.addProperty("timestamp", pLocationObject.getTimestamp());
		
		SoapSerializationEnvelope envelope = 
				new SoapSerializationEnvelope(SoapEnvelope.VER11); 
		envelope.setOutputSoapObject(request);
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			SoapPrimitive  resultsRequestSOAP =
					(SoapPrimitive) envelope.getResponse();
		} catch(Exception e) {
			// Probably server is down.
			e.printStackTrace();
		}
		return true;
	}

}
