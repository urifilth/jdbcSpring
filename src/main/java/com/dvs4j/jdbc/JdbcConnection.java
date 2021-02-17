package com.dvs4j.jdbc;
//Las clases que tienen que ver con jdbc se encuentran en los paquetes java.sql y javax.sql, esas clases ya vienen en java standar edition por lo que no
//necesitan ninguna dependencia adicional.
//Puedo utilizar  clases de el proveedor de bases de dato. Por ejemplo, org.h2.tools.RunScript
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
			//DriverManager permite crear una conexión a una base de datos
			/**
			 * Necesita una URL y puede recibir configuraciones adicionales como el usuario y el password
			 */
			Connection connection = DriverManager.getConnection("jdbc:h2:~/test");
			System.out.println("Connected!");
			
			/**
			 * El runScript nos permite ejecutar una serie de sentencias SQL en el ejemplo se borra la tabla si existe
			 * y se vuelve a crear
			 */
			System.out.println("Executing script...");
			RunScript.execute(connection, new FileReader("src/main/resources/schema.sql"));
			System.out.println("Script executed!");
			
			/**
			 * A través del objeto connection se crea uno de tipo statement, preparedStatement y CallableStatement
			 */
			PreparedStatement statement = connection.prepareStatement("insert into person(name, last_name, nickname) values (?,?,?)");
			
			/**
			 * PreparedStatement permite ejecutarlo multiples veces con diferentes parámetros.
			 */
			statement.setString(1, "Alex");
			statement.setString(2, "Bautista");
			statement.setString(3, "@raidentrace");
			
			/**
			 * Devuelve el número de registros impactados 
			 */
			int rows = statement.executeUpdate();
			System.out.println("Rows impacted: "+rows);
			
			statement.setString(1, "Juan");
			statement.setString(2, "Lopez");
			statement.setString(3, "El juan");
			
			/**
			 * Puede devolver o el número de registros impactados o un resultSet 
			 */
			boolean execute = statement.execute();
			System.out.println("Is insertion: "+(execute==false));
			rows = statement.getUpdateCount();
//			rows = statement.executeUpdate();
			System.out.println("Rows impacted: "+rows);
			/**
			 * Los statements se deben de cerrar 
			 */
			statement.close();
			
			PreparedStatement statementQuery = connection.prepareStatement("select * from person");
			boolean execute2 = statementQuery.execute();
			System.out.println("Is resultSet: "+(execute));
			ResultSet rs = statementQuery.getResultSet();
//			ResultSet rs = statementQuery.executeQuery();
			
			/**
			 * El método next nos mueve el cursor y nos dice si hay valores o no
			 */
			while(rs.next()) {
				//Los métodos getXXX nos devuelven el valor de la columna
				System.out.printf("\nId[%d] \tName [%s] \tLastName [%s] \tNickname [%s]", rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4));
			}
			System.out.println();
			statementQuery.close();
			
			
			PreparedStatement statementDelete = connection.prepareStatement("delete from person");
			int rowsDeleted = statementDelete.executeUpdate();
			System.out.println("Rows deleted: "+rowsDeleted);
			
			
			System.out.println("Closing...");
			connection.close();
			//las conexiones siempre se deben de cerrar
			System.out.println("Closed!");
			
			//en caso de error se producirá una SQLException
		} catch (SQLException | FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
