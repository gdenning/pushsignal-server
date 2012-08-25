package com.pushsignal.dao;

import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * Interface for AbstractJpaDao.
 */
public interface JpaDao<T> {
	EntityManager getEntityManager();
	
	T find(Class<T> entityClass, Object primaryKey);
	
	Query createNamedQuery(String name, int firstResult, int maxResults, Object... params);
	
	Object executeQueryByNameSingleResult(String name, Object...params);
	
	T store(T objectToPersist);
	
	void remove(T objectToDelete);
	
	void flush();
}
