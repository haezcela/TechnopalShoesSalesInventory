package com.laponhcet.admissionapplication;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.laponhcet.academicprogram.AcademicProgramDTO;
import com.laponhcet.semester.SemesterDTO;
import com.mytechnopal.ActionResponse;
import com.mytechnopal.UploadedFile;
import com.mytechnopal.base.ActionAjaxBase;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.dto.BarangayDTO;
import com.mytechnopal.dto.CityDTO;
import com.mytechnopal.dto.MobileProviderDTO;
import com.mytechnopal.dto.SHSStrandDTO;
import com.mytechnopal.link.LinkDTO;
import com.mytechnopal.util.BarangayUtil;
import com.mytechnopal.util.DTOUtil;
import com.mytechnopal.util.DateTimeUtil;
import com.mytechnopal.util.EMailUtil;
import com.mytechnopal.util.FileUtil;
import com.mytechnopal.util.MobileProviderUtil;
import com.mytechnopal.util.PageUtil;
import com.mytechnopal.util.StringUtil;

public class AdmissionApplicationActionAjax extends ActionAjaxBase {
		private static final long serialVersionUID = 1L;

		protected void customAction(JSONObject jsonObj, String action) {
			AdmissionApplicationDTO admissionApplication = (AdmissionApplicationDTO) getSessionAttribute(AdmissionApplicationDTO.SESSION_ADMISSION_APPLICATION);
			List<DTOBase> cityList = (List<DTOBase>) getSessionAttribute(CityDTO.SESSION_CITY_LIST);
			List<DTOBase> barangayList = (List<DTOBase>) getSessionAttribute(BarangayDTO.SESSION_BARANGAY_LIST);
			List<DTOBase> barangayListByCity = (List<DTOBase>) getSessionAttribute(BarangayDTO.SESSION_BARANGAY_LIST + "_BY_CITY");
			List<DTOBase> shsStrandList = (List<DTOBase>) getSessionAttribute(SHSStrandDTO.SESSION_SHS_STRAND_LIST);
			List<DTOBase> academicProgramList = (List<DTOBase>) getSessionAttribute(AcademicProgramDTO.SESSION_ACADEMIC_PROGRAM_LIST);
			List<DTOBase> semesterList = (List<DTOBase>) getSessionAttribute(SemesterDTO.SESSION_SEMESTER_LIST);
			
			UploadedFile uploadedFilePict = (UploadedFile) getSessionAttribute(UploadedFile.SESSION_UPLOADED_FILE + "_0");
			UploadedFile uploadedFileSignatureApplicant = (UploadedFile) getSessionAttribute(UploadedFile.SESSION_UPLOADED_FILE + "_1");
			UploadedFile uploadedFileSignatureGuardian = (UploadedFile) getSessionAttribute(UploadedFile.SESSION_UPLOADED_FILE + "_2");
			
			if(action.equalsIgnoreCase(AdmissionApplicationDTO.ACTION_PREPARE_APPLICATION)) {
				try {
					jsonObj.put(LinkDTO.PAGE_CONTENT, AdmissionApplicationUtil.getDataEntryStr(sessionInfo, admissionApplication, cityList, barangayListByCity, shsStrandList, academicProgramList, uploadedFilePict, uploadedFileSignatureApplicant, uploadedFileSignatureGuardian));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else if(action.equalsIgnoreCase(AdmissionApplicationDTO.ACTION_SELECT_ADDRESS_CITY)) {
				setInput();
				//admissionApplication.printContent();
				if(admissionApplication.getPermanentAddressCity().getId() > 0) {
					barangayListByCity = BarangayUtil.getBarangayListByCityCode(barangayList, admissionApplication.getPermanentAddressCity().getCode());
					setSessionAttribute(BarangayDTO.SESSION_BARANGAY_LIST + "_BY_CITY", barangayListByCity);
				}
				try {
					jsonObj.put(LinkDTO.PAGE_CONTENT, AdmissionApplicationUtil.getDataEntryStr(sessionInfo, admissionApplication, cityList, barangayListByCity, shsStrandList, academicProgramList, uploadedFilePict, uploadedFileSignatureApplicant, uploadedFileSignatureGuardian));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else if(action.equalsIgnoreCase(AdmissionApplicationDTO.ACTION_SUBMIT_APPLICATION)) {
				setInput();
				validateInput();
				if(StringUtil.isEmpty(actionResponse.getType())) {
					//Save data here
					saveApplication();
					if(StringUtil.isEmpty(actionResponse.getType())) {					
						File fileFromPict = new File(sessionInfo.getSettings().getStaticDir(true) + "/tmp/" + admissionApplication.getPict());
						File fileToPict = new File(sessionInfo.getSettings().getStaticDir(true) + "/" + sessionInfo.getSettings().getCode() + "/media/applicant_pict/" + admissionApplication.getPict());
						
						//System.out.println("from: " + fileFromPict.getAbsolutePath());
						//System.out.println("to: " + fileToPict.getAbsolutePath());
						
						try {
							FileUtils.copyFile(fileFromPict, fileToPict);
							FileUtil.setFileAccessRights(fileToPict);
							fileFromPict.delete(); 
						} catch (IOException e) {
							// TODO Auto-generated catch block
							actionResponse.constructMessage(ActionResponse.TYPE_INFO, "Record was successfully saved but failed  to upload the picture.  You may submit it to the guidance office on the day of your schedule");
						}
						
						if(StringUtil.isEmpty(actionResponse.getType())) {
							File fileFromSignatureApplicant = new File(sessionInfo.getSettings().getStaticDir(true) + "/tmp/" + admissionApplication.getSignatureApplicant());
							File fileToSignatureApplicant = new File(sessionInfo.getSettings().getStaticDir(true) + "/" + sessionInfo.getSettings().getCode() + "/media/applicant_signature/" + admissionApplication.getSignatureApplicant());
							try {
								FileUtils.copyFile(fileFromSignatureApplicant, fileToSignatureApplicant);
								FileUtil.setFileAccessRights(fileToSignatureApplicant);
								fileFromSignatureApplicant.delete(); 
							} catch (IOException e) {
								// TODO Auto-generated catch block
								actionResponse.constructMessage(ActionResponse.TYPE_INFO, "Record was successfully saved but failed  to upload the picture.  You may submit it to the guidance office on the day of your schedule");
							}
							
							if(StringUtil.isEmpty(actionResponse.getType())) {
								File fileFromSignatureGuardian = new File(sessionInfo.getSettings().getStaticDir(true) + "/tmp/" + admissionApplication.getSignatureGuardian());
								File fileToGuardianApplicant = new File(sessionInfo.getSettings().getStaticDir(true) + "/" + sessionInfo.getSettings().getCode() + "/media/guardian_signature/" + admissionApplication.getSignatureGuardian());
								try {
									FileUtils.copyFile(fileFromSignatureGuardian, fileToGuardianApplicant);
									FileUtil.setFileAccessRights(fileToGuardianApplicant);
									fileFromSignatureGuardian.delete(); 
								} catch (IOException e) {
									// TODO Auto-generated catch block
									actionResponse.constructMessage(ActionResponse.TYPE_INFO, "Record was successfully saved but failed  to upload the picture.  You may submit it to the guidance office on the day of your schedule");
								}
								
								if(StringUtil.isEmpty(actionResponse.getType())) {
									actionResponse.constructMessage(ActionResponse.TYPE_SUCCESS, "Submitted.  Please visit the page from time-to-time for the updates of your application");
								}
							}
						}
						
						try {
							jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataPrintPreview(sessionInfo, admissionApplication.getLastName() + ", " + admissionApplication.getFirstName(), AdmissionApplicationUtil.getDataViewStr(sessionInfo, admissionApplication)));

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						//Sending SMS
//						MessageSMSDTO messageSMS = new MessageSMSDTO();
//						messageSMS.setOwnerCode(sessionInfo.getSettings().getCode());
//						messageSMS.setCpNumber(admissionApplication.getCpNumber());
//						messageSMS.setMessage("BCC Admission Exam Application Advisory:We received your application. Your Reference Code is " + admissionApplication.getCode() + ". Use code to verify latest status at our website.");
//						messageSMS.setPriority(1);
//						messageSMS.setGroupNum(1);
//						messageSMS.setStatus(MessageSMSDTO.STATUS_PROCESSING);
//						MessageSMSDAO messageSMSDAO = new MessageSMSDAO();
//						messageSMSDAO.daoConnectorUtil.setDbSettings(new DBSettingsDTO(DBSettingsDTO.SERVER_SMS));
//						messageSMSDAO.executeAdd(messageSMS);
//						messageSMSDAO.daoConnectorUtil.setDbSettings(new DBSettingsDTO(DBSettingsDTO.SERVER_LOCAL));
						
						EMailUtil.sendMail("admission@bagocitycollege.com", "cypxuw-hezwof-6cynMi", "smtpout.secureserver.net", "465", new String[] {admissionApplication.getEmailAddress()}, "Bago City College Admission Application for AY 2025-2026 First Semester", "Dear " + admissionApplication.getFirstName() + ",<br><br>We received your application for data validation. We will notify you for any results of your application. Your Reference Code is " + admissionApplication.getCode() + ". Use this code to verify latest status at https://admission.bagocitycollege.com.<br><br><br>Bago City College Admin");

						
					}	
					else {
						actionResponse.constructMessage(ActionResponse.TYPE_FAIL, "Incomplete data. Please try again. If still persist please contact the Bago City College Guidance Office");
					}
				}
			}
		}
		
		protected void setInput() {
			AdmissionApplicationDTO admissionApplication = (AdmissionApplicationDTO) getSessionAttribute(AdmissionApplicationDTO.SESSION_ADMISSION_APPLICATION);
			List<DTOBase> cityList = (List<DTOBase>) getSessionAttribute(CityDTO.SESSION_CITY_LIST);
			List<DTOBase> barangayList = (List<DTOBase>) getSessionAttribute(BarangayDTO.SESSION_BARANGAY_LIST);
			List<DTOBase> academicProgramList = (List<DTOBase>) getSessionAttribute(AcademicProgramDTO.SESSION_ACADEMIC_PROGRAM_LIST);
			List<DTOBase> shsStrandList = (List<DTOBase>) getSessionAttribute(SHSStrandDTO.SESSION_SHS_STRAND_LIST);
			UploadedFile uploadedFilePict = (UploadedFile) getSessionAttribute(UploadedFile.SESSION_UPLOADED_FILE + "_0");
			UploadedFile uploadedFileSignatureApplicant = (UploadedFile) getSessionAttribute(UploadedFile.SESSION_UPLOADED_FILE + "_1");
			UploadedFile uploadedFileSignatureGuardian = (UploadedFile) getSessionAttribute(UploadedFile.SESSION_UPLOADED_FILE + "_2");
			
			admissionApplication.setLastName(getRequestString("txtLastName"));
			admissionApplication.setFirstName(getRequestString("txtFirstName"));
			admissionApplication.setMiddleName(getRequestString("txtMiddleName"));
			admissionApplication.setDateOfBirth(DateTimeUtil.getTimestamp(getRequestString("txtDateOfBirth"), "MM/dd/yyyy"));
			admissionApplication.setPlaceOfBirth(getRequestString("txtPlaceOfBirth"));
			admissionApplication.setGender(getRequestString("cboGender"));
			admissionApplication.setCivilStatus(getRequestString("cboCivilStatus"));
			
			int permanentAddressCityId = getRequestInt("cboPermanentAddressCity");
			if(permanentAddressCityId == 0) {
				admissionApplication.setPermanentAddressCity(new CityDTO());
			}
			else {
				admissionApplication.setPermanentAddressCity((CityDTO) DTOUtil.getObjById(cityList, permanentAddressCityId));
			}
			
			int permanentAddressBarangayId = getRequestInt("cboPermanentAddressBarangay");
			if(permanentAddressBarangayId == 0) {
				admissionApplication.setPermanentAddressBarangay(new BarangayDTO());
			}
			else {
				admissionApplication.setPermanentAddressBarangay((BarangayDTO) DTOUtil.getObjById(barangayList, permanentAddressBarangayId));
			}
			admissionApplication.setPermanentAddressDetails(getRequestString("txtPermanentAddressDetails"));
			
			admissionApplication.setEmailAddress(getRequestString("txtEmailAddress"));
			admissionApplication.setCpNumber(getRequestString("txtCPNumber"));
			
			admissionApplication.setLastSchoolAttendedName(getRequestString("txtLastSchoolAttendedName"));
			
			int shsStrandId = getRequestInt("cboSHSStrand");
			if(shsStrandId > 0) {
				admissionApplication.setShsStrand((SHSStrandDTO) DTOUtil.getObjById(shsStrandList, shsStrandId));
			}

			admissionApplication.setLastSchoolAttendedYear(getRequestInt("txtLastSchoolAttendedYear"));
			int lastSchoolAttendedCityId = getRequestInt("cboLastSchoolAttendedCity");
			if(lastSchoolAttendedCityId > 0) {
				admissionApplication.setLastSchoolAttendedCity((CityDTO) DTOUtil.getObjById(cityList, lastSchoolAttendedCityId));
			}
			admissionApplication.setApplicantType(getRequestString("cboApplicantType"));
			
			int academicProgramId = getRequestInt("cboAcademicProgram");
			if(academicProgramId > 0) {
				admissionApplication.setAcademicProgram((AcademicProgramDTO) DTOUtil.getObjById(academicProgramList, academicProgramId));
			}
			
			if(uploadedFilePict.getFile() != null) {
				admissionApplication.setPict(uploadedFilePict.getFile().getName());
			}
			if(uploadedFileSignatureApplicant.getFile() != null) {
				admissionApplication.setSignatureApplicant(uploadedFileSignatureApplicant.getFile().getName());
			}
			if(uploadedFileSignatureGuardian.getFile() != null) {
				admissionApplication.setSignatureGuardian(uploadedFileSignatureGuardian.getFile().getName());
			}
		}
		
		protected void validateInput() {
			AdmissionApplicationDTO admissionApplication = (AdmissionApplicationDTO) getSessionAttribute(AdmissionApplicationDTO.SESSION_ADMISSION_APPLICATION);
			List<DTOBase> mobileProviderList = (List<DTOBase>) getSessionAttribute(MobileProviderDTO.SESSION_MOBILE_PROVIDER_LIST);
			if(StringUtil.isEmpty(admissionApplication.getLastName())) {
				actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "Last Name");
			}
			else if(StringUtil.isEmpty(admissionApplication.getFirstName())) {
				actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "First Name");
			}
			else if(new AdmissionApplicationDAO().getAdmissionApplicationByLastNameFirstNameMiddleName(admissionApplication.getLastName(), admissionApplication.getFirstName(), admissionApplication.getMiddleName()) != null) {
				actionResponse.constructMessage(ActionResponse.TYPE_EXIST, "Last, First and Middle Name");
			}
			else if (admissionApplication.getDateOfBirth() == null) {
				actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "Date of Birth");
			}
			else if(DateTimeUtil.getAge(admissionApplication.getDateOfBirth()) < 16) {
				actionResponse.constructMessage(ActionResponse.TYPE_INVALID, "Your age is too young to enter College");
			}
			else if(StringUtil.isEmpty(admissionApplication.getPlaceOfBirth())) {
				actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "Place of Birth");
			}
			else if(StringUtil.isEmpty(admissionApplication.getGender())) {
				actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "Gender");
			}
			else if(StringUtil.isEmpty(admissionApplication.getCivilStatus())) {
				actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "Civil Status");
			}
			else if(StringUtil.isEmpty(admissionApplication.getPermanentAddressCity().getCode())) {
				actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "City Address");
			}
			else if(StringUtil.isEmpty(admissionApplication.getPermanentAddressBarangay().getCode())) {
				actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "Barangay Address");
			}
			else if(StringUtil.isEmpty(admissionApplication.getPermanentAddressDetails())) {
				actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "Purok/Street/Subdivision/Village");
			}
			else if(StringUtil.isEmpty(admissionApplication.getEmailAddress())) {
				actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "e-Mail Address");
			}
			else if (admissionApplication.getEmailAddress().split("@").length != 2) {
				actionResponse.constructMessage(ActionResponse.TYPE_INVALID, "Email Address");
			}
			else if(!StringUtil.isValidText(admissionApplication.getEmailAddress(), StringUtil.ALPHA_NUMERIC + "_@.")) {
				actionResponse.constructMessage(ActionResponse.TYPE_INVALID, "Email Address");
			}
			else if(new AdmissionApplicationDAO().getAdmissionApplicationByEmailAddress(admissionApplication.getEmailAddress()) != null) {
				actionResponse.constructMessage(ActionResponse.TYPE_EXIST, "Email Address");
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
			else if(StringUtil.isEmpty(admissionApplication.getLastSchoolAttendedName())) {
				actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "Last School Name");
			}
			else if(StringUtil.isEmpty(admissionApplication.getShsStrand().getCode())) {
				actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "SHS Strand");
			}
			else if(admissionApplication.getLastSchoolAttendedYear() >= DateTimeUtil.getCurrentYear() + 2) {
				actionResponse.constructMessage(ActionResponse.TYPE_INVALID, "Last School Attended Year");
			}
			else if(admissionApplication.getLastSchoolAttendedYear() <= DateTimeUtil.getCurrentYear()-20) {
				actionResponse.constructMessage(ActionResponse.TYPE_INVALID, "Last School Attended Year");
			}
			else if(StringUtil.isEmpty(admissionApplication.getLastSchoolAttendedCity().getCode())) {
				actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "Last School Attended City/Municipality");
			}
			else if(StringUtil.isEmpty(admissionApplication.getApplicantType())) {
				actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "Applicant Type");
			}
			else if(StringUtil.isEmpty(admissionApplication.getAcademicProgram().getCode())) {
				actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "Preferred Program/Course");
			}
			else if(StringUtil.isEmpty(admissionApplication.getPict())) {
				actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "ID Picture");
			}
			else if(StringUtil.isEmpty(admissionApplication.getSignatureApplicant())) {
				actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "Applicant Signature");
			}
			else if(StringUtil.isEmpty(admissionApplication.getSignatureGuardian())) {
				actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "Parent/Guardian Signature");
			}
		}
				
		protected void saveApplication() {
			AdmissionApplicationDTO admissionApplication = (AdmissionApplicationDTO) getSessionAttribute(AdmissionApplicationDTO.SESSION_ADMISSION_APPLICATION);
			admissionApplication.setCode(DateTimeUtil.getDateTimeToStr(DateTimeUtil.getCurrentTimestamp(), "YYYYMMddkkmmss") + StringUtil.getUniqueId(1, 1));
			admissionApplication.setStatus(AdmissionApplicationDTO.STATUS_SUBMITTED);
			admissionApplication.setAddedBy(sessionInfo.getCurrentUser().getCode());
			admissionApplication.setAddedTimestamp(DateTimeUtil.getCurrentTimestamp());
			AdmissionApplicationDAO admissionApplicationDAO = new AdmissionApplicationDAO();
			admissionApplicationDAO.executeAdd(admissionApplication);
			actionResponse = (ActionResponse) admissionApplicationDAO.getResult().get(ActionResponse.SESSION_ACTION_RESPONSE);
		}
	}