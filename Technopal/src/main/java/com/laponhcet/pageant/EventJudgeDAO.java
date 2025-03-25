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

public class EventJudgeDAO extends DAOBase {
	private static final long serialVersionUID = 1L;

	private String qryEventJudgeList = "EVENT_JUDGE_LIST";
	private String qryJudgeAdd = "EVENT_JUDGE_ADD";
	private String qryGetEventCodeById = "EVENT_CODE_BY_ID";
	private String qryGetUserCodeById = "USER_CODE_BY_ID";
	
	@Override
	public void executeAdd(DTOBase obj) {
		EventJudgeDTO judge = (EventJudgeDTO) obj;
		judge.setBaseDataOnInsert();
		Connection conn = daoConnectorUtil.getConnection();
		List<PreparedStatement> prepStmntList = new ArrayList<PreparedStatement>();
		add(conn, prepStmntList, judge);
		result.put(ActionResponse.SESSION_ACTION_RESPONSE, executeIUD(conn, prepStmntList));
	}

	public void add(Connection conn, List<PreparedStatement> prepStmntList, DTOBase obj) {
		EventJudgeDTO judge = (EventJudgeDTO) obj;
		PreparedStatement prepStmnt = null;
//		Random random = new Random();
//        int code = 100 + random.nextInt(900); // Ensures a 3-digit number (100-999)
//        System.out.println("Random 3-Digit Code: " + code);
//        String codeString = String.valueOf(code);
		try {
			prepStmnt = conn.prepareStatement(getQueryStatement(qryJudgeAdd), Statement.RETURN_GENERATED_KEYS);
			prepStmnt.setString(1, judge.getEventCode());
			prepStmnt.setString(2, judge.getUserCode());

		} catch (SQLException e) {
			e.printStackTrace();
		}
		prepStmntList.add(prepStmnt);
	}
	 public String getEventCodeById(String id) {
		 int idInt = Integer.parseInt(id);
		 String code = "";
	        try (Connection conn = daoConnectorUtil.getConnection();
	        	     PreparedStatement prepStmnt = conn.prepareStatement(getQueryStatement(qryGetEventCodeById))) {

	        	    prepStmnt.setInt(1, idInt);
	        	    try (ResultSet rs = prepStmnt.executeQuery()) {
	        	        if (rs.next()) { 
	        	            code = rs.getString("code");
	        	        }
	        	    }
	        	} catch (SQLException e) {	
	        	    e.printStackTrace();
	        	}

			return code;

	
	    }
	 public String getUserCodeById(String id) {
		 int idInt = Integer.parseInt(id);
		 String code = "";
	        try (Connection conn = daoConnectorUtil.getConnection();
	        	     PreparedStatement prepStmnt = conn.prepareStatement(getQueryStatement(qryGetUserCodeById))) {

	        	    prepStmnt.setInt(1, idInt);
	        	    try (ResultSet rs = prepStmnt.executeQuery()) {
	        	        if (rs.next()) { 
	        	            code = rs.getString("code");
	        	        }
	        	    }
	        	} catch (SQLException e) {	
	        	    e.printStackTrace();
	        	}

			return code;

	
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
	
	public List<DTOBase> getEventJudgeList() {
		return getDTOList(qryEventJudgeList);
	}
	

	@Override
	protected DTOBase rsToObj(ResultSet rs) {
		EventJudgeDTO eventJudge = new EventJudgeDTO();
		eventJudge.setId(getDBValInt(rs, "id"));
		eventJudge.getJudge().setCode(getDBValStr(rs, "user_code"));
		eventJudge.getEvent().setCode(getDBValStr(rs, "event_code"));
		eventJudge.setUserCode(getDBValStr(rs, "user_code"));
		eventJudge.setEventCode(getDBValStr(rs, "event_code"));
		return eventJudge;
	}
	
	public static void main(String[] a) {
		System.out.println(new EventJudgeDAO().getEventJudgeList().size());
	}

	@Override
	public void executeAddList(List<DTOBase> arg0) {
		// TODO Auto-generated method stub
		
	}

}
