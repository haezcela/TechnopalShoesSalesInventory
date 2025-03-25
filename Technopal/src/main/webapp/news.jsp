<%@ page import="java.io.File"%>
<%@ page import="com.mytechnopal.util.WebUtil"%>
<%@ page import="com.mytechnopal.*"%>
<%@page import="com.mytechnopal.base.*"%>
<%@ page import="com.mytechnopal.dto.*"%>
<%@ page import="com.mytechnopal.util.*"%>
<%@ page import="com.laponhcet.news.*"%>

<%
SessionInfo sessionInfo = (SessionInfo) session.getAttribute(SessionInfo.SESSION_INFO);
NewsDTO news = (NewsDTO) session.getAttribute(NewsDTO.SESSION_NEWS);
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

String newsTitle = FileUtil.readText(fileHeader, true).toString();
%>	
<!DOCTYPE html>
<html lang="en">
	<head>
	   <!-- SITE TITTLE -->
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<title>Negros Occidental Tourism Division | <%=newsTitle%></title>
	   <!-- Plugins css Style -->
   	<link href="/static/star-2-3/Static HTML//assets/plugins/fontawesome-5.15.2/css/all.min.css" rel="stylesheet">
   	<link href="/static/star-2-3/Static HTML//assets/plugins/fontawesome-5.15.2/css/fontawesome.min.css" rel="stylesheet">
   	<link href="/static/star-2-3/Static HTML//assets/plugins/animate/animate.css" rel="stylesheet">
   	<link href="/static/star-2-3/Static HTML//assets/plugins/menuzord/css/menuzord.css" rel="stylesheet">
   	<link href="/static/star-2-3/Static HTML//assets/plugins/menuzord/css/menuzord-animations.css" rel="stylesheet">
   	<link href="/static/star-2-3/Static HTML//assets/plugins/isotope/isotope.min.css" rel="stylesheet">
   	<link href="/static/star-2-3/Static HTML//assets/plugins/fancybox/jquery.fancybox.min.css" rel="stylesheet">
   	<link href="/static/star-2-3/Static HTML//assets/plugins/selectric/selectric.css" rel="stylesheet">
   	<link href="/static/star-2-3/Static HTML//assets/plugins/daterangepicker/css/daterangepicker.css" rel="stylesheet">
    <link href='/static/star-2-3/Static HTML//assets/plugins/slick/slick.css' rel='stylesheet'>
    <link href='/static/star-2-3/Static HTML//assets/plugins/slick/slick-theme.css' rel='stylesheet'>
   	<link href="/static/star-2-3/Static HTML//assets/plugins/dzsparallaxer/dzsparallaxer.css" rel="stylesheet">
   	<link href="/static/star-2-3/Static HTML//assets/plugins/revolution/css/settings.css" rel="stylesheet">
   	
   	<link href="/static/star-2-3/Static HTML//assets/plugins/fancybox/jquery.fancybox.min.css" rel="stylesheet">
    <link href="/static/star-2-3/Static HTML//assets/plugins/owl-carousel/owl.carousel.min.css" rel="stylesheet" media="screen">
    <link href="/static/star-2-3/Static HTML//assets/plugins/owl-carousel/owl.theme.default.min.css" rel="stylesheet" media="screen">
   	
   	<!-- GOOGLE FONT -->
   	<link href="https://fonts.googleapis.com/css?family=Montserrat:400,500,600,700" rel="stylesheet">
   	<!-- CUSTOM CSS -->
   	<link href="/static/star-2-3/Static HTML/assets/css/star.css" id="option_style" rel="stylesheet">
   	<!-- FAVICON -->
   	<link rel="shortcut icon" type="image/png" href="/static/star-2-3/Static HTML//assets/img/favicon.png"/>

   <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
   <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
   <!--[if lt IE 9]>
   <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
   <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
   <![endif]-->
	
	<link rel="stylesheet" href="/static/star-2-3/Static HTML//assets/css/star-color1.css" id="option_style">

	<link rel="stylesheet" href="https://unpkg.com/leaflet/dist/leaflet.css" />
	
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
	<!-- Maphighlight Script -->	
</head>     

<body id="body" class="up-scroll">  
  	<!-- Preloader -->
	<div id="preloader" class="smooth-loader-wrapper">
    	<div class="smooth-loader">
      		<div class="sk-circle">
		        <div class="sk-circle1 sk-child"></div>
		        <div class="sk-circle2 sk-child"></div>
		        <div class="sk-circle3 sk-child"></div>
		        <div class="sk-circle4 sk-child"></div>
		        <div class="sk-circle5 sk-child"></div>
		        <div class="sk-circle6 sk-child"></div>
		        <div class="sk-circle7 sk-child"></div>
		        <div class="sk-circle8 sk-child"></div>
		        <div class="sk-circle9 sk-child"></div>
		        <div class="sk-circle10 sk-child"></div>
		        <div class="sk-circle11 sk-child"></div>
		        <div class="sk-circle12 sk-child"></div>
      		</div>
    	</div>
  	</div>
	
	<header class="header" id="pageTop">    
	   	<nav class="nav-menuzord">
	     	<div class="container clearfix">
	       		<div id="menuzord" class="menuzord">
	       			<a href="https://visitnegocc.com" target="_blank">
	        			<img src="/static/VisitNegOcc/images/Sweet Surprises Logo.png" width="280px" height="80px" >
					</a>
				</div>
			</div>
		</nav>
	</header>					
	<section class="section-top">
		<div class="py-8 py-md-9 py-lg-10">
	    	<div class="container">
	      		<div class="row">
	        		<div class="col-lg-12 col-xl-9 order-1 order-md-0">
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
	          			</div>
	        		</div>
	  			</div>
	    	</div>
		</div>   	
	</section>
    <!-- Javascript -->
    <script src="/static/star-2-3/Static HTML//assets/plugins/jquery/jquery-3.4.1.min.js"></script>
    <script src="/static/star-2-3/Static HTML//assets/plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
    <script src="/static/star-2-3/Static HTML//assets/plugins/menuzord/js/menuzord.js"></script>
    <script src="/static/star-2-3/Static HTML//assets/plugins/isotope/isotope.min.js"></script>
    <script src="/static/star-2-3/Static HTML//assets/plugins/images-loaded/js/imagesloaded.pkgd.min.js"></script>
    <script src="/static/star-2-3/Static HTML//assets/plugins/fancybox/jquery.fancybox.min.js"></script>
    <script src="/static/star-2-3/Static HTML//assets/plugins/selectric/jquery.selectric.min.js"></script>
    <script src="/static/star-2-3/Static HTML//assets/plugins/daterangepicker/js/moment.min.js"></script>
    <script src="/static/star-2-3/Static HTML//assets/plugins/daterangepicker/js/daterangepicker.min.js"></script>
    <script src="/static/star-2-3/Static HTML//assets/plugins/lazyestload/lazyestload.js"></script>
    <script src="/static/star-2-3/Static HTML//assets/plugins/dzsparallaxer/dzsparallaxer.js"></script>
    <script src="/static/star-2-3/Static HTML//assets/plugins/slick/slick.min.js"></script>
    
    <script src="/static/star-2-3/Static HTML//assets/plugins/revolution/js/jquery.themepunch.tools.min.js"></script>
    <script src="/static/star-2-3/Static HTML//assets/plugins/revolution/js/jquery.themepunch.revolution.min.js"></script>
    <script src="/static/star-2-3/Static HTML//assets/plugins/owl-carousel/owl.carousel.min.js"></script>
    <script src="/static/star-2-3/Static HTML//assets/plugins/smoothscroll/SmoothScroll.js"></script>
    <script src="/static/star-2-3/Static HTML//assets/js/star.js"></script>	
    
    <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDU79W1lu5f6PIiuMqNfT1C6M0e_lq1ECY"></script>
	<script src="https://unpkg.com/leaflet/dist/leaflet.js"></script>
	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/maphilight/1.4.0/jquery.maphilight.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</body>
</html>