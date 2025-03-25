package com.laponhcet.person;

import java.io.Serializable;
import java.util.List;

import com.laponhcet.enrollment.EnrollmentDTO;
import com.laponhcet.item.ItemDTO;
import com.laponhcet.student.StudentUtil;
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

public class PersonUtil implements Serializable {
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
			PersonDTO person = (PersonDTO) dataTable.getRecordListCurrentPage().get(row);
			strArr[row][0] = person.getLastName();
			strArr[row][1] = person.getFirstName();
			strArr[row][2] = person.getMiddleName();
			strArr[row][3] = person.getGender();
			strArr[row][4] = person.getPicture();
			strArr[row][5] = dataTable.getRecordButtonStr(sessionInfo, person.getCode());
		}
		return strArr;
	}
	
	public static String getDataEntryStr(SessionInfo sessionInfo, PersonDTO person, UploadedFile uploadedFile) {		
		StringBuffer strBuff = new StringBuffer();
		//Bpotsrap
		//strBuff.append(new TextBoxWebControl().getTextBoxWebControl("form-group col-lg-2", true, "Code", "Code", "Code", person.getCode(), 16, WebControlBase.DATA_TYPE_STRING, ""));
		strBuff.append(new TextBoxWebControl().getTextBoxWebControl("form-group col-lg-3", true, "Last Name", "Last Name", "LastName", person.getLastName(), 50, WebControlBase.DATA_TYPE_STRING, ""));
		strBuff.append(new TextBoxWebControl().getTextBoxWebControl("form-group col-lg-3", true, "First Name", "First Name", "FirstName", person.getFirstName(), 50, WebControlBase.DATA_TYPE_STRING, ""));
		strBuff.append(new TextBoxWebControl().getTextBoxWebControl("form-group col-lg-3", false, "Middle Name", "Middle Name", "MiddleName", person.getMiddleName(), 50, WebControlBase.DATA_TYPE_STRING, ""));
		strBuff.append(new SelectWebControl().getSelectWebControl("col-lg-3", true, "Gender", "Gender", new String[] {"Male", "Female"}, person.getGender(), new String[] {"Male", "Female"}, "Select Gender", "", ""));		
		
		//strBuff.append(new FileInputWebControl().getFileInputWebControl("", true, "", "File", uploadedFile));
		strBuff.append(new FileInputWebControl().getFileInputWebControl("col-lg-3", true, "Upload Picture", "File", uploadedFile));
		System.out.println("Uploaded file: " + (uploadedFile != null ? uploadedFile.getFile() : "No file uploaded"));
		
		
		//strBuff.append(new FileInputWebControl().getFileInputWebControl(null, false, null, null, uploadedFile);

		return strBuff.toString();
	}
	
	public static String getDataViewStr(SessionInfo sessionInfo, PersonDTO person) {
		StringBuffer strBuff = new StringBuffer();
		 strBuff.append("<div class='col-lg-12'>");
		strBuff.append("<p>Last Name: " + person.getLastName() + "</p>");
		strBuff.append("<p>First Name: " + person.getFirstName()+ "</p>");
		strBuff.append("<p>Middle Name: " + person.getMiddleName() + "</p>");
		strBuff.append("<p>Gender: " + person.getGender()+ "</p>");
		
		strBuff.append("<p> Picture: </p>");
		if (person.getPicture() != null && !person.getPicture().isEmpty()) {
		    strBuff.append("<img src='/static/" + sessionInfo.getSettings().getCode() + "/images/" + person.getPicture() + "' style='width: 300px; height: auto;'>");
		} else {
		    strBuff.append("<p>No picture uploaded</p>");
		}

		return strBuff.toString();
	}

//	public static void searchByUserLastName(DataTable dataTable, String searchValue) {
//		dataTable.setRecordListInvisible();
//		for(DTOBase dto: dataTable.getRecordList()) {
//			PersonDTO person = (PersonDTO) dto;
//			if(person.getLastName().toUpperCase().contains(searchValue.toUpperCase())) {
//				person.setVisible(true);
//			}
//		}
//	}

	public static void searchByLastName(DataTable dataTable, String searchValue, List<DTOBase> personList) {
    	System.out.println("Search Value" + searchValue);
    	dataTable.setRecordListInvisible();
		for(DTOBase dto: dataTable.getRecordList()) {
			PersonDTO person = (PersonDTO) dto;
			if(person.getLastName().toUpperCase().contains(searchValue.toUpperCase())) {
				System.out.println("Search Value" + searchValue);
				person.setVisible(true);
			}
		}
	}
}
