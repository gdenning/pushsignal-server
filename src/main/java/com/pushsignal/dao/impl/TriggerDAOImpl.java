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

import com.pushsignal.dao.TriggerDAO;
import com.pushsignal.domain.Trigger;
import com.pushsignal.domain.User;

@Scope("singleton")
@Repository("TriggerDAO")
@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
public class TriggerDAOImpl extends AbstractJpaDao<Trigger> implements TriggerDAO {

	@PersistenceContext(unitName = "entityManagerFactory")
	private EntityManager entityManager;

	@Override
	public EntityManager getEntityManager() {
		return entityManager;
	}

	/**
	 * JPQL Query - findTriggerByPrimaryKey
	 */
	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
	public Trigger findTriggerByPrimaryKey(long triggerId) throws DataAccessException {
		try {
			return find(Trigger.class, triggerId);
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findActiveTriggersForUser
	 */
	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
	public Set<Trigger> findActiveTriggersForUser(User user) throws DataAccessException {
		return findActiveTriggersForUser(-1, -1, user);
	}

	/**
	 * JPQL Query - findActiveTriggersForUser
	 */
	@SuppressWarnings("unchecked")
	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
	public Set<Trigger> findActiveTriggersForUser(int startResult, int maxRows, User user) throws DataAccessException {
		Query query = createNamedQuery("findActiveTriggersForUser", startResult, maxRows, user.getUserId());
		return new LinkedHashSet<Trigger>(query.getResultList());
	}
}
