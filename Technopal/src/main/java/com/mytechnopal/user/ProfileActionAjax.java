package com.mytechnopal.user;

import org.json.JSONException;
import org.json.JSONObject;

import com.mytechnopal.ActionResponse;
import com.mytechnopal.DataTable;
import com.mytechnopal.base.ActionAjaxBase;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.dao.UserDAO;
import com.mytechnopal.dto.UserDTO;
import com.mytechnopal.link.LinkDTO;
import com.mytechnopal.usercontact.UserContactDTO;
import com.mytechnopal.usercontact.UserContactUtil;
import com.mytechnopal.util.DateTimeUtil;
import com.mytechnopal.util.StringUtil;
import com.mytechnopal.util.UserUtil;

public class ProfileActionAjax extends ActionAjaxBase {
	private static final long serialVersionUID = 1L;

	protected void executeLogic(JSONObject jsonObj, DataTable dataTable, String action) {	
		if(action.equalsIgnoreCase(DataTable.ACTION_UPDATE_VIEW)) {
			try {
				jsonObj.put(LinkDTO.PAGE_CONTENT, ProfileUtil.getDataEntryStr(sessionInfo));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		else if(action.equalsIgnoreCase(DataTable.ACTION_UPDATE_SAVE)) {
			UserDAO userDAO = new UserDAO();
			UserDTO user = sessionInfo.getCurrentUser();
			String userName = getRequestString("txtUserName");
			String password = getRequestString("txtPassword");
			String cpNumber = getRequestString("txtCPNumber");
			
			if(StringUtil.isEmpty(userName)) {
				actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "Username");
			}
			else if(StringUtil.isEmpty(password)) {
				actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "Password");
			}
			else if(!UserUtil.isUserNameValid(userName)) {
				actionResponse.constructMessage(ActionResponse.TYPE_INVALID, "Username");
			}
			else if(!UserUtil.isPasswordValid(password)) {
				actionResponse.constructMessage(ActionResponse.TYPE_INVALID, "Password");
			}
			else if(!sessionInfo.getCurrentUser().getUserName().equalsIgnoreCase(userName)) {
				if(userDAO.getUserByUserName(userName) != null) {
					actionResponse.constructMessage(ActionResponse.TYPE_EXIST, "Username");
				}
			}
			else if(!StringUtil.isEmpty(cpNumber)) {
				if(cpNumber.length() != 11) {
					actionResponse.constructMessage(ActionResponse.TYPE_EXIST, "Cellphone Number. Should be 11 characters");
				}
				else if(!StringUtil.getLeft(cpNumber, 2).equalsIgnoreCase("09")) {
					actionResponse.constructMessage(ActionResponse.TYPE_EXIST, "Cellphone Number. Should starts with 09");
				}
			}
			
			if(StringUtil.isEmpty(actionResponse.getMessageStr())) {
				UserDTO userNew = new UserDTO();
				userNew.setId(user.getId());
				userNew.setUserName(userName);
				userNew.setPassword(password);
				userNew.setUpdatedBy(sessionInfo.getCurrentUser().getCode());
				userNew.setUpdatedTimestamp(DateTimeUtil.getCurrentTimestamp());
				
				UserContactDTO userContact = UserContactUtil.getUserContactByContactType(sessionInfo.getCurrentUser().getUserContactList(), UserContactDTO.CONTACT_TYPE_CELLPHONE);
				if(userContact == null) {
					userContact = new UserContactDTO();
					userContact.setUserCode(sessionInfo.getCurrentUser().getCode());
					userContact.setAddedBy(sessionInfo.getCurrentUser().getCode());
					userContact.setBaseDataOnInsert();
					userContact.setRecordStatus(DTOBase.RECORD_STATUS_FOR_ADDITION);
				}
				else {
					userContact.setUpdatedBy(sessionInfo.getCurrentUser().getCode());
					userContact.setBaseDataOnUpdate();
					userContact.setRecordStatus(DTOBase.RECORD_STATUS_FOR_UPDATE);
				}
				userContact.setContact(cpNumber);
				
				userDAO.executeUpdateUserNamePasswordCPNumber(userNew, userContact);
				actionResponse = (ActionResponse) userDAO.getResult().get(ActionResponse.SESSION_ACTION_RESPONSE);
				if(StringUtil.isEmpty(actionResponse.getType())) {
					actionResponse.constructMessage(ActionResponse.TYPE_SUCCESS, "Updated");
					user.setUserName(userName);
					user.setPassword(password);
				}
			}
		}
	}
	
	
}
