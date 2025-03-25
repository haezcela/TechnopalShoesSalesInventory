package com.laponhcet.pageant;

import java.util.List;

import com.mytechnopal.DataTable;
import com.mytechnopal.base.ActionBase;
import com.mytechnopal.base.DTOBase;

public class ContestantInputAction extends ActionBase {
	private static final long serialVersionUID = 1L;

	protected void setSessionVars() {
		List<DTOBase> personList = new ContestantDAO().getContestantList();
		
		DataTable dataTable = new DataTable(ContestantDTO.SESSION_CONTESTANT_DATA_TABLE, personList, new String[] {ContestantDTO.ACTION_SEARCH_BY_NAME}, new String[] {"Name"});
		dataTable.setColumnNameArr(new String[] {"Name", "Sequence", "Picture Name", " "});
		dataTable.setColumnWidthArr(new String[] {"25", "25", "25", "25"});
		setSessionAttribute(ContestantDTO.SESSION_CONTESTANT_DATA_TABLE, dataTable);
		
		setSessionAttribute(ContestantDTO.SESSION_CONTESTANT_LIST, personList);
	}
}
