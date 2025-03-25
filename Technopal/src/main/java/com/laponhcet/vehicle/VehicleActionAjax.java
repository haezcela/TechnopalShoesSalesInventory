package com.laponhcet.vehicle;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.mytechnopal.ActionResponse;
import com.mytechnopal.DataTable;

import com.mytechnopal.base.ActionAjaxBase;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.link.LinkDTO;
import com.mytechnopal.util.PageUtil;
import com.mytechnopal.util.StringUtil;

public class VehicleActionAjax extends ActionAjaxBase {
    private static final long serialVersionUID = 1L;

    
    //METHODS TO DIPSPLAY = LIST
    protected void dataTableAction(JSONObject jsonObj, DataTable dataTable) {
        initDataTable(dataTable);
        try {
            jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataTablePage(sessionInfo, VehicleUtil.getDataTableStr(sessionInfo, dataTable)));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected void setInput(String action) {
        VehicleDTO vehicle = (VehicleDTO) getSessionAttribute(VehicleDTO.SESSION_VEHICLE);
        vehicle.setVehicleTypeCode(getRequestString("cboVehicleTypeCode"));
        //vehicle.setVehicleTypeCode(getRequestString("txtVehicleTypeCode"));
        vehicle.setPlateNumber(getRequestString("txtPlateNumber"));
    }

	
    protected void validateInput(String action) {
        if (action.equalsIgnoreCase(DataTable.ACTION_ADD_SAVE) || action.equalsIgnoreCase(DataTable.ACTION_UPDATE_SAVE)) {
            if (StringUtil.isEmpty(getRequestString("cboVehicleTypeCode"))) {
                actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "VehicleTypeCode");
            }
//            if (StringUtil.isEmpty(getRequestString("txtVehicleTypeCode"))) {
//                actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "VehicleTypeCode");
//            }
            if (StringUtil.isEmpty(getRequestString("txtPlateNumber"))) {
                actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "PlateNumber");
            }
        }
    }
	
    protected void executeLogic(JSONObject jsonObj, DataTable dataTable, String action) {
    	if (action.equalsIgnoreCase(DataTable.ACTION_VIEW)) {
    	    VehicleDTO vehicle = (VehicleDTO) dataTable.getSelectedRecord();
    	    
    	    // Debugging: Print selected person details
    	    System.out.println("Selected Person for VIEW: " + (vehicle != null ? vehicle.getId() + " - " + vehicle.getPlateNumber() : "No vehicle selected"));
    	    
    	    try {
    	        jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataViewPage(sessionInfo, VehicleUtil.getDataViewStr(sessionInfo, vehicle), "")); //Title and Buttons
    	    } catch (JSONException e) {
    	        e.printStackTrace();
    	    }
    	}
    	else if (action.equalsIgnoreCase(DataTable.ACTION_ADD_VIEW)) {
            VehicleDTO vehicle = new VehicleDTO();

            try {
                jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataEntryPage(sessionInfo, VehicleUtil.getDataEntryStr(sessionInfo, vehicle), ""));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            setSessionAttribute(VehicleDTO.SESSION_VEHICLE, vehicle);
        }

    	else if(action.equalsIgnoreCase(DataTable.ACTION_ADD_SAVE)) {
			
			VehicleDAO vehicleDAO = new VehicleDAO();
			VehicleDTO vehicle = (VehicleDTO) getSessionAttribute(VehicleDTO.SESSION_VEHICLE);
			
			vehicle.setAddedBy(sessionInfo.getCurrentUser().getCode());
			vehicleDAO.executeAdd(vehicle);
			actionResponse = (ActionResponse) vehicleDAO.getResult().get(ActionResponse.SESSION_ACTION_RESPONSE);
		
			if (StringUtil.isEmpty(actionResponse.getType())) {
                actionResponse.constructMessage(ActionResponse.TYPE_SUCCESS, "Added");
                setSessionAttribute(VehicleDTO.SESSION_VEHICLE_LIST, vehicleDAO.getVehicleList());
            }
    	}

        else if (action.equalsIgnoreCase(DataTable.ACTION_UPDATE_VIEW)) {
            VehicleDTO vehicleUpdate = (VehicleDTO) dataTable.getSelectedRecord();
            try {
                jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataEntryPage(sessionInfo, VehicleUtil.getDataEntryStr(sessionInfo, vehicleUpdate), ""));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            setSessionAttribute(VehicleDTO.SESSION_VEHICLE, vehicleUpdate);
        }
    	
        else if (action.equalsIgnoreCase(DataTable.ACTION_UPDATE_SAVE)) {
            VehicleDAO vehicleDAO = new VehicleDAO();
            VehicleDTO vehicle = (VehicleDTO) getSessionAttribute(VehicleDTO.SESSION_VEHICLE);
            vehicleDAO.executeUpdate(vehicle);
            actionResponse = (ActionResponse) vehicleDAO.getResult().get(ActionResponse.SESSION_ACTION_RESPONSE);
            if (StringUtil.isEmpty(actionResponse.getType())) {
                setSessionAttribute(VehicleDTO.SESSION_VEHICLE_LIST, new VehicleDAO().getVehicleList());
                actionResponse.constructMessage(ActionResponse.TYPE_SUCCESS, "Updated");
            }
        }
    	
        else if (action.equalsIgnoreCase(DataTable.ACTION_DELETE_VIEW)) {
            VehicleDTO vehicleSelected = (VehicleDTO) dataTable.getSelectedRecord();
            try {
                jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataViewPage(sessionInfo, VehicleUtil.getDataViewStr(sessionInfo, vehicleSelected), ""));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            setSessionAttribute(VehicleDTO.SESSION_VEHICLE, vehicleSelected);
        }
        else if (action.equalsIgnoreCase(DataTable.ACTION_DELETE)) {
            VehicleDAO vehicleDAO = new VehicleDAO();
            VehicleDTO vehicle = (VehicleDTO) getSessionAttribute(VehicleDTO.SESSION_VEHICLE);
            vehicleDAO.executeDelete(vehicle);
            actionResponse = (ActionResponse) vehicleDAO.getResult().get(ActionResponse.SESSION_ACTION_RESPONSE);
            if (StringUtil.isEmpty(actionResponse.getType())) {
                setSessionAttribute(VehicleDTO.SESSION_VEHICLE_LIST, new VehicleDAO().getVehicleList());
                actionResponse.constructMessage(ActionResponse.TYPE_SUCCESS, "Deleted");
            }
        }
    }

	protected void initDataTable(DataTable dataTable) {
		List<DTOBase> vehicleTypeList = (List<DTOBase>) getSessionAttribute(VehicleDTO.SESSION_VEHICLE_LIST);
		dataTable.setRecordList(vehicleTypeList);
		dataTable.setCurrentPageRecordList();
	}
	  protected void search(DataTable dataTable) {
		    String searchValue = dataTable.getSearchValue();
		    String searchCriteria = VehicleDTO.ACTION_SEARCH_BY_PLATENUMBER;
		    System.out.println("Search Criteria: " + searchCriteria);
		    System.out.println("Search Value: " + searchValue);
		    
		    if (searchCriteria.equalsIgnoreCase(VehicleDTO.ACTION_SEARCH_BY_PLATENUMBER)) {
		        List<DTOBase> personList = (List<DTOBase>) getSessionAttribute(VehicleDTO.SESSION_VEHICLE_LIST);
		        VehicleUtil.searchByName(dataTable, searchValue, personList);
		    } else if (searchCriteria.equalsIgnoreCase(VehicleDTO.ACTION_SEARCH_BY_PLATENUMBER)) {
		    	
		    }
	    } 
}

