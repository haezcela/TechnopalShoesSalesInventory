package com.laponhcet.vehicle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.laponhcet.vehicletype.VehicleTypeDTO;
import com.mysql.jdbc.Statement;
import com.mytechnopal.ActionResponse;
import com.mytechnopal.base.DAOBase;
import com.mytechnopal.base.DTOBase;



public class VehicleDAO extends DAOBase {

	private static final long serialVersionUID = 1L;

	private String qryVehicleAdd = "VEHICLE_ADD";
	private String qryVehicleList = "VEHICLE_LIST";
	private String qryVehicleUpdate = "VEHICLE_UPDATE";
	private String qryVehicleDelete = "VEHICLE_DELETE";
	private String qryVehicleLast = "VEHICLE_LAST";
	 
	protected String getGeneratedCode(String qryName) {
	    String baseCode = "001";
	    DTOBase dto = getLast(qryVehicleLast);

	    if (dto != null && dto.getCode() != null && dto.getCode().matches("\\d+")) { // Ensure the code is numeric
	        try {
	            int nextNum = Integer.parseInt(dto.getCode()) + 1;
	            baseCode = String.format("%03d", nextNum);
	        } catch (NumberFormatException e) {
	            e.printStackTrace();
	        }
	    }
	    System.out.println("Generated Code: " + baseCode); // Debugging output
	    return baseCode;
	}

	@Override
	public void executeAdd(DTOBase obj) {
	VehicleDTO vehicle = (VehicleDTO) obj;
	String generatedCode = getGeneratedCode(qryVehicleLast);
	    vehicle.setCode(generatedCode);
	    vehicle.setBaseDataOnInsert();
		
		Connection conn = daoConnectorUtil.getConnection();
		List<PreparedStatement> prepStmntList = new ArrayList<PreparedStatement>();
		add(conn, prepStmntList, vehicle);
		result.put(ActionResponse.SESSION_ACTION_RESPONSE, executeIUD(conn, prepStmntList));
		
		//new VehicleType().add
	}

	public void add(Connection conn, List<PreparedStatement> prepStmntList, DTOBase obj) {
		VehicleDTO vehicle = (VehicleDTO) obj;
		//VehicleTypeDTO vehicleType = (VehicleTypeDTO) obj;
		PreparedStatement prepStmnt = null;
		try {
			prepStmnt = conn.prepareStatement(getQueryStatement(qryVehicleAdd), Statement.RETURN_GENERATED_KEYS);

			prepStmnt.setString(1, vehicle.getCode());
			prepStmnt.setString(2, vehicle.getVehicleTypeCode());
			prepStmnt.setString(3, vehicle.getPlateNumber());
			prepStmnt.setString(4, vehicle.getAddedBy());
			prepStmnt.setTimestamp(5, vehicle.getAddedTimestamp());
			prepStmnt.setString(6, vehicle.getUpdatedBy());
			prepStmnt.setTimestamp(7, vehicle.getUpdatedTimestamp());
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
			VehicleDTO vehicle = (VehicleDTO) arg0;
	        Connection conn = daoConnectorUtil.getConnection();
	        List<PreparedStatement> prepStmntList = new ArrayList<>();
	        delete(conn, prepStmntList, vehicle);
	        result.put(ActionResponse.SESSION_ACTION_RESPONSE, executeIUD(conn, prepStmntList));
	    }
	    
	    public void delete(Connection conn, List<PreparedStatement> prepStmntList, DTOBase obj) {
	        VehicleDTO vehicle = (VehicleDTO) obj;
	        PreparedStatement prepStmnt = null;
	        try {
	            prepStmnt = conn.prepareStatement(getQueryStatement(qryVehicleDelete), Statement.RETURN_GENERATED_KEYS);
	            prepStmnt.setInt(1, vehicle.getId());
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
		VehicleDTO vehicle = (VehicleDTO) obj;
		vehicle.setBaseDataOnUpdate();
	    Connection conn = daoConnectorUtil.getConnection();
	    List<PreparedStatement> prepStmntList = new ArrayList<>();
	    update(conn, prepStmntList, vehicle);
	    result.put(ActionResponse.SESSION_ACTION_RESPONSE, executeIUD(conn, prepStmntList));
	}
	    public void update(Connection conn, List<PreparedStatement> prepStmntList, DTOBase obj) {
	    	VehicleDTO vehicle = (VehicleDTO) obj;
	        PreparedStatement prepStmnt = null;
	        try {
	        	prepStmnt = conn.prepareStatement(getQueryStatement(qryVehicleUpdate), Statement.RETURN_GENERATED_KEYS);
				prepStmnt.setString(1, vehicle.getCode());
				prepStmnt.setString(2, vehicle.getVehicleTypeCode());
				prepStmnt.setString(3, vehicle.getPlateNumber());
				prepStmnt.setString(4, vehicle.getAddedBy());
				prepStmnt.setTimestamp(5, vehicle.getAddedTimestamp());
				prepStmnt.setString(6, vehicle.getUpdatedBy());
				prepStmnt.setTimestamp(7, vehicle.getUpdatedTimestamp());
		        prepStmnt.setLong(8, vehicle.getId());
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        prepStmntList.add(prepStmnt);
	    }
	
	@Override
	public void executeUpdateList(List<DTOBase> arg0) {
		// TODO Auto-generated method stub
	}

	public List<DTOBase> getVehicleList() {
		return getDTOList(qryVehicleList);
	}
	
	@Override
	protected DTOBase rsToObj(ResultSet resultSet) {
		VehicleDTO vehicle = new VehicleDTO();
		vehicle.setId(getDBValInt(resultSet, "id"));
		vehicle.setCode(getDBValStr(resultSet, "code"));
		vehicle.setVehicleTypeCode(getDBValStr(resultSet, "vehicle_type_code"));
		vehicle.setPlateNumber(getDBValStr(resultSet, "plate_number"));
		vehicle.setAddedBy(getDBValStr(resultSet, "added_by"));
		vehicle.setAddedTimestamp(getDBValTimestamp(resultSet, "added_timestamp"));
		vehicle.setUpdatedBy(getDBValStr(resultSet, "updated_by"));
		vehicle.setUpdatedTimestamp(getDBValTimestamp(resultSet, "updated_timestamp"));

		return vehicle;
	}
	
	public static void main(String[] args) {
	    testAdd();
	}

	
	public static void testAdd() {
	    VehicleDAO vehicleDAO = new VehicleDAO();
	    VehicleDTO vehicle = new VehicleDTO();
	    
	    vehicle.setVehicleTypeCode("001");
	    vehicle.setPlateNumber("ABC1234");
	    
	    
	    
	    try {
            vehicleDAO.executeAdd(vehicle);
            System.out.println("Vehicle added successfully!");
            System.out.println("Added Vehicle Type Code: " + vehicle.getVehicleTypeCode());
            System.out.println("Added Vehicle Plate Number: " + vehicle.getPlateNumber());
            System.out.println("Added by: " + vehicle.getAddedBy());
            System.out.println("Added Timestamp: " + vehicle.getAddedTimestamp());
            System.out.println("Updated by: " + vehicle.getUpdatedBy());
            System.out.println("Updated Timestamp: " + vehicle.getUpdatedTimestamp());
            
        } catch (Exception e) {
            System.err.println("Error adding Vehicle Type: " + e.getMessage());
            e.printStackTrace();
        }

	}


}