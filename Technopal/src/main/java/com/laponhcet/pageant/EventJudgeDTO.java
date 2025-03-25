package com.laponhcet.pageant;

import com.mytechnopal.base.DTOBase;
import com.mytechnopal.dto.UserDTO;

public class EventJudgeDTO extends DTOBase {
	private static final long serialVersionUID = 1L;
	
	public static final String SESSION_EVENT_JUDGE = "SESSION_EVENT_JUDGE";
	public static final String SESSION_EVENT_JUDGE_LIST = "SESSION_EVENT_JUDGE_LIST";
	public static final String SESSION_EVENT_JUDGE_DATA_TABLE = "SESSION_EVENT_JUDGE_DATA_TABLE";
	private UserDTO judge;
	private EventDTO event;
	private String userCode;
	private String eventCode;
	
	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getEventCode() {
		return eventCode;
	}

	public void setEventCode(String eventCode) {
		this.eventCode = eventCode;
	}
	

	public EventJudgeDTO() {
		super();
		judge = new UserDTO();
		event = new EventDTO();
	}
	
	public EventJudgeDTO getEventJudge() {
		EventJudgeDTO eventJudge = new EventJudgeDTO();
		eventJudge.setId(super.getId());
		eventJudge.setJudge(this.judge);
		eventJudge.setEvent(this.event);
		return eventJudge;
	}
	public UserDTO getJudge() {
		return judge;
	}

	public void setJudge(UserDTO judge) {
		this.judge = judge;
	}

	public EventDTO getEvent() {
		return event;
	}

	public void setEvent(EventDTO event) {
		this.event = event;
	}
} 