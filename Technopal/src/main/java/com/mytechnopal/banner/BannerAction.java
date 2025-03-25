package com.mytechnopal.banner;

import java.util.List;

import com.mytechnopal.DataTable;
import com.mytechnopal.base.ActionBase;
import com.mytechnopal.base.DTOBase;

public class BannerAction extends ActionBase {

	private static final long serialVersionUID = 1L;

	protected void setSessionVars() {
		List<DTOBase> bannerList = new BannerDAO().getBannerList();
		
		DataTable dataTable = new DataTable(BannerDTO.SESSION_BANNER_DATA_TABLE, bannerList, new String[] {BannerDTO.ACTION_SEARCH_BY_FILENAME}, new String[] {"Filename"});
		dataTable.setColumnNameArr(new String[] {"FILE", "Active Start Timestamp", "Active End Timestamp", ""});
		dataTable.setColumnWidthArr(new String[] {"35", "20", "20", "25"});
		setSessionAttribute(BannerDTO.SESSION_BANNER_DATA_TABLE, dataTable);
		
		setSessionAttribute(BannerDTO.SESSION_BANNER_LIST, bannerList);
	}
}