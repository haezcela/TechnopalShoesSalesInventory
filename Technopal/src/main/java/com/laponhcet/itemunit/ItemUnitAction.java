package com.laponhcet.itemunit;

import java.util.List;
import com.mytechnopal.DataTable;
import com.mytechnopal.base.ActionBase;
import com.mytechnopal.base.DTOBase;

public class ItemUnitAction extends ActionBase {

    private static final long serialVersionUID = 1L;

    protected void setSessionVars() {
        List<DTOBase> UnitList = new ItemUnitDAO().getItemUnitList();

        DataTable dataTable = new DataTable( ItemUnitDTO.SESSION_ITEM_UNIT_DATA_TABLE,UnitList, new String[] {ItemUnitDTO.ACTION_SEARCH_BY_CODE }, new String[] { "Unit Name" } );
        
        dataTable.setColumnNameArr(new String[] { "Code","Unit Name", "" });
        dataTable.setColumnWidthArr(new String[] { "15", "60", "25" });

        setSessionAttribute(ItemUnitDTO.SESSION_ITEM_UNIT_DATA_TABLE, dataTable);
        setSessionAttribute(ItemUnitDTO.SESSION_ITEM_UNIT_LIST, UnitList);
    }
}