package com.pushsignal.push;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.pushsignal.http.HttpExecutor;
import com.pushsignal.http.HttpResponse;

@Service(value="PushClientGCM")
public class PushClientGcmImpl implements PushClient {

	private static final Logger LOG = LoggerFactory.getLogger(PushClientGcmImpl.class);

	private static final String GCM_API_KEY = "AIzaSyBDyyuZGPPww5j08ikk6RZwaUeVX_APboI";
	private static final String GCM_POST_URL = "https://android.googleapis.com/gcm/send";
	
	private int collapseKey;
	
	public PushClientGcmImpl() {
		collapseKey = new Random().nextInt();
	}

	public synchronized void sendMessage(final String deviceId, final String registrationId, final String message) {
		collapseKey++;
		final Map<String, String> params = new HashMap<String, String>();
		params.put("registration_id", registrationId);
		params.put("collapse_key", "PushSignal" + collapseKey);
		params.put("data.message", message);
		final Map<String, String> requestProperties = new HashMap<String, String>();
		requestProperties.put("Authorization", "key=" + GCM_API_KEY);

		final HttpResponse httpResponse = HttpExecutor.executePost(GCM_POST_URL, requestProperties, params);

		if (httpResponse.getStatusCode() == HttpServletResponse.SC_OK) {
			// Do nothing - everything worked
			LOG.debug(StringUtils.join(httpResponse.getResponseLines(), "\n"));
		} else {
			LOG.error("Error while sending GCM message: HttpStatusCode " + httpResponse.getStatusCode());
		}
	}
}
