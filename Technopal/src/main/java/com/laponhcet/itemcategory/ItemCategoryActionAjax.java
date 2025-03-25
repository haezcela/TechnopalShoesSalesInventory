package com.laponhcet.itemcategory;

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

public class ItemCategoryActionAjax extends ActionAjaxBase {
    private static final long serialVersionUID = 1L;

    protected void setInput(String action) {
        ItemCategoryDTO itemCategory = (ItemCategoryDTO) getSessionAttribute(ItemCategoryDTO.SESSION_ITEM_CATEGORY);
        itemCategory.setName(getRequestString("txtName"));
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
        	ItemCategoryDTO categorySelected = (ItemCategoryDTO) dataTable.getSelectedRecord();
            try {
                jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataViewPage(sessionInfo, ItemCategoryUtil.getDataViewStr(sessionInfo, categorySelected),""));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (action.equalsIgnoreCase(DataTable.ACTION_ADD_VIEW)) {
        	ItemCategoryDTO categorySelected = new ItemCategoryDTO();
            try {
                jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataEntryPage(sessionInfo, ItemCategoryUtil.getDataEntryStr(sessionInfo, categorySelected, null),""));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            setSessionAttribute(ItemCategoryDTO.SESSION_ITEM_CATEGORY, categorySelected);
        } else if (action.equalsIgnoreCase(DataTable.ACTION_DELETE_VIEW)) {
        	ItemCategoryDTO categorySelected = (ItemCategoryDTO) dataTable.getSelectedRecord();
            try {
                jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataViewPage(sessionInfo, ItemCategoryUtil.getDataViewStr(sessionInfo, categorySelected),""));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            setSessionAttribute(ItemCategoryDTO.SESSION_ITEM_CATEGORY, categorySelected);
        } else if (action.equalsIgnoreCase(DataTable.ACTION_ADD_SAVE)) {
        	ItemCategoryDAO itemDAO = new ItemCategoryDAO();
        	ItemCategoryDTO itemCategory = (ItemCategoryDTO) getSessionAttribute(ItemCategoryDTO.SESSION_ITEM_CATEGORY);
            itemCategory.setAddedBy(sessionInfo.getCurrentUser().getCode());
            itemDAO.executeAdd(itemCategory);
            actionResponse = (ActionResponse) itemDAO.getResult().get(ActionResponse.SESSION_ACTION_RESPONSE);
            
            if (StringUtil.isEmpty(actionResponse.getType())) {
                actionResponse.constructMessage(ActionResponse.TYPE_SUCCESS, "Added");
                setSessionAttribute(ItemCategoryDTO.SESSION_ITEM_CATEGORY_LIST, new ItemCategoryDAO().getItemCategoryList());
            }
        } else if (action.equalsIgnoreCase(DataTable.ACTION_DELETE)) {
        	ItemCategoryDAO itemDAO = new ItemCategoryDAO();
        	ItemCategoryDTO itemCategory = (ItemCategoryDTO) getSessionAttribute(ItemCategoryDTO.SESSION_ITEM_CATEGORY);
            itemDAO.executeDelete(itemCategory); 
            actionResponse = (ActionResponse) itemDAO.getResult().get(ActionResponse.SESSION_ACTION_RESPONSE);
            
            if (StringUtil.isEmpty(actionResponse.getType())) {
                setSessionAttribute(ItemCategoryDTO.SESSION_ITEM_CATEGORY_LIST, new ItemCategoryDAO().getItemCategoryList());
                actionResponse.constructMessage(ActionResponse.TYPE_SUCCESS, "Deleted");
            }
        }else if(action.equalsIgnoreCase(DataTable.ACTION_UPDATE_SAVE)) {
        	ItemCategoryDTO category = (ItemCategoryDTO) getSessionAttribute(ItemCategoryDTO.SESSION_ITEM_CATEGORY);
            category.setUpdatedBy(sessionInfo.getCurrentUser().getCode());
            category.setUpdatedTimestamp(DateTimeUtil.getCurrentTimestamp());

            ItemCategoryDAO itemDAO = new ItemCategoryDAO();
            itemDAO.executeUpdate(category);
            actionResponse = (ActionResponse) itemDAO.getResult().get(ActionResponse.SESSION_ACTION_RESPONSE);
            if(StringUtil.isEmpty(actionResponse.getType())) {
                actionResponse.constructMessage(ActionResponse.TYPE_SUCCESS, "Updated");
                setSessionAttribute(ItemCategoryDTO.SESSION_ITEM_CATEGORY, new ItemCategoryDTO());
                setSessionAttribute(ItemCategoryDTO.SESSION_ITEM_CATEGORY_LIST, itemDAO.getItemCategoryList());
            }
        }else if (action.equalsIgnoreCase(DataTable.ACTION_UPDATE_VIEW)) {
        	ItemCategoryDTO itemToUpdate = (ItemCategoryDTO) dataTable.getSelectedRecord();
        	ItemCategoryDTO item_category = itemToUpdate.getItemCategory();
            try {
                jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataEntryPage(sessionInfo, ItemCategoryUtil.getDataEntryStr(sessionInfo, item_category, null),""));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            setSessionAttribute(ItemCategoryDTO.SESSION_ITEM_CATEGORY, item_category);   
        }
    }
    
    protected void dataTableAction(JSONObject jsonObj, DataTable dataTable) {
        initDataTable(dataTable);
        try {
            jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataTablePage(sessionInfo, ItemCategoryUtil.getDataTableStr(sessionInfo, dataTable)));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    
    
    
    protected void initDataTable(DataTable dataTable) {
        List<DTOBase> itemCategoryList = (List<DTOBase>) getSessionAttribute(ItemCategoryDTO.SESSION_ITEM_CATEGORY_LIST);
        dataTable.setRecordList(itemCategoryList);
        dataTable.setCurrentPageRecordList();
    }
}