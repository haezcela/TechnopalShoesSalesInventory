package com.laponhcet.admissionapplication;

import java.util.Date;

import com.laponhcet.academicprogram.AcademicProgramDTO;
import com.laponhcet.semester.SemesterDTO;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.dto.BarangayDTO;
import com.mytechnopal.dto.CityDTO;
import com.mytechnopal.dto.SHSStrandDTO;
import com.mytechnopal.util.DateTimeUtil;

public class AdmissionApplicationDTO extends DTOBase {
	
	private static final long serialVersionUID = 1L;
	
	public static final String SESSION_ADMISSION_APPLICATION = "SESSION_ADMISSION_APPLICATION";
	public static final String SESSION_ADMISSION_APPLICATION_LIST = "SESSION_ADMISSION_APPLICATION_LIST";
	public static final String SESSION_ADMISSION_APPLICATION_DATA_TABLE = "SESSION_ADMISSION_APPLICATION_DATA_TABLE";
	
	public static final String[] GENDER_LIST = {"Male", "Female"};
	public static final String[] CIVIL_STATUS_LIST = {"Single", "Married", "Seperated", "Divorced", "Widowed", "Common-Law"};
	public static final String[] APPLICANT_TYPE_LIST = {"Freshmen", "Transferee", "Returnee"};
	public static final String[] SEMESTER_LIST = {"First", "Second"};
	
	public static final String ACTION_PREPARE_APPLICATION = "ACTION_PREPARE_APPLICATION";
	public static final String ACTION_SELECT_ADDRESS_CITY = "ACTION_SELECT_ADDRESS_CITY";
	public static final String ACTION_SUBMIT_APPLICATION = "ACTION_SUBMIT_APPLICATION";
	public static final String ACTION_UPDATE_APPLICATION_STATUS_EXAM_DONE = "ACTION_UPDATE_APPLICATION_STATUS_EXAM_DONE";
	public static final String ACTION_UPDATE_APPLICATION_STATUS_REJECT = "ACTION_UPDATE_APPLICATION_STATUS_REJECT";
	public static final String ACTION_REPORT_VIEW_APPLICATIONLIST = "ACTION_REPORT_VIEW_APPLICATIONLIST";
	
	public static final String ACTION_SEARCH_BY_NAME = "Name";
	public static final String ACTION_SEARCH_BY_CODE = "Code"; 
	
	public static final String STATUS_SUBMITTED = "SUBMITTED";
	public static final String STATUS_FOR_EXAMNINATION = "FOR EXAMINATION";
	public static final String STATUS_EXAM_DONE = "EXAM DONE";
	public static final String STATUS_APPLICATION_REJECTED = "APPLICATION REJECTED";
	
	private String lastName;
	private String firstName;
	private String middleName;
	private Date dateOfBirth;
	private String placeOfBirth;
	private String gender;
	private String civilStatus;
	private CityDTO permanentAddressCity;
	private BarangayDTO permanentAddressBarangay;
	private String permanentAddressDetails;
	private String emailAddress;
	private String cpNumber;
	private String lastSchoolAttendedName;
	private SHSStrandDTO shsStrand;
	private int lastSchoolAttendedYear;
	private CityDTO lastSchoolAttendedCity;
	private String applicantType;
	private SemesterDTO semester;
	private AcademicProgramDTO academicProgram;
	private String pict;
	private String signatureApplicant;
	private String signatureGuardian;
	private String status;
	private String examScheduleCode;
	private String lrn;
	
	public AdmissionApplicationDTO() {
		super();
		this.firstName = "";
		this.middleName = "";
		this.lastName = "";
		this.dateOfBirth = DateTimeUtil.addYear(DateTimeUtil.getCurrentTimestamp(), -18);
		this.gender = GENDER_LIST[0];
		this.placeOfBirth = "";
		this.civilStatus = CIVIL_STATUS_LIST[0];
		this.permanentAddressCity = new CityDTO();
		this.permanentAddressBarangay = new BarangayDTO();
		this.permanentAddressDetails = "";
		this.emailAddress = "";
		this.cpNumber = "";
		this.lastSchoolAttendedName = "";
		this.shsStrand = new SHSStrandDTO();
		this.lastSchoolAttendedYear = DateTimeUtil.getCurrentYear();
		this.lastSchoolAttendedCity = new CityDTO();
		this.applicantType = "";
		this.semester = new SemesterDTO();
		this.academicProgram = new AcademicProgramDTO();
		this.pict = "";
		this.signatureApplicant = "";
		this.signatureGuardian = "";
		this.status = "";
		this.examScheduleCode = "";
		this.lrn = "";
	}

	public AdmissionApplicationDTO getAdmissionApplication() {
		AdmissionApplicationDTO admissionApplication = new AdmissionApplicationDTO();
		admissionApplication.setId(super.getId());
		admissionApplication.setCode(super.getCode());
		admissionApplication.setLastName(this.getLastName());
		admissionApplication.setFirstName(this.getFirstName());
		admissionApplication.setMiddleName(this.getMiddleName());
		admissionApplication.setDateOfBirth(this.getDateOfBirth());
		admissionApplication.setPlaceOfBirth(this.getPlaceOfBirth());
		admissionApplication.setGender(this.getGender());
		admissionApplication.setCivilStatus(this.getCivilStatus());
		admissionApplication.setPermanentAddressCity(this.getPermanentAddressCity());
		admissionApplication.setPermanentAddressBarangay(this.getPermanentAddressBarangay());
		admissionApplication.setPermanentAddressDetails(this.getPermanentAddressDetails());
		admissionApplication.setEmailAddress(this.getEmailAddress());
		admissionApplication.setCpNumber(this.getCpNumber());
		admissionApplication.setLastSchoolAttendedName(this.getLastSchoolAttendedName());
		admissionApplication.setShsStrand(this.shsStrand);
		admissionApplication.setLastSchoolAttendedYear(this.getLastSchoolAttendedYear());
		admissionApplication.setLastSchoolAttendedCity(this.getLastSchoolAttendedCity());
		admissionApplication.setSemester(this.getSemester());
		admissionApplication.setAcademicProgram(this.getAcademicProgram());
		admissionApplication.setPict(this.pict);
		admissionApplication.setSignatureApplicant(this.signatureApplicant);
		admissionApplication.setSignatureGuardian(this.signatureGuardian);
		admissionApplication.setStatus(this.status);		
		admissionApplication.setExamScheduleCode(this.examScheduleCode);
		admissionApplication.setLrn(this.lrn);
		return admissionApplication;
	}
	
	public void printContent() {
		System.out.println("Id: " + super.getId());
		System.out.println("Code: " + super.getCode());
		System.out.println("Last Name: " + this.getLastName());
		System.out.println("First Name: " + this.getFirstName());
		System.out.println("Middle Name: " + this.getMiddleName());
		System.out.println("Date of Birth: " + this.getDateOfBirth());
		System.out.println("Place of Birth: " + this.getPlaceOfBirth());
		System.out.println("Gender: " + this.getGender());
		System.out.println("Civil Status: " + this.getCivilStatus());
		System.out.println("Permanent Address City: " + this.getPermanentAddressCity().getName());
		System.out.println("Permanent Address Barangay: " + this.getPermanentAddressBarangay().getName());
		System.out.println("Permanent Address Details: " + this.getPermanentAddressDetails());
		System.out.println("Email Address: " + this.getEmailAddress());
		System.out.println("CP Number: " + this.getCpNumber());
		System.out.println("School Last Attended Name: " + this.getLastSchoolAttendedName());
		System.out.println("SHS Strand: " + this.getShsStrand().getLabel());
		System.out.println("School Last Attended Year: " + this.getLastSchoolAttendedYear());
		System.out.println("School Last Attended City: " + this.getLastSchoolAttendedCity().getName());
		System.out.println("Applicant Type: " + this.getApplicantType());
		System.out.println("Academic Program: " + this.getAcademicProgram().getName());
		System.out.println("Pict: " + this.getPict());
		System.out.println("Signature Applicant: " + this.getSignatureApplicant());
		System.out.println("Signature Guardian: " + this.getSignatureGuardian());
		System.out.println("Status: " + this.getStatus());
		System.out.println("Exam Schedule Code: " + this.getExamScheduleCode());
		System.out.println("LRN: " + this.getLrn());
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getPlaceOfBirth() {
		return placeOfBirth;
	}

	public void setPlaceOfBirth(String placeOfBirth) {
		this.placeOfBirth = placeOfBirth;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public String getCivilStatus() {
		return civilStatus;
	}

	public void setCivilStatus(String civilStatus) {
		this.civilStatus = civilStatus;
	}

	public CityDTO getPermanentAddressCity() {
		return permanentAddressCity;
	}

	public void setPermanentAddressCity(CityDTO permanentAddressCity) {
		this.permanentAddressCity = permanentAddressCity;
	}

	public BarangayDTO getPermanentAddressBarangay() {
		return permanentAddressBarangay;
	}

	public void setPermanentAddressBarangay(BarangayDTO permanentAddressBarangay) {
		this.permanentAddressBarangay = permanentAddressBarangay;
	}

	public String getPermanentAddressDetails() {
		return permanentAddressDetails;
	}

	public void setPermanentAddressDetails(String permanentAddressDetails) {
		this.permanentAddressDetails = permanentAddressDetails;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getCpNumber() {
		return cpNumber;
	}

	public void setCpNumber(String cpNumber) {
		this.cpNumber = cpNumber;
	}

	public String getLastSchoolAttendedName() {
		return lastSchoolAttendedName;
	}

	public void setLastSchoolAttendedName(String lastSchoolAttendedName) {
		this.lastSchoolAttendedName = lastSchoolAttendedName;
	}
	
	public int getLastSchoolAttendedYear() {
		return lastSchoolAttendedYear;
	}

	public void setLastSchoolAttendedYear(int lastSchoolAttendedYear) {
		this.lastSchoolAttendedYear = lastSchoolAttendedYear;
	}
	
	public CityDTO getLastSchoolAttendedCity() {
		return lastSchoolAttendedCity;
	}

	public void setLastSchoolAttendedCity(CityDTO lastSchoolAttendedCity) {
		this.lastSchoolAttendedCity = lastSchoolAttendedCity;
	}

	public String getApplicantType() {
		return applicantType;
	}

	public void setApplicantType(String applicantType) {
		this.applicantType = applicantType;
	}
	
	public SemesterDTO getSemester() {
		return semester;
	}

	public void setSemester(SemesterDTO semester) {
		this.semester = semester;
	}

	public AcademicProgramDTO getAcademicProgram() {
		return academicProgram;
	}

	public void setAcademicProgram(AcademicProgramDTO academicProgram) {
		this.academicProgram = academicProgram;
	}

	public SHSStrandDTO getShsStrand() {
		return shsStrand;
	}

	public void setShsStrand(SHSStrandDTO shsStrand) {
		this.shsStrand = shsStrand;
	}

	public String getPict() {
		return pict;
	}

	public void setPict(String pict) {
		this.pict = pict;
	}

	public String getSignatureApplicant() {
		return signatureApplicant;
	}

	public void setSignatureApplicant(String signatureApplicant) {
		this.signatureApplicant = signatureApplicant;
	}

	public String getSignatureGuardian() {
		return signatureGuardian;
	}

	public void setSignatureGuardian(String signatureGuardian) {
		this.signatureGuardian = signatureGuardian;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getExamScheduleCode() {
		return examScheduleCode;
	}

	public void setExamScheduleCode(String examScheduleCode) {
		this.examScheduleCode = examScheduleCode;
	}

	public String getLrn() {
		return lrn;
	}

	public void setLrn(String lrn) {
		this.lrn = lrn;
	}
}
