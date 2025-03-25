package com.laponhcet.pageant;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.mytechnopal.ActionResponse;
import com.mytechnopal.base.ActionAjaxBase;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.link.LinkDTO;
import com.mytechnopal.util.DTOUtil;

public class Top5ActionAjax extends ActionAjaxBase {
	private static final long serialVersionUID = 1L;

	protected void customAction(JSONObject jsonObj, String action) {
		EventDTO event = (EventDTO) getSessionAttribute(EventDTO.SESSION_EVENT);
		List<DTOBase> contestantList = (List<DTOBase>) getSessionAttribute(ContestantDTO.SESSION_CONTESTANT_LIST);
		List<DTOBase> eventCriteriaList = (List<DTOBase>) getSessionAttribute(EventCriteriaDTO.SESSION_EVENT_CRITERIA_LIST);
		List<DTOBase> eventScoreList = (List<DTOBase>) getSessionAttribute(EventScoreDTO.SESSION_EVENT_SCORE_LIST);
	
		if(action.equalsIgnoreCase("SUBMIT SCORE")) {
			String contestantCode = getRequestString("txtContestantCode");
			ContestantDTO contestant = (ContestantDTO) DTOUtil.getObjByCode(contestantList, contestantCode);
			
			for(DTOBase eventCriteriaObj: eventCriteriaList) {
				EventCriteriaDTO eventCriteria = (EventCriteriaDTO) eventCriteriaObj;
				double score = getRequestDouble("cbo" + contestantCode + eventCriteria.getCode());
				
				if(score < eventCriteria.getScoreMin()) {
					actionResponse.constructMessage(ActionResponse.TYPE_INVALID, "Score for criteria " + eventCriteria.getName() + ".  Score must not be less than " + eventCriteria.getScoreMin() + "%");
				}
				else if(score > eventCriteria.getScoreMax()) {
					actionResponse.constructMessage(ActionResponse.TYPE_INVALID, "Score for criteria " + eventCriteria.getName() + ".  Score must not be greater than " + eventCriteria.getScoreMax() + "%");
				}
				else {
					EventScoreUtil.submitScore(sessionInfo, eventScoreList, contestant, event, eventCriteria, score);
					
					List<DTOBase> eventScoreListByEventCode = EventScoreUtil.getEventScoreListByEventCode(new EventScoreDAO().getEventScoreList(), event.getCode());
					eventScoreList = EventScoreUtil.getEventScoreListByJudgeCode(eventScoreListByEventCode, sessionInfo.getCurrentUser().getCode());
					setSessionAttribute(EventScoreDTO.SESSION_EVENT_SCORE_LIST, eventScoreList);
				}
			}
		}
		
		try {
			jsonObj.put(LinkDTO.PAGE_CONTENT, EventUtil.getFinalistStr(sessionInfo, contestantList, eventCriteriaList, eventScoreList));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	
}
