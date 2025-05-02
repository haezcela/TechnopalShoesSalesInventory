package com.laponhcet.sales;

import java.io.Serializable;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import com.laponhcet.item.ItemDTO;
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
    "<select name='changeStatusCode' onchange=\"handleStatusChange('" + sales.getCode() + "', this.value)\"" +
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
	    strBuff.append("<div class='row p-1'>");
	 // Date Input
	    strBuff.append("<div class='col-lg-6'>");
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
	    
	    strBuff.append(new SelectWebControl().getSelectWebControl("col-lg-6", true, "Customer", "Customer", userList, sales.getUser(), "-Select-", "0", ""));
	    
		strBuff.append("</div>");
		
		strBuff.append("<div class='row p-1' id='addItemRow'>");

		// Dropdown for Item
		strBuff.append("<div class='col-lg-6'>");
		strBuff.append("<label>Item</label>");
		strBuff.append("<select class='form-control' id='selectItem'>");
		strBuff.append("<option value='' data-price='0'>-Select-</option>");
		for (DTOBase item : itemList) {
		    ItemDTO itm = (ItemDTO) item;
		    strBuff.append("<option value='" + itm.getCode() + "' data-name='" + itm.getName() + "' data-price='" + itm.getUnitPrice() + "'>" + itm.getName() + "</option>");
		}
		strBuff.append("</select>");
		strBuff.append("</div>");

//		// Quantity Input
//		strBuff.append("<div class='col-lg-3'>");
//		strBuff.append("<label>Quantity</label>");
//		strBuff.append("<input type='number' class='form-control' id='inputQuantity' min='1' value='1' />");
//		strBuff.append("</div>");
		
		// Quantity Input with - and + buttons
		strBuff.append("<div class='col-lg-4'>");
		strBuff.append("<label>Quantity</label>");
		strBuff.append("<div class='input-group'>");
		strBuff.append("  <div class='input-group-prepend'>");
		strBuff.append("    <button class='btn btn btn-outline-danger' type='button' id='btnDecreaseQty'>-</button>");
		strBuff.append("  </div>");
		strBuff.append("  <input type='number' class='form-control text-center' id='inputQuantity' min='1' value='1' />");
		strBuff.append("  <div class='input-group-append'>");
		strBuff.append("    <button class='btn btn btn-outline-success' type='button' id='btnIncreaseQty'>+</button>");
		strBuff.append("  </div>");
		strBuff.append("</div>");
		strBuff.append("</div>");
		
		strBuff.append("<script>");
		strBuff.append("$(document).ready(function() {");

		strBuff.append("    $('#btnDecreaseQty').click(function() {");
		strBuff.append("        let qty = parseInt($('#inputQuantity').val()) || 1;");
		strBuff.append("        if (qty > 1) {");
		strBuff.append("            $('#inputQuantity').val(qty - 1);");
		strBuff.append("        }");
		strBuff.append("    });");

		strBuff.append("    $('#btnIncreaseQty').click(function() {");
		strBuff.append("        let qty = parseInt($('#inputQuantity').val()) || 1;");
		strBuff.append("        $('#inputQuantity').val(qty + 1);");
		strBuff.append("    });");

		strBuff.append("});");
		strBuff.append("</script>");


		// Add Button
		strBuff.append("<div class='col-lg-2 d-flex align-items-end'>");
		strBuff.append("<button type='button' class='btn btn-success' id='btnAddToTable'>Add</button>");
		strBuff.append("</div>");

		strBuff.append("</div>");

		// Table to display the added items
		strBuff.append("<div class='row mt-3'>");
		strBuff.append("<div class='col-lg-12'>");
		strBuff.append("<table class='table table-bordered' id='itemTable'>");
		strBuff.append("<thead>");
		strBuff.append("<tr>");
		strBuff.append("<th>Item</th>");
		strBuff.append("<th>Unit Price</th>");
		strBuff.append("<th>Quantity</th>");
		strBuff.append("<th>Subtotal</th>");
		strBuff.append("<th></th>");
		strBuff.append("</tr>");
		strBuff.append("</thead>");
		strBuff.append("<tbody></tbody>");
		strBuff.append("</table>");
		strBuff.append("</div>");
		strBuff.append("</div>");

		strBuff.append("<script>");
		strBuff.append("$(document).ready(function() {");

		strBuff.append("    function updateTotal() {");
		strBuff.append("        let total = 0;");
		strBuff.append("        $('#itemTable tbody tr').each(function() {");
		strBuff.append("            let subtotal = parseFloat($(this).find('.subtotal').text()) || 0;");
		strBuff.append("            total += subtotal;");
		strBuff.append("        });");
		strBuff.append("        total = Math.round((total + Number.EPSILON) * 100) / 100;");
		strBuff.append("        $('.dynamic-total').val(total.toFixed(2));");
		strBuff.append("        $('#txtHiddenTotal').val(total.toFixed(2));");
		strBuff.append("    }");

		strBuff.append("    function updateHiddenFields() {");
		strBuff.append("        let itemCodes = [];");
		strBuff.append("        let quantities = [];");
		strBuff.append("        let unitPrices = [];");
		strBuff.append("        $('#itemTable tbody tr').each(function() {");
		strBuff.append("            itemCodes.push($(this).data('code'));");
		strBuff.append("            quantities.push($(this).find('.quantity').text());");
		strBuff.append("            unitPrices.push($(this).find('.unit-price').text());");
		strBuff.append("        });");
		strBuff.append("        $('#txtHiddenItems').val(itemCodes.join(','));");
		strBuff.append("        $('#txtQuantity').val(quantities.join(','));");
		strBuff.append("        $('#txtUnitPrice').val(unitPrices.join(','));");
		strBuff.append("    }");

		strBuff.append("    $('#btnAddToTable').click(function() {");
		strBuff.append("        let selected = $('#selectItem option:selected');");
		strBuff.append("        let itemCode = selected.val();");
		strBuff.append("        let itemName = selected.data('name');");
		strBuff.append("        let unitPrice = parseFloat(selected.data('price'));");
		strBuff.append("        let quantity = parseInt($('#inputQuantity').val());");

		strBuff.append("        if (!itemCode || quantity <= 0 || isNaN(unitPrice)) {");
		strBuff.append("            alert('Please select a valid item and quantity.');");
		strBuff.append("            return;");
		strBuff.append("        }");

		strBuff.append("        let existingRow = $('#itemTable tbody tr[data-code=\"' + itemCode + '\"]');");
		strBuff.append("        if (existingRow.length > 0) {");
		strBuff.append("            let existingQty = parseInt(existingRow.find('.quantity').text());");
		strBuff.append("            let newQty = existingQty + quantity;");
		strBuff.append("            let newSubtotal = unitPrice * newQty;");
		strBuff.append("            existingRow.find('.quantity').text(newQty);");
		strBuff.append("            existingRow.find('.subtotal').text(newSubtotal.toFixed(2));");
		strBuff.append("        } else {");
		strBuff.append("            let subtotal = unitPrice * quantity;");
		strBuff.append("            let newRow = \"<tr data-code='\" + itemCode + \"'>\" +");
		strBuff.append("                \"<td>\" + itemName + \"</td>\" +");
		strBuff.append("                \"<td class='unit-price'>\" + unitPrice.toFixed(2) + \"</td>\" +");
		strBuff.append("                \"<td class='quantity'>\" + quantity + \"</td>\" +");
		strBuff.append("                \"<td class='subtotal'>\" + subtotal.toFixed(2) + \"</td>\" +");
		strBuff.append("                \"<td><button class='btn btn-danger btn-sm btnRemoveRow'>Remove</button></td>\" +");
		strBuff.append("            \"</tr>\";");
		strBuff.append("            $('#itemTable tbody').append(newRow);");
		strBuff.append("        }");

		strBuff.append("        updateTotal();");
		strBuff.append("        updateHiddenFields();");
		strBuff.append("    });");

		strBuff.append("    $(document).on('click', '.btnRemoveRow', function() {");
		strBuff.append("        $(this).closest('tr').remove();");
		strBuff.append("        updateTotal();");
		strBuff.append("        updateHiddenFields();");
		strBuff.append("    });");
		
		strBuff.append("});");
		strBuff.append("</script>");

		// Hidden Fields  
		strBuff.append("<div id='hiddenFields' style='display: none;'>");

		strBuff.append(new TextBoxWebControl().getTextBoxWebControl("col-lg-3", true, "HiddenItems", "HiddenItems", "HiddenItems", "", 255, WebControlBase.DATA_TYPE_STRING, ""));
		strBuff.append(new TextBoxWebControl().getTextBoxWebControl("col-lg-3", true, "Quantity", "Quantity", "Quantity", "", 45, WebControlBase.DATA_TYPE_INTEGER, ""));
		strBuff.append(new TextBoxWebControl().getTextBoxWebControl("col-lg-3", true, "UnitPrice", "UnitPrice", "UnitPrice", "", 45, WebControlBase.DATA_TYPE_INTEGER, ""));

		strBuff.append("</div>");

	    
	    strBuff.append("<div class='row p-1'>");

	 	// Total Field with Label and Adjusted Width
	 	strBuff.append("<div class='col-lg-4'>");
	 	strBuff.append("<label class='p-0 m-0' style='font-weight: bold;'>Total</label>");
	 	strBuff.append("<input type='text' class='form-control dynamic-total w-100' id='txtHiddenTotal' name='total' placeholder='' readonly />");
	 	strBuff.append("</div>");
	    
	    strBuff.append(new SelectWebControl().getSelectWebControl("col-lg-4", true, "PaymentMethod", "PaymentMethod", new String[]{"Cash", "Credit Card", "Bank Transfer", "Online Payment"}, sales.getSalesPayment().getPaymentMethod(), new String[]{"Cash", "Credit Card", "Bank Transfer", "Online Payment"}, "NA", "", ""));
	    strBuff.append(new TextBoxWebControl().getTextBoxWebControl("col-lg-4", true, "AmountPaid", "AmountPaid", "AmountPaid", String.valueOf(sales.getSalesPayment().getAmountPaid()), 45, WebControlBase.DATA_TYPE_INTEGER, ""));
		strBuff.append("</div>");
	    
	    return strBuff.toString();
	}

	
	public static String getPaymentModalStr() {
	    StringBuffer strBuff = new StringBuffer();
	    strBuff.append("<!-- Payment Modal -->");
	    strBuff.append("<div id=\"customModal\">");
	    strBuff.append("  <div class=\"modal-content\">");
	    strBuff.append("    <h3>Enter amount of payment</h3>");
	    strBuff.append("    <p>Total: <span id=\"modalTotal\"></span></p>");
	    strBuff.append("    <p>Amount Paid: <span id=\"modalPaid\"></span></p>");
	    strBuff.append("    <!-- Amount input -->");
	    strBuff.append("    <input type=\"number\" id=\"amount\" name=\"amount\" placeholder=\"Type your payment amount\">");
	    strBuff.append("");
	    strBuff.append("    <!-- Dropdown -->");
	    strBuff.append("    <label for=\"PaymentMethod\">Choose an option:</label>");
	    strBuff.append("    <select id=\"PaymentMethod\" name=\"PaymentMethod\">");
	    strBuff.append("      <option value=\"\">-- Please select --</option>");
	    strBuff.append("      <option value=\"Online Payment\">Online Payment</option>");
	    strBuff.append("      <option value=\"Cash\">Cash</option>");
	    strBuff.append("      <option value=\"Credit Card\">Credit Card</option>");
	    strBuff.append("      <option value=\"Bank Transfer\">Bank Transfer</option>");
	    strBuff.append("    </select>");
	    strBuff.append("");
	    strBuff.append("    <!-- Date input -->");
	    strBuff.append("    <div class=\"col-lg-3\" style=\"margin-top: 10px;\">");
	    strBuff.append("      <label for=\"date\">Date:</label>");
	    strBuff.append("        <input type=\"date\" id=\"FormattedDate\" name=\"FormattedDate\">");
	    strBuff.append("    </div>");
	    strBuff.append("");
	    strBuff.append("    <!-- Hidden fields -->");
	    strBuff.append("    <input type=\"hidden\" id=\"salesId\" name=\"salesId\">");
	    strBuff.append("    <input type=\"hidden\" id=\"salesCode\" name=\"salesCode\">");
	    strBuff.append("    <input type=\"hidden\" id=\"total\" name=\"total\">");
	    strBuff.append("");
	    strBuff.append("    <!-- Buttons -->");
	    strBuff.append("    <div style=\"margin-top: 15px;\">");
	    strBuff.append("      <button class=\"btn-submit\" id=\"saveeeee\" onclick=\"ACTION_VIEW_SALES_PAYMENT_SAVE()\">Submit</button>");
	    strBuff.append("      <button class=\"btn-cancel\" onclick=\"closeModal()\">Cancel</button>");
	    strBuff.append("    </div>");
	    strBuff.append("  </div>");
	    strBuff.append("</div>");
	    
	    return strBuff.toString();
	}
	
	
	public static String getPaymentModalStyles() {
	    StringBuffer strBuff = new StringBuffer();
	    strBuff.append("<style>");
	    strBuff.append("  #customModal {");
	    strBuff.append("    display: none;");
	    strBuff.append("    position: fixed;");
	    strBuff.append("    z-index: 9999;");
	    strBuff.append("    left: 0; top: 0;");
	    strBuff.append("    width: 100%; height: 100%;");
	    strBuff.append("    background-color: rgba(0,0,0,0.5);");
	    strBuff.append("    justify-content: center;");
	    strBuff.append("    align-items: center;");
	    strBuff.append("  }");
	    
	    strBuff.append("</style>");
	    

	    strBuff.append("<style>");
	    strBuff.append("  #customModal {");
	    strBuff.append("    display: none;");
	    strBuff.append("    position: fixed;");
	    strBuff.append("    z-index: 9999;");
	    strBuff.append("    left: 0; top: 0;");
	    strBuff.append("    width: 100%; height: 100%;");
	    strBuff.append("    background-color: rgba(0,0,0,0.5);");
	    strBuff.append("    justify-content: center;");
	    strBuff.append("    align-items: center;");
	    strBuff.append("  }");

	    strBuff.append("  .modal-content {");
	    strBuff.append("    background-color: #fff;");
	    strBuff.append("    padding: 20px;");
	    strBuff.append("    border-radius: 10px;");
	    strBuff.append("    width: 300px;");
	    strBuff.append("    box-shadow: 0 0 10px rgba(0,0,0,0.3);");
	    strBuff.append("    text-align: center;");
	    strBuff.append("  }");

	    strBuff.append("  .modal-content h3 {");
	    strBuff.append("    margin-top: 0;");
	    strBuff.append("  }");

	    strBuff.append("  .modal-content input,");
	    strBuff.append("  .modal-content select {");
	    strBuff.append("    width: 100%;");
	    strBuff.append("    padding: 8px;");
	    strBuff.append("    margin: 10px 0;");
	    strBuff.append("  }");

	    strBuff.append("  .modal-content button {");
	    strBuff.append("    padding: 8px 12px;");
	    strBuff.append("    margin: 5px;");
	    strBuff.append("    border: none;");
	    strBuff.append("    border-radius: 5px;");
	    strBuff.append("    cursor: pointer;");
	    strBuff.append("  }");

	    strBuff.append("  .btn-submit {");
	    strBuff.append("    background-color: #3498db;");
	    strBuff.append("    color: white;");
	    strBuff.append("  }");

	    strBuff.append("  .btn-cancel {");
	    strBuff.append("    background-color: #e74c3c;");
	    strBuff.append("    color: white;");
	    strBuff.append("  }");

	    strBuff.append("  .dropdown-wrapper {");
	    strBuff.append("    position: relative;");
	    strBuff.append("    display: inline-block;");
	    strBuff.append("  }");

	    strBuff.append("  .dropdown-toggle {");
	    strBuff.append("    cursor: pointer;");
	    strBuff.append("  }");

	    strBuff.append("  .dropdown-content {");
	    strBuff.append("    display: none;");
	    strBuff.append("    position: absolute;");
	    strBuff.append("    top: 100%;");
	    strBuff.append("    left: 0;");
	    strBuff.append("    margin-top: 5px;");
	    strBuff.append("    background-color: #fff;");
	    strBuff.append("    border: 1px solid #ccc;");
	    strBuff.append("    min-width: 180px;");
	    strBuff.append("    z-index: 1000;");
	    strBuff.append("    padding: 8px;");
	    strBuff.append("    border-radius: 8px;");
	    strBuff.append("    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);");
	    strBuff.append("  }");

	    strBuff.append("  .dropdown-content .dropdown-item {");
	    strBuff.append("    display: block;");
	    strBuff.append("    width: 100%;");
	    strBuff.append("    padding: 8px 12px;");
	    strBuff.append("    background-color: #f8f9fa;");
	    strBuff.append("    border: none;");
	    strBuff.append("    border-radius: 5px;");
	    strBuff.append("    text-align: left;");
	    strBuff.append("    font-size: 14px;");
	    strBuff.append("    color: #333;");
	    strBuff.append("    transition: background-color 0.2s ease;");
	    strBuff.append("    margin-bottom: 4px;");
	    strBuff.append("  }");

	    strBuff.append("  .dropdown-content .dropdown-item:hover {");
	    strBuff.append("    background-color: #e2e6ea;");
	    strBuff.append("  }");
	    strBuff.append("</style>");
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