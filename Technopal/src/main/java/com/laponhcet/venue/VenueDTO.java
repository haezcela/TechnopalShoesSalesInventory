package com.laponhcet.venue;

import com.mytechnopal.base.DTOBase;

public class VenueDTO extends DTOBase {
	private static final long serialVersionUID = 1L;

	public static final String SESSION_VENUE = "SESSION_VENUE";
	public static final String SESSION_VENUE_LIST = "SESSION_VENUE_LIST";
	public static final String SESSION_VENUE_DATA_TABLE = "SESSION_VENUE_DATA_TABLE";
		
	private String name;
	private String location;
	private int concurrentSession;
	private int maxPax; //maximum number of persons can accommodate

	public VenueDTO() {
		super();
		name = "";
		location = "";
		concurrentSession = 1;
		maxPax = 999;
	}
	
	public VenueDTO getVenue() {
		VenueDTO venue = new VenueDTO();
		venue.setId(super.getId());
		venue.setCode(super.getCode());
		venue.setName(this.name);
		venue.setLocation(this.location);
		venue.setConcurrentSession(this.concurrentSession);
		venue.setMaxPax(this.maxPax);
		venue.setDisplayStr(getName());
		return venue;
	}
	
	public String getName() {
		if(isTBA()) {
			return "TBA";
		}
		else {
			return name;
		}
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getLocation() {
		return location;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}

	public int getConcurrentSession() {
		return concurrentSession;
	}

	public void setConcurrentSession(int concurrentSession) {
		this.concurrentSession = concurrentSession;
	}

	public int getMaxPax() {
		return maxPax;
	}

	public void setMaxPax(int maxPax) {
		this.maxPax = maxPax;
	}
}
