package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class PropertiesFileReader {

	public String getUserDefinedProperty(String prop) {
		
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(new File("./resources/browser-config.properties")));
			properties.load(new FileInputStream(new File("./resources/demoSite.properties")));
			properties.load(new FileInputStream(new File("./resources/excel-config.properties")));
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} 
		return properties.getProperty(prop);

	}

}
