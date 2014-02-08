package br.com.ppo.persistence.util;


public interface ISqlUtil{
	public String sqlSave(Object obj) throws Exception;
	public String sqlUpdate(Object obj) throws Exception;
	public String sqlFindById(Object obj, Object id) throws Exception;
	public String sqlRemove(Object obj) throws Exception;
	public String sqlRemoveAll(Class<?> clazz) throws Exception;
	public String sqlFindAll(Class<?> clazz) throws Exception;
}
