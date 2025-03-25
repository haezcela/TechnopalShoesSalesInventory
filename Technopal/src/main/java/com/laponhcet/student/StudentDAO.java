package com.laponhcet.student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.mysql.jdbc.Statement;
import com.mytechnopal.base.DAOBase;
import com.mytechnopal.base.DTOBase;

public class StudentDAO extends DAOBase {
	private static final long serialVersionUID = 1L;

	private String qryStudentAdd = "STUDENT_ADD";
	private String qryStudentUpdate = "STUDENT_UPDATE";
	private String qryStudentDelete = "STUDENT_DELETE";
	private String qryStudentByUserCode = "STUDENT_BY_USERCODE";
	private String qryStudentList = "STUDENT_LIST";
	
	@Override
	public void executeAdd(DTOBase obj) {
		
	}

	public void add(Connection conn, List<PreparedStatement> prepStmntList, DTOBase obj) {
		StudentDTO student = (StudentDTO) obj;
		PreparedStatement prepStmnt = null;
		try {
			prepStmnt = conn.prepareStatement(getQueryStatement(qryStudentAdd), Statement.RETURN_GENERATED_KEYS);
			prepStmnt.setString(1, student.getCode());
			prepStmnt.setString(2, student.getLearnerReferenceNumber());
			prepStmnt.setString(3, student.getAcademicProgram().getCode());
			prepStmnt.setString(4, student.getAddedBy());
			prepStmnt.setTimestamp(5, student.getAddedTimestamp());
			prepStmnt.setString(6, student.getUpdatedBy());
			prepStmnt.setTimestamp(7, student.getUpdatedTimestamp());
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
	
	public void delete(Connection conn, List<PreparedStatement> prepStmntList, DTOBase obj) {
		StudentDTO student = (StudentDTO) obj;
		PreparedStatement prepStmnt = null;
		try {
			prepStmnt = conn.prepareStatement(getQueryStatement(qryStudentDelete), Statement.RETURN_GENERATED_KEYS);
			prepStmnt.setInt(1, student.getId());
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
	
	public void update(Connection conn, List<PreparedStatement> prepStmntList, DTOBase obj) {
		StudentDTO student = (StudentDTO) obj;
		PreparedStatement prepStmnt = null;
		try {
			prepStmnt = conn.prepareStatement(getQueryStatement(qryStudentUpdate), Statement.RETURN_GENERATED_KEYS);
			prepStmnt.setString(1, student.getLearnerReferenceNumber());
			prepStmnt.setString(2, student.getAcademicProgram().getCode());
			prepStmnt.setString(3, student.getUpdatedBy());
			prepStmnt.setTimestamp(4, student.getUpdatedTimestamp());
			prepStmnt.setInt(5, student.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		prepStmntList.add(prepStmnt);
	}

	@Override
	public void executeUpdateList(List<DTOBase> arg0) {
		// TODO Auto-generated method stub

	}

	public StudentDTO getStudentByUserCode(String userCode) {
		return (StudentDTO) getDTO(qryStudentByUserCode, userCode);
	}
	
	public List<DTOBase> getStudentList() {
		return getDTOList(qryStudentList);
	}
	
	@Override
	protected DTOBase rsToObj(ResultSet resultSet) {
		StudentDTO student = new StudentDTO();
		student.setId(getDBValInt(resultSet, "id"));
		student.setCode(getDBValStr(resultSet, "user_code"));
		student.setLearnerReferenceNumber(getDBValStr(resultSet, "learner_reference_number"));
		student.getAcademicProgram().setCode(getDBValStr(resultSet, "academic_program_code"));
		return student;
	}
	
	public static void main(String[] a) {
		System.out.println(new StudentDAO().getStudentList().size());
	}

}
