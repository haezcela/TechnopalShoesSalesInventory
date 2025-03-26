
package com.laponhcet.itemunit;

import com.laponhcet.itemcategory.ItemCategoryDTO;

import com.mytechnopal.base.DTOBase;
import java.sql.Timestamp;

public class ItemUnitDTO extends DTOBase {
    private static final long serialVersionUID = 1L;

    public static final String SESSION_ITEM_UNIT = "SESSION_ITEM_UNIT";
    public static final String SESSION_ITEM_UNIT_LIST = "SESSION_ITEM_UNIT_LIST";
    public static final String SESSION_ITEM_UNIT_DATA_TABLE = "SESSION_ITEM_UNIT_DATA_TABLE";
    
    public static final String ACTION_SEARCH_BY_CODE = "ACTION_SEARCH_BY_CODE";
    
    private String name;


    public ItemUnitDTO() {
        super();
        this.name = "";
    }
    
	public ItemUnitDTO getItemUnit() {
		ItemUnitDTO itemUnit = new ItemUnitDTO();
		itemUnit.setId(super.getId());
		itemUnit.setCode(super.getCode());
		itemUnit.setName(this.name);
		return itemUnit;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
