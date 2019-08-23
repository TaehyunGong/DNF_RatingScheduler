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
import vo.ItemStatus;

public class main {

	public void test() throws SQLException {
		DBConnection dbConn = DBConnection.getInstance();
		DnfItemRatingDao dao = DnfItemRatingDao.getInstance();
		
		Connection conn = dbConn.getConnection();
		
		List<Equipment> list = dao.selectAllEquipmentList(conn);
		List<String> containList = dao.selectOptionList(dbConn.getConnection());
		
		for(Equipment equip : list) {
			equip.setItemStatus(equip.getMaxItemStatus());
			
			htmlTagInsertList(containList, equip.getMaxItemStatus(), equip.getItemStatus());
		}
		
	}
	
	//수치값과 차이점
	public String htmlTagInsertList(List<String> containList, List<ItemStatus> maxList, List<ItemStatus> list) throws SQLException {
		htmlBuilder optionContain = new htmlBuilder();
		
		//필요한 옵션만 가져오도록
		for(int i=0; i<list.size(); i++) {
			ItemStatus status = list.get(i);
			if(containList.contains(status.getName())) {
				System.out.println(status.getName() + " - " + status.getValue());
				optionContain.setText(status.getName() + " : " + status.getValue())
				.tag("font","style='color:#ff0000; font-weight: bold;' ")
				.setText("(+" + (Integer.parseInt(maxList.get(i).getValue()) - Integer.parseInt(status.getValue())) + ")")
				.tagEnd()
				.br();
			}
		}
		
		return optionContain.build();
	}
	
	public static void main(String[] args) throws JsonGenerationException, JsonMappingException, IOException, SQLException {

		//그냥 process클래스에서 모든 모듈을 가져와 실행하도록 수정
		Process proc = new Process("resources/");
		proc.process();
		
//		main test = new main();
//		test.test();
		
	}

}
 