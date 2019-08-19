package vo;

import java.util.List;

public class ItemReinforceSkill{
	private String jobName;
	private String jobId;
	private List<LevelRange> levelRange;
	private List<Skills> skills;
	
	public ItemReinforceSkill() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ItemReinforceSkill(String jobName, String jobId,
			List<LevelRange> levelRange,
			List<Skills> skills) {
		super();
		this.jobName = jobName;
		this.jobId = jobId;
		this.levelRange = levelRange;
		this.skills = skills;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public List<LevelRange> getLevelRange() {
		return levelRange;
	}

	public void setLevelRange(List<LevelRange> levelRange) {
		this.levelRange = levelRange;
	}

	public List<Skills> getSkills() {
		return skills;
	}

	public void setSkills(List<Skills> skills) {
		this.skills = skills;
	}

	@Override
	public String toString() {
		return "ItemReinforceSkill [jobName=" + jobName + ", jobId=" + jobId + ", LevelRange=" + levelRange
				+ ", Skills=" + skills + "]";
	}

}