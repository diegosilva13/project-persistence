package br.com.ppo.persistence.database;

import java.sql.Connection;
import java.sql.DriverManager;

public class Config {
	private static String urlBanco = "jdbc:postgresql://localhost:5432/empresa";
	private static String database ="postgres";
	private static String password = "1234";
	public static Connection startConection(){
		try {
			Class.forName("org.postgresql.Driver");
			return DriverManager.getConnection(urlBanco,database,password);
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
