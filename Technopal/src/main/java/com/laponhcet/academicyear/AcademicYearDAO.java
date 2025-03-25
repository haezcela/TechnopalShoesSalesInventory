package com.laponhcet.academicyear;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mytechnopal.ActionResponse;
import com.mytechnopal.base.DAOBase;
import com.mytechnopal.base.DTOBase;

public class AcademicYearDAO extends DAOBase{
	
	private static final long serialVersionUID = 1L;

	private final String qryAcademicYearAdd = "ACADEMIC_YEAR_ADD";
	private final String qryAcademicYearUpdate = "ACADEMIC_YEAR_UPDATE";
	private final String qryAcademicYearDelete = "ACADEMIC_YEAR_DELETE";
	private final String qryAcademicYearLast = "ACADEMIC_YEAR_LAST";
	private final String qryAcademicYearList = "ACADEMIC_YEAR_LIST";
	
	public List<DTOBase> getAcademicYearList() {
		return getDTOList(qryAcademicYearList);
	}
	
	@Override
	protected DTOBase rsToObj(ResultSet resultSet) {
		AcademicYearDTO academicYear = new AcademicYearDTO();
		academicYear.setId(getDBValInt(resultSet, "id"));
		academicYear.setCode(getDBValStr(resultSet, "code"));
		academicYear.setName(getDBValStr(resultSet, "name"));
		academicYear.setRemarks(getDBValStr(resultSet, "remarks"));
		academicYear.setDateStart(getDBValDate(resultSet, "date_start"));
		academicYear.setDateEnd(getDBValDate(resultSet, "date_end"));
		academicYear.setDisplayStr(academicYear.getName());
		return academicYear;
	}

	@Override
	public void executeAdd(DTOBase obj) {
		Connection conn = daoConnectorUtil.getConnection();
		List<PreparedStatement> prepStmntList = new ArrayList<PreparedStatement>();
		AcademicYearDTO academicYear = (AcademicYearDTO) obj;
		academicYear.setCode(getGeneratedCode(qryAcademicYearLast, 3));
		academicYear.setBaseDataOnInsert();
		//add(conn, prepStmntList, academicYear);
		result.put(ActionResponse.SESSION_ACTION_RESPONSE, executeIUD(conn, prepStmntList));
	}
	
//	protected void add(Connection conn, List<PreparedStatement> prepStmntList, DTOBase obj) {
//		AcademicYearDTO academicYear = (AcademicYearDTO) obj;
//		PreparedStatement prepStmnt = null;
//		try {
//			prepStmnt = conn.prepareStatement(getQueryStatement(qryAcademicYearAdd));
//			prepStmnt.setString(1, academicYear.getCode());
//			prepStmnt.setString(2, academicYear.getName());
//			prepStmnt.setString(3, academicYear.getRemarks());
//			prepStmnt.setTimestamp(4, (Timestamp) academicYear.getDateStart());
//			prepStmnt.setTimestamp(5, (Timestamp) academicYear.getDateEnd());
//			prepStmnt.setString(6, academicYear.getAcademicProgramGroupCodes());
//			prepStmnt.setString(7, academicYear.getGradingSystem());
//			prepStmnt.setString(8, academicYear.getAddedBy());
//			prepStmnt.setTimestamp(9, academicYear.getAddedTimestamp());
//			prepStmnt.setString(10, academicYear.getUpdatedBy());
//			prepStmnt.setTimestamp(11, academicYear.getUpdatedTimestamp());
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		prepStmntList.add(prepStmnt);
//	}

	@Override
	public void executeAddList(List<DTOBase> dtoList) {
		// TODO Auto-generated method stub

	}

	@Override
	public void executeUpdate(DTOBase obj) {
		Connection conn = daoConnectorUtil.getConnection();
		List<PreparedStatement> prepStmntList = new ArrayList<PreparedStatement>();
		AcademicYearDTO academicYear = (AcademicYearDTO) obj;
		academicYear.setBaseDataOnUpdate();
		//update(conn, prepStmntList, academicYear);
		result.put(ActionResponse.SESSION_ACTION_RESPONSE, executeIUD(conn, prepStmntList, true));
	}
	
//	protected void update(Connection conn, List<PreparedStatement> prepStmntList, DTOBase obj) {
//		AcademicYearDTO academicYear = (AcademicYearDTO) obj;
//		PreparedStatement prepStmnt = null;
//		try {
//			prepStmnt = conn.prepareStatement(getQueryStatement(qryAcademicYearUpdate));
//			prepStmnt.setString(1, academicYear.getName());
//			prepStmnt.setString(2, academicYear.getRemarks());
//			prepStmnt.setTimestamp(3, (Timestamp) academicYear.getDateStart());
//			prepStmnt.setTimestamp(4, (Timestamp) academicYear.getDateEnd());
//			prepStmnt.setString(5, academicYear.getAcademicProgramGroupCodes());
//			prepStmnt.setString(6, academicYear.getGradingSystem());
//			prepStmnt.setString(7, academicYear.getUpdatedBy());
//			prepStmnt.setTimestamp(8, academicYear.getUpdatedTimestamp());
//			prepStmnt.setInt(9, academicYear.getId());
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		prepStmntList.add(prepStmnt);
//	}

	@Override
	public void executeUpdateList(List<DTOBase> dtoList) {
		// TODO Auto-generated method stub

	}

	@Override
	public void executeDelete(DTOBase obj) {
		Connection conn = daoConnectorUtil.getConnection();
		List<PreparedStatement> prepStmntList = new ArrayList<PreparedStatement>();
		AcademicYearDTO academicYear = (AcademicYearDTO) obj;
		delete(conn, prepStmntList, academicYear);
		result.put(ActionResponse.SESSION_ACTION_RESPONSE, executeIUD(conn, prepStmntList));
	}
	
	protected void delete(Connection conn, List<PreparedStatement> prepStmntList, DTOBase obj) {
		AcademicYearDTO academicYear = (AcademicYearDTO) obj;
		PreparedStatement prepStmnt = null;
		try {
			prepStmnt = conn.prepareStatement(getQueryStatement(qryAcademicYearDelete));
			prepStmnt.setInt(1, academicYear.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		prepStmntList.add(prepStmnt);
	}

	@Override
	public void executeDeleteList(List<DTOBase> dtoList) {
		// TODO Auto-generated method stub

	}

	public static void main(String[] a) {
		System.out.println(new AcademicYearDAO().getAcademicYearList().size());
	}

}
