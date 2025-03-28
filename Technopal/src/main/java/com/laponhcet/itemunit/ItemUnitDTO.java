package com.laponhcet.itemunit;

import com.mytechnopal.base.DTOBase;

public class ItemUnitDTO extends DTOBase {
    private static final long serialVersionUID = 1L;

    public static final String SESSION_ITEM_UNIT = "SESSION_ITEM_UNIT";
    public static final String SESSION_ITEM_UNIT_LIST = "SESSION_ITEM_UNIT_LIST";
    public static final String SESSION_ITEM_UNIT_DATA_TABLE = "SESSION_ITEM_UNIT_DATA_TABLE";

    private String code;
    private String name;
    private String addedBy;
    private String updatedBy;

    public ItemUnitDTO() {
        super();
        this.code = "";
        this.name = "";
        this.addedBy = "";
        this.updatedBy = "";
    }

    public ItemUnitDTO getItemUnit() {
        ItemUnitDTO itemUnit = new ItemUnitDTO();
        itemUnit.setId(super.getId());  
        itemUnit.setCode(this.code);
        itemUnit.setName(this.name);
        itemUnit.setAddedBy(this.addedBy);
        itemUnit.setUpdatedBy(this.updatedBy);
        return itemUnit;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
}
