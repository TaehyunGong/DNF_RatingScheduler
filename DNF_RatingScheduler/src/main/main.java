package main;

import java.io.IOException;

public class main {

	public static void main(String[] args) throws IOException {
		
		httpConnection conn = new httpConnection(); 
		conn.initProperty("resources/APIKEY.properties");
		conn.RequestAccessToken();
		
		System.out.println(conn.getAccessToken());
	}

}
