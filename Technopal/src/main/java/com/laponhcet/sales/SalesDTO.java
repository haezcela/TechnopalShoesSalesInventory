package com.laponhcet.sales;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.laponhcet.item.ItemDTO;
import com.laponhcet.sales.SalesDTO;
import com.laponhcet.salesdetails.SalesDetailsDTO;
import com.laponhcet.salespayment.SalesPaymentDTO;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.dto.UserDTO;
import com.mytechnopal.util.StringUtil;

public class SalesDTO extends DTOBase {
	private static final long serialVersionUID = 1L;

	public static String SESSION_SALES = "SESSION_SALES";
	public static String SESSION_SALES_LIST = "SESSION_PERSON_LIST";
	public static String SESSION_SALES_DATA_TABLE = "SESSION_SALES_DATA_TABLE";
	public static final String ACTION_PREPARE_SALES = "ACTION_PREPARE_SALES";
	public static String ACTION_SEARCH_BY_NAME = "ACTION_SEARCH_BY_LASTNAME";
	public static final String ACTION_VIEW_SALES_PAYMENT = "ACTION_VIEW_SALES_PAYMENT";
	public static final String ACTION_VIEW_SALES_PAYMENT_SAVE = "ACTION_VIEW_SALES_PAYMENT_SAVE";
	public static final String ACTION_CHANGE_SALES_STATUS = "ACTION_CHANGE_SALES_STATUS";
	public static final String ACTION_ADD_VIEW_PAYMENT = "ACTION_ADD_VIEW_PAYMENT";
	public static final String ACTION_ADD_SAVE_PAYMENT = "ACTION_ADD_SAVE_PAYMENT";
	
	private String code;
	private Date date;
	private String customerCode;
	private double total;
	private String paymentStatus;
	private String status;
	
	private String name;

	private SalesDetailsDTO salesDetails;
	private SalesPaymentDTO salesPaymentDTO;
	private UserDTO user;
	
	
	
	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}


	public SalesDetailsDTO getSalesDetails() {
		return salesDetails;
	}

	public void setSalesDetails(SalesDetailsDTO salesDetails) {
		this.salesDetails = salesDetails;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}
	private ItemDTO item;
	public ItemDTO getItem() {
		return item;
	}

	public void setItem(ItemDTO item) {
		this.item = item;
	}

	public SalesDTO() {
		super();
		code = "";
		name = "";
		salesPaymentDTO = new SalesPaymentDTO();
		user = new UserDTO();
		item = new ItemDTO();
		salesDetails = new SalesDetailsDTO();
	}
	
	public SalesPaymentDTO getSalesPayment() {
		return salesPaymentDTO;
	}

	public void setSalesPayment(SalesPaymentDTO salesPaymentDTO) {
		this.salesPaymentDTO = salesPaymentDTO;
	}
	
	


private Map<String, Double> totalAmountPaidBySalesCode = new HashMap<>();

public Map<String, Double> getTotalAmountPaidBySalesCode() {
    return totalAmountPaidBySalesCode;
}

public void setTotalAmountPaidBySalesCode(Map<String, Double> totalAmountPaidBySalesCode) {
    this.totalAmountPaidBySalesCode = totalAmountPaidBySalesCode;
}



	public double getTotal() {
		return total;
	}


	public void setTotal(double total) {
		this.total = total;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}


	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	  public SalesDTO getSales() {
		  	SalesDTO sales = new SalesDTO();
		  	sales.setId(super.getId());
		  	sales.setName(this.name);
		  	sales.setDate(this.date);
		  	sales.setPaymentStatus(this.paymentStatus);
		  	sales.setTotal(this.total);
		  	sales.setStatus(this.status);
		  	sales.setCode(super.getCode());
		  	sales.setUser(this.user);
		  	sales.setItem(this.item);
		  	sales.setSalesDetails(this.salesDetails);
		  	sales.setCustomerCode(this.customerCode);
	        return sales;
	    }
}
	