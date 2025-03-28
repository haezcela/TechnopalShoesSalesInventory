package com.laponhcet.itemunit;

import java.util.List;
import com.mytechnopal.DataTable;
import com.mytechnopal.base.ActionBase;
import com.mytechnopal.base.DTOBase;

public class ItemUnitAction extends ActionBase {
    private static final long serialVersionUID = 1L;

    protected void setSessionVars() {
        List<DTOBase> itemUnitList = new ItemUnitDAO().getItemUnitList();

        DataTable dataTable = new DataTable(ItemUnitDTO.SESSION_ITEM_UNIT_DATA_TABLE, itemUnitList, new String[] {ItemUnitDTO.SESSION_ITEM_UNIT}, new String[] {"Unit Name"});
        dataTable.setColumnNameArr(new String[] {"ID", "CODE", "UNIT NAME", "ADDED BY", "UPDATED BY", ""});
        dataTable.setColumnWidthArr(new String[] {"5", "10", "30", "20", "20", "5"});

        setSessionAttribute(ItemUnitDTO.SESSION_ITEM_UNIT_DATA_TABLE, dataTable);
        setSessionAttribute(ItemUnitDTO.SESSION_ITEM_UNIT_LIST, itemUnitList);
        
    }
}
