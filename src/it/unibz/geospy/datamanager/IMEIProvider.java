package it.unibz.geospy.datamanager;

import android.content.Context;
import android.telephony.TelephonyManager;

/**
 * This class is responsible for providing devide id: IMEI or something else.
 * @author Dainius Jocas
 *
 */
public class IMEIProvider {

	/**
	 * This method get phone IMEI number or other identificator
	 * @param pContext
	 * @return
	 */
	public static String getIMEI(Context pContext) {
		String anIMEI = null;
		TelephonyManager aTelephonyManager = 
				(TelephonyManager) pContext.getSystemService(Context.TELEPHONY_SERVICE);
		/* Returns the unique device ID, for example, the IMEI for GSM and the 
		 * MEID or ESN for CDMA phones. 
		 */
		anIMEI = aTelephonyManager.getDeviceId();
		return anIMEI;
	}
}
