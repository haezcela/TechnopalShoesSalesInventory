package com.laponhcet.academicprogram;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.laponhcet.academicprogramsubgroup.AcademicProgramSubGroupDTO;
import com.laponhcet.academicsection.AcademicSectionDAO;
import com.laponhcet.enrollment.EnrollmentDAO;
import com.laponhcet.enrollment.EnrollmentDTO;
import com.laponhcet.enrollment.EnrollmentUtil;
import com.laponhcet.student.StudentDAO;
import com.laponhcet.teacher2.TeacherDTO;
import com.mytechnopal.ActionResponse;
import com.mytechnopal.DataTable;
import com.mytechnopal.SessionInfo;
import com.mytechnopal.UploadedFile;
import com.mytechnopal.base.ActionAjaxBase;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.dao.UserDAO;
import com.mytechnopal.dto.UserDTO;
import com.mytechnopal.util.DTOUtil;
import com.mytechnopal.util.FileUtil;
import com.mytechnopal.util.PageUtil;
import com.mytechnopal.util.StringUtil;

public class AcademicProgramActionAjax extends ActionAjaxBase {
	private static final long serialVersionUID = 1L;
	
	protected void setInput(String action) {
		AcademicProgramDTO academicProgram = (AcademicProgramDTO) getSessionAttribute(AcademicProgramDTO.SESSION_ACADEMIC_PROGRAM);
		if(action.equalsIgnoreCase(DataTable.ACTION_ADD_SAVE) || action.equalsIgnoreCase(DataTable.ACTION_UPDATE_SAVE)) {
			int academicProgramSubGroupId = getRequestInt("cboAcademicProgramSubGroupCode");
			if(academicProgramSubGroupId > 0) {
				List<DTOBase> academicProgramSubGroupList = (List<DTOBase>) getSessionAttribute(AcademicProgramSubGroupDTO.SESSION_ACADEMIC_PROGRAM_SUBGROUP_LIST);
				academicProgram.setAcademicProgramSubGroup((AcademicProgramSubGroupDTO) DTOUtil.getObjById(academicProgramSubGroupList, academicProgramSubGroupId));
			}
			academicProgram.setName(getRequestString("txtName"));
			academicProgram.setDescription(getRequestString("txtDescription"));
			academicProgram.setGraduationYearLevel(getRequestInt("txtGraduationYearLevel"));
			int headUserId = getRequestInt("cboHeadUserCode");
			if(headUserId > 0) {
				List<DTOBase> userList = (List<DTOBase>) getSessionAttribute(UserDTO.SESSION_USER_LIST);
				academicProgram.setHeadUserCode((UserDTO) DTOUtil.getObjById(userList, headUserId));
			}
			academicProgram.setLogo(getRequestString("txtLogo"));
		}
	}
	
	protected void validateInput(JSONObject jsonObj, String action) {
		AcademicProgramDTO academicProgram = (AcademicProgramDTO) getSessionAttribute(AcademicProgramDTO.SESSION_ACADEMIC_PROGRAM);
		List<DTOBase> academicProgramList = (List<DTOBase>) getSessionAttribute(AcademicProgramDTO.SESSION_ACADEMIC_PROGRAM_LIST);
		if(action.equalsIgnoreCase(DataTable.ACTION_ADD_SAVE) || action.equalsIgnoreCase(DataTable.ACTION_UPDATE_SAVE)) {
			if(academicProgram.getAcademicProgramSubGroup().getId() == 0) {
				actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "Academic Program Sub Group");
			}
			else if(StringUtil.isEmpty(academicProgram.getName())) {
				actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "Name");
			}
			else if(academicProgram.getGraduationYearLevel() == 0){
				actionResponse.constructMessage(ActionResponse.TYPE_INVALID, "Graduation Year Level");
			}
		}
		else if(action.equalsIgnoreCase(DataTable.ACTION_DELETE)) {
			AcademicProgramDTO academicProgramDTO = (AcademicProgramDTO) DTOUtil.getObjByCode(academicProgramList, academicProgram.getCode());
			List<DTOBase> academicSectionListByAcademicProgramCode = new AcademicSectionDAO().getAcademicSectionListByAcademicProgramCode(academicProgramDTO.getCode());
			if(!academicSectionListByAcademicProgramCode.isEmpty()) {
				actionResponse.constructMessage(ActionResponse.TYPE_FAIL, "Academic Program is being used in Academic Section");
			}
		}
	}
	
	protected void executeLogic(JSONObject jsonObj, DataTable dataTable, String action) {
		AcademicProgramDTO academicProgram = (AcademicProgramDTO) getSessionAttribute(AcademicProgramDTO.SESSION_ACADEMIC_PROGRAM);
		List<DTOBase> academicProgramSubGroupList = (List<DTOBase>) getSessionAttribute(AcademicProgramSubGroupDTO.SESSION_ACADEMIC_PROGRAM_SUBGROUP_LIST);
		List<DTOBase> userList = (List<DTOBase>) getSessionAttribute(UserDTO.SESSION_USER_LIST);
		UploadedFile uploadedFile = (UploadedFile) getSessionAttribute(UploadedFile.SESSION_UPLOADED_FILE + "_0");
		if(action.equalsIgnoreCase(DataTable.ACTION_VIEW)) {
			AcademicProgramDTO academicProgramSelected = (AcademicProgramDTO) dataTable.getSelectedRecord();
			academicProgram = academicProgramSelected.getAcademicProgram();
			//System.out.println("ID: " + academicProgram.getId());
			try {
				jsonObj.put(DTOBase.PAGE_CONTENT, PageUtil.getDataViewPage(sessionInfo, AcademicProgramUtil.getDataViewStr(sessionInfo, academicProgram), action));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			setSessionAttribute(AcademicProgramDTO.SESSION_ACADEMIC_PROGRAM, new AcademicProgramDTO());
		}
		else if(action.equalsIgnoreCase(DataTable.ACTION_ADD_VIEW)) {
			try {
				jsonObj.put(DTOBase.PAGE_CONTENT, PageUtil.getDataEntryPage(sessionInfo, AcademicProgramUtil.getDataEntryStr(sessionInfo, academicProgram, academicProgramSubGroupList, userList, uploadedFile), action));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			setSessionAttribute(AcademicProgramDTO.SESSION_ACADEMIC_PROGRAM, new AcademicProgramDTO());
		}
		else if(action.equalsIgnoreCase(DataTable.ACTION_ADD_SAVE)) {
			academicProgram.setLogo(uploadedFile.getFile().getName());
			academicProgram.setAddedBy(sessionInfo.getCurrentUser().getCode());
			AcademicProgramDAO academicProgramDAO = new AcademicProgramDAO();
			academicProgramDAO.executeAdd(academicProgram);
			actionResponse = (ActionResponse) academicProgramDAO.getResult().get(ActionResponse.SESSION_ACTION_RESPONSE);
			if(StringUtil.isEmpty(actionResponse.getType())) {
					actionResponse.constructMessage(ActionResponse.TYPE_SUCCESS, "Added");
					setSessionAttribute(AcademicProgramDTO.SESSION_ACADEMIC_PROGRAM_LIST, new AcademicProgramDAO().getAcademicProgramList());
					File fileTo = new File(sessionInfo.getSettings().getStaticDir(true) + "/" + sessionInfo.getSettings().getCode() + "/logo/" + academicProgram.getCode() + "." + FileUtil.getFileExtension(uploadedFile.getFile()));
					try {
						FileUtils.copyFile(uploadedFile.getFile(), fileTo);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						actionResponse.constructMessage(ActionResponse.TYPE_INFO, "Record was successfully saved but failed  to upload the picture.");
					}			
			}
		}
		else if(action.equalsIgnoreCase(DataTable.ACTION_UPDATE_VIEW)) {
			AcademicProgramDTO academicProgramSelected = (AcademicProgramDTO) dataTable.getSelectedRecord();
			academicProgram = academicProgramSelected.getAcademicProgram();
			//System.out.println("ID: " + academicProgram.getId());
			uploadedFile.setFile(new File(sessionInfo.getSettings().getStaticDir(false) + "/" + sessionInfo.getSettings().getCode() + "/logo/" +  academicProgram.getCode()  + ".jpg"));
			try {
				jsonObj.put(DTOBase.PAGE_CONTENT, PageUtil.getDataEntryPage(sessionInfo, AcademicProgramUtil.getDataEntryStr(sessionInfo, academicProgram, academicProgramSubGroupList, userList, uploadedFile), action));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			setSessionAttribute(AcademicProgramDTO.SESSION_ACADEMIC_PROGRAM, academicProgram);
		}
		else if(action.equalsIgnoreCase(DataTable.ACTION_UPDATE_SAVE)) {
			//System.out.println("ID: " + academicProgram.getId());
			academicProgram.setUpdatedBy(sessionInfo.getCurrentUser().getCode());
			AcademicProgramDAO academicProgramDAO = new AcademicProgramDAO();
			academicProgramDAO.executeUpdate(academicProgram);
			actionResponse = (ActionResponse) academicProgramDAO.getResult().get(ActionResponse.SESSION_ACTION_RESPONSE);
			if(StringUtil.isEmpty(actionResponse.getType())) {
				actionResponse.constructMessage(ActionResponse.TYPE_SUCCESS, "Updated");
				setSessionAttribute(AcademicProgramDTO.SESSION_ACADEMIC_PROGRAM_LIST, new AcademicProgramDAO().getAcademicProgramList());
				
				if(!academicProgram.getLogo().equalsIgnoreCase(uploadedFile.getFile().getName()));
					academicProgram.setLogo(uploadedFile.getFile().getName());
					File fileTo = new File(sessionInfo.getSettings().getStaticDir(true) + "/" + sessionInfo.getSettings().getCode() + "/logo/" + academicProgram.getCode() + "." + FileUtil.getFileExtension(uploadedFile.getFile()));
				try {
					FileUtils.copyFile(uploadedFile.getFile(), fileTo);
					FileUtil.setFileAccessRights(fileTo);
				} catch (IOException e) {
					actionResponse.constructMessage(ActionResponse.TYPE_INFO, "Details had been saved successfully but failed to upload files");
				}
			}
		}
		else if(action.equalsIgnoreCase(DataTable.ACTION_DELETE_VIEW)) {
			academicProgram = (AcademicProgramDTO) dataTable.getSelectedRecord();
			//System.out.println("ID: " + academicProgram.getId());
			try {
				jsonObj.put(DTOBase.PAGE_CONTENT, PageUtil.getDataViewPage(sessionInfo, AcademicProgramUtil.getDataViewStr(sessionInfo, academicProgram), action));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			setSessionAttribute(AcademicProgramDTO.SESSION_ACADEMIC_PROGRAM, academicProgram);
		}
		else if(action.equalsIgnoreCase(DataTable.ACTION_DELETE)) {
			//System.out.println("ID: " + academicProgram.getId());
			AcademicProgramDAO academicProgramDAO = new AcademicProgramDAO();
			academicProgramDAO.executeDelete(academicProgram);
			actionResponse = (ActionResponse) academicProgramDAO.getResult().get(ActionResponse.SESSION_ACTION_RESPONSE);
			if(StringUtil.isEmpty(actionResponse.getType())) {
				actionResponse.constructMessage(ActionResponse.TYPE_SUCCESS, "Deleted");
				setSessionAttribute(AcademicProgramDTO.SESSION_ACADEMIC_PROGRAM_LIST, new AcademicProgramDAO().getAcademicProgramList());
			}
		}
		else if(action.equalsIgnoreCase(DataTable.ACTION_SORT)) {
			String sortType = getRequestString("txtSortType");
			int column = getRequestInt("txtColumn");
			if(column == 2) {
				if(sortType.equalsIgnoreCase(DataTable.SORT_TYPE_ASCENDING)) { 
					new SortNameAscending().sort(dataTable.getRecordList()); 
				} 
				else if(sortType.equalsIgnoreCase(DataTable.SORT_TYPE_DESCENDING)) { 
					new SortNameDescending().sort(dataTable.getRecordList()); 
				}
			}
		}
	}
	
	protected void dataTableAction(SessionInfo sessionInfo, JSONObject jsonObj, DataTable dataTable) {
		initDataTable(dataTable);
		try {
			jsonObj.put(DTOBase.PAGE_CONTENT, PageUtil.getDataTablePage(AcademicProgramUtil.getDataTableStr(sessionInfo, dataTable)));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void search(JSONObject jsonObj, DataTable dataTable) {
		if(StringUtil.isEmpty(dataTable.getSearchValue())) {
			dataTable.setRecordListVisible();
		}
		else {
			if(dataTable.getSearchCriteria().equalsIgnoreCase(AcademicProgramDTO.ACTION_SEARCH_BY_NAME)) {
				AcademicProgramUtil.searchAcademicProgramByName(dataTable, dataTable.getSearchValue());
			}
		}
	}
	
	protected void initDataTable(DataTable dataTable) {
			List<DTOBase> academicProgramSubGroupList = (List<DTOBase>) getSessionAttribute(AcademicProgramSubGroupDTO.SESSION_ACADEMIC_PROGRAM_SUBGROUP_LIST);
			List<DTOBase> userList = (List<DTOBase>) getSessionAttribute(UserDTO.SESSION_USER_LIST);
			List<DTOBase> academicProgramList = (List<DTOBase>) getSessionAttribute(AcademicProgramDTO.SESSION_ACADEMIC_PROGRAM_LIST);
			dataTable.setRecordList(academicProgramList);
			dataTable.setCurrentPageRecordList();
			for(int row=0; row < dataTable.getRecordListCurrentPage().size(); row++) {
				AcademicProgramDTO academicProgram = (AcademicProgramDTO) dataTable.getRecordListCurrentPage().get(row);
				academicProgram.setAcademicProgramSubGroup((AcademicProgramSubGroupDTO) DTOUtil.getObjByCode(academicProgramSubGroupList, academicProgram.getAcademicProgramSubGroup().getCode()));
				if(academicProgram.getHeadUserCode() != null) {
					academicProgram.setHeadUserCode((UserDTO) DTOUtil.getObjByCode(userList, academicProgram.getHeadUserCode().getCode()));
				}
			}
		}
}