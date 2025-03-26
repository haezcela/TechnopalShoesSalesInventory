<%@ page import="java.util.*"%>
<%@ page import="com.mytechnopal.*"%>
<%@ page import="com.mytechnopal.base.WebControlBase"%>
<%@ page import="com.mytechnopal.base.DTOBase"%>
<%@ page import="com.mytechnopal.dto.*"%>
<%@ page import="com.mytechnopal.*"%>
<%@ page import="com.mytechnopal.util.*"%>
<%@ page import="com.mytechnopal.dao.*"%>
<%@ page import="com.mytechnopal.webcontrol.*" %>

<%@ page import="com.laponhcet.item.*" %>

<%
SessionInfo sessionInfo = (SessionInfo) session.getAttribute(SessionInfo.SESSION_INFO);
DataTable dataTable = (DataTable)session.getAttribute(ItemDTO.SESSION_ITEM_DATA_TABLE);
String dataInput = "txtName: $('#txtName').val(), txtDescription: $('#txtDescription').val(), txtUnitPrice: $('#txtUnitPrice').val(), txtQuantity: $('#txtQuantity').val(), txtReorderpoint: $('#txtReorderpoint').val(), cboItemCategory: $('#cboItemCategory').val(), cboItemUnit: $('#cboItemUnit').val()"; 
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


</script> 