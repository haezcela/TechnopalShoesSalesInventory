package com.laponhcet.examschedule;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.laponhcet.admissionapplication.AdmissionApplicationDAO;
import com.laponhcet.admissionapplication.AdmissionApplicationDTO;
import com.laponhcet.admissionapplication.AdmissionApplicationEMailDAO;
import com.mysql.jdbc.Statement;
import com.mytechnopal.ActionResponse;
import com.mytechnopal.base.DAOBase;
import com.mytechnopal.base.DTOBase;

public class ExamScheduleDAO extends DAOBase {
	private static final long serialVersionUID = 1L;

	private String qryExamScheduleUpdateStatus = "EXAM_SCHEDULE_UPDATE_STATUS";
	private String qryExamScheduleList = "EXAM_SCHEDULE_LIST";
	
	public void executeUpdateStatusSubmitted(ExamScheduleDTO examSchedule) {
		examSchedule.setBaseDataOnUpdate();
		Connection conn = daoConnectorUtil.getConnection();
		List<PreparedStatement> prepStmntList = new ArrayList<PreparedStatement>();
		updateStatus(conn, prepStmntList, examSchedule);
		AdmissionApplicationDAO admissionApplicationDAO = new AdmissionApplicationDAO();
		for(DTOBase admissionApplicationObj: examSchedule.getAdmissionApplicationList()) {
			AdmissionApplicationDTO admissionApplication = (AdmissionApplicationDTO) admissionApplicationObj;
			admissionApplication.setStatus(AdmissionApplicationDTO.STATUS_FOR_EXAMNINATION);
			admissionApplication.setExamScheduleCode(examSchedule.getCode());
			admissionApplicationDAO.updateStatus(conn, prepStmntList, admissionApplication);
		}
		result.put(ActionResponse.SESSION_ACTION_RESPONSE, executeIUD(conn, prepStmntList));

	}
	
	public void executeUpdateStatusEmailed(ExamScheduleDTO examSchedule, List<DTOBase> admissionApplicationEMailList) {
		Connection conn = daoConnectorUtil.getConnection();
		List<PreparedStatement> prepStmntList = new ArrayList<PreparedStatement>();
		updateStatus(conn, prepStmntList, examSchedule);
		new AdmissionApplicationEMailDAO().addList(conn, prepStmntList, admissionApplicationEMailList);
		result.put(ActionResponse.SESSION_ACTION_RESPONSE, executeIUD(conn, prepStmntList));

	}
	
	public void updateStatus(Connection conn, List<PreparedStatement> prepStmntList, DTOBase obj) {
		ExamScheduleDTO examSchedule = (ExamScheduleDTO) obj;
		PreparedStatement prepStmnt = null;
		try {
			prepStmnt = conn.prepareStatement(getQueryStatement(qryExamScheduleUpdateStatus), Statement.RETURN_GENERATED_KEYS);
			prepStmnt.setString(1, examSchedule.getStatus());
			prepStmnt.setString(2, examSchedule.getUpdatedBy());
			prepStmnt.setTimestamp(3, examSchedule.getUpdatedTimestamp());
			prepStmnt.setInt(4, examSchedule.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		prepStmntList.add(prepStmnt);
	}
	
	@Override
	public void executeAdd(DTOBase arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void executeAddList(List<DTOBase> arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void executeDelete(DTOBase arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void executeDeleteList(List<DTOBase> arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void executeUpdate(DTOBase arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void executeUpdateList(List<DTOBase> arg0) {
		// TODO Auto-generated method stub

	}

	public List<DTOBase> getExamScheduleList() {
		return getDTOList(qryExamScheduleList);
	}
	
	@Override
	protected DTOBase rsToObj(ResultSet resultSet) {
		ExamScheduleDTO examSchedule = new ExamScheduleDTO();
		examSchedule.setId(getDBValInt(resultSet, "id"));
		examSchedule.setCode(getDBValStr(resultSet, "code"));
		examSchedule.setBatch(getDBValInt(resultSet, "batch"));
		examSchedule.setDateTimeStart(getDBValTimestamp(resultSet, "date_time_start"));
		examSchedule.setDateTimeEnd(getDBValTimestamp(resultSet, "date_time_end"));
		examSchedule.setStatus(getDBValStr(resultSet, "status"));
		examSchedule.setTotalExaminee(getDBValInt(resultSet, "total_examinee"));
		return examSchedule;
	}

}
