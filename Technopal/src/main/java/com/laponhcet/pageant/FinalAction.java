package com.laponhcet.pageant;
import java.util.List;

import com.mytechnopal.base.ActionBase;
import com.mytechnopal.base.DTOBase;

public class FinalAction extends ActionBase {
	private static final long serialVersionUID = 1L;

	protected void setSessionVars() {
		setSessionAttribute(EventDTO.SESSION_EVENT, new EventDAO().getEventByCode(EventDTO.EVENT_FINALROUND_CODE));		
		setSessionAttribute(ContestantDTO.SESSION_CONTESTANT_LIST, ContestantUtil.getContestantListTop(new ContestantDAO().getContestantList(), 3));
		setSessionAttribute(EventCriteriaDTO.SESSION_EVENT_CRITERIA_LIST, EventCriteriaUtil.getEventCriteriaListByEventCode(new EventCriteriaDAO().getEventCriteriaList(), EventDTO.EVENT_FINALROUND_CODE));	
		
		List<DTOBase> eventScoreListByEventCode = EventScoreUtil.getEventScoreListByEventCode(new EventScoreDAO().getEventScoreList(),EventDTO.EVENT_FINALROUND_CODE);
		List<DTOBase> eventScoreList = EventScoreUtil.getEventScoreListByJudgeCode(eventScoreListByEventCode, sessionInfo.getCurrentUser().getCode());
		setSessionAttribute(EventScoreDTO.SESSION_EVENT_SCORE_LIST, eventScoreList);
	}
}
