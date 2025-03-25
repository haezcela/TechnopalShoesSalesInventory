<%@ page import="java.util.*"%>
<%@ page import="com.mytechnopal.*"%>
<%@ page import="com.mytechnopal.dto.*"%>
<%@ page import="com.mytechnopal.util.*"%>
<%@ page import="com.mytechnopal.base.*"%>
<%@ page import="com.mytechnopal.link.*"%>
<%@ page import="com.mytechnopal.linkuser.*"%>
<%@ page import="com.laponhcet.advisory.*"%>
<%
SessionInfo sessionInfo = (SessionInfo) session.getAttribute(SessionInfo.SESSION_INFO);

for(DTOBase linkUserObj: LinkUserUtil.getLinkUserListUniqueLink(sessionInfo.getCurrentUser().getLinkUserList())) {
	LinkUserDTO linkUser = (LinkUserDTO)linkUserObj;
%>
<div class="py-2 col-lg-3 text-center">
	<div class="card border">
    	<div class="card-header">
        	<b><%=linkUser.getLink().getLabel()%></b>
        </div>
       	<div class="card-body">
	       	<div class="row">
	        	<a href="#" onclick="openLink('<%=linkUser.getLink().getCode()%>')">
	           		<div class="col-lg-12 px-0">
	           			<img width="50px" height="50px" src="/static/common/images/<%=linkUser.getLink().getIcon()%>">
	           		</div>
	          	</a>
	       	</div>
	    </div>
   		<div class="card-footer text-muted">
   			<%=linkUser.getLink().getDescription()%>
   		</div>
   	</div>
</div>

<%
}
%>
