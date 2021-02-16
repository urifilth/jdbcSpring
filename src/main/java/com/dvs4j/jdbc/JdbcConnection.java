package com.dvs4j.jdbc;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.h2.tools.RunScript;

public class JdbcConnection {

	public static void main(String[] args) {
		try {
			System.out.println("connecting...");
			Connection connection = DriverManager.getConnection("jdbc:h2:~/test");
			System.out.println("Connected!");
			
			System.out.println("Executing script...");
			RunScript.execute(connection, new FileReader("src/main/resources/schema.sql"));
			System.out.println("Script executed!");
			
			PreparedStatement statement = connection.prepareStatement("insert into person(name, last_name, nickname) values (?,?,?)");
			statement.setString(1, "Alex");
			statement.setString(2, "Bautista");
			statement.setString(3, "@raidentrace");
			
			int rows = statement.executeUpdate();
			System.out.println("Rows impacted: "+rows);
			
			statement.setString(1, "Juan");
			statement.setString(2, "Lopez");
			statement.setString(3, "El juan");
			boolean execute = statement.execute();
			System.out.println("Is insertion: "+(execute==false));
			rows = statement.getUpdateCount();
//			rows = statement.executeUpdate();
			System.out.println("Rows impacted: "+rows);
			statement.close();
			
			PreparedStatement statementQuery = connection.prepareStatement("select * from person");
			boolean execute2 = statementQuery.execute();
			System.out.println("Is resultSet: "+(execute));
			ResultSet rs = statementQuery.getResultSet();
//			ResultSet rs = statementQuery.executeQuery();
			while(rs.next()) {
				System.out.printf("\nId[%d] \tName [%s] \tLastName [%s] \tNickname [%s]", rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4));
			}
			System.out.println();
			statementQuery.close();
			
			
			PreparedStatement statementDelete = connection.prepareStatement("delete from person");
			int rowsDeleted = statementDelete.executeUpdate();
			System.out.println("Rows deleted: "+rowsDeleted);
			
			
			System.out.println("Closing...");
			connection.close();
			System.out.println("Closed!");
			
		} catch (SQLException | FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
