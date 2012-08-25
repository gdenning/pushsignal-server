package com.pushsignal.push;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pushsignal.domain.Event;
import com.pushsignal.domain.EventMember;
import com.pushsignal.domain.Trigger;
import com.pushsignal.domain.TriggerAlert;
import com.pushsignal.domain.User;

@Service
public class Notifier {
		
	@Autowired
	private NotificationQueue queue;

	public void sendNotifications(Trigger trigger, String message, IdStrategy idStrategy, User... excludes) {
		List<User> users = createExclusionList(excludes);
		for (final TriggerAlert triggerAlert : trigger.getTriggerAlerts()) {
			if (!users.contains(triggerAlert.getUser())) {
				queue.notify(new NotificationQueue.Notification(triggerAlert.getUser(), message + idStrategy.getMessage(triggerAlert.getTriggerAlertId())));
			}
		}
	}
	
	public void sendNotifications(User user, String message, StaticId idStrategy) {
		queue.notify(new NotificationQueue.Notification(user, message + idStrategy.getMessage(0)));
	}
	
	public void sendNotifications(Event event, String message, IdStrategy idStrategy, User... excludes) {
		List<User> users = createExclusionList(excludes);
		for (final EventMember eventMember : event.getMembers()) {
			if (!users.contains(eventMember.getUser())) {
				queue.notify(new NotificationQueue.Notification(eventMember.getUser(), message + idStrategy.getMessage(eventMember.getEventMemberId())));
			}
		}
	}
	
	private List<User> createExclusionList(User... excludes) {
		List<User> users = new ArrayList<User>();
		if (excludes != null) {
			users.addAll(Arrays.asList(excludes));
		}
		return users;
	}

	
	public static PerTriggerId perTriggerId() {
		return new PerTriggerId();
	}

	public static StaticId staticId(long id) {
		return new StaticId(id);
	}

	public interface IdStrategy {
		public String getMessage(long id);
	}
	
	public static class PerTriggerId implements IdStrategy {

		public PerTriggerId() {
		}
		
		public String getMessage(long id) {
			return String.valueOf(id);
		}		
	}
	
	public static class StaticId implements IdStrategy {
		private final long id;

		public StaticId(long id) {
			this.id = id;
		}
		
		public String getMessage(long id) {
			return String.valueOf(this.id);
		}
	}

}