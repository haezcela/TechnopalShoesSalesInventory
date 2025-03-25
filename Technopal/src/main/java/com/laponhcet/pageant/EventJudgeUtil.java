package com.laponhcet.pageant;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.mytechnopal.DataTable;
import com.mytechnopal.SessionInfo;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.base.WebControlBase;
import com.mytechnopal.dto.UserDTO;
import com.mytechnopal.util.DTOUtil;
import com.mytechnopal.webcontrol.DataTableWebControl;
import com.mytechnopal.webcontrol.SelectWebControl;
import com.mytechnopal.webcontrol.TextBoxWebControl;

public class EventJudgeUtil implements Serializable {
	private static final long serialVersionUID = 1L;

	public static List<DTOBase> getEventJudgeListByJudge(List<DTOBase> eventJudgeList, String userCode) {
		List<DTOBase> list = new ArrayList<DTOBase>();
		for(DTOBase eventJudgeObj: eventJudgeList) {
			EventJudgeDTO eventJudge = (EventJudgeDTO) eventJudgeObj;
			if(eventJudge.getJudge().getCode().equalsIgnoreCase(userCode)) {
				list.add(eventJudge);
			}
		}
		return list;
	}
	
	public static List<DTOBase> getEventJudgeListByEventCode(List<DTOBase> eventJudgeList, String eventCode) {
		List<DTOBase> list = new ArrayList<DTOBase>();
		for(DTOBase eventJudgeObj: eventJudgeList) {
			EventJudgeDTO eventJudge = (EventJudgeDTO) eventJudgeObj;
			if(eventJudge.getEvent().getCode().equalsIgnoreCase(eventCode)) {
				list.add(eventJudge);
			}
		}
		return list;
	}
	
	public static void setEventJudgeListJudge(List<DTOBase> eventJudgeList, List<DTOBase> userList) {
		for(DTOBase eventJudgeObj: eventJudgeList) {
			EventJudgeDTO eventJudge = (EventJudgeDTO) eventJudgeObj;
			eventJudge.setJudge((UserDTO) DTOUtil.getObjByCode(userList, eventJudge.getJudge().getCode()));
		}
	}
	public static String getDataTableStr(SessionInfo sessionInfo, DataTable dataTable) {
		DataTableWebControl dtwc = new DataTableWebControl(sessionInfo, dataTable);
		StringBuffer strBuff = new StringBuffer();
		if(dataTable.getRecordList().size() >= 1) {
			strBuff.append(dtwc.getDataTableHeader(sessionInfo, dataTable));
			dataTable.setDataTableRecordArr(getDataTableCurrentPageRecordArr(sessionInfo, dataTable));
			strBuff.append(dtwc.getDataTable(true, false));
		}
		return strBuff.toString();
	}
	
	private static String[][] getDataTableCurrentPageRecordArr(SessionInfo sessionInfo, DataTable dataTable) {
		String[][] strArr = new String[dataTable.getRecordListCurrentPage().size()][dataTable.getColumnNameArr().length];
		DecimalFormat df = new DecimalFormat("0.##");
		for(int row=0; row < dataTable.getRecordListCurrentPage().size(); row++) {			
			EventJudgeDTO eventJudge = (EventJudgeDTO) dataTable.getRecordListCurrentPage().get(row);
			strArr[row][0] = eventJudge.getUserCode();
			strArr[row][1] = eventJudge.getEventCode();
//			strArr[row][0] = eventJudge.getUserCode();
//			strArr[row][1] = eventJudge.getEventCode();
			strArr[row][2] = dataTable.getRecordButtonStr(sessionInfo, eventJudge.getCode());
		}
		return strArr;
	}
	
	public static String getDataEntryStr(SessionInfo sessionInfo, EventJudgeDTO eventJudge, List<DTOBase> eventList, List<DTOBase> userList) {		
		StringBuffer strBuff = new StringBuffer();


		strBuff.append(new SelectWebControl().getSelectWebControl("form-group col-lg-4", true, "Event", "Event", eventList, eventJudge.getEvent(), "-Select-", "0", ""));
		strBuff.append(new SelectWebControl().getSelectWebControl("form-group col-lg-4", true, "Judge", "Judge", userList, eventJudge.getJudge(), "-Select-", "0", ""));

		return strBuff.toString();
	}
	
	public static String getDataViewStr(SessionInfo sessionInfo, EventDTO eventInput) {
		StringBuffer strBuff = new StringBuffer();
		strBuff.append("<div class='col-lg-12'>");
		strBuff.append("<p>Code: " + eventInput.getCode()+ "</p>");
		strBuff.append("<p>Event type Code: " + eventInput.getEventType().getCode()+ "</p>");
		strBuff.append("<p>Name: " + eventInput.getName()+ "</p>");
		strBuff.append("<p>Percentage: " + eventInput.getEventType().getCode()+ "</p>");
		strBuff.append("</div>");
		return strBuff.toString();
	}
}
