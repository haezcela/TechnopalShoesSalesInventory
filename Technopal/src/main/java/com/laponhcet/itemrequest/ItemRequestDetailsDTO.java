package com.laponhcet.itemrequest;

import com.mytechnopal.base.DTOBase;
import com.mytechnopal.util.StringUtil;

public class ItemRequestDetailsDTO extends DTOBase {
private static final long serialVersionUID = 1L;

public static final String SESSION_ITEM_REQUEST = "SESSION_ITEM_REQUEST";
public static final String SESSION_ITEM_REQUEST_LIST = "SESSION_ITEM_REQUEST_LIST";
public static final String SESSION_ITEM_REQUEST_DATA_TABLE = "SESSION_ITEM_REQUEST_DATA_TABLE";
public static final String ACTION_SEARCH_BY_NAME = "ACTION_SEARCH_BY_NAME";

   

public static String ITEM_OFFICE_CODE = "";
public static String ITEM_OFFICE_STR = "";

public static String ITEM_LEARNINGMAT_CODE = "";
public static String ITEM_CATEGORY_STR = "";

public static String[] ITEM_CATEGORY_CODE_ARR = new String[] {ITEM_OFFICE_CODE, ITEM_LEARNINGMAT_CODE};
public static String[] ITEM_CATEGORY_STR_ARR = new String[] {ITEM_OFFICE_STR,ITEM_CATEGORY_STR,};

public static String UNIT_OFFICE_CODE = "";
public static String UNIT_OFFICE_STR = "";

public static String UNIT_LEARNINGMAT_CODE = "";
public static String UNIT_STR = "";

public static String[] ITEM_UNIT_CODE_ARR = new String[] {UNIT_OFFICE_CODE, UNIT_LEARNINGMAT_CODE};
public static String[] ITEM_UNIT_STR_ARR = new String[] {UNIT_OFFICE_STR,UNIT_STR,};

private String item_request_code;
private String item_code;
private double quantity;
//private String office_code;
//private String item_unit_code;
//private Double quantity;
//private Double reorder_point;
//private int duration; //*1000 for second

//private String item_unit_code;

public ItemRequestDetailsDTO() {
super();
item_request_code = "";
item_code = "";
//quantity= "";
//office_code = "";
//item_unit_code = "";
quantity= (double) 1;
//reorder_point=(double) 3;
//duration = 3;
}
public ItemRequestDetailsDTO getItem() {
ItemRequestDetailsDTO item = new ItemRequestDetailsDTO();
item.setId(super.getId());
item.setCode(this.item_request_code);
item.setItemCode(this.item_code);
item.setQuantity(this.quantity);
//item.setOfficeCode(this.office_code);
//item.setUnit(this.item_unit_code);
//item.setQuantity(this.quantity);
//item.setReorderpoint(this.reorder_point);
return item;
}


//public String getCategoryStr() {
//	return ITEM_CATEGORY_STR_ARR[StringUtil.getIndexFromArrayStr(ITEM_CATEGORY_CODE_ARR, request_timestamp)];
//}
//
//public String getUnitStr() {
//	return ITEM_UNIT_STR_ARR[StringUtil.getIndexFromArrayStr(ITEM_UNIT_CODE_ARR, item_unit_code)];
//}

public void display() {


System.out.println("Code: " + item_request_code);
System.out.println("ItemCode: " + item_code);
System.out.println("Quantity: " + quantity);
//System.out.println("OfficeCode: " + office_code);
//System.out.println("Unit: " + item_unit_code);
//System.out.println("Quantity: " + quantity);
//System.out.println("Reorderpoint: " + reorder_point);

}
public String getCode() {
return item_request_code;
}

public void setCode(String item_request_code) {
this.item_request_code = item_request_code;
}

public String getItemCode() {
return item_code;
}

public void setItemCode(String item_code) {
this.item_code = item_code;
}

public double getQuantity() {
return quantity;
}

public void setQuantity(double quantity) {
this.quantity = quantity;
}
public String getDateTime() {
	// TODO Auto-generated method stub
	return null;
}

}
