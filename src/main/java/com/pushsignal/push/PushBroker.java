package com.pushsignal.push;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class PushBroker {

	@Autowired
	private Map<String, PushClient> clients = new HashMap<String, PushClient>();

	public void sendMessage(final String deviceType, final String deviceId, final String registrationId, final String message) {
		final PushClient pushClient = clients.get("PushClient" + deviceType);
		if (pushClient == null) {
			throw new IllegalArgumentException("deviceType " + deviceType + " not recognized."
					+ "  Available deviceTypes (ignore PushClient prefix): "
					+ clients.keySet().toString());
		}
		PushClientCommandImpl pushClientCommand = new PushClientCommandImpl(pushClient, deviceId, registrationId, message);
		pushClientCommand.queue();
	}

}
