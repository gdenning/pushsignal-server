package com.pushsignal.dao.impl;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pushsignal.dao.EventDAO;
import com.pushsignal.domain.Event;

@Scope("singleton")
@Repository("EventDAO")
@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
public class EventDAOImpl extends AbstractJpaDao<Event> implements EventDAO {

	@PersistenceContext(unitName = "entityManagerFactory")
	private EntityManager entityManager;
	
	@Override
	public EntityManager getEntityManager() {
		return entityManager;
	}

	/**
	 * JPQL Query - findEventByPrimaryKey
	 */
	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
	public Event findEventByPrimaryKey(long eventId) throws DataAccessException {
		try {
			return find(Event.class, eventId);
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findEventByGuid
	 */
	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
	public Event findEventByGuid(String urlGuid) throws DataAccessException {
		try {
			return executeQueryByNameSingleResult("findEventByGuid", urlGuid);
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findEventByNameContaining
	 */
	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
	public Set<Event> findEventsByNameContaining(String name) throws DataAccessException {
		return findEventsByNameContaining(name, -1, -1);
	}

	/**
	 * JPQL Query - findEventByNameContaining
	 */
	@SuppressWarnings("unchecked")
	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
	public Set<Event> findEventsByNameContaining(String name, int startResult, int maxRows) throws DataAccessException {
		final Query query = createNamedQuery("findEventsByNameContaining", startResult, maxRows, name);
		return new LinkedHashSet<Event>(query.getResultList());
	}

	/**
	 * JPQL Query - findPublicEvents
	 */
	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
	public Set<Event> findPublicEvents(int maxRows) throws DataAccessException {
		final Query query = createNamedQuery("findPublicEvents", -1, maxRows);
		return new LinkedHashSet<Event>(query.getResultList());
	}

	/**
	 * JPQL Query - findEventsByUserID
	 */
	@SuppressWarnings("unchecked")
	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
	public Set<Event> findEventsByUserId(long userID) throws DataAccessException {
		final Query query = createNamedQuery("findEventsByUserId", -1, -1, userID);
		return new LinkedHashSet<Event>(query.getResultList());
	}
}
