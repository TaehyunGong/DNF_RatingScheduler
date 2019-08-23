package main;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import vo.Equipment;

public class DnfItemRating {

	/**
	 * @return List<Equipment>
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 * @description /resources/DNF_Equipment.json 에서 모든 json을 가져와 파싱한다.
	 */
	public List<Equipment> getEquipment() throws JsonGenerationException, JsonMappingException, IOException{
		List<Equipment> equipmentList;
		
		ObjectMapper mapper = new ObjectMapper();
		
		InputStream input = null ;
		
		input = new FileInputStream("/resources/DNF_Equipment.json");
		
	    equipmentList = mapper.readValue(input, new TypeReference<List<Equipment>>(){});
	    
	    input.close();
	    
	    return equipmentList;
	}
	
	/**
	 * @param equipList
	 * @param apikey
	 * @return
	 * @throws Exception 
	 * @description dnf api요청으로 현재날짜 등급가져와서 값 삽입 후 반환
	 */
	public List<Equipment> ratingItem(List<Equipment> equipList, String apikey) throws Exception{
		
		httpConnection conn = httpConnection.getInstance();
		ObjectMapper objmap = new ObjectMapper();
		String responseMsg = null;
		
		String apiurl;
		try {
			for(Equipment equip : equipList){
				
				apiurl = "https://api.neople.co.kr/df/items/" + equip.getItemId() +"/shop?apikey=" + apikey;
				responseMsg = conn.HttpGetConnection(apiurl).toString();
				
				Equipment eq = objmap.readValue(responseMsg, Equipment.class);
				equip.setItemGradeName(eq.getItemGradeName());
				equip.setItemGradeValue(eq.getItemGradeValue());
				equip.setItemStatus(eq.getItemStatus());
				
			}
		}catch(Exception e) {
			throw new Exception(responseMsg);
		}
		
		return equipList;
	}
	
	/**
	 * @param list
	 * @return
	 * @description 세트 명 가져오기
	 */
	public Set<String> itemSetList(List<Equipment> list){
		Set<String> set = new HashSet<String>();
		for(Equipment equip : list) {
			set.add(equip.getSetItemName());
		}
		set.remove(null);
		
		return set;
	}
	
}
