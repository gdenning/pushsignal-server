package com.pushsignal.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pushsignal.dao.UserDeviceDAO;
import com.pushsignal.domain.UserDevice;

@Scope("singleton")
@Repository("UserDeviceDAO")
@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
public class UserDeviceDAOImpl extends AbstractJpaDao<UserDevice> implements UserDeviceDAO {

	@PersistenceContext(unitName = "entityManagerFactory")
	private EntityManager entityManager;

	@Override
	public EntityManager getEntityManager() {
		return entityManager;
	}
}
