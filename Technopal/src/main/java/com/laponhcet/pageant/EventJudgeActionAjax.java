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

public class EventJudgeActionAjax extends ActionAjaxBase {
    private static final long serialVersionUID = 1L;
	private static final List<DTOBase> event = null;

    
    protected void setInput(String action) {
    	EventJudgeDTO judge = (EventJudgeDTO) getSessionAttribute(EventJudgeDTO.SESSION_EVENT_JUDGE);
    	EventJudgeDAO dao = new EventJudgeDAO();
    	String eventCode = dao.getEventCodeById(getRequestString("cboEvent"));
    	String userCode = dao.getUserCodeById(getRequestString("cboJudge"));
    	System.out.println("eventCode: "+getRequestString("cboEvent")+" userCode: "+getRequestString("cboUser"));
    	judge.setEventCode(eventCode);
    	judge.setUserCode(userCode);
   
    }
//
//
//    protected void validateInput(String action) {
//     	if(action.equalsIgnoreCase(DataTable.ACTION_ADD_SAVE) || action.equalsIgnoreCase(DataTable.ACTION_UPDATE_SAVE)) {
//			UploadedFile uploadedFile = (UploadedFile) getSessionAttribute(UploadedFile.SESSION_UPLOADED_FILE + "_0");
//			if (StringUtil.isEmpty(getRequestString("txtName"))) {
//                actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "Name");
//            }
//			if (StringUtil.isEmpty(getRequestString("txtMaxScore"))) {
//                actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "Max Score");
//            }
//            if (StringUtil.isEmpty(String.valueOf(getRequestInt("cboEventName")))) {
//                actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "Event Name");
//            }
//		}
//      
//    }
//
    @SuppressWarnings({ "unchecked", "unchecked" })
	protected void executeLogic(JSONObject jsonObj, DataTable dataTable, String action) {
    	List<DTOBase> eventList1 = (List<DTOBase>) getSessionAttribute(EventDTO.SESSION_EVENT_LIST);
    	List<DTOBase> userList = (List<DTOBase>) getSessionAttribute(UserDTO.SESSION_USER_LIST);
    	if(action.equalsIgnoreCase(DataTable.ACTION_VIEW)) {

		}
    	else if (action.equalsIgnoreCase(DataTable.ACTION_ADD_VIEW)) {
    		System.out.println("ACTION ADD VIEW EVENT JUDGE CLICKED!!");
    		EventJudgeDTO eventJudgeInput = new EventJudgeDTO();
	        try {
	            jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataEntryPage(sessionInfo, EventJudgeUtil.getDataEntryStr(sessionInfo, eventJudgeInput, eventList1, userList), ""));
	        } catch (JSONException e) {
	            e.printStackTrace();
	        }
	        setSessionAttribute(EventJudgeDTO.SESSION_EVENT_JUDGE, eventJudgeInput);
    	}
    	else if(action.equalsIgnoreCase(DataTable.ACTION_ADD_SAVE)) {
    		System.out.println("ACTION ADD SAVE EVENT JUDGE CLICKED!!");
    		EventJudgeDAO judgeDao = new EventJudgeDAO();
    		EventJudgeDTO judge = (EventJudgeDTO) getSessionAttribute(EventJudgeDTO.SESSION_EVENT_JUDGE);
    		judgeDao.executeAdd(judge);
			actionResponse = (ActionResponse) judgeDao.getResult().get(ActionResponse.SESSION_ACTION_RESPONSE);
			if(StringUtil.isEmpty(actionResponse.getType())) {
				actionResponse.constructMessage(ActionResponse.TYPE_SUCCESS, "Added");
				setSessionAttribute(EventJudgeDTO.SESSION_EVENT_JUDGE_LIST, judgeDao.getEventJudgeList());
			}
		}
    	else if(action.equalsIgnoreCase(DataTable.ACTION_DELETE_VIEW)) {

		}
		else if(action.equalsIgnoreCase(DataTable.ACTION_DELETE)) {
			
		}
		else if(action.equalsIgnoreCase(DataTable.ACTION_UPDATE_VIEW)) {
			
		}
		else if (action.equalsIgnoreCase(DataTable.ACTION_UPDATE_SAVE)) {
		
            
		}
	}
    

    protected void dataTableAction(JSONObject jsonObj, DataTable dataTable) {
        initDataTable(dataTable);
        try {
            jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataTablePage(sessionInfo, EventJudgeUtil.getDataTableStr(sessionInfo, dataTable)));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    
    protected void initDataTable(DataTable dataTable) {
        List<DTOBase> eventJudgeList = (List<DTOBase>) getSessionAttribute(EventJudgeDTO.SESSION_EVENT_JUDGE_LIST);
        List<DTOBase> eventList = (List<DTOBase>) getSessionAttribute(EventDTO.SESSION_EVENT_LIST);
        List<DTOBase> userList = (List<DTOBase>) getSessionAttribute(UserDTO.SESSION_USER_LIST);
        
        dataTable.setRecordList(eventJudgeList);
        dataTable.setCurrentPageRecordList();

		for(int row=0; row < dataTable.getRecordListCurrentPage().size(); row++) {			
			EventJudgeDTO eventJudge = (EventJudgeDTO) dataTable.getRecordListCurrentPage().get(row);
			eventJudge.setEvent((EventDTO) DTOUtil.getObjByCode(eventList, eventJudge.getUserCode()));
			eventJudge.setJudge((UserDTO) DTOUtil.getObjByCode(userList, eventJudge.getUserCode()));

		}
    }
    
    
}
