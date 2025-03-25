package com.laponhcet.itemunit;

import java.sql.Connection;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.laponhcet.itemcategory.ItemCategoryDTO;
import com.mysql.jdbc.Statement;
import com.mytechnopal.ActionResponse;
import com.mytechnopal.base.DAOBase;
import com.mytechnopal.base.DTOBase;

public class ItemUnitDAO extends DAOBase {
    private static final long serialVersionUID = 1L;

    private String qryItemUnitAdd = "ITEM_UNIT_ADD";
    private String qryItemUnitDelete = "ITEM_UNIT_DELETE";
    private String qryItemUnitList = "ITEM_UNIT_LIST";
    private String qryItemUnitUpdate = "ITEM_UNIT_UPDATE";

    private String qryItemUnitLast = "ITEM_UNIT_LAST"; 

    protected String getGeneratedCode(String qryName) {
        String baseCode = "001"; 
        DTOBase dto = getLast(qryName); 
        
        if (dto != null && dto.getCode() != null) {
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
    	ItemUnitDTO itemUnit = (ItemUnitDTO) obj;
    	 String generatedCode = getGeneratedCode(qryItemUnitLast);
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
            prepStmnt.setTimestamp(4, itemUnit.getAddedTimestamp());
            prepStmnt.setString(5, itemUnit.getUpdatedBy());
            prepStmnt.setTimestamp(6, itemUnit.getUpdatedTimestamp());
            
            prepStmntList.add(prepStmnt);
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
	        prepStmnt.setInt(1, itemUnit.getId());
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    prepStmntList.add(prepStmnt);
	}

	public List<DTOBase> getItemUnitList() {
	    return getDTOList(qryItemUnitList);
	}
	
	@Override
	protected DTOBase rsToObj(ResultSet resultSet) {
		ItemUnitDTO itemUnit = new ItemUnitDTO();
		itemUnit.setId(getDBValInt(resultSet, "id"));
		itemUnit.setCode(getDBValStr(resultSet, "code"));
		itemUnit.setName(getDBValStr(resultSet, "name"));
		itemUnit.setAddedBy(getDBValStr(resultSet, "added_by"));
		itemUnit.setAddedTimestamp(getDBValTimestamp(resultSet, "added_timestamp"));
		itemUnit.setUpdatedBy(getDBValStr(resultSet, "updated_by"));
		itemUnit.setUpdatedTimestamp(getDBValTimestamp(resultSet, "updated_timestamp"));
		itemUnit.setDisplayStr(itemUnit.getName());
	    return itemUnit;
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
	        prepStmnt.setTimestamp(3, itemUnit.getUpdatedTimestamp());
	        prepStmnt.setInt(4, itemUnit.getId());
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    prepStmntList.add(prepStmnt);
	}

	@Override
	public void executeUpdateList(List<DTOBase> arg0) {
		// TODO Auto-generated method stub
		
	}
}