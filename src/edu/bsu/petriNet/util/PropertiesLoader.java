package edu.bsu.petriNet.util;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

public class PropertiesLoader {
	private static HashMap<String,Properties> properties = new HashMap<String,Properties>();
	public static Properties getProperties(String name){
		Properties props = properties.get(name);
		if(props!=null){
			return props;
		} else{
			String resourceName ="/edu/bsu/petriNet/resources/"+ name + ".properties"; 
			props = new Properties();
			try(InputStream resourceStream = PropertiesLoader.class.getResourceAsStream(resourceName)) {
			    props.load(resourceStream);
			}catch(Exception e){
				e.printStackTrace();
			}
			properties.put(name, props);
			return props;
		}
	}
}