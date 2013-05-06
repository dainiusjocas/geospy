package it.unibz.geospy.datamanager;

import java.util.ArrayList;

import it.unibz.geospy.database.DBAdapter;
import it.unibz.geospy.sender.LocationSender;
import it.unibz.geospy.sender.NetworkManager;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Manages location services.
 * @author Dainius Jocas
 *
 */
public class CoordinatesProvider {

	private static Context context;
	private static LocationManager locationManager;
	private static LocationListener locationListener;

	/**
	 * Constructor initializes location provider services.
	 * @param pContext
	 */
	public CoordinatesProvider(Context pContext) {
		CoordinatesProvider.context = pContext;

		// Acquire a reference to the system Location Manager
		CoordinatesProvider.locationManager = 
				(LocationManager) pContext.getSystemService
				(Context.LOCATION_SERVICE);

		// Define a listener that responds to location updates
		CoordinatesProvider.locationListener = new LocationListener() {
			@Override
			public void onLocationChanged(Location location) {
				// Called when a new location is found by the network location
				// provider.
				// if network is available, send the location, if not store 
				// the current location in the DB
				
				DBAdapter aDBAdapter = 
						new DBAdapter(CoordinatesProvider.context);
				long modifiedLatitude = 
						(long) (location.getLatitude() * 1000000);
				long modifiedLongitude = 
						(long) (location.getLongitude() * 1000000);
				if (NetworkManager.isNetworkAvailable(context)) {
					// available network
					// send current location
					// sync all unsyced
					// TODO: send a list or every location a piece.
					LocationObject aLocationObject = 
							new LocationObject(IMEIProvider.getIMEI(context),
									String.valueOf(modifiedLatitude),
									String.valueOf(modifiedLongitude),
									String.valueOf(location.getTime()));
					LocationSender.sendLocation(aLocationObject);
					// sync unsynced location:
					// retrieve list of location objects from DB
					// if there are any, send them.
					// TODO: potential place to lose some records
					ArrayList<LocationObject> locations =
							aDBAdapter.getListOfNotSyncedLocations();
					for (LocationObject aLocation : locations) {
						LocationSender.sendLocation(aLocation);
					}
					Toast.makeText(context, "Network is awailable", Toast.LENGTH_LONG).show();
				} else {
					// no network connection. store location in the DB.
					aDBAdapter.storeLocation(modifiedLatitude,
							modifiedLongitude, 
							location.getTime());
					Toast.makeText(context, "No Network", Toast.LENGTH_LONG).show();
				}
				
			}

			@Override
			public void onStatusChanged(String provider, int status,
					Bundle extras) {
				Toast.makeText(CoordinatesProvider.context,
						"onStatusChanged", Toast.LENGTH_LONG).show();
			}

			@Override
			public void onProviderEnabled(String provider) {
				Toast.makeText(CoordinatesProvider.context,
						"onProviderEnabled", Toast.LENGTH_LONG).show();
			}

			@Override
			public void onProviderDisabled(String provider) {
				Toast.makeText(CoordinatesProvider.context,
						"onProviderDisabled", Toast.LENGTH_LONG).show();
			}
		};

		/*
		 *  Register the listener with the Location Manager to receive
		 *  location updates. Currently using both.
		 */
		CoordinatesProvider.locationManager.requestLocationUpdates(
				LocationManager.GPS_PROVIDER,
				60, // minimum time interval between notifications	
				0, // minimum change in distance between notifications
				locationListener);
		CoordinatesProvider.locationManager.requestLocationUpdates(
				LocationManager.NETWORK_PROVIDER, 
				60,	
				0, 
				locationListener);
	}
	/**
	 * Get last known GPS location. If there is a GPS location available, then
	 * return GPS, if not - GSM and WIFI location.
	 * TODO: should I be concerned about LocationManager.NETWORK_PROVIDER ??
	 * @return Location object
	 */
	public static Location getLocation() {
		Location aLocation = 
				CoordinatesProvider.locationManager.getLastKnownLocation
				(LocationManager.GPS_PROVIDER);
		if (null == aLocation) {
			aLocation = 
					CoordinatesProvider.locationManager.getLastKnownLocation
					(LocationManager.NETWORK_PROVIDER);
		}
		return aLocation;
	}

	/**
	 * Turn of receiving updates about location.
	 */
	public static void stopUpdates() {
		if (null != CoordinatesProvider.locationListener) {
			locationManager.removeUpdates(CoordinatesProvider.locationListener);
		}
	}

}
