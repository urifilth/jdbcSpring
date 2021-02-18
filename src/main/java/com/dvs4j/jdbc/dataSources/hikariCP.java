package com.dvs4j.jdbc.dataSources;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class hikariCP {
		public static void main(String[] args) throws SQLException {
			HikariConfig config = new HikariConfig();
			config.setJdbcUrl("jdbc:h2:~/test");
			config.setUsername("");
			config.setPassword("");
			config.setMaximumPoolSize(5);
			config.setConnectionTimeout(5000);
			
			HikariDataSource connectionPool = new HikariDataSource(config);
			
			
			Connection connection1 = connectionPool.getConnection();
			Connection connection2 = connectionPool.getConnection();
			Connection connection3 = connectionPool.getConnection();
			Connection connection4 = connectionPool.getConnection();
			Connection connection5 = connectionPool.getConnection();
			

			connection1.close();
			
			//si cerramos una conexi�n antes de solicitar otra, se podr� utilizar y no habr� error
//			En caso de que no se haya cerrado habr� un error y no podr� obtener conexi�n la �ltima solicitada
			
			Connection connection6 = connectionPool.getConnection();
			
			connection2.close();
			connection3.close();
			connection4.close();
			connection5.close();
			connection6.close();
			
			connectionPool.close();
		}
	}


