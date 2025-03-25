package com.laponhcet.teacher;

import com.mytechnopal.base.DTOBase;

public class TeacherStatusDTO extends DTOBase {
	private static final long serialVersionUID = 1L;

	public static final String SESSION_TEACHER_STATUS = "SESSION_TEACHER_STATUS";
	public static final String SESSION_TEACHER_STATUS_LIST = "SESSION_TEACHER_STATUS_LIST";
	public static final String SESSION_TEACHER_STATUS_DATA_TABLE = "SESSION_TEACHER_STATUS_DATA_TABLE";
	
	private String name;
	
	public TeacherStatusDTO() {
		super();
		this.name = "";
	}
	
	public TeacherStatusDTO getTeacherStatus() {
		TeacherStatusDTO teacherStatus = new TeacherStatusDTO();
		teacherStatus.setId(super.getId());
		teacherStatus.setCode(super.getCode());
		teacherStatus.setName(this.name);
		return teacherStatus;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}