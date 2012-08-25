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

import com.pushsignal.dao.ActivityDAO;
import com.pushsignal.domain.Activity;
import com.pushsignal.domain.Event;

@Scope("singleton")
@Repository("ActivityDAO")
@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
public class ActivityDAOImpl extends AbstractJpaDao<Activity> implements ActivityDAO {
	@PersistenceContext(unitName = "entityManagerFactory")
	private EntityManager entityManager;

	@Override
	public EntityManager getEntityManager() {
		return entityManager;
	}

	/**
	 * JPQL Query - findActivityByPrimaryKey
	 */
	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
	public Activity findActivityByPrimaryKey(long activityId) throws DataAccessException {
		try {
			return find(Activity.class, activityId);
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findActivitiesByUserID
	 */
	@SuppressWarnings("unchecked")
	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
	public Set<Activity> findActivitiesByUserID(long userID, int maxRows) throws DataAccessException {
		final Query query = createNamedQuery("findActivitiesByUserId", -1, maxRows, userID);
		return new LinkedHashSet<Activity>(query.getResultList());
	}
}
