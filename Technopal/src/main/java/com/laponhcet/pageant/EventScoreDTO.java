package com.laponhcet.pageant;

import com.mytechnopal.base.DTOBase;
import com.mytechnopal.dto.UserDTO;

public class EventScoreDTO extends DTOBase {
	private static final long serialVersionUID = 1L;
	
	public static final String SESSION_EVENT_SCORE = "SESSION_EVENT_SCORE";
	public static final String SESSION_EVENT_SCORE_LIST = "SESSION_EVENT_SCORE_LIST";
	
	private UserDTO judge;
	private ContestantDTO contestant;
	private EventDTO event;
	private EventCriteriaDTO eventCriteria;
	private double score;
	
	public EventScoreDTO() {
		super();
		this.judge = new UserDTO();
		this.contestant = new ContestantDTO();
		this.event = new EventDTO();
		this.eventCriteria = new EventCriteriaDTO();
		this.score = 0d;
	}
	
	public EventScoreDTO getEventScore() {
		EventScoreDTO eventScore = new EventScoreDTO();
		eventScore.setId(super.getId());
		eventScore.setJudge(this.judge);
		eventScore.setContestant(this.contestant);
		eventScore.setEvent(this.event);
		eventScore.setEventCriteria(this.eventCriteria);
		eventScore.setScore(this.score);
		return eventScore;
	}

	public UserDTO getJudge() {
		return judge;
	}

	public void setJudge(UserDTO judge) {
		this.judge = judge;
	}

	public ContestantDTO getContestant() {
		return contestant;
	}

	public void setContestant(ContestantDTO contestant) {
		this.contestant = contestant;
	}
	
	public EventDTO getEvent() {
		return event;
	}

	public void setEvent(EventDTO event) {
		this.event = event;
	}

	public EventCriteriaDTO getEventCriteria() {
		return eventCriteria;
	}

	public void setEventCriteria(EventCriteriaDTO eventCriteria) {
		this.eventCriteria = eventCriteria;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}
}
