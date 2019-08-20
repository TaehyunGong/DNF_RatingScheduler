package main;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import dao.DBConnection;
import dao.DnfItemRatingDao;
import vo.Equipment;

public class main {

	public static final String path = "resources/";
	
	public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
//		naverCafeWriter ncw = new naverCafeWriter();
//		ncw.cafeWrtier();
		
		propertyGetAPIKEY getkey = propertyGetAPIKEY.getInstance();
		
		getkey.initProperty(main.path + "APIKEY.properties");
		DnfItemRating dnf = new DnfItemRating();
//		List<Equipment> list = dnf.ratingItem(dnf.getEquipment(), getkey.getKeyBox().get("DNFApiKey"));
		List<Equipment> list = dnf.getEquipment();
		
		DBConnection conn = DBConnection.getInstance();
		DnfItemRatingDao dao = DnfItemRatingDao.getInstance();
		System.out.println(dao.insertStatus(conn.getConnection(), list));
		
		
//		for(Equipment equip : list) {
//			System.out.println(equip.getItemName());
//			for(ItemStatus status : equip.getMaxItemStatus()) {
//				System.out.println(status.getName() + " : " + status.getValue());
//			}
//		}
		
	}

}
