package com.laponhcet.pageant;

import java.util.ArrayList;
import java.util.List;

import com.mytechnopal.base.DTOBase;
import com.mytechnopal.dto.UserDTO;

public class EventInputDTO  extends DTOBase {
	private static final long serialVersionUID = 1L;
	
	public static final String SESSION_EVENT_INPUT = "SESSION_EVENT_INPUT";
	public static final String SESSION_EVENT_INPUT_LIST = "SESSION_EVENT_INPUT_LIST";
	public static final String SESSION_EVENT_INPUT_DATA_TABLE = "SESSION_EVENT_INPUT_DATA_TABLE";
	
	public static final String ACTION_SEARCH_BY_NAME = "ACTION_SEARCH_BY_NAME";
//	

	public static final String SESSION_EVENT = "SESSION_EVENT";
	public static final String SESSION_EVENT_LIST = "SESSION_EVENT_LIST";
	public static final String SESSION_EVENT_DATA_TABLE = "SESSION_EVENT_DATA_TABLE";
	public static final String EVENT_TOP10_CODE = "006";
	public static final String EVENT_FINALROUND_CODE = "007";
	
	private String code;
	private String event_type_code;
//	private EventTypeDTO event_type_code ;
	private String name ;
	private double percentage_for_final ;
	private EventTypeDTO eventType;

	private List<DTOBase> eventCriteriaList;
	
	public double getPercentage_for_final() {
		return percentage_for_final;
	}

	public void setPercentage_for_final(double percentage_for_final) {
		this.percentage_for_final = percentage_for_final;
	}

	public List<DTOBase> getEventCriteriaList() {
		return eventCriteriaList;
	}

	public void setEventCriteriaList(List<DTOBase> eventCriteriaList) {
		this.eventCriteriaList = eventCriteriaList;
	}

	public EventInputDTO() {
		super();
		this.eventType = new EventTypeDTO();
		this.name = "";
		this.percentage_for_final = 0d;
		eventCriteriaList = new ArrayList<DTOBase>();
	}
	
	public EventDTO getEvent() {
		EventDTO event = new EventDTO();
		event.setId(super.getId());
		event.setCode(super.getCode());
		event.setEventType(this.eventType);
		event.setName(this.name);
		event.setPercentageForFinal(this.percentage_for_final);
//		event.setBanner(this.banner);
		event.setEventCriteriaList(this.eventCriteriaList);
		return event;
	}

	public EventTypeDTO getEventType() {
		return eventType;
	}

	public void setEventType(EventTypeDTO eventType) {
		this.eventType = eventType;
	}
	
//	public EventInputDTO() {
//		code = "";
//		event_type_code = "";
////		event_type_code = new EventTypeDTO();
//		name = "";
//		percentage_for_final = (double)1;
//	}

	public EventInputDTO getEventInput() {
		EventInputDTO event = new EventInputDTO();
		event.setId(super.getId());
		event.setCode(this.code);
		event.setEventTypeCode(this.event_type_code);
		event.setName(this.name);
		event.setPercentage(this.percentage_for_final);
		return event;
	}
	
	public EventInputDTO setEventInput() {
		EventInputDTO event = new EventInputDTO();
		event.setId(super.getId());
		event.setCode(this.code);
		event.setEventTypeCode(this.event_type_code);
		event.setName(this.name);
		event.setPercentage(this.percentage_for_final);
		return event;
	}

	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
	this.code = code;
	}
	
	public String getEventTypeCode() {
		return event_type_code ;
		}
		
	public void setEventTypeCode(String event_type_code ) {
	this.event_type_code  = event_type_code ;
	}
	
//	public EventTypeDTO getEventTypeCode() {
//	return event_type_code ;
//	}
//	
//	public void setEventTypeCode(EventTypeDTO event_type_code ) {
//	this.event_type_code  = event_type_code ;
//	}
	
	public String getName() {
	return name;
	}
	
	public void setName(String name) {
	this.name = name;
	}
	
	public double getPercentage() {
	return percentage_for_final;
	}
	
	public void setPercentage(double percentage_for_final) {
	this.percentage_for_final = percentage_for_final;
	} 
	public EventDTO toEventDTO() {
	    EventDTO eventDTO = new EventDTO();
	    eventDTO.setCode(this.getCode());
	    eventDTO.setName(this.getName());
	    eventDTO.setPercentageForFinal(this.percentage_for_final);
	    return eventDTO;
	}

	
}




