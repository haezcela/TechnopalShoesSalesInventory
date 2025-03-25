package com.laponhcet.vehicle;

import java.io.Serializable;
import java.util.List;

import com.laponhcet.vehicletype.VehicleTypeDTO;
import com.laponhcet.vehicletype.VehicleTypeDAO;
import com.mytechnopal.DataTable;
import com.mytechnopal.SessionInfo;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.base.WebControlBase;
import com.mytechnopal.webcontrol.DataTableWebControl;
import com.mytechnopal.webcontrol.SelectWebControl;
import com.mytechnopal.webcontrol.TextBoxWebControl;

public class VehicleUtil implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public static String getDataTableStr(SessionInfo sessionInfo, DataTable dataTable) {
		DataTableWebControl dtwc = new DataTableWebControl(sessionInfo, dataTable);
		StringBuffer strBuff = new StringBuffer();
		if(dataTable.getRecordList().size() >= 1) {
			strBuff.append(dtwc.getDataTableHeader(sessionInfo, dataTable));
			dataTable.setDataTableRecordArr(getDataTableCurrentPageRecordArr(sessionInfo, dataTable));
			strBuff.append(dtwc.getDataTable(true, false));
		}
		return strBuff.toString();
	}
	
	private static String[][] getDataTableCurrentPageRecordArr(SessionInfo sessionInfo, DataTable dataTable) {
		String[][] strArr = new String[dataTable.getRecordListCurrentPage().size()][dataTable.getColumnNameArr().length];
		for(int row=0; row < dataTable.getRecordListCurrentPage().size(); row++) {			
			VehicleDTO vehicle = (VehicleDTO) dataTable.getRecordListCurrentPage().get(row);
			
			strArr[row][0] = String.valueOf(vehicle.getId());
			strArr[row][1] = vehicle.getCode();
			strArr[row][2] = vehicle.getVehicleTypeCode();
			strArr[row][3] = vehicle.getPlateNumber();
			strArr[row][4] = vehicle.getAddedBy();
			strArr[row][5] = String.valueOf(vehicle.getAddedTimestamp());
			strArr[row][6] = vehicle.getUpdatedBy();
			strArr[row][7] = String.valueOf(vehicle.getUpdatedTimestamp());
			strArr[row][8] = dataTable.getRecordButtonStr(sessionInfo, vehicle.getCode());
		}
		return strArr;
	}
	
	public static String getDataEntryStr(SessionInfo sessionInfo, VehicleDTO vehicle) {		
		StringBuffer strBuff = new StringBuffer();
		//Bpotstrap
		
		//This a combobox fetching all current vehicle type codes from vehicle_type
        // Fetch vehicle type codes dynamically
        String[] vehicleTypeCodes = getVehicleTypeCodes();

        strBuff.append(new SelectWebControl().getSelectWebControl(
            "col-lg-3", 
            true, 
            "VehicleTypeCode", 
            "VehicleTypeCode", 
            vehicleTypeCodes, 
            vehicle.getVehicleTypeCode(), 
            vehicleTypeCodes, 
            "Vehicle Type", 
            "", 
            ""
        ));
		//strBuff.append(new SelectWebControl().getSelectWebControl("col-lg-3", true, "VehicleTypeCode", "VehicleTypeCode", new String[] {"Male", "Female"}, vehicle.getVehicleTypeCode(), new String[] {"Male", "Female"}, "Vehicle Type", "", ""));	
		//strBuff.append(new TextBoxWebControl().getTextBoxWebControl("form-group col-lg-3", true, "VehicleTypeCode", "VehicleTypeCode", "VehicleTypeCode", vehicle.getVehicleTypeCode(), 3, WebControlBase.DATA_TYPE_STRING, ""));
		strBuff.append(new TextBoxWebControl().getTextBoxWebControl("form-group col-lg-3", true, "PlateNumber", "PlateNumber", "PlateNumber", vehicle.getPlateNumber(), 8, WebControlBase.DATA_TYPE_STRING, ""));
		return strBuff.toString();
	}
	
	public static String getDataViewStr(SessionInfo sessionInfo, VehicleDTO vehicle) {
		StringBuffer strBuff = new StringBuffer();
		strBuff.append("<div class='col-lg-12'>");
		strBuff.append("<p>Vehicle Type: " + vehicle.getVehicleTypeCode()+ "</p>");
		strBuff.append("<div class='col-lg-12'>");
		strBuff.append("<p>Plate Number: " + vehicle.getPlateNumber()+ "</p>");

		return strBuff.toString();
	}

	    private static String[] getVehicleTypeCodes() {
	        VehicleTypeDAO vehicleTypeDAO = new VehicleTypeDAO();
	        List<DTOBase> vehicleTypes = vehicleTypeDAO.getVehicleTypeList();

	        return vehicleTypes.stream()
	            .map(dto -> ((VehicleTypeDTO) dto).getCode())
	            .toArray(String[]::new);
	    }

	public static void searchByName(DataTable dataTable, String searchValue, List<DTOBase> vehicleTypeList) {
    	System.out.println("Search Value" + searchValue);
    	dataTable.setRecordListInvisible();
		for(DTOBase dto: dataTable.getRecordList()) {
			VehicleTypeDTO vehicleType = (VehicleTypeDTO) dto;
			if(vehicleType.getName().toUpperCase().contains(searchValue.toUpperCase())) {
				System.out.println("Search Value" + searchValue);
				vehicleType.setVisible(true);
			}
		}
	}
}
	