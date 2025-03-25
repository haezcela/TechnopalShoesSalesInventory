<%@ page import="java.util.*"%>
<%@ page import="com.mytechnopal.*"%>
<%@ page import="com.mytechnopal.base.WebControlBase"%>
<%@ page import="com.mytechnopal.base.DTOBase"%>
<%@ page import="com.mytechnopal.dto.*"%>
<%@ page import="com.mytechnopal.*"%>
<%@ page import="com.mytechnopal.util.*"%>
<%@ page import="com.mytechnopal.dao.*"%>
<%@ page import="com.mytechnopal.webcontrol.*" %>
<%@ page import="com.laponhcet.examschedule.*" %>

<%
SessionInfo sessionInfo = (SessionInfo) session.getAttribute(SessionInfo.SESSION_INFO);
DataTable dataTable = (DataTable)session.getAttribute(ExamScheduleDTO.SESSION_EXAMSCHEDULE_DATATABLE);
%>

<div class="container" id='<%=dataTable.getId()%>'></div>

<script>
	setTimeout(function (){
		list<%=sessionInfo.getCurrentLink().getCode()%>();
	}, 50); 

	<%=WebUtil.getJSList(sessionInfo, dataTable)%>
	<%=WebUtil.getJSCustom(sessionInfo, ExamScheduleDTO.ACTION_ADD_EXAMINEE, "txtScheduleCode:$('#txtSelectedRecord').val()", dataTable.getId(), ActionResponse.DIALOG_TYPE_SWAL)%>
	<%=WebUtil.getJSCustom(sessionInfo, ExamScheduleDTO.ACTION_VIEW_EXAMINEE, "txtScheduleCode:$('#txtSelectedRecord').val()", dataTable.getId(), ActionResponse.DIALOG_TYPE_SWAL)%>
	<%=WebUtil.getJSCustom(sessionInfo, ExamScheduleDTO.ACTION_EMAIL_EXAMINEE, "txtScheduleCode:$('#txtSelectedRecord').val()", dataTable.getId(), ActionResponse.DIALOG_TYPE_SWAL, "processing();")%>

	function addExaminee(scheduleCode) {
		$("#txtSelectedRecord").val(scheduleCode);
		<%=ExamScheduleDTO.ACTION_ADD_EXAMINEE%>();
	}
	
	function viewExaminee(scheduleCode) {
		$("#txtSelectedRecord").val(scheduleCode);
		<%=ExamScheduleDTO.ACTION_VIEW_EXAMINEE%>();
	}
	
	function emailExaminee(scheduleCode) {
		$("#txtSelectedRecord").val(scheduleCode);
		<%=ExamScheduleDTO.ACTION_EMAIL_EXAMINEE%>();
	}
	
	function processing() {
		$("#divEmailButton").html("Sending email...");
	}
	
	function printExaminee(ExamScheduleCode) {
		printPreview("divExaminee" + ExamScheduleCode, "BCC-Admission");
	}
</script>	