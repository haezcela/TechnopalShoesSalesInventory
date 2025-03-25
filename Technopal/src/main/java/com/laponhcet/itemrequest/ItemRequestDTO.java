package com.laponhcet.itemrequest;

import com.mytechnopal.base.DTOBase;
import com.mytechnopal.util.StringUtil;

public class ItemRequestDTO extends DTOBase {
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

private String code;
private String request_timestamp;
private String need_date;
private String office_code;
//private String item_unit_code;
//private Double quantity;
//private Double reorder_point;
//private int duration; //*1000 for second

private String item_unit_code;

public ItemRequestDTO() {
super();
code = "";
request_timestamp = "";
need_date = "";
office_code = "";
//item_unit_code = "";
//quantity= (double) 1;
//reorder_point=(double) 3;
//duration = 3;
}
public ItemRequestDTO getItem() {
ItemRequestDTO item = new ItemRequestDTO();
item.setId(super.getId());
item.setCode(this.code);
item.setRequestTimestamp(this.request_timestamp);
item.setNeedDate(this.need_date);
item.setOfficeCode(this.office_code);
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


System.out.println("Code: " + code);
System.out.println("RequestTimestamp: " + request_timestamp);
System.out.println("NeedDate: " + need_date);
System.out.println("OfficeCode: " + office_code);
//System.out.println("Unit: " + item_unit_code);
//System.out.println("Quantity: " + quantity);
//System.out.println("Reorderpoint: " + reorder_point);

}
public String getCode() {
return code;
}

public void setCode(String code) {
this.code = code;
}

public String getRequestTimestamp() {
return request_timestamp;
}

public void setRequestTimestamp(String request_timestamp) {
this.request_timestamp = request_timestamp;
}

public String getNeedDate() {
return need_date;
}

public void setNeedDate(String need_date) {
this.need_date = need_date;
}

public String getOfficeCode() {
return office_code;
}

public void setOfficeCode(String office_code) {
this.office_code = office_code;
}
}
