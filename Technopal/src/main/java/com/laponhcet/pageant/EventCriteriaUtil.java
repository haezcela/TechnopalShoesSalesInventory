package com.laponhcet.pageant;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.laponhcet.item.ItemDTO;
import com.mytechnopal.DataTable;
import com.mytechnopal.SessionInfo;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.base.WebControlBase;
import com.mytechnopal.webcontrol.DataTableWebControl;
import com.mytechnopal.webcontrol.SelectWebControl;
import com.mytechnopal.webcontrol.TextBoxWebControl;

public class EventCriteriaUtil implements Serializable {
	private static final long serialVersionUID = 1L;

	public static List<DTOBase> getEventCriteriaListByEventCode(List<DTOBase> eventCriteriaList, String eventCode) {
		List<DTOBase> list = new ArrayList<DTOBase>();
		for(DTOBase eventCriteriaObj: eventCriteriaList) {
			EventCriteriaDTO eventCriteria = (EventCriteriaDTO) eventCriteriaObj;
			if(eventCriteria.getEvent().getCode().equalsIgnoreCase(eventCode)) {
				list.add(eventCriteria);
			}
		}
		return list;
	}
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
		DecimalFormat df = new DecimalFormat("0.##");
		for(int row=0; row < dataTable.getRecordListCurrentPage().size(); row++) {			
			EventCriteriaDTO event = (EventCriteriaDTO) dataTable.getRecordListCurrentPage().get(row);
			strArr[row][0] = event.getName();
			strArr[row][1] = event.getEvent().getName();
			System.out.println("event name: "+ event.getEvent().getName()+"|");
			strArr[row][2] = event.getNameShort();
			strArr[row][3] = String.valueOf(event.getScoreMin()) + "-" + String.valueOf(event.getScoreMax());
			strArr[row][4] = dataTable.getRecordButtonStr(sessionInfo, event.getCode());
		}
		return strArr;
	}
	public static String getDataEntryStr(SessionInfo sessionInfo, EventCriteriaDTO criteria, List<DTOBase> eventList) {
		StringBuffer strBuff = new StringBuffer();
//	    List<String> eventNames = eventList.stream()
//        .map(EventDTO::getName) // Assuming EventTypeDTO has a getName() method
//        .collect(Collectors.toList());

// Convert list to an array
	   // String[] eventNamesArray = eventNames.toArray(new String[0]);
		strBuff.append(new TextBoxWebControl().getTextBoxWebControl("form-group col-lg-4", true, "Name", "Name", "Name", criteria.getName(), 45, WebControlBase.DATA_TYPE_STRING, ""));
		strBuff.append(new TextBoxWebControl().getTextBoxWebControl("form-group col-lg-8", true, "ShortName", "ShortName", "ShortName", criteria.getNameShort(), 45, WebControlBase.DATA_TYPE_STRING, ""));
		strBuff.append(new TextBoxWebControl().getTextBoxWebControl("form-group col-lg-4", true, "MaxScore", "MaxScore", "MaxScore", String.valueOf(criteria.getScoreMax()), 45, WebControlBase.DATA_TYPE_STRING, ""));
		//strBuff.append(new SelectWebControl().getSelectWebControl("", true, "Event", "Event", eventNamesArray, "Select", eventNamesArray, "Select", "0", ""));
		strBuff.append(new SelectWebControl().getSelectWebControl("form-group col-lg-4", true, "EventName", "EventName", eventList, criteria.getEvent(), "-Select-", "0", ""));
		return strBuff.toString();
	}
	public static String getDataViewStr(SessionInfo sessionInfo, EventCriteriaDTO criteria) {
	    StringBuffer strBuff = new StringBuffer();
	    
	    strBuff.append("<div class='col-lg-12'>");
	    strBuff.append("<p>Name: " + criteria.getName() + "</p>");
	    strBuff.append("<p>Short Name: " + criteria.getNameShort()+ "</p>");
	    strBuff.append("<p>Max Score: "+ criteria.getScoreMax()+"</p>");
	    return strBuff.toString();
	}
}
