<%@ page import="java.util.*"%>
<%@ page import="com.mytechnopal.*"%>
<%@ page import="com.mytechnopal.base.WebControlBase"%>
<%@ page import="com.mytechnopal.base.DTOBase"%>
<%@ page import="com.mytechnopal.dto.*"%>
<%@ page import="com.mytechnopal.*"%>
<%@ page import="com.mytechnopal.util.*"%>
<%@ page import="com.mytechnopal.dao.*"%>
<%@ page import="com.mytechnopal.webcontrol.*" %>

<%@ page import="com.laponhcet.pageant.*" %>

<%
SessionInfo sessionInfo = (SessionInfo) session.getAttribute(SessionInfo.SESSION_INFO);
DataTable dataTable = (DataTable)session.getAttribute(EventCriteriaDTO.SESSION_EVENT_CRITERIA_DATA_TABLE);
String dataInput = " txtCode: $('#txtCode').val(), txtName: $('#txtName').val(), txtShortName: $('#txtShortName').val(), txtMaxScore: $('#txtMaxScore').val(), cboEventName: $('#cboEventName').val()"; 
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
