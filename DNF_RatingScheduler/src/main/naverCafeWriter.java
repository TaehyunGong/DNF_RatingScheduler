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

	httpConnection conn = httpConnection.getInstance(); 
	propertyGetAPIKEY getkey = propertyGetAPIKEY.getInstance();
	
	/**
	 * @param subject
	 * @param content
	 * @throws IOException
	 * @description 제목과 본문을 map으로 저장후 apikey와 같이 post요청으로 글작성
	 */
	public void cafeWrtier(String subject, String content) throws IOException {
		//apikey를 가져옴
		String apiURL = getkey.getKeyBox().get("apiURL");
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("subject", subject);
		map.put("content", content);
		
		conn.HttpPostConnection(apiURL, RequestAccessToken(), map);
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
	
	/**
	 * @return
	 * @throws IOException
	 * @description Naver access 토큰 발급
	 */
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
