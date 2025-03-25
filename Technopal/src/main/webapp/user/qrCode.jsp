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

<%@ page import="com.laponhcet.student.*" %>
<%
SessionInfo sessionInfo = (SessionInfo) session.getAttribute(SessionInfo.SESSION_INFO);
DataTable dataTable = (DataTable)session.getAttribute(UserDTO.SESSION_USER_DATA_TABLE);
%>

<div class="container" id='<%=dataTable.getId()%>'></div>

<script>
	setTimeout(function (){
		list<%=sessionInfo.getCurrentLink().getCode()%>();
	}, 100); 

	<%=WebUtil.getJSList(sessionInfo, dataTable)%>
	
	function viewQRCode(userCode) {	
		$.ajax({		
			url: 'AjaxController?txtSelectedLink=013', 
			data: {			
				txtAction: 'ADD QR CODE',	
				txtUserCode: userCode
			},		
			method: 'POST',		
			dataType: 'JSON',		
			success: function(response) {		
				if(response.MESSAGE_TYPE === 'success') {				
					list<%=sessionInfo.getCurrentLink().getCode()%>();		
				}		
				else {
					Swal.fire({title: response.MESSAGE_TITLE, text: response.MESSAGE_STR, icon: response.MESSAGE_TYPE});
				}
			}	
		});
	}
	
	function clearItems() {	
		$.ajax({		
			url: 'AjaxController?txtSelectedLink=013', 
			data: {			
				txtAction: 'CLEAR QR CODE'
			},		
			method: 'POST',		
			dataType: 'JSON',		
			success: function(response) {		
				if(response.MESSAGE_TYPE === 'success') {				
					list<%=sessionInfo.getCurrentLink().getCode()%>();		
				}			
			}	
		});
	}
</script>	