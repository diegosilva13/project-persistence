package br.com.ppo.persistence.database;

import java.sql.Connection;
import java.sql.DriverManager;

public class Config {
	private static String urlBanco = "jdbc:postgresql://localhost:5432/bancoescola";
	private static String database ="postgres";
	private static String password = "postgres";
	public static Connection startConection(){
		try {
			Class.forName("org.postgresql.Driver");
			return DriverManager.getConnection(urlBanco,database,password);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
