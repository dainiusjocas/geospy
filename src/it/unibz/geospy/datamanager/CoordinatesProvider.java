package it.unibz.geospy.datamanager;

import it.unibz.geospy.database.DBAdapter;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.Toast;

/**
 * 
 * @author Dainius Jocas
 *
 */
public class CoordinatesProvider {

	/**
	 * 
	 * @return Location object
	 */
	public static Location getCoordinates(Context pContext) {
		Location aLocation = null;
		// the usual trick
		final Context aContext = pContext;
		// Acquire a reference to the system Location Manager
		LocationManager locationManager = 
				(LocationManager) pContext.getSystemService(Context.LOCATION_SERVICE);
		
		// Define a listener that responds to location updates
		LocationListener locationListener = new LocationListener() {
		    public void onLocationChanged(Location location) {
		        // Called when a new location is found by the network location provider.
		    	// if network is available, send the location, if not store 
		    	// the current location in the DB
		    	// makeUseOfNewLocation(location);
		    	DBAdapter aDBAdapter = new DBAdapter(aContext);
		    	long modifiedLatitude = 
		    			(long) (location.getLatitude() * 1000000);
		    	long modifiedLongitude = 
		    			(long) (location.getLongitude() * 1000000);
		    	aDBAdapter.storeLocation(modifiedLatitude,
		    			modifiedLongitude, 
		    			location.getTime());
		    	Toast.makeText(aContext, 
		    			location.getLatitude() + "\n| " +
		    			location.getLongitude() + "\n| " +
		    			location.getTime() + " ",
		    			Toast.LENGTH_LONG).show();
		    }

		    public void onProviderEnabled(String provider) {}

		    public void onProviderDisabled(String provider) {}

			@Override
			public void onStatusChanged(String provider, int status,
					Bundle extras) {
				// TODO Auto-generated method stub
				
			}
		  };

		// Register the listener with the Location Manager to receive location updates
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, // NETWORK_PROVIDER is another option
				3, // the second is the minimum time interval between notifications	
				0, // minimum change in distance between notifications
				locationListener);
		
		return aLocation;
	}
	

}
