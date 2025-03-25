package com.laponhcet.advisory;

import java.util.List;

import com.laponhcet.academicsection.AcademicSectionDTO;
import com.laponhcet.enrollment.EnrollmentDAO;
import com.laponhcet.enrollment.EnrollmentDTO;
import com.laponhcet.enrollment.EnrollmentUtil;
import com.laponhcet.enrollment.SortLastNameAscending;
import com.laponhcet.student.StudentDAO;
import com.laponhcet.student.StudentDTO;
import com.mytechnopal.DataTable;
import com.mytechnopal.base.ActionBase;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.dao.MobileProviderDAO;
import com.mytechnopal.dao.UserDAO;
import com.mytechnopal.dto.MobileProviderDTO;
import com.mytechnopal.dto.UserDTO;
import com.mytechnopal.usercontact.UserContactDAO;
import com.mytechnopal.usercontact.UserContactDTO;
import com.mytechnopal.usermedia.UserMediaDAO;
import com.mytechnopal.usermedia.UserMediaDTO;
import com.mytechnopal.util.DTOUtil;

public class AdvisoryAction extends ActionBase {
	private static final long serialVersionUID = 1L;

	protected void setSessionVars() {
		String academicSectionCode = getRequestString("txtSelectedRecord");
		List<DTOBase> enrollmentList = enrollmentList = EnrollmentUtil.getEnrollmentListByAcademicSectionCode( new EnrollmentDAO().getEnrollmentListByAcademicYearCode("001"), academicSectionCode);
		new SortLastNameAscending().sort(enrollmentList);
		
		DataTable dataTable = new DataTable(EnrollmentDTO.SESSION_ENROLLMENT_DATA_TABLE, enrollmentList, new String[] {EnrollmentDTO.ACTION_SEARCH_BY_NAME, EnrollmentDTO.ACTION_SEARCH_BY_USER_CODE}, new String[] {"Name", "ID"});
		dataTable.setColumnNameArr(new String[] {"ID", "LAST NAME", "FIRST NAME", "MIDDLE NAME", "PROG/SEC", ""});
		dataTable.setColumnWidthArr(new String[] {"10", "15", "20", "10", "30", "15"});
		setSessionAttribute(EnrollmentDTO.SESSION_ENROLLMENT_DATA_TABLE, dataTable);
		
		setSessionAttribute(MobileProviderDTO.SESSION_MOBILE_PROVIDER_LIST, new MobileProviderDAO().getMobileProviderList());
		setSessionAttribute(EnrollmentDTO.SESSION_ENROLLMENT_LIST, enrollmentList);
		setSessionAttribute(UserDTO.SESSION_USER_LIST, new UserDAO().getUserList());
		setSessionAttribute(StudentDTO.SESSION_STUDENT_LIST, new StudentDAO().getStudentList());
		setSessionAttribute(UserContactDTO.SESSION_USERCONTACT_LIST, new UserContactDAO().getUserContactList());
		setSessionAttribute(UserMediaDTO.SESSION_USERMEDIA_LIST, new UserMediaDAO().getUserMediaList());
		setSessionAttribute(AcademicSectionDTO.SESSION_ACADEMIC_SECTION, DTOUtil.getObjByCode((List<DTOBase>) getSessionAttribute(AcademicSectionDTO.SESSION_ACADEMIC_SECTION_LIST), academicSectionCode));
	}
}
