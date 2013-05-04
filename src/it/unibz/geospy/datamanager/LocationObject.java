package it.unibz.geospy.datamanager;

/**
 * Object of this class will be sent to the server through SOA services. So it
 * must contain all the necessary data.
 * @author Dainius Jocas
 *
 */
public class LocationObject {

	/*
	 * List of attributes.
	 */
	private String imei;
	private String latitude;
	private String longitude;
	private String timestamp;
	
	/**
	 * Constructor
	 * @param pIMEI
	 * @param pLatitude
	 * @param pLongitude
	 * @param pTimestamp
	 */
	public LocationObject(String pIMEI, String pLatitude, String pLongitude, 
			String pTimestamp) {
		this.imei = pIMEI;
		this.latitude = pLatitude;
		this.latitude = pLongitude;
		this.timestamp = pTimestamp;
	}
	
	/**
	 * All the getters and setters.
	 */
	public void setIMEI(String pIMEI) {
		this.imei = pIMEI;
	}
	public String getIMEI() {
		return this.imei;
	}
	public void setLatitude(String pLatitude) {
		this.latitude = pLatitude;
	}
	public String getLatitude() {
		return this.latitude;
	}
	public void setLongitude(String pLongitude) {
		this.longitude = pLongitude;
	}
	public String getLongitude() {
		return this.longitude;
	}
	public void setTimestamp(String pTimestamp) {
		this.timestamp = pTimestamp;
	}
	public String getTimestamp() {
		return this.timestamp;
	}
}
