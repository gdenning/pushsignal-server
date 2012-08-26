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

import com.pushsignal.dao.EventMemberDAO;
import com.pushsignal.domain.EventMember;

@Scope("singleton")
@Repository("EventMemberDAO")
@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
public class EventMemberDAOImpl extends AbstractJpaDao<EventMember> implements EventMemberDAO {

	@PersistenceContext(unitName = "entityManagerFactory")
	private EntityManager entityManager;

	@Override
	public EntityManager getEntityManager() {
		return entityManager;
	}

	/**
	 * JPQL Query - findEventMemberByPrimaryKey
	 */
	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
	public EventMember findEventMemberByPrimaryKey(long eventMemberId) throws DataAccessException {
		try {
			return find(EventMember.class, eventMemberId);
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findAllMembers
	 */
	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
	public Set<EventMember> findAllMembers() throws DataAccessException {

		return findAllMembers(-1, -1);
	}

	/**
	 * JPQL Query - findAllMembers
	 */
	@SuppressWarnings("unchecked")
	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
	public Set<EventMember> findAllMembers(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllMembers", startResult, maxRows);
		return new LinkedHashSet<EventMember>(query.getResultList());
	}

}
