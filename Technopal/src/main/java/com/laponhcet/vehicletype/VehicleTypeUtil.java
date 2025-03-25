package com.laponhcet.vehicletype;

import java.io.Serializable;
import java.util.List;


import com.mytechnopal.DataTable;
import com.mytechnopal.SessionInfo;

import com.mytechnopal.base.DTOBase;
import com.mytechnopal.base.WebControlBase;

import com.mytechnopal.webcontrol.DataTableWebControl;

import com.mytechnopal.webcontrol.TextBoxWebControl;

public class VehicleTypeUtil implements Serializable {
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
			VehicleTypeDTO vehicleType = (VehicleTypeDTO) dataTable.getRecordListCurrentPage().get(row);
			
			strArr[row][0] = String.valueOf(vehicleType.getId());
			strArr[row][1] = vehicleType.getCode();
			strArr[row][2] = vehicleType.getName();
			strArr[row][3] = vehicleType.getAddedBy();
			strArr[row][4] = String.valueOf(vehicleType.getAddedTimestamp());
			strArr[row][5] = vehicleType.getUpdatedBy();
			strArr[row][6] = String.valueOf(vehicleType.getUpdatedTimestamp());
			strArr[row][7] = dataTable.getRecordButtonStr(sessionInfo, vehicleType.getCode());
		}
		return strArr;
	}
	
	public static String getDataEntryStr(SessionInfo sessionInfo, VehicleTypeDTO vehicleType) {		
		StringBuffer strBuff = new StringBuffer();
		//Bpotstrap

		strBuff.append(new TextBoxWebControl().getTextBoxWebControl("form-group col-lg-3", true, "Name", "Name", "Name", vehicleType.getName(), 30, WebControlBase.DATA_TYPE_STRING, ""));

		return strBuff.toString();
	}
	
	public static String getDataViewStr(SessionInfo sessionInfo, VehicleTypeDTO vehicleType) {
		StringBuffer strBuff = new StringBuffer();
		 strBuff.append("<div class='col-lg-12'>");
		//strBuff.append("<p>Code: " + vehicleType.getCode() + "</p>");
		strBuff.append("<p>Name: " + vehicleType.getName()+ "</p>");

		return strBuff.toString();
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
