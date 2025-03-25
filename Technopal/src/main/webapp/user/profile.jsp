<%@ page import="java.util.*" %>
<%@ page import="com.mytechnopal.*" %>
<%@ page import="com.mytechnopal.base.*" %>
<%@ page import="com.mytechnopal.dto.*" %>
<%@ page import="com.mytechnopal.link.*" %>
<%@ page import="com.mytechnopal.util.*" %>
<%@ page import="com.mytechnopal.webcontrol.*" %>

<%
SessionInfo sessionInfo = (SessionInfo) session.getAttribute(SessionInfo.SESSION_INFO);
String dataInput = "txtUserName: $('#txtUserName').val(), txtPassword: $('#txtPassword').val(), txtCPNumber: $('#txtCPNumber').val()"; 
%>

<div class="container" id='divProfile'></div>

<script>
	setTimeout(function (){
		getProfile();
	}, 100); 

	<%=WebUtil.getJSUpdate(sessionInfo, dataInput)%>
	
	function getProfile() {
		$.ajax({
			url: 'AjaxController?txtSelectedLink=<%=sessionInfo.getCurrentLink().getCode()%>&txtAction=<%=DataTable.ACTION_UPDATE_VIEW%>',
			type: 'POST',
		  	dataType: 'JSON',
		  	success: function(response) { 
		  		$("#divProfile").html(response.<%=LinkDTO.PAGE_CONTENT%>);
			}
		});	
	}
</script>