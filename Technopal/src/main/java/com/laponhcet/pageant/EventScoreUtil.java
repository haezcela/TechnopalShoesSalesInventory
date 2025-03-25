package com.laponhcet.pageant;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.mytechnopal.SessionInfo;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.util.DateTimeUtil;

public class EventScoreUtil implements Serializable {
	private static final long serialVersionUID = 1L;

	public static List<DTOBase> getEventScoreListByEventCode(List<DTOBase> eventScoreList, String eventCode) {
		List<DTOBase> list = new ArrayList<DTOBase>();
		
		for(DTOBase eventScoreObj: eventScoreList) {
			EventScoreDTO eventScore = (EventScoreDTO) eventScoreObj;
			if(eventScore.getEvent().getCode().equalsIgnoreCase(eventCode)) {
				list.add(eventScore);
			}
		}
		return list;
	}
	
	public static List<DTOBase> getEventScoreListByJudgeCode(List<DTOBase> eventScoreList, String judgeCode) {
		List<DTOBase> list = new ArrayList<DTOBase>();
		
		for(DTOBase eventScoreObj: eventScoreList) {
			EventScoreDTO eventScore = (EventScoreDTO) eventScoreObj;
			if(eventScore.getJudge().getCode().equalsIgnoreCase(judgeCode)) {
				list.add(eventScore);
			}
		}
		return list;
	}
	
	public static List<DTOBase> getEventScoreListByContestantCode(List<DTOBase> eventScoreList, String contestantCode) {
		List<DTOBase> list = new ArrayList<DTOBase>();
		
		for(DTOBase eventScoreObj: eventScoreList) {
			EventScoreDTO eventScore = (EventScoreDTO) eventScoreObj;
			if(eventScore.getContestant().getCode().equalsIgnoreCase(contestantCode)) {
				list.add(eventScore);
			}
		}
		return list;
	}
	
	public static EventScoreDTO getEventScoreByJudgeCodeEventCriteriaCodeContestantCode(List<DTOBase> eventScoreList, String judgeCode, String eventCriteriaCode, String contestantCode) {
		for(DTOBase eventScoreObj: eventScoreList) {
			EventScoreDTO eventScore = (EventScoreDTO) eventScoreObj;
			if(eventScore.getJudge().getCode().equalsIgnoreCase(judgeCode) && eventScore.getEventCriteria().getCode().equalsIgnoreCase(eventCriteriaCode) && eventScore.getContestant().getCode().equalsIgnoreCase(contestantCode)) {
				return eventScore;
			}
		}
		return null;
	}
	
	public static double getTotalEventScoreList(List<DTOBase> eventScoreList) {
		double total = 0d;
		for(DTOBase eventScoreObj: eventScoreList) {
			EventScoreDTO eventScore = (EventScoreDTO) eventScoreObj;
			total += eventScore.getScore();
		}
		return total;
	}
	
	
	public static List<DTOBase> getEventFinalScoreList(EventDTO event, List<DTOBase> contestantList, List<DTOBase> eventScoreList, EventJudgeDTO eventJudge) {
		List<DTOBase> list = new ArrayList<DTOBase>();
		List<DTOBase> eventScoreListByEventCode = getEventScoreListByEventCode(eventScoreList, event.getCode());
		
		for(DTOBase contestantObj: contestantList) {
			ContestantDTO contestant = (ContestantDTO) contestantObj;
			List<DTOBase> eventScoreListByContestantCode = getEventScoreListByContestantCode(eventScoreListByEventCode, contestant.getCode());
			List<DTOBase> eventScoreListByJudgeCode = getEventScoreListByJudgeCode(eventScoreListByContestantCode, eventJudge.getJudge().getCode());
			double score = getTotalEventScoreList(eventScoreListByJudgeCode);
			EventScoreDTO eventScore = new EventScoreDTO();
			eventScore.setEvent(event);
			eventScore.setContestant(contestant);
			eventScore.setScore(score);
			list.add(eventScore);
		}
		return list;
	}
	
	
	public static List<DTOBase> getEventFinalScoreList(EventDTO event, List<DTOBase> contestantList, List<DTOBase> eventScoreList, List<DTOBase> eventJudgeList) {
		List<DTOBase> list = new ArrayList<DTOBase>();
		List<DTOBase> eventScoreListByEventCode = getEventScoreListByEventCode(eventScoreList, event.getCode());
		List<DTOBase> eventJudgeListByEventCode = EventJudgeUtil.getEventJudgeListByEventCode(eventJudgeList, event.getCode());
		
		for(DTOBase contestantObj: contestantList) {
			ContestantDTO contestant = (ContestantDTO) contestantObj;
			List<DTOBase> eventScoreListByContestantCode = getEventScoreListByContestantCode(eventScoreListByEventCode, contestant.getCode());
			double score = getTotalEventScoreList(eventScoreListByContestantCode);
			EventScoreDTO eventScore = new EventScoreDTO();
			eventScore.setEvent(event);
			eventScore.setContestant(contestant);
			eventScore.setScore(score/eventJudgeListByEventCode.size());
			list.add(eventScore);
		}
		return list;
	}
	
	
	public static List<DTOBase> getEventFinalScoreList(List<DTOBase> eventList, List<DTOBase> contestantList, List<DTOBase> eventScoreList, List<DTOBase> eventJudgeList) {
		List<DTOBase> list = new ArrayList<DTOBase>();		
		for(DTOBase contestantObj: contestantList) {
			ContestantDTO contestant = (ContestantDTO) contestantObj;
			List<DTOBase> eventScoreListByContestantCode = getEventScoreListByContestantCode(eventScoreList, contestant.getCode());
			double score = 0d;
			for(DTOBase eventObj: eventList) {
				EventDTO event = (EventDTO) eventObj;
				List<DTOBase> eventScoreListByEventCode = getEventScoreListByEventCode(eventScoreListByContestantCode, event.getCode());
				List<DTOBase> eventJudgeListByEventCode = EventJudgeUtil.getEventJudgeListByEventCode(eventJudgeList, event.getCode());
				score += (getTotalEventScoreList(eventScoreListByEventCode)/eventJudgeListByEventCode.size()) * (event.getPercentageForFinal()/100);
			}	
			EventScoreDTO eventScore = new EventScoreDTO();
			eventScore.setContestant(contestant);
			eventScore.setScore(score);
			list.add(eventScore);
		}
		return list;
	}
	
	
	public static void submitScore(SessionInfo sessionInfo, List<DTOBase> eventScoreList, ContestantDTO contestant, EventDTO event, EventCriteriaDTO eventCriteria, double score) {
		EventScoreDTO eventScore = null;
		for(DTOBase eventScoreObj: eventScoreList) {
			EventScoreDTO es = (EventScoreDTO) eventScoreObj;
			if(es.getContestant().getCode().equalsIgnoreCase(contestant.getCode()) && es.getEventCriteria().getCode().equalsIgnoreCase(eventCriteria.getCode())) {
				es.setScore(score);
				eventScore = es;
				eventScore.setUpdatedTimestamp(DateTimeUtil.getCurrentTimestamp());
				eventScore.setRecordStatus(DTOBase.RECORD_STATUS_FOR_UPDATE);
				break;
			}
		}
		
		if(eventScore == null) {
			eventScore = new EventScoreDTO();
			eventScore.setRecordStatus(DTOBase.RECORD_STATUS_FOR_ADDITION);
			eventScore.getJudge().setCode(sessionInfo.getCurrentUser().getCode());
			eventScore.setContestant(contestant);
			eventScore.setEvent(event);
			eventScore.setEventCriteria(eventCriteria);
			eventScore.setScore(score);
			eventScore.setAddedTimestamp(DateTimeUtil.getCurrentTimestamp());
			eventScoreList.add(eventScore);
		}
		EventScoreDAO eventScoreDAO = new EventScoreDAO();
		eventScoreDAO.executeSubmitScore(eventScore);
	}
	
	public static void main(String[] a) {
		EventJudgeDTO eventJudge = new EventJudgeDTO();
		eventJudge.getJudge().setCode("000000000002");
		List<DTOBase> eventScoreListFinalScore = getEventFinalScoreList(new EventDAO().getEventByCode("003"), new ContestantDAO().getContestantList(), new EventScoreDAO().getEventScoreList(), eventJudge);
		new EventScoreSortDescending().sort(eventScoreListFinalScore);
		for(DTOBase eventScoreObj: eventScoreListFinalScore) {
			EventScoreDTO eventScore = (EventScoreDTO) eventScoreObj;
			System.out.println(eventScore.getContestant().getName() + " " + eventScore.getScore());
		}
	}
	
}
