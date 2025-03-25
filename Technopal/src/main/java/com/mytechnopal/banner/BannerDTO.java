package com.mytechnopal.banner;

import com.mytechnopal.base.DTOBase;

public class BannerDTO extends DTOBase {
	private static final long serialVersionUID = 1L;

	public static final String SESSION_BANNER = "SESSION_BANNER";
	public static final String SESSION_BANNER_LIST = "SESSION_BANNER_LIST";
	public static final String SESSION_BANNER_DATA_TABLE = "SESSION_BANNER_DATA_TABLE";
	
	public static final String ACTION_SEARCH_BY_FILENAME = "ACTION_SEARCH_BY_FILENAME";
	
	private String label;
	private String description;
	private String filename;
	private int duration; //*1000 for second

	public BannerDTO() {
		super();
		label = "";
		description = "";
		filename = "";
		duration = 3;
	}
	
	public BannerDTO getBanner() {
		BannerDTO banner = new BannerDTO();
		banner.setId(super.getId());
		banner.setCode(String.valueOf(banner.getId()));
		banner.setLabel(this.label);
		banner.setDescription(this.description);
		banner.setFilename(this.filename);
		banner.setDuration(this.duration);
		return banner;
	}
	
	public void display() {
		System.out.println("Label: " + label);
		System.out.println("Description: " + description);
		System.out.println("Filename: " + filename);
		System.out.println("Duration: " + duration);
		System.out.println("Active Start: " + getActiveStartTimestamp());
		System.out.println("Active End: " + getActiveEndTimestamp());
	}
	
	public int getDurationInSeconds() {
		return duration*1000;
	}
	
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}
}
