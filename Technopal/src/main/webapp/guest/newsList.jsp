<%@page import="java.io.File"%>
<%@page import="java.util.*" %>
<%@page import="com.mytechnopal.*"%>
<%@page import="com.mytechnopal.base.*"%>
<%@page import="com.mytechnopal.dto.*"%>
<%@page import="com.mytechnopal.util.*"%>
<%@page import="com.laponhcet.*"%>
<%@page import="com.laponhcet.news.*"%>

<%
SessionInfo sessionInfo = (SessionInfo) session.getAttribute(SessionInfo.SESSION_INFO);
List<DTOBase> newsList = (ArrayList<DTOBase>)session.getAttribute(NewsDTO.SESSION_NEWS_LIST);
%>
<section class="page-title">
	<div class="page-title-img bg-img bg-overlay-darken" style="background-image: url(/static/VisitNegOcc/images/header_photos/news_and_events.jpg);">
    	<div class="container">
      		<div class="row align-items-center justify-content-center" style="height: 250px;">
        		<div class="col-lg-6">
          			<div class="page-title-content">
            			<div class="title-border">
              				<h2 class="text-uppercase text-white font-weight-bold">News and Events</h2>
            			</div>
          			</div>
        		</div>
      		</div>
    	</div>
	</div>
</section>

<section class="bg-smoke py-10">
	<div class="container">
   		<div class="row align-items-center justify-content-center">
        <%
        for(DTOBase newsObj: newsList) {
        	NewsDTO news = (NewsDTO)newsObj;
        	String pict = "";
        	File file;
        	String eventDate = "";
        	String eventPlace = "";
        	if(news.getType().equalsIgnoreCase(NewsDTO.TYPE_NEWS)) {
        		pict = "/static/VisitNegOcc/news/" + news.getHeadlinePict();
        		file = new File(sessionInfo.getSettings().getStaticDir(true) + "/VisitNegOcc/news/" + news.getHeadline());
        	}
        	else {
        		pict = "/static/VisitNegOcc/events/" + news.getHeadlinePict();
        		file = new File(sessionInfo.getSettings().getStaticDir(true) + "/VisitNegOcc/events/" + news.getHeadline());
        		eventDate = "<i class='fas fa-calendar-alt' aria-hidden='true'></i> " + DateTimeUtil.getDateTimeToStr(news.getEventStart(), "MM-dd-yyyy") + " - " + DateTimeUtil.getDateTimeToStr(news.getEventEnd(), "MM-dd-yyyy");
        		eventPlace = "<i class='fas fa-map-marker-alt' aria-hidden='true'></i> " +  news.getEventPlace();
        	}
        %>
        	<div class="col-md-6 col-lg-4 mb-5">
            	<div class="card card-hover">
                	<img class="card-img-top lazyestload" data-src=<%=pict%> src="<%=pict%>" alt="image">
              		<div class="card-body">
                		<h5><%=FileUtil.readText(file, true) %></h5>
                		<span><%=eventDate%><br><%=eventPlace%></span>
                		<div class="text-center">
                    		<a class='btn btn-xs btn-outline-secondary text-uppercase' href='#' onclick="viewNews('<%=news.getCode()%>')">Read More</a>
                		</div>
              		</div>
            	</div>
          </div>
        <%	
        }
        %>  
       </div>
   	</div>
</section>
<script>
	function viewNews(newsCode) {
		$("#txtSelectedRecord").val(newsCode);
		openLink("007");
	}
</script>