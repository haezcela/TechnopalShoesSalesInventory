package com.laponhcet.sales;

import java.io.Serializable;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;


import com.mytechnopal.DataTable;
import com.mytechnopal.SessionInfo;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.base.WebControlBase;
import com.mytechnopal.dto.UserDTO;
import com.mytechnopal.util.DTOUtil;
import com.mytechnopal.util.DateTimeUtil;
import com.mytechnopal.webcontrol.DataTableWebControl;
import com.mytechnopal.webcontrol.SelectWebControl;
import com.mytechnopal.webcontrol.TextBoxWebControl;


public class SalesUtil implements Serializable {
	private static final long serialVersionUID = 1L;
	

	public static String getDataTableStr(SessionInfo sessionInfo, DataTable dataTable) {
		DataTableWebControl dtwc = new DataTableWebControl(sessionInfo, dataTable);
		StringBuffer strBuff = new StringBuffer();
		if(dataTable.getRecordList().size() >= 1) {
			strBuff.append(dtwc.getDataTableHeader(sessionInfo, dataTable));
			dataTable.setDataTableRecordArr(getDataTableCurrentPageRecordArr(sessionInfo, dataTable));
			strBuff.append(dtwc.getDataTable(true, false));
		}
		return strBuff.toString();
	}


	private static String[][] getDataTableCurrentPageRecordArr(SessionInfo sessionInfo, DataTable dataTable) {
		String[][] strArr = new String[dataTable.getRecordListCurrentPage().size()][dataTable.getColumnNameArr().length];
		int row1 = 0;
		 DecimalFormat df = new DecimalFormat("0.##");
		 SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		 
		for (int row = 0; row < dataTable.getRecordListCurrentPage().size(); row++) {
			row1++;
			SalesDTO sales = (SalesDTO) dataTable.getRecordListCurrentPage().get(row);
			String statusIconClass =
				    sales.getStatus().equals("PENDING") ? "fa-clock" :
				    sales.getStatus().equals("PROCESSING") ? "fa-spinner fa-spin" :
				    sales.getStatus().equals("SHIPPED") ? "fa-truck" :
				    sales.getStatus().equals("DELIVERED") ? "fa-check-circle" :
				    sales.getStatus().equals("CANCELLED") ? "fa-times-circle text-danger" :
				    "fa-question-circle text-secondary";
			strArr[row][0] = sales.getCode();
			strArr[row][1] = sales.getUser().getFirstName()+" "+sales.getUser().getLastName();
			strArr[row][2] = String.valueOf(sales.getTotal());
			strArr[row][3] = String.valueOf(sales.getPaymentStatus());
			// Determine icon class based on status


			strArr[row][4] = sales.getStatus();

			strArr[row][5] = String.valueOf(sales.getSalesPayment().getTotalAmountPaid());
			strArr[row][6] = formatter.format(sales.getDate());

			// Build the buttons
			strArr[row][7] = dataTable.getRecordButtonStr(sessionInfo, sales.getCode()) +

			    // View Payment button
			    "<button type=\"button\" class=\"btn btn-success\" data-toggle=\"tooltip\" data-placement=\"top\" title=\"View Payment\" " +
			    "onclick=\"" + SalesDTO.ACTION_VIEW_SALES_PAYMENT + "(" + sales.getId() + ", '" + sales.getTotal() + "', '" + sales.getCode() + "', '" + sales.getSalesPayment().getTotalAmountPaid() + "')\">" +
			    "<i class=\"fa fa-money-bill\"></i></button> " +

"<div class='dropdown-wrapper' style='display: inline-block; position: relative;'>" +
"<label style='display: flex; align-items: center; gap: 6px; background-color: #ffc107; color: black; padding: 6px 12px; border-radius: 4px; cursor: pointer;'>" +
    "<i class='fa " + statusIconClass + "' style='color: black;'></i>" +
    "<select name='changeStatusCode' id='changeStatusCode' onchange=\"" + SalesDTO.ACTION_CHANGE_SALES_STATUS + "(" + sales.getId() + ", '" + sales.getCode() + "', this.value)\"" +
        " style='background: transparent; border: none; color: black; font-weight: bold; margin-left: 5px; appearance: none; outline: none; cursor: pointer;'>" +
        "<option value='"+sales.getCode()+", Pending'" + ("PENDING".equals(sales.getStatus()) ? " selected" : "") + ">Pending</option>" +
        "<option value='"+sales.getCode()+", Processing'" + ("PROCESSING".equals(sales.getStatus()) ? " selected" : "") + ">Processing</option>" +
        "<option value='"+sales.getCode()+", Shipped'" + ("SHIPPED".equals(sales.getStatus()) ? " selected" : "") + ">Shipped</option>" +
        "<option value='"+sales.getCode()+", Delivered'" + ("DELIVERED".equals(sales.getStatus()) ? " selected" : "") + ">Delivered</option>" +
        "<option value='"+sales.getCode()+", Cancelled'" + ("CANCELLED".equals(sales.getStatus()) ? " selected" : "") + ">Cancelled</option>" +
    "</select>" +
"</label>" +
"</div>";


			
		}
		return strArr;
	}
	public static String getPaymentEntrySts(SessionInfo sessionInfo, SalesDTO sales) {
	    StringBuffer strBuff = new StringBuffer();
	    strBuff.append(new TextBoxWebControl().getTextBoxWebControl("col-lg-3", true, "AmountPaid", "AmountPaid", "AmountPaid", String.valueOf(sales.getSalesPayment().getAmountPaid()), 45, WebControlBase.DATA_TYPE_INTEGER, ""));
	    return strBuff.toString();
	}
	public static String getDataEntryStr(SessionInfo sessionInfo, SalesDTO sales, List<DTOBase> userList, List<DTOBase> itemList) {
	    StringBuffer strBuff = new StringBuffer();

	    // Date Input
	    strBuff.append("<div class='col-lg-3'>");
	    strBuff.append("    Date <font color='red' style='font-weight:700'>*</font>");
	    strBuff.append("    <input type='text' class='form-control' id='txtDate' name='txtDate' value='" + DateTimeUtil.getDateTimeToStr(sales.getDate(), "MM/dd/yyyy") + "'>");
	    strBuff.append("    <script>");
	    strBuff.append("        $('#txtDate').daterangepicker({");
	    strBuff.append("            singleDatePicker: true,");
	    strBuff.append("            showDropdowns: true,");
	    strBuff.append("            locale: { format: 'MM/DD/YYYY' }");
	    strBuff.append("        });");
	    strBuff.append("    </script>");
	    strBuff.append("</div>");

	    strBuff.append(new SelectWebControl().getSelectWebControl("col-lg-4", true, "Customer", "Customer", userList, sales.getUser(), "-Select-", "0", ""));

	    strBuff.append("<div id='salesEntryContainer' style='display: flex; flex-wrap: wrap; gap: 10px;'>");

	    strBuff.append("<div class='sales-entry' style='display: flex; gap: 10px; align-items: center; width: 100%;'>");
	    strBuff.append("<div id='hiddenFields' style='display: none;'>");
	    strBuff.append(new TextBoxWebControl().getTextBoxWebControl("col-lg-3", true, "Total", "Total", "HiddenTotal", String.valueOf(sales.getTotal()), 45, WebControlBase.DATA_TYPE_INTEGER, ""));
	    strBuff.append(new TextBoxWebControl().getTextBoxWebControl("col-lg-3", true, "hiddenItems", "hiddenItems", "HiddenItems", "", 255, WebControlBase.DATA_TYPE_STRING, ""));
	    strBuff.append(new SelectWebControl().getSelectWebControl("col-lg-4", true, "Item", "Item", itemList, sales.getItem(), "-Select-", "0", ""));
	    strBuff.append(new TextBoxWebControl().getTextBoxWebControl("col-lg-3", true, "Quantity", "Quantity", "Quantity", String.valueOf(sales.getSalesDetails().getQuantity()), 45, WebControlBase.DATA_TYPE_INTEGER, ""));
	    strBuff.append(new TextBoxWebControl().getTextBoxWebControl("col-lg-3", true, "UnitPrice", "UnitPrice", "UnitPrice", String.valueOf(sales.getSalesDetails().getUnitPrice()), 45, WebControlBase.DATA_TYPE_INTEGER, ""));
	    strBuff.append("</div>");	
	    strBuff.append("<button type='button' id='btnAddItem' class='btn btn-primary' style='width: 150px; margin-top: 10px; display: block; margin-left: auto; margin-right: auto;'>Add Item</button>");
	    strBuff.append("</div>");

	    strBuff.append("</div>");

	    strBuff.append("<script>");
	    strBuff.append("$(document).ready(function() {");
	    strBuff.append("    let index = 1;");
	    strBuff.append("    $('#btnAddItem').click(function() {");
	    strBuff.append("        let newEntry = $(\"<div class='sales-entry' style='display: flex; gap: 10px; align-items: center; width: 100%;'>\" +");
	    strBuff.append("            \"<label style='width: 100px;'>Item:</label>\" +");
	    strBuff.append("            \"<select class='form-control dynamic-item' name='cboItem[]'>\" +");
	    strBuff.append("                $('#cboItem').html() +");  // Clone options
	    strBuff.append("            \"</select>\" +");
	    strBuff.append("            \"<label style='width: 100px;'>Quantity:</label>\" +");
	    strBuff.append("            \"<input type='text' class='form-control dynamic-quantity' name='txtQuantity[]' placeholder='Quantity' />\" +");
	    strBuff.append("            \"<label style='width: 100px;'>Unit Price:</label>\" +");
	    strBuff.append("            \"<input type='text' class='form-control dynamic-unitprice' name='txtUnitPrice[]' placeholder='Unit Price' />\" +");
	    strBuff.append("            \"<button type='button' class='btn btn-danger btnRemoveItem' style='height: 38px;'>Remove</button>\" +");
	    strBuff.append("        \"</div>\");");

	    strBuff.append("        $('#salesEntryContainer').append(newEntry);");
	    strBuff.append("        updateMainFields();");
	    strBuff.append("    });");

	    strBuff.append("    $(document).on('change', '.dynamic-item', function() { updateMainFields(); });");
	    strBuff.append("    $(document).on('input', '.dynamic-quantity, .dynamic-unitprice', function() { updateMainFields(); });");

	    strBuff.append("    $(document).on('click', '.btnRemoveItem', function() {");
	    strBuff.append("        $(this).closest('.sales-entry').remove();");
	    strBuff.append("        updateMainFields();");
	    strBuff.append("    });");

	    strBuff.append("    function updateMainFields() {");
	    strBuff.append("        let totalPrice = 0;");
	    strBuff.append("        $('.sales-entry').each(function() {");
	    strBuff.append("            let quantity = parseFloat($(this).find('.dynamic-quantity').val()) || 0;");
	    strBuff.append("            let unitPrice = parseFloat($(this).find('.dynamic-unitprice').val()) || 0;");
	    strBuff.append("            totalPrice += quantity * unitPrice;");
	    strBuff.append("        });");
	    strBuff.append("        $('#txtHiddenTotal').val(totalPrice.toFixed(2));"); // Hidden total field
	    strBuff.append("        $('.dynamic-total').val(totalPrice.toFixed(2));"); 
	    strBuff.append("        let allItems = $('.dynamic-item').map(function() { return $(this).val(); }).get().filter(q => q.trim() !== '');");
	    strBuff.append("        let allQuantities = $('.dynamic-quantity').map(function() { return $(this).val(); }).get().filter(q => q.trim() !== '');");
	    strBuff.append("        let allUnitPrices = $('.dynamic-unitprice').map(function() { return $(this).val(); }).get().filter(q => q.trim() !== '');");

	    strBuff.append("        $('#txtHiddenItems').val(allItems.join(', '));");
	    strBuff.append("        $('#txtQuantity').val(allQuantities.join(', '));");
	    strBuff.append("        $('#txtUnitPrice').val(allUnitPrices.join(', '));");
	    strBuff.append("    }");
	    strBuff.append("});");
	    strBuff.append("</script>");

	    // Total Field with Label and Adjusted Width
	    strBuff.append("<label>Total:</label>");
	    strBuff.append("<input type='text' class='form-control col-lg-3 dynamic-total' name='total' placeholder='' style='width: 150px;' />");

	    
	    strBuff.append(new SelectWebControl().getSelectWebControl("col-lg-4", true, "PaymentMethod", "PaymentMethod", new String[]{"Cash", "Credit Card", "Bank Transfer", "Online Payment"}, sales.getSalesPayment().getPaymentMethod(), new String[]{"Cash", "Credit Card", "Bank Transfer", "Online Payment"}, "NA", "", ""));
	    strBuff.append(new TextBoxWebControl().getTextBoxWebControl("col-lg-3", true, "AmountPaid", "AmountPaid", "AmountPaid", String.valueOf(sales.getSalesPayment().getAmountPaid()), 45, WebControlBase.DATA_TYPE_INTEGER, ""));
	    return strBuff.toString();
	}

	public static String getDataViewStr(SessionInfo sessionInfo, SalesDTO sales) {
		StringBuffer strBuff = new StringBuffer();
		strBuff.append("<p>Code: " + sales.getCode() + "</p>");
		strBuff.append("<p>Item: </p>");
		strBuff.append("<p>Customer:"+sales.getUser().getFirstName()+" "+sales.getUser().getLastName()+" </p>");
		strBuff.append("<p>Quantity: " + sales.getSalesDetails().getQuantity() + "</p>");
		strBuff.append("<p>Unit Price: " + sales.getSalesDetails().getUnitPrice() + "</p>");
		strBuff.append("<p>Total: " + sales.getTotal() + "</p>");
		strBuff.append("<p>Payment Status: " + sales.getPaymentStatus() + "</p>");
		strBuff.append("<p>Status: " + sales.getStatus() + "</p>");
		strBuff.append("<p>Payment Method: " + sales.getSalesPayment().getPaymentMethod() + "</p>");
		strBuff.append("<p>Amount Paid: " + sales.getSalesPayment().getAmountPaid() + "</p>");
		strBuff.append("<p>Reference: " + sales.getSalesPayment().getReference() + "</p>");
		strBuff.append("</div>");
		return strBuff.toString();
	}

	public static String getDataViewStr(SessionInfo sessionInfo, SalesDTO sales, List<DTOBase> salesUnitList, List<DTOBase> salesCategoryList) {
		StringBuffer strBuff = new StringBuffer();
		return strBuff.toString();
}

}

