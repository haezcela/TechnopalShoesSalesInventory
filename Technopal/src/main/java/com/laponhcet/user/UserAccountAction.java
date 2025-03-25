package com.laponhcet.user;

import com.mytechnopal.base.ActionBase;
import com.mytechnopal.dto.UserDTO;

public class UserAccountAction extends ActionBase {
	private static final long serialVersionUID = 1L;
	
	protected void setSessionVars() {
		UserDTO user = sessionInfo.getCurrentUser().getUser();
		setSessionAttribute(UserDTO.SESSION_USER, user.getUser());
	}
}