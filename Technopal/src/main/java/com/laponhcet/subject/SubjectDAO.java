package com.laponhcet.subject;

import java.sql.ResultSet;
import java.util.List;

import com.mytechnopal.base.DAOBase;
import com.mytechnopal.base.DTOBase;

public class SubjectDAO extends DAOBase {
	private static final long serialVersionUID = 1L;

	private String qrySubjectList = "SUBJECT_LIST";
	
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
	
	public List<DTOBase> getSubjectList() {
		return getDTOList(qrySubjectList);
	}

	@Override
	protected DTOBase rsToObj(ResultSet resultSet) {
		SubjectDTO subject = new SubjectDTO();
		subject.setId(getDBValInt(resultSet, "id"));
		subject.setCode(getDBValStr(resultSet, "code"));
		subject.setDescription(getDBValStr(resultSet, "description"));
		subject.setCreditUnit(getDBValDouble(resultSet, "credit_unit"));
		subject.setPayUnit(getDBValDouble(resultSet, "pay_unit"));
		return subject;
	}

}
