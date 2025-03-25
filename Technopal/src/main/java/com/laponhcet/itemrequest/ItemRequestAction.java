package com.laponhcet.itemrequest;

import java.util.List;

import com.laponhcet.item.ItemDAO;
import com.laponhcet.item.ItemDTO;
import com.mytechnopal.DataTable;
import com.mytechnopal.base.ActionBase;
import com.mytechnopal.base.DTOBase;

public class ItemRequestAction extends ActionBase {

	private static final long serialVersionUID = 1L;

	protected void setSessionVars() {
	    List<DTOBase> bannerList = new ItemRequestDetailsDAO().getItemList();
	    
	    DataTable dataTable = new DataTable(ItemRequestDetailsDTO.SESSION_ITEM_REQUEST_DATA_TABLE, bannerList, new String[] {ItemRequestDetailsDTO.ACTION_SEARCH_BY_NAME}, new String[] {"Item"});
	    
	    // Update column names to include "Date/Time"
	    dataTable.setColumnNameArr(new String[] {"Item Request Code", "Date/Time", "Action"});
	    
	    // Update column widths as necessary
	    dataTable.setColumnWidthArr(new String[] {"40", "40", "20"});
	    
	    setSessionAttribute(ItemRequestDTO.SESSION_ITEM_REQUEST_DATA_TABLE, dataTable);
	    setSessionAttribute(ItemRequestDTO.SESSION_ITEM_REQUEST_LIST, bannerList);
	    //(ItemDTO.SESSION_ITEM_LIST, new ItemDAO().getItemList());
	}
}