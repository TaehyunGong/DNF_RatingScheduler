package main;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
	private List<String> containList = null;
	
	public Process(String path) throws SQLException {
		this.dbConn = DBConnection.getInstance();
		this.dao = DnfItemRatingDao.getInstance();
		this.containList = dao.selectOptionList(dbConn.getConnection());
		
		this.getkey = propertyGetAPIKEY.getInstance();
		getkey.initProperty(path + "APIKEY.properties");
	}

	//비즈니스 로직단
	public void process() throws JsonGenerationException, JsonMappingException, IOException, SQLException {
		
		DnfItemRating dnf = new DnfItemRating();
		List<Equipment> equipList = dao.selectAllEquipmentList(dbConn.getConnection());
		String dnfApiKey = getkey.getKeyBox().get("DNFApiKey");
		
		// restAPI의 호출로 오늘날짜 데이터를 equipList (itemStatus, ItemGrade)에 삽입함
		equipList = dnf.ratingItem(equipList, dnfApiKey);
		
		//DB에 ItemGrade 및 ItemStatus에 오늘날 값을 insert한다.
		boolean is = insertToday(dbConn.getConnection(), equipList);
		
		//DB insert실패시 시스템 강제종료
		if(!is) return;
		
		String apiURL = "https://openapi.naver.com/v1/cafe/29837103/menu/1/articles";	//테스트용  테스트950 카페
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일 테이 등급 ");
		String subject = sdf.format(new Date()) + equipList.get(0).getItemGradeName() + "(" + getItemMaxRating(equipList) + "%)";	//글 제목
		String content = contentHtmlMake(equipList);	//글 본문
		
		//테스트 테이블 추가
//		content += listCalendar(null);
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("subject", subject);
		map.put("content", content);
		
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
	
	//아이템 가장 높은 등급
	public int getItemMaxRating(List<Equipment> list){
		int max = 0;
		
		for(Equipment equip : list)
			if(Integer.parseInt(equip.getItemGradeValue()) > max)
				max = Integer.parseInt(equip.getItemGradeValue());
		
		return max;
	}
	
	// 카페 본문 내용
	public String contentHtmlMake(List<Equipment> list) throws IOException, SQLException {
		htmlBuilder html = new htmlBuilder();
		Map<String, String> attr = new HashMap<String, String>();
		
		//sb.append("<h1>CTRL+F 로 원하는 장비 등급을 확인하세요.</h1>");
		attr.put("style", "width:100%");
		html.tag("table", attr);
		
		String checkSetName = "";	// 첫번째 세트명
		
		for(Equipment equip : list){
			if(!checkSetName.equals(nvlString(equip.getSetItemName(), "천공의 유산"))) {
				attr.clear();
				attr.put("colspan", "4");
				attr.put("font-size", "20px");
				attr.put("font-weight", "bold");
				
				html.tag("tr")
				.tag("td", attr)
				.setText(nvlString(equip.getSetItemName(), "천공의 유산"))
				.tagEnd()
				.tagEnd();
			}
			checkSetName = nvlString(equip.getSetItemName(), "천공의 유산");	// 세트명 
			
			html.tag("tr")
			.tag("td","width='8%'")
				.tag("img","src='https://img-api.neople.co.kr/df/items/" + equip.getItemId() + "'")
				.tagEnd()
			.tagEnd()
			
			.tag("td","width='25%'")
				.setText(equip.getItemName())
			.tagEnd()

			.tag("td","width='25%'")
				.setText(equip.getItemGradeName() + " (" + equip.getItemGradeValue() + "%)")
			.tagEnd()
			
			.tag("td","width='43%'")
				.setText(htmlTagInsertList(equip.getMaxItemStatus(), equip.getItemStatus()))
			.tagEnd()
			
			.tagEnd();
		}
		html.tagEnd();
		
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
				.tagEnd()
				.br();
			}
		}
		
		return optionContain.build();
	}
	
}
