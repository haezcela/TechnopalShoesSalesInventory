package com.laponhcet.sales;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.laponhcet.sales.SalesDTO;
import com.laponhcet.salesdetails.SalesDetailsDAO;
import com.laponhcet.salesdetails.SalesDetailsDTO;
import com.laponhcet.sales.SalesDTO;
import com.laponhcet.salespayment.SalesPaymentDAO;
import com.laponhcet.salespayment.SalesPaymentDTO;
import com.laponhcet.sales.SalesDTO;
import com.mysql.jdbc.Statement;
import com.mytechnopal.ActionResponse;
import com.mytechnopal.base.DAOBase;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.dao.SettingsDAO;
import com.mytechnopal.dao.UserDAO;
import com.mytechnopal.dto.SettingsDTO;
import com.mytechnopal.dto.UserDTO;
import com.mytechnopal.util.StringUtil;

public class SalesDAO extends DAOBase {
    private static final long serialVersionUID = 1L;
    
    private String qrySalesAdd = "SALES_ADD";
    private String qrySalesDelete = "SALES_DELETE";
    private String qrySalesUpdate = "SALES_UPDATE";
    private String qrySalesList = "SALES_LIST";
    private String qryGetNameByCodeUnit = "SALES_UNIT_NAME";
    private String qryGetNameByCodeCategory = "SALES_CATEGORY_NAME";
    private String qrySalesLast = "SALES_LAST";

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
    public void executeAdd(DTOBase obj) {
        SalesPaymentDAO salesPaymentd = new SalesPaymentDAO();
        String salesPaymentLastQry = salesPaymentd.qrySalesPaymentLast;
        SalesDTO sales = (SalesDTO) obj;
        String generatedCodeSalesPayment = getGeneratedCode(salesPaymentLastQry, 3);
        String generatedCodeSales = getGeneratedCode(qrySalesLast, 3);

        sales.setBaseDataOnInsert();
        sales.setCode(generatedCodeSales);
        
        Connection conn = daoConnectorUtil.getConnection();
        List<PreparedStatement> prepStmntList = new ArrayList<>();

        add(conn, prepStmntList, sales);

        SalesPaymentDTO salesPayment = new SalesPaymentDTO();
        salesPayment.setPaymentMethod(sales.getSalesPayment().getPaymentMethod());
        salesPayment.setReference(sales.getSalesPayment().getReference());
        salesPayment.setAddedBy(sales.getAddedBy());
        salesPayment.setAmountPaid(sales.getSalesPayment().getAmountPaid());
        salesPayment.setSalesCode(generatedCodeSales);
        salesPayment.setCode(generatedCodeSalesPayment);
        salesPayment.setDate(sales.getDate());
 
        List<SalesDetailsDTO> salesDetailsList = sales.getSalesDetails().getSalesDetailsList();
        for (SalesDetailsDTO detail : salesDetailsList) {
            if (detail.getSalesCode() == null || detail.getSalesCode().isEmpty()) {
                detail.setSalesCode(generatedCodeSales);
            }
        }

        new SalesPaymentDAO().add(conn, prepStmntList, salesPayment);
        System.out.println("Final Sales Details List Size Before Insertion: " + salesDetailsList.size());
        for (SalesDetailsDTO detail : salesDetailsList) {
            System.out.println("Inserting: " + detail.getItemCode() + ", Quantity: " + detail.getQuantity() + ", Price: " + detail.getUnitPrice());
        }

        new SalesDetailsDAO().add(conn, prepStmntList, salesDetailsList); // Use updated list
        
        result.put(ActionResponse.SESSION_ACTION_RESPONSE, executeIUD(conn, prepStmntList));
    }


	public void add(Connection conn, List<PreparedStatement> prepStmntList, DTOBase obj) {
		
		SalesDTO sales = (SalesDTO) obj;
		PreparedStatement prepStmnt = null;
		Random random = new Random();
		int code = 100 + random.nextInt(900); // Ensures a 3-digit number (100-999)

		String codeString = String.valueOf(code);
		try {
			prepStmnt = conn.prepareStatement(getQueryStatement(qrySalesAdd), Statement.RETURN_GENERATED_KEYS);
			prepStmnt.setString(1, sales.getCode());
			//prepStmnt.setString(2, sales.getName());
			prepStmnt.setString(2, sales.getCustomerCode());
			prepStmnt.setDouble(3, sales.getTotal());
			prepStmnt.setString(4, sales.getPaymentStatus());
			prepStmnt.setString(5, sales.getStatus());
			prepStmnt.setDate(6, new java.sql.Date(sales.getDate().getTime()));
			prepStmnt.setString(7, sales.getAddedBy());
			
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
		SalesDTO sales = (SalesDTO) obj;
		Connection conn = daoConnectorUtil.getConnection();
		List<PreparedStatement> prepStmntList = new ArrayList<>();
		new SalesPaymentDAO().delete(conn, prepStmntList, sales.getSalesPayment());
		new SalesDetailsDAO().delete(conn, prepStmntList, sales.getSalesDetails());
		delete(conn, prepStmntList, sales);
		result.put(ActionResponse.SESSION_ACTION_RESPONSE, executeIUD(conn, prepStmntList));
	}

	public void delete(Connection conn, List<PreparedStatement> prepStmntList, DTOBase obj) {
		SalesDTO sales = (SalesDTO) obj;
		PreparedStatement prepStmnt = null;
		try {
			prepStmnt = conn.prepareStatement(getQueryStatement(qrySalesDelete), Statement.RETURN_GENERATED_KEYS);
			prepStmnt.setInt(1, sales.getId());
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

    public List<DTOBase> getSalesList() {
        return getDTOList(qrySalesList);
        
    }
//    public List<DTOBase> getSalesList2() {
//        Connection conn = daoConnectorUtil.getConnection();
//        return getSalesListMethod(conn);
//    }


	@Override
	public void executeUpdate(DTOBase arg0) {
		// TODO Auto-generated method stub
		
	}



	@Override
	protected DTOBase rsToObj(ResultSet resultSet) {
		SalesDTO sales = new SalesDTO();
		sales.setId(getDBValInt(resultSet, "id"));
		sales.setCode(getDBValStr(resultSet, "code"));
		sales.setCustomerCode(getDBValStr(resultSet, "customer_code"));
		sales.setTotal(getDBValDouble(resultSet, "total"));
		sales.setPaymentStatus(getDBValStr(resultSet, "payment_status"));
		sales.setStatus(getDBValStr(resultSet, "status"));
		sales.getUser().setCode(getDBValStr(resultSet, "customer_code"));
		sales.setDate(getDBValDate(resultSet, "date"));
		sales.getSalesPayment().setCode(getDBValStr(resultSet, "code"));
		sales.getSalesDetails().setCode(getDBValStr(resultSet, "code"));
		
		return sales;
	}
    

}