package com.pushsignal.logic;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import com.pushsignal.domain.Event;
import com.pushsignal.domain.TriggerPermissionEnum;

@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners( {
		DependencyInjectionTestExecutionListener.class,
		DirtiesContextTestExecutionListener.class,
		TransactionalTestExecutionListener.class })
@ContextConfiguration(locations = { "file:WebRoot/WEB-INF/applicationContext.xml" })
@Transactional
public class TriggerLogicIntegrationTest {
	private static final String TEST_EMAIL1 = "bob@bob.com";
	private static final String TEST_EMAIL2 = "chuck@chuck.com";
	
	@Autowired
	private UserLogic userLogic;
	
	@Autowired
	private EventLogic eventLogic;

	@Autowired
	private TriggerLogic triggerLogic;

	@Test
	@Rollback(true)
	public void testTriggerEvent() {
		userLogic.createUser(TEST_EMAIL1, "Bob Barker", "description");
		userLogic.createUser(TEST_EMAIL2, "Chuck Chester", "description");
		final Event event = eventLogic.createEvent(TEST_EMAIL1, "Test Event", "description", TriggerPermissionEnum.OWNER_ONLY, true);
		eventLogic.joinEvent(TEST_EMAIL2, event.getEventId());
		triggerLogic.createTrigger(TEST_EMAIL1, event.getEventId());
	}
}
