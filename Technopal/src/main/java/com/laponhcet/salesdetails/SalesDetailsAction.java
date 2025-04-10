package com.laponhcet.salesdetails;

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

public class SalesDetailsAction extends ActionBase {

    private static final long serialVersionUID = 1L;

    protected void setSessionVars() {
        List<DTOBase> salesDetailsList = new SalesDetailsDAO().getSalesDetailsList();

        DataTable dataTable = new DataTable(SalesDetailsDTO.SESSION_SALES_DETAILS_DATA_TABLE, salesDetailsList, new String[] {SalesDetailsDTO.ACTION_SEARCH_BY_NAME}, new String[] {"Name"});
        dataTable.setColumnNameArr(new String[] {"Code", "Customer", "Item",  "Quantity", "Unit Price", "Total", "Payment Status", "Status", "Payment Method", "Amount Paid", "Reference", "Actions"});
        dataTable.setColumnWidthArr(new String[] {"8","8", "8", "8", "8", "8", "8", "8", "8", "8", "8", "8", "8"}); 

        setSessionAttribute(SalesDetailsDTO.SESSION_SALES_DETAILS_DATA_TABLE, dataTable);
        setSessionAttribute(SalesDetailsDTO.SESSION_SALES_DETAILS_LIST, salesDetailsList);

        

    }
}

