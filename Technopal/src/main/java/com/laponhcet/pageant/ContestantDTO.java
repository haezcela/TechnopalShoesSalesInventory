package com.laponhcet.pageant;

import com.mytechnopal.base.DTOBase;

public class ContestantDTO extends DTOBase {
	private static final long serialVersionUID = 1L;

	public static final String SESSION_CONTESTANT = "SESSION_CONTESTANT";
	public static final String SESSION_CONTESTANT_LIST = "SESSION_CONTESTANT_LIST";
	public static final String SESSION_CONTESTANT_DATA_TABLE = "SESSION_CONTESTANT_DATA_TABLE";
	public static final String SESSION_PERSON_DATA_TABLE = "SESSION_PERSON_DATA_TABLE";
	public static final String ACTION_SEARCH_BY_NAME = "ACTION_SEARCH_BY_NAME";
	public static final String SESSION_ITEM_DATA_TABLE = "SESSION_ITEM_DATA_TABLE";
	private int sequence;
	private String name;
	private String pict;
	private int rankPreliminary;
	
	public ContestantDTO() {
		super();
		sequence = 0;
		name = "";
		pict = "";
		rankPreliminary = 0;
	}
	
	public ContestantDTO getContestant() {
		ContestantDTO contestant = new ContestantDTO();
		contestant.setId(super.getId());
		contestant.setCode(super.getCode());
		contestant.setSequence(this.sequence);
		contestant.setName(this.name);
		contestant.setPict(this.pict);
		contestant.setRankPreliminary(this.rankPreliminary);
		return contestant;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPict() {
		return pict;
	}

	public void setPict(String pict) {
		this.pict = pict;
	}

	public int getRankPreliminary() {
		return rankPreliminary;
	}

	public void setRankPreliminary(int rankPreliminary) {
		this.rankPreliminary = rankPreliminary;
	}
}
