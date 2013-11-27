package assignment3;
import java.lang.ClassNotFoundException;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;

public class impl {

	public static void main(String args[]){
		Connection connection = null;
		Statement statement = null;
		
		try{
			System.out.println("connecting to database");
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test","root","hello");
			System.out.println("Connection Successful");
			statement = connection.createStatement();
			statement.execute("CREATE TABLE books (id INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,name_u VARCHAR(100),author VARCHAR(100),publisher VARCHAR(100))");
		}
		catch(ClassNotFoundException error){
			System.out.println("Error1: " + error.getMessage());
		}
		catch(SQLException error){
			System.out.println("Error2: " + error.getMessage());
		}
		finally{
			if(connection!=null)try{connection.close();}catch(SQLException ignore){}
			if(statement!=null)try{statement.close();}catch(SQLException ignore){}
		}
	}
	
}
