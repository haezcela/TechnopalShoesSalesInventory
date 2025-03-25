package com.laponhcet.admissionapplication;

import java.util.ArrayList;
import java.util.List;

import com.laponhcet.academicprogram.AcademicProgramDAO;
import com.laponhcet.academicprogram.AcademicProgramDTO;
import com.laponhcet.academicyear.AcademicYearDAO;
import com.laponhcet.semester.SemesterDAO;
import com.laponhcet.semester.SemesterDTO;
import com.laponhcet.semester.SemesterUtil;
import com.mytechnopal.UploadedFile;
import com.mytechnopal.base.ActionBase;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.comparator.CityNameComparator;
import com.mytechnopal.dao.BarangayDAO;
import com.mytechnopal.dao.CityDAO;
import com.mytechnopal.dao.MobileProviderDAO;
import com.mytechnopal.dao.ProvinceDAO;
import com.mytechnopal.dao.RegionDAO;
import com.mytechnopal.dao.SHSSTrandDAO;
import com.mytechnopal.dto.BarangayDTO;
import com.mytechnopal.dto.CityDTO;
import com.mytechnopal.dto.MobileProviderDTO;
import com.mytechnopal.dto.SHSStrandDTO;
import com.mytechnopal.util.BarangayUtil;
import com.mytechnopal.util.CityUtil;
import com.mytechnopal.util.DTOUtil;
import com.mytechnopal.util.DateTimeUtil;
import com.mytechnopal.util.ProvinceUtil;

public class AdmissionApplicationAction extends ActionBase {
	private static final long serialVersionUID = 1L;
	
	protected void setSessionVars() {
		List<DTOBase> semesterList = SemesterUtil.getSemesterListByAcademicYearList(new SemesterDAO().getSemesterList(), new AcademicYearDAO().getAcademicYearList());
		List<DTOBase> regionList = new RegionDAO().getRegionList();
		List<DTOBase> provinceList = new ProvinceDAO().getProvinceList();
		List<DTOBase> cityList = new CityDAO().getCityList();
		new CityNameComparator().sort(cityList);
		
		List<DTOBase> barangayList = new BarangayDAO().getBarangayList();
		BarangayUtil.setBarangayList(barangayList, cityList);
		
		ProvinceUtil.setProvinceList(provinceList, regionList);
		CityUtil.setCityList(cityList, provinceList);
		
		AdmissionApplicationDTO admissionApplication = new AdmissionApplicationDTO();
		admissionApplication.setDateOfBirth(DateTimeUtil.addYear(DateTimeUtil.getCurrentTimestamp(), -18));
		admissionApplication.setSemester((SemesterDTO) DTOUtil.getObjByCode(semesterList, "040")); // First Sem of AY 2023-2024
		
		setSessionAttribute(MobileProviderDTO.SESSION_MOBILE_PROVIDER_LIST, new MobileProviderDAO().getMobileProviderList());
		setSessionAttribute(CityDTO.SESSION_CITY_LIST, cityList);
		setSessionAttribute(BarangayDTO.SESSION_BARANGAY_LIST, barangayList);
		setSessionAttribute(BarangayDTO.SESSION_BARANGAY_LIST + "_BY_CITY", new ArrayList<DTOBase>());
		setSessionAttribute(AcademicProgramDTO.SESSION_ACADEMIC_PROGRAM_LIST, new AcademicProgramDAO().getAcademicProgramList());
		setSessionAttribute(SHSStrandDTO.SESSION_SHS_STRAND_LIST, new SHSSTrandDAO().getSHSSTrandList());
		setSessionAttribute(AdmissionApplicationDTO.SESSION_ADMISSION_APPLICATION, admissionApplication);
		setSessionAttribute(SemesterDTO.SESSION_SEMESTER_LIST , semesterList);
		
		UploadedFile uploadedFilePict = new UploadedFile(); //no need to specify id for the default id is 0
		uploadedFilePict.setSettings(sessionInfo.getSettings());
		uploadedFilePict.setValidFileExt(new String[] {"png", "jpg"});
		uploadedFilePict.setMaxSize(1024000); //1Mb 
		setSessionAttribute(UploadedFile.SESSION_UPLOADED_FILE + "_0", uploadedFilePict);	
		
		UploadedFile uploadedFileSignatureApplicant = new UploadedFile(); 
		uploadedFileSignatureApplicant.setSettings(sessionInfo.getSettings());
		uploadedFileSignatureApplicant.setId(1);
		uploadedFileSignatureApplicant.setValidFileExt(new String[] {"png", "jpg"});
		uploadedFileSignatureApplicant.setMaxSize(1024000); //1Mb 
		setSessionAttribute(UploadedFile.SESSION_UPLOADED_FILE + "_1", uploadedFileSignatureApplicant);	
		
		UploadedFile uploadedFileSignatureGuardian = new UploadedFile(); 
		uploadedFileSignatureGuardian.setSettings(sessionInfo.getSettings());
		uploadedFileSignatureGuardian.setId(2);
		uploadedFileSignatureGuardian.setValidFileExt(new String[] {"png", "jpg"});
		uploadedFileSignatureGuardian.setMaxSize(1024000); //1Mb 
		setSessionAttribute(UploadedFile.SESSION_UPLOADED_FILE + "_2", uploadedFileSignatureGuardian);	
	}
}