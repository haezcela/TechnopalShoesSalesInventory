package com.laponhcet.academicsection;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.laponhcet.academicprogram.AcademicProgramDTO;
import com.laponhcet.advisory.AdvisoryDTO;
import com.mytechnopal.DataTable;
import com.mytechnopal.SessionInfo;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.base.WebControlBase;
import com.mytechnopal.util.DTOUtil;
import com.mytechnopal.util.StringUtil;
import com.mytechnopal.webcontrol.ComboBoxWebControl;
import com.mytechnopal.webcontrol.DataTableWebControl;
import com.mytechnopal.webcontrol.TextBoxWebControl;

public class AcademicSectionUtil implements Serializable {
	private static final long serialVersionUID = 1L;

	private static String[][] getDataTableCurrentPageRecordArr(SessionInfo sessionInfo, DataTable dataTable) {
//		dataTable.setCurrentPageRecordList();
		String[][] strArr = new String[dataTable.getRecordListCurrentPage().size()][dataTable.getColumnNameArr().length];
		for(int row=0; row < dataTable.getRecordListCurrentPage().size(); row++) {			
			AcademicSectionDTO academicSection = (AcademicSectionDTO) dataTable.getRecordListCurrentPage().get(row);
//			String adviser = "";
//			if(academicSection.getAdviser() != null) {
//				adviser = academicSection.getAdviser().getName(false, true, true);
//			}
			strArr[row][0] = academicSection.getAcademicProgram().getName();
			strArr[row][1] = Integer.toString(academicSection.getYearLevel());
			strArr[row][2] = academicSection.getName();
			//strArr[row][3] = adviser;
			strArr[row][3] = dataTable.getRecordButtonStr(sessionInfo, academicSection.getCode());
		}
		return strArr;
	}
	
	public static String getDataTableStr(SessionInfo sessionInfo, DataTable dataTable) {
		DataTableWebControl dtwc = new DataTableWebControl(sessionInfo, dataTable);
		StringBuffer strBuff = new StringBuffer();
		if(dataTable.getRecordList().size() >= 1) {
			strBuff.append(dtwc.getDataTableHeader(sessionInfo, dataTable));
			dataTable.setDataTableRecordArr(getDataTableCurrentPageRecordArr(sessionInfo, dataTable));
			strBuff.append(dtwc.getDataTable(true, false));
		}
		return strBuff.toString();
	}
	
	public static void searchAcademicSectionByName(DataTable dataTable, String searchValue) {
		dataTable.setRecordListInvisible();
		for(DTOBase dto: dataTable.getRecordList()) {
			AcademicSectionDTO academicSection = (AcademicSectionDTO) dto;
			if(academicSection.getName().toLowerCase().contains(searchValue.toLowerCase())) {
				academicSection.setVisible(true);
			}
		}
	}
	
	public static String getDataEntryStr(SessionInfo sessionInfo, AcademicSectionDTO academicSection, List<DTOBase> academicProgramList) {
		StringBuffer strBuff = new StringBuffer();
		strBuff.append(new ComboBoxWebControl().getComboBoxWebControl("col-sm-3 no-padding", true, "Academic Program", "AcademicProgram", academicProgramList, academicSection.getAcademicProgram(), "Select", "0", "onchange='setAcademicProgramCode()'")); 
		if(!StringUtil.isEmpty(academicSection.getAcademicProgram().getCode())) {
		strBuff.append(new ComboBoxWebControl().getComboBoxWebControl("col-sm-2", true, "Year Level", "YearLevel", StringUtil.getSeries(1, academicSection.getAcademicProgram().getGraduationYearLevel(), 1), String.valueOf(academicSection.getYearLevel()), StringUtil.getSeries(1, academicSection.getAcademicProgram().getGraduationYearLevel(), 1), "Select", "0", ""));
		strBuff.append(new TextBoxWebControl().getTextBoxWebControl("col-sm-3", true, "Name", "Name", "Name", academicSection.getName(), 45, WebControlBase.DATA_TYPE_STRING, "")); 
		//strBuff.append(new ComboBoxWebControl().getComboBoxWebControl("col-sm-4", false, "Adviser", "Adviser", teacherList, academicSection.getAdviser(), "Select", "0", ""));
		}
		strBuff.append("<script>");
		strBuff.append("	function setAcademicProgramCode() {");
		strBuff.append("		if($('#cboAcademicProgramCode').val() != '0') {");	
		strBuff.append("			$('#divSKSpinner').show();");	
		strBuff.append("			$.ajax({");	
		strBuff.append("				url: 'AjaxController?txtSelectedLink=" + sessionInfo.getCurrentLink().getCode() + "',");	
		strBuff.append("				data: {");		
		//strBuff.append("					txtAction: '" + AcademicSectionDTO.ACTION_SET_ACADEMIC_PROGRAM + "',");	
		strBuff.append("					cboAcademicProgramId: $('#cboAcademicProgram').val()");		
		strBuff.append("				},");	
		strBuff.append("				method: 'POST',");	
		strBuff.append("				dataType: 'JSON',");	
		strBuff.append("				success: function(response) {");		
//		strBuff.append("					$('#divPage003').html(response.PAGE_CONTENT);");		
		strBuff.append("					displayPage('divPage" + sessionInfo.getCurrentLink().getCode() + "', response.PAGE_CONTENT);");			
		strBuff.append("					$('#divSKSpinner').hide();");		
		strBuff.append("				}");		
		strBuff.append("			});");	
		strBuff.append("		}");
		strBuff.append("	}");
		strBuff.append("</script>");
		return strBuff.toString();
	}
	
	public static String getDataViewStr(SessionInfo sessionInfo, AcademicSectionDTO academicSection) {
		StringBuffer strBuff = new StringBuffer();
		strBuff.append("<div class='col-sm-12 no-padding'>Academic Program: " + academicSection.getAcademicProgram().getName() + "</div>");
		strBuff.append("<div class='col-sm-12 no-padding'>Year Level: " + academicSection.getYearLevel() + "</div>");
		strBuff.append("<div class='col-sm-12 no-padding'>Name: " + academicSection.getName() + "</div>");
		return strBuff.toString();
	}
	
	public static void setAcademicSectionList(List<DTOBase> academicSectionList, List<DTOBase> academicProgramList) {
		for(DTOBase academicSectionObj: academicSectionList) {
			AcademicSectionDTO academicSection = (AcademicSectionDTO) academicSectionObj;
			academicSection.setAcademicProgram((AcademicProgramDTO) DTOUtil.getObjByCode(academicProgramList, academicSection.getAcademicProgram().getCode()));
			academicSection.setDisplayStr(getYearLevelSectionStr(academicSection));
		}
	}
	
	public static List<DTOBase> getAcademicSectionListByAdvisoryList(List<DTOBase> academicSectionList, List<DTOBase> advisoryList) {
		List<DTOBase> list = new ArrayList<DTOBase>();
		for(DTOBase advisoryObj: advisoryList) {
			AdvisoryDTO advisory = (AdvisoryDTO) advisoryObj;
			list.add(DTOUtil.getObjByCode(academicSectionList, advisory.getAcademicSection().getCode()));
		}
		return list;
	}
	
	public static String getYearLevelStr(AcademicSectionDTO academicSection) {
		String grade = "";
		if(academicSection.getAcademicProgram().getCode().equalsIgnoreCase("001")) {
			if(academicSection.getYearLevel() == 1) {
				grade = "Prep";
			}
			else if(academicSection.getYearLevel() == 2) {
				grade = "Kinder 1";
			}
			else {
				grade = "Kinder 2";
			}
		}
		else if(academicSection.getAcademicProgram().getCode().equalsIgnoreCase("002")) {
			grade = "Grade " + String.valueOf(academicSection.getYearLevel());
		}
		else if(academicSection.getAcademicProgram().getCode().equalsIgnoreCase("003")) {
			grade = "Grade " + String.valueOf(academicSection.getYearLevel() + 6);
		}
		else if(academicSection.getAcademicProgram().getCode().equalsIgnoreCase("004") || academicSection.getAcademicProgram().getCode().equalsIgnoreCase("005") || academicSection.getAcademicProgram().getCode().equalsIgnoreCase("006") || academicSection.getAcademicProgram().getCode().equalsIgnoreCase("007") || academicSection.getAcademicProgram().getCode().equalsIgnoreCase("008") || academicSection.getAcademicProgram().getCode().equalsIgnoreCase("009")) {
			grade =  "Grade " + String.valueOf(academicSection.getYearLevel() + 10) + " (" + academicSection.getAcademicProgram().getName() + ")";
		}
		else {
			grade = academicSection.getAcademicProgram().getName() + " | " + academicSection.getYearLevel();
		}
		return grade;
	}
	
	public static String getYearLevelSectionStr(AcademicSectionDTO academicSection) {
		if(academicSection.getAcademicProgram().getCode().equalsIgnoreCase("001")) { //Kindergarten
			String gradeSection = "";
			if(academicSection.getYearLevel() == 1) {
				gradeSection = "Prep";
			}
			else if(academicSection.getYearLevel() == 2) {
				gradeSection = "Kinder 1";
			}
			else {
				gradeSection = "Kinder 2";
			}

			if(StringUtil.isEmpty(academicSection.getName())) {
				return gradeSection;
			}
			else {
				return gradeSection + " - " + academicSection.getName();
			}
		}
		else if(academicSection.getAcademicProgram().getCode().equalsIgnoreCase("002")) { //elementary
			if(StringUtil.isEmpty(academicSection.getName())) {
				return "Grade " + String.valueOf(academicSection.getYearLevel());
			}
			else {
				return "Grade " + String.valueOf(academicSection.getYearLevel()) + " - " + academicSection.getName();
			}
		}
		else if(academicSection.getAcademicProgram().getCode().equalsIgnoreCase("003")) { //JHS
			if(StringUtil.isEmpty(academicSection.getName())) {
				return "Grade " + String.valueOf(academicSection.getYearLevel() + 6);
			}
			else {
				return "Grade " + String.valueOf(academicSection.getYearLevel() + 6) + " - " + academicSection.getName();
			}
		}
		else if(academicSection.getAcademicProgram().getCode().equalsIgnoreCase("004") || academicSection.getAcademicProgram().getCode().equalsIgnoreCase("005") || academicSection.getAcademicProgram().getCode().equalsIgnoreCase("006") || academicSection.getAcademicProgram().getCode().equalsIgnoreCase("007") || academicSection.getAcademicProgram().getCode().equalsIgnoreCase("008") || academicSection.getAcademicProgram().getCode().equalsIgnoreCase("009")) { //SHS
			if(StringUtil.isEmpty(academicSection.getName())) {
				return "Grade " + String.valueOf(academicSection.getYearLevel() + 10) + " (" + academicSection.getAcademicProgram().getName() + ")";
			}
			else {
				return "Grade " + String.valueOf(academicSection.getYearLevel() + 10) + " - " + academicSection.getName() + " (" + academicSection.getAcademicProgram().getName() + ")";
			}
		}
		else { //Vocational or College
			if(StringUtil.isEmpty(academicSection.getName())) {
				return academicSection.getAcademicProgram().getName() + "-" + String.valueOf(academicSection.getYearLevel());
			}
			else {
				return academicSection.getAcademicProgram().getName() + "-" + String.valueOf(academicSection.getYearLevel()) + academicSection.getName();
			}
		}
	}
}
