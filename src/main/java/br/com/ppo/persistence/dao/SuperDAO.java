package br.com.ppo.persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.ppo.persistence.database.Config;
import br.com.ppo.persistence.exception.PersistenceException;
import br.com.ppo.persistence.util.ISqlUtil;
import br.com.ppo.persistence.util.ObjectReflectionUtil;
import br.com.ppo.persistence.util.SqlUtil;

@SuppressWarnings("rawtypes")
public class SuperDAO implements ISuperDAO{
	
	private Connection conn = null;
	private ISqlUtil sqlUtil = new SqlUtil();
	private ObjectReflectionUtil reflectionUtil = new ObjectReflectionUtil();
	private PreparedStatement prepare;
	
	public SuperDAO() throws PersistenceException{
		this.conn = Config.startConection();
	}
	
	@Override
	public Object save(Object object) throws PersistenceException {
		try {
			prepare = conn.prepareStatement(sqlUtil.sqlSave(object));
			if(!prepare.execute()){
				ResultSet resultSet = conn.prepareStatement(sqlUtil.sqlSaveSucess(object)).executeQuery();
				if(resultSet.next()){
					return this.findById(object, resultSet.getObject(2).toString());
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new PersistenceException(e,"Ocorreu uma falha ao executar o SQL!");
		} catch (Exception e) {
			throw new PersistenceException(e);
		}
		return null;
	}

	@Override
	public Object update(Object object) throws PersistenceException {
		try {
			prepare = conn.prepareStatement(sqlUtil.sqlUpdate(object));
			if(!prepare.execute()){
				return this.findById(object, reflectionUtil.getValue(object, "id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new PersistenceException(e,"Ocorreu uma falha ao executar o SQL!");
		} catch (Exception e) {
			throw new PersistenceException(e);
		}
		return null;
	}

	@Override
	public Boolean remove(Object object) throws PersistenceException {
		try {
			prepare = conn.prepareStatement(sqlUtil.sqlRemove(object));
			return !prepare.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new PersistenceException(e,"Ocorreu uma falha ao executar o SQL!");
		} catch (Exception e) {
			throw new PersistenceException(e);
		}
	}

	@Override
	public Boolean removeAll(Object object) throws PersistenceException {
		try {
			prepare = conn.prepareStatement(sqlUtil.sqlRemoveAll(object.getClass()));
			return !prepare.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new PersistenceException(e,"Ocorreu uma falha ao executar o SQL!");
		} catch (Exception e) {
			throw new PersistenceException(e);
		}
	}

	@Override
	public Object findById(Object object, Object id) throws PersistenceException {
		try {
			Class<?> clazz = object.getClass();
			prepare = conn.prepareStatement(sqlUtil.sqlFindById(object, id));
			ResultSet resultSet = prepare.executeQuery();
			List<String> fieldsName = reflectionUtil.fieldsName(clazz);
			Object obj = ObjectReflectionUtil.newInstance(clazz);
			if(resultSet.next()){
				for(String name: fieldsName){
					Object value = resultSet.getObject(name.toLowerCase());
					if(value != null){
						obj = reflectionUtil.setValue(obj,value,name);
					}
				}
			}
			return obj;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new PersistenceException(e,"Ocorreu uma falha ao executar o SQL!");
		} catch (Exception e) {
			throw new PersistenceException(e);
		}
	}
	
	@Override
	public List findAll(Class clazz) throws PersistenceException {
		try {
			prepare = conn.prepareStatement(sqlUtil.sqlFindAll(clazz));
			ResultSet resultSet = prepare.executeQuery();
			List<Object> list = new ArrayList<>();
			List<String> fieldsName = reflectionUtil.fieldsName(clazz);
			while(resultSet.next()){
				Object obj = ObjectReflectionUtil.newInstance(clazz);
				for(String name: fieldsName){
					Object value = resultSet.getObject(name.toLowerCase());
					if(value != null){
						obj = reflectionUtil.setValue(obj,value,name);
					}
				}
				list.add(obj);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new PersistenceException(e,"Ocorreu uma falha ao executar o SQL!");
		} catch (Exception e) {
			throw new PersistenceException(e);
		}
	}
}
