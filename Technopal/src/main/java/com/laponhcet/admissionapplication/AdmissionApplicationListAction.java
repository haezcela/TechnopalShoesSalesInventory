package com.laponhcet.admissionapplication;

import java.util.List;

import com.laponhcet.academicprogram.AcademicProgramDAO;
import com.laponhcet.academicprogram.AcademicProgramDTO;
import com.mytechnopal.DataTable;
import com.mytechnopal.base.ActionBase;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.dao.BarangayDAO;
import com.mytechnopal.dao.CityDAO;
import com.mytechnopal.dao.SHSSTrandDAO;
import com.mytechnopal.dto.BarangayDTO;
import com.mytechnopal.dto.CityDTO;
import com.mytechnopal.dto.SHSStrandDTO;

public class AdmissionApplicationListAction extends ActionBase {
	private static final long serialVersionUID = 1L;

	protected void setSessionVars() {
		//DAOConnectorUtil dAOConnectorUtil = DAOConnectorUtil.getInstance();
		//dAOConnectorUtil.getDbSettings().setDbURL("jdbc:mysql://209.159.156.238/bcc_admission");
		
		List<DTOBase> admissionApplicationList = new AdmissionApplicationDAO().getAdmissionApplicationList();
		DataTable dataTable = new DataTable(AdmissionApplicationDTO.SESSION_ADMISSION_APPLICATION_DATA_TABLE, admissionApplicationList, new String[] {AdmissionApplicationDTO.ACTION_SEARCH_BY_NAME, AdmissionApplicationDTO.ACTION_SEARCH_BY_CODE}, new String[] {"Applicant Name", "Reference Code"});
		dataTable.setColumnNameArr(new String[] {"Reference", "Last Name", "First Name", "City/Municipality", "Applicant Type", "Prefered Program/Course", "Status", ""});
		dataTable.setColumnWidthArr(new String[] {"10", "10", "15", "15", "10", "14", "10", "16"});
		setSessionAttribute(AdmissionApplicationDTO.SESSION_ADMISSION_APPLICATION_DATA_TABLE, dataTable);
		setSessionAttribute(AdmissionApplicationDTO.SESSION_ADMISSION_APPLICATION_LIST, admissionApplicationList);
		setSessionAttribute(CityDTO.SESSION_CITY_LIST, new CityDAO().getCityList());
		setSessionAttribute(BarangayDTO.SESSION_BARANGAY_LIST, new BarangayDAO().getBarangayList());
		setSessionAttribute(AcademicProgramDTO.SESSION_ACADEMIC_PROGRAM_LIST, new AcademicProgramDAO().getAcademicProgramList());
		setSessionAttribute(SHSStrandDTO.SESSION_SHS_STRAND_LIST, new SHSSTrandDAO().getSHSSTrandList());
	}
}