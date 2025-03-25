<%@ page import="com.mytechnopal.SessionInfo" %>
<%@ page import="com.mytechnopal.ActionResponse" %>

<%
SessionInfo sessionInfo = (SessionInfo) session.getAttribute(SessionInfo.SESSION_INFO);
%>

<video autoplay="true" id="video" style="display:none"></video>
<section id="divBanner" class="bg-smoke">
	<jsp:include page="banner.jsp"></jsp:include>
</section>	
<section>
	<br><jsp:include page="calendar.jsp"></jsp:include>
	<br><marquee scrollamount="3"><h1 class="text-white"><%=sessionInfo.getSettings().getName()%>, FaceKeeper Powered By TechnoPal Software</h1></marquee>
</section>	
<section id="divFaceLog" class="bg-smoke" style="display:none">
	<div class="container">
	   	<div class="row">
			<div id="divFaceLogCurrent" class="col-lg-4">
				<div class="card card-hover mb-5 mb-lg-0">
		            <img class="card-img-top lazyestload" src="/static/common/images/pict_default.png" alt="Card image cap">
		       	</div>
		       	<div class="card-body bg-white">
		            
		      	</div>
		      	<div class="card card-hover mb-5 mb-lg-0">
		            <img class="card-img-top lazyestload" src="/static/common/images/pict_default.png" alt="Card image cap">
		       	</div>
			</div>
			<div class="col-lg-8">
				<div class="row justify-content-center">
				<%
					for(int i=2; i<=10; i++) {
				%>
					
					<div id="divFaceLog<%=i%>" class="col-md-6 col-lg-4">
				        <div class="card">
				            <img class="card-img-top lazyestload" src="/static/common/images/pict_default.png">
				       	</div>
				       	<div class='card-body bg-white'>
				       		&nbsp;&nbsp;&nbsp;
				       	</div>
				  	</div>
				<%
					}
				%>  	
				</div>	
			</div>
		</div>
	</div>
	<div id="divFaceLog1" style="display:none">
		<div class="card">
            <img class="card-img-top lazyestload" src="/static/common/images/pict_default.png">
       	</div>
       	<div class='card-body bg-white'>
       		&nbsp;&nbsp;&nbsp;
       	</div>
	</div>
</section>
<section id="divBottom">&nbsp;</section>

<script>
	var video = document.querySelector("#video");
	if(navigator.mediaDevices.getUserMedia) {       
		navigator.mediaDevices.getUserMedia({video: true}).then(
		function(stream) {
	    	video.srcObject = stream;
	  	}).catch(function(err0r) {
	    	console.log("Something went wrong!");
	  	});
	}
	document.addEventListener("keyup", function(e) {
		if(e.keyCode === 13) { //Enter was pressed
			tap();
		}
		else if(e.keyCode === 121) { //F10 was pressed
			if(document.getElementById("divFaceLog").offsetParent === null) {
				$("#divFaceLog").show();
				document.getElementById("divBottom").scrollIntoView();
			}
			else {
				$("#divFaceLog").hide();
				window.scrollTo({ top: 0, behavior: 'smooth' });
			}
		}
		else {
			$("#txtSelectedRecord").val($("#txtSelectedRecord").val() + String.fromCharCode(e.keyCode));
		}
	});
	
	function tap() {
		if($('#txtSelectedRecord').val().length == 0) {
			showAlert("Access Code is Empty", 5000);
		}
		else if($('#txtSelectedRecord').val().length < 5) {
			showAlert("Access Code is Invalid", 5000);
		}
		else {	
			var canvas = document.createElement('canvas');    
		    var video = document.getElementById('video');
		    canvas.width = video.videoWidth;
		    canvas.height = video.videoHeight;
		    canvas.getContext('2d').drawImage(video, 0, 0, video.videoWidth, video.videoHeight);

			$.ajax({		
				url: 'AjaxController?txtSelectedLink=FK',		
				data: {			
					txtSelectedRecord: $("#txtSelectedRecord").val(),
					txtDataURL: canvas.toDataURL()
				},		
				method: 'POST',		
				dataType: 'JSON',		
				success: function(response) {	
					if(response.<%=ActionResponse.MESSAGE_STR%>.length === 0) {
						for(i=10; i>=2; i--) {
							document.getElementById("divFaceLog" + i).innerHTML = document.getElementById("divFaceLog" + (i-1)).innerHTML;
						}
						getFaceLogStr(response.isIn, response.capturedPict, response.profilePict, response.firstName, response.timeLog);
						showFaceLog();	
					}
					else {
						showAlert(response.<%=ActionResponse.MESSAGE_STR%>, 5000);
					}			
				}
			});
		}
		setElementVal("txtSelectedRecord", "");
	}
	
	function showFaceLog(){
		$("#divFaceLog").show();
		document.getElementById("divBottom").scrollIntoView();
		setTimeout(function() {
			$("#divFaceLog").hide();
			window.scrollTo({ top: 0, behavior: 'smooth' });
	 	}, 5000);
	}
	
	function getFaceLogStr(isIn, capturedPict, profilePict, firstName, timeLog) {
		logDetailsStr = "<h3>" + timeLog + "<span class='badge bg-danger' style='float:right'>OUT</span></h3>";
		if(isIn) {
			logDetailsStr = "<h3>" + timeLog + "<span class='badge bg-success' style='float:right'>IN</span></h3>";
		}
		faceLogStr = "<div class='card'>"
					+ "		<img src=\"" + capturedPict + "\">"
					+ "	</div>"
					+ "	<div class='card-body bg-white'>"
					+ "		<h5><a href='#' class='card-title text-uppercase'>" + firstName + "</a></h5>"		    
					+ "		<div class='d-flex justify-content-between align-items-center'>"
					+ "			<div class='col-lg-12'>"
					+ 				logDetailsStr 
					+ "			</div>"
					+ "		</div>"
					+ "	</div>";
		$("#divFaceLog1").html(faceLogStr + "</div>");	
		$("#divFaceLogCurrent").html(faceLogStr + "<div class='card'><img class='img-fluid' src=\"" + profilePict + "\"></div> </div>");
	}
	
	function getFaceLog1Str(isIn, capturedPict, profilePict, firstName, timeLog) {
		logDetailsStr = "<h3>" + timeLog + "<span class='badge bg-danger' style='float:right'>OUT</span></h3>";
		if(isIn) {
			logDetailsStr = "<h3>" + timeLog + "<span class='badge bg-success' style='float:right'>IN</span></h3>";
		}
		return "<div class='card'>"
			+ "		<img src=\"" + capturedPict + "\">"
			+ "	</div>"
			+ "	<div class='card-body bg-white'>"
			+ "		<h5><a href='#' class='card-title text-uppercase'>" + firstName + "</a></h5>"		    
			+ "		<div class='d-flex justify-content-between align-items-center'>"
			+ "			<div class='col-lg-12'>"
			+ 				logDetailsStr 
			+ "			</div>"
			+ "		</div>"
			+ "	</div>"
	}
	
	function showAlert(msg, duration) {
		window.scrollTo({ top: 0, behavior: 'smooth' });
		var el = document.createElement("div");
		el.setAttribute("style","text-align:center;position:absolute;top:15%;left:15%;padding:20px 50px;border-radius:10px;background-color:<%=sessionInfo.getSettings().getDefaultPrimaryColor()%>;box-shadow: 2px 3px 3px 1px rgba(0, 0, 0, 0.2);font-weight:600;color:white; opacity:0.8;");
		el.innerHTML = "<h4>" + msg + "</h4>";
		setTimeout(function() {
			el.parentNode.removeChild(el);
		}, duration);
		document.body.appendChild(el);
	}
</script>