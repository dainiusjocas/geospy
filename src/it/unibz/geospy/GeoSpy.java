package it.unibz.geospy;

import android.app.Application;
import android.content.Context;

/**
 * Purpose of this class is to statically get Context of the application
 * @author Dainius Jocas
 *
 */
public class GeoSpy extends Application {

	private static Context mContext;
	
	/**
	 * Called when the application is starting, before any activity, service, 
	 * or receiver objects (excluding content providers) have been created.
	 */
	public void onCreate() {
		super.onCreate();
		GeoSpy.mContext = getApplicationContext();
	}
	
	/**
	 * Statically returns Context object
	 * @return Context
	 */
	public static Context getAppContext() {
        return GeoSpy.mContext;
    }
}
