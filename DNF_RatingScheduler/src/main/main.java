package main;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import dao.DBConnection;
import dao.DnfItemRatingDao;
import vo.Equipment;

public class main {

	public static final String path = "resources/";
	
	public static void main(String[] args) throws JsonGenerationException, JsonMappingException, IOException, SQLException{
//		naverCafeWriter ncw = new naverCafeWriter();
//		ncw.cafeWrtier();
		
		propertyGetAPIKEY getkey = propertyGetAPIKEY.getInstance();
		
		getkey.initProperty(main.path + "APIKEY.properties");
		DnfItemRating dnf = new DnfItemRating();
//		List<Equipment> list = dnf.ratingItem(dnf.getEquipment(), getkey.getKeyBox().get("DNFApiKey"));
		List<Equipment> list = dnf.getEquipment();
		
		DBConnection conn = DBConnection.getInstance();
		DnfItemRatingDao dao = DnfItemRatingDao.getInstance();
		
		List<Equipment> equipList = dao.selectAllEquipmentList(conn.getConnection());
		System.out.println("select 완료");
		
		// restAPI의 호출로 오늘날짜 데이터를 equipList (itemStatus, ItemGrade)에 삽입함
		dnf.ratingItem(equipList, getkey.getKeyBox().get("DNFApiKey"));
		System.out.println("http response 완료");
		
		int result = dao.insertTodayStatus(conn.getConnection(), equipList);
		System.out.println("스텟 insert 완료");
		
		result = dao.insertTodayGrade(conn.getConnection(), equipList);
		
		System.out.println(result);
	}

}
