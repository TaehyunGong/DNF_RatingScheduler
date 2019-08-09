package main;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Properties;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class httpConnection {

	Properties property ;
	public static final String ENCODING = "UTF-8";
	
	//네이버 api키
	private String RefeshToken;
	private String AccessToken;
	private String ClientId;
	private String ClientSecretKey;
	
	//던파 api키
	private String DNFApiKey;
	
	public httpConnection(){}
	
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
		
		RefeshToken = (String) property.get("RefeshToken");
		AccessToken = (String) property.get("AccessToken");
		ClientId = (String) property.get("ClientId");
		ClientSecretKey = (String) property.get("ClientSecretKey");
		DNFApiKey = (String) property.get("DNFApiKey");
		
	}
	
	//access 토큰 발급
	public void RequestAccessToken() throws IOException {
        String apiURL = "https://nid.naver.com/oauth2.0/token?";
        apiURL += "grant_type=refresh_token";
        apiURL += "&client_id=" + ClientId;
        apiURL += "&client_secret=" + ClientSecretKey;
        apiURL += "&refresh_token=" + RefeshToken;
        
        URL url = new URL(apiURL);
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("GET");
        
        int responseCode = con.getResponseCode();
        BufferedReader br;
        if(responseCode==200) { // 정상 호출
            br = new BufferedReader(new InputStreamReader(con.getInputStream()));
        } else {  // 에러 발생
            br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
        }
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = br.readLine()) != null) {
            response.append(inputLine);
        }
        br.close();
        
        HashMap<String, String> rs = jsonParser(response);
        
        //accesstoken 재발급
        this.AccessToken = rs.get("access_token");
        
	}
	
	//json 파싱
	public HashMap<String, String> jsonParser(StringBuffer response) throws JsonParseException, JsonMappingException, IOException{
		return new ObjectMapper().readValue(response.toString(), HashMap.class);
	}
	
	//파라메터 URL 인코딩
	public String URLencoder(String contents) throws UnsupportedEncodingException {
		return URLEncoder.encode(URLEncoder.encode(contents, ENCODING), "MS949");
	}

	public Properties getProperty() {
		return property;
	}

	public void setProperty(Properties property) {
		this.property = property;
	}

	public String getClientId() {
		return ClientId;
	}

	public void setClientId(String clientId) {
		ClientId = clientId;
	}

	public String getClientSecretKey() {
		return ClientSecretKey;
	}

	public void setClientSecretKey(String clientSecretKey) {
		ClientSecretKey = clientSecretKey;
	}

	public String getDNFApiKey() {
		return DNFApiKey;
	}

	public void setDNFApiKey(String dNFApiKey) {
		DNFApiKey = dNFApiKey;
	}

	public String getRefeshToken() {
		return RefeshToken;
	}

	public void setRefeshToken(String refeshToken) {
		RefeshToken = refeshToken;
	}

	public String getAccessToken() {
		return AccessToken;
	}

	public void setAccessToken(String accessToken) {
		AccessToken = accessToken;
	}
	
	
	
}
