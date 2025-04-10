package com.laponhcet.salespayment;

import com.laponhcet.item.ItemDTO;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.dto.UserDTO;

import java.sql.Timestamp;
import java.util.Date;

public class SalesPaymentDTO extends DTOBase {
    private static final long serialVersionUID = 1L;

    public static final String SESSION_SALES_PAYMENT = "SESSION_SALES_PAYMENT";
    public static final String SESSION_SALES_PAYMENT_LIST = "SESSION_SALES_PAYMENT_LIST";
    public static final String SESSION_SALES_PAYMENT_DATA_TABLE = "SESSION_SALES_PAYMENT_DATA_TABLE";
    
    public static final String ACTION_SEARCH_BY_CODE = "ACTION_SEARCH_BY_CODE";

	
    

    private String name;
    private Date date;


	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	private double amountPaid;
    private String paymentMethod;
    private String reference;
    private String salesCode;
    private UserDTO user;
	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

    public String getSalesCode() {
		return salesCode;
	}

	public void setSalesCode(String salesCode) {
		this.salesCode = salesCode;
	}

	public double getAmountPaid() {
		return amountPaid;
	}

	public void setAmountPaid(double amountPaid) {
		this.amountPaid = amountPaid;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public SalesPaymentDTO() {
        super();
        this.name = "";
        this.paymentMethod = "";
        this.amountPaid = (double) 1;
        this.reference = "";
        this.user = user;
    
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
 
    public SalesPaymentDTO getSales_Payment() {
    	SalesPaymentDTO salesPayment = new SalesPaymentDTO();
        salesPayment.setId(super.getId());
        salesPayment.setCode(super.getCode());
        salesPayment.setName(salesPayment.getName());
        salesPayment.setUser(this.user);
        return salesPayment;
    }


}