package com.laponhcet.itemunit;

import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

import com.laponhcet.itemcategory.ItemCategoryDAO;
import com.laponhcet.itemcategory.ItemCategoryDTO;
import com.laponhcet.itemcategory.ItemCategoryUtil;
import com.laponhcet.itemunit.ItemUnitDAO;
import com.laponhcet.itemunit.ItemUnitDTO;
import com.laponhcet.itemunit.ItemUnitUtil;
import com.mytechnopal.ActionResponse;
import com.mytechnopal.DataTable;
import com.mytechnopal.base.ActionAjaxBase;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.link.LinkDTO;
import com.mytechnopal.util.DTOUtil;
import com.mytechnopal.util.PageUtil;
import com.mytechnopal.util.StringUtil;

public class ItemUnitActionAjax extends ActionAjaxBase {
    private static final long serialVersionUID = 1L;

    protected void dataTableAction(JSONObject jsonObj, DataTable dataTable) {
        initDataTable(dataTable);
        try {
            jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataTablePage(sessionInfo, ItemUnitUtil.getDataTableStr(sessionInfo, dataTable)));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected void setInput(String action) {
        ItemUnitDTO itemUnit = (ItemUnitDTO) getSessionAttribute(ItemUnitDTO.SESSION_ITEM_UNIT);
        itemUnit.setCode(getRequestString("txtCode"));
        itemUnit.setName(getRequestString("txtName"));
    }

    protected void validateInput(String action) {
    	ItemUnitDTO itemUnit = (ItemUnitDTO) getSessionAttribute(ItemUnitDTO.SESSION_ITEM_UNIT);
        List<DTOBase> itemUnitList = (List<DTOBase>) getSessionAttribute(ItemUnitDTO.SESSION_ITEM_UNIT_LIST);
        if (action.equalsIgnoreCase(DataTable.ACTION_ADD_SAVE) || action.equalsIgnoreCase(DataTable.ACTION_UPDATE_SAVE)) {
           
            System.out.println("Action: " + action);
            System.out.println("ItemCategory ID: " + itemUnit.getId());

            if (StringUtil.isEmpty(itemUnit.getCode())) {
                actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "Code");
            }
            else if (StringUtil.isEmpty(itemUnit.getName())) {
                actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "Name");
            }
            
            else {
            	ItemUnitDTO itemUnitOrig = (ItemUnitDTO) getSessionAttribute(ItemUnitDTO.SESSION_ITEM_UNIT + "_ORIG");
            	System.out.println("orig code: " + itemUnitOrig.getCode());
            	System.out.println("orig name: " + itemUnitOrig.getName());
            	if(itemUnitOrig.getCode().equalsIgnoreCase(itemUnit.getCode()) && itemUnitOrig.getName().equalsIgnoreCase(itemUnit.getName())) {
            		 actionResponse.constructMessage(ActionResponse.TYPE_INFO, "No changes were made. Click Ok and then close");
            	}
            	else {
	            	if (!isCodeUnique(itemUnit.getCode(), itemUnit.getId(), itemUnitList)) {
	                    actionResponse.constructMessage(ActionResponse.TYPE_EXIST, "Code");
	                }
	                else if (!isNameUnique(itemUnit.getName(), itemUnit.getId(), itemUnitList)) {
	                    actionResponse.constructMessage(ActionResponse.TYPE_EXIST, "Name");
                }
            }
         }
      }
 }
    
    private boolean isCodeUnique(String code, int currentId, List<DTOBase> itemUnitList) {
        for (DTOBase dto : itemUnitList) {
        	ItemUnitDTO existingItemUnitCode = (ItemUnitDTO) dto;
        	if (existingItemUnitCode.getId() == currentId) { 
                continue; // Ignore the current item
            }
        	if (existingItemUnitCode.getCode().equalsIgnoreCase(code)) {
                return false; 
            }
        }
        return true; 
    }
    
    private boolean isNameUnique(String name, int currentId, List<DTOBase> itemUnitList) {
        for (DTOBase dto : itemUnitList) {
        	ItemUnitDTO existingItemUnitCame = (ItemUnitDTO) dto;
        	if (existingItemUnitCame.getId() == currentId) { 
                continue; // Ignore the current item
            }
        	if (existingItemUnitCame.getName().equalsIgnoreCase(name)) {
                return false; 
            }
        }
        return true; 
    }

    protected void executeLogic(JSONObject jsonObj, DataTable dataTable, String action) {
    if (action.equalsIgnoreCase(DataTable.ACTION_ADD_SAVE) || action.equalsIgnoreCase(DataTable.ACTION_UPDATE_SAVE)) {
    	
    	validateInput(action);
    	
    	if (actionResponse.getMessageStr() != null && !actionResponse.getMessageStr().isEmpty()) {
            System.out.println("Validation errors found. Aborting save operation."); 
            return; 
        }
    	
    	ItemUnitDAO itemUnitDAO = new ItemUnitDAO();
        ItemUnitDTO itemUnit = (ItemUnitDTO) getSessionAttribute(ItemUnitDTO.SESSION_ITEM_UNIT);
        
        if (action.equalsIgnoreCase(DataTable.ACTION_ADD_SAVE)) {
            itemUnit.setAddedBy(sessionInfo.getCurrentUser().getCode());
            itemUnitDAO.executeAdd(itemUnit);
            
            actionResponse = (ActionResponse) itemUnitDAO.getResult().get(ActionResponse.SESSION_ACTION_RESPONSE);
            if (actionResponse.getMessageStr() == null || actionResponse.getMessageStr().isEmpty()) {
                setSessionAttribute(ItemUnitDTO.SESSION_ITEM_UNIT_LIST, itemUnitDAO.getItemUnitList());
                actionResponse.constructMessage(ActionResponse.TYPE_SUCCESS, "Added");
            }
        }
        else if (action.equalsIgnoreCase(DataTable.ACTION_UPDATE_SAVE)) {
        	 itemUnit.setUpdatedBy(sessionInfo.getCurrentUser().getCode());
             itemUnitDAO.executeUpdate(itemUnit);
        
            actionResponse = (ActionResponse) itemUnitDAO.getResult().get(ActionResponse.SESSION_ACTION_RESPONSE);
            if (actionResponse.getMessageStr() == null || actionResponse.getMessageStr().isEmpty()) {
                setSessionAttribute(ItemUnitDTO.SESSION_ITEM_UNIT_LIST, itemUnitDAO.getItemUnitList());
                actionResponse.constructMessage(ActionResponse.TYPE_SUCCESS, "Updated");
            }
        	} 
    	}
        else if (action.equalsIgnoreCase(DataTable.ACTION_VIEW)) {
        	ItemUnitDTO itemUnit = (ItemUnitDTO) dataTable.getSelectedRecord();
        	try {
                jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataViewPage(sessionInfo, ItemUnitUtil.getDataViewStr(sessionInfo, itemUnit), ""));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } 
        else if (action.equalsIgnoreCase(DataTable.ACTION_ADD_VIEW)) {
        	ItemUnitDTO itemUnit = new ItemUnitDTO();
            List<DTOBase> itemUnitList = (List<DTOBase>) getSessionAttribute(ItemUnitDTO.SESSION_ITEM_UNIT_LIST);

            try {
                jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataEntryPage(sessionInfo, ItemUnitUtil.getDataEntryStr(sessionInfo, itemUnit),""));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            setSessionAttribute(ItemUnitDTO.SESSION_ITEM_UNIT, itemUnit);
        } 
        else if (action.equalsIgnoreCase(DataTable.ACTION_UPDATE_VIEW)) {
        	ItemUnitDTO itemUnit = (ItemUnitDTO) dataTable.getSelectedRecord();
            List<DTOBase> itemUnitList = (List<DTOBase>) getSessionAttribute(ItemUnitDTO.SESSION_ITEM_UNIT_LIST);
            try {
                jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataEntryPage(sessionInfo, ItemUnitUtil.getDataEntryStr(sessionInfo, itemUnit),""));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            setSessionAttribute(ItemUnitDTO.SESSION_ITEM_UNIT, itemUnit);
            setSessionAttribute(ItemUnitDTO.SESSION_ITEM_UNIT + "_ORIG",  itemUnit.getItemUnit());
        } 
        else if (action.equalsIgnoreCase(DataTable.ACTION_DELETE_VIEW)) {
        	ItemUnitDTO itemUnitSelected = (ItemUnitDTO) dataTable.getSelectedRecord();
            try {
                jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataViewPage(sessionInfo, ItemUnitUtil.getDataViewStr(sessionInfo, itemUnitSelected),""));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            setSessionAttribute(ItemUnitDTO.SESSION_ITEM_UNIT, itemUnitSelected);
        } 
        else if (action.equalsIgnoreCase(DataTable.ACTION_DELETE)) {
        	ItemUnitDAO itemUnitDAO = new ItemUnitDAO();
        	ItemUnitDTO itemUnit = (ItemUnitDTO) getSessionAttribute(ItemUnitDTO.SESSION_ITEM_UNIT);
            itemUnitDAO.executeDelete(itemUnit);
            actionResponse = (ActionResponse) itemUnitDAO.getResult().get(ActionResponse.SESSION_ACTION_RESPONSE);
            if (actionResponse.getMessageStr() == null || actionResponse.getMessageStr().isEmpty()) {
                setSessionAttribute(ItemUnitDTO.SESSION_ITEM_UNIT_LIST, itemUnitDAO.getItemUnitList());
                actionResponse.constructMessage(ActionResponse.TYPE_SUCCESS, "Deleted");
            }
        }
    
    }


    protected void initDataTable(DataTable dataTable) {
        List<DTOBase> itemUnitList = (List<DTOBase>) getSessionAttribute(ItemUnitDTO.SESSION_ITEM_UNIT_LIST);
        dataTable.setRecordList(itemUnitList);
        dataTable.setCurrentPageRecordList();
    }

    protected void search(DataTable dataTable) {
        String searchValue = dataTable.getSearchValue();
        System.out.println("Search Value: " + searchValue);

        List<DTOBase> itemUnitList = (List<DTOBase>) getSessionAttribute(ItemUnitDTO.SESSION_ITEM_UNIT_LIST);
        ItemUnitUtil.searchByName(dataTable, searchValue, itemUnitList);
    }
}
