<%@ page import="java.util.*"%>
<%@ page import="com.mytechnopal.*"%>
<%@ page import="com.mytechnopal.link.*"%>
<%@ page import="com.mytechnopal.base.WebControlBase"%>
<%@ page import="com.mytechnopal.base.DTOBase"%>
<%@ page import="com.mytechnopal.dto.*"%>
<%@ page import="com.mytechnopal.util.*"%>
<%@ page import="com.mytechnopal.dao.*"%>
<%@ page import="com.mytechnopal.webcontrol.*" %>
<%@ page import="com.laponhcet.enrollment.*" %>

<%@ page import="com.laponhcet.pageant.*" %>

<%
SessionInfo sessionInfo = (SessionInfo) session.getAttribute(SessionInfo.SESSION_INFO);
List<DTOBase> contestantList = (List<DTOBase>)session.getAttribute(ContestantDTO.SESSION_CONTESTANT_LIST);
List<DTOBase> eventJudgeList = (List<DTOBase>)session.getAttribute(EventJudgeDTO.SESSION_EVENT_JUDGE_LIST);
List<DTOBase> eventCriteriaList = (List<DTOBase>)session.getAttribute(EventCriteriaDTO.SESSION_EVENT_CRITERIA_LIST);
List<DTOBase> eventScoreList = (List<DTOBase>)session.getAttribute(EventScoreDTO.SESSION_EVENT_SCORE_LIST);

String[] colorStr = new String[] {"#DAF7A6", "#FDEFBC", "#FF8DA7"};
%>

<div class="pt-5 mb-5">
	<div class="row">
		<div class="col-lg-12" style="overflow: auto;">
			<table class="table table-bordered">
	          	<tr>
	              	<td>#</td>
	              	<td>Contestant</td>
                 	<%
                 	for(DTOBase eventJudgeObj: eventJudgeList) {
                 		EventJudgeDTO eventJudge = (EventJudgeDTO)eventJudgeObj;
                 	%>
                 	<td align="center">
                 		<%=eventJudge.getJudge().getName(true, true, true) %>
                 		<table width="100%">
                 			<tr>
                 	<%
                 		for(DTOBase eventCriteriaObj: eventCriteriaList) {
                 			EventCriteriaDTO eventCriteria = (EventCriteriaDTO)eventCriteriaObj;
                 	%>
                 				<td width="<%=80/eventCriteriaList.size()%>%" align="center"><%=eventCriteria.getNameShort()%></td>		
                 	<%	
                 		}
                 	%>	
                 				<td width="20%" align="center" >TOTAL</td>
                 			</tr>
                 		</table>
                 	</td>	
                 	<%
                 	}
                 	%>	
                 	<td>TOTAL</td>	
	         	</tr>	
				<%
				for(DTOBase contestantObj: contestantList) {
					ContestantDTO contestant = (ContestantDTO)contestantObj;	
					double totalPerContestant = 0d;
				%>
				<tr>
					<td><%=contestant.getSequence()%></td>
					<td><%=contestant.getName()%></td>
				<%
					for(DTOBase eventJudgeObj: eventJudgeList) {
               			EventJudgeDTO eventJudge = (EventJudgeDTO)eventJudgeObj;
            	%>
             		<td>
	                	<table width="100%">
	                		<tr>
	           	<%
            			double totalPerJudge = 0d;
            			for(DTOBase eventCriteriaObj: eventCriteriaList) {
            				EventCriteriaDTO eventCriteria = (EventCriteriaDTO)eventCriteriaObj;
            				EventScoreDTO eventScore = EventScoreUtil.getEventScoreByJudgeCodeEventCriteriaCodeContestantCode(eventScoreList, eventJudge.getJudge().getCode(), eventCriteria.getCode(), contestant.getCode());
            				String eventScoreStr = "-"; //eventScore == null?"-":
            				if(eventScore != null) {
            					eventScoreStr = StringUtil.getFormattedNum(eventScore.getScore(), StringUtil.NUMERIC_STANDARD_FORMAT);
            					totalPerJudge += eventScore.getScore();
            				}
            	%>
            					<td width="<%=80/eventCriteriaList.size()%>%" align="center"><%=eventScoreStr%></td>		
            	<%	
            			}
            			totalPerContestant += totalPerJudge;
            	%>	
            					<td width="20%" align="center"><b><%=totalPerJudge%></b></td>
            				</tr>
            			</table>
            		</td>	
            	<%
					}
				%>	
					<td align="center"><b><%=totalPerContestant/eventJudgeList.size()%></b></td>
				</tr>
				<%	
				}
				%>	
			</table>
		</div>
	</div>
</div>