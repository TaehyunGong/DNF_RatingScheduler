package main;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import dao.DBConnection;
import dao.DnfItemRatingDao;
import vo.Equipment;

public class main {

	public void test() throws SQLException {
		DBConnection dbConn = DBConnection.getInstance();
		DnfItemRatingDao dao = DnfItemRatingDao.getInstance();
		
		Connection conn = dbConn.getConnection();
		
		List<Equipment> list = dao.selectAllEquipmentList(conn);
		
		for(Equipment equip : list) {
			System.out.println(equip.getItemName() +" : " + equip.getMaxItemStatus().size());
		}
		
	}
	
	public static void main(String[] args) throws JsonGenerationException, JsonMappingException, IOException, SQLException {

		//그냥 process클래스에서 모든 모듈을 가져와 실행하도록 수정
//		Process proc = new Process("resources/");
//		proc.process();
		
		main test = new main();
		test.test();
		
	}

}
 