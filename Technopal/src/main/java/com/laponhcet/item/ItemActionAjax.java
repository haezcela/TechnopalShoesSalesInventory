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
    
    
  //METHODS TO DIPSPLAY = LIST
    protected void dataTableAction(JSONObject jsonObj, DataTable dataTable) {
        initDataTable(dataTable);
        try {
            jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataTablePage(sessionInfo, ItemUtil.getDataTableStr(sessionInfo, dataTable)));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    protected void setInput(String action) {
        ItemDTO item = (ItemDTO) getSessionAttribute(ItemDTO.SESSION_ITEM);
        
        
        int itemCategoryId= getRequestInt("cboItemCategory");
                	System.out.println("Item Category ID: " + itemCategoryId);
        List<DTOBase> itemCategoryList= (List<DTOBase>) getSessionAttribute(ItemCategoryDTO.SESSION_ITEM_CATEGORY_LIST);
        ItemCategoryDTO itemCategory=(ItemCategoryDTO) DTOUtil.getObjById(itemCategoryList, itemCategoryId);
    	System.out.println("itemCategoryId is null  :  " + itemCategory==null  );
    	item.setItemCategory(itemCategory);
        
        item.setName(getRequestString("txtName"));
        item.setDescription(getRequestString("txtDescription"));

        
        int itemUnitId= getRequestInt("cboItemUnit");
    	System.out.println("Item Category ID: " + itemUnitId);
    	List<DTOBase> itemUnitList= (List<DTOBase>) getSessionAttribute(ItemUnitDTO.SESSION_ITEM_UNIT_LIST);
    	ItemUnitDTO itemUnit=(ItemUnitDTO) DTOUtil.getObjById(itemUnitList, itemUnitId);
    	System.out.println("itemUnitId is null  :  " + itemUnit==null  );
    	item.setItemUnit(itemUnit);

        item.setUnitPrice(getRequestDouble("txtUnitPrice"));
        item.setQuantity(getRequestDouble("txtQuantity"));
        item.setReorderpoint(getRequestDouble("txtReorderpoint"));
        
        String unit = getRequestString("txtUnitPrice");
    	System.out.println("UnitPrice SET : " + unit);
        
        
//        String unitPrice = getRequestString("txtUnitPrice");
//    	System.out.println("UnitPrice: " + unitPrice);
    }

    protected void validateInput(String action) {
    	ItemDTO item = (ItemDTO) getSessionAttribute(ItemDTO.SESSION_ITEM);
        if (action.equalsIgnoreCase(DataTable.ACTION_ADD_SAVE) || action.equalsIgnoreCase(DataTable.ACTION_UPDATE_SAVE)) {
        	        	
        	if(StringUtil.isEmpty(getRequestString("cboItemCategory"))) {
				actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "Category");
			}
   
        	if (StringUtil.isEmpty(getRequestString("txtName"))) {
                actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "Name");
            }
            
        	if (StringUtil.isEmpty(getRequestString("txtDescription"))) {
                actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "Description");
            }
          String unit = getRequestString("txtUnitPrice");
        	System.out.println("UnitPrice VAlidate : " + unit);
        	if(StringUtil.isEmpty(getRequestString("cboItemUnit"))) {
				actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "Unit");
			}
        	
        	if (StringUtil.isEmpty(Double.toString(getRequestDouble("txtUnitPrice")))) {
                actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "UnitPrice");
            }

        	if (StringUtil.isEmpty(Double.toString(getRequestDouble("txtQuantity")))) {
                actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "Quantity");
            }
        	if (StringUtil.isEmpty(Double.toString(getRequestDouble("txtReorderpoint")))) {
                actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "ReorderPoint");
            }
        }
    }

    protected void executeLogic(JSONObject jsonObj, DataTable dataTable, String action) {

        if (action.equalsIgnoreCase(DataTable.ACTION_VIEW)) {
        	
            ItemDTO item = (ItemDTO) dataTable.getSelectedRecord();
            try {
    	        jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataViewPage(sessionInfo, ItemUtil.getDataViewStr(sessionInfo, item), "")); //Title and Buttons
    	    } catch (JSONException e) {
    	        e.printStackTrace();
    	    }
        } else if (action.equalsIgnoreCase(DataTable.ACTION_ADD_VIEW)) {
        	ItemDTO item = new ItemDTO();
            List<DTOBase> itemCategoryList= (List<DTOBase>) getSessionAttribute(ItemCategoryDTO.SESSION_ITEM_CATEGORY_LIST);
            
            System.out.println("ItemCategoryList size "+ itemCategoryList.size());
            
            for (DTOBase obj:itemCategoryList) {
          		ItemCategoryDTO itemCategory= (ItemCategoryDTO) obj;
          		System.out.println("itemCategoryId" + itemCategory.getId());
          		System.out.println("itemCategoryCode" + itemCategory.getCode());
          		System.out.println("itemCategoryName" + itemCategory.getName());
          		System.out.println("display" + itemCategory.getDisplayStr());
          	 }
            
            List<DTOBase> itemUnitList= (List<DTOBase>) getSessionAttribute(ItemUnitDTO.SESSION_ITEM_UNIT_LIST);
            
            System.out.println("itemUnitList size "+ itemUnitList.size());
            
            for (DTOBase obj:itemUnitList) {
            	ItemUnitDTO itemUnit= (ItemUnitDTO) obj;
          		System.out.println("itemCategoryId" + itemUnit.getId());
          		System.out.println("itemCategoryCode" + itemUnit.getCode());
          		System.out.println("itemCategoryName" + itemUnit.getName());
          		System.out.println("display" + itemUnit.getDisplayStr());
          	 }
            try {
                jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataEntryPage(sessionInfo, ItemUtil.getDataEntryStr(sessionInfo, item, itemCategoryList, itemUnitList), ""));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            setSessionAttribute(ItemDTO.SESSION_ITEM, item);
        
        } else if (action.equalsIgnoreCase(DataTable.ACTION_UPDATE_VIEW)) {
            ItemDTO itemSelected = (ItemDTO) dataTable.getSelectedRecord();
            List<DTOBase> itemCategoryList= (List<DTOBase>) getSessionAttribute(ItemCategoryDTO.SESSION_ITEM_CATEGORY_LIST);
            List<DTOBase> itemUnitList= (List<DTOBase>) getSessionAttribute(ItemUnitDTO.SESSION_ITEM_UNIT_LIST);
            try {
            	jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataEntryPage(sessionInfo, ItemUtil.getDataEntryStr(sessionInfo, itemSelected, itemCategoryList, itemUnitList), ""));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            setSessionAttribute(ItemDTO.SESSION_ITEM, itemSelected);
        } else if (action.equalsIgnoreCase(DataTable.ACTION_DELETE_VIEW)) {
            ItemDTO itemSelected = (ItemDTO) dataTable.getSelectedRecord();
            try {
                jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataViewPage(sessionInfo, ItemUtil.getDataViewStr(sessionInfo, itemSelected), ""));
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
		dataTable.setRecordList(itemList);
		dataTable.setCurrentPageRecordList();
	}
    }