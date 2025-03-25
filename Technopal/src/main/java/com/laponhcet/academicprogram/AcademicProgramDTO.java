package com.laponhcet.academicprogram;

import com.mytechnopal.base.DTOBase;

public class AcademicProgramDTO extends DTOBase {
	private static final long serialVersionUID = 1L;
	public static final String SESSION_ACADEMIC_PROGRAM = "SESSION_ACADEMIC_PROGRAM";
	public static final String SESSION_ACADEMIC_PROGRAM_LIST = "SESSION_ACADEMIC_PROGRAMG_LIST";
	public static final String SESSION_ACADEMIC_PROGRAM_DATA_TABLE = "SESSION_ACADEMIC_PROGRAM_DATA_TABLE";

	public static final String  PERIOD_TYPE_ACADEMICYEAR = "ACADEMIC YEAR";
	public static final String  PERIOD_TYPE_SEMESTER = "SEMESTER";
	
	public static final String ACTION_SEARCH_BY_NAME = "ACTION_SEARCH_BY_NAME";

	private String name;
	private String description;
	private int graduationYearLevel;
	private String logo;
	private String periodType;
	
	public AcademicProgramDTO() {
		super();
		this.name = "";
		this.description = "";
		this.graduationYearLevel = 0;
		this.logo = "";
		this.periodType = PERIOD_TYPE_ACADEMICYEAR;
	}

	public AcademicProgramDTO getAcademicProgram() {
		AcademicProgramDTO academicProgram = new AcademicProgramDTO();
		academicProgram.setId(super.getId());
		academicProgram.setCode(super.getCode());
		academicProgram.setName(this.name);
		academicProgram.setDescription(this.description);
		academicProgram.setGraduationYearLevel(this.graduationYearLevel);
		academicProgram.setLogo(this.logo);
		academicProgram.setPeriodType(this.periodType);
		return academicProgram;
	}
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getGraduationYearLevel() {
		return graduationYearLevel;
	}

	public void setGraduationYearLevel(int graduationYearLevel) {
		this.graduationYearLevel = graduationYearLevel;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getPeriodType() {
		return periodType;
	}

	public void setPeriodType(String periodType) {
		this.periodType = periodType;
	}
}
