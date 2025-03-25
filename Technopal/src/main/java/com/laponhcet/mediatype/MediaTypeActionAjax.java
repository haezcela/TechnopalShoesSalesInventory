package com.laponhcet.mediatype;

import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import com.mytechnopal.ActionResponse;
import com.mytechnopal.DataTable;
import com.mytechnopal.base.ActionAjaxBase;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.link.LinkDTO;
import com.mytechnopal.util.PageUtil;
import com.mytechnopal.util.StringUtil;

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

    protected void validateInput(String action) {
        List<DTOBase> mediaTypeList = (List<DTOBase>) getSessionAttribute(MediaTypeDTO.SESSION_MEDIA_TYPE_LIST);
        MediaTypeDTO mediaType = (MediaTypeDTO) getSessionAttribute(MediaTypeDTO.SESSION_MEDIA_TYPE);

        if (action.equalsIgnoreCase(DataTable.ACTION_ADD_SAVE) || action.equalsIgnoreCase(DataTable.ACTION_UPDATE_SAVE)) {
            for (DTOBase obj : mediaTypeList) {
                MediaTypeDTO existingType = (MediaTypeDTO) obj;
                if (mediaType.getName().equalsIgnoreCase(existingType.getName())) {
                    actionResponse.constructMessage(ActionResponse.TYPE_EXIST, "Media Type Name");
                    return;
                }
            }
            if (StringUtil.isEmpty(mediaType.getName())) {
                actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "Media Type Name");
            }
        }
    }

    protected void executeLogic(JSONObject jsonObj, DataTable dataTable, String action) {
        if (action.equalsIgnoreCase(DataTable.ACTION_ADD_VIEW)) {
            MediaTypeDTO mediaType = new MediaTypeDTO();
            try {
                jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataEntryPage(sessionInfo, MediaTypeUtil.getDataEntryStr(sessionInfo, mediaType),""));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            setSessionAttribute(MediaTypeDTO.SESSION_MEDIA_TYPE, mediaType);
        }
        else if (action.equalsIgnoreCase(DataTable.ACTION_ADD_SAVE)) {
            MediaTypeDAO mediaTypeDAO = new MediaTypeDAO();
            MediaTypeDTO mediaType = (MediaTypeDTO) getSessionAttribute(MediaTypeDTO.SESSION_MEDIA_TYPE);
            mediaType.setAddedBy(sessionInfo.getCurrentUser().getCode());
            mediaTypeDAO.executeAdd(mediaType);
            actionResponse = (ActionResponse) mediaTypeDAO.getResult().get(ActionResponse.SESSION_ACTION_RESPONSE);
            if (StringUtil.isEmpty(actionResponse.getType())) {
                actionResponse.constructMessage(ActionResponse.TYPE_SUCCESS, "Added");
                setSessionAttribute(MediaTypeDTO.SESSION_MEDIA_TYPE_LIST, mediaTypeDAO.getMediaTypeList());
            }
        }
        else if (action.equalsIgnoreCase(DataTable.ACTION_UPDATE_VIEW)) {
            MediaTypeDTO mediaType = (MediaTypeDTO) dataTable.getSelectedRecord();
            try {
                jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataEntryPage(sessionInfo, MediaTypeUtil.getDataEntryStr(sessionInfo, mediaType),""));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            setSessionAttribute(MediaTypeDTO.SESSION_MEDIA_TYPE, mediaType);
        }
        else if (action.equalsIgnoreCase(DataTable.ACTION_UPDATE_SAVE)) {
            MediaTypeDAO mediaTypeDAO = new MediaTypeDAO();
            MediaTypeDTO mediaType = (MediaTypeDTO) getSessionAttribute(MediaTypeDTO.SESSION_MEDIA_TYPE);
            mediaType.setUpdatedBy(sessionInfo.getCurrentUser().getCode());
            mediaTypeDAO.executeUpdate(mediaType);
            actionResponse = (ActionResponse) mediaTypeDAO.getResult().get(ActionResponse.SESSION_ACTION_RESPONSE);
            if (StringUtil.isEmpty(actionResponse.getType())) {
                actionResponse.constructMessage(ActionResponse.TYPE_SUCCESS, "Updated");
                setSessionAttribute(MediaTypeDTO.SESSION_MEDIA_TYPE_LIST, mediaTypeDAO.getMediaTypeList());
            }
        }
        else if (action.equalsIgnoreCase(DataTable.ACTION_DELETE)) {
            MediaTypeDAO mediaTypeDAO = new MediaTypeDAO();
            MediaTypeDTO mediaType = (MediaTypeDTO) getSessionAttribute(MediaTypeDTO.SESSION_MEDIA_TYPE);
            mediaTypeDAO.executeDelete(mediaType);
            actionResponse = (ActionResponse) mediaTypeDAO.getResult().get(ActionResponse.SESSION_ACTION_RESPONSE);
            if (StringUtil.isEmpty(actionResponse.getType())) {
                actionResponse.constructMessage(ActionResponse.TYPE_SUCCESS, "Deleted");
                setSessionAttribute(MediaTypeDTO.SESSION_MEDIA_TYPE_LIST, mediaTypeDAO.getMediaTypeList());
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
