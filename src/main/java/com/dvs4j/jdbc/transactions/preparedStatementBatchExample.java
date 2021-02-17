package com.dvs4j.jdbc.transactions;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;

import org.h2.tools.RunScript;

import com.github.javafaker.Faker;

public class preparedStatementBatchExample {
	public static void main(String[] args) throws SQLException, FileNotFoundException {
		try(Connection connection = DriverManager.getConnection("jdbc:h2:~/test");
				PreparedStatement statement = connection.prepareStatement("insert into person (name, last_name, nickname) values (?,?,?)");){
		System.out.println("Connected!");
		
		System.out.println("Executing script...");
		RunScript.execute(connection, new FileReader("src/main/resources/schema.sql"));
		connection.setAutoCommit(false);
		
		try {
			Faker faker = new Faker();
			connection.setAutoCommit(false);
			for(int i=0; i<100;i++) {
				statement.setString(1, faker.name().firstName());
				statement.setString(2, faker.name().lastName());
				statement.setString(3, faker.dragonBall().character());
				statement.addBatch();
				connection.commit();
			}
			int [] executeBatch = statement.executeBatch();
			for (int rowsImpacted : executeBatch) {
				System.out.println("Rows impacted: "+rowsImpacted);
			}
		}catch(SQLException e) {
			System.out.println("Error "+e.getMessage());
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
