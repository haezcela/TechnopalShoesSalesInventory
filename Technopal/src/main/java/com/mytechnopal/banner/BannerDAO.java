package com.mytechnopal.banner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Statement;
import com.mytechnopal.ActionResponse;
import com.mytechnopal.base.DAOBase;
import com.mytechnopal.base.DTOBase;

public class BannerDAO extends DAOBase {
	private static final long serialVersionUID = 1L;
	
	private String qryBannerAdd = "BANNER_ADD";
	private String qryBannerDelete = "BANNER_DELETE";
	private String qryBannerList = "BANNER_LIST";

	@Override
	public void executeAdd(DTOBase obj) {
		BannerDTO banner = (BannerDTO) obj;
		banner.setBaseDataOnInsert();
		Connection conn = daoConnectorUtil.getConnection();
		List<PreparedStatement> prepStmntList = new ArrayList<PreparedStatement>();
		add(conn, prepStmntList, banner);
		result.put(ActionResponse.SESSION_ACTION_RESPONSE, executeIUD(conn, prepStmntList));
	}

	public void add(Connection conn, List<PreparedStatement> prepStmntList, DTOBase obj) {
		BannerDTO banner = (BannerDTO) obj;
		PreparedStatement prepStmnt = null;
		try {
			prepStmnt = conn.prepareStatement(getQueryStatement(qryBannerAdd), Statement.RETURN_GENERATED_KEYS);
			prepStmnt.setString(1, banner.getLabel());
			prepStmnt.setString(2, banner.getDescription());
			prepStmnt.setString(3, banner.getFilename());
			prepStmnt.setInt(4, banner.getDuration());
			prepStmnt.setTimestamp(5, banner.getActiveStartTimestamp());
			prepStmnt.setTimestamp(6, banner.getActiveEndTimestamp());
			prepStmnt.setString(7, banner.getAddedBy());
			prepStmnt.setTimestamp(8, banner.getAddedTimestamp());
			prepStmnt.setString(9, banner.getUpdatedBy());
			prepStmnt.setTimestamp(10, banner.getUpdatedTimestamp());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		prepStmntList.add(prepStmnt);
	}
	
	@Override
	public void executeAddList(List<DTOBase> arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void executeDelete(DTOBase obj) {
		BannerDTO banner = (BannerDTO) obj;
		Connection conn = daoConnectorUtil.getConnection();
		List<PreparedStatement> prepStmntList = new ArrayList<PreparedStatement>();
		delete(conn, prepStmntList, banner);
		result.put(ActionResponse.SESSION_ACTION_RESPONSE, executeIUD(conn, prepStmntList));
	}
	
	public void delete(Connection conn, List<PreparedStatement> prepStmntList, DTOBase obj) {
		BannerDTO banner = (BannerDTO) obj;
		PreparedStatement prepStmnt = null;
		try {
			prepStmnt = conn.prepareStatement(getQueryStatement(qryBannerDelete), Statement.RETURN_GENERATED_KEYS);
			prepStmnt.setInt(1, banner.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		prepStmntList.add(prepStmnt);
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

	public List<DTOBase> getBannerList() {
		return getDTOList(qryBannerList);
	}
	
	@Override
	protected DTOBase rsToObj(ResultSet resultSet) {
		BannerDTO banner = new BannerDTO();
		banner.setId(getDBValInt(resultSet, "id"));
		banner.setCode(String.valueOf(banner.getId()));
		banner.setLabel(getDBValStr(resultSet, "label"));
		banner.setDescription(getDBValStr(resultSet, "description"));
		banner.setFilename(getDBValStr(resultSet, "filename"));
		banner.setDuration(getDBValInt(resultSet, "duration"));
		banner.setActiveStartTimestamp(getDBValTimestamp(resultSet, "active_start_timestamp"));
		banner.setActiveEndTimestamp(getDBValTimestamp(resultSet, "active_end_timestamp"));
		return banner;
	}
}
