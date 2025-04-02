
package com.laponhcet.mediatype;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.laponhcet.itemcategory.ItemCategoryDTO;
import com.laponhcet.vehicle.VehicleDTO;
import com.mysql.jdbc.Statement;
import com.mytechnopal.ActionResponse;
import com.mytechnopal.base.DAOBase;
import com.mytechnopal.base.DTOBase;

public class MediaTypeDAO extends DAOBase {
    private static final long serialVersionUID = 1L;

    private String qryMediaTypeAdd = "MEDIA_TYPE_ADD";
    private String qryMediaTypeUpdate = "MEDIA_TYPE_UPDATE";
    private String qryMediaTypeDelete = "MEDIA_TYPE_DELETE";
    private String qryMediaTypeList = "MEDIA_TYPE_LIST";
    private String qryMediaTypeLast = "MEDIA_TYPE_LAST";

    protected String getGeneratedCode(String qryName) {
        String baseCode = "001";
        DTOBase dto = getLast(qryName);

        if (dto != null && dto.getCode() != null && !dto.getCode().isEmpty()) {
            String lastCode = dto.getCode();
            try {
                int nextNum = Integer.parseInt(lastCode) + 1;
                baseCode = String.format("%03d", nextNum);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return baseCode;
    }

    @Override
    public void executeAdd(DTOBase obj) {
        Connection conn = null;
        List<PreparedStatement> prepStmntList = new ArrayList<>();
        try {
            conn = (Connection) daoConnectorUtil.getConnection();
            MediaTypeDTO mediaType = (MediaTypeDTO) obj;

            String generatedCode = getGeneratedCode(qryMediaTypeLast);
            mediaType.setCode(generatedCode);

            mediaType.setBaseDataOnInsert();

            add(conn, prepStmntList, mediaType);
            result.put(ActionResponse.SESSION_ACTION_RESPONSE, executeIUD(conn, prepStmntList));
        } finally {
            closeDB(prepStmntList, conn);
        }
    }

    private void closeDB(List<PreparedStatement> prepStmntList, Connection conn) {
        for (PreparedStatement prepStmnt : prepStmntList) {
            if (prepStmnt != null) {
                try {
                    prepStmnt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void add(Connection conn, List<PreparedStatement> prepStmntList, DTOBase obj) {
        MediaTypeDTO mediaType = (MediaTypeDTO) obj;
        PreparedStatement prepStmnt = null;
        try {
        	prepStmnt = conn.prepareStatement(getQueryStatement(qryMediaTypeAdd), Statement.RETURN_GENERATED_KEYS);
            prepStmnt.setString(1, mediaType.getCode());
            prepStmnt.setString(2, mediaType.getName());
            prepStmnt.setString(3, mediaType.getAddedBy());
            prepStmnt.setTimestamp(4, mediaType.getAddedTimestamp());
            prepStmnt.setString(5, mediaType.getUpdatedBy());
            prepStmnt.setTimestamp(6, mediaType.getUpdatedTimestamp());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        prepStmntList.add(prepStmnt);
    }

    @Override
    public void executeUpdate(DTOBase obj) {
        MediaTypeDTO mediaType = (MediaTypeDTO) obj;
        mediaType.setBaseDataOnUpdate();
        Connection conn = (Connection) daoConnectorUtil.getConnection();
        List<PreparedStatement> prepStmntList = new ArrayList<>();
        update(conn, prepStmntList, mediaType);
        result.put(ActionResponse.SESSION_ACTION_RESPONSE, executeIUD(conn, prepStmntList));
    }

    public void update(Connection conn, List<PreparedStatement> prepStmntList, DTOBase obj) {
        MediaTypeDTO mediaType = (MediaTypeDTO) obj;
        PreparedStatement prepStmnt = null;
        try {
        	prepStmnt = conn.prepareStatement(getQueryStatement(qryMediaTypeUpdate), Statement.RETURN_GENERATED_KEYS);
            prepStmnt.setString(1, mediaType.getCode());
            prepStmnt.setString(2, mediaType.getName());
            prepStmnt.setString(3, mediaType.getUpdatedBy());
            prepStmnt.setTimestamp(4, mediaType.getUpdatedTimestamp());
            prepStmnt.setInt(5, mediaType.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        prepStmntList.add(prepStmnt);
    }

    @Override
    public void executeDelete(DTOBase obj) {
        MediaTypeDTO mediaType = (MediaTypeDTO) obj;
        Connection conn = (Connection) daoConnectorUtil.getConnection();
        List<PreparedStatement> prepStmntList = new ArrayList<>();
        delete(conn, prepStmntList, mediaType);
        result.put(ActionResponse.SESSION_ACTION_RESPONSE, executeIUD(conn, prepStmntList));
    }

    public void delete(Connection conn, List<PreparedStatement> prepStmntList, DTOBase obj) {
        MediaTypeDTO mediaType = (MediaTypeDTO) obj;
        PreparedStatement prepStmnt = null;
        try {
        	prepStmnt = conn.prepareStatement(getQueryStatement(qryMediaTypeDelete), Statement.RETURN_GENERATED_KEYS);
            prepStmnt.setInt(1, mediaType.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        prepStmntList.add(prepStmnt);
    }

    public List<DTOBase> getMediaTypeList() {
	    return getDTOList(qryMediaTypeList);
	}

    @Override
    public void executeAddList(List<DTOBase> arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void executeDeleteList(List<DTOBase> arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void executeUpdateList(List<DTOBase> arg0) {
        // TODO Auto-generated method stub
    }


    

	@Override
	protected DTOBase rsToObj(ResultSet resultSet) {
		MediaTypeDTO mediaType = new MediaTypeDTO();
		mediaType.setId(getDBValInt(resultSet, "id"));
		mediaType.setCode(getDBValStr(resultSet, "code"));
		mediaType.setName(getDBValStr(resultSet, "name"));
		mediaType.setAddedBy(getDBValStr(resultSet, "added_by"));
		mediaType.setAddedTimestamp(getDBValTimestamp(resultSet, "added_timestamp"));
		mediaType.setUpdatedBy(getDBValStr(resultSet, "updated_by"));
		mediaType.setUpdatedTimestamp(getDBValTimestamp(resultSet, "updated_timestamp"));
		mediaType.setDisplayStr(mediaType.getName());
	    return mediaType;
	}
	
	public static void main(String[] args) {
        testCRUD();
    }
	
	public static void testCRUD() {
	    MediaTypeDAO mediaTypeDAO = new MediaTypeDAO();

	    // Fetch and print all media type names
	    List<DTOBase> mediaTypeList = mediaTypeDAO.getMediaTypeList();
	    System.out.println("List of Media Type Names:");
	    for (DTOBase dto : mediaTypeList) {
	        MediaTypeDTO mediaType = (MediaTypeDTO) dto;
	        System.out.println(mediaType.getName());
	    }
	}

}
