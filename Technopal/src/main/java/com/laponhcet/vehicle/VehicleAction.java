package com.laponhcet.vehicle;

import java.util.List;


import com.mytechnopal.DataTable;
import com.mytechnopal.base.ActionBase;
import com.mytechnopal.base.DTOBase;

public class VehicleAction extends ActionBase {
	private static final long serialVersionUID = 1L;

	protected void setSessionVars() {
	    List<DTOBase> vehicleList = new VehicleDAO().getVehicleList();

	    DataTable dataTable = new DataTable(VehicleDTO.SESSION_VEHICLE_DATA_TABLE, vehicleList, 
	        new String[] {VehicleDTO.ACTION_SEARCH_BY_PLATENUMBER}, new String[] {"PlateNumber"});
	    dataTable.setColumnNameArr(new String[] {"ID", "CODE","VEHICLE TYPE CODE","PLATE NUMBER","ADDED BY", "ADDED TIMESTAMP","UPDATED BY", "UPDATED TIMESTAMP",""});
	    dataTable.setColumnWidthArr(new String[] {"5", "5", "5", "10", "15","15","15","15","5"});

	    // ✅ Fix: Set SESSION_PERSON attribute
	    setSessionAttribute(VehicleDTO.SESSION_VEHICLE, new VehicleDTO());
	    setSessionAttribute(VehicleDTO.SESSION_VEHICLE_DATA_TABLE, dataTable);
	    setSessionAttribute(VehicleDTO.SESSION_VEHICLE_LIST, vehicleList);
	    //add session inside()
	}

}
