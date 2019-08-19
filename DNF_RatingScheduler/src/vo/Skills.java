package vo;

import java.util.List;

public class Skills{
	private String skillId;
	private String name;
	private String value;
	private List<Skills> skills;
	
	public Skills() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Skills(String skillId, String name, String value, List<Skills> skills) {
		super();
		this.skillId = skillId;
		this.name = name;
		this.value = value;
		this.skills = skills;
	}

	public String getSkillId() {
		return skillId;
	}

	public void setSkillId(String skillId) {
		this.skillId = skillId;
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

	public List<Skills> getSkills() {
		return skills;
	}

	public void setSkills(List<Skills> skills) {
		this.skills = skills;
	}

	@Override
	public String toString() {
		return "Skills [skillId=" + skillId + ", name=" + name + ", value=" + value + ", skills=" + skills
				+ "]";
	}
	
}