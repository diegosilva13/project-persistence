package br.com.ppo.persistence.util;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;

import br.com.ppo.persistence.exception.PersistenceException;
import br.com.ppo.persistence.exception.UtilException;

public class SqlReflectionUtil implements ISqlReflectionUtil {

	ObjectReflectionUtil reflectionUtil = new ObjectReflectionUtil();
	
	@Override
	public String sqlSave(Object obj) throws UtilException {
		Class<?> clazz = obj.getClass();
		StringBuffer querys = new StringBuffer();
		StringBuffer sqlParte1 = new StringBuffer();
		StringBuffer sqlParte2 = new StringBuffer();
		try {
			sqlParte1.append("INSERT INTO \"" + clazz.getSimpleName() + "\"(");
			sqlParte2.append(" VALUES(");
			while (clazz.getSuperclass() != null) {
				for (Field field : clazz.getDeclaredFields()) {
					field.setAccessible(true);
					Object value = field.get(obj);
					if (value != null && !this.isInstanceOfCollection(value)
							&& !field.getName().equalsIgnoreCase("id")) {
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
				clazz = clazz.getSuperclass();
			}
			buildSqlSave(querys, sqlParte1, sqlParte2);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new UtilException(e,"Ocorreu um erro inesperado.");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			throw new UtilException(e,"Ocorreu um erro inesperado.");
		}
		return querys.toString();
	}

	private void buildSqlSave(StringBuffer querys, StringBuffer sqlParte1,
			StringBuffer sqlParte2) {
		sqlParte1.setLength(sqlParte1.length() - 1);
		sqlParte2.setLength(sqlParte2.length() - 1);
		sqlParte1.append(")");
		sqlParte2.append(")");
		sqlParte1.append(sqlParte2);
		querys.append(sqlParte1.toString());
		querys.append(";");
	}

	@Override
	public String sqlUpdate(Object obj) throws UtilException, PersistenceException {
		Class<?> clazz = obj.getClass();
		StringBuffer sql = new StringBuffer();
		StringBuffer querys = new StringBuffer();
		try {
			Integer id = null;
			sql.append("UPDATE ");
			sql.append("\""+clazz.getSimpleName()+"\"");
			sql.append(" SET ");
			while (clazz.getSuperclass() != null) {
				for (Field field : clazz.getDeclaredFields()) {
					field.setAccessible(true);
					Object value = field.get(obj);
					if (value != null && !this.isInstanceOfCollection(value)
							&& !field.getName().equalsIgnoreCase("id")) {
						if (this.isNumeric(value)) {
							sql.append(field.getName()).append("=")
									.append(value.toString());
						} else {
							sql.append(field.getName()).append("=").append("'")
									.append(value.toString()).append("'");
						}
						sql.append(",");
					} else if (field.getName().equalsIgnoreCase("id")) {
						id = value != null ? (Integer) value : id;
					}
				}
				clazz = clazz.getSuperclass();
			}
			sql.setLength(sql.length() - 1);
			sql.append(" WHERE id = :id");
			sql.append(";");
			querys.append(sql.toString());
			if(id == null){
				throw new PersistenceException("O objeto não possui um [ID] para atualização.");
			}
			return querys.toString().replaceAll(":id", String.valueOf(id));
		} catch (SecurityException e) {
			e.printStackTrace();
			throw new UtilException(e,"Ocorreu um erro inesperado.");
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new UtilException(e,"Ocorreu um erro inesperado.");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			throw new UtilException(e,"Ocorreu um erro inesperado.");
		}
	}

	@Override
	public String sqlRemove(Object obj) throws IllegalArgumentException, IllegalAccessException, SecurityException, NoSuchFieldException{
		StringBuffer sql = new StringBuffer();
		Integer id = (Integer) reflectionUtil.getValue(obj, "id");
		sql.append("DELETE FROM \"").append(obj.getClass().getSimpleName()+"\"");
			if (id != null) {
				sql.append(" WHERE ").append("id").append("=").append(id);
			}
		return sql.toString();
	}

	@Override
	public String sqlRemoveAll(Class<?> clazz) throws IllegalArgumentException, IllegalAccessException, InstantiationException, SecurityException, NoSuchFieldException {
		return sqlRemove(clazz.newInstance());
	}

	@Override
	public String sqlFindById(Class<?> clazz, Object id){
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM \"").append(clazz.getSimpleName()+"\"");
		if (id != null) {
			sql.append(" WHERE ").append("id").append("=").append(id);
		}
		return sql.toString();
	}

	@Override
	public String sqlFindAll(Class<?> clazz) throws InstantiationException, IllegalAccessException{
			return sqlFindById(clazz, null);
	}
	
	private boolean isInstanceOfCollection(Object value) throws UtilException {
		try {
			if (value instanceof Collection) {
				return true;
			}
			return false;
		} catch (ClassCastException e) {
			e.printStackTrace();
			throw new UtilException(e);
		}
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
	
	@Override
	public String sqlSaveSucess(Object obj){
		return "SELECT * FROM \""+obj.getClass().getSimpleName()+"_id_seq\"";
	}
}