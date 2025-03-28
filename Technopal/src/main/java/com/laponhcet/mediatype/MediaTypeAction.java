package com.laponhcet.mediatype;

import java.util.List;
import com.mytechnopal.DataTable;
import com.mytechnopal.base.ActionBase;
import com.mytechnopal.base.DTOBase;
//import com.mytechnopal.dao.MediaTypeDAO;
//import com.mytechnopal.dto.MediaTypeDTO;

public class MediaTypeAction extends ActionBase {
    private static final long serialVersionUID = 1L;

    @Override
    protected void setSessionVars() {
        List<DTOBase> mediaTypeList = new MediaTypeDAO().getMediaTypeList();

        DataTable dataTable = new DataTable(MediaTypeDTO.SESSION_MEDIA_TYPE_DATA_TABLE,mediaTypeList,new String[] {MediaTypeDTO.SESSION_MEDIA_TYPE},new String[] {"Media Type Name"});
        dataTable.setColumnNameArr(new String[] {"ID", "CODE", "MEDIA TYPE NAME", "ADDED BY", "UPDATED BY", ""});
        dataTable.setColumnWidthArr(new String[] {"5", "10", "30", "20", "20", "5"});

        setSessionAttribute(MediaTypeDTO.SESSION_MEDIA_TYPE_DATA_TABLE, dataTable);
        setSessionAttribute(MediaTypeDTO.SESSION_MEDIA_TYPE_LIST, mediaTypeList);
        
    }
}
