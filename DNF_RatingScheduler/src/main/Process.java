package main;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import dao.DBConnection;
import dao.DnfItemRatingDao;
import vo.Equipment;

public class Process {

	DBConnection dbConn = null;
	DnfItemRatingDao dao = null;
	propertyGetAPIKEY getkey = null;
	
	public Process(String path) {
		this.dbConn = DBConnection.getInstance();
		this.dao = DnfItemRatingDao.getInstance();
		
		this.getkey = propertyGetAPIKEY.getInstance();
		getkey.initProperty(path + "APIKEY.properties");
	}

	//비즈니스 로직단
	public void process() throws JsonGenerationException, JsonMappingException, IOException, SQLException {
//		naverCafeWriter ncw = new naverCafeWriter();
//		ncw.cafeWrtier();
		
		DnfItemRating dnf = new DnfItemRating();
		List<Equipment> equipList = dao.selectAllEquipmentList(dbConn.getConnection());
		
		// restAPI의 호출로 오늘날짜 데이터를 equipList (itemStatus, ItemGrade)에 삽입함
		equipList = dnf.ratingItem(equipList, getkey.getKeyBox().get("DNFApiKey"));
		
		//DB에 ItemGrade 및 ItemStatus에 오늘날 값을 insert한다.
		boolean is = insertToday(dbConn.getConnection(), equipList);
		
		System.out.println("체크여부 : " + is);
	}
	
	/**
	 * @param conn
	 * @param dao
	 * @param equipList
	 * @return
	 * @throws SQLException
	 * @description 오늘 업데이트된 장비정보를 DB에 insert
	 */
	public boolean insertToday(Connection conn, List<Equipment> equipList) throws SQLException{
		boolean check = false;
		
		int resultStatus = dao.insertTodayStatus(conn, equipList);
		int resultGrade = dao.insertTodayGrade(conn, equipList); 
		
		//하나라도 insert못한다면 전체 rollback
		if(resultStatus == 0 || resultGrade == 0) {
			conn.rollback();
		}else {
			conn.commit();
			check = true;
		}
			
		return check;
	}
	
}
