<%@ page import="java.util.*"%>
<%@ page import="com.mytechnopal.*"%>
<%@ page import="com.mytechnopal.base.WebControlBase"%>
<%@ page import="com.mytechnopal.base.DTOBase"%>
<%@ page import="com.mytechnopal.dto.*"%>
<%@ page import="com.mytechnopal.util.*"%>
<%@ page import="com.mytechnopal.dao.*"%>
<%@ page import="com.mytechnopal.webcontrol.*" %>
<%@ page import="com.mytechnopal.SessionInfo, com.mytechnopal.DataTable" %>
<%@ page import="com.laponhcet.itemunit.*" %>

<%
SessionInfo sessionInfo = (SessionInfo) session.getAttribute(SessionInfo.SESSION_INFO);
DataTable dataTable = (DataTable) session.getAttribute(ItemUnitDTO.SESSION_ITEM_UNIT_DATA_TABLE);
String dataInput = "txtCode: $('#txtCode').val(), txtName: $('#txtName').val()"; 
%>

<div class="container" id='<%=dataTable != null ? dataTable.getId() : "dataTableNull"%>'></div>

<script>
    setTimeout(function () {
            list<%=sessionInfo.getCurrentLink().getCode()%>();
    }, 100);

    <% if (dataTable != null) { %>
        <%=WebUtil.getJSList(sessionInfo, dataTable)%>
        <%=WebUtil.getJSAddView(sessionInfo, dataTable, dataTable.getId())%>
        <%=WebUtil.getJSAddSave(sessionInfo, dataTable, dataInput)%>
        <%=WebUtil.getJSUpdate(sessionInfo, dataTable, dataInput)%>
        <%=WebUtil.getJSDelete(sessionInfo, dataTable)%>
    <% } else { %>
        console.error("DataTable is null, JS functions cannot be executed.");
    <% } %>
</script>
