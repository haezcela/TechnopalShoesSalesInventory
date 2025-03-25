package com.laponhcet.semester;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.laponhcet.academicyear.AcademicYearDTO;
import com.mytechnopal.ActionResponse;
import com.mytechnopal.DataTable;
import com.mytechnopal.SessionInfo;
import com.mytechnopal.base.ActionAjaxBase;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.dto.UserDTO;
import com.mytechnopal.util.DTOUtil;
import com.mytechnopal.util.DateTimeUtil;
import com.mytechnopal.util.PageUtil;
import com.mytechnopal.util.StringUtil;

public class SemesterActionAjax extends ActionAjaxBase {
	private static final long serialVersionUID = 1L;
	
	protected void setInput(String action) {
		List<DTOBase> semesterList = (List<DTOBase>) getSessionAttribute(SemesterDTO.SESSION_SEMESTER_LIST);
		List<DTOBase> academicYearList = (List<DTOBase>) getSessionAttribute(AcademicYearDTO.SESSION_ACADEMIC_YEAR_LIST);
		SemesterDTO semester = (SemesterDTO) getSessionAttribute(SemesterDTO.SESSION_SEMESTER);
		if(getRequestInt("cboAcademicYearCode") > 0){
			semester.setAcademicYear((AcademicYearDTO) DTOUtil.getObjById(academicYearList, getRequestInt("cboAcademicYearCode")));
		}
//		semester.getAcademicYear().setCode(getRequestString("cboAcademicYearCode"));
		semester.setName(getRequestInt("cboName"));
/*		semester.getAcademicYear().setGradingSystem(getRequestString("txtGradingSystem"));*/
		semester.setDateStart(DateTimeUtil.getTimestamp(getRequestString("txtDateStart"), "MM/dd/yyyy"));
		semester.setDateEnd(DateTimeUtil.getTimestamp(getRequestString("txtDateEnd"), "MM/dd/yyyy"));

	}
	
	protected void validateInput(JSONObject jsonObj, String action) {
		SemesterDTO semester = (SemesterDTO) getSessionAttribute(SemesterDTO.SESSION_SEMESTER);
		if(action.equalsIgnoreCase(DataTable.ACTION_ADD_SAVE) || action.equalsIgnoreCase(DataTable.ACTION_UPDATE_SAVE)) {
//			if(StringUtil.isEmpty(semester.getName())) {
//				actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "Name");
//			}
//			

			if(StringUtil.isStringExistFromString(semester.getDateStart().toString(), semester.getDateEnd().toString())){
				actionResponse.constructMessage(ActionResponse.TYPE_EXIST, "Date Start");
			}
		}
	}
	
	protected void executeLogic(JSONObject jsonObj, DataTable dataTable, String action) {
		List<DTOBase> academicYearList = (List<DTOBase>) getSessionAttribute(AcademicYearDTO.SESSION_ACADEMIC_YEAR_LIST);
		
		if(action.equalsIgnoreCase(DataTable.ACTION_ADD_VIEW)) {
			SemesterDTO semester = new SemesterDTO();
			try {
					jsonObj.put(DTOBase.PAGE_CONTENT, PageUtil.getDataEntryPage(sessionInfo, SemesterUtil.getDataEntryStr(sessionInfo, semester, academicYearList), action));
			
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			setSessionAttribute(SemesterDTO.SESSION_SEMESTER, semester);
		}
		else if(action.equalsIgnoreCase(DataTable.ACTION_ADD_SAVE)) {
			SemesterDTO semester = (SemesterDTO) getSessionAttribute(SemesterDTO.SESSION_SEMESTER);
			//AcademicYearDTO academicYear = (AcademicYearDTO) getSessionAttribute(AcademicYearDTO.SESSION_ACADEMIC_YEAR);
			semester.setAddedBy(sessionInfo.getCurrentUser().getCode());
//			semester.getAcademicYear().setGradingSystem(academicYear.getGradingSystem());
			SemesterDAO semesterDAO = new SemesterDAO();
			semesterDAO.executeAdd(semester);
			actionResponse = (ActionResponse) semesterDAO.getResult().get(ActionResponse.SESSION_ACTION_RESPONSE);
			if(StringUtil.isEmpty(actionResponse.getType())) {
				actionResponse.constructMessage(ActionResponse.TYPE_SUCCESS, "Added");
				init(dataTable, jsonObj, action, semester);
			}
		}
		else if(action.equalsIgnoreCase(DataTable.ACTION_UPDATE_VIEW)) {
			SemesterDTO semester = (SemesterDTO) DTOUtil.getObjByCode(dataTable.getRecordListCurrentPage(), getRequestString("txtObjCode"));
			try {
				jsonObj.put(DTOBase.PAGE_CONTENT, PageUtil.getDataEntryPage(sessionInfo, SemesterUtil.getDataEntryStr(sessionInfo, semester, academicYearList), action));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			setSessionAttribute(SemesterDTO.SESSION_SEMESTER, semester);
		}
		else if(action.equalsIgnoreCase(DataTable.ACTION_UPDATE_SAVE)) {
			SemesterDTO semester = (SemesterDTO) getSessionAttribute(SemesterDTO.SESSION_SEMESTER);
			semester.setUpdatedBy(sessionInfo.getCurrentUser().getCode());
			SemesterDAO semesterDAO = new SemesterDAO();
			semesterDAO.executeUpdate(semester);
			actionResponse = (ActionResponse) semesterDAO.getResult().get(ActionResponse.SESSION_ACTION_RESPONSE);
			if(StringUtil.isEmpty(actionResponse.getType())) {
				actionResponse.constructMessage(ActionResponse.TYPE_SUCCESS, "Updated");
				init(dataTable, jsonObj, action, semester);
			}
		}
		else if(action.equalsIgnoreCase(DataTable.ACTION_DELETE)) {
			SemesterDTO semester = (SemesterDTO) DTOUtil.getObjByCode(dataTable.getRecordListCurrentPage(), getRequestString("txtObjCode"));
			SemesterDAO semesterDAO = new SemesterDAO();			
			semesterDAO.executeDelete(semester);
			actionResponse = (ActionResponse) semesterDAO.getResult().get(ActionResponse.SESSION_ACTION_RESPONSE);
			if(StringUtil.isEmpty(actionResponse.getType())) {
				actionResponse.constructMessage(ActionResponse.TYPE_SUCCESS, "Deleted");
				init(dataTable, jsonObj, action, semester);
			}
		}
		else if(action.equalsIgnoreCase(DataTable.ACTION_VIEW)) {
			SemesterDTO semester = (SemesterDTO) DTOUtil.getObjByCode(dataTable.getRecordListCurrentPage(), getRequestString("txtObjCode"));
			try {
				jsonObj.put(DTOBase.PAGE_CONTENT, PageUtil.getDataViewPage(sessionInfo, SemesterUtil.getDataViewStr(sessionInfo, semester), action));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			setSessionAttribute(SemesterDTO.SESSION_SEMESTER, semester);
		}
	}
	
	protected void search(JSONObject jsonObj, DataTable dataTable) {
		if(StringUtil.isEmpty(dataTable.getSearchValue())) {
			dataTable.setRecordListVisible();
		}
		else {
			if(dataTable.getSearchCriteria().equalsIgnoreCase(SemesterDTO.ACTION_SEARCH_BY_NAME)) {
				SemesterUtil.searchSemesterByName(dataTable, dataTable.getSearchValue());
			}
		}
	}
	
	protected void dataTableAction(SessionInfo sessionInfo, JSONObject jsonObj, DataTable dataTable) {
		try {
			jsonObj.put(DTOBase.PAGE_CONTENT, PageUtil.getDataTablePage(SemesterUtil.getDataTableStr(sessionInfo, dataTable, true)));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void init(DataTable dataTable, JSONObject jsonObj, String action, SemesterDTO semester) {
		setSessionAttribute(SemesterDTO.SESSION_SEMESTER, new SemesterDTO());
		List<DTOBase> semesterList = new SemesterDAO().getSemesterList();
			dataTable.setRecordList(semesterList);
			if(!action.equalsIgnoreCase(DataTable.ACTION_DELETE)) {
				dataTable.setRecordStatus(semester.getCode(), DTOBase.RECORD_STATUS_SELECTED);
			}
			dataTableAction(sessionInfo, jsonObj, dataTable);
	}

}
