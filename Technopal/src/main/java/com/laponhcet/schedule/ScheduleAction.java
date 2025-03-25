package com.laponhcet.schedule;

import java.util.ArrayList;
import java.util.List;

import com.laponhcet.subject.SubjectDAO;
import com.laponhcet.subject.SubjectDTO;
import com.laponhcet.teacher.TeacherDAO;
import com.laponhcet.teacher.TeacherDTO;
import com.laponhcet.teacher.TeacherSortLastNameAscending;
import com.laponhcet.teacher.TeacherUtil;
import com.laponhcet.venue.VenueDAO;
import com.laponhcet.venue.VenueDTO;
import com.mytechnopal.base.ActionBase;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.dto.UserDTO;

public class ScheduleAction extends ActionBase {

	private static final long serialVersionUID = 1L;

	protected void setSessionVars() {
		setSessionAttribute(SubjectDTO.SESSION_SUBJECT_LIST, new SubjectDAO().getSubjectList());
		
		List<DTOBase> teacherList = TeacherUtil.getActiveteacherList(new TeacherDAO().getTeacherList(), (List<DTOBase>) getSessionAttribute(UserDTO.SESSION_USER_LIST));
		new TeacherSortLastNameAscending().sort(teacherList);
		setSessionAttribute(TeacherDTO.SESSION_TEACHER_LIST, teacherList);
		
		setSessionAttribute(VenueDTO.SESSION_VENUE_LIST, new VenueDAO().getVenueList());

		setSessionAttribute(ScheduleDTO.SESSION_SCHEDULE, new ScheduleDTO());
		setSessionAttribute(ScheduleDTO.SESSION_SCHEDULE_LIST, new ArrayList<DTOBase>());
	}
}
