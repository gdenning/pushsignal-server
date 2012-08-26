package com.pushsignal.dao;

import java.util.Set;

import org.springframework.dao.DataAccessException;

import com.pushsignal.domain.Event;

public interface EventDAO extends JpaDao<Event> {

	Event findEventByPrimaryKey(long eventId) throws DataAccessException;

	Event findEventByGuid(String urlGuid) throws DataAccessException;

	Set<Event> findEventsByNameContaining(String name) throws DataAccessException;

	Set<Event> findEventsByNameContaining(String name, int startResult, int maxRows) throws DataAccessException;

	Set<Event> findEventsByUserId(long userID) throws DataAccessException;

	Set<Event> findPublicEvents(int maxRows) throws DataAccessException;
}