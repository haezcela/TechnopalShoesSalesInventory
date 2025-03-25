package com.mytechnopal.banner;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.mytechnopal.ActionResponse;
import com.mytechnopal.DataTable;
import com.mytechnopal.UploadedFile;
import com.mytechnopal.base.ActionAjaxBase;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.link.LinkDTO;
import com.mytechnopal.util.FileUtil;
import com.mytechnopal.util.PageUtil;
import com.mytechnopal.util.StringUtil;

public class BannerActionAjax extends ActionAjaxBase {
	private static final long serialVersionUID = 1L;

	protected void setInput(String action) {
		BannerDTO banner = (BannerDTO) getSessionAttribute(BannerDTO.SESSION_BANNER);
		banner.setLabel(getRequestString("txtLabel"));
		banner.setDescription(getRequestString("txtDescription"));
	}
	
	protected void validateInput(String action) {
		if(action.equalsIgnoreCase(DataTable.ACTION_ADD_SAVE) || action.equalsIgnoreCase(DataTable.ACTION_UPDATE_SAVE)) {
			UploadedFile uploadedFile = (UploadedFile) getSessionAttribute(UploadedFile.SESSION_UPLOADED_FILE + "_0");
			if(uploadedFile.getFile() == null) {
				actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "File");
			}
		}
	}
	
	protected void executeLogic(JSONObject jsonObj, DataTable dataTable, String action) {
		if(action.equalsIgnoreCase(DataTable.ACTION_VIEW)) {
			BannerDTO bannerSelected = (BannerDTO) dataTable.getSelectedRecord();
			try {
				jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataViewPage(sessionInfo, "Banner", BannerUtil.getDataViewStr(sessionInfo, bannerSelected)));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		else if(action.equalsIgnoreCase(DataTable.ACTION_ADD_VIEW)) {
			UploadedFile uploadedFile = new UploadedFile(); //no need to specify id for the default id is 0
			uploadedFile.setSettings(sessionInfo.getSettings());
			uploadedFile.setValidFileExt(new String[] {"png", "jpg"});
			uploadedFile.setMaxSize(3072000); //3Mb 
			setSessionAttribute(UploadedFile.SESSION_UPLOADED_FILE + "_0", uploadedFile);
			
			BannerDTO banner = new BannerDTO();
			try {
				jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataEntryPage(sessionInfo, "Banner", BannerUtil.getDataEntryStr(sessionInfo, banner, uploadedFile)));
			} catch (JSONException e) {
				e.printStackTrace();
			}
			setSessionAttribute(BannerDTO.SESSION_BANNER, banner);
		}
		else if(action.equalsIgnoreCase(DataTable.ACTION_DELETE_VIEW)) {
			BannerDTO bannerSelected = (BannerDTO) dataTable.getSelectedRecord();
			try {
				jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataViewPage(sessionInfo, "Banner", BannerUtil.getDataViewStr(sessionInfo, bannerSelected)));
			} catch (JSONException e) {
				e.printStackTrace();
			}
			setSessionAttribute(BannerDTO.SESSION_BANNER, bannerSelected);
		}
		else  if(action.equalsIgnoreCase(DataTable.ACTION_ADD_SAVE)) {
			UploadedFile uploadedFile= (UploadedFile) getSessionAttribute(UploadedFile.SESSION_UPLOADED_FILE + "_0");
			BannerDAO bannerDAO = new BannerDAO();
			BannerDTO banner = (BannerDTO) getSessionAttribute(BannerDTO.SESSION_BANNER);
			banner.setFilename(uploadedFile.getFile().getName());
			banner.setAddedBy(sessionInfo.getCurrentUser().getCode());
			bannerDAO.executeAdd(banner);
			actionResponse = (ActionResponse) bannerDAO.getResult().get(ActionResponse.SESSION_ACTION_RESPONSE);
			if(StringUtil.isEmpty(actionResponse.getType())) {
				if(uploadedFile.getFile() != null) {
					File fileFrom = new File(sessionInfo.getSettings().getStaticDir(true) + "/tmp/" + uploadedFile.getFile().getName());
					File fileTo = new File(sessionInfo.getSettings().getStaticDir(true) + "/" + sessionInfo.getSettings().getCode() + "/media/banner/" + uploadedFile.getFile().getName());
					try {
						FileUtils.copyFile(fileFrom, fileTo);
						FileUtil.setFileAccessRights(fileTo);
						fileFrom.delete(); 
					} catch (IOException e) {
						// TODO Auto-generated catch block
						actionResponse.constructMessage(ActionResponse.TYPE_INFO, "Record was successfully saved but failed  to upload the picture.");
					}
					
					actionResponse.constructMessage(ActionResponse.TYPE_SUCCESS, "Added");
					setSessionAttribute(BannerDTO.SESSION_BANNER_LIST, new BannerDAO().getBannerList());
				}

			}
		}
		else  if(action.equalsIgnoreCase(DataTable.ACTION_DELETE)) {
			BannerDAO bannerDAO = new BannerDAO();
			BannerDTO banner = (BannerDTO) getSessionAttribute(BannerDTO.SESSION_BANNER);
			bannerDAO.executeDelete(banner);
			actionResponse = (ActionResponse) bannerDAO.getResult().get(ActionResponse.SESSION_ACTION_RESPONSE);
			if(StringUtil.isEmpty(actionResponse.getType())) {
				setSessionAttribute(BannerDTO.SESSION_BANNER_LIST, new BannerDAO().getBannerList());
				File file = new File(sessionInfo.getSettings().getStaticDir(true) + "/" + sessionInfo.getSettings().getCode() + "/media/banner/" + banner.getFilename());
				if(file.delete()) {
					actionResponse.constructMessage(ActionResponse.TYPE_SUCCESS, "Deleted");
				}
				else {
					actionResponse.constructMessage(ActionResponse.TYPE_SUCCESS, "Deleted a Record but not the File. Please report it to TechnoPal");
				}
			}
		}
	}
	
	protected void dataTableAction(JSONObject jsonObj, DataTable dataTable) {
		initDataTable(dataTable);
		try {
			jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataTablePage(sessionInfo, BannerUtil.getDataTableStr(sessionInfo, dataTable)));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	protected void initDataTable(DataTable dataTable) {
		List<DTOBase> bannerList = (List<DTOBase>) getSessionAttribute(BannerDTO.SESSION_BANNER_LIST);
		dataTable.setRecordList(bannerList);
		dataTable.setCurrentPageRecordList();
	}
}
