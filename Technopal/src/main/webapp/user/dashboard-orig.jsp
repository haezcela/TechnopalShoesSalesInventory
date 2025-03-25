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
%>
<%
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
   		</div>
   	</div>
</div>
<%
}
List<DTOBase> advisoryListByCurrentUser = (ArrayList<DTOBase>) session.getAttribute(AdvisoryDTO.SESSION_ADVISORY_LIST + "_CURRENT_USER");
if(advisoryListByCurrentUser.size() >= 1) {
%> 
<div class="text-center section-title">
 	<h2 class="text-uppercase font-weight-bold position-relative">
    	<span class="bg-grey">My Advisory Class</span>
   	</h2>
</div>    

<%
	for(DTOBase advisoryObj: advisoryListByCurrentUser) {
		AdvisoryDTO advisory = (AdvisoryDTO)advisoryObj;
%>
<div class="py-2 col-lg-4 text-center">
	<div class="card border">
   		<div class="card-header">
       		<b><%=advisory.getAcademicSection().getDisplayStr()%></b>
  		</div>
 		<div class="card-body">
	  		<div class="row">
	   			<a href="#" onclick="viewAdvisory('<%=advisory.getAcademicSection().getCode()%>')">
		        	<div class="col-lg-12 px-0">
		           		<img width="110px" height="50px" src="/static/common/images/adviser.png">
		           	</div>
		       	</a>
		  	</div>
		</div>
   		<div class="card-footer text-muted">
   		</div>
   	</div>
</div>
<%	
	}
	LinkDTO linkMasterList = (LinkDTO)DTOUtil.getObjByCode(sessionInfo.getLinkList(), "014");
%>		

<div class="py-2 col-lg-3 text-center">
	<div class="card border">
    	<div class="card-header">
        	<b>MasterList</b>
        </div>
       	<div class="card-body">
	       	<div class="row">
	        	<a href="#" onclick="openLink('<%=linkMasterList.getCode()%>')">
	           		<div class="col-lg-12 px-0">
	           			<img width="50px" height="50px" src="/static/common/images/<%=linkMasterList.getIcon()%>">
	           		</div>
	          	</a>
	       	</div>
	    </div>
   		<div class="card-footer text-muted">
   		</div>
   	</div>
</div>


<script>
	function viewAdvisory(academicSectionCode) {
		$("#txtSelectedRecord").val(academicSectionCode);
		openLink("012");
	}
</script>
<%
}
%>

<jsp:include page="faceLog.jsp"></jsp:include>