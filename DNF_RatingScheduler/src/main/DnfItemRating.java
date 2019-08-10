package main;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class DnfItemRating {

	public void getEquipment() throws JsonGenerationException, JsonMappingException, IOException{
		
		ObjectMapper mapper = new ObjectMapper();
		
		InputStream input = null ; 
		input = new FileInputStream("resources/DNF_Equipment.json");
		Equipment[] myObjects = mapper.readValue(input, Equipment[].class);
		input.close();
		
		for(Equipment eq : myObjects){
			System.out.println(eq);
		}
		
//	    List<Equipment> myObjects2 = mapper.readValue("resources/DNF_Equipment.json", new TypeReference<List<Equipment>>(){});
	    
	}
}
