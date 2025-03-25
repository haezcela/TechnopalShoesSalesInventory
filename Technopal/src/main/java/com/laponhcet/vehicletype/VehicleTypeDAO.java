package com.laponhcet.vehicletype;

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

public class VehicleTypeDAO extends DAOBase {

	private static final long serialVersionUID = 1L;

	private String qryVehicleTypeAdd = "VEHICLETYPE_ADD";
	private String qryVehicleTypeList = "VEHICLETYPE_LIST";
	private String qryVehicleTypeUpdate = "VEHICLETYPE_UPDATE";
	private String qryVehicleTypeDelete = "VEHICLETYPE_DELETE";
	private String qryVehicleTypeLast = "VEHICLETYPE_LAST";
	 
	protected String getGeneratedCode(String qryName) {
	    String baseCode = "001";
	    DTOBase dto = getLast(qryVehicleTypeLast);

	    if (dto != null && dto.getCode() != null && dto.getCode().matches("\\d+")) { 
	        try {
	            int nextNum = Integer.parseInt(dto.getCode()) + 1;
	            baseCode = String.format("%03d", nextNum);
	        } catch (NumberFormatException e) {
	            e.printStackTrace();
	        }
	    }
	    System.out.println("Generated Code: " + baseCode); // Debug
	    return baseCode;
	}

	
	@Override
	public void executeAdd(DTOBase obj) {
	VehicleTypeDTO vehicleType = (VehicleTypeDTO) obj;
	    String generatedCode = getGeneratedCode(qryVehicleTypeLast);
	    vehicleType.setCode(generatedCode);
	    vehicleType.setBaseDataOnInsert();
		
		Connection conn = daoConnectorUtil.getConnection();
		List<PreparedStatement> prepStmntList = new ArrayList<PreparedStatement>();
		add(conn, prepStmntList, vehicleType);
		result.put(ActionResponse.SESSION_ACTION_RESPONSE, executeIUD(conn, prepStmntList));
	}
	
	public void add(Connection conn, List<PreparedStatement> prepStmntList, DTOBase obj) {
	    VehicleTypeDTO vehicleType = (VehicleTypeDTO) obj;
	    PreparedStatement prepStmnt = null;

	    try {
	        prepStmnt = conn.prepareStatement(getQueryStatement(qryVehicleTypeAdd), Statement.RETURN_GENERATED_KEYS);
	        prepStmnt.setLong(1, vehicleType.getId());
	        prepStmnt.setString(2, vehicleType.getCode());
	        prepStmnt.setString(3, vehicleType.getName());
	        prepStmnt.setString(4, vehicleType.getAddedBy());
	        prepStmnt.setTimestamp(5, vehicleType.getAddedTimestamp());
	        prepStmnt.setString(6, vehicleType.getUpdatedBy());
	        prepStmnt.setTimestamp(7, vehicleType.getUpdatedTimestamp());
	        

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
			VehicleTypeDTO vehicleType = (VehicleTypeDTO) arg0;
	        Connection conn = daoConnectorUtil.getConnection();
	        List<PreparedStatement> prepStmntList = new ArrayList<>();
	        delete(conn, prepStmntList, vehicleType);
	        result.put(ActionResponse.SESSION_ACTION_RESPONSE, executeIUD(conn, prepStmntList));
	    }
	    
	    public void delete(Connection conn, List<PreparedStatement> prepStmntList, DTOBase obj) {
	        VehicleTypeDTO vehicleType = (VehicleTypeDTO) obj;
	        PreparedStatement prepStmnt = null;
	        try {
	            prepStmnt = conn.prepareStatement(getQueryStatement(qryVehicleTypeDelete), Statement.RETURN_GENERATED_KEYS);
	            prepStmnt.setInt(1, vehicleType.getId());
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
		VehicleTypeDTO vehicleType = (VehicleTypeDTO) obj;
		vehicleType.setBaseDataOnUpdate();
	    Connection conn = daoConnectorUtil.getConnection();
	    List<PreparedStatement> prepStmntList = new ArrayList<>();
	    update(conn, prepStmntList, vehicleType);
	    result.put(ActionResponse.SESSION_ACTION_RESPONSE, executeIUD(conn, prepStmntList));
	}
	    public void update(Connection conn, List<PreparedStatement> prepStmntList, DTOBase obj) {
	    	VehicleTypeDTO vehicleType = (VehicleTypeDTO) obj;
	        PreparedStatement prepStmnt = null;
	        try {
	        	prepStmnt = conn.prepareStatement(getQueryStatement(qryVehicleTypeUpdate), Statement.RETURN_GENERATED_KEYS);
		        prepStmnt.setString(1, vehicleType.getCode());
		        prepStmnt.setString(2, vehicleType.getName());
		        prepStmnt.setString(3, vehicleType.getAddedBy());
		        prepStmnt.setTimestamp(4, vehicleType.getAddedTimestamp());
		        prepStmnt.setString(5, vehicleType.getUpdatedBy());
		        prepStmnt.setTimestamp(6, vehicleType.getUpdatedTimestamp());
	        	prepStmnt.setInt(7, vehicleType.getId());
	        	
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        prepStmntList.add(prepStmnt);
	    }

		@Override
		public void executeUpdateList(List<DTOBase> arg0) {
			// TODO Auto-generated method stub

		}

	public List<DTOBase> getVehicleTypeList() {
		return getDTOList(qryVehicleTypeList);
	}
	
	@Override
	protected DTOBase rsToObj(ResultSet resultSet) {
		VehicleTypeDTO vehicleType = new VehicleTypeDTO();
		vehicleType.setId(getDBValInt(resultSet, "id"));
		vehicleType.setCode(getDBValStr(resultSet, "code"));
		vehicleType.setName(getDBValStr(resultSet, "name"));
		vehicleType.setAddedBy(getDBValStr(resultSet, "added_by"));
		vehicleType.setAddedTimestamp(getDBValTimestamp(resultSet, "added_timestamp"));
		vehicleType.setUpdatedBy(getDBValStr(resultSet, "updated_by"));
		vehicleType.setUpdatedTimestamp(getDBValTimestamp(resultSet, "updated_timestamp"));
		return vehicleType;
	}
	

	    public static void main(String[] args) {
	        testAdd();
	    }

	    public static void testAdd() {
	        VehicleTypeDTO vehicleType = new VehicleTypeDTO();
	        vehicleType.setName("SUV");
	         
	        VehicleTypeDAO vehicleTypeDAO = new VehicleTypeDAO();
	        
	        try {
	            vehicleTypeDAO.executeAdd(vehicleType);
	            System.out.println("Vehicle Type added successfully!");
	            System.out.println("Added Vehicle Type Name: " + vehicleType.getName());
	            System.out.println("Added by: " + vehicleType.getAddedBy());
	            System.out.println("Added Timestamp: " + vehicleType.getAddedTimestamp());
	            System.out.println("Updated by: " + vehicleType.getUpdatedBy());
	            System.out.println("Updated Timestamp: " + vehicleType.getUpdatedTimestamp());
	            
	        } catch (Exception e) {
	            System.err.println("Error adding Vehicle Type: " + e.getMessage());
	            e.printStackTrace();
	        }
	    }
	
}