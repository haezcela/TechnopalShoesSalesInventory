package com.laponhcet.sales;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.json.JSONObject;

import com.laponhcet.academicprogram.AcademicProgramDTO;
import com.laponhcet.academicsection.AcademicSectionDTO;

import com.laponhcet.sales.SalesDAO;
import com.laponhcet.sales.SalesDTO;
import com.laponhcet.salesdetails.SalesDetailsDAO;
import com.laponhcet.salesdetails.SalesDetailsDTO;
import com.laponhcet.enrollment.EnrollmentDTO;
import com.laponhcet.enrollment.SortLastNameAscending;
import com.laponhcet.item.ItemDAO;
import com.laponhcet.item.ItemDTO;
import com.laponhcet.salespayment.SalesPaymentDAO;
import com.laponhcet.salespayment.SalesPaymentDTO;
import com.laponhcet.semester.SemesterDTO;
import com.laponhcet.student.StudentDTO;
import com.laponhcet.student.StudentUtil;
import com.mytechnopal.ActionResponse;
import com.mytechnopal.DataTable;
import com.mytechnopal.UploadedFile;
import com.mytechnopal.base.ActionAjaxBase;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.dao.UserDAO;
import com.mytechnopal.dto.BarangayDTO;
import com.mytechnopal.dto.CityDTO;
import com.mytechnopal.dto.SHSStrandDTO;
import com.mytechnopal.dto.UserDTO;
import com.mytechnopal.link.LinkDTO;
import com.mytechnopal.usercontact.UserContactDTO;
import com.mytechnopal.usercontact.UserContactUtil;
import com.mytechnopal.usermedia.UserMediaUtil;
import com.mytechnopal.util.DTOUtil;
import com.mytechnopal.util.PageUtil;
import com.mytechnopal.util.StringUtil;


public class SalesActionAjax extends ActionAjaxBase {
    private static final long serialVersionUID = 1L;
    
    private String generateReference() {
        Random random = new Random();
        int randomNumber = 100 + random.nextInt(900000);
    	return String.valueOf(randomNumber);
    	
    }
    
    public static double[] convertToDoubleArray(String str) {
        if (str == null || str.trim().isEmpty()) {
            return new double[0];  // return empty array if input is null or empty
        }
        String[] strArray = str.split(",\\s*");
        List<Double> valuesList = new ArrayList<>();

        for (String s : strArray) {
            if (!s.trim().isEmpty()) {
                try {
                    valuesList.add(Double.parseDouble(s.trim()));
                } catch (NumberFormatException e) {
                    System.err.println("Invalid number format: '" + s + "'");
                    // Optionally: skip or throw your own exception
                }
            }
        }

        // Convert list to array
        double[] values = new double[valuesList.size()];
        for (int i = 0; i < valuesList.size(); i++) {
            values[i] = valuesList.get(i);
        }

        return values;
    }


    // Method to convert a comma-separated string to an int array
    public static int[] convertToIntArray(String str) {
        if (str == null || str.trim().isEmpty()) {
            return new int[0]; // return empty array to avoid crash
        }

        String[] strArray = str.split(",\\s*");
        List<Integer> valuesList = new ArrayList<>();

        for (String s : strArray) {
            if (!s.trim().isEmpty()) {
                try {
                    valuesList.add(Integer.parseInt(s.trim()));
                } catch (NumberFormatException e) {
                    System.err.println("Invalid int format: '" + s + "'");
                    // Optionally log or skip the invalid value
                }
            }
        }

        int[] values = new int[valuesList.size()];
        for (int i = 0; i < valuesList.size(); i++) {
            values[i] = valuesList.get(i);
        }

        return values;
    }

    public static String getPaymentStatus(double total, double amountPaid) {
        double epsilon = 0.00001;

        if (amountPaid == 0) {
            return "UNPAID";
        }
        if (Math.abs(total - amountPaid) < epsilon) {
            return "PAID";
        }
        if (amountPaid > 0 && amountPaid < total) {
            return "PARTIALLY PAID";
        }
        if (amountPaid > 0 && amountPaid > total) {
            return "ERROR";
        }
        return ""; // Optional case if amountPaid > total
    }

    protected void setInput(String action) {
        SalesDTO sales = (SalesDTO) getSessionAttribute(SalesDTO.SESSION_SALES);

        // Hidden inputs
        String itemCodesStr = getRequestString("txtHiddenItems");
        String quantitiesStr = getRequestString("txtQuantity");
        String unitPricesStr = getRequestString("txtUnitPrice");

        System.out.println("Raw quantity string: '" + quantitiesStr + "'");
        System.out.println("Raw unit price string: '" + unitPricesStr + "'");

        // Parse arrays
        String[] itemCodes = itemCodesStr.split(",");
        double[] quantities = convertToDoubleArray(quantitiesStr);
        double[] unitPrices = convertToDoubleArray(unitPricesStr);

        // Get total, payment, and date
        double total = getRequestDouble("txtHiddenTotal");
        double amountPaid = getRequestDouble("txtAmountPaid");
        String dateStr = getRequestString("txtDate");
        String paymentStatus = getPaymentStatus(total, amountPaid);
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

        if (paymentStatus.equals("ERROR")) {
            actionResponse.constructMessage(ActionResponse.TYPE_INVALID, "Amount paid cannot be greater than the total amount");
        }

        System.out.println("Items: " + Arrays.toString(itemCodes));
        System.out.println("Quantities: " + Arrays.toString(quantities));
        System.out.println("Unit Prices: " + Arrays.toString(unitPrices));
        System.out.println("TOTAL: " + total);
        System.out.println("amountPaid: " + amountPaid);

        // Prepare salesDetailsList
        List<SalesDetailsDTO> salesDetailsList = new ArrayList<>();
        int length = Math.min(Math.min(itemCodes.length, quantities.length), unitPrices.length);

        for (int i = 0; i < length; i++) {
            String itemCode = itemCodes[i].trim();
            if (!itemCode.isEmpty()) {
                SalesDetailsDTO detail = new SalesDetailsDTO();
                detail.setItemCode(itemCode);
                detail.setQuantity(quantities[i]);
                detail.setUnitPrice(unitPrices[i]);

                System.out.println("Adding item: " + itemCode + ", qty: " + quantities[i] + ", price: " + unitPrices[i]);
                salesDetailsList.add(detail);
            }
        }

        // Set parsed date
        try {
            Date parsedDate = formatter.parse(dateStr);
            sales.setDate(parsedDate);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        // Set sales details
        sales.getSalesDetails().setSalesDetailsList(salesDetailsList);
        sales.setTotal(total);
        sales.setPaymentStatus(paymentStatus);
        sales.setStatus("Pending");

        // Payment
        sales.getSalesPayment().setPaymentMethod(getRequestString("cboPaymentMethod"));
        sales.getSalesPayment().setAmountPaid(amountPaid);
        sales.getSalesPayment().setReference(generateReference());

        // Set customer
        int userId = getRequestInt("cboCustomer");
        if (userId > 0) {
            List<DTOBase> userList = (List<DTOBase>) getSessionAttribute(UserDTO.SESSION_USER_LIST);
            UserDTO user = (UserDTO) DTOUtil.getObjById(userList, userId);
            sales.setUser(user);
            sales.setCustomerCode(user.getCode());
        }

        // Log
        System.out.println("✅ setInput completed");
        System.out.println("Customer code: " + sales.getCustomerCode());
    }
    protected void validateInput(String action) {
//    	SalesDTO sales = (SalesDTO) getSessionAttribute(SalesDTO.SESSION_SALES);
//        if (action.equalsIgnoreCase(DataTable.ACTION_ADD_SAVE) || action.equalsIgnoreCase(DataTable.ACTION_UPDATE_SAVE)) {       	
//        	if(StringUtil.isEmpty(String.valueOf(getRequestString("txtQuantity")))) {
//				actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "Quantity");
//			}
//        	if (StringUtil.isEmpty(getRequestString("txtUnitPrice"))) {
//                actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "UnitPrice");
//            }
//        	if(StringUtil.isEmpty(String.valueOf(getRequestInt("cboCustomer")))) {
//				actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "Customer");
//			}
//        	if (StringUtil.isEmpty(getRequestString("cboStatus"))) {
//                actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "Status");
//            }
//        	if (StringUtil.isEmpty(getRequestString("cboPaymentMethod"))) {
//                actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "Payment Method");
//            }
//        	if (StringUtil.isEmpty(getRequestString("cboPaymentStatus"))) {
//                actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "Payment Status");
//            }
//        	if (StringUtil.isEmpty(getRequestString("txtAmountPaid"))) {
//                actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "Amount Paid");
//            }
//
//        }
    }
    protected void customAction(JSONObject jsonObj, String action) {
    
    	
    	if(action.equalsIgnoreCase(SalesDTO.ACTION_VIEW_SALES_PAYMENT_SAVE)) {
    		SalesDTO sales = new SalesDTO();
        	SalesDAO salesDAO = new SalesDAO();
        	double amount = getRequestInt("amount");
        	//String strAmount = String.valueOf(amount);
        	int id = getRequestInt("salesId");
        	String salesCode = getRequestString("salesCode");
        	double total = getRequestDouble("total");
        	String date = getRequestString("FormattedDate");
        	if (date.equals("") || date == "" || date == null) {
        		actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "date");
        		return;
        	}
        	if (amount == 0 || amount < 1) {
        		actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "amount");
        		return;
        	}
        	SimpleDateFormat formatter1 = new SimpleDateFormat("MM/dd/yyyy");
        	try {
        	 
        	    LocalDate parsedDate1 = LocalDate.parse(date); 
        	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        	    String formattedDate = parsedDate1.format(formatter); 
        	     
        	    Date parsedDate = formatter1.parse(formattedDate);
        	    sales.getSalesPayment().setDate(parsedDate);
        	    System.out.println(formattedDate);
        	} catch (DateTimeParseException e) {
        	
        	    e.printStackTrace();
        	} catch (java.text.ParseException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
          	String paymentMethod = getRequestString("PaymentMethod");
          
        	System.out.println("FormattedDateFormattedDateFormattedDateFormattedDate"+date);
        	SalesPaymentDAO sdao = new SalesPaymentDAO();
        	String recentAmount = sdao.executeGetAmountPaidBySalesCode(salesCode);
        	double currentAmount = Double.parseDouble(recentAmount) + amount;
        	String paymentStatus = getPaymentStatus(total, currentAmount);
            if (paymentStatus.equals("ERROR")) {
            	actionResponse.constructMessage(ActionResponse.TYPE_FAIL, "amout paid has exceed the total");
            	return;
            }
     
            sales.getSalesPayment().setPaymentMethod(paymentMethod);
            sales.getSalesPayment().setAmountPaid(amount);
            sales.getSalesPayment().setSalesCode(salesCode);
            sales.getSalesPayment().setAddedBy(sessionInfo.getCurrentUser().getCode());
        	sales.setPaymentStatus(paymentStatus);
        	sales.setId(id);
        	System.out.println("adddddddddddddddddddd add savee"+amount+total+id);
        	salesDAO.executeUpdate(sales);
            
        	actionResponse = (ActionResponse) salesDAO.getResult().get(ActionResponse.SESSION_ACTION_RESPONSE);
            if (StringUtil.isEmpty(actionResponse.getType())) {
                actionResponse.constructMessage(ActionResponse.TYPE_SUCCESS, "Added");
                setSessionAttribute(SalesDTO.SESSION_SALES_LIST, new SalesDAO().getSalesList());
                setSessionAttribute(SalesDetailsDTO.SESSION_SALES_DETAILS_LIST, new SalesDetailsDAO().getSalesDetailsList());
                setSessionAttribute(SalesPaymentDTO.SESSION_SALES_PAYMENT_LIST, new SalesPaymentDAO().getSalesPaymentList());
                setSessionAttribute(UserDTO.SESSION_USER_LIST, new UserDAO().getUserList());
                
                // Refresh table
                dataTableAction(jsonObj, (DataTable) getSessionAttribute(SalesDTO.SESSION_SALES_DATA_TABLE));
                
            }
		}
    	else if (action.equalsIgnoreCase(SalesDTO.ACTION_CHANGE_SALES_STATUS)) {
    		SalesDTO sales = new SalesDTO();
        	SalesDAO salesDAO = new SalesDAO();
    		String statusCodeAndStatus = getRequestString("changeStatusCode");
    	  	if (statusCodeAndStatus.equals("") || statusCodeAndStatus == "" || statusCodeAndStatus == null) {
        		actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "status");
        		return;
        	}
    		System.out.println("adddddddddddddasdasdasdasdasdasdasdsadjaskd statusCode"+statusCodeAndStatus);
    		String[] parts = statusCodeAndStatus.split(",\\s*"); // split by comma and optional space
    		String code = parts[0];         // "Code001"
    		String status = parts[1];
    		sales.setCode(code);
    		sales.setStatus(status);
    		salesDAO.executeUpdateStatus(sales);
         	actionResponse = (ActionResponse) salesDAO.getResult().get(ActionResponse.SESSION_ACTION_RESPONSE);
            if (StringUtil.isEmpty(actionResponse.getType())) {
                actionResponse.constructMessage(ActionResponse.TYPE_SUCCESS, "status changed");
                setSessionAttribute(SalesDTO.SESSION_SALES_LIST, new SalesDAO().getSalesList());
                setSessionAttribute(SalesDetailsDTO.SESSION_SALES_DETAILS_LIST, new SalesDetailsDAO().getSalesDetailsList());
                setSessionAttribute(SalesPaymentDTO.SESSION_SALES_PAYMENT_LIST, new SalesPaymentDAO().getSalesPaymentList());
                setSessionAttribute(UserDTO.SESSION_USER_LIST, new UserDAO().getUserList());
            }
    	
    	}
    }

    protected void executeLogic(JSONObject jsonObj, DataTable dataTable, String action) {
		List<DTOBase> userList = (List<DTOBase>) getSessionAttribute(UserDTO.SESSION_USER_LIST);
		List<DTOBase> itemList = (List<DTOBase>) getSessionAttribute(ItemDTO.SESSION_ITEM_LIST);
        if (action.equalsIgnoreCase(DataTable.ACTION_VIEW)) {
            SalesDTO salesSelected = (SalesDTO) dataTable.getSelectedRecord();
            try {
                jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataViewPage(sessionInfo, SalesUtil.getDataViewStr(sessionInfo, salesSelected),""));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (action.equalsIgnoreCase(DataTable.ACTION_ADD_VIEW)) {
            SalesDTO sales = new SalesDTO();
            try {
                jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataEntryPage(sessionInfo, SalesUtil.getDataEntryStr(sessionInfo, sales, userList, itemList),""));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            setSessionAttribute(SalesDTO.SESSION_SALES, sales);
        }else if (action.equalsIgnoreCase(DataTable.ACTION_ADD_SAVE)) {
            SalesDAO salesDAO = new SalesDAO();
            SalesDTO sales = (SalesDTO) getSessionAttribute(SalesDTO.SESSION_SALES);
            sales.setAddedBy(sessionInfo.getCurrentUser().getCode());
            Random random = new Random();
            int randomNumber = 100 + random.nextInt(900);
            sales.getUser().setCode(String.valueOf(randomNumber));
            salesDAO.executeAdd(sales);
        	System.out.println("adddddddddddddddddddd add savee");
            actionResponse = (ActionResponse) salesDAO.getResult().get(ActionResponse.SESSION_ACTION_RESPONSE);
            if (StringUtil.isEmpty(actionResponse.getType())) {
                actionResponse.constructMessage(ActionResponse.TYPE_SUCCESS, "Added");
                setSessionAttribute(SalesDTO.SESSION_SALES_LIST, new SalesDAO().getSalesList());
                setSessionAttribute(SalesDetailsDTO.SESSION_SALES_DETAILS_LIST, new SalesDetailsDAO().getSalesDetailsList());
                setSessionAttribute(SalesPaymentDTO.SESSION_SALES_PAYMENT_LIST, new SalesPaymentDAO().getSalesPaymentList());
                setSessionAttribute(UserDTO.SESSION_USER_LIST, new UserDAO().getUserList());
            }
       
        } else if (action.equalsIgnoreCase(DataTable.ACTION_DELETE_VIEW)) {
            SalesDTO salesSelected = (SalesDTO) dataTable.getSelectedRecord();
            try {
                jsonObj.put(LinkDTO.PAGE_CONTENT,  PageUtil.getDataViewPage(sessionInfo, SalesUtil.getDataViewStr(sessionInfo, salesSelected),""));
//                jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataViewPage(sessionInfo, EventCriteriaUtil.getDataViewStr(sessionInfo, criteriaSelected), ""));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            setSessionAttribute(SalesDTO.SESSION_SALES, salesSelected);
            setSessionAttribute(SalesDTO.SESSION_SALES, salesSelected);
        } else if (action.equalsIgnoreCase(DataTable.ACTION_DELETE)) {
            SalesDAO SalesDAO = new SalesDAO();
            SalesDTO sales = (SalesDTO) getSessionAttribute(SalesDTO.SESSION_SALES);
            SalesDAO.executeDelete(sales);
            actionResponse = (ActionResponse) SalesDAO.getResult().get(ActionResponse.SESSION_ACTION_RESPONSE);
            if (StringUtil.isEmpty(actionResponse.getType())) {
                setSessionAttribute(SalesDTO.SESSION_SALES_LIST, new SalesDAO().getSalesList());
                setSessionAttribute(SalesPaymentDTO.SESSION_SALES_PAYMENT_LIST, new SalesPaymentDAO().getSalesPaymentList());
                actionResponse.constructMessage(ActionResponse.TYPE_SUCCESS, "Deleted");
            }
        }else if (action.equalsIgnoreCase(DataTable.ACTION_ADD_VIEW+"_PAYMENT")) {
            System.out.println("ayyyyy");


        }
    }

    protected void dataTableAction(JSONObject jsonObj, DataTable dataTable) {
        initDataTable(dataTable);
        try {
            jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataTablePage(sessionInfo, SalesUtil.getDataTableStr(sessionInfo, dataTable)));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected void initDataTable(DataTable dataTable) {
       List<DTOBase> salesList = (List<DTOBase>) getSessionAttribute(SalesDTO.SESSION_SALES_LIST);
       List<DTOBase> salesPaymentList = (List<DTOBase>) getSessionAttribute(SalesPaymentDTO.SESSION_SALES_PAYMENT_LIST);
       List<DTOBase> salesDetailsList = (List<DTOBase>) getSessionAttribute(SalesDetailsDTO.SESSION_SALES_DETAILS_LIST);
       List<DTOBase> userList = (List<DTOBase>) getSessionAttribute(UserDTO.SESSION_USER_LIST);
       List<DTOBase> itemList = (List<DTOBase>) getSessionAttribute(ItemDTO.SESSION_ITEM_LIST);
       dataTable.setRecordList(salesList);
       dataTable.setCurrentPageRecordList();	
   		for(int row=0; row < dataTable.getRecordListCurrentPage().size(); row++) {			
   			SalesDTO sales = (SalesDTO) dataTable.getRecordListCurrentPage().get(row);
   			sales.setSalesPayment((SalesPaymentDTO) DTOUtil.getObjByCode(salesPaymentList, sales.getSalesPayment().getCode()));
   			//sales.setSalesDetails((SalesDetailsDTO) DTOUtil.getObjByCode(salesDetailsList, sales.getSalesDetails().getCode()));
   			sales.setUser((UserDTO) DTOUtil.getObjByCode(userList, sales.getUser().getCode()));
   			//sales.getSalesDetails().setItem((ItemDTO) DTOUtil.getObjByCode(itemList, sales.getSalesDetails().getItem().getCode()));
   		}
      }
   }
           