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

public class ItemRequestDAO extends DAOBase {
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
        String baseCode = "001"; // Default base numeric code (3 digits to fit within 16 characters)
        DTOBase dto = getLast(qryName);

        if (dto != null && dto.getCode() != null) {
            String lastCode = dto.getCode().substring(12, 15); // Extract the numeric portion (3 digits)
            try {
                int nextNum = Integer.parseInt(lastCode) + 1;
                baseCode = String.format("%03d", nextNum); // Ensure 3-digit format
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

       
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMddyyyyHHmm");
        String currentDateTime = dateFormat.format(new Date());

        
        String randomAlpha = getRandomAlphabetic(2);

        
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
        ItemRequestDTO item = (ItemRequestDTO) obj;
        PreparedStatement prepStmnt = null;
        try {
            prepStmnt = conn.prepareStatement(getQueryStatement(qryItemAdd), Statement.RETURN_GENERATED_KEYS);
            prepStmnt.setString(1, item.getCode());
            prepStmnt.setString(2, item.getRequestTimestamp());
            prepStmnt.setString(3, item.getNeedDate());
            prepStmnt.setString(4, item.getOfficeCode());
//            prepStmnt.setString(5, item.getUnit());
//            prepStmnt.setDouble(6, item.getQuantity());
//            prepStmnt.setDouble(7, item.getReorderpoint());
            prepStmnt.setString(5, item.getAddedBy());
            prepStmnt.setTimestamp(6, item.getAddedTimestamp());
            prepStmnt.setString(7, item.getUpdatedBy());
            prepStmnt.setTimestamp(8, item.getUpdatedTimestamp());
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
    	 ItemRequestDTO item = ( ItemRequestDTO) obj;
        Connection conn = daoConnectorUtil.getConnection();
        List<PreparedStatement> prepStmntList = new ArrayList<>();
        delete(conn, prepStmntList, item);
        result.put(ActionResponse.SESSION_ACTION_RESPONSE, executeIUD(conn, prepStmntList));
    }
    
    public void delete(Connection conn, List<PreparedStatement> prepStmntList, DTOBase obj) {
    	 ItemRequestDTO item = ( ItemRequestDTO) obj;
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
    	 ItemRequestDTO item = ( ItemRequestDTO) obj;
        item.setBaseDataOnUpdate();
        Connection conn = daoConnectorUtil.getConnection();
        List<PreparedStatement> prepStmntList = new ArrayList<>();
        update(conn, prepStmntList, item);
        result.put(ActionResponse.SESSION_ACTION_RESPONSE, executeIUD(conn, prepStmntList));
    }

    public void update(Connection conn, List<PreparedStatement> prepStmntList, DTOBase obj) {
    	 ItemRequestDTO item = ( ItemRequestDTO) obj;
        PreparedStatement prepStmnt = null;
        try {
            // Prepare the update SQL query
        	prepStmnt = conn.prepareStatement(getQueryStatement(qryItemUpdate), Statement.RETURN_GENERATED_KEYS);

        
			// Set the parameters for the prepared statement
        	prepStmnt.setString(1, item.getRequestTimestamp());
        	prepStmnt.setString(2, item.getNeedDate());                
        	prepStmnt.setString(3, item.getAddedBy()); 
      		prepStmnt.setTimestamp(4, item.getAddedTimestamp());
//        	prepStmnt.setDouble(5, item.getQuantity());            
//        	prepStmnt.setDouble(6, item.getReorderpoint());        
        	prepStmnt.setString(5, item.getUpdatedBy());           
        	prepStmnt.setTimestamp(6, item.getUpdatedTimestamp()); 
        	prepStmnt.setInt(7, item.getId());                     

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
    	 ItemRequestDTO item = new  ItemRequestDTO();
        item.setId(getDBValInt(resultSet, "id"));
        item.setCode(getDBValStr(resultSet, "code"));
        item.setRequestTimestamp(getDBValStr(resultSet, "request_timestamp"));
        item.setNeedDate(getDBValStr(resultSet, "need_date"));
        item.setOfficeCode(getDBValStr(resultSet, "office_code"));
        //item.setUnit(getDBValStr(resultSet, "item_unit_code"));
//        item.setQuantity(getDBValDouble(resultSet, "quantity"));
//        item.setReorderpoint(getDBValDouble(resultSet, "reorder_point"));
//       
        return item;
    }
}
