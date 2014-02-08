package br.com.ppo.persistence.dao;

import java.util.List;

public interface ISuperDAO<T> {
	
	public Object save(Object object);
	public Object edit(Object object);
	public Boolean remove(Object object);
	public Boolean removeAll(Object object);
	public Object findById(Object object, Object id);
	@SuppressWarnings("rawtypes")
	public List findAll(Class<?> clazz);
	}
