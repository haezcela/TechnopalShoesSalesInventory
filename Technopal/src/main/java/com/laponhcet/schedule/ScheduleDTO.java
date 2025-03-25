package com.laponhcet.schedule;

import java.util.ArrayList;
import java.util.List;

import com.laponhcet.academicsection.AcademicSectionDTO;
import com.laponhcet.semester.SemesterDTO;
import com.laponhcet.subject.SubjectDTO;
import com.mytechnopal.base.DTOBase;

public class ScheduleDTO extends DTOBase {
	private static final long serialVersionUID = 1L;
	
	public static final String SESSION_SCHEDULE = "SESSION_SCHEDULE";
	public static final String SESSION_SCHEDULE_LIST = "SESSION_SCHEDULE_LIST";
	public static final String SESSION_SCHEDULE_DATA_TABLE = "SESSION_SCHEDULE_DATA_TABLE";
	
	private AcademicSectionDTO academicSection;
	private SemesterDTO semester;
	private SubjectDTO subject;
	private int maxEnrollee;
	private DTOBase mergeToSchedule;
	
	private ScheduleDetailsDTO scheduleDetails;
	private List<DTOBase> scheduleDetailsList;
	
	public ScheduleDTO() {
		super();
		academicSection = new AcademicSectionDTO();
		semester = new SemesterDTO();
		subject = new SubjectDTO();
		maxEnrollee = 0;
		mergeToSchedule = new DTOBase();
		
		scheduleDetails = new ScheduleDetailsDTO();
		scheduleDetailsList = new ArrayList<DTOBase>();
	}
	
	public ScheduleDTO getSchedule() {
		ScheduleDTO schedule = new ScheduleDTO();
		schedule.setId(super.getId());
		schedule.setCode(super.getCode());
		schedule.setAcademicSection(this.academicSection);
		schedule.setSemester(this.semester);
		schedule.setSubject(this.subject);
		schedule.setMaxEnrollee(this.maxEnrollee);
		schedule.setMergeToSchedule(this.mergeToSchedule);
		schedule.setScheduleDetailsList(this.scheduleDetailsList);
		schedule.setScheduleDetails(this.scheduleDetails);
		return schedule;
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

	public SubjectDTO getSubject() {
		return subject;
	}

	public void setSubject(SubjectDTO subject) {
		this.subject = subject;
	}

	public int getMaxEnrollee() {
		return maxEnrollee;
	}

	public void setMaxEnrollee(int maxEnrollee) {
		this.maxEnrollee = maxEnrollee;
	}

	public DTOBase getMergeToSchedule() {
		return mergeToSchedule;
	}

	public void setMergeToSchedule(DTOBase mergeToSchedule) {
		this.mergeToSchedule = mergeToSchedule;
	}

	public List<DTOBase> getScheduleDetailsList() {
		return scheduleDetailsList;
	}

	public void setScheduleDetailsList(List<DTOBase> scheduleDetailsList) {
		this.scheduleDetailsList = scheduleDetailsList;
	}

	public ScheduleDetailsDTO getScheduleDetails() {
		return scheduleDetails;
	}

	public void setScheduleDetails(ScheduleDetailsDTO scheduleDetails) {
		this.scheduleDetails = scheduleDetails;
	}
}