package com.laponhcet.student;

import com.laponhcet.academicprogram.AcademicProgramDTO;
import com.mytechnopal.dto.UserDTO;

public class StudentDTO extends UserDTO {
	private static final long serialVersionUID = 1L;

	public static final String SESSION_STUDENT = "SESSION_STUDENT";
	public static final String SESSION_STUDENT_LIST = "SESSION_STUDENT_LIST";
	public static final String SESSION_STUDENT_DATA_TABLE = "SESSION_STUDENT_DATA_TABLE";
	
	public static final String ACTION_SEARCH_BY_NAME = "ACTION_SEARCH_BY_NAME";
	public static final String ACTION_SEARCH_BY_USER_CODE = "ACTION_SEARCH_BY_USER_CODE";
	
	private String learnerReferenceNumber;
	private AcademicProgramDTO academicProgram;
	
	public StudentDTO() {
		super();
		learnerReferenceNumber = "";
		academicProgram = new AcademicProgramDTO();
	}
	
	public StudentDTO getStudent() {
		StudentDTO student = new StudentDTO();
		student.setId(super.getId());
		student.setCode(super.getCode());
		student.setLearnerReferenceNumber(this.learnerReferenceNumber);
		student.setAcademicProgram(this.academicProgram);
		return student;
	}
	
	public String getLearnerReferenceNumber() {
		return learnerReferenceNumber;
	}
	
	public void setLearnerReferenceNumber(String learnerReferenceNumber) {
		this.learnerReferenceNumber = learnerReferenceNumber;
	}
	
	public AcademicProgramDTO getAcademicProgram() {
		return academicProgram;
	}
	
	public void setAcademicProgram(AcademicProgramDTO academicProgram) {
		this.academicProgram = academicProgram;
	}
}
