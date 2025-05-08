package com.laponhcet.salespayment;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


import com.mysql.jdbc.Statement;
import com.mytechnopal.ActionResponse;
import com.mytechnopal.base.DAOBase;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.dao.SettingsDAO;
import com.mytechnopal.dto.SettingsDTO;
import com.mytechnopal.util.StringUtil;

public class SalesPaymentDAO extends DAOBase {
    private static final long serialVersionUID = 1L;
    
    private String qrySalesPaymentAdd = "SALES_PAYMENT_ADD";
    private String qrySalesPaymentDelete = "SALES_PAYMENT_DELETE";
    private String qrySalesPaymentUpdate = "SALES_PAYMENT_UPDATE";
    private String qrySalesPaymentList = "SALES_PAYMENT_LIST";
    private String qrySalesPaymentGroupBySalesCode= "SALES_PAYMENT_GROUP_BY_SALES_CODE ";
    private String qryGetNameByCodeUnit = "SALES_PAYMENT_UNIT_NAME";
    private String qryGetNameByCodeCategory = "SALES_PAYMENT_CATEGORY_NAME";
    public static String qrySalesPaymentLast = "SALES_PAYMENT_LAST";
    private String getAmountPaidBySalesCode = "SALES_PAYMENT_AMOUNTPAID_BY_SALES_CODE";
    

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
    public void getAmountPaidById(int id) {
    	
    }
    
    
    public String executeGetAmountPaidBySalesCode(String code) {
        Connection conn = daoConnectorUtil.getConnection();
        return getAmountPaidBySalesCode(conn, code);
    }
    public String getAmountPaidBySalesCode(Connection conn, String code) {
        PreparedStatement prepStmnt = null;
        try {
        	prepStmnt = conn.prepareStatement(getQueryStatement(getAmountPaidBySalesCode), Statement.RETURN_GENERATED_KEYS);
        	prepStmnt.setString(1, code);
            try (ResultSet rs = prepStmnt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("total_amount_paid"); // Fetching the name
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log or handle exception properly
        }
        return null; // Return null if not found or an error occurs
    }
    public void add(Connection conn, List<PreparedStatement> prepStmntList, DTOBase obj) {
    	String generatedCodeSalesPayment = getGeneratedCode(qrySalesPaymentLast, 3);
		SalesPaymentDTO salesPayment = (SalesPaymentDTO) obj;
		PreparedStatement prepStmnt = null;
		Random random = new Random();
		int code = 100 + random.nextInt(900000); // Ensures a 3-digit number (100-999)
		String codeString = String.valueOf(code);
		try {
			prepStmnt = conn.prepareStatement(getQueryStatement(qrySalesPaymentAdd), Statement.RETURN_GENERATED_KEYS);
			prepStmnt.setString(1, generatedCodeSalesPayment);
			prepStmnt.setString(2, salesPayment.getSalesCode());
			prepStmnt.setDouble(3, salesPayment.getAmountPaid());
			prepStmnt.setString(4, salesPayment.getPaymentMethod());
			prepStmnt.setString(5, codeString);
			prepStmnt.setDate(6, new java.sql.Date(salesPayment.getDate().getTime()));
			prepStmnt.setString(7, salesPayment.getAddedBy());
;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		prepStmntList.add(prepStmnt);
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
        SalesPaymentDTO salesPaymentPayment = (SalesPaymentDTO) obj;
        Connection conn = daoConnectorUtil.getConnection();
        List<PreparedStatement> prepStmntList = new ArrayList<>();
        delete(conn, prepStmntList, salesPaymentPayment);
        result.put(ActionResponse.SESSION_ACTION_RESPONSE, executeIUD(conn, prepStmntList));
    }
    
    public void delete(Connection conn, List<PreparedStatement> prepStmntList, DTOBase obj) {
        SalesPaymentDTO salesPaymentPayment = (SalesPaymentDTO) obj;
        PreparedStatement prepStmnt = null;
        try {
            prepStmnt = conn.prepareStatement(getQueryStatement(qrySalesPaymentDelete), Statement.RETURN_GENERATED_KEYS);
            prepStmnt.setInt(1, salesPaymentPayment.getId());
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

    public List<DTOBase> getSalesPaymentList() {
        return getDTOList(qrySalesPaymentList);
        
    }
    public void printSalesPaymentList() {
        List<DTOBase> dtoList = getSalesPaymentList();

        System.out.println("=== Sales Payment Records ===");

        for (DTOBase dto : dtoList) {
            SalesPaymentDTO payment = (SalesPaymentDTO) dto;
            System.out.println(
                "ID: " + payment.getId() +
                ", Code: " + payment.getCode() +
                ", Sales Code: " + payment.getSalesCode() +
                ", Amount Paid: " + payment.getAmountPaid() +
                ", Payment Method: " + payment.getPaymentMethod() +
                ", Reference: " + payment.getReference()
            );
        }
    }

    


//    public List<DTOBase> getSalesPaymentList2() {
//        Connection conn = daoConnectorUtil.getConnection();
//        return getSalesPaymentListMethod(conn);
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
		SalesPaymentDTO salesPaymentPayment = new SalesPaymentDTO();
		salesPaymentPayment.setId(getDBValInt(resultSet, "id"));
		salesPaymentPayment.setCode(getDBValStr(resultSet, "code"));
		salesPaymentPayment.setSalesCode(getDBValStr(resultSet, "sales_code"));
		salesPaymentPayment.setAmountPaid(getDBValDouble(resultSet, "amount_paid"));
		salesPaymentPayment.setTotalAmountPaid(getDBValDouble(resultSet, "total_amount_paid_per_sales_code"));
		salesPaymentPayment.setPaymentMethod(getDBValStr(resultSet, "payment_method"));
		salesPaymentPayment.setReference(getDBValStr(resultSet, "reference"));
		return salesPaymentPayment;
	}
	

	
	public static void main(String[] args) {

		    SalesPaymentDAO salesPaymentDAO = new SalesPaymentDAO();
		    salesPaymentDAO.printSalesPaymentList();
		

	}
	 
}