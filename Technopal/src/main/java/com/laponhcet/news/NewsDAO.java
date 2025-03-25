package com.laponhcet.news;

import java.sql.ResultSet;
import java.util.List;

import com.mytechnopal.base.DAOBase;
import com.mytechnopal.base.DTOBase;

public class NewsDAO extends DAOBase {

	private static final long serialVersionUID = 1L;

	private String qryNewsByCode = "NEWS_BY_CODE";
	private String qryNewsList = "NEWS_LIST";
	
	@Override
	public void executeAdd(DTOBase arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void executeAddList(List<DTOBase> arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void executeDelete(DTOBase arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void executeDeleteList(List<DTOBase> arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void executeUpdate(DTOBase arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void executeUpdateList(List<DTOBase> arg0) {
		// TODO Auto-generated method stub

	}

	public NewsDTO getNewsByCode(String code) {
		return (NewsDTO) getDTO(qryNewsByCode, code);
	}
	
	public List<DTOBase> getNewsList() {
		return getDTOList(qryNewsList);
	}
	
	@Override
	protected DTOBase rsToObj(ResultSet resultSet) {
		NewsDTO news = new NewsDTO();
		news.setId(getDBValInt(resultSet, "id"));
		news.setCode(getDBValStr(resultSet, "code"));
		news.setType(getDBValStr(resultSet, "type"));
		news.setHeadlinePict(getDBValStr(resultSet, "headline_pict"));
		news.setBodyPict(getDBValStr(resultSet, "body_pict"));
		news.setHeadline(getDBValStr(resultSet, "headline"));
		news.setBody(getDBValStr(resultSet, "body"));
		news.setEventStart(getDBValDate(resultSet, "event_start"));
		news.setEventEnd(getDBValDate(resultSet, "event_end"));
		news.setEventPlace(getDBValStr(resultSet, "event_place"));
		return news;
	}

	public static void main(String[] a) {
		System.out.println(new NewsDAO().getNewsList().size());
	}
}
