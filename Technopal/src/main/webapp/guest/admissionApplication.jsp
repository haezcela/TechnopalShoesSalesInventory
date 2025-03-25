<%@ page import="java.util.*"%>
<%@ page import="com.mytechnopal.*"%>
<%@ page import="com.mytechnopal.base.WebControlBase"%>
<%@ page import="com.mytechnopal.base.DTOBase"%>
<%@ page import="com.mytechnopal.dto.*"%>
<%@ page import="com.mytechnopal.util.*"%>
<%@ page import="com.mytechnopal.dao.*"%>
<%@ page import="com.mytechnopal.webcontrol.*" %>
<%@ page import="com.laponhcet.admissionapplication.*" %>

<%
SessionInfo sessionInfo = (SessionInfo) session.getAttribute(SessionInfo.SESSION_INFO);
String dataInput = "txtLastName: $('#txtLastName').val(), txtFirstName: $('#txtFirstName').val(), txtMiddleName: $('#txtMiddleName').val(), txtDateOfBirth: $('#txtDateOfBirth').val(), txtPlaceOfBirth: $('#txtPlaceOfBirth').val(), cboGender: $('#cboGender').val(), cboCivilStatus: $('#cboCivilStatus').val(), cboPermanentAddressCity: $('#cboPermanentAddressCity').val(), cboPermanentAddressBarangay: $('#cboPermanentAddressBarangay').val(), txtPermanentAddressDetails: $('#txtPermanentAddressDetails').val(), txtEmailAddress: $('#txtEmailAddress').val(), txtCPNumber: $('#txtCPNumber').val(), txtLastSchoolAttendedName: $('#txtLastSchoolAttendedName').val(), cboSHSStrand: $('#cboSHSStrand').val(),txtLastSchoolAttendedYear: $('#txtLastSchoolAttendedYear').val(), cboLastSchoolAttendedCity: $('#cboLastSchoolAttendedCity').val(), cboApplicantType: $('#cboApplicantType').val(), cboAcademicProgram: $('#cboAcademicProgram').val()"; 
%>
<div class='container' id='<%=sessionInfo.getCurrentLink().getPageId()%>'></div>


<script>
	setTimeout(function (){
		<%=AdmissionApplicationDTO.ACTION_PREPARE_APPLICATION%>();
	}, 100); 
	<%=WebUtil.getJSCustom(sessionInfo, AdmissionApplicationDTO.ACTION_PREPARE_APPLICATION, "", sessionInfo.getCurrentLink().getPageId(), "")%>
	<%=WebUtil.getJSCustom(sessionInfo, AdmissionApplicationDTO.ACTION_SELECT_ADDRESS_CITY, dataInput, sessionInfo.getCurrentLink().getPageId(), ActionResponse.DIALOG_TYPE_SWAL)%>
	<%=WebUtil.getJSCustom(sessionInfo, AdmissionApplicationDTO.ACTION_SUBMIT_APPLICATION, dataInput, sessionInfo.getCurrentLink().getPageId(), ActionResponse.DIALOG_TYPE_SWAL, "$(\"#btnSubmit\").append(\"&nbsp;<span id='spanSubmit' class='spinner-border spinner-border-sm' role='status' aria-hidden='true'></span>\");$(\"#btnSubmit\").prop('disabled', true);", "", "$('#spanSubmit').remove();$(\"#btnSubmit\").prop('disabled', false);")%>
</script>