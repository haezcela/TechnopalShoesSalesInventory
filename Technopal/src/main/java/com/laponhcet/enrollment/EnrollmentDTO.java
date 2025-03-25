package com.laponhcet.enrollment;

import com.laponhcet.academicsection.AcademicSectionDTO;
import com.laponhcet.semester.SemesterDTO;
import com.laponhcet.student.StudentDTO;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.util.StringUtil;


public class EnrollmentDTO extends DTOBase {
	private static final long serialVersionUID = 1L;

	public static final String SESSION_ENROLLMENT = "SESSION_ENROLLMENT";
	public static final String SESSION_ENROLLMENT_LIST = "SESSION_ENROLLMENT_LIST";
	public static final String SESSION_ENROLLMENT_DATA_TABLE = "SESSION_ENROLLMENT_DATA_TABLE";
	
	public static final String ACTION_SEARCH_BY_NAME = "ACTION_SEARCH_BY_NAME";
	public static final String ACTION_SEARCH_BY_LASTNAME = "ACTION_SEARCH_BY_LASTNAME";
	public static final String ACTION_SEARCH_BY_USER_CODE = "ACTION_SEARCH_BY_USER_CODE";
	
	public static String STATUS_CONTINUING_CODE = "C";
	public static String STATUS_CONTINUING_STR = "CONTINUING";
		
	public static String STATUS_NEW_CODE = "N";
	public static String STATUS_NEW_STR = "NEW";
	
	public static String STATUS_RETURNEE_CODE = "R";
	public static String STATUS_RETURNEE_STR = "RETURNEE";
	
	public static String STATUS_TRANSFEREE_CODE = "T";
	public static String STATUS_TRANSFEREE_STR = "TRANSFEREE";
	
	
	public static String[] STATUS_CODE_ARR = new String[] {STATUS_CONTINUING_CODE, STATUS_NEW_CODE, STATUS_TRANSFEREE_CODE, STATUS_RETURNEE_CODE};
	public static String[] STATUS_STR_ARR = new String[] {STATUS_CONTINUING_STR, STATUS_NEW_STR, STATUS_TRANSFEREE_STR, STATUS_RETURNEE_STR,};
	
	private StudentDTO student;
	private SemesterDTO semester;
	private AcademicSectionDTO academicSection;
	private String status;

	
	public EnrollmentDTO() {
		super();
		student = new StudentDTO();
		semester = new SemesterDTO();
		academicSection = new AcademicSectionDTO();
		status = STATUS_CONTINUING_CODE;
		
	}


	public EnrollmentDTO getEnrollment() {
		EnrollmentDTO enrollment = new EnrollmentDTO();
		enrollment.setId(super.getId());
		enrollment.setCode(super.getCode());
		enrollment.setStudent(this.student);
		enrollment.setSemester(this.semester);
		enrollment.setAcademicSection(this.academicSection);
		enrollment.setStatus(this.status);
		return enrollment;
	}

	public String getStatusStr() {
		return STATUS_STR_ARR[StringUtil.getIndexFromArrayStr(STATUS_CODE_ARR, status)];
	}
	
//	public double getTotalPayUnit() {
//		double total = 0d;
//		for(DTOBase enrollmentDetailObj: enrollmentDetailsList) {
//			EnrollmentDetailsDTO enrollmentDetails = (EnrollmentDetailsDTO) enrollmentDetailObj;
//			total += enrollmentDetails.getSchedule().getSubject().getPayUnit();
//		}
//		return total;
//	}
	
//	public double getTotalCreditUnit() {
//		double total = 0d;
//		for(DTOBase enrollmentDetailObj: enrollmentDetailsList) {
//			EnrollmentDetailsDTO enrollmentDetails = (EnrollmentDetailsDTO) enrollmentDetailObj;
//			total += enrollmentDetails.getSchedule().getSubject().getCreditUnit();
//		}
//		return total;
//	}
	
	public String getSemesterTermTaken() {
	    String str = semester.getName()==0?"S":String.valueOf(semester.getName()) ;
	    return str + StringUtil.getRight(semester.getAcademicYear().getName().split("-")[0], 2) + StringUtil.getRight(semester.getAcademicYear().getName().split("-")[1], 2);
	}

	public StudentDTO getStudent() {
		return student;
	}

	public void setStudent(StudentDTO student) {
		this.student = student;
	}

	public SemesterDTO getSemester() {
		return semester;
	}

	public void setSemester(SemesterDTO semester) {
		this.semester = semester;
	}

	public AcademicSectionDTO getAcademicSection() {
		return academicSection;
	}

	public void setAcademicSection(AcademicSectionDTO academicSection) {
		this.academicSection = academicSection;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}