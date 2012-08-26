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

import com.pushsignal.dao.EventInviteDAO;
import com.pushsignal.domain.EventInvite;

@Scope("singleton")
@Repository("EventInviteDAO")
@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
public class EventInviteDAOImpl extends AbstractJpaDao<EventInvite> implements EventInviteDAO {

	@PersistenceContext(unitName = "entityManagerFactory")
	private EntityManager entityManager;

	@Override
	public EntityManager getEntityManager() {
		return entityManager;
	}

	/**
	 * JPQL Query - findEventInviteByPrimaryKey
	 */
	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
	public EventInvite findEventInviteByPrimaryKey(long eventInviteId) throws DataAccessException {
		try {
			return find(EventInvite.class, eventInviteId);
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findEventInvitesByEmail
	 */
	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
	public Set<EventInvite> findEventInvitesByEmail(String email) throws DataAccessException {
		Query query = createNamedQuery("findEventInvitesByEmail", -1, -1, email);
		return new LinkedHashSet<EventInvite>(query.getResultList());
	}
}
