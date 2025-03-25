 package com.laponhcet.itemcategory;

import java.io.Serializable;
import java.util.List;

import com.laponhcet.enrollment.EnrollmentDTO;
import com.laponhcet.itemunit.ItemUnitDTO;
import com.laponhcet.student.StudentDTO;
import com.laponhcet.student.StudentUtil;
import com.mytechnopal.DataTable;
import com.mytechnopal.SessionInfo;
import com.mytechnopal.UploadedFile;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.base.WebControlBase;
import com.mytechnopal.dto.UserDTO;
import com.mytechnopal.util.DTOUtil;
import com.mytechnopal.util.DateTimeUtil;
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
            strArr[row][0] = itemCategory.getCode();
            strArr[row][1] = itemCategory.getName();
            strArr[row][2] = dataTable.getRecordButtonStr(sessionInfo, itemCategory.getCode());
        }
        return strArr;
    }

    public static String getDataEntryStr(SessionInfo sessionInfo, ItemCategoryDTO unit, UploadedFile uploadedFile) {
        StringBuffer strBuff = new StringBuffer();
        strBuff.append(new TextBoxWebControl().getTextBoxWebControl( "form-group col-lg-8", false, "Add New Category", "Add New Category", "Name", unit.getName(), 45, WebControlBase.DATA_TYPE_STRING, "" ));
        return strBuff.toString();
    }

    public static String getDataViewStr(SessionInfo sessionInfo, ItemCategoryDTO unit) {
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

  



//	public static void setItemCategory (ItemCategoryDTO itemcategory) {
//		// TODO Auto-generated method stub
//		itemcategory.setName(itemcategory.getName());
//	}
    
    
	
}