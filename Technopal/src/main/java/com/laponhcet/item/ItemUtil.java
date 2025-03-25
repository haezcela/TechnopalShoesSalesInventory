package com.laponhcet.item;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.List;

import com.laponhcet.itemcategory.ItemCategoryDTO;
import com.laponhcet.itemcategory.ItemCategoryUtil;
import com.laponhcet.itemunit.ItemUnitDTO;
import com.mytechnopal.DataTable;
import com.mytechnopal.SessionInfo;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.base.WebControlBase;
import com.mytechnopal.dto.UserDTO;
import com.mytechnopal.util.DTOUtil;
import com.mytechnopal.webcontrol.DataTableWebControl;
import com.mytechnopal.webcontrol.SelectWebControl;
import com.mytechnopal.webcontrol.TextBoxWebControl;

public class ItemUtil implements Serializable {
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
		for (int row = 0; row < dataTable.getRecordListCurrentPage().size(); row++) {
			ItemDTO item = (ItemDTO) dataTable.getRecordListCurrentPage().get(row);
			strArr[row][0] = item.getCode();
			strArr[row][1] = item.getItemCategory().getName(); 
			strArr[row][2] = item.getName();
			strArr[row][3] = item.getDescription();
			strArr[row][4] = item.getItemUnit().getName(); 
			strArr[row][5] = df.format(item.getUnitPrice());
			strArr[row][6] = df.format(item.getQuantity()); 
		    strArr[row][7] = df.format(item.getReorderpoint()); 
			strArr[row][8] = dataTable.getRecordButtonStr(sessionInfo, item.getCode());
		}
		return strArr;
	}
	public static String getDataEntryStr(SessionInfo sessionInfo, ItemDTO item, List<DTOBase> itemCategoryList, List<DTOBase> itemUnitList) {
		StringBuffer strBuff = new StringBuffer();
		strBuff.append(new SelectWebControl().getSelectWebControl("col-lg-2", false, "Category", "ItemCategory", itemCategoryList, item.getItemCategory(), "Select", "0", ""));
		strBuff.append(new TextBoxWebControl().getTextBoxWebControl("col-lg-4", true, "Name", "Name", "Name", item.getName(), 45, WebControlBase.DATA_TYPE_STRING, ""));
		strBuff.append(new TextBoxWebControl().getTextBoxWebControl("col-lg-8", false, "Description", "Description", "Description", item.getDescription(), 45, WebControlBase.DATA_TYPE_STRING, ""));
		strBuff.append(new SelectWebControl().getSelectWebControl("col-lg-2", false, "Unit", "ItemUnit", itemUnitList, item.getItemUnit(), "Select", "0", ""));
		strBuff.append(new TextBoxWebControl().getTextBoxWebControl("col-lg-4", false, "UnitPrice", "UnitPrice", "UnitPrice", String.valueOf(item.getUnitPrice()), 45, WebControlBase.DATA_TYPE_DOUBLE, ""));
		strBuff.append(new TextBoxWebControl().getTextBoxWebControl("col-lg-4", false, "Quantity", "Quantity", "Quantity", String.valueOf(item.getQuantity()), 45, WebControlBase.DATA_TYPE_DOUBLE, ""));
		strBuff.append(new TextBoxWebControl().getTextBoxWebControl("col-lg-4", false, "Reorder Point", "Reorderpoint", "Reorderpoint", String.valueOf(item.getReorderpoint()), 45, WebControlBase.DATA_TYPE_DOUBLE, ""));
		return strBuff.toString();
	}

	public static String getDataViewStr(SessionInfo sessionInfo, ItemDTO item, List<DTOBase> itemUnitList, List<DTOBase> itemCategoryList) {
		StringBuffer strBuff = new StringBuffer();
		strBuff.append("<p>Category: " + item.getItemCategory() + "</p>");
		strBuff.append("<p>Name: " + item.getName() + "</p>");
		strBuff.append("<p>Description: " + item.getDescription() + "</p>");
		strBuff.append("<p>Unit: " + item.getItemUnit() + "</p>");
		strBuff.append("<p>Quantity: " + item.getUnitPrice() + "</p>");
		strBuff.append("<p>Quantity: " + item.getQuantity() + "</p>");
		strBuff.append("<p>Reorder Point: " + item.getReorderpoint() + "</p>");
		strBuff.append("</div>");
		return strBuff.toString();
}
	
	public static void searchByItemName(DataTable dataTable, String searchValue, List<DTOBase> itemList) {
    	System.out.println("Search Value" + searchValue);
    	dataTable.setRecordListInvisible();
		for(DTOBase dto: dataTable.getRecordList()) {
			ItemDTO item = (ItemDTO) dto;
			if(item.getName().toUpperCase().contains(searchValue.toUpperCase())) {
				System.out.println("Search Value" + searchValue);
				item.setVisible(true);
			}
		}
	}
}