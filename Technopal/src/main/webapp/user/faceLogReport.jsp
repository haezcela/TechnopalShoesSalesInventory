<%@ page import="java.util.*"%>
<%@ page import="com.mytechnopal.*"%>
<%@ page import="com.mytechnopal.base.WebControlBase"%>
<%@ page import="com.mytechnopal.base.DTOBase"%>
<%@ page import="com.mytechnopal.dto.*"%>
<%@ page import="com.mytechnopal.*"%>
<%@ page import="com.mytechnopal.util.*"%>
<%@ page import="com.mytechnopal.dao.*"%>
<%@ page import="com.mytechnopal.webcontrol.*" %>
<%@ page import="com.mytechnopal.link.*"%>

<%
SessionInfo sessionInfo = (SessionInfo) session.getAttribute(SessionInfo.SESSION_INFO);
%>
<div class="container" id='divFaceLogReport'></div>

<script>
	setTimeout(function (){
		viewFaceLogList();
	}, 100); 

	function viewFaceLogList() {
		$.ajax({		
			url: 'AjaxController?txtSelectedLink=<%=sessionInfo.getCurrentLink().getCode()%>', 
			data: {			
				txtAction: 'VIEW FACE LOG LIST'	
			},		
			method: 'POST',		
			dataType: 'JSON',		
			success: function(response) {	
				$("#divFaceLogReport").html(response.<%=LinkDTO.PAGE_CONTENT%>);
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
				$("#divFaceLogReport").html(response.<%=LinkDTO.PAGE_CONTENT%>);
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
				$("#divFaceLogReport").html(response.<%=LinkDTO.PAGE_CONTENT%>);
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
				$("#divFaceLogReport").html(response.<%=LinkDTO.PAGE_CONTENT%>);
			}	
		});
	}
</script>