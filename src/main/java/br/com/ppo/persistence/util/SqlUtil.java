package br.com.ppo.persistence.util;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;

import org.junit.Test;

import br.com.ppo.persistence.dao.SuperClass;

public class SqlUtil implements ISqlUtil {

	@Override
	public String sqlSave(Object obj) throws Exception {
		Class<?> clazz = obj.getClass();
		StringBuilder querys = new StringBuilder();
		StringBuilder sqlParte1 = new StringBuilder();
		StringBuilder sqlParte2 = new StringBuilder();
		boolean possuiHeranca = !obj.getClass().getSuperclass().equals(Object.class);
		try {
			while (clazz.getSuperclass() != null) {
				sqlParte1.append("INSERT INTO " + clazz.getSimpleName() + "(");
				sqlParte2.append(" VALUES(");
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
				if(possuiHeranca){
					sqlParte1.append("id,");
					sqlParte2.append("123,");
				}
				buildSqlSave(querys, sqlParte1, sqlParte2);
				sqlParte1 = new StringBuilder();
				sqlParte2 = new StringBuilder();
				clazz = clazz.getSuperclass();
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new Exception("Ocorreu um erro inesperado.");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			throw new Exception("Ocorreu um erro inesperado.");
		}
		return querys.toString();
	}

	private void buildSqlSave(StringBuilder querys, StringBuilder sqlParte1,
			StringBuilder sqlParte2) {
		sqlParte1.setLength(sqlParte1.length() - 1);
		sqlParte2.setLength(sqlParte2.length() - 1);
		sqlParte1.append(")");
		sqlParte2.append(")");
		sqlParte1.append(sqlParte2);
		querys.append(sqlParte1.toString());
		querys.append(";");
	}

	@Override
	public String sqlUpdate(Object obj) throws Exception {
		Class<?> clazz = obj.getClass();
		StringBuffer sql = new StringBuffer();
		StringBuffer querys = new StringBuffer();
		try {
			Integer id = null;
			while (clazz.getSuperclass() != null) {
				sql.append("UPDATE ");
				sql.append(clazz.getSimpleName());
				sql.append(" SET ");
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
				sql.setLength(sql.length() - 1);
				sql.append(" WHERE id = :id");
				sql.append(";");
				querys.append(sql.toString());
				sql = new StringBuffer();
				clazz = clazz.getSuperclass();
			}
			System.out.println("Famoso id:" +id);
			return id != null ? querys.toString().replaceAll(":id", String.valueOf(id)) : null;
		} catch (SecurityException e) {
			e.printStackTrace();
			throw new Exception("Ocorreu um erro inesperado.");
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new Exception("Ocorreu um erro inesperado.");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			throw new Exception("Ocorreu um erro inesperado.");
		}
	}

	@Override
	public String sqlRemove(Object obj, Integer id) throws Exception {
		StringBuilder sql = new StringBuilder();
		if(obj.getClass().getSuperclass().equals(Object.class)){
			sql.append("DELETE FROM ").append(obj.getClass().getSimpleName());
			if (id != null) {
				sql.append(" WHERE ").append("id").append("=").append(id);
			}
		}else{
			Class<?> clazz = obj.getClass();
			while(!clazz.equals(Object.class)){
				sql.append("DELETE FROM ").append(clazz.getSimpleName());
				if (id != null) {
					sql.append(" WHERE ").append("id").append("=").append(id);
				}
				sql.append(";");
				clazz = clazz.getSuperclass();
			}
		}
		return sql.toString();
	}

	@Override
	public String sqlRemoveAll(Object object) throws Exception {
		if(object.getClass().getSuperclass().equals(Object.class)){
			return sqlRemove(object, null);
		}else{
			String sql = "";
			while(!object.getClass().equals(Object.class)){
				 sql += this.sqlRemove(object, null);
				 object = object.getClass().getSuperclass().newInstance(); 
			}
			return sql;
		}
	}

	@Override
	public String sqlFindById(Object obj, Integer id) throws Exception {
		StringBuilder sql = new StringBuilder();
		if(obj.getClass().getSuperclass().equals(Object.class)){
			sql.append("SELECT * FROM ").append(obj.getClass().getSimpleName());
			if (id != null) {
				sql.append(" WHERE ").append("id").append("=").append(id);
			}
		}else{
			int i = 0;
			Class<?> clazz = obj.getClass(); 
			while(!clazz.getSuperclass().equals(Object.class)){
				sql.append("SELECT * FROM ").append(clazz.getSimpleName()).append(" a"+(i++));
				sql.append(" INNER JOIN ").append(clazz.getSuperclass().getSimpleName()).append(" a" + (i));
				sql.append(" on a"+(i-1)+".id = a"+i+".id");
				clazz = clazz.getSuperclass();
			}
			sql.append(" WHERE a0 = "+id);
		}
		return sql.toString();
	}

	@Override
	public String sqlFindAll(Object object) throws Exception {
		if(object.getClass().getSuperclass().equals(Object.class)){
			return sqlFindById(object, null);
		}else{
			StringBuilder sql = new StringBuilder();
			int i = 0;
			Class<?> clazz = object.getClass(); 
			while(!clazz.getSuperclass().equals(Object.class)){
				sql.append("SELECT * FROM ").append(clazz.getSimpleName()).append(" a"+(i++));
				sql.append(" INNER JOIN ").append(clazz.getSuperclass().getSimpleName()).append(" a" + (i));
				sql.append(" on a"+(i-1)+".id = a"+i+".id");
				i++;
				sql.append(";");
				clazz = clazz.getSuperclass();
			}
			return sql.toString();
		}
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
	
	@Test
	public void teste() {
		System.out.println("testando teste 1,2");
		br.com.ppo.persistence.dao.Test test = new br.com.ppo.persistence.dao.Test();
		test.setNome("Diego Brener");
		test.setSenha("1234");
		test.setCodigo(1234);
		test.setId(1000);
		SuperClass superClass = new SuperClass();
		superClass.setCodigo(100);
		superClass.setId(2000);
		try{
			System.out.println("++++-------OPERAÇÕES SEM HERANÇA -----++++++++");
			System.out.println(this.sqlSave(superClass));
			System.out.println(this.sqlUpdate(superClass));
			System.out.println(this.sqlFindAll(superClass));
			System.out.println(this.sqlFindById(superClass, 20));
			System.out.println(this.sqlRemoveAll(superClass));
			System.out.println(this.sqlRemove(superClass, 10));
			
			
			System.out.println("++++-------OPERAÇÕES COM HERANÇA -----++++++++");
			System.out.println(this.sqlSave(test));
			System.out.println(this.sqlUpdate(test));
			System.out.println(this.sqlFindAll(test));
			System.out.println(this.sqlRemoveAll(test));
			System.out.println(this.sqlFindById(test, 20));
			System.out.println("envocando remove");
			System.out.println(this.sqlRemove(test, 10));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}