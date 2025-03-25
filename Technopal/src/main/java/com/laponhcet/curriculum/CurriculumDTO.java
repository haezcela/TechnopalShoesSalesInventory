package com.laponhcet.curriculum;

import com.laponhcet.academicprogram.AcademicProgramDTO;
import com.laponhcet.semester.SemesterDTO;
import com.laponhcet.subject.SubjectDTO;
import com.mytechnopal.base.DTOBase;

public class CurriculumDTO extends DTOBase {
	private static final long serialVersionUID = 1L;

	public static final String SESSION_CURRICULUM = "SESSION_CURRICULUM";
	public static final String SESSION_CURRICULUM_LIST = "SESSION_CURRICULUM_LIST";
	public static final String SESSION_CURRICULUM_DATA_TABLE = "SESSION_CURRICULUM_DATA_TABLE";
	
	private int sequence;
	private SemesterDTO semester;
	private AcademicProgramDTO academicProgram;
	private int yearLevel;
	private SubjectDTO subject;
	
	public CurriculumDTO() {
		super();
		sequence = 0;
		semester = new SemesterDTO();
		academicProgram = new AcademicProgramDTO();
		yearLevel = 0;
		subject = new SubjectDTO();
	}
	
	public CurriculumDTO getCurriculum() {
		CurriculumDTO curriculum = new CurriculumDTO();
		curriculum.setId(super.getId());
		curriculum.setCode(super.getCode());
		curriculum.setSequence(this.sequence);
		curriculum.setSemester(this.semester);
		curriculum.setAcademicProgram(this.academicProgram);
		curriculum.setYearLevel(this.yearLevel);
		curriculum.setSubject(this.subject);
		return curriculum;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	public SemesterDTO getSemester() {
		return semester;
	}

	public void setSemester(SemesterDTO semester) {
		this.semester = semester;
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

	public SubjectDTO getSubject() {
		return subject;
	}

	public void setSubject(SubjectDTO subject) {
		this.subject = subject;
	}
}
