package br.com.ppo.persistence.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import br.com.ppo.persistence.exception.PersistenceException;

public class Config {
	private final static String urlDatabase = "jdbc:postgresql://localhost:5432/empresa";
	private final static String databaseName ="postgres";
	private final static String passwodDatabase = "1234";
	public static Connection startConection() throws PersistenceException{
		try {
			Class.forName("org.postgresql.Driver");
			return DriverManager.getConnection(urlDatabase,databaseName,passwodDatabase);
		}catch(ClassNotFoundException e){
			e.printStackTrace();
			throw new PersistenceException(e,"Ocorreu uma falha ao conectar com a Base de Dados.");
		} catch (SQLException e) {
			e.printStackTrace();
			throw new PersistenceException(e,"Ocorreu uma falha ao conectar com a Base de Dados.");
		}
	}
}
