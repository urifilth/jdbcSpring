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
		//Nos lee lo que escribimos en la línea de comandos o en la consola
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr); 
		return br.readLine();
	}
	
	public static void main(String[] args) throws SQLException, IOException{
		//nos conectamos a la base de datos
			Connection connection = DriverManager.getConnection("jdbc:h2:~/test");
			//leemos el primer comando
			String command = readCommand();
			
			//si el comando es "quit" nos cierra el shell, sino es, crea una sentencia SQL.
			while(!"quit".equals(command)) {
				try {
				//Ejecuta una sentencia usando statement.execute 
				Statement statement = connection.createStatement(); 
				boolean resultType = statement.execute(command);
				//el tipo de resultado va a definir si es verdadero significa que es una consulta, si es falso significa que es otro tipo de operación
				if(resultType==true) {
					ResultSet rs = statement.getResultSet();
					//si era una consulta obtenemos los resultados, con el método getMetaData para obtener las columnas, o información
					while(rs.next()) {
						ResultSetMetaData metaData = rs.getMetaData();
						//Se itera desde la primera columna hasta la última columna 
						for(int i=1; i<=metaData.getColumnCount();i++) {
							//por cada una traemos el valor en tipo String
							String value = rs.getString(i);
							//Imprimimos cada valor en la pantalla  para que vaya tabulado y con salto de línea 
							System.out.print("\t"+value);
						}
						System.out.println("");
					}
				}else {
					//sino es una consulta, imprimimos el número de registros impactados 
					System.out.println("Rows impacted: "+statement.getUpdateCount());
				}
				}catch(SQLException ex){
					//si se produce una excepción imprimimos el mensaje de error
					System.out.printf("\nError %s executing statement %s", ex.getMessage(), command);
				}finally {
					//Sin importar si hay excepción o no, seguimos leyendo líneas de comando a menos que se haya puesto "Quit"
					command = readCommand();
				}
			}
			//al final cerramos la conexión
			connection.close();
		
	}

}
