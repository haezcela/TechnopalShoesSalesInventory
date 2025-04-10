package com.laponhcet.salesdetails;

import java.io.Serializable;
import java.sql.Connection;
import java.text.DecimalFormat;
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


public class SalesDetailsUtil implements Serializable {
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
		 DecimalFormat df = new DecimalFormat("0.##");
		for (int row = 0; row < dataTable.getRecordListCurrentPage().size(); row++) {
			
			SalesDetailsDTO salesDetails = (SalesDetailsDTO) dataTable.getRecordListCurrentPage().get(row);

			strArr[row][0] = salesDetails.getSalesCode();
			strArr[row][1] = String.valueOf(salesDetails.getQuantity());
			strArr[row][2] = String.valueOf(salesDetails.getUnitPrice());
			strArr[row][3] = "";//+salesDetails.getItem().getName();
			strArr[row][4] = dataTable.getRecordButtonStr(sessionInfo, salesDetails.getCode());
			
		}
		return strArr;
	}
	public static String getDataEntryStr(SessionInfo sessionInfo, SalesDetailsDTO salesDetails) {
	    StringBuffer strBuff = new StringBuffer();
//
//	    // Date Input
//	    strBuff.append("<div class='col-lg-3'>");
//	    strBuff.append("    Date <font color='red' style='font-weight:700'>*</font>");
//	    strBuff.append("    <input type='text' class='form-control' id='txtDate' name='txtDate' value='" + DateTimeUtil.getDateTimeToStr(salesDetails.getDate(), "MM/dd/yyyy") + "'>");
//	    strBuff.append("    <script>");
//	    strBuff.append("        $('#txtDate').daterangepicker({");
//	    strBuff.append("            singleDatePicker: true,");
//	    strBuff.append("            showDropdowns: true,");
//	    strBuff.append("            locale: { format: 'MM/DD/YYYY' }");
//	    strBuff.append("        });");
//	    strBuff.append("    </script>");
//	    strBuff.append("</div>");
//
//	    strBuff.append(new SelectWebControl().getSelectWebControl("col-lg-4", true, "Customer", "Customer", userList, salesDetails.getUser(), "-Select-", "0", ""));
//
//	    strBuff.append("<div id='salesDetailsEntryContainer' style='display: flex; flex-wrap: wrap; gap: 10px;'>");
//
//	    strBuff.append("<div class='salesDetails-entry' style='display: flex; gap: 10px; align-items: center; width: 100%;'>");
//	    strBuff.append("<div id='hiddenFields' style='display: none;'>");
//	    strBuff.append(new TextBoxWebControl().getTextBoxWebControl("col-lg-3", true, "Total", "Total", "HiddenTotal", String.valueOf(salesDetails.getTotal()), 45, WebControlBase.DATA_TYPE_INTEGER, ""));
//	    strBuff.append(new TextBoxWebControl().getTextBoxWebControl("col-lg-3", true, "hiddenItems", "hiddenItems", "HiddenItems", "", 255, WebControlBase.DATA_TYPE_STRING, ""));
//	    strBuff.append(new SelectWebControl().getSelectWebControl("col-lg-4", true, "Item", "Item", itemList, salesDetails.getItem(), "-Select-", "0", ""));
//	    strBuff.append(new TextBoxWebControl().getTextBoxWebControl("col-lg-3", true, "Quantity", "Quantity", "Quantity", String.valueOf(salesDetails.getSalesDetailsDetails().getQuantity()), 45, WebControlBase.DATA_TYPE_INTEGER, ""));
//	    strBuff.append(new TextBoxWebControl().getTextBoxWebControl("col-lg-3", true, "UnitPrice", "UnitPrice", "UnitPrice", String.valueOf(salesDetails.getSalesDetailsDetails().getUnitPrice()), 45, WebControlBase.DATA_TYPE_INTEGER, ""));
//	    strBuff.append("</div>");	
//	    strBuff.append("<button type='button' id='btnAddItem' class='btn btn-primary' style='width: 150px; margin-top: 10px; display: block; margin-left: auto; margin-right: auto;'>Add Item</button>");
//	    strBuff.append("</div>");
//
//	    strBuff.append("</div>");
//
//	    strBuff.append("<script>");
//	    strBuff.append("$(document).ready(function() {");
//	    strBuff.append("    let index = 1;");
//	    strBuff.append("    $('#btnAddItem').click(function() {");
//	    strBuff.append("        let newEntry = $(\"<div class='salesDetails-entry' style='display: flex; gap: 10px; align-items: center; width: 100%;'>\" +");
//	    strBuff.append("            \"<label style='width: 100px;'>Item:</label>\" +");
//	    strBuff.append("            \"<select class='form-control dynamic-item' name='cboItem[]'>\" +");
//	    strBuff.append("                $('#cboItem').html() +");  // Clone options
//	    strBuff.append("            \"</select>\" +");
//	    strBuff.append("            \"<label style='width: 100px;'>Quantity:</label>\" +");
//	    strBuff.append("            \"<input type='text' class='form-control dynamic-quantity' name='txtQuantity[]' placeholder='Quantity' />\" +");
//	    strBuff.append("            \"<label style='width: 100px;'>Unit Price:</label>\" +");
//	    strBuff.append("            \"<input type='text' class='form-control dynamic-unitprice' name='txtUnitPrice[]' placeholder='Unit Price' />\" +");
//	    strBuff.append("            \"<button type='button' class='btn btn-danger btnRemoveItem' style='height: 38px;'>Remove</button>\" +");
//	    strBuff.append("        \"</div>\");");
//
//	    strBuff.append("        $('#salesDetailsEntryContainer').append(newEntry);");
//	    strBuff.append("        updateMainFields();");
//	    strBuff.append("    });");
//
//	    strBuff.append("    $(document).on('change', '.dynamic-item', function() { updateMainFields(); });");
//	    strBuff.append("    $(document).on('input', '.dynamic-quantity, .dynamic-unitprice', function() { updateMainFields(); });");
//
//	    strBuff.append("    $(document).on('click', '.btnRemoveItem', function() {");
//	    strBuff.append("        $(this).closest('.salesDetails-entry').remove();");
//	    strBuff.append("        updateMainFields();");
//	    strBuff.append("    });");
//
//	    strBuff.append("    function updateMainFields() {");
//	    strBuff.append("        let totalPrice = 0;");
//	    strBuff.append("        $('.salesDetails-entry').each(function() {");
//	    strBuff.append("            let quantity = parseFloat($(this).find('.dynamic-quantity').val()) || 0;");
//	    strBuff.append("            let unitPrice = parseFloat($(this).find('.dynamic-unitprice').val()) || 0;");
//	    strBuff.append("            totalPrice += quantity * unitPrice;");
//	    strBuff.append("        });");
//	    strBuff.append("        $('#txtHiddenTotal').val(totalPrice.toFixed(2));"); // Hidden total field
//	    strBuff.append("        $('.dynamic-total').val(totalPrice.toFixed(2));"); 
//	    strBuff.append("        let allItems = $('.dynamic-item').map(function() { return $(this).val(); }).get().filter(q => q.trim() !== '');");
//	    strBuff.append("        let allQuantities = $('.dynamic-quantity').map(function() { return $(this).val(); }).get().filter(q => q.trim() !== '');");
//	    strBuff.append("        let allUnitPrices = $('.dynamic-unitprice').map(function() { return $(this).val(); }).get().filter(q => q.trim() !== '');");
//
//	    strBuff.append("        $('#txtHiddenItems').val(allItems.join(', '));");
//	    strBuff.append("        $('#txtQuantity').val(allQuantities.join(', '));");
//	    strBuff.append("        $('#txtUnitPrice').val(allUnitPrices.join(', '));");
//	    strBuff.append("    }");
//	    strBuff.append("});");
//	    strBuff.append("</script>");
//
//	    // Total Field with Label and Adjusted Width
//	    strBuff.append("<label>Total:</label>");
//	    strBuff.append("<input type='text' class='form-control col-lg-3 dynamic-total' name='total' placeholder='' style='width: 150px;' />");
//
//	    
//	    strBuff.append(new SelectWebControl().getSelectWebControl("col-lg-4", true, "PaymentMethod", "PaymentMethod", new String[]{"Cash", "Credit Card", "Bank Transfer", "Online Payment"}, salesDetails.getSalesDetailsPayment().getPaymentMethod(), new String[]{"Cash", "Credit Card", "Bank Transfer", "Online Payment"}, "NA", "", ""));
//	    strBuff.append(new TextBoxWebControl().getTextBoxWebControl("col-lg-3", true, "AmountPaid", "AmountPaid", "AmountPaid", String.valueOf(salesDetails.getSalesDetailsPayment().getAmountPaid()), 45, WebControlBase.DATA_TYPE_INTEGER, ""));
	    return strBuff.toString();
	}

	public static String getDataViewStr(SessionInfo sessionInfo, SalesDetailsDTO salesDetails) {
		StringBuffer strBuff = new StringBuffer();

		strBuff.append("<p>Item: " + salesDetails.getItem().getName() + "</p>");
		strBuff.append("<p>Quantity: " + salesDetails.getQuantity() + "</p>");
		strBuff.append("<p>Unit Price: " + salesDetails.getUnitPrice() + "</p>");
		strBuff.append("</div>");

		return strBuff.toString();
	}

	public static String getDataViewStr(SessionInfo sessionInfo, SalesDetailsDTO salesDetails, List<DTOBase> salesDetailsUnitList, List<DTOBase> salesDetailsCategoryList) {
		StringBuffer strBuff = new StringBuffer();
		return strBuff.toString();
}

}

