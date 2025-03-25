package com.laponhcet.enrollment;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.laponhcet.academicsection.AcademicSectionDTO;
import com.laponhcet.academicsection.AcademicSectionUtil;
import com.laponhcet.advisory.AdvisoryDTO;
import com.laponhcet.advisory.AdvisoryUtil;
import com.laponhcet.student.StudentDTO;
import com.laponhcet.student.StudentUtil;
import com.mytechnopal.Calendar;
import com.mytechnopal.DataTable;
import com.mytechnopal.SessionInfo;
import com.mytechnopal.UploadedFile;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.base.WebControlBase;
import com.mytechnopal.dto.UserDTO;
import com.mytechnopal.facelog.FaceLogDAO;
import com.mytechnopal.facelog.FaceLogDTO;
import com.mytechnopal.telegram.TelegramSettingsDAO;
import com.mytechnopal.telegram.TelegramSettingsDTO;
import com.mytechnopal.usercontact.UserContactDTO;
import com.mytechnopal.usercontact.UserContactUtil;
import com.mytechnopal.util.AESUtil;
import com.mytechnopal.util.DTOUtil;
import com.mytechnopal.util.DateTimeUtil;
import com.mytechnopal.util.QRCodeUtil;
import com.mytechnopal.util.StringUtil;
import com.mytechnopal.util.WebUtil;
import com.mytechnopal.webcontrol.DataTableWebControl;
import com.mytechnopal.webcontrol.FileInputWebControl;
import com.mytechnopal.webcontrol.SelectWebControl;
import com.mytechnopal.webcontrol.TextBoxWebControl;

public class EnrollmentUtil implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static void setEnrollment(EnrollmentDTO enrollment, List<DTOBase> userList, List<DTOBase> academicSectionList) {
		UserDTO user = (UserDTO) DTOUtil.getObjByCode(userList, enrollment.getStudent().getCode());
		StudentUtil.setStudent(enrollment.getStudent(), user);
		enrollment.setAcademicSection((AcademicSectionDTO) DTOUtil.getObjByCode(academicSectionList, enrollment.getAcademicSection().getCode()));
	}
	
	public static String getDataViewStr(SessionInfo sessionInfo, EnrollmentDTO enrollment) {
		StringBuffer strBuff = new StringBuffer();
		String qrCode = QRCodeUtil.generateQRCodeBase64(enrollment.getStudent().getCode(), 200, 200);
		strBuff.append("<div class='col-lg-12'>Student: " + enrollment.getStudent().getName(false, false, false));
		strBuff.append("	<br><img src='" + qrCode  + "'>");
		strBuff.append("</div>");
		return strBuff.toString();
	}
	
	public static String getDataEntryStr(SessionInfo sessionInfo, EnrollmentDTO enrollment, List<DTOBase> academicSectionList, UploadedFile uploadedFileIDPicture, UploadedFile uploadedFileSignature) {		
		UserContactDTO userContact = new UserContactDTO();
		if(enrollment.getStudent().getUserContactList().size() >=1 ) {
			userContact = (UserContactDTO) UserContactUtil.getUserContactByContactType(enrollment.getStudent().getUserContactList(), UserContactDTO.CONTACT_TYPE_CELLPHONE);
		}
		
		StringBuffer strBuff = new StringBuffer();
		
		strBuff.append(new TextBoxWebControl().getTextBoxWebControl("form-group col-lg-3", true, "Last Name", "Last Name", "LastName", enrollment.getStudent().getLastName(), 45, WebControlBase.DATA_TYPE_STRING, ""));
		strBuff.append(new TextBoxWebControl().getTextBoxWebControl("form-group col-lg-4", true, "First Name", "First Name", "FirstName", enrollment.getStudent().getFirstName(), 45, WebControlBase.DATA_TYPE_STRING, ""));
		strBuff.append(new TextBoxWebControl().getTextBoxWebControl("form-group col-lg-3", false, "Middle Name", "Middle Name", "MiddleName", enrollment.getStudent().getMiddleName(), 45, WebControlBase.DATA_TYPE_STRING, ""));
		strBuff.append(new SelectWebControl().getSelectWebControl("form-group col-lg-2", false, "Suffix", "SuffixName", new String[] {"Jr", "Sr", "I", "II", "III", "IV"}, enrollment.getStudent().getSuffixName(), new String[] {"Jr", "Sr", "I", "II", "III", "IV"}, "NA", "", ""));

		strBuff.append(new TextBoxWebControl().getTextBoxWebControl("form-group col-lg-3", false, "LRN", "LRN", "LearnerReferenceNumber", enrollment.getStudent().getLearnerReferenceNumber(), 16, WebControlBase.DATA_TYPE_STRING, ""));
		strBuff.append(new SelectWebControl().getSelectWebControl("form-group col-lg-5", true, "Year/Section", "AcademicSection", academicSectionList, enrollment.getAcademicSection(), "-Select-", "0", ""));
		strBuff.append(new SelectWebControl().getSelectWebControl("form-group col-lg-2", false, "Status", "Status", EnrollmentDTO.STATUS_CODE_ARR, enrollment.getStatus(), EnrollmentDTO.STATUS_STR_ARR, "", "", ""));
		strBuff.append(new SelectWebControl().getSelectWebControl("form-group col-lg-2", true, "Gender", "Gender", new String[] {UserDTO.GENDER_MALE, UserDTO.GENDER_FEMALE}, enrollment.getStudent().getGender(),  new String[] {UserDTO.GENDER_MALE, UserDTO.GENDER_FEMALE}, "", "", ""));

		strBuff.append(new TextBoxWebControl().getTextBoxWebControl("form-group col-lg-4", false, "Contact Person (<small>Must have a Contact Number</small>)", "Contact Person", "ContactName", userContact.getContactName(), 45, WebControlBase.DATA_TYPE_STRING, ""));
		strBuff.append(new TextBoxWebControl().getTextBoxWebControl("form-group col-lg-4", false, "Contact Number", "Contact Number", "Contact", userContact.getContact(), 45, WebControlBase.DATA_TYPE_STRING, ""));
		strBuff.append(new SelectWebControl().getSelectWebControl("form-group col-lg-4", false, "Relationship", "ContactRelationship", new String[] {"Mother", "Father", "Guardian", "Aunt", "Uncle"}, userContact.getContactRelationship(), new String[] {"Mother", "Father", "Guardian", "Aunt", "Uncle"}, "NA", "", ""));

		strBuff.append(new FileInputWebControl().getFileInputWebControl("form-group col-lg-6", false, "ID Picture", "IDPict", uploadedFileIDPicture));
		strBuff.append(new FileInputWebControl().getFileInputWebControl("form-group col-lg-6", false, "Signature", "Signature", uploadedFileSignature));
		return strBuff.toString();
	}
	
	private static String[][] getDataTableCurrentPageRecordArr(SessionInfo sessionInfo, DataTable dataTable) {
		String[][] strArr = new String[dataTable.getRecordListCurrentPage().size()][dataTable.getColumnNameArr().length];
		for(int row=0; row < dataTable.getRecordListCurrentPage().size(); row++) {			
			EnrollmentDTO enrollment = (EnrollmentDTO) dataTable.getRecordListCurrentPage().get(row);
			strArr[row][0] = enrollment.getStudent().getCode();
			strArr[row][1] = WebUtil.getHTMLStr(enrollment.getStudent().getLastName());
			strArr[row][2] = WebUtil.getHTMLStr(enrollment.getStudent().getFirstName());
			strArr[row][3] = WebUtil.getHTMLStr(enrollment.getStudent().getMiddleName());
			strArr[row][4] = AcademicSectionUtil.getYearLevelSectionStr(enrollment.getAcademicSection());
			String actionBtns = "";
			if(sessionInfo.getCurrentLink().getCode().equalsIgnoreCase("012")) {
				actionBtns += "<button type='button' class='btn btn-success' data-toggle='tooltip' data-placement='top' title='Edit Record' onClick=\"dataTableRecordAction" + dataTable.getId() + "('" + DataTable.ACTION_VIEW + "', " + dataTable.getCurrentPage() + ", '" + enrollment.getCode() + "')\"><i class='fa fa-search'></i></button>";
				actionBtns += "&nbsp;<button type='button' class='btn btn-warning' data-toggle='tooltip' data-placement='top' title='Edit Record' onClick=\"dataTableRecordAction" + dataTable.getId() + "('" + DataTable.ACTION_UPDATE_VIEW + "', " + dataTable.getCurrentPage() + ", '" + enrollment.getCode() + "')\"><i class='fa fa-pencil'></i></button>";
			}
			else {
				actionBtns = dataTable.getRecordButtonStr(sessionInfo, enrollment.getCode());
			}
			
			strArr[row][5] = actionBtns;
		}
		return strArr;
	}
	
	public static String getDataTableStr(SessionInfo sessionInfo, DataTable dataTable) {
		DataTableWebControl dtwc = new DataTableWebControl(sessionInfo, dataTable);
		StringBuffer strBuff = new StringBuffer();
		if(dataTable.getRecordList().size() >= 1) {
			strBuff.append(dtwc.getDataTableHeader(sessionInfo, dataTable));
			dataTable.setDataTableRecordArr(getDataTableCurrentPageRecordArr(sessionInfo, dataTable));
			strBuff.append(dtwc.getDataTable(true, false));
		}
		return strBuff.toString();
	}
	
	public static String getDataCardStr(SessionInfo sessionInfo, List<DTOBase> academicSectionList, List<DTOBase> enrollmentList, List<DTOBase> advisoryList) {
		StringBuffer strBuff = new StringBuffer();
		strBuff.append("<div class='row'>");
		for(DTOBase obj: academicSectionList) {
			AcademicSectionDTO academicSection = (AcademicSectionDTO) obj;
			List<DTOBase> enrollmentListByAcademicSectionCode = EnrollmentUtil.getEnrollmentListByAcademicSectionCode(enrollmentList, academicSection.getCode());
			if(enrollmentListByAcademicSectionCode.size() >= 1) {
				AdvisoryDTO advisory = AdvisoryUtil.getAdvisoryByAcademicSectionCode(advisoryList, academicSection.getCode());
				strBuff.append("<div class='col-lg-3'>");
				strBuff.append("	<div class='card card-md text-white rounded-top-0 bg-info mb-3 mb-lg-0'>");
				strBuff.append("		<div class='card-body d-flex align-items-center justify-content-between'>");
				strBuff.append("			<div class=''>");
				strBuff.append("				<h5 class='font-weight-bold text-white mb-1'>" + AcademicSectionUtil.getYearLevelStr(academicSection) + "</h5>");
				strBuff.append("				<p class='card-text text-white text-capitalize'>" + academicSection.getName() + "</p>");
				strBuff.append("				<p class='card-text text-white text-capitalize'> Adviser: ");
				if(advisory == null) {
					strBuff.append("				TBA");
				}
				else {
					strBuff.append(					advisory.getUser().getName(false, false, false));
				}
				strBuff.append("				</p>");
				strBuff.append("			</div>");
				strBuff.append("			<div class='badge border border-white rounded-circle badge-sm p-0'>");
				strBuff.append("				<span class='d-block'>");
				strBuff.append("					<small>" + enrollmentListByAcademicSectionCode.size() + "</small>");
				strBuff.append("				</span>");
				strBuff.append("			</div>");
				strBuff.append("		</div>");
				strBuff.append("		<a href='#' onclick=\"viewEnrollmentListByAcademicSectionCode('" + academicSection.getCode() + "')\" class='btn card-footer text-capitalize text-white font-size-15 d-flex justify-content-between align-items-center'>");
				strBuff.append("			<div class=''>");
				strBuff.append("				View Masterlist");
				strBuff.append("			</div>");
				strBuff.append("			<div class=''>");
				strBuff.append("				<i class='fas fa-long-arrow-alt-right ltr'></i>");
				strBuff.append("			</div>");
				strBuff.append("		</a>");
				strBuff.append("		<a href='#' onclick=\"viewQRCodeListByAcademicSectionCode('" + academicSection.getCode() + "')\" class='btn card-footer text-capitalize text-white font-size-15 d-flex justify-content-between align-items-center'>");
				strBuff.append("			<div class=''>");
				strBuff.append("				View QR Code List");
				strBuff.append("			</div>");
				strBuff.append("			<div class=''>");
				strBuff.append("				<i class='fas fa-long-arrow-alt-right ltr'></i>");
				strBuff.append("			</div>");
				strBuff.append("		</a>");
				strBuff.append("		<a href='#' onclick=\"viewTelegramRegistrationListByAcademicSectionCode('" + academicSection.getCode() + "')\" class='btn card-footer text-capitalize text-white font-size-15 d-flex justify-content-between align-items-center'>");
				strBuff.append("			<div class=''>");
				strBuff.append("				View Telegram Registration List");
				strBuff.append("			</div>");
				strBuff.append("			<div class=''>");
				strBuff.append("				<i class='fas fa-long-arrow-alt-right ltr'></i>");
				strBuff.append("			</div>");
				strBuff.append("		</a>");
				strBuff.append("		<a href='#' onclick=\"viewFaceLogListByAcademicSectionCode('" + academicSection.getCode() + "')\" class='btn card-footer text-capitalize text-white font-size-15 d-flex justify-content-between align-items-center'>");
				strBuff.append("			<div class=''>");
				strBuff.append("				View Face Log List");
				strBuff.append("			</div>");
				strBuff.append("			<div class=''>");
				strBuff.append("				<i class='fas fa-long-arrow-alt-right ltr'></i>");
				strBuff.append("			</div>");
				strBuff.append("		</a>");
				strBuff.append("	</div><br>");
				strBuff.append("</div>");
			}
		}
		strBuff.append("</div>");
		return strBuff.toString();
	}
	
	public static String getEnrollmentListByAcademicSectionStr(SessionInfo sessionInfo, AcademicSectionDTO academicSection, List<DTOBase> enrollmentList) {
		List<DTOBase> enrollmentListMale = EnrollmentUtil.getEnrollmentListByGender(enrollmentList, UserDTO.GENDER_MALE);
		List<DTOBase> enrollmentListFemale = EnrollmentUtil.getEnrollmentListByGender(enrollmentList, UserDTO.GENDER_FEMALE);
				
		StringBuffer strBuff = new StringBuffer();
		strBuff.append("<div class='row'>");
		strBuff.append("	<div class='col-lg-12 text-center'>");
		strBuff.append(			sessionInfo.getSettings().getName() + "<br>" + "List of Enrollment | School Year 2024-2025<br>" + AcademicSectionUtil.getYearLevelSectionStr(academicSection));
		strBuff.append("	</div>");
		strBuff.append("</div>");
		strBuff.append("<div class='row'>");
		strBuff.append("	<div class='col-lg-6'>");
		strBuff.append("		<p class='text-center'>Boys</p>");
		strBuff.append("		<table class='table table-bordered'>");
		strBuff.append("			<thead>");
		strBuff.append("				<tr>");
		strBuff.append("					<td></td>");
		strBuff.append("					<td>Last Name</td>");
		strBuff.append("					<td>First Name</td>");
		strBuff.append("					<td>Middle Name</td>");
		strBuff.append("					<td>LRN #</td>");
		strBuff.append("				</tr>");
		strBuff.append("			</thead>");
		strBuff.append("			<tbody>");
		int i=1;
		for(DTOBase enrollmentObj: enrollmentListMale) {
			EnrollmentDTO enrollment = (EnrollmentDTO)enrollmentObj;
		strBuff.append("				<tr>");
		strBuff.append("					<td>" + i + "</td>");
		strBuff.append("					<td>" + WebUtil.getHTMLStr(enrollment.getStudent().getLastName()) + "</td>");
		strBuff.append("					<td>" + WebUtil.getHTMLStr(enrollment.getStudent().getFirstName()) + "</td>");
		strBuff.append("					<td>" + WebUtil.getHTMLStr(enrollment.getStudent().getMiddleName()) + "</td>");
		strBuff.append("					<td>" + enrollment.getStudent().getLearnerReferenceNumber() + "</td>");
		strBuff.append("				</tr>");
			i++;
		}
		strBuff.append("			</tbody>");
		strBuff.append("		</table>");
		strBuff.append("	</div>");
		
		strBuff.append("	<div class='col-lg-6'>");
		strBuff.append("		<p class='text-center'>Girls</p>");
		strBuff.append("		<table class='table table-bordered'>");
		strBuff.append("			<thead>");
		strBuff.append("				<tr>");
		strBuff.append("					<td></td>");
		strBuff.append("					<td>Last Name</td>");
		strBuff.append("					<td>First Name</td>");
		strBuff.append("					<td>Middle Name</td>");
		strBuff.append("					<td>LRN #</td>");
		strBuff.append("				</tr>");
		strBuff.append("			</thead>");
		strBuff.append("			<tbody>");
		i=1;
		for(DTOBase enrollmentObj: enrollmentListFemale) {
			EnrollmentDTO enrollment = (EnrollmentDTO)enrollmentObj;
		strBuff.append("				<tr>");
		strBuff.append("					<td>" + i + "</td>");
		strBuff.append("					<td>" + WebUtil.getHTMLStr(enrollment.getStudent().getLastName()) + "</td>");
		strBuff.append("					<td>" + WebUtil.getHTMLStr(enrollment.getStudent().getFirstName()) + "</td>");
		strBuff.append("					<td>" + WebUtil.getHTMLStr(enrollment.getStudent().getMiddleName()) + "</td>");
		strBuff.append("					<td>" + enrollment.getStudent().getLearnerReferenceNumber() + "</td>");
		strBuff.append("				</tr>");
			i++;
		}
		strBuff.append("			</tbody>");
		strBuff.append("		</table>");
		strBuff.append("	</div>");
		strBuff.append("</div>");
		return strBuff.toString();
	}
	
	public static String getQRCodeListByAcademicSectionStr(SessionInfo sessionInfo, AcademicSectionDTO academicSection, List<DTOBase> enrollmentList) {
		StringBuffer strBuff = new StringBuffer();
		int i=0;
		for(DTOBase enrollmentObj: enrollmentList) {
			EnrollmentDTO enrollment = (EnrollmentDTO)enrollmentObj;
			if(i==0) {
		strBuff.append("<div class='row'>"); 
			}
			else if(i%4==0) {
		strBuff.append("</div>");
				if(i<enrollmentList.size()) {
		strBuff.append("<div class='row'>");
				}
			}
		strBuff.append("<div class='col-sm-3 text-center'>");
		strBuff.append("	<img src='" + QRCodeUtil.generateQRCodeBase64(enrollment.getStudent().getCode(), 80, 80) + "'><br>");
		strBuff.append("	<small>" + enrollment.getStudent().getName(false, false, true) + "</small>");
		strBuff.append("</div>");
			i++;
		}
		return strBuff.toString();
	}
	
	public static String getTelegramRegistrationListByAcademicSectionStr(SessionInfo sessionInfo, AcademicSectionDTO academicSection, List<DTOBase> enrollmentList, TelegramSettingsDTO telegramSettings) {
		StringBuffer strBuff = new StringBuffer();
		strBuff.append("<div class='row'>"); 
		strBuff.append("	<div class='col-lg-12'>"); 
		strBuff.append("		<table class='table'>");
		int i=0;
		for(DTOBase enrollmentObj: enrollmentList) {
			EnrollmentDTO enrollment = (EnrollmentDTO)enrollmentObj;
			
			String updateKey = "";
			try {
				updateKey = AESUtil.encrypt(enrollment.getStudent().getCode(), enrollment.getStudent().getPassword());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			String newRegistrationURL = telegramSettings.getBotURL() + "?start=NEWREG" + enrollment.getStudent().getCode();
			//String modifyRegistrationURL = telegramSettings.getBotURL() + "?start=MODREG" + enrollment.getStudent().getCode() + " " + updateKey;
			if(i==0) {
		strBuff.append("			<tr>");
			}
			else if(i%2==0) {
		strBuff.append("			</tr>");
				if(i<enrollmentList.size()) {
		strBuff.append("			<tr>");
				}
			}
		strBuff.append("				<td style='padding-left: 20px;'>");
		strBuff.append("					<img width='60px' height='60px' src='" + sessionInfo.getSettings().getLogo() + "'>");
		strBuff.append("					<small style='font-size: 12px'><b>" + sessionInfo.getSettings().getName() + "</b></small>");
		strBuff.append("					<br><small style='font-size: 10px'>" + enrollment.getAcademicSection().getCode() + "-" + StringUtil.getPadded(String.valueOf(i+1), 3, "0", true) + " | <b>" + enrollment.getStudent().getName(false, false, true) + "</b> | " + AcademicSectionUtil.getYearLevelSectionStr(enrollment.getAcademicSection()) + "</small>");
		strBuff.append("					<br><small style='font-size: 10px'>We are pleased to announce that we have upgraded our notification system from SMS to Telegram to serve you better and provide up-to-date news and updates on mobile.</small>");
		strBuff.append("					<table width='100%'>");	
		strBuff.append("						<tr>");
		strBuff.append("							<td width='15%' valign='top'>");
		strBuff.append("								<small style='font-size: 10px'>To register, scan the QR Code and wait for the acknowledgment.</small>");
		strBuff.append("							</td>");
		strBuff.append("							<td width='20%' valign='top'>");
		strBuff.append("								<img class='img-fluid' src='" + QRCodeUtil.generateQRCodeBase64(newRegistrationURL, 200, 200) + "'>");
		strBuff.append("							</td>");
		strBuff.append("							<td width='60%' valign='top'>");
		strBuff.append("								<small style='font-size: 10px'>Alternatively, you may login at <b>" + sessionInfo.getSettings().getWebsite() + "</b></small>");
		strBuff.append("								<br><small style='font-size: 10px'>Username: <b>" + enrollment.getStudent().getUserName() + "</b><br>Password: <b>" +  enrollment.getStudent().getPassword() + "</b></small>");
		strBuff.append("								<br><small style='font-size: 10px'>For concerns send telegram to <b>" + telegramSettings.getBotSupportURL() + "</b></small>");
		strBuff.append("							</td>");
		strBuff.append("						</tr>");
		strBuff.append("					</table>");
		strBuff.append("				</td>");
			i++;
		}
		strBuff.append("		</table");
		strBuff.append("	</div>");
		strBuff.append("</div>");
		return strBuff.toString();
	}
	
	public static String getFaceLogListByAcademicSectionStr(SessionInfo sessionInfo, Calendar calendar, List<DTOBase> enrollmentList) {
		StringBuffer strBuff = new StringBuffer();
		strBuff.append("<div class='col-md-4'>");
		strBuff.append(	getCalendarMonthYearStr("001", calendar.getDate(), true, true));
		strBuff.append("</div>");
		
		strBuff.append("<div class='col-md-6 col-lg-3'>");
		strBuff.append("	<div class='mb-3 form-group-icon mb-lg-0'>");
		strBuff.append("		<i class='far fa-calendar-alt' aria-hidden='true'></i>");
		strBuff.append("  		<input type='text' class='form-control daterange-picker' autocomplete='off' name='dateRange' value='' placeholder='MM/DD/YYYY' />");
		strBuff.append("	</div>");
		strBuff.append("</div> ");              
		
		int i=0;
		for(DTOBase enrollmentObj: enrollmentList) {
			EnrollmentDTO enrollment = (EnrollmentDTO)enrollmentObj;
			List<DTOBase> faceLogList = new FaceLogDAO().getFaceLogListByUserCodeTimeLogDate(enrollment.getStudent().getCode(), DateTimeUtil.getDateTimeToStr(calendar.getDate(), "yyyy-MM-dd"));
			if(i==0) {
		strBuff.append("<div class='row'>"); 
			}
			else if(i%3==0) {
		strBuff.append("</div>");
				if(i<enrollmentList.size()) {
		strBuff.append("<div class='row'>");
				}
			}
			strBuff.append("<div class='col-md-6 col-lg-4 mb-5'>");
			strBuff.append("	<div class='card card-hover' align='center'>");
			strBuff.append("		<img height='150px' width='150px' src='" + sessionInfo.getSettings().getLogo() + "' alt='Card image cap'>");
			strBuff.append("		<div class='card-body'>");
			strBuff.append("			<h5 class='text-primary text-center'>" + enrollment.getStudent().getName(false, false, true) + "</h5>");
			strBuff.append("			<div class='row'>");
			strBuff.append("				<div class='col-lg-6 alert alert-primary'>");
			strBuff.append("					<h4 class='text-center'>Time In</h4>");
			strBuff.append(						getFaceLogListStr(getFaceLogListByInOut(faceLogList, true)));
			strBuff.append("				</div>");
			strBuff.append("				<div class='col-lg-6 alert alert-info'>");
			strBuff.append("					<h4 class='text-center'>Time Out</h4>");
			strBuff.append(						getFaceLogListStr(getFaceLogListByInOut(faceLogList, false)));
			strBuff.append("				</div>");
			strBuff.append("			</div>");
			strBuff.append("		</div>");
			strBuff.append("	</div>");
			strBuff.append("</div>");
			i++;
		}
		return strBuff.toString();
	}
	
	private static String getFaceLogListStr(List<DTOBase> faceLogList) {
		StringBuffer strBuff = new StringBuffer();
		strBuff.append("<ul>");
		for(DTOBase faceLogObj: faceLogList) {
			FaceLogDTO faceLog = (FaceLogDTO) faceLogObj;
			strBuff.append("<li>" + DateTimeUtil.getDateTimeToStr(faceLog.getTimeLog(), "hh:mm a") + "</li>");
		}
		strBuff.append("</ul>");
		return strBuff.toString();
	}
	
	private static List<DTOBase> getFaceLogListByInOut(List<DTOBase> faceLogList, boolean isIn) {
		List<DTOBase> list = new ArrayList<DTOBase>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm") ;
		for(DTOBase faceLogObj: faceLogList) {
			FaceLogDTO faceLog = (FaceLogDTO) faceLogObj;
			if(isIn) {
				if(faceLog.isIn()) {
					list.add(faceLog);
				}
			}
			else {
				if(!faceLog.isIn()) {
					list.add(faceLog);
				}
			}
		}
		return list;
	}
	
	private static List<DTOBase> getFaceLogListByPeriod(List<DTOBase> faceLogList, boolean isAM) {
		List<DTOBase> list = new ArrayList<DTOBase>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm") ;
		for(DTOBase faceLogObj: faceLogList) {
			FaceLogDTO faceLog = (FaceLogDTO) faceLogObj;
			if(isAM) {
				try {
					if(dateFormat.parse(dateFormat.format(faceLog.getTimeLog())).before(dateFormat.parse("12:00"))) {
						list.add(faceLog);
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else {
				try {
					if(dateFormat.parse(dateFormat.format(faceLog.getTimeLog())).after(dateFormat.parse("12:00"))) {
						list.add(faceLog);
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return list;
	}
	
	public static EnrollmentDTO getEnrollmentByStudentCodeAcademicYearCode(List<DTOBase> enrollmentList, String studentCode, String academicYearCode) {
		for(DTOBase enrollmentObj: enrollmentList) {
			EnrollmentDTO enrollment = (EnrollmentDTO)enrollmentObj;
			if(enrollment.getStudent().getCode().equalsIgnoreCase(studentCode) && enrollment.getSemester().getAcademicYear().getCode().equalsIgnoreCase(academicYearCode)) {
				return enrollment;
			}
		}
		return null;
	}
	
	public static EnrollmentDTO getEnrollmentByStudentCode(List<DTOBase> enrollmentList, String studentCode) {
		for(DTOBase enrollmentObj: enrollmentList) {
			EnrollmentDTO enrollment = (EnrollmentDTO)enrollmentObj;
			if(enrollment.getStudent().getCode().equalsIgnoreCase(studentCode)) {
				return enrollment;
			}
		}
		return null;
	}
	
	public static List<DTOBase> getEnrollmentListByAcademicSectionCode(List<DTOBase> enrollmentList, String academicSectionCode) {
		List<DTOBase> list = new ArrayList<DTOBase>();
		for(DTOBase enrollmentObj: enrollmentList) {
			EnrollmentDTO enrollment = (EnrollmentDTO)enrollmentObj;
			if(enrollment.getAcademicSection().getCode().equalsIgnoreCase(academicSectionCode)) {
				list.add(enrollment);
			}
		}
		return list;
		
	}
	
	public static void setEnrollmentListAcademicSection(List<DTOBase> enrollmentList, List<DTOBase> academicSectionList) {
		for(DTOBase enrollmentObj: enrollmentList) {
			EnrollmentDTO enrollment = (EnrollmentDTO)enrollmentObj;
			AcademicSectionDTO academicSection = (AcademicSectionDTO) DTOUtil.getObjByCode(academicSectionList, enrollment.getAcademicSection().getCode());
			if(academicSection != null) {
				enrollment.setAcademicSection(academicSection);
			}
		}
	}
	
	public static void setEnrollmentListAcademicSectionStudent(List<DTOBase> enrollmentList, List<DTOBase> academicSectionList, List<DTOBase> studentList) {
		for(DTOBase enrollmentObj: enrollmentList) {
			EnrollmentDTO enrollment = (EnrollmentDTO)enrollmentObj;
			AcademicSectionDTO academicSection = (AcademicSectionDTO) DTOUtil.getObjByCode(academicSectionList, enrollment.getAcademicSection().getCode());
			if(academicSection != null) {
				enrollment.setAcademicSection(academicSection);
			}
			enrollment.setStudent((StudentDTO) DTOUtil.getObjByCode(studentList, enrollment.getStudent().getCode()));
		}
	}
	
	public static void searchByName(DataTable dataTable, String searchValue, List<DTOBase> userList) {
		for(DTOBase dto: dataTable.getRecordList()) {
			EnrollmentDTO enrollment = (EnrollmentDTO) dto;
			UserDTO user = (UserDTO) DTOUtil.getObjByCode(userList, enrollment.getStudent().getCode());
			if(user.getName(false, false, false).toUpperCase().contains(searchValue.toUpperCase())) {
				StudentUtil.setStudent(enrollment.getStudent(), user);
				enrollment.setVisible(true);
			}
		}
	}
	
	public static void searchByLastName(DataTable dataTable, String searchValue, List<DTOBase> userList) {
		for(DTOBase dto: dataTable.getRecordList()) {
			EnrollmentDTO enrollment = (EnrollmentDTO) dto;
			UserDTO user = (UserDTO) DTOUtil.getObjByCode(userList, enrollment.getStudent().getCode());
			if(user.getLastName().toUpperCase().equalsIgnoreCase(searchValue.toUpperCase())) {
				StudentUtil.setStudent(enrollment.getStudent(), user);
				enrollment.setVisible(true);
			}
		}
	}
	
	public static void searchByUserCode(DataTable dataTable, String searchValue) {
		dataTable.setRecordListInvisible();
		for(DTOBase dto: dataTable.getRecordList()) {
			EnrollmentDTO enrollment = (EnrollmentDTO) dto;
			if(enrollment.getStudent().getCode().toUpperCase().contains(searchValue.toUpperCase())) {
				enrollment.setVisible(true);
			}
		}
	}
	
	private static List<DTOBase> getEnrollmentListByGender(List<DTOBase> enrollmentList, String gender) {
		List<DTOBase> list = new ArrayList<DTOBase>();
		for(DTOBase enrollmentObj: enrollmentList) {
			EnrollmentDTO enrollment = (EnrollmentDTO)enrollmentObj;
			if(enrollment.getStudent().getGender().equalsIgnoreCase(gender)) {
				list.add(enrollment);
			}
		}
		return list;
	}
	
	public static String getCalendarMonthYearStr(String calendarId, Date date, boolean isMonthEditable, boolean isYearEditable) {
		StringBuffer strBuff = new StringBuffer();
		Date firstDate = DateTimeUtil.getFirstDate(date);
		Date lastDate = DateTimeUtil.getLastDate(date);
		Date runningDate = DateTimeUtil.getFirstDate(date);

		boolean isStarted = false;
		String[] dayStr = new String[]{"Su", "Mo", "Tu", "We", "Th", "Fr", "Sa"};
		strBuff.append("<div class='col-lg-12'>");
		if(isMonthEditable) {
			String[] monthArrStr = new String[]{"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"}; 
			strBuff.append("	<button class='btn btn-primary dropdown-toggle' type='button' id='dropdownMenuButton1' data-bs-toggle='dropdown' aria-expanded='false'>");
			strBuff.append(			DateTimeUtil.getMonthName(DateTimeUtil.getDateMonth(date)));
			strBuff.append("  	</button>");
			strBuff.append("  	<ul class='dropdown-menu' aria-labelledby='dropdownMenuButton1'>");
			for(int i=0; i<monthArrStr.length; i++) {
				strBuff.append("    <li>");
				if(DateTimeUtil.getDateMonth(date) == i) {
					strBuff.append("	<b class='dropdown-item'>" + monthArrStr[i] + "</b>");
				}
				else {
					strBuff.append("    <a class='dropdown-item' href='#' onclick=\"selectCalendarMonth(" + i + ")\">");
					strBuff.append(			monthArrStr[i]);
					strBuff.append("    </a>");
				}
				strBuff.append("    </li>");
			}
			strBuff.append("	</ul>");
		}
		else {
			strBuff.append(			DateTimeUtil.getMonthName(DateTimeUtil.getDateMonth(date)));
		}
		
		if(isYearEditable) {
			int[] yearArr = new int[]{2024, 2025, 2026}; 
			strBuff.append("	<button class='btn btn-primary dropdown-toggle' type='button' id='dropdownMenuButton1' data-bs-toggle='dropdown' aria-expanded='false'>");
			strBuff.append(			DateTimeUtil.getDateTimeToStr(date, "YYYY"));
			strBuff.append("  	</button>");
			strBuff.append("  	<ul class='dropdown-menu' aria-labelledby='dropdownMenuButton1'>");
			for(int i=0; i<yearArr.length; i++) {
				strBuff.append("    <li>");
				if(DateTimeUtil.getDateYear(date) == yearArr[i]) {
					strBuff.append("	<b class='dropdown-item'>" + yearArr[i] + "</b>");
				}
				else {
					strBuff.append("    <a class='dropdown-item' href='#' onclick=\"selectCalendarYear(" + yearArr[i] + ")\">");
					strBuff.append(			yearArr[i]);
					strBuff.append("    </a>");
				}
				strBuff.append("    </li>");
			}
			strBuff.append("	</ul>");
		}
		else {
			strBuff.append("<strong>" + DateTimeUtil.getDateTimeToStr(date, "YYYY") + "</strong>");
		}
		strBuff.append("</div>");
		strBuff.append("<div class='col-sm-12 table-responsive'>");
		strBuff.append("	<br><table class='table table-bordered'>");
		strBuff.append("		<tr>");
		for(int i=0; i<dayStr.length; i++) {  	
			strBuff.append("		<td align='center'><b>" + dayStr[i] + "</b></td>");
		}			
		strBuff.append("		</tr>");
		for(int row=0; row<=5; row++) {
			strBuff.append("	<tr>");
			for(int col=0; col<=6; col++) {
				if((DateTimeUtil.getDateDayOfTheWeek(firstDate)-1) == col) {
        			isStarted = true;
        		}
				if(runningDate.after(lastDate) || !isStarted) {
					strBuff.append("<td>");
				}
				else {
					strBuff.append("<td align='center' style='");
					if(DateTimeUtil.isDateEqual(DateTimeUtil.getCurrentTimestamp(), runningDate)) { //current date
						strBuff.append("font-size: large; font-weight: bold");
						if(DateTimeUtil.isDateEqual(date, runningDate)) { //selected date
							strBuff.append(";background: yellow'");
						}
						else {
							strBuff.append(";cursor: pointer' onclick=\"selectCalendarDayOfTheMonth(" + DateTimeUtil.getDateDayOfTheMonth(runningDate) + ")\"");
						}
					}
					else {
						if(DateTimeUtil.isDateEqual(date, runningDate)) {//selected date
							strBuff.append("background: yellow'");
						}
						else {
							strBuff.append("cursor: pointer' onclick=\"selectCalendarDayOfTheMonth(" + DateTimeUtil.getDateDayOfTheMonth(runningDate) + ")\"");
						}
					}
					strBuff.append(">");
				}

        		if(isStarted) {
        			if(lastDate.after(runningDate) || DateTimeUtil.isDateEqual(lastDate, runningDate)) {
        				strBuff.append(	DateTimeUtil.getDateDayOfTheMonth(runningDate));
        			}
        			runningDate = DateTimeUtil.addDay(runningDate, 1);
        		}
        		strBuff.append("	</td>");
			}	
			strBuff.append("	</tr>");
		}      	     
		strBuff.append("	</table>");
		strBuff.append("</div>");
		return strBuff.toString();
	}
	
	public static void main(String[] a) {
//		Date date = new Date() ;
//		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm") ;
//		dateFormat.format(date);
//		System.out.println(dateFormat.format(date));
//		try {
//			if(dateFormat.parse(dateFormat.format(date)).after(dateFormat.parse("08:47")))
//			{
//			    System.out.println("Current time is greater than 12.07");
//			}else{
//			    System.out.println("Current time is less than 12.07");
//			}
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		String str = "MODREG 000000001439";
		String s1 = str.split(" ")[0];
		String s2 = str.split(" ")[1];
		String s3 = str.split(" ")[2] == null?"":str.split(" ")[2];
		
		System.out.println("S1: " + s1);
		System.out.println("S2: " + s2);
		System.out.println("S3: " + s3);
		
	}
}
