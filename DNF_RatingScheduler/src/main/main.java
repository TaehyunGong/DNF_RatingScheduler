package main;

import java.io.IOException;

public class main {

	public static void main(String[] args) throws IOException {
		naverCafeWriter ncw = new naverCafeWriter();
		
		long startTime = System.currentTimeMillis();
		
		ncw.cafeWrtier();
		
		long endTime = System.currentTimeMillis();

		System.out.println("process time : " + (endTime - startTime));
	}

}
