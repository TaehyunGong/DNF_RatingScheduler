package main;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

public class propertyGetAPIKEY {

	public static String CLIENT_ID = "ClientId";
	public static String CLIENT_SECRETKEY = "ClientSecretKey";
	public static String DNF_APIKEY = "DNFApiKey";
	public static String REFESH_TOKEN = "RefeshToken";
	public static String ACCESS_TOKEN = "AccessToken";
	
	private Properties property ;
	private Map<String, String> keyBox;
	
	private propertyGetAPIKEY(){}
	
	private static class propertyGetAPIKEY_Singieton{
		private static final propertyGetAPIKEY instance = new propertyGetAPIKEY();
	}
	
	public static propertyGetAPIKEY getInstance(){
		return propertyGetAPIKEY_Singieton.instance;
	}
	
	// properties 가져와서 전역변수로 키값 삽입
	public void initProperty(String propertyPath){
		this.property = new Properties();
		
		InputStream input = null ; 
		try {
			input = new FileInputStream(propertyPath);
			property.load(input);
			input.close();
			System.out.println("properties load success");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("properties load false");
		}finally {
			try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		keyBox = new HashMap<String, String>();
		
		for(Entry<Object, Object> val : property.entrySet()) {
			System.out.println("getBox Insert - " + val.getKey() + " - " + val.getValue());
			keyBox.put((String)val.getKey(), (String)val.getValue());
		}
		
	}

	public Properties getProperty() {
		return property;
	}

	public void setProperty(Properties property) {
		this.property = property;
	}

	public Map<String, String> getKeyBox() {
		return keyBox;
	}

	public void setKeyBox(Map<String, String> keyBox) {
		this.keyBox = keyBox;
	}
	
	
}
