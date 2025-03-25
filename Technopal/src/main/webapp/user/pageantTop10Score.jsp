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
List<DTOBase> eventList = (List<DTOBase>)session.getAttribute(EventDTO.SESSION_EVENT_LIST);
List<DTOBase> contestantList = (List<DTOBase>)session.getAttribute(ContestantDTO.SESSION_CONTESTANT_LIST);
List<DTOBase> eventJudgeList = (List<DTOBase>)session.getAttribute(EventJudgeDTO.SESSION_EVENT_JUDGE_LIST);
List<DTOBase> eventCriteriaList = (List<DTOBase>)session.getAttribute(EventCriteriaDTO.SESSION_EVENT_CRITERIA_LIST);
List<DTOBase> eventScoreList = (List<DTOBase>)session.getAttribute(EventScoreDTO.SESSION_EVENT_SCORE_LIST);

String[] colorStr = new String[] {"#FAF9D0", "#ADD8E6", "#ff474c", "#50C878", "#FFFFF7", "#D7D7D8", "#F7E7CE", "#CBC3E3", "#414a4c", "#FFB6C1"};
%>

<div class="pt-5 mb-5">
	<div class="row">
   		<div class="col-lg-12">
     		<div id="destination">
       			<div class="mb-0">
         			<ul class="nav nav-tabs destination-tabs" id="destinationTab" role="tablist">
           			<%
           			boolean isTabActive = false;
           			for(int i=0; i<eventList.size(); i++) {
           				EventDTO event = (EventDTO)eventList.get(i);
           				if(i==0) {
           					isTabActive = true;
           				}
           				else {
           					isTabActive = false;
           				}
           			%>
           				<li class="nav-item">
             				<a class="nav-link<%=isTabActive?" active":""%>" data-bs-toggle="tab" href="#tab<%=event.getCode()%>" role="tab"><%=event.getName()%></a>
           				</li>
           			<%	
           			}
           			%>	
          				 <li class="nav-item d-none"></li>
         			</ul>
     
        			<div class="tab-content destinationTab-content" id="destinationTabContent">
        			<%
           			for(int i=0; i<eventList.size(); i++) {
           				EventDTO event = (EventDTO)eventList.get(i);
                       	List<DTOBase> eventJudgeListByEventCode = EventJudgeUtil.getEventJudgeListByEventCode(eventJudgeList, event.getCode());
           				List<DTOBase> eventScoreListByEventCode = EventScoreUtil.getEventScoreListByEventCode(eventScoreList, event.getCode());
           				if(i==0) {
           					isTabActive = true;
           				}
           				else {
           					isTabActive = false;
           				}
           			%>	
           				<div class="tab-pane show<%=isTabActive?" active":""%>" id="tab<%=event.getCode()%>" role="tabpanel">
            				 <div class="row">
								<div class="col-lg-12" style="overflow: auto;">
               						<table width="100%">
			                        	<tr>
			                            	<td></td>
			                               	<td>Contestant</td>
			                               	<%
			                               	for(DTOBase eventJudgeObj: eventJudgeListByEventCode) {
			                               		EventJudgeDTO eventJudge = (EventJudgeDTO)eventJudgeObj;
			                               	%>
			                               	<td align="center">
			                               		<%=eventJudge.getJudge().getName(true, true, true) %>
			                              	<%	
			                               	List<DTOBase> eventCriteriaListByEventCode = EventCriteriaUtil.getEventCriteriaListByEventCode(eventCriteriaList, event.getCode());
			                               	%>
			                               		<table width="100%">
			                               			<tr>
			                               	<%
			                               		for(DTOBase eventCriteriaObj: eventCriteriaListByEventCode) {
			                               			EventCriteriaDTO eventCriteria = (EventCriteriaDTO)eventCriteriaObj;
			                               	%>
			                               				<td align="center"><%=eventCriteria.getNameShort()%></td>		
			                               	<%	
			                               		}
			                               	%>	
			                               				<td align="center" >T</td>
			                               				<td align="center" >R</td>
			                               			</tr>
			                               		</table>
			                               	</td>	
			                               	<%
			                               	}
			                               	%>	
			                               	<td>RS</td>	
			                               	<td>%</td>	
			                       		</tr>	
		               					<%
		               					for(DTOBase contestantObj: contestantList) {
		               						ContestantDTO contestant = (ContestantDTO)contestantObj;	
			               					double totalPerContestant = 0d;
		               					%>
               							<tr id="tr<%=event.getCode()%><%=contestant.getCode()%>">
               								<td><%=contestant.getSequence()%></td>
               								<td><%=contestant.getName()%></td>
               							<%
               							for(DTOBase eventJudgeObj: eventJudgeListByEventCode) {
		                               		EventJudgeDTO eventJudge = (EventJudgeDTO)eventJudgeObj;
			                               	List<DTOBase> eventCriteriaListByEventCode = EventCriteriaUtil.getEventCriteriaListByEventCode(eventCriteriaList, event.getCode());
			                            %>
			                            	<td>
		                               			<table width="100%">
		                               				<tr>
		                               	<%
		                               		double totalPerJudge = 0d;
		                               		for(DTOBase eventCriteriaObj: eventCriteriaListByEventCode) {
		                               			EventCriteriaDTO eventCriteria = (EventCriteriaDTO)eventCriteriaObj;
		                               			EventScoreDTO eventScore = EventScoreUtil.getEventScoreByJudgeCodeEventCriteriaCodeContestantCode(eventScoreListByEventCode, eventJudge.getJudge().getCode(), eventCriteria.getCode(), contestant.getCode());
		                               			String eventScoreStr = "-"; //eventScore == null?"-":
		                               			if(eventScore != null) {
		                               				eventScoreStr = StringUtil.getFormattedNum(eventScore.getScore(), StringUtil.NUMERIC_STANDARD_FORMAT);
		                               				totalPerJudge += eventScore.getScore();
		                               			}
		                               	%>
		                               					<td align="center"><%=eventScoreStr%></td>		
		                               	<%	
		                               		}
		                               		totalPerContestant += totalPerJudge;
		                               	%>	
		                               					<td align="center"><b><%=totalPerJudge%></b></td>
		                               					<td id="td<%=event.getCode()%><%=contestant.getCode()%><%=eventJudge.getJudge().getCode()%>" align="center"></td>
		                               				</tr>
		                               			</table>
		                               		</td>	
		                               	<%
			                               	List<DTOBase> eventScoreListFinalScore = EventScoreUtil.getEventFinalScoreList(event, contestantList, eventScoreList, eventJudge);
	               	        				new EventScoreSortDescending().sort(eventScoreListFinalScore);
	               	                       	for(int j=1; j<=eventScoreListFinalScore.size(); j++) {
	               	                       		EventScoreDTO contestantScore = (EventScoreDTO)eventScoreListFinalScore.get(j-1);                 
	               	                  		%>
	               	                   		<script>
	               	                       		setTimeout(function (){
	               	                       			$("#td<%=event.getCode()%><%=contestantScore.getContestant().getCode()%><%=eventJudge.getJudge().getCode()%>").html("<b><%=j%></b>");
	               								}, 1500); 
	               	                       	</script>
	               	                   		<% 		
	               	                       	}
               							}
               							%>	
               								<td align="center"><b><%=totalPerContestant%></b></td>
               								<td align="center"><b><%=totalPerContestant/eventJudgeListByEventCode.size()%></b></td>
               							</tr>
               					<%	
               					}
               					%>	
               						</table>
                				</div>
             				</div>
           				</div>
					<%
           			}
					%>           				
          			</div>
        		</div>
      		</div>
    	</div>
    	<div class="col-12"></div>
  	</div>
</div>

<div class="pt-5 mb-5">
	<div class="row">
   		<div class="col-lg-12" style="overflow: auto;">
   			<h3>Summary</h3>
   			<table class="table table-bordered">
              	<tr>
                  	<td></td>
                   	<td>Contestant</td>
                <%
                for(DTOBase eventObj: eventList) {
       				EventDTO event = (EventDTO)eventObj;
       			%>
       				<td align="center"><%=event.getName()%></td>
       			<%	
                }
                %>  
                	<td align="center">Total</td> 	
                	<td align="center">Rank</td> 
                </tr>
                <%
                for(DTOBase contestantObj: contestantList) {
					ContestantDTO contestant = (ContestantDTO)contestantObj;	
       				List<DTOBase> eventScoreListByContestantCode = EventScoreUtil.getEventScoreListByContestantCode(eventScoreList, contestant.getCode());
                    double totalPerContestant = 0d;
       			%>
                <tr id="tr<%=contestant.getCode()%>">	
                	<td><%=contestant.getSequence()%></td>
                	<td><%=contestant.getName()%></td>
                <%
	                for(DTOBase eventObj: eventList) {
	       				EventDTO event = (EventDTO)eventObj;
	       				List<DTOBase> eventScoreListByEventCode = EventScoreUtil.getEventScoreListByEventCode(eventScoreListByContestantCode, event.getCode());
	       				List<DTOBase> eventJudgeListByEventCode = EventJudgeUtil.getEventJudgeListByEventCode(eventJudgeList, event.getCode());

	       				double totalPerEvent = EventScoreUtil.getTotalEventScoreList(eventScoreListByEventCode)/eventJudgeListByEventCode.size();
	       				totalPerContestant += (totalPerEvent * (event.getPercentageForFinal()/100));
	       		%>
	       			<td align="center"><%=totalPerEvent%> (<%=StringUtil.getFormattedNum(event.getPercentageForFinal(), StringUtil.NUMERIC_WHOLE_NUMBER_FORMAT) %>%)</td>
	       		<%		
	                }
               	%>
               		<td align="center"><b><%=totalPerContestant%></b></td> 	
               		<td id="td<%=contestant.getCode()%>" align="center"><b></b></td> 	
               	</tr>	
               	<% 
                }
				List<DTOBase> eventScoreListFinalScore = EventScoreUtil.getEventFinalScoreList(eventList, contestantList, eventScoreList, eventJudgeList);
				new EventScoreSortDescending().sort(eventScoreListFinalScore);
               	for(int j=1; j<=eventScoreListFinalScore.size(); j++) {
               		EventScoreDTO contestantScore = (EventScoreDTO)eventScoreListFinalScore.get(j-1);                 
          		%>
           		<script>
               		setTimeout(function (){
               			$("#td<%=contestantScore.getContestant().getCode()%>").html("<b><%=j%></b>");
					}, 1500); 
               	</script>
           		<% 		
           			for(int i=1; i<=10; i++) {	
           				if(j==i) {
           		%>				
      			<script>
              		setTimeout(function (){
               			$("#tr<%=contestantScore.getContestant().getCode()%>").css("background-color", "<%=colorStr[i-1]%>");
					}, 1500); 
              	</script>
           		<%       	
               			}
           			}
               	}
          		%>
           	</table>        	
   		</div>
   	</div>
 </div>  		
 
 <div class="pt-5 mb-5">
	<div class="row">
   		<div class="col-lg-12 text-center">
   			<div style="display: flex; gap: 10px;">
   				<div style="display: flex; justify-content: center; align-items: center;"><h3>Legend</h3></div>
   			<%
   			for(int i=0; i<colorStr.length; i++) {
   			%>
   				<div style="display: flex; justify-content: center; align-items: center; width: 50px; height: 50px; background-color: <%=colorStr[i]%>"; border: 1px solid <%=colorStr[i]%>;><b><%=StringUtil.getRank(i+1)%></b></div>
   			<%	
   			}
   			%>
   			</div>
   			<hr>
   			<button class="btn btn-primary" onclick="confirmFinalist(3)">Confirm Top 10</button>	
   		</div>
   	</div>
</div>   	


<script>
	function confirmFinalist(num) {
		$.ajax({		
			url: 'AjaxController?txtSelectedLink=<%=sessionInfo.getCurrentLink().getCode()%>', 
			data: {			
				txtAction: 'CONFIRM TOP10'
			},		
			method: 'POST',		
			dataType: 'JSON',		
			success: function(response) {	
				Swal.fire({
					title: response.<%=ActionResponse.MESSAGE_TITLE%>,
					text: response.<%=ActionResponse.MESSAGE_STR%>,
					icon: response.<%=ActionResponse.MESSAGE_TYPE%>
				});
			}	
		});
	}
</script>	