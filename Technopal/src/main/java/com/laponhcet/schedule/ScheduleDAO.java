package com.laponhcet.schedule;

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

public class ScheduleDAO extends DAOBase {
	private static final long serialVersionUID = 1L;
	
	private String qryScheduleAdd = "SCHEDULE_ADD";
	private String qryScheduleListByAcademicSectionCodeAcademicYearCode = "SCHEDULE_LIST_BYACADEMICSECTIONCODEACADEMICYEARCODE";
	private String qryScheduleListByAcademicSectionCodeSemesterCode = "SCHEDULE_LIST_BYACADEMICSECTIONCODESEMESTERCODE";


	@Override
	public void executeAdd(DTOBase obj) {
		ScheduleDTO schedule = (ScheduleDTO) obj;
		
		schedule.setBaseDataOnInsert();
		Connection conn = daoConnectorUtil.getConnection();
		List<PreparedStatement> prepStmntList = new ArrayList<PreparedStatement>();
		ScheduleDetailsDAO scheduleDetailsDAO = new ScheduleDetailsDAO();
		
		schedule.getScheduleDetails().setScheduleCode(schedule.getCode());
		schedule.getScheduleDetails().setAcademicSection(schedule.getAcademicSection());
		schedule.getScheduleDetails().setSemester(schedule.getSemester());
		schedule.getScheduleDetails().setAddedBy(schedule.getAddedBy());
		schedule.getScheduleDetails().setAddedTimestamp(schedule.getAddedTimestamp());
		schedule.getScheduleDetails().setBaseDataOnInsert();
		scheduleDetailsDAO.add(conn, prepStmntList, schedule.getScheduleDetails());
		add(conn, prepStmntList, schedule);
		result.put(ActionResponse.SESSION_ACTION_RESPONSE, executeIUD(conn, prepStmntList));
	}
	
	public void add(Connection conn, List<PreparedStatement> prepStmntList, DTOBase obj) {
		ScheduleDTO schedule = (ScheduleDTO) obj;
		PreparedStatement prepStmnt = null;
		try {
			prepStmnt = conn.prepareStatement(getQueryStatement(qryScheduleAdd), Statement.RETURN_GENERATED_KEYS);
			prepStmnt.setString(1, schedule.getCode());
			prepStmnt.setString(2, schedule.getAcademicSection().getCode());
			prepStmnt.setString(3, schedule.getSemester().getAcademicYear().getCode());
			prepStmnt.setString(4, schedule.getSemester().getCode());
			prepStmnt.setString(5, schedule.getSubject().getCode());
			prepStmnt.setInt(6, schedule.getMaxEnrollee());
			prepStmnt.setString(7, schedule.getMergeToSchedule().getCode());
			prepStmnt.setString(8, schedule.getAddedBy());
			prepStmnt.setTimestamp(9, schedule.getAddedTimestamp());
			prepStmnt.setString(10, schedule.getUpdatedBy());
			prepStmnt.setTimestamp(11, schedule.getUpdatedTimestamp());
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

	public List<DTOBase> getScheduleListByAcademicSectionCodeAcademicYearCode(String academicSectionCode, String academicYearCode) {
		List<Object> objList = new ArrayList<Object>();
		objList.add(academicSectionCode);
		objList.add(academicYearCode);
		return getDTOList(qryScheduleListByAcademicSectionCodeAcademicYearCode, objList);
	}
	
	public List<DTOBase> getScheduleListByAcademicSectionCodeSemesterCode(String academicSectionCode, String semesterCode) {
		List<Object> objList = new ArrayList<Object>();
		objList.add(academicSectionCode);
		objList.add(semesterCode);
		return getDTOList(qryScheduleListByAcademicSectionCodeSemesterCode, objList);
	}
	
	@Override
	protected DTOBase rsToObj(ResultSet resultSet) {
		ScheduleDTO schedule = new ScheduleDTO();
		schedule.setId(getDBValInt(resultSet, "id"));
		schedule.setCode(getDBValStr(resultSet, "code"));
		schedule.getAcademicSection().setCode(getDBValStr(resultSet, "academic_section_code"));
		schedule.getSemester().getAcademicYear().setCode(getDBValStr(resultSet, "academic_year_code"));
		schedule.getSemester().setCode(getDBValStr(resultSet, "semester_code"));
		schedule.getSubject().setCode(getDBValStr(resultSet, "subject_code"));
		schedule.setMaxEnrollee(getDBValInt(resultSet, "max_enrollee"));
		schedule.getMergeToSchedule().setCode(getDBValStr(resultSet, "merge_to_schedule_code"));
		return schedule;
	}

}
