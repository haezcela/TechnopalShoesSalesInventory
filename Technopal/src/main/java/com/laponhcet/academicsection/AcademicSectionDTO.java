package com.laponhcet.academicsection;

import com.laponhcet.academicprogram.AcademicProgramDTO;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.dto.UserDTO;

public class AcademicSectionDTO extends DTOBase{
	private static final long serialVersionUID = 1L;
	
	public static final String SESSION_ACADEMIC_SECTION= "SESSION_ACADEMIC_SECTION";
	public static final String SESSION_ACADEMIC_SECTION_LIST = "SESSION_ACADEMIC_SECTION_LIST";
	public static final String SESSION_ACADEMIC_SECTION_DATA_TABLE = "SESSION_ACADEMIC_SECTION_DATA_TABLE";

	public static final String ACTION_SEARCH_BY_NAME = "ACTION_SEARCH_BY_NAME";
	
	private AcademicProgramDTO academicProgram;
	private int yearLevel;
	private String name;
	
	public AcademicSectionDTO() {
		super();
		this.academicProgram = new AcademicProgramDTO();
		this.yearLevel = 0;
		this.name = "";
	}
	
	public AcademicSectionDTO getAcademicSection() {
		AcademicSectionDTO academicSection = new AcademicSectionDTO();
		academicSection.setId(super.getId());
		academicSection.setCode(super.getCode());
		academicSection.setAcademicProgram(this.getAcademicProgram());
		academicSection.setName(this.getName());
		academicSection.setYearLevel(this.getYearLevel());
		return academicSection;
	}


	public AcademicProgramDTO getAcademicProgram() {
		return academicProgram;
	}
	
	public void setAcademicProgram(AcademicProgramDTO academicProgram) {
		this.academicProgram = academicProgram;
	}
	
	public int getYearLevel() {
		return yearLevel;
	}
	
	public void setYearLevel(int yearLevel) {
		this.yearLevel = yearLevel;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}