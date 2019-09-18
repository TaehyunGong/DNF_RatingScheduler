package vo;

public class RatingCalendar {

	private String yyyymmdd;
	private String itemGradeName;
	private String max;
	
	public RatingCalendar() {}

	public String getYyyymmdd() {
		return yyyymmdd;
	}

	public void setYyyymmdd(String yyyymmdd) {
		this.yyyymmdd = yyyymmdd;
	}

	public String getItemGradeName() {
		return itemGradeName;
	}

	public void setItemGradeName(String itemGradeName) {
		this.itemGradeName = itemGradeName;
	}

	public String getMax() {
		return max;
	}

	public void setMax(String max) {
		this.max = max;
	}

	@Override
	public String toString() {
		return "RatingCalendar [yyyymmdd=" + yyyymmdd + ", itemGradeName=" + itemGradeName + ", max=" + max + "]";
	}
	
}
