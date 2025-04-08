package com.laponhcet.item;

import java.util.List;

import com.laponhcet.itemcategory.ItemCategoryDAO;
import com.laponhcet.itemcategory.ItemCategoryDTO;
import com.laponhcet.itemunit.ItemUnitDAO;
import com.laponhcet.itemunit.ItemUnitDTO;
import com.laponhcet.itemmedia.ItemMediaDAO;
import com.laponhcet.itemmedia.ItemMediaDTO;


import com.mytechnopal.DataTable;
import com.mytechnopal.UploadedFile;
import com.mytechnopal.base.ActionBase;
import com.mytechnopal.base.DTOBase;

public class ItemAction extends ActionBase {

    private static final long serialVersionUID = 1L;

    protected void setSessionVars() {
        List<DTOBase> itemList = new ItemDAO().getItemList();

        
		DataTable dataTable = new DataTable(ItemDTO.SESSION_ITEM_DATA_TABLE, itemList,
				new String[] { ItemDTO.ACTION_SEARCH_BY_NAME }, new String[] { "Name" });
        dataTable.setColumnNameArr(new String[] {"ID", "Code", "Category", "Name", "Description", "Unit", "Unit Price", "Quantity", "Reorderpoint", "Actions"});
        dataTable.setColumnWidthArr(new String[] { "10","10", "10", "10", "10", "10" , "15","10", "10", "15"}); 

        setSessionAttribute(ItemDTO.SESSION_ITEM, new ItemDTO());
        setSessionAttribute(ItemCategoryDTO.SESSION_ITEM_CATEGORY_LIST, new ItemCategoryDAO().getItemCategoryList());
        setSessionAttribute(ItemUnitDTO.SESSION_ITEM_UNIT_LIST, new ItemUnitDAO().getItemUnitList());
        setSessionAttribute(ItemDTO.SESSION_ITEM_DATA_TABLE, dataTable);
        setSessionAttribute(ItemDTO.SESSION_ITEM_LIST, itemList);
        
        setSessionAttribute(ItemMediaDTO.SESSION_ITEM_MEDIA, new ItemMediaDTO());

        
        UploadedFile uploadedFile = new UploadedFile(); //no need to specify id for the default id is 0
        uploadedFile.setSettings(sessionInfo.getSettings());
        uploadedFile.setValidFileExt(new String[] {"png", "jpg"});
        uploadedFile.setMaxSize(1024000); //1Mb 
		setSessionAttribute(UploadedFile.SESSION_UPLOADED_FILE + "_0", uploadedFile);	
		
        
    }
}