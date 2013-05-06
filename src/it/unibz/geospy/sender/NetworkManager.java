package it.unibz.geospy.sender;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Class chaecks if the network access is availabe
 * @author Dainius Jocas
 *
 */
public class NetworkManager {
	
	/**
	 * Constructor
	 */
	private NetworkManager() {
		
	}
	
	/**
	 * Checks the network access. 
	 * @return 1 - accessible; 0 - no network.
	 */
	public static boolean isNetworkAvailable(Context pContext) {
		ConnectivityManager cm =
		        (ConnectivityManager) pContext.getSystemService
		        (Context.CONNECTIVITY_SERVICE);
		    NetworkInfo netInfo = cm.getActiveNetworkInfo();
		    if (netInfo != null && netInfo.isConnectedOrConnecting()) {
		        return true;
		    }
		    return false;
	}
}
