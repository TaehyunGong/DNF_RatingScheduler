package main;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

import vo.Equipment;
import vo.ItemStatus;

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
	
	public void cafeWrtier(String subject, String content) throws IOException {
		//apikey를 가져옴
//		getkey.initProperty(main.path + "APIKEY.properties");
		
		String apiURL = "https://openapi.naver.com/v1/cafe/29837103/menu/1/articles";	//테스트용  테스트950 카페
//		String apiURL = "https://openapi.naver.com/v1/cafe/11276312/menu/48/articles";	//실제 운영할 카페 던공카
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일 테이 등급 ");
		
		DnfItemRating dnf = new DnfItemRating();
		List<Equipment> list = dnf.ratingItem(dnf.getEquipment(), getkey.getKeyBox().get("DNFApiKey"));
		
//		String subject = sdf.format(new Date()) + list.get(0).getItemGradeName() + "(" + getItemMaxRating(list) + "%)";	//글 제목
//		String content = contentHtmlMake(dnf, list);	//글 본문
		
		//테스트 테이블 추가
		content += "<br><br><br>";
		content += listCalendar(null);
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("subject", subject);
		map.put("content", content);
		
		System.out.println(subject);
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
			sb.append("<td width='25%'> " + equip.getItemGradeName() + " (" + equip.getItemGradeValue() + "%) </td>");
			sb.append("<td width='43%'> " + htmlTagInsertList(equip.getMaxItemStatus(), equip.getItemStatus()) +" </td>");
			
			sb.append("</tr>");
			
		}
		
		sb.append("</table>");
		return sb.toString();
	}
	
	//아이템 가장 높은 등급
	public int getItemMaxRating(List<Equipment> list){
		int max = 0;
		
		for(Equipment equip : list)
			if(Integer.parseInt(equip.getItemGradeValue()) > max)
				max = Integer.parseInt(equip.getItemGradeValue());
		
		return max;
	}
	
	//수치값과 차이점
	public String htmlTagInsertList(List<ItemStatus> maxList, List<ItemStatus> list) {
		String content = "";
		
		for(int i=0; i<list.size(); i++) {
			ItemStatus status = list.get(i);
			
			if(containList.contains(status.getName())) {
				content += status.getName() + " : " + status.getValue() + "<font style='color:#ff0000; font-weight: bold;'>(+" + 
						   (Integer.parseInt(maxList.get(i).getValue()) - Integer.parseInt(status.getValue())) + ")</font> <br>";
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
	
	public String listCalendar(List list) {
		
		Calendar cal = Calendar.getInstance();
		cal.set(cal.DAY_OF_MONTH, 1);
		int maxDay = cal.getActualMaximum(cal.DAY_OF_MONTH);
		int startWeek = cal.get(cal.DAY_OF_WEEK);

		StringBuffer html = new StringBuffer();
		html.append("<table border='0' width='588px' height='40' cellspacing='1' cellpadding='1' bgcolor='#B7BBB5'>");
		html.append("<tbody>");
		html.append("<tr bgcolor='#FFFFFF'>");
		
		for(int i=1; i<maxDay+startWeek; i++) {
			if(startWeek > i){
				html.append("<td width='84px'></td>");
			}else {
				html.append("<td style='font-size:9pt;font-family:2820189_9;' width='84px'>" + (i+1-startWeek) + "</td>");
			}
			
			if(i%7==0) {
				html.append("</tr>");
				html.append("<tr bgcolor='#FFFFFF'>");
			}
		}
		html.append("</tr>");
		
		html.append("</tbody>");
		html.append("</table>");
		
		return html.toString();
	}
	
	// Naver access 토큰 발급
	public String RequestAccessToken() throws IOException {
		String AccessToken;
		
        String apiURL = "https://nid.naver.com/oauth2.0/token?";
        apiURL += "grant_type=refresh_token";
        apiURL += "&client_id=" + getkey.getKeyBox().get("ClientId");
        apiURL += "&client_secret=" + getkey.getKeyBox().get("ClientSecretKey");
        apiURL += "&refresh_token=" + getkey.getKeyBox().get("RefeshToken");
        
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
