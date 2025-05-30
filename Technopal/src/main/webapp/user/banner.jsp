
<%@ page import="java.util.*"%>
<%@ page import="com.mytechnopal.*"%>
<%@ page import="com.mytechnopal.base.WebControlBase"%>
<%@ page import="com.mytechnopal.base.DTOBase"%>
<%@ page import="com.mytechnopal.dto.*"%>
<%@ page import="com.mytechnopal.util.*"%>
<%@ page import="com.mytechnopal.dao.*"%>
<%@ page import="com.mytechnopal.webcontrol.*"%>
<%@ page import="com.mytechnopal.banner.*"%>

<%
SessionInfo sessionInfo = (SessionInfo) session.getAttribute(SessionInfo.SESSION_INFO);
DataTable dataTable = (DataTable) session.getAttribute(BannerDTO.SESSION_BANNER_DATA_TABLE);
String dataInput = "txtLabel: $('#txtLabel').val(), txtDescription: $('#txtDescription').val()";
%>

<div class="container" id='<%= dataTable.getId() %>'></div>

<form id="frmMain" enctype="multipart/form-data">
    <!-- Your form fields here -->
    <input type="file" name="file" id="file">
    <!-- Other form fields -->
</form>

<script>
    setTimeout(function () {
        list<%= sessionInfo.getCurrentLink().getCode() %>();
    }, 100);

    <%= WebUtil.getJSList(sessionInfo, dataTable) %>
    <%= WebUtil.getJSAddView(sessionInfo, dataTable, dataTable.getId()) %>
    <%= WebUtil.getJSAddSave(sessionInfo, dataTable, dataInput) %>
    <%= WebUtil.getJSUpdate(sessionInfo, dataTable, dataInput) %>
    <%= WebUtil.getJSDelete(sessionInfo, dataTable) %>

    function uploadFileReset(action, fileId, label, fileType) {
        $.ajax({
            url: 'UploadFileController?txtAction=' + action + '&txtFileId=' + fileId + '&txtLabel=' + label + '&txtFileType=' + fileType,
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
            url: 'UploadFileController?txtAction=' + action + '&txtFileId=' + fileId + '&txtLabel=' + label + '&txtFileType=' + fileType,
            type: 'POST',
            data: new FormData($('#frmMain')[0]), // The form with the file inputs.
            processData: false,
            contentType: false, // Using FormData, no need to process data.
            dataType: 'JSON',
            success: function(response) {
                if (response.MESSAGE_TYPE === "SUCCESS") {
                    $("#divUploadedFilePict" + fileId).html(response.uploadedFileContent);
                    $("#divUploadedFileRemarks" + fileId).html(response.uploadedFileRemarks);
                    if (cFunction !== "") { 
                        cFunction;
                    }
                } else {
                    Swal.fire({
                        title: "ERROR",
                        text: response.MESSAGE_STR,
                        icon: "error"
                    });
                }
            }
        });
    }
</script>
