package assignment3Redo;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream.GetField;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class parser {

	public ArrayList<String> get_tables(BufferedReader br) throws IOException {
		ArrayList<String> line = new ArrayList<String>();
		while(br.ready()){
			String s = getTable(br);
			line.add(s);
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
					sb.append(line);
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
	
	public String getTableFields(String line){
		String tableName = null;
		if(line.matches("(?i).*Table fields:.*")){
			tableName = line.substring(line.lastIndexOf(":") + 1);
		}
		return tableName;
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
					sb.append(line+"\n");
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

	public String getValues(String line){
		String tableName = null;
			tableName = line.substring(line.lastIndexOf(":") + 1);
		return tableName;
	}


}
