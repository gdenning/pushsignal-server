package com.pushsignal.dao;

import java.util.Set;

import org.springframework.dao.DataAccessException;

import com.pushsignal.domain.EventInvite;

public interface EventInviteDAO extends JpaDao<EventInvite> {

	EventInvite findEventInviteByPrimaryKey(long EventInviteId) throws DataAccessException;

	Set<EventInvite> findEventInvitesByEmail(String email) throws DataAccessException;
}