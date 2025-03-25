<%@page import="java.io.File"%>
<%@page import="java.util.*" %>
<%@page import="com.mytechnopal.*"%>
<%@page import="com.mytechnopal.dto.*"%>
<%@ page import="com.mytechnopal.link.*"%>

<%
SessionInfo sessionInfo = (SessionInfo) session.getAttribute(SessionInfo.SESSION_INFO);
%>

<footer class="copyright py-2 bg-black">
   	<div class="container">
     	<div class="row align-items-center">
       		<div class="col-md-1">
         		<p class="mb-0 mb-md-0 text-md-start">
         			<img class="img-fluid" height="100px" width="100px" src="/static/VisitNegOcc/images/negros_occidental_logo.jpg">
         		</p>
       		</div>
       		<div class="col-md-11">
      			<p class="mb-0 mb-md-0 text-md-start">Copyright<span id="copy-year"></span> All Rights Reserved by NOTD</p>
         		<ul class="list-inline text-capitalize d-flex mb-md-0" style="overflow-wrap: break-word;">
           			<li class="me-3">
             			<a href="#" onclick="openLink('GH')">Home</a>
           			</li>
					<li class="me-3">
             			<a href="#" onclick="openLink('006')">News and Events</a>
           			</li>
           			<li class="me-3">
             			<a href="#" onclick="openLink('001')">About us</a>
           			</li>
					<li class="me-3">
             			<a href="#" onclick="openLink('002')">LGU Tourist Destinations</a>
           			</li>
           			<li class="me-3">
               			<a href="#" onclick="openLink('005')">FAQ</a>
            		</li>
           			<li class="me-3">
             			<a href="#" onclick="openLink('003')">Contact Us</a>
           			</li>
					<%
					if(sessionInfo.getCurrentUser().getCode().equalsIgnoreCase(UserDTO.USER_CODE_GUEST)) {
					%>
					<li class="me-3">
                 		<a href="javascript:void(0)" data-bs-toggle="modal" data-bs-target="#divLogin">Login</a>
                 	</li>	
               		<%
					}
					else {
					%>
					<li class="me-3">
						<a href="javascript:void(0)" onclick="openLink('<%=LinkDTO.USER_HOME%>')">Dashboard</a>
					</li>
					<li class="me-3">
						<a href="javascript:void(0)" onclick="openLink('<%=LinkDTO.USER_HOME_LOGOUT%>')">Logout</a>
					</li>
					<%	
					}
               		%>
         		</ul>
       		</div>
     	</div>
	</div>
</footer>