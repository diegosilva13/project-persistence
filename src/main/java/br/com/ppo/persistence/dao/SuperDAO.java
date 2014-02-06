package br.com.ppo.persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import br.com.ppo.persistence.database.Config;
import br.com.ppo.persistence.util.ISqlUtil;
import br.com.ppo.persistence.util.SqlUtil;

public class SuperDAO implements ISuperDAO{
	
	private Connection conn = null;
	private ISqlUtil sqlUtil = new SqlUtil();
	private PreparedStatement prepare;
	Logger logger = Logger.getAnonymousLogger();
	public SuperDAO() {
		this.conn = Config.startConection();
	}
	
	@Override
	public Boolean save(Object object) {
		try {
			logger.fine("SQL GERADO: "+sqlUtil.sqlSave(object));
			System.out.println("SQL GERADO: "+sqlUtil.sqlSave(object));
			prepare = conn.prepareStatement(sqlUtil.sqlSave(object));
			return prepare.execute();
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
	public void remove(Object object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeAll(Object object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object findById(Object object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Object> findAll(Object object) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
