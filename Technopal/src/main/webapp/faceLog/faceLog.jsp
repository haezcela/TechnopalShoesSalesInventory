<%@ page import="com.mytechnopal.SessionInfo" %>
<%
SessionInfo sessionInfo = (SessionInfo) session.getAttribute(SessionInfo.SESSION_INFO);
%>

<html lang="en">
	<head>
	   <!-- SITE TITTLE -->
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<title>FaceKeeper | <%=sessionInfo.getSettings().getName()%></title>
	<!-- Plugins css Style -->
   	<link href="/static/star-2-3/Static HTML/assets/plugins/fontawesome-5.15.2/css/all.min.css" rel="stylesheet">
   	<link href="/static/star-2-3/Static HTML/assets/plugins/fontawesome-5.15.2/css/fontawesome.min.css" rel="stylesheet">
   	<link href="/static/star-2-3/Static HTML/assets/plugins/animate/animate.css" rel="stylesheet">
   	<link href="/static/star-2-3/Static HTML/assets/plugins/menuzord/css/menuzord.css" rel="stylesheet">
   	<link href="/static/star-2-3/Static HTML/assets/plugins/menuzord/css/menuzord-animations.css" rel="stylesheet">
   	<link href="/static/star-2-3/Static HTML/assets/plugins/isotope/isotope.min.css" rel="stylesheet">
   	<link href="/static/star-2-3/Static HTML/assets/plugins/fancybox/jquery.fancybox.min.css" rel="stylesheet">

   	<link href="/static/star-2-3/Static HTML/assets/plugins/daterangepicker/css/daterangepicker.css" rel="stylesheet">
    <link href='/static/star-2-3/Static HTML/assets/plugins/slick/slick.css' rel='stylesheet'>
    <link href='/static/star-2-3/Static HTML/assets/plugins/slick/slick-theme.css' rel='stylesheet'>
   	<link href="/static/star-2-3/Static HTML/assets/plugins/dzsparallaxer/dzsparallaxer.css" rel="stylesheet">
   	<link href="/static/star-2-3/Static HTML/assets/plugins/revolution/css/settings.css" rel="stylesheet">
   	
   	<link href="/static/star-2-3/Static HTML/assets/plugins/fancybox/jquery.fancybox.min.css" rel="stylesheet">
    <link href="/static/star-2-3/Static HTML/assets/plugins/owl-carousel/owl.carousel.min.css" rel="stylesheet" media="screen">
    <link href="/static/star-2-3/Static HTML/assets/plugins/owl-carousel/owl.theme.default.min.css" rel="stylesheet" media="screen">
   	
   	<!-- GOOGLE FONT -->
   	<link href="https://fonts.googleapis.com/css?family=Montserrat:400,500,600,700" rel="stylesheet">
   	<!-- CUSTOM CSS -->
   	<link href="/static/star-2-3/Static HTML/assets/css/star.css" id="option_style" rel="stylesheet">
   	<!-- FAVICON -->
   	<link rel="shortcut icon" type="image/png" href="/static/star-2-3/Static HTML/assets/img/favicon.png"/>

	<link rel="stylesheet" href="/static/star-2-3/Static HTML/assets/css/star-color1.css" id="option_style">
	
	<link href="/static/common/sweet_alert2/sweetalert2.css" rel="stylesheet"> 
	<link href="/static/common/fontawesome-free-6.6.0-web/css/all.min.css" rel="stylesheet"> 
</head>     

<body style="overflow: hidden; background-color: <%=sessionInfo.getSettings().getDefaultPrimaryColor()%>" onload="initFaceLog()">  
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
  	
 	<form id="frmMain" name="frmMain" method="post" action="Web" onSubmit="return false" style="margin: 0; padding: 0; outline: 0">
		<input id="txtSelectedLink" name="txtSelectedLink" type="hidden" value="" />
		<input id="txtSelectedRecord" name="txtSelectedRecord" type="hidden" value="" />
		<jsp:include page="capture.jsp"></jsp:include>
	</form>	
	
    <!-- Javascript -->
    <script src="/static/star-2-3/Static HTML/assets/plugins/jquery/jquery-3.4.1.min.js"></script>
    <script src="/static/star-2-3/Static HTML/assets/plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
    <script src="/static/star-2-3/Static HTML/assets/plugins/menuzord/js/menuzord.js"></script>
    <script src="/static/star-2-3/Static HTML/assets/plugins/isotope/isotope.min.js"></script>
    <script src="/static/star-2-3/Static HTML/assets/plugins/images-loaded/js/imagesloaded.pkgd.min.js"></script>
    <script src="/static/star-2-3/Static HTML/assets/plugins/fancybox/jquery.fancybox.min.js"></script>
    <script src="/static/star-2-3/Static HTML/assets/plugins/daterangepicker/js/moment.min.js"></script>
    <script src="/static/star-2-3/Static HTML/assets/plugins/daterangepicker/js/daterangepicker.min.js"></script>
    <script src="/static/star-2-3/Static HTML/assets/plugins/lazyestload/lazyestload.js"></script>
    <script src="/static/star-2-3/Static HTML/assets/plugins/dzsparallaxer/dzsparallaxer.js"></script>
    <script src="/static/star-2-3/Static HTML/assets/plugins/slick/slick.min.js"></script>
    
    <script src="/static/star-2-3/Static HTML/assets/plugins/revolution/js/jquery.themepunch.tools.min.js"></script>
    <script src="/static/star-2-3/Static HTML/assets/plugins/revolution/js/jquery.themepunch.revolution.min.js"></script>
    <script src="/static/star-2-3/Static HTML/assets/plugins/owl-carousel/owl.carousel.min.js"></script>
    <script src="/static/star-2-3/Static HTML/assets/plugins/smoothscroll/SmoothScroll.js"></script>
    <script src="/static/star-2-3/Static HTML/assets/js/star.js"></script>	
	<script src="common/common.js"></script>
	
	<script src="/static/common/sweet_alert2/sweetalert2.all.js"> </script>
</body>

<script>
	refreshAt(1, 0, 0);
</script>

<script>
	function initFaceLog() {
		$.ajax({		
			url: 'AjaxController?txtSelectedLink=FK',		
			data: {			
				txtAction: "INIT"
			},		
			method: 'POST',		
			dataType: 'JSON',		
			success: function(response) {	
				showAlert("Initializing...Done", 5);		
			}
		});
	}
</script>
</html>