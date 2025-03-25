package com.laponhcet.pageant;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.laponhcet.enrollment.EnrollmentDTO;
import com.laponhcet.enrollment.EnrollmentUtil;
import com.laponhcet.pageant.ContestantDTO;
import com.laponhcet.pageant.ContestantDTO;
import com.mytechnopal.ActionResponse;
import com.mytechnopal.DataTable;
import com.mytechnopal.UploadedFile;

import com.mytechnopal.base.ActionAjaxBase;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.dto.UserDTO;
import com.mytechnopal.link.LinkDTO;
import com.mytechnopal.util.DTOUtil;
import com.mytechnopal.util.DateTimeUtil;
import com.mytechnopal.util.FileUtil;
import com.mytechnopal.util.PageUtil;
import com.mytechnopal.util.StringUtil;

public class ContestantInputActionAjax extends ActionAjaxBase {
	private static final long serialVersionUID = 1L;

	protected void dataTableAction(JSONObject jsonObj, DataTable dataTable) {
		initDataTable(dataTable);
		System.out.println("data table actio ");
		try {
			jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataTablePage(sessionInfo, ContestantInputUtil.getDataTableStr(sessionInfo, dataTable)));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	protected void setInput(String action) {
		ContestantDTO contestant = (ContestantDTO) getSessionAttribute(ContestantDTO.SESSION_CONTESTANT);
//		contestantDAO dao = new contestantDAO();
//		contestant.setCode(getRequestString("txtCode"));
		contestant.setName(getRequestString("txtName"));
		contestant.setSequence(getRequestInt("txtSequence"));
//		String code = dao.getEventTypeCodeByName(getRequestString("cboEventType"));
//		System.out.println("getRequestString(cboEventType):::: "+getRequestString("cboEventType"));
//		contestant.setCode(code);
	}
	
//	protected void validateInput(String action) {
//		PersonDTO person = (PersonDTO) getSessionAttribute(PersonDTO.SESSION_PERSON);
//		if(StringUtil.isEmpty(person.getCode())) {
//			actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "Code");
//		}
//		if(StringUtil.isEmpty(person.getLastName())) {
//			actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "Last Name");
//		}
//	}
    protected void validateInput(String action) {
    	if(action.equalsIgnoreCase(DataTable.ACTION_ADD_SAVE) || action.equalsIgnoreCase(DataTable.ACTION_UPDATE_SAVE)) {
			UploadedFile uploadedFile = (UploadedFile) getSessionAttribute(UploadedFile.SESSION_UPLOADED_FILE + "_0");
			if(uploadedFile.getFile() == null) {
				actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "File");
			}
			if (StringUtil.isEmpty(getRequestString("txtName"))) {
                actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "Name");
            }
            
            if (StringUtil.isEmpty(String.valueOf(getRequestInt("txtSequence")))) {
                actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "Sequence");
            }
		}
    }
//    protected void search(DataTable dataTable) {
//		if(dataTable.getSearchCriteria().equalsIgnoreCase(PersonDTO.ACTION_SEARCH_BY_LASTNAME)) {
//			System.out.println("viewed");
//		}
//
//	}
	protected void search(DataTable dataTable) {
		if(dataTable.getSearchCriteria().equalsIgnoreCase(ContestantDTO.ACTION_SEARCH_BY_NAME)) {
			List<DTOBase> userList = (List<DTOBase>) getSessionAttribute(UserDTO.SESSION_USER_LIST);
			ContestantInputUtil.searchByName(dataTable, dataTable.getSearchValue(), userList);
		}
//		else if(dataTable.getSearchCriteria().equalsIgnoreCase(PersonDTO.ACTION_SEARCH_BY_LASTNAME)) {
//			List<DTOBase> userList = (List<DTOBase>) getSessionAttribute(UserDTO.SESSION_USER_LIST);
//			PersonUtil.searchByLastName(dataTable, dataTable.getSearchValue(), userList);
//		}
//		else if(dataTable.getSearchCriteria().equalsIgnoreCase(PersonDTO.ACTION_SEARCH_BY_MIDDLENAME)) {
//			List<DTOBase> userList = (List<DTOBase>) getSessionAttribute(UserDTO.SESSION_USER_LIST);
//			PersonUtil.searchByMiddleName(dataTable, dataTable.getSearchValue(), userList);
//		}
//		else if(dataTable.getSearchCriteria().equalsIgnoreCase(PersonDTO.ACTION_SEARCH_BY_CODE)) {
//			List<DTOBase> userList = (List<DTOBase>) getSessionAttribute(UserDTO.SESSION_USER_LIST);
//			PersonUtil.searchByCode(dataTable, dataTable.getSearchValue(), userList);
//		}
	}
   
    
	protected void executeLogic(JSONObject jsonObj, DataTable dataTable, String action) {
		if(action.equalsIgnoreCase(DataTable.ACTION_VIEW)) {
			System.out.println("ACTION VIEW CONTESTANT CLICKED!!");
			ContestantDTO contestantSelected = (ContestantDTO) dataTable.getSelectedRecord();
			try {
				jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataViewPage(sessionInfo, ContestantInputUtil.getDataViewStr(sessionInfo, contestantSelected), ""));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		else if(action.equalsIgnoreCase(DataTable.ACTION_ADD_VIEW)) {
//			EventTypeDTO eventype = new EventTypeDTO();
			ContestantDAO conDao = new ContestantDAO();
//			List<EventTypeDTO> contestantUnitList = conDao.getEventTypeList();
			UploadedFile uploadedFile = new UploadedFile(); //no need to specify id for the default id is 0
			uploadedFile.setSettings(sessionInfo.getSettings());
			uploadedFile.setValidFileExt(new String[] {"png", "jpg"});
			uploadedFile.setMaxSize(3072000); //3Mb 
			setSessionAttribute(UploadedFile.SESSION_UPLOADED_FILE + "_0", uploadedFile);
			System.out.println("session ifooo: "+sessionInfo.getSettings().getLocalStaticDirectory());
			System.out.println("ADD VIEWWWWWWWW");
			ContestantDTO contestant = new ContestantDTO();
			try {
				jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataEntryPage(sessionInfo, ContestantInputUtil.getDataEntryStr(sessionInfo, contestant, uploadedFile), ""));
			} catch (JSONException e) {
				e.printStackTrace();
			}
			setSessionAttribute(ContestantDTO.SESSION_CONTESTANT, contestant);
		}else if(action.equalsIgnoreCase(DataTable.ACTION_ADD_SAVE)) {
			UploadedFile uploadedFile= (UploadedFile) getSessionAttribute(UploadedFile.SESSION_UPLOADED_FILE + "_0");
			ContestantDAO contestantDAO = new ContestantDAO();
			ContestantDTO contestant = (ContestantDTO) getSessionAttribute(ContestantDTO.SESSION_CONTESTANT);
			if(StringUtil.isEmpty(contestant.getName().trim()) || StringUtil.isEmpty(String.valueOf(contestant.getSequence()))) {
				actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "all required input fields");
				return;
			}
			contestant.setPict(uploadedFile.getFile().getName());
			contestantDAO.executeAdd(contestant);
			actionResponse = (ActionResponse) contestantDAO.getResult().get(ActionResponse.SESSION_ACTION_RESPONSE);
			if(StringUtil.isEmpty(actionResponse.getType())) {
				if(uploadedFile.getFile() != null) {
					System.out.println("file name file name: "+uploadedFile.getFile().getName());
					File fileFrom = new File(sessionInfo.getSettings().getStaticDir(true) + "/tmp/" + uploadedFile.getFile().getName());
					File fileTo = new File(sessionInfo.getSettings().getStaticDir(true) + "/" + sessionInfo.getSettings().getCode() + "/media/contestant/" + uploadedFile.getFile().getName());
					try {
						FileUtils.copyFile(fileFrom, fileTo);
						FileUtil.setFileAccessRights(fileTo);
						fileFrom.delete(); 
					} catch (IOException e) {
						// TODO Auto-generated catch block
						actionResponse.constructMessage(ActionResponse.TYPE_INFO, "Record was successfully saved but failed  to upload the picture.");
					}
					actionResponse.constructMessage(ActionResponse.TYPE_SUCCESS, "Added");
					setSessionAttribute(ContestantDTO.SESSION_CONTESTANT_LIST, contestantDAO.getContestantList());
				}
				
			}
			
		
		}
		else if(action.equalsIgnoreCase(DataTable.ACTION_DELETE_VIEW)) {
			System.out.println("DELETE VIEW CONTESTANT CLICKED!");
			ContestantDTO contestantSelected = (ContestantDTO) dataTable.getSelectedRecord();
			try {
				jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataViewPage(sessionInfo, "contestant", ContestantInputUtil.getDataViewStr(sessionInfo, contestantSelected)));
			} catch (JSONException e) {
				e.printStackTrace();
			}
			setSessionAttribute(ContestantDTO.SESSION_CONTESTANT, contestantSelected);
		}
		else if(action.equalsIgnoreCase(DataTable.ACTION_DELETE)) {
			System.out.println("DELETE CONTESTANT CLICKED!");
			ContestantDAO contestantDAO = new ContestantDAO();
			ContestantDTO contestant = (ContestantDTO) getSessionAttribute(ContestantDTO.SESSION_CONTESTANT);
			contestantDAO.executeDelete(contestant);
			actionResponse = (ActionResponse) contestantDAO.getResult().get(ActionResponse.SESSION_ACTION_RESPONSE);
			if(StringUtil.isEmpty(actionResponse.getType())) {
				setSessionAttribute(ContestantDTO.SESSION_CONTESTANT_LIST, new ContestantDAO().getContestantList());
				File file = new File(sessionInfo.getSettings().getStaticDir(true) + "/" + sessionInfo.getSettings().getCode() + "/media/contestant/" + contestant.getPict());
				if(file.delete()) {
					actionResponse.constructMessage(ActionResponse.TYPE_SUCCESS, "Deleted");
				}
				else {
					actionResponse.constructMessage(ActionResponse.TYPE_SUCCESS, "Deleted a Record but not the File. Please report it to TechnoPal");
				}
			}
		}
		else if (action.equalsIgnoreCase(DataTable.ACTION_UPDATE_VIEW)) {
			System.out.println("UPDATE VIEW CONTESTANT CLICKED!");
            ContestantDTO contestantSelected = (ContestantDTO) dataTable.getSelectedRecord();
        	UploadedFile uploadedFile = new UploadedFile(); //no need to specify id for the default id is 0
			uploadedFile.setSettings(sessionInfo.getSettings());
			uploadedFile.setValidFileExt(new String[] {"png", "jpg"});
			uploadedFile.setMaxSize(3072000); //3Mb 
			setSessionAttribute(UploadedFile.SESSION_UPLOADED_FILE + "_0", uploadedFile);
            try {
                jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataEntryPage(sessionInfo, ContestantInputUtil.getDataEntryStr(sessionInfo, contestantSelected, uploadedFile), ""));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            setSessionAttribute(ContestantDTO.SESSION_CONTESTANT, contestantSelected);
        }
//		else if (action.equalsIgnoreCase(DataTable.ACTION_UPDATE_SAVE)) {
//			System.out.println("UPDATE SAVE CONTESTANT CLICKED!");
//            ContestantDAO contestantDAO = new ContestantDAO();
//            ContestantDTO contestant = (ContestantDTO) getSessionAttribute(ContestantDTO.SESSION_CONTESTANT);
//            actionResponse = (ActionResponse) contestantDAO.getResult().get(ActionResponse.SESSION_ACTION_RESPONSE);
//            UploadedFile uploadedFile= (UploadedFile) getSessionAttribute(UploadedFile.SESSION_UPLOADED_FILE + "_0");
//            contestant.setPict(uploadedFile.getFile().getName());
//    		contestantDAO.executeUpdate(contestant);
//            if (StringUtil.isEmpty(actionResponse.getType())) {
//
//            	if(uploadedFile.getFile() != null) {
//					System.out.println("file name file name: "+uploadedFile.getFile().getName());
//					File fileFrom = new File(sessionInfo.getSettings().getStaticDir(true) + "/tmp/" + uploadedFile.getFile().getName());
//					File fileTo = new File(sessionInfo.getSettings().getStaticDir(true) + "/" + sessionInfo.getSettings().getCode() + "/media/contestant/" + uploadedFile.getFile().getName());
//					try {
//						FileUtils.copyFile(fileFrom, fileTo);
//						FileUtil.setFileAccessRights(fileTo);
//						fileFrom.delete(); 
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						actionResponse.constructMessage(ActionResponse.TYPE_INFO, "Record was successfully saved but failed  to upload the picture.");
//					}
//					actionResponse.constructMessage(ActionResponse.TYPE_SUCCESS, "Added");
//					setSessionAttribute(ContestantDTO.SESSION_CONTESTANT_LIST, contestantDAO.getContestantList());
//				}
//
//                setSessionAttribute(ContestantDTO.SESSION_CONTESTANT_LIST, new ContestantDAO().getContestantList());
//                actionResponse.constructMessage(ActionResponse.TYPE_SUCCESS, "Updated");
//            }
//        }
		else if(action.equalsIgnoreCase(DataTable.ACTION_UPDATE_SAVE)) {
			UploadedFile uploadedFile= (UploadedFile) getSessionAttribute(UploadedFile.SESSION_UPLOADED_FILE + "_0");
			ContestantDAO contestantDAO = new ContestantDAO();
			ContestantDTO contestant = (ContestantDTO) getSessionAttribute(ContestantDTO.SESSION_CONTESTANT);
			if(StringUtil.isEmpty(contestant.getName().trim()) || StringUtil.isEmpty(String.valueOf(contestant.getSequence()))) {
				actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "all required input fields");
				return;
			}
			contestant.setPict(uploadedFile.getFile().getName());
			contestantDAO.executeUpdate(contestant);
			actionResponse = (ActionResponse) contestantDAO.getResult().get(ActionResponse.SESSION_ACTION_RESPONSE);
			if(StringUtil.isEmpty(actionResponse.getType())) {
				if(uploadedFile.getFile() != null) {
					System.out.println("file name file name: "+uploadedFile.getFile().getName());
					File fileFrom = new File(sessionInfo.getSettings().getStaticDir(true) + "/tmp/" + uploadedFile.getFile().getName());
					File fileTo = new File(sessionInfo.getSettings().getStaticDir(true) + "/" + sessionInfo.getSettings().getCode() + "/media/contestant/" + uploadedFile.getFile().getName());
					try {
						FileUtils.copyFile(fileFrom, fileTo);
						FileUtil.setFileAccessRights(fileTo);
						fileFrom.delete(); 
					} catch (IOException e) {
						// TODO Auto-generated catch block
						actionResponse.constructMessage(ActionResponse.TYPE_INFO, "Record was successfully saved but failed  to upload the picture.");
					}
					actionResponse.constructMessage(ActionResponse.TYPE_SUCCESS, "Updated");
					setSessionAttribute(ContestantDTO.SESSION_CONTESTANT_LIST, contestantDAO.getContestantList());
				}
				
			}
			
		
		}
		

       
	}
	
	protected void initDataTable(DataTable dataTable) {
		List<DTOBase> personList = (List<DTOBase>) getSessionAttribute(ContestantDTO.SESSION_CONTESTANT_LIST);
		dataTable.setRecordList(personList);
		dataTable.setCurrentPageRecordList();
	}

	
}
