package com.laponhcet.examschedule;

import java.io.Serializable;

import com.laponhcet.admissionapplication.AdmissionApplicationDTO;
import com.laponhcet.admissionapplication.SortLastNameAscending;
import com.mytechnopal.DataTable;
import com.mytechnopal.SessionInfo;
import com.mytechnopal.util.DateTimeUtil;
import com.mytechnopal.webcontrol.DataTableWebControl;


public class ExamScheduleUtil implements Serializable {
	private static final long serialVersionUID = 1L;

	public static String getDataTableStr(SessionInfo sessionInfo, DataTable dataTable) {
		DataTableWebControl dtwc = new DataTableWebControl(sessionInfo, dataTable);
		StringBuffer strBuff = new StringBuffer();
		if(dataTable.getRecordList().size() >= 1) {
			dataTable.setDataTableRecordArr(getDataTableCurrentPageRecordArr(sessionInfo, dataTable));
			strBuff.append(dtwc.getDataTable(true, false));
		}
		return strBuff.toString();
	}
	
	private static String[][] getDataTableCurrentPageRecordArr(SessionInfo sessionInfo, DataTable dataTable) {
		String[][] strArr = new String[dataTable.getRecordListCurrentPage().size()][dataTable.getColumnNameArr().length];
		for(int row=0; row < dataTable.getRecordListCurrentPage().size(); row++) {			
			ExamScheduleDTO examSchedule = (ExamScheduleDTO) dataTable.getRecordListCurrentPage().get(row);
			strArr[row][0] = "Batch" + examSchedule.getBatch();
			strArr[row][1] = DateTimeUtil.getDateTimeToStr(examSchedule.getDateTimeStart(), "MMM dd, yyyy hh:mm a");
			strArr[row][2] = DateTimeUtil.getDateTimeToStr(examSchedule.getDateTimeEnd(), "MMM dd, yyyy hh:mm a");
			strArr[row][3] = examSchedule.getStatus();
			strArr[row][4] = String.valueOf(examSchedule.getTotalExaminee());
			if(examSchedule.getStatus().equalsIgnoreCase(ExamScheduleDTO.STATUS_NO_EXAMINEE)) {
//				strArr[row][4] = "<input type='text' id='txtTotalExaminee" + examSchedule.getCode() + "' name='txtTotalExaminee" + examSchedule.getCode() + "' class='form-control text-right' placeholder='0' onKeyPress='return numbersonly(this, event, true, 9999)'  />";
				strArr[row][5] = "<button type='button' class='btn btn-success' data-toggle='tooltip' data-placement='top' title='Add Examinee' onClick=\"addExaminee('" + examSchedule.getCode() + "')\"><i class='fa fa-plus' aria-hidden='true'></i></button>";
				strArr[row][6] = "Click Plus button to Add Examinees";
			}
			else if(examSchedule.getStatus().equalsIgnoreCase(ExamScheduleDTO.STATUS_SCHEDULED)) {
				strArr[row][5] = "<button type='button' class='btn btn-secondary' data-toggle='tooltip' data-placement='top' title='View Examinee' onClick=\"viewExaminee('" + examSchedule.getCode() + "')\"><i class='fa fa-search' aria-hidden='true'></i></button>";
				strArr[row][6] = "<div id='divEmailButton'><button type='button' class='btn btn-warning' data-toggle='tooltip' data-placement='top' title='Send Email' onClick=\"emailExaminee('" + examSchedule.getCode() + "')\"><i class='fa fa-envelope' aria-hidden='true'></i></button></div>";
			}
			else {
				strArr[row][5] = "<button type='button' class='btn btn-secondary' data-toggle='tooltip' data-placement='top' title='View Examinee' onClick=\"viewExaminee('" + examSchedule.getCode() + "')\"><i class='fa fa-search' aria-hidden='true'></i></button>";
				strArr[row][6] = "Email Sent";
			}
		}
		return strArr;
	}
	
//	private static String[][] getDataTableCurrentPageRecordArr(SessionInfo sessionInfo, DataTable dataTable) {
//		String[][] strArr = new String[dataTable.getRecordListCurrentPage().size()][dataTable.getColumnNameArr().length];
//		for(int row=0; row < dataTable.getRecordListCurrentPage().size(); row++) {			
//			ExamScheduleDTO examSchedule = (ExamScheduleDTO) dataTable.getRecordListCurrentPage().get(row);
//			strArr[row][0] = examSchedule.getVenue().getName();
//			
//			strArr[row][3] = examSchedule.getStatus();
//			
//			String btnStr = "<button type='button' class='btn btn-success' data-toggle='tooltip' data-placement='top' title='View Examinee' onClick=\"viewExaminee('" + examSchedule.getCode() + "')\"><i class='fa fa-search'></i></button>";
//			if(examSchedule.getStatus().equalsIgnoreCase(ExamScheduleDTO.STATUS_NO_EXAMINEE)) {
//				btnStr = "<button type='button' class='btn btn-success' data-toggle='tooltip' data-placement='top' title='Add Examinee' onClick=\"addExaminee('" + examSchedule.getCode() + "')\"><i class='fa fa-plus'></i></button>";
//			}
//			strArr[row][4] = btnStr;
//
//		}
//		return strArr;
//	}
	
	public static String getViewExamineeStr(SessionInfo sessionInfo, ExamScheduleDTO examSchedule) {
		new SortLastNameAscending().sort(examSchedule.getAdmissionApplicationList());
		StringBuffer strBuff = new StringBuffer();
		int totalRecordPerPage = 60;
		int totalPage = examSchedule.getAdmissionApplicationList().size()%totalRecordPerPage==0?examSchedule.getAdmissionApplicationList().size()/totalRecordPerPage:examSchedule.getAdmissionApplicationList().size()/totalRecordPerPage+1;
		int recordStart = 0;
		int recordEnd = 0;
		int totalColumn = 2;
		int ctr = 1;
		strBuff.append("<div id='divExaminee" + examSchedule.getCode() + "'>");
		for(int p=1; p<=totalPage; p++) {
		strBuff.append("<div class='col-lg-12'>");
		strBuff.append("	<div class='text-center'>");
		strBuff.append("		<h4>Bago City College - Guidance Office<br>List of Applicants for Examination" + "<br>" + DateTimeUtil.getDateTimeToStr(examSchedule.getDateTimeStart(), "MMM dd, yyyy") + " | " + DateTimeUtil.getDateTimeToStr(examSchedule.getDateTimeStart(), "hh:mm a") + "-" + DateTimeUtil.getDateTimeToStr(examSchedule.getDateTimeEnd(), "hh:mm a") + " | Batch " + examSchedule.getBatch());
		strBuff.append("	</div>");
		strBuff.append("	<table width='100%'>");
		strBuff.append("		<tr>");
		for(int col=1; col<=totalColumn; col++) {
			recordStart = recordEnd+1;
			recordEnd = recordStart+29;
			if(recordEnd > examSchedule.getAdmissionApplicationList().size()) {
				recordEnd = examSchedule.getAdmissionApplicationList().size();
				col = totalColumn+1;
			}
			strBuff.append("		<td width='48%' valign='top'>");
			strBuff.append("			<table>");
			strBuff.append("				<tr>");
			strBuff.append("					<th></th>");
			strBuff.append("					<th>Last Name</th>");
			strBuff.append("					<th>First Name</th>");
			strBuff.append("					<th>Middle Name</th>");
			strBuff.append("					<th>Reference</th>");
			strBuff.append("				</tr>");
			for(int i=recordStart; i<=recordEnd; i++) {
				AdmissionApplicationDTO admissionApplication = (AdmissionApplicationDTO)examSchedule.getAdmissionApplicationList().get(i-1);
				strBuff.append("			<tr>");
				strBuff.append("				<td width='10%'><small>" + ctr++ + "</small>.</td>");
				strBuff.append("				<td width='25%'><small>" + admissionApplication.getLastName().replace("?", "Ñ") + "</small></td>");
				strBuff.append("				<td width='25%'><small>" + admissionApplication.getFirstName().replace("?", "Ñ") + "</small></td>");
				strBuff.append("				<td width='25%'><small>" + admissionApplication.getMiddleName().replace("?", "Ñ") + "</small></td>");
				strBuff.append("				<td width='15%'><small>" + admissionApplication.getCode() + "</small></td>");
				strBuff.append("			</tr>");
			}
			strBuff.append("			</table>");
			strBuff.append("		</td>");
				if(col==1) {
					strBuff.append("<td width='4%' valign='top'>&nbsp;</td>");
				}
			}
		strBuff.append("		</tr>");
		strBuff.append("	</table>");
		strBuff.append("</div>");
		strBuff.append("<div style='page-break-after:always'><br></div>");
		}
		strBuff.append("</div>");
		return strBuff.toString();
	}
}