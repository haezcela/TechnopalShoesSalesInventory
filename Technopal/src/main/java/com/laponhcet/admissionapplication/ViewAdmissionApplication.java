package com.laponhcet.admissionapplication;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.laponhcet.academicprogram.AcademicProgramDAO;
import com.laponhcet.academicprogram.AcademicProgramDTO;
import com.laponhcet.academicyear.AcademicYearDAO;
import com.laponhcet.semester.SemesterDAO;
import com.laponhcet.semester.SemesterDTO;
import com.laponhcet.semester.SemesterUtil;
import com.laponhcet.venue.VenueDAO;
import com.mytechnopal.SessionInfo;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.dao.BarangayDAO;
import com.mytechnopal.dao.CityDAO;
import com.mytechnopal.dao.ProvinceDAO;
import com.mytechnopal.dao.RegionDAO;
import com.mytechnopal.dao.SHSSTrandDAO;
import com.mytechnopal.dto.BarangayDTO;
import com.mytechnopal.dto.CityDTO;
import com.mytechnopal.dto.SHSStrandDTO;
import com.mytechnopal.util.BarangayUtil;
import com.mytechnopal.util.CityUtil;
import com.mytechnopal.util.DTOUtil;
import com.mytechnopal.util.ProvinceUtil;

/**
 * Servlet implementation class ViewAdmissionApplication
 */
public class ViewAdmissionApplication extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewAdmissionApplication() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String applicationReferenceCode = request.getParameter("txtCode");
		AdmissionApplicationDTO admissionApplicationByCode = new AdmissionApplicationDAO().getAdmissionApplicationByCode(applicationReferenceCode);
		if(admissionApplicationByCode == null) {
			
		}
		else {
			List<DTOBase> semesterList = SemesterUtil.getSemesterListByAcademicYearList(new SemesterDAO().getSemesterList(), new AcademicYearDAO().getAcademicYearList());
			List<DTOBase> venueList = new VenueDAO().getVenueList();
			List<DTOBase> regionList = new RegionDAO().getRegionList();
			List<DTOBase> provinceList = new ProvinceDAO().getProvinceList();
			List<DTOBase> cityList = new CityDAO().getCityList();
			
			List<DTOBase> barangayList = new BarangayDAO().getBarangayList();
			BarangayUtil.setBarangayList(barangayList, cityList);
			
			ProvinceUtil.setProvinceList(provinceList, regionList);
			CityUtil.setCityList(cityList, provinceList);
			
			List<DTOBase> shsStrandList = new SHSSTrandDAO().getSHSSTrandList();
			List<DTOBase> academicProgramList = new AcademicProgramDAO().getAcademicProgramList();
			
			
			admissionApplicationByCode.setPermanentAddressBarangay((BarangayDTO) DTOUtil.getObjByCode(barangayList, admissionApplicationByCode.getPermanentAddressBarangay().getCode()));
			admissionApplicationByCode.setPermanentAddressCity((CityDTO) DTOUtil.getObjByCode(cityList, admissionApplicationByCode.getPermanentAddressCity().getCode()));
			admissionApplicationByCode.setShsStrand((SHSStrandDTO) DTOUtil.getObjByCode(shsStrandList, admissionApplicationByCode.getShsStrand().getCode()));
			admissionApplicationByCode.setLastSchoolAttendedCity((CityDTO) DTOUtil.getObjByCode(cityList, admissionApplicationByCode.getLastSchoolAttendedCity().getCode()));
			admissionApplicationByCode.setSemester((SemesterDTO) DTOUtil.getObjByCode(semesterList, admissionApplicationByCode.getSemester().getCode()));
			admissionApplicationByCode.setAcademicProgram((AcademicProgramDTO) DTOUtil.getObjByCode(academicProgramList, admissionApplicationByCode.getAcademicProgram().getCode()));

			
			response.getWriter().append("<HTML><HEAD>");
			response.getWriter().append("<meta charset='utf-8'>");
			response.getWriter().append("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
			response.getWriter().append("<meta http-equiv='X-UA-Compatible' content='IE=edge'>");		
			response.getWriter().append("<link href='/static/inspinia/tp-icons/style.css' rel='stylesheet' />");	
			response.getWriter().append("<link href='/static/inspinia/css/custom.css' rel='stylesheet'>");
			response.getWriter().append("<link href='/static/inspinia/css/bootstrap.min.css' rel='stylesheet'>");
			response.getWriter().append("<link href='/static/inspinia/font-awesome/css/font-awesome.css' rel='stylesheet'>");
			response.getWriter().append("<link href='/static/inspinia/css/animate.css' rel='stylesheet'>");
			response.getWriter().append("<link href='/static/inspinia/css/style.css' rel='stylesheet'>");
			response.getWriter().append("<link href='/static/inspinia/footer/css/Footer-with-button-logo.css' rel='stylesheet'>");
			response.getWriter().append("<script src='common/common.js'></script>");
			response.getWriter().append("<script src='/static/inspinia/js/jquery-3.1.1.min.js'></script>");
			response.getWriter().append("<script src='/static/inspinia/js/bootstrap.min.js'></script>");
			response.getWriter().append("<script src='/static/inspinia/js/inspinia.js'></script>");
			response.getWriter().append("<script src='/static/inspinia/js/plugins/pace/pace.min.js'></script>");
			response.getWriter().append("</HEAD><BODY>");
			response.getWriter().append(AdmissionApplicationUtil.getDataViewStr(new SessionInfo(), admissionApplicationByCode));
			response.getWriter().append("</BODY></HTML>");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
