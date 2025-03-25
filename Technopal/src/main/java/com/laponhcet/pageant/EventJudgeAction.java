package com.laponhcet.pageant;

import java.util.List;


import com.mytechnopal.DataTable;
import com.mytechnopal.base.ActionBase;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.dao.UserDAO;
import com.mytechnopal.dto.UserDTO;


public class EventJudgeAction extends ActionBase {

    private static final long serialVersionUID = 1L;

    protected void setSessionVars() {
        List<DTOBase> eventJudgeList = new EventJudgeDAO().getEventJudgeList();
        List<DTOBase> eventList = new EventDAO().getEventList();
        List<DTOBase> judgeList = new UserDAO().getUserList();
        DataTable dataTable = new DataTable(EventJudgeDTO.SESSION_EVENT_JUDGE_DATA_TABLE, eventJudgeList, new String[] {""}, new String[] {"Name"});
        dataTable.setColumnNameArr(new String[] {"UserCode", "EventCode", ""});
        dataTable.setColumnWidthArr(new String[] {"40","40", "20"}); 

        setSessionAttribute(EventJudgeDTO.SESSION_EVENT_JUDGE_DATA_TABLE, dataTable);
        setSessionAttribute(EventJudgeDTO.SESSION_EVENT_JUDGE_LIST, eventJudgeList);
        setSessionAttribute(EventDTO.SESSION_EVENT_LIST, eventList);
        setSessionAttribute(UserDTO.SESSION_USER_LIST, judgeList);
        
        

    }
}
