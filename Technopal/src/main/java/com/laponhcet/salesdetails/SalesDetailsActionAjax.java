package com.laponhcet.salesdetails;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.json.JSONObject;

import com.laponhcet.academicprogram.AcademicProgramDTO;
import com.laponhcet.academicsection.AcademicSectionDTO;
import com.laponhcet.enrollment.EnrollmentDTO;
import com.laponhcet.enrollment.SortLastNameAscending;
import com.laponhcet.item.ItemDAO;
import com.laponhcet.item.ItemDTO;

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

public class SalesDetailsActionAjax extends ActionAjaxBase {
    private static final long serialVersionUID = 1L;
    
    
    protected void setInput(String action) {
    	
    }
    protected void validateInput(String action) {
//    	SalesDetailsDTO salesDetails = (SalesDetailsDTO) getSessionAttribute(SalesDetailsDTO.SESSION_SALES_DETAILS);
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
            SalesDetailsDTO salesDetailsSelected = (SalesDetailsDTO) dataTable.getSelectedRecord();
            try {
                jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataViewPage(sessionInfo, SalesDetailsUtil.getDataViewStr(sessionInfo, salesDetailsSelected)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } 
    }

    protected void dataTableAction(JSONObject jsonObj, DataTable dataTable) {
        initDataTable(dataTable);
        try {
            jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataTablePage(sessionInfo, SalesDetailsUtil.getDataTableStr(sessionInfo, dataTable)));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected void initDataTable(DataTable dataTable) {
      List<DTOBase> salesDetailsList = (List<DTOBase>) getSessionAttribute(SalesDetailsDTO.SESSION_SALES_DETAILS_LIST);
    	//       List<DTOBase> salesDetailsPaymentList = (List<DTOBase>) getSessionAttribute(SalesDetailsPaymentDTO.SESSION_SALES_DETAILS_PAYMENT_LIST);
//       List<DTOBase> salesDetailsDetailsList = (List<DTOBase>) getSessionAttribute(SalesDetailsDetailsDTO.SESSION_SALES_DETAILS_DETAILS_LIST);
//       List<DTOBase> userList = (List<DTOBase>) getSessionAttribute(UserDTO.SESSION_USER_LIST);
//       List<DTOBase> itemList = (List<DTOBase>) getSessionAttribute(ItemDTO.SESSION_ITEM_LIST);
       dataTable.setRecordList(salesDetailsList);
       dataTable.setCurrentPageRecordList();	
//   		for(int row=0; row < dataTable.getRecordListCurrentPage().size(); row++) {			
//   			SalesDetailsDTO salesDetails = (SalesDetailsDTO) dataTable.getRecordListCurrentPage().get(row);
//   			salesDetails.setSalesDetailsPayment((SalesDetailsPaymentDTO) DTOUtil.getObjByCode(salesDetailsPaymentList, salesDetails.getSalesDetailsPayment().getCode()));
//   			salesDetails.setSalesDetailsDetails((SalesDetailsDetailsDTO) DTOUtil.getObjByCode(salesDetailsDetailsList, salesDetails.getSalesDetailsDetails().getCode()));
//   			salesDetails.setUser((UserDTO) DTOUtil.getObjByCode(userList, salesDetails.getUser().getCode()));
//   			salesDetails.getSalesDetailsDetails().setItem((ItemDTO) DTOUtil.getObjByCode(itemList, salesDetails.getSalesDetailsDetails().getItem().getCode()));
//   		}
      }
   }
           