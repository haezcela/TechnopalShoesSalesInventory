package com.laponhcet.guest;

import java.sql.Timestamp;

import com.mytechnopal.base.DTOBase;

public class RegisterDTO extends DTOBase {
	private static final long serialVersionUID = 1L;

	public static final String SESSION_REGISTER = "SESSION_REGISTER";
    public static final String SESSION_REGISTER_LIST = "SESSION_REGISTER_LIST";
    public static final String SESSION_REGISTER_DATA_TABLE = "SESSION_REGISTER_DATA_TABLE";
    
    public static final String GODADDY_EMAIL_SMTP_SERVER = "smtpout.secureserver.net";
    public static final String GODADDY_EMAIL_SMTP_SSL_PORT = "465";
    
    public static final String MYTECHNOPAL_SUPPORT_EMAIL_ADDRESS = "support@mytechnopal.com";
    public static final String MYTECHNOPAL_SUPPORT_PASSWORD = "tpsupport@2022";
    
	private String email;
	private String password;
	private String activationCode;
	private Timestamp activatedTimestamp;
	
	public RegisterDTO() {
		super();
		email = "";
		password = "";
		activationCode = "";
		activatedTimestamp = null;
	}
	
	public RegisterDTO getRegister() {
		RegisterDTO register = new RegisterDTO();
		register.setId(super.getId());
		register.setCode(super.getCode());
		register.setEmail(this.email);
		register.setPassword(this.password);
		register.setActivationCode(this.activationCode);
		register.setActivatedTimestamp(this.activatedTimestamp);
		return register;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getActivationCode() {
		return activationCode;
	}

	public void setActivationCode(String activationCode) {
		this.activationCode = activationCode;
	}

	public Timestamp getActivatedTimestamp() {
		return activatedTimestamp;
	}
	
	public void setActivatedTimestamp(Timestamp activatedTimestamp) {
		this.activatedTimestamp = activatedTimestamp;
	}
}
