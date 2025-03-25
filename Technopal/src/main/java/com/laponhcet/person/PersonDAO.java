package com.laponhcet.person;

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

public class PersonDAO extends DAOBase {

	private static final long serialVersionUID = 1L;

	private String qryPersonAdd = "PERSON_ADD";
	private String qryPersonList = "PERSON_LIST";
	private String qryPersonUpdate = "PERSON_UPDATE";
	private String qryPersonDelete = "PERSON_DELETE";
	private String qryPersonLast = "PERSON_LAST";
	 
	protected String getGeneratedCode(String qryName) {
	    String baseCode = "00001";
	    DTOBase dto = getLast(qryPersonLast);

	    if (dto != null && dto.getCode() != null && dto.getCode().matches("\\d+")) { // Ensure the code is numeric
	        try {
	            int nextNum = Integer.parseInt(dto.getCode()) + 1;
	            baseCode = String.format("%05d", nextNum);
	        } catch (NumberFormatException e) {
	            e.printStackTrace();
	        }
	    }
	    System.out.println("Generated Code: " + baseCode); // Debugging output
	    return baseCode;
	}

	
	@Override
	public void executeAdd(DTOBase obj) {
	PersonDTO person = (PersonDTO) obj;
	    String generatedCode = getGeneratedCode(qryPersonLast, 5);
	    person.setCode(generatedCode);
	    person.setBaseDataOnInsert();
		
		Connection conn = daoConnectorUtil.getConnection();
		List<PreparedStatement> prepStmntList = new ArrayList<PreparedStatement>();
		add(conn, prepStmntList, person);
		result.put(ActionResponse.SESSION_ACTION_RESPONSE, executeIUD(conn, prepStmntList));
	}
	
//	public void add(Connection conn, List<PreparedStatement> prepStmntList, DTOBase obj) {
//	    PersonDTO person = (PersonDTO) obj;
//	    PreparedStatement prepStmnt = null;
//
//	    try {
//	        prepStmnt = conn.prepareStatement(getQueryStatement(qryPersonAdd), Statement.RETURN_GENERATED_KEYS);
//	        prepStmnt.setString(1, person.getCode());
//	        prepStmnt.setString(2, person.getFirstName());
//	        prepStmnt.setString(3, person.getMiddleName());
//	        prepStmnt.setString(4, person.getLastName());
//	        prepStmnt.setString(5, person.getGender());
//	        //prepStmnt.setString(6, person.getPicture()); // Store file name in DB
//	        prepStmnt.setString(6, person.getPicture().isEmpty() ? "default.jpg" : person.getPicture());
//
//	    } catch (SQLException e) {
//	        e.printStackTrace();
//	    }
//	    System.out.println("Saving picture: " + person.getPicture());
//
//
//	    prepStmntList.add(prepStmnt);
//	}
	
	public void add(Connection conn, List<PreparedStatement> prepStmntList, DTOBase obj) {
	    PersonDTO person = (PersonDTO) obj;
	    PreparedStatement prepStmnt = null;

	    try {
	        prepStmnt = conn.prepareStatement(getQueryStatement(qryPersonAdd), Statement.RETURN_GENERATED_KEYS);
	        prepStmnt.setString(1, person.getCode());
	        prepStmnt.setString(2, person.getFirstName());
	        prepStmnt.setString(3, person.getMiddleName());
	        prepStmnt.setString(4, person.getLastName());
	        prepStmnt.setString(5, person.getGender());
	        prepStmnt.setString(6, person.getPicture().isEmpty() ? "default.jpg" : person.getPicture()); // ✅ Fix: Default image handling

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    System.out.println("Saving picture: " + person.getPicture());
	    prepStmntList.add(prepStmnt);
	}



	@Override
	public void executeAddList(List<DTOBase> arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void executeDelete(DTOBase arg0) {
		// TODO Auto-generated method stub
			PersonDTO person = (PersonDTO) arg0;
	        Connection conn = daoConnectorUtil.getConnection();
	        List<PreparedStatement> prepStmntList = new ArrayList<>();
	        delete(conn, prepStmntList, person);
	        result.put(ActionResponse.SESSION_ACTION_RESPONSE, executeIUD(conn, prepStmntList));
	    }
	    
	    public void delete(Connection conn, List<PreparedStatement> prepStmntList, DTOBase obj) {
	        PersonDTO person = (PersonDTO) obj;
	        PreparedStatement prepStmnt = null;
	        try {
	            prepStmnt = conn.prepareStatement(getQueryStatement(qryPersonDelete), Statement.RETURN_GENERATED_KEYS);
	            prepStmnt.setInt(1, person.getId());
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
	public void executeUpdate(DTOBase obj) {
		PersonDTO person = (PersonDTO) obj;
		person.setBaseDataOnUpdate();
	    Connection conn = daoConnectorUtil.getConnection();
	    List<PreparedStatement> prepStmntList = new ArrayList<>();
	    update(conn, prepStmntList, person);
	    result.put(ActionResponse.SESSION_ACTION_RESPONSE, executeIUD(conn, prepStmntList));
	}
	    public void update(Connection conn, List<PreparedStatement> prepStmntList, DTOBase obj) {
	    	PersonDTO person = (PersonDTO) obj;
	        PreparedStatement prepStmnt = null;
	        try {
	        	prepStmnt = conn.prepareStatement(getQueryStatement(qryPersonUpdate), Statement.RETURN_GENERATED_KEYS);
	        	prepStmnt.setString(1, person.getFirstName());
	        	prepStmnt.setString(2, person.getMiddleName());
	        	prepStmnt.setString(3, person.getLastName());
	        	prepStmnt.setString(4, person.getGender());
	        	prepStmnt.setString(5, person.getPicture());
	        	prepStmnt.setInt(6, person.getId());
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        prepStmntList.add(prepStmnt);
	    }
	@Override
	public void executeUpdateList(List<DTOBase> arg0) {
		// TODO Auto-generated method stub
	}

	public List<DTOBase> getPersonList() {
		return getDTOList(qryPersonList);
	}
	
	@Override
	protected DTOBase rsToObj(ResultSet resultSet) {
		PersonDTO person = new PersonDTO();
		person.setId(getDBValInt(resultSet, "id"));
		person.setCode(getDBValStr(resultSet, "code"));
		person.setFirstName(getDBValStr(resultSet, "firstname"));
		person.setMiddleName(getDBValStr(resultSet, "middlename"));
		person.setLastName(getDBValStr(resultSet, "lastname"));
		person.setGender(getDBValStr(resultSet, "gender"));
		person.setPicture(getDBValStr(resultSet, "picture"));
		return person;
	}
}