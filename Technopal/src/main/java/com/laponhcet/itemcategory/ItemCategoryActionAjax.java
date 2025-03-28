package com.laponhcet.itemcategory;

import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

import com.laponhcet.itemcategory.ItemCategoryDAO;
import com.laponhcet.itemcategory.ItemCategoryDTO;
import com.laponhcet.itemcategory.ItemCategoryUtil;
import com.laponhcet.itemunit.ItemUnitDTO;
import com.laponhcet.vehicle.VehicleDAO;
import com.laponhcet.vehicle.VehicleDTO;
import com.laponhcet.vehicle.VehicleUtil;
import com.laponhcet.vehicletype.VehicleTypeDTO;
import com.laponhcet.itemcategory.ItemCategoryDTO;
import com.mytechnopal.ActionResponse;
import com.mytechnopal.DataTable;
import com.mytechnopal.base.ActionAjaxBase;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.link.LinkDTO;
import com.mytechnopal.util.DTOUtil;
import com.mytechnopal.util.PageUtil;
import com.mytechnopal.util.StringUtil;

public class ItemCategoryActionAjax extends ActionAjaxBase {
    private static final long serialVersionUID = 1L;

    protected void dataTableAction(JSONObject jsonObj, DataTable dataTable) {
        initDataTable(dataTable);
        try {
            jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataTablePage(sessionInfo, ItemCategoryUtil.getDataTableStr(sessionInfo, dataTable)));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected void setInput(String action) {
        ItemCategoryDTO itemCategory = (ItemCategoryDTO) getSessionAttribute(ItemCategoryDTO.SESSION_ITEM_CATEGORY);
        itemCategory.setCode(getRequestString("txtCode"));
        itemCategory.setName(getRequestString("txtName"));
    }

    protected void validateInput(String action) {
    	ItemCategoryDTO itemCategory = (ItemCategoryDTO) getSessionAttribute(ItemCategoryDTO.SESSION_ITEM_CATEGORY);
        List<DTOBase> itemCategoryList = (List<DTOBase>) getSessionAttribute(ItemCategoryDTO.SESSION_ITEM_CATEGORY_LIST);
        if (action.equalsIgnoreCase(DataTable.ACTION_ADD_SAVE) || action.equalsIgnoreCase(DataTable.ACTION_UPDATE_SAVE)) {
           
            System.out.println("Action: " + action);
            System.out.println("ItemCategory ID: " + itemCategory.getId());

            if (StringUtil.isEmpty(itemCategory.getCode())) {
                actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "Code");
            }
            else if (StringUtil.isEmpty(itemCategory.getName())) {
                actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "Name");
            }
            
            else {
            	ItemCategoryDTO itemCategoryOrig = (ItemCategoryDTO) getSessionAttribute(ItemCategoryDTO.SESSION_ITEM_CATEGORY + "_ORIG");
            	System.out.println("orig code: " + itemCategoryOrig.getCode());
            	System.out.println("orig name: " + itemCategoryOrig.getName());
            	if(itemCategoryOrig.getCode().equalsIgnoreCase(itemCategory.getCode()) && itemCategoryOrig.getName().equalsIgnoreCase(itemCategory.getName())) {
            		 actionResponse.constructMessage(ActionResponse.TYPE_INFO, "No changes were made. Click Ok and then close");
            	}
            	else {
	            	if (!isCodeUnique(itemCategory.getCode(), itemCategory.getId(), itemCategoryList)) {
	                    actionResponse.constructMessage(ActionResponse.TYPE_EXIST, "Code");
	                }
	                else if (!isNameUnique(itemCategory.getName(), itemCategory.getId(), itemCategoryList)) {
	                    actionResponse.constructMessage(ActionResponse.TYPE_EXIST, "Name");
	                }
            	}
            }
        }
        
    }
    
    private boolean isCodeUnique(String code, int currentId, List<DTOBase> itemCategoryList) {
        for (DTOBase dto : itemCategoryList) {
        	ItemCategoryDTO existingItemCategory = (ItemCategoryDTO) dto;
        	if (existingItemCategory.getId() == currentId) { 
                continue; // Ignore the current item
            }
        	if (existingItemCategory.getCode().equalsIgnoreCase(code)) {
                return false; 
            }
        }
        return true; 
    }
    
    private boolean isNameUnique(String name, int currentId, List<DTOBase> itemCategoryList) {
        for (DTOBase dto : itemCategoryList) {
        	ItemCategoryDTO existingItemCategory = (ItemCategoryDTO) dto;
        	if (existingItemCategory.getId() == currentId) { 
                continue; // Ignore the current item
            }
        	if (existingItemCategory.getName().equalsIgnoreCase(name)) {
                return false; 
            }
        }
        return true; 
    }
    
    
    protected void executeLogic(JSONObject jsonObj, DataTable dataTable, String action) {
        if (action.equalsIgnoreCase(DataTable.ACTION_ADD_SAVE) || action.equalsIgnoreCase(DataTable.ACTION_UPDATE_SAVE)) {
           
            validateInput(action);
           
            if (actionResponse.getMessageStr() != null && !actionResponse.getMessageStr().isEmpty()) {
                System.out.println("Validation errors found. Aborting save operation."); // Debug log
                return; 
            }
           
            ItemCategoryDAO itemCategoryDAO = new ItemCategoryDAO();
            ItemCategoryDTO itemCategory = (ItemCategoryDTO) getSessionAttribute(ItemCategoryDTO.SESSION_ITEM_CATEGORY);

            if (action.equalsIgnoreCase(DataTable.ACTION_ADD_SAVE)) {
            	itemCategory.setAddedBy(sessionInfo.getCurrentUser().getCode());
            	itemCategoryDAO.executeAdd(itemCategory);
            	
                actionResponse = (ActionResponse) itemCategoryDAO.getResult().get(ActionResponse.SESSION_ACTION_RESPONSE);
                if (actionResponse.getMessageStr() == null || actionResponse.getMessageStr().isEmpty()) {
                    setSessionAttribute(ItemCategoryDTO.SESSION_ITEM_CATEGORY_LIST, itemCategoryDAO.getItemCategoryList());
                    actionResponse.constructMessage(ActionResponse.TYPE_SUCCESS, "Added");
                }
                
            } else if (action.equalsIgnoreCase(DataTable.ACTION_UPDATE_SAVE)) {
            	itemCategory.setUpdatedBy(sessionInfo.getCurrentUser().getCode());
            	itemCategoryDAO.executeUpdate(itemCategory);
            	
                actionResponse = (ActionResponse) itemCategoryDAO.getResult().get(ActionResponse.SESSION_ACTION_RESPONSE);
                if (actionResponse.getMessageStr() == null || actionResponse.getMessageStr().isEmpty()) {
                    setSessionAttribute(ItemCategoryDTO.SESSION_ITEM_CATEGORY_LIST, itemCategoryDAO.getItemCategoryList());
                    actionResponse.constructMessage(ActionResponse.TYPE_SUCCESS, "Updated");
                }
            }

            
        } else if (action.equalsIgnoreCase(DataTable.ACTION_VIEW)) {
        	ItemCategoryDTO itemCategory = (ItemCategoryDTO) dataTable.getSelectedRecord();

            // Debugging: Print selected vehicle details
            System.out.println("Selected Vehicle for VIEW: " + (itemCategory != null ? itemCategory.getId() + " - " + itemCategory.getCode() : "No vehicle selected"));

            try {
                jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataViewPage(sessionInfo, ItemCategoryUtil.getDataViewStr(sessionInfo, itemCategory),"")); // Title and Buttons
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } 
        else if (action.equalsIgnoreCase(DataTable.ACTION_ADD_VIEW)) {
            ItemCategoryDTO itemCategory = new ItemCategoryDTO();
            List<DTOBase> itemCategoryList = (List<DTOBase>) getSessionAttribute(ItemCategoryDTO.SESSION_ITEM_CATEGORY_LIST);

            try {
                jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataEntryPage(sessionInfo, ItemCategoryUtil.getDataEntryStr(sessionInfo, itemCategory),""));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            setSessionAttribute(ItemCategoryDTO.SESSION_ITEM_CATEGORY, itemCategory);
        } 
        else if (action.equalsIgnoreCase(DataTable.ACTION_UPDATE_VIEW)) {
        	ItemCategoryDTO itemCategory = (ItemCategoryDTO) dataTable.getSelectedRecord();
            List<DTOBase> itemCategoryList = (List<DTOBase>) getSessionAttribute(ItemCategoryDTO.SESSION_ITEM_CATEGORY_LIST);
            try {
                jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataEntryPage(sessionInfo, ItemCategoryUtil.getDataEntryStr(sessionInfo, itemCategory),""));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            setSessionAttribute(ItemCategoryDTO.SESSION_ITEM_CATEGORY, itemCategory);
            setSessionAttribute(ItemCategoryDTO.SESSION_ITEM_CATEGORY + "_ORIG",  itemCategory.getItemCategory());
        } 
        else if (action.equalsIgnoreCase(DataTable.ACTION_DELETE_VIEW)) {
        	ItemCategoryDTO itemCategorySelected = (ItemCategoryDTO) dataTable.getSelectedRecord();
            try {
                jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataViewPage(sessionInfo, ItemCategoryUtil.getDataViewStr(sessionInfo, itemCategorySelected),""));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            setSessionAttribute(ItemCategoryDTO.SESSION_ITEM_CATEGORY, itemCategorySelected);
        } 
        else if (action.equalsIgnoreCase(DataTable.ACTION_DELETE)) {
        	ItemCategoryDAO itemCategoryDAO = new ItemCategoryDAO();
            ItemCategoryDTO itemCategory = (ItemCategoryDTO) getSessionAttribute(ItemCategoryDTO.SESSION_ITEM_CATEGORY);
            itemCategoryDAO.executeDelete(itemCategory);
            actionResponse = (ActionResponse) itemCategoryDAO.getResult().get(ActionResponse.SESSION_ACTION_RESPONSE);
            if (actionResponse.getMessageStr() == null || actionResponse.getMessageStr().isEmpty()) {
                setSessionAttribute(ItemCategoryDTO.SESSION_ITEM_CATEGORY_LIST, new ItemCategoryDAO().getItemCategoryList());
                actionResponse.constructMessage(ActionResponse.TYPE_SUCCESS, "Deleted");
            }
        }
    }

    protected void initDataTable(DataTable dataTable) {
        List<DTOBase> itemCategoryList = (List<DTOBase>) getSessionAttribute(ItemCategoryDTO.SESSION_ITEM_CATEGORY_LIST);
        dataTable.setRecordList(itemCategoryList);
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
        }
    }
}
   