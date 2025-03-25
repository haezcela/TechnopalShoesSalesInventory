package com.laponhcet.itemcategory;

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

public class ItemCategoryDAO extends DAOBase {
    private static final long serialVersionUID = 1L;

    private String qryItemCategoryAdd = "ITEM_CATEGORY_ADD";
    private String qryItemCategoryDelete = "ITEM_CATEGORY_DELETE";
    private String qryItemCategoryList = "ITEM_CATEGORY_LIST";
    private String qryItemCategoryUpdate = "ITEM_CATEGORY_UPDATE";

    private String qryItemCategoryLast = "ITEM_CATEGORY_LAST"; 

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
        ItemCategoryDTO itemCategory = (ItemCategoryDTO) obj;
        String generatedCode = getGeneratedCode(qryItemCategoryLast);
        itemCategory.setCode(generatedCode);

        itemCategory.setBaseDataOnInsert();
        Connection conn = daoConnectorUtil.getConnection();
        List<PreparedStatement> prepStmntList = new ArrayList<>();
        add(conn, prepStmntList, itemCategory);
        result.put(ActionResponse.SESSION_ACTION_RESPONSE, executeIUD(conn, prepStmntList));
    }


    public void add(Connection conn, List<PreparedStatement> prepStmntList, DTOBase obj) {
    	ItemCategoryDTO itemCategory = (ItemCategoryDTO) obj;
        PreparedStatement prepStmnt = null;
        try {
            prepStmnt = conn.prepareStatement(getQueryStatement(qryItemCategoryAdd), Statement.RETURN_GENERATED_KEYS);
            prepStmnt.setString(1, itemCategory.getCode());
            prepStmnt.setString(2, itemCategory.getName());
            prepStmnt.setString(3, itemCategory.getAddedBy());
            prepStmnt.setTimestamp(4, itemCategory.getAddedTimestamp());
            prepStmnt.setString(5, itemCategory.getUpdatedBy());
            prepStmnt.setTimestamp(6, itemCategory.getUpdatedTimestamp());
            
            prepStmntList.add(prepStmnt);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

	@Override
	public void executeDelete(DTOBase obj) {
		ItemCategoryDTO itemCategory = (ItemCategoryDTO) obj;
	    Connection conn = daoConnectorUtil.getConnection();
	    List<PreparedStatement> prepStmntList = new ArrayList<>();
	    delete(conn, prepStmntList, itemCategory);
	    result.put(ActionResponse.SESSION_ACTION_RESPONSE, executeIUD(conn, prepStmntList));
	}

	public void delete(Connection conn, List<PreparedStatement> prepStmntList, DTOBase obj) {
		ItemCategoryDTO itemCategory = (ItemCategoryDTO) obj;
	    PreparedStatement prepStmnt = null;
	    try {
	        prepStmnt = conn.prepareStatement(getQueryStatement(qryItemCategoryDelete), Statement.RETURN_GENERATED_KEYS);
	        prepStmnt.setInt(1, itemCategory.getId());
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    prepStmntList.add(prepStmnt);
	}

	public List<DTOBase> getItemCategoryList() {
	    return getDTOList(qryItemCategoryList);
	}
	
	@Override
	protected DTOBase rsToObj(ResultSet resultSet) {
		ItemCategoryDTO itemCategory = new ItemCategoryDTO();
		itemCategory.setId(getDBValInt(resultSet, "id"));
		itemCategory.setCode(getDBValStr(resultSet, "code"));
		itemCategory.setName(getDBValStr(resultSet, "name"));
		itemCategory.setAddedBy(getDBValStr(resultSet, "added_by"));
		itemCategory.setAddedTimestamp(getDBValTimestamp(resultSet, "added_timestamp"));
		itemCategory.setUpdatedBy(getDBValStr(resultSet, "updated_by"));
		itemCategory.setUpdatedTimestamp(getDBValTimestamp(resultSet, "updated_timestamp"));
		itemCategory.setDisplayStr(itemCategory.getName());
	    return itemCategory;
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
		ItemCategoryDTO itemCategory = (ItemCategoryDTO) obj;
		itemCategory.setBaseDataOnUpdate();
	    Connection conn = daoConnectorUtil.getConnection();
	    List<PreparedStatement> prepStmntList = new ArrayList<>();
	    update(conn, prepStmntList, itemCategory);
	    result.put(ActionResponse.SESSION_ACTION_RESPONSE, executeIUD(conn, prepStmntList));
	}

	public void update(Connection conn, List<PreparedStatement> prepStmntList, DTOBase obj) {
		ItemCategoryDTO itemCategory = (ItemCategoryDTO) obj;
	    PreparedStatement prepStmnt = null;
	    try {
	        prepStmnt = conn.prepareStatement(getQueryStatement(qryItemCategoryUpdate), Statement.RETURN_GENERATED_KEYS);
	        prepStmnt.setString(1, itemCategory.getName());
	        prepStmnt.setString(2, itemCategory.getUpdatedBy());
	        prepStmnt.setTimestamp(3, itemCategory.getUpdatedTimestamp());
	        prepStmnt.setInt(4, itemCategory.getId());
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