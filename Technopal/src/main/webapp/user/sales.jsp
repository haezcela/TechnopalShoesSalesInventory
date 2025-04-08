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
String dataInput = "txtHiddenTotal: $('#txtHiddenTotal').val(), txtHiddenItems: $('#txtHiddenItems').val(), cboItem: $('#cboItem').val(), txtQuantity: $('#txtQuantity').val(), txtUnitPrice: $('#txtUnitPrice').val(), cboPaymentStatus: $('#cboPaymentStatus').val(), cboStatus: $('#cboStatus').val(), txtAmountPaid: $('#txtAmountPaid').val(), cboPaymentMethod: $('#cboPaymentMethod').val(), txtReference: $('#txtReference').val(), cboCustomer: $('#cboCustomer').val()"; 
%>

<div class="container" id='<%=dataTable.getId()%>'></div>

<script>
setTimeout(function (){
list<%=sessionInfo.getCurrentLink().getCode()%>();
}, 100); 

<%=WebUtil.getJSList(sessionInfo, dataTable)%>
<%=WebUtil.getJSAddView(sessionInfo, dataTable, dataTable.getId())%>
<%=WebUtil.getJSAddSave(sessionInfo, dataTable, dataInput)%>
<%=WebUtil.getJSUpdate(sessionInfo, dataTable, dataInput)%>
<%=WebUtil.getJSDelete(sessionInfo, dataTable)%>
function cancel(){
	alert("cancel");
}


</script> 