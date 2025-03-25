package com.laponhcet.itemunit;

import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

import com.mytechnopal.ActionResponse;
import com.mytechnopal.DataTable;
import com.mytechnopal.base.ActionAjaxBase;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.link.LinkDTO;
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
        List<DTOBase> itemUnitList = (List<DTOBase>) getSessionAttribute(ItemUnitDTO.SESSION_ITEM_UNIT_LIST);
        ItemUnitDTO itemUnit = (ItemUnitDTO) getSessionAttribute(ItemUnitDTO.SESSION_ITEM_UNIT);

        if (StringUtil.isEmpty(itemUnit.getName())) {
            actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "Unit Name");
        }
    }

    protected void executeLogic(JSONObject jsonObj, DataTable dataTable, String action) {
        if (action.equalsIgnoreCase(DataTable.ACTION_VIEW)) {
            ItemUnitDTO itemUnit = (ItemUnitDTO) dataTable.getSelectedRecord();
            try {
                jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataViewPage(sessionInfo, ItemUnitUtil.getDataViewStr(sessionInfo, itemUnit),""));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } 
        else if (action.equalsIgnoreCase(DataTable.ACTION_ADD_VIEW)) {
            ItemUnitDTO itemUnit = new ItemUnitDTO();
            try {
                jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataEntryPage(sessionInfo, ItemUnitUtil.getDataEntryStr(sessionInfo, itemUnit),""));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            setSessionAttribute(ItemUnitDTO.SESSION_ITEM_UNIT, itemUnit);
        } 
        else if (action.equalsIgnoreCase(DataTable.ACTION_ADD_SAVE)) {
            ItemUnitDAO itemUnitDAO = new ItemUnitDAO();
            ItemUnitDTO itemUnit = (ItemUnitDTO) getSessionAttribute(ItemUnitDTO.SESSION_ITEM_UNIT);
            itemUnit.setAddedBy(sessionInfo.getCurrentUser().getCode());

            itemUnitDAO.executeAdd(itemUnit);
            actionResponse = (ActionResponse) itemUnitDAO.getResult().get(ActionResponse.SESSION_ACTION_RESPONSE);
            if (StringUtil.isEmpty(actionResponse.getType())) {
                actionResponse.constructMessage(ActionResponse.TYPE_SUCCESS, "Added");
                setSessionAttribute(ItemUnitDTO.SESSION_ITEM_UNIT_LIST, itemUnitDAO.getItemUnitList());
            }
        } 
        else if (action.equalsIgnoreCase(DataTable.ACTION_UPDATE_VIEW)) {
            ItemUnitDTO itemUnitUpdate = (ItemUnitDTO) dataTable.getSelectedRecord();
            try {
                jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataEntryPage(sessionInfo, ItemUnitUtil.getDataEntryStr(sessionInfo, itemUnitUpdate),""));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            setSessionAttribute(ItemUnitDTO.SESSION_ITEM_UNIT, itemUnitUpdate);
        } 
        else if (action.equalsIgnoreCase(DataTable.ACTION_UPDATE_SAVE)) {
            ItemUnitDAO itemUnitDAO = new ItemUnitDAO();
            ItemUnitDTO itemUnit = (ItemUnitDTO) getSessionAttribute(ItemUnitDTO.SESSION_ITEM_UNIT);
            itemUnit.setUpdatedBy(sessionInfo.getCurrentUser().getCode());

            itemUnitDAO.executeUpdate(itemUnit);
            actionResponse = (ActionResponse) itemUnitDAO.getResult().get(ActionResponse.SESSION_ACTION_RESPONSE);
            if (StringUtil.isEmpty(actionResponse.getType())) {
                setSessionAttribute(ItemUnitDTO.SESSION_ITEM_UNIT_LIST, itemUnitDAO.getItemUnitList());
                actionResponse.constructMessage(ActionResponse.TYPE_SUCCESS, "Updated");
            }
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
            if (StringUtil.isEmpty(actionResponse.getType())) {
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
