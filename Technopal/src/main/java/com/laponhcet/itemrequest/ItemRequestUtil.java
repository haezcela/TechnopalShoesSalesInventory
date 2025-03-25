package com.laponhcet.itemrequest;

import java.io.Serializable;

import java.text.DecimalFormat;
import java.time.LocalDate;

import com.laponhcet.enrollment.EnrollmentDTO;
import com.mytechnopal.DataTable;
import com.mytechnopal.SessionInfo;
import com.mytechnopal.base.WebControlBase;
import com.mytechnopal.webcontrol.DataTableWebControl;
import com.mytechnopal.webcontrol.SelectWebControl;
import com.mytechnopal.webcontrol.TextBoxWebControl;

public class ItemRequestUtil implements Serializable {
	private static final long serialVersionUID = 1L;

	public static String getDataTableStr(SessionInfo sessionInfo, DataTable dataTable) {
		DataTableWebControl dtwc = new DataTableWebControl(sessionInfo, dataTable);
		StringBuffer strBuff = new StringBuffer();
		if (dataTable.getRecordList().size() >= 1) {
			strBuff.append(dtwc.getDataTableHeader(sessionInfo, dataTable));
			dataTable.setDataTableRecordArr(getDataTableCurrentPageRecordArr(sessionInfo, dataTable));
			strBuff.append(dtwc.getDataTable(true, false));
		}
		return strBuff.toString();
	}
	
	

	private static String[][] getDataTableCurrentPageRecordArr(SessionInfo sessionInfo, DataTable dataTable) {
	    String[][] strArr = new String[dataTable.getRecordListCurrentPage().size()][dataTable.getColumnNameArr().length];
	  
	    
	    for (int row = 0; row < dataTable.getRecordListCurrentPage().size(); row++) {
	        ItemRequestDetailsDTO item = (ItemRequestDetailsDTO) dataTable.getRecordListCurrentPage().get(row);
	        
	        
	        strArr[row][0] = item.getCode();
	        
	        strArr[row][1] = item.getDateTime() != null ? item.getDateTime() : "Dec. 12, 2024 02:35 PM";  
	        
	        strArr[row][2] = dataTable.getRecordButtonStr(sessionInfo, item.getCode());
	    }
	    
	    return strArr;
	}


	public static String getDataEntryStr(SessionInfo sessionInfo, ItemRequestDetailsDTO item) {
		StringBuffer strBuff = new StringBuffer();

        
		// Form group with Office label and search bar
		strBuff.append("<div class='form-group row' style='display: flex; justify-content: space-between; align-items: center;'>");

		// Office label with outline
		strBuff.append("<div class='col-md-6'>");
		strBuff.append("<label for='office' style='outline: 1px solid #ddd; padding: 5px; display: inline-block;'>Office:</label>");
		strBuff.append("</div>");

		// Search bar with outline
		strBuff.append("<div class='col-md-6' style='text-align: right;'>");
		strBuff.append("<input type='text' class='form-control' id='searchBar' placeholder='Search...' style='width: 50%; display: inline-block; outline: 1px solid #ddd; padding: 5px;'>");
		strBuff.append("</div>");
		strBuff.append("</div>");


        strBuff.append("<table style='width: 100%; border-collapse: collapse; margin-bottom: 20px;'>");
        strBuff.append("<thead>");
        strBuff.append("<tr>");
        strBuff.append("<th style='border: 1px solid #ddd; padding: 8px;'>Item Code</th>");
        strBuff.append("<th style='border: 1px solid #ddd; padding: 8px;'>Item Name</th>");
        strBuff.append("<th style='border: 1px solid #ddd; padding: 8px;'>Unit</th>");
        strBuff.append("<th style='border: 1px solid #ddd; padding: 8px;'>Stocks</th>");
        strBuff.append("<th style='border: 1px solid #ddd; padding: 8px;'>Select</th>");
        strBuff.append("<th style='border: 1px solid #ddd; padding: 8px;'>Quantity Needed</th>");
        strBuff.append("</tr>");
        strBuff.append("</thead>");
        strBuff.append("<tbody>");

        for (int i = 0; i < 3; i++) {
            String itemCode = "IC00" + (i + 1);
            String itemName = "Item Name " + (i + 1);
            int stocks = 100 - (i * 10);

            strBuff.append("<tr>");
            strBuff.append("<td style='border: 1px solid #ddd; padding: 8px;'>" + itemCode + "</td>");
            strBuff.append("<td style='border: 1px solid #ddd; padding: 8px;'>" + itemName + "</td>");
            strBuff.append("<td style='border: 1px solid #ddd; padding: 8px;'>PCS</td>");
            strBuff.append("<td style='border: 1px solid #ddd; padding: 8px;'>" + stocks + "</td>");
            strBuff.append("<td style='border: 1px solid #ddd; padding: 8px; text-align: center;'><input type='checkbox' class='form-check-input' onclick='toggleQuantityInput(this, " + i + ", \"" + itemName + "\")'></td>");
            strBuff.append("<td style='border: 1px solid #ddd; padding: 8px;'><input type='number' class='form-control' name='qtyNeeded" + i + "' id='qtyNeeded" + i + "' style='width: 100%; padding: 6px; display: none;' placeholder='Enter quantity'></td>");
            strBuff.append("</tr>");
        }

        strBuff.append("</tbody>");
        strBuff.append("</table>");

        // Add spacing and header with Preview List
        strBuff.append("<div style='display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px;'>");
        strBuff.append("<h3 style='margin: 0 auto;'>Requested Items</h3>");
        strBuff.append("<span style='margin-right: 0; padding-left: 20px;'>Preview List</span>");
        strBuff.append("</div>");

        strBuff.append("<table id='requestedItemsTable' style='width: 100%; border-collapse: collapse;'>");
        strBuff.append("<thead>");
        strBuff.append("<tr>");
        strBuff.append("<th style='border: 1px solid #ddd; padding: 8px;'>Item Name</th>");
        strBuff.append("<th style='border: 1px solid #ddd; padding: 8px;'>Unit</th>");
        strBuff.append("<th style='border: 1px solid #ddd; padding: 8px;'>Quantity</th>");
        strBuff.append("<th style='border: 1px solid #ddd; padding: 8px;'>Date Needed</th>");
        strBuff.append("<th style='border: 1px solid #ddd; padding: 8px;'>Action</th>");
        strBuff.append("</tr>");
        strBuff.append("</thead>");
        strBuff.append("<tbody>");
        strBuff.append("</tbody>");
        strBuff.append("</table>");

        strBuff.append("<script>");
        strBuff.append("function toggleQuantityInput(checkbox, index, itemName) {");
        strBuff.append("  var input = document.getElementById('qtyNeeded' + index);");
        strBuff.append("  if (checkbox.checked) {");
        strBuff.append("    input.style.display = 'block';");
        strBuff.append("    input.onchange = function() {");
        strBuff.append("      addItemToRequestedList(itemName, 'PCS', input.value);");
        strBuff.append("    };");
        strBuff.append("  } else {");
        strBuff.append("    input.style.display = 'none';");
        strBuff.append("    removeItemFromRequestedList(itemName);");
        strBuff.append("  }");
        strBuff.append("}");
        strBuff.append("function addItemToRequestedList(itemName, unit, quantity) {");
        strBuff.append("  var table = document.getElementById('requestedItemsTable').getElementsByTagName('tbody')[0];");
        strBuff.append("  var existingRow = document.querySelector('tr[data-item-name=\"' + itemName + '\"]');");
        strBuff.append("  if (existingRow) {");
        strBuff.append("    existingRow.cells[2].innerText = quantity;");
        strBuff.append("  } else {");
        strBuff.append("    var row = table.insertRow();");
        strBuff.append("    row.setAttribute('data-item-name', itemName);");
        strBuff.append("    var currentDate = new Date().toISOString().split('T')[0];");
        strBuff.append("    row.innerHTML = '<td>' + itemName + '</td><td>' + unit + '</td><td>' + quantity + '</td><td>' + currentDate + '</td>' + ");
        strBuff.append("                    '<td><button onclick=\"removeItemFromRequestedList(\\'' + itemName + '\\')\">Delete</button></td>'; ");
        strBuff.append("  }");
        strBuff.append("}");
        strBuff.append("function removeItemFromRequestedList(itemName) {");
        strBuff.append("  var table = document.getElementById('requestedItemsTable').getElementsByTagName('tbody')[0];");
        strBuff.append("  var rows = table.getElementsByTagName('tr');");
        strBuff.append("  for (var i = 0; i < rows.length; i++) {");
        strBuff.append("    if (rows[i].getAttribute('data-item-name') === itemName) {");
        strBuff.append("      table.deleteRow(i);");
        strBuff.append("      break;");
        strBuff.append("    }");
        strBuff.append("  }");
        strBuff.append("}");
        strBuff.append("</script>");

        return strBuff.toString();
    }

	public static String getDataViewStr(SessionInfo sessionInfo, ItemRequestDetailsDTO itemSelected) {
	    StringBuffer strBuff = new StringBuffer();
	    
	    strBuff.append("<div class='col-lg-12'>");
	    strBuff.append("    <table class='table table-bordered'>");
	    strBuff.append("        <thead>");
	    strBuff.append("            <tr>");
	    strBuff.append("                <th>Item Code</th>");
	    strBuff.append("                <th>Item Name</th>");
	    strBuff.append("                <th>Unit</th>");
	    strBuff.append("                <th>Stocks</th>");
	    strBuff.append("                <th>Quantity Needed</th>");
	    strBuff.append("            </tr>");
	    strBuff.append("        </thead>");
	    strBuff.append("        <tbody>");
	    
	    // Add a single item row (you can add more rows as needed)
	    strBuff.append("            <tr>");
	    strBuff.append("                <td>IC001</td>");
	    strBuff.append("                <td>Item Name 1</td>");
	    strBuff.append("                <td>PCS</td>");
	    strBuff.append("                <td>100</td>");
	    strBuff.append("                <td>24</td>");
	    strBuff.append("            </tr>");
	    
	    // Example of another item
	    strBuff.append("            <tr>");
	    strBuff.append("                <td>IC002</td>");
	    strBuff.append("                <td>Item Name 2</td>");
	    strBuff.append("                <td>BOX</td>");
	    strBuff.append("                <td>50</td>");
	    strBuff.append("                <td>10</td>");
	    strBuff.append("            </tr>");
	    
	    strBuff.append("        </tbody>");
	    strBuff.append("    </table>");
	    strBuff.append("</div>");
	    
	    return strBuff.toString();
	}



}
