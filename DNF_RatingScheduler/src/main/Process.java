package main;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import dao.DBConnection;
import dao.DnfItemRatingDao;
import vo.Equipment;

public class Process {

	DBConnection dbConn = null;
	DnfItemRatingDao dao = null;
	propertyGetAPIKEY getkey = null;
	
	public Process(String path) throws SQLException, ClassNotFoundException {
		this.getkey = propertyGetAPIKEY.getInstance();
		getkey.addAllProperty(path);
		
		this.dbConn = DBConnection.getInstance().setUrl(getkey.getKeyBox().get("jdbcURL"))
												.setUser(getkey.getKeyBox().get("user"))
												.setPassword(getkey.getKeyBox().get("password"))
												.initConnection();
		
		this.dao = DnfItemRatingDao.getInstance();
	}

	/**
	 * @throws Exception
	 * @description 모든 모듈을 가지고 프로세스를 진행하는 메소드
	 */
	public void process() throws Exception {
		//DB에 게시글에 뿌려줄 옵션리스트를 가져온다.
		List<String> containList = dao.selectOptionList(dbConn.getConnection());
		
		DnfItemRating dnf = new DnfItemRating(containList);
		List<Equipment> equipList = dao.selectAllEquipmentList(dbConn.getConnection());
		List<Equipment> yetEquipList = dao.selectYesterStatus(dbConn.getConnection());
		
		String dnfApiKey = getkey.getKeyBox().get("DNFApiKey");
		
		//진짜 만약 혹시나 모든 값이 같을수 있기때문에 최종체크를 한번 더해준다.
		boolean resultCheck = false;
		
		// restAPI의 호출로 오늘날짜 데이터를 equipList (itemStatus, ItemGrade)에 삽입함
		// 그러나 rest쪽의 업데이트가 늦어지므로 업데이트가 됨을 확인할때까지 2*90 최대 90초를  2초주기로 반복함
		for(int cycle=0; cycle<90; cycle++){
			if(dnf.ratingItem(equipList, yetEquipList, dnfApiKey)){
				resultCheck = true;
				System.out.println("업데이트 완료 - 총 테이 갯수 : " + equipList.size());
				break;
			}
			System.out.println("2초 대기..."); 
			Thread.sleep(2000);
		}
		
		//만약 끝까지 모든 값들이 업데이트가 안됬거나 또는 업데이트가 됬음에도 불과하고 어제랑 status가 같다면 그냥 모든 값들을 현재 status로 덮어준다.
		if(!resultCheck) {
			System.out.println("FINAL UPDATE STATUS DATA");
			dnf.finalRatingItem(equipList, dnfApiKey);
		}
		
		//DB에 ItemGrade 및 ItemStatus에 오늘날 값을 insert한다.
		//어차피 insert error 나면 자동 rollback 된다.
		insertToday(dbConn.getConnection(), equipList);
		
		naverCafeWriter ncw = new naverCafeWriter(equipList, containList);
		ncw.cafeWrtier();
	}
	
	/**
	 * @param conn
	 * @param dao
	 * @param equipList
	 * @return
	 * @throws SQLException
	 * @description 오늘 업데이트된 장비정보를 DB에 insert
	 */
	public void insertToday(Connection conn, List<Equipment> equipList) throws SQLException{
		
		try {
			int resultStatus = dao.insertTodayStatus(conn, equipList);
			int resultGrade = dao.insertTodayGrade(conn, equipList);
			
			//하나라도 insert못한다면 전체 rollback
			if(resultStatus == 0 || resultGrade == 0) {
				conn.rollback();
			}else {
				conn.commit();
			}
		}catch(Exception e) {
			System.out.println("SQL INSERT 에러 rollback");
			conn.rollback();
		}
	}
	
}
