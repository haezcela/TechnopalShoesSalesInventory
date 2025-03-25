<%@ page import="java.util.*"%>
<%@ page import="com.mytechnopal.*"%>
<%@ page import="com.mytechnopal.dto.*"%>
<%@ page import="com.mytechnopal.util.*"%>
<%@ page import="com.mytechnopal.base.*"%>
<%@ page import="com.mytechnopal.Calendar"%>
<%@ page import="com.mytechnopal.facelog.*"%>

<%
SessionInfo sessionInfo = (SessionInfo)session.getAttribute(SessionInfo.SESSION_INFO);
Calendar calendar = (Calendar)session.getAttribute(Calendar.SESSION_CALENDAR);
Date firstDate = DateTimeUtil.getFirstDate(calendar.getDate());
Date lastDate = DateTimeUtil.getLastDate(calendar.getDate());
Date runningDate = firstDate;

List<DTOBase> faceLogListByUser = (ArrayList<DTOBase>)session.getAttribute(FaceLogDTO.SESSION_FACELOG_LIST + "_" + sessionInfo.getCurrentUser().getCode());
List<DTOBase> faceLogListByMonthYear = FaceLogUtil.getFaceLogListByMonthYear(faceLogListByUser, calendar.getDate());
String[][] dateContentArr = new String[6][7];

boolean isFirstDateDayOfTheWeekFound = false;
for (int i=0; i<dateContentArr.length; i++) {
    for(int j=0; j<dateContentArr[i].length; j++) {
    	if(j+1 == DateTimeUtil.getDateDayOfTheWeek(firstDate)) {
    		isFirstDateDayOfTheWeekFound = true;
    	}
    	
    	if(isFirstDateDayOfTheWeekFound) {
    		if(runningDate.before(DateTimeUtil.addDay(lastDate, 1))) {
    			String str = "";
    			String strIn = FaceLogUtil.getFaceLogListByDate(faceLogListByMonthYear, runningDate, true);
    			String strOut = FaceLogUtil.getFaceLogListByDate(faceLogListByMonthYear, runningDate, true);
    			if(StringUtil.isEmpty(strIn) && StringUtil.isEmpty(strOut)) {
    				str = "<table class='table'><tbody>";
        			str += "<tr><td><small>" + String.valueOf(DateTimeUtil.getDateDayOfTheMonth(runningDate)) + "</small></td></tr>";
            		str += "<tr><td><small></small></td></tr>";
            		str += "<tr><td><small></small></td></tr>";
            		str += "</tbody></table>";
    			}
    			else {
    				String strInShort = StringUtil.getShortDesc(strIn, 18);
        			String strOutShort = StringUtil.getShortDesc(strOut, 18);
    				str = "<table class='table table-striped'><tbody>";
        			str += "<tr><td><small>" + String.valueOf(DateTimeUtil.getDateDayOfTheMonth(runningDate)) + "</small></td></tr>";
            		str += "<tr><td><small>" + strInShort + "</small></td></tr>";
            		str += "<tr><td><small>" + strOutShort + "</small></td></tr>";
            		str += "</tbody></table>";
    			}

        		dateContentArr[i][j] = str;
    		}
    		else {
    			dateContentArr[i][j] = "";
    		}
    		runningDate = DateTimeUtil.addDay(runningDate, 1);
    	}
    	else {
    		dateContentArr[i][j] = "";
    	}
	}
}
%>


<table class='table' width='98%'>
	<thead>
		<tr>
			<td colspan="8" align="center">
				<h3><%=DateTimeUtil.getMonthName(calendar.getDate().getMonth())%> <%=DateTimeUtil.getDateYear(calendar.getDate())%></h3>
			</td>
		</tr>
	</thead>
	<tbody>
		<tr>
	<%
	String[] dayStr = new String[]{"Su", "Mo", "Tu", "We", "Th", "Fr", "Sa"};
	for(int day=0; day<dayStr.length; day++) {
	%>
			<td width="14%"><%=dayStr[day]%></td>	
	<%	
	}
	%>
		</tr>
	<%
	for (int i=0; i<dateContentArr.length; i++) {
	%>	
		<tr>
		<%
		for(int j=0; j<dateContentArr[i].length; j++) {
		%>
			<td style="border: 1px solid black"><%=dateContentArr[i][j]%></td>	
		<%	 
		}
		%>
		</tr>
	<%
	}
	%>	
	</tbody>
</table>	