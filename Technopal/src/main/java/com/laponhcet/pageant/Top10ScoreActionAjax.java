package com.laponhcet.pageant;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

import com.mytechnopal.ActionResponse;
import com.mytechnopal.base.ActionAjaxBase;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.util.StringUtil;

public class Top10ScoreActionAjax extends ActionAjaxBase {
	private static final long serialVersionUID = 1L;

	protected void customAction(JSONObject jsonObj, String action) {
		List<DTOBase> eventList = (List<DTOBase>) getSessionAttribute(EventDTO.SESSION_EVENT_LIST);
		List<DTOBase> contestantList = (List<DTOBase>) getSessionAttribute(ContestantDTO.SESSION_CONTESTANT_LIST);
		List<DTOBase> eventScoreList = (List<DTOBase>) getSessionAttribute(EventScoreDTO.SESSION_EVENT_SCORE_LIST);
		List<DTOBase> eventJudgeList = (List<DTOBase>) getSessionAttribute(EventJudgeDTO.SESSION_EVENT_JUDGE_LIST);
		
		
		if(action.equalsIgnoreCase("CONFIRM TOP10")) {
			List<DTOBase> list = new ArrayList<DTOBase>();
			List<DTOBase> eventScoreListFinalScore = EventScoreUtil.getEventFinalScoreList(eventList, contestantList, eventScoreList, eventJudgeList);
			new EventScoreSortDescending().sort(eventScoreListFinalScore);
			for(int i=0; i<eventScoreListFinalScore.size(); i++) {
				EventScoreDTO eventScore = (EventScoreDTO) eventScoreListFinalScore.get(i); 
				ContestantDTO contestant = eventScore.getContestant();
				contestant.setRankPreliminary(i+1);
				list.add(contestant);
			}
			
			ContestantDAO contestantDAO = new ContestantDAO();
			contestantDAO.executeContestantListUpdateRankPreliminary(list);
			actionResponse = (ActionResponse) contestantDAO.getResult().get(ActionResponse.SESSION_ACTION_RESPONSE);
			if(StringUtil.isEmpty(actionResponse.getType())) {
				actionResponse.constructMessage(ActionResponse.TYPE_SUCCESS, "Confirmed");
			}

		}
	}
}
