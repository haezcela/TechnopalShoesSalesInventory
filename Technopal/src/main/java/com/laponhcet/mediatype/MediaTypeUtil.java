package com.laponhcet.mediatype;

import java.io.Serializable;
import java.util.List;

import com.mytechnopal.DataTable;
import com.mytechnopal.SessionInfo;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.base.WebControlBase;
import com.mytechnopal.webcontrol.DataTableWebControl;
import com.mytechnopal.webcontrol.TextBoxWebControl;
//import com.mytechnopal.dto.MediaTypeDTO;

public class MediaTypeUtil implements Serializable {
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
            MediaTypeDTO mediaType = (MediaTypeDTO) dataTable.getRecordListCurrentPage().get(row);
            strArr[row][0] = String.valueOf(mediaType.getId());
            strArr[row][1] = mediaType.getCode();
            strArr[row][2] = mediaType.getName();
            strArr[row][3] = mediaType.getAddedBy();
            strArr[row][4] = mediaType.getUpdatedBy();
            strArr[row][5] = dataTable.getRecordButtonStr(sessionInfo, mediaType.getCode());
        }
        return strArr;
    }

    public static String getDataEntryStr(SessionInfo sessionInfo, MediaTypeDTO mediaType) {
        StringBuffer strBuff = new StringBuffer();
        strBuff.append(new TextBoxWebControl().getTextBoxWebControl("form-group col-lg-4", true, "Code", "Code", "Code", mediaType.getCode(), 10, WebControlBase.DATA_TYPE_STRING, ""));
        strBuff.append(new TextBoxWebControl().getTextBoxWebControl("form-group col-lg-4", true, "Media Type Name", "Name", "Name", mediaType.getName(), 50, WebControlBase.DATA_TYPE_STRING, ""));
        return strBuff.toString();
    }

    public static String getDataViewStr(SessionInfo sessionInfo, MediaTypeDTO mediaType) {
        StringBuffer strBuff = new StringBuffer();
        strBuff.append("<div class='col-lg-12'>");
        strBuff.append("<p>ID: " + mediaType.getId() + "</p>");
        strBuff.append("<p>Code: " + mediaType.getCode() + "</p>");
        strBuff.append("<p>Media Type Name: " + mediaType.getName() + "</p>");
        strBuff.append("<p>Added By: " + mediaType.getAddedBy() + "</p>");
        strBuff.append("<p>Updated By: " + mediaType.getUpdatedBy() + "</p>");
        strBuff.append("</div>");
        return strBuff.toString();
    }

    public static void searchByName(DataTable dataTable, String searchValue, List<DTOBase> mediaTypeList) {
        dataTable.setRecordListInvisible();
        for (DTOBase dto : dataTable.getRecordList()) {
            MediaTypeDTO mediaType = (MediaTypeDTO) dto;
            if (mediaType.getName().toUpperCase().contains(searchValue.toUpperCase())) {
                mediaType.setVisible(true);
            }
        }
    }
}
