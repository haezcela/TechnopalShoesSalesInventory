package com.laponhcet.academicprogram;

import java.io.Serializable;
import java.util.List;

import com.mytechnopal.DataTable;
import com.mytechnopal.SessionInfo;
import com.mytechnopal.UploadedFile;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.base.WebControlBase;
import com.mytechnopal.dao.UserDAO;
import com.mytechnopal.dto.UserDTO;
import com.mytechnopal.util.DTOUtil;
import com.mytechnopal.webcontrol.ComboBoxWebControl;
import com.mytechnopal.webcontrol.DataTableWebControl;
import com.mytechnopal.webcontrol.FileInputWebControl;
import com.mytechnopal.webcontrol.TextBoxWebControl;

public class AcademicProgramUtil implements Serializable {
	private static final long serialVersionUID = 1L;

	
	public static void setAcademicProgramList(List<DTOBase> academicProgramList, List<DTOBase> academicProgramGroupList, List<DTOBase> academicProgramSubGroupList) {
		for(DTOBase academicProgramObj: academicProgramList) {
			AcademicProgramDTO academicProgram = (AcademicProgramDTO) academicProgramObj;
			academicProgram.setAcademicProgramSubGroup((AcademicProgramSubGroupDTO) DTOUtil.getObjByCode(academicProgramSubGroupList, academicProgram.getAcademicProgramSubGroup().getCode()));
			academicProgram.getAcademicProgramSubGroup().setAcademicProgramGroup((AcademicProgramGroupDTO) DTOUtil.getObjByCode(academicProgramGroupList, academicProgram.getAcademicProgramSubGroup().getAcademicProgramGroup().getCode()));
		}
	}
	
	private static String[][] getDataTableCurrentPageRecordArr(SessionInfo sessionInfo, DataTable dataTable) {
		String[][] strArr = new String[dataTable.getRecordListCurrentPage().size()][dataTable.getColumnNameArr().length];
		for(int row=0; row < dataTable.getRecordListCurrentPage().size(); row++) {			
			AcademicProgramDTO academicProgram = (AcademicProgramDTO) dataTable.getRecordListCurrentPage().get(row);
			strArr[row][0] = academicProgram.getCode();
			strArr[row][1] = academicProgram.getAcademicProgramSubGroup().getCode();
			strArr[row][2] = academicProgram.getName();
			strArr[row][3] = dataTable.getRecordButtonStr(sessionInfo, String.valueOf(academicProgram.getId()));
		}
		return strArr;
	}
	
	public static String getDataTableStr(SessionInfo sessionInfo, DataTable dataTable) {
		DataTableWebControl dtwc = new DataTableWebControl(sessionInfo, dataTable);
		StringBuffer strBuff = new StringBuffer();
		if(dataTable.getRecordList().size() >= 1) {
			strBuff.append(dtwc.getDataTableHeader(sessionInfo, dataTable));
			dataTable.setDataTableRecordArr(getDataTableCurrentPageRecordArr(sessionInfo, dataTable));
			strBuff.append(dtwc.getDataTable(true, ""));
		}
		return strBuff.toString();
	}
	
	public static void searchAcademicProgramByName(DataTable dataTable, String searchValue) {
		dataTable.setRecordListInvisible();
		for(DTOBase dto: dataTable.getRecordList()) {
			AcademicProgramDTO academicProgram = (AcademicProgramDTO) dto;
			if(academicProgram.getCode().toLowerCase().contains(searchValue.toLowerCase())) {
				academicProgram.setVisible(true);
			} 
		}
	}
	
	public static String getDataEntryStr(SessionInfo sessionInfo, AcademicProgramDTO academicProgram, List<DTOBase> academicProgramSubGroupList, List<DTOBase> userList, UploadedFile uploadedFile) {
		StringBuffer strBuff = new StringBuffer();
		strBuff.append(new ComboBoxWebControl().getComboBoxWebControl("col-sm-4", true, "Academic Program Sub Group", "AcademicProgramSubGroupCode", academicProgramSubGroupList, academicProgram.getAcademicProgramSubGroup(), "Select", "", ""));
		strBuff.append(new TextBoxWebControl().getTextBoxWebControl("col-sm-4", true, "Name", "Name", "Name", academicProgram.getName(), 180, WebControlBase.DATA_TYPE_STRING, ""));	
		strBuff.append(new TextBoxWebControl().getTextBoxWebControl("col-sm-4", false, "Description", "Description", "Description", academicProgram.getDescription(), 180, WebControlBase.DATA_TYPE_STRING, ""));
		strBuff.append(new TextBoxWebControl().getTextBoxWebControl("col-sm-6", true, "Graduation Year Level", "Graduation Year Level", "GraduationYearLevel", Integer.toString(academicProgram.getGraduationYearLevel()), 180, WebControlBase.DATA_TYPE_INTEGER, ""));
		strBuff.append(new ComboBoxWebControl().getComboBoxWebControl("col-sm-6", false, "Head User", "HeadUserCode", userList, academicProgram.getHeadUserCode(),  "Select", "", ""));
		strBuff.append("<div class='col-sm-12'><br><hr style='border: 1px solid green'></div>");
		strBuff.append(	new FileInputWebControl().getFileInputWebControl("col-sm-12 no-padding", false, "", "Logo", FileInputWebControl.FILE_TYPE_STANDARD, uploadedFile, ""));
		return strBuff.toString();
	}
	
	public static String getDataViewStr(SessionInfo sessionInfo, AcademicProgramDTO academicProgram) {
		String path = "C:\\home\\static\\HRA2\\logo\\";
		StringBuffer strBuff = new StringBuffer();
		strBuff.append("<div class='col-sm-4 no-padding'>Code: " + academicProgram.getCode() + "</div>");
		strBuff.append("<div class='col-sm-4 no-padding'>Academic Program Sub Group Code: " + academicProgram.getAcademicProgramSubGroup().getCode() + "</div>");
		strBuff.append("<div class='col-sm-4 no-padding'>Name: " + academicProgram.getName() + "</div>");
		strBuff.append("<div class='col-sm-4 no-padding'>Description: " + academicProgram.getDescription() + "</div>");
		strBuff.append("<div class='col-sm-4 no-padding'>Graduation Year Level: " + academicProgram.getGraduationYearLevel() + "</div>");
/*		strBuff.append("<div class='col-sm-4 no-padding'>Head User Code: " + academicProgram.getHeadUserCode().getCode() + "</div>");*/
		strBuff.append("<div class='col-sm-12'><br><hr style='border: 1px solid green'></div>");
		strBuff.append("			<div class='col-sm-6 no-padding'>Logo: ");
		strBuff.append("				<img alt='image' src='/static/" + sessionInfo.getSettings().getCode() + "/logo/" + academicProgram.getCode() + ".jpg'>");
		strBuff.append("        	</div>");
		return strBuff.toString();
	}
	
	public static String[] getYearLevelDisplayArr(SessionInfo sessionInfo, AcademicProgramDTO academicProgram) {
		String[] yearLevelArr = null;
		if(academicProgram != null) {
			yearLevelArr = new String[academicProgram.getGraduationYearLevel()];
			for(int i=0; i<academicProgram.getGraduationYearLevel(); i++) {
				yearLevelArr[i] = getYearLevelStr(sessionInfo, academicProgram, i+1);
			}
		}
		return yearLevelArr;
	}
	
	public static List<DTOBase> getAcademicProgramList(List<DTOBase> list, List<DTOBase> academicProgramGroupList, List<DTOBase> academicProgramSubgroupList) {
		setAcademicProgramList(list, academicProgramGroupList, academicProgramSubgroupList);
		for(DTOBase academicProgramObj: list) {
			AcademicProgramDTO academicProgram = (AcademicProgramDTO) academicProgramObj;
			if(academicProgram.getHeadUserCode().isTBA()) {
				TeacherDTO teacher = TeacherUtil.getTBA();
				academicProgram.setHeadUserCode(teacher);
			}
			else {
				UserDTO user = new UserDAO().getUserByCode(academicProgram.getHeadUserCode().getCode());
				TeacherDTO teacher = new TeacherDAO().getTeacherByUserCode(user.getCode());
				TeacherUtil.setTeacher(teacher, user);
			}
		}
		return list;
	}
	
public static final String getPreSchoolYearLevelStr(SessionInfo sessionInfo, int yearLevel) {
		
		String yearLevelStr = "";
		if(sessionInfo.getSettings().getCode().equalsIgnoreCase("HRA2")) {
			if(yearLevel == 1) {
				yearLevelStr = "PREKINDERGARTEN";
			}
			else {
				yearLevelStr = "KINDERGARTEN";
			}
		}
		else if(sessionInfo.getSettings().getCode().equalsIgnoreCase("CSNT")) {
			if(yearLevel == 1) {
				yearLevelStr = "NURSERY";
			}
			else if(yearLevel == 2) {
				yearLevelStr = "PRE-KINDER";
			}
			else {
				yearLevelStr = "KINDER"; 
			}
		}
		else {
			if(yearLevel == 1) {
				yearLevelStr = "NURSERY";
			}
			else if(yearLevel == 2) {
				yearLevelStr = "KINDER 1";
			}
			else {
				yearLevelStr = "KINDER 2"; 
			}
		}
		return yearLevelStr;
	}


	
	public static final String getYearLevelStr(SessionInfo sessionInfo, AcademicProgramDTO academicProgram, int yearLevel) {
		String yearLevelStr = "";
		if(academicProgram.getAcademicProgramSubGroup().getCode().equalsIgnoreCase(AcademicProgramSubGroupDTO.PS_CODE)) {
			yearLevelStr = getPreSchoolYearLevelStr(sessionInfo, yearLevel);
		}
		else {
			if(academicProgram.getAcademicProgramSubGroup().getCode().equalsIgnoreCase(AcademicProgramSubGroupDTO.ELEM_CODE)) {
				yearLevelStr = "GRADE " + yearLevel;
			}
			else if(academicProgram.getAcademicProgramSubGroup().getCode().equalsIgnoreCase(AcademicProgramSubGroupDTO.JHS_CODE)) {
				yearLevelStr = "GRADE " + (yearLevel + 6);
			}
			else if(academicProgram.getAcademicProgramSubGroup().getCode().equalsIgnoreCase(AcademicProgramSubGroupDTO.SHS_CODE)) {
				yearLevelStr = "GRADE " + (yearLevel + 10);
			}
			else {
				if(yearLevel == 1) {
					yearLevelStr = "First";
				}
				else if(yearLevel == 2) {
					yearLevelStr = "Second";
				}
				else if(yearLevel == 3) {
					yearLevelStr = "Third";
				}
				else if(yearLevel == 4) {
					yearLevelStr = "Fourth";
				}
				else if(yearLevel == 5) {
					yearLevelStr = "Fifth";
				}
				yearLevelStr += " Year";
			}
		}
		return yearLevelStr;
	}
	
	public static String[] getYearLevelValueArr(AcademicProgramDTO academicProgram) {
		String[] yearLevelArr = null;
		if(academicProgram != null) {
			yearLevelArr = new String[academicProgram.getGraduationYearLevel()];
			for(int i=0; i<academicProgram.getGraduationYearLevel(); i++) {
				yearLevelArr[i] = String.valueOf(i+1);
			}
		}
		return yearLevelArr;
	}
}