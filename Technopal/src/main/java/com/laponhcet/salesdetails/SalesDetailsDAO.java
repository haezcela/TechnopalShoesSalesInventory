package com.laponhcet.salesdetails;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


import com.mysql.jdbc.Statement;
import com.mytechnopal.ActionResponse;
import com.mytechnopal.base.DAOBase;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.dao.SettingsDAO;
import com.mytechnopal.dto.SettingsDTO;
import com.mytechnopal.util.StringUtil;

public class SalesDetailsDAO extends DAOBase {
    private static final long serialVersionUID = 1L;
    
    private String qrySalesDetailsAdd = "SALES_DETAILS_ADD";
    private String qrySalesDetailsDelete = "SALES_DETAILS_DELETE";
    private String qrySalesDetailsUpdate = "SALES_DETAILS_UPDATE";
    private String qrySalesDetailsList = "SALES_DETAILS_LIST";
    private String qryGetNameByCodeUnit = "SALES_DETAILS_UNIT_NAME";
    private String qryGetNameByCodeCategory = "SALES_DETAILS_CATEGORY_NAME";
    public static String qrySalesDetailsLast = "SALES_DETAILS_LAST";

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
    public void add(Connection conn, List<PreparedStatement> prepStmntList, DTOBase obj) {
		SalesDetailsDTO salesDetails = (SalesDetailsDTO) obj;
		PreparedStatement prepStmnt = null;
		Random random = new Random();
		int code = 100 + random.nextInt(900); // Ensures a 3-digit number (100-999)
		String codeString = String.valueOf(code);
		try {
			prepStmnt = conn.prepareStatement(getQueryStatement(qrySalesDetailsAdd), Statement.RETURN_GENERATED_KEYS);
			prepStmnt.setString(1, salesDetails.getItemCode());
			prepStmnt.setString(2, salesDetails.getSalesCode());
			prepStmnt.setDouble(3, salesDetails.getQuantity());
			prepStmnt.setDouble(4, salesDetails.getUnitPrice());
;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		prepStmntList.add(prepStmnt);
	}
    
    
    public void add(Connection conn, List<PreparedStatement> prepStmntList, List<SalesDetailsDTO> salesDetailsList) {
    	System.out.println("adddddddddddddddddddd");
        if (salesDetailsList == null || salesDetailsList.isEmpty()) {
            return; // Exit if there's nothing to insert
        }

        try {
            PreparedStatement prepStmnt = conn.prepareStatement(getQueryStatement(qrySalesDetailsAdd), Statement.RETURN_GENERATED_KEYS);
            
            for (SalesDetailsDTO salesDetails : salesDetailsList) {
                prepStmnt.setString(1, salesDetails.getSalesCode());
                prepStmnt.setDouble(2, salesDetails.getQuantity());
                prepStmnt.setDouble(3, salesDetails.getUnitPrice());
                prepStmnt.setString(4, salesDetails.getItemCode());
                prepStmnt.addBatch(); // Add this set of parameters to the batch
            }
            
            prepStmnt.executeBatch(); // Execute batch insert
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    

    private void closeDB(List<PreparedStatement> prepStmntList, Connection conn) {
		// TODO Auto-generated method stub
		
	}

    @Override
    public void executeAddList(List<DTOBase> arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void executeDelete(DTOBase obj) {
        SalesDetailsDTO salesDetailsPayment = (SalesDetailsDTO) obj;
        Connection conn = daoConnectorUtil.getConnection();
        List<PreparedStatement> prepStmntList = new ArrayList<>();
        delete(conn, prepStmntList, salesDetailsPayment);
        result.put(ActionResponse.SESSION_ACTION_RESPONSE, executeIUD(conn, prepStmntList));
    }
    
    public void delete(Connection conn, List<PreparedStatement> prepStmntList, DTOBase obj) {
        SalesDetailsDTO salesDetailsPayment = (SalesDetailsDTO) obj;
        PreparedStatement prepStmnt = null;
        try {
            prepStmnt = conn.prepareStatement(getQueryStatement(qrySalesDetailsDelete), Statement.RETURN_GENERATED_KEYS);
            prepStmnt.setInt(1, salesDetailsPayment.getId());
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
    public void executeUpdateList(List<DTOBase> arg0) {
        // TODO Auto-generated method stub
    }

    public List<DTOBase> getSalesDetailsList() {
        return getDTOList(qrySalesDetailsList);
        
    }
//    public List<DTOBase> getSalesDetailsList2() {
//        Connection conn = daoConnectorUtil.getConnection();
//        return getSalesDetailsListMethod(conn);
//    }


	@Override
	public void executeAdd(DTOBase arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void executeUpdate(DTOBase arg0) {
		// TODO Auto-generated method stub
		
	}
	

	@Override
	protected DTOBase rsToObj(ResultSet resultSet) {
		SalesDetailsDTO salesDetailsPayment = new SalesDetailsDTO();
		salesDetailsPayment.setId(getDBValInt(resultSet, "id"));
		salesDetailsPayment.setCode(getDBValStr(resultSet, "sales_code"));
		salesDetailsPayment.setQuantity(getDBValDouble(resultSet, "quantity"));
		salesDetailsPayment.setUnitPrice(getDBValDouble(resultSet, "unit_price"));
		salesDetailsPayment.getItem().setCode(getDBValStr(resultSet, "item_code"));
		return salesDetailsPayment;
	}
    

}