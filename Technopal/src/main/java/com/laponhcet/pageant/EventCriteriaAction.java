package com.laponhcet.pageant;

import java.util.List;

//import com.laponhcet.itemcategory.ItemCategoryDAO;
//import com.laponhcet.itemcategory.ItemCategoryDTO;
import com.mytechnopal.DataTable;
import com.mytechnopal.base.ActionBase;
import com.mytechnopal.base.DTOBase;
	
public class EventCriteriaAction extends ActionBase {

    private static final long serialVersionUID = 1L;

    protected void setSessionVars() {
        List<DTOBase> eventCriteriaList = new EventCriteriaDAO().getEventCriteriaList();
        List<DTOBase> eventList = new EventDAO().getEventList();
        
        DataTable dataTable = new DataTable(EventCriteriaDTO.SESSION_EVENT_CRITERIA_DATA_TABLE, eventCriteriaList, new String[] {""}, new String[] {"Name"});
        dataTable.setColumnNameArr(new String[] {"Event Criteria Name", "Event Name", "Short Name" ,"Minimum Score - Maximum Score", ""});
        dataTable.setColumnWidthArr(new String[] {"30","20", "20", "10", "10", "10"}); 

        setSessionAttribute(EventCriteriaDTO.SESSION_EVENT_CRITERIA_DATA_TABLE, dataTable);
        setSessionAttribute(EventCriteriaDTO.SESSION_EVENT_CRITERIA_LIST, eventCriteriaList);
        setSessionAttribute(EventDTO.SESSION_EVENT_LIST, eventList);
        
//        List<DTOBase> eventTypeList = new EventDAO().getEventList();
//        for (DTOBase obj:eventTypeList) {
//        	EventTypeDTO eventType = (EventTypeDTO) obj;
//        	eventType.display();
//        }
    }
}
