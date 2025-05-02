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
	
	function uploadFileReset(action, fileId, label, fileType) {
        console.log("Debug: uploadFileReset called");
        console.log("Action:", action);
        console.log("FileId:", fileId);
        console.log("Label:", label);
        console.log("FileType:", fileType);

        $.ajax({
            url: 'UploadFileAjaxController?txtAction=' + action + '&txtFileId=' + fileId + '&txtLabel=' + label + '&txtFileType=' + fileType, 
            type: 'POST',
            dataType: 'JSON',
            success: function(response) {  
                console.log("Success Response:", response);
                $("#divUploadedFilePict" + fileId).html(response.uploadedFileContent);
                $("#divUploadedFileRemarks" + fileId).html(response.uploadedFileRemarks);
            },
            error: function(xhr, status, error) {
                console.error("AJAX Error:", error);
                console.error("Status:", status);
                console.error("Response Text:", xhr.responseText);
            }
        });    
    }
	
	function refreshSalesTable() {
        $.ajax({
            url: 'SalesActionAjax',
            type: 'POST',
            data: { ajax: '1', action: 'DataTable' },
            success: function(response) {
                let parsed = JSON.parse(response);
                $('#<%=dataTable.getId()%>').html(parsed.pageContent); // reload table content
            },
            error: function(xhr) {
                console.error('Failed to refresh table', xhr);
            }
        });
    }

    function uploadFile(action, fileId, cFunction, label, fileType) {
        console.log("Debug: uploadFile called");
        console.log(" Action:", action);
        console.log(" FileId:", fileId);
        console.log(" Label:", label);
        console.log(" FileType:", fileType);
        console.log(" Function Callback:", cFunction);

        var formData = new FormData();
        var fileInput = $("#file" + fileId)[0];

        if (fileInput.files.length === 0) {
            console.warn(" No file selected!");
            return;
        }

        formData.append("file", fileInput.files[0]);
        formData.append("txtAction", action);
        formData.append("txtFileId", fileId);
        formData.append("txtLabel", label);
        formData.append("txtFileType", fileType);

        $.ajax({
            url: 'UploadFileAjaxController',
            type: 'POST',
            data: formData,
            contentType: false,
            processData: false,
            dataType: 'JSON',
            success: function(response) {
                console.log("Success Response:", response);
                $("#divUploadedFilePict" + fileId).html(response.uploadedFileContent);
                $("#divUploadedFileRemarks" + fileId).html(response.uploadedFileRemarks);
                if (cFunction) {
                    cFunction();
                }
            },
            error: function(xhr, status, error) {
                console.error("AJAX Error:", error);
                console.error("Status:", status);
                console.error("Response Text:", xhr.responseText);
            }
        });
    }
</script>	