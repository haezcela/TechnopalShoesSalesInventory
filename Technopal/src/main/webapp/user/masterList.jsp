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
<%@ page import="com.mytechnopal.link.*"%>

<%
SessionInfo sessionInfo = (SessionInfo) session.getAttribute(SessionInfo.SESSION_INFO);
DataTable dataTable = (DataTable)session.getAttribute(AcademicSectionDTO.SESSION_ACADEMIC_SECTION_DATA_TABLE);
%>
<div class="container" id='<%=dataTable.getId()%>'></div>

<!-- Modal -->
<div class="modal fade" id="divModalEnrollmentListBySection" tabindex="-1" role="dialog" aria-hidden="true">
	<div class="modal-dialog modal-xl" role="document">
    	<div class="modal-content">
      		<div class="modal-header">
        		<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      		</div>
      		<div id="divModalEnrollmentListBySectionContent" class="modal-body">
        		
      		</div>
      		<div class="modal-footer">
        		<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
        		<button type="button" class="btn btn-primary" onclick="printPreview('divModalEnrollmentListBySectionContent', 'Masterlist')">Print</button>
      		</div>
    	</div>
  	</div>
</div>
           
<script>
	setTimeout(function (){
		list<%=sessionInfo.getCurrentLink().getCode()%>();
	}, 100); 

	<%=WebUtil.getJSList(sessionInfo, dataTable)%>
	
	function viewEnrollmentListByAcademicSectionCode(academicSectionCode) {
		$.ajax({		
			url: 'AjaxController?txtSelectedLink=<%=sessionInfo.getCurrentLink().getCode()%>', 
			data: {			
				txtAction: 'VIEW ENROLLMENT LIST',	
				txtAcademicSectionCode: academicSectionCode
			},		
			method: 'POST',		
			dataType: 'JSON',		
			success: function(response) {	
				$("#divModalEnrollmentListBySection").modal('show');
				$("#divModalEnrollmentListBySectionContent").html(response.<%=LinkDTO.PAGE_CONTENT%>);
			}	
		});
	}
	
	function viewQRCodeListByAcademicSectionCode(academicSectionCode) {
		$.ajax({		
			url: 'AjaxController?txtSelectedLink=<%=sessionInfo.getCurrentLink().getCode()%>', 
			data: {			
				txtAction: 'VIEW QR CODE LIST',	
				txtAcademicSectionCode: academicSectionCode
			},		
			method: 'POST',		
			dataType: 'JSON',		
			success: function(response) {	
				$("#divModalEnrollmentListBySection").modal('show');
				$("#divModalEnrollmentListBySectionContent").html(response.<%=LinkDTO.PAGE_CONTENT%>);
			}	
		});
	}
	
	function viewTelegramRegistrationListByAcademicSectionCode(academicSectionCode) {
		$('#divDialogLoading').show(); 
		$.ajax({		
			url: 'AjaxController?txtSelectedLink=<%=sessionInfo.getCurrentLink().getCode()%>', 
			data: {			
				txtAction: 'VIEW TELEGRAM REGISTRATION LIST',	
				txtAcademicSectionCode: academicSectionCode
			},		
			method: 'POST',		
			dataType: 'JSON',		
			success: function(response) {	
				$('#divDialogLoading').hide();
				$("#divModalEnrollmentListBySection").modal('show');
				$("#divModalEnrollmentListBySectionContent").html(response.<%=LinkDTO.PAGE_CONTENT%>);
			}	
		});
	}
	
	function viewFaceLogListByAcademicSectionCode(academicSectionCode) {
		$.ajax({		
			url: 'AjaxController?txtSelectedLink=<%=sessionInfo.getCurrentLink().getCode()%>', 
			data: {			
				txtAction: 'VIEW FACE LOG LIST',	
				txtAcademicSectionCode: academicSectionCode
			},		
			method: 'POST',		
			dataType: 'JSON',		
			success: function(response) {	
				$("#divModalEnrollmentListBySection").modal('show');
				$("#divModalEnrollmentListBySectionContent").html(response.<%=LinkDTO.PAGE_CONTENT%>);
			}	
		});
	}
	
	function selectCalendarDayOfTheMonth(dayOfTheMonth) {
		$.ajax({		
			url: 'AjaxController?txtSelectedLink=<%=sessionInfo.getCurrentLink().getCode()%>', 
			data: {			
				txtAction: 'SELECT CALENDAR DAY OF THE MONTH',	
				txtDayOfTheMonth: dayOfTheMonth
			},		
			method: 'POST',		
			dataType: 'JSON',		
			success: function(response) {	
				$("#divModalEnrollmentListBySectionContent").html(response.<%=LinkDTO.PAGE_CONTENT%>);
			}	
		});
	}
	
	function selectCalendarMonth(month) {
		$.ajax({		
			url: 'AjaxController?txtSelectedLink=<%=sessionInfo.getCurrentLink().getCode()%>', 
			data: {			
				txtAction: 'SELECT CALENDAR MONTH',	
				txtMonth: month
			},		
			method: 'POST',		
			dataType: 'JSON',		
			success: function(response) {	
				$("#divModalEnrollmentListBySectionContent").html(response.<%=LinkDTO.PAGE_CONTENT%>);
			}	
		});
	}
	
	function selectCalendarYear(year) {
		$.ajax({		
			url: 'AjaxController?txtSelectedLink=<%=sessionInfo.getCurrentLink().getCode()%>', 
			data: {			
				txtAction: 'SELECT CALENDAR YEAR',	
				txtYear: year
			},		
			method: 'POST',		
			dataType: 'JSON',		
			success: function(response) {	
				$("#divModalEnrollmentListBySectionContent").html(response.<%=LinkDTO.PAGE_CONTENT%>);
			}	
		});
	}
</script>