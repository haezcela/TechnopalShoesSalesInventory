package com.laponhcet.salesdetails;

import java.util.List;


import com.laponhcet.salesdetails.SalesDetailsDAO;
import com.laponhcet.salesdetails.SalesDetailsDTO;

import com.mytechnopal.DataTable;
import com.mytechnopal.base.ActionBase;
import com.mytechnopal.base.DTOBase;


public class SalesDetailsAction extends ActionBase {

    private static final long serialVersionUID = 1L;

    protected void setSessionVars() {
        List<DTOBase> salesDetailsList = new SalesDetailsDAO().getSalesDetailsList();
        DataTable dataTable = new DataTable(SalesDetailsDTO.SESSION_SALES_DETAILS_DATA_TABLE, salesDetailsList, new String[] {SalesDetailsDTO.ACTION_SEARCH_BY_NAME}, new String[] {"Name"});
        dataTable.setColumnNameArr(new String[] {"Item", "Quantity", "Unit Price", "Actions"});
        dataTable.setColumnWidthArr(new String[] {"25","25", "25", "25"}); 

        setSessionAttribute(SalesDetailsDTO.SESSION_SALES_DETAILS_DATA_TABLE, dataTable);
        setSessionAttribute(SalesDetailsDTO.SESSION_SALES_DETAILS_LIST, salesDetailsList);

        

    }
}

