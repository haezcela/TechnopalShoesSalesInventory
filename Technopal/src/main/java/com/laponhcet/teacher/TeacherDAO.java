package com.laponhcet.teacher;

import java.sql.ResultSet;
import java.util.List;

import com.mytechnopal.base.DAOBase;
import com.mytechnopal.base.DTOBase;

public class TeacherDAO extends DAOBase {

	private static final long serialVersionUID = 1L;

	private String qryTeacherList = "TEACHER_LIST";
	
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

	public List<DTOBase> getTeacherList() {
		return getDTOList(qryTeacherList);
	}
	
	@Override
	protected DTOBase rsToObj(ResultSet resultSet) {
		TeacherDTO teacher = new TeacherDTO();
		teacher.setId(getDBValInt(resultSet, "id"));
		teacher.setCode(getDBValStr(resultSet, "user_code"));
		teacher.setDateHired(getDBValDate(resultSet, "date_hired"));
		teacher.getTeacherStatus().setCode(getDBValStr(resultSet, "teacher_status_code"));
		return teacher;
	}

	
	public static void main(String[] a) {
		System.out.println(new TeacherDAO().getTeacherList().size());
	}
}
