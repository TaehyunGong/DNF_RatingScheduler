package common;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class httpConnection {

	public static final String ENCODING = "UTF-8";

	
	private httpConnection(){}
	
	private static class httpConnection_Singieton{
		private static final httpConnection instance = new httpConnection();
	}
	
	public static httpConnection getInstance(){
		return httpConnection_Singieton.instance;
	}
	
	//get방식 rest 호출시 사용
	public StringBuffer HttpGetConnection(String apiURL) throws IOException {
		StringBuffer response = new StringBuffer();

        URL url = new URL(apiURL);
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("GET");
        
        return responseHttp(con);
	}
	
	//post방식 rest 호출시 사용
	public StringBuffer HttpPostConnection(String apiURL, String accessToken, Map<String, String> map) throws IOException {
	      URL url = new URL(apiURL);
	      HttpURLConnection con = (HttpURLConnection)url.openConnection();
	      con.setRequestMethod("POST");
	      con.setRequestProperty("Authorization", accessToken);
	      
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
	      
	      return responseHttp(con);
	}
	
	//서버에 요청하는 메소드
	public StringBuffer responseHttp(HttpURLConnection con) throws IOException {
		StringBuffer response = new StringBuffer();
		
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
	
	//파라메터 URL 인코딩
	public String URLencoder(String contents) throws UnsupportedEncodingException {
		return URLEncoder.encode(URLEncoder.encode(contents, ENCODING), "MS949");
	}
	
	
}
