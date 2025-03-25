package com.laponhcet.pageant;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.laponhcet.pageant.EventDTO;
import com.laponhcet.pageant.EventInputUtil;
import com.laponhcet.student.StudentDTO;
import com.laponhcet.student.StudentUtil;
import com.laponhcet.academicsection.AcademicSectionDTO;
import com.laponhcet.enrollment.EnrollmentDTO;
import com.laponhcet.enrollment.SortLastNameAscending;
import com.laponhcet.item.ItemDTO;
import com.laponhcet.item.ItemUtil;
//import com.laponhcet.itemcategory.ItemCategoryDTO;
import com.laponhcet.pageant.EventInputDAO;
import com.laponhcet.pageant.EventDTO;
import com.laponhcet.pageant.EventInputUtil;
import com.mytechnopal.ActionResponse;
import com.mytechnopal.DataTable;
import com.mytechnopal.SessionInfo;
import com.mytechnopal.UploadedFile;
import com.mytechnopal.base.ActionAjaxBase;
import com.mytechnopal.base.DAOBase;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.dto.UserDTO;
import com.mytechnopal.link.LinkDTO;
import com.mytechnopal.usercontact.UserContactUtil;
import com.mytechnopal.usermedia.UserMediaUtil;
import com.mytechnopal.util.DTOUtil;
import com.mytechnopal.util.PageUtil;
import com.mytechnopal.util.StringUtil;

public class EventCriteriaActionAjax extends ActionAjaxBase {
    private static final long serialVersionUID = 1L;
	private static final List<DTOBase> event = null;

    
    protected void setInput(String action) {
    	EventCriteriaDTO criteria = (EventCriteriaDTO) getSessionAttribute(EventCriteriaDTO.SESSION_EVENT_CRITERIA);
    	EventCriteriaDAO dao = new EventCriteriaDAO();
//		contestant.setCode(getRequestString("txtCode"));
    	criteria.setName(getRequestString("txtName"));
    	criteria.setNameShort(getRequestString("txtShortName"));
    	criteria.setScoreMax(getRequestDouble("txtMaxScore"));
    	criteria.setEventName(getRequestString("cboEvent"));
		String code = dao.getEventCodeById(getRequestString("cboEventName"));
		System.out.println("getRequestString(cboEventName):::: "+getRequestString("cboEventName"));
		criteria.setEventCode(code);
		criteria.setScoreMin(1.0);
   
    }


    protected void validateInput(String action) {
     	if(action.equalsIgnoreCase(DataTable.ACTION_ADD_SAVE) || action.equalsIgnoreCase(DataTable.ACTION_UPDATE_SAVE)) {
			UploadedFile uploadedFile = (UploadedFile) getSessionAttribute(UploadedFile.SESSION_UPLOADED_FILE + "_0");
			if (StringUtil.isEmpty(getRequestString("txtName"))) {
                actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "Name");
            }
			if (StringUtil.isEmpty(getRequestString("txtMaxScore"))) {
                actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "Max Score");
            }
            if (StringUtil.isEmpty(String.valueOf(getRequestInt("cboEventName")))) {
                actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "Event Name");
            }
		}
      
    }

    @SuppressWarnings({ "unchecked", "unchecked" })
	protected void executeLogic(JSONObject jsonObj, DataTable dataTable, String action) {
		List<DTOBase> eventList1 = (List<DTOBase>) getSessionAttribute(EventDTO.SESSION_EVENT_LIST);
    	if(action.equalsIgnoreCase(DataTable.ACTION_VIEW)) {
			System.out.println("ACTION VIEW EVENT CRITERIA CLICKED!!");
			EventCriteriaDTO criteriaSelected = (EventCriteriaDTO) dataTable.getSelectedRecord();
			try {
				jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataViewPage(sessionInfo, EventCriteriaUtil.getDataViewStr(sessionInfo, criteriaSelected), ""));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
    	else if (action.equalsIgnoreCase(DataTable.ACTION_ADD_VIEW)) {
    		System.out.println("ACTION ADD VIEW EVENT CRITERIA CLICKED!!");
    		EventCriteriaDTO eventCriteriaInput = new EventCriteriaDTO();
    		EventCriteriaDAO eventDAO = new EventCriteriaDAO();
    		List<EventDTO> eventList = eventDAO.getEventList();
	        try {
	            jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataEntryPage(sessionInfo, EventCriteriaUtil.getDataEntryStr(sessionInfo, eventCriteriaInput, eventList1), ""));
	        } catch (JSONException e) {
	            e.printStackTrace();
	        }
	        setSessionAttribute(EventCriteriaDTO.SESSION_EVENT_CRITERIA, eventCriteriaInput);
    	}
    	else if(action.equalsIgnoreCase(DataTable.ACTION_ADD_SAVE)) {
    		System.out.println("ACTION ADD SAVE EVENT CRITERIA CLICKED!!");
    		EventCriteriaDAO eventDAO = new EventCriteriaDAO();
    		EventCriteriaDTO criteria = (EventCriteriaDTO) getSessionAttribute(EventCriteriaDTO.SESSION_EVENT_CRITERIA);
			eventDAO.executeAdd(criteria);
			actionResponse = (ActionResponse) eventDAO.getResult().get(ActionResponse.SESSION_ACTION_RESPONSE);
			if(StringUtil.isEmpty(actionResponse.getType())) {
				actionResponse.constructMessage(ActionResponse.TYPE_SUCCESS, "Added");
				setSessionAttribute(EventCriteriaDTO.SESSION_EVENT_CRITERIA_LIST, eventDAO.getEventCriteriaList());
			}
		}
    	else if(action.equalsIgnoreCase(DataTable.ACTION_DELETE_VIEW)) {
			System.out.println("DELETE VIEW EVENT CRITERIA CLICKED!");
			EventCriteriaDTO criteriaSelected = (EventCriteriaDTO) dataTable.getSelectedRecord();
			try {
				jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataViewPage(sessionInfo, EventCriteriaUtil.getDataViewStr(sessionInfo, criteriaSelected), ""));
			} catch (JSONException e) {
				e.printStackTrace();
			}
			setSessionAttribute(EventCriteriaDTO.SESSION_EVENT_CRITERIA, criteriaSelected);
		}
		else if(action.equalsIgnoreCase(DataTable.ACTION_DELETE)) {
			System.out.println("DELETE EVENT CRITERIA CLICKED!");
			EventCriteriaDAO criteriaDAO = new EventCriteriaDAO();
			EventCriteriaDTO criteria = (EventCriteriaDTO) getSessionAttribute(EventCriteriaDTO.SESSION_EVENT_CRITERIA);
			criteriaDAO.executeDelete(criteria);
			actionResponse = (ActionResponse) criteriaDAO.getResult().get(ActionResponse.SESSION_ACTION_RESPONSE);
			if(StringUtil.isEmpty(actionResponse.getType())) {
				setSessionAttribute(EventCriteriaDTO.SESSION_EVENT_CRITERIA_LIST, new EventCriteriaDAO().getEventCriteriaList());
				actionResponse.constructMessage(ActionResponse.TYPE_SUCCESS, "Deleted");


			}
		}
		else if(action.equalsIgnoreCase(DataTable.ACTION_UPDATE_VIEW)) {
			System.out.println("UPDATE EVENT CRITERIA CLICKED!");
			EventCriteriaDTO eventCriteriaUpdate = (EventCriteriaDTO) dataTable.getSelectedRecord();	
			EventCriteriaDAO eventCriteriaDAO= new EventCriteriaDAO ();
	  		List<EventDTO> eventList = eventCriteriaDAO.getEventList(); 
            try {
                jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataEntryPage(sessionInfo, EventCriteriaUtil.getDataEntryStr(sessionInfo, eventCriteriaUpdate, eventList1), ""));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            setSessionAttribute(EventCriteriaDTO.SESSION_EVENT_CRITERIA, eventCriteriaUpdate);
		}
		else if (action.equalsIgnoreCase(DataTable.ACTION_UPDATE_SAVE)) {
			System.out.println("UPDATE SAVE EVENT CRITERIA CLICKED!");
			EventCriteriaDAO eventCriteriaDAO = new EventCriteriaDAO();
			EventCriteriaDTO event = (EventCriteriaDTO) getSessionAttribute(EventCriteriaDTO.SESSION_EVENT_CRITERIA);
            String code = eventCriteriaDAO.getEventCodeById(getRequestString("cboEventName")); 
            event.setEventCode(code);
            eventCriteriaDAO.executeUpdate(event);
            actionResponse = (ActionResponse) eventCriteriaDAO.getResult().get(ActionResponse.SESSION_ACTION_RESPONSE);
            if (StringUtil.isEmpty(actionResponse.getType())) {
            	setSessionAttribute(EventCriteriaDTO.SESSION_EVENT_CRITERIA_LIST, new EventCriteriaDAO().getEventCriteriaList());
            	actionResponse.constructMessage(ActionResponse.TYPE_SUCCESS, "Updated");
            }
            
		}
	}
    

    protected void dataTableAction(JSONObject jsonObj, DataTable dataTable) {
        initDataTable(dataTable);
        try {
            jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataTablePage(sessionInfo, EventCriteriaUtil.getDataTableStr(sessionInfo, dataTable)));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    
    protected void initDataTable(DataTable dataTable) {
        List<DTOBase> eventCList = (List<DTOBase>) getSessionAttribute(EventCriteriaDTO.SESSION_EVENT_CRITERIA_LIST);
        List<DTOBase> eventList = (List<DTOBase>) getSessionAttribute(EventDTO.SESSION_EVENT_LIST);
        
        dataTable.setRecordList(eventCList);
        dataTable.setCurrentPageRecordList();

		for(int row=0; row < dataTable.getRecordListCurrentPage().size(); row++) {			
			EventCriteriaDTO criteria = (EventCriteriaDTO) dataTable.getRecordListCurrentPage().get(row);
			criteria.setEvent((EventDTO) DTOUtil.getObjByCode(eventList, criteria.getEvent().getCode()));

		}
    }
    
}
