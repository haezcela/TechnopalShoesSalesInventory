package com.laponhcet.pageant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.laponhcet.item.ItemDTO;
import com.mysql.jdbc.Statement;
import com.mytechnopal.ActionResponse;
import com.mytechnopal.base.DAOBase;
import com.mytechnopal.base.DTOBase;

public class EventCriteriaDAO extends DAOBase {
	private static final long serialVersionUID = 1L;
	
	private String qryEventCriteriaList = "EVENT_CRITERIA_LIST";
	private String qryEventList = "EVENT_LIST";
	private String qryGetEventCodeById = "EVENT_CODE_BY_ID";
	private String qryCriteriaLast = "EVENT_CRITERIA_LAST";
	private String qryCriteraAdd = "EVENT_CRITERIA_ADD";
	private String qryCriteriaDelete = "EVENT_CRITERIA_DELETE";
	private String qryCriteriaUpdate = "EVENT_CRITERIA_UPDATE";
	@Override
	public void executeAdd(DTOBase obj) {
		EventCriteriaDTO criteria = (EventCriteriaDTO) obj;
		String generatedCode = getGeneratedCode(qryCriteriaLast, 5);
		criteria.setCode(generatedCode);
		criteria.setBaseDataOnInsert();
		Connection conn = daoConnectorUtil.getConnection();
		List<PreparedStatement> prepStmntList = new ArrayList<PreparedStatement>();
		add(conn, prepStmntList, criteria);
		result.put(ActionResponse.SESSION_ACTION_RESPONSE, executeIUD(conn, prepStmntList));
	}

	public void add(Connection conn, List<PreparedStatement> prepStmntList, DTOBase obj) {
		EventCriteriaDTO criteria = (EventCriteriaDTO) obj;
		PreparedStatement prepStmnt = null;
//		Random random = new Random();
//        int code = 100 + random.nextInt(900); // Ensures a 3-digit number (100-999)
//        System.out.println("Random 3-Digit Code: " + code);
//        String codeString = String.valueOf(code);
		try {
			prepStmnt = conn.prepareStatement(getQueryStatement(qryCriteraAdd), Statement.RETURN_GENERATED_KEYS);
			prepStmnt.setString(1, criteria.getName());
			prepStmnt.setString(2, criteria.getNameShort());
			prepStmnt.setDouble(3, criteria.getScoreMax());
			prepStmnt.setString(4, criteria.getCode());
			prepStmnt.setString(5, criteria.getEventCode());
			prepStmnt.setDouble(6, criteria.getScoreMin());

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
		EventCriteriaDTO criteria = (EventCriteriaDTO) obj;
		Connection conn = daoConnectorUtil.getConnection();
		List<PreparedStatement> prepStmntList = new ArrayList<PreparedStatement>();
		delete(conn, prepStmntList, criteria);
		result.put(ActionResponse.SESSION_ACTION_RESPONSE, executeIUD(conn, prepStmntList));
	}
	
	public void delete(Connection conn, List<PreparedStatement> prepStmntList, DTOBase obj) {
		EventCriteriaDTO criteria = (EventCriteriaDTO) obj;
		PreparedStatement prepStmnt = null;
		try {
			prepStmnt = conn.prepareStatement(getQueryStatement(qryCriteriaDelete), Statement.RETURN_GENERATED_KEYS);
			prepStmnt.setInt(1, criteria.getId());
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
		EventCriteriaDTO criteria = (EventCriteriaDTO) obj;
		criteria.setBaseDataOnUpdate();
	    Connection conn = daoConnectorUtil.getConnection();
	    List<PreparedStatement> prepStmntList = new ArrayList<>();
	    update(conn, prepStmntList, criteria);
	    result.put(ActionResponse.SESSION_ACTION_RESPONSE, executeIUD(conn, prepStmntList));
	}
	    public void update(Connection conn, List<PreparedStatement> prepStmntList, DTOBase obj) {
	    	EventCriteriaDTO criteria = (EventCriteriaDTO) obj;
	        PreparedStatement prepStmnt = null;
	        try {
	        	prepStmnt = conn.prepareStatement(getQueryStatement(qryCriteriaUpdate), Statement.RETURN_GENERATED_KEYS);
	        	prepStmnt.setString(1, criteria.getName());
	        	prepStmnt.setString(2, criteria.getNameShort());
	        	prepStmnt.setString(3, criteria.getEventCode());
	        	prepStmnt.setDouble(4, criteria.getScoreMax());
	        	prepStmnt.setInt(5, criteria.getId());
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	       
	        prepStmntList.add(prepStmnt);
	    }

	@Override
	public void executeUpdateList(List<DTOBase> arg0) {
		// TODO Auto-generated method stub

	}
	
	public List<DTOBase> getEventCriteriaList() {
		return getDTOList(qryEventCriteriaList);
	}
	public List<DTOBase> getEventCriteriaList2() {
        Connection conn = daoConnectorUtil.getConnection();
    	List<DTOBase> criteriaList = new ArrayList<>();
        PreparedStatement prepStmnt = null;
     
        try {
        	prepStmnt = conn.prepareStatement(getQueryStatement(qryEventCriteriaList), Statement.RETURN_GENERATED_KEYS);
            try (ResultSet rs = prepStmnt.executeQuery()) {
                while (rs.next()) {
                	EventCriteriaDTO dto = new EventCriteriaDTO();
                	dto.setId(rs.getInt("id"));
                	dto.setName(rs.getString("name"));
                	dto.setCode(rs.getString("code"));
                	dto.setNameShort(rs.getString("name_short"));
                	dto.setScoreMax(rs.getDouble("score_max"));
                	dto.setScoreMin(rs.getDouble("score_min"));
                	dto.setEventCode(rs.getString("event_code"));
                	dto.setEventName(rs.getString("event_name"));
                	
                
                	criteriaList.add(dto);
                	
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log or handle exception properly
        }
        return criteriaList; // Return null if not found or an error occurs
    }
	
	
	public List<EventDTO> getEventList() {
        Connection conn = daoConnectorUtil.getConnection();
    	List<EventDTO> eventType = new ArrayList<>();
        PreparedStatement prepStmnt = null;
     
        try {
        	prepStmnt = conn.prepareStatement(getQueryStatement(qryEventList), Statement.RETURN_GENERATED_KEYS);
            try (ResultSet rs = prepStmnt.executeQuery()) {
                while (rs.next()) {
                	EventDTO dto = new EventDTO();
                	dto.setName(rs.getString("name"));
                	eventType.add(dto);	
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log or handle exception properly
        }
        return eventType; // Return null if not found or an error occurs
    }

	@Override
	protected DTOBase rsToObj(ResultSet resultSet) {
		EventCriteriaDTO eventCriteria = new EventCriteriaDTO();
		eventCriteria.setId(getDBValInt(resultSet, "id"));
		eventCriteria.setCode(getDBValStr(resultSet, "code"));
		eventCriteria.getEvent().setCode(getDBValStr(resultSet, "event_code"));
		eventCriteria.setName(getDBValStr(resultSet, "name"));
		eventCriteria.setNameShort(getDBValStr(resultSet, "name_short"));
		eventCriteria.setScoreMin(getDBValDouble(resultSet, "score_min"));
		eventCriteria.setScoreMax(getDBValDouble(resultSet, "score_max"));
		return eventCriteria;
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


}
