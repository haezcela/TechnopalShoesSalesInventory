package com.laponhcet.examschedule;

import java.util.List;

import com.laponhcet.admissionapplication.AdmissionApplicationDAO;
import com.laponhcet.admissionapplication.AdmissionApplicationDTO;
import com.mytechnopal.DataTable;
import com.mytechnopal.base.ActionBase;
import com.mytechnopal.base.DTOBase;

public class ExamScheduleAction extends ActionBase {
	private static final long serialVersionUID = 1L;

	protected void setSessionVars() {
		List<DTOBase> examScheduleList = new ExamScheduleDAO().getExamScheduleList();
		DataTable dataTable = new DataTable(ExamScheduleDTO.SESSION_EXAMSCHEDULE_DATATABLE, examScheduleList);
		dataTable.setColumnNameArr(new String[] {"Batch", "Date/Time Start", "Date/Time End", "Status", "Total Examinee", "", ""});
		dataTable.setColumnWidthArr(new String[] {"10", "20", "20", "20", "10", "10", "10"});
		setSessionAttribute(ExamScheduleDTO.SESSION_EXAMSCHEDULE_DATATABLE, dataTable);
		setSessionAttribute(ExamScheduleDTO.SESSION_EXAMSCHEDULE_LIST, examScheduleList);		
		setSessionAttribute(AdmissionApplicationDTO.SESSION_ADMISSION_APPLICATION_LIST, new AdmissionApplicationDAO().getAdmissionApplicationList());
	}
}