package com.laponhcet.admissionapplication;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.mysql.jdbc.Statement;
import com.mytechnopal.base.DAOBase;
import com.mytechnopal.base.DTOBase;

public class AdmissionApplicationEMailDAO extends DAOBase {
	private static final long serialVersionUID = 1L;

	private String qryAdmissionApplicationEMailAdd = "ADMISSION_APPLICATION_EMAIL_ADD";
	
	@Override
	public void executeAdd(DTOBase obj) {
		
	}
	
	public void addList(Connection conn, List<PreparedStatement> prepStmntList, List<DTOBase> objList) {
		for(DTOBase obj: objList) {
			AdmissionApplicationEMailDTO admissionApplicationEMail = (AdmissionApplicationEMailDTO) obj;
			PreparedStatement prepStmnt = null;
			try {
				prepStmnt = conn.prepareStatement(getQueryStatement(qryAdmissionApplicationEMailAdd), Statement.RETURN_GENERATED_KEYS);
				prepStmnt.setString(1, admissionApplicationEMail.getAdmissionApplicationCode());
				prepStmnt.setBoolean(2, admissionApplicationEMail.isSent());
				prepStmnt.setString(3, admissionApplicationEMail.getAddedBy());
				prepStmnt.setTimestamp(4, admissionApplicationEMail.getAddedTimestamp());
			} catch (SQLException e) {
				e.printStackTrace();
			}
			prepStmntList.add(prepStmnt);
		}
	}

	@Override
	public void executeAddList(List<DTOBase> objList) {
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

	@Override
	protected DTOBase rsToObj(ResultSet arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
