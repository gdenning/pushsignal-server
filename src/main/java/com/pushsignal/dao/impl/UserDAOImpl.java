package com.pushsignal.dao.impl;

import java.util.Iterator;
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

import com.pushsignal.dao.UserDAO;
import com.pushsignal.domain.User;

@Scope("singleton")
@Repository("UserDAO")
@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
public class UserDAOImpl extends AbstractJpaDao<User> implements UserDAO {

	@PersistenceContext(unitName = "entityManagerFactory")
	private EntityManager entityManager;

	@Override
	public EntityManager getEntityManager() {
		return entityManager;
	}

	/**
	 * JPQL Query - findUserByPrimaryKey
	 */
	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
	public User findUserByPrimaryKey(long userId) throws DataAccessException {
		try {
			return find(User.class, userId);
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findUserByEmail
	 */
	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
	public User findUserByEmail(String email) throws DataAccessException {
		Query query = createNamedQuery("findUserByEmail", 0, 1, email);
		Iterator<User> userIterator = query.getResultList().iterator();
		if (!userIterator.hasNext()) {
			return null;
		}
		return userIterator.next();
	}

	/**
	 * JPQL Query - findUserByName
	 */
	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
	public Set<User> findUserByName(String name) throws DataAccessException {

		return findUserByName(name, -1, -1);
	}

	/**
	 * JPQL Query - findUserByName
	 */
	@SuppressWarnings("unchecked")
	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
	public Set<User> findUserByName(String name, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findUserByName", startResult, maxRows, name);
		return new LinkedHashSet<User>(query.getResultList());
	}

	/**
	 * JPQL Query - findUserByNameContaining
	 */
	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
	public Set<User> findUserByNameContaining(String name) throws DataAccessException {

		return findUserByNameContaining(name, -1, -1);
	}

	/**
	 * JPQL Query - findUserByNameContaining
	 */
	@SuppressWarnings("unchecked")
	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
	public Set<User> findUserByNameContaining(String name, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findUserByNameContaining", startResult, maxRows, name);
		return new LinkedHashSet<User>(query.getResultList());
	}

	/**
	 * JPQL Query - findAllUsers
	 */
	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
	public Set<User> findAllUsers() throws DataAccessException {
		return findAllUsers(-1, -1);
	}

	/**
	 * JPQL Query - findAllUsers
	 */
	@SuppressWarnings("unchecked")
	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
	public Set<User> findAllUsers(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllUsers", startResult, maxRows);
		return new LinkedHashSet<User>(query.getResultList());
	}

	/**
	 * JPQL Query - getPoints
	 */
	public long getPoints(long userId) throws DataAccessException {
		Query query = createNamedQuery("getUserPoints", -1, -1, userId);
		Long points = (Long) query.getSingleResult();
		return points == null ? 0 : points;
	}

}
