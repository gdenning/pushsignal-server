package com.pushsignal.push;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pushsignal.domain.User;
import com.pushsignal.domain.UserDevice;

@Service
public class NotificationQueue {

	private static final Logger LOG = Logger.getLogger("com.pushsignal.push.NotificationQueue");

	public static class Notification {
		private final String message;
		private final User user;

		public Notification(final User user, final String message) {
			this.user = user;
			this.message = message;
		}

		public String getMessage() {
			return message;
		}

		public User getUser() {
			return user;
		}
	}

	@Autowired
	private PushBroker pushBroker;

	BlockingQueue<NotificationQueue.Notification> notifications = new LinkedBlockingQueue<NotificationQueue.Notification>();

	public NotificationQueue() {
		Thread thread = new Thread(new Runnable() {
			public void run() {
				while (true) {
					NotificationQueue.Notification notification;
					try {
						notification = notifications.take();
						if (notification != null) {
							pushMessageToUser(notification.getUser(), notification.getMessage());
						}
					} catch (InterruptedException e) {
						// Do nothing
					}
				}
			}
		});
		thread.start();
	}

	public void notify(final Notification notification) {
		this.notifications.offer(notification);
	}

	private void pushMessageToUser(final User user, final String message) {
		for (final UserDevice device : user.getDevices()) {
			LOG.debug("Sending " + message + " to " + device.getDeviceType() + ":" + device.getDeviceId());
			pushBroker.sendMessage(device.getDeviceType(),
					device.getDeviceId(),
					device.getRegistrationId(),
					message);
		}
	}
}