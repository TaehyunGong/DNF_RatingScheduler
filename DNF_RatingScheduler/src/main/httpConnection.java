package main;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
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
        
        StringBuffer resultResponse = HttpGetConnection(apiURL);
        HashMap<String, String> rs = jsonParser(resultResponse);
        
        //accesstoken 재발급
        this.AccessToken = "Bearer " + rs.get("access_token");
        
	}
	
	//get방식 rest 호출시 사용
	public StringBuffer HttpGetConnection(String apiURL) throws IOException {
		StringBuffer response = new StringBuffer();

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
        while ((inputLine = br.readLine()) != null) {
            response.append(inputLine);
        }
        br.close();
		
		return response;
	}
	
	//post방식 rest 호출시 사용
	public StringBuffer HttpPostConnection(String apiURL, Map<String, String> map) throws IOException {
		StringBuffer response = new StringBuffer();
		
	      URL url = new URL(apiURL);
	      HttpURLConnection con = (HttpURLConnection)url.openConnection();
	      con.setRequestMethod("POST");
	      con.setRequestProperty("Authorization", AccessToken);
	      
	      // post request
	      // 해당 string은 UTF-8로 encode 후 MS949로 재 encode를 수행한 값
	      String subject = URLencoder(map.get("subject"));
	      String content = URLencoder(map.get("content"));
	      String postParams = "subject="+subject + "&content="+ content;
	      
	      con.setDoOutput(true);
	      DataOutputStream wr = new DataOutputStream(con.getOutputStream());
	      wr.writeBytes(postParams);
	      wr.flush();
	      wr.close();
	      
	      int responseCode = con.getResponseCode();
	      BufferedReader br;
	      if(responseCode==200) { // 정상 호출
	          br = new BufferedReader(new InputStreamReader(con.getInputStream()));
	      } else {  // 에러 발생
	          br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
	      }
	      
	      String inputLine;
	      while ((inputLine = br.readLine()) != null) {
	          response.append(inputLine);
	      }
	      br.close();
	      System.out.println("1 - " + response.toString());
		      
		return response;
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
