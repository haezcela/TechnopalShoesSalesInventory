package com.laponhcet.academicyear;

import java.io.Serializable;
import java.util.List;

import com.laponhcet.academicyear.AcademicYearDTO;
import com.laponhcet.semester.SemesterDTO;
import com.mytechnopal.ActionResponse;
import com.mytechnopal.DataTable;
import com.mytechnopal.SessionInfo;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.base.WebControlBase;
import com.mytechnopal.util.DTOUtil;
import com.mytechnopal.util.DateTimeUtil;
import com.mytechnopal.webcontrol.DataTableWebControl;
import com.mytechnopal.webcontrol.RadioButtonWebControl;
import com.mytechnopal.webcontrol.TextBoxWebControl;

public class AcademicYearUtil implements Serializable {
	private static final long serialVersionUID = 1L;

	private static String[][] getDataTableCurrentPageRecordArr(SessionInfo sessionInfo, DataTable dataTable, List<DTOBase> academicProgramGroupList) {
		dataTable.setCurrentPageRecordList();
		String[][] strArr = new String[dataTable.getRecordListCurrentPage().size()][dataTable.getColumnNameArr().length];
		for(int row=0; row < dataTable.getRecordListCurrentPage().size(); row++) {			
			AcademicYearDTO academicYear = (AcademicYearDTO) dataTable.getRecordListCurrentPage().get(row);
			AcademicProgramGroupDTO academicProgramGroup = (AcademicProgramGroupDTO) DTOUtil.getObjByCode(academicProgramGroupList, academicYear.getAcademicProgramGroupCodes());
			strArr[row][0] = academicYear.getCode();
			strArr[row][1] = academicYear.getName();
			strArr[row][2] = academicYear.getRemarks();
			strArr[row][3] = DateTimeUtil.getDateTimeToStr(academicYear.getDateStart(), "MM/dd/yyyy");
			strArr[row][4] = DateTimeUtil.getDateTimeToStr(academicYear.getDateEnd(), "MM/dd/yyyy");
			strArr[row][5] = academicProgramGroup.getName();
			strArr[row][6] = academicYear.getGradingSystem();
			strArr[row][7] = dataTable.getRecordButtonStr(sessionInfo, academicYear.getCode());
		}
		return strArr;
	}
	
	private static String[][] getDataTableCurrentPageRecordArr(DataTable dataTable) {
		dataTable.setCurrentPageRecordList();
		String[][] strArr = new String[dataTable.getRecordListCurrentPage().size()][dataTable.getColumnNameArr().length];
		for(int row=0; row < dataTable.getRecordListCurrentPage().size(); row++) {			
			AcademicYearDTO academicYear = (AcademicYearDTO) dataTable.getRecordListCurrentPage().get(row);
			strArr[row][0] = academicYear.getCode();
			strArr[row][1] = academicYear.getName();
			strArr[row][2] = academicYear.getRemarks();
			strArr[row][3] = DateTimeUtil.getDateTimeToStr(academicYear.getDateStart(), "MM/dd/yyyy");
			strArr[row][4] = DateTimeUtil.getDateTimeToStr(academicYear.getDateEnd(), "MM/dd/yyyy");
			strArr[row][5] = academicYear.getAcademicProgramGroupCodes();
			strArr[row][6] = academicYear.getGradingSystem();
		}
		return strArr;
	}
	
	public static String getDataTableStr(SessionInfo sessionInfo, DataTable dataTable, boolean isAdmin, List<DTOBase> academicProgramGroupList) {
		DataTableWebControl dtwc = new DataTableWebControl(sessionInfo, dataTable);
		StringBuffer strBuff = new StringBuffer();
		if(dataTable.getRecordList().size() >= 1) {
			if(isAdmin) {
				strBuff.append(dtwc.getDataTableHeader(dataTable, true, true));
				dataTable.setDataTableRecordArr(getDataTableCurrentPageRecordArr(sessionInfo, dataTable, academicProgramGroupList));
				strBuff.append(dtwc.getDataTable(true, ""));
			}
			else {
				strBuff.append(dtwc.getDataTableHeader(dataTable, false, false));
				dataTable.setDataTableRecordArr(getDataTableCurrentPageRecordArr(dataTable));
				strBuff.append(dtwc.getDataTable(true, "selectAcademicYear"));
			}
		}
		else {
			strBuff.append("<div class='col-lg-12 label label-warning-light'><h3>No Record Yet To Display</h3></div>");
		}
		return strBuff.toString();
	}
	
	public static void searchAcademicYearByName(DataTable dataTable, String searchValue) {
		dataTable.setRecordListInvisible();
		for(DTOBase dto: dataTable.getRecordList()) {
			AcademicYearDTO academicYear = (AcademicYearDTO) dto;
			if(academicYear.getName().toLowerCase().contains(searchValue.toLowerCase())) {
				academicYear.setVisible(true);
			} 
		}
	}
	
	public static String getDataEntryStr(SessionInfo sessionInfo, AcademicYearDTO academicYear, List<DTOBase> academicProgramGroupCodesList) {
		StringBuffer strBuff = new StringBuffer();
		strBuff.append(new TextBoxWebControl().getTextBoxWebControl("col-sm-6", true, "Name", "Name", "Name", academicYear.getName(), 180, WebControlBase.DATA_TYPE_STRING, "")); 
		strBuff.append(new TextBoxWebControl().getTextBoxWebControl("col-sm-6", true, "Remarks", "Remarks", "Remarks", academicYear.getRemarks(), 180, WebControlBase.DATA_TYPE_STRING, "")); 
		strBuff.append(new TextBoxWebControl().getTextBoxWebControl("col-sm-6", false, "Date Start", "Date Start", "DateStart", DateTimeUtil.getDateTimeToStr(academicYear.getDateStart(), "MM/dd/yyyy"), 10, TextBoxWebControl.DATA_TYPE_DATE, "", "maxDate: '-24M', changeMonth: true, changeYear: true")); 
		strBuff.append(new TextBoxWebControl().getTextBoxWebControl("col-sm-6", false, "Date End", "Date End", "DateEnd", DateTimeUtil.getDateTimeToStr(academicYear.getDateEnd(), "MM/dd/yyyy"), 10, TextBoxWebControl.DATA_TYPE_DATE, "", "maxDate: '-24M', changeMonth: true, changeYear: true")); 
		strBuff.append(new RadioButtonWebControl().getRadioButtonWebControl("col-sm-6", true	, "Academic Program Group Codes",  "AcademicProgramGroupCodes", DTOUtil.getCodeArrFromObjList(academicProgramGroupCodesList), academicYear.getAcademicProgramGroupCodes(), DTOUtil.getStrArrFromObjList(academicProgramGroupCodesList), true, ""));
		strBuff.append(new TextBoxWebControl().getTextBoxWebControl("col-sm-6", true, "Grading System", "Grading System", "GradingSystem", academicYear.getGradingSystem(), 180, WebControlBase.DATA_TYPE_STRING, "")); 
		strBuff.append("<input type = 'hidden' id = 'txtAcademicProgramGroupCodes' name = 'txtAcademicProgramGroupCodes' value = '"+academicYear.getAcademicProgramGroupCodes()+"' >");
		strBuff.append("<script>");
		strBuff.append("$('input[name=rbAcademicProgramGroupCodes]').click(function(){");
		strBuff.append("$('#txtAcademicProgramGroupCodes').val($(this).val());");
		strBuff.append("});");		
		strBuff.append("</script>");
		return strBuff.toString();
	}
	
	public static String getDataViewStr(SessionInfo sessionInfo, AcademicYearDTO academicYear, List<DTOBase> academicProgramGroupList) {
		AcademicProgramGroupDTO academicProgramGroup = (AcademicProgramGroupDTO) DTOUtil.getObjByCode(academicProgramGroupList, academicYear.getAcademicProgramGroupCodes());
		StringBuffer strBuff = new StringBuffer();
		strBuff.append("<div class='col-sm-6 no-padding'>Code: " + academicYear.getCode() + "</div>");
		strBuff.append("<div class='col-sm-6 no-padding'>Name: " + academicYear.getName() + "</div>");
		strBuff.append("<div class='col-sm-6 no-padding'>Remarks: " + academicYear.getRemarks() + "</div>");
		strBuff.append("<div class='col-sm-6 no-padding'>Date Start: " + DateTimeUtil.getDateTimeToStr(academicYear.getDateStart(), "MM/dd/yyyy") + "</div>");
		strBuff.append("<div class='col-sm-6 no-padding'>Date End: " + DateTimeUtil.getDateTimeToStr(academicYear.getDateEnd(), "MM/dd/yyyy") + "</div>");
		strBuff.append("<div class='col-sm-6 no-padding'>Academic Program Group Code: " + academicProgramGroup.getName() + "</div>");
		strBuff.append("<div class='col-sm-6 no-padding'>Name: " + academicYear.getGradingSystem() + "</div>");
		return strBuff.toString();
	}
	 
	 public static String[] getAcademicYearCodeArrFromObjList(List<DTOBase> semesterList) {
		String[] codesArr = new String[semesterList.size()];
	    int i = 0;
	    for (DTOBase obj : semesterList){
	    	SemesterDTO semester = (SemesterDTO) obj; 
	      codesArr[i++] = semester.getAcademicYear().getCode(); 
	    }
	    return codesArr;
	 }
}
