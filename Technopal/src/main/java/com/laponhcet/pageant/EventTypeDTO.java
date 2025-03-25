package com.laponhcet.pageant;

import com.mytechnopal.base.DTOBase;

public class EventTypeDTO extends DTOBase {
	private static final long serialVersionUID = 1L;
	
	public static final String EVENT_TYPE_CODE_PREPAGEANT = "001";
	public static final String EVENT_TYPE_CODE_PRELIMINARY = "002";
	public static final String EVENT_TYPE_CODE_FINAL = "003";
	public static final String EVENT_TYPE_CODE_TOP10 = "T10";
	public static final String EVENT_TYPE_CODE_TOP5 = "T05";
	
	private String name;
	
	public EventTypeDTO() {
		super();
		this.name = "";
	}
	
	public EventTypeDTO getEventType() {
		EventTypeDTO eventType = new EventTypeDTO();
		eventType.setId(super.getId());
		eventType.setCode(super.getCode());
		eventType.setName(this.name);
		return eventType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}