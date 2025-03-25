package com.laponhcet.pageant;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import com.laponhcet.enrollment.EnrollmentDTO;

import com.laponhcet.student.StudentDTO;
import com.laponhcet.student.StudentUtil;
import com.mytechnopal.DataTable;
import com.mytechnopal.SessionInfo;
import com.mytechnopal.UploadedFile;
import com.mytechnopal.banner.BannerDTO;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.base.WebControlBase;
import com.mytechnopal.dto.UserDTO;
import com.mytechnopal.util.DTOUtil;
import com.mytechnopal.util.DateTimeUtil;
import com.mytechnopal.webcontrol.DataTableWebControl;
import com.mytechnopal.webcontrol.FileInputWebControl;
import com.mytechnopal.webcontrol.SelectWebControl;
import com.mytechnopal.webcontrol.TextBoxWebControl;

public class ContestantInputUtil implements Serializable {
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
		for(int row=0; row < dataTable.getRecordListCurrentPage().size(); row++) {			
			ContestantDTO contestant = (ContestantDTO) dataTable.getRecordListCurrentPage().get(row);
			strArr[row][0] = contestant.getName();
			strArr[row][1] = String.valueOf(contestant.getSequence());
			strArr[row][2] = contestant.getPict();
			strArr[row][3] = dataTable.getRecordButtonStr(sessionInfo, contestant.getCode());

		}
		return strArr;
	}
	public static String getDataViewStr(SessionInfo sessionInfo, ContestantDTO contestant) {
	    StringBuffer strBuff = new StringBuffer();
	    
	    strBuff.append("<div class='col-lg-12'>");
	    strBuff.append("<p>Name: " + contestant.getName() + "</p>");
	    strBuff.append("<p>Sequence: " + contestant.getSequence() + "</p>");
	    strBuff.append("<p>Image:</p>");
	    strBuff.append("<img src='/static/" + sessionInfo.getSettings().getCode() + "/media/contestant/" + contestant.getPict() 
	                   + "' style='width: 300px; height: auto;'>"); // Adjust width as needed
	    strBuff.append("</div>");
	    return strBuff.toString();
	}

//	  public static String getDataViewStr(SessionInfo sessionInfo, ContestantDTO contestant) {
//			StringBuffer strBuff = new StringBuffer();
//			strBuff.append("<div class='col-lg-12'>");
//			strBuff.append("	<img src='/static/" + sessionInfo.getSettings().getCode() + "/media/banner/" + banner.getFilename() + "'>");
//			System.out.println("banner.getFilename(): "+banner.getFilename());
//			strBuff.append("</div>");
//			return strBuff.toString();
//		}
	public static String getDataEntryStr(SessionInfo sessionInfo, ContestantDTO contestant, UploadedFile uploadedFile) {		
	    StringBuffer strBuff = new StringBuffer();
//	    List<String> eventNames = eventList.stream()
//	            .map(EventTypeDTO::getName) // Assuming EventTypeDTO has a getName() method
//	            .collect(Collectors.toList());
//
//	    // Convert list to an array
//	    String[] eventNamesArray = eventNames.toArray(new String[0]);

	    // Add a container for proper spacing
	    strBuff.append("<div class='container text-center'>");

	    // Form row
	    strBuff.append("<div class='row justify-content-center'>");

	    // Name input
	    strBuff.append("<div class='col-md-3 mb-2'>");
	    strBuff.append(new TextBoxWebControl().getTextBoxWebControl("", true, "Name", "Name", "Name", contestant.getName(), 45, WebControlBase.DATA_TYPE_STRING, ""));
	    strBuff.append("</div>");

	    // Event Type dropdown
//	    strBuff.append("<div class='col-md-3 mb-2'>");
//	    strBuff.append(new SelectWebControl().getSelectWebControl("", false, "EventType", "EventType", eventNamesArray, "Select", eventNamesArray, "Select", "0", ""));
//	    strBuff.append("</div>");

	    // Sequence input
	    strBuff.append("<div class='col-md-3 mb-2'>");
	    strBuff.append(new TextBoxWebControl().getTextBoxWebControl("", true, "Sequence", "Sequence", "Sequence", String.valueOf(contestant.getSequence()), 45, WebControlBase.DATA_TYPE_STRING, ""));
	    strBuff.append("</div>");
	 
	    // File input
	    strBuff.append("<div class='col-md-3 mb-2'>");
	    strBuff.append(new FileInputWebControl().getFileInputWebControl("", true, "", "File", uploadedFile));
	    strBuff.append("</div>");

	    // Close row and container
	    strBuff.append("</div></div>");

	    return strBuff.toString();
	}

	public static void searchByName(DataTable dataTable, String searchValue, List<DTOBase> userList) {
		searchValue = searchValue.toUpperCase(); // Normalize search value for case-insensitive comparison
		for(DTOBase dto: dataTable.getRecordList()) {
			ContestantDTO contestant = (ContestantDTO) dto;
			String firstName = contestant.getName().toUpperCase();
			//UserDTO user = (UserDTO) DTOUtil.getObjByCode(userList, person.getPerson().getCode());
			if(hasCommonCharacter(firstName, searchValue)) {
//				PersonUtil.setPerson(person.getPerson(), user);
				contestant.setVisible(true);
			}
		}
	}
	
//	public static void searchByMiddleName(DataTable dataTable, String searchValue, List<DTOBase> userList) {
//	    searchValue = searchValue.toUpperCase(); // Normalize search value for case-insensitive comparison
//
//	    for (DTOBase dto : dataTable.getRecordList()) {
//	        PersonDTO person = (PersonDTO) dto;
//	        String middleName = person.getMiddleName().toUpperCase(); // Convert to uppercase for case-insensitivity
//
//	        // Check if at least one character from searchValue is present in middleName
//	        if (hasCommonCharacter(middleName, searchValue)) {
//	            person.setVisible(true);
//	        }
//	    }
//	}
//	public static void searchByLastName(DataTable dataTable, String searchValue, List<DTOBase> userList) {
//		searchValue = searchValue.toUpperCase(); // Normalize search value for case-insensitive comparison
//		for(DTOBase dto: dataTable.getRecordList()) {
//			PersonDTO person = (PersonDTO) dto;
//			String lastName = person.getLastName().toUpperCase(); 
////			UserDTO user = (UserDTO) DTOUtil.getObjByCode(userList, person.getPerson().getCode());
////			if(person.getLastName().toUpperCase().equalsIgnoreCase(searchValue.toUpperCase())) {
//			if (hasCommonCharacter(lastName, searchValue)) {
////				PersonUtil.setPerson(person.getPerson(), user);
//				person.setVisible(true);
//			}
//		}
//	}
//	public static void searchByCode(DataTable dataTable, String searchValue, List<DTOBase> userList) {
//		for(DTOBase dto: dataTable.getRecordList()) {
//			PersonDTO person = (PersonDTO) dto;
////			UserDTO user = (UserDTO) DTOUtil.getObjByCode(userList, person.getPerson().getCode());
//			if(person.getCode().toUpperCase().equalsIgnoreCase(searchValue.toUpperCase())) {
////				PersonUtil.setPerson(person.getPerson(), user);
//				person.setVisible(true);
//			}
//		}
//	}
	private static boolean hasCommonCharacter(String name, String searchValue) {
	    for (char c : searchValue.toCharArray()) {
	        if (name.contains(String.valueOf(c))) {
	            return true;
	        }
	    }
	    return false;
	}
	
//	public static void setPerson(PersonDTO person, UserDTO user) {
//		person.setFirstName(user.getFirstName());
//		person.setMiddleName(user.getMiddleName());
//		person.setLastName(user.getLastName());
//		person.setGender(user.getGender());
//	}
	
}
