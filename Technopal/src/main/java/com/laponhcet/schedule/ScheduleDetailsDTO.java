package com.laponhcet.schedule;

import java.util.Date;

import com.laponhcet.academicsection.AcademicSectionDTO;
import com.laponhcet.semester.SemesterDTO;
import com.laponhcet.teacher.TeacherDTO;
import com.laponhcet.venue.VenueDTO;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.util.DateTimeUtil;
import com.mytechnopal.util.StringUtil;

public class ScheduleDetailsDTO extends DTOBase {
	private static final long serialVersionUID = 1L;

	public static final String SESSION_SCHEDULE_DETAILS = "SESSION_SCHEDULE_DETAILS";
	public static final String SESSION_SCHEDULE_DETAILS_LIST = "SESSION_SCHEDULE_DETAILS_LIST";
	
	public static final String[] DAY_CODE_LIST = new String[] {"M", "T", "W", "TH", "F", "SAT", "SUN"};
	public static final String[] DAY_NAME_LIST = new String[] {"MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN"};
	
	private AcademicSectionDTO academicSection;
	private SemesterDTO semester;
	private String scheduleCode;
	private String day;
	private Date startTime;
	private Date endTime;
	private VenueDTO venue;
	private TeacherDTO teacher;
	
	public ScheduleDetailsDTO() {
		super();
		academicSection = new AcademicSectionDTO();
		semester = new SemesterDTO();
		scheduleCode = "";
		day = "";
		startTime = DateTimeUtil.getCurrentTimestamp();
		endTime = DateTimeUtil.getCurrentTimestamp();
		venue = new VenueDTO();
		teacher = new TeacherDTO();
	}
	
	public ScheduleDetailsDTO getScheduleDetails() {
		ScheduleDetailsDTO scheduleDetails = new ScheduleDetailsDTO();
		scheduleDetails.setId(super.getId());
		scheduleDetails.setAcademicSection(this.academicSection);
		scheduleDetails.setSemester(this.semester);
		scheduleDetails.setScheduleCode(this.scheduleCode);
		scheduleDetails.setDay(this.day);
		scheduleDetails.setStartTime(this.startTime);
		scheduleDetails.setEndTime(this.endTime);
		scheduleDetails.setVenue(this.venue);
		scheduleDetails.setTeacher(this.teacher);
		return scheduleDetails;
	}

	public String getDayTimeStr() {
		String str = StringUtil.getStrFromStrArr(day.split("~"), "/");
		str += " | ";
		str += DateTimeUtil.isTimeTBA(startTime)?"TBA":DateTimeUtil.getDateTimeToStr(startTime, "hh:mm a");
		str += "-";
		str += DateTimeUtil.isTimeTBA(endTime)?"TBA":DateTimeUtil.getDateTimeToStr(endTime, "hh:mm a");
		return str;
	}
	
	public String getDayTimeVenueTeacherStr() {
		String str = StringUtil.getStrFromStrArr(day.split("~"), "/");
		str += " | ";
		str += DateTimeUtil.isTimeTBA(startTime)?"TBA":DateTimeUtil.getDateTimeToStr(startTime, "hh:mm a");
		str += "-";
		str += DateTimeUtil.isTimeTBA(endTime)?"TBA":DateTimeUtil.getDateTimeToStr(endTime, "hh:mm a");
		str += " | ";
		str += getVenue().getName();
		str += " | ";
		str += getTeacher().getName(false, false, true);
		return str;
	}
	
	public AcademicSectionDTO getAcademicSection() {
		return academicSection;
	}

	public void setAcademicSection(AcademicSectionDTO academicSection) {
		this.academicSection = academicSection;
	}

	public SemesterDTO getSemester() {
		return semester;
	}

	public void setSemester(SemesterDTO semester) {
		this.semester = semester;
	}

	public String getScheduleCode() {
		return scheduleCode;
	}

	public void setScheduleCode(String scheduleCode) {
		this.scheduleCode = scheduleCode;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public VenueDTO getVenue() {
		return venue;
	}

	public void setVenue(VenueDTO venue) {
		this.venue = venue;
	}

	public TeacherDTO getTeacher() {
		return teacher;
	}

	public void setTeacher(TeacherDTO teacher) {
		this.teacher = teacher;
	}
}
