package utilities;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

@SuppressWarnings("serial")
public class MyProperties extends Properties{
	
	public String getProperty(String propertyVariable){
		
		FileReader reader=null;
		try {
			reader = new FileReader("D:\\EclipseWorkspace\\massmutual\\src\\test\\resources\\PropertiesFile\\SIT_environment.properties");
		} catch (FileNotFoundException e1) {
			System.out.println("File NOT Found");
					e1.printStackTrace();
		}  
	      
	    Properties p=new Properties();  
	    try {
			p.load(reader);
		} catch (IOException e) {
			e.printStackTrace();
		}  
	    propertyVariable = p.getProperty(propertyVariable);
		System.out.println(propertyVariable);
		return propertyVariable;
		
	}

}
