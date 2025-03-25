package com.laponhcet.guest;

import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.laponhcet.academicprogram.AcademicProgramDTO;
import com.laponhcet.admissionapplication.AdmissionApplicationDAO;
import com.laponhcet.admissionapplication.AdmissionApplicationDTO;
import com.laponhcet.admissionapplication.AdmissionApplicationUtil;
import com.mytechnopal.ActionResponse;
import com.mytechnopal.banner.BannerUtil;
import com.mytechnopal.base.ActionAjaxBase;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.dao.UserDAO;
import com.mytechnopal.dto.BarangayDTO;
import com.mytechnopal.dto.CityDTO;
import com.mytechnopal.dto.MobileProviderDTO;
import com.mytechnopal.dto.SHSStrandDTO;
import com.mytechnopal.dto.UserDTO;
import com.mytechnopal.link.LinkDTO;
import com.mytechnopal.linkuser.LinkUserDAO;
import com.mytechnopal.linkuser.LinkUserUtil;
import com.mytechnopal.usercontact.UserContactDAO;
import com.mytechnopal.usermedia.UserMediaDAO;
import com.mytechnopal.util.DTOUtil;
import com.mytechnopal.util.DateTimeUtil;
import com.mytechnopal.util.MobileProviderUtil;
import com.mytechnopal.util.PageUtil;
import com.mytechnopal.util.StringUtil;

public class HomeActionAjax extends ActionAjaxBase {
	private static final long serialVersionUID = 1L;

	protected void customAction(JSONObject jsonObj, String action) {
		//System.out.println("inside HomeActionAjax action: " + action);
		if(action.equalsIgnoreCase(UserDTO.ACTION_LOGIN)) {
			UserDTO user = new UserDTO();
			String userName = getRequestString("txtUserName");
			String password = getRequestString("txtPassword");
			
			if(StringUtil.isEmpty(userName)) {
				actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, new String[]{"User Name"});
			}
			else if(StringUtil.isEmpty(password)) {
				actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, new String[]{"Password"});
			}
			else {
				UserDAO userDAO = new UserDAO();
				user = userDAO.getUserByUserNamePassword(userName, password);
				if(user == null) {
					actionResponse.constructMessage(ActionResponse.TYPE_FAIL, new String[]{"Wrong Combination of user name and password."});
				}
				else {
					if(user.isActive()) {
						LinkDTO link = (LinkDTO) DTOUtil.getObjByCode(sessionInfo.getLinkList(), LinkDTO.USER_HOME);
						user.setUserMediaList(new UserMediaDAO().getUserMediaListByUserCode(user.getCode()));
						user.setUserContactList(new UserContactDAO().getUserContactListByUserCode(user.getCode()));
						resetSessionInfo(link, user, link);		
						user.setLinkUserList(new LinkUserDAO().getLinkUserListByUserCode(user.getCode()));
						LinkUserUtil.setLinkUserList(user.getLinkUserList(), sessionInfo.getLinkList());
						//user.setAddedTimestamp(DateTimeUtil.getCurrentTimestamp());
//						try {
//							user.setSourceDeviceInfo(InetAddress.getLocalHost().toString());
//						} catch (UnknownHostException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
						//new UserLogDAO().executeAdd(user);
						
						
						//new HomeAction().executeAction(request, response, sessionInfo);
						
//						setSessionAttribute(AdmissionApplicationDTO.SESSION_ADMISSION_APPLICATION_LIST, jsonObj);
//						
//						try {
//							jsonObj.put(LinkDTO.PAGE_CONTENT, HomeUtil.getHomeStr(sessionInfo));
//						} catch (JSONException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
					}
					else {
						actionResponse.constructMessage(ActionResponse.TYPE_FAIL, new String[]{"Your profile is already inactive.  Please contact the administrator"});
					}
				}
			}
		}
		else if(action.equalsIgnoreCase("ViewAdmissionApplication")) {
			String admissionApplicationCode = getRequestString("txtAdmissionApplicationCode");
			AdmissionApplicationDTO admissionApplication = new AdmissionApplicationDAO().getAdmissionApplicationByCode(admissionApplicationCode);
			if(admissionApplication == null) {
				actionResponse.constructMessage(ActionResponse.TYPE_FAIL, "Invalid Reference Code or Date of Birth");
			}
			else {
				String dateOfBirthStr = getRequestString("txtDateOfBirth");
				if(DateTimeUtil.getDateTimeToStr(admissionApplication.getDateOfBirth(), "MMddyyyy").equalsIgnoreCase(dateOfBirthStr)) {
					List<DTOBase> cityList = (List<DTOBase>) getSessionAttribute(CityDTO.SESSION_CITY_LIST);
					List<DTOBase> barangayList = (List<DTOBase>) getSessionAttribute(BarangayDTO.SESSION_BARANGAY_LIST);
					List<DTOBase> shsStrandList = (List<DTOBase>) getSessionAttribute(SHSStrandDTO.SESSION_SHS_STRAND_LIST);
					List<DTOBase> academicProgramList = (List<DTOBase>) getSessionAttribute(AcademicProgramDTO.SESSION_ACADEMIC_PROGRAM_LIST);
					
					admissionApplication.setPermanentAddressCity((CityDTO) DTOUtil.getObjByCode(cityList, admissionApplication.getPermanentAddressCity().getCode()));
					admissionApplication.setPermanentAddressBarangay((BarangayDTO) DTOUtil.getObjByCode(barangayList, admissionApplication.getPermanentAddressBarangay().getCode()));
					admissionApplication.setLastSchoolAttendedCity((CityDTO) DTOUtil.getObjByCode(cityList, admissionApplication.getLastSchoolAttendedCity().getCode()));
					admissionApplication.setShsStrand((SHSStrandDTO) DTOUtil.getObjByCode(shsStrandList, admissionApplication.getShsStrand().getCode()));
					admissionApplication.setAcademicProgram((AcademicProgramDTO) DTOUtil.getObjByCode(academicProgramList, admissionApplication.getAcademicProgram().getCode()));

					try {
						jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataPrintPreview(sessionInfo, "", AdmissionApplicationUtil.getDataViewStr(sessionInfo, admissionApplication)));
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				else {
					actionResponse.constructMessage(ActionResponse.TYPE_FAIL, "Invalid Reference or Date of Birth");
				}
			}
		}
		else if(action.equalsIgnoreCase("SubmitEnrollmentApplication")) {
			List<DTOBase> academicProgramList = (List<DTOBase>) getSessionAttribute(AcademicProgramDTO.SESSION_ACADEMIC_PROGRAM_LIST);
			List<DTOBase> mobileProviderList = (List<DTOBase>) getSessionAttribute(MobileProviderDTO.SESSION_MOBILE_PROVIDER_LIST);
			AdmissionApplicationDTO admissionApplication = new AdmissionApplicationDTO();
			
			admissionApplication.setLastName(getRequestString("txtLastName"));
			admissionApplication.setFirstName(getRequestString("txtFirstName"));
			admissionApplication.setMiddleName(getRequestString("txtMiddleName"));
			admissionApplication.setLrn(getRequestString("txtLRN"));
			admissionApplication.setCpNumber(getRequestString("txtCPNumber"));
			admissionApplication.setEmailAddress(getRequestString("txtEmailAddress"));
			admissionApplication.setDateOfBirth(DateTimeUtil.getTimestamp(getRequestString("txtDateOfBirth"), "MM/dd/yyyy"));
			int academicProgramId = getRequestInt("cboAcademicProgram");
			if(academicProgramId > 0) {
				admissionApplication.setAcademicProgram((AcademicProgramDTO) DTOUtil.getObjById(academicProgramList, academicProgramId));
			}
			
			admissionApplication.printContent();
			
			if(StringUtil.isEmpty(admissionApplication.getLastName())) {
				actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "Last Name");
			}
			else if(StringUtil.isEmpty(admissionApplication.getFirstName())) {
				actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "First Name");
			}
			else if(new AdmissionApplicationDAO().getAdmissionApplicationByLastNameFirstNameMiddleName(admissionApplication.getLastName(), admissionApplication.getFirstName(), admissionApplication.getMiddleName()) != null) {
				actionResponse.constructMessage(ActionResponse.TYPE_EXIST, "Last, First and Middle Name");
			}
			else if(StringUtil.isEmpty(admissionApplication.getLrn())) {
				actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "LRN");
			}
			else if(StringUtil.isEmpty(admissionApplication.getCpNumber())) {
				actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "Cellphone Number");
			}
			else if(admissionApplication.getCpNumber().length() != 11) {
				actionResponse.constructMessage(ActionResponse.TYPE_INVALID, "Cellphone Number");
			}
			else if (!MobileProviderUtil.isValidCPNumber(mobileProviderList, getRequestString("txtCPNumber"))) {
			    actionResponse.constructMessage(ActionResponse.TYPE_INVALID, "Cellphone Number");
			}
			else if(new AdmissionApplicationDAO().getAdmissionApplicationByCPNumber(admissionApplication.getCpNumber()) != null) {
				actionResponse.constructMessage(ActionResponse.TYPE_EXIST, "Cellphone Number");
			}
			else if (admissionApplication.getDateOfBirth() == null) {
				actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "Date of Birth");
			}
			else if(StringUtil.isEmpty(admissionApplication.getAcademicProgram().getCode())) {
				actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "Enrolling For");
			}
			
			if(StringUtil.isEmpty(actionResponse.getType())) {
				admissionApplication.setCode(DateTimeUtil.getDateTimeToStr(DateTimeUtil.getCurrentTimestamp(), "YYYYMMddkkmmss") + StringUtil.getUniqueId(1, 1));
				admissionApplication.setAddedBy(sessionInfo.getCurrentUser().getCode());
				admissionApplication.setAddedTimestamp(DateTimeUtil.getCurrentTimestamp());
				AdmissionApplicationDAO admissionApplicationDAO = new AdmissionApplicationDAO();
				admissionApplicationDAO.executeAdd(admissionApplication);
				actionResponse = (ActionResponse) admissionApplicationDAO.getResult().get(ActionResponse.SESSION_ACTION_RESPONSE);
				
				if(StringUtil.isEmpty(actionResponse.getType())) {
					actionResponse.constructMessage(ActionResponse.TYPE_SUCCESS, "Submitted.  Please visit this page from time-to-time for enrollment updates");
				}
			}
		}
	}
}