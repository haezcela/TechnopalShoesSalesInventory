package com.laponhcet.semester;

import java.util.List;

import com.laponhcet.academicyear.AcademicYearDAO;
import com.laponhcet.academicyear.AcademicYearDTO;
import com.mytechnopal.DataTable;
import com.mytechnopal.UploadedFile;
import com.mytechnopal.base.ActionBase;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.dao.UserDAO;
import com.mytechnopal.dto.UserDTO;

public class SemesterAction extends ActionBase {
	private static final long serialVersionUID = 1L;

	protected void setSessionVars() {		
		List<DTOBase> semesterList = new SemesterDAO().getSemesterList();
		DataTable dataTable = new DataTable(SemesterDTO.SESSION_SEMESTER_DATA_TABLE, semesterList, new String[] {SemesterDTO.ACTION_SEARCH_BY_NAME}, new String[] {"Name"});
		dataTable.setColumnNameArr(new String[] {"Code", "Academic Year Name", "Name","Date Start", "Date End", "Action"});
		dataTable.setColumnWidthArr(new String[] {"15", "15", "15", "20", "20", "15"});
		setSessionAttribute(SemesterDTO.SESSION_SEMESTER_DATA_TABLE, dataTable);
		setSessionAttribute(SemesterDTO.SESSION_SEMESTER, new SemesterDTO());
		setSessionAttribute(AcademicYearDTO.SESSION_ACADEMIC_YEAR_LIST, new AcademicYearDAO().getAcademicYearList());
		
	}
}
