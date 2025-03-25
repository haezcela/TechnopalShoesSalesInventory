package com.laponhcet.person;

import java.util.List;


import com.mytechnopal.DataTable;
import com.mytechnopal.base.ActionBase;
import com.mytechnopal.base.DTOBase;

public class PersonAction extends ActionBase {
	private static final long serialVersionUID = 1L;

//	protected void setSessionVars() {
//		List<DTOBase> personList = new PersonDAO().getPersonList();
//		
//		DataTable dataTable = new DataTable(PersonDTO.SESSION_PERSON_DATA_TABLE, personList, new String[] {PersonDTO.ACTION_SEARCH_BY_LASTNAME}, new String[] {"Last Name"});
//		dataTable.setColumnNameArr(new String[] {"LAST NAME", "FIRST NAME", "MIDDLE NAME", "GENDER", "PICTURE", ""});
//		dataTable.setColumnWidthArr(new String[] {"17", "17", "17", "17", "15", "17"});
//		
//		setSessionAttribute(PersonDTO.SESSION_PERSON_DATA_TABLE, dataTable);
//		setSessionAttribute(PersonDTO.SESSION_PERSON_LIST, personList);
//		
//		//setSessionAttribute(PersonDTO);
//	}
	protected void setSessionVars() {
	    List<DTOBase> personList = new PersonDAO().getPersonList();

	    DataTable dataTable = new DataTable(PersonDTO.SESSION_PERSON_DATA_TABLE, personList, 
	        new String[] {PersonDTO.ACTION_SEARCH_BY_LASTNAME}, new String[] {"Last Name"});
	    dataTable.setColumnNameArr(new String[] {"LAST NAME", "FIRST NAME", "MIDDLE NAME", "GENDER", "PICTURE", ""});
	    dataTable.setColumnWidthArr(new String[] {"17", "17", "17", "17", "15", "17"});

	    // ✅ Fix: Set SESSION_PERSON attribute
	    setSessionAttribute(PersonDTO.SESSION_PERSON, new PersonDTO());
	    
	    setSessionAttribute(PersonDTO.SESSION_PERSON_DATA_TABLE, dataTable);
	    setSessionAttribute(PersonDTO.SESSION_PERSON_LIST, personList);
	    //add session inside()
	}

}
