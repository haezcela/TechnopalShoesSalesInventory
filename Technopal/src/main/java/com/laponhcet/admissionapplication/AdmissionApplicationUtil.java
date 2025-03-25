package com.laponhcet.admissionapplication;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.mytechnopal.DataTable;
import com.mytechnopal.SessionInfo;
import com.mytechnopal.UploadedFile;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.base.WebControlBase;
import com.mytechnopal.util.DateTimeUtil;
import com.mytechnopal.util.QRCodeUtil;
import com.mytechnopal.webcontrol.DataTableWebControl;
import com.mytechnopal.webcontrol.FileInputWebControl;
import com.mytechnopal.webcontrol.SelectWebControl;
import com.mytechnopal.webcontrol.TextBoxWebControl;

public class AdmissionApplicationUtil implements Serializable {
	private static final long serialVersionUID = 1L;

	private static String[][] getDataTableCurrentPageRecordArr(SessionInfo sessionInfo, DataTable dataTable) {
		String[][] strArr = new String[dataTable.getRecordListCurrentPage().size()][dataTable.getColumnNameArr().length];
		for(int row=0; row < dataTable.getRecordListCurrentPage().size(); row++) {			
			AdmissionApplicationDTO admissionApplication = (AdmissionApplicationDTO) dataTable.getRecordListCurrentPage().get(row);
			strArr[row][0] = admissionApplication.getCode();
			strArr[row][1] = admissionApplication.getLastName();
			strArr[row][2] = admissionApplication.getFirstName();
			strArr[row][3] = admissionApplication.getPermanentAddressCity().getName();
			strArr[row][4] = admissionApplication.getApplicantType();
			strArr[row][5] = admissionApplication.getAcademicProgram().getName();
			strArr[row][6] = admissionApplication.getStatus();
			if(admissionApplication.getStatus().equalsIgnoreCase(AdmissionApplicationDTO.STATUS_FOR_EXAMNINATION)) {
				strArr[row][7] = dataTable.getCustomRecordButtonStr(sessionInfo, admissionApplication.getCode(), new String[] {AdmissionApplicationDTO.ACTION_UPDATE_APPLICATION_STATUS_REJECT, DataTable.ACTION_VIEW, AdmissionApplicationDTO.ACTION_UPDATE_APPLICATION_STATUS_EXAM_DONE}, new String[] {"Reject", "View Application", "EXAM DONE"}, new String[] {"btn-danger btn-sm", "btn-success btn-sm", "btn-warning btn-sm"}, new String[] {"fa fa-times", "fa fa-search", "fa fa-check"});
			}
			else {
				strArr[row][7] = dataTable.getCustomRecordButtonStr(sessionInfo, admissionApplication.getCode(), new String[] {DataTable.ACTION_VIEW}, new String[] {"View Application"}, new String[] {"btn-success btn-sm"}, new String[] {"fa fa-search"});
			}
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
	
	public static void searchAdmissionApplicationByName(DataTable dataTable, String searchValue) {
		dataTable.setRecordListInvisible();
		for(DTOBase obj: dataTable.getRecordList()) {
			AdmissionApplicationDTO admissionApplication = (AdmissionApplicationDTO) obj;
			if(admissionApplication.getLastName().toLowerCase().contains(searchValue.toLowerCase()) || admissionApplication.getFirstName().toLowerCase().contains(searchValue.toLowerCase())) {
				admissionApplication.setVisible(true);
			} 
		}
	}
	
	public static void searchAdmissionApplicationByCode(DataTable dataTable, String searchValue) {
		dataTable.setRecordListInvisible();
		for(DTOBase obj: dataTable.getRecordList()) {
			AdmissionApplicationDTO admissionApplication = (AdmissionApplicationDTO) obj;
			if(admissionApplication.getCode().toLowerCase().contains(searchValue.toLowerCase())) {
				admissionApplication.setVisible(true);
			} 
		}
	}
	
	public static String getDataViewStr(SessionInfo sessionInfo, AdmissionApplicationDTO admissionApplication) {
		StringBuffer strBuff = new StringBuffer();
		strBuff.append("<div class='white-bg' id='divApplicantInformation'>");
		strBuff.append("	<div class='col-sm-12 text-center'>");
		strBuff.append("		<img height='100px' width='110px' src='" + sessionInfo.getSettings().getLogo() + "'><br>");
		strBuff.append("		<h3>Bago City College</h3>");
		strBuff.append("		<h5>Rafael M. Salas Dr. Brgy. Balingasag, Bago City, Negros Occidental, Phil, 6101</h5>");
		strBuff.append("		<h3>GUIDANCE CENTER</h3>");
		strBuff.append("		<h4>ADMISSION EXAMINATION APPLICATION FORM</h4>");
		strBuff.append("	</div>");
		strBuff.append("	<table width='100%'>");
		strBuff.append("		<tr>");
		strBuff.append("			<td>APPLICANT'S INFORMATION</td>");
		strBuff.append("		</tr>");
		strBuff.append("		<tr>");
		strBuff.append("			<td>Date/Time of Application:&nbsp;" + DateTimeUtil.getDateTimeToStr(admissionApplication.getAddedTimestamp(), "MMMM dd, yyyy hh:mm a") + "</td>");
		strBuff.append("		</tr>");
		strBuff.append("		<tr>");
		strBuff.append("			<td>");
		strBuff.append("				<img style='position: absolute; top: 0;  right: 0;' width='144px' height='144px' src='" + sessionInfo.getSettings().getStaticDir(false) + "/" + sessionInfo.getSettings().getCode() + "/media/applicant_pict/" + admissionApplication.getPict() + "'>");
		strBuff.append("				<table width='100%'>");
		strBuff.append("					<tr>");
		strBuff.append("						<td width='70%' valign='top'>");
		strBuff.append("							<table width='100%'>");
		strBuff.append("								<tr>");
		strBuff.append("									<td>");
		strBuff.append("										<table width='100%'>");
		strBuff.append("											<tr>");
		strBuff.append("												<td>Name</td>");
		strBuff.append("												<td align='center'>" + admissionApplication.getLastName() + "</td>");
		strBuff.append("												<td align='center'>" + admissionApplication.getFirstName() + "</td>");
		strBuff.append("												<td align='center'>" + admissionApplication.getMiddleName() + "</td>");
		strBuff.append("											</tr>");
		strBuff.append("											<tr>");
		strBuff.append("												<td></td>");
		strBuff.append("												<td align='center'>(<small>Last Name</small>)</td>");
		strBuff.append("												<td align='center'>(<small>First Name</small>)</td>");
		strBuff.append("												<td align='center'>(<small>Middle Name</small>)</td>");
		strBuff.append("											</tr>");
		strBuff.append("										</table>");
		strBuff.append("									</td>");
		strBuff.append("								</tr>");
		strBuff.append("								<tr>");
		strBuff.append("									<td>");
		strBuff.append("										<table width='100%' style='font-size: 10px;'>");
		strBuff.append("											<tr>");
		strBuff.append("												<td>Date of Birth</td>");
		strBuff.append("												<td>:</td>");
		strBuff.append(" 												<td>" +	DateTimeUtil.getDateTimeToStr(admissionApplication.getDateOfBirth(), "MMMM dd, yyyy") + "</td>");
		strBuff.append("												<td>Place of Birth</td>");
		strBuff.append("												<td>:</td>");
		strBuff.append(" 												<td>" +	admissionApplication.getPlaceOfBirth() + "</td>");
		strBuff.append("											</tr>");
		strBuff.append("											<tr>");
		strBuff.append("												<td>Age</td>");
		strBuff.append("												<td>:</td>");
		strBuff.append(" 												<td>" +	DateTimeUtil.getAge(admissionApplication.getDateOfBirth()) + "</td>");
		strBuff.append("												<td>Civil Status</td>");
		strBuff.append("												<td>:</td>");
		strBuff.append(" 												<td>" +	admissionApplication.getCivilStatus() + "</td>");
		strBuff.append("											</tr>");
		strBuff.append("											<tr>");
		strBuff.append("												<td>Sex</td>");
		strBuff.append("												<td>:</td>");
		strBuff.append(" 												<td>" + admissionApplication.getGender() + "</td>");
		strBuff.append("												<td>Permanent Address</td>");
		strBuff.append("												<td>:</td>");
		strBuff.append(" 												<td>" +	admissionApplication.getPermanentAddressDetails() + ", Barangay " + admissionApplication.getPermanentAddressBarangay().getName() + ", " +  admissionApplication.getPermanentAddressCity().getName() + "</td>");
		strBuff.append("											</tr>");
		strBuff.append("											<tr>");
		strBuff.append("												<td>E-mail Address</td>");
		strBuff.append("												<td>:</td>");
		strBuff.append(" 												<td>" + admissionApplication.getEmailAddress()+ "</td>");
		strBuff.append("												<td>Contact Number</td>");
		strBuff.append("												<td>:</td>");
		strBuff.append(" 												<td>" +	admissionApplication.getCpNumber() + "</td>");
		strBuff.append("											</tr>");
		strBuff.append("											<tr>");
		strBuff.append("												<td>Last School Attendded</td>");
		strBuff.append("												<td>:</td>");
		strBuff.append(" 												<td>" + admissionApplication.getLastSchoolAttendedName()+ "</td>");
		strBuff.append("												<td>School Address</td>");
		strBuff.append("												<td>:</td>");
		strBuff.append(" 												<td>" +	admissionApplication.getLastSchoolAttendedCity().getName() + "</td>");
		strBuff.append("											</tr>");
		strBuff.append("											<tr>");
		strBuff.append("												<td>Applicant Type</td>");
		strBuff.append("												<td>:</td>");
		strBuff.append(" 												<td>" + admissionApplication.getApplicantType()+ "</td>");
		strBuff.append("												<td>Track/Strand</td>");
		strBuff.append("												<td>:</td>");
		strBuff.append(" 												<td>" +	admissionApplication.getShsStrand().getLabel()+ "</td>");
		strBuff.append("											</tr>");
		strBuff.append("											<tr>");
		strBuff.append("												<td>Preferred Program</td>");
		strBuff.append("												<td>:</td>");
		strBuff.append(" 												<td>" + admissionApplication.getAcademicProgram().getName()+ "</td>");
		strBuff.append("												<td>Last School Year Attended</td>");
		strBuff.append("												<td>:</td>");
		strBuff.append(" 												<td>" +	 admissionApplication.getLastSchoolAttendedYear()+ "</td>");
		strBuff.append("											</tr>");
		strBuff.append("										</table>");
		strBuff.append("									</td>");
		strBuff.append("								</tr>");
		strBuff.append("							</table>");
		strBuff.append("						</td>");
		strBuff.append("					</tr>");
		strBuff.append("				</table>");
		strBuff.append("			</td>");
		strBuff.append("		</tr>");
		strBuff.append("		<tr>");
		strBuff.append("			<td style='font-size: 10px;'>");
		strBuff.append("				<h4>Terms and Conditions</h4>");
		strBuff.append("				<ol>");	 
		strBuff.append("			 		<li><b>The entrance examination will be taken once. There will be no second chance.</b></li>");
		strBuff.append("			 		<li>The passing score in the entrance examination does not guarantee in the program where the examinee has applied as there are other requirements for the respective program/course.</li>");
		strBuff.append("			 		<li>Those who can't take the entrance examination on the scheduled date will forfeit his/her chance of taking the exam again.</li>");
		strBuff.append("			 		<li>The Applicant is allowed to choose only one(1) course/program.</li>");
		strBuff.append("			 		<li>The entrance examination only form should be duly signed by the applicant and parent/guardian and submitted during the schedule date of examination. No form, No Examination.</li>");
		strBuff.append("		 		</ol>");
		strBuff.append("			</td>");
		strBuff.append("		</tr>");
		strBuff.append("		<tr>");
		strBuff.append("			<td style='font-size: 10px;'>");
		strBuff.append("				In submission of my application, I have read and understood the terms and conditions stated herein set by Bago city college<td>");
		strBuff.append("			</td>");
		strBuff.append("		</tr>");
		strBuff.append("		<tr>");
		strBuff.append("			<td>");
		strBuff.append("				<table width='100%'>");
		strBuff.append("					<tr>");
		strBuff.append("						<td width='34%' align='center'><img src='" + QRCodeUtil.generateQRCodeBase64("https://admission.bagocitycollege.com/BCCAdmission/ViewAdmissionApplication?txtCode=" + admissionApplication.getCode(), 100, 100) + "'></td>");
		strBuff.append("						<td width='33%' align='center'><img width='160px' height='100px' src='" + sessionInfo.getSettings().getStaticDir(false) + "/" + sessionInfo.getSettings().getCode() + "/media/applicant_signature/" + admissionApplication.getSignatureApplicant() + "'></td>");
		strBuff.append("						<td width='33%' align='center'><img width='160px' height='100px' src='" + sessionInfo.getSettings().getStaticDir(false) + "/" + sessionInfo.getSettings().getCode() + "/media/guardian_signature/" + admissionApplication.getSignatureGuardian()+ "'></td>");
		strBuff.append("					</tr>");
		strBuff.append("					<tr>");
		strBuff.append("						<td align='center'>Reference Code:&nbsp;<b>" + admissionApplication.getCode() + "</b></td>");
		strBuff.append("						<td align='center'>Applicant Signature</td>");
		strBuff.append("						<td align='center'>Parent/Guardian Signature</td>");
		strBuff.append("				</table>");
		strBuff.append("			</td>");
		strBuff.append("		</tr>");
		strBuff.append("	</table>");
		strBuff.append("</div>");
		
//		strBuff.append("<div class='col-sm-12 text-center'>");
//		strBuff.append("	<button class='btn btn-primary' onclick='printPreview(\"divApplicantInformation\", \"Bago City College-Applicant Information\")'><i class='fa fa-print'></i>&nbsp;Print</button><br><br>");
//		strBuff.append("</div>");
		return strBuff.toString();
	}
	
	public static String getAdmissionApplicationUpdateStatusStr(SessionInfo sessionInfo, AdmissionApplicationDTO admissionApplication) {
		StringBuffer strBuff = new StringBuffer();
		strBuff.append(new SelectWebControl().getSelectWebControl("col-md-3", true, "Status", "Status", new String[] {AdmissionApplicationDTO.STATUS_SUBMITTED, AdmissionApplicationDTO.STATUS_FOR_EXAMNINATION}, "", new String[] {AdmissionApplicationDTO.STATUS_SUBMITTED, AdmissionApplicationDTO.STATUS_FOR_EXAMNINATION}, "Select Status", "", ""));
		strBuff.append(new TextBoxWebControl().getTextBoxWebControl("col-md-9", false, "Remarks", "Remarks", "Remarks", "", 360, WebControlBase.DATA_TYPE_STRING, ""));
		return strBuff.toString();
		
	}
	
	public static String getDataEntryStr(SessionInfo sessionInfo, AdmissionApplicationDTO admissionApplication, List<DTOBase> cityList, List<DTOBase> barangayList, List<DTOBase> shsStrandList, List<DTOBase> academicProgramList, UploadedFile uploadedFilePict, UploadedFile uploadedFileSignatureApplicant, UploadedFile uploadedFileSignatureGuardian) {
		StringBuffer strBuff = new StringBuffer();
		strBuff.append("<div class='row'>");
		strBuff.append("<div class='py-5 col-sm-6'>");
		strBuff.append("	Bago City College");
		strBuff.append("	Rafael M. Salas Dr. Brgy. Balingasag, Bago City, Negros Occidental, Phil, 6101");
		strBuff.append("	GUIDANCE CENTER");
		strBuff.append("	Admission Examination Application Form");
		strBuff.append("	AY 2025-2026 | First Semester");
		strBuff.append("</div>");
		strBuff.append("<div class='py-5 col-sm-6'>");
		strBuff.append("	Instructions");
		strBuff.append("	<ul>");
		strBuff.append("		<li>Fill-up the necessary data in the form.</li>");
		strBuff.append("		<li>Field marks with <font color='red'>*</font> are mandatory.</li>");
		strBuff.append("	</ul>");
		strBuff.append("</div>");
		strBuff.append("<div class='col-sm-12'><hr></div>");
//		strBuff.append("<div class='clearfix'></div>");
		strBuff.append(new TextBoxWebControl().getTextBoxWebControl("col-sm-4", true, "Last Name", "Last Name", "LastName", admissionApplication.getLastName(), 45, WebControlBase.DATA_TYPE_STRING, ""));
		strBuff.append(new TextBoxWebControl().getTextBoxWebControl("col-sm-4", true, "First Name", "First Name", "FirstName", admissionApplication.getFirstName(), 45, WebControlBase.DATA_TYPE_STRING, ""));
		strBuff.append(new TextBoxWebControl().getTextBoxWebControl("col-sm-4", false, "Middle Name", "Middle Name", "MiddleName", admissionApplication.getMiddleName(), 45, WebControlBase.DATA_TYPE_STRING, ""));
		
		strBuff.append("	<div class='col-lg-2'>");
		strBuff.append("    	Date of Birth <i class='far fa-calendar-alt' aria-hidden='true'></i><font color='red' style='weight:700'>*</font>");
		strBuff.append("        <input type='text' class='form-control' id='txtDateOfBirth' name='txtDateOfBirth' value='" + DateTimeUtil.getDateTimeToStr(admissionApplication.getDateOfBirth(), "MM/dd/YYYY") + "'>");
		strBuff.append("		<script>");
		strBuff.append("			$('#txtDateOfBirth').daterangepicker({");
		strBuff.append("				singleDatePicker: true,");
		strBuff.append("				showDropdowns: true,");
		strBuff.append("				locale: {");
		strBuff.append("    				format: 'MM/DD/YYYY'"); // Date display format
		strBuff.append("				}");
		strBuff.append("			});");
		strBuff.append("		</script>");
		strBuff.append("  	</div>");
		//strBuff.append(new TextBoxWebControl().getTextBoxWebControl("col-sm-2", true, "Date of Birth", "Date of Birth", "DateOfBith", DateTimeUtil.getDateTimeToStr(admissionApplication.getDateOfBirth(), "MM/dd/yyyy"), 10, TextBoxWebControl.DATA_TYPE_DATE, "", "changeMonth: true, changeYear: true"));
		strBuff.append(new TextBoxWebControl().getTextBoxWebControl("col-sm-4", true, "Place Of Birth", "Place Of Birth", "PlaceOfBirth", admissionApplication.getPlaceOfBirth(), 180, WebControlBase.DATA_TYPE_STRING, ""));
		strBuff.append(new SelectWebControl().getSelectWebControl("col-sm-3", true, "Civil Status", "CivilStatus", AdmissionApplicationDTO.CIVIL_STATUS_LIST, admissionApplication.getCivilStatus(), AdmissionApplicationDTO.CIVIL_STATUS_LIST, "Select Civil Status", "", ""));
		strBuff.append(new SelectWebControl().getSelectWebControl("col-sm-3", true, "Sex", "Gender", AdmissionApplicationDTO.GENDER_LIST, admissionApplication.getGender(), AdmissionApplicationDTO.GENDER_LIST, "Select Gender", "", ""));
		
		strBuff.append(new SelectWebControl().getSelectWebControl("col-sm-2", true, "City", "PermanentAddressCity", cityList, admissionApplication.getPermanentAddressCity(), "Select City", "", "onchange='" + AdmissionApplicationDTO.ACTION_SELECT_ADDRESS_CITY + "()'"));
		strBuff.append(new SelectWebControl().getSelectWebControl("col-sm-2", true, "Barangay", "PermanentAddressBarangay", barangayList, admissionApplication.getPermanentAddressBarangay(), "Select Barangay", "", ""));
		strBuff.append(new TextBoxWebControl().getTextBoxWebControl("col-sm-4", true, "Purok/Street/Subdivision/Village", "Purok/Street/Subdivision/Village", "PermanentAddressDetails", admissionApplication.getPermanentAddressDetails(), 90, WebControlBase.DATA_TYPE_STRING, ""));
		strBuff.append(new TextBoxWebControl().getTextBoxWebControl("col-sm-2", true, "Email Address", "Email Address", "EmailAddress", admissionApplication.getEmailAddress(), 90, WebControlBase.DATA_TYPE_STRING, ""));
		strBuff.append(new TextBoxWebControl().getTextBoxWebControl("col-sm-2", true, "Cellphone No.", "Cellphone No.", "CPNumber", admissionApplication.getCpNumber(), 11, WebControlBase.DATA_TYPE_INTEGER, ""));

		strBuff.append(new TextBoxWebControl().getTextBoxWebControl("col-sm-6", true, "Last School Attended Name", "Last School Attended Name", "LastSchoolAttendedName", admissionApplication.getLastSchoolAttendedName(), 180, WebControlBase.DATA_TYPE_STRING, ""));
		strBuff.append(new TextBoxWebControl().getTextBoxWebControl("col-sm-2", true, "Year", "Last School Attended Year", "LastSchoolAttendedYear", String.valueOf(admissionApplication.getLastSchoolAttendedYear()), 4, WebControlBase.DATA_TYPE_STRING, ""));
		strBuff.append(new SelectWebControl().getSelectWebControl("col-sm-2", true, "City", "LastSchoolAttendedCity", cityList, admissionApplication.getLastSchoolAttendedCity(), "Select City", "", ""));
		strBuff.append(new SelectWebControl().getSelectWebControl("col-sm-2", true, "SHS Strand", "SHSStrand", shsStrandList, admissionApplication.getShsStrand(), "Select Strand", "0", ""));

		strBuff.append(new SelectWebControl().getSelectWebControl("col-sm-4", true, "Applicant Type", "ApplicantType", AdmissionApplicationDTO.APPLICANT_TYPE_LIST, String.valueOf( admissionApplication.getApplicantType()), AdmissionApplicationDTO.APPLICANT_TYPE_LIST, "Select Applicant Type", "", ""));
		strBuff.append(new SelectWebControl().getSelectWebControl("col-sm-4", true, "Prefered Program/Course", "AcademicProgram", academicProgramList, admissionApplication.getAcademicProgram(), "Select Program/Course", "", ""));

		strBuff.append("<div class='col-sm-12'><hr style='border: 1px solid black'></div>");
		strBuff.append("<div class='col-sm-12 text-center'><font style='color:blue'>Note: Please upload all three pictures in white background. For images more than 1Mb you may use photo resizer apps to reduce the size.</font><br><br></div>");
		
		strBuff.append(	new FileInputWebControl().getFileInputWebControl("col-sm-4", true, "2x2 Picture", "UploadedFilePict", uploadedFilePict));
		strBuff.append(	new FileInputWebControl().getFileInputWebControl("col-sm-4", true, "Applicant Signature", "UploadedFileSignatureApplicant", uploadedFileSignatureApplicant));
		strBuff.append(	new FileInputWebControl().getFileInputWebControl("col-sm-4", true, "Parent/Guardian Signature", "UploadedFileSignatureGuardian", uploadedFileSignatureGuardian));

		strBuff.append("<div class='col-sm-12'><br></div>");
		strBuff.append("<div class='col-sm-12 text-center'>");
		strBuff.append("	<button class='btn btn-primary' onclick=\"openLink('GH')\">Back</button>");
		strBuff.append("	<button id='btnSubmit' class='btn btn-primary' onclick='" + AdmissionApplicationDTO.ACTION_SUBMIT_APPLICATION + "()'>Submit</button>");
		strBuff.append("	<br><br><br>");
		strBuff.append("</div>");
		strBuff.append("</div>");
		return strBuff.toString();
	}
	
	public static List<DTOBase> getAdmissionApplicationListByExamScheduleCode(List<DTOBase> admissionApplicationList, String examScheduleCode) {
		List<DTOBase> list = new ArrayList<DTOBase>();
		for(DTOBase obj: admissionApplicationList) {
			AdmissionApplicationDTO admissionApplication = (AdmissionApplicationDTO) obj;
			if(admissionApplication.getExamScheduleCode().equalsIgnoreCase(examScheduleCode)) {
				list.add(admissionApplication);
			}
		}
		return list;
	}
}
