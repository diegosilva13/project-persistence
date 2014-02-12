package br.com.ppo.persistence.dao;

import java.util.List;

import br.com.ppo.persistence.entity.IBaseEntity;
import br.com.ppo.persistence.exception.PersistenceException;

public class BaseDAO implements IBaseDAO {
	
	private ISuperDAO superDAO;
	
	public BaseDAO() {
		try {
			superDAO = new SuperDAO();
		} catch (PersistenceException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <E extends IBaseEntity> E save(E model) throws PersistenceException {
		try {
			return (E) superDAO.save(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new PersistenceException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E extends IBaseEntity> E update(E model) throws PersistenceException {
		try {
			return (E) superDAO.update(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new PersistenceException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E extends IBaseEntity> List<E> findAll(Class<E> clazz) throws PersistenceException {
		try {
			return (List<E>)superDAO.findAll(clazz);
		} catch (Exception e) {
			e.printStackTrace();
			throw new PersistenceException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E extends IBaseEntity> E findById(Class<E> clazz, Object id) throws PersistenceException {
		try {
			return (E) superDAO.findById(clazz, id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new PersistenceException(e);
		}
	}

	@Override
	public <E extends IBaseEntity> Boolean remove(E model) throws PersistenceException {
		try {
			return superDAO.remove(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new PersistenceException(e);
		}
	}

	@Override
	public <E extends IBaseEntity> Boolean removeAll(Class<E> clazz) throws PersistenceException {
		try {
			return superDAO.removeAll(clazz);
		} catch (Exception e) {
			e.printStackTrace();
			throw new PersistenceException(e);
		}
	}
}
