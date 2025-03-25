package com.laponhcet.advisory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.laponhcet.academicsection.AcademicSectionDTO;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.dto.UserDTO;
import com.mytechnopal.util.DTOUtil;

public class AdvisoryUtil implements Serializable {
	private static final long serialVersionUID = 1L;

	public static void setAcademicSection(List<DTOBase> advisoryList, List<DTOBase> academicSectionList) {
		for(DTOBase advisoryObj: advisoryList) {
			AdvisoryDTO advisory = (AdvisoryDTO) advisoryObj;
			advisory.setAcademicSection((AcademicSectionDTO) DTOUtil.getObjByCode(academicSectionList, advisory.getAcademicSection().getCode()));
		}
	}
	
	public static void setUser(List<DTOBase> advisoryList, List<DTOBase> userList) {
		for(DTOBase advisoryObj: advisoryList) {
			AdvisoryDTO advisory = (AdvisoryDTO) advisoryObj;
			advisory.setUser((UserDTO) DTOUtil.getObjByCode(userList, advisory.getUser().getCode()));
		}
	}
	
	public static AdvisoryDTO getAdvisoryByAcademicSectionCode(List<DTOBase> advisoryList, String academicSectionCode) {
		for(DTOBase advisoryObj: advisoryList) {
			AdvisoryDTO advisory = (AdvisoryDTO) advisoryObj;
			if(advisory.getAcademicSection().getCode().equalsIgnoreCase(academicSectionCode)) {
				return advisory;
			}
		}
		return null;
	}
	
	public static List<DTOBase> getAdvisoryListByUserCode(List<DTOBase> advisoryList, String userCode) {
		List<DTOBase> list = new ArrayList<DTOBase>();
		for(DTOBase advisoryObj: advisoryList) {
			AdvisoryDTO advisory = (AdvisoryDTO) advisoryObj;
			if(advisory.getUser().getCode().equalsIgnoreCase(userCode)) {
				list.add(advisory);
			}
		}
		return list;
	}
}
