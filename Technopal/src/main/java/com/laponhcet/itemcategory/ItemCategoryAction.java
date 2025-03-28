package com.laponhcet.itemcategory;

import java.util.List;
import com.mytechnopal.DataTable;
import com.mytechnopal.base.ActionBase;
import com.mytechnopal.base.DTOBase;

public class ItemCategoryAction extends ActionBase {
    private static final long serialVersionUID = 1L;

    protected void setSessionVars() {
        List<DTOBase> itemAICategoryList = new ItemCategoryDAO().getItemCategoryList();

        DataTable dataTable = new DataTable(ItemCategoryDTO.SESSION_ITEM_CATEGORY_DATA_TABLE, itemAICategoryList, new String[] {ItemCategoryDTO.SESSION_ITEM_CATEGORY}, new String[] {"CATEGORY Name"});

        dataTable.setColumnNameArr(new String[] {"ID", "CODE", "CATEGORY NAME", "ADDED BY", "UPDATED BY", ""});
        dataTable.setColumnWidthArr(new String[] {"5", "10", "30", "20", "20", "5"});

        setSessionAttribute(ItemCategoryDTO.SESSION_ITEM_CATEGORY_DATA_TABLE, dataTable);
        setSessionAttribute(ItemCategoryDTO.SESSION_ITEM_CATEGORY_LIST, itemAICategoryList);
    }
}
