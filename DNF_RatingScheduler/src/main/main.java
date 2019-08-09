package main;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class main {

	public static void main(String[] args) throws IOException {
		
		httpConnection conn = new httpConnection(); 
		conn.initProperty("resources/APIKEY.properties");
		conn.RequestAccessToken();
		
		String apiURL = "https://openapi.naver.com/v1/cafe/29837103/menu/1/articles";
		Map<String, String> map = new HashMap<String, String>();
		map.put("subject", new Date().toString());
		map.put("content", "본문테스트2");
		
		conn.HttpPostConnection(apiURL, map);
		
	}

}
