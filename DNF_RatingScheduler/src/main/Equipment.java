package main;

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
	private List<itemStatus> itemStatus;
	
	//아이템 max 등급
	private String itemObtainInfo;
	private String itemExplain;
	private String itemExplainDetail;
	private String itemFlavorText;
	private String setItemId;
	private String setItemName;
	private List<itemStatus> maxItemStatus;
	private List<itemReinforceSkill> itemReinforceSkill;
	private itemBuff itemBuff;
	
	public static class itemStatus{
		private String name;
		private String value;
		public itemStatus() {
			super();
			// TODO Auto-generated constructor stub
		}
		public itemStatus(String name, String value) {
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
			return "itemStatus [name=" + name + ", value=" + value + "]";
		}
		
	}
	
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
	
	public static class itemReinforceSkill{
		private String jobName;
		private String jobId;
		private List<levelRange> levelRange;
		private List<skills> skills;
		
		public static class levelRange{
			private String minLevel;
			private String maxLevel;
			private String value;
			public levelRange() {
				super();
				// TODO Auto-generated constructor stub
			}
			public levelRange(String minLevel, String maxLevel, String value) {
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
				return "levelRange [minLevel=" + minLevel + ", maxLevel=" + maxLevel + ", value=" + value + "]";
			}
			
			
		}

		public static class skills{
			private String skillId;
			private String name;
			private String value;
			
			public skills() {
				super();
				// TODO Auto-generated constructor stub
			}

			public skills(String skillId, String name, String value) {
				super();
				this.skillId = skillId;
				this.name = name;
				this.value = value;
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

			@Override
			public String toString() {
				return "skills [skillId=" + skillId + ", name=" + name + ", value=" + value + "]";
			}
		
		}
		
		public itemReinforceSkill() {
			super();
			// TODO Auto-generated constructor stub
		}

		public itemReinforceSkill(String jobName, String jobId,
				List<itemReinforceSkill.levelRange> levelRange,
				List<itemReinforceSkill.skills> skills) {
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

		public List<levelRange> getLevelRange() {
			return levelRange;
		}

		public void setLevelRange(List<levelRange> levelRange) {
			this.levelRange = levelRange;
		}

		public List<skills> getSkills() {
			return skills;
		}

		public void setSkills(List<skills> skills) {
			this.skills = skills;
		}

		@Override
		public String toString() {
			return "itemReinforceSkill [jobName=" + jobName + ", jobId=" + jobId + ", levelRange=" + levelRange
					+ ", skills=" + skills + "]";
		}

	}
	
	public static class itemBuff{
		private String explain;
		private List<reinforceSkill> reinforceSkill;
		
		public static class reinforceSkill{
			private String jobName;
			private String jobId;
			private List<skills> skills;
			
			public static class skills{
				private String skillId;
				private String name;
				private String value;
				private List<skills> skills;
				
				public skills() {
					super();
					// TODO Auto-generated constructor stub
				}

				public skills(String skillId, String name, String value, List<skills> skills) {
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

				public List<skills> getSkills() {
					return skills;
				}

				public void setSkills(List<skills> skills) {
					this.skills = skills;
				}

				@Override
				public String toString() {
					return "skills [skillId=" + skillId + ", name=" + name + ", value=" + value + ", skills=" + skills
							+ "]";
				}
				
			}

			public reinforceSkill() {
				super();
				// TODO Auto-generated constructor stub
			}

			public reinforceSkill(String jobName, String jobId,
					List<itemBuff.reinforceSkill.skills> skills) {
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

			public List<skills> getSkills() {
				return skills;
			}

			public void setSkills(List<skills> skills) {
				this.skills = skills;
			}

			@Override
			public String toString() {
				return "reinforceSkill [jobName=" + jobName + ", jobId=" + jobId + ", skills=" + skills + "]";
			}
		}

		public itemBuff() {
			super();
			// TODO Auto-generated constructor stub
		}

		public itemBuff(String explain, List<itemBuff.reinforceSkill> reinforceSkill) {
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

		public List<reinforceSkill> getReinforceSkill() {
			return reinforceSkill;
		}

		public void setReinforceSkill(List<reinforceSkill> reinforceSkill) {
			this.reinforceSkill = reinforceSkill;
		}

		@Override
		public String toString() {
			return "itemBuff [explain=" + explain + ", reinforceSkill=" + reinforceSkill + "]";
		}
	
		
	}
	
	public Equipment() {}

	public Equipment(String itemId, String itemName, String itemRarity, String itemType, String itemTypeDetail,
			int itemAvailableLevel, String itemGradeName, String itemGradeValue,
			List<itemStatus> itemStatus, String itemObtainInfo, String itemExplain,
			String itemExplainDetail, String itemFlavorText, String setItemId, String setItemName,
			List<itemStatus> maxItemStatus, List<itemReinforceSkill> itemReinforceSkill,
			itemBuff itemBuff) {
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

	public List<itemStatus> getItemStatus() {
		return itemStatus;
	}

	public void setItemStatus(List<itemStatus> itemStatus) {
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

	public List<itemStatus> getMaxItemStatus() {
		return maxItemStatus;
	}

	public void setMaxItemStatus(List<itemStatus> maxItemStatus) {
		this.maxItemStatus = maxItemStatus;
	}

	public List<itemReinforceSkill> getItemReinforceSkill() {
		return itemReinforceSkill;
	}

	public void setItemReinforceSkill(List<itemReinforceSkill> itemReinforceSkill) {
		this.itemReinforceSkill = itemReinforceSkill;
	}

	public itemBuff getItemBuff() {
		return itemBuff;
	}

	public void setItemBuff(itemBuff itemBuff) {
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
