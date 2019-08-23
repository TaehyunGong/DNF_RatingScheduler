package vo;

import java.util.List;

public class ItemBuff{
	private String explain;
	private List<ReinforceSkill> reinforceSkill;
	
	public ItemBuff() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ItemBuff(String explain, List<ReinforceSkill> reinforceSkill) {
		super();
		this.explain = explain;
		this.reinforceSkill = reinforceSkill;
	}

	public String getExplain() {
		return explain;
	}

	public void setExplain(String explain) {
		this.explain = explain;
	}

	public List<ReinforceSkill> getReinforceSkill() {
		return reinforceSkill;
	}

	public void setReinforceSkill(List<ReinforceSkill> reinforceSkill) {
		this.reinforceSkill = reinforceSkill;
	}

	@Override
	public String toString() {
		return "itemBuff [explain=" + explain + ", ReinforceSkill=" + reinforceSkill + "]";
	}

	
}
