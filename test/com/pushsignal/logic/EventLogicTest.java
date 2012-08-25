package com.pushsignal.logic;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.pushsignal.dao.EventDAO;
import com.pushsignal.domain.Event;
import com.pushsignal.domain.TriggerPermissionEnum;
import com.pushsignal.exceptions.BadRequestException;
import com.pushsignal.push.Notifier;

public class EventLogicTest extends AbstractTest {
	
	private EventLogic eventLogic;
	
	private ActivityLogic mockActivityLogic;
	
	private EventDAO mockEventDAO;

	@Before
	public void setup() {
		super.setup();
		
		mockEventDAO = mock(EventDAO.class);
		
		mockActivityLogic = mock(ActivityLogic.class);
		
		Notifier mockNotifier = mock(Notifier.class);

		EventLogicTransactional eventLogicTransactional = new EventLogicTransactional();
		eventLogicTransactional.setEventDAO(mockEventDAO);
		
		eventLogic = new EventLogic();
		eventLogic.setPersist(eventLogicTransactional);
		eventLogic.setUserDAO(mockUserDAO);
		eventLogic.setEventDAO(mockEventDAO);
		eventLogic.setActivityLogic(mockActivityLogic);
		eventLogic.setNotifier(mockNotifier);
	}

	@Test
	public void testGetAllEvents() {
		authenticateAs(USER_EMAIL_EVENT_MEMBER, USER_NAME_EVENT_MEMBER);
		assertNotNull(eventLogic.getAllEvents(USER_EMAIL_EVENT_MEMBER));
	}

	@Test
	public void testGetPublicEvents() {
		authenticateAs(USER_EMAIL_EVENT_MEMBER, USER_NAME_EVENT_MEMBER);
		assertNotNull(eventLogic.getPublicEvents());
	}

	@Test
	public void testGetPrivateEvent() {
		authenticateAs(USER_EMAIL_EVENT_MEMBER, USER_NAME_EVENT_MEMBER);
		when(mockEventDAO.findEventByPrimaryKey(EVENT_ID)).thenReturn(createEvent(false, true));
		assertNotNull(eventLogic.getEvent(USER_EMAIL_EVENT_MEMBER, EVENT_ID));
	}

	@Test
	public void testGetPublicEvent() {
		authenticateAs(USER_EMAIL_EVENT_MEMBER, USER_NAME_EVENT_MEMBER);
		when(mockEventDAO.findEventByPrimaryKey(EVENT_ID)).thenReturn(createEvent(true, true));
		assertNotNull(eventLogic.getEvent(USER_EMAIL_EVENT_MEMBER, EVENT_ID));
	}

	@Test
	public void testCreateEvent() {
		authenticateAs(USER_EMAIL_EVENT_OWNER, USER_NAME_EVENT_OWNER);
		eventLogic.createEvent(USER_EMAIL_EVENT_OWNER, "Geoff", "", TriggerPermissionEnum.OWNER_ONLY, false);
	}

	@Test
	public void testUpdateEvent() {
		authenticateAs(USER_EMAIL_EVENT_OWNER, USER_NAME_EVENT_OWNER);
		Event event = createEvent(false, true);
		when(mockEventDAO.findEventByPrimaryKey(EVENT_ID)).thenReturn(event);
		when(mockEventDAO.store(event)).thenReturn(event);
		eventLogic.updateEvent(USER_EMAIL_EVENT_OWNER, EVENT_ID, "Geoff", "", TriggerPermissionEnum.OWNER_ONLY, false);
	}

	@Test
	public void testJoinPublicEvent() {
		authenticateAs(USER_EMAIL_EVENT_MEMBER, USER_NAME_EVENT_MEMBER);
		Event event = createEvent(true, false);
		when(mockEventDAO.findEventByPrimaryKey(EVENT_ID)).thenReturn(event);
		when(mockEventDAO.store(event)).thenReturn(event);
		eventLogic.joinEvent(USER_EMAIL_EVENT_MEMBER, EVENT_ID);
	}

	@Test
	public void testJoinPublicEventAlreadyMember() {
		authenticateAs(USER_EMAIL_EVENT_MEMBER, USER_NAME_EVENT_MEMBER);
		Event event = createEvent(true, true);
		when(mockEventDAO.findEventByPrimaryKey(EVENT_ID)).thenReturn(event);
		when(mockEventDAO.store(event)).thenReturn(event);
		try {
			eventLogic.joinEvent(USER_EMAIL_EVENT_MEMBER, EVENT_ID);
			fail("Should not be able to join public event if user is already a member.");
		} catch (BadRequestException ex) {
			// Correct behavior
		}
	}

	@Test
	public void testJoinPrivateEvent() {
		authenticateAs(USER_EMAIL_EVENT_MEMBER, USER_NAME_EVENT_MEMBER);
		Event event = createEvent(false, false);
		when(mockEventDAO.findEventByPrimaryKey(EVENT_ID)).thenReturn(event);
		when(mockEventDAO.store(event)).thenReturn(event);
		try {
			eventLogic.joinEvent(USER_EMAIL_EVENT_MEMBER, EVENT_ID);
			fail("Should not be able to join private event without invite.");
		} catch (BadRequestException ex) {
			// Correct behavior
		}
	}

	@Test
	public void testLeaveEvent() {
		authenticateAs(USER_EMAIL_EVENT_MEMBER, USER_NAME_EVENT_MEMBER);
		Event event = createEvent(false, true);
		when(mockEventDAO.findEventByPrimaryKey(EVENT_ID)).thenReturn(event);
		when(mockEventDAO.store(event)).thenReturn(event);
		eventLogic.leaveEvent(USER_EMAIL_EVENT_MEMBER, EVENT_ID);
	}

	@Test
	public void testDeleteEventAsEventMember() {
		authenticateAs(USER_EMAIL_EVENT_MEMBER, USER_NAME_EVENT_MEMBER);
		Event event = createEvent(false, true);
		when(mockEventDAO.findEventByPrimaryKey(EVENT_ID)).thenReturn(event);
		try {
			eventLogic.deleteEvent(USER_EMAIL_EVENT_MEMBER, EVENT_ID);
			fail("Only owner should be able to delete event");
		} catch (BadRequestException ex) {
			// Correct behavior
		}
	}

	@Test
	public void testDeleteEventAsEventOwner() {
		authenticateAs(USER_EMAIL_EVENT_OWNER, USER_NAME_EVENT_OWNER);
		Event event = createEvent(false, true);
		when(mockEventDAO.findEventByPrimaryKey(EVENT_ID)).thenReturn(event);
		eventLogic.deleteEvent(USER_EMAIL_EVENT_OWNER, EVENT_ID);
	}
}
