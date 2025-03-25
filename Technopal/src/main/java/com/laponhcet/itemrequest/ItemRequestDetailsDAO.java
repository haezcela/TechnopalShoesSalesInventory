package com.laponhcet.itemrequest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.mysql.jdbc.Statement;
import com.mytechnopal.ActionResponse;
import com.mytechnopal.base.DAOBase;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.dao.SettingsDAO;
import com.mytechnopal.dto.SettingsDTO;
import com.mytechnopal.util.StringUtil;

public class ItemRequestDetailsDAO extends DAOBase {
    private static final long serialVersionUID = 1L;
    
    private String qryItemAdd = "ITEM_REQUEST_ADD";
    private String qryItemDelete = "ITEM_REQUEST_DELETE";
    private String qryItemUpdate = "ITEM_REQUEST_UPDATE";
    private String qryItemList = "ITEM_REQUEST_LIST";
    
    private String qryItemLast = "ITEM_REQUEST_LAST";
    
//    private String qryItemAdd = "ITEM_ADD";
//    private String qryItemDelete = "ITEM_DELETE";
//    private String qryItemUpdate = "ITEM_UPDATE";
//    private String qryItemList = "ITEM_LIST";
//    
//    private String qryItemLast = "ITEM_LAST";

    protected String getGeneratedCode(String qryName) {
        String baseCode = "0000000000000001"; // Default base numeric code (3 digits to fit within 16 characters)
        DTOBase dto = getLast(qryName);

        if (dto != null && dto.getCode() != null) {
            String lastCode = dto.getCode().substring(12, 15); // Extract the numeric portion (3 digits)
            try {
                int nextNum = Integer.parseInt(lastCode) + 1;
                baseCode = String.format("%16d", nextNum); 
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        // Get the current date in MMDDYYYYHHmm format (12 characters)
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMddyyyyHHmm");
        String currentDateTime = dateFormat.format(new Date());

        // Generate two random alphabetic characters
        String randomAlpha = getRandomAlphabetic(2);

        // Concatenate date, base code, and random alpha to ensure 16 characters
        return currentDateTime + baseCode + randomAlpha;
    }

    private String getRandomAlphabetic(int length) {
        Random random = new Random();
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < length; i++) {
            char randomChar = (char) ('A' + random.nextInt(26)); // Generate random uppercase alphabet
            builder.append(randomChar);
        }

        return builder.toString();
    }

    @Override
    public void executeAdd(DTOBase obj) {
        Connection conn = null;
        List<PreparedStatement> prepStmntList = new ArrayList<>();
        try {
            conn = daoConnectorUtil.getConnection();
            ItemRequestDTO  Item= (ItemRequestDTO) obj;

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
        ItemRequestDetailsDTO item = (ItemRequestDetailsDTO) obj;
        PreparedStatement prepStmnt = null;
        try {
            prepStmnt = conn.prepareStatement(getQueryStatement(qryItemAdd), Statement.RETURN_GENERATED_KEYS);
            prepStmnt.setString(1, item.getCode());
            prepStmnt.setString(2, item.getItemCode());
            prepStmnt.setDouble(3, item.getQuantity());
//            prepStmnt.setString(5, item.getUnit());
//            prepStmnt.setDouble(6, item.getQuantity());
//            prepStmnt.setDouble(7, item.getReorderpoint());
           
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
    	ItemRequestDetailsDTO item = ( ItemRequestDetailsDTO) obj;
        Connection conn = daoConnectorUtil.getConnection();
        List<PreparedStatement> prepStmntList = new ArrayList<>();
        delete(conn, prepStmntList, item);
        result.put(ActionResponse.SESSION_ACTION_RESPONSE, executeIUD(conn, prepStmntList));
    }
    
    public void delete(Connection conn, List<PreparedStatement> prepStmntList, DTOBase obj) {
    	ItemRequestDetailsDTO item = ( ItemRequestDetailsDTO) obj;
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
    	ItemRequestDetailsDTO item = ( ItemRequestDetailsDTO) obj;
        item.setBaseDataOnUpdate();
        Connection conn = daoConnectorUtil.getConnection();
        List<PreparedStatement> prepStmntList = new ArrayList<>();
        update(conn, prepStmntList, item);
        result.put(ActionResponse.SESSION_ACTION_RESPONSE, executeIUD(conn, prepStmntList));
    }

    public void update(Connection conn, List<PreparedStatement> prepStmntList, DTOBase obj) {
    	 ItemRequestDetailsDTO item = ( ItemRequestDetailsDTO) obj;
        PreparedStatement prepStmnt = null;
        try {
            // Prepare the update SQL query
        	prepStmnt = conn.prepareStatement(getQueryStatement(qryItemUpdate), Statement.RETURN_GENERATED_KEYS);

        
			// Set the parameters for the prepared statement
        	prepStmnt.setString(1, item.getItemCode());
        	prepStmnt.setDouble(2, item.getQuantity());                
//        	prepStmnt.setString(3, item.getAddedBy()); 
//      		prepStmnt.setTimestamp(4, item.getAddedTimestamp());
////        	prepStmnt.setDouble(5, item.getQuantity());            
////        	prepStmnt.setDouble(6, item.getReorderpoint());        
//        	prepStmnt.setString(5, item.getUpdatedBy());           
//        	prepStmnt.setTimestamp(6, item.getUpdatedTimestamp()); 
        	prepStmnt.setInt(3, item.getId());                     

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

    public List<DTOBase> getItemList() {
        return getDTOList(qryItemList);
    }
    
    @Override
    protected DTOBase rsToObj(ResultSet resultSet) {
    	 ItemRequestDetailsDTO item = new  ItemRequestDetailsDTO();
        item.setId(getDBValInt(resultSet, "id"));
        item.setCode(getDBValStr(resultSet, "item_request_code"));
        item.setItemCode(getDBValStr(resultSet, "item_code"));
 
       // item.setOfficeCode(getDBValStr(resultSet, "office_code"));
        //item.setUnit(getDBValStr(resultSet, "item_unit_code"));
        item.setQuantity(getDBValDouble(resultSet, "quantity"));
//        item.setReorderpoint(getDBValDouble(resultSet, "reorder_point"));
//       
        return item;
    }
}
