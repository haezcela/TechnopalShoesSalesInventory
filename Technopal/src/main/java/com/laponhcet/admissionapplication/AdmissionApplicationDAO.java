package com.laponhcet.admissionapplication;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Statement;
import com.mytechnopal.ActionResponse;
import com.mytechnopal.base.DAOBase;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.util.DateTimeUtil;

public class AdmissionApplicationDAO extends DAOBase {
	
private static final long serialVersionUID = 1L;
	
	private String qryAdmissionApplicationAdd = "ADMISSION_APPLICATION_ADD";
	private String qryAdmissionApplicationUpdateStatus = "ADMISSION_APPLICATION_UPDATE_STATUS";
	private String qryAdmissionApplicationList = "ADMISSION_APPLICATION_LIST";
	private String qryAdmissionApplicationListByStatusLimit = "ADMISSION_APPLICATION_LIST_BY_STATUS_LIMIT";
	private String qryAdmissionApplicationByCode = "ADMISSION_APPLICATION_BY_CODE";
	private String qryAdmissionApplicationByLastNameFirstNameMiddleName = "ADMISSION_APPLICATION_BY_LASTNAMEFIRSTNAMEMIDDLENAME";
	private String qryAdmissionApplicationByEmailAddress = "ADMISSION_APPLICATION_BY_EMAILADDRESS";
	private String qryAdmissionApplicationByCPNumber = "ADMISSION_APPLICATION_BY_CPNumber";
	
	@Override
	public void executeAdd(DTOBase obj) {
		Connection conn = daoConnectorUtil.getConnection();
		List<PreparedStatement> prepStmntList = new ArrayList<PreparedStatement>();
		AdmissionApplicationDTO admissionApplication = (AdmissionApplicationDTO) obj;
		add(conn, prepStmntList, admissionApplication);
		//new AdmissionApplicationStatusDAO().add(conn, prepStmntList, admissionApplication.getAdmissionApplicationStatus());
//		AdmissionApplicationStatusDAO admissionApplicationStatusDAO = new AdmissionApplicationStatusDAO();
//		for(DTOBase admissionApplicationStatusObj: admissionApplication.getAdmissionApplicationStatusList()) {
//			AdmissionApplicationStatusDTO admissionApplicationStatus = (AdmissionApplicationStatusDTO) admissionApplicationStatusObj;
//			admissionApplicationStatusDAO.add(conn, prepStmntList, admissionApplicationStatus);
//		}
		
		result.put(ActionResponse.SESSION_ACTION_RESPONSE, executeIUD(conn, prepStmntList));

	}
	
	public void add(Connection conn, List<PreparedStatement> prepStmntList, DTOBase obj) {
		AdmissionApplicationDTO admissionApplication = (AdmissionApplicationDTO) obj;
		PreparedStatement prepStmnt = null;
		try {
			prepStmnt = conn.prepareStatement(getQueryStatement(qryAdmissionApplicationAdd), Statement.RETURN_GENERATED_KEYS);
			prepStmnt.setString(1, admissionApplication.getCode());
			prepStmnt.setString(2, admissionApplication.getLastName());
			prepStmnt.setString(3, admissionApplication.getFirstName());
			prepStmnt.setString(4, admissionApplication.getMiddleName());
			prepStmnt.setString(5, DateTimeUtil.getDateTimeToStr(admissionApplication.getDateOfBirth(), "yyyy-MM-dd"));
			prepStmnt.setString(6, admissionApplication.getPlaceOfBirth());
			prepStmnt.setString(7, admissionApplication.getGender());
			prepStmnt.setString(8, admissionApplication.getCivilStatus());
			prepStmnt.setString(9, admissionApplication.getPermanentAddressCity().getCode());
			prepStmnt.setString(10, admissionApplication.getPermanentAddressBarangay().getCode());
			prepStmnt.setString(11, admissionApplication.getPermanentAddressDetails());
			prepStmnt.setString(12, admissionApplication.getEmailAddress());
			prepStmnt.setString(13, admissionApplication.getCpNumber());
			prepStmnt.setString(14, admissionApplication.getLastSchoolAttendedName());
			prepStmnt.setString(15, admissionApplication.getShsStrand().getCode());
			prepStmnt.setInt(16, admissionApplication.getLastSchoolAttendedYear());
			prepStmnt.setString(17, admissionApplication.getLastSchoolAttendedCity().getCode());
			prepStmnt.setString(18, admissionApplication.getApplicantType());
			prepStmnt.setString(19, admissionApplication.getSemester().getCode());
			prepStmnt.setString(20, admissionApplication.getAcademicProgram().getCode());
			prepStmnt.setString(21, admissionApplication.getPict());
			prepStmnt.setString(22, admissionApplication.getSignatureApplicant());
			prepStmnt.setString(23, admissionApplication.getSignatureGuardian());
			prepStmnt.setTimestamp(24, admissionApplication.getAddedTimestamp());
			prepStmnt.setString(25, admissionApplication.getLrn());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		prepStmntList.add(prepStmnt);
	}
	

	public void updateStatus(Connection conn, List<PreparedStatement> prepStmntList, DTOBase obj) {
		AdmissionApplicationDTO admissionApplication = (AdmissionApplicationDTO) obj;
		PreparedStatement prepStmnt = null;
		try {
			prepStmnt = conn.prepareStatement(getQueryStatement(qryAdmissionApplicationUpdateStatus), Statement.RETURN_GENERATED_KEYS);
			prepStmnt.setString(1, admissionApplication.getStatus());
			prepStmnt.setString(2, admissionApplication.getExamScheduleCode());
			prepStmnt.setInt(3, admissionApplication.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		prepStmntList.add(prepStmnt);
	}
	
	@Override
	public void executeAddList(List<DTOBase> arg0) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void executeDelete(DTOBase obj) {
	}
	

	@Override
	public void executeDeleteList(List<DTOBase> arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void executeUpdate(DTOBase obj) {
		Connection conn = daoConnectorUtil.getConnection();
		List<PreparedStatement> prepStmntList = new ArrayList<PreparedStatement>();
		AdmissionApplicationDTO admissionApplication = (AdmissionApplicationDTO) obj;
		updateStatus(conn, prepStmntList, admissionApplication);
		result.put(ActionResponse.SESSION_ACTION_RESPONSE, executeIUD(conn, prepStmntList));
		
	}
	
	@Override
	public void executeUpdateList(List<DTOBase> obj) {
		// TODO Auto-generated method stub

	}

	public AdmissionApplicationDTO getAdmissionApplicationByEmailAddress(String emailAddress) {
		return (AdmissionApplicationDTO) getDTO(qryAdmissionApplicationByEmailAddress, emailAddress);
	}
	
	public AdmissionApplicationDTO getAdmissionApplicationByCPNumber(String cpNumber) {
		return (AdmissionApplicationDTO) getDTO(qryAdmissionApplicationByCPNumber, cpNumber);
	}
	
	public AdmissionApplicationDTO getAdmissionApplicationByLastNameFirstNameMiddleName(String lastName, String firstName, String middleName) {
		List<Object> paramList = new ArrayList<Object>();
		paramList.add(lastName);
		paramList.add(firstName);
		paramList.add(middleName);
		return (AdmissionApplicationDTO) getDTO(qryAdmissionApplicationByLastNameFirstNameMiddleName, paramList);
	}
	
	public AdmissionApplicationDTO getAdmissionApplicationByCode(String code) {
		return (AdmissionApplicationDTO) getDTO(qryAdmissionApplicationByCode, code);
	}
	
	public List<DTOBase> getAdmissionApplicationList() {
		return getDTOList(qryAdmissionApplicationList);
	}
	
	public List<DTOBase> getAdmissionApplicationListByStatusLimit(String status, int limit) {
		List<Object> paramList = new ArrayList<Object>();
		paramList.add(status);
		paramList.add(limit);
		return getDTOList(qryAdmissionApplicationListByStatusLimit, paramList);
	}
	
	@Override
	protected DTOBase rsToObj(ResultSet rs) {
		AdmissionApplicationDTO admissionApplication = new AdmissionApplicationDTO();
		admissionApplication.setId(getDBValInt(rs, "id"));
		admissionApplication.setCode(getDBValStr(rs, "code"));
		admissionApplication.setLastName(getDBValStr(rs, "last_name"));
		admissionApplication.setFirstName(getDBValStr(rs, "first_name"));
		admissionApplication.setMiddleName(getDBValStr(rs, "middle_name"));
		admissionApplication.setDateOfBirth(getDBValDate(rs, "date_of_birth"));
		admissionApplication.setPlaceOfBirth(getDBValStr(rs, "place_of_birth"));
		admissionApplication.setGender(getDBValStr(rs, "gender"));
		admissionApplication.setCivilStatus(getDBValStr(rs, "civil_status"));
		admissionApplication.getPermanentAddressCity().setCode(getDBValStr(rs, "permanent_address_city_code"));
		admissionApplication.getPermanentAddressBarangay().setCode(getDBValStr(rs, "permanent_address_barangay_code"));
		admissionApplication.setPermanentAddressDetails(getDBValStr(rs, "permanent_address_details"));
		admissionApplication.setEmailAddress(getDBValStr(rs, "email_address"));
		admissionApplication.setCpNumber(getDBValStr(rs, "cp_number"));
		admissionApplication.setLastSchoolAttendedName(getDBValStr(rs, "last_school_attended_name"));
		admissionApplication.getShsStrand().setCode(getDBValStr(rs, "shs_strand_code"));
		admissionApplication.setLastSchoolAttendedYear(getDBValInt(rs, "last_school_attended_year"));
		admissionApplication.getLastSchoolAttendedCity().setCode(getDBValStr(rs, "last_school_attended_city_code"));
		admissionApplication.setApplicantType(getDBValStr(rs, "applicant_type"));
		admissionApplication.getSemester().setCode(getDBValStr(rs, "semester_code"));
		admissionApplication.getAcademicProgram().setCode(getDBValStr(rs, "academic_program_code"));
		admissionApplication.setPict(getDBValStr(rs, "pict"));
		admissionApplication.setSignatureApplicant(getDBValStr(rs, "signature_applicant"));
		admissionApplication.setSignatureGuardian(getDBValStr(rs, "signature_guardian"));
		admissionApplication.setAddedTimestamp(getDBValTimestamp(rs, "added_timestamp"));
		admissionApplication.setStatus(getDBValStr(rs, "status"));
		admissionApplication.setExamScheduleCode(getDBValStr(rs, "exam_schedule_code"));
		admissionApplication.setLrn(getDBValStr(rs, "lrn"));
		return admissionApplication;
	}
}
