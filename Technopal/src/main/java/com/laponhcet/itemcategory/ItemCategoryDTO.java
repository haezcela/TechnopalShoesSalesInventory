package com.laponhcet.itemcategory;

import com.mytechnopal.base.DTOBase;


import java.sql.Timestamp;

public class ItemCategoryDTO extends DTOBase {
    private static final long serialVersionUID = 1L;

    public static final String SESSION_ITEM_CATEGORY = "SESSION_ITEM_CATEGORY";
    public static final String SESSION_ITEM_CATEGORY_LIST = "SESSION_ITEM_CATEGORY_LIST";
    public static final String SESSION_ITEM_CATEGORY_DATA_TABLE = "SESSION_ITEM_CATEGORY_DATA_TABLE";
    
    public static final String ACTION_SEARCH_BY_CODE = "ACTION_SEARCH_BY_CODE";
   
    private String name;


    public ItemCategoryDTO() {
        super();
        this.name = "";
    }

	public ItemCategoryDTO getItemCategory() {
		ItemCategoryDTO itemCategory = new ItemCategoryDTO();
		itemCategory.setId(super.getId());
		itemCategory.setCode(super.getCode());
		itemCategory.setName(this.name);
		return itemCategory;
	}
		// TODO Auto-generated constructor stub
	

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}