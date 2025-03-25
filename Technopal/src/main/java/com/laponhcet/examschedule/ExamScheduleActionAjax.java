package com.laponhcet.examschedule;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.laponhcet.admissionapplication.AdmissionApplicationDAO;
import com.laponhcet.admissionapplication.AdmissionApplicationDTO;
import com.laponhcet.admissionapplication.AdmissionApplicationEMailDTO;
import com.laponhcet.admissionapplication.AdmissionApplicationUtil;
import com.mytechnopal.ActionResponse;
import com.mytechnopal.DataTable;
import com.mytechnopal.base.ActionAjaxBase;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.link.LinkDTO;
import com.mytechnopal.util.DTOUtil;
import com.mytechnopal.util.DateTimeUtil;
import com.mytechnopal.util.EMailUtil;
import com.mytechnopal.util.PageUtil;
import com.mytechnopal.util.StringUtil;

public class ExamScheduleActionAjax extends ActionAjaxBase {
	private static final long serialVersionUID = 1L;
	
	protected void customAction(JSONObject jsonObj, String action) {
		DataTable dataTable = (DataTable) getSessionAttribute(ExamScheduleDTO.SESSION_EXAMSCHEDULE_DATATABLE);
		List<DTOBase> examScheduleList = (List<DTOBase>) getSessionAttribute(ExamScheduleDTO.SESSION_EXAMSCHEDULE_LIST);
		String scheduleCode = getRequestString("txtScheduleCode");
		ExamScheduleDTO examSchedule = (ExamScheduleDTO) DTOUtil.getObjByCode(examScheduleList, scheduleCode);
		ExamScheduleDAO examScheduleDAO = new ExamScheduleDAO();
		if(action.equalsIgnoreCase(ExamScheduleDTO.ACTION_ADD_EXAMINEE)) {
			examSchedule.setUpdatedBy(sessionInfo.getCurrentUser().getCode());
			examSchedule.setStatus(ExamScheduleDTO.STATUS_SCHEDULED);
			examSchedule.setAdmissionApplicationList(new AdmissionApplicationDAO().getAdmissionApplicationListByStatusLimit(AdmissionApplicationDTO.STATUS_SUBMITTED, examSchedule.getTotalExaminee()));
			examScheduleDAO.executeUpdateStatusSubmitted(examSchedule);
			actionResponse = (ActionResponse) examScheduleDAO.getResult().get(ActionResponse.SESSION_ACTION_RESPONSE);
			if(StringUtil.isEmpty(actionResponse.getType())) {
				actionResponse.constructMessage(ActionResponse.TYPE_SUCCESS, "Updated");
			}
			setSessionAttribute(ExamScheduleDTO.SESSION_EXAMSCHEDULE_LIST, examScheduleDAO.getExamScheduleList());
			setSessionAttribute(AdmissionApplicationDTO.SESSION_ADMISSION_APPLICATION_LIST, new AdmissionApplicationDAO().getAdmissionApplicationList());
			dataTableAction(jsonObj, dataTable);
		}
		else if(action.equalsIgnoreCase(ExamScheduleDTO.ACTION_VIEW_EXAMINEE)) {
			String title = DateTimeUtil.getDateTimeToStr(examSchedule.getDateTimeStart(), "MMM dd, yyyy") + " | " + DateTimeUtil.getDateTimeToStr(examSchedule.getDateTimeStart(), "hh:mm a") + "-" + DateTimeUtil.getDateTimeToStr(examSchedule.getDateTimeEnd(), "hh:mm a") + " | Batch " + examSchedule.getBatch();
			try {
				jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataEntryPage(sessionInfo, title, ExamScheduleUtil.getViewExamineeStr(sessionInfo, examSchedule), new String[] {"printExaminee('" + examSchedule.getCode() + "')"}, new String[] {"Print"}, new String[] {"fa-print"}));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		else if(action.equalsIgnoreCase(ExamScheduleDTO.ACTION_EMAIL_EXAMINEE)) {
			List<DTOBase> admissionApplicationEMailList = new ArrayList<DTOBase>();
			Timestamp dateTime = DateTimeUtil.getCurrentTimestamp();
			for(DTOBase admissionApplicationObj: examSchedule.getAdmissionApplicationList()) {
				AdmissionApplicationDTO admissionApplication = (AdmissionApplicationDTO) admissionApplicationObj;
				
//				String emailContent = DateTimeUtil.getDateTimeToStr(DateTimeUtil.getCurrentTimestamp(), "MMMM dd, yyyy");
//				emailContent += "<br><br>Hi " + admissionApplication.getFirstName() + ",";
//				emailContent += "<br>Good day!";
//				emailContent += "<br><br>This is to inform you that your scheduled date for Bago City College Admission Examination will be on " + DateTimeUtil.getDateTimeToStr(examSchedule.getDateTimeStart(), "MMMM dd, yyyy") + " at " + DateTimeUtil.getDateTimeToStr(examSchedule.getDateTimeStart(), "hh:mm a") + ".";
//				emailContent += "<br>Please be at the venue 30 minutes before your schedule and bring the following requirements:";
//				emailContent += "<ol>";
//				emailContent += "	<li>Printed Online Admission Application Form.  Click <a href='https://admission.bagocitycollege.com/BCCAdmission/ViewAdmissionApplication?txtCode=" + admissionApplication.getCode() + "'>here</a> to view your application form.</li>";
//				emailContent += "	<li>Valid ID</li>";
//				emailContent += "	<li>Black Ballpen</li>";
//				emailContent += "	<li>1 pc. Short White Folder</li>";
//				emailContent += "	<li>1 pc. Short Size Bond Paper</li>";
//				emailContent += "	<li>1 pc. Long Clear Envelope</li>";
//				emailContent += "</ol>";
//				emailContent += "Failure to come on your scheduled date means your chance of taking the admission exam is forfeited.";
//				emailContent += "<br><br><br>Be guided accordingly.";
//
//				boolean isSent = EMailUtil.sendMail("admission@bagocitycollege.com", "cypxuw-hezwof-6cynMi", "smtpout.secureserver.net", "465", admissionApplication.getEmailAddress(), "Bago City College Guidance Center - Entrance Examination Schedule", emailContent);
				
				AdmissionApplicationEMailDTO admissionApplicationEMail = new AdmissionApplicationEMailDTO();
				admissionApplicationEMail.setAdmissionApplicationCode(admissionApplication.getCode());
				admissionApplicationEMail.setSent(true);
				admissionApplicationEMail.setAddedBy(sessionInfo.getCurrentUser().getCode());
				admissionApplicationEMail.setAddedTimestamp(dateTime);
				
				admissionApplicationEMailList.add(admissionApplicationEMail);
			}
			
			examSchedule.setStatus(ExamScheduleDTO.STATUS_EMAILED);
			examSchedule.setUpdatedBy(sessionInfo.getCurrentUser().getCode());
			examSchedule.setUpdatedTimestamp(dateTime);
			examScheduleDAO.executeUpdateStatusEmailed(examSchedule, admissionApplicationEMailList);
			actionResponse = (ActionResponse) examScheduleDAO.getResult().get(ActionResponse.SESSION_ACTION_RESPONSE);
			
			actionResponse.constructMessage(ActionResponse.TYPE_SUCCESS, "Email Queued");
			setSessionAttribute(ExamScheduleDTO.SESSION_EXAMSCHEDULE_LIST, examScheduleDAO.getExamScheduleList());
			setSessionAttribute(AdmissionApplicationDTO.SESSION_ADMISSION_APPLICATION_LIST, new AdmissionApplicationDAO().getAdmissionApplicationList());
			dataTableAction(jsonObj, dataTable);

		}
	}
	
	
	protected void dataTableAction(JSONObject jsonObj, DataTable dataTable) {
		initDataTable(dataTable);
		try {
			jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataTablePage(sessionInfo, ExamScheduleUtil.getDataTableStr(sessionInfo, dataTable)));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	protected void initDataTable(DataTable dataTable) {
		List<DTOBase> examScheduleList = (List<DTOBase>) getSessionAttribute(ExamScheduleDTO.SESSION_EXAMSCHEDULE_LIST);
		List<DTOBase> admissionApplicationList = (List<DTOBase>) getSessionAttribute(AdmissionApplicationDTO.SESSION_ADMISSION_APPLICATION_LIST);
		dataTable.setRecordList(examScheduleList);
		dataTable.setCurrentPageRecordList();
		for(int row=0; row < dataTable.getRecordListCurrentPage().size(); row++) {	
			ExamScheduleDTO examSchedule = (ExamScheduleDTO) dataTable.getRecordListCurrentPage().get(row);
			examSchedule.setAdmissionApplicationList(AdmissionApplicationUtil.getAdmissionApplicationListByExamScheduleCode(admissionApplicationList, examSchedule.getCode()));
		}
	}

}
