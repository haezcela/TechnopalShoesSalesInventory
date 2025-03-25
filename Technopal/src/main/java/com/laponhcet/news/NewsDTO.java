package com.laponhcet.news;

import java.util.Date;

import com.mytechnopal.base.DTOBase;

public class NewsDTO extends DTOBase {
	private static final long serialVersionUID = 1L;
	
	public static final String TYPE_NEWS = "NEWS";
	public static final String TYPE_EVENTS = "EVENTS";
	
	public static final String SESSION_NEWS = "SESSION_NEWS";
	public static final String SESSION_NEWS_LIST = "SESSION_NEWS_LIST";
	
	private String type;
	private String headlinePict;
	private String bodyPict;
	private String headline;
	private String body;
	
	private Date eventStart;
	private Date eventEnd;
	private String eventPlace;
	
	public NewsDTO() {
		type = TYPE_NEWS;
		headlinePict = "";
		bodyPict = "";
		headline = "";
		body = "";
		eventStart = null;
		eventEnd = null;
		eventPlace = "";
	}
	
	public NewsDTO getNews() {
		NewsDTO news = new NewsDTO();
		news.setId(super.getId());
		news.setCode(super.getCode());
		news.setType(this.type);
		news.setHeadlinePict(this.headlinePict);
		news.setBodyPict(this.bodyPict);
		news.setHeadline(this.headline);
		news.setBody(this.body);
		news.setEventStart(this.eventStart);
		news.setEventEnd(this.eventEnd);
		news.setEventPlace(this.eventPlace);
		return news;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getHeadlinePict() {
		return headlinePict;
	}

	public void setHeadlinePict(String headlinePict) {
		this.headlinePict = headlinePict;
	}

	public String getBodyPict() {
		return bodyPict;
	}

	public void setBodyPict(String bodyPict) {
		this.bodyPict = bodyPict;
	}

	public String getHeadline() {
		return headline;
	}

	public void setHeadline(String headline) {
		this.headline = headline;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Date getEventStart() {
		return eventStart;
	}

	public void setEventStart(Date eventStart) {
		this.eventStart = eventStart;
	}

	public Date getEventEnd() {
		return eventEnd;
	}

	public void setEventEnd(Date eventEnd) {
		this.eventEnd = eventEnd;
	}

	public String getEventPlace() {
		return eventPlace;
	}

	public void setEventPlace(String eventPlace) {
		this.eventPlace = eventPlace;
	}
}
