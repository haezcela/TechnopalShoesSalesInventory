package com.laponhcet.itemunit;

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

public class ItemUnitDAO extends DAOBase {
    private static final long serialVersionUID = 1L;

    private String qryItemUnitAdd = "ITEM_UNIT_ADD";
    private String qryItemUnitList = "ITEM_UNIT_LIST";
    private String qryItemUnitUpdate = "ITEM_UNIT_UPDATE";
    private String qryItemUnitDelete = "ITEM_UNIT_DELETE";
    private String qryItemUnitLast = "ITEM_UNIT_LAST";

    
    @Override
    public void executeAdd(DTOBase obj) {
        ItemUnitDTO itemUnit = (ItemUnitDTO) obj;
        String generatedCode = getGeneratedCode(qryItemUnitLast, 3);
        itemUnit.setCode(generatedCode);
        itemUnit.setBaseDataOnInsert();

        Connection conn = daoConnectorUtil.getConnection();
        List<PreparedStatement> prepStmntList = new ArrayList<>();

        add(conn, prepStmntList, itemUnit);
        result.put(ActionResponse.SESSION_ACTION_RESPONSE, executeIUD(conn, prepStmntList));
    }

    public void add(Connection conn, List<PreparedStatement> prepStmntList, DTOBase obj) {
        ItemUnitDTO itemUnit = (ItemUnitDTO) obj;
        PreparedStatement prepStmnt = null;

        try {
            prepStmnt = conn.prepareStatement(getQueryStatement(qryItemUnitAdd), Statement.RETURN_GENERATED_KEYS);
            prepStmnt.setString(1, itemUnit.getCode());
            prepStmnt.setString(2, itemUnit.getName());
            prepStmnt.setString(3, itemUnit.getAddedBy());
            prepStmnt.setString(4, itemUnit.getUpdatedBy());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        prepStmntList.add(prepStmnt);
    }

    @Override
    public void executeAddList(List<DTOBase> arg0) {
        // To be implemented if needed
    }

    @Override
    public void executeUpdate(DTOBase obj) {
        ItemUnitDTO itemUnit = (ItemUnitDTO) obj;
        itemUnit.setBaseDataOnUpdate();

        Connection conn = daoConnectorUtil.getConnection();
        List<PreparedStatement> prepStmntList = new ArrayList<>();

        update(conn, prepStmntList, itemUnit);
        result.put(ActionResponse.SESSION_ACTION_RESPONSE, executeIUD(conn, prepStmntList));
    }

    public void update(Connection conn, List<PreparedStatement> prepStmntList, DTOBase obj) {
        ItemUnitDTO itemUnit = (ItemUnitDTO) obj;
        PreparedStatement prepStmnt = null;

        try {
            prepStmnt = conn.prepareStatement(getQueryStatement(qryItemUnitUpdate), Statement.RETURN_GENERATED_KEYS);
            prepStmnt.setString(1, itemUnit.getName());
            prepStmnt.setString(2, itemUnit.getUpdatedBy());
            prepStmnt.setString(3, itemUnit.getCode());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        prepStmntList.add(prepStmnt);
    }

    @Override
    public void executeUpdateList(List<DTOBase> arg0) {
        // To be implemented if needed
    }
    
   
    @Override
    public void executeDelete(DTOBase obj) {
        ItemUnitDTO itemUnit = (ItemUnitDTO) obj;
        Connection conn = daoConnectorUtil.getConnection();
        List<PreparedStatement> prepStmntList = new ArrayList<>();

        delete(conn, prepStmntList, itemUnit);
        result.put(ActionResponse.SESSION_ACTION_RESPONSE, executeIUD(conn, prepStmntList));
    }

    public void delete(Connection conn, List<PreparedStatement> prepStmntList, DTOBase obj) {
        ItemUnitDTO itemUnit = (ItemUnitDTO) obj;
        PreparedStatement prepStmnt = null;

        try {
            prepStmnt = conn.prepareStatement(getQueryStatement(qryItemUnitDelete), Statement.RETURN_GENERATED_KEYS);
            prepStmnt.setString(1, itemUnit.getCode());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        prepStmntList.add(prepStmnt);
    }

    @Override
    public void executeDeleteList(List<DTOBase> arg0) {
        // To be implemented if needed
    }

  
    public List<DTOBase> getItemUnitList() {
        return getDTOList(qryItemUnitList);
    }

  
    @Override
    protected DTOBase rsToObj(ResultSet resultSet) {
        ItemUnitDTO itemUnit = new ItemUnitDTO();
        try {
            itemUnit.setId(resultSet.getInt("id"));
            itemUnit.setCode(resultSet.getString("code"));
            itemUnit.setName(resultSet.getString("name"));
            itemUnit.setAddedBy(resultSet.getString("added_by"));
            itemUnit.setUpdatedBy(resultSet.getString("updated_by"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return itemUnit;
    }
}
