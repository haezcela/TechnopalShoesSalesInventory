package com.laponhcet.pageant;
import java.util.List;

import com.mytechnopal.base.ActionBase;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.dao.UserDAO;
import com.mytechnopal.util.DTOUtil;

public class Top10ScoreAction extends ActionBase {
	private static final long serialVersionUID = 1L;

	protected void setSessionVars() {
		List<DTOBase> eventList = EventUtil.getEventListByEventTypeCode(new EventDAO().getEventList(), EventTypeDTO.EVENT_TYPE_CODE_TOP10);
		DTOUtil.addObjList(EventUtil.getEventListByEventTypeCode(new EventDAO().getEventList(), EventTypeDTO.EVENT_TYPE_CODE_PREPAGEANT), eventList);
		setSessionAttribute(EventDTO.SESSION_EVENT_LIST, eventList);
		setSessionAttribute(ContestantDTO.SESSION_CONTESTANT_LIST, new ContestantDAO().getContestantList());
		setSessionAttribute(EventCriteriaDTO.SESSION_EVENT_CRITERIA_LIST, new EventCriteriaDAO().getEventCriteriaList());	
		setSessionAttribute(EventScoreDTO.SESSION_EVENT_SCORE_LIST, new EventScoreDAO().getEventScoreList());
		
		List<DTOBase> eventJudgeList = new EventJudgeDAO().getEventJudgeList();
		EventJudgeUtil.setEventJudgeListJudge(eventJudgeList, new UserDAO().getUserList());
		setSessionAttribute(EventJudgeDTO.SESSION_EVENT_JUDGE_LIST, eventJudgeList);
	}
}
