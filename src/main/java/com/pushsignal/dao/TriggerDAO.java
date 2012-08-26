package com.pushsignal.dao;

import java.util.Set;

import org.springframework.dao.DataAccessException;

import com.pushsignal.domain.Trigger;
import com.pushsignal.domain.User;

public interface TriggerDAO extends JpaDao<Trigger> {

	Trigger findTriggerByPrimaryKey(long triggerId) throws DataAccessException;

	Set<Trigger> findActiveTriggersForUser(User user) throws DataAccessException;

	Set<Trigger> findActiveTriggersForUser(int startResult, int maxRows,	User user) throws DataAccessException;
}