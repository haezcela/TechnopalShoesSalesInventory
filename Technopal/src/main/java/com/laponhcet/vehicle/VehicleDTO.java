package com.laponhcet.vehicle;

//import com.laponhcet.vehicletype.VehicleTypeDTO;
import com.mytechnopal.base.DTOBase;
import java.sql.Timestamp;

import com.mytechnopal.base.DTOBase;


//Create an object of VehicleTypeDTO using "has a"

public class VehicleDTO extends DTOBase {
    private static final long serialVersionUID = 1L;
    
	public static String SESSION_VEHICLE = "SESSION_VEHICLE";
	public static String SESSION_VEHICLE_LIST = "SESSION_VEHICLE_LIST";
	public static String SESSION_VEHICLE_DATA_TABLE = "SESSION_VEHICLE_DATA_TABLE";
	public static String ACTION_SEARCH_BY_PLATENUMBER = "ACTION_SEARCH_BY_PLATENUMBER";
    
    private String vehicleTypeCode;
    
    private String plateNumber;
    //constructor
    public VehicleDTO() {
        super();
        //this.vehicleTypeCode = new VehicleTypeDTO().getCode();
        this.vehicleTypeCode = "";
        this.plateNumber = "";
    }

    public VehicleDTO getVehicle() {
        VehicleDTO vehicle = new VehicleDTO();
        vehicle.setId(super.getId());
        vehicle.setCode(super.getCode());
        //vehicle.setVehicleTypeCode(super.getCode());
        vehicle.getVehicleTypeCode();
        vehicle.getPlateNumber();
		vehicle.getAddedBy();
		vehicle.getAddedTimestamp();
		vehicle.getUpdatedBy();
		vehicle.getUpdatedTimestamp();
        return vehicle;
    }
    
    public String getVehicleTypeCode() {
        return vehicleTypeCode;
    }
    
    void setVehicleTypeCode(String vehicleTypeCode) {
    	this.vehicleTypeCode = vehicleTypeCode;
	}
    
    public String getPlateNumber() {
        return plateNumber;
    }
    
    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }
    
}
