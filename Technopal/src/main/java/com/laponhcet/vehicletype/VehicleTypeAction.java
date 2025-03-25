package com.laponhcet.vehicletype;

import java.util.List;


import com.mytechnopal.DataTable;
import com.mytechnopal.base.ActionBase;
import com.mytechnopal.base.DTOBase;

public class VehicleTypeAction extends ActionBase {
	private static final long serialVersionUID = 1L;

	protected void setSessionVars() {
	    List<DTOBase> vehicleTypeList = new VehicleTypeDAO().getVehicleTypeList();

	    DataTable dataTable = new DataTable(VehicleTypeDTO.SESSION_VEHICLETYPE_DATA_TABLE, vehicleTypeList, 
	        new String[] {VehicleTypeDTO.ACTION_SEARCH_BY_NAME}, new String[] {"Name"});
	    dataTable.setColumnNameArr(new String[] {"ID", "CODE","NAME","ADDED BY", "ADDED TIMESTAMP","UPDATED BY", "UPDATED TIMESTAMP",""});
	    dataTable.setColumnWidthArr(new String[] {"5", "5", "15", "15","15","15","15","5"});

	    // ✅ Fix: Set SESSION_PERSON attribute
	    setSessionAttribute(VehicleTypeDTO.SESSION_VEHICLETYPE, new VehicleTypeDTO());
	    setSessionAttribute(VehicleTypeDTO.SESSION_VEHICLETYPE_DATA_TABLE, dataTable);
	    setSessionAttribute(VehicleTypeDTO.SESSION_VEHICLETYPE_LIST, vehicleTypeList);
	    //add session inside()
	}

}
