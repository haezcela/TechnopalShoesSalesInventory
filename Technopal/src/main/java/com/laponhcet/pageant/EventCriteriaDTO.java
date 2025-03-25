package com.laponhcet.pageant;


import com.laponhcet.enrollment.EnrollmentDTO;
import com.mytechnopal.base.DTOBase;

public class EventCriteriaDTO extends DTOBase {
	private static final long serialVersionUID = 1L;

	public static final String SESSION_EVENT_CRITERIA = "SESSION_EVENT_CRITERIA";
	public static final String SESSION_EVENT_CRITERIA_LIST = "SESSION_EVENT_CRITERIA_LIST"; 
	public static final String SESSION_EVENT_CRITERIA_DATA_TABLE = "SESSION_EVENT_CRITERIA_DATA_TABLE"; 
	private EventDTO event;
	private String name;
	private String eventName;
	private String eventCode;
	private String nameShort;
	private double scoreMin;
	private int id;
	private double scoreMax;
	
	public EventCriteriaDTO() {
		super();
		event = new EventDTO();
		name = "";
		nameShort = "";
		scoreMin = 0d;
		scoreMax = 0d;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public EventCriteriaDTO getEventCriteria() {
		EventCriteriaDTO eventCriteria = new EventCriteriaDTO();
		eventCriteria.setId(super.getId());
		eventCriteria.setEvent(this.event);
		eventCriteria.setName(this.name);
		eventCriteria.setNameShort(this.nameShort);
		eventCriteria.setScoreMin(this.scoreMin);
		eventCriteria.setScoreMax(this.scoreMax);
		return eventCriteria;
	}
	public String getEventCode() {
		return eventCode;
	}

	public void setEventCode(String eventCode) {
		this.eventCode = eventCode;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public EventDTO getEvent() {
		return event;
	}

	public void setEvent(EventDTO event) {
		this.event = event;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNameShort() {
		return nameShort;
	}

	public void setNameShort(String nameShort) {
		this.nameShort = nameShort;
	}

	public double getScoreMin() {
		return scoreMin;
	}

	public void setScoreMin(double scoreMin) {
		this.scoreMin = scoreMin;
	}

	public double getScoreMax() {
		return scoreMax;
	}

	public void setScoreMax(double scoreMax) {
		this.scoreMax = scoreMax;
	}
}
