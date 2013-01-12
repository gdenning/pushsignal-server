package com.pushsignal.push;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pushsignal.http.HttpExecutor;
import com.pushsignal.http.HttpResponse;
import com.pushsignal.logic.ConfigC2dmLogic;

@Service(value="PushClientC2dm")
public class PushClientC2dmImpl implements PushClient {
	private static final Logger LOG = LoggerFactory.getLogger(PushClientC2dmImpl.class);

	private static final String C2DM_POST_URL = "https://android.apis.google.com/c2dm/send";
	
	@Autowired
	private ConfigC2dmLogic configC2dmLogic;
	
	private int collapseKey;

	public PushClientC2dmImpl() {
		collapseKey = new Random().nextInt();
	}

	public synchronized void sendMessage(final String deviceId, final String registrationId, final String message) {
		final Map<String, String> params = new HashMap<String, String>();
		params.put("registration_id", registrationId);
		params.put("collapse_key", "PushSignal" + collapseKey);
		params.put("data.message", message);
		final Map<String, String> requestProperties = new HashMap<String, String>();
		requestProperties.put("Authorization", "GoogleLogin auth=" + configC2dmLogic.getAuthToken());

		final HttpResponse httpResponse = HttpExecutor.executePost(C2DM_POST_URL, requestProperties, params);

		if (httpResponse.getStatusCode() == HttpServletResponse.SC_OK) {
			// Do nothing - everything worked
		} else if ((httpResponse.getStatusCode() == HttpServletResponse.SC_UNAUTHORIZED) ||
				(httpResponse.getStatusCode() == HttpServletResponse.SC_FORBIDDEN)) {
			// The token is too old - return false to retry later, will fetch the token
			// from DB. This happens if the password is changed or token expires. Either admin
			// is updating the token, or Update-Client-Auth was received by another server,
			// and next retry will get the good one from database.
			LOG.warn("Error while sending C2DM message: AuthToken too old - invalidating token and retrying.");
			configC2dmLogic.invalidateAuthToken();
			sendMessage(deviceId, registrationId, message);
		} else {
			LOG.error("Error while sending C2DM message: HttpStatusCode " + httpResponse.getStatusCode());
		}
	}
}
