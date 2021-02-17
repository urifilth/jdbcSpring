package com.dvs4j.jdbc.dataSources;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;

import org.h2.jdbcx.JdbcDataSource;
import org.h2.tools.RunScript;

public class DatasourceExample {
	public static void main(String[] args) throws SQLException, FileNotFoundException {
		JdbcDataSource datasource = new JdbcDataSource();
		datasource.setUrl("jdbc:h2:~/test");
		
		try(Connection connection = datasource.getConnection();){
		RunScript.execute(connection, new FileReader("src/main/resources/schema.sql"));
		
		
		}
	}

}
