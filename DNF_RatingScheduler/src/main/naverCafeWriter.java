package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

import main.Equipment.itemStatus;

public class naverCafeWriter {

	ArrayList<String> containList;
	
	public naverCafeWriter() {
		containList = new ArrayList<String>();
		containList.add("힘");
		containList.add("지능");
		containList.add("체력");
		containList.add("정신력");
		containList.add("모든 속성 강화");
		containList.add("물리 방어력");
		containList.add("마법 방어력");
		containList.add("화속성 강화");
		containList.add("수속성 강화");
		containList.add("명속성 강화");
		containList.add("암속성 강화");
		containList.add("물리 공격력");
		containList.add("마법 공격력");
		containList.add("독립 공격력");
	}

	httpConnection conn = httpConnection.getInstance(); 
	propertyGetAPIKEY getkey = propertyGetAPIKEY.getInstance();
	
	public void cafeWrtier() throws IOException {
		getkey.initProperty("resources/APIKEY.properties");		

		String apiURL = "https://openapi.naver.com/v1/cafe/29837103/menu/1/articles";
		Map<String, String> map = new HashMap<String, String>();
		
		DnfItemRating dnf = new DnfItemRating();
		String subject = new Date().toString();	//글 제목
		String content = contentHtmlMake(dnf);	//글 본문
		
		map.put("subject", subject);
		map.put("content", content);
		conn.HttpPostConnection(apiURL, RequestAccessToken(), map);
	}
	
	// 카페 본문 내용
	public String contentHtmlMake(DnfItemRating dnf) throws IOException {
		StringBuffer sb = new StringBuffer();
		
		sb.append("<table style='width:100%'>");
		
		List<Equipment> list = dnf.ratingItem(dnf.getEquipment(), getkey.getKeyBox().get(getkey.DNF_APIKEY));
		
		int count= 0;
		String checkSetName = list.get(0).getSetItemName();	// 첫번째 세트명
		
		for(Equipment equip : list){
			if(checkSetName.equals(equip.getSetItemName())) {
				if(count == 0) {
					sb.append("<tr class='setline'><td>" + equip.getSetItemName() + "</td> </tr>");
				}
			}else if(equip.getSetItemName() != null) {
			}
			checkSetName = equip.getSetItemName();	// 세트명 
			
			sb.append("<tr>");
			
			sb.append("<td> <img src='https://img-api.neople.co.kr/df/items/" + equip.getItemId() +"' alt='" + equip.getItemName() + "'> </td>");
			sb.append("<td> " + equip.getItemName() +" </td>");
			sb.append("<td> " + nvlString(equip.getSetItemName()) +" </td>");
			sb.append("<td> " + equip.getItemGradeName() + " : (" + equip.getItemGradeValue() + "%) </td>");
			sb.append("<td> " + htmlTagInsertList(equip.getMaxItemStatus(), equip.getItemStatus()) +" </td>");
			
			sb.append("</tr>");
			
		}
		
		sb.append("</table>");
		return sb.toString();
	}
	
	public String htmlTagInsertList(List<itemStatus> maxList, List<itemStatus> list) {
		String content = "";
		
		for(int i=0; i<list.size(); i++) {
			itemStatus status = list.get(i);
			
			if(containList.contains(status.getName())) {
				content += status.getName() + " : " + status.getValue() + "(+" + (Integer.parseInt(maxList.get(i).getValue()) - Integer.parseInt(status.getValue())) + ")<br>";
			}
		}
		
		return content;
	}
	
	public String nvlString(String str) {
		if(str == null)
			str = "";
		return str;
	}
	
	// Naver access 토큰 발급
	public String RequestAccessToken() throws IOException {
		String AccessToken;
		
        String apiURL = "https://nid.naver.com/oauth2.0/token?";
        apiURL += "grant_type=refresh_token";
        apiURL += "&client_id=" + getkey.getKeyBox().get(getkey.CLIENT_ID);
        apiURL += "&client_secret=" + getkey.getKeyBox().get(getkey.CLIENT_SECRETKEY);
        apiURL += "&refresh_token=" + getkey.getKeyBox().get(getkey.REFESH_TOKEN);
        
        StringBuffer resultResponse = conn.HttpGetConnection(apiURL);
        HashMap<String, String> rs = new ObjectMapper().readValue(resultResponse.toString(), HashMap.class);
        
        if(rs.get("access_token") == null){
        	System.out.println("access_token 를 못가져옴");
        	throw new NullPointerException();
        }
        
        //accesstoken 재발급
        AccessToken = "Bearer " + rs.get("access_token");
        
        return AccessToken;
	}	
	
}
