package br.com.ppo.persistence.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;

import org.hibernate.PropertyNotFoundException;


public class SqlUtil implements ISqlUtil{

	@Override
	public String sqlSave(Object obj) throws Exception {
		@SuppressWarnings("rawtypes")
		Class clazz = obj.getClass();
		StringBuilder sqlParte1 = new StringBuilder();
		StringBuilder sqlParte2 = new StringBuilder();
		try {
			sqlParte1.append("INSERT INTO " + clazz.getSimpleName() + "(");
			sqlParte2.append(" VALUES(");
			for (Field field : clazz.getDeclaredFields()) {
				Method method = getMethodByName(obj, field);

				Object value = method.invoke(obj);
				if (value != null && !this.isInstanceOfCollection(value)  && !field.getName().equalsIgnoreCase("id")) {
					sqlParte1.append(field.getName());
					if (this.isNumeric(value)) {
						sqlParte2.append(value.toString());
					} else {
						sqlParte2.append("'" + value.toString() + "'");
					}
					sqlParte1.append(",");
					sqlParte2.append(",");
				}
			}
			sqlParte1.setLength(sqlParte1.length() - 1);
			sqlParte2.setLength(sqlParte2.length() - 1);
			sqlParte1.append(")");
			sqlParte2.append(")");
			sqlParte1.append(sqlParte2);
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			throw new Exception("Ocorreu um erro inesperado.");
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new Exception("Ocorreu um erro inesperado.");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			throw new Exception("Ocorreu um erro inesperado.");
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			throw new Exception("Ocorreu um erro inesperado.");
		}
		return sqlParte1.toString();
	}

	@Override
	public String sqlUpdate(Object obj) throws Exception{
		@SuppressWarnings({ "rawtypes" })
		Class clazz = obj.getClass();
		StringBuilder sql= new StringBuilder();
		try {
			sql.append("UPDATE ");
			sql.append(clazz.getSimpleName());
			sql.append(" SET ");
			Integer id = null;
			for(Field field: clazz.getDeclaredFields()){
				Method method = this.getMethodByName(obj, field);
				Object value = method.invoke(obj);
				if(value != null && !this.isInstanceOfCollection(value) && !field.getName().equalsIgnoreCase("id")){
					if(this.isNumeric(value)){
						sql.append(field.getName()).append("=").append(value.toString());
					}else{
						sql.append(field.getName()).append("=").append("'").append(value.toString()).append("'");
					}
					sql.append(",");
				}else if(field.getName().equalsIgnoreCase("id")){
					id = (Integer) value;			
				}
			}
			if(id == null){
			}
			sql.setLength(sql.length()-1);
			if(id != null){
				sql.append(" WHERE id = " + id.toString());
			}else{
				throw new Exception("[id] para atualização não informado.");
			}
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			throw new Exception("Ocorreu um erro inesperado.");
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new Exception("Ocorreu um erro inesperado.");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			throw new Exception("Ocorreu um erro inesperado.");
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			throw new Exception("Ocorreu um erro inesperado.");
		}
		
		return sql.toString();
	}
	
	@Override
	public String sqlRemove(Object obj, Integer id) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM ").append(obj.getClass().getSimpleName());
		if(id != null){
			sql.append(" WHERE ").append("id").append("=").append(id);
		}
		return sql.toString();
	}
	
	@Override
	public String sqlRemoveAll(Object object) throws Exception{
		return sqlRemove(object, null);
	}
	
	@Override
	public String sqlFindById(Object obj, Integer id) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM ").append(obj.getClass().getSimpleName());
		if(id != null){
			sql.append(" WHERE ").append("id").append("=").append(id);
		}
		return sql.toString();
	}
	
	@Override
	public String sqlFindAll(Object object) throws Exception{
		return sqlRemove(object, null);
	}
	
	private Method getMethodByName(Object obj, Field field)
			throws NoSuchMethodException {
		return obj.getClass().getMethod(
				"get"
						+ field.getName().replace(
								field.getName().substring(0, 1),
								field.getName().substring(0, 1)
										.toUpperCase()));
	}

	private boolean isInstanceOfCollection(Object value) {
		try {
			if (value instanceof Collection) {
				return true;
			}
		} catch (ClassCastException e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

	private boolean isNumeric(Object obj) {
		if (obj instanceof Integer || obj instanceof BigInteger) {
			return true;
		} else if (obj instanceof Double || obj instanceof Float) {
			return true;
		} else if (obj instanceof BigDecimal) {
			return true;
		}
		return false;
	}
}