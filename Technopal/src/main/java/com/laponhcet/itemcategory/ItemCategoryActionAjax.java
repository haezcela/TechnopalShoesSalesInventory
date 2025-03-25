package com.laponhcet.itemcategory;

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
        List<DTOBase> itemCategoryList = (List<DTOBase>) getSessionAttribute(ItemCategoryDTO.SESSION_ITEM_CATEGORY_LIST);
        ItemCategoryDTO itemCategory = (ItemCategoryDTO) getSessionAttribute(ItemCategoryDTO.SESSION_ITEM_CATEGORY);

//        if (action.equalsIgnoreCase(DataTable.ACTION_ADD_SAVE) || action.equalsIgnoreCase(DataTable.ACTION_UPDATE_SAVE)) {
//            for (DTOBase obj : itemCategoryList) {
//                ItemCategoryDTO existingCategory = (ItemCategoryDTO) obj;
//                if (itemCategory.getName().equalsIgnoreCase(existingCategory.getName())) {
//                    actionResponse.constructMessage(ActionResponse.TYPE_EXIST, "Category name");
//                    return;
//                }
//            }
            if (StringUtil.isEmpty(itemCategory.getName())) {
                actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "Category Name");
            }
        }
//    }

    protected void executeLogic(JSONObject jsonObj, DataTable dataTable, String action) {
        if (action.equalsIgnoreCase(DataTable.ACTION_VIEW)) {
            ItemCategoryDTO itemCategory = (ItemCategoryDTO) dataTable.getSelectedRecord();
            try {
                jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataViewPage(sessionInfo, ItemCategoryUtil.getDataViewStr(sessionInfo, itemCategory),""));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } 
        else if (action.equalsIgnoreCase(DataTable.ACTION_ADD_VIEW)) {
            ItemCategoryDTO itemCategory = new ItemCategoryDTO();
            try {
                jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataEntryPage(sessionInfo, ItemCategoryUtil.getDataEntryStr(sessionInfo, itemCategory), ""));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            setSessionAttribute(ItemCategoryDTO.SESSION_ITEM_CATEGORY, itemCategory);
        } 
        else if (action.equalsIgnoreCase(DataTable.ACTION_ADD_SAVE)) {
            ItemCategoryDAO itemCategoryDAO = new ItemCategoryDAO();
            ItemCategoryDTO itemCategory = (ItemCategoryDTO) getSessionAttribute(ItemCategoryDTO.SESSION_ITEM_CATEGORY);
            itemCategory.setAddedBy(sessionInfo.getCurrentUser().getCode());

            itemCategoryDAO.executeAdd(itemCategory);
            actionResponse = (ActionResponse) itemCategoryDAO.getResult().get(ActionResponse.SESSION_ACTION_RESPONSE);
            if (StringUtil.isEmpty(actionResponse.getType())) {
                actionResponse.constructMessage(ActionResponse.TYPE_SUCCESS, "Added");
                setSessionAttribute(ItemCategoryDTO.SESSION_ITEM_CATEGORY_LIST, itemCategoryDAO.getItemCategoryList());
            }
        } 
        else if (action.equalsIgnoreCase(DataTable.ACTION_UPDATE_VIEW)) {
            ItemCategoryDTO itemCategoryUpdate = (ItemCategoryDTO) dataTable.getSelectedRecord();
            try {
                jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataEntryPage(sessionInfo, ItemCategoryUtil.getDataEntryStr(sessionInfo, itemCategoryUpdate), ""));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            setSessionAttribute(ItemCategoryDTO.SESSION_ITEM_CATEGORY, itemCategoryUpdate);
        } 
        else if (action.equalsIgnoreCase(DataTable.ACTION_UPDATE_SAVE)) {
            ItemCategoryDAO itemCategoryDAO = new ItemCategoryDAO();
            ItemCategoryDTO itemCategory = (ItemCategoryDTO) getSessionAttribute(ItemCategoryDTO.SESSION_ITEM_CATEGORY);
            itemCategory.setUpdatedBy(sessionInfo.getCurrentUser().getCode());

            itemCategoryDAO.executeUpdate(itemCategory);
            actionResponse = (ActionResponse) itemCategoryDAO.getResult().get(ActionResponse.SESSION_ACTION_RESPONSE);
            if (StringUtil.isEmpty(actionResponse.getType())) {
                setSessionAttribute(ItemCategoryDTO.SESSION_ITEM_CATEGORY_LIST, itemCategoryDAO.getItemCategoryList());
                actionResponse.constructMessage(ActionResponse.TYPE_SUCCESS, "Updated");
            }
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
            if (StringUtil.isEmpty(actionResponse.getType())) {
                setSessionAttribute(ItemCategoryDTO.SESSION_ITEM_CATEGORY_LIST, itemCategoryDAO.getItemCategoryList());
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
        System.out.println("Search Value: " + searchValue);

        List<DTOBase> itemCategoryList = (List<DTOBase>) getSessionAttribute(ItemCategoryDTO.SESSION_ITEM_CATEGORY_LIST);
        ItemCategoryUtil.searchByName(dataTable, searchValue, itemCategoryList);
    }
}
