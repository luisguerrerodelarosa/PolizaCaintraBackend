package com.caintra.Configuracion;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;



public class Utils {

	public boolean validaConexion(DBManager dataSource){
		if(dataSource == null)
			return false;
		try {
			if(dataSource.getMainDB().getConnection().isClosed())
				return false;
			else
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public  String getProperties(String property){
		Properties prop = new Properties();
		InputStream input = null;
		try {
			input = getClass().getClassLoader().getResourceAsStream("config.properties");
			
			if(input != null){
				prop.load(input);
				return prop.getProperty(property).trim();
			}else{
				//throw new FileNotFoundException("");
				return "";
			}
		}catch(Exception e){
			return "";
		}
		finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
					
				}
			}
		}
		
		
	}
}
