package com.laponhcet.itemcategory;

import java.util.List;
import com.mytechnopal.DataTable;
import com.mytechnopal.base.ActionBase;
import com.mytechnopal.base.DTOBase;

public class ItemCategoryAction extends ActionBase {

    private static final long serialVersionUID = 1L;

    protected void setSessionVars() {
        List<DTOBase> ItemCategoryList = new ItemCategoryDAO().getItemCategoryList();

        DataTable dataTable = new DataTable( ItemCategoryDTO.SESSION_ITEM_CATEGORY_DATA_TABLE,ItemCategoryList, new String[] {ItemCategoryDTO.ACTION_SEARCH_BY_CODE }, new String[] { "CODE" } );
        
        dataTable.setColumnNameArr(new String[] { "CODE","Category Name", "" });
        dataTable.setColumnWidthArr(new String[] { "15", "60", "25" });

        setSessionAttribute(ItemCategoryDTO.SESSION_ITEM_CATEGORY_DATA_TABLE, dataTable);
        setSessionAttribute(ItemCategoryDTO.SESSION_ITEM_CATEGORY_LIST, ItemCategoryList);
    }
}