package assignment3Redo;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

import com.mysql.jdbc.StringUtils;

public class DBMS {
	Connection connection = null;
	Statement statement = null;
	ResultSet resultSet = null;

	static ArrayList<String> tableNames = new ArrayList<String>();
	
	String[] columns;
	String tableName;
	
	public void createConnection() throws Exception{
		
		try{
			System.out.println("connecting to database");
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test","root","hello");
			System.out.println("Connection Successful");
			statement = connection.createStatement();
//			resultSet = statement.executeQuery("Select * From books");
		}
		catch(ClassNotFoundException error){
			System.out.println("Error1: " + error.getMessage());
		}
		catch(SQLException error){
			System.out.println("Error2: " + error.getMessage());
		}

	}

	public void uploadfile() throws Exception {
		parser p = new parser();
		BufferedReader br = new BufferedReader(new FileReader("tables.txt"));
		ArrayList<String > tables = p.get_tables(br);
		for(int i = 0; i < tables.size();i++){
			String stString = constructString(tables.get(i),p);
			try{
				statement.execute(stString);
				String valString = constructIVString(p.getTableValuesPart(tables.get(i)),p);
				statement.execute(valString);}
			catch(SQLException error){
				System.out.println("Error: " + error.getMessage());
			}
			System.out.println("");
		}
		br.close();
	}

	
	private String constructIVString(String stString, parser p) {
		StringBuilder sb = new StringBuilder("INSERT INTO "+tableName+" (");
		for(int i = 0;i<columns.length;i++){
			sb.append(columns[i]);
			if(i!=columns.length-1){
				sb.append(",");
				}
			else{
				sb.append(") VALUES ");
			}
		}
		getAndPutValues(sb,stString,p);
		return sb.toString();
	}

	private void getAndPutValues(StringBuilder sb,String valStr,parser p) {
		String[] temp = p.getValues(valStr).split("\\n");
		String pd[] = removenull(temp);
		for(int i = 0 ;i < pd.length;i++){
			String[] tmpVal = pd[i].split(" ");
			String[] tmpVal1 = removenull(tmpVal);
			for(int j = 0; j<tmpVal1.length;j++){
				if(j!=columns.length-1){
					if(j == 0){
						sb.append("(");
					}
					sb.append("'"+tmpVal1[j]+"',");
				}
				else{
					sb.append("'"+tmpVal1[j]+"')");
					if(i!=pd.length-1){
						sb.append(",");
					}
				}
			}
		}
	}

	private String constructString(String string, parser p) throws IOException {
		StringBuilder sb = new StringBuilder("CREATE TABLE ");
		tableName = p.getTableName(p.getTableNamePart(string));
		tableNames.add(tableName);
		sb.append(tableName+" (");
		String[] delmString = removenull(p.getTableFields(p.getTableFieldPart(string)).split(" "));
		columns = new String[delmString.length/2];
		
		for(int i = 0; i< delmString.length;i++){
			if(i==0 || i % 2 == 0){
				sb.append(" "+delmString[i]+" ");
				columns[i/2] = delmString[i];
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
//		System.out.print(sb.toString());
		
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

	
	private String[] removenull(String[] delmString) {
		String[] ret = new String[delmString.length];
		int reti = 0;
		for(int i = 0; i < delmString.length;i++){
			if(delmString[i]==""||delmString[i].isEmpty()||StringUtils.isEmptyOrWhitespaceOnly(delmString[i]))
			{}
			else{
				ret[reti] = delmString[i];
				reti++;
			}
		}
		return concateArray(ret,reti);
	}

	private String[] concateArray(String[] ret, int reti) {
		String[] retu = new String[reti];
			for(int i = 0; i <reti;i++){
				String temp = ret[i].replaceAll("\\t", "");
				retu[i] = temp;
			}
		return retu;
	}
	
	
}
