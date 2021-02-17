package com.dvs4j.jdbc.dataSources;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.h2.jdbcx.JdbcConnectionPool;
import org.h2.jdbcx.JdbcDataSource;

public class ExerciseConnections {
	
	private static final int num_connections = 200;
	/**
	 * ---Con driver Manager---
	 * Para 1 conexión es 259 ms
	 * Para 10 conexiones con 511 ms
	 * Para 30 conexiones son 906 ms
	 * Para 100 conexiones son 2244 ms
	 * Para 200 conexiones son 4192 ms
	 */
	
	/**
	 * ---Con ConnectionPool---
	 * Para 1 conexión 189 ms
	 * Para 10 conexiones 174 ms
	 * Para 30 conexiones 175 ms
	 * Para 100 conexiones 180 ms
	 *  
	 */
	
	public static void main(String[] args) throws SQLException {
		
		JdbcConnectionPool connectionPool = JdbcConnectionPool.create("jdbc:h2:~/test","","");
		Long startTime = System.currentTimeMillis();
		for(int i=0;i<num_connections;i++) {
			//Connection connection = DriverManager.getConnection("jdbc:h2:~/test"); 
			Connection connection = connectionPool.getConnection();
			connection.close();
		}
		System.out.println("Total time; "+(System.currentTimeMillis()-startTime)+" milisegundos");
	}

}
