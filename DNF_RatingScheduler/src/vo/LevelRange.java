package vo;

public class LevelRange{
	private String minLevel;
	private String maxLevel;
	private String value;
	public LevelRange() {
		super();
		// TODO Auto-generated constructor stub
	}
	public LevelRange(String minLevel, String maxLevel, String value) {
		super();
		this.minLevel = minLevel;
		this.maxLevel = maxLevel;
		this.value = value;
	}
	public String getMinLevel() {
		return minLevel;
	}
	public void setMinLevel(String minLevel) {
		this.minLevel = minLevel;
	}
	public String getMaxLevel() {
		return maxLevel;
	}
	public void setMaxLevel(String maxLevel) {
		this.maxLevel = maxLevel;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return "LevelRange [minLevel=" + minLevel + ", maxLevel=" + maxLevel + ", value=" + value + "]";
	}
	
	
}