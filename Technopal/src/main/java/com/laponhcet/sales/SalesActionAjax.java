package com.laponhcet.sales;

import java.util.ArrayList;
import java.util.Arrays;
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
import com.mytechnopal.base.ActionAjaxBase;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.dao.UserDAO;
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
        String[] strArray = str.split(",\\s*"); // Split by comma and optional space
        double[] values = new double[strArray.length];

        for (int i = 0; i < strArray.length; i++) {
            values[i] = Double.parseDouble(strArray[i].trim()); // Convert to double
        }
        return values;
    }

    // Method to convert a comma-separated string to an int array
    public static int[] convertToIntArray(String str) {
        String[] strArray = str.split(",\\s*"); // Split by comma and optional space
        int[] values = new int[strArray.length];

        for (int i = 0; i < strArray.length; i++) {
            values[i] = Integer.parseInt(strArray[i].trim()); // Convert to int
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
    	String item5 = getRequestString("txtHiddenItems");
    	String quantity = getRequestString("txtQuantity");
    	String unitPrice = getRequestString("txtUnitPrice");
    	double total = getRequestDouble("txtHiddenTotal");
       	double amountPaid = getRequestDouble("txtAmountPaid");
       	String paymentStatus = getPaymentStatus(total, amountPaid);
       	if (paymentStatus.equals("ERROR")) {
       		actionResponse.constructMessage(ActionResponse.TYPE_INVALID, "Amount paid cannot be greater than the total amount");
       	}
    	double[] quantityValues = convertToDoubleArray(quantity);
        
        // Convert unitPrice to double array
        double[] unitPriceValues = convertToDoubleArray(unitPrice);
        
        // Convert item to int array
        int[] itemValues = convertToIntArray(item5);

        // Print to verify
        System.out.println("Quantity: " + Arrays.toString(quantityValues));
        System.out.println("Unit Price: " + Arrays.toString(unitPriceValues));
        System.out.println("Items: " + Arrays.toString(itemValues));
        System.out.println("TOTAL: " + total);
        System.out.println("amountPaid: " + amountPaid);
        
        int length = Math.min(Math.min(quantityValues.length, unitPriceValues.length), itemValues.length);

        List<SalesDetailsDTO> salesDetailsList = new ArrayList<>();

        for (int i = 0; i < length; i++) {
            if (itemValues[i] != 0) {  
                String codeItem = new ItemDAO().executeGetCodeById(itemValues[i]);
                SalesDetailsDTO salesDetail = new SalesDetailsDTO();
                salesDetail.setItemCode(codeItem); // Ensure the itemId is being set
                salesDetail.setQuantity(quantityValues[i]);
                salesDetail.setUnitPrice(unitPriceValues[i]);

                final int index = i; // Define a final variable

                // Ensure no duplicate sales details are added
                boolean exists = salesDetailsList.stream()
                	    .anyMatch(detail -> detail.getItemCode().equals(codeItem) // Compare as String
                	                     && detail.getQuantity() == quantityValues[index]
                	                     && detail.getUnitPrice() == unitPriceValues[index]);

                	if (!exists) { 
                	    System.out.println("Adding: " + codeItem + ", Quantity: " + quantityValues[index] + ", Price: " + unitPriceValues[index]);
                	    salesDetailsList.add(salesDetail);
                	} else {
                	    System.out.println("Duplicate found, skipping: " + codeItem);
                	}

            }
        }


    	sales.getSalesDetails().setSalesDetailsList(salesDetailsList);
    	sales.setTotal(total);
    	sales.setPaymentStatus(paymentStatus);
    	sales.setStatus("Pending");
        sales.getSalesPayment().setPaymentMethod(getRequestString("cboPaymentMethod"));
        sales.getSalesPayment().setAmountPaid(amountPaid);
       // sales.getSalesPayment().setReference(getRequestString("txtReference"));
        sales.getSalesPayment().setReference(generateReference());
        int userId = getRequestInt("cboCustomer");
        System.out.println("userId: userId: "+userId);
        if(getRequestInt("cboCustomer") > 0) {
        	List<DTOBase> userList = (List<DTOBase>) getSessionAttribute(UserDTO.SESSION_USER_LIST);
        	UserDTO  user = (UserDTO) DTOUtil.getObjById(userList, userId);
        	sales.setUser(user);
		}
        int itemId = getRequestInt("cboItem");
        if(getRequestInt("cboItem") > 0) {
        	List<DTOBase> itemList = (List<DTOBase>) getSessionAttribute(ItemDTO.SESSION_ITEM_LIST);
        	ItemDTO  item = (ItemDTO) DTOUtil.getObjById(itemList, itemId);
        	sales.setItem(item);
		}
    	System.out.println("adddddddddddddddddddd set input");
        System.out.println("usercode: usercode: "+sales.getUser().getCode());
        sales.setCustomerCode(sales.getUser().getCode());
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

    protected void executeLogic(JSONObject jsonObj, DataTable dataTable, String action) {
		List<DTOBase> userList = (List<DTOBase>) getSessionAttribute(UserDTO.SESSION_USER_LIST);
		List<DTOBase> itemList = (List<DTOBase>) getSessionAttribute(ItemDTO.SESSION_ITEM_LIST);
        if (action.equalsIgnoreCase(DataTable.ACTION_VIEW)) {
            SalesDTO salesSelected = (SalesDTO) dataTable.getSelectedRecord();
            try {
                jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataViewPage(sessionInfo, SalesUtil.getDataViewStr(sessionInfo, salesSelected)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (action.equalsIgnoreCase(DataTable.ACTION_ADD_VIEW)) {
            SalesDTO sales = new SalesDTO();
            try {
                jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataEntryPage(sessionInfo, SalesUtil.getDataEntryStr(sessionInfo, sales, userList, itemList)));
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
                jsonObj.put(LinkDTO.PAGE_CONTENT,  PageUtil.getDataViewPage(sessionInfo, SalesUtil.getDataViewStr(sessionInfo, salesSelected)));
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
   			sales.setSalesDetails((SalesDetailsDTO) DTOUtil.getObjByCode(salesDetailsList, sales.getSalesDetails().getCode()));
   			sales.setUser((UserDTO) DTOUtil.getObjByCode(userList, sales.getUser().getCode()));
   			sales.getSalesDetails().setItem((ItemDTO) DTOUtil.getObjByCode(itemList, sales.getSalesDetails().getItem().getCode()));
   		}
      }
   }
           