package com.dvs4j.jdbc.transactions;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;

import org.h2.tools.RunScript;

public class devs4jTransactions {
	public static void main(String[] args) throws SQLException, FileNotFoundException {
		try(Connection connection = DriverManager.getConnection("jdbc:h2:~/test");
				PreparedStatement statement = connection.prepareStatement("insert into person (name, last_name, nickname) values (?,?,?)");){
		System.out.println("Connected!");
		
		System.out.println("Executing script...");
		RunScript.execute(connection, new FileReader("src/main/resources/schema.sql"));
		connection.setAutoCommit(false);
		Savepoint sp=null;
		try {
		statement.setString(1, "Uriel");
		statement.setString(2, "Mendoza");
		statement.setString(3, "Urifilth");
		statement.executeUpdate();

//		sp = connection.setSavepoint("Primer save point");
		
		statement.setString(1, "Juan");
		statement.setString(2, "Lopez");
		statement.setString(3, "El juan");
		statement.executeUpdate();
		
		sp = connection.setSavepoint("Primer save point");
		
		statement.setString(1, "pancho");
		statement.setString(2, null);
		statement.setString(3, "el pancho");
		statement.executeUpdate();
		
		connection.commit();
		}catch(SQLException e) {
			System.out.println("Rolling back because "+e.getMessage());
			if(sp==null) {
				connection.rollback();
			}else {
				connection.rollback(sp);
			}
		}finally {
			connection.setAutoCommit(true);
		}
		System.out.println("Records persisted");
		
		PreparedStatement ps = connection.prepareStatement("select * from person");
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			System.out.printf("\nId [%d] Name [%s] last_name [%s] nickname [%s]", rs.getInt(1),rs.getString(2), rs.getString(3),rs.getString(4));
			
			
		}
		connection.close();
		}
	}

}
