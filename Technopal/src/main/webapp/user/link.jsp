<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.mytechnopal.base.WebControlBase"%>
<%@ page import="com.mytechnopal.base.DTOBase"%>
<%@ page import="com.mytechnopal.*"%>
<%@ page import="com.mytechnopal.dto.*"%>
<%@ page import="com.mytechnopal.util.*"%>
<%@ page import="com.mytechnopal.dao.*"%>
<%@ page import="com.mytechnopal.webcontrol.*" %>
<%@ page import="com.mytechnopal.link.*"%>

<%
SessionInfo sessionInfo = (SessionInfo) session.getAttribute(SessionInfo.SESSION_INFO);
DataTable dataTable = (DataTable)session.getAttribute(LinkDTO.SESSION_LINK_DATA_TABLE);
LinkDTO link = (LinkDTO)session.getAttribute(LinkDTO.SESSION_LINK);
String dataInput = "txtClassForName: $('#txtClassForName').val(), txtPage: $('#txtPage').val(), txtLabel: $('#txtLabel').val(), txtDescription: $('#txtDescription').val(), txtSeq: $('#txtSeq').val(), txtIcon: $('#txtIcon').val(), txtPurposes: $('#txtPurposes').val(), txtIsUser: $('#txtIsUser').val()"; 
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