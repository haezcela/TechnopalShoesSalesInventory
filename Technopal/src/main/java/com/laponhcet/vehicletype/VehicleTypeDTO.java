package com.laponhcet.vehicletype;

import java.sql.Timestamp;

import com.mytechnopal.base.DTOBase;

public class VehicleTypeDTO extends DTOBase {
	private static final long serialVersionUID = 1L;
	
	public static String SESSION_VEHICLETYPE = "SESSION_VEHICLETYPE";
	public static String SESSION_VEHICLETYPE_LIST = "SESSION_VEHICLETYPE_LIST";
	public static String SESSION_VEHICLETYPE_DATA_TABLE = "SESSION_VEHICLETYPE_DATA_TABLE";
	public static String ACTION_SEARCH_BY_NAME = "ACTION_SEARCH_BY_NAME";

	private String name;

	public VehicleTypeDTO() {
		super();
		this.name = "";
	}

	public VehicleTypeDTO getVehicleType() {
		VehicleTypeDTO vehicleType = new VehicleTypeDTO();
		vehicleType.setId(super.getId());
		vehicleType.setCode(super.getCode());
		vehicleType.setName(name);
		vehicleType.getAddedBy();
		vehicleType.getAddedTimestamp();
		vehicleType.getUpdatedBy();
		vehicleType.getUpdatedTimestamp();
		return vehicleType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}



}