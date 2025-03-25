package com.laponhcet.curriculum;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.mytechnopal.base.DAOBase;
import com.mytechnopal.base.DTOBase;

public class CurriculumDAO extends DAOBase {

	private static final long serialVersionUID = 1L;

	private String qryCurriculumListByAcademicYearCode = "CURRICULUM_LIST_BY_ACADEMICYEARCODE";
	private String qryCurriculumListByAcademicYearCodeAcademicProgramCode = "CURRICULUM_LIST_BY_ACADEMICYEARCODEACADEMICPROGRAMCODE";
	private String qryCurriculumListByAcademicYearCodeAcademicProgramCodeYearLevel = "CURRICULUM_LIST_BY_ACADEMICYEARCODEACADEMICPROGRAMCODEYEARLEVEL";
	private String qryCurriculumListBySemesterCodeAcademicProgramCodeYearLevel = "CURRICULUM_LIST_BY_SEMESTERCODEACADEMICPROGRAMCODEYEARLEVEL";

	@Override
	public void executeAdd(DTOBase arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void executeAddList(List<DTOBase> arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void executeDelete(DTOBase arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void executeDeleteList(List<DTOBase> arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void executeUpdate(DTOBase arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void executeUpdateList(List<DTOBase> arg0) {
		// TODO Auto-generated method stub

	}

	public List<DTOBase> getCurriculumListByAcademicYearCode(String academicYearCode) {
		return getDTOList(qryCurriculumListByAcademicYearCode, academicYearCode);
	}
	
	public List<DTOBase> getCurriculumListByAcademicYearCodeAcademicProgramCode(String academicYearCode, String academicProgramCode) {
		List<Object> objList = new ArrayList<Object>();
		objList.add(academicYearCode);
		objList.add(academicProgramCode);
		return getDTOList(qryCurriculumListByAcademicYearCodeAcademicProgramCode, objList);
	}
	
	public List<DTOBase> getCurriculumListByAcademicYearCodeAcademicProgramCodeYearLevel(String academicYearCode, String academicProgramCode, int yearLevel) {
		List<Object> objList = new ArrayList<Object>();
		objList.add(academicYearCode);
		objList.add(academicProgramCode);
		objList.add(yearLevel);
		return getDTOList(qryCurriculumListByAcademicYearCodeAcademicProgramCodeYearLevel, objList);
	}
	
	public List<DTOBase> getCurriculumListBySemesterCodeAcademicProgramCodeYearLevel(String semesterCode, String academicProgramCode, int yearLevel) {
		List<Object> objList = new ArrayList<Object>();
		objList.add(semesterCode);
		objList.add(academicProgramCode);
		objList.add(yearLevel);
		return getDTOList(qryCurriculumListBySemesterCodeAcademicProgramCodeYearLevel, objList);
	}
	
	@Override
	protected DTOBase rsToObj(ResultSet resultSet) {
		CurriculumDTO curriculum = new CurriculumDTO();
		curriculum.setId(getDBValInt(resultSet, "id"));
		curriculum.setSequence(getDBValInt(resultSet, "sequence"));
		curriculum.getSemester().getAcademicYear().setCode(getDBValStr(resultSet, "academic_year_code"));
		curriculum.getSemester().setCode(getDBValStr(resultSet, "semester_code"));
		curriculum.getAcademicProgram().setCode(getDBValStr(resultSet, "academic_program_code"));
		curriculum.setYearLevel(getDBValInt(resultSet, "year_level"));
		curriculum.getSubject().setCode(getDBValStr(resultSet, "subject_code"));
		return curriculum;
	}

}
