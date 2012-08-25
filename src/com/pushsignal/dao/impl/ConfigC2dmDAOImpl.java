package com.pushsignal.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pushsignal.dao.ConfigC2dmDAO;
import com.pushsignal.domain.ConfigC2dm;

@Scope("singleton")
@Repository("ConfigC2dmDAO")
@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
public class ConfigC2dmDAOImpl extends AbstractJpaDao<ConfigC2dm> implements ConfigC2dmDAO {

	@PersistenceContext(unitName = "entityManagerFactory")
	private EntityManager entityManager;

	@Override
	public EntityManager getEntityManager() {
		return entityManager;
	}
}
