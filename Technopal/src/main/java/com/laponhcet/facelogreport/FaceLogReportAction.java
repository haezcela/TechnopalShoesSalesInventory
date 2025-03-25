package com.laponhcet.facelogreport;

import java.util.List;

import com.laponhcet.academicsection.AcademicSectionDTO;
import com.laponhcet.enrollment.EnrollmentDAO;
import com.laponhcet.enrollment.EnrollmentDTO;
import com.laponhcet.enrollment.EnrollmentUtil;
import com.laponhcet.student.StudentDAO;
import com.laponhcet.student.StudentUtil;
import com.mytechnopal.base.ActionBase;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.dto.UserDTO;

public class FaceLogReportAction extends ActionBase {
	private static final long serialVersionUID = 1L;

	protected void setSessionVars() {
		List<DTOBase> studentList = new StudentDAO().getStudentList();
		List<DTOBase> userList = (List<DTOBase>) getSessionAttribute(UserDTO.SESSION_USER_LIST);
		StudentUtil.setStudentList(studentList, userList);
		
		List<DTOBase> enrollmentList = new EnrollmentDAO().getEnrollmentListByAcademicYearCode("001");
		List<DTOBase> academicSectionList = (List<DTOBase>) getSessionAttribute(AcademicSectionDTO.SESSION_ACADEMIC_SECTION_LIST);
		EnrollmentUtil.setEnrollmentListAcademicSectionStudent(enrollmentList, academicSectionList, studentList);
		setSessionAttribute(EnrollmentDTO.SESSION_ENROLLMENT_LIST, enrollmentList);
	}
}
