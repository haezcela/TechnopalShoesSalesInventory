package com.laponhcet.semester;

import java.sql.ResultSet;
import java.util.List;

import com.mytechnopal.base.DAOBase;
import com.mytechnopal.base.DTOBase;

public class SemesterDAO extends DAOBase {
	private static final long serialVersionUID = 1L;
	
//	private final String qrySemesterAdd = "SEMESTER_ADD";
//	private final String qrySemesterUpdate = "SEMESTER_UPDATE";
//	private final String qrySemesterDelete = "SEMESTER_DELETE";
//	private final String qrySemesterLast = "SEMESTER_LAST";
	private final String qrySemesterList = "SEMESTER_LIST";
	
	public List<DTOBase> getSemesterList() {
		return getDTOList(qrySemesterList);
	}
	
	@Override
	protected DTOBase rsToObj(ResultSet resultSet) {
		SemesterDTO semester = new SemesterDTO();
		semester.setId(getDBValInt(resultSet, "id"));
		semester.setCode(getDBValStr(resultSet, "code"));
		semester.getAcademicYear().setCode(getDBValStr(resultSet, "academic_year_code"));
		semester.setName(getDBValInt(resultSet, "name"));
		semester.setDateStart(getDBValTimestamp(resultSet, "date_start"));
		semester.setDateEnd(getDBValTimestamp(resultSet, "date_end"));
		return semester;
	}

	@Override
	public void executeAdd(DTOBase obj) {
//		Connection conn = daoConnectorUtil.getConnection();
//		List<PreparedStatement> prepStmntList = new ArrayList<PreparedStatement>();
//		SemesterDTO semester = (SemesterDTO) obj;
//		semester.setCode(getGeneratedCode(qrySemesterLast, 3));
//		semester.setBaseDataOnInsert();
//		add(conn, prepStmntList, semester);
//		result.put(ActionResponse.SESSION_ACTION_RESPONSE, executeIUD(conn, prepStmntList));
	}
	
//	protected void add(Connection conn, List<PreparedStatement> prepStmntList, DTOBase obj) {
//		SemesterDTO semester = (SemesterDTO) obj;
//		PreparedStatement prepStmnt = null;
//		try {
//			prepStmnt = conn.prepareStatement(getQueryStatement(this.qrySemesterAdd));
//			prepStmnt.setString(1, semester.getCode());
//			prepStmnt.setString(2, semester.getAcademicYear().getCode());
//			prepStmnt.setInt(3, semester.getName());
//			prepStmnt.setTimestamp(4, (Timestamp) semester.getDateStart());
//			prepStmnt.setTimestamp(5, (Timestamp) semester.getDateEnd());
//			prepStmnt.setString(6, semester.getAcademicYear().getGradingSystem());
//			prepStmnt.setString(7, semester.getAddedBy());
//			prepStmnt.setTimestamp(8, semester.getAddedTimestamp());
//			prepStmnt.setString(9, semester.getUpdatedBy());
//			prepStmnt.setTimestamp(10, semester.getUpdatedTimestamp());
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		prepStmntList.add(prepStmnt);
//	}

	@Override
	public void executeAddList(List<DTOBase> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void executeDelete(DTOBase obj) {
//		Connection conn = daoConnectorUtil.getConnection();
//		List<PreparedStatement> prepStmntList = new ArrayList<PreparedStatement>();
//		SemesterDTO semester = (SemesterDTO) obj;
//		delete(conn, prepStmntList, semester);
//		result.put(ActionResponse.SESSION_ACTION_RESPONSE, executeIUD(conn, prepStmntList));
	}
	
//	protected void delete(Connection conn, List<PreparedStatement> prepStmntList, DTOBase obj) {
//		SemesterDTO semester = (SemesterDTO) obj;
//		PreparedStatement prepStmnt = null;
//		try {
//			prepStmnt = conn.prepareStatement(getQueryStatement(qrySemesterDelete));
//			prepStmnt.setInt(1, semester.getId());
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		prepStmntList.add(prepStmnt);
//	}

	@Override
	public void executeDeleteList(List<DTOBase> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void executeUpdate(DTOBase obj) {
//		Connection conn = daoConnectorUtil.getConnection();
//		List<PreparedStatement> prepStmntList = new ArrayList<PreparedStatement>();
//		SemesterDTO semester = (SemesterDTO) obj;
//		semester.setBaseDataOnUpdate();
//		update(conn, prepStmntList, semester);
//		result.put(ActionResponse.SESSION_ACTION_RESPONSE, executeIUD(conn, prepStmntList, true));
	}
	
//	protected void update(Connection conn, List<PreparedStatement> prepStmntList, DTOBase obj) {
//		SemesterDTO semester = (SemesterDTO) obj;
//		PreparedStatement prepStmnt = null;
//		try {
//			prepStmnt = conn.prepareStatement(getQueryStatement(qrySemesterUpdate));
//			prepStmnt.setString(1, semester.getAcademicYear().getCode());
//			prepStmnt.setInt(2, semester.getName());
//			prepStmnt.setTimestamp(3, (Timestamp) semester.getDateStart());
//			prepStmnt.setTimestamp(4, (Timestamp) semester.getDateEnd());
//			prepStmnt.setString(5, semester.getAcademicYear().getGradingSystem());
//			prepStmnt.setString(6, semester.getUpdatedBy());
//			prepStmnt.setTimestamp(7, semester.getUpdatedTimestamp());
//			prepStmnt.setInt(8, semester.getId());
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		prepStmntList.add(prepStmnt);
//	}
	
	@Override
	public void executeUpdateList(List<DTOBase> arg0) {
		// TODO Auto-generated method stub
		
	}

}