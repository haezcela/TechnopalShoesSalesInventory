<%@ page import="java.util.*"%>
<%@ page import="com.mytechnopal.*"%>
<%@ page import="com.mytechnopal.base.WebControlBase"%>
<%@ page import="com.mytechnopal.base.DTOBase"%>
<%@ page import="com.mytechnopal.dto.*"%>
<%@ page import="com.mytechnopal.*"%>
<%@ page import="com.mytechnopal.util.*"%>
<%@ page import="com.mytechnopal.dao.*"%>
<%@ page import="com.mytechnopal.webcontrol.*" %>
<%@ page import="com.mytechnopal.link.*" %>
<%@ page import="com.laponhcet.enrollment.*" %>
<%@ page import="com.laponhcet.schedule.*" %>
<%@ page import="com.laponhcet.teacher.*" %>
<%@ page import="com.laponhcet.venue.*" %>

<%
SessionInfo sessionInfo = (SessionInfo) session.getAttribute(SessionInfo.SESSION_INFO);
ScheduleDTO schedule = (ScheduleDTO)session.getAttribute(ScheduleDTO.SESSION_SCHEDULE);
List<DTOBase> teacherList = (ArrayList<DTOBase>)session.getAttribute(TeacherDTO.SESSION_TEACHER_LIST);
List<DTOBase> venueList = (ArrayList<DTOBase>)session.getAttribute(VenueDTO.SESSION_VENUE_LIST);
%>

<style>
	.autocomplete {
	  /*the container must be positioned relative:*/
	  position: relative;
	  display: inline-block;
	}
	.autocomplete-items {
	  position: absolute;
	  border: 1px solid #d4d4d4;
	  border-bottom: none;
	  border-top: none;
	  z-index: 99;
	  /*position the autocomplete items to be the same width as the container:*/
	  top: 100%;
	  left: 0;
	  right: 0;
	}
	.autocomplete-items div {
	  padding: 10px;
	  cursor: pointer;
	  background-color: #fff;
	  border-bottom: 1px solid #d4d4d4;
	}
	.autocomplete-items div:hover {
	  /*when hovering an item:*/
	  background-color: #e9e9e9;
	}
	.autocomplete-active {
	  /*when navigating through the items using the arrow keys:*/
	  background-color: DodgerBlue !important;
	  color: #ffffff;
	}
</style>

<input type="hidden" id="txtDay">
<div class="container" id='divSchedule'></div>
<div class="modal fade" id="divSubjectSchedule" tabindex="-1" aria-hidden="true"></div>
<div class="modal fade" id="divTeacher" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-xl">
    	<div class="modal-content">
      		<div class="modal-header">
        		<h5 class="modal-title" id="exampleModalLabel">Select a Teacher</h5>
        		<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      		</div>
      		<div class="modal-body">
      			<div class="row">
        	<%
        	for(DTOBase teacherObj: teacherList) {
        		TeacherDTO teacher = (TeacherDTO)teacherObj;
        	%>
       			<div class="col-lg-4 btn btn-outline-primary mb-2" role="button" tabindex="0" onclick="selectTeacher('<%=teacher.getCode()%>')">
       				<%=teacher.getDisplayStr()%>
       			</div>
        			
        	<%	
        	}
        	%>
        		</div>
      		</div>
      		<div class="modal-footer">
        		<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
      		</div>
    	</div>
  	</div>
</div>



<script>
	setTimeout(function (){
		getSchedule();
	}, 50); 
	
	function getSchedule() {
		$.ajax({
			url: 'AjaxController?txtSelectedLink=<%=sessionInfo.getCurrentLink().getCode()%>&txtAction=<%=DataTable.ACTION_UPDATE_VIEW%>',
			type: 'POST',
		  	dataType: 'JSON',
		  	success: function(response) { 
		  		$("#divSchedule").html(response.<%=LinkDTO.PAGE_CONTENT%>);
			}
		});	
	}
	
	function selectAcademicSection() {
		$.ajax({
			url: 'AjaxController?txtSelectedLink=<%=sessionInfo.getCurrentLink().getCode()%>',
			data: {			
				txtAction: 'SELECT_ACADEMIC_SECTION',
				cboAcademicSection: $('#cboAcademicSection').val()
			},
			type: 'POST',
		  	dataType: 'JSON',
		  	success: function(response) { 
		  		$("#divSchedule").html(response.<%=LinkDTO.PAGE_CONTENT%>);
			}
		});	
	}
	
	function viewSchedule() {
		$.ajax({
			url: 'AjaxController?txtSelectedLink=<%=sessionInfo.getCurrentLink().getCode()%>',
			data: {			
				txtAction: 'VIEW_SCHEDULE',
				cboAcademicYear: $('#cboAcademicYear').val(),
				cboSemester: $('#cboSemester').val()
			},
			type: 'POST',
		  	dataType: 'JSON',
		  	success: function(response) { 
		  		$("#divSchedule").html(response.<%=LinkDTO.PAGE_CONTENT%>);
			}
		});	
	}
	
	function viewSubjectSchedule(subjectCode) {
		$.ajax({
			url: 'AjaxController?txtSelectedLink=<%=sessionInfo.getCurrentLink().getCode()%>',
			data: {			
				txtAction: 'VIEW_SUBJECT_SCHEDULE',
				txtSubjectCode: subjectCode
			},
			type: 'POST',
		  	dataType: 'JSON',
		  	success: function(response) { 
		  		$("#divSubjectSchedule").html(response.<%=LinkDTO.PAGE_CONTENT%>);
		  		setTimeout(function (){
			  		new bootstrap.Modal(document.getElementById('divSubjectSchedule'), { keyboard: false}).show();
			  		var teacherArr = <%=StringUtil.getJavascriptArray(StringUtil.getObjDisplayList(teacherList)) %>;
			  		var teacherCodeArr = <%=StringUtil.getJavascriptArray(StringUtil.getObjCodeArr(teacherList)) %>;
			  		autoComplete(document.getElementById("txtTeacher"), teacherArr, teacherCodeArr, document.getElementById("txtTeacherCode"));
			  		
			  		var venueArr = <%=StringUtil.getJavascriptArray(StringUtil.getObjDisplayList(venueList)) %>;
			  		var venueCodeArr = <%=StringUtil.getJavascriptArray(StringUtil.getObjCodeArr(venueList)) %>;
			  		autoComplete(document.getElementById("txtVenue"), venueArr, venueCodeArr, document.getElementById("txtVenueCode"));
		  		}, 50); 
			}
		});	
	}
	
	function getDayStr() {
		dayStr = "";
		<%
		for(int i=0; i<ScheduleDetailsDTO.DAY_CODE_LIST.length; i++) {
		%>
			if($('#chkDay<%=i%>').is(":checked")) {
				if(dayStr != "") {
					dayStr += "~";
				}
				dayStr += "<%=ScheduleDetailsDTO.DAY_CODE_LIST[i]%>";
			}
		<%
		}
		%>
		$("#txtDay").val(dayStr);
	}
	
	function addScheduleDetails() {
		$.ajax({
			url: 'AjaxController?txtSelectedLink=<%=sessionInfo.getCurrentLink().getCode()%>',
			data: {			
				txtAction: 'ADD_SCHEDULE_DETAILS',
				txtDay: $('#txtDay').val(),
				cboStartTime: $('#cboStartTime').val(),
				cboEndTime: $('#cboEndTime').val(),
				txtTeacherCode: $('#txtTeacherCode').val(),
				txtVenueCode: $('#txtVenueCode').val()
			},
			type: 'POST',
		  	dataType: 'JSON',
		  	success: function(response) { 
		  		Swal.fire({
					title: response.<%=ActionResponse.MESSAGE_TITLE%>,
					text: response.<%=ActionResponse.MESSAGE_STR%>,
					icon: response.<%=ActionResponse.MESSAGE_TYPE%>
				});
		  		
		  		if(response.MESSAGE_TYPE === "<%=ActionResponse.MESSAGE_SWAL_TYPE_SUCCESS%>") {
		  			bootstrap.Modal.getInstance(document.getElementById('divSubjectSchedule')).hide();
		  			viewSchedule();
		  		}
			}
		});	
	}
	
	
	function viewTeacher(subjectCode) {
		$("#txtSelectedRecord").val(subjectCode);
		new bootstrap.Modal(document.getElementById('divTeacher'), { keyboard: false}).show();
	}
	
	function selectTeacher(teacherCode) {
		$.ajax({
			url: 'AjaxController?txtSelectedLink=<%=sessionInfo.getCurrentLink().getCode()%>',
			data: {			
				txtAction: 'SELECT_TEACHER',
				txtSubjectCode: $("#txtSelectedRecord").val(),
				txtTeacherCode: teacherCode
			},
			type: 'POST',
		  	dataType: 'JSON',
		  	success: function(response) { 
		  		$("#divSchedule").html(response.<%=LinkDTO.PAGE_CONTENT%>);
				bootstrap.Modal.getInstance(document.getElementById('divTeacher')).hide();
			}
		});	
	}
	
	function deleteScheduleDetails(scheduleCode, scheduleDetailsId) {
		$.ajax({
			url: 'AjaxController?txtSelectedLink=<%=sessionInfo.getCurrentLink().getCode()%>',
			data: {			
				txtAction: 'DELETE_SCHEDULE_DETAILS',
				txtScheduleCode: scheduleCode,
				txtScheduleDetailsId: scheduleDetailsId
			},
			type: 'POST',
		  	dataType: 'JSON',
		  	success: function(response) { 
		  		$("#divSchedule").html(response.<%=LinkDTO.PAGE_CONTENT%>);
			}
		});	
	}
</script>