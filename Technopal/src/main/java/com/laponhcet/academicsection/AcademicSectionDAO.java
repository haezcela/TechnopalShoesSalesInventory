package com.laponhcet.academicsection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.mytechnopal.ActionResponse;
import com.mytechnopal.base.DAOBase;
import com.mytechnopal.base.DTOBase;

public class AcademicSectionDAO extends DAOBase {
	private static final long serialVersionUID = 1L;

	private final String qryAcademicSectionList = "ACADEMIC_SECTION_LIST";
	
	public List<DTOBase> getAcademicSectionList() {
		return getDTOList(qryAcademicSectionList);
	}
	
	@Override
	protected DTOBase rsToObj(ResultSet resultSet) {
		AcademicSectionDTO academicSection = new AcademicSectionDTO();
		academicSection.setId(getDBValInt(resultSet, "id"));
		academicSection.setCode(getDBValStr(resultSet, "code"));
		academicSection.getAcademicProgram().setCode(getDBValStr(resultSet, "academic_program_code"));
		academicSection.setName(getDBValStr(resultSet, "name"));
		academicSection.setYearLevel(getDBValInt(resultSet, "year_level"));
		return academicSection;
	}

	@Override
	public void executeAdd(DTOBase obj) {
		Connection conn = daoConnectorUtil.getConnection();
		List<PreparedStatement> prepStmntList = new ArrayList<PreparedStatement>();
		AcademicSectionDTO academicSection = (AcademicSectionDTO) obj;
		//academicSection.setCode(getGeneratedCode(qryAcademicSectionLast, 3));
		academicSection.setBaseDataOnInsert();
		//add(conn, prepStmntList, academicSection);
		result.put(ActionResponse.SESSION_ACTION_RESPONSE, executeIUD(conn, prepStmntList, true));
	}
	
//	protected void add(Connection conn, List<PreparedStatement> prepStmntList, DTOBase obj) {
//		AcademicSectionDTO academicSection = (AcademicSectionDTO) obj;
//		PreparedStatement prepStmnt = null;
//		try {
//			prepStmnt = conn.prepareStatement(getQueryStatement(qryAcademicSectionAdd), Statement.RETURN_GENERATED_KEYS);
//			prepStmnt.setString(1, academicSection.getCode());
//			prepStmnt.setString(2, academicSection.getAcademicProgram().getCode());
//			prepStmnt.setInt(3, academicSection.getYearLevel());
//			prepStmnt.setString(4, academicSection.getName());
//			prepStmnt.setString(5, academicSection.getAdviser().getCode());
//			prepStmnt.setString(6, academicSection.getAddedBy());
//			prepStmnt.setTimestamp(7, academicSection.getAddedTimestamp());
//			prepStmnt.setString(8, academicSection.getUpdatedBy());
//			prepStmnt.setTimestamp(9, academicSection.getUpdatedTimestamp());
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
		AcademicSectionDTO academicSection = (AcademicSectionDTO) obj;
		academicSection.setBaseDataOnUpdate();
		//update(conn, prepStmntList, academicSection);
		result.put(ActionResponse.SESSION_ACTION_RESPONSE, executeIUD(conn, prepStmntList));
	}
	
//	protected void update(Connection conn, List<PreparedStatement> prepStmntList, DTOBase obj) {
//		AcademicSectionDTO academicSection = (AcademicSectionDTO) obj;
//		PreparedStatement prepStmnt = null;
//		try {
//			prepStmnt = conn.prepareStatement(getQueryStatement(qryAcademicSectionUpdate), Statement.RETURN_GENERATED_KEYS);
//			prepStmnt.setString(1, academicSection.getAcademicProgram().getCode());
//			prepStmnt.setInt(2, academicSection.getYearLevel());
//			prepStmnt.setString(3, academicSection.getName());
//			prepStmnt.setString(4, academicSection.getAdviser().getCode());
//			prepStmnt.setString(5, academicSection.getUpdatedBy());
//			prepStmnt.setTimestamp(6, academicSection.getUpdatedTimestamp());
//			prepStmnt.setInt(7, academicSection.getId());
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
		AcademicSectionDTO academicSection = (AcademicSectionDTO) obj;
		//delete(conn, prepStmntList, academicSection);
		result.put(ActionResponse.SESSION_ACTION_RESPONSE, executeIUD(conn, prepStmntList));
	}
	
//	protected void delete(Connection conn, List<PreparedStatement> prepStmntList, DTOBase obj) {
//		AcademicSectionDTO academicSection = (AcademicSectionDTO) obj;
//		PreparedStatement prepStmnt = null;
//		try {
//			prepStmnt = conn.prepareStatement(getQueryStatement(qryAcademicSectionDelete), Statement.RETURN_GENERATED_KEYS);
//			prepStmnt.setInt(1, academicSection.getId());
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		prepStmntList.add(prepStmnt);
//	}

	@Override
	public void executeDeleteList(List<DTOBase> dtoList) {
		// TODO Auto-generated method stub

	}

	public static void main(String[] a) {
		System.out.println(new AcademicSectionDAO().getAcademicSectionList().size());
	}
}
