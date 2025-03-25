package com.laponhcet.enrollment;

import java.util.List;

import com.laponhcet.student.StudentDAO;
import com.laponhcet.student.StudentDTO;
import com.mytechnopal.DataTable;
import com.mytechnopal.base.ActionBase;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.dao.MobileProviderDAO;
import com.mytechnopal.dto.MobileProviderDTO;
import com.mytechnopal.usercontact.UserContactDAO;
import com.mytechnopal.usercontact.UserContactDTO;
import com.mytechnopal.usermedia.UserMediaDAO;
import com.mytechnopal.usermedia.UserMediaDTO;

public class EnrollmentAction extends ActionBase {
	private static final long serialVersionUID = 1L;

	protected void setSessionVars() {
		List<DTOBase> enrollmentList = new EnrollmentDAO().getEnrollmentListByAcademicYearCode("001");
		
		DataTable dataTable = new DataTable(EnrollmentDTO.SESSION_ENROLLMENT_DATA_TABLE, enrollmentList, new String[] {EnrollmentDTO.ACTION_SEARCH_BY_NAME, EnrollmentDTO.ACTION_SEARCH_BY_LASTNAME, EnrollmentDTO.ACTION_SEARCH_BY_USER_CODE}, new String[] {"Name", "Last Name", "ID"});
		dataTable.setColumnNameArr(new String[] {"ID", "LAST NAME", "FIRST NAME", "MIDDLE NAME", "PROG/SEC", ""});
		dataTable.setColumnWidthArr(new String[] {"10", "10", "15", "10", "30", "25"});
		//dataTable.setColumnSortTypeArr(new String[] {"", DataTable.SORT_TYPE_DESCENDING, DataTable.SORT_TYPE_ASCENDING, "", "", ""});
		setSessionAttribute(EnrollmentDTO.SESSION_ENROLLMENT_DATA_TABLE, dataTable);
		
		setSessionAttribute(MobileProviderDTO.SESSION_MOBILE_PROVIDER_LIST, new MobileProviderDAO().getMobileProviderList());
		setSessionAttribute(EnrollmentDTO.SESSION_ENROLLMENT_LIST, enrollmentList);
		setSessionAttribute(StudentDTO.SESSION_STUDENT_LIST, new StudentDAO().getStudentList());
		setSessionAttribute(UserContactDTO.SESSION_USERCONTACT_LIST, new UserContactDAO().getUserContactList());
		setSessionAttribute(UserMediaDTO.SESSION_USERMEDIA_LIST, new UserMediaDAO().getUserMediaList());
	}
}
