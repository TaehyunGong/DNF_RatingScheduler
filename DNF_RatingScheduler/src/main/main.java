package main;

import java.io.IOException;
import java.util.HashMap;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class main {

	public static void main(String[] args) throws IOException {
		
//		httpConnection conn = httpConnection.getInstance(); 
//		propertyGetAPIKEY getkey = propertyGetAPIKEY.getInstance();
//		getkey.initProperty("resources/APIKEY.properties");		
//
//		String apiURL = "https://openapi.naver.com/v1/cafe/29837103/menu/1/articles";
//		Map<String, String> map = new HashMap<String, String>();
//		map.put("subject", new Date().toString());
//		map.put("content", "<h1 color='red'>html 태그 먹히냐?</h1>");
//		
//		conn.HttpPostConnection(apiURL, new main().RequestAccessToken(),map);		
//		System.out.println(conn.HttpGetConnection("https://api.neople.co.kr/df/servers?apikey=P4GiGs1KtJyD3VoMB3jkgzDsMI4tDNGi"));
				
		DnfItemRating dnf = new DnfItemRating();
		dnf.getEquipment();
	}

	
	//access 토큰 발급
	public String RequestAccessToken() throws IOException {
		String AccessToken;
		httpConnection conn = httpConnection.getInstance(); 		
		propertyGetAPIKEY getkey = propertyGetAPIKEY.getInstance();		
		
        String apiURL = "https://nid.naver.com/oauth2.0/token?";
        apiURL += "grant_type=refresh_token";
        apiURL += "&client_id=" + getkey.getKeyBox().get(getkey.CLIENT_ID);
        apiURL += "&client_secret=" + getkey.getKeyBox().get(getkey.CLIENT_SECRETKEY);
        apiURL += "&refresh_token=" + getkey.getKeyBox().get(getkey.REFESH_TOKEN);
        
        StringBuffer resultResponse = conn.HttpGetConnection(apiURL);
        HashMap<String, String> rs = new main().jsonParser(resultResponse);
        
        if(rs.get("access_token") == null){
        	System.out.println("access_token 를 못가져옴");
        	throw new NullPointerException();
        }
        
        //accesstoken 재발급
        AccessToken = "Bearer " + rs.get("access_token");
        
        return AccessToken;
	}	
	
	//json 파싱
	public HashMap<String, String> jsonParser(StringBuffer response) throws JsonParseException, JsonMappingException, IOException{
		return new ObjectMapper().readValue(response.toString(), HashMap.class);
	}
}
