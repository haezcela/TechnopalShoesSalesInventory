package com.laponhcet.schedule;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.laponhcet.academicprogram.AcademicProgramDTO;
import com.laponhcet.academicsection.AcademicSectionUtil;
import com.mytechnopal.SessionInfo;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.base.WebControlBase;
import com.mytechnopal.util.DateTimeUtil;
import com.mytechnopal.util.StringUtil;
import com.mytechnopal.webcontrol.CheckBoxWebControl;
import com.mytechnopal.webcontrol.ComboBoxWebControl;
import com.mytechnopal.webcontrol.SelectWebControl;
import com.mytechnopal.webcontrol.TextBoxWebControl;

public class ScheduleUtil implements Serializable {

	private static final long serialVersionUID = 1L;

	public static String getDataEntryStr(SessionInfo sessionInfo, ScheduleDTO schedule, List<DTOBase> academicYearList, List<DTOBase> semesterList, List<DTOBase> academicSectionList, List<DTOBase> scheduleList) {
		StringBuffer strBuff = new StringBuffer();
		strBuff.append("<div class='row'>");
		strBuff.append(new SelectWebControl().getSelectWebControl("col-lg-4", true, "Section", "AcademicSection", academicSectionList, schedule.getAcademicSection(), "-Select-", "0", "onchange='selectAcademicSection()'"));
		
		if(!StringUtil.isEmpty(schedule.getAcademicSection().getCode())) {
			if(schedule.getAcademicSection().getAcademicProgram().getPeriodType().equalsIgnoreCase(AcademicProgramDTO.PERIOD_TYPE_ACADEMICYEAR)) {
				strBuff.append(new SelectWebControl().getSelectWebControl("col-lg-4", true, "Academic year", "AcademicYear", academicYearList, schedule.getSemester().getAcademicYear(), "-Select-", "0", ""));
			}
			else if(schedule.getAcademicSection().getAcademicProgram().getPeriodType().equalsIgnoreCase(AcademicProgramDTO.PERIOD_TYPE_SEMESTER)) {
				strBuff.append(new SelectWebControl().getSelectWebControl("col-lg-4", true, "Semester", "Semester", semesterList, schedule.getSemester(), "-Select-", "0", ""));
			}
			strBuff.append("<div class='col-lg-4'><br><button type='button' class='btn btn-primary btn-sm mb-2' onclick='viewSchedule()'>View</button></div>");
			if(scheduleList.size()>=1) {
				strBuff.append("<div class='pt-5 mb-5'>");
				strBuff.append("	<div class='row'>");
				for(DTOBase scheduleObj: scheduleList) {
					ScheduleDTO sched = (ScheduleDTO) scheduleObj;
					strBuff.append("	<div class='py-2 col-lg-4'>");
					strBuff.append("		<div class='card border text-center'>");
					strBuff.append("			<div class='card-header'>");
					strBuff.append(					sched.getSubject().getCode());
					strBuff.append("			</div>");
					strBuff.append("    		<div class='card-body'>");
					strBuff.append("      			<a href='#' onclick=\"viewSubjectSchedule('" + sched.getSubject().getCode() + "')\"><img height='30px' width='40px' src='/static/common/images/date-and-time-icon.png'></a>");
					strBuff.append("				&nbsp; " + getTeacher(sched) + "");
					strBuff.append("      		</div>");
					strBuff.append("     		<div class='card-footer text-muted'>");
					strBuff.append(      			sched.getSubject().getDescription());
					strBuff.append("      		</div>");
					strBuff.append("		</div>");
					strBuff.append("	</div>");

				}
				strBuff.append("	</div>");
				strBuff.append("</div>");
			}
		}
		strBuff.append("</div>");
		return strBuff.toString();
	}
	
	public static String getSubjectScheduleStr(ScheduleDTO schedule) {
		int timeInterval = 10;
		Date date1 = DateTimeUtil.getStrToDateTime("07:30 am", "hh:mm a");
		Date date2 = DateTimeUtil.getStrToDateTime("5:00 pm", "hh:mm a");
		List<Date> timeList = DateTimeUtil.getTimeList(date1, date2, timeInterval);
		
		StringBuffer strBuff = new StringBuffer();
		strBuff.append("<div class='modal-dialog modal-xl'>");
		strBuff.append("	<div class='modal-content'>");
		strBuff.append("		<div class='modal-header'>");
		strBuff.append("			<h5 class='modal-title'>" + AcademicSectionUtil.getYearLevelSectionStr(schedule.getAcademicSection()) + ": " + schedule.getSubject().getDescription() + "</h5>");
		strBuff.append("			<button type='button' class='btn-close' data-bs-dismiss='modal' aria-label='Close'></button>");
		strBuff.append("		</div>");
		strBuff.append("		<div class='modal-body'>");
		strBuff.append("			<div class='row'>");
		strBuff.append(					new CheckBoxWebControl().getCheckBoxWebControl("col-sm-8", true, "Day(s)", "Day", ScheduleDetailsDTO.DAY_CODE_LIST, schedule.getScheduleDetails().getDay().split("~"), ScheduleDetailsDTO.DAY_NAME_LIST, "onclick=\"getDayStr()\""));
		strBuff.append(					new SelectWebControl().getSelectWebControl("col-sm-2", true, "Start Time", "StartTime", DateTimeUtil.getTimeListStrArr(timeList, "hh:mm a", false), DateTimeUtil.getDateTimeToStr(schedule.getScheduleDetails().getStartTime(), "hh:mm a"), DateTimeUtil.getTimeListStrArr(timeList, "hh:mm a", false), "", "", ""));
		strBuff.append(					new SelectWebControl().getSelectWebControl("col-sm-2", true, "End Time", "EndTime", DateTimeUtil.getTimeListStrArr(timeList, "hh:mm a", false), DateTimeUtil.getDateTimeToStr(schedule.getScheduleDetails().getEndTime(), "hh:mm a"), DateTimeUtil.getTimeListStrArr(timeList, "hh:mm a", false), "", "", ""));
		strBuff.append(					new TextBoxWebControl().getTextBoxWebControl("col-sm-6 autocomplete", true, "Teacher", "Teacher", "Teacher", "", 135, WebControlBase.DATA_TYPE_STRING, "autoCompleteCheckInput(this)", ""));
		strBuff.append(					new TextBoxWebControl().getTextBoxWebControl("col-sm-6 autocomplete", true, "Room/Venue", "Venue", "Venue", "", 20, WebControlBase.DATA_TYPE_STRING, "autoCompleteCheckInput(this)", ""));
		strBuff.append("				<input type='hidden' id='txtTeacherCode'>");
		strBuff.append("				<input type='hidden' id='txtVenueCode'>");
		strBuff.append("			</div>");
		strBuff.append("		</div>");
		strBuff.append("		<div class='modal-footer'>");
		strBuff.append("			<button type='button' class='btn btn-primary' onclick='addScheduleDetails()'>Add</button>");
		strBuff.append("			<button type='button' class='btn btn-secondary' data-bs-dismiss='modal'>Close</button>");
		strBuff.append("		</div>");
		strBuff.append("	</div>");
		strBuff.append("</div>");
		return strBuff.toString();
	}
	
	public static String getScheduleCode(ScheduleDTO schedule) {
		return schedule.getSemester().getAcademicYear().getCode() + schedule.getAcademicSection().getCode() + schedule.getSubject().getCode();
	}
	
	public static ScheduleDTO getScheduleBySubjectCode(List<DTOBase> scheduleList, String subjectCode) {
		for(DTOBase scheduleObj: scheduleList) {
			ScheduleDTO sched = (ScheduleDTO) scheduleObj;
			if(sched.getSubject().getCode().equalsIgnoreCase(subjectCode)) {
				return sched;
			}
		}
		return null;
	}
	
	public static String getTeacher(ScheduleDTO schedule) {
		String str = "";
		for(DTOBase scheduleDetailsObj: schedule.getScheduleDetailsList()) {
			ScheduleDetailsDTO scheduleDetails = (ScheduleDetailsDTO) scheduleDetailsObj;
			if(!StringUtil.isEmpty(str)) {
				str += "<br>";
			}
			str += "<small>" + scheduleDetails.getTeacher().getDisplayStr() + "&nbsp;<i onclick=\"deleteScheduleDetails('" + schedule.getCode() + "', " + scheduleDetails.getId() + ")\" class='fas fa-trash-alt' style='font-size: 1rem; cursor: pointer;'></i></small>";
		}
		return str;
	}
}
