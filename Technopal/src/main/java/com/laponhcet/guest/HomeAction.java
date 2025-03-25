package com.laponhcet.guest;

import com.laponhcet.academicprogram.AcademicProgramDAO;
import com.laponhcet.academicprogram.AcademicProgramDTO;
import com.mytechnopal.banner.BannerDAO;
import com.mytechnopal.banner.BannerDTO;
import com.mytechnopal.base.ActionBase;
import com.mytechnopal.dao.BarangayDAO;
import com.mytechnopal.dao.CityDAO;
import com.mytechnopal.dao.MobileProviderDAO;
import com.mytechnopal.dao.SHSSTrandDAO;
import com.mytechnopal.dto.BarangayDTO;
import com.mytechnopal.dto.CityDTO;
import com.mytechnopal.dto.MobileProviderDTO;
import com.mytechnopal.dto.SHSStrandDTO;

public class HomeAction extends ActionBase {
	private static final long serialVersionUID = 1L;

	protected void setSessionVars() {		
		//setSessionAttribute(NewsDTO.SESSION_NEWS_LIST, new NewsDAO().getNewsList());
		setSessionAttribute(BannerDTO.SESSION_BANNER_LIST, new BannerDAO().getBannerList());
		
//		setSessionAttribute(CityDTO.SESSION_CITY_LIST, new CityDAO().getCityList());
//		setSessionAttribute(BarangayDTO.SESSION_BARANGAY_LIST, new BarangayDAO().getBarangayList());
//		setSessionAttribute(AcademicProgramDTO.SESSION_ACADEMIC_PROGRAM_LIST, new AcademicProgramDAO().getAcademicProgramList());
//		setSessionAttribute(SHSStrandDTO.SESSION_SHS_STRAND_LIST, new SHSSTrandDAO().getSHSSTrandList());
//		setSessionAttribute(MobileProviderDTO.SESSION_MOBILE_PROVIDER_LIST, new MobileProviderDAO().getMobileProviderList());
	}
}
