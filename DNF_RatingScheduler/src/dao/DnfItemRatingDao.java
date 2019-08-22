package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vo.Equipment;
import vo.ItemStatus;

public class DnfItemRatingDao {

	//오늘날 SYSDATE
	private final String SYSDATE = "DATE_FORMAT(NOW(),'%Y%m%d')";
	
	private DnfItemRatingDao(){}
	
	private static class DnfItemRatingDao_Singieton{
		private static final DnfItemRatingDao instance = new DnfItemRatingDao();
	}
	
	public static DnfItemRatingDao getInstance(){
		return DnfItemRatingDao_Singieton.instance;
	}
	
	/**
	 * @param conn
	 * @param list
	 * @return 0 or 1
	 * @throws SQLException
	 * @description 장비의 오늘날 옵션 DB INSERT
	 */
	public int insertTodayStatus(Connection conn, List<Equipment> list) throws SQLException {
		
		List<String> itemId = new ArrayList<String>();
		List<String> name = new ArrayList<String>();
		List<String> value = new ArrayList<String>();
		
		for(Equipment equip : list) {
			for(ItemStatus stat : equip.getItemStatus()) {
				itemId.add(equip.getItemId());
				name.add(stat.getName());
				value.add(stat.getValue());
			}
		}
		
		int result = 0;
		PreparedStatement ps = null;
		StringBuffer sql = new StringBuffer("INSERT INTO ItemStatus VALUES(" + SYSDATE + ", ?, ?, ?, " + SYSDATE + " )");
		
		for(int n=0; n<itemId.size()-1; n++)
			sql.append(", (" + SYSDATE + ", ?, ?, ?, " + SYSDATE + " )");
		
		try {
			ps = conn.prepareStatement(sql.toString());
			
			for(int i=0; i<itemId.size(); i++) {
				ps.setString(1+(i*3), itemId.get(i));
				ps.setString(2+(i*3), name.get(i));
				ps.setString(3+(i*3), value.get(i));
			}
			
			result = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}finally {
			ps.close();
		}
		
		return result;
	}
	
	
	/**
	 * @param conn
	 * @param list
	 * @return 0 or 1
	 * @throws SQLException
	 * @description 현재 날짜의 등급(상급 70%)을 insert 
	 */
	public int insertTodayGrade(Connection conn, List<Equipment> list) throws SQLException {
		
		int result = 0;
		PreparedStatement ps = null;
		StringBuffer sql = new StringBuffer("INSERT INTO ItemGrade VALUES(" + SYSDATE + ", ?, ?, ?)");
		Equipment equip =null;
		
		for(int n=0; n<list.size()-1; n++)
			sql.append(", (" + SYSDATE + ", ?, ?, ?)");
		
		try {
			ps = conn.prepareStatement(sql.toString());
			for(int i=0; i<list.size(); i++) {
				equip = list.get(i);
				
				ps.setString(1+(i*3), equip.getItemId());
				ps.setString(2+(i*3), equip.getItemGradeName());
				ps.setString(3+(i*3), equip.getItemGradeValue());
			}
			
			result = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}finally {
			ps.close();
		}
		
		return result;
	}
	
	/**
	 * @param conn
	 * @param list
	 * @return 0 or 1
	 * @throws SQLException
	 * @description 장비의 최대 옵션(도감) DB INSERT
	 */
	public int insertMaxStatus(Connection conn, List<Equipment> list) throws SQLException {
		
		List<String> itemId = new ArrayList<String>();
		List<String> name = new ArrayList<String>();
		List<String> value = new ArrayList<String>();
		
		for(Equipment equip : list) {
			for(ItemStatus stat : equip.getMaxItemStatus()) {
				itemId.add(equip.getItemId());
				name.add(stat.getName());
				value.add(stat.getValue());
			}
		}
		
		int result = 0;
		PreparedStatement ps = null;
		StringBuffer sql = new StringBuffer("INSERT INTO MaxItemStatus (itemId, name, value, updateDate) VALUES(?, ?, ?, " + SYSDATE + " )");
		
		for(int n=0; n<itemId.size()-1; n++)
			sql.append(", (?, ?, ?, " + SYSDATE + " )");
		
		try {
			ps = conn.prepareStatement(sql.toString());
			
			for(int i=0; i<itemId.size(); i++) {
				ps.setString(1+(i*3), itemId.get(i));
				ps.setString(2+(i*3), name.get(i));
				ps.setString(3+(i*3), value.get(i));
			}
			
			result = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			ps.close();
			conn.close();
		}
		
		
		return result;
	}
	
	/**
	 * @param conn
	 * @param list
	 * @return 0 or 1
	 * @throws SQLException 
	 * @description 장비를 DB INSERT
	 */
	public int insertEquipment(Connection conn, List<Equipment> list) throws SQLException {

		int result = 0;
		PreparedStatement ps = null;
		StringBuffer sql = new StringBuffer("INSERT INTO Equipment VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " + SYSDATE + " )");
		
		for(int n=0; n<list.size()-1; n++)
			sql.append(", (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " + SYSDATE + " )");
		
		try {
			ps = conn.prepareStatement(sql.toString());
			
			for(int i=0; i<list.size(); i++) {
				Equipment equip = list.get(i);
				
				ps.setString(1+(i*13), equip.getItemId());
				ps.setString(2+(i*13), equip.getItemName());
				ps.setString(3+(i*13), equip.getItemRarity());
				ps.setString(4+(i*13), equip.getItemType());
				ps.setString(5+(i*13), equip.getItemTypeDetail());
				ps.setInt(6+(i*13), equip.getItemAvailableLevel());
				ps.setString(7+(i*13), equip.getItemObtainInfo());
				ps.setString(8+(i*13), equip.getItemExplain());
				ps.setString(9+(i*13), equip.getItemExplainDetail());
				ps.setString(10+(i*13), equip.getItemFlavorText());
				ps.setString(11+(i*13), equip.getSetItemId());
				ps.setString(12+(i*13), equip.getSetItemName());
				ps.setInt(13+(i*13), i+1);
			}
			result = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			ps.close();
			conn.close();
		}
		
		return result;
	}

	
	/**
	 * @param conn
	 * @param tableName
	 * @return ArrayList<String>
	 * @throws SQLException
	 * @description 파라메터에 정의된 테이블명의 모든 컬럼명을 가져옴
	 */
	public ArrayList<String> selectColumnList(Connection conn, String tableName) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SHOW FULL COLUMNS FROM " + tableName;
		
		ArrayList<String> list = new ArrayList<String>();
		
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				list.add(rs.getString(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			ps.close();
			rs.close();
		}
		
		return list;
	}
	
	
	/**
	 * @param conn
	 * @return
	 * @throws SQLException
	 * @description Equipment의 모든 데이터를 select 하여 가져온다.
	 */
	public List<Equipment> selectAllEquipmentList(Connection conn) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM Equipment ORDER BY printOrder";
		
		ArrayList<Equipment> list = new ArrayList<Equipment>();
		Equipment equip = null;
		
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				equip = new Equipment();
				
				equip.setItemId(rs.getString("itemId"));
				equip.setItemName(rs.getString("itemName"));
				equip.setItemRarity(rs.getString("itemRarity"));
				equip.setItemType(rs.getString("itemType"));
				equip.setItemTypeDetail(rs.getString("itemTypeDetail"));
				equip.setItemAvailableLevel(rs.getInt("itemAvailableLevel"));
				equip.setItemObtainInfo(rs.getString("itemObtainInfo"));
				equip.setItemExplain(rs.getString("itemExplain"));
				equip.setItemExplainDetail(rs.getString("itemExplainDetail"));
				equip.setItemFlavorText(rs.getString("itemFlavorText"));
				equip.setSetItemId(rs.getString("setItemId"));
				equip.setSetItemName(rs.getString("setItemName"));
				
				list.add(equip);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			ps.close();
			rs.close();
		}
		
		return list;
	}
	
}
