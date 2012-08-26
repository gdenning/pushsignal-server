package com.pushsignal.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.pushsignal.dao.JpaDao;

public abstract class AbstractJpaDao<T> implements JpaDao<T> {
	abstract public EntityManager getEntityManager();
	
	public T find(Class<T> entityClass, Object primaryKey) {
		return getEntityManager().find(entityClass, primaryKey);
	}
	
	public Query createNamedQuery(final String name, final int firstResult, 
			final int maxResults, final Object... params) {
		final Query query = getEntityManager().createNamedQuery(name);
		if (firstResult >= 0) {
			query.setFirstResult(firstResult);
		}
		if (maxResults >= 0) {
			query.setMaxResults(maxResults);
		}
		
		int paramIndex = 0;
		for (Object param : params) {
			paramIndex++;
			query.setParameter(paramIndex, param);
		}
		return query;
	}
	
	public T executeQueryByNameSingleResult(final String name, final Object...params) {
		final Query query = getEntityManager().createNamedQuery(name);

		int paramIndex = 0;
		for (Object param : params) {
			paramIndex++;
			query.setParameter(paramIndex, param);
		}
		return (T) query.getSingleResult();
	}
	
	public T store(final T objectToPersist) {
		T result = getEntityManager().merge(objectToPersist);
		return result;
	}
	
	public void remove(final T objectToDelete) {
		getEntityManager().remove(objectToDelete);
	}
	
	public void flush() {
		getEntityManager().flush();
	}
}
