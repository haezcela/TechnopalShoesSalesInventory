package com.laponhcet.semester;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.laponhcet.academicyear.AcademicYearDTO;
import com.laponhcet.semester.SemesterDTO;
import com.mytechnopal.DataTable;
import com.mytechnopal.SessionInfo;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.util.DTOUtil;
import com.mytechnopal.util.DateTimeUtil;
import com.mytechnopal.webcontrol.ComboBoxWebControl;
import com.mytechnopal.webcontrol.DataTableWebControl;
import com.mytechnopal.webcontrol.TextBoxWebControl;

public class SemesterUtil implements Serializable {
	private static final long serialVersionUID = 1L;

	private static String[][] getDataTableCurrentPageRecordArr(SessionInfo sessionInfo, DataTable dataTable) {
		dataTable.setCurrentPageRecordList();
		String[][] strArr = new String[dataTable.getRecordListCurrentPage().size()][dataTable.getColumnNameArr().length];
		for(int row=0; row < dataTable.getRecordListCurrentPage().size(); row++) {			
			SemesterDTO semester = (SemesterDTO) dataTable.getRecordListCurrentPage().get(row);
			strArr[row][0] = semester.getCode();
			strArr[row][1] = semester.getAcademicYear().getCode();
			strArr[row][2] = Integer.toString(semester.getName());
			strArr[row][3] = DateTimeUtil.getDateTimeToStr(semester.getDateStart(), "MM/dd/yyyy");
			strArr[row][4] = DateTimeUtil.getDateTimeToStr(semester.getDateEnd(), "MM/dd/yyyy");
//			strArr[row][3] = semester.getAcademicYear().getGradingSystem();
			strArr[row][5] = dataTable.getRecordButtonStr(sessionInfo, semester.getCode());
		}
		return strArr;
	}
	
	private static String[][] getDataTableCurrentPageRecordArr(DataTable dataTable) {
		dataTable.setCurrentPageRecordList();
		String[][] strArr = new String[dataTable.getRecordListCurrentPage().size()][dataTable.getColumnNameArr().length];
		for(int row=0; row < dataTable.getRecordListCurrentPage().size(); row++) {			
			SemesterDTO semester = (SemesterDTO) dataTable.getRecordListCurrentPage().get(row);
			strArr[row][0] = semester.getCode();
			strArr[row][1] = semester.getAcademicYear().getCode();
			strArr[row][2] = Integer.toString(semester.getName());
			strArr[row][3] = DateTimeUtil.getDateTimeToStr(semester.getDateStart(), "MM/dd/yyyy");
			strArr[row][4] = DateTimeUtil.getDateTimeToStr(semester.getDateEnd(), "MM/dd/yyyy");
//			strArr[row][3] = semester.getAcademicYear().getGradingSystem();

		}
		return strArr;
	}
	
	public static String getDataTableStr(SessionInfo sessionInfo, DataTable dataTable, boolean isAdmin) {
		DataTableWebControl dtwc = new DataTableWebControl(sessionInfo, dataTable);
		StringBuffer strBuff = new StringBuffer();
		if(dataTable.getRecordList().size() >= 1) {
			if(isAdmin) {
				strBuff.append(dtwc.getDataTableHeader(dataTable, true, true));
				dataTable.setDataTableRecordArr(getDataTableCurrentPageRecordArr(sessionInfo, dataTable));
				strBuff.append(dtwc.getDataTable(true, ""));
			}
			else {
				strBuff.append(dtwc.getDataTableHeader(dataTable, false, false));
				dataTable.setDataTableRecordArr(getDataTableCurrentPageRecordArr(dataTable));
				strBuff.append(dtwc.getDataTable(true, "selectSemester"));
			}
		}
		else {
			strBuff.append("<div class='col-lg-12 label label-warning-light'><h3>No Record Yet To Display</h3></div>");
		}
		return strBuff.toString();
	}
	
	public static void searchSemesterByName(DataTable dataTable, String searchValue) {
		dataTable.setRecordListInvisible();
		for(DTOBase dto: dataTable.getRecordList()) {
			SemesterDTO semester = (SemesterDTO) dto;
//			if(semester.getName().toLowerCase().contains(searchValue.toLowerCase())) {
//				semester.setVisible(true);
//			} 
		}
	}
	
	public static String getDataEntryStr(SessionInfo sessionInfo, SemesterDTO semester, List<DTOBase> academicYearList) {
		StringBuffer strBuff = new StringBuffer();
		strBuff.append(new ComboBoxWebControl().getComboBoxWebControl("col-sm-6", true, "Academic Year Code", "AcademicYearCode", academicYearList, semester.getAcademicYear(), "", "", ""));
		strBuff.append(new ComboBoxWebControl().getComboBoxWebControl("col-sm-6 ", true, "Name", "Name", SemesterDTO.CODE_ARR, Integer.toString(semester.getName()), SemesterDTO.NAME_ARR,  "Select", "0", ""));
		strBuff.append(new TextBoxWebControl().getTextBoxWebControl("col-sm-6", false, "Date Start", "Date Start", "DateStart", DateTimeUtil.getDateTimeToStr(semester.getDateStart(), "MM/dd/yyyy"), 10, TextBoxWebControl.DATA_TYPE_DATE, "", "maxDate: '-24M', changeMonth: true, changeYear: true")); 
		strBuff.append(new TextBoxWebControl().getTextBoxWebControl("col-sm-6", false, "Date End", "Date End", "DateEnd", DateTimeUtil.getDateTimeToStr(semester.getDateEnd(), "MM/dd/yyyy"), 10, TextBoxWebControl.DATA_TYPE_DATE, "", "maxDate: '-24M', changeMonth: true, changeYear: true")); 

		return strBuff.toString();
	}
	
	public static String getDataViewStr(SessionInfo sessionInfo, SemesterDTO semester) {
		StringBuffer strBuff = new StringBuffer();
		strBuff.append("<div class='col-sm-6 no-padding'>Code: " + semester.getCode() + "</div>");
		strBuff.append("<div class='col-sm-6 no-padding'>Academic Year Code: " + semester.getAcademicYear().getCode() + "</div>");
		strBuff.append("<div class='col-sm-6 no-padding'>Name: " + semester.getName() + "</div>");
		strBuff.append("<div class='col-sm-6 no-padding'>Date Start: " + DateTimeUtil.getDateTimeToStr(semester.getDateStart(), "MM/dd/yyyy") + "</div>");
		strBuff.append("<div class='col-sm-6 no-padding'>Date End: " + DateTimeUtil.getDateTimeToStr(semester.getDateEnd(), "MM/dd/yyyy") + "</div>");
		strBuff.append("<div class='col-sm-6 no-padding'>Grading System: " + semester.getAcademicYear().getGradingSystem() + "</div>");
		return strBuff.toString();
	}
	
	public static List<DTOBase> getSemesterListByAcademicYearList( List<DTOBase> semesterList, List<DTOBase> academicYearList) {
		List<DTOBase> resultList = new ArrayList<DTOBase>();
		for(DTOBase ayObj: academicYearList) {
			AcademicYearDTO ay = (AcademicYearDTO) ayObj;
			DTOUtil.addObjList(getSemesterListByAcademicYear(semesterList, ay), resultList);
		}
		return resultList;
	}
	
	public static List<DTOBase> getSemesterListByAcademicYear(List<DTOBase> semesterList, AcademicYearDTO academicYear) {
		List<DTOBase> resultList = new ArrayList<DTOBase>();
		for(DTOBase semesterObj: semesterList) {
			SemesterDTO semester = (SemesterDTO) semesterObj;
			if(semester.getAcademicYear().getCode().equalsIgnoreCase(academicYear.getCode())) {
				semester.setAcademicYear(academicYear);
				semester.setDisplayStr(academicYear.getName() + " [" + SemesterDTO.SEMESTER_DESCRIPTION_LIST[semester.getName()] + "]");
				resultList.add(semester);
			}
		}
		return resultList;
	}
}
