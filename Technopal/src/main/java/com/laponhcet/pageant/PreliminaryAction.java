package com.laponhcet.pageant;
import java.util.ArrayList;

import com.mytechnopal.base.ActionBase;
import com.mytechnopal.base.DTOBase;

public class PreliminaryAction extends ActionBase {
	private static final long serialVersionUID = 1L;

	protected void setSessionVars() {
		setSessionAttribute(EventDTO.SESSION_EVENT_LIST, EventUtil.getEventListByEventTypeCode(new EventDAO().getEventList(), EventTypeDTO.EVENT_TYPE_CODE_PRELIMINARY));
		setSessionAttribute(EventDTO.SESSION_EVENT, new EventDTO());
		setSessionAttribute(ContestantDTO.SESSION_CONTESTANT_LIST, new ContestantDAO().getContestantList());
		setSessionAttribute(EventCriteriaDTO.SESSION_EVENT_CRITERIA_LIST, new EventCriteriaDAO().getEventCriteriaList());	
		setSessionAttribute(EventScoreDTO.SESSION_EVENT_SCORE_LIST, new ArrayList<DTOBase>());
	}
}
