package com.laponhcet.itemrequest;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.laponhcet.item.ItemDTO;
import com.mytechnopal.ActionResponse;
import com.mytechnopal.DataTable;
import com.mytechnopal.base.ActionAjaxBase;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.link.LinkDTO;
import com.mytechnopal.util.PageUtil;
import com.mytechnopal.util.StringUtil;

public class ItemRequestActionAjax extends ActionAjaxBase {
    private static final long serialVersionUID = 1L;

    protected void setInput(String action) {
        ItemRequestDetailsDTO item = (ItemRequestDetailsDTO) getSessionAttribute(ItemRequestDetailsDTO.SESSION_ITEM_REQUEST);
        //ItemDTO item = (ItemDTO) getSessionAttribute(ItemDTO.SESSION_ITEM);
        item.setItemCode(getRequestString("txtItemCode"));
        item.setQuantity(getRequestDouble("txtQuantity"));
        //item.setOfficeCode(getRequestString("txtOfficeCode"));
      
    }

    protected void validateInput(String action) {
        if (action.equalsIgnoreCase(DataTable.ACTION_ADD_SAVE) || action.equalsIgnoreCase(DataTable.ACTION_UPDATE_SAVE)) {
//        	  
//
//        	
//           
//            if (StringUtil.isEmpty(getRequestString("txtNeedDate"))) {
//                actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "Need Date");
//            }
        }
    }

    protected void executeLogic(JSONObject jsonObj, DataTable dataTable, String action) {
        if (action.equalsIgnoreCase(DataTable.ACTION_VIEW)) {
            ItemRequestDetailsDTO itemSelected = (ItemRequestDetailsDTO) dataTable.getSelectedRecord();
            try {
                jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataViewPage(sessionInfo, ItemRequestUtil.getDataViewStr(sessionInfo, itemSelected)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (action.equalsIgnoreCase(DataTable.ACTION_ADD_VIEW)) {
        	  ItemRequestDetailsDTO item = new ItemRequestDetailsDTO();
            try {
                jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataEntryPage(sessionInfo, ItemRequestUtil.getDataEntryStr(sessionInfo, item)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            setSessionAttribute(  ItemRequestDetailsDTO.SESSION_ITEM_REQUEST, item);
        } else if (action.equalsIgnoreCase(DataTable.ACTION_UPDATE_VIEW)) {
        	ItemRequestDetailsDTO itemSelected = (ItemRequestDetailsDTO) dataTable.getSelectedRecord();
            try {
                jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataEntryPage(sessionInfo, ItemRequestUtil.getDataEntryStr(sessionInfo, itemSelected)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            setSessionAttribute(ItemRequestDetailsDTO.SESSION_ITEM_REQUEST, itemSelected);
        } else if (action.equalsIgnoreCase(DataTable.ACTION_DELETE_VIEW)) {
        	ItemRequestDetailsDTO itemSelected = (ItemRequestDetailsDTO) dataTable.getSelectedRecord();
            try {
                jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataViewPage(sessionInfo, ItemRequestUtil.getDataViewStr(sessionInfo, itemSelected)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            setSessionAttribute(ItemRequestDetailsDTO.SESSION_ITEM_REQUEST, itemSelected);
        } else if (action.equalsIgnoreCase(DataTable.ACTION_ADD_SAVE)) {
            ItemRequestDetailsDAO itemDAO = new ItemRequestDetailsDAO();
            ItemRequestDetailsDTO item = (ItemRequestDetailsDTO) getSessionAttribute(ItemRequestDetailsDTO.SESSION_ITEM_REQUEST);
            item.setAddedBy(sessionInfo.getCurrentUser().getCode());
            itemDAO.executeAdd(item);
            actionResponse = (ActionResponse) itemDAO.getResult().get(ActionResponse.SESSION_ACTION_RESPONSE);
            if (StringUtil.isEmpty(actionResponse.getType())) {
                actionResponse.constructMessage(ActionResponse.TYPE_SUCCESS, "Added");
                setSessionAttribute(ItemRequestDetailsDTO.SESSION_ITEM_REQUEST_LIST, new ItemRequestDetailsDAO().getItemList());
            }
        } else if (action.equalsIgnoreCase(DataTable.ACTION_UPDATE_SAVE)) {
            ItemRequestDetailsDAO itemDAO = new ItemRequestDetailsDAO();
            ItemRequestDetailsDTO item = (ItemRequestDetailsDTO) getSessionAttribute(ItemRequestDetailsDTO.SESSION_ITEM_REQUEST);
            item.setAddedBy(sessionInfo.getCurrentUser().getCode());
            itemDAO.executeUpdate(item);
            actionResponse = (ActionResponse) itemDAO.getResult().get(ActionResponse.SESSION_ACTION_RESPONSE);
            if (StringUtil.isEmpty(actionResponse.getType())) {
                setSessionAttribute( ItemRequestDetailsDTO.SESSION_ITEM_REQUEST_LIST, new ItemRequestDetailsDAO().getItemList());
                actionResponse.constructMessage(ActionResponse.TYPE_SUCCESS, "Updated");
            }
        } else if (action.equalsIgnoreCase(DataTable.ACTION_DELETE)) {
            ItemRequestDetailsDAO itemDAO = new  ItemRequestDetailsDAO();
            ItemRequestDetailsDTO item = ( ItemRequestDetailsDTO) getSessionAttribute( ItemRequestDetailsDTO.SESSION_ITEM_REQUEST);
            itemDAO.executeDelete(item);
            actionResponse = (ActionResponse) itemDAO.getResult().get(ActionResponse.SESSION_ACTION_RESPONSE);
            if (StringUtil.isEmpty(actionResponse.getType())) {
                setSessionAttribute( ItemRequestDetailsDTO.SESSION_ITEM_REQUEST_LIST, new ItemRequestDetailsDAO().getItemList());
                actionResponse.constructMessage(ActionResponse.TYPE_SUCCESS, "Deleted");
            }
        }
    }

    protected void dataTableAction(JSONObject jsonObj, DataTable dataTable) {
        initDataTable(dataTable);
        try {
            jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataTablePage(sessionInfo, ItemRequestUtil.getDataTableStr(sessionInfo, dataTable)));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected void initDataTable(DataTable dataTable) {
        List<DTOBase> itemList = (List<DTOBase>) getSessionAttribute(ItemRequestDetailsDTO.SESSION_ITEM_REQUEST_LIST);
        dataTable.setRecordList(itemList);
        dataTable.setCurrentPageRecordList();
    }
}
