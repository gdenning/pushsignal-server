package com.pushsignal.dao;

import java.util.Set;

import org.springframework.dao.DataAccessException;

import com.pushsignal.domain.TriggerAlert;

public interface TriggerAlertDAO extends JpaDao<TriggerAlert> {

	TriggerAlert findTriggerAlertByPrimaryKey(long TriggerAlertId) throws DataAccessException;

	Set<TriggerAlert> findAllTriggerAlerts() throws DataAccessException;

	Set<TriggerAlert> findAllTriggerAlerts(int startResult, int maxRows) throws DataAccessException;

}