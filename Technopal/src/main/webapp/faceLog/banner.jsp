<%@ page import="java.util.*"%>
<%@ page import="com.mytechnopal.*"%>
<%@ page import="com.mytechnopal.dto.*"%>
<%@ page import="com.mytechnopal.util.*"%>
<%@ page import="com.mytechnopal.base.*"%>
<%@ page import="com.mytechnopal.banner.*"%>
<%
SessionInfo sessionInfo = (SessionInfo) session.getAttribute(SessionInfo.SESSION_INFO);
List<DTOBase> bannerList = new BannerDAO().getBannerList();
%>
<div id="rev_slider_12_1_wrapper" class="rev_slider_wrapper fullwidthbanner-container" data-alias="star-test" data-source="gallery" style="margin:0px auto;background:rgba(0,0,0,0.15);padding:0px;margin-top:0px;margin-bottom:0px;background-repeat:no-repeat;background-size:cover;background-position:center center;" dir="ltr">
	<div id="rev_slider_12_1" data-version="5.4.8.1">
		<ul>
		<%
		for(DTOBase obj: bannerList) {
			BannerDTO banner = (BannerDTO)obj;
		%>
			<li data-delay="<%=banner.getDurationInSeconds()%>">
	        	<img src="/static/<%=sessionInfo.getSettings().getCode().toUpperCase()%>/media/banner/<%=banner.getFilename()%>">
	      	</li> 	
		<%	
		}
		%>
		</ul>
	</div>
</div>