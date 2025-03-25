package com.laponhcet.admissionapplication;

import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

import com.laponhcet.academicprogram.AcademicProgramDTO;
import com.mytechnopal.ActionResponse;
import com.mytechnopal.DataTable;
import com.mytechnopal.base.ActionAjaxBase;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.dto.BarangayDTO;
import com.mytechnopal.dto.CityDTO;
import com.mytechnopal.dto.SHSStrandDTO;
import com.mytechnopal.link.LinkDTO;
import com.mytechnopal.util.DTOUtil;
import com.mytechnopal.util.PageUtil;
import com.mytechnopal.util.StringUtil;

public class AdmissionApplicationListActionAjax extends ActionAjaxBase {
	private static final long serialVersionUID = 1L;
	
	protected void customAction(JSONObject jsonObj, String action) {
		DataTable dataTable = (DataTable) getSessionAttribute(AdmissionApplicationDTO.SESSION_ADMISSION_APPLICATION_DATA_TABLE);
		AdmissionApplicationDTO admissionApplication = (AdmissionApplicationDTO) dataTable.getSelectedRecord();
		String statusOrig = admissionApplication.getStatus(); //get orig status and reset if update was failed
		AdmissionApplicationDAO admissionApplicationDAO = new AdmissionApplicationDAO();
		if(action.equalsIgnoreCase(AdmissionApplicationDTO.ACTION_UPDATE_APPLICATION_STATUS_EXAM_DONE)) {
			admissionApplication.setStatus(AdmissionApplicationDTO.STATUS_EXAM_DONE);
		}
		else if(action.equalsIgnoreCase(AdmissionApplicationDTO.ACTION_UPDATE_APPLICATION_STATUS_REJECT)) {
			admissionApplication.setStatus(AdmissionApplicationDTO.STATUS_APPLICATION_REJECTED);
		}
		
		admissionApplicationDAO.executeUpdate(admissionApplication);
		actionResponse = (ActionResponse) admissionApplicationDAO.getResult().get(ActionResponse.SESSION_ACTION_RESPONSE);
		if(StringUtil.isEmpty(actionResponse.getType())) {
			actionResponse.constructMessage(ActionResponse.TYPE_SUCCESS, "Updated");
		}
		else {
			admissionApplication.setStatus(statusOrig);
		}
		dataTableAction(jsonObj, dataTable);
	}

	protected void executeLogic(JSONObject jsonObj, DataTable dataTable, String action) {
		if(action.equalsIgnoreCase(DataTable.ACTION_VIEW)) {
			AdmissionApplicationDTO admissionApplication = (AdmissionApplicationDTO) dataTable.getSelectedRecord();
			try {
				jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataPrintPreview(sessionInfo, admissionApplication.getLastName() + ", " + admissionApplication.getFirstName(), AdmissionApplicationUtil.getDataViewStr(sessionInfo, admissionApplication)));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
	
	protected void search(DataTable dataTable) {
		if(StringUtil.isEmpty(dataTable.getSearchValue())) {
			dataTable.setRecordListVisible();
		}
		else {
			if(dataTable.getSearchCriteria().equalsIgnoreCase(AdmissionApplicationDTO.ACTION_SEARCH_BY_NAME)) {
				AdmissionApplicationUtil.searchAdmissionApplicationByName(dataTable, dataTable.getSearchValue());
			}
			else if(dataTable.getSearchCriteria().equalsIgnoreCase(AdmissionApplicationDTO.ACTION_SEARCH_BY_CODE)) {
				AdmissionApplicationUtil.searchAdmissionApplicationByCode(dataTable, dataTable.getSearchValue());
			}
		}
	}
	
	protected void dataTableAction(JSONObject jsonObj, DataTable dataTable) {
		initDataTable(dataTable);
		try {
			jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataTablePage(sessionInfo, sessionInfo.getCurrentLink().getLabel(), AdmissionApplicationUtil.getDataTableStr(sessionInfo, dataTable)));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void initDataTable(DataTable dataTable) {
		List<DTOBase> admissionApplicationList = (List<DTOBase>) getSessionAttribute(AdmissionApplicationDTO.SESSION_ADMISSION_APPLICATION_LIST);
		List<DTOBase> cityList = (List<DTOBase>) getSessionAttribute(CityDTO.SESSION_CITY_LIST);
		List<DTOBase> barangayList = (List<DTOBase>) getSessionAttribute(BarangayDTO.SESSION_BARANGAY_LIST);
		List<DTOBase> shsStrandList = (List<DTOBase>) getSessionAttribute(SHSStrandDTO.SESSION_SHS_STRAND_LIST);
		List<DTOBase> academicProgramList = (List<DTOBase>) getSessionAttribute(AcademicProgramDTO.SESSION_ACADEMIC_PROGRAM_LIST);
		dataTable.setRecordList(admissionApplicationList);
		dataTable.setCurrentPageRecordList();
		for(int row=0; row < dataTable.getRecordListCurrentPage().size(); row++) {	
			AdmissionApplicationDTO admissionApplication = (AdmissionApplicationDTO) dataTable.getRecordListCurrentPage().get(row);
			admissionApplication.setPermanentAddressCity((CityDTO) DTOUtil.getObjByCode(cityList, admissionApplication.getPermanentAddressCity().getCode()));
			admissionApplication.setPermanentAddressBarangay((BarangayDTO) DTOUtil.getObjByCode(barangayList, admissionApplication.getPermanentAddressBarangay().getCode()));
			admissionApplication.setLastSchoolAttendedCity((CityDTO) DTOUtil.getObjByCode(cityList, admissionApplication.getLastSchoolAttendedCity().getCode()));
			admissionApplication.setShsStrand((SHSStrandDTO) DTOUtil.getObjByCode(shsStrandList, admissionApplication.getShsStrand().getCode()));
			admissionApplication.setAcademicProgram((AcademicProgramDTO) DTOUtil.getObjByCode(academicProgramList, admissionApplication.getAcademicProgram().getCode()));
		}
	}
}
