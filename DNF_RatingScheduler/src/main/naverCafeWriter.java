package main;

import java.io.IOException;
import java.text.SimpleDateFormat;
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
		containList.add("화속성강화");
		containList.add("수속성강화");
		containList.add("명속성강화");
		containList.add("암속성강화");
		containList.add("물리 공격력");
		containList.add("마법 공격력");
		containList.add("독립 공격력");
	}

	httpConnection conn = httpConnection.getInstance(); 
	propertyGetAPIKEY getkey = propertyGetAPIKEY.getInstance();
	
	public void cafeWrtier() throws IOException {
		//apikey를 가져옴
		getkey.initProperty("resources/APIKEY.properties");
		
		String apiURL = "https://openapi.naver.com/v1/cafe/29837103/menu/1/articles";
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일 등급 ");
		
		DnfItemRating dnf = new DnfItemRating();
		List<Equipment> list = dnf.ratingItem(dnf.getEquipment(), getkey.getKeyBox().get(getkey.DNF_APIKEY));
		
		String subject = sdf.format(new Date()) + list.get(0).getItemGradeName() + "(" + requestItemAverageRating(list) + "%)";	//글 제목
		String content = contentHtmlMake(dnf, list);	//글 본문
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("subject", subject);
		map.put("content", content);
		
		conn.HttpPostConnection(apiURL, RequestAccessToken(), map);
	}
	
	// 카페 본문 내용
	public String contentHtmlMake(DnfItemRating dnf, List<Equipment> list) throws IOException {
		StringBuffer sb = new StringBuffer();
		
		//sb.append("<h1>CTRL+F 로 원하는 장비 등급을 확인하세요.</h1>");
		sb.append("<table style='width:100%;'>");
		
		String checkSetName = "";	// 첫번째 세트명
		
		for(Equipment equip : list){
			if(!checkSetName.equals(nvlString(equip.getSetItemName(), "천공의 유산"))) {
				sb.append("<tr><td colspan='4' style='font-size: 20px; font-weight: bold;'> -" + nvlString(equip.getSetItemName(), "천공의 유산") + "</td> </tr>");
			}
			checkSetName = nvlString(equip.getSetItemName(), "천공의 유산");	// 세트명 
			
			sb.append("<tr>");
			
			sb.append("<td width='8%'> <img src='https://img-api.neople.co.kr/df/items/" + equip.getItemId() +"'> </td>");
			sb.append("<td width='25%'> " + equip.getItemName() +" </td>");
			sb.append("<td width='20%'> " + equip.getItemGradeName() + " (" + equip.getItemGradeValue() + "%) </td>");
			sb.append("<td width='48%'> " + htmlTagInsertList(equip.getMaxItemStatus(), equip.getItemStatus()) +" </td>");
			
			sb.append("</tr>");
			
		}
		
		sb.append("</table>");
		return sb.toString();
	}
	
	//아이템 평균 등급
	public int requestItemAverageRating(List<Equipment> list){
		int sum = 0;
		
		for(Equipment equip : list)
			sum += Integer.parseInt(equip.getItemGradeValue());
		
		return sum / list.size();
	}
	
	//수치값과 차이점
	public String htmlTagInsertList(List<itemStatus> maxList, List<itemStatus> list) {
		String content = "";
		
		for(int i=0; i<list.size(); i++) {
			itemStatus status = list.get(i);
			
			if(containList.contains(status.getName())) {
				content += status.getName() + " : " + status.getValue() + "<b style='color:red;'>(+" + 
						   (Integer.parseInt(maxList.get(i).getValue()) - Integer.parseInt(status.getValue())) + ")</b> <br>";
			}
		}
		
		return content;
	}
	
	//nvl('a')
	public String nvlString(String str) {
		if(str == null)
			str = "";
		return str;
	}
	
	//nvl('a', 'b')
	public String nvlString(String str, String replace) {
		if(str == null)
			str = replace;
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
