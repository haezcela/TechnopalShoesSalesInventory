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
String dataInput = "txtLastName: $('#txtLastName').val(), txtFirstName: $('#txtFirstName').val(), txtMiddleName: $('#txtMiddleName').val(), cboSuffixName: $('#cboSuffixName').val(), txtLearnerReferenceNumber: $('#txtLearnerReferenceNumber').val(), cboAcademicSection: $('#cboAcademicSection').val(), txtContactName: $('#txtContactName').val(), txtContact: $('#txtContact').val(), cboContactRelationship: $('#cboContactRelationship').val()"; 

List<DTOBase> academicSectionList = (ArrayList<DTOBase>)session.getAttribute(AcademicSectionDTO.SESSION_ACADEMIC_SECTION_LIST);
List<DTOBase> enrollmentList = (ArrayList<DTOBase>)session.getAttribute(EnrollmentDTO.SESSION_ENROLLMENT_LIST);
List<DTOBase> userList = (List<DTOBase>) session.getAttribute(UserDTO.SESSION_USER_LIST);
List<DTOBase> studentList = (List<DTOBase>) session.getAttribute(StudentDTO.SESSION_STUDENT_LIST);


for(DTOBase enrollmentObj: enrollmentList) {
	EnrollmentDTO enrollment = (EnrollmentDTO)enrollmentObj;
	enrollment.setStudent((StudentDTO) DTOUtil.getObjByCode(studentList, enrollment.getStudent().getCode()));
	StudentUtil.setStudent(enrollment.getStudent(), (UserDTO) DTOUtil.getObjByCode(userList, enrollment.getStudent().getCode()));
	enrollment.setAcademicSection((AcademicSectionDTO) DTOUtil.getObjByCode(academicSectionList, enrollment.getAcademicSection().getCode()));
}
%>


<div id="divList">
<%
for(DTOBase academicSectionObj: academicSectionList) {
	AcademicSectionDTO academicSection = (AcademicSectionDTO)academicSectionObj;
	List<DTOBase> enrollmentListByAcademicSection = EnrollmentUtil.getEnrollmentListByAcademicSectionCode(enrollmentList, academicSection.getCode());
	if(enrollmentListByAcademicSection.size()>=1) {
%>
	<div class="row">
		<div class="col-sm-12">
			<p class="text-center"><b><%=academicSection.getDisplayStr()%></b></p>
			<%
			new SortLastNameAscending().sort(enrollmentListByAcademicSection);
			int i=0;
			for(DTOBase enrollmentObj: enrollmentListByAcademicSection) {
				EnrollmentDTO enrollment = (EnrollmentDTO)enrollmentObj;
				if(i==0) {
				%>
			<div class="row">
				<%	
				}
				else if(i%4==0) {
				%>
			</div>
				<%
					if(i<enrollmentListByAcademicSection.size()) {
				%>
			<div class="row">
				<%		
					}
				}
			%>
				<div class="col-sm-3 text-center">
					<img src='<%=QRCodeUtil.generateQRCodeBase64(enrollment.getStudent().getCode(), 130, 130)%>'><br>
					<small><%=i+1%>. <%=enrollment.getStudent().getName(false, false, true) %></small>
				</div>
			<%	
				i++;
			}
			%>
			</div>
		</div>
	</div>
	<div style="page-break-after:always"><br></div>	
<%
	}	
}
%>
</div>

<button type="button" class="btn btn-primary" onclick="printPreview('divList', 'SBA')">Print</button>


<!-- <div class='no-padding' id='SESSION_ENROLLMENT_DATA_TABLE'></div> -->

<script>
// 	setTimeout(function (){
<%-- 		list<%=sessionInfo.getCurrentLink().getCode()%>(); --%>
// 	}, 1000); 

	<%=WebUtil.getJSList(sessionInfo, dataTable)%>
	<%=WebUtil.getJSAddView(sessionInfo, dataTable, dataTable.getId())%>
	<%=WebUtil.getJSUpdate(sessionInfo, dataTable, dataInput)%>
	<%=WebUtil.getJSDelete(sessionInfo, dataTable)%>
	
	
	function save011(isSaveAndClose) {	
		$.ajax({		
			url: 'AjaxController?txtSelectedLink=011',		
			data: {			
				txtAction: 'ACTION_ADD_SAVE',			
				txtDataTableId: 'SESSION_ENROLLMENT_DATA_TABLE',
				txtLastName: $('#txtLastName').val(), 
				txtFirstName: $('#txtFirstName').val(), 
				txtMiddleName: $('#txtMiddleName').val(), 
				cboSuffixName: $('#cboSuffixName').val(),
				txtLearnerReferenceNumber: $('#txtLearnerReferenceNumber').val(),
				cboAcademicSection: $('#cboAcademicSection').val(),
				txtContactName: $('#txtContactName').val(),
				txtContact: $('#txtContact').val(),
				cboContactRelationship: $('#cboContactRelationship').val()
			},		
			method: 'POST',		
			dataType: 'JSON',		
			success: function(response) {			
				if(response.MESSAGE_TYPE === 'success') {				
					if(isSaveAndClose) {					
						list011();				
					}				
				else {					
					addView011();				
				}			
			}			
			Swal.fire({
				title: response.<%=ActionResponse.MESSAGE_TITLE%>,
				text: response.<%=ActionResponse.MESSAGE_STR%>,
				icon: response.<%=ActionResponse.MESSAGE_TYPE%>
			});
			}	
		});
	}

	
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


