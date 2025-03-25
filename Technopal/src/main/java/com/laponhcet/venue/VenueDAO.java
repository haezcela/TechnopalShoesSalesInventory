package com.laponhcet.venue;

import java.sql.ResultSet;
import java.util.List;

import com.mytechnopal.base.DAOBase;
import com.mytechnopal.base.DTOBase;

public class VenueDAO extends DAOBase {

	private static final long serialVersionUID = 1L;
	private String qryVenueList = "VENUE_LIST";

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

	public List<DTOBase> getVenueList() {
		return getDTOList(qryVenueList);
	}
	
	@Override
	protected DTOBase rsToObj(ResultSet resultSet) {
		VenueDTO venue = new VenueDTO();
		venue.setId(getDBValInt(resultSet, "id"));
		venue.setCode(getDBValStr(resultSet, "code"));
		venue.setName(getDBValStr(resultSet, "name"));
		venue.setLocation(getDBValStr(resultSet, "location"));
		venue.setConcurrentSession(getDBValInt(resultSet, "concurrent_session"));
		venue.setMaxPax(getDBValInt(resultSet, "max_pax"));
		venue.setDisplayStr(venue.getName());
		return venue;
	}

}
