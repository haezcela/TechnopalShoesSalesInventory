package com.laponhcet.qrcode;

import java.util.ArrayList;
import java.util.List;

import com.laponhcet.academicprogram.AcademicProgramDAO;
import com.laponhcet.academicsection.AcademicSectionDTO;
import com.laponhcet.academicsection.AcademicSectionUtil;
import com.laponhcet.academicsection.AcademicSectionDAO;
import com.laponhcet.enrollment.EnrollmentDAO;
import com.laponhcet.enrollment.EnrollmentDTO;
import com.mytechnopal.DataTable;
import com.mytechnopal.base.ActionBase;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.dao.UserDAO;
import com.mytechnopal.dto.UserDTO;

public class QRCodeAction extends ActionBase {
	private static final long serialVersionUID = 1L;

	protected void setSessionVars() {
		List<DTOBase> userList = new UserDAO().getUserList();
		
		DataTable dataTable = new DataTable(UserDTO.SESSION_USER_DATA_TABLE, userList, new String[] {UserDTO.ACTION_SEARCH_BY_NAME}, new String[] {"Name"});
		dataTable.setColumnNameArr(new String[] {"ID", "LAST NAME", "FIRST NAME", "MIDDLE NAME", "", ""});
		dataTable.setColumnWidthArr(new String[] {"10", "15", "20", "15", "30", "10"});
		setSessionAttribute(UserDTO.SESSION_USER_DATA_TABLE, dataTable);
		
		List<DTOBase> academicSectionList = new AcademicSectionDAO().getAcademicSectionList();
		AcademicSectionUtil.setAcademicSectionList(academicSectionList, new AcademicProgramDAO().getAcademicProgramList());
		
		setSessionAttribute(EnrollmentDTO.SESSION_ENROLLMENT_LIST, new EnrollmentDAO().getEnrollmentListByAcademicYearCode("001"));
		setSessionAttribute(AcademicSectionDTO.SESSION_ACADEMIC_SECTION_LIST, academicSectionList);
		setSessionAttribute(UserDTO.SESSION_USER_LIST, userList);
		setSessionAttribute(UserDTO.SESSION_USER_LIST + "_QRCODE", new ArrayList<DTOBase>());
		//setSessionAttribute(null, dataTable);
	}
}