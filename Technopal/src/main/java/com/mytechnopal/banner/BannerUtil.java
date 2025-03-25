package com.mytechnopal.banner;

import java.io.Serializable;

import com.laponhcet.enrollment.EnrollmentDTO;
import com.mytechnopal.DataTable;
import com.mytechnopal.SessionInfo;
import com.mytechnopal.UploadedFile;
import com.mytechnopal.base.WebControlBase;
import com.mytechnopal.util.DateTimeUtil;
import com.mytechnopal.util.QRCodeUtil;
import com.mytechnopal.webcontrol.DataTableWebControl;
import com.mytechnopal.webcontrol.FileInputWebControl;
import com.mytechnopal.webcontrol.TextBoxWebControl;

public class BannerUtil implements Serializable {
	private static final long serialVersionUID = 1L;

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
	
	private static String[][] getDataTableCurrentPageRecordArr(SessionInfo sessionInfo, DataTable dataTable) {
		String[][] strArr = new String[dataTable.getRecordListCurrentPage().size()][dataTable.getColumnNameArr().length];
		for(int row=0; row < dataTable.getRecordListCurrentPage().size(); row++) {			
			BannerDTO banner = (BannerDTO) dataTable.getRecordListCurrentPage().get(row);
			strArr[row][0] = banner.getFilename();
			strArr[row][1] = DateTimeUtil.getDateTimeToStr(banner.getActiveStartTimestamp(), "MMM dd, yyyy hh:mm a");
			strArr[row][2] = DateTimeUtil.getDateTimeToStr(banner.getActiveEndTimestamp(), "MMM dd, yyyy hh:mm a");
			strArr[row][3] = dataTable.getRecordButtonStr(sessionInfo, banner.getCode());
		}
		return strArr;
	}
	
	public static String getDataEntryStr(SessionInfo sessionInfo, BannerDTO banner, UploadedFile uploadedFile) {		
		StringBuffer strBuff = new StringBuffer();
		strBuff.append(new TextBoxWebControl().getTextBoxWebControl("form-group col-lg-4", false, "Label", "Label", "Label", banner.getLabel(), 45, WebControlBase.DATA_TYPE_STRING, ""));
		strBuff.append(new TextBoxWebControl().getTextBoxWebControl("form-group col-lg-8", false, "Description", "Description", "Description", banner.getDescription(), 45, WebControlBase.DATA_TYPE_STRING, ""));
		strBuff.append(new FileInputWebControl().getFileInputWebControl("form-group col-lg-6", true, "", "File", uploadedFile));
		return strBuff.toString();
	}
	
	public static String getDataViewStr(SessionInfo sessionInfo, BannerDTO banner) {
		StringBuffer strBuff = new StringBuffer();
		strBuff.append("<div class='col-lg-12'>");
		strBuff.append("	<img src='/static/" + sessionInfo.getSettings().getCode() + "/media/banner/" + banner.getFilename() + "'>");
		strBuff.append("</div>");
		return strBuff.toString();
	}
}