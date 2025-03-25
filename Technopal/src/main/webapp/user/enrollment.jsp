<%@ page import="java.util.*"%>
<%@ page import="com.mytechnopal.*"%>
<%@ page import="com.mytechnopal.base.WebControlBase"%>
<%@ page import="com.mytechnopal.base.DTOBase"%>
<%@ page import="com.mytechnopal.dto.*"%>
<%@ page import="com.mytechnopal.*"%>
<%@ page import="com.mytechnopal.util.*"%>
<%@ page import="com.mytechnopal.dao.*"%>
<%@ page import="com.mytechnopal.webcontrol.*" %>
<%@ page import="com.laponhcet.enrollment.*" %>

<%@ page import="com.laponhcet.academicsection.*" %>
<%@ page import="com.laponhcet.student.*" %>

<%
SessionInfo sessionInfo = (SessionInfo) session.getAttribute(SessionInfo.SESSION_INFO);
DataTable dataTable = (DataTable)session.getAttribute(EnrollmentDTO.SESSION_ENROLLMENT_DATA_TABLE);
String dataInput = "txtLastName: $('#txtLastName').val(), txtFirstName: $('#txtFirstName').val(), txtMiddleName: $('#txtMiddleName').val(), cboSuffixName: $('#cboSuffixName').val(), txtLearnerReferenceNumber: $('#txtLearnerReferenceNumber').val(), cboAcademicSection: $('#cboAcademicSection').val(), cboStatus: $('#cboStatus').val(), cboGender: $('#cboGender').val(), txtContactName: $('#txtContactName').val(), txtContact: $('#txtContact').val(), cboContactRelationship: $('#cboContactRelationship').val()"; 
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
	
	function uploadFileReset(action, fileId, label, fileType) {
		$.ajax({
			url: 'UploadFileAjaxController?txtAction=' + action + '&txtFileId=' + fileId + '&txtLabel=' + label + '&txtFileType=' + fileType, 
		  	type: 'POST',
		  	dataType: 'JSON',
		  	success: function(response) {  
		  		$("#divUploadedFilePict" + fileId).html(response.uploadedFileContent);
		  		$("#divUploadedFileRemarks" + fileId).html(response.uploadedFileRemarks);
			}
		});	
	}
	
	function uploadFile(action, fileId, cFunction, label, fileType) {
		$.ajax({
			url: 'UploadFileAjaxController?txtAction=' + action + '&txtFileId=' + fileId + '&txtLabel=' + label + '&txtFileType=' + fileType, 
		  	type: 'POST',
		  	data: new FormData($('#frmMain')[0]), // The form with the file inputs.
		  	processData: false,
		  	contentType: false,                   // Using FormData, no need to process data.
		  	dataType: 'JSON',
		  	success: function(response) {  
		  		if(response.MESSAGE_TYPE === "<%=ActionResponse.MESSAGE_SWAL_TYPE_SUCCESS%>") {
		  			$("#divUploadedFilePict" + fileId).html(response.uploadedFileContent);
			  		$("#divUploadedFileRemarks" + fileId).html(response.uploadedFileRemarks);
			  		if(cFunction !== "") {
		  				cFunction;
					}
				}
				else {
					Swal.fire({
						title: "ERROR",
						text: response.<%=ActionResponse.MESSAGE_STR%>,
						icon: "error"
					});
				}
			}
		});	
	}
</script>