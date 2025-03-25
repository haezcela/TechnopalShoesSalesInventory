package com.laponhcet.teacher;

import java.util.Date;

import com.mytechnopal.dto.UserDTO;

public class TeacherDTO extends UserDTO {
	private static final long serialVersionUID = 1L;

	public static final String SESSION_TEACHER = "SESSION_TEACHER";
	public static final String SESSION_TEACHER_LIST = "SESSION_TEACHER_LIST";
	public static final String SESSION_TEACHER_DATA_TABLE = "SESSION_TEACHER_DATA_TABLE";
	
	private Date dateHired;
	private TeacherStatusDTO teacherStatus;

	public TeacherDTO() {
		super();
		dateHired = null;
		teacherStatus = new TeacherStatusDTO();
	}
	
	public TeacherDTO getTeacher() {
		TeacherDTO teacher = new TeacherDTO();
		teacher.setId(super.getId());
		teacher.setCode(super.getCode());
		teacher.setDateHired(this.dateHired);
		teacher.setTeacherStatus(this.teacherStatus);
		return teacher;
	}

	public TeacherStatusDTO getTeacherStatus() {
		return teacherStatus;
	}

	public void setTeacherStatus(TeacherStatusDTO teacherStatus) {
		this.teacherStatus = teacherStatus;
	}

	public Date getDateHired() {
		return dateHired;
	}

	public void setDateHired(Date dateHired) {
		this.dateHired = dateHired;
	}
}