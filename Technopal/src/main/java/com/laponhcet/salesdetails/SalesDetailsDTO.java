package com.laponhcet.salesdetails;

import com.laponhcet.item.ItemDTO;
import com.laponhcet.sales.SalesDTO;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.dto.UserDTO;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class SalesDetailsDTO extends DTOBase {
    private static final long serialVersionUID = 1L;

    public static final String SESSION_SALES_DETAILS = "SESSION_SALES_DETAILS";
    public static final String SESSION_SALES_DETAILS_LIST = "SESSION_SALES_DETAILS_LIST";
    public static final String SESSION_SALES_DETAILS_DATA_TABLE = "SESSION_SALES_DETAILS_DATA_TABLE";
    
    public static final String ACTION_SEARCH_BY_CODE = "ACTION_SEARCH_BY_CODE";

	public static final String ACTION_SEARCH_BY_NAME = null;



	
    


    private double quantity;
    private double unitPrice;
    private String itemCode;
    private String salesCode;
    public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getSalesCode() {
		return salesCode;
	}

	public void setSalesCode(String salesCode) {
		this.salesCode = salesCode;
	}

	private ItemDTO item;
    private SalesDTO sales;
	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public ItemDTO getItem() {
		return item;
	}

	public void setItem(ItemDTO item) {
		this.item = item;
	}

	public SalesDTO getSales() {
		return sales;
	}

	public void setSales(SalesDTO sales) {
		this.sales = sales;
	}
	private List<SalesDetailsDTO> salesDetailsList = new ArrayList<>(); // Initialize the list

    // Getter for salesDetailsList
    public List<SalesDetailsDTO> getSalesDetailsList() {
        return salesDetailsList;
    }

    // Setter for salesDetailsList
    public void setSalesDetailsList(List<SalesDetailsDTO> salesDetailsList) {
        this.salesDetailsList = salesDetailsList;
    }



	public SalesDetailsDTO() {
        super();
        this.item = item;
        this.sales = sales;
    	item = new ItemDTO();
    }

    public SalesDetailsDTO getSales_Payment() {
    	SalesDetailsDTO salesDetails = new SalesDetailsDTO();
        salesDetails.setId(super.getId());
        salesDetails.setCode(super.getCode());
        salesDetails.setItem(this.item);
        salesDetails.setSales(this.sales);
        return salesDetails;
    }


}