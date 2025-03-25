package com.laponhcet.masterlist;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.laponhcet.academicsection.AcademicSectionDTO;
import com.laponhcet.advisory.AdvisoryDTO;
import com.laponhcet.enrollment.EnrollmentDTO;
import com.laponhcet.enrollment.EnrollmentUtil;
import com.laponhcet.enrollment.SortLastNameAscending;
import com.laponhcet.student.StudentDTO;
import com.laponhcet.student.StudentUtil;
import com.mytechnopal.Calendar;
import com.mytechnopal.DataTable;
import com.mytechnopal.base.ActionAjaxBase;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.link.LinkDTO;
import com.mytechnopal.dto.UserDTO;
import com.mytechnopal.telegram.TelegramSettingsDTO;
import com.mytechnopal.usercontact.UserContactDTO;
import com.mytechnopal.usercontact.UserContactUtil;
import com.mytechnopal.usermedia.UserMediaDTO;
import com.mytechnopal.usermedia.UserMediaUtil;
import com.mytechnopal.util.DTOUtil;
import com.mytechnopal.util.DateTimeUtil;

public class MasterListActionAjax extends ActionAjaxBase {
	private static final long serialVersionUID = 1L;

	protected void customAction(JSONObject jsonObj, String action) {
		if(action.equalsIgnoreCase("VIEW ENROLLMENT LIST")) {
			List<DTOBase> academicSectionList = (List<DTOBase>) getSessionAttribute(AcademicSectionDTO.SESSION_ACADEMIC_SECTION_LIST);
			AcademicSectionDTO academicSection = (AcademicSectionDTO) DTOUtil.getObjByCode(academicSectionList, getRequestString("txtAcademicSectionCode"));
			
			List<DTOBase> enrollmentList = (List<DTOBase>) getSessionAttribute(EnrollmentDTO.SESSION_ENROLLMENT_LIST);
			List<DTOBase> enrollmentListByAcademicSectionCode = EnrollmentUtil.getEnrollmentListByAcademicSectionCode(enrollmentList, academicSection.getCode());
			
			List<DTOBase> userList = (List<DTOBase>) getSessionAttribute(UserDTO.SESSION_USER_LIST);
			List<DTOBase> studentList = (List<DTOBase>) getSessionAttribute(StudentDTO.SESSION_STUDENT_LIST);
			List<DTOBase> userContactList = (List<DTOBase>) getSessionAttribute(UserContactDTO.SESSION_USERCONTACT_LIST);
			List<DTOBase> userMediaList = (List<DTOBase>) getSessionAttribute(UserMediaDTO.SESSION_USERMEDIA_LIST);
			
			for(int row=0; row < enrollmentListByAcademicSectionCode.size(); row++) {			
				EnrollmentDTO enrollment = (EnrollmentDTO) enrollmentListByAcademicSectionCode.get(row);
				enrollment.setStudent((StudentDTO) DTOUtil.getObjByCode(studentList, enrollment.getStudent().getCode()));
				StudentUtil.setStudent(enrollment.getStudent(), (UserDTO) DTOUtil.getObjByCode(userList, enrollment.getStudent().getCode()));
				enrollment.getStudent().setUserContactList(UserContactUtil.getUserContactListByUserCode(userContactList, enrollment.getStudent().getCode()));
				enrollment.getStudent().setUserMediaList(UserMediaUtil.getUserMediaListByUserCode(userMediaList, enrollment.getStudent().getCode()));
			}
			new SortLastNameAscending().sort(enrollmentListByAcademicSectionCode);
			
			try {
				jsonObj.put(LinkDTO.PAGE_CONTENT, EnrollmentUtil.getEnrollmentListByAcademicSectionStr(sessionInfo, academicSection, enrollmentListByAcademicSectionCode));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		else if(action.equalsIgnoreCase("VIEW QR CODE LIST")) {
			List<DTOBase> academicSectionList = (List<DTOBase>) getSessionAttribute(AcademicSectionDTO.SESSION_ACADEMIC_SECTION_LIST);
			AcademicSectionDTO academicSection = (AcademicSectionDTO) DTOUtil.getObjByCode(academicSectionList, getRequestString("txtAcademicSectionCode"));
			
			List<DTOBase> enrollmentList = (List<DTOBase>) getSessionAttribute(EnrollmentDTO.SESSION_ENROLLMENT_LIST);
			List<DTOBase> enrollmentListByAcademicSectionCode = EnrollmentUtil.getEnrollmentListByAcademicSectionCode(enrollmentList, academicSection.getCode());
			
			List<DTOBase> userList = (List<DTOBase>) getSessionAttribute(UserDTO.SESSION_USER_LIST);
			for(int row=0; row < enrollmentListByAcademicSectionCode.size(); row++) {			
				EnrollmentDTO enrollment = (EnrollmentDTO) enrollmentListByAcademicSectionCode.get(row);
				StudentUtil.setStudent(enrollment.getStudent(), (UserDTO) DTOUtil.getObjByCode(userList, enrollment.getStudent().getCode()));
			}
			new SortLastNameAscending().sort(enrollmentListByAcademicSectionCode);
			
			try {
				jsonObj.put(LinkDTO.PAGE_CONTENT, EnrollmentUtil.getQRCodeListByAcademicSectionStr(sessionInfo, academicSection, enrollmentListByAcademicSectionCode));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		else if(action.equalsIgnoreCase("VIEW TELEGRAM REGISTRATION LIST")) {
			List<DTOBase> academicSectionList = (List<DTOBase>) getSessionAttribute(AcademicSectionDTO.SESSION_ACADEMIC_SECTION_LIST);
			AcademicSectionDTO academicSection = (AcademicSectionDTO) DTOUtil.getObjByCode(academicSectionList, getRequestString("txtAcademicSectionCode"));
			
			List<DTOBase> enrollmentList = (List<DTOBase>) getSessionAttribute(EnrollmentDTO.SESSION_ENROLLMENT_LIST);
			List<DTOBase> enrollmentListByAcademicSectionCode = EnrollmentUtil.getEnrollmentListByAcademicSectionCode(enrollmentList, academicSection.getCode());
			
			List<DTOBase> userList = (List<DTOBase>) getSessionAttribute(UserDTO.SESSION_USER_LIST);
			for(int row=0; row < enrollmentListByAcademicSectionCode.size(); row++) {			
				EnrollmentDTO enrollment = (EnrollmentDTO) enrollmentListByAcademicSectionCode.get(row);
				enrollment.setAcademicSection((AcademicSectionDTO) DTOUtil.getObjByCode(academicSectionList, enrollment.getAcademicSection().getCode()));
				StudentUtil.setStudent(enrollment.getStudent(), (UserDTO) DTOUtil.getObjByCode(userList, enrollment.getStudent().getCode()));
			}
			new SortLastNameAscending().sort(enrollmentListByAcademicSectionCode);
			
			try {
				jsonObj.put(LinkDTO.PAGE_CONTENT, EnrollmentUtil.getTelegramRegistrationListByAcademicSectionStr(sessionInfo, academicSection, enrollmentListByAcademicSectionCode, (TelegramSettingsDTO) getSessionAttribute(TelegramSettingsDTO.SESSION_TELEGRAMSETTINGS)));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		else if(action.equalsIgnoreCase("VIEW FACE LOG LIST")) {
			Calendar calendar = (Calendar) getSessionAttribute(Calendar.SESSION_CALENDAR);
			List<DTOBase> academicSectionList = (List<DTOBase>) getSessionAttribute(AcademicSectionDTO.SESSION_ACADEMIC_SECTION_LIST);
			AcademicSectionDTO academicSection = (AcademicSectionDTO) DTOUtil.getObjByCode(academicSectionList, getRequestString("txtAcademicSectionCode"));
			setSessionAttribute(AcademicSectionDTO.SESSION_ACADEMIC_SECTION, academicSection);
			
			List<DTOBase> enrollmentList = (List<DTOBase>) getSessionAttribute(EnrollmentDTO.SESSION_ENROLLMENT_LIST);
			List<DTOBase> enrollmentListByAcademicSectionCode = EnrollmentUtil.getEnrollmentListByAcademicSectionCode(enrollmentList, academicSection.getCode());
			
			List<DTOBase> userList = (List<DTOBase>) getSessionAttribute(UserDTO.SESSION_USER_LIST);
			for(int row=0; row < enrollmentListByAcademicSectionCode.size(); row++) {			
				EnrollmentDTO enrollment = (EnrollmentDTO) enrollmentListByAcademicSectionCode.get(row);
				StudentUtil.setStudent(enrollment.getStudent(), (UserDTO) DTOUtil.getObjByCode(userList, enrollment.getStudent().getCode()));
			}
			new SortLastNameAscending().sort(enrollmentListByAcademicSectionCode);
			
			try {
				jsonObj.put(LinkDTO.PAGE_CONTENT, EnrollmentUtil.getFaceLogListByAcademicSectionStr(sessionInfo, calendar, enrollmentListByAcademicSectionCode));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		else if(action.equalsIgnoreCase("SELECT CALENDAR DAY OF THE MONTH")) {
			Calendar calendar = (Calendar) getSessionAttribute(Calendar.SESSION_CALENDAR);
			calendar.setDate(DateTimeUtil.getDateBySettingDate(calendar.getDate(), getRequestInt("txtDayOfTheMonth")));
			List<DTOBase> academicSectionList = (List<DTOBase>) getSessionAttribute(AcademicSectionDTO.SESSION_ACADEMIC_SECTION_LIST);
			AcademicSectionDTO academicSection = (AcademicSectionDTO) getSessionAttribute(AcademicSectionDTO.SESSION_ACADEMIC_SECTION);
			
			List<DTOBase> enrollmentList = (List<DTOBase>) getSessionAttribute(EnrollmentDTO.SESSION_ENROLLMENT_LIST);
			List<DTOBase> enrollmentListByAcademicSectionCode = EnrollmentUtil.getEnrollmentListByAcademicSectionCode(enrollmentList, academicSection.getCode());
			
			List<DTOBase> userList = (List<DTOBase>) getSessionAttribute(UserDTO.SESSION_USER_LIST);
			for(int row=0; row < enrollmentListByAcademicSectionCode.size(); row++) {			
				EnrollmentDTO enrollment = (EnrollmentDTO) enrollmentListByAcademicSectionCode.get(row);
				StudentUtil.setStudent(enrollment.getStudent(), (UserDTO) DTOUtil.getObjByCode(userList, enrollment.getStudent().getCode()));
			}
			new SortLastNameAscending().sort(enrollmentListByAcademicSectionCode);
			
			try {
				jsonObj.put(LinkDTO.PAGE_CONTENT, EnrollmentUtil.getFaceLogListByAcademicSectionStr(sessionInfo, calendar, enrollmentListByAcademicSectionCode));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		else if(action.equalsIgnoreCase("SELECT CALENDAR MONTH")) {
			Calendar calendar = (Calendar) getSessionAttribute(Calendar.SESSION_CALENDAR);
			calendar.setDate(DateTimeUtil.getDateBySettingMonth(calendar.getDate(), getRequestInt("txtMonth")));
			List<DTOBase> academicSectionList = (List<DTOBase>) getSessionAttribute(AcademicSectionDTO.SESSION_ACADEMIC_SECTION_LIST);
			AcademicSectionDTO academicSection = (AcademicSectionDTO) getSessionAttribute(AcademicSectionDTO.SESSION_ACADEMIC_SECTION);
			
			List<DTOBase> enrollmentList = (List<DTOBase>) getSessionAttribute(EnrollmentDTO.SESSION_ENROLLMENT_LIST);
			List<DTOBase> enrollmentListByAcademicSectionCode = EnrollmentUtil.getEnrollmentListByAcademicSectionCode(enrollmentList, academicSection.getCode());
			
			List<DTOBase> userList = (List<DTOBase>) getSessionAttribute(UserDTO.SESSION_USER_LIST);
			for(int row=0; row < enrollmentListByAcademicSectionCode.size(); row++) {			
				EnrollmentDTO enrollment = (EnrollmentDTO) enrollmentListByAcademicSectionCode.get(row);
				StudentUtil.setStudent(enrollment.getStudent(), (UserDTO) DTOUtil.getObjByCode(userList, enrollment.getStudent().getCode()));
			}
			new SortLastNameAscending().sort(enrollmentListByAcademicSectionCode);
			
			try {
				jsonObj.put(LinkDTO.PAGE_CONTENT, EnrollmentUtil.getFaceLogListByAcademicSectionStr(sessionInfo, calendar, enrollmentListByAcademicSectionCode));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		else if(action.equalsIgnoreCase("SELECT CALENDAR YEAR")) {
			Calendar calendar = (Calendar) getSessionAttribute(Calendar.SESSION_CALENDAR);
			calendar.setDate(DateTimeUtil.getDateBySettingYear(calendar.getDate(), getRequestInt("txtYear")));			
			List<DTOBase> academicSectionList = (List<DTOBase>) getSessionAttribute(AcademicSectionDTO.SESSION_ACADEMIC_SECTION_LIST);
			AcademicSectionDTO academicSection = (AcademicSectionDTO) getSessionAttribute(AcademicSectionDTO.SESSION_ACADEMIC_SECTION);
			
			List<DTOBase> enrollmentList = (List<DTOBase>) getSessionAttribute(EnrollmentDTO.SESSION_ENROLLMENT_LIST);
			List<DTOBase> enrollmentListByAcademicSectionCode = EnrollmentUtil.getEnrollmentListByAcademicSectionCode(enrollmentList, academicSection.getCode());
			
			List<DTOBase> userList = (List<DTOBase>) getSessionAttribute(UserDTO.SESSION_USER_LIST);
			for(int row=0; row < enrollmentListByAcademicSectionCode.size(); row++) {			
				EnrollmentDTO enrollment = (EnrollmentDTO) enrollmentListByAcademicSectionCode.get(row);
				StudentUtil.setStudent(enrollment.getStudent(), (UserDTO) DTOUtil.getObjByCode(userList, enrollment.getStudent().getCode()));
			}
			new SortLastNameAscending().sort(enrollmentListByAcademicSectionCode);
			
			try {
				jsonObj.put(LinkDTO.PAGE_CONTENT, EnrollmentUtil.getFaceLogListByAcademicSectionStr(sessionInfo, calendar, enrollmentListByAcademicSectionCode));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
	
	protected void dataTableAction(JSONObject jsonObj, DataTable dataTable) {
		List<DTOBase> enrollmentList = (List<DTOBase>) getSessionAttribute(EnrollmentDTO.SESSION_ENROLLMENT_LIST);
		List<DTOBase> advisoryList = (List<DTOBase>) getSessionAttribute(AdvisoryDTO.SESSION_ADVISORY_LIST);
		try {
			jsonObj.put(LinkDTO.PAGE_CONTENT, EnrollmentUtil.getDataCardStr(sessionInfo, (List<DTOBase>) getSessionAttribute(AcademicSectionDTO.SESSION_ACADEMIC_SECTION_LIST + "_BY_ADVISORY"), enrollmentList, advisoryList));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
