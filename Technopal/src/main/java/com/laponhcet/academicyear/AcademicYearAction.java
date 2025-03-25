package com.laponhcet.academicyear;

import java.util.List;

import com.mytechnopal.DataTable;
import com.mytechnopal.base.ActionBase;
import com.mytechnopal.base.DTOBase;

public class AcademicYearAction extends ActionBase {
	private static final long serialVersionUID = 1L;
	
	protected void setSessionVars() {		
		List<DTOBase> academicYearList = new AcademicYearDAO().getAcademicYearList();
		DataTable dataTable = new DataTable(AcademicYearDTO.SESSION_ACADEMIC_YEAR_DATA_TABLE, academicYearList, new String[] {AcademicYearDTO.ACTION_SEARCH_BY_NAME}, new String[] {"Name"});
		dataTable.setColumnNameArr(new String[] {"Code", "Name", "Remarks", "Date Start", "Date End", "Academic Progam Group Code", "Grading System", "Action" });
		dataTable.setColumnWidthArr(new String[] {"10", "10", "10", "15", "15", "10", "20", "10"});
		setSessionAttribute(AcademicYearDTO.SESSION_ACADEMIC_YEAR_DATA_TABLE, dataTable);
		setSessionAttribute(AcademicYearDTO.SESSION_ACADEMIC_YEAR, new AcademicYearDTO());
		setSessionAttribute(AcademicProgramGroupDTO.SESSION_ACADEMIC_PROGRAM_GROUP_LIST, new AcademicProgramGroupDAO().getAcademicProgramGroupList());
	}
}
