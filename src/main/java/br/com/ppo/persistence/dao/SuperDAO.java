package br.com.ppo.persistence.dao;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import br.com.ppo.persistence.database.Config;
import br.com.ppo.persistence.exception.PersistenceException;
import br.com.ppo.persistence.util.ISqlReflectionUtil;
import br.com.ppo.persistence.util.ObjectReflectionUtil;
import br.com.ppo.persistence.util.SqlReflectionUtil;

@SuppressWarnings("rawtypes")
public class SuperDAO implements ISuperDAO{
	
	private Connection conn = null;
	private ISqlReflectionUtil sql = new SqlReflectionUtil();
	private ObjectReflectionUtil reflectionUtil = new ObjectReflectionUtil();
	private PreparedStatement prepare;
	private Logger logger = Logger.getLogger(SuperDAO.class);
	private Object objectTemp = new Object();
	
	
	
	public SuperDAO() throws PersistenceException{
		this.conn = Config.startConection();
	}
	
	@Override
	public Object save(Object object) throws PersistenceException {
		try {
			prepare = conn.prepareStatement(sql.sqlSave(object));
			if(!prepare.execute()){
				ResultSet resultSet = conn.prepareStatement(sql.sqlSaveSucess(object)).executeQuery();
				if(resultSet.next()){
					return this.findById(object.getClass(), resultSet.getObject(2).toString());
				}
			}
			conn.rollback();
		} catch (SQLException e) {
			logger.debug(e);
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
			prepare = conn.prepareStatement(sql.sqlUpdate(object));
			if(!prepare.execute()){
				return this.findById(object.getClass(), reflectionUtil.getValue(object, "id"));
			}
			conn.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new PersistenceException(e,"Ocorreu uma falha ao executar o SQL.");
		} catch (Exception e) {
			throw new PersistenceException(e);
		}
		return null;
	}

	@Override
	public Boolean remove(Object object) throws PersistenceException {
		try {
			prepare = conn.prepareStatement(sql.sqlRemove(object));
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
			prepare = conn.prepareStatement(sql.sqlRemoveAll(object.getClass()));
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
			prepare = conn.prepareStatement(sql.sqlFindById(clazz, id));
			ResultSet resultSet = prepare.executeQuery();
			List<Field> fields = reflectionUtil.fields(clazz);
			Object obj = ObjectReflectionUtil.newInstance(clazz);
			Map<Field, Object> fieldValue = new HashMap<Field, Object>();
			if(resultSet.next()){
				for(Field field: fields){
					Object value = resultSet.getObject(field.getName().toLowerCase());
					Object association = this.findAssociacao(field.getType(), id, clazz);
					if(association != value){
						fieldValue.put(field, association);
					}else{
						fieldValue.put(field, value);
					}
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
			prepare = conn.prepareStatement(sql.sqlFindAll(clazz));
			ResultSet resultSet = prepare.executeQuery();
			List<Field> fields = reflectionUtil.fields(clazz);
			Map<Field, Object> fieldValue = new HashMap<Field, Object>();
			List<Object> objects = new ArrayList<>();
			while(resultSet.next()){
				Object obj = ObjectReflectionUtil.newInstance(clazz);
				for(Field field: fields){
					Object value = resultSet.getObject(field.getName().toLowerCase());
					Object association = this.findAssociacao(field.getType(), value, clazz);
					if(association != value){
						fieldValue.put(field, association);
					}else{
						fieldValue.put(field, value);
					}
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
	
	private boolean isOther(Class<?> clazz, String field , Object id) throws PersistenceException{
		try {
			prepare = conn.prepareStatement(sql.sqlFindByFieldAndValue(clazz, field, id));
			ResultSet resultSet = prepare.executeQuery();
			Object value = null;
			Object obj = ObjectReflectionUtil.newInstance(clazz);
			List<Field> fields = reflectionUtil.fields(clazz);
			Map<Field, Object> fieldValue = new HashMap<Field, Object>();
			if(resultSet.next()){
				for(Field f: fields){
					value = resultSet.getObject(f.getName().toLowerCase());
					if(value != null){
						fieldValue.put(f, value);
					}
				}
				obj = reflectionUtil.setAllValuesIgnoringClasses(fieldValue, clazz);
				value = String.valueOf(resultSet.getObject(field.toLowerCase()));
			}
			if(value != null){
				if(Integer.valueOf((String) value) == Integer.valueOf(String.valueOf(id))){
					this.setObjectTemp(obj);
					return false;
				}
			}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new PersistenceException(e,"Ocorreu uma falha ao executar o SQL!");
		} catch (Exception e) {
			throw new PersistenceException(e);
		}
	}
	
	private Object findAssociacao(Class<?> clazz, Object value, Class<?> evictLoop) throws PersistenceException{
		try {
			
			if(reflectionUtil.hasField(clazz, "id")){
				Object obj = ObjectReflectionUtil.newInstance(clazz);
				List<Field> fields = reflectionUtil.fields(clazz);
				boolean  boo = true;
				for(Field field: fields){
					if(field.getType().equals(evictLoop) && value != null){
						boo = this.isOther(clazz, field.getName(), value);
					}
				}
				if(obj != null && boo && value != null){
					return this.findById(obj.getClass(), value);
				}
			}
		} catch (Exception e) {
			throw new PersistenceException(e);
		}
		return value;
	}

	public Object getObjectTemp() {
		return objectTemp;
	}

	public void setObjectTemp(Object objectTemp) {
		this.objectTemp = objectTemp;
	}
}
