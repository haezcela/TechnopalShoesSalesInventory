<%@page import="com.mytechnopal.webcontrol.TextBoxWebControl"%>
<%@page import="java.io.File"%>
<%@page import="java.util.*" %>
<%@page import="com.mytechnopal.*"%>
<%@page import="com.mytechnopal.base.*"%>
<%@page import="com.mytechnopal.dto.*"%>
<%@page import="com.mytechnopal.util.*"%>
<%@page import="com.laponhcet.news.*"%>

<%
SessionInfo sessionInfo = (SessionInfo) session.getAttribute(SessionInfo.SESSION_INFO);
NewsDTO news = (NewsDTO)session.getAttribute(NewsDTO.SESSION_NEWS);
String pict = "";
File fileHeader;
File fileBody;
String eventDate = "";
String eventPlace = "";
if(news.getType().equalsIgnoreCase(NewsDTO.TYPE_NEWS)) {
	pict = "/static/VisitNegOcc/news/" + news.getHeadlinePict();
	fileHeader = new File(sessionInfo.getSettings().getStaticDir(true) + "/VisitNegOcc/news/" + news.getHeadline());
	fileBody = new File(sessionInfo.getSettings().getStaticDir(true) + "/VisitNegOcc/news/" + news.getBody());
}
else {
	pict = "/static/VisitNegOcc/events/" + news.getHeadlinePict();
	fileHeader = new File(sessionInfo.getSettings().getStaticDir(true) + "/VisitNegOcc/events/" + news.getHeadline());
	fileBody = new File(sessionInfo.getSettings().getStaticDir(true) + "/VisitNegOcc/events/" + news.getBody());
	eventDate = "<i class='fas fa-calendar-alt' aria-hidden='true'></i> " + DateTimeUtil.getDateTimeToStr(news.getEventStart(), "MM-dd-yyyy") + " - " + DateTimeUtil.getDateTimeToStr(news.getEventEnd(), "MM-dd-yyyy");
	eventPlace = "<i class='fas fa-map-marker-alt' aria-hidden='true'></i> " +  news.getEventPlace();
}
%>

<section class="section-top">
	<div class="py-8 py-md-9 py-lg-10">
    	<div class="container">
      		<div class="row">
        		<div class="col-lg-8 col-xl-9 order-1 order-md-0">
          			<div class="card card-lg card-transparent mb-8">
		            	<div class="position-relative">
		              		<img class="card-img-top rounded-lg lazyestload" data-src="<%=pict%>" src="<%=pict%>" alt="image">
		            	</div>
            			<div class="card-body px-2 py-6">
              				<h3 class="mb-4"><%=FileUtil.readText(fileHeader, true) %></h3>
              				<div class="meta-post-sm mb-4">
	                			<ul class="list-unstyled d-flex flex-wrap mb-0">
	                  				<li class="meta-tag me-4 mb-1">
	                    				<%=eventDate %>
	                 				</li>
	                  
	                  				<li class="meta-tag me-4 mb-1">
	                    				<%=eventPlace %>
	                  				</li>
	                			</ul>
              				</div>
             				<%=FileUtil.readText(fileBody, true)%>
            			</div>
  
			            <div class="card-footer d-flex align-items-center bg-smoke rounded p-3 p-md-4">
			              <span class="font-weight-bold font-size-15">Share it</span>
			  
			              <ul class="list-unstyled d-flex ms-auto mb-0">
			                <li class="me-3">
			                  <a href="https://www.facebook.com/sharer/sharer.php?u=https://visitnegocc.com/VisitaApp/News?code=<%=news.getCode()%>" target="_blank" class="text-gray-color hover-text-primary">
			                    <i class="fab fa-facebook-f icon-medium" aria-hidden="true"></i>
			                  </a>
			                </li>
			  
			                <li class="me-3">
			                  <a href="https://twitter.com/intent/tweet?url=https://visitnegocc.com/VisitaApp/News?code=<%=news.getCode()%>" target="_blank" class="text-gray-color hover-text-primary">
			                    <i class="fab fa-twitter icon-medium" aria-hidden="true"></i>
			                  </a>
			                </li>
			  
			                <li class="me-3">
			                  <a href="https://www.linkedin.com/sharing/share-offsite/?url=https://visitnegocc.com/VisitaApp/News?code=<%=news.getCode()%>" target="_blank" class="text-gray-color hover-text-primary">
			                    <i class="fab fa-linkedin-in icon-medium" aria-hidden="true"></i>
			                  </a>
			                </li>
			  
			                <li class="me-3">
			                  <a href="https://www.pinterest.com/pin/create/button/?url=https://visitnegocc.com/VisitaApp/News?code=<%=news.getCode()%>" target="_blank" class="text-gray-color hover-text-primary">
			                    <i class="fab fa-pinterest-p icon-medium" aria-hidden="true"></i>
			                  </a>
			                </li>
			              </ul>
			            </div>
          			</div>
        		</div>
  
		        <div class="col-lg-4 col-xl-3">
		            <div class="row">
		            	<div class="col-md-6 col-lg-12 bg-smoke">
		                	<h3 class="py-2 mb-4 text-center">Other News and Events</h3>
		  					<%
		  					List<DTOBase> newsList = (ArrayList<DTOBase>)session.getAttribute(NewsDTO.SESSION_NEWS_LIST);
					        for(DTOBase newsObj: newsList) {
					        	NewsDTO news2 = (NewsDTO)newsObj;
					        	if(!news.getCode().equalsIgnoreCase(news2.getCode())) {
						        	String pict2 = "";
						        	File file2;
						        	if(news2.getType().equalsIgnoreCase(NewsDTO.TYPE_NEWS)) {
						        		pict2 = "/static/VisitNegOcc/news/" + news2.getHeadlinePict();
						        		file2 = new File(sessionInfo.getSettings().getStaticDir(true) + "/VisitNegOcc/news/" + news2.getHeadline());
						        	}
						        	else {
						        		pict2 = "/static/VisitNegOcc/events/" + news2.getHeadlinePict();
						        		file2 = new File(sessionInfo.getSettings().getStaticDir(true) + "/VisitNegOcc/events/" + news2.getHeadline());
						        	}
					        %>
		  					<div class="media meta-post-sm border-bottom border-off-white pb-3 mb-3">
		                    	<div class="img-overlay rounded me-2">
		                           	<img class="lazyestload" height="70px" width="100px" data-src="<%=pict2%>" src="<%=pict2%>" alt="Generic placeholder image">
		                          	<a href="#" onclick="viewNews('<%=news2.getCode()%>')" class="hover-img-overlay-dark"></a>
		                      	</div>
		  
		                       	<div class="media-body">
		                        	<a href="#" onclick="viewNews('<%=news2.getCode()%>')" class="text-dark hover-text-primary text-capitalize mb-1">
		                            	<%=FileUtil.readText(file2, true) %>
		                            </a>
		                     	</div>
		                    </div>	
		              	<%
					        	}
					        }
		              	%>  	  	
		                   	<div class="bg-smoke border border-light-gray rounded p-3 mb-4 text-center">
		          				<a class='btn btn-sm btn-outline-secondary text-uppercase' href='#' onclick="openLink('006')">All News and Events</a>
		            		</div>
		          		</div>
		        	</div>
      			</div>
  			</div>
    	</div>
	</div>   	
</section>

<script>
	function viewNews(newsCode) {
		$("#txtSelectedRecord").val(newsCode);
		openLink("007");
	}
</script>