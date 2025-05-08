<%@ page import="java.util.*"%>
<%@ page import="com.mytechnopal.*"%>
<%@ page import="com.mytechnopal.base.WebControlBase"%>
<%@ page import="com.mytechnopal.base.DTOBase"%>
<%@ page import="com.mytechnopal.dto.*"%>
<%@ page import="com.mytechnopal.*"%>
<%@ page import="com.mytechnopal.util.*"%>
<%@ page import="com.mytechnopal.dao.*"%>
<%@ page import="com.mytechnopal.webcontrol.*" %>
<%@ page import="com.laponhcet.sales.*" %>

<%
SessionInfo sessionInfo = (SessionInfo) session.getAttribute(SessionInfo.SESSION_INFO);
DataTable dataTable = (DataTable)session.getAttribute(SalesDTO.SESSION_SALES_DATA_TABLE);
String dataInput = "changeStatusCode: $('#changeStatusCode').val(), salesCode: $('#salesCode').val(), FormattedDate: $('#FormattedDate').val(), PaymentMethod: $('#PaymentMethod').val(), salesId: $('#salesId').val(), total: $('#total').val(), amount: $('#amount').val(), txtHiddenTotal: $('#txtHiddenTotal').val(), txtHiddenItems: $('#txtHiddenItems').val(), cboItem: $('#cboItem').val(), txtQuantity: $('#txtQuantity').val(), txtUnitPrice: $('#txtUnitPrice').val(), cboPaymentStatus: $('#cboPaymentStatus').val(), cboStatus: $('#cboStatus').val(), txtAmountPaid: $('#txtAmountPaid').val(), cboPaymentMethod: $('#cboPaymentMethod').val(), txtReference: $('#txtReference').val(), cboCustomer: $('#cboCustomer').val(), txtDate: $('#txtDate').val()"; 

// Sepate Sales and Sales Payment

%>
<div class="container" id='<%=dataTable.getId()%>'></div>


<%=SalesUtil.showPaymentModal()%>




<script>

setTimeout(function (){
list<%=sessionInfo.getCurrentLink().getCode()%>();
}, 100); 


function updateTableAfterPayment() {
    $.ajax({
        url: 'SalesActionAjax', // Replace with your actual server endpoint
        type: 'POST',
        data: {
            action: 'ACTION_VIEW_SALES_PAYMENT_SAVE',
            salesId: $('#salesId').val(), // Include necessary data
            salesCode: $('#salesCode').val(),
            amount: $('#amount').val(),
            total: $('#total').val(),
            PaymentMethod: $('#PaymentMethod').val(),
            FormattedDate: $('#FormattedDate').val()
        },
        success: function(response) {
        	alert(response);
            if (response.message) {
            	
                alert(response.message); // Display success message
                
            }

            // Update the table content
            if (response.tableContent) {
                $('#<%=dataTable.getId()%>').html(response.tableContent);
            }
        },
        error: function(xhr, status, error) {
            console.error('Error:', error); // Log the error for debugging
            alert('An error occurred while processing your request.');
        }
    });
}


function toggleDropdown(button) {
    const dropdown = button.nextElementSibling;
    // Close all others first
    document.querySelectorAll('.dropdown-content').forEach(menu => {
        if (menu !== dropdown) menu.style.display = 'none';
    });
    dropdown.style.display = dropdown.style.display === 'block' ? 'none' : 'block';
}

// Optional: close dropdowns when clicking outside
document.addEventListener('click', function(e) {
    if (!e.target.closest('.dropdown-wrapper')) {
        document.querySelectorAll('.dropdown-content').forEach(menu => {
            menu.style.display = 'none';
        });
    }
});
function closeModal() {
    document.getElementById('customModal').style.display = 'none';
  }
function ACTION_VIEW_SALES_PAYMENT_SAVE() {	
	$.ajax({		url: 'AjaxController?txtSelectedLink=032',		
			data: {		txtAction: 'ACTION_VIEW_SALES_PAYMENT_SAVE',
				changeStatusCode: $('#changeStatusCode').val(), 
				salesCode: $('#salesCode').val(), 
				FormattedDate: $('#FormattedDate').val(), 
				PaymentMethod: $('#PaymentMethod').val(), 
				salesId: $('#salesId').val(), total: $('#total').val(), 
				amount: $('#amount').val(), 
				txtHiddenTotal: $('#txtHiddenTotal').val(), 
				txtHiddenItems: $('#txtHiddenItems').val(), 
				cboItem: $('#cboItem').val(), 
				txtQuantity: $('#txtQuantity').val(), 
				txtUnitPrice: $('#txtUnitPrice').val(), 
				cboPaymentStatus: $('#cboPaymentStatus').val(), 
				cboStatus: $('#cboStatus').val(), 
				txtAmountPaid: $('#txtAmountPaid').val(), 
				cboPaymentMethod: $('#cboPaymentMethod').val(), 
				txtReference: $('#txtReference').val(), 
				cboCustomer: $('#cboCustomer').val(), 
				txtDate: $('#txtDate').val()		},		
				method: 'POST',		
				dataType: 'JSON',		

				success: function(response) {
				    if (response.MESSAGE_TYPE != '') {
				        Swal.fire({
				            title: response.MESSAGE_TITLE,
				            text: response.MESSAGE_STR,
				            icon: response.MESSAGE_TYPE
				        });
				    }

				    // Update the table content
				    if (response.PAGE_CONTENT) {
				        document.getElementById('<%=dataTable.getId()%>').innerHTML = response.PAGE_CONTENT;
				    }

				    console.log("Updated Table Content:", response.PAGE_CONTENT);
				}

				
	});}

  
  
document.addEventListener('DOMContentLoaded', function () {
	  document.getElementById('saveeeee').addEventListener('click', function () {
		  console.log("sasdasdsadsadsa");
			 document.getElementById('customModal').style.display = 'none';
	  });
	});
	
// Transfer to ActionAjax
<%-- function ACTION_VIEW_SALES_PAYMENT(id, total, code, amountPaid) {
	
	//Transfer to Util
    document.getElementById('modalTotal').textContent = total;
    document.getElementById('modalPaid').textContent = amountPaid;
    document.getElementById('salesId').value = id;
    document.getElementById('salesCode').value = code;
    document.getElementById('total').value = total;
    document.getElementById('customModal').style.display = 'flex';
    
    // Remove the duplicate table container if it exists
    const duplicateContainer = document.getElementById('<%=dataTable.getId()%>');
    if (duplicateContainer) {
        duplicateContainer.remove();
    }
} --%>

<%=WebUtil.getJSList(sessionInfo, dataTable)%>
<%=WebUtil.getJSAddView(sessionInfo, dataTable, dataTable.getId())%>
<%=WebUtil.getJSAddSave(sessionInfo, dataTable, dataInput)%>
<%=WebUtil.getJSUpdate(sessionInfo, dataTable, dataInput)%>
<%=WebUtil.getJSDelete(sessionInfo, dataTable)%>
<%-- <%=WebUtil.getJSCustom(sessionInfo, SalesDTO.ACTION_VIEW_SALES_PAYMENT_SAVE, dataInput, sessionInfo.getCurrentLink().getPageId(), ActionResponse.DIALOG_TYPE_SWAL)%> --%>
<%=WebUtil.getJSCustom(sessionInfo, SalesDTO.ACTION_CHANGE_SALES_STATUS, dataInput, sessionInfo.getCurrentLink().getPageId(), ActionResponse.DIALOG_TYPE_SWAL)%>
function cancel(){
	alert("cancel");
}

</script> 

<style>
  #customModal {
    display: none;
    position: fixed;
    z-index: 9999;
    left: 0; top: 0;
    width: 100%; height: 100%;
    background-color: rgba(0,0,0,0.5);
    justify-content: center;
    align-items: center;
  }

  .modal-content {
    background-color: #fff;
    padding: 20px;
    border-radius: 10px;
    width: 300px;
    box-shadow: 0 0 10px rgba(0,0,0,0.3);
    text-align: center;
  }

  .modal-content h3 {
    margin-top: 0;
  }

  .modal-content input,
  .modal-content select {
    width: 100%;
    padding: 8px;
    margin: 10px 0;
  }

  .modal-content button {
    padding: 8px 12px;
    margin: 5px;
    border: none;
    border-radius: 5px;
    cursor: pointer;
  }

  .btn-submit {
    background-color: #3498db;
    color: white;
  }

  .btn-cancel {
    background-color: #e74c3c;
    color: white;
  }
  /* Style the dropdown wrapper */
.dropdown-wrapper {
    position: relative;
    display: inline-block;
}

.dropdown-toggle {
    cursor: pointer;
}

.dropdown-content {
    display: none;
    position: absolute;
    top: 100%;
    left: 0;
    margin-top: 5px;
    background-color: #fff;
    border: 1px solid #ccc;
    min-width: 180px;
    z-index: 1000;
    padding: 8px;
    border-radius: 8px;
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
}

.dropdown-content .dropdown-item {
    display: block;
    width: 100%;
    padding: 8px 12px;
    background-color: #f8f9fa;
    border: none;
    border-radius: 5px;
    text-align: left;
    font-size: 14px;
    color: #333;
    transition: background-color 0.2s ease;
    margin-bottom: 4px;
}

.dropdown-content .dropdown-item:hover {
    background-color: #e2e6ea;
}
</style>
