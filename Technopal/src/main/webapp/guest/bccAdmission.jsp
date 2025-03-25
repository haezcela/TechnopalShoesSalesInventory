<%@ page import="java.util.*"%>
<%@ page import="com.mytechnopal.*"%>
<%@ page import="com.mytechnopal.dto.*"%>
<%@ page import="com.mytechnopal.util.*"%>
<%@ page import="com.mytechnopal.base.*"%>
<%@ page import="com.mytechnopal.banner.*"%>
<%
SessionInfo sessionInfo = (SessionInfo) session.getAttribute(SessionInfo.SESSION_INFO);
%>

<section class="bg-smoke py-3">
	<div class="container" id="<%=sessionInfo.getCurrentLink().getCode()%>">
    	<div class="row">  		
      		<div class="col-lg-8">
      			<img class="img-fluid" src="/static/BCC_ADMISSION/media/banner/BAS_Screenshot_2025_01_17_at_10_41_28_AM_902.png">
      		</div>
      		<div class="col-lg-4">
        		<div class="row">
          			<div class="col-md-6 col-lg-12">
          			<%
//           			boolean isAllow = false;
// 					Date dateFrom = DateTimeUtil.getStrToDateTime("18:00", "kk:mm");
// 					Date dateTo = DateTimeUtil.getStrToDateTime("23:59", "kk:mm");
					
// 					Date dateFrom2 = DateTimeUtil.getStrToDateTime("00:01", "kk:mm");
// 					Date dateTo2 = DateTimeUtil.getStrToDateTime("06:00", "kk:mm");
					
// 					if(DateTimeUtil.isTimeWithin(DateTimeUtil.getCurrentTimestamp(), dateFrom, dateTo) || DateTimeUtil.isTimeWithin(DateTimeUtil.getCurrentTimestamp(), dateFrom2, dateTo2)) {
// 						isAllow = true;
// 					}
// 					if(isAllow) {
          			%>
            			<div class="mb-6 bg-white p-3 border-top border-top-5 border-primary rounded">
               				<h4 class="text-uppercase font-weight-bold">View Application Form</h4>
               				To view your application form input your application reference and your date of birth in a MMDDYYYY format. If your birthday is September 7, 2006 your date of birth is 09072006.
               				<hr>
               				<div class="form-group form-group-icon form-group-icon-dark mb-5">
                 				Reference 
                 				<input type="text" class="form-control" autocomplete="off" id="txtAdmissionApplicationCode" name="txtAdmissionApplicationCode" value="" placeholder="Reference Code" />
               				</div>
               				<div class="form-group form-group-icon form-group-icon-dark mb-5">
                 				Date of Birth
                 				<input type="text" class="form-control" autocomplete="off" id="txtDateOfBirth" name="txtDateOfBirth" value="" placeholder="MMDDYYYY" />
               				</div>
               				<div class="d-grid">
                 				<button type="button" onclick="ViewAdmissionApplication()" class="btn btn-xs btn-outline-secondary text-uppercase">Search</button>
               				</div>
          				 </div>
          				 <h3>New Application? Please click <a href="#" onclick="openLink('010')" target="_blank">here</a></h3>
          			<%
// 					}
// 					else {
					%>
<!-- 					<h2 class="card-header bg-smoke border-bottom"> -->
<!--               			Due to heavy web traffic, viewing and encoding of admission application is only available from 6PM to 6AM only. -->
<!--             		</h2> -->
					<%	
					//}
          			%>		 
          			</div>
        		</div>
      		</div>
  		</div>
  	</div>	
 </section>

<script>
	<%=WebUtil.getJSCustom(sessionInfo, "ViewAdmissionApplication", "txtAdmissionApplicationCode:$('#txtAdmissionApplicationCode').val(), txtDateOfBirth:$('#txtDateOfBirth').val()", sessionInfo.getCurrentLink().getCode(), ActionResponse.DIALOG_TYPE_SWAL, "") %>
</script>	