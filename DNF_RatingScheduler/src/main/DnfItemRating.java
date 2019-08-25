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
import vo.ItemStatus;

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
	public int ratingItem(List<Equipment> equipList, List<Equipment> yetEquipList, String apikey) throws Exception{
		
		httpConnection conn = httpConnection.getInstance();
		ObjectMapper objmap = new ObjectMapper();
		String responseMsg = null;
		
		int updateStatusSize = 0;
		
		String apiurl;
		try {
			for(int i=0; i<equipList.size(); i++){
				Equipment equip = equipList.get(i);
				
				//Status가 null일경우에만 값을 넣어준다.
				if(equip.getItemStatus() == null){
					apiurl = "https://api.neople.co.kr/df/items/" + equip.getItemId() +"/shop?apikey=" + apikey;
					responseMsg = conn.HttpGetConnection(apiurl).toString();
					
					Equipment eq = objmap.readValue(responseMsg, Equipment.class);
					
					//아직 업데이트가 안될수 있기때문에 어제자 등급과 status의 value를 일일히 대조한다.
					if(equalsTodayStatus(yetEquipList.get(i).getItemStatus(), eq.getItemStatus())){
						System.out.println(eq.getItemName() + " : 아직 업데이트 안됨");
						continue;
					}
					
					equip.setItemGradeName(eq.getItemGradeName());
					equip.setItemGradeValue(eq.getItemGradeValue());
					equip.setItemStatus(eq.getItemStatus());
				}
				
			}
			System.out.println("완료");
		}catch(Exception e) {
			e.printStackTrace();
			throw new Exception(responseMsg);
		}
		
		return updateStatusSize;
	}
	
	/**
	 * @param selectStatus
	 * @param receiveStatus
	 * @return boolean
	 * @description 어제 아이템등급 %와 오늘받아온 %가 같다면 아직 Rest쪽에서 업데이트가 안됬으므로 false를 반환하여 재요청을 한다.
	 */
	public boolean equalsTodayStatus(List<ItemStatus> selectStatus, List<ItemStatus> receiveStatus){
		boolean check = false;
		
		//어제 아이템등급 %와 오늘받아온 %가 같다면 아직 Rest쪽에서 업데이트가 안됬으므로 false를 반환하여 재요청을 한다.
		for(int i=0; i<selectStatus.size(); i++){
			if(isStatusValue(selectStatus.get(i), receiveStatus.get(i))){
				check = true;
				break;
			}
		}
		
		return check;
	}
	
	/**
	 * @param dbStatus
	 * @param receiveStatus
	 * @return boolean
	 * @description 파라메터 status의 value값을 서로 비교후 일치하면 true를 반환
	 */
	public boolean isStatusValue(ItemStatus dbStatus, ItemStatus receiveStatus){
		boolean check = false;
		
		if(dbStatus.getValue().equals(receiveStatus.getValue())){
			System.out.println(dbStatus.getValue() + ",  " +  receiveStatus.getValue());
			check = true;
		}
		
		return check;
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
