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
			
//			ps.setArray(1, conn.createArrayOf("VARCHAR", itemId.toArray()));
			
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
	
}
