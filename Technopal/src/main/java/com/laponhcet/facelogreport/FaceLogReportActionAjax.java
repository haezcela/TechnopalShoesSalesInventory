package com.laponhcet.facelogreport;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.laponhcet.enrollment.EnrollmentDTO;
import com.mytechnopal.Calendar;
import com.mytechnopal.base.ActionAjaxBase;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.facelog.FaceLogDAO;
import com.mytechnopal.link.LinkDTO;
import com.mytechnopal.util.DateTimeUtil;

public class FaceLogReportActionAjax extends ActionAjaxBase {
static final long serialVersionUID = 1L;

	protected void customAction(JSONObject jsonObj, String action) {
		Calendar calendar = (Calendar) getSessionAttribute(Calendar.SESSION_CALENDAR);
		List<DTOBase> enrollmentList = (List<DTOBase>) getSessionAttribute(EnrollmentDTO.SESSION_ENROLLMENT_LIST);
		List<DTOBase> faceLogList = new ArrayList<DTOBase>();
		if(action.equalsIgnoreCase("VIEW FACE LOG LIST")) {
			faceLogList = new FaceLogDAO().getFaceLogListByTimeLogDate(DateTimeUtil.getDateTimeToStr(calendar.getDate(), "yyyy-MM-dd"));
		}
		else if(action.equalsIgnoreCase("SELECT CALENDAR DAY OF THE MONTH")) {
			calendar.setDate(DateTimeUtil.getDateBySettingDate(calendar.getDate(), getRequestInt("txtDayOfTheMonth")));
			faceLogList = new FaceLogDAO().getFaceLogListByTimeLogDate(DateTimeUtil.getDateTimeToStr(calendar.getDate(), "yyyy-MM-dd"));
		}
		else if(action.equalsIgnoreCase("SELECT CALENDAR MONTH")) {
			calendar.setDate(DateTimeUtil.getDateBySettingMonth(calendar.getDate(), getRequestInt("txtMonth")));
			faceLogList = new FaceLogDAO().getFaceLogListByTimeLogDateFromTo(DateTimeUtil.getDateTimeToStr(DateTimeUtil.getFirstDate(calendar.getDate()), "yyyy-MM-dd"), DateTimeUtil.getDateTimeToStr(DateTimeUtil.getLastDate(calendar.getDate()), "yyyy-MM-dd"));
		}
		else if(action.equalsIgnoreCase("SELECT CALENDAR YEAR")) {
			calendar.setDate(DateTimeUtil.getDateBySettingYear(calendar.getDate(), getRequestInt("txtYear")));			
			faceLogList = new FaceLogDAO().getFaceLogListByTimeLogDateFromTo(DateTimeUtil.getDateTimeToStr(DateTimeUtil.getFirstDate(calendar.getDate()), "yyyy-MM-dd"), DateTimeUtil.getDateTimeToStr(DateTimeUtil.getLastDate(calendar.getDate()), "yyyy-MM-dd"));
		}
		
		try {
			jsonObj.put(LinkDTO.PAGE_CONTENT, FaceLogReportUtil.getFaceLogListByTimeLogDate(sessionInfo, calendar, faceLogList, enrollmentList));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
