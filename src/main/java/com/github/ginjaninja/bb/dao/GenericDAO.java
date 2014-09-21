package com.github.ginjaninja.bb.dao;

import java.util.Collection;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Generic DAO to handle basic CRUD operations 
 * @author CH137762
 *
 * @param <T>	Entity Class to persist
 */
public abstract class GenericDAO <T>{

	protected Class<T> entityClass;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	/**
	 * Set entity class
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public GenericDAO(Class entityClass){
		this.entityClass = entityClass;
	}
	
	/**
	 * Get entity by id
	 * @param id Integer
	 * @return entity
	 */
	public T get(Integer id) {
		return this.entityManager.find(entityClass, id);
	}
	
	/**
	 * Save entity
	 * @param t entityClass
	 * @return entityClass
	 */
	public T save(T t) {
		this.entityManager.persist(t);
        return t;
	}
	
	/**
	 * Update entity
	 * @param t entityClass
	 * @return entityClass
	 */
	public T update(T t) {
		this.entityManager.merge(t);
		return t;
	}
	
	/**
	 * Delete entityClass
	 * @param t entityClass
	 * @return entityClass
	 */
	public void delete(T t) {
		t = this.entityManager.merge(t);
        this.entityManager.remove(t);
	}

	/**
	 * Sets params for NamedQueries
	 * @param String queryName
	 * @param Map<String, Object> params
	 * @return Collection<entityClass>
	 */
	@SuppressWarnings("unchecked")
	public Collection<T> getMany(String queryName, Map<String, Object> params) {
		Query query = entityManager.createNamedQuery(queryName);
		if(params != null){
			for(Map.Entry<String, Object> param : params.entrySet()){
				query.setParameter(param.getKey(), param.getValue());
			}
		}
		return query.getResultList();
	}

	/**
	 * Sets params to null for getMany()
	 */
	public Collection<T> getMany(String queryName) {
		return this.getMany(queryName, null);
	}
}
