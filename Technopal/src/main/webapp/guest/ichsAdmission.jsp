<%@ page import="java.util.*"%>
<%@ page import="com.mytechnopal.*"%>
<%@ page import="com.mytechnopal.dto.*"%>
<%@ page import="com.mytechnopal.util.*"%>
<%@ page import="com.mytechnopal.base.*"%>
<%@ page import="com.mytechnopal.banner.*"%>
<%
SessionInfo sessionInfo = (SessionInfo) session.getAttribute(SessionInfo.SESSION_INFO);
%>

<script type="text/javascript" src="https://cdn.jsdelivr.net/jquery/latest/jquery.min.js"></script>

<section class="bg-smoke py-5">
	<div class="container" id="<%=sessionInfo.getCurrentLink().getCode()%>">
    	<div class="row">
    		<div class="col-lg-4">
      			<img class="img-fluid" src="/static/ICHS/media/banner/banner2.jpg">
      		</div>
      		<div class="col-lg-8 bg-white p-3 border-top border-top-5 border-primary rounded">
      			<h4 class="text-uppercase font-weight-bold">Online Enrollment Form</h4>
           		Please fill up all necessary data for online enrollment. Mandatory data are data labeled with <font color='red'>*</font>.
           		<hr>
        		<div class="row">
          			<div class="col-lg-6 ">
           				<div class="form-group form-group-icon form-group-icon-dark mb-5">
             				Last Name <font color='red'>*</font>
             				<input type="text" class="form-control" autocomplete="off" id="txtLastName" name="txtLastName" value="" placeholder="Last Name" />
           				</div>
           				<div class="form-group form-group-icon form-group-icon-dark mb-5">
             				First Name <font color='red'>*</font>
             				<input type="text" class="form-control" autocomplete="off" id="txtFirstName" name="txtFirstName" value="" placeholder="First Name" />
           				</div>
           				<div class="form-group form-group-icon form-group-icon-dark mb-5">
             				Middle Name 
             				<input type="text" class="form-control" autocomplete="off" id="txtMiddleName" name="txtMiddleName" value="" placeholder="Middle Name" />
           				</div>
           				<div class="form-group form-group-icon form-group-icon-dark mb-5">
             				LRN <font color='red'>*</font>
             				<input type="text" class="form-control" autocomplete="off" id="txtLRN" name="txtLRN" value="" placeholder="LRN" />
           				</div>
      				 </div>
      				 <div class="col-lg-6 ">
           				<div class="form-group form-group-icon form-group-icon-dark mb-5">
             				Cellphone <font color='red'>*</font>
             				<input type="text" class="form-control" autocomplete="off" id="txtCPNumber" name="txtCPNumber" value="" placeholder="Cellphone" />
           				</div>
           				<div class="form-group form-group-icon form-group-icon-dark mb-5">
             				Email 
             				<input type="text" class="form-control" autocomplete="off" id="txtEmailAddress" name="txtEmailAddress" value="" placeholder="Email" />
           				</div>
           				<div class="form-group form-group-icon form-group-icon-dark mb-5">
             				Date of Birth <font color='red'>*</font>
             				<input type="text" class="form-control" autocomplete="off" id="txtDateOfBirth" name="txtDateOfBirth" value="" placeholder="Date of Birth" />
           				</div>
           				<script>
           				$(function() {
           				  $('input[name="txtDateOfBirth"]').daterangepicker({
           					singleDatePicker: true,
           					showDropdowns: true,
           				    locale: {
           				      format: 'MM/DD/YYYY'
           				    }
           				  });
           				});
           				</script>
           				<div class="form-group form-group-icon form-group-icon-dark mb-5">
             				I am enrolling for <font color='red'>*</font>
							<select class="form-control select-option" id="cboAcademicProgram">
                    			<option value="2">Grade 7</option>
                    			<option value="4">Grade 11 - ABM</option>
                    			<option value="6">Grade 11 - HUMSS</option>
                    			<option value="5">Grade 11 - STEM</option>
                  			</select>
           				</div>
      				 </div>
      				 <div class="col-lg-12 text-center">
      				 	<button class="btn btn-success" onclick="SubmitEnrollmentApplication()">Submit</button>
      				 </div>
        		</div>
      		</div>
  		</div>
  	</div>	
 </section>

<script>
	<%=WebUtil.getJSCustom(sessionInfo, "SubmitEnrollmentApplication", "txtLastName:$('#txtLastName').val(), txtFirstName:$('#txtFirstName').val(), txtMiddleName:$('#txtMiddleName').val(), txtLRN:$('#txtLRN').val(), txtCPNumber:$('#txtCPNumber').val(), txtEmailAddress:$('#txtEmailAddress').val(), txtDateOfBirth:$('#txtDateOfBirth').val(), cboAcademicProgram:$('#cboAcademicProgram').val()", sessionInfo.getCurrentLink().getCode(), ActionResponse.DIALOG_TYPE_SWAL, "") %>
</script>	