package com.laponhcet.pageant;
import java.util.List;

import com.mytechnopal.base.ActionBase;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.dao.UserDAO;

public class Top5ScoreAction extends ActionBase {
	private static final long serialVersionUID = 1L;

	protected void setSessionVars() {
		setSessionAttribute(EventDTO.SESSION_EVENT_LIST, EventUtil.getEventListByEventTypeCode(new EventDAO().getEventList(), EventTypeDTO.EVENT_TYPE_CODE_TOP5));
		
		setSessionAttribute(ContestantDTO.SESSION_CONTESTANT_LIST, ContestantUtil.getContestantListTop(new ContestantDAO().getContestantList(), 10));
		setSessionAttribute(EventCriteriaDTO.SESSION_EVENT_CRITERIA_LIST, EventCriteriaUtil.getEventCriteriaListByEventCode(new EventCriteriaDAO().getEventCriteriaList(), EventDTO.EVENT_TOP10_CODE));	
		setSessionAttribute(EventScoreDTO.SESSION_EVENT_SCORE_LIST, EventScoreUtil.getEventScoreListByEventCode(new EventScoreDAO().getEventScoreList(), EventDTO.EVENT_TOP10_CODE));
		
		List<DTOBase> eventJudgeList = EventJudgeUtil.getEventJudgeListByEventCode(new EventJudgeDAO().getEventJudgeList(), EventDTO.EVENT_TOP10_CODE);
		EventJudgeUtil.setEventJudgeListJudge(eventJudgeList, new UserDAO().getUserList());
		setSessionAttribute(EventJudgeDTO.SESSION_EVENT_JUDGE_LIST, eventJudgeList);
	}
}