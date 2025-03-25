package com.laponhcet.academicyear;


import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.laponhcet.academicyear.AcademicYearDAO;
import com.laponhcet.academicyear.AcademicYearDTO;
import com.laponhcet.academicyear.AcademicYearUtil;
import com.laponhcet.semester.SemesterDTO;
import com.mytechnopal.ActionResponse;
import com.mytechnopal.DataTable;
import com.mytechnopal.SessionInfo;
import com.mytechnopal.base.ActionAjaxBase;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.util.DTOUtil;
import com.mytechnopal.util.DateTimeUtil;
import com.mytechnopal.util.PageUtil;
import com.mytechnopal.util.StringUtil;

public class AcademicYearActionAjax extends ActionAjaxBase {
	private static final long serialVersionUID = 1L;
	
	protected void setInput(String action) {
		AcademicYearDTO academicYear = (AcademicYearDTO) getSessionAttribute(AcademicYearDTO.SESSION_ACADEMIC_YEAR);
		List<DTOBase> semesterList = (List<DTOBase>) getSessionAttribute(SemesterDTO.SESSION_SEMESTER_LIST);
		academicYear.setName(getRequestString("txtName"));
		academicYear.setRemarks(getRequestString("txtRemarks"));
		academicYear.setDateStart(DateTimeUtil.getTimestamp(getRequestString("txtDateStart"), "MM/dd/yyyy"));
		academicYear.setDateEnd(DateTimeUtil.getTimestamp(getRequestString("txtDateEnd"), "MM/dd/yyyy"));
		academicYear.setAcademicProgramGroupCodes(getRequestString("txtAcademicProgramGroupCodes"));
		academicYear.setGradingSystem(getRequestString("txtGradingSystem"));
		
		
		System.out.println("Array : " + AcademicYearUtil.getAcademicYearCodeArrFromObjList(semesterList));
	}
	
	protected void validateInput(JSONObject jsonObj, String action) {
		AcademicYearDTO academicYear = (AcademicYearDTO) getSessionAttribute(AcademicYearDTO.SESSION_ACADEMIC_YEAR);
		List<DTOBase> semesterList = (List<DTOBase>) getSessionAttribute(SemesterDTO.SESSION_SEMESTER_LIST);
		if(action.equalsIgnoreCase(DataTable.ACTION_ADD_SAVE) || action.equalsIgnoreCase(DataTable.ACTION_UPDATE_SAVE)) {
			if(StringUtil.isEmpty(academicYear.getName())) {
				actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "Name");
			}
			else if(StringUtil.isEmpty(academicYear.getRemarks())){
				actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "Remarks");
			}
			else if(StringUtil.isEmpty(academicYear.getAcademicProgramGroupCodes())){
				actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "Academic Progam Group Code");
			}
			else if(StringUtil.isEmpty(academicYear.getGradingSystem())){
				actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "Grading System");
			}
			else if(StringUtil.isStringExistFromString(academicYear.getDateStart().toString(), academicYear.getDateEnd().toString())){
				actionResponse.constructMessage(ActionResponse.TYPE_EXIST, "Date Start");
			}
		}
	}
	
	protected void executeLogic(JSONObject jsonObj, DataTable dataTable, String action) {
		List<DTOBase> academicProgramGroupCodesList = (List<DTOBase>) getSessionAttribute(AcademicProgramGroupDTO.SESSION_ACADEMIC_PROGRAM_GROUP_LIST);
		if(action.equalsIgnoreCase(DataTable.ACTION_ADD_VIEW)) {
			AcademicYearDTO academicYear = new AcademicYearDTO();
			try {
				if(sessionInfo.isCurrentUserLinkHasFunction(LinkFunctionDTO.ACTION_ADD)) {
					jsonObj.put(DTOBase.DATA_SPECIFIC, PageUtil.getDataEntryPage(sessionInfo, AcademicYearUtil.getDataEntryStr(sessionInfo, academicYear, academicProgramGroupCodesList), action));
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			setSessionAttribute(AcademicYearDTO.SESSION_ACADEMIC_YEAR, academicYear);
		}
		else if(action.equalsIgnoreCase(DataTable.ACTION_ADD_SAVE)) {
			AcademicYearDTO academicYear = (AcademicYearDTO) getSessionAttribute(AcademicYearDTO.SESSION_ACADEMIC_YEAR);
			academicYear.setAddedBy(sessionInfo.getCurrentUser().getCode());
			AcademicYearDAO academicYearDAO = new AcademicYearDAO();
			academicYearDAO.executeAdd(academicYear);
			actionResponse = (ActionResponse) academicYearDAO.getResult().get(ActionResponse.SESSION_ACTION_RESPONSE);
			if(StringUtil.isEmpty(actionResponse.getType())) {
				actionResponse.constructMessage(ActionResponse.TYPE_SUCCESS, "Added");
				init(dataTable, jsonObj, action, academicYear);
			}
		}
		else if(action.equalsIgnoreCase(DataTable.ACTION_UPDATE_VIEW)) {
			AcademicYearDTO academicYear = (AcademicYearDTO) DTOUtil.getObjByCode(dataTable.getRecordListCurrentPage(), getRequestString("txtObjCode"));
			try {
				jsonObj.put(DTOBase.DATA_SPECIFIC, PageUtil.getDataEntryPage(sessionInfo, AcademicYearUtil.getDataEntryStr(sessionInfo, academicYear, academicProgramGroupCodesList), action));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			setSessionAttribute(AcademicYearDTO.SESSION_ACADEMIC_YEAR, academicYear);
		}
		else if(action.equalsIgnoreCase(DataTable.ACTION_UPDATE_SAVE)) {
			AcademicYearDTO academicYear = (AcademicYearDTO) getSessionAttribute(AcademicYearDTO.SESSION_ACADEMIC_YEAR);
			academicYear.setUpdatedBy(sessionInfo.getCurrentUser().getCode());
			AcademicYearDAO academicYearDAO = new AcademicYearDAO();
			academicYearDAO.executeUpdate(academicYear);
			actionResponse = (ActionResponse) academicYearDAO.getResult().get(ActionResponse.SESSION_ACTION_RESPONSE);
			if(StringUtil.isEmpty(actionResponse.getType())) {
				actionResponse.constructMessage(ActionResponse.TYPE_SUCCESS, "Updated");
				init(dataTable, jsonObj, action, academicYear);
			}
		}
		else if(action.equalsIgnoreCase(DataTable.ACTION_DELETE)) {
			AcademicYearDTO academicYear = (AcademicYearDTO) DTOUtil.getObjByCode(dataTable.getRecordListCurrentPage(), getRequestString("txtObjCode"));
			AcademicYearDAO academicYearDAO = new AcademicYearDAO();			
			academicYearDAO.executeDelete(academicYear);
			actionResponse = (ActionResponse) academicYearDAO.getResult().get(ActionResponse.SESSION_ACTION_RESPONSE);
			if(StringUtil.isEmpty(actionResponse.getType())) {
				actionResponse.constructMessage(ActionResponse.TYPE_SUCCESS, "Deleted");
				init(dataTable, jsonObj, action, academicYear);
			}
		}
		else if(action.equalsIgnoreCase(DataTable.ACTION_VIEW)) {
			List<DTOBase> academicProgramGroupList = (List<DTOBase>) getSessionAttribute(AcademicProgramGroupDTO.SESSION_ACADEMIC_PROGRAM_GROUP_LIST);
			AcademicYearDTO academicYear = (AcademicYearDTO) DTOUtil.getObjByCode(dataTable.getRecordListCurrentPage(), getRequestString("txtObjCode"));
			try {
				jsonObj.put(DTOBase.DATA_SPECIFIC, PageUtil.getDataViewPage(sessionInfo, AcademicYearUtil.getDataViewStr(sessionInfo, academicYear, academicProgramGroupList)));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			setSessionAttribute(AcademicYearDTO.SESSION_ACADEMIC_YEAR, academicYear);
		}
	}
	
	protected void search(JSONObject jsonObj, DataTable dataTable) {
		if(StringUtil.isEmpty(dataTable.getSearchValue())) {
			dataTable.setRecordListVisible();
		}
		else {
			if(dataTable.getSearchCriteria().equalsIgnoreCase(AcademicYearDTO.ACTION_SEARCH_BY_NAME)) {
				AcademicYearUtil.searchAcademicYearByName(dataTable, dataTable.getSearchValue());
			}
		}
	}
	
	protected void dataTableAction(SessionInfo sessionInfo, JSONObject jsonObj, DataTable dataTable) {
		List<DTOBase> academicProgramGroupList = (List<DTOBase>) getSessionAttribute(AcademicProgramGroupDTO.SESSION_ACADEMIC_PROGRAM_GROUP_LIST);
		try {
			jsonObj.put(DTOBase.DATA_TABLE, PageUtil.getDataTablePage(AcademicYearUtil.getDataTableStr(sessionInfo, dataTable, true, academicProgramGroupList)));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void init(DataTable dataTable, JSONObject jsonObj, String action, AcademicYearDTO academicYear) {
		setSessionAttribute(AcademicYearDTO.SESSION_ACADEMIC_YEAR, new AcademicYearDTO());
		if(sessionInfo.isCurrentUserLinkHasFunction(LinkFunctionDTO.ACTION_VIEW)) {
			dataTable.setRecordList(new AcademicYearDAO().getAcademicYearList());
			if(!action.equalsIgnoreCase(DataTable.ACTION_DELETE)) {
				dataTable.setRecordStatus(academicYear.getCode(), DTOBase.STATUS_SELECTED);
			}
			dataTableAction(sessionInfo, jsonObj, dataTable);
		}
	}

}
