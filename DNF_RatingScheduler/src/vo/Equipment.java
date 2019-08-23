package vo;

import java.util.List;

public class Equipment {
	
	private String itemId;
	private String itemName;
	private String itemRarity;
	private String itemType;
	private String itemTypeDetail;
	private int itemAvailableLevel;
	
	//이날 아이템등급
	private String itemGradeName;
	private String itemGradeValue;
	private List<ItemStatus> itemStatus;
	
	//아이템 max 등급
	private String itemObtainInfo;
	private String itemExplain;
	private String itemExplainDetail;
	private String itemFlavorText;
	private String setItemId;
	private String setItemName;
	private List<ItemStatus> maxItemStatus;
	private List<ItemReinforceSkill> itemReinforceSkill;
	private ItemBuff itemBuff;
	
	public static class maxItemStatus{
		private String name;
		private String value;
		public maxItemStatus() {
			super();
			// TODO Auto-generated constructor stub
		}
		public maxItemStatus(String name, String value) {
			super();
			this.name = name;
			this.value = value;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		@Override
		public String toString() {
			return "maxItemStatus [name=" + name + ", value=" + value + "]";
		}
	}
	
	
	public Equipment() {}

	public Equipment(String itemId, String itemName, String itemRarity, String itemType, String itemTypeDetail,
			int itemAvailableLevel, String itemGradeName, String itemGradeValue,
			List<ItemStatus> itemStatus, String itemObtainInfo, String itemExplain,
			String itemExplainDetail, String itemFlavorText, String setItemId, String setItemName,
			List<ItemStatus> maxItemStatus, List<ItemReinforceSkill> itemReinforceSkill,
			ItemBuff itemBuff) {
		super();
		this.itemId = itemId;
		this.itemName = itemName;
		this.itemRarity = itemRarity;
		this.itemType = itemType;
		this.itemTypeDetail = itemTypeDetail;
		this.itemAvailableLevel = itemAvailableLevel;
		this.itemGradeName = itemGradeName;
		this.itemGradeValue = itemGradeValue;
		this.itemStatus = itemStatus;
		this.itemObtainInfo = itemObtainInfo;
		this.itemExplain = itemExplain;
		this.itemExplainDetail = itemExplainDetail;
		this.itemFlavorText = itemFlavorText;
		this.setItemId = setItemId;
		this.setItemName = setItemName;
		this.maxItemStatus = maxItemStatus;
		this.itemReinforceSkill = itemReinforceSkill;
		this.itemBuff = itemBuff;
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

	public String getItemGradeName() {
		return itemGradeName;
	}

	public void setItemGradeName(String itemGradeName) {
		this.itemGradeName = itemGradeName;
	}

	public String getItemGradeValue() {
		return itemGradeValue;
	}

	public void setItemGradeValue(String itemGradeValue) {
		this.itemGradeValue = itemGradeValue;
	}

	public List<ItemStatus> getItemStatus() {
		return itemStatus;
	}

	public void setItemStatus(List<ItemStatus> itemStatus) {
		this.itemStatus = itemStatus;
	}

	public String getItemObtainInfo() {
		return itemObtainInfo;
	}

	public void setItemObtainInfo(String itemObtainInfo) {
		this.itemObtainInfo = itemObtainInfo;
	}

	public String getItemExplain() {
		return itemExplain;
	}

	public void setItemExplain(String itemExplain) {
		this.itemExplain = itemExplain;
	}

	public String getItemExplainDetail() {
		return itemExplainDetail;
	}

	public void setItemExplainDetail(String itemExplainDetail) {
		this.itemExplainDetail = itemExplainDetail;
	}

	public String getItemFlavorText() {
		return itemFlavorText;
	}

	public void setItemFlavorText(String itemFlavorText) {
		this.itemFlavorText = itemFlavorText;
	}

	public String getSetItemId() {
		return setItemId;
	}

	public void setSetItemId(String setItemId) {
		this.setItemId = setItemId;
	}

	public String getSetItemName() {
		return setItemName;
	}

	public void setSetItemName(String setItemName) {
		this.setItemName = setItemName;
	}

	public List<ItemStatus> getMaxItemStatus() {
		return maxItemStatus;
	}

	public void setMaxItemStatus(List<ItemStatus> maxItemStatus) {
		this.maxItemStatus = maxItemStatus;
	}

	public List<ItemReinforceSkill> getItemReinforceSkill() {
		return itemReinforceSkill;
	}

	public void setItemReinforceSkill(List<ItemReinforceSkill> itemReinforceSkill) {
		this.itemReinforceSkill = itemReinforceSkill;
	}

	public ItemBuff getItemBuff() {
		return itemBuff;
	}

	public void setItemBuff(ItemBuff itemBuff) {
		this.itemBuff = itemBuff;
	}

	@Override
	public String toString() {
		return "Equipment [itemId=" + itemId + ", itemName=" + itemName + ", itemRarity=" + itemRarity + ", itemType="
				+ itemType + ", itemTypeDetail=" + itemTypeDetail + ", itemAvailableLevel=" + itemAvailableLevel
				+ ", itemGradeName=" + itemGradeName + ", itemGradeValue=" + itemGradeValue + ", itemStatus="
				+ itemStatus + ", itemObtainInfo=" + itemObtainInfo + ", itemExplain=" + itemExplain
				+ ", itemExplainDetail=" + itemExplainDetail + ", itemFlavorText=" + itemFlavorText + ", setItemId="
				+ setItemId + ", setItemName=" + setItemName + ", maxItemStatus=" + maxItemStatus
				+ ", itemReinforceSkill=" + itemReinforceSkill + ", itemBuff=" + itemBuff + "]";
	}

}
