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

import com.pushsignal.dao.TriggerAlertDAO;
import com.pushsignal.domain.TriggerAlert;

@Scope("singleton")
@Repository("TriggerAlertDAO")
@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
public class TriggerAlertDAOImpl extends AbstractJpaDao<TriggerAlert> implements TriggerAlertDAO {

	@PersistenceContext(unitName = "entityManagerFactory")
	private EntityManager entityManager;

	@Override
	public EntityManager getEntityManager() {
		return entityManager;
	}

	/**
	 * JPQL Query - findTriggerAlertByPrimaryKey
	 */
	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
	public TriggerAlert findTriggerAlertByPrimaryKey(long triggerAlertId) throws DataAccessException {
		try {
			return find(TriggerAlert.class, triggerAlertId);
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findAllTriggerAlerts
	 */
	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
	public Set<TriggerAlert> findAllTriggerAlerts() throws DataAccessException {

		return findAllTriggerAlerts(-1, -1);
	}

	/**
	 * JPQL Query - findAllTriggerAlerts
	 */
	@SuppressWarnings("unchecked")
	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
	public Set<TriggerAlert> findAllTriggerAlerts(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllTriggerAlerts", startResult, maxRows);
		return new LinkedHashSet<TriggerAlert>(query.getResultList());
	}

}
