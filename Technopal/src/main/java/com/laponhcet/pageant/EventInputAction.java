package com.laponhcet.pageant;

import java.util.List;

//import com.laponhcet.itemcategory.ItemCategoryDAO;
//import com.laponhcet.itemcategory.ItemCategoryDTO;
import com.mytechnopal.DataTable;
import com.mytechnopal.base.ActionBase;
import com.mytechnopal.base.DTOBase;

public class EventInputAction extends ActionBase {

    private static final long serialVersionUID = 1L;

    protected void setSessionVars() {
        List<DTOBase> eventList = new EventDAO().getEventList();
        
        DataTable dataTable = new DataTable(EventDTO.SESSION_EVENT_DATA_TABLE, eventList, new String[] {""}, new String[] {"Name"});
        dataTable.setColumnNameArr(new String[] {"Code", "EventTypeCode", "Name" , "Percentage", "Actions"});
        dataTable.setColumnWidthArr(new String[] {"15","20", "20", "20", "25"}); 

        setSessionAttribute(EventDTO.SESSION_EVENT_DATA_TABLE, dataTable);
        setSessionAttribute(EventDTO.SESSION_EVENT_LIST, eventList);
        
//        List<DTOBase> eventTypeList = new EventDAO().getEventList();
//        for (DTOBase obj:eventTypeList) {
//        	EventTypeDTO eventType = (EventTypeDTO) obj;
//        	eventType.display();
//        }
    }
}
