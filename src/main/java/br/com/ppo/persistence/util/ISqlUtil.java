package br.com.ppo.persistence.util;

public interface ISqlUtil{
	public String sqlSave(Object object) throws Exception;
	public String sqlUpdate(Object object) throws Exception;
	public String sqlRemove(Object object, Integer id) throws Exception;
	public String sqlRemoveAll(Object object) throws Exception;
	public String sqlFindAll(Object object) throws Exception;
	public String sqlFindById(Object obj, Integer id) throws Exception;
}
