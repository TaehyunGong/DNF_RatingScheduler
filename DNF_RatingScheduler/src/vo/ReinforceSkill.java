package vo;

import java.util.List;

public class ReinforceSkill{
	private String jobName;
	private String jobId;
	private List<Skills> skills;
	
	public ReinforceSkill() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ReinforceSkill(String jobName, String jobId,
			List<Skills> skills) {
		super();
		this.jobName = jobName;
		this.jobId = jobId;
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

	public List<Skills> getSkills() {
		return skills;
	}

	public void setSkills(List<Skills> skills) {
		this.skills = skills;
	}

	@Override
	public String toString() {
		return "ReinforceSkill [jobName=" + jobName + ", jobId=" + jobId + ", Skills=" + skills + "]";
	}
}
