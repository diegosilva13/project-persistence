package br.com.ppo.persistence.dao;

import java.util.List;

public interface ISuperDAO {
	
	public Boolean save(Object object);
	public Object edit(Object object);
	public void remove(Object object);
	public void removeAll(Object object);
	public Object findById(Object object);
	public List<Object> findAll(Object object);
}
