package com.laponhcet.academicprogram;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.mytechnopal.ActionResponse;
import com.mytechnopal.base.DAOBase;
import com.mytechnopal.base.DTOBase;

public class AcademicProgramDAO extends DAOBase {
	private static final long serialVersionUID = 1L;
	
	private final String qryAcademicProgramList = "ACADEMIC_PROGRAM_LIST";
	
	public List<DTOBase> getAcademicProgramList() {
		return getDTOList(qryAcademicProgramList);
	}
	
	@Override
	protected DTOBase rsToObj(ResultSet resultSet) {
		AcademicProgramDTO academicProgram = new AcademicProgramDTO();
		academicProgram.setId(getDBValInt(resultSet, "id"));
		academicProgram.setCode(getDBValStr(resultSet, "code"));
		academicProgram.setName(getDBValStr(resultSet, "name"));
		academicProgram.setDescription(getDBValStr(resultSet, "description"));
		academicProgram.setGraduationYearLevel(getDBValInt(resultSet, "graduation_year_level"));
		academicProgram.setLogo(getDBValStr(resultSet, "logo"));
		academicProgram.setPeriodType(getDBValStr(resultSet, "period_type"));
		academicProgram.setDisplayStr(academicProgram.getName());
		return academicProgram;
	}

	@Override
	public void executeAdd(DTOBase obj) {
		Connection conn = daoConnectorUtil.getConnection();
		List<PreparedStatement> prepStmntList = new ArrayList<PreparedStatement>();
		AcademicProgramDTO academicProgram = (AcademicProgramDTO) obj;
		//academicProgram.setCode(getGeneratedCode(qryAcademicProgramLast, 3));
		academicProgram.setBaseDataOnInsert();
		//add(conn, prepStmntList, academicProgram);
		result.put(ActionResponse.SESSION_ACTION_RESPONSE, executeIUD(conn, prepStmntList, true));
		
	}
	
//	protected void add(Connection conn, List<PreparedStatement> prepStmntList, DTOBase obj) {
//		AcademicProgramDTO academicProgram = (AcademicProgramDTO) obj;
//		PreparedStatement prepStmnt = null;
//		try {
//			prepStmnt = conn.prepareStatement(getQueryStatement(qryAcademicProgramAdd), Statement.RETURN_GENERATED_KEYS);
//			prepStmnt.setString(1, academicProgram.getCode());
//			prepStmnt.setString(2, academicProgram.getAcademicProgramSubGroup().getCode());
//			prepStmnt.setString(3, academicProgram.getName());
//			prepStmnt.setString(4, academicProgram.getDescription());
//			prepStmnt.setInt(5, academicProgram.getGraduationYearLevel());
//			prepStmnt.setString(6, academicProgram.getHeadUserCode().getCode());
//			prepStmnt.setString(7, academicProgram.getLogo());
//			prepStmnt.setString(8, academicProgram.getAddedBy());
//			prepStmnt.setTimestamp(9, academicProgram.getAddedTimestamp());
//			prepStmnt.setString(10, academicProgram.getUpdatedBy());
//			prepStmnt.setTimestamp(11, academicProgram.getUpdatedTimestamp());
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
		Connection conn = daoConnectorUtil.getConnection();
		List<PreparedStatement> prepStmntList = new ArrayList<PreparedStatement>();
		AcademicProgramDTO academicProgram = (AcademicProgramDTO) obj;
		//delete(conn, prepStmntList, academicProgram);	
		result.put(ActionResponse.SESSION_ACTION_RESPONSE, executeIUD(conn, prepStmntList, true));

	}
	
//	public void delete(Connection conn, List<PreparedStatement> prepStmntList, DTOBase obj) {
//		AcademicProgramDTO academicProgram = (AcademicProgramDTO) obj;
//		PreparedStatement prepStmnt = null;
//		try {
//			prepStmnt = conn.prepareStatement(getQueryStatement(qryAcademicProgramDelete), Statement.RETURN_GENERATED_KEYS);
//			prepStmnt.setInt(1, academicProgram.getId());
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
		Connection conn = daoConnectorUtil.getConnection();
		List<PreparedStatement> prepStmntList = new ArrayList<PreparedStatement>();
		AcademicProgramDTO academicProgram = (AcademicProgramDTO) obj;
		academicProgram.setBaseDataOnUpdate();
		//update(conn, prepStmntList, academicProgram);
		result.put(ActionResponse.SESSION_ACTION_RESPONSE, executeIUD(conn, prepStmntList , true));
	}
	
//	protected void update(Connection conn, List<PreparedStatement> prepStmntList, DTOBase obj) {
//		AcademicProgramDTO academicProgram = (AcademicProgramDTO) obj;
//		PreparedStatement prepStmnt = null;
//		try {
//			prepStmnt = conn.prepareStatement(getQueryStatement(qryAcademicProgramUpdate), Statement.RETURN_GENERATED_KEYS);
//			prepStmnt.setString(1, academicProgram.getAcademicProgramSubGroup().getCode());
//			prepStmnt.setString(2, academicProgram.getName());
//			prepStmnt.setString(3, academicProgram.getDescription());
//			prepStmnt.setInt(4, academicProgram.getGraduationYearLevel());
//			prepStmnt.setString(5, academicProgram.getHeadUserCode().getCode());
//			prepStmnt.setString(6, academicProgram.getLogo());
//			prepStmnt.setString(7, academicProgram.getUpdatedBy());
//			prepStmnt.setTimestamp(8, academicProgram.getUpdatedTimestamp());
//			prepStmnt.setInt(9, academicProgram.getId());
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		prepStmntList.add(prepStmnt);
//	}

	@Override
	public void executeUpdateList(List<DTOBase> arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] a) {
		System.out.println( new AcademicProgramDAO().getAcademicProgramList().size());
	}
}