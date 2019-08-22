package vo;

public class ItemStatus{
	
	private String itemId;
	private String name;
	private String value;
	
	public ItemStatus() {
	}

	public ItemStatus(String itemId, String name, String value) {
		super();
		this.itemId = itemId;
		this.name = name;
		this.value = value;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
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
		return "ItemStatus [itemId=" + itemId + ", name=" + name + ", value=" + value + "]";
	}
	
	
}
