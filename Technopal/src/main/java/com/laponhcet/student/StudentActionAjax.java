package com.laponhcet.student;

import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.laponhcet.academicsection.AcademicSectionDTO;
import com.laponhcet.enrollment.EnrollmentDAO;
import com.laponhcet.enrollment.EnrollmentDTO;
import com.laponhcet.enrollment.EnrollmentUtil;
import com.laponhcet.studentwithenrollment.StudentWithEnrollmentDAO;
import com.laponhcet.studentwithenrollment.StudentWithEnrollmentUtil;
import com.mytechnopal.ActionResponse;
import com.mytechnopal.DataTable;
import com.mytechnopal.SessionInfo;
import com.mytechnopal.UploadedFile;
import com.mytechnopal.base.ActionAjaxBase;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.contact.ContactDTO;
import com.mytechnopal.contact.ContactTypeDTO;
import com.mytechnopal.contact.ContactUtil;
import com.mytechnopal.dao.UserDAO;
import com.mytechnopal.dto.UserDTO;
import com.mytechnopal.identification.IdentificationDTO;
import com.mytechnopal.identification.IdentificationUtil;
import com.mytechnopal.util.DTOUtil;
import com.mytechnopal.util.FileUtil;
import com.mytechnopal.util.PageUtil;
import com.mytechnopal.util.StringUtil;

public class StudentActionAjax extends ActionAjaxBase {
	private static final long serialVersionUID = 1L;

	protected void setInput(String action) {
		EnrollmentDTO enrollment = (EnrollmentDTO) getSessionAttribute(EnrollmentDTO.SESSION_ENROLLMENT);
		if(action.equalsIgnoreCase(DataTable.ACTION_ADD_SAVE) || action.equalsIgnoreCase(DataTable.ACTION_UPDATE_SAVE)) {
			enrollment.getSemester().getAcademicYear().setCode("047");
			enrollment.getStudent().setLastName(getRequestString("txtLastName"));
			enrollment.getStudent().setFirstName(getRequestString("txtFirstName"));
			enrollment.getStudent().setMiddleName(getRequestString("txtMiddleName"));
			enrollment.getStudent().setSuffixName(getRequestString("cboSuffixName"));
			
			if(getRequestInt("cboAcademicSection") > 0) {
				List<DTOBase> academicSectionList = (List<DTOBase>) getSessionAttribute(AcademicSectionDTO.SESSION_ACADEMIC_SECTION_LIST);
				enrollment.setAcademicSection((AcademicSectionDTO) DTOUtil.getObjById(academicSectionList, getRequestInt("cboAcademicSection")));
			}
			
			ContactDTO contact = ContactUtil.getContactByContactTypeCode(enrollment.getStudent().getContactList(), ContactTypeDTO.CONTACT_TYPE_PHONE_CODE);
			if(contact == null) {
				contact = new ContactDTO();
				contact.setEntity("USER");
			}
			contact.getContactType().setCode(ContactTypeDTO.CONTACT_TYPE_PHONE_CODE);
			contact.setDetails(getRequestString("txtContactCPNumber"));
			if(contact.getId() == 0) {
				enrollment.getStudent().getContactList().add(contact);
			}
			
			IdentificationDTO identification = IdentificationUtil.getIdentificationByType(enrollment.getStudent().getIdentificationList(), IdentificationDTO.TYPE_FACEKEEPER_ID);
			if(identification == null) {
				identification = new IdentificationDTO();
				identification.setType(IdentificationDTO.TYPE_FACEKEEPER_ID);
			}
			identification.setDetails(getRequestString("txtFaceKeeperID"));
			if(identification.getId() == 0) {
				enrollment.getStudent().getIdentificationList().add(identification);
			}
		}
	}
	protected void customAction(JSONObject jsonObj, String action) {
		if(action.equalsIgnoreCase("VIEW_QR")) {
//			EnrollmentDTO enrollment = (EnrollmentDTO) getSessionAttribute(EnrollmentDTO.SESSION_ENROLLMENT);
			List<DTOBase> studentList = (List<DTOBase>) getSessionAttribute(StudentDTO.SESSION_STUDENT_LIST);
//			List<DTOBase> enrollmentList = new EnrollmentDAO().getEnrollmentListByAcademicYearCode("047");
//			String dataTableId = getRequestString("txtDataTableId", true);
//			DataTable dataTable = (DataTable) request.getSession().getAttribute(dataTableId);
//			System.out.println("HEHE: " + dataTable);
//			StudentDTO studentSelected = (StudentDTO) dataTable.getSelectedRecord();
//			StudentDTO student1 = studentSelected.getStudent();
			StudentDTO student = (StudentDTO) DTOUtil.getObjByCode(studentList, getRequestString("txtObjCode"));
//			initDataTable(dataTable);
//			selectRecord(dataTable, student);
			
//			setSessionAttribute(EnrollmentDTO.SESSION_ENROLLMENT, enrollment);
			try {
				jsonObj.put(DTOBase.PAGE_CONTENT, PageUtil.getDataViewPage(sessionInfo, StudentUtil.getFaceKeeperID(student, sessionInfo), action));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			dataTableAction(sessionInfo, jsonObj, dataTable);
//			setSessionAttribute(StudentDTO.SESSION_STUDENT, student);
		}
	}
	protected void validateInput(JSONObject jsonObj, String action) {
		EnrollmentDTO enrollment = (EnrollmentDTO) getSessionAttribute(EnrollmentDTO.SESSION_ENROLLMENT);
		if(action.equalsIgnoreCase(DataTable.ACTION_ADD_SAVE) || action.equalsIgnoreCase(DataTable.ACTION_UPDATE_SAVE)) {
			if(StringUtil.isEmpty(enrollment.getStudent().getLastName())) {
				actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "Last Name");
			}
			else if(StringUtil.isEmpty(enrollment.getStudent().getFirstName())) {
				actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "First Name");
			}	
			else if(StringUtil.isEmpty(enrollment.getAcademicSection().getCode())) {
				actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "Section");
			}
//			else {
//				if(!StringUtil.isEmpty(enrollment.getStudent().getRfid())) {
//					UserDTO user = new UserDAO().getUserByRFID(enrollment.getStudent().getRfid());
//					if(user != null) {
//						if(action.equalsIgnoreCase(DataTable.ACTION_ADD_SAVE)) {
//							actionResponse.constructMessage(ActionResponse.TYPE_EXIST, "RFID");
//						}
//						else {
//							if(!user.getCode().equalsIgnoreCase(enrollment.getStudent().getCode())) {
//								actionResponse.constructMessage(ActionResponse.TYPE_EXIST, "RFID");
//							}
//						}
//					}
//				}
//			}
		}
	}
	
	protected void executeLogic(JSONObject jsonObj, DataTable dataTable, String action) {
		EnrollmentDTO enrollment = (EnrollmentDTO) getSessionAttribute(EnrollmentDTO.SESSION_ENROLLMENT);
		UploadedFile uploadedFile = (UploadedFile) getSessionAttribute(UploadedFile.SESSION_UPLOADED_FILE + "_0");
		List<DTOBase> academicSectionList = (List<DTOBase>) getSessionAttribute(AcademicSectionDTO.SESSION_ACADEMIC_SECTION_LIST);
		if(action.equalsIgnoreCase(DataTable.ACTION_ADD_VIEW)) {
			try {
				jsonObj.put(DTOBase.PAGE_CONTENT, PageUtil.getDataEntryPage(sessionInfo, StudentUtil.getDataEntryStr(sessionInfo, enrollment, academicSectionList, uploadedFile), action));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			setSessionAttribute(EnrollmentDTO.SESSION_ENROLLMENT, new EnrollmentDTO());
		}
		else if(action.equalsIgnoreCase(DataTable.ACTION_ADD_SAVE)) {
			enrollment.setAddedBy(sessionInfo.getCurrentUser().getCode());
			StudentWithEnrollmentDAO studentWithEnrollmentDAO = new StudentWithEnrollmentDAO();
			studentWithEnrollmentDAO.executeAdd(enrollment);
			actionResponse = (ActionResponse) studentWithEnrollmentDAO.getResult().get(ActionResponse.SESSION_ACTION_RESPONSE);
			if(StringUtil.isEmpty(actionResponse.getType())) {
				setSessionAttribute(StudentDTO.SESSION_STUDENT_LIST, new StudentDAO().getStudentList());
				setSessionAttribute(UserDTO.SESSION_USER_LIST, new UserDAO().getUserList());
				
				List<DTOBase> enrollmentList = new EnrollmentDAO().getEnrollmentListByAcademicYearCode("047");
				EnrollmentUtil.setEnrollmentListAcademicSection(enrollmentList, (List<DTOBase>) getSessionAttribute(AcademicSectionDTO.SESSION_ACADEMIC_SECTION_LIST));
				
				actionResponse.constructMessage(ActionResponse.TYPE_SUCCESS, "Added");
				File fileTo = new File(sessionInfo.getSettings().getStaticDir(true) + "/" + sessionInfo.getSettings().getCode() + "/user/" + enrollment.getStudent().getCode() + "." + FileUtil.getFileExtension(uploadedFile.getFile()));
				try {
					FileUtils.copyFile(uploadedFile.getFile(), fileTo);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					actionResponse.constructMessage(ActionResponse.TYPE_INFO, "Record was successfully saved but failed  to upload the picture.");
				}
			}
		}
		else if(action.equalsIgnoreCase(DataTable.ACTION_UPDATE_VIEW)) {
			StudentDTO studentSelected = (StudentDTO) dataTable.getSelectedRecord();
			StudentDTO student = studentSelected.getStudent();
			enrollment = EnrollmentUtil.getEnrollmentByStudentCode((List<DTOBase>) getSessionAttribute(EnrollmentDTO.SESSION_ENROLLMENT_LIST), student.getCode());
			enrollment.setStudent(student);
			StudentUtil.setStudent(student, (UserDTO) DTOUtil.getObjByCode((List<DTOBase>) getSessionAttribute(UserDTO.SESSION_USER_LIST), student.getCode()));
			uploadedFile.setFile(new File(sessionInfo.getSettings().getStaticDir(false) + "/" + sessionInfo.getSettings().getCode() + "/user/" + student.getCode() + ".png"));
			try {
				jsonObj.put(DTOBase.PAGE_CONTENT, PageUtil.getDataEntryPage(sessionInfo, StudentUtil.getDataEntryStr(sessionInfo, enrollment, academicSectionList, uploadedFile), action));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			setSessionAttribute(StudentDTO.SESSION_STUDENT, student);
		}
	}
	
	protected void search(JSONObject jsonObj, DataTable dataTable) {
		List<DTOBase> userList = (List<DTOBase>) getSessionAttribute(UserDTO.SESSION_USER_LIST);
		if(dataTable.getSearchCriteria().equalsIgnoreCase(StudentDTO.ACTION_SEARCH_BY_NAME)) {
			StudentUtil.searchByName(dataTable, dataTable.getSearchValue(), userList);
		}
	}
	
	protected void dataTableAction(SessionInfo sessionInfo, JSONObject jsonObj, DataTable dataTable) {
		initDataTable(dataTable);
		try {
			jsonObj.put(DTOBase.PAGE_CONTENT, PageUtil.getDataTablePage(StudentUtil.getDataGridStr(sessionInfo, dataTable, (List<DTOBase>) getSessionAttribute(EnrollmentDTO.SESSION_ENROLLMENT_LIST), (List<DTOBase>) getSessionAttribute(AcademicSectionDTO.SESSION_ACADEMIC_SECTION_LIST))));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void initDataTable(DataTable dataTable) {
		List<DTOBase> userList = (List<DTOBase>) getSessionAttribute(UserDTO.SESSION_USER_LIST);
		List<DTOBase> studentList = (List<DTOBase>) getSessionAttribute(StudentDTO.SESSION_STUDENT_LIST);
		List<DTOBase> contactList = (List<DTOBase>) getSessionAttribute(ContactDTO.SESSION_CONTACT_LIST);
		List<DTOBase> identificationList = (List<DTOBase>) getSessionAttribute(IdentificationDTO.SESSION_IDENTIFICATION_LIST);
		dataTable.setRecordList(studentList);
		dataTable.setCurrentPageRecordList();
		for(int row=0; row < dataTable.getRecordListCurrentPage().size(); row++) {			
			StudentDTO student = (StudentDTO) dataTable.getRecordListCurrentPage().get(row);
			UserDTO user = (UserDTO) DTOUtil.getObjByCode(userList, student.getCode());
			user.setContactList(ContactUtil.getContactListByCode(contactList, user.getCode()));
			user.setIdentificationList(IdentificationUtil.getIdentificationListByCode(identificationList, student.getCode()));
			StudentUtil.setStudent(student, user);
		}
	}
}
