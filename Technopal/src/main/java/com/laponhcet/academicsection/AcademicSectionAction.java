package com.laponhcet.academicsection;

import java.util.List;

import com.laponhcet.academicprogram.AcademicProgramDAO;
import com.laponhcet.academicprogram.AcademicProgramDTO;
import com.mytechnopal.DataTable;
import com.mytechnopal.base.ActionBase;
import com.mytechnopal.base.DTOBase;

public class AcademicSectionAction extends ActionBase {
	private static final long serialVersionUID = 1L;
	
	protected void setSessionVars() {		
		List<DTOBase> academicProgramList = new AcademicProgramDAO().getAcademicProgramList();
		List<DTOBase> academicSectionList = new AcademicSectionDAO().getAcademicSectionList();
		
		new SortNameAscending().sort(academicSectionList);
		
		DataTable dataTable = new DataTable(AcademicSectionDTO.SESSION_ACADEMIC_SECTION_DATA_TABLE, academicSectionList, new String[] {AcademicSectionDTO.ACTION_SEARCH_BY_NAME}, new String[] {"Name"});
		dataTable.setColumnNameArr(new String[] {"Academic Program", "Year Level", "Name", ""});
		dataTable.setColumnWidthArr(new String[] {"25", "25", "25", "25"});
		dataTable.setColumnSortTypeArr(new String[] {"", "", DataTable.SORT_TYPE_DESCENDING, ""});
		setSessionAttribute(AcademicSectionDTO.SESSION_ACADEMIC_SECTION_DATA_TABLE, dataTable);
		setSessionAttribute(AcademicSectionDTO.SESSION_ACADEMIC_SECTION, new AcademicSectionDTO());
		setSessionAttribute(AcademicSectionDTO.SESSION_ACADEMIC_SECTION_LIST, academicSectionList);
		setSessionAttribute(AcademicProgramDTO.SESSION_ACADEMIC_PROGRAM_LIST, academicProgramList);
//		List<DTOBase> teacherList = new TeacherDAO().getTeacherList();
//		TeacherUtil.setTeacherList(teacherList, new UserDAO().getUserList());
//		setSessionAttribute(TeacherDTO.SESSION_TEACHER_LIST, teacherList);
	}
}