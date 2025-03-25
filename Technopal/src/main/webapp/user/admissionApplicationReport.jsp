<%@ page import="java.util.*"%>
<%@ page import="com.mytechnopal.*"%>
<%@ page import="com.mytechnopal.base.WebControlBase"%>
<%@ page import="com.mytechnopal.base.DTOBase"%>
<%@ page import="com.mytechnopal.dto.*"%>
<%@ page import="com.mytechnopal.util.*"%>
<%@ page import="com.mytechnopal.dao.*"%>
<%@ page import="com.mytechnopal.webcontrol.*" %>
<%@ page import="com.laponhcet.admissionapplication.*" %>

<%
SessionInfo sessionInfo = (SessionInfo) session.getAttribute(SessionInfo.SESSION_INFO);
List<DTOBase> admissionApplicationList = (ArrayList<DTOBase>)session.getAttribute(AdmissionApplicationDTO.SESSION_ADMISSION_APPLICATION_LIST);
%>

<div class="container" id='divAdmissionApplicationList'>
<%
	int totalRecordPerPage = 60;
	int totalPage = admissionApplicationList.size()%totalRecordPerPage==0?admissionApplicationList.size()/totalRecordPerPage:admissionApplicationList.size()/totalRecordPerPage+1;
	int recordStart = 0;
	int recordEnd = 0;
	int totalColumn = 2;
	int ctr = 1;
	for(int p=1; p<=totalPage; p++) {
%>	
	<div class="col-lg-12">
		<div class="text-center">
			<h4>Bago City College - Guidance Office<br>List of Applicants for Examination - Batch 1<br>January 25, 2025</h4>
		</div>
		<table width='100%'>
			<tr>
		<%
		for(int col=1; col<=totalColumn; col++) {
			recordStart = recordEnd+1;
			recordEnd = recordStart+29;
			if(recordEnd > admissionApplicationList.size()) {
				recordEnd = admissionApplicationList.size();
				col = totalColumn+1;
			}
		%>	
				<td width='48%' valign='top'>
					<table>
						<tr>
							<th></th>
							<th>Last Name</th>
							<th>First Name</th>
							<th>Middle Name</th>
							<th>Reference</th>
						</tr>
				<%
				for(int i=recordStart; i<=recordEnd; i++) {
					AdmissionApplicationDTO admissionApplication = (AdmissionApplicationDTO)admissionApplicationList.get(i-1);
				%>
						<tr>
							<td width="10%"><small><%=ctr++%></small>.</td>
							<td width="25%"><small><%=admissionApplication.getLastName().replace("?", "Ñ")%></small></td>
							<td width="25%"><small><%=admissionApplication.getFirstName().replace("?", "Ñ")%></small></td>
							<td width="25%"><small><%=admissionApplication.getMiddleName().replace("?", "Ñ")%></small></td>
							<td width="15%"><small><%=admissionApplication.getCode()%></small></td>
						</tr>
				<%	
				}
				%>
					</table>
				</td>
		<%
			if(col==1) {
		%>
				<td width='4%' valign='top'>&nbsp;</td>
		<%		
			}
		}
		%>	
			</tr>
		</table>
	</div>
	<div style="page-break-after:always"><br></div>	
<%
	}
%>		
</div>
<div class='col-lg-12 text-center'>
	<button type='button' class='btn btn-success' data-toggle='tooltip' data-placement='top' title='Print' onClick="printPreview('divAdmissionApplicationList')"><i class='fa fa-print'></i> Print</button>
</div>