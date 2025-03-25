package com.laponhcet.advisory;

import java.sql.ResultSet;
import java.util.List;

import com.mytechnopal.base.DAOBase;
import com.mytechnopal.base.DTOBase;

public class AdvisoryDAO extends DAOBase {

	private static final long serialVersionUID = 1L;

	private String qryAdvisoryListByAcademicYearCode = "ADVISORY_LIST_BY_ACADEMICYEARCODE";
	private String qryAdvisoryListByUserCode = "ADVISORY_LIST_BY_USERCODE";
	
	@Override
	public void executeAdd(DTOBase arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void executeAddList(List<DTOBase> arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void executeDelete(DTOBase arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void executeDeleteList(List<DTOBase> arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void executeUpdate(DTOBase arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void executeUpdateList(List<DTOBase> arg0) {
		// TODO Auto-generated method stub

	}

	public List<DTOBase> getAdvisoryListByAcademicYearCode(String academicYearCode) {
		return getDTOList(qryAdvisoryListByAcademicYearCode, academicYearCode);
	}
	
	public List<DTOBase> getAdvisoryListByUserCode(String userCode) {
		return getDTOList(qryAdvisoryListByUserCode, userCode);
	}
	
	@Override
	protected DTOBase rsToObj(ResultSet resultSet) {
		AdvisoryDTO advisory = new AdvisoryDTO();
		advisory.setId(getDBValInt(resultSet, "id"));
		advisory.getAcademicYear().setCode(getDBValStr(resultSet, "academic_year_code"));
		advisory.getAcademicSection().setCode(getDBValStr(resultSet, "academic_section_code"));
		advisory.getUser().setCode(getDBValStr(resultSet, "user_code"));
		return advisory;
	}

	
	public static void main(String[] a) {
		AdvisoryDAO advisoryDAO = new AdvisoryDAO();
		System.out.println(advisoryDAO.getAdvisoryListByUserCode("000000000001").size());
	}
}
