package com.laponhcet.facelogreport;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.laponhcet.academicprogram.AcademicProgramUtil;
import com.laponhcet.academicsection.AcademicSectionUtil;
import com.laponhcet.enrollment.EnrollmentDTO;
import com.laponhcet.enrollment.EnrollmentUtil;
import com.mytechnopal.Calendar;
import com.mytechnopal.SessionInfo;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.facelog.FaceLogDTO;
import com.mytechnopal.util.DateTimeUtil;

public class FaceLogReportUtil implements Serializable {
	private static final long serialVersionUID = 1L;

	public static String getFaceLogListByTimeLogDate(SessionInfo sessionInfo, Calendar calendar, List<DTOBase> faceLogList,  List<DTOBase> enrollmentList) {
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
		
		if(faceLogList.size() >= 1) {
			strBuff.append("<div class='col-md-12'>");
			strBuff.append("	<table class='table' id='tbl" + DateTimeUtil.getDateTimeToStr(calendar.getDate(), "MMddyyyy") + "'> ");   
			strBuff.append("		<tr> ");
			strBuff.append("			<th scope='col'>#</th>");
			strBuff.append("			<th scope='col'>Name</th>");
			strBuff.append("			<th scope='col'>Program/Year Level</th>");
			strBuff.append("			<th scope='col'>Time In</th>");
			strBuff.append("		</tr> ");     
			int i=1;
			for(DTOBase faceLogObj: faceLogList) {
				FaceLogDTO faceLog = (FaceLogDTO)faceLogObj;
				EnrollmentDTO enrollment = EnrollmentUtil.getEnrollmentByStudentCode(enrollmentList, faceLog.getUser().getCode());
				if(enrollment != null) {
				strBuff.append("	<tr> ");
				strBuff.append("		<td>" + i++ + ". </td>");
				strBuff.append("		<td>" + enrollment.getStudent().getName(false, false, false) + "</td>");
				strBuff.append("		<td>" + AcademicSectionUtil.getYearLevelStr(enrollment.getAcademicSection()) + "</td>");
				strBuff.append("		<td>" + DateTimeUtil.getDateTimeToStr(faceLog.getTimeLog(), "hh:mm a") + "</td>");
				strBuff.append("	</tr> ");  
				}
			}
			strBuff.append("	</table> ");  
			strBuff.append("	<div class='col-md-12 text-center'><button type='button' class='btn btn-success mb-2' onclick=\"downloadTableAsCSV('tbl" + DateTimeUtil.getDateTimeToStr(calendar.getDate(), "MMddyyyy") + "', 'facelog_" + DateTimeUtil.getDateTimeToStr(calendar.getDate(), "MM-dd-yyyy") + ".csv')\">Download</button></div>");
			strBuff.append("</div> ");      
		}
		return strBuff.toString();
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
}
