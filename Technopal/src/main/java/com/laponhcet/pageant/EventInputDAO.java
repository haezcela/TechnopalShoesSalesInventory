package com.laponhcet.pageant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.laponhcet.item.ItemDTO;
//import com.laponhcet.pageant.EventInputDTO;
import com.laponhcet.pageant.EventInputDTO;
import com.laponhcet.pageant.EventDTO;
//import com.laponhcet.person.PersonDTO;
import com.mysql.jdbc.Statement;
import com.mytechnopal.ActionResponse;
import com.mytechnopal.base.DAOBase;
import com.mytechnopal.base.DTOBase;

public class EventInputDAO extends DAOBase {
	private static final long serialVersionUID = 1L;
	private String qryEventInputAdd = "EVENT_INPUT_ADD";
	private String qryGetEventList = "EVENT_LIST";
	private String qryEventTypeList = "EVENT_TYPE_NAME";
	private String qryGetEventTypeCodeByName = "EVENT_TYPE_CODE_BY_NAME";
	private String qryEventUpdate = "EVENT_INPUT_UPDATE";
	private String qryEventDelete = "EVENT_DELETE";
	private String qryEventLast = "EVENT_LAST";
	
	@Override
	public void executeAdd(DTOBase obj) {
	EventDTO person = (EventDTO) obj;
	    String generatedCode = getGeneratedCode(qryEventLast, 3);
	    person.setCode(generatedCode);
	    person.setBaseDataOnInsert();
		
		Connection conn = daoConnectorUtil.getConnection();
		List<PreparedStatement> prepStmntList = new ArrayList<PreparedStatement>();
		add(conn, prepStmntList, person);
		result.put(ActionResponse.SESSION_ACTION_RESPONSE, executeIUD(conn, prepStmntList));
	}
	
	public void add(Connection conn, List<PreparedStatement> prepStmntList, DTOBase obj) {
		EventDTO person = (EventDTO) obj;
		PreparedStatement prepStmnt = null;
		try {
			prepStmnt = conn.prepareStatement(getQueryStatement(qryEventInputAdd), Statement.RETURN_GENERATED_KEYS);
			prepStmnt.setString(1, person.getCode());		 
			prepStmnt.setString(2, person.getEventType().getCode());
			prepStmnt.setString(3, person.getName());
			prepStmnt.setDouble(4, person.getEvent().getPercentageForFinal());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		prepStmntList.add(prepStmnt);
	}
	
	public List<EventTypeDTO> getEventTypeList() {
        Connection conn = daoConnectorUtil.getConnection();
    	List<EventTypeDTO> eventType = new ArrayList<>();
        PreparedStatement prepStmnt = null;
     
        try {
        	prepStmnt = conn.prepareStatement(getQueryStatement(qryEventTypeList), Statement.RETURN_GENERATED_KEYS);
            try (ResultSet rs = prepStmnt.executeQuery()) {
                while (rs.next()) {
                	EventTypeDTO dto = new EventTypeDTO();
                	dto.setName(rs.getString("name"));
                
                	eventType.add(dto);	
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log or handle exception properly
        }
        return eventType; // Return null if not found or an error occurs
    }

	
	 public String getEventTypeCodeByName(String eventName) {
		 String code = "";
	        try (Connection conn = daoConnectorUtil.getConnection();
	        	     PreparedStatement prepStmnt = conn.prepareStatement(getQueryStatement(qryGetEventTypeCodeByName))) {

	        	    prepStmnt.setString(1, eventName);
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
	public void executeAddList(List<DTOBase> arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void executeDelete(DTOBase arg0) {
		// TODO Auto-generated method stub
		EventDTO event = (EventDTO) arg0;
	        Connection conn = daoConnectorUtil.getConnection();
	        List<PreparedStatement> prepStmntList = new ArrayList<>();
	        delete(conn, prepStmntList, event);
	        result.put(ActionResponse.SESSION_ACTION_RESPONSE, executeIUD(conn, prepStmntList));
	    }
	    
	    public void delete(Connection conn, List<PreparedStatement> prepStmntList, DTOBase obj) {
	    	EventDTO person = (EventDTO) obj;
	        PreparedStatement prepStmnt = null;
	        try {
	            prepStmnt = conn.prepareStatement(getQueryStatement(qryEventDelete), Statement.RETURN_GENERATED_KEYS);
	            prepStmnt.setInt(1, person.getId());
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
	public void executeUpdate(DTOBase obj) {
		EventDTO person = (EventDTO) obj;
		person.setBaseDataOnUpdate();
	    Connection conn = daoConnectorUtil.getConnection();
	    List<PreparedStatement> prepStmntList = new ArrayList<>();
	    update(conn, prepStmntList, person);
	    result.put(ActionResponse.SESSION_ACTION_RESPONSE, executeIUD(conn, prepStmntList));
	}
	    public void update(Connection conn, List<PreparedStatement> prepStmntList, DTOBase obj) {
	    	EventDTO event = (EventDTO) obj;
	        PreparedStatement prepStmnt = null;
	        try {
	        	prepStmnt = conn.prepareStatement(getQueryStatement(qryEventUpdate), Statement.RETURN_GENERATED_KEYS);
	        	prepStmnt.setString(1, event.getCode());
	        	prepStmnt.setString(2, event.getEventTypeCode());
	        	prepStmnt.setString(3, event.getName());
	        	prepStmnt.setDouble(4, event.getPercentageForFinal());
	        	prepStmnt.setInt(5, event.getId());
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	       
	        prepStmntList.add(prepStmnt);
	    }

	@Override
	public void executeUpdateList(List<DTOBase> arg0) {
		// TODO Auto-generated method stub

	}
	
//	public void executeContestantListUpdateRankPreliminary(List<DTOBase> objList) {
//		Connection conn = daoConnectorUtil.getConnection();
//		List<PreparedStatement> prepStmntList = new ArrayList<PreparedStatement>();
//		for(DTOBase obj: objList) {
//			ContestantDTO contestant = (ContestantDTO) obj;
//			updateRankPreliminary(conn, prepStmntList, contestant);
//		}
//		result.put(ActionResponse.SESSION_ACTION_RESPONSE, executeIUD(conn, prepStmntList));
//	}
//	
//	public void updateRankPreliminary(Connection conn, List<PreparedStatement> prepStmntList, DTOBase obj) {
//		ContestantDTO constant = (ContestantDTO) obj;
//		PreparedStatement prepStmnt = null;
//		try {
//			prepStmnt = conn.prepareStatement(getQueryStatement(qryContestantUpdateRankPreliminary), Statement.RETURN_GENERATED_KEYS);
//			prepStmnt.setInt(1, constant.getRankPreliminary());
//			prepStmnt.setInt(2, constant.getId());
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		prepStmntList.add(prepStmnt);
//	}
//	
//
//	public List<DTOBase> getContestantList() {
//		return getDTOList(qryContestantList);
//	}
	
//	public void executeUpdateFinalist(DTOBase obj) {
//		Connection conn = daoConnectorUtil.getConnection();
//		List<PreparedStatement> prepStmntList = new ArrayList<PreparedStatement>();
//		ContestantDTO contestant = (ContestantDTO) obj;
//		updateFinalist(conn, prepStmntList, contestant);
//		result.put(ActionResponse.SESSION_ACTION_RESPONSE, executeIUD(conn, prepStmntList));
//	}
	
//	public void updateFinalist(Connection conn, List<PreparedStatement> prepStmntList, DTOBase obj) {
//		ContestantDTO contestant = (ContestantDTO) obj;
//		PreparedStatement prepStmnt = null;
//		try {
//			prepStmnt = conn.prepareStatement(getQueryStatement(qryContestantUpdateFinalist), Statement.RETURN_GENERATED_KEYS);
//			prepStmnt.setBoolean(1, contestant.isFinalist());
//			prepStmnt.setInt(2, contestant.getId());
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		prepStmntList.add(prepStmnt);
//	}

	    public List<DTOBase> getEventList() {
	        return getDTOList(qryGetEventList);
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
//	protected DTOBase rsToObj(ResultSet resultSet) {
//		EventDTO event = new EventDTO();
//		event.setId(getDBValInt(resultSet, "id"));
//		event.setCode(getDBValStr(resultSet, "code"));
//		event.getEventType().setCode(getDBValStr(resultSet, "event_type_code"));
//		event.setName(getDBValStr(resultSet, "name"));
//		event.setPercentageForFinal(getDBValDouble(resultSet, "percentage_for_final"));
//		event.setDisplayStr(event.getName());
//		//event.setBanner(getDBValStr(resultSet, "banner"));
//		return event;
//	}

	public static void main(String[] a) {
		List<DTOBase> eventList = new EventInputDAO().getEventList();
		System.out.println(eventList.size());
	}
}
