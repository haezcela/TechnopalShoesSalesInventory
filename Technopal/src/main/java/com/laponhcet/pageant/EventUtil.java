package com.laponhcet.pageant;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.mytechnopal.ActionResponse;
import com.mytechnopal.SessionInfo;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.link.LinkDTO;
import com.mytechnopal.util.StringUtil;
import com.mytechnopal.webcontrol.SelectWebControl;

public class EventUtil implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//scores are in selection box 
//	public static String getPreliminaryStr(SessionInfo session, List<DTOBase> eventList, EventDTO event, List<DTOBase> contestantList, List<DTOBase> eventScoreList) {
//		StringBuffer strBuff = new StringBuffer();
//		strBuff.append("<div class='row'>");
//		strBuff.append(new SelectWebControl().getSelectWebControl("col-md-4", true, "Event", "Event", eventList, event, "-Select-", "0", "onchange='selectEvent(this.value)'"));
//		strBuff.append("</div>");
//		if(event.getId() > 0) {
//			strBuff.append("<div class='row'>");
//			strBuff.append("	<div class='col-lg-12'><hr></div>");
//			for(DTOBase contestantObj: contestantList) {
//				ContestantDTO contestant = (ContestantDTO) contestantObj;
//				strBuff.append("<div class='col-md-6 col-lg-4'>");
//				strBuff.append("	<div class='card shadow mb-5 mb-lg-0'>");
//				strBuff.append("		<img class='card-img-top' data-src='/static/CONTEST/media/contestant/" + contestant.getPict() + "' src='/static/CONTEST/media/contestant/" + contestant.getPict() + "'>");
//				strBuff.append("		<div class='card-body'>");
//				strBuff.append("			<h5 class='card-title'>" + contestant.getSequence() + ". " + contestant.getName() + "</h5>");
//				strBuff.append("  			<table width='100%'>");
//				for(DTOBase eventCriteriaObj: event.getEventCriteriaList()) {
//					EventCriteriaDTO eventCriteria = (EventCriteriaDTO) eventCriteriaObj;
//					EventScoreDTO eventScore = getEventScoreByContestantEventCriteria(eventScoreList, contestant, eventCriteria);
//					double score = eventScore == null?0d:eventScore.getScore();
//					strBuff.append("    		<tr>");
//					strBuff.append("				<td>" +	eventCriteria.getName() + "</td>");		
//					strBuff.append("				<td align='center'>[" + StringUtil.getFormattedNum(eventCriteria.getScoreMin(), StringUtil.NUMERIC_WHOLE_NUMBER_FORMAT) + " - " + StringUtil.getFormattedNum(eventCriteria.getScoreMax(), StringUtil.NUMERIC_WHOLE_NUMBER_FORMAT) + "]</td>");
//					strBuff.append("				<td align='center'>");
//					strBuff.append(						new SelectWebControl().getSelectWebControl(null, false, "", contestant.getCode() + eventCriteria.getCode(), StringUtil.getSeries((int)eventCriteria.getScoreMax(), (int)eventCriteria.getScoreMin(), -1), String.valueOf((int)score), StringUtil.getSeries((int)eventCriteria.getScoreMax(), (int)eventCriteria.getScoreMin(), -1), "-", "0", "style='text-align: right; direction: rtl;'"));
//					strBuff.append("				</td>");
//					//strBuff.append("				<td align='right'><input type='text' class='text-right' placeholder='' style='text-align: right;' name='txt" + contestant.getCode() + eventCriteria.getCode() + "' id='txt" + contestant.getCode() + eventCriteria.getCode() + "' value='" + StringUtil.getFormattedNum(score, StringUtil.NUMERIC_STANDARD_FORMAT_NO_COMMA) + "' size='5' onkeypress='return numbersonly(this, event, true, 5)' onclick='this.select();'>%</td>");
//					strBuff.append("    		</tr>");
//				}
//				strBuff.append("   			</table>");
//				strBuff.append("  			<div align='center'>");
//				strBuff.append("				<button class='btn btn-primary' onclick=\"submitScore" + contestant.getCode() + "()\">Submit</button>");
//				strBuff.append("  			</div>");
//				strBuff.append("  		</div>");
//				strBuff.append("  	</div>");
//				strBuff.append("</div>");
//				
//				strBuff.append("<script>");
//				strBuff.append("function submitScore" + contestant.getCode() +"() {");
//				strBuff.append("	$.ajax({");		
//				strBuff.append("		url: 'AjaxController?txtSelectedLink=" + session.getCurrentLink().getCode() +"',");
//				strBuff.append("		data: {");			
//				strBuff.append("			txtAction: 'SUBMIT SCORE',");
//				strBuff.append("			txtContestantCode: '" + contestant.getCode() + "',");
//				for(int i=0; i<event.getEventCriteriaList().size(); i++) {
//					EventCriteriaDTO eventCriteria = (EventCriteriaDTO)event.getEventCriteriaList().get(i);
//				strBuff.append("			cbo" + contestant.getCode() + eventCriteria.getCode() + ": $('#cbo" + contestant.getCode() + eventCriteria.getCode() + "').val()");
//					if(i<event.getEventCriteriaList().size()-1) {
//				strBuff.append(",");
//					}
//				}
//				strBuff.append("		},");		
//				strBuff.append("		method: 'POST',");		
//				strBuff.append("		dataType: 'JSON',");		
//				strBuff.append("		success: function(response) {");	
//				strBuff.append("			$('#divEvent').html(response." + LinkDTO.PAGE_CONTENT + ");");
//				strBuff.append("			if(response.MESSAGE_TYPE === '') {");
//				strBuff.append("				Swal.fire({");
//				strBuff.append("					title: 'SUCCESS',");
//				strBuff.append("					text: 'SUCCESSFULLY SUBMITTED',");
//				strBuff.append("					icon: 'success'");
//				strBuff.append("				});");
//				strBuff.append("			}");
//				strBuff.append("			else {");
//				strBuff.append("				Swal.fire({");
//				strBuff.append("					title: 'ERROR',");
//				strBuff.append("					text: response.MESSAGE_STR,");
//				strBuff.append("					icon: 'error'");
//				strBuff.append("				});");
//				strBuff.append("			}");	
//				strBuff.append("		}");	
//				strBuff.append("	});");
//				strBuff.append("}");
//				strBuff.append("</script>");
//				
//			}
//			strBuff.append("</div>");
//		}
//		return strBuff.toString();
//	}
	
//	public static String getFinalistStr(SessionInfo session, List<DTOBase> contestantList, List<DTOBase> eventCriteriaList, List<DTOBase> eventScoreList) {
//	StringBuffer strBuff = new StringBuffer();
//	
//	strBuff.append("<div class='row'>");
//	for(DTOBase contestantObj: contestantList) {
//		ContestantDTO contestant = (ContestantDTO) contestantObj;
//		strBuff.append("<div class='col-md-6 col-lg-4'>");
//		strBuff.append("	<div class='card shadow mb-5 mb-lg-0'>");
//		strBuff.append("		<img class='card-img-top' data-src='/static/CONTEST/media/contestant/" + contestant.getPict() + "' src='/static/CONTEST/media/contestant/" + contestant.getPict() + "'>");
//		strBuff.append("		<div class='card-body'>");
//		strBuff.append("			<h5 class='card-title'>" + contestant.getSequence() + ". " + contestant.getName() + "</h5>");
//		strBuff.append("  			<table width='100%'>");
//		for(DTOBase eventCriteriaObj: eventCriteriaList) {
//			EventCriteriaDTO eventCriteria = (EventCriteriaDTO) eventCriteriaObj;
//			EventScoreDTO eventScore = getEventScoreByContestantEventCriteria(eventScoreList, contestant, eventCriteria);
//			double score = eventScore == null?0d:eventScore.getScore();
//			strBuff.append("    		<tr>");
//			strBuff.append("				<td>" +	eventCriteria.getName() + "</td>");		
//			strBuff.append("				<td align='center'>" + StringUtil.getFormattedNum(eventCriteria.getScoreMax(), StringUtil.NUMERIC_WHOLE_NUMBER_FORMAT) + "%</td>");
//			strBuff.append("				<td align='center'>");
//			strBuff.append(						new SelectWebControl().getSelectWebControl(null, false, "", contestant.getCode() + eventCriteria.getCode(), StringUtil.getSeries((int)eventCriteria.getScoreMax(), (int)eventCriteria.getScoreMin(), -1), String.valueOf((int)score), StringUtil.getSeries((int)eventCriteria.getScoreMax(), (int)eventCriteria.getScoreMin(), -1), "-", "0", "style='text-align: right; direction: rtl;'"));
//			strBuff.append("				</td>");
//			//strBuff.append("				<td align='right'><input type='text' class='text-right' placeholder='' style='text-align: right;' name='txt" + contestant.getCode() + eventCriteria.getCode() + "' id='txt" + contestant.getCode() + eventCriteria.getCode() + "' value='" + StringUtil.getFormattedNum(score, StringUtil.NUMERIC_STANDARD_FORMAT_NO_COMMA) + "' size='5' onkeypress='return numbersonly(this, event, true, 5)' onclick='this.select();'>%</td>");
//			strBuff.append("    		</tr>");
//		}
//		strBuff.append("   			</table>");
//		strBuff.append("  			<div align='center'>");
//		strBuff.append("				<button class='btn btn-primary' onclick=\"submitScore" + contestant.getCode() + "()\">Submit</button>");
//		strBuff.append("  			</div>");
//		strBuff.append("  		</div>");
//		strBuff.append("  	</div>");
//		strBuff.append("</div>");
//		
//		strBuff.append("<script>");
//		strBuff.append("function submitScore" + contestant.getCode() +"() {");
//		strBuff.append("	$.ajax({");		
//		strBuff.append("		url: 'AjaxController?txtSelectedLink=" + session.getCurrentLink().getCode() +"',");
//		strBuff.append("		data: {");			
//		strBuff.append("			txtAction: 'SUBMIT SCORE',");
//		strBuff.append("			txtContestantCode: '" + contestant.getCode() + "',");
//		for(int i=0; i<eventCriteriaList.size(); i++) {
//			EventCriteriaDTO eventCriteria = (EventCriteriaDTO)eventCriteriaList.get(i);
//		strBuff.append("			cbo" + contestant.getCode() + eventCriteria.getCode() + ": $('#cbo" + contestant.getCode() + eventCriteria.getCode() + "').val()");
//			if(i<eventCriteriaList.size()-1) {
//		strBuff.append(",");
//			}
//		}
//		strBuff.append("		},");		
//		strBuff.append("		method: 'POST',");		
//		strBuff.append("		dataType: 'JSON',");		
//		strBuff.append("		success: function(response) {");	
//		strBuff.append("			$('#divEvent').html(response." + LinkDTO.PAGE_CONTENT + ");");
//		strBuff.append("			if(response.MESSAGE_TYPE === '') {");
//		strBuff.append("				Swal.fire({");
//		strBuff.append("					title: 'SUCCESS',");
//		strBuff.append("					text: 'SUCCESSFULLY SUBMITTED',");
//		strBuff.append("					icon: 'success'");
//		strBuff.append("				});");
//		strBuff.append("			}");
//		strBuff.append("			else {");
//		strBuff.append("				Swal.fire({");
//		strBuff.append("					title: 'ERROR',");
//		strBuff.append("					text: response.MESSAGE_STR,");
//		strBuff.append("					icon: 'error'");
//		strBuff.append("				});");
//		strBuff.append("			}");	
//		strBuff.append("		}");	
//		strBuff.append("	});");
//		strBuff.append("}");
//		strBuff.append("</script>");
//		
//	}
//	strBuff.append("</div>");
//	return strBuff.toString();
//}
	
	//scores are in stars
	public static String getPreliminaryStr(SessionInfo session, List<DTOBase> eventList, EventDTO event, List<DTOBase> contestantList, List<DTOBase> eventScoreList) {
		StringBuffer strBuff = new StringBuffer();
		strBuff.append("<div class='row'>");
		strBuff.append(new SelectWebControl().getSelectWebControl("col-md-4", true, "Event", "Event", eventList, event, "-Select-", "0", "onchange='selectEvent(this.value)'"));
		strBuff.append("</div>");
		if(event.getId() > 0) {
			strBuff.append("<div class='row'>");
			for(DTOBase contestantObj: contestantList) {
				ContestantDTO contestant = (ContestantDTO) contestantObj;
				strBuff.append("<div class='col-md-6 col-lg-4 py-2' id='divContestant" + contestant.getCode() + "' >");
				strBuff.append("	<div class='card shadow mb-5 mb-lg-0'>");
				strBuff.append("		<img class='card-img-top' data-src='/static/CONTEST/media/contestant/" + contestant.getPict() + "' src='/static/CONTEST/media/contestant/" + contestant.getPict() + "'>");
				strBuff.append("		<div class='card-body'>");
				strBuff.append("			<h5 class='card-title'>" + contestant.getSequence() + ". " + contestant.getName() + "</h5>");
				strBuff.append("  			<table width='100%'>");
				for(DTOBase eventCriteriaObj: event.getEventCriteriaList()) {
					EventCriteriaDTO eventCriteria = (EventCriteriaDTO) eventCriteriaObj;
					EventScoreDTO eventScore = getEventScoreByContestantEventCriteria(eventScoreList, contestant, eventCriteria);
					double score = eventScore == null?0d:eventScore.getScore();
					strBuff.append("    		<tr>");
					strBuff.append("				<td align='center'>");
					if(score == 1) {
						strBuff.append("1 STAR");
					}
					else if(score > 1) {
						strBuff.append((int)score + " STARS");
					}
					else {
						strBuff.append("No Rating Yet");
					}
					strBuff.append("<br>");
					for(int i=1; i<=score; i++) {
						strBuff.append("				<i class='fa-solid fa-star fa-2x' style='color:#FFD700; cursor:pointer' aria-hidden='true' onclick=\"submitScore" + contestant.getCode() + "('" + eventCriteria.getCode() + "', " + i + ")\"></i>");
					}
					for(int i=(int) (score+1); i<=eventCriteria.getScoreMax(); i++) {
						strBuff.append("				<i class='fa-solid fa-star fa-2x' style='color:grey; cursor:pointer' aria-hidden='true' onclick=\"submitScore" + contestant.getCode() + "('" + eventCriteria.getCode() + "', " + i + ")\"></i>");
					}
					strBuff.append("				</td>");
					strBuff.append("    		</tr>");
				}
				strBuff.append("   			</table>");
				strBuff.append("  		</div>");
				strBuff.append("  	</div>");
				strBuff.append("</div>");
				
				strBuff.append("<script>");
				strBuff.append("function submitScore" + contestant.getCode() +"(eventCriteriaCode, score) {");
				strBuff.append("	$.ajax({");		
				strBuff.append("		url: 'AjaxController?txtSelectedLink=" + session.getCurrentLink().getCode() +"',");
				strBuff.append("		data: {");			
				strBuff.append("			txtAction: 'SUBMIT SCORE',");
				strBuff.append("			txtContestantCode: '" + contestant.getCode() + "',");
				strBuff.append("			txtEventCriteriaCode: eventCriteriaCode,");
				strBuff.append("			txtScore: score");
				strBuff.append("		},");		
				strBuff.append("		method: 'POST',");		
				strBuff.append("		dataType: 'JSON',");		
				strBuff.append("		success: function(response) {");	
				strBuff.append("			$('#divEvent').html(response." + LinkDTO.PAGE_CONTENT + ");");
				strBuff.append("			if(response.MESSAGE_TYPE === '') {");
				strBuff.append("				toastr.success('Successfully Submitted')");
				strBuff.append("			}");
				strBuff.append("			else {");
				strBuff.append("				toastr.danger(response." + ActionResponse.MESSAGE_STR + ");");
				strBuff.append("			}");	
				strBuff.append("			$('body').scrollTo('#divContestant" + contestant.getCode() + "');");
				strBuff.append("		}");	
				strBuff.append("	});");
				strBuff.append("}");
				strBuff.append("</script>");
				
			}
			strBuff.append("</div>");
		}
		return strBuff.toString();
	}
	
	public static String getFinalistStr(SessionInfo session, List<DTOBase> contestantList, List<DTOBase> eventCriteriaList, List<DTOBase> eventScoreList) {
		StringBuffer strBuff = new StringBuffer();
		
		strBuff.append("<div class='row'>");
		for(DTOBase contestantObj: contestantList) {
			ContestantDTO contestant = (ContestantDTO) contestantObj;
			strBuff.append("<div class='col-md-6 col-lg-4'>");
			strBuff.append("	<div class='card shadow mb-5 mb-lg-0'>");
			strBuff.append("		<img class='card-img-top' data-src='/static/CONTEST/media/contestant/" + contestant.getPict() + "' src='/static/CONTEST/media/contestant/" + contestant.getPict() + "'>");
			strBuff.append("		<div class='card-body'>");
			strBuff.append("			<h5 class='card-title'>" + contestant.getSequence() + ". " + contestant.getName() + "</h5>");
			strBuff.append("  			<table width='100%'>");
			for(DTOBase eventCriteriaObj: eventCriteriaList) {
				EventCriteriaDTO eventCriteria = (EventCriteriaDTO) eventCriteriaObj;
				EventScoreDTO eventScore = getEventScoreByContestantEventCriteria(eventScoreList, contestant, eventCriteria);
				double score = eventScore == null?0d:eventScore.getScore();
				strBuff.append("    		<tr>");
				strBuff.append("				<td align='center'>");
				if(score == 1) {
					strBuff.append("1 STAR");
				}
				else if(score > 1) {
					strBuff.append((int)score + " STARS");
				}
				else {
					strBuff.append("No Rating Yet");
				}
				strBuff.append("<br>");
				for(int i=1; i<=score; i++) {
					strBuff.append("				<i class='fa-solid fa-star fa-2x' style='color:#FFD700; cursor:pointer' aria-hidden='true' onclick=\"submitScore" + contestant.getCode() + "('" + eventCriteria.getCode() + "', " + i + ")\"></i>");
				}
				for(int i=(int) (score+1); i<=eventCriteria.getScoreMax(); i++) {
					strBuff.append("				<i class='fa-solid fa-star fa-2x' style='color:grey; cursor:pointer' aria-hidden='true' onclick=\"submitScore" + contestant.getCode() + "('" + eventCriteria.getCode() + "', " + i + ")\"></i>");
				}
				strBuff.append("				</td>");
				strBuff.append("    		</tr>");
			}
			strBuff.append("   			</table>");
			strBuff.append("  		</div>");
			strBuff.append("  	</div>");
			strBuff.append("</div>");
			
			strBuff.append("<script>");
			strBuff.append("function submitScore" + contestant.getCode() +"(eventCriteriaCode, score) {");
			strBuff.append("	$.ajax({");		
			strBuff.append("		url: 'AjaxController?txtSelectedLink=" + session.getCurrentLink().getCode() +"',");
			strBuff.append("		data: {");			
			strBuff.append("			txtAction: 'SUBMIT SCORE',");
			strBuff.append("			txtContestantCode: '" + contestant.getCode() + "',");
			strBuff.append("			txtEventCriteriaCode: eventCriteriaCode,");
			strBuff.append("			txtScore: score");
			strBuff.append("		},");		
			strBuff.append("		method: 'POST',");		
			strBuff.append("		dataType: 'JSON',");		
			strBuff.append("		success: function(response) {");	
			strBuff.append("			$('#divEvent').html(response." + LinkDTO.PAGE_CONTENT + ");");
			strBuff.append("			if(response.MESSAGE_TYPE === '') {");
			strBuff.append("				toastr.success('Successfully Submitted')");
			strBuff.append("			}");
			strBuff.append("			else {");
			strBuff.append("				toastr.danger(response." + ActionResponse.MESSAGE_STR + ");");
			strBuff.append("			}");	
			strBuff.append("			$('body').scrollTo('#divContestant" + contestant.getCode() + "');");
			strBuff.append("		}");	
			strBuff.append("	});");
			strBuff.append("}");
			strBuff.append("</script>");
			
		}
		strBuff.append("</div>");
		return strBuff.toString();
	}
	

	
	public static List<DTOBase> getEventListByEventTypeCode(List<DTOBase> eventList, String eventTypeCode) {
		List<DTOBase> list = new ArrayList<DTOBase>();
		for(DTOBase eventObj: eventList) {
			EventDTO event = (EventDTO) eventObj;
			if(event.getEventType().getCode().equalsIgnoreCase(eventTypeCode)) {
				list.add(event);
			}
		}
		return list;
	}
	
	private static EventScoreDTO getEventScoreByContestantEventCriteria(List<DTOBase> eventScoreList, ContestantDTO contestant, EventCriteriaDTO eventCriteria) {
		for(DTOBase eventScoreObj: eventScoreList) {
			EventScoreDTO eventScore = (EventScoreDTO) eventScoreObj;
			if(eventScore.getContestant().getCode().equalsIgnoreCase(contestant.getCode()) && eventScore.getEventCriteria().getCode().equalsIgnoreCase(eventCriteria.getCode())) {
				return eventScore;
			}
		}
		return null;
	}
}
