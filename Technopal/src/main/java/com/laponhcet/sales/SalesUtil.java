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
			strArr[row][0] = sales.getCode();
			strArr[row][1] = sales.getUser().getFirstName()+" "+sales.getUser().getLastName();
			strArr[row][2] = String.valueOf(sales.getTotal());
			strArr[row][3] = String.valueOf(sales.getPaymentStatus());
			strArr[row][4] = sales.getStatus();
			strArr[row][5] = sales.getSalesPayment().getPaymentMethod();
			strArr[row][6] = String.valueOf(sales.getSalesPayment().getAmountPaid());
			strArr[row][7] = sales.getSalesPayment().getReference();
			strArr[row][8] = formatter.format(sales.getDate());
			strArr[row][9] = dataTable.getRecordButtonStr(sessionInfo, sales.getCode())+"<button type=\"button\" class=\"btn btn-success\" data-toggle=\"tooltip\" data-placement=\"top\" title=\"Delete Record\" onclick=\"dataTableRecordActionSESSION_SALES_DATA_TABLE('ACTION_ADD_VIEW_PAYMENT', " + row1 + ", '" + sales.getCode() + "')\"><i class=\"fa fa-money-bill\"></i></button>";
			
		}
		return strArr;
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

		// Check if item already exists
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
		strBuff.append("    });");

		strBuff.append("    $(document).on('click', '.btnRemoveRow', function() {");
		strBuff.append("        $(this).closest('tr').remove();");
		strBuff.append("        updateTotal();");
		strBuff.append("    });");

		strBuff.append("});");
		strBuff.append("</script>");



		// Hidden Fields
		strBuff.append("<div id='hiddenFields' style='display: none;'>");

		strBuff.append(new TextBoxWebControl().getTextBoxWebControl("col-lg-3", true, "HiddenItems", "HiddenItems", "txtHiddenItems", "", 255, WebControlBase.DATA_TYPE_STRING, ""));
		strBuff.append(new TextBoxWebControl().getTextBoxWebControl("col-lg-3", true, "Quantity", "Quantity", "txtQuantity", "", 45, WebControlBase.DATA_TYPE_INTEGER, ""));
		strBuff.append(new TextBoxWebControl().getTextBoxWebControl("col-lg-3", true, "UnitPrice", "UnitPrice", "txtUnitPrice", "", 45, WebControlBase.DATA_TYPE_INTEGER, ""));

		strBuff.append("</div>");

		
	    
	    strBuff.append("<div class='row p-1'>");

	 // Total Field with Label and Adjusted Width
	 strBuff.append("<div class='col-lg-4'>");
	 strBuff.append("<label class='p-0 m-0' style='font-weight: bold;'>Total</label>");

	 strBuff.append("<input type='text' class='form-control dynamic-total w-100' name='total' placeholder='' readonly />");
	 strBuff.append("</div>");
	    
	    strBuff.append(new SelectWebControl().getSelectWebControl("col-lg-4", true, "PaymentMethod", "PaymentMethod", new String[]{"Cash", "Credit Card", "Bank Transfer", "Online Payment"}, sales.getSalesPayment().getPaymentMethod(), new String[]{"Cash", "Credit Card", "Bank Transfer", "Online Payment"}, "NA", "", ""));
	    strBuff.append(new TextBoxWebControl().getTextBoxWebControl("col-lg-4", true, "AmountPaid", "AmountPaid", "AmountPaid", String.valueOf(sales.getSalesPayment().getAmountPaid()), 45, WebControlBase.DATA_TYPE_INTEGER, ""));
		strBuff.append("</div>");
	    
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

//TEST PULLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL
//TEST PULLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL222222222222222