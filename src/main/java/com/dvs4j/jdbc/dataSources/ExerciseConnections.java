package com.dvs4j.jdbc.dataSources;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.commons.dbcp2.BasicDataSource;
import org.h2.jdbcx.JdbcConnectionPool;
import org.h2.jdbcx.JdbcDataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.cfg.C3P0Config;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;


public class ExerciseConnections {
	
	private static final int num_connections = 30;
	/**
	 * ---Con driver Manager---
	 * Para 1 conexión es 259 ms
	 * Para 10 conexiones con 511 ms
	 * Para 30 conexiones son 906 ms
	 * Para 100 conexiones son 2244 ms
	 * Para 200 conexiones son 4192 ms
	 */
	
	/**
	 * ---Con ConnectionPool(h2)---
	 * Para 1 conexión 189 ms
	 * Para 10 conexiones 174 ms
	 * Para 30 conexiones 175 ms
	 * Para 100 conexiones 180 ms
	 * Para 200 conexiones 184 ms
	 */
	
	/**
	 * ---Con HikariCP---
	 * 	Para 1 conexión 16 ms
	 * 	Para 10 conexiones 16 ms
	 *  Para 30 conexiones 17 ms
	 *  Para 100 conexiones 17 ms
	 *  Para 200 conexiones 17 ms
	 */
	
	/**
	 * ---Con DBCP2---
	 *  Con 1 conexión 306 ms
	 *  con 10 conexiones 313 ms
	 *  Con 30 conexiones 308 ms
	 *  Con 100 conexiones 309 ms
	 *  Con 200 conexiones 317 ms
	 */
	
	/**
	 * ---Con C3P0---
	 * Para una conexión 302 ms
	 * Para 10 conexiones 223 ms
	 * 
	 * Para 100 conexiones 229 ms
	 * Para 200 conexiones 237 ms
	 */
	
	public static void main(String[] args) throws SQLException {
		
		ComboPooledDataSource connectionPool = new ComboPooledDataSource();
		connectionPool.setJdbcUrl("jdbc:h2:~/test");
		connectionPool.setUser("");
		connectionPool.setPassword("");
		
//Connection dbcp2		BasicDataSource connectionPool = new BasicDataSource();
//Connection dbcp2		connectionPool.setUrl("jdbc:h2:~/test");
//Connection dbcp2		connectionPool.setUsername("");
//Connection dbcp2		connectionPool.setPassword("");
//		
//Connection hikari		HikariConfig config = new HikariConfig();
//Connection hikari		config.setJdbcUrl("jdbc:h2:~/test");
//Connection hikari		config.setUsername("");
//Connection hikari		config.setPassword("");
//Connection hikari		
//Connection hikari		HikariDataSource connectionPool = new HikariDataSource(config);
		
//ConectionPool		JdbcConnectionPool connectionPool = JdbcConnectionPool.create("jdbc:h2:~/test","","");
		Long startTime = System.currentTimeMillis();
		for(int i=0;i<num_connections;i++) {
//Conection Driver Manager			Connection connection = DriverManager.getConnection("jdbc:h2:~/test"); 
			Connection connection = connectionPool.getConnection();
			connection.close();
		}
		System.out.println("Total time; "+(System.currentTimeMillis()-startTime)+" milisegundos");
		connectionPool.close();
	}

}
