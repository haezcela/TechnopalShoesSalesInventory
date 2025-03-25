package com.laponhcet.academicprogram;

import java.util.List;

import com.laponhcet.academicsection.AcademicSectionDAO;
import com.laponhcet.academicsection.AcademicSectionDTO;
import com.laponhcet.enrollment.EnrollmentDTO;
import com.laponhcet.enrollment.EnrollmentUtil;
import com.mytechnopal.DataTable;
import com.mytechnopal.UploadedFile;
import com.mytechnopal.base.ActionBase;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.dao.UserDAO;
import com.mytechnopal.dto.UserDTO;

public class AcademicProgramAction extends ActionBase {
	private static final long serialVersionUID = 1L;

	protected void setSessionVars() {		
		List<DTOBase> academicProgramList = new AcademicProgramDAO().getAcademicProgramList();
		DataTable dataTable = new DataTable(AcademicProgramDTO.SESSION_ACADEMIC_PROGRAM_DATA_TABLE, academicProgramList, new String[] {AcademicProgramDTO.ACTION_SEARCH_BY_NAME}, new String[] {"Name"});
		
		dataTable.setColumnNameArr(new String[] {"Code","Academic Program Sub Group Code", "Name", "Action"});
		dataTable.setColumnWidthArr(new String[] {"25", "25", "25", "25"});
		dataTable.setColumnSortTypeArr(new String[] {"", "", DataTable.SORT_TYPE_ASCENDING, "", "", ""});
		
		setSessionAttribute(AcademicProgramDTO.SESSION_ACADEMIC_PROGRAM_DATA_TABLE, dataTable);
		setSessionAttribute(AcademicProgramDTO.SESSION_ACADEMIC_PROGRAM, new AcademicProgramDTO());
		setSessionAttribute(AcademicProgramDTO.SESSION_ACADEMIC_PROGRAM_LIST, academicProgramList);
		
		List<DTOBase> academicProgramSubGroupList = new AcademicProgramSubGroupDAO().getAcademicProgramSubGroupList();
		setSessionAttribute(AcademicProgramSubGroupDTO.SESSION_ACADEMIC_PROGRAM_SUBGROUP_LIST, academicProgramSubGroupList);
		
		setSessionAttribute(UserDTO.SESSION_USER_LIST, new UserDAO().getUserList());
		
		UploadedFile uploadedFile = new UploadedFile(sessionInfo.getSettings());
		uploadedFile.setValidFileExt(new String[] {"png", "jpg"});
		uploadedFile.setMaxSize(1024000); //1Mb 
		setSessionAttribute(UploadedFile.SESSION_UPLOADED_FILE + "_0", uploadedFile);	
	}
}