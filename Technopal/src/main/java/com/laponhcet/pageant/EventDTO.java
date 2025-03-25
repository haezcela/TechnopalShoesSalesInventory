package com.laponhcet.pageant;

import java.util.ArrayList;
import java.util.List;

import com.mytechnopal.base.DTOBase;

public class EventDTO extends DTOBase {
	private static final long serialVersionUID = 1L;

	public static final String SESSION_EVENT = "SESSION_EVENT";
	public static final String SESSION_EVENT_LIST = "SESSION_EVENT_LIST";
	public static final String SESSION_EVENT_DATA_TABLE = "SESSION_EVENT_DATA_TABLE";
	public static final String EVENT_TOP10_CODE = "006";
	public static final String EVENT_FINALROUND_CODE = "007";
	
	private EventTypeDTO eventType;
	private String name;
	private String eventTypeCode;
	private double percentageForFinal;
	private String banner;
	private List<DTOBase> eventCriteriaList;
	
	public String getEventTypeCode() {
		return eventTypeCode;
	}

	public void setEventTypeCode(String eventTypeCode) {
		this.eventTypeCode = eventTypeCode;
	}

	public EventDTO() {
		super();
		this.eventType = new EventTypeDTO();
		this.name = "";
		this.percentageForFinal = 0d;
		this.banner = "";
		eventCriteriaList = new ArrayList<DTOBase>();
	}
	
	public EventDTO getEvent() {
		EventDTO event = new EventDTO();
		event.setId(super.getId());
		event.setCode(super.getCode());
		event.setEventType(this.eventType);
		event.setName(this.name);
		event.setPercentageForFinal(this.percentageForFinal);
		event.setBanner(this.banner);
		event.setEventCriteriaList(this.eventCriteriaList);
		return event;
	}

	public EventTypeDTO getEventType() {
		return eventType;
	}

	public void setEventType(EventTypeDTO eventType) {
		this.eventType = eventType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPercentageForFinal() {
		return percentageForFinal;
	}

	public void setPercentageForFinal(double percentageForFinal) {
		this.percentageForFinal = percentageForFinal;
	}

	public String getBanner() {
		return banner;
	}

	public void setBanner(String banner) {
		this.banner = banner;
	}

	public List<DTOBase> getEventCriteriaList() {
		return eventCriteriaList;
	}

	public void setEventCriteriaList(List<DTOBase> eventCriteriaList) {
		this.eventCriteriaList = eventCriteriaList;
	}
}
