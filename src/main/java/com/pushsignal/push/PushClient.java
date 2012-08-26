package com.pushsignal.push;

public interface PushClient {
	/**
	 * Send a message to the specified device using Push technology.
	 * 
	 * @param registrationId registration Id for the destination device
	 * @param message to send to the device
	 */
	public void sendMessage(String deviceId, String registrationId, String message);
}
