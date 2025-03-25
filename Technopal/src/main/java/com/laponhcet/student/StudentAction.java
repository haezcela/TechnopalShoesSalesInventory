package com.laponhcet.student;

import java.util.List;

import com.laponhcet.academicprogram.AcademicProgramDAO;
import com.laponhcet.academicprogram.AcademicProgramUtil;
import com.laponhcet.academicsection.AcademicSectionDAO;
import com.laponhcet.academicsection.AcademicSectionDTO;
import com.laponhcet.academicsection.AcademicSectionUtil;
import com.laponhcet.enrollment.EnrollmentDAO;
import com.laponhcet.enrollment.EnrollmentDTO;
import com.laponhcet.enrollment.EnrollmentUtil;
import com.mytechnopal.DataTable;
import com.mytechnopal.UploadedFile;
import com.mytechnopal.base.ActionBase;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.dao.UserDAO;
import com.mytechnopal.dto.UserDTO;
import com.mytechnopal.identification.IdentificationDAO;
import com.mytechnopal.identification.IdentificationDTO;

public class StudentAction extends ActionBase {
	private static final long serialVersionUID = 1L;

	protected void setSessionVars() {	
		List<DTOBase> studentList = new StudentDAO().getStudentList();
		DataTable dataTable = new DataTable(StudentDTO.SESSION_STUDENT_DATA_TABLE, studentList, new String[] {StudentDTO.ACTION_SEARCH_BY_NAME}, new String[] {"Name"});
		setSessionAttribute(StudentDTO.SESSION_STUDENT_DATA_TABLE, dataTable);
		
		setSessionAttribute(UserDTO.SESSION_USER_LIST, new UserDAO().getUserList());
		
		List<DTOBase> academicProgramList = new AcademicProgramDAO().getAcademicProgramList();
		List<DTOBase> academicProgramGroupList = new AcademicProgramGroupDAO().getAcademicProgramGroupList();
		List<DTOBase> academicProgramSubGroupList = new AcademicProgramSubGroupDAO().getAcademicProgramSubGroupList();
		
		AcademicProgramUtil.setAcademicProgramList(academicProgramList, academicProgramGroupList, academicProgramSubGroupList);
		List<DTOBase> academicSectionList = new AcademicSectionDAO().getAcademicSectionList();
		AcademicSectionUtil.setAcademicSectionList(academicSectionList, academicProgramList);
		setSessionAttribute(AcademicSectionDTO.SESSION_ACADEMIC_SECTION_LIST, academicSectionList);
		
		setSessionAttribute(IdentificationDTO.SESSION_IDENTIFICATION_LIST, new IdentificationDAO().getIdentificationList());
		setSessionAttribute(ContactDTO.SESSION_CONTACT_LIST, new ContactDAO().getContactList());
		setSessionAttribute(StudentDTO.SESSION_STUDENT_LIST, studentList);
		
		UploadedFile uploadedFile = new UploadedFile(sessionInfo.getSettings()); //no need to specify id for the default id is 0
		uploadedFile.setValidFileExt(new String[] {"png", "jpg"});
		uploadedFile.setMaxSize(1024000); //1Mb 
		setSessionAttribute(UploadedFile.SESSION_UPLOADED_FILE + "_0", uploadedFile);	
		
		List<DTOBase> enrollmentList = new EnrollmentDAO().getEnrollmentListByAcademicYearCode("047");
		EnrollmentUtil.setEnrollmentListAcademicSection(enrollmentList, academicSectionList);
		
		setSessionAttribute(EnrollmentDTO.SESSION_ENROLLMENT_LIST, enrollmentList);
		setSessionAttribute(EnrollmentDTO.SESSION_ENROLLMENT, new EnrollmentDTO());
	}
}
