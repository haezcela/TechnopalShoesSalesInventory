package com.laponhcet.mediatype;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.mytechnopal.ActionResponse;
import com.mytechnopal.base.DAOBase;
import com.mytechnopal.base.DTOBase;
import com.mysql.jdbc.Statement;

public class MediaTypeDAO extends DAOBase {
    private static final long serialVersionUID = 1L;

    private String qryMediaTypeAdd = "MEDIA_TYPE_ADD";
    private String qryMediaTypeUpdate = "MEDIA_TYPE_UPDATE";
    private String qryMediaTypeDelete = "MEDIA_TYPE_DELETE";
    private String qryMediaTypeList = "MEDIA_TYPE_LIST";
    private String qryMediaTypeLast = "MEDIA_TYPE_LAST";

    
    @Override
    public void executeAdd(DTOBase obj) {
        MediaTypeDTO mediaType = (MediaTypeDTO) obj;
//        String generatedCode = getGeneratedCode(qryMediaTypeLast, 3);
//        mediaType.setCode(generatedCode);
//        mediaType.setBaseDataOnInsert();

        Connection conn = daoConnectorUtil.getConnection();
        List<PreparedStatement> prepStmntList = new ArrayList<>();

        add(conn, prepStmntList, mediaType);
        result.put(ActionResponse.SESSION_ACTION_RESPONSE, executeIUD(conn, prepStmntList));
    }

    public void add(Connection conn, List<PreparedStatement> prepStmntList, DTOBase obj) {
        MediaTypeDTO mediaType = (MediaTypeDTO) obj;
        PreparedStatement prepStmnt = null;
        try {
            prepStmnt = conn.prepareStatement(getQueryStatement(qryMediaTypeAdd), Statement.RETURN_GENERATED_KEYS);
            prepStmnt.setString(1, mediaType.getCode());
            prepStmnt.setString(2, mediaType.getName());
            prepStmnt.setString(3, mediaType.getAddedBy());
            prepStmnt.setString(4, mediaType.getUpdatedBy());
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
    public void executeUpdate(DTOBase obj) {
        MediaTypeDTO mediaType = (MediaTypeDTO) obj;
        mediaType.setBaseDataOnUpdate();

        Connection conn = daoConnectorUtil.getConnection();
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
            prepStmnt.setInt(4, mediaType.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        prepStmntList.add(prepStmnt);
    }

    @Override
    public void executeUpdateList(List<DTOBase> arg0) {
        // TODO Auto-generated method stub
    }

    
    @Override
    public void executeDelete(DTOBase obj) {
        MediaTypeDTO mediaType = (MediaTypeDTO) obj;
        Connection conn = daoConnectorUtil.getConnection();
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

    @Override
    public void executeDeleteList(List<DTOBase> arg0) {
        // TODO Auto-generated method stub
    }

  
    public List<DTOBase> getMediaTypeList() {
        return getDTOList(qryMediaTypeList);
    }

    
    @Override
    protected DTOBase rsToObj(ResultSet resultSet) {
        MediaTypeDTO mediaType = new MediaTypeDTO();
        try {
            mediaType.setId(resultSet.getInt("id"));
            mediaType.setCode(resultSet.getString("code"));
            mediaType.setName(resultSet.getString("name"));
            mediaType.setAddedBy(resultSet.getString("added_by"));
            mediaType.setUpdatedBy(resultSet.getString("updated_by"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mediaType;
    }
}
