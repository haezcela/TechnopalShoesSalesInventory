package com.laponhcet.enrollment;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.laponhcet.academicsection.AcademicSectionDTO;
import com.laponhcet.student.StudentDAO;
import com.laponhcet.student.StudentDTO;
import com.laponhcet.student.StudentUtil;
import com.mytechnopal.usermedia.UserMediaDAO;
import com.mytechnopal.usermedia.UserMediaDTO;
import com.mytechnopal.usermedia.UserMediaUtil;
import com.mytechnopal.ActionResponse;
import com.mytechnopal.DataTable;
import com.mytechnopal.UploadedFile;
import com.mytechnopal.base.ActionAjaxBase;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.dao.UserDAO;
import com.mytechnopal.usercontact.UserContactDAO;
import com.mytechnopal.usercontact.UserContactDTO;
import com.mytechnopal.usercontact.UserContactUtil;
import com.mytechnopal.dto.MobileProviderDTO;
import com.mytechnopal.dto.UserDTO;
import com.mytechnopal.link.LinkDTO;
import com.mytechnopal.util.DTOUtil;
import com.mytechnopal.util.DateTimeUtil;
import com.mytechnopal.util.FileUtil;
import com.mytechnopal.util.MobileProviderUtil;
import com.mytechnopal.util.PageUtil;
import com.mytechnopal.util.StringUtil;
import com.mytechnopal.util.UploadedFileUtil;

public class EnrollmentActionAjax extends ActionAjaxBase {
	private static final long serialVersionUID = 1L;
	
	protected void setInput(String action) {
		EnrollmentDTO enrollment = (EnrollmentDTO) getSessionAttribute(EnrollmentDTO.SESSION_ENROLLMENT);
		enrollment.getStudent().setUserContactList(new ArrayList<DTOBase>());
		enrollment.getStudent().setUserMediaList(new ArrayList<DTOBase>());
		enrollment.getSemester().getAcademicYear().setCode("001");
		enrollment.getStudent().setLastName(getRequestString("txtLastName"));
		enrollment.getStudent().setFirstName(getRequestString("txtFirstName"));
		enrollment.getStudent().setMiddleName(getRequestString("txtMiddleName"));
		enrollment.getStudent().setSuffixName(getRequestString("cboSuffixName"));
		enrollment.getStudent().setLearnerReferenceNumber(getRequestString("txtLearnerReferenceNumber"));
		enrollment.getStudent().setGender(getRequestString("cboGender"));
		
		if(getRequestInt("cboAcademicSection") > 0) {
			List<DTOBase> academicSectionList = (List<DTOBase>) getSessionAttribute(AcademicSectionDTO.SESSION_ACADEMIC_SECTION_LIST);
			enrollment.setAcademicSection((AcademicSectionDTO) DTOUtil.getObjById(academicSectionList, getRequestInt("cboAcademicSection")));
		}
		enrollment.setStatus(getRequestString("cboStatus"));
		
		String contactName = getRequestString("txtContactName");
		String contact = getRequestString("txtContact");
		String contactRelationship = getRequestString("cboContactRelationship");

		if(!StringUtil.isEmpty(contact) ||!StringUtil.isEmpty(contactName)) {
			UserContactDTO userContact = new UserContactDTO();
			userContact.setContactName(contactName);
			userContact.setContactRelationship(contactRelationship);
			userContact.setContactType(UserContactDTO.CONTACT_TYPE_CELLPHONE);
			userContact.setContact(contact); 
			enrollment.getStudent().getUserContactList().add(userContact);	
		}
		
		UploadedFile uploadedFileIDPicture = (UploadedFile) getSessionAttribute(UploadedFile.SESSION_UPLOADED_FILE + "_0");
		if(uploadedFileIDPicture.getFile() != null) {
			UserMediaDTO userMediaIDPicture = new UserMediaDTO();
			userMediaIDPicture.setFilename(uploadedFileIDPicture.getFile().getName());
			userMediaIDPicture.setMediaType(UserMediaDTO.MEDIA_TYPE_ID_PICTURE);
			enrollment.getStudent().getUserMediaList().add(userMediaIDPicture);
		}
		
		UploadedFile uploadedFileSignature = (UploadedFile) getSessionAttribute(UploadedFile.SESSION_UPLOADED_FILE + "_1");
		if(uploadedFileSignature.getFile() != null) {
			UserMediaDTO userMediaSignature = new UserMediaDTO();
			userMediaSignature.setFilename(uploadedFileSignature.getFile().getName());
			userMediaSignature.setMediaType(UserMediaDTO.MEDIA_TYPE_SIGNATURE);
			enrollment.getStudent().getUserMediaList().add(userMediaSignature);
		}
	}
	
	protected void validateInput(String action) {
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
			else if(StringUtil.isEmpty(enrollment.getStudent().getGender())) {
				actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "Gender");
			}
			else {
				if(enrollment.getStudent().getUserContactList().size()>=1) {
					UserContactDTO userContact = (UserContactDTO) enrollment.getStudent().getUserContactList().get(0);
					if(StringUtil.isEmpty(userContact.getContact())) {
						if(!StringUtil.isEmpty(userContact.getContactName())) {
							 actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "Cellphone Number");
						}
					}
					else {
						List<DTOBase> mobileProviderList = (List<DTOBase>) getSessionAttribute(MobileProviderDTO.SESSION_MOBILE_PROVIDER_LIST);
						if(userContact.getContact().length() != 11) {
							actionResponse.constructMessage(ActionResponse.TYPE_INVALID, "Cellphone Number");
						}
						else if (!MobileProviderUtil.isValidCPNumber(mobileProviderList, userContact.getContact())) {
						    actionResponse.constructMessage(ActionResponse.TYPE_INVALID, "Cellphone Number");
						}
					}
				}
			}
		}
	}
	
	protected void executeLogic(JSONObject jsonObj, DataTable dataTable, String action) {
		List<DTOBase> academicSectionList = (List<DTOBase>) getSessionAttribute(AcademicSectionDTO.SESSION_ACADEMIC_SECTION_LIST);
		if(action.equalsIgnoreCase(DataTable.ACTION_VIEW)) {
			EnrollmentDTO enrollmentSelected = (EnrollmentDTO) dataTable.getSelectedRecord();
			//enrollment = enrollmentSelected.getEnrollment();
			try {
				jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataViewPage(sessionInfo, enrollmentSelected.getStudent().getLastName() + ", " + enrollmentSelected.getStudent().getFirstName(), EnrollmentUtil.getDataViewStr(sessionInfo, enrollmentSelected)));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		else if(action.equalsIgnoreCase(DataTable.ACTION_ADD_VIEW)) {
			UploadedFile uploadedFileIDPicture = new UploadedFile(); //no need to specify id for the default id is 0
			uploadedFileIDPicture.setSettings(sessionInfo.getSettings());
			uploadedFileIDPicture.setValidFileExt(new String[] {"png", "jpg"});
			uploadedFileIDPicture.setMaxSize(1024000); //1Mb 
			setSessionAttribute(UploadedFile.SESSION_UPLOADED_FILE + "_0", uploadedFileIDPicture);
			
			UploadedFile uploadedFileSignature = new UploadedFile(); 
			uploadedFileSignature.setId(1); //id is now specified
			uploadedFileSignature.setSettings(sessionInfo.getSettings());
			uploadedFileSignature.setValidFileExt(new String[] {"png", "jpg"});
			uploadedFileSignature.setMaxSize(1024000); //1Mb 
			setSessionAttribute(UploadedFile.SESSION_UPLOADED_FILE + "_1", uploadedFileSignature);	
			
			EnrollmentDTO enrollment = new EnrollmentDTO();
			try {
				jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataEntryPage(sessionInfo, sessionInfo.getCurrentLink().getLabel() + " - NEW", EnrollmentUtil.getDataEntryStr(sessionInfo, enrollment, academicSectionList, uploadedFileIDPicture, uploadedFileSignature)));
			} catch (JSONException e) {
				e.printStackTrace();
			}
			setSessionAttribute(EnrollmentDTO.SESSION_ENROLLMENT, enrollment);
		}
		else if(action.equalsIgnoreCase(DataTable.ACTION_UPDATE_VIEW)) {
			EnrollmentDTO enrollmentSelected = (EnrollmentDTO) dataTable.getSelectedRecord();
			EnrollmentDTO enrollment = enrollmentSelected.getEnrollment();
			
			//User Media
			List<DTOBase> userMediaList = (List<DTOBase>) getSessionAttribute(UserMediaDTO.SESSION_USERMEDIA_LIST);
			UserMediaDTO userMediaIDPict = UserMediaUtil.getUserMediaByUserCodeMediaType(userMediaList, enrollment.getStudent().getCode(), UserMediaDTO.MEDIA_TYPE_ID_PICTURE);
			UserMediaDTO userMediaSignature = UserMediaUtil.getUserMediaByUserCodeMediaType(userMediaList, enrollment.getStudent().getCode(), UserMediaDTO.MEDIA_TYPE_SIGNATURE);

			UploadedFile uploadedFileIDPicture = new UploadedFile(); 
			if(userMediaIDPict != null) {
				File file = new File("/static/" + sessionInfo.getSettings().getCode() + "/media/id_pict/" + userMediaIDPict.getFilename());
				uploadedFileIDPicture.setFile(file);
			}

			uploadedFileIDPicture.setSettings(sessionInfo.getSettings());
			uploadedFileIDPicture.setValidFileExt(new String[] {"png", "jpg"});
			uploadedFileIDPicture.setMaxSize(1024000); //1Mb 
			setSessionAttribute(UploadedFile.SESSION_UPLOADED_FILE + "_0", uploadedFileIDPicture.getUploadedFile());
			
			UploadedFile uploadedFileSignature = new UploadedFile(); 
			if(userMediaSignature != null) {
				File file = new File("/static/" + sessionInfo.getSettings().getCode() + "/media/signature/" + userMediaSignature.getFilename());
				uploadedFileSignature.setFile(file);
			}
			uploadedFileSignature.setId(1); //id is now specified
			uploadedFileSignature.setSettings(sessionInfo.getSettings());
			uploadedFileSignature.setValidFileExt(new String[] {"png", "jpg"});
			uploadedFileSignature.setMaxSize(1024000); //1Mb 
			setSessionAttribute(UploadedFile.SESSION_UPLOADED_FILE + "_1", uploadedFileSignature.getUploadedFile());	
			
			setSessionAttribute(UploadedFile.SESSION_UPLOADED_FILE + "_0_ORIG", uploadedFileIDPicture);
			setSessionAttribute(UploadedFile.SESSION_UPLOADED_FILE + "_1_ORIG", uploadedFileSignature);
			//User Media
			
			try {
				jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataEntryPage(sessionInfo, sessionInfo.getCurrentLink().getLabel() + " - UPDATE", EnrollmentUtil.getDataEntryStr(sessionInfo, enrollment, academicSectionList, uploadedFileIDPicture, uploadedFileSignature)));
			} catch (JSONException e) {
				e.printStackTrace();
			}
			setSessionAttribute(EnrollmentDTO.SESSION_ENROLLMENT, enrollment);
		}
		else if(action.equalsIgnoreCase(DataTable.ACTION_DELETE_VIEW)) {
			EnrollmentDTO enrollment = (EnrollmentDTO) dataTable.getSelectedRecord();
			try {
				jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataViewPage(sessionInfo, sessionInfo.getCurrentLink().getLabel() + " - DELETE", EnrollmentUtil.getDataViewStr(sessionInfo, enrollment)));
			} catch (JSONException e) {
				e.printStackTrace();
			}
			setSessionAttribute(EnrollmentDTO.SESSION_ENROLLMENT, enrollment);
		}
		else if(action.equalsIgnoreCase(DataTable.ACTION_ADD_SAVE)) {
			EnrollmentDTO enrollment = (EnrollmentDTO) getSessionAttribute(EnrollmentDTO.SESSION_ENROLLMENT);
			enrollment.setAddedBy(sessionInfo.getCurrentUser().getCode());
			EnrollmentDAO enrollmentDAO = new EnrollmentDAO();
			enrollmentDAO.executeAdd(enrollment);
			actionResponse = (ActionResponse) enrollmentDAO.getResult().get(ActionResponse.SESSION_ACTION_RESPONSE);
			if(StringUtil.isEmpty(actionResponse.getType())) {
				UploadedFile uploadedFileIDPicture = (UploadedFile) getSessionAttribute(UploadedFile.SESSION_UPLOADED_FILE + "_0");
				if(uploadedFileIDPicture.getFile() != null) {
					File fileFromIDPicture = new File(sessionInfo.getSettings().getStaticDir(true) + "/tmp/" + uploadedFileIDPicture.getFile().getName());
					File fileToIDPicture = new File(sessionInfo.getSettings().getStaticDir(true) + "/" + sessionInfo.getSettings().getCode() + "/media/id_pict/" + uploadedFileIDPicture.getFile().getName());
					try {
						FileUtils.copyFile(fileFromIDPicture, fileToIDPicture);
						FileUtil.setFileAccessRights(fileToIDPicture);
						fileFromIDPicture.delete(); 
					} catch (IOException e) {
						// TODO Auto-generated catch block
						actionResponse.constructMessage(ActionResponse.TYPE_INFO, "Record was successfully saved but failed  to upload the picture.");
					}
				}
				
				if(StringUtil.isEmpty(actionResponse.getType())) {
					UploadedFile uploadedFileSignature = (UploadedFile) getSessionAttribute(UploadedFile.SESSION_UPLOADED_FILE + "_1");
					if(uploadedFileSignature.getFile() != null) {
						File fileFromSignature = new File(sessionInfo.getSettings().getStaticDir(true) + "/tmp/" + uploadedFileSignature.getFile().getName());
						File fileToSignature = new File(sessionInfo.getSettings().getStaticDir(true) + "/" + sessionInfo.getSettings().getCode() + "/media/signature/" + uploadedFileSignature.getFile().getName());
						try {
							FileUtils.copyFile(fileFromSignature, fileToSignature);
							FileUtil.setFileAccessRights(fileToSignature);
							fileFromSignature.delete(); 
						} catch (IOException e) {
							// TODO Auto-generated catch block
							actionResponse.constructMessage(ActionResponse.TYPE_INFO, "Record was successfully saved but failed  to upload the picture.");
						}
					}
				}
				
				actionResponse.constructMessage(ActionResponse.TYPE_SUCCESS, "Added");
				setSessionAttribute(EnrollmentDTO.SESSION_ENROLLMENT, new EnrollmentDTO());
				setSessionAttribute(UserDTO.SESSION_USER_LIST, new UserDAO().getUserList());
				setSessionAttribute(EnrollmentDTO.SESSION_ENROLLMENT_LIST, new EnrollmentDAO().getEnrollmentListByAcademicYearCode("001"));
				setSessionAttribute(StudentDTO.SESSION_STUDENT_LIST, new StudentDAO().getStudentList());
				setSessionAttribute(UserContactDTO.SESSION_USERCONTACT_LIST, new UserContactDAO().getUserContactList());
				setSessionAttribute(UserMediaDTO.SESSION_USERMEDIA_LIST, new UserMediaDAO().getUserMediaList());
			}
		}
		else if(action.equalsIgnoreCase(DataTable.ACTION_UPDATE_SAVE)) {
			EnrollmentDTO enrollment = (EnrollmentDTO) getSessionAttribute(EnrollmentDTO.SESSION_ENROLLMENT);
			enrollment.setUpdatedBy(sessionInfo.getCurrentUser().getCode());
			enrollment.setUpdatedTimestamp(DateTimeUtil.getCurrentTimestamp());

			EnrollmentDAO enrollmentDAO = new EnrollmentDAO();
			enrollmentDAO.executeUpdate(enrollment);
			actionResponse = (ActionResponse) enrollmentDAO.getResult().get(ActionResponse.SESSION_ACTION_RESPONSE);
			if(StringUtil.isEmpty(actionResponse.getType())) {
				UploadedFile uploadedFileIDPictureOrig = (UploadedFile) getSessionAttribute(UploadedFile.SESSION_UPLOADED_FILE + "_0_ORIG");
				UploadedFile uploadedFileIDPicture = (UploadedFile) getSessionAttribute(UploadedFile.SESSION_UPLOADED_FILE + "_0");
				String mediaIDPictStaticPath = sessionInfo.getSettings().getCode() + "/media/id_pict/";
				
				String status = UploadedFileUtil.getUploadedFileStatus(sessionInfo, uploadedFileIDPictureOrig, uploadedFileIDPicture, mediaIDPictStaticPath);
				if(status.equalsIgnoreCase("error")) {
					actionResponse.constructMessage(ActionResponse.TYPE_INFO, "Record was successfully saved but failed to upload the picture.");
				}
				else {
					UploadedFile uploadedFileSignatureOrig = (UploadedFile) getSessionAttribute(UploadedFile.SESSION_UPLOADED_FILE + "_1_ORIG");
					UploadedFile uploadedFileSignature = (UploadedFile) getSessionAttribute(UploadedFile.SESSION_UPLOADED_FILE + "_1");
					String mediaSignatureStaticPath = sessionInfo.getSettings().getCode() + "/media/signature/";
					status = UploadedFileUtil.getUploadedFileStatus(sessionInfo, uploadedFileSignatureOrig, uploadedFileSignature, mediaSignatureStaticPath);
					if(status.equalsIgnoreCase("error")) {
						actionResponse.constructMessage(ActionResponse.TYPE_INFO, "Record was successfully saved but failed to upload the picture.");
					}
				}
				
				if(StringUtil.isEmpty(actionResponse.getType())) {
					actionResponse.constructMessage(ActionResponse.TYPE_SUCCESS, "Updated");
					setSessionAttribute(EnrollmentDTO.SESSION_ENROLLMENT, new EnrollmentDTO());
					setSessionAttribute(UserDTO.SESSION_USER_LIST, new UserDAO().getUserList());
					setSessionAttribute(EnrollmentDTO.SESSION_ENROLLMENT_LIST, new EnrollmentDAO().getEnrollmentListByAcademicYearCode("001"));
					setSessionAttribute(StudentDTO.SESSION_STUDENT_LIST, new StudentDAO().getStudentList());
					setSessionAttribute(UserContactDTO.SESSION_USERCONTACT_LIST, new UserContactDAO().getUserContactList());
					setSessionAttribute(UserMediaDTO.SESSION_USERMEDIA_LIST, new UserMediaDAO().getUserMediaList());
				}
			}
		}
		else if(action.equalsIgnoreCase(DataTable.ACTION_DELETE)) {
			EnrollmentDTO enrollmentSelected = (EnrollmentDTO) dataTable.getSelectedRecord();
			EnrollmentDAO enrollmentDAO = new EnrollmentDAO();
			enrollmentDAO.executeDelete(enrollmentSelected);
			actionResponse = (ActionResponse) enrollmentDAO.getResult().get(ActionResponse.SESSION_ACTION_RESPONSE);
			if(StringUtil.isEmpty(actionResponse.getType())) {
				actionResponse.constructMessage(ActionResponse.TYPE_SUCCESS, "Deleted");
				setSessionAttribute(EnrollmentDTO.SESSION_ENROLLMENT, new EnrollmentDTO());
				setSessionAttribute(UserDTO.SESSION_USER_LIST, new UserDAO().getUserList());
				setSessionAttribute(EnrollmentDTO.SESSION_ENROLLMENT_LIST, new EnrollmentDAO().getEnrollmentListByAcademicYearCode("001"));
				setSessionAttribute(StudentDTO.SESSION_STUDENT_LIST, new StudentDAO().getStudentList());
				setSessionAttribute(UserContactDTO.SESSION_USERCONTACT_LIST, new UserContactDAO().getUserContactList());
				setSessionAttribute(UserMediaDTO.SESSION_USERMEDIA_LIST, new UserMediaDAO().getUserMediaList());
			}
		}
//		else if(action.equalsIgnoreCase(DataTable.ACTION_SORT)) {
//			String sortType = getRequestString("txtSortType");
//			int column = getRequestInt("txtColumn");
//			if(column == 1) {
//				if(sortType.equalsIgnoreCase(DataTable.SORT_TYPE_ASCENDING)) { 
//					new SortLastNameAscending().sort(dataTable.getRecordList()); 
//				} 
//				else if(sortType.equalsIgnoreCase(DataTable.SORT_TYPE_DESCENDING)) { 
//					new SortLastNameDescending().sort(dataTable.getRecordList()); 
//				}
//			}
//			else if(column == 2) {
//				if(sortType.equalsIgnoreCase(DataTable.SORT_TYPE_ASCENDING)) { 
//					new SortFirstNameAscending().sort(dataTable.getRecordList()); 
//				} 
//				else if(sortType.equalsIgnoreCase(DataTable.SORT_TYPE_DESCENDING)) { 
//					new SortFirstNameDescending().sort(dataTable.getRecordList()); 
//				}
//			}
//		}
	}
	
	protected void dataTableAction(JSONObject jsonObj, DataTable dataTable) {
		initDataTable(dataTable);
		try {
			jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataTablePage(sessionInfo, EnrollmentUtil.getDataTableStr(sessionInfo, dataTable)));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	protected void search(DataTable dataTable) {
		if(dataTable.getSearchCriteria().equalsIgnoreCase(EnrollmentDTO.ACTION_SEARCH_BY_NAME)) {
			List<DTOBase> userList = (List<DTOBase>) getSessionAttribute(UserDTO.SESSION_USER_LIST);
			EnrollmentUtil.searchByName(dataTable, dataTable.getSearchValue(), userList);
		}
		else if(dataTable.getSearchCriteria().equalsIgnoreCase(EnrollmentDTO.ACTION_SEARCH_BY_LASTNAME)) {
			List<DTOBase> userList = (List<DTOBase>) getSessionAttribute(UserDTO.SESSION_USER_LIST);
			EnrollmentUtil.searchByLastName(dataTable, dataTable.getSearchValue(), userList);
		}
		else if(dataTable.getSearchCriteria().equalsIgnoreCase(EnrollmentDTO.ACTION_SEARCH_BY_USER_CODE)) {
			EnrollmentUtil.searchByUserCode(dataTable, dataTable.getSearchValue());
		}
	}
	
	protected void initDataTable(DataTable dataTable) {
		List<DTOBase> enrollmentList = (List<DTOBase>) getSessionAttribute(EnrollmentDTO.SESSION_ENROLLMENT_LIST);
		List<DTOBase> userList = (List<DTOBase>) getSessionAttribute(UserDTO.SESSION_USER_LIST);
		List<DTOBase> studentList = (List<DTOBase>) getSessionAttribute(StudentDTO.SESSION_STUDENT_LIST);
		List<DTOBase> academicSectionList = (List<DTOBase>) getSessionAttribute(AcademicSectionDTO.SESSION_ACADEMIC_SECTION_LIST);
		List<DTOBase> userContactList = (List<DTOBase>) getSessionAttribute(UserContactDTO.SESSION_USERCONTACT_LIST);
		List<DTOBase> userMediaList = (List<DTOBase>) getSessionAttribute(UserMediaDTO.SESSION_USERMEDIA_LIST);

		dataTable.setRecordList(enrollmentList);
		dataTable.setCurrentPageRecordList();
		for(int row=0; row < dataTable.getRecordListCurrentPage().size(); row++) {			
			EnrollmentDTO enrollment = (EnrollmentDTO) dataTable.getRecordListCurrentPage().get(row);
			enrollment.setStudent((StudentDTO) DTOUtil.getObjByCode(studentList, enrollment.getStudent().getCode()));
			StudentUtil.setStudent(enrollment.getStudent(), (UserDTO) DTOUtil.getObjByCode(userList, enrollment.getStudent().getCode()));
			enrollment.setAcademicSection((AcademicSectionDTO) DTOUtil.getObjByCode(academicSectionList, enrollment.getAcademicSection().getCode()));
			enrollment.getStudent().setUserContactList(UserContactUtil.getUserContactListByUserCode(userContactList, enrollment.getStudent().getCode()));
			enrollment.getStudent().setUserMediaList(UserMediaUtil.getUserMediaListByUserCode(userMediaList, enrollment.getStudent().getCode()));
		}
		new SortLastNameAscending().sort(enrollmentList);
	}
}
