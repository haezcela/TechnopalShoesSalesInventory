package com.mytechnopal.user;

import java.io.Serializable;

import com.mytechnopal.SessionInfo;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.base.WebControlBase;
import com.mytechnopal.usercontact.UserContactDTO;
import com.mytechnopal.usercontact.UserContactUtil;
import com.mytechnopal.usermedia.UserMediaDTO;
import com.mytechnopal.usermedia.UserMediaUtil;
import com.mytechnopal.util.QRCodeUtil;
import com.mytechnopal.util.StringUtil;
import com.mytechnopal.webcontrol.TextBoxWebControl;

public class ProfileUtil implements Serializable {
	private static final long serialVersionUID = 1L;

	public static String getDataEntryStr(SessionInfo sessionInfo) {
		UserMediaDTO idPict = UserMediaUtil.getUserMediaByMediaType(sessionInfo.getCurrentUser().getUserMediaList(), UserMediaDTO.MEDIA_TYPE_ID_PICTURE);
		if(idPict == null) {
			idPict = new UserMediaDTO();
			idPict.setFilename(sessionInfo.getSettings().getLogo());
		}
		else {
			idPict.setFilename("/static/" + sessionInfo.getSettings().getCode() + "/media/id_pict/" + idPict.getFilename());
		}
		
		UserContactDTO cpNumber = UserContactUtil.getUserContactByContactType(sessionInfo.getCurrentUser().getUserContactList(), UserContactDTO.CONTACT_TYPE_CELLPHONE);
		if(cpNumber == null) {
			cpNumber = new UserContactDTO();
			cpNumber.setContactType(UserContactDTO.CONTACT_TYPE_CELLPHONE);
		}
		
		UserContactDTO telegram = UserContactUtil.getUserContactByContactType(sessionInfo.getCurrentUser().getUserContactList(), UserContactDTO.CONTACT_TYPE_TELEGRAM);
		if(telegram == null) {
			telegram = new UserContactDTO();
			telegram.setContactType(UserContactDTO.CONTACT_TYPE_TELEGRAM);
		}
		
		StringBuffer strBuff = new StringBuffer();
		strBuff.append("<div class='row'>");
		strBuff.append("	<div class='col-lg-4'>");
		strBuff.append("		<img src='" + idPict.getFilename() + "' class='img-fluid img-circle circle-border m-b-md' alt='profile'>");
		strBuff.append("	</div>");
		strBuff.append("	<div class='col-lg-8'>");
		strBuff.append("		<h2>Name: " + sessionInfo.getCurrentUser().getName(true, true, false) + "</h2>");
		strBuff.append("		<div class='row'>");
		strBuff.append(				new TextBoxWebControl().getTextBoxWebControl("col-sm-4", true, "Username", "username", "UserName", sessionInfo.getCurrentUser().getUserName(), 16, WebControlBase.DATA_TYPE_STRING, ""));
		strBuff.append(				new TextBoxWebControl().getTextBoxWebControl("col-sm-4", true, "Password", "password", "Password", sessionInfo.getCurrentUser().getPassword(), 16, WebControlBase.DATA_TYPE_PASSWORD, ""));
		strBuff.append(				new TextBoxWebControl().getTextBoxWebControl("col-sm-4", false, "CellPhone Number", "CellPhone Number", "CPNumber", cpNumber.getContact(), 11, WebControlBase.DATA_TYPE_STRING, ""));
		strBuff.append("			<div class='col-lg-12'>");
		strBuff.append("				*Username is minimum of 6 characters and maximum of 16 characters without space. Can be all aphabet or numeric or the combination<br>*Password is minimum of 6 characters and maximum of 16 characters without space. Can be all aphabet or numeric or the combination that can be included of special characters like @-_.<br>*Cellphone number should be 11 characters and starts with '09' e.g. 09091234567");
		strBuff.append("			</div>");
		strBuff.append("			<div class='col-lg-12 text-center'><br><button class='btn btn-primary' onClick='updateUP()'>Update</button></div>");
		strBuff.append("			<div class='col-lg-12'>");
		if(StringUtil.isEmpty(telegram.getContact())) {
			String newRegistrationURL = sessionInfo.getTelegramSettings().getBotURL() + "?start=NEWREG" + sessionInfo.getCurrentUser().getCode();
			strBuff.append("			<hr>");
			strBuff.append("			<table width='100%'>");	
			strBuff.append("				<tr>");
			strBuff.append("					<td width='65%' valign='top'>");
			strBuff.append("						You have not yet registered to Telegram.  Please download the app via Play Store or App Store.  We will be using this app as a primary channel where you can view updates, news and notifications. After downloading the App, scan the QR Code to register and wait for the acknowledgment. It will take at least 1 minute to arrive.");
			strBuff.append("					</td>");
			strBuff.append("					<td width='5%' valign='top'>");
			strBuff.append("						&nbsp;");
			strBuff.append("					</td>");
			strBuff.append("					<td width='30%' valign='top'>");
			strBuff.append("						<img class='img-fluid' src='" + QRCodeUtil.generateQRCodeBase64(newRegistrationURL, 200, 200) + "'>");
			strBuff.append("					</td>");
			strBuff.append("				</tr>");
			strBuff.append("			</table>");
		}
		strBuff.append("			</div>");
		strBuff.append("		</div>");
		strBuff.append("	</div>");
		strBuff.append("</div>");
		return strBuff.toString();
	}
}