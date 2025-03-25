package com.laponhcet.admissionapplication;

import java.util.List;

import com.laponhcet.academicprogram.AcademicProgramDAO;
import com.laponhcet.academicprogram.AcademicProgramDTO;
import com.mytechnopal.base.ActionBase;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.dao.BarangayDAO;
import com.mytechnopal.dao.CityDAO;
import com.mytechnopal.dao.SHSSTrandDAO;
import com.mytechnopal.dto.BarangayDTO;
import com.mytechnopal.dto.CityDTO;
import com.mytechnopal.dto.SHSStrandDTO;
import com.mytechnopal.util.DTOUtil;

public class AdmissionApplicationReportAction extends ActionBase {
	private static final long serialVersionUID = 1L;

	protected void setSessionVars() {
		List<DTOBase> admissionApplicationList = new AdmissionApplicationDAO().getAdmissionApplicationListByStatusLimit(AdmissionApplicationDTO.STATUS_SUBMITTED, 440);
		List<DTOBase> cityList = new CityDAO().getCityList();
		List<DTOBase> barangayList = new BarangayDAO().getBarangayList();
		List<DTOBase> shsStrandList = new SHSSTrandDAO().getSHSSTrandList();
		List<DTOBase> academicProgramList = new AcademicProgramDAO().getAcademicProgramList();

		for(DTOBase admissionApplicationObj: admissionApplicationList) {	
			AdmissionApplicationDTO admissionApplication = (AdmissionApplicationDTO) admissionApplicationObj;
			admissionApplication.setPermanentAddressCity((CityDTO) DTOUtil.getObjByCode(cityList, admissionApplication.getPermanentAddressCity().getCode()));
			admissionApplication.setPermanentAddressBarangay((BarangayDTO) DTOUtil.getObjByCode(barangayList, admissionApplication.getPermanentAddressBarangay().getCode()));
			admissionApplication.setLastSchoolAttendedCity((CityDTO) DTOUtil.getObjByCode(cityList, admissionApplication.getLastSchoolAttendedCity().getCode()));
			admissionApplication.setShsStrand((SHSStrandDTO) DTOUtil.getObjByCode(shsStrandList, admissionApplication.getShsStrand().getCode()));
			admissionApplication.setAcademicProgram((AcademicProgramDTO) DTOUtil.getObjByCode(academicProgramList, admissionApplication.getAcademicProgram().getCode()));
		}		
		new SortLastNameAscending().sort(admissionApplicationList);
		setSessionAttribute(AdmissionApplicationDTO.SESSION_ADMISSION_APPLICATION_LIST, admissionApplicationList);
	}
}
