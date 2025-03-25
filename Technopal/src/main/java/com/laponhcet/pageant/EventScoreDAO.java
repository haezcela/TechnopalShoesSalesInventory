package com.laponhcet.pageant;

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

public class EventScoreDAO extends DAOBase {
	private static final long serialVersionUID = 1L;

	private String qryEventScoreAdd = "EVENT_SCORE_ADD";
	private String qryEventScoreUpdateScore = "EVENT_SCORE_UPDATE_SCORE";
	private String qryEventScoreList = "EVENT_SCORE_LIST";
	
	@Override
	public void executeAdd(DTOBase obj) {
		
	}
	
	public void executeSubmitScore(DTOBase obj) {
		Connection conn = daoConnectorUtil.getConnection();
		List<PreparedStatement> prepStmntList = new ArrayList<PreparedStatement>();
		EventScoreDTO eventScore = (EventScoreDTO) obj;
		if(eventScore.getRecordStatus().equalsIgnoreCase(DTOBase.RECORD_STATUS_FOR_ADDITION)) {
			add(conn, prepStmntList, eventScore);
		}
		else if(eventScore.getRecordStatus().equalsIgnoreCase(DTOBase.RECORD_STATUS_FOR_UPDATE)) {
			updateScore(conn, prepStmntList, eventScore);
		}
		result.put(ActionResponse.SESSION_ACTION_RESPONSE, executeIUD(conn, prepStmntList));
	}

	public void add(Connection conn, List<PreparedStatement> prepStmntList, DTOBase obj) {
		EventScoreDTO eventScore = (EventScoreDTO) obj;
		PreparedStatement prepStmnt = null;
		try {
			prepStmnt = conn.prepareStatement(getQueryStatement(qryEventScoreAdd), Statement.RETURN_GENERATED_KEYS);
			prepStmnt.setString(1, eventScore.getJudge().getCode());
			prepStmnt.setString(2, eventScore.getContestant().getCode());
			prepStmnt.setString(3, eventScore.getEvent().getCode());
			prepStmnt.setString(4, eventScore.getEventCriteria().getCode());
			prepStmnt.setDouble(5, eventScore.getScore());
			prepStmnt.setTimestamp(6, eventScore.getActiveEndTimestamp());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		prepStmntList.add(prepStmnt);
	}
	
	public void updateScore(Connection conn, List<PreparedStatement> prepStmntList, DTOBase obj) {
		EventScoreDTO eventScore = (EventScoreDTO) obj;
		PreparedStatement prepStmnt = null;
		try {
			prepStmnt = conn.prepareStatement(getQueryStatement(qryEventScoreUpdateScore), Statement.RETURN_GENERATED_KEYS);
			prepStmnt.setDouble(1, eventScore.getScore());
			prepStmnt.setTimestamp(2, eventScore.getUpdatedTimestamp());
			prepStmnt.setInt(3, eventScore.getId());
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
	
	public List<DTOBase> getEventScoreList() {
		return getDTOList(qryEventScoreList);
	}

	@Override
	protected DTOBase rsToObj(ResultSet resultSet) {
		EventScoreDTO eventScore = new EventScoreDTO();
		eventScore.setId(getDBValInt(resultSet, "id"));
		eventScore.getJudge().setCode(getDBValStr(resultSet, "judge_code"));
		eventScore.getContestant().setCode(getDBValStr(resultSet, "contestant_code"));
		eventScore.getEvent().setCode(getDBValStr(resultSet, "event_code"));
		eventScore.getEventCriteria().setCode(getDBValStr(resultSet, "event_criteria_code"));
		eventScore.setScore(getDBValDouble(resultSet, "score"));
		return eventScore;
	}

}
