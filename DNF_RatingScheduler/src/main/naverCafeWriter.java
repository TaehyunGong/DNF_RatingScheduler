package main;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

import vo.Equipment;
import vo.ItemStatus;

public class naverCafeWriter {

	httpConnection conn = httpConnection.getInstance(); 
	propertyGetAPIKEY getkey = propertyGetAPIKEY.getInstance();
	
	List<Equipment> equipList = null;
	List<String> containList = null;
	
	// 어차피 장비등급 리스트를 가져와 본문을 작성해야하니 생성자에 필수값으로 가져오도록 한다.
	public naverCafeWriter(List<Equipment> equipList, List<String> containList) {
		this.equipList = equipList;
		this.containList = containList;
	}

	/**
	 * @param subject
	 * @param content
	 * @throws IOException
	 * @throws SQLException 
	 * @description 제목과 본문을 map으로 저장후 apikey와 같이 post요청으로 글작성
	 */
	public void cafeWrtier() throws IOException, SQLException {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일 테이 등급 ");
		String subject = sdf.format(new Date()) + equipList.get(0).getItemGradeName() + "(" + getItemMaxRating(equipList) + "%)";	//글 제목
		String content = contentHtmlMake(equipList);	//글 본문
		
		//apikey를 가져옴
		String apiURL = getkey.getKeyBox().get("apiURL");
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("subject", subject);
		map.put("content", content);
		
		conn.HttpPostConnection(apiURL, RequestAccessToken(), map);
	}
	
	public String listCalendar(List list) {
		
		Calendar cal = Calendar.getInstance();
		cal.set(cal.DAY_OF_MONTH, 1);
		int maxDay = cal.getActualMaximum(cal.DAY_OF_MONTH);
		int startWeek = cal.get(cal.DAY_OF_WEEK);

		StringBuffer html = new StringBuffer();
		html.append("<table border='0' width='588px' height='40' cellspacing='1' cellpadding='1' bgcolor='#B7BBB5'>");
		html.append("<tbody>");
		html.append("<tr bgcolor='#FFFFFF'>");
		
		for(int i=1; i<maxDay+startWeek; i++) {
			if(startWeek > i){
				html.append("<td width='84px'></td>");
			}else {
				html.append("<td style='font-size:9pt;font-family:2820189_9;' width='84px'>" + (i+1-startWeek) + "</td>");
			}
			
			if(i%7==0) {
				html.append("</tr>");
				html.append("<tr bgcolor='#FFFFFF'>");
			}
		}
		html.append("</tr>");
		
		html.append("</tbody>");
		html.append("</table>");
		
		return html.toString();
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
			if(this.containList.contains(status.getName())) {
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
	
	/**
	 * @return
	 * @throws IOException
	 * @description Naver access 토큰 발급
	 */
	public String RequestAccessToken() throws IOException {
		String AccessToken;
		
        String apiURL = "https://nid.naver.com/oauth2.0/token?";
        apiURL += "grant_type=refresh_token";
        apiURL += "&client_id=" + getkey.getKeyBox().get("ClientId");
        apiURL += "&client_secret=" + getkey.getKeyBox().get("ClientSecretKey");
        apiURL += "&refresh_token=" + getkey.getKeyBox().get("RefeshToken");
        
        StringBuffer resultResponse = conn.HttpGetConnection(apiURL);
        HashMap<String, String> rs = new ObjectMapper().readValue(resultResponse.toString(), HashMap.class);
        
        if(rs.get("access_token") == null){
        	System.out.println("access_token 를 못가져옴");
        	throw new NullPointerException();
        }
        
        //accesstoken 재발급
        AccessToken = "Bearer " + rs.get("access_token");
        
        return AccessToken;
	}	
	
}
