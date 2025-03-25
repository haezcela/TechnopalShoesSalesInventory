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

public class ContestantDAO extends DAOBase {
	private static final long serialVersionUID = 1L;
	private String qryContestantList = "CONTESTANT_LIST";
	private String qryContestantUpdateRankPreliminary = "CONTESTANT_UPDATE_RANKPRELIMINARY";

	private String qryContestantAdd = "CONTESTANT_ADD";
//	private String qryPersonList = "CONTESTANT_LIST";
	private String qryContestantDelete = "CONTESTANT_DELETE";
	private String qryContestantUpdate = "CONTESTANT_UPDATE";
	private String qryContestantLast = "CONTESTANT_LAST";
	private String qryEventTypeList = "EVENT_TYPE_LIST";
	private String qryGetEventTypeCodeByName = "EVENT_TYPE_CODE_BY_NAME";

//	 protected String getGeneratedCode(String qryName) {
//	        String baseCode = "001"; 
//	        DTOBase dto = getLast(qryName); 
//	        
//	        if (dto != null && dto.getCode() != null) {
//	            String lastCode = dto.getCode();
//	            try {
//	                int nextNum = Integer.parseInt(lastCode) + 1;
//	                baseCode = String.format("%03d", nextNum);
//	            } catch (NumberFormatException e) {
//	                e.printStackTrace();
//	            }
//	        }
//	        return baseCode;
//	    }
	@Override
	public void executeAdd(DTOBase obj) {
		ContestantDTO contestant = (ContestantDTO) obj;
		String generatedCode = getGeneratedCode(qryContestantLast, 3);
		contestant.setCode(generatedCode);
		contestant.setBaseDataOnInsert();
		Connection conn = daoConnectorUtil.getConnection();
		List<PreparedStatement> prepStmntList = new ArrayList<PreparedStatement>();
		add(conn, prepStmntList, contestant);
		result.put(ActionResponse.SESSION_ACTION_RESPONSE, executeIUD(conn, prepStmntList));
	}

	public void add(Connection conn, List<PreparedStatement> prepStmntList, DTOBase obj) {
		ContestantDTO contestant = (ContestantDTO) obj;
		PreparedStatement prepStmnt = null;
//		Random random = new Random();
//        int code = 100 + random.nextInt(900); // Ensures a 3-digit number (100-999)
//        System.out.println("Random 3-Digit Code: " + code);
//        String codeString = String.valueOf(code);
		try {
			prepStmnt = conn.prepareStatement(getQueryStatement(qryContestantAdd), Statement.RETURN_GENERATED_KEYS);
			prepStmnt.setString(1, contestant.getCode());
			prepStmnt.setString(2, contestant.getName());
			prepStmnt.setInt(3, contestant.getSequence());
			prepStmnt.setString(4, contestant.getPict());

		} catch (SQLException e) {
			e.printStackTrace();
		}
		prepStmntList.add(prepStmnt);
	}

	@Override
	public void executeDelete(DTOBase obj) {
		ContestantDTO contestant = (ContestantDTO) obj;
		Connection conn = daoConnectorUtil.getConnection();
		List<PreparedStatement> prepStmntList = new ArrayList<PreparedStatement>();
		delete(conn, prepStmntList, contestant);
		result.put(ActionResponse.SESSION_ACTION_RESPONSE, executeIUD(conn, prepStmntList));
	}
	
	public void delete(Connection conn, List<PreparedStatement> prepStmntList, DTOBase obj) {
		ContestantDTO contestant = (ContestantDTO) obj;
		PreparedStatement prepStmnt = null;
		try {
			prepStmnt = conn.prepareStatement(getQueryStatement(qryContestantDelete), Statement.RETURN_GENERATED_KEYS);
			prepStmnt.setInt(1, contestant.getId());
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
        ContestantDTO contestant = (ContestantDTO) obj;
        contestant.setBaseDataOnUpdate();
        Connection conn = daoConnectorUtil.getConnection();
        List<PreparedStatement> prepStmntList = new ArrayList<>();
        update(conn, prepStmntList, contestant);
        result.put(ActionResponse.SESSION_ACTION_RESPONSE, executeIUD(conn, prepStmntList));
    }

    public void update(Connection conn, List<PreparedStatement> prepStmntList, DTOBase obj) {
        ContestantDTO contestant = (ContestantDTO) obj;
        PreparedStatement prepStmnt = null;
        try {
            // Prepare the update SQL query
        	prepStmnt = conn.prepareStatement(getQueryStatement(qryContestantUpdate), Statement.RETURN_GENERATED_KEYS);
        	prepStmnt.setString(1, contestant.getName());
        	prepStmnt.setInt(2, contestant.getSequence());                
        	prepStmnt.setString(3, contestant.getPict());                     
        	prepStmnt.setInt(4, contestant.getId());  
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Add the prepared statement to the list
        prepStmntList.add(prepStmnt);
    }

	@Override
	public void executeUpdateList(List<DTOBase> arg0) {
		// TODO Auto-generated method stub

	}

	public void executeContestantListUpdateRankPreliminary(List<DTOBase> objList) {
		Connection conn = daoConnectorUtil.getConnection();
		List<PreparedStatement> prepStmntList = new ArrayList<PreparedStatement>();
		for (DTOBase obj : objList) {
			ContestantDTO contestant = (ContestantDTO) obj;
			updateRankPreliminary(conn, prepStmntList, contestant);
		}
		result.put(ActionResponse.SESSION_ACTION_RESPONSE, executeIUD(conn, prepStmntList));
	}

	public void updateRankPreliminary(Connection conn, List<PreparedStatement> prepStmntList, DTOBase obj) {
		ContestantDTO constant = (ContestantDTO) obj;
		PreparedStatement prepStmnt = null;
		try {
			prepStmnt = conn.prepareStatement(getQueryStatement(qryContestantUpdateRankPreliminary),
					Statement.RETURN_GENERATED_KEYS);
			prepStmnt.setInt(1, constant.getRankPreliminary());
			prepStmnt.setInt(2, constant.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		prepStmntList.add(prepStmnt);
	}

	public List<DTOBase> getContestantList() {
		return getDTOList(qryContestantList);
	}

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

	@Override
	protected DTOBase rsToObj(ResultSet resultSet) {
		ContestantDTO contestant = new ContestantDTO();
		contestant.setId(getDBValInt(resultSet, "id"));
		contestant.setCode(getDBValStr(resultSet, "code"));
		contestant.setSequence(getDBValInt(resultSet, "sequence"));
		contestant.setName(getDBValStr(resultSet, "name"));
		contestant.setPict(getDBValStr(resultSet, "pict"));
		contestant.setRankPreliminary(getDBValInt(resultSet, "rank_preliminary"));
		return contestant;
	}

	public static void main(String[] a) {
		List<DTOBase> contestantList = new ContestantDAO().getContestantList();
		System.out.println(contestantList.size());
	}

	@Override
	public void executeAddList(List<DTOBase> arg0) {
		// TODO Auto-generated method stub

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

	public List<EventTypeDTO> getEventTypeList() {
		Connection conn = daoConnectorUtil.getConnection();
		List<EventTypeDTO> eventType = new ArrayList<>();
		PreparedStatement prepStmnt = null;

		try {
			prepStmnt = conn.prepareStatement(getQueryStatement(qryEventTypeList), Statement.RETURN_GENERATED_KEYS);
			try (ResultSet rs = prepStmnt.executeQuery()) {
				while (rs.next()) {
					EventTypeDTO dto = new EventTypeDTO();
					dto.setName( rs.getString("name"));

					eventType.add(dto);

				}
			}
		} catch (SQLException e) {
			e.printStackTrace(); // Log or handle exception properly
		}
		return eventType; // Return null if not found or an error occurs
	}

}
