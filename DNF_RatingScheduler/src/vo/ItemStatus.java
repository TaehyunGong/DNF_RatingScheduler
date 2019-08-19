package vo;

public class ItemStatus{
	private String name;
	private String value;
	public ItemStatus() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ItemStatus(String name, String value) {
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
		return "ItemStatus [name=" + name + ", value=" + value + "]";
	}
	
}
