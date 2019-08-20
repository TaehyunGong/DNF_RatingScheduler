package dao;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vo.Equipment;
import vo.ItemStatus;

public class DnfItemRatingDao {

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
	 * @description 장비 옵션 DB INSERT
	 */
	public int insertStatus(Connection conn, List<Equipment> list) throws SQLException {
		
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
		StringBuffer sql = new StringBuffer("INSERT INTO MaxItemStatus (itemId, name, value, updateDate) VALUES(?, ?, ?, DATE_FORMAT(NOW(),'%Y%m%d') )");
		
		for(int n=0; n<itemId.size()-1; n++)
			sql.append(", (?, ?, ?, DATE_FORMAT(NOW(),'%Y%m%d') )");
		
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
		StringBuffer sql = new StringBuffer("INSERT INTO Equipment VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, DATE_FORMAT(NOW(),'%Y%m%d') )");
		
		for(int n=0; n<list.size()-1; n++)
			sql.append(", (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, DATE_FORMAT(NOW(),'%Y%m%d') )");
		
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
}
