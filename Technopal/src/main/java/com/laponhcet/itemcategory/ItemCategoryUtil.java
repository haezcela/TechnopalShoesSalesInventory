package com.laponhcet.itemcategory;

import java.io.Serializable;
import java.util.List;

import com.mytechnopal.DataTable;
import com.mytechnopal.SessionInfo;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.base.WebControlBase;
import com.mytechnopal.webcontrol.DataTableWebControl;
import com.mytechnopal.webcontrol.TextBoxWebControl;

public class ItemCategoryUtil implements Serializable {
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
            ItemCategoryDTO itemCategory = (ItemCategoryDTO) dataTable.getRecordListCurrentPage().get(row);

            strArr[row][0] = String.valueOf(itemCategory.getId());
            strArr[row][1] = itemCategory.getCode();
            strArr[row][2] = itemCategory.getName();
            strArr[row][3] = itemCategory.getAddedBy();
            strArr[row][4] = itemCategory.getUpdatedBy();
            strArr[row][5] = dataTable.getRecordButtonStr(sessionInfo, itemCategory.getCode());
        }
        return strArr;
    }

    public static String getDataEntryStr(SessionInfo sessionInfo, ItemCategoryDTO itemCategory) {
        StringBuffer strBuff = new StringBuffer();
        strBuff.append(new TextBoxWebControl().getTextBoxWebControl("form-group col-lg-4", true, "Code", "Code", "Code", itemCategory.getCode(), 10, WebControlBase.DATA_TYPE_STRING, ""));
        strBuff.append(new TextBoxWebControl().getTextBoxWebControl("form-group col-lg-4", true, "Category Name", "Name", "Name", itemCategory.getName(), 50, WebControlBase.DATA_TYPE_STRING, ""));
        return strBuff.toString();
    }

    public static String getDataViewStr(SessionInfo sessionInfo, ItemCategoryDTO itemCategory) {
        StringBuffer strBuff = new StringBuffer();
        strBuff.append("<div class='col-lg-12'>");
        strBuff.append("<p>ID: " + itemCategory.getId() + "</p>");
        strBuff.append("<p>Code: " + itemCategory.getCode() + "</p>");
        strBuff.append("<p>Name: " + itemCategory.getName() + "</p>");
        strBuff.append("<p>Added By: " + itemCategory.getAddedBy() + "</p>");
        strBuff.append("<p>Updated By: " + itemCategory.getUpdatedBy() + "</p>");
        strBuff.append("</div>");
        return strBuff.toString();
    }

    public static void searchByName(DataTable dataTable, String searchValue, List<DTOBase> itemCategoryList) {
        dataTable.setRecordListInvisible();
        for (DTOBase dto : dataTable.getRecordList()) {
            ItemCategoryDTO itemCategory = (ItemCategoryDTO) dto;
            if (itemCategory.getName().toUpperCase().contains(searchValue.toUpperCase())) {
                itemCategory.setVisible(true);
            }
        }
    }
}
