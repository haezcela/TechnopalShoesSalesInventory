package com.laponhcet.admissionapplication;

import com.mytechnopal.base.DTOBase;

public class AdmissionApplicationEMailDTO extends DTOBase {

	private static final long serialVersionUID = 1L;
	
	private String admissionApplicationCode;
	private boolean isSent;
	 

	public AdmissionApplicationEMailDTO() {
		super();
		admissionApplicationCode = "";
		isSent = false;
	}

	public AdmissionApplicationEMailDTO getAdmissionApplicationEMail() {
		AdmissionApplicationEMailDTO admissionApplicationEMail = new AdmissionApplicationEMailDTO();
		admissionApplicationEMail.setId(super.getId());
		admissionApplicationEMail.setAdmissionApplicationCode(admissionApplicationCode);
		admissionApplicationEMail.setSent(isSent);
		return admissionApplicationEMail;
	}

	public String getAdmissionApplicationCode() {
		return admissionApplicationCode;
	}


	public void setAdmissionApplicationCode(String admissionApplicationCode) {
		this.admissionApplicationCode = admissionApplicationCode;
	}


	public boolean isSent() {
		return isSent;
	}


	public void setSent(boolean isSent) {
		this.isSent = isSent;
	}
}
