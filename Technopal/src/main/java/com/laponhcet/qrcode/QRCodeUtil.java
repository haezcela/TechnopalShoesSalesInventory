package com.laponhcet.qrcode;

import java.io.Serializable;
import java.util.List;

import com.laponhcet.academicsection.AcademicSectionDTO;
import com.laponhcet.enrollment.EnrollmentDTO;
import com.laponhcet.enrollment.EnrollmentUtil;
import com.mytechnopal.DataTable;
import com.mytechnopal.SessionInfo;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.dto.UserDTO;
import com.mytechnopal.util.DTOUtil;
import com.mytechnopal.util.WebUtil;
import com.mytechnopal.webcontrol.DataTableWebControl;

public class QRCodeUtil implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private static String[][] getDataTableCurrentPageRecordArr(SessionInfo sessionInfo, DataTable dataTable, List<DTOBase> enrollmentList, List<DTOBase> academicSectionList) {
		String[][] strArr = new String[dataTable.getRecordListCurrentPage().size()][dataTable.getColumnNameArr().length];
		for(int row=0; row < dataTable.getRecordListCurrentPage().size(); row++) {			
			UserDTO user = (UserDTO) dataTable.getRecordListCurrentPage().get(row);
			EnrollmentDTO enrollment = EnrollmentUtil.getEnrollmentByStudentCodeAcademicYearCode(enrollmentList, user.getCode(), "001");
			String academicProgramSectionStr = "";
			if(enrollment != null) {
				enrollment.setAcademicSection((AcademicSectionDTO) DTOUtil.getObjByCode(academicSectionList, enrollment.getAcademicSection().getCode()));
				academicProgramSectionStr = enrollment.getAcademicSection().getDisplayStr();
			}
			
			strArr[row][0] = user.getCode();
			strArr[row][1] = WebUtil.getHTMLStr(user.getLastName());
			strArr[row][2] = WebUtil.getHTMLStr(user.getFirstName());
			strArr[row][3] = WebUtil.getHTMLStr(user.getMiddleName());
			strArr[row][4] = academicProgramSectionStr;
			strArr[row][5] = "<button type='button' class='btn btn-primary' data-toggle='tooltip' data-placement='top' title='Edit Record' onClick=\"viewQRCode('" + user.getCode() + "')\"><i class='fa fa-plus'></i></button>";;
		}
		return strArr;
	}
	
	public static String getDataTableStr(SessionInfo sessionInfo, DataTable dataTable, List<DTOBase> userListQRCode, List<DTOBase> enrollmentList, List<DTOBase> academicSectionList) {
		DataTableWebControl dtwc = new DataTableWebControl(sessionInfo, dataTable);
		StringBuffer strBuff = new StringBuffer();
		if(dataTable.getRecordList().size() >= 1) {
			strBuff.append(dtwc.getDataTableHeader(sessionInfo, dataTable));
			dataTable.setDataTableRecordArr(getDataTableCurrentPageRecordArr(sessionInfo, dataTable, enrollmentList, academicSectionList));
			strBuff.append(dtwc.getDataTable(true, false));
		}
		if(userListQRCode.size() >= 1) {
			strBuff.append("<hr>");
			strBuff.append("<div class='row' id='divUserListQRCode'>");
			for(DTOBase userObj: userListQRCode) {
				UserDTO user = (UserDTO) userObj;
				strBuff.append("<div class='col-sm-3 text-center'>");
				strBuff.append("	<img src='" + com.mytechnopal.util.QRCodeUtil.generateQRCodeBase64(user.getCode(), 120, 120) + "'>");
				strBuff.append("	<br><small>" + user.getName(false, false, true) + "</small><br><br>");
				strBuff.append("</div>");
			}
			strBuff.append("</div>");
			strBuff.append("<div class='col-lg-12 text-center'><br><br><button class='btn btn-warning' onclick='clearItems()'>Clear</button>&nbsp;<button class='btn btn-primary' onclick=\"printPreview('divUserListQRCode')\">Print</button></div>");
		}
		return strBuff.toString();
	}
	
	public static void searchByName(DataTable dataTable, String searchValue, List<DTOBase> userList) {
		for(DTOBase dto: dataTable.getRecordList()) {
			UserDTO user = (UserDTO) dto;
			if(user.getName(false, false, false).contains(searchValue.toUpperCase())) {
				user.setVisible(true);
			}
		}
	}
}
