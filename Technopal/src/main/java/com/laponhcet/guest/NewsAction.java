package com.laponhcet.guest;

import java.util.List;

import com.laponhcet.news.NewsDTO;
import com.mytechnopal.base.ActionBase;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.util.DTOUtil;

public class NewsAction extends ActionBase {
	private static final long serialVersionUID = 1L;


	protected void setSessionVars() {
		String newsCode = getRequestString("txtSelectedRecord");
		List<DTOBase> newsList = (List<DTOBase>) getSessionAttribute(NewsDTO.SESSION_NEWS_LIST);
		setSessionAttribute(NewsDTO.SESSION_NEWS, DTOUtil.getObjByCode(newsList, newsCode));
	}
}
