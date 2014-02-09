package br.com.ppo.persistence.util;

import br.com.ppo.persistence.exception.UtilException;


public interface ISqlReflectionUtil{
	public String sqlSave(Object obj) throws UtilException;
	public String sqlUpdate(Object obj) throws UtilException;
	public String sqlFindById(Object obj, Object id);
	public String sqlRemove(Object obj) throws IllegalArgumentException, IllegalAccessException;
	public String sqlRemoveAll(Class<?> clazz) throws IllegalArgumentException, IllegalAccessException, InstantiationException;
	public String sqlFindAll(Class<?> clazz) throws InstantiationException, IllegalAccessException;
	public String sqlSaveSucess(Object obj);
}
