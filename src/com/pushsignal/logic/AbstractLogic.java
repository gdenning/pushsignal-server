package com.pushsignal.logic;

import org.springframework.beans.factory.annotation.Autowired;

import com.pushsignal.dao.UserDAO;
import com.pushsignal.domain.User;
import com.pushsignal.exceptions.ResourceNotFoundException;

public abstract class AbstractLogic {
	@Autowired
	protected UserDAO userDAO;

	protected User getAuthenticatedUser(final String authenticatedEmail) {
		final User userMe = userDAO.findUserByEmail(authenticatedEmail);
		if (userMe == null) {
			throw new ResourceNotFoundException("Unable to locate account for authenticated user: "
							+ authenticatedEmail);
		}
		return userMe;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
}
