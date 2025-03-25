<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.mytechnopal.base.WebControlBase"%>
<%@ page import="com.mytechnopal.base.DTOBase"%>
<%@ page import="com.mytechnopal.*"%>
<%@ page import="com.mytechnopal.dto.*"%>
<%@ page import="com.mytechnopal.util.*"%>
<%@ page import="com.mytechnopal.dao.*"%>
<%@ page import="com.mytechnopal.webcontrol.*" %>
<%@ page import="com.laponhcet.person.*" %>

<%@ page import="com.mytechnopal.SessionInfo, com.mytechnopal.DataTable, com.laponhcet.person.PersonDTO" %>

<%
    SessionInfo sessionInfo = (SessionInfo) session.getAttribute(SessionInfo.SESSION_INFO);

    DataTable dataTable = (DataTable) session.getAttribute(PersonDTO.SESSION_PERSON_DATA_TABLE);
	//add upload file
    String dataInput = "txtLastName: $('#txtLastName').val(), txtFirstName: $('#txtFirstName').val(), txtMiddleName: $('#txtMiddleName').val(), cboGender: $('#cboGender').val(), UPLOADED_FILE_0: $('#UPLOADED_FILE_0').val()";
%>

<div class="container" id='<%=dataTable.getId()%>'></div>

<script>
	setTimeout(function (){
		list<%=sessionInfo.getCurrentLink().getCode()%>();
	}, 100); 
	setTimeout(function () {
	    console.log("Table container content:", document.getElementById("table-container").innerHTML);
	}, 500);

	<%=WebUtil.getJSList(sessionInfo, dataTable)%>
	<%=WebUtil.getJSAddView(sessionInfo, dataTable, dataTable.getId())%>
	<%=WebUtil.getJSAddSave(sessionInfo, dataTable, dataInput)%>
	<%=WebUtil.getJSUpdate(sessionInfo, dataTable, dataInput)%>
	<%=WebUtil.getJSDelete(sessionInfo, dataTable)%>
	
	<%-- 	function uploadFileReset(action, fileId, label, fileType) {
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
	} --%>

</script>


