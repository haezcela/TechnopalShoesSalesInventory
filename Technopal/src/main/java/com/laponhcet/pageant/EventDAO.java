package com.laponhcet.pageant;

import java.sql.ResultSet;
import java.util.List;

import com.mytechnopal.base.DAOBase;
import com.mytechnopal.base.DTOBase;

public class EventDAO extends DAOBase {
	private static final long serialVersionUID = 1L;
	private String qryEventList = "EVENT_LIST";
	private String qryEventByCode = "EVENT_BY_CODE";
	
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

	public EventDTO getEventByCode(String code) {
		return (EventDTO) getDTO(qryEventByCode, code);
	}
	
	public List<DTOBase> getEventList() {
		return getDTOList(qryEventList);
	}
	
	@Override
	protected DTOBase rsToObj(ResultSet resultSet) {
		EventDTO event = new EventDTO();
		event.setId(getDBValInt(resultSet, "id"));
		event.setCode(getDBValStr(resultSet, "code"));
		event.getEventType().setCode(getDBValStr(resultSet, "event_type_code"));
		event.setName(getDBValStr(resultSet, "name"));
		event.setPercentageForFinal(getDBValDouble(resultSet, "percentage_for_final"));
		event.setDisplayStr(event.getName());
		//event.setBanner(getDBValStr(resultSet, "banner"));
		return event;
	}

}
