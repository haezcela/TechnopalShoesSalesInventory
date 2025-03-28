package com.laponhcet.itemunit;

import java.io.Serializable;
import java.util.List;

import com.mytechnopal.DataTable;
import com.mytechnopal.SessionInfo;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.base.WebControlBase;
import com.mytechnopal.webcontrol.DataTableWebControl;
import com.mytechnopal.webcontrol.TextBoxWebControl;

public class ItemUnitUtil implements Serializable {
    private static final long serialVersionUID = 1L;

    public static String getDataTableStr(SessionInfo sessionInfo, DataTable dataTable) {
        DataTableWebControl dtwc = new DataTableWebControl(sessionInfo, dataTable);
        StringBuffer strBuff = new StringBuffer();
        if (dataTable.getRecordList().size() >= 1) {
            strBuff.append(dtwc.getDataTableHeader(sessionInfo, dataTable));
            dataTable.setDataTableRecordArr(getDataTableCurrentPageRecordArr(sessionInfo, dataTable));
            strBuff.append(dtwc.getDataTable(true, false));
        }
        return strBuff.toString();
    }

    private static String[][] getDataTableCurrentPageRecordArr(SessionInfo sessionInfo, DataTable dataTable) {
        String[][] strArr = new String[dataTable.getRecordListCurrentPage().size()][dataTable.getColumnNameArr().length];
        for (int row = 0; row < dataTable.getRecordListCurrentPage().size(); row++) {
            ItemUnitDTO itemUnit = (ItemUnitDTO) dataTable.getRecordListCurrentPage().get(row);

            strArr[row][0] = String.valueOf(itemUnit.getId());
            strArr[row][1] = itemUnit.getCode();
            strArr[row][2] = itemUnit.getName();
            strArr[row][3] = itemUnit.getAddedBy();
            strArr[row][4] = itemUnit.getUpdatedBy();
            strArr[row][5] = dataTable.getRecordButtonStr(sessionInfo, itemUnit.getCode());
        }
        return strArr;
    }

    public static String getDataEntryStr(SessionInfo sessionInfo, ItemUnitDTO itemUnit) {
        StringBuffer strBuff = new StringBuffer();
        strBuff.append(new TextBoxWebControl().getTextBoxWebControl("form-group col-lg-4", true, "Code", "Code", "Code", itemUnit.getCode(), 10, WebControlBase.DATA_TYPE_STRING, ""));
        strBuff.append(new TextBoxWebControl().getTextBoxWebControl("form-group col-lg-4", true, "Unit Name", "Name", "Name", itemUnit.getName(), 50, WebControlBase.DATA_TYPE_STRING, ""));
        return strBuff.toString();
    }

    public static String getDataViewStr(SessionInfo sessionInfo, ItemUnitDTO itemUnit) {
        StringBuffer strBuff = new StringBuffer();
        strBuff.append("<div class='col-lg-12'>");
        strBuff.append("<p>ID: " + itemUnit.getId() + "</p>");
        strBuff.append("<p>Code: " + itemUnit.getCode() + "</p>");
        strBuff.append("<p>Unit Name: " + itemUnit.getName() + "</p>");
        strBuff.append("<p>Added By: " + itemUnit.getAddedBy() + "</p>");
        strBuff.append("<p>Updated By: " + itemUnit.getUpdatedBy() + "</p>");
        strBuff.append("</div>");
        return strBuff.toString();
    }

    
    public static void searchByName(DataTable dataTable, String searchValue, List<DTOBase> itemUnitList) {
        dataTable.setRecordListInvisible();
        for (DTOBase dto : dataTable.getRecordList()) {
            ItemUnitDTO itemUnit = (ItemUnitDTO) dto;
            if (itemUnit.getName().toUpperCase().contains(searchValue.toUpperCase())) {
                itemUnit.setVisible(true);
            }
        }
    }
}
