package com.laponhcet.mediatype;

import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

import com.laponhcet.itemunit.ItemUnitDAO;
import com.laponhcet.itemunit.ItemUnitDTO;
import com.laponhcet.itemunit.ItemUnitUtil;
import com.mytechnopal.ActionResponse;
import com.mytechnopal.DataTable;
import com.mytechnopal.base.ActionAjaxBase;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.link.LinkDTO;
import com.mytechnopal.util.PageUtil;
import com.mytechnopal.util.StringUtil;
//import com.mytechnopal.dao.MediaTypeDAO;
//import com.mytechnopal.dto.MediaTypeDTO;

public class MediaTypeActionAjax extends ActionAjaxBase {
    private static final long serialVersionUID = 1L;

    protected void dataTableAction(JSONObject jsonObj, DataTable dataTable) {
        initDataTable(dataTable);
        try {
            jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataTablePage(sessionInfo, MediaTypeUtil.getDataTableStr(sessionInfo, dataTable)));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected void setInput(String action) {
        MediaTypeDTO mediaType = (MediaTypeDTO) getSessionAttribute(MediaTypeDTO.SESSION_MEDIA_TYPE);
        mediaType.setCode(getRequestString("txtCode"));
        mediaType.setName(getRequestString("txtName"));
    }

//    protected void validateInput(String action) {
//        List<DTOBase> mediaTypeList = (List<DTOBase>) getSessionAttribute(MediaTypeDTO.SESSION_MEDIA_TYPE_LIST);
//        MediaTypeDTO mediaType = (MediaTypeDTO) getSessionAttribute(MediaTypeDTO.SESSION_MEDIA_TYPE);
//
//        if (action.equalsIgnoreCase(DataTable.ACTION_ADD_SAVE) || action.equalsIgnoreCase(DataTable.ACTION_UPDATE_SAVE)) {
//            for (DTOBase obj : mediaTypeList) {
//                MediaTypeDTO existingMediaType = (MediaTypeDTO) obj;
//               
//                if (mediaType.getCode().equalsIgnoreCase(existingMediaType.getCode())) {
//                    actionResponse.constructMessage(ActionResponse.TYPE_EXIST, "Code");
//                }
//                else if (mediaType.getName().equalsIgnoreCase(existingMediaType.getName())) {
//                    actionResponse.constructMessage(ActionResponse.TYPE_EXIST, "Media Type Name");
//                    return;
//                }
//            }
//            if (StringUtil.isEmpty(mediaType.getCode())) {
//                actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "Code");
//            }
//            else if (StringUtil.isEmpty(mediaType.getName())) {
//                actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "Media Type Name");
//            }
//        }
//    }
//
//    protected void executeLogic(JSONObject jsonObj, DataTable dataTable, String action) {
//        if (action.equalsIgnoreCase(DataTable.ACTION_ADD_VIEW)) {
//            MediaTypeDTO mediaType = new MediaTypeDTO();
//            try {
//                jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataEntryPage(sessionInfo, MediaTypeUtil.getDataEntryStr(sessionInfo, mediaType),""));
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            setSessionAttribute(MediaTypeDTO.SESSION_MEDIA_TYPE, mediaType);
//        }
//        else if (action.equalsIgnoreCase(DataTable.ACTION_ADD_SAVE)) {
//            MediaTypeDAO mediaTypeDAO = new MediaTypeDAO();
//            MediaTypeDTO mediaType = (MediaTypeDTO) getSessionAttribute(MediaTypeDTO.SESSION_MEDIA_TYPE);
//            mediaType.setAddedBy(sessionInfo.getCurrentUser().getCode());
//            mediaTypeDAO.executeAdd(mediaType);
//            actionResponse = (ActionResponse) mediaTypeDAO.getResult().get(ActionResponse.SESSION_ACTION_RESPONSE);
//            if (StringUtil.isEmpty(actionResponse.getType())) {
//                actionResponse.constructMessage(ActionResponse.TYPE_SUCCESS, "Added");
//                setSessionAttribute(MediaTypeDTO.SESSION_MEDIA_TYPE_LIST, mediaTypeDAO.getMediaTypeList());
//            }
//        }
//        else if (action.equalsIgnoreCase(DataTable.ACTION_UPDATE_VIEW)) {
//            MediaTypeDTO mediaType = (MediaTypeDTO) dataTable.getSelectedRecord();
//            try {
//                jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataEntryPage(sessionInfo, MediaTypeUtil.getDataEntryStr(sessionInfo, mediaType),""));
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            setSessionAttribute(MediaTypeDTO.SESSION_MEDIA_TYPE, mediaType);
//        }
//        else if (action.equalsIgnoreCase(DataTable.ACTION_UPDATE_SAVE)) {
//            MediaTypeDAO mediaTypeDAO = new MediaTypeDAO();
//            MediaTypeDTO mediaType = (MediaTypeDTO) getSessionAttribute(MediaTypeDTO.SESSION_MEDIA_TYPE);
//            mediaType.setUpdatedBy(sessionInfo.getCurrentUser().getCode());
//            mediaTypeDAO.executeUpdate(mediaType);
//            actionResponse = (ActionResponse) mediaTypeDAO.getResult().get(ActionResponse.SESSION_ACTION_RESPONSE);
//            if (StringUtil.isEmpty(actionResponse.getType())) {
//                actionResponse.constructMessage(ActionResponse.TYPE_SUCCESS, "Updated");
//                setSessionAttribute(MediaTypeDTO.SESSION_MEDIA_TYPE_LIST, mediaTypeDAO.getMediaTypeList());
//            }
//        }
//        else if (action.equalsIgnoreCase(DataTable.ACTION_DELETE)) {
//            MediaTypeDAO mediaTypeDAO = new MediaTypeDAO();
//            MediaTypeDTO mediaType = (MediaTypeDTO) getSessionAttribute(MediaTypeDTO.SESSION_MEDIA_TYPE);
//            mediaTypeDAO.executeDelete(mediaType);
//            actionResponse = (ActionResponse) mediaTypeDAO.getResult().get(ActionResponse.SESSION_ACTION_RESPONSE);
//            if (StringUtil.isEmpty(actionResponse.getType())) {
//                actionResponse.constructMessage(ActionResponse.TYPE_SUCCESS, "Deleted");
//                setSessionAttribute(MediaTypeDTO.SESSION_MEDIA_TYPE_LIST, mediaTypeDAO.getMediaTypeList());
//            }
//        }
//    }
    
    protected void validateInput(String action) {
        MediaTypeDTO mediaType = (MediaTypeDTO) getSessionAttribute(MediaTypeDTO.SESSION_MEDIA_TYPE);
        List<DTOBase> mediaTypeList = (List<DTOBase>) getSessionAttribute(MediaTypeDTO.SESSION_MEDIA_TYPE_LIST);
        if (action.equalsIgnoreCase(DataTable.ACTION_ADD_SAVE) || action.equalsIgnoreCase(DataTable.ACTION_UPDATE_SAVE)) {
            
        	if (StringUtil.isEmpty(mediaType.getCode())) {
                actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "Code");
            } 
            else if (StringUtil.isEmpty(mediaType.getName())) {
                actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "Name");
            } 
            else {
                if (!isCodeUnique(mediaType.getCode(), mediaType.getId(), mediaTypeList)) {
                    actionResponse.constructMessage(ActionResponse.TYPE_EXIST, "Code");
                } 
                else if (!isNameUnique(mediaType.getName(), mediaType.getId(), mediaTypeList)) {
                    actionResponse.constructMessage(ActionResponse.TYPE_EXIST, "Name");
                }
            }
        }
    }

    private boolean isCodeUnique(String code, int currentId, List<DTOBase> itemUnitList) {
        for (DTOBase dto : itemUnitList) {
        	MediaTypeDTO existingMediaTypeCode = (MediaTypeDTO ) dto;
            if (existingMediaTypeCode.getId() == currentId) {
                continue;
            }
            if (existingMediaTypeCode.getCode().equalsIgnoreCase(code)) {
                return false;
            }
        }
        return true;
    }

    private boolean isNameUnique(String name, int currentId, List<DTOBase> itemUnitList) {
        for (DTOBase dto : itemUnitList) {
        	MediaTypeDTO  existingMediaTypeName = (MediaTypeDTO ) dto;
            if (existingMediaTypeName.getId() == currentId) {
                continue;
            }
            if (existingMediaTypeName.getName().equalsIgnoreCase(name)) {
                return false;
            }
        }
        return true;
    }

    protected void executeLogic(JSONObject jsonObj, DataTable dataTable, String action) {
    if (action.equalsIgnoreCase(DataTable.ACTION_ADD_SAVE) || action.equalsIgnoreCase(DataTable.ACTION_UPDATE_SAVE)) {
    	
    	validateInput(action);
    	
    	if (actionResponse.getMessageStr() != null && !actionResponse.getMessageStr().isEmpty()) {
            System.out.println("Validation errors found. Aborting save operation."); // Debug log
            return; 
        }
    	
    	MediaTypeDAO mediaTypeDAO = new MediaTypeDAO();
    	MediaTypeDTO mediaType = (MediaTypeDTO) getSessionAttribute(MediaTypeDTO.SESSION_MEDIA_TYPE);
        
        if (action.equalsIgnoreCase(DataTable.ACTION_ADD_SAVE)) {
        	mediaType.setAddedBy(sessionInfo.getCurrentUser().getCode());
        	mediaTypeDAO.executeAdd(mediaType);
            
            actionResponse = (ActionResponse) mediaTypeDAO.getResult().get(ActionResponse.SESSION_ACTION_RESPONSE);
            if (actionResponse.getMessageStr() == null || actionResponse.getMessageStr().isEmpty()) {
                setSessionAttribute(MediaTypeDTO.SESSION_MEDIA_TYPE_LIST, mediaTypeDAO.getMediaTypeList());
                actionResponse.constructMessage(ActionResponse.TYPE_SUCCESS, "Added");
            }
        }
        else if (action.equalsIgnoreCase(DataTable.ACTION_UPDATE_SAVE)) {
        	mediaType.setUpdatedBy(sessionInfo.getCurrentUser().getCode());
        	mediaTypeDAO.executeUpdate(mediaType);
        
            actionResponse = (ActionResponse) mediaTypeDAO.getResult().get(ActionResponse.SESSION_ACTION_RESPONSE);
            if (actionResponse.getMessageStr() == null || actionResponse.getMessageStr().isEmpty()) {
                setSessionAttribute(MediaTypeDTO.SESSION_MEDIA_TYPE_LIST, mediaTypeDAO.getMediaTypeList());
                actionResponse.constructMessage(ActionResponse.TYPE_SUCCESS, "Updated");
            }
            
        	} 
        
    	}
        else if (action.equalsIgnoreCase(DataTable.ACTION_VIEW)) {
        	MediaTypeDTO mediaType = (MediaTypeDTO) dataTable.getSelectedRecord();
        	try {
                jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataViewPage(sessionInfo, MediaTypeUtil.getDataViewStr(sessionInfo, mediaType), ""));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } 
        else if (action.equalsIgnoreCase(DataTable.ACTION_ADD_VIEW)) {
          MediaTypeDTO mediaType = new MediaTypeDTO();
          try {
              jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataEntryPage(sessionInfo, MediaTypeUtil.getDataEntryStr(sessionInfo, mediaType),""));
          } catch (JSONException e) {
              e.printStackTrace();
          }
          setSessionAttribute(MediaTypeDTO.SESSION_MEDIA_TYPE, mediaType);
      }
        else if (action.equalsIgnoreCase(DataTable.ACTION_UPDATE_VIEW)) {
        	MediaTypeDTO mediaType = (MediaTypeDTO) dataTable.getSelectedRecord();
            List<DTOBase> mediaTypetList = (List<DTOBase>) getSessionAttribute(MediaTypeDTO.SESSION_MEDIA_TYPE_LIST);
            try {
                jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataEntryPage(sessionInfo, MediaTypeUtil.getDataEntryStr(sessionInfo, mediaType),""));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            setSessionAttribute(MediaTypeDTO.SESSION_MEDIA_TYPE, mediaType);
        } 
        else if (action.equalsIgnoreCase(DataTable.ACTION_DELETE_VIEW)) {
        	MediaTypeDTO mediaTypeSelected = (MediaTypeDTO) dataTable.getSelectedRecord();
            try {
                jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataViewPage(sessionInfo, MediaTypeUtil.getDataViewStr(sessionInfo, mediaTypeSelected),""));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            setSessionAttribute(MediaTypeDTO.SESSION_MEDIA_TYPE, mediaTypeSelected);
        } 
        else if (action.equalsIgnoreCase(DataTable.ACTION_DELETE)) {
        	MediaTypeDAO mediaTypeDAO = new MediaTypeDAO();
        	MediaTypeDTO mediaType = (MediaTypeDTO) getSessionAttribute(MediaTypeDTO.SESSION_MEDIA_TYPE);
        	mediaTypeDAO.executeDelete(mediaType);
            actionResponse = (ActionResponse) mediaTypeDAO.getResult().get(ActionResponse.SESSION_ACTION_RESPONSE);
            if (actionResponse.getMessageStr() == null || actionResponse.getMessageStr().isEmpty()) {
                setSessionAttribute(MediaTypeDTO.SESSION_MEDIA_TYPE_LIST, mediaTypeDAO.getMediaTypeList());
                actionResponse.constructMessage(ActionResponse.TYPE_SUCCESS, "Deleted");
            }
        }
    
    }

    protected void initDataTable(DataTable dataTable) {
        List<DTOBase> mediaTypeList = (List<DTOBase>) getSessionAttribute(MediaTypeDTO.SESSION_MEDIA_TYPE_LIST);
        dataTable.setRecordList(mediaTypeList);
        dataTable.setCurrentPageRecordList();
    }

    protected void search(DataTable dataTable) {
        String searchValue = dataTable.getSearchValue();
        List<DTOBase> mediaTypeList = (List<DTOBase>) getSessionAttribute(MediaTypeDTO.SESSION_MEDIA_TYPE_LIST);
        MediaTypeUtil.searchByName(dataTable, searchValue, mediaTypeList);
    }
}
