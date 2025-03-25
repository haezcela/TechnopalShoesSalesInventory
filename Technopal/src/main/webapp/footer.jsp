<%@page import="com.mytechnopal.*"%>
<%@page import="com.mytechnopal.dto.*"%>
<%@ page import="com.mytechnopal.link.*"%>

<%
SessionInfo sessionInfo = (SessionInfo) session.getAttribute(SessionInfo.SESSION_INFO);
%>

<div class="sticky-bottom bg-black py-3">
   	<div class="row">
       	<div class="col-lg-12 text-center">
    		<a href="#" onclick="openLink('GH')">Home</a>
    		<%
				if(sessionInfo.getCurrentUser().getCode().equalsIgnoreCase(UserDTO.USER_CODE_GUEST)) {
			%>
				&nbsp;&nbsp;&nbsp;-&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)" data-bs-toggle="modal" data-bs-target="#divLogin">Login</a>
          	<%
			}
			else {
			%>
				&nbsp;&nbsp;&nbsp;-&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)" onclick="openLink('<%=LinkDTO.USER_HOME%>')">Dashboard</a>
				&nbsp;&nbsp;&nbsp;-&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)" onclick="openLink('<%=LinkDTO.USER_HOME_LOGOUT%>')">Logout</a>
			<%	
			}
          	%>
   		</div>
   		<div class="col-lg-12 text-center text-white">
   			<br>All Rights Reserved by TechnoPal Software<br>
   		</div>	
 	</div>
</div>   