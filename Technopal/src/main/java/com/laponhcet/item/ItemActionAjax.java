package com.laponhcet.item;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.json.JSONObject;


import com.laponhcet.itemcategory.ItemCategoryDTO;
import com.laponhcet.itemcategory.ItemCategoryUtil;
import com.laponhcet.itemunit.ItemUnitDTO;
import com.laponhcet.itemunit.ItemUnitUtil;
import com.laponhcet.vehicle.VehicleDTO;
import com.laponhcet.vehicle.VehicleUtil;
import com.laponhcet.itemmedia.ItemMediaDAO;
import com.laponhcet.itemmedia.ItemMediaDTO;

import com.mytechnopal.ActionResponse;
import com.mytechnopal.DataTable;
import com.mytechnopal.UploadedFile;
import com.mytechnopal.banner.BannerDAO;
import com.mytechnopal.banner.BannerDTO;
import com.mytechnopal.banner.BannerUtil;
import com.mytechnopal.base.ActionAjaxBase;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.dto.MediaDTO;
import com.mytechnopal.link.LinkDTO;

import com.mytechnopal.util.DTOUtil;
import com.mytechnopal.util.FileUtil;
import com.mytechnopal.util.PageUtil;
import com.mytechnopal.util.StringUtil;

public class ItemActionAjax extends ActionAjaxBase {
    private static final long serialVersionUID = 1L;
    
    
  //METHODS TO DIPSPLAY = LIST
    protected void dataTableAction(JSONObject jsonObj, DataTable dataTable) {
        initDataTable(dataTable);
        try {
            jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataTablePage(sessionInfo, ItemUtil.getDataTableStr(sessionInfo, dataTable)));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected void setInput(String action) {
        ItemDTO item = (ItemDTO) getSessionAttribute(ItemDTO.SESSION_ITEM);
        ItemMediaDTO itemMedia = (ItemMediaDTO) getSessionAttribute(ItemMediaDTO.SESSION_ITEM_MEDIA);
        
        
        int itemCategoryId= getRequestInt("cboItemCategory");
        //System.out.println("Item Category ID: " + itemCategoryId);
        List<DTOBase> itemCategoryList= (List<DTOBase>) getSessionAttribute(ItemCategoryDTO.SESSION_ITEM_CATEGORY_LIST);
        ItemCategoryDTO itemCategory=(ItemCategoryDTO) DTOUtil.getObjById(itemCategoryList, itemCategoryId);
    	System.out.println("itemCategoryId is null  :  " + itemCategory==null  );
    	item.setItemCategory(itemCategory);
        
        item.setName(getRequestString("txtName"));
        item.setDescription(getRequestString("txtDescription"));

        
        int itemUnitId= getRequestInt("cboItemUnit");
    	//System.out.println("Item Category ID: " + itemUnitId);
    	List<DTOBase> itemUnitList= (List<DTOBase>) getSessionAttribute(ItemUnitDTO.SESSION_ITEM_UNIT_LIST);
    	ItemUnitDTO itemUnit=(ItemUnitDTO) DTOUtil.getObjById(itemUnitList, itemUnitId);
    	//System.out.println("itemUnitId is null  :  " + itemUnit==null  );
    	item.setItemUnit(itemUnit);

        item.setUnitPrice(getRequestDouble("txtUnitPrice"));
        item.setQuantity(getRequestDouble("txtQuantity"));
        item.setReorderpoint(getRequestDouble("txtReorderpoint"));
        
        
        itemMedia.setItem(item);
        itemMedia.setMedia(new MediaDTO());
        
        
        
        
    	
    	// Handle File Upload Debugging
    	//System.out.println("DEBUG: Attempting to retrieve uploaded file from session...");

    	UploadedFile uploadedFile = (UploadedFile) getSessionAttribute(UploadedFile.SESSION_UPLOADED_FILE);

    	if (uploadedFile != null) {
    	    //System.out.println("DEBUG: UploadedFile object retrieved -> " + uploadedFile);
    	    //System.out.println("DEBUG: UploadedFile Name: " + uploadedFile.getName());
    	    //System.out.println("DEBUG: UploadedFile Max Size Allowed: " + uploadedFile.getMaxSize());

    	    if (uploadedFile.getFile() != null) {
    	        File file = uploadedFile.getFile();
    	        //System.out.println("DEBUG: Uploaded file path -> " + file.getAbsolutePath());
    	        //System.out.println("DEBUG: Uploaded file size -> " + file.length() + " bytes");

    	        if (file.exists()) {
    	            //System.out.println("DEBUG: File exists on disk.");
    	            item.setPicture(file.getName());
    	        } else {
    	            //System.out.println("ERROR: File does not exist on the specified path! Possible upload failure.");
    	            item.setPicture("");
    	        }
    	    } else {
    	        //System.out.println("ERROR: uploadedFile.getFile() is NULL! Possible missing Apache Commons FileUpload.");
    	        item.setPicture("");
    	    }
    	} else {
    	    //System.out.println("ERROR: UploadedFile is NULL! Ensure Apache Commons FileUpload is installed and configured correctly.");
    	    item.setPicture(""); // Default if no image uploaded
    	}

        
    }

    protected void validateInput(String action) {
    	ItemDTO item = (ItemDTO) getSessionAttribute(ItemDTO.SESSION_ITEM);
        if (action.equalsIgnoreCase(DataTable.ACTION_ADD_SAVE) || action.equalsIgnoreCase(DataTable.ACTION_UPDATE_SAVE)) {
        	        	
        	if(StringUtil.isEmpty(getRequestString("cboItemCategory"))) {
				actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "Category");
			}
   
        	if (StringUtil.isEmpty(getRequestString("txtName"))) {
                actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "Name");
            }
            
        	if (StringUtil.isEmpty(getRequestString("txtDescription"))) {
                actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "Description");
            }
          String unit = getRequestString("txtUnitPrice");
        	System.out.println("UnitPrice VAlidate : " + unit);
        	if(StringUtil.isEmpty(getRequestString("cboItemUnit"))) {
				actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "Unit");
			}
        	
        	if (StringUtil.isEmpty(Double.toString(getRequestDouble("txtUnitPrice")))) {
                actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "UnitPrice");
            }

        	if (StringUtil.isEmpty(Double.toString(getRequestDouble("txtQuantity")))) {
                actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "Quantity");
            }
        	if (StringUtil.isEmpty(Double.toString(getRequestDouble("txtReorderpoint")))) {
                actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "ReorderPoint");
            }
        }
    }

    protected void executeLogic(JSONObject jsonObj, DataTable dataTable, String action) {

        if (action.equalsIgnoreCase(DataTable.ACTION_VIEW)) {
        	
            ItemDTO item = (ItemDTO) dataTable.getSelectedRecord();
            
            try {
    	        jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataViewPage(sessionInfo, ItemUtil.getDataViewStr(sessionInfo, item), "")); //Title and Buttons
    	    } catch (JSONException e) {
    	        e.printStackTrace();
    	    }
        } else if (action.equalsIgnoreCase(DataTable.ACTION_ADD_VIEW)) {
        	UploadedFile uploadedFile = new UploadedFile(); //no need to specify id for the default id is 0
			uploadedFile.setSettings(sessionInfo.getSettings());
			uploadedFile.setValidFileExt(new String[] {"png", "jpg"});
			uploadedFile.setMaxSize(3072000); //3Mb 
			setSessionAttribute(UploadedFile.SESSION_UPLOADED_FILE + "_0", uploadedFile);
        	
        	ItemDTO item = new ItemDTO();
            List<DTOBase> itemCategoryList= (List<DTOBase>) getSessionAttribute(ItemCategoryDTO.SESSION_ITEM_CATEGORY_LIST);
            
            List<DTOBase> itemUnitList= (List<DTOBase>) getSessionAttribute(ItemUnitDTO.SESSION_ITEM_UNIT_LIST);
            
            try {
                //jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataEntryPage(sessionInfo, ItemUtil.getDataEntryStr(sessionInfo, item, itemCategoryList, itemUnitList, uploadedFile), ""));
            	jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataEntryPage(sessionInfo, "Item", ItemUtil.getDataEntryStr(sessionInfo, item, itemCategoryList, itemUnitList,  uploadedFile)));
    			
            } catch (JSONException e) {
                e.printStackTrace();
            }
            setSessionAttribute(ItemDTO.SESSION_ITEM, item);
        
        }  else if (action.equalsIgnoreCase(DataTable.ACTION_ADD_SAVE)) {
            UploadedFile uploadedFile = (UploadedFile) getSessionAttribute(UploadedFile.SESSION_UPLOADED_FILE + "_0");

            ItemDAO itemDAO = new ItemDAO();
            ItemDTO item = (ItemDTO) getSessionAttribute(ItemDTO.SESSION_ITEM);
            item.setAddedBy(sessionInfo.getCurrentUser().getCode());
            itemDAO.executeAdd(item);

            ItemMediaDAO itemMediaDAO = new ItemMediaDAO();
            ItemMediaDTO itemMedia = (ItemMediaDTO) getSessionAttribute(ItemMediaDTO.SESSION_ITEM_MEDIA);

            itemMedia.setFileName(uploadedFile.getFile().getName());

            itemMedia.setAddedBy(sessionInfo.getCurrentUser().getCode());

            actionResponse = (ActionResponse) itemDAO.getResult().get(ActionResponse.SESSION_ACTION_RESPONSE);
            if (StringUtil.isEmpty(actionResponse.getType())) {
                if (uploadedFile.getFile() != null) {
                    File fileFrom = new File(sessionInfo.getSettings().getStaticDir(true) + "/tmp/" + uploadedFile.getFile().getName());
                    File fileTo = new File(sessionInfo.getSettings().getStaticDir(true) + "/" + sessionInfo.getSettings().getCode() + "/media/item/" + uploadedFile.getFile().getName());
                    
                    try {
                        FileUtils.copyFile(fileFrom, fileTo);
                        FileUtil.setFileAccessRights(fileTo);
                        fileFrom.delete();
                    } catch (IOException e) {
                        actionResponse.constructMessage(ActionResponse.TYPE_INFO, "Record was successfully saved but failed to upload the picture.");
                    }
                    
                    itemMedia.setBase64Data(fileTo);  // Set the file as base64 encoded
                    
                 // Print debug values
                    System.out.println("ItemMediaDTO Values:");
                    System.out.println("Item Code: " + itemMedia.getItem().getCode());
                    System.out.println("Media Filename: " + itemMedia.getFileName());
                    System.out.println("Base64: " + itemMedia.getBase64Data());
                    System.out.println("Added By: " + itemMedia.getAddedBy());
                    System.out.println("Updated By: " + itemMedia.getUpdatedBy());

                    itemMediaDAO.executeAdd(itemMedia);

                    actionResponse.constructMessage(ActionResponse.TYPE_SUCCESS, "Added");
                    setSessionAttribute(BannerDTO.SESSION_BANNER_LIST, new BannerDAO().getBannerList());
                }
                actionResponse.constructMessage(ActionResponse.TYPE_SUCCESS, "Added");
                setSessionAttribute(ItemDTO.SESSION_ITEM_LIST, new ItemDAO().getItemList());
            }
            
        }else if (action.equalsIgnoreCase(DataTable.ACTION_UPDATE_VIEW)) {
            ItemDTO itemUpdate = (ItemDTO) dataTable.getSelectedRecord();
            List<DTOBase> itemCategoryListUpdate= (List<DTOBase>) getSessionAttribute(ItemCategoryDTO.SESSION_ITEM_CATEGORY_LIST);
            
            List<DTOBase> itemUnitListUpdate= (List<DTOBase>) getSessionAttribute(ItemUnitDTO.SESSION_ITEM_UNIT_LIST);
            UploadedFile uploadedFileUpdate = (UploadedFile) getSessionAttribute(UploadedFile.SESSION_UPLOADED_FILE + "_0");
            
            
            try {
            	jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataEntryPage(sessionInfo, "Item", ItemUtil.getDataEntryStr(sessionInfo, itemUpdate, itemCategoryListUpdate, itemUnitListUpdate,  uploadedFileUpdate)));
    			
            
            } catch (JSONException e) {
                e.printStackTrace();
            }
            setSessionAttribute(ItemDTO.SESSION_ITEM, itemUpdate);
        }
    	else if (action.equalsIgnoreCase(DataTable.ACTION_UPDATE_SAVE)) {
            ItemDAO itemDAO = new ItemDAO();
            ItemDTO item = (ItemDTO) getSessionAttribute(ItemDTO.SESSION_ITEM);
            item.setUpdatedBy(sessionInfo.getCurrentUser().getCode());
            
            itemDAO.executeUpdate(item);
            actionResponse = (ActionResponse) itemDAO.getResult().get(ActionResponse.SESSION_ACTION_RESPONSE);
            if (StringUtil.isEmpty(actionResponse.getType())) {
                setSessionAttribute(ItemDTO.SESSION_ITEM_LIST, new ItemDAO().getItemList());
                actionResponse.constructMessage(ActionResponse.TYPE_SUCCESS, "Updated");
            }
        
        } else if (action.equalsIgnoreCase(DataTable.ACTION_DELETE)) {
            ItemDAO itemDAO = new ItemDAO();
            ItemDTO item = (ItemDTO) getSessionAttribute(ItemDTO.SESSION_ITEM);
            itemDAO.executeDelete(item);
            
            ItemMediaDAO itemMediaDAO = new ItemMediaDAO();
            ItemMediaDTO itemMedia = (ItemMediaDTO) getSessionAttribute(ItemMediaDTO.SESSION_ITEM_MEDIA);
            itemMediaDAO.executeDelete(itemMedia);
            
            
            actionResponse = (ActionResponse) itemDAO.getResult().get(ActionResponse.SESSION_ACTION_RESPONSE);
            if (StringUtil.isEmpty(actionResponse.getType())) {
                setSessionAttribute(ItemDTO.SESSION_ITEM_LIST, new ItemDAO().getItemList());
                actionResponse.constructMessage(ActionResponse.TYPE_SUCCESS, "Deleted");
            }
        }
        else if (action.equalsIgnoreCase(DataTable.ACTION_DELETE_VIEW)) {
            ItemDTO itemSelected = (ItemDTO) dataTable.getSelectedRecord();
            try {
                jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataViewPage(sessionInfo, ItemUtil.getDataViewStr(sessionInfo, itemSelected), ""));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            setSessionAttribute(ItemDTO.SESSION_ITEM, itemSelected);
        }
    }

    
    protected void search(DataTable dataTable) {
	    String searchValue = dataTable.getSearchValue();
	    String searchCriteria = ItemDTO.ACTION_SEARCH_BY_NAME;
	    //System.out.println("Search Criteria: " + searchCriteria);
	    //System.out.println("Search Value: " + searchValue);
	    
	    if (searchCriteria.equalsIgnoreCase(ItemDTO.ACTION_SEARCH_BY_NAME)) {
	        List<DTOBase> itemList = (List<DTOBase>) getSessionAttribute(ItemDTO.SESSION_ITEM_LIST);
	        ItemUtil.searchByItemName(dataTable, searchValue, itemList);
	    } else if (searchCriteria.equalsIgnoreCase(ItemDTO.ACTION_SEARCH_BY_NAME)) {
	    }
    }                                                                               
    
	protected void initDataTable(DataTable dataTable) {
		List<DTOBase> itemList = (List<DTOBase>) getSessionAttribute(ItemDTO.SESSION_ITEM_LIST);
		List<DTOBase> itemCategoryList = (List<DTOBase>) getSessionAttribute(ItemCategoryDTO.SESSION_ITEM_CATEGORY_LIST);
		List<DTOBase> itemUnitList = (List<DTOBase>) getSessionAttribute(ItemUnitDTO.SESSION_ITEM_UNIT_LIST);
		dataTable.setRecordList(itemList);
		dataTable.setCurrentPageRecordList();
		
		for (DTOBase obj:itemCategoryList) {
			ItemCategoryDTO itemCategory= (ItemCategoryDTO) obj;
//      		System.out.println("itemCategoryId: " + itemCategory.getId());
//      		System.out.println("itemCategoryCode: " + itemCategory.getCode());
//      		System.out.println("itemCategoryName: " + itemCategory.getName());
//      		System.out.println("itemCategorydisplay: " + itemCategory.getDisplayStr());
      	 }
		
		for (int row = 0; row < dataTable.getRecordListCurrentPage().size(); row++) {
            ItemDTO item = (ItemDTO) dataTable.getRecordListCurrentPage().get(row);
            
            //System.out.println(" Inside ITEM itemCategory: " + item.getItemCategory().getCode());
            
            ItemCategoryDTO itemCategory = (ItemCategoryDTO) DTOUtil.getObjByCode(itemCategoryList, item.getItemCategory().getCode());
            item.setItemCategory(itemCategory);
            
            //System.out.println("itemCategory: " + itemCategory);
            
            ItemUnitDTO itemUnit = (ItemUnitDTO) DTOUtil.getObjByCode(itemUnitList, item.getItemUnit().getCode());
            item.setItemUnit(itemUnit);
            //System.out.println("itemUnit: " + itemUnit);
        }
		
		
	}
    }