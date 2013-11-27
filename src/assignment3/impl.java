package assignment3;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.ClassNotFoundException;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.util.ArrayList;

public class impl {

	public static void main(String args[]) throws Exception{
		Connection connection = null;
		Statement statement = null;
		
		try{
			System.out.println("connecting to database");
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test","root","hello");
			System.out.println("Connection Successful");
			statement = connection.createStatement();
			new impl().uploadfile(statement);

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
	
//	public static void main(String args[]) throws Exception{
//	new impl().uploadfile();	
//	}
	
	@SuppressWarnings("unused")
	private void uploadfile(Statement statement) throws Exception {
//	private void uploadfile() throws Exception {
		parser p = new parser();
		BufferedReader br = new BufferedReader(new FileReader("tables.txt"));
		ArrayList<String > tables = p.get_tables(br);
		String stString = constructString(tables.get(0),p);
		statement.execute(stString);		
	}

	private String constructString(String string, parser p) throws IOException {
		StringBuilder sb = new StringBuilder("CREATE TABLE ");
		sb.append(p.getTableName(p.getTableNamePart(string))+" (");
		String[] delmString = removenull(p.getTableFields(p.getTableFieldPart(string)).split(" "));
		for(int i = 0; i< delmString.length;i++){
			if(i==0 || i % 2 == 0){
				sb.append(" "+delmString[i]+" ");
				
			}
			else{
				String temp = delmString[i].replaceAll(" ", "");
				sb.append(whatType(temp));
				if(i!=delmString.length-1){
					sb.append(",");
					}
				// based on string type (number), (string)
			}
		}
		sb.append(")");
		System.out.print(sb.toString());
		return sb.toString();
	}


	private String whatType(String delmString) {
		String s = "";
		if(delmString.equalsIgnoreCase("(number)")){
			s= "INT";
		}
		else if(delmString.equalsIgnoreCase("(text)")){
			s = "VARCHAR(255)";
		}

		return s;
	}

	private static String[] removenull(String[] delmString) {
		String[] ret = new String[delmString.length];
		int reti = 0;
		for(int i = 0; i < delmString.length;i++){
			if(delmString[i]==""||delmString[i].isEmpty())
			{}
			else{
				ret[reti] = delmString[i];
				reti++;
			}
		}
		return concateArray(ret,reti);
	}

	private static String[] concateArray(String[] ret, int reti) {
		String[] retu = new String[reti];
			for(int i = 0; i <reti;i++){
				retu[i] = ret[i];
			}
		return retu;
	}
	
}

//"CREATE TABLE table_name
//(
//column_name1 data_type(size),
//column_name2 data_type(size),
//column_name3 data_type(size),
//....
//);"
//"CREATE TABLE books (id INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,name_u VARCHAR(100),author VARCHAR(100),publisher VARCHAR(100))";
