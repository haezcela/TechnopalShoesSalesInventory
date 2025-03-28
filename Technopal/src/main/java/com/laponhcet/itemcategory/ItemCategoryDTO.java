package com.laponhcet.itemcategory;

import com.mytechnopal.base.DTOBase;

public class ItemCategoryDTO extends DTOBase {
    private static final long serialVersionUID = 1L;

    public static final String SESSION_ITEM_CATEGORY = "SESSION_ITEM_CATEGORY";
    public static final String SESSION_ITEM_CATEGORY_LIST = "SESSION_ITEM_CATEGORY_LIST";
    public static final String SESSION_ITEM_CATEGORY_DATA_TABLE = "SESSION_ITEM_CATEGORY_DATA_TABLE";

    private String code;
    private String name;
    private String addedBy;
    private String updatedBy;

    
    public ItemCategoryDTO() {
        super();
        this.code = "";
        this.name = "";
        this.addedBy = "";
        this.updatedBy = "";
    }

    public ItemCategoryDTO getItemCategory() {
        ItemCategoryDTO itemCategory = new ItemCategoryDTO();
        itemCategory.setId(super.getId()); 
        itemCategory.setCode(this.code);
        itemCategory.setName(this.name);
        itemCategory.setAddedBy(this.addedBy);
        itemCategory.setUpdatedBy(this.updatedBy);
        return itemCategory;
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
