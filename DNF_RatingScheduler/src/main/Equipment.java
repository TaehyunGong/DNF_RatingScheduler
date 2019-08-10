package main;

public class Equipment {
	
	private String itemId;
	private String itemName;
	private String itemRarity;
	private String itemType;
	private String itemTypeDetail;
	private int itemAvailableLevel;
	
	public Equipment() {}

	public Equipment(String itemId, String itemName, String itemRarity, String itemType, String itemTypeDetail,
			int itemAvailableLevel) {
		super();
		this.itemId = itemId;
		this.itemName = itemName;
		this.itemRarity = itemRarity;
		this.itemType = itemType;
		this.itemTypeDetail = itemTypeDetail;
		this.itemAvailableLevel = itemAvailableLevel;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemRarity() {
		return itemRarity;
	}

	public void setItemRarity(String itemRarity) {
		this.itemRarity = itemRarity;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public String getItemTypeDetail() {
		return itemTypeDetail;
	}

	public void setItemTypeDetail(String itemTypeDetail) {
		this.itemTypeDetail = itemTypeDetail;
	}

	public int getItemAvailableLevel() {
		return itemAvailableLevel;
	}

	public void setItemAvailableLevel(int itemAvailableLevel) {
		this.itemAvailableLevel = itemAvailableLevel;
	}

	@Override
	public String toString() {
		return "Equipment [itemId=" + itemId + ", itemName=" + itemName + ", itemRarity=" + itemRarity + ", itemType="
				+ itemType + ", itemTypeDetail=" + itemTypeDetail + ", itemAvailableLevel=" + itemAvailableLevel + "]";
	}
	
	
	
}
