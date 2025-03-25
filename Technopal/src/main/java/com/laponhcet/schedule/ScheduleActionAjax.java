package com.laponhcet.schedule;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.laponhcet.academicsection.AcademicSectionDTO;
import com.laponhcet.academicyear.AcademicYearDTO;
import com.laponhcet.curriculum.CurriculumDAO;
import com.laponhcet.curriculum.CurriculumDTO;
import com.laponhcet.semester.SemesterDTO;
import com.laponhcet.subject.SubjectDTO;
import com.laponhcet.teacher.TeacherDTO;
import com.laponhcet.venue.VenueDTO;
import com.mytechnopal.ActionResponse;
import com.mytechnopal.DataTable;
import com.mytechnopal.base.ActionAjaxBase;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.link.LinkDTO;
import com.mytechnopal.util.DTOUtil;
import com.mytechnopal.util.DateTimeUtil;
import com.mytechnopal.util.StringUtil;

public class ScheduleActionAjax extends ActionAjaxBase {
	private static final long serialVersionUID = 1L;

	protected void customAction(JSONObject jsonObj, String action) {
		ScheduleDTO schedule = (ScheduleDTO) getSessionAttribute(ScheduleDTO.SESSION_SCHEDULE);
		List<DTOBase> academicYearList = (List<DTOBase>) getSessionAttribute(AcademicYearDTO.SESSION_ACADEMIC_YEAR_LIST);
		List<DTOBase> semesterList = (List<DTOBase>) getSessionAttribute(SemesterDTO.SESSION_SEMESTER_LIST);
		List<DTOBase> academicSectionList = (List<DTOBase>) getSessionAttribute(AcademicSectionDTO.SESSION_ACADEMIC_SECTION_LIST);
		List<DTOBase> scheduleList = (List<DTOBase>) getSessionAttribute(ScheduleDTO.SESSION_SCHEDULE_LIST);
		List<DTOBase> teacherList = (List<DTOBase>) getSessionAttribute(TeacherDTO.SESSION_TEACHER_LIST);

		if(action.equalsIgnoreCase("SELECT_ACADEMIC_SECTION")) {
			int academicSectionId = getRequestInt("cboAcademicSection");
			if(academicSectionId == 0) {
				schedule.setAcademicSection(new AcademicSectionDTO());
			}
			else {
				if(schedule.getAcademicSection().getId() != academicSectionId) {
					schedule.setAcademicSection((AcademicSectionDTO) DTOUtil.getObjById(academicSectionList, academicSectionId));
					scheduleList = new ArrayList<DTOBase>();
					setSessionAttribute(ScheduleDTO.SESSION_SCHEDULE_LIST, scheduleList);
				}
			}
			try {
				jsonObj.put(LinkDTO.PAGE_CONTENT, ScheduleUtil.getDataEntryStr(sessionInfo, schedule, academicYearList, semesterList, academicSectionList, scheduleList));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		else if(action.equalsIgnoreCase("VIEW_SCHEDULE")) {
			int academicYearId = getRequestInt("cboAcademicYear");
			int semesterId = getRequestInt("cboSemester");
			if(academicYearId >= 1) {
				AcademicYearDTO academicYear = (AcademicYearDTO) DTOUtil.getObjById(academicYearList, academicYearId);
				schedule.getSemester().setAcademicYear(academicYear);
		
				List<DTOBase> scheduleListByAcademicYearCodeAcademicSectionCode = new ScheduleDAO().getScheduleListByAcademicSectionCodeAcademicYearCode(schedule.getAcademicSection().getCode(), schedule.getSemester().getAcademicYear().getCode());
				
				List<DTOBase> curriculumListByAcademicYearCodeAcademicProgramCodeYearLevel = new CurriculumDAO().getCurriculumListByAcademicYearCodeAcademicProgramCodeYearLevel(schedule.getSemester().getAcademicYear().getCode(), schedule.getAcademicSection().getAcademicProgram().getCode(), schedule.getAcademicSection().getYearLevel());
				List<DTOBase> subjectList = (List<DTOBase>) getSessionAttribute(SubjectDTO.SESSION_SUBJECT_LIST);
				List<DTOBase> schedList = new ArrayList<DTOBase>();
				for(DTOBase curriculumObj: curriculumListByAcademicYearCodeAcademicProgramCodeYearLevel) {
					CurriculumDTO curriculum = (CurriculumDTO) curriculumObj;
					ScheduleDTO sched = ScheduleUtil.getScheduleBySubjectCode(scheduleListByAcademicYearCodeAcademicSectionCode, curriculum.getSubject().getCode());
					if(sched == null) {
						sched = new ScheduleDTO();
					}
					else {
						sched.setScheduleDetailsList(new ScheduleDetailsDAO().getScheduleDetailsListByScheduleCode(sched.getCode()));
						for(DTOBase scheduleDetailsObj: sched.getScheduleDetailsList()) {
							ScheduleDetailsDTO scheduleDetails = (ScheduleDetailsDTO) scheduleDetailsObj;
							scheduleDetails.setTeacher((TeacherDTO) DTOUtil.getObjByCode(teacherList, scheduleDetails.getTeacher().getCode()));
						}
					}
					sched.setSemester(schedule.getSemester());
					sched.setSubject((SubjectDTO) DTOUtil.getObjByCode(subjectList, curriculum.getSubject().getCode()));
					sched.setAcademicSection(schedule.getAcademicSection());
					schedList.add(sched);
				}
				
				scheduleList = new ArrayList<DTOBase>();
				DTOUtil.addObjList(schedList, scheduleList);
				setSessionAttribute(ScheduleDTO.SESSION_SCHEDULE_LIST, schedList);
			}
			else {
				if(semesterId >= 1) {
					SemesterDTO semester = (SemesterDTO) DTOUtil.getObjById(semesterList, semesterId);
					schedule.setSemester(semester);
			
					List<DTOBase> scheduleListBySemesterCodeAcademicSectionCode = new ScheduleDAO().getScheduleListByAcademicSectionCodeSemesterCode(schedule.getAcademicSection().getCode(), schedule.getSemester().getCode());
					
					List<DTOBase> curriculumListBySemesterCodeAcademicProgramCodeYearLevel = new CurriculumDAO().getCurriculumListBySemesterCodeAcademicProgramCodeYearLevel(schedule.getSemester().getCode(), schedule.getAcademicSection().getAcademicProgram().getCode(), schedule.getAcademicSection().getYearLevel());
					List<DTOBase> subjectList = (List<DTOBase>) getSessionAttribute(SubjectDTO.SESSION_SUBJECT_LIST);
					List<DTOBase> schedList = new ArrayList<DTOBase>();
					for(DTOBase curriculumObj: curriculumListBySemesterCodeAcademicProgramCodeYearLevel) {
						CurriculumDTO curriculum = (CurriculumDTO) curriculumObj;
						ScheduleDTO sched = ScheduleUtil.getScheduleBySubjectCode(scheduleListBySemesterCodeAcademicSectionCode, curriculum.getSubject().getCode());
						if(sched == null) {
							sched = new ScheduleDTO();
						}
						else {
							sched.setScheduleDetailsList(new ScheduleDetailsDAO().getScheduleDetailsListByScheduleCode(sched.getCode()));
							for(DTOBase scheduleDetailsObj: sched.getScheduleDetailsList()) {
								ScheduleDetailsDTO scheduleDetails = (ScheduleDetailsDTO) scheduleDetailsObj;
								scheduleDetails.setTeacher((TeacherDTO) DTOUtil.getObjByCode(teacherList, scheduleDetails.getTeacher().getCode()));
							}
						}
						sched.setSemester(schedule.getSemester());
						sched.setSubject((SubjectDTO) DTOUtil.getObjByCode(subjectList, curriculum.getSubject().getCode()));
						schedList.add(sched);
					}
					
					scheduleList = new ArrayList<DTOBase>();
					DTOUtil.addObjList(schedList, scheduleList);
					setSessionAttribute(ScheduleDTO.SESSION_SCHEDULE_LIST, schedList);
				}
			}
			try {
				jsonObj.put(LinkDTO.PAGE_CONTENT, ScheduleUtil.getDataEntryStr(sessionInfo, schedule, academicYearList, semesterList, academicSectionList, scheduleList));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		else if(action.equalsIgnoreCase("VIEW_SUBJECT_SCHEDULE")) {
			schedule = ScheduleUtil.getScheduleBySubjectCode(scheduleList,  getRequestString("txtSubjectCode"));
			setSessionAttribute(ScheduleDTO.SESSION_SCHEDULE, schedule);
			try {
				jsonObj.put(LinkDTO.PAGE_CONTENT, ScheduleUtil.getSubjectScheduleStr(schedule));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		else if(action.equalsIgnoreCase("ADD_SCHEDULE_DETAILS")) {
			schedule = (ScheduleDTO) getSessionAttribute(ScheduleDTO.SESSION_SCHEDULE);
			List<DTOBase> venueList = (List<DTOBase>) getSessionAttribute(VenueDTO.SESSION_VENUE_LIST);
			List<DTOBase> teacherLsit = (List<DTOBase>) getSessionAttribute(TeacherDTO.SESSION_TEACHER_LIST);
			String day = getRequestString("txtDay");
			Date startTime = getRequestDateTime("cboStartTime", "hh:mm a");
			Date endTime = getRequestDateTime("cboEndTime", "hh:mm a");
			String venueCode = getRequestString("txtVenueCode");
			String teacherCode = getRequestString("txtTeacherCode");
			
			schedule.getScheduleDetails().setDay(day);
			schedule.getScheduleDetails().setStartTime(startTime);
			schedule.getScheduleDetails().setEndTime(endTime);
			schedule.getScheduleDetails().setVenue((VenueDTO) DTOUtil.getObjByCode(venueList, venueCode));
			schedule.getScheduleDetails().setTeacher((TeacherDTO) DTOUtil.getObjByCode(teacherList, teacherCode));
			
//			System.out.println("day: " + schedule.getScheduleDetails().getDay());
//			System.out.println("startTime: " + schedule.getScheduleDetails().getStartTime());
//			System.out.println("endTime: " + schedule.getScheduleDetails().getEndTime());
//			System.out.println("venueCode: " + schedule.getScheduleDetails().getVenue().getName());
//			System.out.println("teacherCode: " + schedule.getScheduleDetails().getTeacher().getName(false, false, false));
			
			schedule.setCode(ScheduleUtil.getScheduleCode(schedule));
			schedule.setAddedBy(sessionInfo.getCurrentUser().getCode());
			schedule.setAddedTimestamp(DateTimeUtil.getCurrentTimestamp());
			schedule.setBaseDataOnInsert();
			
			ScheduleDAO scheduleDAO = new ScheduleDAO();
			scheduleDAO.executeAdd(schedule);
			actionResponse = (ActionResponse) scheduleDAO.getResult().get(ActionResponse.SESSION_ACTION_RESPONSE);
			if(StringUtil.isEmpty(actionResponse.getType())) {
				
				actionResponse.constructMessage(ActionResponse.TYPE_SUCCESS, "Added");
			}

		}
		else if(action.equalsIgnoreCase("SELECT_TEACHER")) {
			String subjectCode = getRequestString("txtSubjectCode");
			String teacherCode = getRequestString("txtTeacherCode");
			ScheduleDTO scheduleSelected = ScheduleUtil.getScheduleBySubjectCode(scheduleList, subjectCode);
			ScheduleDetailsDTO scheduleDetails = new ScheduleDetailsDTO();
			scheduleDetails.setTeacher((TeacherDTO) DTOUtil.getObjByCode(teacherList, teacherCode));
			scheduleSelected.getScheduleDetailsList().add(scheduleDetails);
			
			ScheduleDAO scheduleDAO = new ScheduleDAO();
			scheduleSelected.setCode(schedule.getSemester().getAcademicYear().getCode() + schedule.getAcademicSection().getCode() + subjectCode);
			scheduleSelected.setAcademicSection(schedule.getAcademicSection());
			scheduleSelected.setSemester(schedule.getSemester());
			scheduleSelected.setAddedBy(sessionInfo.getCurrentUser().getCode());
			scheduleSelected.setAddedTimestamp(DateTimeUtil.getCurrentTimestamp());
			scheduleSelected.setBaseDataOnInsert();
			scheduleDAO.executeAdd(scheduleSelected);
		}
		else if(action.equalsIgnoreCase("DELETE_SCHEDULE_DETAILS")) {
			String scheduleCode = getRequestString("txtScheduleCode");
			int scheduleDetailsId = getRequestInt("txtScheduleDetailsId");

			ScheduleDTO sched = (ScheduleDTO) DTOUtil.getObjByCode(scheduleList, scheduleCode);
			ScheduleDetailsDTO scheduleDetails = (ScheduleDetailsDTO) DTOUtil.getObjById(sched.getScheduleDetailsList() , scheduleDetailsId);
			
			ScheduleDetailsDAO scheduleDetailsDAO = new ScheduleDetailsDAO();
			scheduleDetailsDAO.executeDelete(scheduleDetails);
			actionResponse = (ActionResponse) scheduleDetailsDAO.getResult().get(ActionResponse.SESSION_ACTION_RESPONSE);
			if(StringUtil.isEmpty(actionResponse.getType())) {
				DTOUtil.removeObjById(sched.getScheduleDetailsList(), scheduleDetailsId);
			}
		}
		
	}

	
	protected void executeLogic(JSONObject jsonObj, DataTable dataTable, String action) {	
		if(action.equalsIgnoreCase(DataTable.ACTION_UPDATE_VIEW)) {
			try {
				jsonObj.put(LinkDTO.PAGE_CONTENT, ScheduleUtil.getDataEntryStr(sessionInfo, (ScheduleDTO) getSessionAttribute(ScheduleDTO.SESSION_SCHEDULE), (List<DTOBase>) getSessionAttribute(AcademicYearDTO.SESSION_ACADEMIC_YEAR_LIST), (List<DTOBase>) getSessionAttribute(SemesterDTO.SESSION_SEMESTER_LIST), (List<DTOBase>) getSessionAttribute(AcademicSectionDTO.SESSION_ACADEMIC_SECTION_LIST), (List<DTOBase>) getSessionAttribute(ScheduleDTO.SESSION_SCHEDULE_LIST)));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
}
