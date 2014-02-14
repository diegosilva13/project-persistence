package br.com.ppo.persistence.util;

import javassist.NotFoundException;
import br.com.ppo.persistence.exception.PersistenceException;
import br.com.ppo.persistence.exception.UtilException;


public interface ISqlReflectionUtil{
	public String sqlSave(Object obj) throws UtilException, SecurityException, NoSuchFieldException;
	public String sqlUpdate(Object obj) throws UtilException, PersistenceException, NoSuchFieldException;
	public String sqlFindById(Class<?> clazz, Object id);;
	public String sqlRemove(Object obj) throws IllegalArgumentException, IllegalAccessException, SecurityException, NoSuchFieldException;
	public String sqlRemoveAll(Class<?> clazz) throws IllegalArgumentException, IllegalAccessException, InstantiationException, SecurityException, NoSuchFieldException;
	public String sqlFindAll(Class<?> clazz) throws InstantiationException, IllegalAccessException;
	public String sqlSaveSucess(Object obj);
	public String sqlFindByFieldAndValue(Class<?> clazz, String field, Object value)
			throws NotFoundException;
}
