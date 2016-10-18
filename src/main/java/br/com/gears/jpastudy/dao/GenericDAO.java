package br.com.gears.jpastudy.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.gears.jpastudy.util.JPAUtil;

public abstract class GenericDAO<T extends Serializable> {
	private Class<T> aClass;
	
	protected GenericDAO(Class<T> aClass){
		this.aClass = aClass;
	}
	
	protected EntityManager getEntityManager(){
		return JPAUtil.getInstance().getEntityManager();
	}
	
	@SuppressWarnings("unchecked")
	public List<T> findAll(){
		EntityManager manager = getEntityManagerAndBeginTransaction();
		Query query = manager.createQuery("from " + aClass.getSimpleName());
		List<T> entities = query.getResultList();
		commitTransactionaAndCloseEntityManager(manager);
		return entities;
	}
	
	@SuppressWarnings("unchecked")
	public T findOne(String jpql, Object... params){
		EntityManager manager = getEntityManagerAndBeginTransaction();
		Query query = manager.createQuery(jpql);
		for(int i = 0; i < params.length; i++){
			query.setParameter(i+1, params[i]);			
		}
		T entity = (T) query.getSingleResult();
		commitTransactionaAndCloseEntityManager(manager);
		return entity;
	}
	
	@SuppressWarnings("unchecked")
	public List<T> find(String jpql, Object... params){
		EntityManager manager = getEntityManagerAndBeginTransaction();
		Query query = manager.createQuery(jpql);
		for(int i = 0; i < params.length; i++){
			query.setParameter(i+1, params[i]);			
		}	 
		List<T> entities = query.getResultList();
		commitTransactionaAndCloseEntityManager(manager);
		return entities;
	}
	
	public Long count(){
		EntityManager manager = getEntityManagerAndBeginTransaction();		
		Query query = manager.createQuery("select count(o) from " + aClass.getSimpleName() + " o ");
		Long count = (Long) query.getSingleResult();
		commitTransactionaAndCloseEntityManager(manager);
		return count;
	}
	
	public T findById(Long id){
		EntityManager manager = getEntityManagerAndBeginTransaction();
		T entity = manager.find(aClass, id);		
		commitTransactionaAndCloseEntityManager(manager);
		return (T) entity;
	}
	
	public void save(T entity){
		EntityManager manager = getEntityManagerAndBeginTransaction();
		manager.persist(entity);		
		commitTransactionaAndCloseEntityManager(manager);
	}
	
	public void update(T entity){
		EntityManager manager = getEntityManagerAndBeginTransaction();		
		manager.merge(entity);
		commitTransactionaAndCloseEntityManager(manager);
	}
	
	public void delete(Long id){
		EntityManager manager = getEntityManagerAndBeginTransaction();		
		manager.remove(manager.getReference(aClass, id));		
		commitTransactionaAndCloseEntityManager(manager);	
	}
	
	private EntityManager getEntityManagerAndBeginTransaction(){
		EntityManager manager= getEntityManager();
		manager.getTransaction().begin();
		return manager;
	}
	
	private void commitTransactionaAndCloseEntityManager(EntityManager manager){
		manager.getTransaction().commit();
		manager.close();			
	}

}
