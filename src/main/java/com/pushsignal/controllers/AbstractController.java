package com.pushsignal.controllers;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import com.pushsignal.exceptions.PushSignalException;
import com.pushsignal.xml.jaxb.ErrorResultDTO;

public abstract class AbstractController {
	private static final Logger LOG = LoggerFactory.getLogger(AbstractController.class);

	protected String getAuthenticatedEmail() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}

	protected ModelAndView getXMLModelAndView(final String modelName, final Object modelObject) {
		return new ModelAndView("xmlView", BindingResult.MODEL_KEY_PREFIX + modelName, modelObject);
	}

	/**
	 * Set response status and return an XML ErrorResult object.
	 *
	 * @param response
	 * @param ex
	 * @return
	 */
	protected ModelAndView getErrorModelAndView(final HttpServletResponse response, final Exception ex) {
		LOG.error(ex.getMessage(), ex);
		if (ex instanceof PushSignalException) {
			PushSignalException psex = (PushSignalException) ex;
			response.setStatus(psex.getHttpStatus().value());
		} else {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
		if (ex.getMessage() == null || ex.getMessage().equals("")) {
			return getXMLModelAndView("error", new ErrorResultDTO(ex.toString()));
		}
		return getXMLModelAndView("error", new ErrorResultDTO(ex.getMessage()));
	}
}
