package br.com.ppo.persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import br.com.ppo.persistence.database.Config;
import br.com.ppo.persistence.util.ISqlUtil;
import br.com.ppo.persistence.util.ObjectReflectionUtil;
import br.com.ppo.persistence.util.SqlUtil;

@SuppressWarnings("rawtypes")
public class SuperDAO implements ISuperDAO{
	
	private Connection conn = null;
	private ISqlUtil sqlUtil = new SqlUtil();
	private ObjectReflectionUtil reflectionUtil = new ObjectReflectionUtil();
	private PreparedStatement prepare;
	Logger logger = Logger.getAnonymousLogger();
	
	public SuperDAO() {
		this.conn = Config.startConection();
	}
	
	@Override
	public Boolean save(Object object) {
		try {
			prepare = conn.prepareStatement(sqlUtil.sqlSave(object));
			return !prepare.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Object edit(Object object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean remove(Object object) {
		try {
			prepare = conn.prepareStatement(sqlUtil.sqlRemove(object));
			return !prepare.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Boolean removeAll(Object object) {
		try {
			prepare = conn.prepareStatement(sqlUtil.sqlRemoveAll(object.getClass()));
			return !prepare.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Object findById(Object object, Object id) {
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List findAll(Class clazz) {
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
