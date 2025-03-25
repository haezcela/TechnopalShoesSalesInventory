package com.laponhcet.vehicletype;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.laponhcet.person.PersonDTO;
import com.mytechnopal.ActionResponse;
import com.mytechnopal.DataTable;
import com.mytechnopal.UploadedFile;
import com.mytechnopal.banner.BannerDAO;
import com.mytechnopal.banner.BannerDTO;
import com.mytechnopal.base.ActionAjaxBase;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.link.LinkDTO;
import com.mytechnopal.util.FileUtil;
import com.mytechnopal.util.PageUtil;
import com.mytechnopal.util.StringUtil;

public class VehicleTypeActionAjax extends ActionAjaxBase {
    private static final long serialVersionUID = 1L;

    
    //METHODS TO DIPSPLAY = LIST
    protected void dataTableAction(JSONObject jsonObj, DataTable dataTable) {
        initDataTable(dataTable);
        try {
            jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataTablePage(sessionInfo, VehicleTypeUtil.getDataTableStr(sessionInfo, dataTable)));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected void setInput(String action) {
        VehicleTypeDTO vehicleType = (VehicleTypeDTO) getSessionAttribute(VehicleTypeDTO.SESSION_VEHICLETYPE);
        vehicleType.setName(getRequestString("txtName"));
    }

	
    protected void validateInput(String action) {
        if (action.equalsIgnoreCase(DataTable.ACTION_ADD_SAVE) || action.equalsIgnoreCase(DataTable.ACTION_UPDATE_SAVE)) {
            if (StringUtil.isEmpty(getRequestString("txtName"))) {
                actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "Name");
            }
        }
    }
	
    protected void executeLogic(JSONObject jsonObj, DataTable dataTable, String action) {
    	if (action.equalsIgnoreCase(DataTable.ACTION_VIEW)) {
    	    VehicleTypeDTO vehicleType = (VehicleTypeDTO) dataTable.getSelectedRecord();
    	    
    	    // Debugging: Print selected person details
    	    System.out.println("Selected Person for VIEW: " + (vehicleType != null ? vehicleType.getId() + " - " + vehicleType.getName() : "No vehicle selected"));
    	    
    	    try {
    	        jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataViewPage(sessionInfo, VehicleTypeUtil.getDataViewStr(sessionInfo, vehicleType), "")); //Title and Buttons
    	    } catch (JSONException e) {
    	        e.printStackTrace();
    	    }
    	}
    	else if (action.equalsIgnoreCase(DataTable.ACTION_ADD_VIEW)) {
            VehicleTypeDTO vehicleType = new VehicleTypeDTO();

            try {
                jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataEntryPage(sessionInfo, VehicleTypeUtil.getDataEntryStr(sessionInfo, vehicleType), ""));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            setSessionAttribute(VehicleTypeDTO.SESSION_VEHICLETYPE, vehicleType);
        }


    	else if(action.equalsIgnoreCase(DataTable.ACTION_ADD_SAVE)) {
			
			VehicleTypeDAO vehicleTypeDAO = new VehicleTypeDAO();
			VehicleTypeDTO vehicleType = (VehicleTypeDTO) getSessionAttribute(VehicleTypeDTO.SESSION_VEHICLETYPE);
			
			vehicleType.setAddedBy(sessionInfo.getCurrentUser().getCode());
			vehicleTypeDAO.executeAdd(vehicleType);
			actionResponse = (ActionResponse) vehicleTypeDAO.getResult().get(ActionResponse.SESSION_ACTION_RESPONSE);
		
			if (StringUtil.isEmpty(actionResponse.getType())) {
                actionResponse.constructMessage(ActionResponse.TYPE_SUCCESS, "Added");
                setSessionAttribute(VehicleTypeDTO.SESSION_VEHICLETYPE_LIST, vehicleTypeDAO.getVehicleTypeList());
            }
    	}

        else if (action.equalsIgnoreCase(DataTable.ACTION_UPDATE_VIEW)) {
            VehicleTypeDTO vehicleTypeUpdate = (VehicleTypeDTO) dataTable.getSelectedRecord();
            try {
                jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataEntryPage(sessionInfo, VehicleTypeUtil.getDataEntryStr(sessionInfo, vehicleTypeUpdate), ""));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            setSessionAttribute(VehicleTypeDTO.SESSION_VEHICLETYPE, vehicleTypeUpdate);
        }
    	
        else if (action.equalsIgnoreCase(DataTable.ACTION_UPDATE_SAVE)) {
            VehicleTypeDAO vehicleTypeDAO = new VehicleTypeDAO();
            VehicleTypeDTO vehicleType = (VehicleTypeDTO) getSessionAttribute(VehicleTypeDTO.SESSION_VEHICLETYPE);
            vehicleTypeDAO.executeUpdate(vehicleType);
            actionResponse = (ActionResponse) vehicleTypeDAO.getResult().get(ActionResponse.SESSION_ACTION_RESPONSE);
            if (StringUtil.isEmpty(actionResponse.getType())) {
                setSessionAttribute(VehicleTypeDTO.SESSION_VEHICLETYPE_LIST, new VehicleTypeDAO().getVehicleTypeList());
                actionResponse.constructMessage(ActionResponse.TYPE_SUCCESS, "Updated");
            }
        }
    	
        else if (action.equalsIgnoreCase(DataTable.ACTION_DELETE_VIEW)) {
            VehicleTypeDTO vehicleTypeSelected = (VehicleTypeDTO) dataTable.getSelectedRecord();
            try {
                jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataViewPage(sessionInfo, VehicleTypeUtil.getDataViewStr(sessionInfo, vehicleTypeSelected), ""));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            setSessionAttribute(VehicleTypeDTO.SESSION_VEHICLETYPE, vehicleTypeSelected);
        }
        else if (action.equalsIgnoreCase(DataTable.ACTION_DELETE)) {
            VehicleTypeDAO vehicleTypeDAO = new VehicleTypeDAO();
            VehicleTypeDTO vehicleType = (VehicleTypeDTO) getSessionAttribute(VehicleTypeDTO.SESSION_VEHICLETYPE);
            vehicleTypeDAO.executeDelete(vehicleType);
            actionResponse = (ActionResponse) vehicleTypeDAO.getResult().get(ActionResponse.SESSION_ACTION_RESPONSE);
            if (StringUtil.isEmpty(actionResponse.getType())) {
                setSessionAttribute(VehicleTypeDTO.SESSION_VEHICLETYPE_LIST, new VehicleTypeDAO().getVehicleTypeList());
                actionResponse.constructMessage(ActionResponse.TYPE_SUCCESS, "Deleted");
            }
        }
    }

	protected void initDataTable(DataTable dataTable) {
		List<DTOBase> vehicleTypeList = (List<DTOBase>) getSessionAttribute(VehicleTypeDTO.SESSION_VEHICLETYPE_LIST);
		dataTable.setRecordList(vehicleTypeList);
		dataTable.setCurrentPageRecordList();
	}
	  protected void search(DataTable dataTable) {
		    String searchValue = dataTable.getSearchValue();
		    String searchCriteria = VehicleTypeDTO.ACTION_SEARCH_BY_NAME;
		    System.out.println("Search Criteria: " + searchCriteria);
		    System.out.println("Search Value: " + searchValue);
		    
		    
		    
		    if (searchCriteria.equalsIgnoreCase(VehicleTypeDTO.ACTION_SEARCH_BY_NAME)) {
		        List<DTOBase> personList = (List<DTOBase>) getSessionAttribute(VehicleTypeDTO.SESSION_VEHICLETYPE_LIST);
		        VehicleTypeUtil.searchByName(dataTable, searchValue, personList);
		    } else if (searchCriteria.equalsIgnoreCase(VehicleTypeDTO.ACTION_SEARCH_BY_NAME)) {
		    	
		    }
	    } 
}

