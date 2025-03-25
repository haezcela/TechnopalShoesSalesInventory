package com.laponhcet.pageant;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;

import com.laponhcet.enrollment.EnrollmentDTO;
import com.laponhcet.pageant.EventDTO;
import com.laponhcet.pageant.EventInputUtil;
import com.laponhcet.pageant.EventInputDAO;
import com.mytechnopal.DataTable;
import com.mytechnopal.SessionInfo;
import com.mytechnopal.UploadedFile;
import com.mytechnopal.banner.BannerDTO;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.base.WebControlBase;
import com.mytechnopal.dto.UserDTO;
import com.mytechnopal.util.DTOUtil;
import com.mytechnopal.webcontrol.DataTableWebControl;
import com.mytechnopal.webcontrol.FileInputWebControl;
import com.mytechnopal.webcontrol.SelectWebControl;
import com.mytechnopal.webcontrol.TextBoxWebControl;

public class EventInputUtil implements Serializable {
	private static final long serialVersionUID = 1L;

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
			EventDTO event = (EventDTO) dataTable.getRecordListCurrentPage().get(row);
			strArr[row][0] = event.getCode();
			strArr[row][1] = event.getEventType().getCode();
			strArr[row][2] = event.getName();
			strArr[row][3] = String.valueOf(event.getPercentageForFinal());
			System.out.println("double values : "+ String.valueOf(event.getPercentageForFinal()));
			strArr[row][4] = dataTable.getRecordButtonStr(sessionInfo, event.getCode());
		}
		return strArr;
	}
	
	public static String getDataEntryStr(SessionInfo sessionInfo, EventDTO eventInput, List<EventTypeDTO> eventType) {		
		StringBuffer strBuff = new StringBuffer();
		List<String> eventNames = eventType.stream()
                .map(EventTypeDTO::getName) // Assuming EventTypeDTO has a getName() method
                .collect(Collectors.toList());
		//Convert list to an array
		String[] eventNamesArray = eventNames.toArray(new String[0]);

//		strBuff.append(new TextBoxWebControl().getTextBoxWebControl("col-lg-3", true, "Code", "Code", "Code", eventInput.getCode(), 3, WebControlBase.DATA_TYPE_STRING, ""));
		strBuff.append(new SelectWebControl().getSelectWebControl("col-lg-3", true, "Event Type", "EventTypeCode", eventNamesArray, "Select", eventNamesArray, "Select", "0", ""));
		strBuff.append(new TextBoxWebControl().getTextBoxWebControl("col-lg-3", true, "Name", "Name", "Name", eventInput.getName(), 45, WebControlBase.DATA_TYPE_STRING, ""));
		strBuff.append(new TextBoxWebControl().getTextBoxWebControl("col-lg-3", true, "Percentage Score", "Percentage", "Percentage", String.valueOf(eventInput.getPercentageForFinal()), 45, WebControlBase.DATA_TYPE_STRING, ""));
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

//	public static void searchByLastName(DataTable dataTable, String searchValue, List<DTOBase> personList) {
//    	System.out.println("Search Value" + searchValue);
//    	dataTable.setRecordListInvisible();
//		for(DTOBase dto: dataTable.getRecordList()) {
//			EventDTO person = (EventDTO) dto;
//			if(person.getName().toUpperCase().contains(searchValue.toUpperCase())) {
//				System.out.println("Search Value" + searchValue);
//				person.setVisible(true);
//			}
//		}
//	}
}
