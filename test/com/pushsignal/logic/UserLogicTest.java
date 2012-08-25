package com.pushsignal.logic;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

import org.jasypt.util.password.PasswordEncryptor;
import org.junit.Before;
import org.junit.Test;

import com.pushsignal.dao.EventInviteDAO;
import com.pushsignal.dao.UserDeviceDAO;
import com.pushsignal.domain.User;
import com.pushsignal.domain.UserDevice;
import com.pushsignal.email.EmailClient;

public class UserLogicTest extends AbstractTest {

	private UserLogic userLogic;
	
	private ActivityLogic mockActivityLogic;

	private PasswordEncryptor mockPasswordEncryptor;

	private EventInviteDAO mockEventInviteDAO;
	
	private UserDeviceDAO mockUserDeviceDAO;
	
	private EmailClient mockEmailClient;
	
	@Before
	public void setup() {
		super.setup();
		
		mockActivityLogic = mock(ActivityLogic.class);
		
		mockPasswordEncryptor = mock(PasswordEncryptor.class);
		
		mockEventInviteDAO = mock(EventInviteDAO.class);
		
		mockUserDeviceDAO = mock(UserDeviceDAO.class);
		
		mockEmailClient = mock(EmailClient.class);
		
		userLogic = new UserLogic();
		userLogic.setActivityLogic(mockActivityLogic);
		userLogic.setUserDAO(mockUserDAO);
		userLogic.setPasswordEncryptor(mockPasswordEncryptor);
		userLogic.setEventInviteDAO(mockEventInviteDAO);
		userLogic.setUserDeviceDAO(mockUserDeviceDAO);
		userLogic.setEmailClient(mockEmailClient);
	}

	@Test
	public void testCreateUser() {
		when(mockUserDAO.findUserByEmail(USER_EMAIL_EVENT_MEMBER)).thenReturn(null);
		when(mockPasswordEncryptor.encryptPassword(any(String.class))).thenReturn("encryptedPassword");
		when(mockUserDAO.store(any(User.class))).thenReturn(createUser(USER_EMAIL_EVENT_MEMBER, USER_NAME_EVENT_MEMBER));
		assertNotNull(userLogic.createUser(USER_EMAIL_EVENT_MEMBER, USER_NAME_EVENT_MEMBER, ""));
		verify(mockEmailClient).sendEmail(eq(USER_EMAIL_EVENT_MEMBER), any(String.class), any(String.class));
	}

	@Test
	public void testResetUserPassword() {
		authenticateAs(USER_EMAIL_EVENT_MEMBER, USER_NAME_EVENT_MEMBER);
		when(mockUserDAO.store(any(User.class))).thenReturn(createUser(USER_EMAIL_EVENT_MEMBER, USER_NAME_EVENT_MEMBER));
		assertNotNull(userLogic.resetUserPassword(USER_EMAIL_EVENT_MEMBER));
		verify(mockEmailClient).sendEmail(eq(USER_EMAIL_EVENT_MEMBER), any(String.class), any(String.class));
	}
	
	@Test
	public void testUpdateUser() {
		authenticateAs(USER_EMAIL_EVENT_MEMBER, USER_NAME_EVENT_MEMBER);
		when(mockUserDAO.store(any(User.class))).thenReturn(createUser(USER_EMAIL_EVENT_MEMBER, USER_NAME_EVENT_MEMBER));
		assertNotNull(userLogic.updateUser(USER_EMAIL_EVENT_MEMBER, USER_NAME_EVENT_MEMBER, "1234"));
	}

	@Test
	public void testRegisterDevice() {
		authenticateAs(USER_EMAIL_EVENT_MEMBER, USER_NAME_EVENT_MEMBER);
		when(mockUserDeviceDAO.store(any(UserDevice.class))).thenReturn(new UserDevice());
		assertNotNull(userLogic.registerDevice(USER_EMAIL_EVENT_MEMBER, "C2DM", "1234", "54321"));
	}

}
