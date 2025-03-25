package com.laponhcet.item;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;


import com.laponhcet.itemcategory.ItemCategoryDTO;
import com.laponhcet.itemcategory.ItemCategoryUtil;
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

public class ItemActionAjax extends ActionAjaxBase {
    private static final long serialVersionUID = 1L;

    protected void setInput(String action) {
        ItemDTO item = (ItemDTO) getSessionAttribute(ItemDTO.SESSION_ITEM);
        item.setName(getRequestString("txtName"));
        item.setDescription(getRequestString("txtDescription"));
        item.setQuantity(getRequestDouble("txtQuantity"));
        item.setReorderpoint(getRequestDouble("txtReorderpoint"));
        
        //System.out.println("Search Name: " + );
        int itemCategoryId = getRequestInt("cboItemCategory");
        if(getRequestInt("cboCategory") > 0) {
        	List<DTOBase> itemCategoryList = (List<DTOBase>) getSessionAttribute(ItemCategoryDTO.SESSION_ITEM_CATEGORY_LIST);
        	ItemCategoryDTO itemCategory = (ItemCategoryDTO) DTOUtil.getObjById(itemCategoryList, itemCategoryId);
        	item.setItemCategory(itemCategory);
		}
        
        int itemUnitId = getRequestInt("cbiItemUnit");
        if(getRequestInt("cboUnit") > 0) {
        	List<DTOBase> itemUnitList = (List<DTOBase>) getSessionAttribute(ItemUnitDTO.SESSION_ITEM_UNIT_LIST);
        	ItemCategoryDTO itemUnit = (ItemCategoryDTO) DTOUtil.getObjById(itemUnitList, itemUnitId);
        	item.setItemCategory(itemUnit);
		}
    }

    protected void validateInput(String action) {
    	ItemDTO item = (ItemDTO) getSessionAttribute(ItemDTO.SESSION_ITEM);
        if (action.equalsIgnoreCase(DataTable.ACTION_ADD_SAVE) || action.equalsIgnoreCase(DataTable.ACTION_UPDATE_SAVE)) {
        	        	
        	if(StringUtil.isEmpty(item.getItemCategory().getName())) {
				actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "Category");
			}
   
        	if (StringUtil.isEmpty(getRequestString("txtName"))) {
                actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "Name");
            }
            
        	if (StringUtil.isEmpty(getRequestString("txtDescription"))) {
                actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "Description");
            }
            
        	else if(StringUtil.isEmpty(item.getItemUnit().getName())) {
				actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "Unit");
			}
        	if (StringUtil.isEmpty(getRequestString("txtUnitPrice"))) {
                actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "Unit Price");
            }

        	if (StringUtil.isEmpty(getRequestString("txtQuantity"))) {
                actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "Quantity");
            }
        	if (StringUtil.isEmpty(getRequestString("txtReorderpoint"))) {
                actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "Reorder Point");
            }
        }
    }

    protected void executeLogic(JSONObject jsonObj, DataTable dataTable, String action) {
    	List<DTOBase> itemCategoryList = (List<DTOBase>) getSessionAttribute(ItemCategoryDTO.SESSION_ITEM_CATEGORY_LIST);
    	List<DTOBase> itemUnitList = (List<DTOBase>) getSessionAttribute(ItemUnitDTO.SESSION_ITEM_UNIT_LIST);
        if (action.equalsIgnoreCase(DataTable.ACTION_VIEW)) {
            ItemDTO itemSelected = (ItemDTO) dataTable.getSelectedRecord();
            try {
                jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataViewPage(sessionInfo, ItemUtil.getDataViewStr(sessionInfo, itemSelected, itemCategoryList, itemUnitList), action));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (action.equalsIgnoreCase(DataTable.ACTION_ADD_VIEW)) {
            ItemDTO item = new ItemDTO();
            try {
                jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataEntryPage(sessionInfo, ItemUtil.getDataEntryStr(sessionInfo, item, itemCategoryList, itemUnitList), action));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            setSessionAttribute(ItemDTO.SESSION_ITEM, item);
        } else if (action.equalsIgnoreCase(DataTable.ACTION_UPDATE_VIEW)) {
            ItemDTO itemSelected = (ItemDTO) dataTable.getSelectedRecord();
            try {
                jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataEntryPage(sessionInfo, ItemUtil.getDataEntryStr(sessionInfo, itemSelected, itemCategoryList,itemUnitList), action));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            setSessionAttribute(ItemDTO.SESSION_ITEM, itemSelected);
        } else if (action.equalsIgnoreCase(DataTable.ACTION_DELETE_VIEW)) {
            ItemDTO itemSelected = (ItemDTO) dataTable.getSelectedRecord();
            try {
                jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataViewPage(sessionInfo, ItemUtil.getDataViewStr(sessionInfo, itemSelected, itemCategoryList,itemUnitList), action));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            setSessionAttribute(ItemDTO.SESSION_ITEM, itemSelected);
        } else if (action.equalsIgnoreCase(DataTable.ACTION_ADD_SAVE)) {
            ItemDAO itemDAO = new ItemDAO();
            ItemDTO item = (ItemDTO) getSessionAttribute(ItemDTO.SESSION_ITEM);
            item.setAddedBy(sessionInfo.getCurrentUser().getCode());
            itemDAO.executeAdd(item);
            actionResponse = (ActionResponse) itemDAO.getResult().get(ActionResponse.SESSION_ACTION_RESPONSE);
            if (StringUtil.isEmpty(actionResponse.getType())) {
                actionResponse.constructMessage(ActionResponse.TYPE_SUCCESS, "Added");
                setSessionAttribute(ItemDTO.SESSION_ITEM_LIST, new ItemDAO().getItemList());
            }
        } else if (action.equalsIgnoreCase(DataTable.ACTION_UPDATE_SAVE)) {
            ItemDAO itemDAO = new ItemDAO();
            ItemDTO item = (ItemDTO) getSessionAttribute(ItemDTO.SESSION_ITEM);
            item.setAddedBy(sessionInfo.getCurrentUser().getCode());
            itemDAO.executeUpdate(item);
            actionResponse = (ActionResponse) itemDAO.getResult().get(ActionResponse.SESSION_ACTION_RESPONSE);
            if (StringUtil.isEmpty(actionResponse.getType())) {
                setSessionAttribute(ItemDTO.SESSION_ITEM_LIST, new ItemDAO().getItemList());
                actionResponse.constructMessage(ActionResponse.TYPE_SUCCESS, "Updated");
            }
        } else if (action.equalsIgnoreCase(DataTable.ACTION_DELETE)) {
            ItemDAO itemDAO = new ItemDAO();
            ItemDTO item = (ItemDTO) getSessionAttribute(ItemDTO.SESSION_ITEM);
            itemDAO.executeDelete(item);
            actionResponse = (ActionResponse) itemDAO.getResult().get(ActionResponse.SESSION_ACTION_RESPONSE);
            if (StringUtil.isEmpty(actionResponse.getType())) {
                setSessionAttribute(ItemDTO.SESSION_ITEM_LIST, new ItemDAO().getItemList());
                actionResponse.constructMessage(ActionResponse.TYPE_SUCCESS, "Deleted");
            }
        }
    }

    
    
    protected void dataTableAction(JSONObject jsonObj, DataTable dataTable) {
        initDataTable(dataTable);
        try {
            jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataTablePage(sessionInfo, ItemUtil.getDataTableStr(sessionInfo, dataTable)));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    
    protected void search(DataTable dataTable) {
	    String searchValue = dataTable.getSearchValue();
	    String searchCriteria = ItemDTO.ACTION_SEARCH_BY_NAME;
	    System.out.println("Search Criteria: " + searchCriteria);
	    System.out.println("Search Value: " + searchValue);
	    
	    if (searchCriteria.equalsIgnoreCase(ItemDTO.ACTION_SEARCH_BY_NAME)) {
	        List<DTOBase> itemList = (List<DTOBase>) getSessionAttribute(ItemDTO.SESSION_ITEM_LIST);
	        ItemUtil.searchByItemName(dataTable, searchValue, itemList);
	    } else if (searchCriteria.equalsIgnoreCase(ItemDTO.ACTION_SEARCH_BY_NAME)) {
	    }
    }                                                                               
    
    protected void initDataTable(DataTable dataTable) {
       List<DTOBase> itemList = (List<DTOBase>) getSessionAttribute(ItemDTO.SESSION_ITEM_LIST);
       List<DTOBase> itemCategoryList = (List<DTOBase>) getSessionAttribute(ItemCategoryDTO.SESSION_ITEM_CATEGORY_LIST);
       List<DTOBase> itemUnitList = (List<DTOBase>) getSessionAttribute(ItemUnitDTO.SESSION_ITEM_UNIT_LIST);
        
        dataTable.setRecordList(itemList);
        dataTable.setCurrentPageRecordList();
       
			for(int row=0; row < dataTable.getRecordListCurrentPage().size(); row++) {			
				
			ItemDTO item = (ItemDTO) dataTable.getRecordListCurrentPage().get(row);
					item.setItemCategory((ItemCategoryDTO) DTOUtil.getObjByCode(itemCategoryList, item.getItemCategory().getCode()));
					item.setItemUnit((ItemUnitDTO) DTOUtil.getObjByCode(itemUnitList, item.getItemUnit().getCode()));
		}
         }
    }