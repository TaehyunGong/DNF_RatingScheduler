package main;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import dao.DBConnection;
import dao.DnfItemRatingDao;
import vo.Equipment;
import vo.ItemStatus;

public class Process {

	DBConnection dbConn = null;
	DnfItemRatingDao dao = null;
	propertyGetAPIKEY getkey = null;
	public static List<String> containList = null;
	
	public Process(String path) throws SQLException {
		this.dbConn = DBConnection.getInstance();
		this.dao = DnfItemRatingDao.getInstance();
		this.containList = dao.selectOptionList(dbConn.getConnection());
		
		this.getkey = propertyGetAPIKEY.getInstance();
		getkey.initProperty(path + "APIKEY.properties");
	}

	/**
	 * @throws Exception
	 * @description 모든 모듈을 가지고 프로세스를 진행하는 메소드
	 */
	public void process() throws Exception {
		
		DnfItemRating dnf = new DnfItemRating();
		List<Equipment> equipList = dao.selectAllEquipmentList(dbConn.getConnection());
		List<Equipment> yetEquipList = dao.selectYesterStatus(dbConn.getConnection());
		
		String dnfApiKey = getkey.getKeyBox().get("DNFApiKey");
		
		// restAPI의 호출로 오늘날짜 데이터를 equipList (itemStatus, ItemGrade)에 삽입함
		// 그러나 rest쪽의 업데이트가 늦어지므로 업데이트가 됨을 확인할때까지 2*90 최대 90초를  2초주기로 반복함
		for(int cycle=0; cycle<90; cycle++){
			if(dnf.ratingItem(equipList, yetEquipList, dnfApiKey)){
				System.out.println("업데이트 완료 - 총 테이 갯수 : " + equipList.size());
				break;
			}
			System.out.println("2초 대기...");
			Thread.sleep(2000);
		}
	
		//DB에 ItemGrade 및 ItemStatus에 오늘날 값을 insert한다.
		//어차피 insert error 나면 자동 rollback 된다.
		insertToday(dbConn.getConnection(), equipList);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일 테이 등급 ");
		String subject = sdf.format(new Date()) + equipList.get(0).getItemGradeName() + "(" + getItemMaxRating(equipList) + "%)";	//글 제목
		String content = contentHtmlMake(equipList);	//글 본문
		
		naverCafeWriter ncw = new naverCafeWriter();
		ncw.cafeWrtier(subject, content);
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
	
	/**
	 * @param list
	 * @return int
	 * @description 모든 아이템 등급중에서 가장 max값을 반환 
	 */
	public int getItemMaxRating(List<Equipment> list){
		int max = 0;
		
		for(Equipment equip : list)
			if(Integer.parseInt(equip.getItemGradeValue()) > max)
				max = Integer.parseInt(equip.getItemGradeValue());
		
		return max;
	}
	
	/**
	 * @param list
	 * @return String
	 * @throws IOException
	 * @throws SQLException
	 * @description 카페에 뿌려줄 html를 작성
	 */
	public String contentHtmlMake(List<Equipment> list) throws IOException, SQLException {
		htmlBuilder html = new htmlBuilder();
		
		//글 시작전 예시를 간단하게 텍스트로 뿌려줌
		html.tag("span","style='font-size: 12pt;'")
			.setText("예시) ")
			.tag("b", "style='color:#ff0000; font-weight: bold;'")
			.setText("(+n)")
			.endTag()
			.setText(" : 극옵 대비 차이값,  ")
			.tag("b", "style='color:#009e25; font-weight: bold;'")
			.setText("(+0)")
			.endTag()
			.setText(" : 극옵 대비 차이값,  ")
		.endTag()
		.hr();
		
		html.tag("table", "width:100%");
		String checkSetName = "";	// 첫번째 세트명
		
		for(Equipment equip : list){
			if(!checkSetName.equals(nvlString(equip.getSetItemName(), "천공의 유산"))) {
				
				html.tag("tr")
				.tag("td", "colspan='4' style='font-size: 20px; font-weight: bold;'")
				.setText(nvlString(equip.getSetItemName(), "천공의 유산"))
				.endTag()
				.endTag();
			}
			checkSetName = nvlString(equip.getSetItemName(), "천공의 유산");	// 세트명 
			
			html.tag("tr")
			.tag("td","width='8%'")
				.tag("img","src='https://img-api.neople.co.kr/df/items/" + equip.getItemId() + "'")
				.endTag()
			.endTag()
			
			.tag("td","width='25%'")
				.setText(equip.getItemName())
			.endTag()

			.tag("td","width='25%'")
				.setText(equip.getItemGradeName() + " (" + equip.getItemGradeValue() + "%)")
			.endTag()
			
			.tag("td","width='43%'")
				.setText(htmlTagInsertList(equip.getMaxItemStatus(), equip.getItemStatus()))
			.endTag()
			
			.endTag();
		}
		html.endTag();
		
		//네오플 BI 이미지 삽입
		html.br(2)
		.tag("a","href='http://developers.neople.co.kr' target='_blank'")
		.tag("img","src='https://developers.neople.co.kr/img/logo_t1.png' alt='Neople 오픈 API' width='50%'")
		.endTag()
		.endTag();
		
		return html.build();
	}
	
	//nvl('a')
	public String nvlString(String str) {
		if(str == null)
			str = "";
		return str;
	}
	
	//nvl('a', 'b')
	public String nvlString(String str, String replace) {
		if(str == null)
			str = replace;
		return str;
	}
	
	//수치값과 차이점
	public String htmlTagInsertList(List<ItemStatus> maxList, List<ItemStatus> list) throws SQLException {
		htmlBuilder optionContain = new htmlBuilder();
		int maxStat = 0;
		int toDayStat = 0;
		int result = 0;
		
		//필요한 옵션만 가져오도록
		for(int i=0; i<list.size(); i++) {
			ItemStatus status = list.get(i);
			if(containList.contains(status.getName())) {
				maxStat = 0;
				toDayStat = Integer.parseInt(status.getValue());
				
				for(ItemStatus stat : maxList) {
					if(stat.getName().equals(status.getName())){
						maxStat = Integer.parseInt(stat.getValue());
					}
				}
				result = maxStat - toDayStat;
				
				optionContain.setText(status.getName() + " : " + status.getValue());
				
				if(result == 0)
					optionContain.tag("font","style='color:#009e25; font-weight: bold;' ");
				else
					optionContain.tag("font","style='color:#ff0000; font-weight: bold;' ");
				
				optionContain.setText("(+" + (maxStat - toDayStat) + ")")
				.endTag()
				.br();
			}
		}
		
		return optionContain.build();
	}
	
}
