package com.pushsignal.push;

import java.io.IOException;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

public class PushClientCommandImpl extends HystrixCommand<String> {
	
	private PushClient pushClient;
	private String deviceId;
	private String registrationId;
	private String message;
	
	public PushClientCommandImpl(final PushClient pushClient, final String deviceId, final String registrationId, final String message) {
		super(Setter
				.withGroupKey(HystrixCommandGroupKey.Factory.asKey("PushClientCommand"))
				);
		this.pushClient = pushClient;
		this.deviceId = deviceId;
		this.registrationId = registrationId;
		this.message = message;
	}

	@Override
	protected String run() {
		try {
			pushClient.sendMessage(deviceId, registrationId, message);
		} catch (final IOException e) {
			throw new RuntimeException("PushClient failure", e);
		}
		return null;
	}

}
