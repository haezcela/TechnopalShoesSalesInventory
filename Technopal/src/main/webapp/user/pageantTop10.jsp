<%@ page import="java.util.*"%>
<%@ page import="com.mytechnopal.*"%>
<%@ page import="com.mytechnopal.link.*"%>
<%@ page import="com.mytechnopal.base.WebControlBase"%>
<%@ page import="com.mytechnopal.base.DTOBase"%>
<%@ page import="com.mytechnopal.dto.*"%>
<%@ page import="com.mytechnopal.util.*"%>
<%@ page import="com.mytechnopal.dao.*"%>
<%@ page import="com.mytechnopal.webcontrol.*" %>
<%@ page import="com.laponhcet.enrollment.*" %>

<%@ page import="com.laponhcet.pageant.*" %>

<%
SessionInfo sessionInfo = (SessionInfo) session.getAttribute(SessionInfo.SESSION_INFO);
EventDTO event = (EventDTO)session.getAttribute(EventDTO.SESSION_EVENT);
List<DTOBase> eventList = (List<DTOBase>)session.getAttribute(EventDTO.SESSION_EVENT_LIST);
%>

<div class="container" id='divEvent'></div>
<script>
	setTimeout(function (){
		initEvent();
	}, 50); 	
	function initEvent() {
		$.ajax({		
			url: 'AjaxController?txtSelectedLink=<%=sessionInfo.getCurrentLink().getCode()%>', 
			data: {			
				txtAction: 'INIT EVENT'	
			},		
			method: 'POST',		
			dataType: 'JSON',		
			success: function(response) {	
				$("#divEvent").html(response.<%=LinkDTO.PAGE_CONTENT%>);
			}	
		});
	}
	
	function selectEvent(eventId) {
		$.ajax({		
			url: 'AjaxController?txtSelectedLink=<%=sessionInfo.getCurrentLink().getCode()%>', 
			data: {			
				txtAction: 'SELECT EVENT',
				cboEventId: eventId
			},		
			method: 'POST',		
			dataType: 'JSON',		
			success: function(response) {	
				$("#divEvent").html(response.<%=LinkDTO.PAGE_CONTENT%>);
			}	
		});
	}
</script>