package com.laponhcet.item;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.List;

import com.laponhcet.itemcategory.ItemCategoryDAO;
import com.laponhcet.itemcategory.ItemCategoryDTO;
import com.laponhcet.itemcategory.ItemCategoryUtil;
import com.laponhcet.itemmedia.ItemMediaDAO;
import com.laponhcet.itemmedia.ItemMediaDTO;
import com.laponhcet.itemunit.ItemUnitDTO;
import com.laponhcet.itemunit.ItemUnitDAO;
import com.mytechnopal.DataTable;
import com.mytechnopal.SessionInfo;
import com.mytechnopal.UploadedFile;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.base.WebControlBase;
import com.mytechnopal.dto.UserDTO;
import com.mytechnopal.util.DTOUtil;
import com.mytechnopal.webcontrol.DataTableWebControl;
import com.mytechnopal.webcontrol.FileInputWebControl;
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
//		 DecimalFormat df = new DecimalFormat("0.##");
		for (int row = 0; row < dataTable.getRecordListCurrentPage().size(); row++) {
			ItemDTO item = (ItemDTO) dataTable.getRecordListCurrentPage().get(row);
			strArr[row][0] = String.valueOf(item.getId());
			strArr[row][1] = item.getCode();
			strArr[row][2] = item.getItemCategory().getName(); 
			strArr[row][3] = item.getName();
			strArr[row][4] = item.getDescription();
			strArr[row][5] = item.getItemUnit().getName(); 
			strArr[row][6] = String.valueOf(item.getUnitPrice());
			strArr[row][7] = String.valueOf(item.getQuantity()); 
		    strArr[row][8] = String.valueOf(item.getReorderpoint()); 
			strArr[row][9] = dataTable.getRecordButtonStr(sessionInfo, item.getCode());
		}
		return strArr;
	}
	public static String getDataEntryStr(SessionInfo sessionInfo, ItemDTO item, List<DTOBase> itemCategoryList, List<DTOBase> itemUnitList, UploadedFile uploadedFile) {
		StringBuffer strBuff = new StringBuffer();
		
		for (DTOBase obj:itemCategoryList) {
			ItemCategoryDTO itemCategory= (ItemCategoryDTO) obj;
      		System.out.println("itemCategoryId" + itemCategory.getId());
      		System.out.println("itemCategoryCode" + itemCategory.getCode());
      		System.out.println("itemCategoryName" + itemCategory.getName());
      		System.out.println("itemCategorydisplay" + itemCategory.getDisplayStr());
      	 }
		
		for (DTOBase obj:itemUnitList) {
			ItemUnitDTO itemUnit= (ItemUnitDTO) obj;
      		System.out.println("itemUnitId" + itemUnit.getId());
      		System.out.println("itemUnitCode" + itemUnit.getCode());
      		System.out.println("itemUnitName" + itemUnit.getName());
      		System.out.println("itemUnitdisplay" + itemUnit.getDisplayStr());
      	 }
		
		strBuff.append("<div class='row p-1'>");
		strBuff.append(new SelectWebControl().getSelectWebControl("col-lg-6", true, "ItemCategory", "ItemCategory", itemCategoryList, item.getItemCategory(), "Select", "", ""));
		strBuff.append(new TextBoxWebControl().getTextBoxWebControl("col-lg-6", true, "Name", "Name", "Name", item.getName(), 45, WebControlBase.DATA_TYPE_STRING, ""));
		strBuff.append("</div>");

		strBuff.append("<div class='row p-1'>");
		strBuff.append(new TextBoxWebControl().getTextBoxWebControl("col-lg-8", false, "Description", "Description", "Description", item.getDescription(), 45, WebControlBase.DATA_TYPE_STRING, ""));
		strBuff.append(new SelectWebControl().getSelectWebControl("col-lg-4", true, "ItemUnit", "ItemUnit", itemUnitList, item.getItemUnit(), "Select", "", ""));
		strBuff.append("</div>");

		strBuff.append("<div class='row p-1'>");
		strBuff.append(new TextBoxWebControl().getTextBoxWebControl("col-lg-4", false, "UnitPrice", "UnitPrice", "UnitPrice", String.valueOf(item.getUnitPrice()), 45, WebControlBase.DATA_TYPE_DOUBLE, ""));
		strBuff.append(new TextBoxWebControl().getTextBoxWebControl("col-lg-4", false, "Quantity", "Quantity", "Quantity", String.valueOf(item.getQuantity()), 45, WebControlBase.DATA_TYPE_DOUBLE, ""));
		strBuff.append(new TextBoxWebControl().getTextBoxWebControl("col-lg-4", false, "Reorder Point", "Reorderpoint", "Reorderpoint", String.valueOf(item.getReorderpoint()), 45, WebControlBase.DATA_TYPE_DOUBLE, ""));
		strBuff.append("</div>");

		//File Input
		strBuff.append("<div class='d-flex justify-content-center p-1'>"); 
		strBuff.append(new FileInputWebControl().getFileInputWebControl("form-group col-lg-6", true, "", "File", uploadedFile));
		strBuff.append("</div>");
		System.out.println("Uploaded file: " + (uploadedFile != null ? uploadedFile.getFile() : "No file uploaded"));
		return strBuff.toString();
	}
	private static String[] getItemCategory() {
        ItemCategoryDAO itemCategoryDAO = new ItemCategoryDAO();
        List<DTOBase> itemCategories = itemCategoryDAO.getItemCategoryList();

        return itemCategories.stream()
            .map(dto -> ((ItemCategoryDTO) dto).getCode())
            .toArray(String[]::new);
    }
	
	private static String[] getItemUnit() {
        ItemUnitDAO itemUnitDAO = new ItemUnitDAO();
        List<DTOBase> itemCategories = itemUnitDAO.getItemUnitList();

        return itemCategories.stream()
            .map(dto -> ((ItemUnitDTO) dto).getCode())
            .toArray(String[]::new);
	}

	public static String getDataViewStr(SessionInfo sessionInfo, ItemDTO item) {
	    StringBuffer strBuff = new StringBuffer();
	    strBuff.append("<div class='col-lg-12'>");
	    strBuff.append("<p>Category: " + item.getItemCategory().getName() + "</p>");
	    strBuff.append("<p>Name: " + item.getName() + "</p>");
	    strBuff.append("<p>Description: " + item.getDescription() + "</p>");
	    strBuff.append("<p>Unit: " + item.getItemUnit().getName() + "</p>");
	    strBuff.append("<p>Unit Price: " + item.getUnitPrice() + "</p>");
	    strBuff.append("<p>Quantity: " + item.getQuantity() + "</p>");
	    strBuff.append("<p>Reorder Point: " + item.getReorderpoint() + "</p>");

	    // Load the media based on item code
	    ItemMediaDAO itemMediaDAO = new ItemMediaDAO();
	    ItemMediaDTO itemMedia = itemMediaDAO.getByItemCode(item.getCode());  // Get by item code

	    strBuff.append("<p>Picture:</p>");
	    if (itemMedia != null && itemMedia.getFileName() != null && !itemMedia.getFileName().isEmpty()) {
	        // Print filename to console
	        //System.out.println("Item Media File Name: " + itemMedia.getFileName());

	        strBuff.append("<img src='/static/" + sessionInfo.getSettings().getCode() + "/media/item/" + itemMedia.getFileName() + "' style='width: 300px; height: auto;'>");
	    } else {
	        strBuff.append("<p>No picture uploaded</p>");
	    }
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