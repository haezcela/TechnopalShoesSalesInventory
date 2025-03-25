package com.laponhcet.examschedule;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mytechnopal.base.DTOBase;

public class ExamScheduleDTO extends DTOBase {
	private static final long serialVersionUID = 1L;
	
	public static final String SESSION_EXAMSCHEDULE = "SESSION_EXAMSCHEDULE";
	public static final String SESSION_EXAMSCHEDULE_LIST = "SESSION_EXAMSCHEDULE_LIST";
	public static final String SESSION_EXAMSCHEDULE_DATATABLE = "SESSION_EXAMSCHEDULE_DATATABLE";
	
	public static final String ACTION_SEARCH_BY_DATE = "ACTION_SEARCH_BY_DATE";
	public static final String ACTION_SEARCH_BY_ROOM = "ACTION_SEARCH_BY_ROOM";
	
	public static final String ACTION_ADD_EXAMINEE = "ACTION_ADD_EXAMINEE";
	public static final String ACTION_VIEW_EXAMINEE = "ACTION_VIEW_EXAMINEE";
	public static final String ACTION_EMAIL_EXAMINEE = "ACTION_EMAIL_EXAMINEE";
	
	public static String STATUS_NO_EXAMINEE = "NO EXAMINEE YET";
	public static String STATUS_SCHEDULED = "SCHEDULED";
	public static String STATUS_EMAILED = "EMAILED";
		
	private int batch;
	private Timestamp dateTimeStart;
	private Timestamp dateTimeEnd;
	private String status;
	private int totalExaminee;
	private List<DTOBase> admissionApplicationList;
	
	public ExamScheduleDTO() {
		super();
		this.batch = 0;
		this.dateTimeStart = null;
		this.dateTimeEnd = null;
		this.status = STATUS_NO_EXAMINEE;
		this.totalExaminee = 0;
		this.admissionApplicationList = new ArrayList<DTOBase>();
	}
	
	public ExamScheduleDTO getExamScheduleDTO() {
		ExamScheduleDTO examSchedule = new ExamScheduleDTO();
		examSchedule.setId(super.getId());
		examSchedule.setCode(super.getCode());
		examSchedule.setBatch(batch);
		examSchedule.setDateTimeStart(dateTimeStart);
		examSchedule.setDateTimeEnd(dateTimeEnd);
		examSchedule.setStatus(status);
		examSchedule.setTotalExaminee(totalExaminee);
		examSchedule.setAdmissionApplicationList(admissionApplicationList);
		return examSchedule;
	}

	public int getBatch() {
		return batch;
	}

	public void setBatch(int batch) {
		this.batch = batch;
	}
	
	public Timestamp getDateTimeStart() {
		return dateTimeStart;
	}

	public void setDateTimeStart(Timestamp dateTimeStart) {
		this.dateTimeStart = dateTimeStart;
	}

	public Timestamp getDateTimeEnd() {
		return dateTimeEnd;
	}

	public void setDateTimeEnd(Timestamp dateTimeEnd) {
		this.dateTimeEnd = dateTimeEnd;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getTotalExaminee() {
		return totalExaminee;
	}

	public void setTotalExaminee(int totalExaminee) {
		this.totalExaminee = totalExaminee;
	}

	public List<DTOBase> getAdmissionApplicationList() {
		return admissionApplicationList;
	}

	public void setAdmissionApplicationList(List<DTOBase> admissionApplicationList) {
		this.admissionApplicationList = admissionApplicationList;
	}
}
