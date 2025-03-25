
				
			











<div class="container" id="divPage010">        	<div class="col-lg-2">          		<i class="far fa-calendar-alt" aria-hidden="true"></i>                <input type="text" class="form-control daterange-picker sidebar-daterange-picker" autocomplete="off" name="dateRange" value="" placeholder="MM/DD/YYYY">           	</div></div>


		    		


<script>
	setTimeout(function (){
		ACTION_PREPARE_APPLICATION();
	}, 100); 
	function ACTION_PREPARE_APPLICATION() {	$('#divSKSpinner').show();	$.ajax({		url: 'AjaxController?txtSelectedLink=010',		data: {		txtAction: 'ACTION_PREPARE_APPLICATION'		},		method: 'POST',		dataType: 'JSON',		success: function(response) {			if(response.MESSAGE_TYPE != '') {			}		displayPage('divPage010', response.PAGE_CONTENT);			$('#divSKSpinner').hide();		}	});}
	function ACTION_SELECT_ADDRESS_CITY() {	$('#divSKSpinner').show();	$.ajax({		url: 'AjaxController?txtSelectedLink=010',		data: {		txtAction: 'ACTION_SELECT_ADDRESS_CITY',txtLastName: $('#txtLastName').val(), txtFirstName: $('#txtFirstName').val(), txtMiddleName: $('#txtMiddleName').val(), txtDateOfBith: $('#txtDateOfBith').val(), txtPlaceOfBirth: $('#txtPlaceOfBirth').val(), cboGender: $('#cboGender').val(), cboCivilStatus: $('#cboCivilStatus').val(), cboPermanentAddressCity: $('#cboPermanentAddressCity').val(), cboPermanentAddressBarangay: $('#cboPermanentAddressBarangay').val(), txtPermanentAddressDetails: $('#txtPermanentAddressDetails').val(), txtEmailAddress: $('#txtEmailAddress').val(), txtCPNumber: $('#txtCPNumber').val(), txtLastSchoolAttendedName: $('#txtLastSchoolAttendedName').val(), cboSHSStrand: $('#cboSHSStrand').val(),txtLastSchoolAttendedYear: $('#txtLastSchoolAttendedYear').val(), cboLastSchoolAttendedCity: $('#cboLastSchoolAttendedCity').val(), cboApplicantType: $('#cboApplicantType').val(), cboAcademicProgram: $('#cboAcademicProgram').val()		},		method: 'POST',		dataType: 'JSON',		success: function(response) {			if(response.MESSAGE_TYPE != '') {			Swal.fire({title: response.MESSAGE_TITLE, text: response.MESSAGE_STR, icon: response.MESSAGE_TYPE});			}		displayPage('divPage010', response.PAGE_CONTENT);			$('#divSKSpinner').hide();		}	});}
	function ACTION_SUBMIT_APPLICATION() {	$('#divSKSpinner').show();	$.ajax({		url: 'AjaxController?txtSelectedLink=010',		data: {		txtAction: 'ACTION_SUBMIT_APPLICATION',txtLastName: $('#txtLastName').val(), txtFirstName: $('#txtFirstName').val(), txtMiddleName: $('#txtMiddleName').val(), txtDateOfBith: $('#txtDateOfBith').val(), txtPlaceOfBirth: $('#txtPlaceOfBirth').val(), cboGender: $('#cboGender').val(), cboCivilStatus: $('#cboCivilStatus').val(), cboPermanentAddressCity: $('#cboPermanentAddressCity').val(), cboPermanentAddressBarangay: $('#cboPermanentAddressBarangay').val(), txtPermanentAddressDetails: $('#txtPermanentAddressDetails').val(), txtEmailAddress: $('#txtEmailAddress').val(), txtCPNumber: $('#txtCPNumber').val(), txtLastSchoolAttendedName: $('#txtLastSchoolAttendedName').val(), cboSHSStrand: $('#cboSHSStrand').val(),txtLastSchoolAttendedYear: $('#txtLastSchoolAttendedYear').val(), cboLastSchoolAttendedCity: $('#cboLastSchoolAttendedCity').val(), cboApplicantType: $('#cboApplicantType').val(), cboAcademicProgram: $('#cboAcademicProgram').val()		},		method: 'POST',		dataType: 'JSON',		success: function(response) {			if(response.MESSAGE_TYPE != '') {			Swal.fire({title: response.MESSAGE_TITLE, text: response.MESSAGE_STR, icon: response.MESSAGE_TYPE});			}		displayPage('divPage010', response.PAGE_CONTENT);			$('#divSKSpinner').hide();		}	});}
</script>	


<script>
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
	  		if(response.MESSAGE_TYPE === "success") {
	  			$("#divUploadedFilePict" + fileId).html(response.uploadedFileContent);
		  		$("#divUploadedFileRemarks" + fileId).html(response.uploadedFileRemarks);
		  		if(cFunction !== "") {
	  				cFunction;
				}
			}
			else {
				swal("error", response.MESSAGE_STR, "error");
			}
		}
	});	
}
</script>
		
