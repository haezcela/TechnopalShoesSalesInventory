package com.laponhcet.itemunit;

import java.io.Serializable;



import com.laponhcet.item.ItemDTO;
import com.laponhcet.itemcategory.ItemCategoryDTO;
import com.mytechnopal.DataTable;
import com.mytechnopal.SessionInfo;
import com.mytechnopal.UploadedFile;
import com.mytechnopal.base.WebControlBase;
import com.mytechnopal.util.DateTimeUtil;
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
            strArr[row][0] = itemUnit.getCode();
            strArr[row][1] = itemUnit.getName();
            strArr[row][2] = dataTable.getRecordButtonStr(sessionInfo, itemUnit.getCode());
        }
        return strArr;
    }

    public static String getDataEntryStr(SessionInfo sessionInfo, ItemUnitDTO unit, UploadedFile uploadedFile) {
        StringBuffer strBuff = new StringBuffer();
        strBuff.append(new TextBoxWebControl().getTextBoxWebControl( "form-group col-lg-8", false, "Add New Unit", "Add New Unit", "Name", unit.getName(), 45, WebControlBase.DATA_TYPE_STRING, "" ));
        return strBuff.toString();
    }

    public static String getDataViewStr(SessionInfo sessionInfo, ItemUnitDTO unit) {
        StringBuffer strBuff = new StringBuffer();
        strBuff.append("<div class='col-lg-12'>");
        strBuff.append("<p>Code: " + unit.getCode() + "</p>");
        strBuff.append("<p>Name: " + unit.getName() + "</p>");
        strBuff.append("<p>Added By: " + unit.getAddedBy() + "</p>");
        strBuff.append("<p>Added Timestamp: " + DateTimeUtil.getDateTimeToStr(unit.getAddedTimestamp(), "MMM dd, yyyy hh:mm a") + "</p>");
        strBuff.append("<p>Updated By: " + unit.getUpdatedBy() + "</p>");
        strBuff.append("<p>Updated Timestamp: " + DateTimeUtil.getDateTimeToStr(unit.getUpdatedTimestamp(), "MMM dd, yyyy hh:mm a") + "</p>");
        strBuff.append("</div>");
        return strBuff.toString();
    }

    public static void setItemUnit (ItemUnitDTO itemunit) {
		// TODO Auto-generated method stub
		itemunit.setName(itemunit.getName());
	}
}