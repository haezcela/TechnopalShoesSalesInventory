package com.laponhcet.item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.laponhcet.itemcategory.ItemCategoryDTO;
import com.laponhcet.itemunit.ItemUnitDTO;
import com.mysql.jdbc.Statement;
import com.mytechnopal.ActionResponse;
import com.mytechnopal.base.DAOBase;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.dao.SettingsDAO;
import com.mytechnopal.dto.SettingsDTO;
import com.mytechnopal.util.StringUtil;

public class ItemDAO extends DAOBase {
    private static final long serialVersionUID = 1L;
    
    private String qryItemAdd = "ITEM_ADD";
    private String qryItemDelete = "ITEM_DELETE";
    private String qryItemUpdate = "ITEM_UPDATE";
    private String qryItemList = "ITEM_LIST";
    
    private String qryItemLast = "ITEM_LAST";

    protected String getGeneratedCode(String qryName) {
        String baseCode = "00001"; 
        DTOBase dto = getLast(qryName); 
        
        if (dto != null && dto.getCode() != null) {
            String lastCode = dto.getCode();
            try {
                int nextNum = Integer.parseInt(lastCode) + 1;
                baseCode = String.format("%05d", nextNum);
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
            conn = daoConnectorUtil.getConnection();
            ItemDTO  Item= (ItemDTO) obj;

            String generatedCode = getGeneratedCode(qryItemLast);
            Item.setCode(generatedCode);

            Item.setBaseDataOnInsert();
         
            add(conn, prepStmntList, Item);
            result.put(ActionResponse.SESSION_ACTION_RESPONSE, executeIUD(conn, prepStmntList));
        } finally {
            closeDB(prepStmntList, conn);
        }
    }

    private void closeDB(List<PreparedStatement> prepStmntList, Connection conn) {
		// TODO Auto-generated method stub
		
	}
	public void add(Connection conn, List<PreparedStatement> prepStmntList, DTOBase obj) {
        ItemDTO item = (ItemDTO) obj;
        
        PreparedStatement prepStmnt = null;
        try {
            prepStmnt = conn.prepareStatement(getQueryStatement(qryItemAdd), Statement.RETURN_GENERATED_KEYS);
            prepStmnt.setString(1, item.getCode());
		    prepStmnt.setString(2, item.getItemCategory().getCode());
            prepStmnt.setString(3, item.getName());
            prepStmnt.setString(4, item.getDescription());
            prepStmnt.setString(5, item.getItemUnit().getCode()); 
            prepStmnt.setDouble(6, item.getUnitPrice());
            prepStmnt.setDouble(7, item.getQuantity());
            prepStmnt.setDouble(8, item.getReorderpoint());
            prepStmnt.setString(9, item.getAddedBy());
            prepStmnt.setTimestamp(10, item.getAddedTimestamp());
            prepStmnt.setString(11, item.getUpdatedBy());
            prepStmnt.setTimestamp(12, item.getUpdatedTimestamp());
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
        ItemDTO item = (ItemDTO) obj;
        Connection conn = daoConnectorUtil.getConnection();
        List<PreparedStatement> prepStmntList = new ArrayList<>();
        delete(conn, prepStmntList, item);
        result.put(ActionResponse.SESSION_ACTION_RESPONSE, executeIUD(conn, prepStmntList));
    }
    
    public void delete(Connection conn, List<PreparedStatement> prepStmntList, DTOBase obj) {
        ItemDTO item = (ItemDTO) obj;
        PreparedStatement prepStmnt = null;
        try {
            prepStmnt = conn.prepareStatement(getQueryStatement(qryItemDelete), Statement.RETURN_GENERATED_KEYS);
            prepStmnt.setInt(1, item.getId());
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
        ItemDTO item = (ItemDTO) obj;
        item.setBaseDataOnUpdate();
        Connection conn = daoConnectorUtil.getConnection();
        List<PreparedStatement> prepStmntList = new ArrayList<>();
        update(conn, prepStmntList, item);
        result.put(ActionResponse.SESSION_ACTION_RESPONSE, executeIUD(conn, prepStmntList));
    }

    public void update(Connection conn, List<PreparedStatement> prepStmntList, DTOBase obj) {
        ItemDTO item = (ItemDTO) obj;
        PreparedStatement prepStmnt = null;
        try {
        	prepStmnt = conn.prepareStatement(getQueryStatement(qryItemUpdate), Statement.RETURN_GENERATED_KEYS);
        	prepStmnt.setString(1, item.getItemCategory().getCode());
        	prepStmnt.setString(2, item.getName());                
        	prepStmnt.setString(3, item.getDescription()); 
        	prepStmnt.setString(4, item.getItemUnit().getCode()); 
        	prepStmnt.setDouble(5, item.getUnitPrice()); 
        	prepStmnt.setDouble(6, item.getQuantity());            
        	prepStmnt.setDouble(7, item.getReorderpoint());        
        	prepStmnt.setString(8, item.getUpdatedBy());           
        	prepStmnt.setTimestamp(9, item.getUpdatedTimestamp()); 
        	prepStmnt.setInt(10, item.getId());                     

        } catch (SQLException e) {
            e.printStackTrace();
        }
        prepStmntList.add(prepStmnt);
    }

    @Override
    public void executeUpdateList(List<DTOBase> arg0) {
        // TODO Auto-generated method stub
    }

public List<DTOBase> getItemList() {
	System.out.println("Fetching item list");
	return getDTOList(qryItemList);
}

    
    @Override
    protected DTOBase rsToObj(ResultSet resultSet) {
        ItemDTO item = new ItemDTO();
        ItemCategoryDTO itemCategory = new ItemCategoryDTO();
        ItemUnitDTO itemUnit = new ItemUnitDTO();
        
        item.setId(getDBValInt(resultSet, "id"));
        item.setCode(getDBValStr(resultSet, "code"));
        
        
        itemCategory.setCode(getDBValStr(resultSet, "code"));
        itemCategory.setName(getDBValStr(resultSet, "name"));
        
        item.setName(getDBValStr(resultSet, "name"));
        item.setDescription(getDBValStr(resultSet, "description")); 
        
        
        itemUnit.setCode(getDBValStr(resultSet, "code"));
        itemUnit.setName(getDBValStr(resultSet, "name"));
        
        item.setUnitPrice(getDBValDouble(resultSet, "unit_price"));
        item.setQuantity(getDBValDouble(resultSet, "quantity"));
        item.setReorderpoint(getDBValDouble(resultSet, "reorder_point"));
        return item;
    }
    
        public static void main(String[] args) {
            ItemDAO itemDAO = new ItemDAO();
            itemDAO.testAddItem();
            System.out.println("Item added successfully.");
        }

public void testAddItem() {
    ItemDTO item = new ItemDTO();
    item.setCode("ITEM002");
    //item.setItemCategory(new ItemCategoryDTO("CAT001"));
    item.setName("Test Item");
    item.setDescription("This is a test item.");
    //item.setItemUnit(new ItemUnitDTO("UNIT001"));
    item.setUnitPrice(100.0);
    item.setQuantity(10.0);
    item.setReorderpoint(5.0);

    executeAdd(item);
}

}