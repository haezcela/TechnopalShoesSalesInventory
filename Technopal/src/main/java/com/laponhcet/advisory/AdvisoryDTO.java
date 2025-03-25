package com.laponhcet.advisory;

import com.laponhcet.academicsection.AcademicSectionDTO;
import com.laponhcet.academicyear.AcademicYearDTO;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.dto.UserDTO;

public class AdvisoryDTO extends DTOBase {
	private static final long serialVersionUID = 1L;

	public static final String SESSION_ADVISORY = "SESSION_ADVISORY";
	public static final String SESSION_ADVISORY_LIST = "SESSION_ADVISORY_LIST";
	
	private AcademicYearDTO academicYear;
	private AcademicSectionDTO academicSection;
	private UserDTO user;
	
	public AdvisoryDTO() {
		super();
		academicYear = new AcademicYearDTO();
		academicSection = new AcademicSectionDTO();
		user = new UserDTO();
	}
	
	public AdvisoryDTO getAdvisory() {
		AdvisoryDTO advisory = new AdvisoryDTO();
		advisory.setId(super.getId());
		advisory.setAcademicYear(this.academicYear);
		advisory.setAcademicSection(this.academicSection);
		advisory.setUser(this.user);
		return advisory;
	}

	public AcademicYearDTO getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(AcademicYearDTO academicYear) {
		this.academicYear = academicYear;
	}

	public AcademicSectionDTO getAcademicSection() {
		return academicSection;
	}

	public void setAcademicSection(AcademicSectionDTO academicSection) {
		this.academicSection = academicSection;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}
}
