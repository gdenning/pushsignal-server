package com.pushsignal.logic;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

import com.pushsignal.dao.EventDAO;
import com.pushsignal.dao.TriggerAlertDAO;
import com.pushsignal.dao.TriggerDAO;
import com.pushsignal.domain.Trigger;
import com.pushsignal.domain.TriggerAlert;
import com.pushsignal.domain.TriggerPermissionEnum;
import com.pushsignal.push.Notifier;

public class TriggerLogicTest extends AbstractTest {

	private static final String MESSAGE = "message";

	private static final String URL_GUID = "URL_GUID";

	private TriggerLogic triggerLogic;

	private ActivityLogic mockActivityLogic;

	private EventDAO mockEventDAO;
	
	private TriggerDAO mockTriggerDAO;
	
	private TriggerAlertDAO mockTriggerAlertDAO;
	
	@Before
	public void setup() {
		super.setup();
		
		mockActivityLogic = mock(ActivityLogic.class);
		
		Notifier mockNotifier = mock(Notifier.class);
		
		mockEventDAO = mock(EventDAO.class);
		
		mockTriggerDAO = mock(TriggerDAO.class);
		
		mockTriggerAlertDAO = mock(TriggerAlertDAO.class);
		
		TriggerLogicTransactional triggerLogicTransactional = new TriggerLogicTransactional();
		triggerLogicTransactional.setEventDAO(mockEventDAO);
		triggerLogicTransactional.setTriggerDAO(mockTriggerDAO);
		triggerLogicTransactional.setTriggerAlertDAO(mockTriggerAlertDAO);

		triggerLogic = new TriggerLogic();
		triggerLogic.setUserDAO(mockUserDAO);
		triggerLogic.setTriggerDAO(mockTriggerDAO);
		triggerLogic.setPersist(triggerLogicTransactional);
		triggerLogic.setNotifier(mockNotifier);
		triggerLogic.setActivityLogic(mockActivityLogic);
	}

	@Test
	public void testGetTrigger() {
		authenticateAs(USER_EMAIL_EVENT_MEMBER, USER_NAME_EVENT_MEMBER);
		when(mockTriggerDAO.findTriggerByPrimaryKey(TRIGGER_ID)).thenReturn(createTrigger(true));
		assertNotNull(triggerLogic.getTrigger(USER_EMAIL_EVENT_MEMBER, TRIGGER_ID));
	}

	@Test
	public void testGetMissedTriggers() {
		authenticateAs(USER_EMAIL_EVENT_MEMBER, USER_NAME_EVENT_MEMBER);
		when(mockTriggerDAO.findActiveTriggersForUser(createUser(USER_EMAIL_EVENT_MEMBER, USER_NAME_EVENT_MEMBER))).thenReturn(Collections.singleton(createTrigger(true)));
		assertNotNull(triggerLogic.getMissedTriggers(USER_EMAIL_EVENT_MEMBER));
	}

	@Test
	public void testCreateTrigger() {
		authenticateAs(USER_EMAIL_EVENT_OWNER, USER_NAME_EVENT_OWNER);
		when(mockEventDAO.findEventByPrimaryKey(EVENT_ID)).thenReturn(createEvent(false, true, TriggerPermissionEnum.OWNER_ONLY));
		when(mockTriggerDAO.store(any(Trigger.class))).thenReturn(createTrigger(true));
		assertNotNull(triggerLogic.createTrigger(USER_EMAIL_EVENT_OWNER, EVENT_ID, MESSAGE));
	}

	@Test
	public void testCreateTriggerByGuid() {
		when(mockEventDAO.findEventByGuid(URL_GUID)).thenReturn(createEvent(true, true, TriggerPermissionEnum.URL_ONLY));
		when(mockTriggerDAO.store(any(Trigger.class))).thenReturn(createTrigger(true));
		assertNotNull(triggerLogic.createTriggerByGuid(URL_GUID, MESSAGE));
	}

	@Test
	public void testAckTrigger() {
		authenticateAs(USER_EMAIL_EVENT_MEMBER, USER_NAME_EVENT_MEMBER);
		Trigger trigger = createTrigger(true);
		when(mockTriggerDAO.findTriggerByPrimaryKey(TRIGGER_ID)).thenReturn(trigger);
		when(mockTriggerAlertDAO.findTriggerAlertByPrimaryKey(TRIGGER_ALERT_ID)).thenReturn(trigger.getTriggerAlerts().iterator().next());
		when(mockTriggerAlertDAO.store(any(TriggerAlert.class))).thenReturn(trigger.getTriggerAlerts().iterator().next());
		assertNotNull(triggerLogic.ackTrigger(USER_EMAIL_EVENT_MEMBER, TRIGGER_ID));
	}

	@Test
	public void testSilenceTrigger() {
		authenticateAs(USER_EMAIL_EVENT_MEMBER, USER_NAME_EVENT_MEMBER);
		Trigger trigger = createTrigger(true);
		when(mockTriggerDAO.findTriggerByPrimaryKey(TRIGGER_ID)).thenReturn(trigger);
		when(mockTriggerAlertDAO.findTriggerAlertByPrimaryKey(TRIGGER_ALERT_ID)).thenReturn(trigger.getTriggerAlerts().iterator().next());
		when(mockTriggerAlertDAO.store(any(TriggerAlert.class))).thenReturn(trigger.getTriggerAlerts().iterator().next());
		assertNotNull(triggerLogic.silenceTrigger(USER_EMAIL_EVENT_MEMBER, TRIGGER_ID));
	}

}
