package com.laponhcet.schedule;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mysql.jdbc.Statement;
import com.mytechnopal.ActionResponse;
import com.mytechnopal.base.DAOBase;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.util.DateTimeUtil;

public class ScheduleDetailsDAO extends DAOBase {
	private static final long serialVersionUID = 1L;
	private String qryScheduleDetailsAdd = "SCHEDULE_DETAILS_ADD";
	private String qryScheduleDetailsDelete = "SCHEDULE_DETAILS_DELETE";
	private String qryScheduleDetailsListByScheduleCode = "SCHEDULE_DETAILS_LIST_BYSCHEDULECODE";

	protected void add(Connection conn, List<PreparedStatement> prepStmntList, Object obj) {
		ScheduleDetailsDTO scheduleDetails = (ScheduleDetailsDTO) obj;
		PreparedStatement prepStmnt = null;
		try {
			prepStmnt = conn.prepareStatement(getQueryStatement(qryScheduleDetailsAdd), Statement.RETURN_GENERATED_KEYS);
			prepStmnt.setString(1, scheduleDetails.getAcademicSection().getCode());
			prepStmnt.setString(2, scheduleDetails.getSemester().getAcademicYear().getCode());
			prepStmnt.setString(3, scheduleDetails.getSemester().getCode());
			prepStmnt.setString(4, scheduleDetails.getScheduleCode());
			prepStmnt.setString(5, scheduleDetails.getDay());
			prepStmnt.setString(6, DateTimeUtil.isTimeTBA(scheduleDetails.getStartTime())?DateTimeUtil.getDateTimeToStr(scheduleDetails.getStartTime(), "yyyy-MM-dd") + " 00:00:01":DateTimeUtil.getDateTimeToStr(scheduleDetails.getStartTime(), "yyyy-MM-dd kk:mm"));
			prepStmnt.setString(7, DateTimeUtil.isTimeTBA(scheduleDetails.getEndTime())?DateTimeUtil.getDateTimeToStr(scheduleDetails.getEndTime(), "yyyy-MM-dd") + " 00:00:01":DateTimeUtil.getDateTimeToStr(scheduleDetails.getEndTime(), "yyyy-MM-dd kk:mm"));
			prepStmnt.setString(8, scheduleDetails.getVenue().getCode());
			prepStmnt.setString(9, scheduleDetails.getTeacher().getCode());
			prepStmnt.setString(10, scheduleDetails.getAddedBy());
			prepStmnt.setTimestamp(11, scheduleDetails.getAddedTimestamp());
			prepStmnt.setString(12, scheduleDetails.getUpdatedBy());
			prepStmnt.setTimestamp(13, scheduleDetails.getUpdatedTimestamp());	
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
	public void executeDelete(DTOBase obj) {
		ScheduleDetailsDTO scheduleDetails = (ScheduleDetailsDTO) obj;
		Connection conn = daoConnectorUtil.getConnection();
		List<PreparedStatement> prepStmntList = new ArrayList<PreparedStatement>();
		delete(conn, prepStmntList, scheduleDetails);
		result.put(ActionResponse.SESSION_ACTION_RESPONSE, executeIUD(conn, prepStmntList));
	}

	protected void delete(Connection conn, List<PreparedStatement> prepStmntList, Object obj) {
		ScheduleDetailsDTO scheduleDetails = (ScheduleDetailsDTO) obj;
		PreparedStatement prepStmnt = null;
		try {
			prepStmnt = conn.prepareStatement(getQueryStatement(qryScheduleDetailsDelete), Statement.RETURN_GENERATED_KEYS);
			prepStmnt.setInt(1, scheduleDetails.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		prepStmntList.add(prepStmnt);
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
	
	public List<DTOBase> getScheduleDetailsListByScheduleCode(String scheduleCode) {
		return getDTOList(qryScheduleDetailsListByScheduleCode, scheduleCode);
	}

	@Override
	protected DTOBase rsToObj(ResultSet resultSet) {
		ScheduleDetailsDTO scheduleDetails = new ScheduleDetailsDTO();
		scheduleDetails.setId((Integer) getDBValInt(resultSet, "id"));
		scheduleDetails.getAcademicSection().setCode(getDBValStr(resultSet, "academic_section_code"));
		scheduleDetails.getSemester().getAcademicYear().setCode((String) getDBValStr(resultSet, "academic_year_code"));
		scheduleDetails.getSemester().setCode((String) getDBValStr(resultSet, "semester_code"));
		scheduleDetails.setScheduleCode((String) getDBValStr(resultSet, "schedule_code"));
		scheduleDetails.setDay((String) getDBValStr(resultSet, "day"));
		scheduleDetails.setStartTime((Date) getDBValTime(resultSet, "start_time"));
		scheduleDetails.setEndTime((Date) getDBValTime(resultSet, "end_time"));
		scheduleDetails.getVenue().setCode((String) getDBValStr(resultSet, "venue_code"));
		scheduleDetails.getTeacher().setCode((String) getDBValStr(resultSet, "teacher_code"));
		return scheduleDetails;
	}

}
