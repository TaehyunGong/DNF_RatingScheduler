package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

public class propertyGetAPIKEY {

	private Properties property ;
	private Map<String, String> keyBox;
	
	private propertyGetAPIKEY(){
		this.keyBox = new HashMap<String, String>();
	}
	
	private static class propertyGetAPIKEY_Singieton{
		private static final propertyGetAPIKEY instance = new propertyGetAPIKEY();
	}
	
	public static propertyGetAPIKEY getInstance(){
		return propertyGetAPIKEY_Singieton.instance;
	}
	
	/**
	 * @param propertyPath
	 * @description 초기의 properties 가져와서 전역변수로 키/값 추가
	 */
	public void addProperty(String propertyPath){
		this.property = new Properties();
		
		InputStream input = null ; 
		try {
			input = new FileInputStream(propertyPath);
			property.load(input);
			input.close();
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
		
		for(Entry<Object, Object> val : property.entrySet()) {
			keyBox.put((String)val.getKey(), (String)val.getValue());
		}
		
	}
	
	/**
	 * @param propertyPath
	 * @description 해당 경로에 있는 모든 .properties의 확장자만 읽어서 map에 넣는다.
	 */
	public void addAllProperty(String propertyPath) {
		File[] fileList = new File(propertyPath).listFiles();
		String fileExtend;
		String ext;
		
		for(File file : fileList) {
			fileExtend = file.getName();
			ext = fileExtend.substring(fileExtend.lastIndexOf(".")+1);
			if(ext.equals("properties")) {
				addProperty(file.getPath());
			}
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
