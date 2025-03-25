package com.laponhcet.user;

import java.util.List;

import com.laponhcet.academicprogram.AcademicProgramDAO;
import com.laponhcet.academicprogram.AcademicProgramDTO;
import com.laponhcet.academicsection.AcademicSectionDAO;
import com.laponhcet.academicsection.AcademicSectionDTO;
import com.laponhcet.academicsection.AcademicSectionUtil;
import com.laponhcet.academicyear.AcademicYearDAO;
import com.laponhcet.academicyear.AcademicYearDTO;
import com.laponhcet.advisory.AdvisoryDAO;
import com.laponhcet.advisory.AdvisoryDTO;
import com.laponhcet.advisory.AdvisoryUtil;
import com.laponhcet.semester.SemesterDAO;
import com.laponhcet.semester.SemesterDTO;
import com.laponhcet.semester.SemesterUtil;
import com.mytechnopal.Calendar;
import com.mytechnopal.base.ActionBase;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.dao.UserDAO;
import com.mytechnopal.dto.UserDTO;
import com.mytechnopal.facelog.FaceLogDAO;
import com.mytechnopal.facelog.FaceLogDTO;
import com.mytechnopal.telegram.TelegramSettingsDAO;
import com.mytechnopal.telegram.TelegramSettingsDTO;


public class HomeAction extends ActionBase {

	private static final long serialVersionUID = 1L;
	
	protected void setSessionVars() {
//		List<DTOBase> userList = new UserDAO().getUserList();
//
//		List<DTOBase> academicProgramList = new AcademicProgramDAO().getAcademicProgramList();
//		List<DTOBase> academicSectionList = new AcademicSectionDAO().getAcademicSectionList();
//		AcademicSectionUtil.setAcademicSectionList(academicSectionList, academicProgramList);
//		
//		List<DTOBase> advisoryList = new AdvisoryDAO().getAdvisoryListByAcademicYearCode("001");
//		AdvisoryUtil.setUser(advisoryList, userList);
//		AdvisoryUtil.setAcademicSection(advisoryList, academicSectionList);
//				
//		List<DTOBase> academicYearList = new AcademicYearDAO().getAcademicYearList();
//		
//		setSessionAttribute(UserDTO.SESSION_USER_LIST, userList);
//		setSessionAttribute(AcademicYearDTO.SESSION_ACADEMIC_YEAR_LIST, academicYearList);
//		setSessionAttribute(SemesterDTO.SESSION_SEMESTER_LIST, SemesterUtil.getSemesterListByAcademicYearList(new SemesterDAO().getSemesterList(), academicYearList));
//		setSessionAttribute(AcademicProgramDTO.SESSION_ACADEMIC_PROGRAM_LIST, academicProgramList);
//		setSessionAttribute(AcademicSectionDTO.SESSION_ACADEMIC_SECTION_LIST, academicSectionList);
//		setSessionAttribute(AdvisoryDTO.SESSION_ADVISORY_LIST, advisoryList);
//		setSessionAttribute(AdvisoryDTO.SESSION_ADVISORY_LIST + "_CURRENT_USER", AdvisoryUtil.getAdvisoryListByUserCode(advisoryList, sessionInfo.getCurrentUser().getCode()));
//	
//		List<DTOBase> faceLogList = new FaceLogDAO().getFaceLogListByUserCode(sessionInfo.getCurrentUser().getCode());
//		setSessionAttribute(FaceLogDTO.SESSION_FACELOG_LIST + "_" + sessionInfo.getCurrentUser().getCode() , faceLogList);
//		
//		setSessionAttribute(Calendar.SESSION_CALENDAR, new Calendar());
//		
//		setSessionAttribute(TelegramSettingsDTO.SESSION_TELEGRAMSETTINGS, new TelegramSettingsDAO().getTelegramSettings());
	}
}