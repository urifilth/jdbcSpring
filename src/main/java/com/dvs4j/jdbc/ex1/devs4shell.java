package com.dvs4j.jdbc.ex1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class devs4shell {
	
	public static String readCommand() throws IOException {
		System.out.printf("\n ConsolaDelUri->");
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr); 
		return br.readLine();
	}
	
	public static void main(String[] args) throws SQLException, IOException{
			Connection connection = DriverManager.getConnection("jdbc:h2:~/test");
			String command = readCommand();
			
			while(!"quit".equals(command)) {
				try {
//				JAVA.SQL.STATEMENT STATEMENT = CONNECTION.CREATESTATEMENT();
				Statement statement = connection.createStatement();
				boolean resultType = statement.execute(command);
				if(resultType==true) {
					ResultSet rs = statement.getResultSet();
					while(rs.next()) {
						ResultSetMetaData metaData = rs.getMetaData();
						for(int i=1; i<=metaData.getColumnCount();i++) {
							String value = rs.getString(i);
							System.out.print("\t"+value);
						}
						System.out.println("");
					}
				}else {
					System.out.println("Rows impacted: "+statement.getUpdateCount());
				}
				}catch(SQLException ex){
					System.out.printf("\nError %s executing statement %s", ex.getMessage(), command);
				}finally {
					command = readCommand();
				}
			}
			connection.close();
		
	}

}
