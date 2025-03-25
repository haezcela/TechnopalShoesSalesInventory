package com.laponhcet.student;

import java.io.Serializable;
import java.util.List;

import com.laponhcet.academicprogram.AcademicProgramDTO;
import com.laponhcet.academicsection.AcademicSectionDTO;
import com.laponhcet.enrollment.EnrollmentDTO;
import com.laponhcet.enrollment.EnrollmentUtil;
import com.mytechnopal.DataTable;
import com.mytechnopal.SessionInfo;
import com.mytechnopal.UploadedFile;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.base.WebControlBase;
import com.mytechnopal.dto.UserDTO;
import com.mytechnopal.identification.IdentificationDTO;
import com.mytechnopal.identification.IdentificationUtil;
import com.mytechnopal.util.DTOUtil;
import com.mytechnopal.util.QRCodeUtil;
import com.mytechnopal.util.WebUtil;
import com.mytechnopal.webcontrol.ComboBoxWebControl;
import com.mytechnopal.webcontrol.DataTableWebControl;
import com.mytechnopal.webcontrol.FileInputWebControl;
import com.mytechnopal.webcontrol.TextBoxWebControl;

public class StudentUtil implements Serializable {
	private static final long serialVersionUID = 1L;

	public static String getDataEntryStr(SessionInfo sessionInfo, EnrollmentDTO enrollment, List<DTOBase> academicSectionList, UploadedFile uploadedFile) {		
		StringBuffer strBuff = new StringBuffer();
		//ContactDTO contact = ContactUtil.getContactByContactTypeCode(enrollment.getStudent().getContactList(), ContactTypeDTO.CONTACT_TYPE_PHONE_CODE);
		String contactCPNumberStr = "";
		//if(contact != null) {
			//contactCPNumberStr = contact.getDetails();
		//}
		
		IdentificationDTO identification = IdentificationUtil.getIdentificationByType(enrollment.getStudent().getIdentificationList(), IdentificationDTO.TYPE_FACEKEEPER_ID);
		String identificationStr = "";
		if(identification != null) {
			identificationStr = identification.getDetails();
		}
		strBuff.append(new TextBoxWebControl().getTextBoxWebControl("col-sm-3 no-padding", true, "LastName", "LastName", "LastName", enrollment.getStudent().getLastName(), 180, WebControlBase.DATA_TYPE_STRING, "")); 
		strBuff.append(new TextBoxWebControl().getTextBoxWebControl("col-sm-4", true, "FirstName", "FirstName", "FirstName", enrollment.getStudent().getFirstName(), 180, WebControlBase.DATA_TYPE_STRING, "")); 
		strBuff.append(new TextBoxWebControl().getTextBoxWebControl("col-sm-3", false, "MiddleName", "MiddleName", "MiddleName", enrollment.getStudent().getMiddleName(), 180, WebControlBase.DATA_TYPE_STRING, "")); 
		strBuff.append(new ComboBoxWebControl().getComboBoxWebControl("col-sm-2", false, "Suffix", "SuffixName", UserDTO.SUFFIX_NAME_LIST, enrollment.getStudent().getSuffixName(), UserDTO.SUFFIX_NAME_LIST, "Select", "", "")); 
		
		strBuff.append(new TextBoxWebControl().getTextBoxWebControl("col-sm-4 no-padding", false, "FaceKeeper ID", "FaceKeeper ID", "FaceKeeperID", identificationStr, 10, WebControlBase.DATA_TYPE_STRING, "")); 
		strBuff.append(new TextBoxWebControl().getTextBoxWebControl("col-sm-4", false, "Contact CP#", "Contact CP#", "ContactCPNumber", contactCPNumberStr, 11, WebControlBase.DATA_TYPE_STRING, "")); 
		strBuff.append(new ComboBoxWebControl().getComboBoxWebControl("col-sm-4", true, "Section", "AcademicSection", academicSectionList, enrollment.getAcademicSection(), "Select", "", ""));
		
		strBuff.append("<div class='col-sm-12'><hr style='border: 1px solid black'></div>");
		//strBuff.append(	new FileInputWebControl().getFileInputWebControl("col-sm-12 no-padding", false, "", "UploadedFile", FileInputWebControl.FILE_TYPE_STANDARD, uploadedFile, ""));
		return strBuff.toString();
	}
	public static String getFaceKeeperID(StudentDTO student, SessionInfo sessionInfo) {
		StringBuffer strBuff = new StringBuffer();
		strBuff.append("<div id='divFKID' class='col-sm-12 white-bg p-sm m-b-md'>");
		strBuff.append("<style>");
		strBuff.append("	.id-bg {");
		strBuff.append("		background-color: white;");
		strBuff.append("		padding-bottom: 25px;");
		strBuff.append("	}");
		strBuff.append("	.id-card {");
		strBuff.append("		background-color: white;");
		strBuff.append("		width: 3.5in;");
		strBuff.append("		height: 3.5in;");
		strBuff.append("		border: 1px solid #d3d3d3;");
		strBuff.append("		display: inline-block;");
		strBuff.append("	}");
		strBuff.append("	.id-label {");
		strBuff.append("		width: 3.5in;");
		strBuff.append("		display: inline-block;");
		strBuff.append("		text-align: center;");
		strBuff.append("		color: black;");
		strBuff.append("		font-weight: 700;");
		strBuff.append("	}");
		strBuff.append("	.id-bg-image {");
		strBuff.append("		 margin-bottom: -160% !important;");
		strBuff.append("	}");
		strBuff.append("	.qr-card {");
		strBuff.append("		float: left  !important;");
		strBuff.append("    	width: 100%  !important;");
		strBuff.append("    	text-align: center;");
		strBuff.append("    	color: black;");
		strBuff.append("    	z-index: 9;");
		strBuff.append("    	position: relative;");
		strBuff.append("	}");
		strBuff.append("	.qr-code {");
		strBuff.append("		margin-left: 48% !important;");
		strBuff.append("	    transform: translate(-48%, 0px) !important;");
//		strBuff.append("	    margin-top: 1%  !important;");
//		strBuff.append("	    margin-bottom: 1%  !important;");
//		strBuff.append("	}");
		strBuff.append("</style>");
		strBuff.append("<div class='col-sm-12 p-sm m-b-lg id-bg'>");
		strBuff.append("	<div class='id-label'>QR Code</div>");
//		strBuff.append("	<div class='id-label'>Back</div>");
		strBuff.append("	<button onclick=\"printFKID('divFKID','FK_ID_" + student.getName(true, false, false).replace(" ", "_") + "')\" class='btn btn-primary btn-xs - m-l-md'><i class='fa fa-print'></i>&nbsp;Print</button><br>");
		strBuff.append("	<div class='id-card'>");
//		strBuff.append("		<div class='id-bg-image'>");
//		strBuff.append("			<img style='width: 100%  !important;' src='/static/common/facekeeperIDs/fk-id-front-" + sessionInfo.getSettings().getCode() + ".jpg'>");
//		strBuff.append("		</div>");
		strBuff.append("		<div class='qr-card '>");
		strBuff.append("			<img class='qr-code' src='" + QRCodeUtil.generateQRCodeBase64(student.getStudent().getCode(), 300, 300) + "'>");
		strBuff.append("			<b><font style='font-size:13px;margin-top:1px;'>" + student.getFirstName() + " " + (student.getMiddleInitial().length()>0?student.getMiddleInitial()+".":"") + " " + student.getLastName()+ "</font>" + "</b>");
		strBuff.append("		</div>");
		strBuff.append("	</div>");
//		strBuff.append("	<div class='id-card'>");
//		strBuff.append("			<img style='width: 100%' src='/static/common/facekeeperIDs/fk-id-back.jpg'>");
//		strBuff.append("	</div>");
		strBuff.append("</div>");
		strBuff.append("</div>");
		return strBuff.toString();
	}
	
	public static void setStudent(StudentDTO student, UserDTO user) {
		student.setUserName(user.getUserName());
		student.setPassword(user.getPassword());
		student.setCode(user.getCode());
		student.setLastName(user.getLastName());
		student.setFirstName(user.getFirstName());
		student.setMiddleName(user.getMiddleName());
		student.setSuffixName(user.getSuffixName());
		student.setGender(user.getGender());
		student.setUserContactList(user.getUserContactList());
		student.setUserMediaList(user.getUserMediaList());
	}
	
//	public static String getDataGridStr(SessionInfo sessionInfo, DataTable dataTable, List<DTOBase> enrollmentList, List<DTOBase> academicSectionList) {
//		DataTableWebControl dtwc = new DataTableWebControl(sessionInfo, dataTable);
//		StringBuffer strBuff = new StringBuffer();
//		if(dataTable.getRecordList().size() >= 1) {
//			strBuff.append(dtwc.getDataTableHeader(sessionInfo, dataTable));
//			strBuff.append("<div class='col-sm-12 no-padding'>");
//			strBuff.append("	<hr style='border-top: 3px dashed #bbb;'>");
//			strBuff.append("</div>");
//			strBuff.append(dtwc.getPageInfo());
//			strBuff.append("<div class='row'>");
//			for(int row=0; row < dataTable.getRecordListCurrentPage().size(); row++) {			
//				StudentDTO student = (StudentDTO) dataTable.getRecordListCurrentPage().get(row);
//				EnrollmentDTO enrollment = EnrollmentUtil.getEnrollmentByStudentCode(enrollmentList, student.getCode());
//				enrollment.setAcademicSection((AcademicSectionDTO) DTOUtil.getObjByCode(academicSectionList, enrollment.getAcademicSection().getCode()));
//				String enrollmentStr = enrollment == null?"No Enrollment Yet": getAcademicProgramYearSection(enrollment.getAcademicSection());
//				strBuff.append("<div class='col-lg-4'>");
//				strBuff.append("    <div class='contact-box center-version'>");
//				strBuff.append("		<a href='profile.html'>");
//				strBuff.append("			<img alt='image' class='img-circle' src='/static/" + sessionInfo.getSettings().getCode() + "/user/" + student.getCode() + ".png'>");
//				strBuff.append("			<h3 class='m-b-xs'><strong>" + WebUtil.getHTMLStr(student.getName(false, false, false)) + "</strong></h3>");
//				strBuff.append("			<div class='font-bold'>" + enrollmentStr + "</div>");
//				strBuff.append("		</a>");
//				strBuff.append("        <div class='contact-box-footer'>");
//				strBuff.append("            <div class='m-t-xs btn-group'>");
//				strBuff.append(				dataTable.getRecordButtonStr(sessionInfo, student.getCode()));
//				strBuff.append("            <button type='button' class='btn btn-circle btn-outline btn-info' onClick=\"viewQR('" + student.getCode() + "')\")><i class='fa fa-qrcode'></i></button>");
//				strBuff.append("            </div>");
//				strBuff.append("		</div>");
//				strBuff.append("	</div>");
//				strBuff.append("</div>");
//			}
//			strBuff.append("</div>");
//			strBuff.append(dtwc.getPageInfo());
//			strBuff.append(dtwc.getDataTableJS());
//		}
//		return strBuff.toString();
//	}
	
	public static void searchByName(DataTable dataTable, String searchValue, List<DTOBase> userList) {
		dataTable.setRecordListInvisible();
		for(DTOBase dto: dataTable.getRecordList()) {
			StudentDTO student = (StudentDTO) dto;
			UserDTO user = (UserDTO) DTOUtil.getObjByCode(userList, student.getCode());
			if(user.getName(false, false, false).contains(searchValue.toUpperCase())) {
				StudentUtil.setStudent(student, user);
				student.setVisible(true);
			}
		}
	}
	
//	public static String getAcademicProgramYearSection(AcademicSectionDTO academicSection) {
//		String gradeStr = academicSection.getAcademicProgram().getName();
//		if(academicSection.getAcademicProgram().getAcademicProgramSubGroup().getCode().equalsIgnoreCase(AcademicProgramSubGroupDTO.PS_CODE)) {
//			gradeStr += " Kinder " + academicSection.getYearLevel();
//		}
//		else {
//			if(academicSection.getAcademicProgram().getAcademicProgramSubGroup().getCode().equalsIgnoreCase(AcademicProgramSubGroupDTO.ELEM_CODE)) {
//				gradeStr += " Grade " + academicSection.getYearLevel();
//			}
//			else if(academicSection.getAcademicProgram().getAcademicProgramSubGroup().getCode().equalsIgnoreCase(AcademicProgramSubGroupDTO.JHS_CODE)) {
//				gradeStr += " Grade " + (academicSection.getYearLevel() + 6);
//			}
//			else if(academicSection.getAcademicProgram().getAcademicProgramSubGroup().getCode().equalsIgnoreCase(AcademicProgramSubGroupDTO.SHS_CODE)) {
//				gradeStr += " Grade " + (academicSection.getYearLevel() + 10);
//			}
//		}
//		gradeStr += "-" + academicSection.getName();
//
//		return gradeStr; 
//	}
//	
	public static void setStudentList(List<DTOBase> studentList, List<DTOBase> userList) {
		for(DTOBase studentObj: studentList) {
			StudentDTO student = (StudentDTO) studentObj;
			UserDTO user = (UserDTO) DTOUtil.getObjByCode(userList, student.getCode());
			setStudent(student, user);
			student.setDisplayStr(user.getName(false, false, true));
		}
	}
}
