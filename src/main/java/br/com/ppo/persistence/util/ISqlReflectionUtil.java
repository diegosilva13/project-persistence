package br.com.ppo.persistence.util;

import br.com.ppo.persistence.exception.PersistenceException;
import br.com.ppo.persistence.exception.UtilException;


public interface ISqlReflectionUtil{
	public String sqlSave(Object obj) throws UtilException;
	public String sqlUpdate(Object obj) throws UtilException, PersistenceException;
	public String sqlFindById(Class<?> clazz, Object id);;
	public String sqlRemove(Object obj) throws IllegalArgumentException, IllegalAccessException, SecurityException, NoSuchFieldException;
	public String sqlRemoveAll(Class<?> clazz) throws IllegalArgumentException, IllegalAccessException, InstantiationException, SecurityException, NoSuchFieldException;
	public String sqlFindAll(Class<?> clazz) throws InstantiationException, IllegalAccessException;
	public String sqlSaveSucess(Object obj);
}
