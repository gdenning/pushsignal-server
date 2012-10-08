package com.pushsignal.logic;

import java.util.Date;
import java.util.Random;
import java.util.Set;

import org.apache.log4j.Logger;
import org.jasypt.util.password.PasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pushsignal.dao.EventInviteDAO;
import com.pushsignal.dao.UserDeviceDAO;
import com.pushsignal.domain.EventInvite;
import com.pushsignal.domain.User;
import com.pushsignal.domain.UserDevice;
import com.pushsignal.email.EmailClient;
import com.pushsignal.exceptions.BadRequestException;

@Scope("singleton")
@Service
public class UserLogic extends AbstractLogic {
	private static final Logger LOG = Logger.getLogger(UserLogic.class);

	private static final String ACCEPTABLE_EMAIL_REGEX = "(?i)^[A-Z0-9._%-]+@(?:[A-Z0-9-]+\\.)+[A-Z]{2,4}$";

	private static final String PASSWORD_CHARSET = "0123456789abcdefghijklmnopqrstuvwxyz";

	private static final int PASSWORD_LENGTH = 8;

	@Autowired
	private UserDeviceDAO userDeviceDAO;

	@Autowired
	private EventInviteDAO eventInviteDAO;

	@Autowired
	private ActivityLogic activityLogic;

	@Autowired
	private PasswordEncryptor passwordEncryptor;

	@Autowired
	private EmailClient emailClient;

	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
	public User createUser(final String email,
			final String name,
			final String description) {
		LOG.debug("UserLogic.create(" + email + "," + name + "," + description + ")");

		if (!email.matches(ACCEPTABLE_EMAIL_REGEX)) {
			throw new BadRequestException("Email address " + email + " is invalid.");
		}
		if (name.isEmpty()) {
			throw new BadRequestException("Name is required.");
		}
		if (userDAO.findUserByEmail(email) != null) {
			throw new BadRequestException("Email address " + email + " is already registered.  Please login with your existing password.");
		}
		final String password = generatePassword(PASSWORD_LENGTH);
		final String encryptedPassword = passwordEncryptor.encryptPassword(password);

		User user = new User();
		user.setEmail(email);
		user.setPassword(encryptedPassword);
		user.setName(name);
		user.setDescription(description);
		user = userDAO.store(user);
		user.setUnencryptedPassword(password);

		// Update all invites with this email address to point to new user record
		final Set<EventInvite> invites = eventInviteDAO.findEventInvitesByEmail(email);
		for (EventInvite invite : invites) {
			invite.setEmail(null);
			invite.setUser(user);
			invite = eventInviteDAO.store(invite);
		}

		activityLogic.createActivity(user, "Created PushSignal account", 100);

		StringBuffer body = new StringBuffer();
		body.append("Congratulations on creating your new PushSignal account.\n\n");
		body.append("Please keep this email for your records, as it contains your automatically generated password, which you will need to login should you ever re-install PushSignal or install on a different device.\n\n");
		body.append("Password: ").append(password).append("\n\n");
		body.append("If you enjoy PushSignal, please add a review at https://market.android.com/details?id=com.pushsignal.\n");
		emailClient.sendEmail(email, "Your PushSignal account has been created", body.toString());

		return user;
	}

	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
	public User resetUserPassword(final String authenticatedEmail) {
		LOG.debug("UserLogic.resetPassword(" + authenticatedEmail + ")");

		User userMe = getAuthenticatedUser(authenticatedEmail);

		final String password = generatePassword(PASSWORD_LENGTH);
		final String encryptedPassword = passwordEncryptor.encryptPassword(password);

		userMe.setPassword(encryptedPassword);
		userMe = userDAO.store(userMe);
		userMe.setUnencryptedPassword(password);

		StringBuffer body = new StringBuffer();
		body.append("Your PushSignal account password has been reset.\n\n");
		body.append("Please keep this email for your records, as it contains your automatically generated password, which you will need to login should you ever re-install PushSignal or install on a different device.\n\n");
		body.append("Password: ").append(password).append("\n\n");
		body.append("If you enjoy PushSignal, please add a review at https://market.android.com/details?id=com.pushsignal.\n");
		emailClient.sendEmail(userMe.getEmail(), "Your PushSignal password has been reset", body.toString());

		return userMe;
	}

	private String generatePassword(final int length) {
        Random rand = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int pos = rand.nextInt(PASSWORD_CHARSET.length());
            sb.append(PASSWORD_CHARSET.charAt(pos));
        }
        return sb.toString();
    }

	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
	public User updateUser(final String authenticatedEmail,
			final String name, final String description) {
		LOG.debug("UserLogic.update(" + authenticatedEmail + "," + name + "," + description + ")");

		User userMe = getAuthenticatedUser(authenticatedEmail);

		userMe.setName(name);
		userMe.setDescription(description);
		userMe = userDAO.store(userMe);

		return userMe;
	}

	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
	public UserDevice registerDevice(final String authenticatedEmail,
			final String deviceType, final String deviceId, final String registrationId) {
		LOG.debug("UserLogic.registerDevice(" + authenticatedEmail + "," + deviceType + "," + deviceId + ")");

		final User userMe = getAuthenticatedUser(authenticatedEmail);

		// Determine if this device has already been registered, and update if it is.
		for (UserDevice device : userMe.getDevices()) {
			if (device.getDeviceType().equals(deviceType)
					&& device.getDeviceId().equals(deviceId)) {
				device.setLastSeenDate(new Date());
				device.setRegistrationId(registrationId);
				device = userDeviceDAO.store(device);

				return device;
			}
		}

		UserDevice device = new UserDevice();
		device.setUser(userMe);
		device.setDeviceType(deviceType);
		device.setDeviceId(deviceId);
		device.setRegistrationId(registrationId);
		device.setLastSeenDate(new Date());
		device = userDeviceDAO.store(device);

		return device;
	}

	public long getPoints(final long userId) {
		LOG.debug("UserLogic.getPoints(" + userId + ")");

		return userDAO.getPoints(userId);
	}

	public void setUserDeviceDAO(final UserDeviceDAO userDeviceDAO) {
		this.userDeviceDAO = userDeviceDAO;
	}

	public void setEventInviteDAO(final EventInviteDAO eventInviteDAO) {
		this.eventInviteDAO = eventInviteDAO;
	}

	public void setActivityLogic(final ActivityLogic activityLogic) {
		this.activityLogic = activityLogic;
	}

	public void setPasswordEncryptor(final PasswordEncryptor passwordEncryptor) {
		this.passwordEncryptor = passwordEncryptor;
	}

	public void setEmailClient(final EmailClient emailClient) {
		this.emailClient = emailClient;
	}
}
