package br.com.ppo.persistence.dao;

import java.util.List;

import br.com.ppo.persistence.entity.IBaseEntity;
import br.com.ppo.persistence.exception.PersistenceException;

public interface IBaseDAO {
	public <E extends IBaseEntity> E save(E model) throws PersistenceException;
	public <E extends IBaseEntity> E update(E model) throws PersistenceException;
	public <E extends IBaseEntity> List<E> findAll(Class<E> clazz) throws PersistenceException;
	public <E extends IBaseEntity> E findById(Class<E> clazz, Object id) throws PersistenceException;
	public <E extends IBaseEntity> Boolean remove(E model) throws PersistenceException;
	public <E extends IBaseEntity>Boolean removeAll(Class<E> clazz) throws PersistenceException;
}
