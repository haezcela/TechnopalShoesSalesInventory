package com.laponhcet.qrcode;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.laponhcet.academicsection.AcademicSectionDTO;
import com.laponhcet.enrollment.EnrollmentDTO;
import com.mytechnopal.ActionResponse;
import com.mytechnopal.DataTable;
import com.mytechnopal.base.ActionAjaxBase;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.link.LinkDTO;
import com.mytechnopal.dto.UserDTO;
import com.mytechnopal.util.DTOUtil;
import com.mytechnopal.util.PageUtil;

public class QRCodeActionAjax extends ActionAjaxBase {
	private static final long serialVersionUID = 1L;
	
	
	protected void customAction(JSONObject jsonObj, String action) {
		if(action.equalsIgnoreCase("ADD QR CODE")) {
			List<DTOBase> userListQRCode = (List<DTOBase>) getSessionAttribute(UserDTO.SESSION_USER_LIST + "_QRCODE"); 
			String userCode = getRequestString("txtUserCode");
			
			if(DTOUtil.getObjByCode(userListQRCode, userCode) == null) {
				List<DTOBase> userList = (List<DTOBase>) getSessionAttribute(UserDTO.SESSION_USER_LIST);
				UserDTO user = (UserDTO) DTOUtil.getObjByCode(userList, userCode);
				user.setRecordStatus(DTOBase.RECORD_STATUS_SELECTED);
				userListQRCode.add(user);
				actionResponse.constructMessage(ActionResponse.TYPE_SUCCESS, "Added");
			}
			else {
				actionResponse.constructMessage(ActionResponse.TYPE_EXIST, "QR Code");
			}
		}
		else if(action.equalsIgnoreCase("CLEAR QR CODE")) {
			setSessionAttribute(UserDTO.SESSION_USER_LIST + "_QRCODE", new ArrayList<DTOBase>());
			actionResponse.constructMessage(ActionResponse.TYPE_SUCCESS, "Cleared");
		}
		
	}
	
	protected void dataTableAction(JSONObject jsonObj, DataTable dataTable) {
		List<DTOBase> enrollmentList = (List<DTOBase>) getSessionAttribute(EnrollmentDTO.SESSION_ENROLLMENT_LIST);
		List<DTOBase> academicSectionList = (List<DTOBase>) getSessionAttribute(AcademicSectionDTO.SESSION_ACADEMIC_SECTION_LIST);
		List<DTOBase> userListQRCode = (List<DTOBase>) getSessionAttribute(UserDTO.SESSION_USER_LIST + "_QRCODE"); 
		initDataTable(dataTable);
		try {
			jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataTablePage(sessionInfo, QRCodeUtil.getDataTableStr(sessionInfo, dataTable, userListQRCode, enrollmentList, academicSectionList)));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	protected void search(DataTable dataTable) {
		if(dataTable.getSearchCriteria().equalsIgnoreCase(UserDTO.ACTION_SEARCH_BY_NAME)) {
			List<DTOBase> userList = (List<DTOBase>) getSessionAttribute(UserDTO.SESSION_USER_LIST);
			QRCodeUtil.searchByName(dataTable, dataTable.getSearchValue(), userList);
		}
	}
	
	protected void initDataTable(DataTable dataTable) {
		List<DTOBase> userList = (List<DTOBase>) getSessionAttribute(UserDTO.SESSION_USER_LIST);
		dataTable.setRecordList(userList);
		dataTable.setCurrentPageRecordList();
	}
}