package com.pushsignal.logic;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.pushsignal.dao.UserDAO;
import com.pushsignal.domain.Event;
import com.pushsignal.domain.EventMember;
import com.pushsignal.domain.Trigger;
import com.pushsignal.domain.TriggerAlert;
import com.pushsignal.domain.TriggerAlert.TriggerAlertStatusEnum;
import com.pushsignal.domain.TriggerPermissionEnum;
import com.pushsignal.domain.User;

public abstract class AbstractTest {
	protected static final String USER_NAME_EVENT_OWNER = "Mike";
	protected static final String USER_EMAIL_EVENT_OWNER = "mike@gmail.com";
	protected static final String USER_NAME_EVENT_MEMBER = "Bob";
	protected static final String USER_EMAIL_EVENT_MEMBER = "bob@gmail.com";
	protected static final String USER_NAME_CHUCK = "Chuck";
	protected static final String USER_EMAIL_CHUCK = "chuck@gmail.com";
	protected static final int USER_ID = 1;
	protected static final int EVENT_ID = 2;
	protected static final long TRIGGER_ID = 3;
	protected static final long TRIGGER_ALERT_ID = 4;
	
	protected UserDAO mockUserDAO;
	
	protected void setup() {
		mockUserDAO = mock(UserDAO.class);
	}

	protected void authenticateAs(String email, String name) {
		when(mockUserDAO.findUserByEmail(email)).thenReturn(createUser(email, name));
	}

	protected User createUser(String email, String name) {
		User user = new User();
		user.setUserId(USER_ID);
		user.setName(name);
		user.setEmail(email);
		return user;
	}

	protected Event createEvent(boolean isPublic, boolean isMember) {
		return createEvent(isPublic, isMember, null);
	}

	protected Event createEvent(boolean isPublic, boolean isMember, TriggerPermissionEnum triggerPermission) {
		Event event = new Event();

		if (isMember) {
			EventMember eventMember = new EventMember();
			eventMember.setUser(createUser(USER_EMAIL_EVENT_MEMBER, USER_NAME_EVENT_MEMBER));
			eventMember.setEvent(event);
			event.getMembers().add(eventMember);
		}
		
		event.setEventId(EVENT_ID);
		event.setPublicFlag(isPublic);
		event.setTriggerPermission(triggerPermission);
		event.setOwner(createUser(USER_EMAIL_EVENT_OWNER, USER_NAME_EVENT_OWNER));
		return event;
	}

	protected Trigger createTrigger(boolean isMember) {
		Trigger trigger = new Trigger();

		if (isMember) {
			TriggerAlert triggerMember = new TriggerAlert();
			triggerMember.setTriggerAlertId(TRIGGER_ALERT_ID);
			triggerMember.setStatus(TriggerAlertStatusEnum.ACTIVE);
			triggerMember.setUser(createUser(USER_EMAIL_EVENT_MEMBER, USER_NAME_EVENT_MEMBER));
			triggerMember.setTrigger(trigger);
			trigger.getTriggerAlerts().add(triggerMember);
		}
		
		trigger.setTriggerId(TRIGGER_ID);
		trigger.setEvent(createEvent(false, isMember));
		trigger.setUser(createUser(USER_EMAIL_EVENT_OWNER, USER_NAME_EVENT_OWNER));
		return trigger;
	}
}
