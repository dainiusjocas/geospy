package it.unibz.geospy;

import java.util.ArrayList;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import it.unibz.geospy.R;
import it.unibz.geospy.database.DBAdapter;
import it.unibz.geospy.datamanager.CoordinatesProvider;
import it.unibz.geospy.datamanager.IMEIProvider;
import it.unibz.geospy.datamanager.LocationObject;
import it.unibz.geospy.sender.LocationSender;
import it.unibz.geospy.sender.NetworkManager;

/**
 * Main activity of an Application. When this activity is turned on the spying 
 * process starts.
 * @author Dainius Jocas
 *
 */
public class LocationSpy extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_spy);
        final CoordinatesProvider aCoordinatesProvider = 
        		new CoordinatesProvider(this.getApplicationContext());
        final Context aContext = this.getApplicationContext();
        
        final Button button_locaton = (Button) findViewById(R.id.button2);
        button_locaton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	String anIMEI = IMEIProvider.getIMEI(aContext);
          
            	Location aLocation = CoordinatesProvider.getLocation();
            	String latitude = String.valueOf(aLocation.getLatitude());
            	String longitude = String.valueOf(aLocation.getLongitude());
            	Toast.makeText(aContext, anIMEI + "|" + latitude + "|" + longitude, Toast.LENGTH_LONG).show();
            	
                // Perform action on click
//            	DBAdapter aDBAdapter = new DBAdapter(aContext);
//            	ArrayList<LocationObject> locations =
//            			aDBAdapter.getListOfNotSyncedLocations();
//            	Log.e("DAINIUS", locations.get(0).getIMEI() + " | " + locations.get(0).getLatitude() + " | ");
            }
        });
        
        /*
         * Check if network is available
         */
        final Button button_network = (Button) findViewById(R.id.button3);
        button_network.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	boolean networkAvailable = 
            			NetworkManager.isNetworkAvailable(aContext);
            	String message = "Network is ";
            	if (networkAvailable) {
            		message = message + "available";
//                	LocationSender.soapTest(aContext);
                	String anIMEI = IMEIProvider.getIMEI(aContext);
                    
                	Location aLocation = CoordinatesProvider.getLocation();
                	String latitude = String.valueOf(aLocation.getLatitude());
                	String longitude = String.valueOf(aLocation.getLongitude());
                	LocationObject lo = 
                			new LocationObject(anIMEI, latitude, longitude,
                					String.valueOf(aLocation.getTime()));
                	LocationSender.sendLocation(lo);
            	} else {
            		message = message + " not available";
            	}
            	Toast.makeText(aContext, message, Toast.LENGTH_LONG).show();

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.location_spy, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // So far menu as only one item.
        switch (item.getItemId()) {
            case R.id.action_settings:
            	startActivityForResult(
            			new Intent(android.provider.Settings.
            					ACTION_LOCATION_SOURCE_SETTINGS), 0);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
    /**
     * Exiting the Acivity
     */
    public void onDestroy() {
    	super.onDestroy();
    	CoordinatesProvider.stopUpdates();
    	Toast.makeText(getApplicationContext(), 
    			"To conserve battery you may want to manage location services",
    			Toast.LENGTH_LONG).show();
    }
    
}
