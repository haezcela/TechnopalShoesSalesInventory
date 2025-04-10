package com.laponhcet.item;
import com.laponhcet.itemcategory.ItemCategoryDTO;
import com.laponhcet.itemunit.ItemUnitDTO;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.util.StringUtil;

public class ItemDTO extends DTOBase {
	private static final long serialVersionUID = 1L;
	public static String SESSION_ITEM = "SESSION_ITEM";
	public static String SESSION_ITEM_LIST = "SESSION_ITEM_LIST";
	public static String SESSION_ITEM_DATA_TABLE = "SESSION_ITEM_DATA_TABLE";
	public static String ACTION_SEARCH_BY_NAME = "ACTION_SEARCH_BY_NAME";
	public static String SESSION_UPLOADED_FILE = "SESSION_UPLOADED_FILE";
	
	private ItemCategoryDTO itemCategory;
	private ItemUnitDTO itemUnit;
	private String name;
	private String description;
	private Double unitPrice;
	private Double quantity;
	private Double reorderPoint;
	private String picture;

	public ItemDTO() {
		super();
//		itemCategory = new ItemCategoryDTO();
//		itemUnit = new ItemUnitDTO();
		this.itemCategory = new ItemCategoryDTO();
		this.itemUnit = new ItemUnitDTO();
		this.name = "";
		this.description = "";
		this.unitPrice = 0.00;
		this.quantity= 0.00;
		this.reorderPoint=0.00;
		this.picture = "";
	}
	
	public ItemDTO getItem() { 
		ItemDTO item = new ItemDTO();
		item.setId(super.getId());
		item.setCode(super.getCode());
		item.setItemCategory(this.itemCategory);
		item.setItemUnit(this.itemUnit);
		item.setName(this.name);
		item.setDescription(this.description);
		item.setUnitPrice(this.unitPrice);
		item.setQuantity(this.quantity);
		item.setReorderpoint(this.reorderPoint);
        item.setAddedBy(this.getAddedBy());
        item.setAddedTimestamp(this.getAddedTimestamp());
        item.setUpdatedBy(this.getUpdatedBy());
        item.setUpdatedTimestamp(this.getUpdatedTimestamp());
        item.setPicture(this.picture);
        
		return item;
	}
	
	public ItemCategoryDTO getItemCategory() {
        if (this.itemCategory == null) {
            this.itemCategory = new ItemCategoryDTO(); // Prevent null issues
        }
        return this.itemCategory;
	 }
	
	public void setItemCategory(ItemCategoryDTO itemCategory) {
	    this.itemCategory = itemCategory;
	}

	public ItemUnitDTO getItemUnit() {
        if (this.itemUnit == null) {
            this.itemUnit = new ItemUnitDTO(); // Prevent null issues
        }
        return this.itemUnit;
    }
	
	public void setItemUnit(ItemUnitDTO itemUnit) {
		this.itemUnit = itemUnit;
	 }
	
	public String getName() {
		return name;
	 }
	
	public void setName(String name) {
		this.name = name;
	 }
	
	public String getDescription() {
		return description;
	 }
	
	public void setDescription(String description) {
		this.description = description;
	 }
	
	public Double getQuantity() {
		return quantity;
	 }
	
	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	 }
	
	public Double getReorderpoint() {
		return reorderPoint;
	 }
	
	public void setReorderpoint(Double reorder_point) {
		this.reorderPoint = reorder_point;
	 }

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}
	
	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

//	
//	public ItemMediaDTO getItemMedia() {
//        if (this.itemMedia == null) {
//            this.itemMedia = new ItemMediaDTO(); // Prevent null issues
//        }
//        return this.itemMedia;
//    }
//	
//	public void setItemMedia(ItemMediaDTO itemMedia) {
//		this.itemMedia = itemMedia;
//	 }
//	

	
	//add list of itemmedia DTO
	
	
	
	

}