package com.laponhcet.sales;

import java.util.List;

import com.laponhcet.item.ItemDAO;
import com.laponhcet.item.ItemDTO;
import com.laponhcet.salesdetails.SalesDetailsDAO;
import com.laponhcet.salesdetails.SalesDetailsDTO;
import com.laponhcet.salespayment.SalesPaymentDAO;
import com.laponhcet.salespayment.SalesPaymentDTO;
import com.mytechnopal.DataTable;
import com.mytechnopal.base.ActionBase;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.dao.UserDAO;
import com.mytechnopal.dto.UserDTO;

public class SalesAction extends ActionBase {

    private static final long serialVersionUID = 1L;

    protected void setSessionVars() {
        List<DTOBase> salesList = new SalesDAO().getSalesList();
        List<DTOBase> salesPaymentList = new SalesPaymentDAO().getSalesPaymentList();
        List<DTOBase> salesDetailsList = new SalesDetailsDAO().getSalesDetailsList();
        List<DTOBase> userList = new UserDAO().getUserList();
        DataTable dataTable = new DataTable(SalesDTO.SESSION_SALES_DATA_TABLE, salesList, new String[] {SalesDTO.ACTION_SEARCH_BY_NAME}, new String[] {"Name"});
        dataTable.setColumnNameArr(new String[] {"Code", "Customer","Total", "Payment Status", "Status", "Payment Method", "Amount Paid", "Reference", "Actions"});
        dataTable.setColumnWidthArr(new String[] {"10","10", "10", "10", "10", "10", "10", "10", "10", "10"}); 

        setSessionAttribute(SalesDTO.SESSION_SALES_DATA_TABLE, dataTable);
        setSessionAttribute(SalesDTO.SESSION_SALES_LIST, salesList);
        setSessionAttribute(SalesPaymentDTO.SESSION_SALES_PAYMENT_LIST, salesPaymentList);
        setSessionAttribute(SalesDetailsDTO.SESSION_SALES_DETAILS_LIST, salesDetailsList);
        setSessionAttribute(UserDTO.SESSION_USER_LIST, userList);
        
        setSessionAttribute(ItemDTO.SESSION_ITEM_LIST, new ItemDAO().getItemList());
        

    }
}

