package it.unibz.geospy;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import it.unibz.geospy.R;
import it.unibz.geospy.database.DBAdapter;
import it.unibz.geospy.datamanager.CoordinatesProvider;
import it.unibz.geospy.datamanager.LocationObject;

public class LocationSpy extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_spy);
        CoordinatesProvider.getCoordinates(this);
        final Context aContext = this.getApplicationContext();
        
        final Button button = (Button) findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
            	DBAdapter aDBAdapter = new DBAdapter(aContext);
            	ArrayList<LocationObject> locations =
            			aDBAdapter.getListOfNotSyncedLocations();
            	Log.e("DAINIUS", locations.get(0).getIMEI() + " | " + locations.get(0).getLatitude() + " | ");
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.location_spy, menu);
        return true;
    }
    
}
