package com.laponhcet.pageant;

import java.util.Arrays;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.laponhcet.pageant.EventDTO;
import com.laponhcet.pageant.EventInputUtil;
import com.laponhcet.item.ItemDTO;
import com.laponhcet.item.ItemUtil;
//import com.laponhcet.itemcategory.ItemCategoryDTO;
import com.laponhcet.pageant.EventInputDAO;
import com.laponhcet.pageant.EventDTO;
import com.laponhcet.pageant.EventInputUtil;
import com.mytechnopal.ActionResponse;
import com.mytechnopal.DataTable;
import com.mytechnopal.SessionInfo;
import com.mytechnopal.base.ActionAjaxBase;
import com.mytechnopal.base.DAOBase;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.link.LinkDTO;
import com.mytechnopal.util.DTOUtil;
import com.mytechnopal.util.PageUtil;
import com.mytechnopal.util.StringUtil;

public class EventInputActionAjax extends ActionAjaxBase {
    private static final long serialVersionUID = 1L;
	private static final List<DTOBase> event = null;

    
    protected void setInput(String action) {
       EventDTO item = (EventDTO) getSessionAttribute(EventDTO.SESSION_EVENT);
       EventInputDAO eventDAO = new EventInputDAO();
//        item.setCode(getRequestString("txtCode"));
        String code = eventDAO.getEventTypeCodeByName(getRequestString("cboEventTypeCode"));
        item.getEventType().setCode(code);
        item.setName(getRequestString("txtName"));
        item.setPercentageForFinal(getRequestDouble("txtPercentage"));
    }


    protected void validateInput(String action) {
        if (action.equalsIgnoreCase(DataTable.ACTION_ADD_SAVE) || action.equalsIgnoreCase(DataTable.ACTION_UPDATE_SAVE)) {
        	System.out.println("GEgegegegeg");
//            if (StringUtil.isEmpty(getRequestString("txtCode"))) {
//                actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "Code");
//            }
            
            if (StringUtil.isEmpty(getRequestString("cboEventTypeCode")) || getRequestString("cboEventTypeCode") == "") {
                actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "EventType Name");
            }           
            if (StringUtil.isEmpty(getRequestString("txtName"))) {
                actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "Name");
            }
            if (StringUtil.isEmpty(String.valueOf(getRequestDouble("txtPercentage")))) {
                actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "Percentage");
            }
        }
    }

    protected void executeLogic(JSONObject jsonObj, DataTable dataTable, String action) {
    	if (action.equalsIgnoreCase(DataTable.ACTION_VIEW)) {
        	EventDTO event = (EventDTO) dataTable.getSelectedRecord();
            try {
                jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataViewPage(sessionInfo, EventInputUtil.getDataViewStr(sessionInfo, event), ""));
            } catch (JSONException e) {
                e.printStackTrace();
            }
    	}
    	else if (action.equalsIgnoreCase(DataTable.ACTION_ADD_VIEW)) {
    		EventDTO eventInput = new EventDTO();
    		EventInputDAO eventDAO= new EventInputDAO ();
    		List<EventTypeDTO> eventType = eventDAO.getEventTypeList(); 
        try {
            jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataEntryPage(sessionInfo, EventInputUtil.getDataEntryStr(sessionInfo, eventInput, eventType ), ""));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        setSessionAttribute(EventDTO.SESSION_EVENT, eventInput );
    	}
		else if(action.equalsIgnoreCase(DataTable.ACTION_ADD_SAVE)) {
			EventInputDAO eventDAO = new EventInputDAO();
			EventDTO event = (EventDTO) getSessionAttribute(EventDTO.SESSION_EVENT);
			eventDAO.executeAdd(event);
			actionResponse = (ActionResponse) eventDAO.getResult().get(ActionResponse.SESSION_ACTION_RESPONSE);
			if(StringUtil.isEmpty(actionResponse.getType())) {
				actionResponse.constructMessage(ActionResponse.TYPE_SUCCESS, "Added");
				setSessionAttribute(EventDTO.SESSION_EVENT_LIST, eventDAO.getEventList());
			}
		}
		else if(action.equalsIgnoreCase(DataTable.ACTION_UPDATE_VIEW)) {
			EventDTO eventUpdate = (EventDTO) dataTable.getSelectedRecord();	
    		EventInputDAO eventDAO= new EventInputDAO ();
    		List<EventTypeDTO> eventType = eventDAO.getEventTypeList(); 
            try {
                jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataEntryPage(sessionInfo, EventInputUtil.getDataEntryStr(sessionInfo, eventUpdate, eventType), ""));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            setSessionAttribute(EventDTO.SESSION_EVENT, eventUpdate);
		}
		else if (action.equalsIgnoreCase(DataTable.ACTION_UPDATE_SAVE)) {
            EventInputDAO eventDAO = new EventInputDAO();
            EventDTO event = (EventDTO) getSessionAttribute(EventDTO.SESSION_EVENT);
            String code = eventDAO.getEventTypeCodeByName(getRequestString("cboEventTypeCode")); 
            event.setEventTypeCode(code);
            eventDAO.executeUpdate(event);
            actionResponse = (ActionResponse) eventDAO.getResult().get(ActionResponse.SESSION_ACTION_RESPONSE);
            if (StringUtil.isEmpty(actionResponse.getType())) {
            	
            	setSessionAttribute(EventDTO.SESSION_EVENT_LIST, new EventDAO().getEventList());
            	actionResponse.constructMessage(ActionResponse.TYPE_SUCCESS, "Updated");
            }
            
		}
		else if (action.equalsIgnoreCase(DataTable.ACTION_DELETE_VIEW)) {
            EventDTO event = (EventDTO) dataTable.getSelectedRecord();
            try {
                jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataViewPage(sessionInfo, EventInputUtil.getDataViewStr(sessionInfo, event),""));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            setSessionAttribute(EventDTO.SESSION_EVENT, event);
		}
		else if (action.equalsIgnoreCase(DataTable.ACTION_DELETE)) {
            EventInputDAO eventDAO = new EventInputDAO();
            EventDTO event = (EventDTO) getSessionAttribute(EventDTO.SESSION_EVENT);
            eventDAO.executeDelete(event);
            actionResponse = (ActionResponse) eventDAO.getResult().get(ActionResponse.SESSION_ACTION_RESPONSE);
            if (StringUtil.isEmpty(actionResponse.getType())) {
                setSessionAttribute(EventDTO.SESSION_EVENT_LIST, new EventInputDAO().getEventList());
                actionResponse.constructMessage(ActionResponse.TYPE_SUCCESS, "Deleted");
            }
        }
	}
    

    protected void dataTableAction(JSONObject jsonObj, DataTable dataTable) {
        initDataTable(dataTable);
        try {
            jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataTablePage(sessionInfo, EventInputUtil.getDataTableStr(sessionInfo, dataTable)));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    
    protected void initDataTable(DataTable dataTable) {
        List<DTOBase> eventList = (List<DTOBase>) getSessionAttribute(EventDTO.SESSION_EVENT_LIST);
        dataTable.setRecordList(eventList);
        dataTable.setCurrentPageRecordList();
    }
}
