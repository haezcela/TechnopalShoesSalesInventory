package com.laponhcet.academicsection;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.laponhcet.academicprogram.AcademicProgramDTO;
import com.laponhcet.enrollment.EnrollmentDAO;
import com.mytechnopal.ActionResponse;
import com.mytechnopal.DataTable;
import com.mytechnopal.SessionInfo;
import com.mytechnopal.base.ActionAjaxBase;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.util.DTOUtil;
import com.mytechnopal.util.PageUtil;
import com.mytechnopal.util.StringUtil;

public class AcademicSectionActionAjax extends ActionAjaxBase {
	private static final long serialVersionUID = 1L;

	protected void setInput(String action) {
		AcademicSectionDTO academicSection = (AcademicSectionDTO) getSessionAttribute(AcademicSectionDTO.SESSION_ACADEMIC_SECTION);
		academicSection.setYearLevel(getRequestInt("cboYearLevel"));
		academicSection.setName(getRequestString("txtName"));	
//		int adviserId = getRequestInt("cboAdviser");
//		if(adviserId > 0) {
//			List<DTOBase> teacherList = (List<DTOBase>) getSessionAttribute(TeacherDTO.SESSION_TEACHER_LIST);
//			academicSection.setAdviser((UserDTO) DTOUtil.getObjById(teacherList, adviserId));
//		}
	}
	
	protected void validateInput(JSONObject jsonObj, String action) {
		AcademicSectionDTO academicSection = (AcademicSectionDTO) getSessionAttribute(AcademicSectionDTO.SESSION_ACADEMIC_SECTION);
		List<DTOBase> academicSectionList = (List<DTOBase>) getSessionAttribute(AcademicSectionDTO.SESSION_ACADEMIC_SECTION_LIST);
		if(action.equalsIgnoreCase(DataTable.ACTION_ADD_SAVE) || action.equalsIgnoreCase(DataTable.ACTION_UPDATE_SAVE)) {
			if(academicSection.getYearLevel() == 0) {
				actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "Year Level");
			}
			else if(StringUtil.isEmpty(academicSection.getName())) {
				actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "Name");
			}
		}
		else if(action.equalsIgnoreCase(DataTable.ACTION_DELETE)) {
			AcademicSectionDTO academicSectionDTO = (AcademicSectionDTO) DTOUtil.getObjByCode(academicSectionList, academicSection.getCode());
			List<DTOBase> enrollmentListByAcademicSectionCode = new EnrollmentDAO().getEnrollmentListByAcademicSectionCode(academicSectionDTO.getCode());
			if(!enrollmentListByAcademicSectionCode.isEmpty()) {
				actionResponse.constructMessage(ActionResponse.TYPE_FAIL, "Academic Section is being used in Enrollment");
			}
			
		}
	}
	
	protected void executeLogic(JSONObject jsonObj, DataTable dataTable, String action) {
		AcademicSectionDTO academicSection = (AcademicSectionDTO) getSessionAttribute(AcademicSectionDTO.SESSION_ACADEMIC_SECTION);
		List<DTOBase> academicProgramList = (List<DTOBase>) getSessionAttribute(AcademicProgramDTO.SESSION_ACADEMIC_PROGRAM_LIST);
//		List<DTOBase> teacherList = (List<DTOBase>) getSessionAttribute(TeacherDTO.SESSION_TEACHER_LIST);
		if(action.equalsIgnoreCase(DataTable.ACTION_ADD_VIEW)) {
			try {
					jsonObj.put(DTOBase.PAGE_CONTENT, PageUtil.getDataEntryPage(sessionInfo, AcademicSectionUtil.getDataEntryStr(sessionInfo, academicSection, academicProgramList), action));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			setSessionAttribute(AcademicSectionDTO.SESSION_ACADEMIC_SECTION, new AcademicSectionDTO());
		}
		else if(action.equalsIgnoreCase(DataTable.ACTION_ADD_SAVE)) {
			AcademicSectionDTO academicSection1 = (AcademicSectionDTO) getSessionAttribute(AcademicSectionDTO.SESSION_ACADEMIC_SECTION);
			academicSection.setAddedBy(sessionInfo.getCurrentUser().getCode());
			AcademicSectionDAO academicSectionDAO = new AcademicSectionDAO();
			academicSectionDAO.executeAdd(academicSection1);
			actionResponse = (ActionResponse) academicSectionDAO.getResult().get(ActionResponse.SESSION_ACTION_RESPONSE);
			if(StringUtil.isEmpty(actionResponse.getType())) {
				actionResponse.constructMessage(ActionResponse.TYPE_SUCCESS, "Added");
				setSessionAttribute(AcademicSectionDTO.SESSION_ACADEMIC_SECTION_LIST, new AcademicSectionDAO().getAcademicSectionList());
			}
		}
		else if(action.equalsIgnoreCase(DataTable.ACTION_UPDATE_VIEW)) {
			AcademicSectionDTO academicSectionSelected = (AcademicSectionDTO) dataTable.getSelectedRecord();
			academicSection = academicSectionSelected.getAcademicSection();
			try {
				jsonObj.put(DTOBase.PAGE_CONTENT, PageUtil.getDataEntryPage(sessionInfo, AcademicSectionUtil.getDataEntryStr(sessionInfo, academicSection, academicProgramList), action));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			setSessionAttribute(AcademicSectionDTO.SESSION_ACADEMIC_SECTION, academicSection);
		}
		else if(action.equalsIgnoreCase(DataTable.ACTION_UPDATE_SAVE)) {
			academicSection.setUpdatedBy(sessionInfo.getCurrentUser().getCode());
			AcademicSectionDAO academicSectionDAO = new AcademicSectionDAO();
			academicSectionDAO.executeUpdate(academicSection);
			actionResponse = (ActionResponse) academicSectionDAO.getResult().get(ActionResponse.SESSION_ACTION_RESPONSE);
			if(StringUtil.isEmpty(actionResponse.getType())) {
				actionResponse.constructMessage(ActionResponse.TYPE_SUCCESS, "Updated");
				setSessionAttribute(AcademicSectionDTO.SESSION_ACADEMIC_SECTION_LIST, new AcademicSectionDAO().getAcademicSectionList());
			}
			dataTable.getRecordList();
		}
		else if(action.equalsIgnoreCase(DataTable.ACTION_DELETE)) {
			AcademicSectionDAO academicSectionDAO = new AcademicSectionDAO();			
			academicSectionDAO.executeDelete(academicSection);
			actionResponse = (ActionResponse) academicSectionDAO.getResult().get(ActionResponse.SESSION_ACTION_RESPONSE);
			if(StringUtil.isEmpty(actionResponse.getType())) {
				actionResponse.constructMessage(ActionResponse.TYPE_SUCCESS, "Deleted");
				setSessionAttribute(AcademicSectionDTO.SESSION_ACADEMIC_SECTION_LIST, new AcademicSectionDAO().getAcademicSectionList());
			}
		}
		else if(action.equalsIgnoreCase(DataTable.ACTION_DELETE_VIEW)) {
			academicSection = (AcademicSectionDTO) dataTable.getSelectedRecord();
			try {
				jsonObj.put(DTOBase.PAGE_CONTENT, PageUtil.getDataViewPage(sessionInfo, AcademicSectionUtil.getDataViewStr(sessionInfo, academicSection), action));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			setSessionAttribute(AcademicSectionDTO.SESSION_ACADEMIC_SECTION, academicSection);
		}
		else if(action.equalsIgnoreCase(DataTable.ACTION_VIEW)) {
			AcademicSectionDTO academicSectionSelected = (AcademicSectionDTO) dataTable.getSelectedRecord();
			academicSection = academicSectionSelected.getAcademicSection();
			try {
				jsonObj.put(DTOBase.PAGE_CONTENT, PageUtil.getDataViewPage(sessionInfo, AcademicSectionUtil.getDataViewStr(sessionInfo, academicSection), action));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			setSessionAttribute(AcademicSectionDTO.SESSION_ACADEMIC_SECTION, academicSection);
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
	
	protected void customAction(JSONObject jsonObj, String action) {
		if(action.equalsIgnoreCase(AcademicSectionDTO.ACTION_SET_ACADEMIC_PROGRAM)) {
			List<DTOBase> academicProgramList = (List<DTOBase>) getSessionAttribute(AcademicProgramDTO.SESSION_ACADEMIC_PROGRAM_LIST);
			//List<DTOBase> teacherList = (List<DTOBase>) getSessionAttribute(TeacherDTO.SESSION_TEACHER_LIST);
			AcademicSectionDTO academicSection = (AcademicSectionDTO) getSessionAttribute(AcademicSectionDTO.SESSION_ACADEMIC_SECTION);
			int academicProgramId = getRequestInt("cboAcademicProgramId");
			if (academicProgramId > 0) {
				academicSection.setAcademicProgram((AcademicProgramDTO) DTOUtil.getObjById(academicProgramList, academicProgramId));
			}
			else {
				academicSection.setAcademicProgram(new AcademicProgramDTO());
			}
			try {
				if(academicSection.getId() == 0) {
					action = DataTable.ACTION_ADD_VIEW;
				}
				else {
					action = DataTable.ACTION_UPDATE_VIEW;
				}
				jsonObj.put(DTOBase.PAGE_CONTENT, PageUtil.getDataEntryPage(sessionInfo, AcademicSectionUtil.getDataEntryStr(sessionInfo, academicSection, academicProgramList), action));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} 
	}
	
	
	protected void search(JSONObject jsonObj, DataTable dataTable) {
		if(StringUtil.isEmpty(dataTable.getSearchValue())) {
			dataTable.setRecordListVisible();
		}
		else {
			if(dataTable.getSearchCriteria().equalsIgnoreCase(AcademicSectionDTO.ACTION_SEARCH_BY_NAME)) {
				AcademicSectionUtil.searchAcademicSectionByName(dataTable, dataTable.getSearchValue());
			}
		}
	}
	
	protected void dataTableAction(SessionInfo sessionInfo, JSONObject jsonObj, DataTable dataTable) {
		initDataTable(dataTable);
		try {
			jsonObj.put(DTOBase.PAGE_CONTENT, PageUtil.getDataTablePage(AcademicSectionUtil.getDataTableStr(sessionInfo, dataTable)));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void initDataTable(DataTable dataTable) {
		List<DTOBase> academicProgramList = (List<DTOBase>) getSessionAttribute(AcademicProgramDTO.SESSION_ACADEMIC_PROGRAM_LIST);
		//List<DTOBase> teacherList = (List<DTOBase>) getSessionAttribute(TeacherDTO.SESSION_TEACHER_LIST);
		List<DTOBase> academicSectionList = (List<DTOBase>) getSessionAttribute(AcademicSectionDTO.SESSION_ACADEMIC_SECTION_LIST);
		dataTable.setRecordList(academicSectionList);
		dataTable.setCurrentPageRecordList();
		for(int row=0; row < dataTable.getRecordListCurrentPage().size(); row++) {			
			AcademicSectionDTO academicSection = (AcademicSectionDTO) dataTable.getRecordListCurrentPage().get(row);
			academicSection.setAcademicProgram((AcademicProgramDTO) DTOUtil.getObjByCode(academicProgramList, academicSection.getAcademicProgram().getCode()));
//			if(academicSection.getAdviser() != null) {
//				academicSection.setAdviser((UserDTO) DTOUtil.getObjByCode(teacherList, academicSection.getAdviser().getCode()));
//			}
		}
	}
}