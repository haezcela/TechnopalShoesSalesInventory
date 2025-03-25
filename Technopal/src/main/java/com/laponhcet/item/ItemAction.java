package com.laponhcet.item;

import java.util.List;

import com.laponhcet.itemcategory.ItemCategoryDAO;
import com.laponhcet.itemcategory.ItemCategoryDTO;
import com.laponhcet.itemunit.ItemUnitDAO;
import com.laponhcet.itemunit.ItemUnitDTO;
import com.mytechnopal.DataTable;
import com.mytechnopal.base.ActionBase;
import com.mytechnopal.base.DTOBase;

public class ItemAction extends ActionBase {

    private static final long serialVersionUID = 1L;

    protected void setSessionVars() {
        List<DTOBase> itemList = new ItemDAO().getItemList();
        
        DataTable dataTable = new DataTable(ItemDTO.SESSION_ITEM_DATA_TABLE, itemList, new String[] {ItemDTO.ACTION_SEARCH_BY_NAME}, new String[] {"Name"});
        dataTable.setColumnNameArr(new String[] {"Code", "Category", "Name", "Description", "Unit", "Unit Price", "Quantity", "Reorderpoint", "Actions"});
        dataTable.setColumnWidthArr(new String[] {"10","20", "10", "10", "10", "10" , "15","10", "15"}); 

        setSessionAttribute(ItemDTO.SESSION_ITEM_DATA_TABLE, dataTable);
        setSessionAttribute(ItemDTO.SESSION_ITEM_LIST, itemList);
        setSessionAttribute(ItemCategoryDTO.SESSION_ITEM_CATEGORY_LIST, new ItemCategoryDAO().getItemCategoryList());
        setSessionAttribute(ItemUnitDTO.SESSION_ITEM_UNIT_LIST, new ItemUnitDAO().getItemUnitList());
        
        
        List<DTOBase> itemCategoryList = new ItemCategoryDAO().getItemCategoryList();
        for (DTOBase obj:itemCategoryList) {
        	ItemCategoryDTO itemCategory = (ItemCategoryDTO) obj;
        		itemCategory.display();
        }
        
        setSessionAttribute(ItemUnitDTO.SESSION_ITEM_UNIT_LIST, new ItemUnitDAO().getItemUnitList());
        List<DTOBase> itemUnitList = new ItemUnitDAO().getItemUnitList();
        for (DTOBase obj:itemUnitList) {
        	ItemUnitDTO itemUnit = (ItemUnitDTO) obj;
        		itemUnit.display();
        }
    }
}