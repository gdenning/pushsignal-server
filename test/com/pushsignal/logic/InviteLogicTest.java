package com.pushsignal.logic;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.pushsignal.dao.EventDAO;
import com.pushsignal.dao.EventInviteDAO;
import com.pushsignal.dao.EventMemberDAO;
import com.pushsignal.domain.EventInvite;
import com.pushsignal.domain.EventMember;
import com.pushsignal.email.EmailClient;
import com.pushsignal.push.Notifier;

public class InviteLogicTest extends AbstractTest {

	private static final int INVITE_ID = 1;

	private static final long EVENT_ID = 2;

	private static final int EVENT_MEMBER_ID = 3;
	
	private InviteLogic inviteLogic;
	
	private EventInviteDAO mockEventInviteDAO;
	
	private EventDAO mockEventDAO;
	
	private EventMemberDAO mockEventMemberDAO;
	
	private EmailClient mockEmailClient;
	
	@Before
	public void setup() {
		super.setup();
		
		mockEventInviteDAO = mock(EventInviteDAO.class);
		
		mockEventDAO = mock(EventDAO.class);
		
		mockEventMemberDAO = mock(EventMemberDAO.class);
		
		mockEmailClient = mock(EmailClient.class);

		Notifier mockNotifier = mock(Notifier.class);

		InviteLogicTransactional inviteLogicTransactional = new InviteLogicTransactional();
		inviteLogicTransactional.setUserDAO(mockUserDAO);
		inviteLogicTransactional.setEventDAO(mockEventDAO);
		inviteLogicTransactional.setEventInviteDAO(mockEventInviteDAO);
		inviteLogicTransactional.setEventMemberDAO(mockEventMemberDAO);
		
		inviteLogic = new InviteLogic();
		inviteLogic.setPersist(inviteLogicTransactional);
		inviteLogic.setUserDAO(mockUserDAO);
		inviteLogic.setEventInviteDAO(mockEventInviteDAO);
		inviteLogic.setNotifier(mockNotifier);
		inviteLogic.setEmailClient(mockEmailClient);
	}

	@Test
	public void testGetAllInvites() {
		authenticateAs(USER_EMAIL_EVENT_MEMBER, USER_NAME_EVENT_MEMBER);
		assertNotNull(inviteLogic.getAllInvites(USER_EMAIL_EVENT_MEMBER));
	}

	@Test
	public void testGetInvite() {
		authenticateAs(USER_EMAIL_EVENT_MEMBER, USER_NAME_EVENT_MEMBER);
		when(mockEventInviteDAO.findEventInviteByPrimaryKey(INVITE_ID)).thenReturn(createEventInvite());
		assertNotNull(inviteLogic.getInvite(USER_EMAIL_EVENT_MEMBER, INVITE_ID));
	}

	@Test
	public void testCreateInvite() {
		authenticateAs(USER_EMAIL_EVENT_MEMBER, USER_NAME_EVENT_MEMBER);
		when(mockEventDAO.findEventByPrimaryKey(EVENT_ID)).thenReturn(createEvent(false, true));
		when(mockUserDAO.findUserByEmail("chuck@gmail.com")).thenReturn(createUser(USER_EMAIL_CHUCK, USER_NAME_CHUCK));
		when(mockEventInviteDAO.store(any(EventInvite.class))).thenReturn(createEventInvite());
		assertNotNull(inviteLogic.createInvite(USER_EMAIL_EVENT_MEMBER, EVENT_ID, "chuck@gmail.com"));
	}

	@Test
	public void testAcceptInvite() {
		EventInvite invite = createEventInvite();
		authenticateAs(USER_EMAIL_CHUCK, USER_NAME_CHUCK);
		when(mockEventInviteDAO.findEventInviteByPrimaryKey(INVITE_ID)).thenReturn(invite);
		when(mockEventMemberDAO.store(any(EventMember.class))).thenReturn(createEventMember(invite));
		assertNotNull(inviteLogic.acceptInvite(USER_EMAIL_CHUCK, INVITE_ID));
	}

	@Test
	public void testDeclineInvite() {
		EventInvite invite = createEventInvite();
		authenticateAs(USER_EMAIL_CHUCK, USER_NAME_CHUCK);
		when(mockEventInviteDAO.findEventInviteByPrimaryKey(INVITE_ID)).thenReturn(createEventInvite());
		when(mockEventMemberDAO.store(any(EventMember.class))).thenReturn(createEventMember(invite));
		assertNotNull(inviteLogic.declineInvite(USER_EMAIL_CHUCK, INVITE_ID));
	}

	private EventInvite createEventInvite() {
		EventInvite invite = new EventInvite();
		invite.setEventInviteId(INVITE_ID);
		invite.setUser(createUser(USER_EMAIL_CHUCK, USER_NAME_CHUCK));
		invite.setEvent(createEvent(false, true));
		return invite;
	}

	private EventMember createEventMember(EventInvite invite) {
		EventMember member = new EventMember();
		member.setEventMemberId(EVENT_MEMBER_ID);
		member.setUser(invite.getUser());
		member.setEvent(invite.getEvent());
		return member;
	}
}
