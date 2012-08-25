package com.pushsignal.controllers;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.pushsignal.domain.User;
import com.pushsignal.domain.UserDevice;
import com.pushsignal.logic.UserLogic;
import com.pushsignal.xml.jaxb.UserDTO;
import com.pushsignal.xml.jaxb.UserDeviceDTO;

@Scope("singleton")
@Controller
public class UserController extends AbstractController {
	@Autowired
	private UserLogic userLogic;
	
	/**
	 * Create a new account.
	 * 
	 * @param email
	 * @param name
	 * @param description
	 * @return XML model of the user.
	 */
	@RequestMapping(value="/account/create", method = RequestMethod.POST)
	public ModelAndView create(final HttpServletResponse response,
			@RequestParam final String email,
			@RequestParam final String name,
			@RequestParam final String description) {
		try {
			final User user = userLogic.createUser(email, name, description);
			return getXMLModelAndView("user", new UserDTO(user));
		} catch (final Exception ex) {
			return getErrorModelAndView(response, ex);
		}
	}

	/**
	 * Reset logged-in user's password.
	 * 
	 * @return XML model of the user.
	 */
	@RequestMapping(value="/account/resetPassword", method = RequestMethod.POST)
	public ModelAndView resetPassword(final HttpServletResponse response,
			@RequestParam final String email) {
		try {
			final User user = userLogic.resetUserPassword(email);
			return getXMLModelAndView("user", new UserDTO(user));
		} catch (final Exception ex) {
			return getErrorModelAndView(response, ex);
		}
	}

	/**
	 * Update the name and/or description of the logged-in user.
	 * 
	 * @param name
	 * @param description
	 * @return XML model of the user.
	 */
	@RequestMapping(value="/account/update", method = RequestMethod.POST)
	public ModelAndView update(final HttpServletResponse response,
			@RequestParam final String name,
			@RequestParam final String description) {
		try {
			final User user = userLogic.updateUser(getAuthenticatedEmail(), name, description);
			return getXMLModelAndView("user", new UserDTO(user));
		} catch (final Exception ex) {
			return getErrorModelAndView(response, ex);
		}
	}

	/**
	 * Register a device against the logged-in user.  This device will receive
	 * event and invite updates.  Ensure that you re-register at least every 30 days
	 * or the device will be removed automatically.
	 * 
	 * @param deviceType
	 * @param deviceId
	 * @param registrationId
	 * @return XML model of the UserDevice.
	 */
	@RequestMapping(value="/account/register-device", method = RequestMethod.POST)
	@Deprecated
	public ModelAndView registerDeviceOld(final HttpServletResponse response,
			@RequestParam final String deviceType,
			@RequestParam final String deviceId,
			@RequestParam final String registrationId) {
		try {
			final UserDevice device = userLogic.registerDevice(getAuthenticatedEmail(), deviceType, deviceId, registrationId);
			return getXMLModelAndView("device", new UserDeviceDTO(device));
		} catch (final Exception ex) {
			return getErrorModelAndView(response, ex);
		}
	}

	/**
	 * Register a device against the logged-in user.  This device will receive
	 * event and invite updates.  Ensure that you re-register at least every 30 days
	 * or the device will be removed automatically.
	 * 
	 * @param deviceType
	 * @param deviceId
	 * @param registrationId
	 * @return XML model of the UserDevice.
	 */
	@RequestMapping(value="/account/registerDevice", method = RequestMethod.POST)
	public ModelAndView registerDevice(final HttpServletResponse response,
			@RequestParam final String deviceType,
			@RequestParam final String deviceId,
			@RequestParam final String registrationId) {
		try {
			final UserDevice device = userLogic.registerDevice(getAuthenticatedEmail(), deviceType, deviceId, registrationId);
			return getXMLModelAndView("device", new UserDeviceDTO(device, userLogic.getPoints(device.getUser().getUserId())));
		} catch (final Exception ex) {
			return getErrorModelAndView(response, ex);
		}
	}
}
