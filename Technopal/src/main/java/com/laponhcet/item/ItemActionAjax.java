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
        
        String generatedCodeItem = new ItemDAO().getGeneratedCode("ITEM_LAST");
        item.setCode(generatedCodeItem);

        int itemCategoryId= getRequestInt("cboItemCategory");
        //Item Category List
        List<DTOBase> itemCategoryList= (List<DTOBase>) getSessionAttribute(ItemCategoryDTO.SESSION_ITEM_CATEGORY_LIST);
        ItemCategoryDTO itemCategory=(ItemCategoryDTO) DTOUtil.getObjById(itemCategoryList, itemCategoryId);
    	item.setItemCategory(itemCategory);
        
        item.setName(getRequestString("txtName"));
        item.setDescription(getRequestString("txtDescription"));

        //Item Unit List
        int itemUnitId= getRequestInt("cboItemUnit");
    	List<DTOBase> itemUnitList= (List<DTOBase>) getSessionAttribute(ItemUnitDTO.SESSION_ITEM_UNIT_LIST);
    	ItemUnitDTO itemUnit=(ItemUnitDTO) DTOUtil.getObjById(itemUnitList, itemUnitId);
    	item.setItemUnit(itemUnit);

        item.setUnitPrice(getRequestDouble("txtUnitPrice"));
        item.setQuantity(getRequestDouble("txtQuantity"));
        item.setReorderpoint(getRequestDouble("txtReorderpoint"));
        


//        // Build final saved path (after move)
//        File finalPath = new File(sessionInfo.getSettings().getStaticDir(true) + "/" + sessionInfo.getSettings().getCode() + "/media/item/" + file.getName());
//
//        System.out.println("Trying to read final path: " + finalPath.getAbsolutePath());
//
//            item.getItemMedia().setBase64Data(finalPath.getAbsolutePath());
//            System.out.println("✅ Base64 successfully set.");
//            System.out.println("Base64 Preview: " + item.getItemMedia().getBase64Data().substring(0, 50));
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
        
        } else if (action.equalsIgnoreCase(DataTable.ACTION_ADD_SAVE)) {
            ItemDAO itemDAO = new ItemDAO();
            ItemDTO item = (ItemDTO) getSessionAttribute(ItemDTO.SESSION_ITEM);
            
        	UploadedFile uploadedFile = (UploadedFile) getSessionAttribute(UploadedFile.SESSION_UPLOADED_FILE + "_0");
            File file = uploadedFile.getFile();

            System.out.println("DEBUG: Uploaded file absolute path: " + file.getAbsolutePath());
            item.getItemMedia().setFileName(file.getName());
            System.out.println("FILENAME: " + item.getItemMedia().getFileName());
            
            // Save to database
            itemDAO.executeAdd(item);
            actionResponse = (ActionResponse) itemDAO.getResult().get(ActionResponse.SESSION_ACTION_RESPONSE);

            // File system move and response
            if (StringUtil.isEmpty(actionResponse.getType())) {
                if (uploadedFile != null && uploadedFile.getFile() != null) {
                    File fileFrom = new File(sessionInfo.getSettings().getStaticDir(true) + "/tmp/" + uploadedFile.getFile().getName());
                    File fileTo = new File(sessionInfo.getSettings().getStaticDir(true) + "/" + sessionInfo.getSettings().getCode() + "/media/item/" + uploadedFile.getFile().getName());

                    try {
                        FileUtils.copyFile(fileFrom, fileTo);
                        FileUtil.setFileAccessRights(fileTo);
                        fileFrom.delete();
                    } catch (IOException e) {
                        actionResponse.constructMessage(ActionResponse.TYPE_INFO, "Record was saved but image upload failed.");
                    }
                }
                // Final response + refresh
                actionResponse.constructMessage(ActionResponse.TYPE_SUCCESS, "Added");
                setSessionAttribute(BannerDTO.SESSION_BANNER_LIST, new BannerDAO().getBannerList());
                setSessionAttribute(ItemDTO.SESSION_ITEM_LIST, new ItemDAO().getItemList());
            }
        }
        else if (action.equalsIgnoreCase(DataTable.ACTION_UPDATE_VIEW)) {
            ItemDTO itemUpdate = (ItemDTO) dataTable.getSelectedRecord();
            List<DTOBase> itemCategoryListUpdate = (List<DTOBase>) getSessionAttribute(ItemCategoryDTO.SESSION_ITEM_CATEGORY_LIST);
            List<DTOBase> itemUnitListUpdate = (List<DTOBase>) getSessionAttribute(ItemUnitDTO.SESSION_ITEM_UNIT_LIST);
            UploadedFile uploadedFileUpdate = (UploadedFile) getSessionAttribute(UploadedFile.SESSION_UPLOADED_FILE + "_0");

            // Setup file upload settings
            uploadedFileUpdate.setSettings(sessionInfo.getSettings());
            uploadedFileUpdate.setValidFileExt(new String[] {"png", "jpg"});
            uploadedFileUpdate.setMaxSize(3072000); // 3MB
            setSessionAttribute(UploadedFile.SESSION_UPLOADED_FILE + "_0", uploadedFileUpdate);


         // 💡 Make sure to manually attach the media
         ItemMediaDAO itemMediaDAO = new ItemMediaDAO();
         ItemMediaDTO itemMedia = itemMediaDAO.getByItemCode(itemUpdate.getCode());
         itemUpdate.setItemMedia(itemMedia);

         // Now store the filename to session
         if (itemMedia != null && itemMedia.getFileName() != null) {
             setSessionAttribute("SESSION_OLD_IMAGE_FILENAME", itemMedia.getFileName());
             System.out.println("ACTION_UPDATE_VIEW ✅ Stored old image filename: " + itemMedia.getFileName());
         } else {
             System.out.println("ACTION_UPDATE_VIEW ⚠️ No item media or filename found.");
         }


            // Generate data entry UI
            try {
                jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataEntryPage(
                    sessionInfo, "Item", 
                    ItemUtil.getDataEntryStr(sessionInfo, itemUpdate, itemCategoryListUpdate, itemUnitListUpdate, uploadedFileUpdate)
                ));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            setSessionAttribute(ItemDTO.SESSION_ITEM, itemUpdate);
        }

else if (action.equalsIgnoreCase(DataTable.ACTION_UPDATE_SAVE)) {
    UploadedFile uploadedFile = (UploadedFile) getSessionAttribute(UploadedFile.SESSION_UPLOADED_FILE + "_0");

    ItemDAO itemDAO = new ItemDAO();
    ItemDTO item = (ItemDTO) getSessionAttribute(ItemDTO.SESSION_ITEM);

    
    item.setUpdatedBy(sessionInfo.getCurrentUser().getCode());
    
    File file = uploadedFile.getFile();

    System.out.println("ACTION_UPDATE_SAVE DEBUG: Uploaded file TMP path: " + file.getAbsolutePath());
    item.getItemMedia().setFileName(file.getName());
    System.out.println("NEW UPLOAD FILENAME: " + item.getItemMedia().getFileName());
    
    itemDAO.executeUpdate(item);
    

    // ✅ Get the old image filename stored during ACTION_UPDATE_VIEW
       String oldImageFileName = (String) getSessionAttribute("SESSION_OLD_IMAGE_FILENAME");
       System.out.println("🔁 Retrieved old image filename from session: " + oldImageFileName);

       if (oldImageFileName != null && !oldImageFileName.isEmpty()) {
           String imagePath = sessionInfo.getSettings().getStaticDir(true)
               .replace("/", "\\\\") + "\\" + sessionInfo.getSettings().getCode()
               + "\\media\\item\\" + oldImageFileName;

           System.out.println("Image Path (old file): " + imagePath);

           File imageFile = new File(imagePath);
           if (imageFile.exists()) {
               if (imageFile.delete()) {
                   System.out.println("✅ Old image file deleted: " + imagePath);
               } else {
                   System.err.println("❌ Failed to delete old image file: " + imagePath);
               }
           } else {
               System.out.println("⚠️ Old image file not found: " + imagePath);
           }
       } else {
           System.out.println("⚠️ No old image filename stored in session.");
       }


       // ADD NEW IMAGE
       if (uploadedFile != null && uploadedFile.getFile() != null) {
           File fileFrom = new File(sessionInfo.getSettings().getStaticDir(true) + "/tmp/" + uploadedFile.getFile().getName());
           File fileTo = new File(sessionInfo.getSettings().getStaticDir(true) + "/" + sessionInfo.getSettings().getCode() + "/media/item/" + uploadedFile.getFile().getName());

           System.out.println("=== DEBUG: Uploading New Image ===");
           System.out.println("From: " + fileFrom.getAbsolutePath());
           System.out.println("To: " + fileTo.getAbsolutePath());

           try {
               FileUtils.copyFile(fileFrom, fileTo);
               FileUtil.setFileAccessRights(fileTo);
               if (fileFrom.delete()) {
               }
           } catch (IOException e) {
               e.printStackTrace();
               actionResponse.constructMessage(ActionResponse.TYPE_INFO, "Record was saved but image upload failed.");
           }
       } else {
           System.out.println("⚠️ No new uploaded file to process.");
       }
    
    if (StringUtil.isEmpty(actionResponse.getType())) {
    actionResponse = (ActionResponse) itemDAO.getResult().get(ActionResponse.SESSION_ACTION_RESPONSE);

        // Final response + refresh
        setSessionAttribute(ItemDTO.SESSION_ITEM_LIST, new ItemDAO().getItemList());
        actionResponse.constructMessage(ActionResponse.TYPE_SUCCESS, "Updated");
    } else {
        System.err.println("❌ ActionResponse Type: " + actionResponse.getType());
    }


        }else if (action.equalsIgnoreCase(DataTable.ACTION_DELETE)) {
        	ItemDAO itemDAO = new ItemDAO();
        	ItemDTO item = (ItemDTO) getSessionAttribute(ItemDTO.SESSION_ITEM);
        	
        	    ItemMediaDAO itemMediaDAO = new ItemMediaDAO();
        	    ItemMediaDTO itemMedia = itemMediaDAO.getByItemCode(item.getCode()); 
        	    if (item.getItemMedia() != null) {
        	        System.out.println("Media Filename: " + itemMedia.getFileName());
        	        }

            //Delete the image file from directory
            String imagePath = sessionInfo.getSettings().getStaticDir(true)
                    .replace("/", "\\\\") + "\\" + sessionInfo.getSettings().getCode()
                    + "\\media\\item\\" + itemMedia.getFileName();

            System.out.println("Image Path: " + imagePath);

            File imageFile = new File(imagePath);
            System.out.println("⚠️ DELETE FILE");

            if (imageFile.exists()) {
                if (imageFile.delete()) {
                    System.out.println("✅ Image file deleted: " + imagePath);
                    // ✅ Always delete item from database
                    itemDAO.executeDelete(item);
                } else {
                    System.err.println("❌ Failed to delete image file: " + imagePath);
                }
            } else {
                System.out.println("⚠️ File not found: " + imagePath);
            }

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