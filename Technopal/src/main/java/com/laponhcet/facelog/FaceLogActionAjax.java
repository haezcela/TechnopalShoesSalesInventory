package com.laponhcet.facelog;

import java.io.File;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.mytechnopal.ActionResponse;
import com.mytechnopal.base.ActionAjaxBase;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.dao.MessageSMSDAO;
import com.mytechnopal.dao.MessageTelegramDAO;
import com.mytechnopal.dao.UserDAO;
import com.mytechnopal.dto.MessageSMSDTO;
import com.mytechnopal.dto.MessageTelegramDTO;
import com.mytechnopal.dto.UserDTO;
import com.mytechnopal.facelog.FaceLogDAO;
import com.mytechnopal.facelog.FaceLogDTO;
import com.mytechnopal.facelog.FaceLogSettingsDAO;
import com.mytechnopal.facelog.FaceLogSettingsDTO;
import com.mytechnopal.facelogaccess.FaceLogAccessDAO;
import com.mytechnopal.facelogaccess.FaceLogAccessDTO;
import com.mytechnopal.usercontact.UserContactDAO;
import com.mytechnopal.usercontact.UserContactDTO;
import com.mytechnopal.usercontact.UserContactUtil;
import com.mytechnopal.usermedia.UserMediaDAO;
import com.mytechnopal.usermedia.UserMediaDTO;
import com.mytechnopal.usermedia.UserMediaUtil;
import com.mytechnopal.util.DateTimeUtil;
import com.mytechnopal.util.FileUtil;
import com.mytechnopal.util.StringUtil;
import com.mytechnopal.util.WebUtil;

public class FaceLogActionAjax extends ActionAjaxBase {

	private static final long serialVersionUID = 1L;

	protected void customAction(JSONObject jsonObj, String action) {
		FaceLogDAO faceLogDAO = new FaceLogDAO();
		if(action.equalsIgnoreCase("INIT")) {
			FaceLogSettingsDAO faceLogSettingsDAO = new FaceLogSettingsDAO();
			FaceLogSettingsDTO faceLogSettings = faceLogSettingsDAO.getFaceLogSettings();
	
			Date initDate =DateTimeUtil.getCurrentTimestamp();
			if(faceLogSettings.getInitDate() == null) {
				faceLogSettings.setInitDate(initDate);
				faceLogSettingsDAO.executeUpdateInitDate(initDate);
			}
			else {
				if(DateTimeUtil.addDay(faceLogSettings.getInitDate(), 1).before(initDate)) {
					//update init date on face_log_settings table
					//delete all content of face_log table as it done inside faceLogSettingsDAO
					faceLogSettings.setInitDate(initDate);
					faceLogSettingsDAO.executeUpdateInitDate(initDate);
				}
			}
			setSessionAttribute(FaceLogSettingsDTO.SESSION_FACELOGSETTINGS, faceLogSettings);
		}
		else {
			//System.out.println("here1");
			boolean isValid = false;
			Timestamp timeLog = DateTimeUtil.getCurrentTimestamp();
			String accessCode = getRequestString("txtSelectedRecord");
			//System.out.println("txtSelectedRecord: " + accessCode);
			FaceLogAccessDTO faceLogAccess = new FaceLogAccessDAO().getFaceLogAccessByAccessCode(accessCode);
			FaceLogSettingsDTO faceLogSettings = (FaceLogSettingsDTO) getSessionAttribute(FaceLogSettingsDTO.SESSION_FACELOGSETTINGS);
			FaceLogDTO faceLogLast = faceLogDAO.getFaceLogLastByLogCode(accessCode);
			
			boolean isIn = false;
			if(faceLogSettings.getType().equalsIgnoreCase(FaceLogSettingsDTO.TYPE_INOUT)) {
				if(faceLogLast == null) {
					isIn = true;
					isValid = true;
				}
				else {
					//System.out.println("timeLog.getTime(): " + timeLog);
					//System.out.println("faceLogLast.getTimeLog().getTime(): " + faceLogLast.getTimeLog());
					if((timeLog.getTime() - faceLogLast.getTimeLog().getTime())/1000>=60) { //more that 1 minute since its last log in/out
						isValid = true;
						if(!faceLogLast.isIn()) {
							isIn = true;
						}
					}
					else {
						isValid = false;
					}
				}
			}
			else {
				if(faceLogLast == null) {
					isValid = true;
				}
				else {
					if((timeLog.getTime() - faceLogLast.getTimeLog().getTime())/1000>=60) { //more that 1 minute since its last log in/out
						isValid = true;
					}
					else {
						isValid = false;
					}
				}
				isIn = faceLogSettings.getType().equalsIgnoreCase(FaceLogSettingsDTO.TYPE_IN)?true:false;
			}
			
			//System.out.println("isValid: " + isValid);
			
			if(isValid) {
				String capturedPict = getRequestString("txtDataURL", true);
				String defaultPict =  sessionInfo.getSettings().getDefaultPict();
				String profilePict = "";
				//System.out.println("txtDataURL: " + capturedPict);
				UserDTO user = null;
				
				if(faceLogAccess != null) {
					user = new UserDAO().getUserByCode(faceLogAccess.getUserCode());
				}
				
				if(user == null) {
					user = new UserDTO();
					user.setCode("GUEST");
					user.setFirstName("GUEST");
					profilePict = defaultPict;
				}
				else {
					List<DTOBase> userMediaList = new UserMediaDAO().getUserMediaListByUserCode(user.getCode());
					UserMediaDTO userMediaIDPict = UserMediaUtil.getUserMediaByMediaType(userMediaList, UserMediaDTO.MEDIA_TYPE_ID_PICTURE);
					if(userMediaIDPict == null) {
						profilePict = defaultPict;
					}
					else {
						profilePict = sessionInfo.getSettings().getStaticDir(false) + "/" + sessionInfo.getSettings().getCode() + "/media/id_pict/" + userMediaIDPict.getFilename();
					}
				}
				
				//Write captured image to file
				String path = sessionInfo.getSettings().getStaticDir(true) + "/" + sessionInfo.getSettings().getCode() + "/facelog/" + DateTimeUtil.getDateTimeToStr(timeLog, "MMddyyyy");
				//System.out.println("path: " + path);
				if(!new File(path).exists()) {
					FileUtil.createDir(path);
				}
				String capturedPictFilename = user.getCode() + "_" + accessCode + "_" + DateTimeUtil.getDateTimeToStr(timeLog, "kkmmss") + ".txt";
				File file = new File(path + "/" + capturedPictFilename);
				if(FileUtil.isTextWritten(file, capturedPict, true)) {
					FaceLogDTO faceLog = new FaceLogDTO();
					faceLog.setUser(user);
					faceLog.setLogCode(accessCode);
					faceLog.setTimeLog(timeLog);
					faceLog.setIn(isIn);
					faceLogDAO.executeAdd(faceLog);
					if(StringUtil.isEmpty(actionResponse.getType())) {
						actionResponse = (ActionResponse) faceLogDAO.getResult().get(ActionResponse.SESSION_ACTION_RESPONSE);
						//MessageDTO
						if(!user.getCode().equalsIgnoreCase("GUEST")) {
							//System.out.println("here1");
							List<DTOBase> userContactList = new UserContactDAO().getUserContactListByUserCode(user.getCode());
							if(userContactList.size() >= 1) {
								//System.out.println("here2");
								if(faceLogSettings.isNotifySMS()) {
									//System.out.println("here3");
									UserContactDTO userContactSMS = UserContactUtil.getUserContactByContactType(userContactList, UserContactDTO.CONTACT_TYPE_CELLPHONE);
									if(userContactSMS != null) {
										String isInStr = faceLog.isIn()?"IN":"OUT";
										String message = sessionInfo.getSettings().getCode().toUpperCase() + " FaceKeeper Advisory\nName: " + faceLog.getUser().getName(false, true, true) + "," + "\nTime " + isInStr + ": " + DateTimeUtil.getDateTimeToStr(faceLog.getTimeLog(), "MM/dd/yyyy hh:mm:ss aa");
										MessageSMSDAO messageSMSDAO = new MessageSMSDAO();
										//this is for testing purposes to send sms directly to cloud server
										//messageSMSDAO.daoConnectorUtil.setDbSettings(new DBSettingsDTO(DBSettingsDTO.SERVER_SMS));
										MessageSMSDTO messageSMS = new MessageSMSDTO();
										messageSMS.setOwnerCode(sessionInfo.getSettings().getCode());
										messageSMS.setCpNumber(userContactSMS.getContact());
										messageSMS.setMessage(message);
										messageSMS.setPriority(8);
										messageSMS.setGroupNum(2);
										messageSMS.setStatus(MessageSMSDTO.STATUS_PROCESSING);
										messageSMSDAO.executeAdd(messageSMS);
										//back to local server
										//messageSMSDAO.daoConnectorUtil.setDbSettings(new DBSettingsDTO(DBSettingsDTO.SERVER_LOCAL));
									}
								}
								
								if(faceLogSettings.isNotifyTelegram()) {
									UserContactDTO userContactTelegram = UserContactUtil.getUserContactByContactType(userContactList, UserContactDTO.CONTACT_TYPE_TELEGRAM);
									if(userContactTelegram != null) {
										String isInStr = faceLog.isIn()?"IN":"OUT";
										String message = sessionInfo.getSettings().getCode().toUpperCase() + " FaceKeeper Advisory\nName: " + faceLog.getUser().getName(false, true, true) + "," + "\nTime " + isInStr + ": " + DateTimeUtil.getDateTimeToStr(faceLog.getTimeLog(), "MM/dd/yyyy hh:mm:ss aa");
										MessageTelegramDAO messageTelegramDAO = new MessageTelegramDAO();
										//this is for testing purposes to send sms directly to cloud server
										//messageTelegramDAO.daoConnectorUtil.setDbSettings(new DBSettingsDTO(DBSettingsDTO.SERVER_SMS));
										MessageTelegramDTO messageTelegram = new MessageTelegramDTO();
										messageTelegram.setOwnerCode(sessionInfo.getSettings().getCode());
										messageTelegram.setChatId(userContactTelegram.getContact());
										messageTelegram.setMessage(message);
										messageTelegram.setPriority(8);
										messageTelegram.setGroupNum(2);
										messageTelegram.setStatus(MessageTelegramDTO.STATUS_PROCESSING);
										messageTelegramDAO.executeAdd(messageTelegram);
										//back to local server
										//messageSMSDAO.daoConnectorUtil.setDbSettings(new DBSettingsDTO(DBSettingsDTO.SERVER_LOCAL));
									}
								}
							}
						}
						try {
							jsonObj.put("firstName", WebUtil.getHTMLStr(faceLog.getUser().getFirstName()));
							jsonObj.put("profilePict", profilePict);
							jsonObj.put("capturedPict", capturedPict);
							jsonObj.put("timeLog", DateTimeUtil.getDateTimeToStr(faceLog.getTimeLog(), "hh:mm:ss aa"));
							jsonObj.put("isIn", faceLog.isIn());
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				}
			}
			else {
				actionResponse.constructMessage(ActionResponse.TYPE_FAIL, "Please wait a minute and tap again");
			}
		}
	}
}
