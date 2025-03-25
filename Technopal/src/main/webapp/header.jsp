<%@page import="com.mytechnopal.*"%>
<%@page import="com.mytechnopal.dto.*"%>
<%@ page import="com.mytechnopal.link.*"%>

<%
SessionInfo sessionInfo = (SessionInfo) session.getAttribute(SessionInfo.SESSION_INFO);
%>

<header class="header" id="pageTop">    
   	<nav class="nav-menuzord">
     	<div class="container clearfix">
       		<div id="menuzord" class="menuzord">
        		<img src="<%=sessionInfo.getSettings().getLogo()%>" width="100px" height="100px" >
        		<b>&nbsp;&nbsp;<%=sessionInfo.getSettings().getName()%></b>
        		<ul class="menuzord-menu menuzord-right">
        		<%
        		if(sessionInfo.getCurrentLink().getCode().equalsIgnoreCase(LinkDTO.GUEST_HOME)) {
        		%>
        			<li class="active">
            			<a href="#">Home</a>
          			</li>
        		<%	
        		}
        		else {
        		%>
        			<li>
            			<a href="#" onclick="openLink('GH')">Home</a>
          			</li>
        		<%	
        		}
				if(sessionInfo.getCurrentUser().getCode().equalsIgnoreCase(UserDTO.USER_CODE_GUEST)) {
				%>
					<li>
						<a href="#" data-bs-toggle="modal" data-bs-target="#divLogin">Login</a>
					</li>		

              	<%
				}
				else {
				%>
					<li>
						<a href="#" onclick="openLink('<%=LinkDTO.USER_HOME%>')">Dashboard</a>
					</li>
					<li>	
						<a href="#" onclick="openLink('<%=LinkDTO.USER_HOME_LOGOUT%>')">Logout</a>
					</li>	
				<%	
				}
              	%>

        		</ul>
       		</div>
     	</div>
   	</nav>
</header>