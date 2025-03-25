package com.laponhcet.itemunit;

import java.io.File;


import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.laponhcet.enrollment.EnrollmentDTO;
import com.mytechnopal.ActionResponse;
import com.mytechnopal.DataTable;
import com.mytechnopal.UploadedFile;
import com.mytechnopal.base.ActionAjaxBase;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.link.LinkDTO;
import com.mytechnopal.util.DateTimeUtil;
import com.mytechnopal.util.PageUtil;
import com.mytechnopal.util.StringUtil;

public class ItemUnitActionAjax extends ActionAjaxBase {
    private static final long serialVersionUID = 1L;

    protected void setInput(String action) {
    	ItemUnitDTO itemUnit = (ItemUnitDTO) getSessionAttribute(ItemUnitDTO.SESSION_ITEM_UNIT);
        itemUnit.setName(getRequestString("txtName"));
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
        	ItemUnitDTO unitSelected = (ItemUnitDTO) dataTable.getSelectedRecord();
            try {
                jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataViewPage(sessionInfo, ItemUnitUtil.getDataViewStr(sessionInfo, unitSelected),""));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (action.equalsIgnoreCase(DataTable.ACTION_ADD_VIEW)) {
        	ItemUnitDTO unitSelected = new ItemUnitDTO();
            try {
                jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataEntryPage(sessionInfo, ItemUnitUtil.getDataEntryStr(sessionInfo, unitSelected, null),""));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            setSessionAttribute(ItemUnitDTO.SESSION_ITEM_UNIT, unitSelected);
        } else if (action.equalsIgnoreCase(DataTable.ACTION_DELETE_VIEW)) {
        	ItemUnitDTO unitSelected = (ItemUnitDTO) dataTable.getSelectedRecord();
            try {
                jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataViewPage(sessionInfo, ItemUnitUtil.getDataViewStr(sessionInfo, unitSelected),""));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            setSessionAttribute(ItemUnitDTO.SESSION_ITEM_UNIT, unitSelected);
        } else if (action.equalsIgnoreCase(DataTable.ACTION_ADD_SAVE)) {
        	ItemUnitDAO unitDAO = new ItemUnitDAO();
        	ItemUnitDTO unitCategory = (ItemUnitDTO) getSessionAttribute(ItemUnitDTO.SESSION_ITEM_UNIT);
            unitCategory.setAddedBy(sessionInfo.getCurrentUser().getCode());
            unitDAO.executeAdd(unitCategory);
            actionResponse = (ActionResponse) unitDAO.getResult().get(ActionResponse.SESSION_ACTION_RESPONSE);
            
            if (StringUtil.isEmpty(actionResponse.getType())) {
                actionResponse.constructMessage(ActionResponse.TYPE_SUCCESS, "Added");
                setSessionAttribute(ItemUnitDTO.SESSION_ITEM_UNIT_LIST, new ItemUnitDAO().getItemUnitList());
            }
        } else if (action.equalsIgnoreCase(DataTable.ACTION_DELETE)) {
        	ItemUnitDAO unitDAO = new ItemUnitDAO();
        	ItemUnitDTO unitCategory = (ItemUnitDTO) getSessionAttribute(ItemUnitDTO.SESSION_ITEM_UNIT);
            unitDAO.executeDelete(unitCategory); 
            actionResponse = (ActionResponse) unitDAO.getResult().get(ActionResponse.SESSION_ACTION_RESPONSE);
            
            if (StringUtil.isEmpty(actionResponse.getType())) {
                setSessionAttribute(ItemUnitDTO.SESSION_ITEM_UNIT_LIST, new ItemUnitDAO().getItemUnitList());
                actionResponse.constructMessage(ActionResponse.TYPE_SUCCESS, "Deleted");
            }
        }else if(action.equalsIgnoreCase(DataTable.ACTION_UPDATE_SAVE)) {
        	ItemUnitDTO unit = (ItemUnitDTO) getSessionAttribute(ItemUnitDTO.SESSION_ITEM_UNIT);
            unit.setUpdatedBy(sessionInfo.getCurrentUser().getCode());
            unit.setUpdatedTimestamp(DateTimeUtil.getCurrentTimestamp());

            ItemUnitDAO unitDAO = new ItemUnitDAO();
            unitDAO.executeUpdate(unit);
            actionResponse = (ActionResponse) unitDAO.getResult().get(ActionResponse.SESSION_ACTION_RESPONSE);
            if(StringUtil.isEmpty(actionResponse.getType())) {
                actionResponse.constructMessage(ActionResponse.TYPE_SUCCESS, "Updated");
                setSessionAttribute(ItemUnitDTO.SESSION_ITEM_UNIT, new ItemUnitDTO());
                setSessionAttribute(ItemUnitDTO.SESSION_ITEM_UNIT_LIST, unitDAO.getItemUnitList());
            }
        }else if (action.equalsIgnoreCase(DataTable.ACTION_UPDATE_VIEW)) {
        	ItemUnitDTO unitToUpdate = (ItemUnitDTO) dataTable.getSelectedRecord();
        	ItemUnitDTO unit_category = unitToUpdate.getItemUnit();
            try {
                jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataEntryPage(sessionInfo, ItemUnitUtil.getDataEntryStr(sessionInfo, unit_category, null),""));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            setSessionAttribute(ItemUnitDTO.SESSION_ITEM_UNIT, unit_category);   
        }
    }
    
    protected void dataTableAction(JSONObject jsonObj, DataTable dataTable) {
        initDataTable(dataTable);
        try {
            jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataTablePage(sessionInfo, ItemUnitUtil.getDataTableStr(sessionInfo, dataTable)));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    
    protected void initDataTable(DataTable dataTable) {
        List<DTOBase> itemUnitList = (List<DTOBase>) getSessionAttribute(ItemUnitDTO.SESSION_ITEM_UNIT_LIST);
        dataTable.setRecordList(itemUnitList);
        dataTable.setCurrentPageRecordList();
    }
}