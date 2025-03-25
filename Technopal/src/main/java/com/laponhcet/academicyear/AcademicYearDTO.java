package com.laponhcet.academicyear;

import java.util.Date;

import com.mytechnopal.base.DTOBase;

public class AcademicYearDTO extends DTOBase {
	private static final long serialVersionUID = 1L;

	public static String SESSION_ACADEMIC_YEAR = "SESSION_ACADEMIC_YEAR";
	public static String SESSION_ACADEMIC_YEAR_LIST = "SESSION_ACADEMIC_YEAR_LIST";
	public static final String SESSION_ACADEMIC_YEAR_DATA_TABLE = "SESSION_ACADEMIC_YEAR_DATA_TABLE";
	
	public static final String ACTION_SEARCH_BY_NAME = "ACTION_SEARCH_BY_NAME";
	
	private String name;
	private String remarks;
	private Date dateStart;
	private Date dateEnd;
	
	
	public AcademicYearDTO() {
		super();
		this.name = "";
		this.remarks = "";
		this.dateStart = new Date();
		this.dateEnd = new Date();
	}
	
	
	public AcademicYearDTO getAcademicYear() {
		AcademicYearDTO academicYear= new AcademicYearDTO();
		academicYear.setId(super.getId());
		academicYear.setCode(super.getCode());;		
		academicYear.setName(this.getName());
		academicYear.setRemarks(this.getRemarks());
		academicYear.setDateStart(this.getDateStart());
		academicYear.setDateEnd(this.getDateEnd());
		return academicYear;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Date getDateStart() {
		return dateStart;
	}

	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}

	public Date getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}
}
