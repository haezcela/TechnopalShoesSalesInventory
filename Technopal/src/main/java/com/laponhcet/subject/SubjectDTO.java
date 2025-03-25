package com.laponhcet.subject;

import com.mytechnopal.base.DTOBase;

public class SubjectDTO extends DTOBase {

	private static final long serialVersionUID = 1L;

	public static final String SESSION_SUBJECT = "SESSION_SUBJECT";
	public static final String SESSION_SUBJECT_LIST = "SESSION_SUBJECT_LIST";
	public static final String SESSION_SUBJECT_DATA_TABLE = "SESSION_SUBJECT_DATA_TABLE";
	
	private String description;
	private double creditUnit;	
	private double payUnit;
	
	public SubjectDTO() {
		super();
		this.description = "";
		this.creditUnit = 0d;
		this.payUnit = 0d;
	}
	
	public SubjectDTO getSubject() {
		SubjectDTO subject = new SubjectDTO();
		subject.setId(super.getId());
		subject.setCode(super.getCode());
		subject.setDescription(this.description);
		subject.setCreditUnit(this.creditUnit);
		subject.setPayUnit(this.payUnit);
		return subject;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public double getCreditUnit() {
		return creditUnit;
	}
	
	public void setCreditUnit(double creditUnit) {
		this.creditUnit = creditUnit;
	}
	
	public double getPayUnit() {
		return payUnit;
	}
	
	public void setPayUnit(double payUnit) {
		this.payUnit = payUnit;
	}
}
