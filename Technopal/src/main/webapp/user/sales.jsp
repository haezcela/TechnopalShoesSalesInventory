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
%>

<div class="container" id='<%=dataTable.getId()%>'></div>
<div class='container' id='<%=sessionInfo.getCurrentLink().getPageId()%>'></div>

<div id="customModal">
  <div class="modal-content">
    <h3>Enter amount of payment</h3>
    <p>Total: <span id="modalTotal"></span></p>
	<p>Amount Paid: <span id="modalPaid"></span></p>
    <!-- Amount input -->
    <input type="number" id="amount" name="amount" placeholder="Type your payment amount">

    <!-- Dropdown -->
    <label for="PaymentMethod">Choose an option:</label>
    <select id="PaymentMethod" name="PaymentMethod">
      <option value="">-- Please select --</option>
      <option value="Online Payment">Online Payment</option>
      <option value="Cash">Cash</option>
      <option value="Credit Card">Credit Card</option>
      <option value="Bank Transfer">Bank Transfer</option>
    </select>

    <!-- Date input -->
    <div class="col-lg-3" style="margin-top: 10px;">
      <label for="date">Date:</label>
		<input type="date" id="FormattedDate" name="FormattedDate">
    </div>

    <!-- Hidden fields -->
    <input type="hidden" id="salesId" name="salesId">
    <input type="hidden" id="salesCode" name="salesCode">
    <input type="hidden" id="total" name="total">

    <!-- Buttons -->
    <div style="margin-top: 15px;">
      <button class="btn-submit" id="saveeeee" onclick="ACTION_VIEW_SALES_PAYMENT_SAVE()">Submit</button>
      <button class="btn-cancel" onclick="closeModal()">Cancel</button>
    </div>
  </div>
</div>
<script>





setTimeout(function (){
list<%=sessionInfo.getCurrentLink().getCode()%>();
}, 100); 


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
document.addEventListener('DOMContentLoaded', function () {
	  document.getElementById('saveeeee').addEventListener('click', function () {
		  console.log("sasdasdsadsadsa");
			 document.getElementById('customModal').style.display = 'none';
	  });
	});

function ACTION_VIEW_SALES_PAYMENT(id, total, code, amountPaid) {
	document.getElementById('modalTotal').textContent = total;
	document.getElementById('modalPaid').textContent = amountPaid;
    document.getElementById('salesId').value = id;
    document.getElementById('salesCode').value = code;
    document.getElementById('total').value = total;
    document.getElementById('customModal').style.display = 'flex';
  }


<%=WebUtil.getJSList(sessionInfo, dataTable)%>
<%=WebUtil.getJSAddView(sessionInfo, dataTable, dataTable.getId())%>
<%=WebUtil.getJSAddSave(sessionInfo, dataTable, dataInput)%>
<%=WebUtil.getJSUpdate(sessionInfo, dataTable, dataInput)%>
<%=WebUtil.getJSDelete(sessionInfo, dataTable)%>
<%=WebUtil.getJSCustom(sessionInfo, SalesDTO.ACTION_VIEW_SALES_PAYMENT_SAVE, dataInput, sessionInfo.getCurrentLink().getPageId(), ActionResponse.DIALOG_TYPE_SWAL)%>
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
