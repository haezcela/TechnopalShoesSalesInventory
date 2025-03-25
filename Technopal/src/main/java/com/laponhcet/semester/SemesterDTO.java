package com.laponhcet.semester;

import java.util.Date;

import com.laponhcet.academicyear.AcademicYearDTO;
import com.mytechnopal.DataTable;
import com.mytechnopal.base.DTOBase;


public class SemesterDTO extends DTOBase {
	private static final long serialVersionUID = 1L;
	
	public static final String SESSION_SEMESTER = "SESSION_SEMESTER";
	public static final String SESSION_SEMESTER_LIST = "SESSION_SEMESTER_LIST";
	public static final String SESSION_SEMESTER_DATA_TABLE = "SESSION_SEMESTER_DATA_TABLE";

	public static final String ACTION_SEARCH_BY_NAME = "ACTION_SEARCH_BY_NAME";

	public static final String NAME_SUMMER = "SUMMER";
	public static final String NAME_FIRST_SEMESTER  = "FIRST_SEMESTER";
	public static final String NAME_SECOND_SEMESTER = "SECOND_SEMESTER";
	public static final String[] NAME_ARR = new String[] {NAME_SUMMER , NAME_FIRST_SEMESTER ,NAME_SECOND_SEMESTER  };
	
	public static final String CODE_SUMMER = "0";
	public static final String CODE_FIRST_SEMESTER  = "1";
	public static final String CODE_SECOND_SEMESTER = "2";
	public static final String[] CODE_ARR = new String[] {CODE_SUMMER , CODE_FIRST_SEMESTER, CODE_SECOND_SEMESTER };
	
	
	public static final String[] SEARCH_CRITERIA_LIST = new String[] {"Academic Year"};

	public static final int SEMESTER_NAME_SUMMER = 0;
	public static final int SEMESTER_NAME_FIRST = 1;
	public static final int SEMESTER_NAME_SECOND = 2;
	
	public static final String SEMESTER_DESCRIPTION_MIDYEAR = "MIDYEAR";
	public static final String SEMESTER_DESCRIPTION_SUMMER = "SUMMER";
	public static final String SEMESTER_DESCRIPTION_FIRST = "FIRST SEMESTER";
	public static final String SEMESTER_DESCRIPTION_SECOND = "SECOND SEMESTER";
	
	
	public static final String[] SEMESTER_NAME_LIST = new String[] {String.valueOf(SEMESTER_NAME_SUMMER),  String.valueOf(SEMESTER_NAME_FIRST), String.valueOf(SEMESTER_NAME_SECOND)};
	public static final String[] SEMESTER_DESCRIPTION_LIST = new String[] {SEMESTER_DESCRIPTION_SUMMER, SEMESTER_DESCRIPTION_FIRST, SEMESTER_DESCRIPTION_SECOND}; 
	public static final String[] SEMESTER_DESCRIPTION_LIST2 = new String[] {SEMESTER_DESCRIPTION_MIDYEAR, SEMESTER_DESCRIPTION_FIRST, SEMESTER_DESCRIPTION_SECOND}; 
	public static final String[] SEMESTER_DESCRIPTION_LIST3 = new String[] {SEMESTER_DESCRIPTION_SUMMER + "/" + SEMESTER_DESCRIPTION_MIDYEAR, SEMESTER_DESCRIPTION_FIRST, SEMESTER_DESCRIPTION_SECOND}; 


	private String code;
	private AcademicYearDTO academicYear;
	private int name;
	private Date dateStart;
	private Date dateEnd;
	

	public SemesterDTO() {
		super();
		this.code = "";
		this.academicYear = new AcademicYearDTO();
		this.name = 0;
		this.dateStart = new Date();
		this.dateEnd = new Date();
	}

	public SemesterDTO getAcademicProgram() {
		SemesterDTO semester = new SemesterDTO();
		semester.setId(super.getId());
		semester.setAddedTimestamp(super.getAddedTimestamp());
		semester.setCode(super.getCode());

		semester.setDateEnd(this.getDateEnd());
		semester.setDateStart(this.getDateStart());
		semester.setAcademicYear(this.getAcademicYear());
		semester.setName(this.getName());
		return semester;
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}



	public AcademicYearDTO getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(AcademicYearDTO academicYear) {
		this.academicYear = academicYear;
	}

	public int getName() {
		return name;
	}

	public void setName(int name) {
		this.name = name;
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
