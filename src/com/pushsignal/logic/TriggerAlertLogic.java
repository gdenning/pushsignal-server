package com.pushsignal.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.pushsignal.dao.TriggerAlertDAO;
import com.pushsignal.domain.TriggerAlert;
import com.pushsignal.exceptions.ResourceNotFoundException;

@Scope("singleton")
@Service
public class TriggerAlertLogic extends AbstractLogic {
	@Autowired
	private TriggerAlertDAO triggerAlertDAO;

	public TriggerAlert getTriggerAlert(final String authenticatedEmail, final long triggerAlertId) {
		final TriggerAlert triggerAlert = triggerAlertDAO.findTriggerAlertByPrimaryKey(triggerAlertId);
		if (triggerAlert == null) {
			throw new ResourceNotFoundException("Unable to locate trigger alert with passed ID: "
							+ triggerAlertId);
		}
		return triggerAlert;
	}
}
