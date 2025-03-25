package com.laponhcet.masterlist;

import java.util.List;

import com.laponhcet.academicsection.AcademicSectionDTO;
import com.laponhcet.academicsection.AcademicSectionUtil;
import com.laponhcet.advisory.AdvisoryDTO;
import com.laponhcet.advisory.AdvisoryUtil;
import com.laponhcet.enrollment.EnrollmentDAO;
import com.laponhcet.enrollment.EnrollmentDTO;
import com.laponhcet.student.StudentDAO;
import com.laponhcet.student.StudentDTO;
import com.mytechnopal.Calendar;
import com.mytechnopal.DataTable;
import com.mytechnopal.base.ActionBase;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.dao.MobileProviderDAO;
import com.mytechnopal.dto.MobileProviderDTO;
import com.mytechnopal.usercontact.UserContactDAO;
import com.mytechnopal.usercontact.UserContactDTO;
import com.mytechnopal.usermedia.UserMediaDAO;
import com.mytechnopal.usermedia.UserMediaDTO;

public class MasterListAction extends ActionBase {
	private static final long serialVersionUID = 1L;

	protected void setSessionVars() {
		List<DTOBase> academicSectionList = (List<DTOBase>) getSessionAttribute(AcademicSectionDTO.SESSION_ACADEMIC_SECTION_LIST);
		List<DTOBase> advisoryList = (List<DTOBase>) getSessionAttribute(AdvisoryDTO.SESSION_ADVISORY_LIST);
		
		if(sessionInfo.getCurrentUser().getCode().equalsIgnoreCase("000000000001")) { //Admin
			DataTable dataTable = new DataTable(AcademicSectionDTO.SESSION_ACADEMIC_SECTION_DATA_TABLE, academicSectionList);
			setSessionAttribute(AcademicSectionDTO.SESSION_ACADEMIC_SECTION_DATA_TABLE, dataTable);
			setSessionAttribute(AcademicSectionDTO.SESSION_ACADEMIC_SECTION_LIST + "_BY_ADVISORY", academicSectionList);
		}
		else if(sessionInfo.getSettings().getCode().equalsIgnoreCase("SMA") && (sessionInfo.getCurrentUser().getCode().equalsIgnoreCase("000000000989") || sessionInfo.getCurrentUser().getCode().equalsIgnoreCase("000000000990"))) {
			DataTable dataTable = new DataTable(AcademicSectionDTO.SESSION_ACADEMIC_SECTION_DATA_TABLE, academicSectionList);
			setSessionAttribute(AcademicSectionDTO.SESSION_ACADEMIC_SECTION_DATA_TABLE, dataTable);
			setSessionAttribute(AcademicSectionDTO.SESSION_ACADEMIC_SECTION_LIST + "_BY_ADVISORY", academicSectionList);
		}
		else {
			List<DTOBase> advisoryListByUserCode = (List<DTOBase>)AdvisoryUtil.getAdvisoryListByUserCode(advisoryList, sessionInfo.getCurrentUser().getCode());
			List<DTOBase> academicSectionListByAdvisory = AcademicSectionUtil.getAcademicSectionListByAdvisoryList(academicSectionList, advisoryListByUserCode);
			DataTable dataTable = new DataTable(AcademicSectionDTO.SESSION_ACADEMIC_SECTION_DATA_TABLE, academicSectionListByAdvisory);
			setSessionAttribute(AcademicSectionDTO.SESSION_ACADEMIC_SECTION_DATA_TABLE, dataTable);
			setSessionAttribute(AcademicSectionDTO.SESSION_ACADEMIC_SECTION_LIST + "_BY_ADVISORY", academicSectionListByAdvisory);
		}
		
		setSessionAttribute(MobileProviderDTO.SESSION_MOBILE_PROVIDER_LIST, new MobileProviderDAO().getMobileProviderList());
		setSessionAttribute(EnrollmentDTO.SESSION_ENROLLMENT_LIST, new EnrollmentDAO().getEnrollmentListByAcademicYearCode("001"));
		setSessionAttribute(StudentDTO.SESSION_STUDENT_LIST, new StudentDAO().getStudentList());
		setSessionAttribute(UserContactDTO.SESSION_USERCONTACT_LIST, new UserContactDAO().getUserContactList());
		setSessionAttribute(UserMediaDTO.SESSION_USERMEDIA_LIST, new UserMediaDAO().getUserMediaList());
		
		setSessionAttribute(Calendar.SESSION_CALENDAR, new Calendar());
	}
}
