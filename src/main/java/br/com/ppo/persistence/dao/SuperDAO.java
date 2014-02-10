package br.com.ppo.persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.ppo.persistence.database.Config;
import br.com.ppo.persistence.exception.PersistenceException;
import br.com.ppo.persistence.util.ISqlReflectionUtil;
import br.com.ppo.persistence.util.ObjectReflectionUtil;
import br.com.ppo.persistence.util.SqlReflectionUtil;

@SuppressWarnings("rawtypes")
public class SuperDAO implements ISuperDAO{
	
	private Connection conn = null;
	private ISqlReflectionUtil sqlUtil = new SqlReflectionUtil();
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
					return this.findById(object.getClass(), resultSet.getObject(2).toString());
				}
			}
			conn.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new PersistenceException(e,"Ocorreu uma falha ao executar o SQL!");
		} catch (Exception e) {
			throw new PersistenceException(e);
		}
		return null;
	}

	@Override
	public Object update(Object object) throws PersistenceException{
		try {
			prepare = conn.prepareStatement(sqlUtil.sqlUpdate(object));
			if(!prepare.execute()){
				return this.findById(object.getClass(), reflectionUtil.getValue(object, "id"));
			}
			conn.rollback();
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
			if(!prepare.execute()){
				return true;
			}
			conn.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new PersistenceException(e,"Ocorreu uma falha ao executar o SQL!");
		} catch (Exception e) {
			throw new PersistenceException(e);
		}
		return false;
	}

	@Override
	public Boolean removeAll(Object object) throws PersistenceException {
		try {
			prepare = conn.prepareStatement(sqlUtil.sqlRemoveAll(object.getClass()));
			if(!prepare.execute()){
				return true;
			}
			conn.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new PersistenceException(e,"Ocorreu uma falha ao executar o SQL!");
		} catch (Exception e) {
			throw new PersistenceException(e);
		}
		return false;
	}

	@Override
	public Object findById(Class<?> clazz, Object id) throws PersistenceException {
		try {
			prepare = conn.prepareStatement(sqlUtil.sqlFindById(clazz, id));
			ResultSet resultSet = prepare.executeQuery();
			List<String> fields = reflectionUtil.fields(clazz);
			Object obj = ObjectReflectionUtil.newInstance(clazz);
			Map<String, Object> fieldValue = new HashMap<String, Object>();
			if(resultSet.next()){
				for(String name: fields){
					Object value = resultSet.getObject(name.toLowerCase());
					fieldValue.put(name, value);
				}
				obj = reflectionUtil.setAllValues(fieldValue, clazz);
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
			List<String> fields = reflectionUtil.fields(clazz);
			Map<String, Object> fieldValue = new HashMap<String, Object>();
			List<Object> objects = new ArrayList<>();
			while(resultSet.next()){
				Object obj = ObjectReflectionUtil.newInstance(clazz);
				for(String nameField: fields){
					Object value = resultSet.getObject(nameField.toLowerCase());
					fieldValue.put(nameField, value);
				}
				obj = reflectionUtil.setAllValues(fieldValue, clazz);
				objects.add(obj);
			}
			return objects;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new PersistenceException(e,"Ocorreu uma falha ao executar o SQL!");
		} catch (Exception e) {
			throw new PersistenceException(e);
		}
	}
}
