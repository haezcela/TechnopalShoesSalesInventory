
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

    public ItemUnitDTO(String string) {
		// TODO Auto-generated constructor stub
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ItemUnitDTO getItemUnit() {
    	ItemUnitDTO itemUnit = new ItemUnitDTO();
    	itemUnit.setId(super.getId());
    	itemUnit.setCode(String.valueOf(itemUnit.getCode()));
        itemUnit.setName(this.name);
        
        return itemUnit;
    }

    public void display() {
        System.out.println("Id: " + getId());
        System.out.println("Code: " + getCode());
        System.out.println("Name: " + name);
        System.out.println("Added By: " + getAddedBy());
        System.out.println("Added Timestamp: " + getAddedTimestamp());
        System.out.println("Updated By: " + getUpdatedBy());
        System.out.println("Updated Timestamp: " + getUpdatedTimestamp());

    }
}
