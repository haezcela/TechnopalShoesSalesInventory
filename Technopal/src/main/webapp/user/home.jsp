<%@ page import="com.mytechnopal.*"%>
<%@ page import="com.mytechnopal.dto.*"%>
<%@ page import="com.mytechnopal.util.*"%>
<%@ page import="com.mytechnopal.base.*"%>
<%@ page import="com.mytechnopal.link.*"%>
<%@ page import="com.mytechnopal.linkuser.*"%>
<%
SessionInfo sessionInfo = (SessionInfo) session.getAttribute(SessionInfo.SESSION_INFO);
%>
<section class="section-top">
	<nav class="navbar navbar-expand-md navbar-light navbar-dark-light">
    	<div class="container">
      		<button class="navbar-toggler me-0 ms-3" type="button" data-bs-toggle="collapse" data-bs-target="#navbarTogglerDemo01"
        		aria-controls="navbarTogglerDemo01" aria-expanded="false" aria-label="Toggle navigation">
        		<span class="navbar-toggler-icon"></span>
      		</button>
     		<div class="collapse navbar-collapse" id="navbarTogglerDemo01">
        		<ul class="navbar-nav me-auto">
          			<li class="nav-item dropdown">
            			<a class='nav-link' href='#' onclick="openLink('<%=LinkDTO.USER_HOME%>')">
              				<i class="fas fa-tachometer-alt" aria-hidden="true"></i>
              				<span>Dashboard</span>
            			</a>
          			</li>
          			<li class="nav-item dropdown">
            			<a class='nav-link' href='#' onclick="openLink('<%=LinkDTO.USER_PROFILE%>')">
              				<i class="fa fa-user" aria-hidden="true"></i>
             		 		<span>Profile</span>
            			</a>
          			</li>
          		<%
          		if(sessionInfo.getCurrentUser().getCode().equalsIgnoreCase("000000000001")) {
          		%>
          			<li class="nav-item dropdown">
            			<a class='nav-link ' href='#'>
              				<i class="fa fa-cogs" aria-hidden="true"></i>
              				<span>Settings</span>
            			</a>
          			</li>
          		<%	
          		}
          		%>	
        		</ul>
				<p class="text-center mt-3">Hi <span class="text-primary"><%=sessionInfo.getCurrentUser().getName(true, true, true) %></span>, Welcome to <span class="text-primary"><%=sessionInfo.getSettings().getName()%></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</p>
        		<div class="navbar-nav-right d-none d-md-block">
          			<ul class="list-unstyled d-flex align-items-center mb-0">        			
            			<li class="nav-item dropdown nav-item-left me-0">
              				<a href="#" class="dropdown-toggle" data-bs-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
                				<img width="30px" height="30px" class="img-thumbnail" src="/static/common/images/user.png" data-src="/static/common/images/user.png" alt="Generic placeholder image" class="w-100 rounded-circle me-1 lazyestload">
              				</a>
              				<div class="dropdown-menu dropdown-menu-end dropdown-left">
                				<ul class="list-unstyled list-group list-group-flush">
                  					<li>
                    					<a class='list-group-item rounded-0 border-bottom first-child' href='#' onclick="openLink('<%=LinkDTO.USER_PROFILE%>')">
					                    	<h5 class="fon-size-15 text-dark">Profile</h5>
					                    </a>
					              	</li>
					               	<li>
					                	<a class='list-group-item border-bottom border-off-white rounded-0' href='#'>
					                    	<h5 class="fon-size-15 text-dark text-capitalize">Account setting</h5>
					                    </a>
					              	</li>
					                <li class="">
					                	<a href="javascript:void(0)" onclick="openLink('<%=LinkDTO.USER_HOME_LOGOUT%>')" class="list-group-item border-off-white border-bottom-0 rounded-0 last-child">
					                    	<h5 class="fon-size-15 text-dark">Log out</h5>
					                   	</a>
					              	</li>
                				</ul>
              				</div>
            			</li>
          			</ul>
        		</div>
      		</div>
    	</div>
  	</nav>	  
</section>
<section class="bg-smoke py-2">
	<div class="container">
	   	<div class="row">
  		<%
  		if(sessionInfo.getCurrentLink().getCode().equalsIgnoreCase(LinkDTO.USER_HOME)) {
  		%>
  			<jsp:include page="dashboard.jsp"></jsp:include>
  		<%	
  		}
  		else {
  		%>	
  			<jsp:include page="<%=sessionInfo.getCurrentLink().getPage()%>"></jsp:include>
  		<%
  		}
  		%>	
		</div>        
	</div>
</section>