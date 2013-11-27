package assignment3;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream.GetField;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class parser {
	public static void main(String args[]) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader("tables.txt"));
		parser pr = new parser();

		System.out.println(pr.getTableValuesPart(pr.getTable(br)));
		System.out.println(pr.getTableFieldPart(pr.getTable(br)));
		System.out.println(pr.getTableName(pr.getTableNamePart((pr.getTable(br)))));
		br.close();		
	}
	public ArrayList<String> get_tables() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("tables.txt"));
		ArrayList<String> line = new ArrayList<String>();
		while(br.ready()){
			line.add(getTable(br));
		}
		br.close();		
		return line;
	}
	
	public String getTable(BufferedReader br)throws IOException{
		StringBuilder sb = new StringBuilder();
		br.mark(1);
		if(br.read()!=-1){
			br.reset();
			boolean foundNextTable=false,endreached = false;
			sb.append(br.readLine());
			while(foundNextTable != true&&endreached!=true){
				br.mark(1);
				String line = br.readLine();
				if(!br.ready()){
					endreached = true;
				}
				else if(line.matches("(?i).*Table name:.*")){
					br.reset();
					foundNextTable = true;
				}
				else{
					sb.append(line);
					sb.append(" \r");
				}
				}
			}
		return sb.toString();
	}

	
	
	
	public String getTableName(String line){
		String tableName = null;
		if(line.matches("(?i).*Table name:.*")){
			tableName = line.replaceAll(" ", "");
			tableName = tableName.substring(tableName.lastIndexOf(":") + 1);
		}
		return tableName;
	}
	
	public String getTableNamePart(String line_u) throws IOException{
		BufferedReader br = new BufferedReader(new StringReader(line_u));
		StringBuilder sb = new StringBuilder();
		br.mark(1);
		if(br.read()!=-1){
			br.reset();
			boolean foundNextTable=false,endreached = false;
			sb.append(br.readLine());
			while(foundNextTable != true&&endreached!=true){
				br.mark(1);
				String line = br.readLine();
				if(!br.ready()){
					endreached = true;
				}
				else if(line.matches("(?i).*Table fields:.*")){
					br.reset();
					foundNextTable = true;
				}
				else{
					sb.append(line);
				}
				}
			}
		return sb.toString();
		}
	
	public String getTableFieldPart(String line_u) throws IOException{
		BufferedReader br = new BufferedReader(new StringReader(line_u));
		StringBuilder sb = new StringBuilder();
		br.mark(1);
		if(br.read()!=-1){
			br.reset();
			boolean ridOfTabName=true,foundNextTable=false,endreached = false;
			while(foundNextTable != true&&endreached!=true){
				String line = null;
				if(ridOfTabName == false){
				br.mark(1);
				line = br.readLine();
				if(!br.ready()){
					endreached = true;
				}
				else if(line.matches("(?i).*Table values:.*")){
					br.reset();
					foundNextTable = true;
				}
				else{
					sb.append(line);
				}
				}//if ridoftabname
				else {
					br.mark(1000);
					if(br.readLine().matches("(?i).*Table fields:.*")){
						ridOfTabName = false;
						br.reset();
					}
					else{
						
					}
				}
				}
			}
		return sb.toString();
		}

	public String getTableValuesPart(String line_u) throws IOException{
		BufferedReader br = new BufferedReader(new StringReader(line_u));
		StringBuilder sb = new StringBuilder();
			boolean ridOfTabName=true,brEmpty = false;
			while(!brEmpty){
				if(ridOfTabName == false){
					String line = br.readLine();
					if(line!=null)
					sb.append(line);
					else
						brEmpty = true;
				}
				else{
					br.mark(1000);
					String line = br.readLine();
					if(line.matches("(?i).*Table values:.*")){
						ridOfTabName = false;
						br.reset();
					}
					else{
					}
				}
			}
			return sb.toString();
			}	
}

//String l_line = sc.nextLine();
		//if(l_line.matches("(?i).*Table name:.*")){
//			tableName = l_line.replaceAll(" ", "");
//			tableName = tableName.substring(tableName.lastIndexOf(":") + 1);
//			System.out.println(tableName);
		//}
		//else if(tableName.isEmpty()){
//			l_line = sc.nextLine();
//			tableName = l_line.replaceAll(" ", "");
		//}
		//}
		//System.out.println(tableName);
		//sc.close();		