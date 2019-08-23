package main;

public class main {
	
	public static void main(String[] args) throws Exception {
		//그냥 process클래스에서 모든 모듈을 가져와 실행하도록 수정
		Process proc = new Process("/resources/");
		proc.process();
		
	}

}
 