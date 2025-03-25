package com.laponhcet.guest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mytechnopal.ActionResponse;
import com.mytechnopal.base.DAOBase;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.dao.UserDAO;
import com.mytechnopal.dto.UserDTO;
import com.mytechnopal.util.DateTimeUtil;

public class RegisterDAO extends DAOBase {
	private static final long serialVersionUID = 1L;
	
	private String qryRegisterLast = "REGISTER_LAST";
	private String qryUserLast = "USER_LAST";
	private String qryRegisterByActivationCode = "REGISTER_BY_ACTIVATION_CODE";
	private String qryRegisterAdd = "REGISTER_ADD";
	private String qryRegisterUpdateActivatedTimestamp = "REGISTER_UPDATE_ACTIVATED_TIMESTAMP";

	protected RegisterDTO getRegisterByActivationCode(String activationCode) {
		return (RegisterDTO) getDTO(qryRegisterByActivationCode, activationCode);
	}
	
	protected void add(Connection conn, List<PreparedStatement> prepStmntList, DTOBase obj) {
		RegisterDTO register = (RegisterDTO) obj;
		PreparedStatement prepStmnt = null;
		try {
			prepStmnt = conn.prepareStatement(getQueryStatement(qryRegisterAdd));
			prepStmnt.setString(1, register.getCode());
			prepStmnt.setString(2, register.getEmail());
			prepStmnt.setString(3, register.getPassword());
			prepStmnt.setString(4, register.getActivationCode());
			prepStmnt.setTimestamp(5, register.getActivatedTimestamp());
			prepStmnt.setTimestamp(6, register.getAddedTimestamp());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		prepStmntList.add(prepStmnt);
	}
	
	protected void updateActivatedTimestamp(Connection conn, List<PreparedStatement> prepStmntList, DTOBase obj) {
		RegisterDTO register = (RegisterDTO) obj;
		PreparedStatement prepStmnt = null;
		try {
			prepStmnt = conn.prepareStatement(getQueryStatement(qryRegisterUpdateActivatedTimestamp));
			prepStmnt.setTimestamp(1, register.getActivatedTimestamp());
			prepStmnt.setInt(2, register.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		prepStmntList.add(prepStmnt);
	}
	
	@Override
	protected DTOBase rsToObj(ResultSet resultSet) {
		RegisterDTO register = new RegisterDTO();
		register.setId(getDBValInt(resultSet, "id"));
		register.setCode(getDBValStr(resultSet, "code"));
		register.setEmail(getDBValStr(resultSet, "email"));
		register.setPassword(getDBValStr(resultSet, "password"));
		register.setActivationCode(getDBValStr(resultSet, "activation_code"));
		register.setActivatedTimestamp(getDBValTimestamp(resultSet, "activated_timestamp"));
		register.setAddedTimestamp(getDBValTimestamp(resultSet, "added_timestamp"));
		return register;
	}

	@Override
	public void executeAdd(DTOBase obj) {
		Connection conn = daoConnectorUtil.getConnection();
		List<PreparedStatement> prepStmntList = new ArrayList<PreparedStatement>();
		RegisterDTO register = (RegisterDTO) obj;
		register.setCode(getGeneratedCode(qryRegisterLast, 8));
		register.setBaseDataOnInsert();
		add(conn, prepStmntList, register);
		result.put(ActionResponse.SESSION_ACTION_RESPONSE, executeIUD(conn, prepStmntList));
	}

	public void executeUpdateActivatedTimestamp(DTOBase obj) {
		Connection conn = daoConnectorUtil.getConnection();
		List<PreparedStatement> prepStmntList = new ArrayList<PreparedStatement>();
		RegisterDTO register = (RegisterDTO) obj;
		updateActivatedTimestamp(conn, prepStmntList, register);
		UserDTO user = new UserDTO();
		user.setCode(getTPGeneratedCode(qryUserLast, 12));
		user.setUserName(register.getEmail());
		user.setPassword(register.getPassword());
		user.setBirthDate(DateTimeUtil.getStrToDateTime("2000-01-01", "yyyy-MM-dd"));
		user.setActive(true);
		user.setAddedTimestamp(register.getActivatedTimestamp());
		user.setBaseDataOnInsert();
		new UserDAO().add(conn, prepStmntList, user);
		result.put(ActionResponse.SESSION_ACTION_RESPONSE, executeIUD(conn, prepStmntList));
	}
	
	@Override
	public void executeAddList(List<DTOBase> dtoList) {
		// TODO Auto-generated method stub

	}

	@Override
	public void executeUpdate(DTOBase obj) {
		// TODO Auto-generated method stub

	}

	@Override
	public void executeUpdateList(List<DTOBase> dtoList) {
		// TODO Auto-generated method stub

	}

	@Override
	public void executeDelete(DTOBase obj) {
		// TODO Auto-generated method stub

	}

	@Override
	public void executeDeleteList(List<DTOBase> dtoList) {
		// TODO Auto-generated method stub

	}

}
