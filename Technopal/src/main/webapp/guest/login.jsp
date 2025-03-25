<%@ page import="com.mytechnopal.*"%>
<%@ page import="com.mytechnopal.dto.*"%>
<%@ page import="com.mytechnopal.util.*"%>
<%@ page import="com.mytechnopal.base.*"%>
<%@ page import="com.mytechnopal.link.*"%>
<%@ page import="com.mytechnopal.webcontrol.*"%>
<%
SessionInfo sessionInfo = (SessionInfo) session.getAttribute(SessionInfo.SESSION_INFO);
%>

<div class="modal fade" id="divLogin" tabindex="-1" role="dialog" aria-label="loginModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-sm modal-dialog-centered" role="document">
    	<div class="modal-content">
      		<div class="modal-header rounded">
        		<h3 class="modal-title text-uppercase font-weight-bold">Log in to your account</h3>
        		<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      		</div>
      		<div class="modal-body">
          		<%=new TextBoxWebControl().getTextBoxWebControl("col-sm-12", true, "Username", "username", "UserName", sessionInfo.getCurrentUser().getUserName(), 16, WebControlBase.DATA_TYPE_STRING, "")%>
				<div class="col-sm-12"><small>&nbsp;</small></div>
				<%=new TextBoxWebControl().getTextBoxWebControl("col-sm-12", true, "Password", "password", "Password", sessionInfo.getCurrentUser().getPassword(), 16, WebControlBase.DATA_TYPE_PASSWORD, "")%>		
         		<div class="d-grid">
         			<hr>
          			<button id="btnLogin" type="button" onclick="login()" class="btn btn-primary btn-md" data-loading-text="<i class='fa fa-spinner fa-spin '></i> Loading...">Login</button>
         		</div>
      		</div>
    	</div>
	</div>
</div>

<script>

$("#txtPassword").keyup(function(event) {
    if (event.keyCode === 13) {
    	login();
    }
});

function login() {
	$.ajax({
		url: 'AjaxController?txtSelectedLink=<%=LinkDTO.GUEST_HOME%>',
		data: {
			txtAction: "<%=UserDTO.ACTION_LOGIN%>",
			txtUserName: $("#txtUserName").val(),
			txtPassword: $("#txtPassword").val()
		},
		method: 'POST',
		dataType: 'JSON',
		success: function(response) {
			if(response.<%=ActionResponse.MESSAGE_TYPE%> === "") {
				openLink('<%=LinkDTO.USER_HOME%>');
			}
			else {
				swal.fire({
					title: response.<%=ActionResponse.MESSAGE_TITLE%>,
					text: response.<%=ActionResponse.MESSAGE_STR%>,
					icon: response.<%=ActionResponse.MESSAGE_TYPE%>
				});
			}
		}
	});
}
</script>