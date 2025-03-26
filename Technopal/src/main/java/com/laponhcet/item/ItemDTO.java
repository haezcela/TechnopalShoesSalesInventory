package com.laponhcet.item;
import com.laponhcet.itemcategory.ItemCategoryDTO;
import com.laponhcet.itemunit.ItemUnitDTO;
import com.laponhcet.vehicletype.VehicleTypeDTO;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.util.StringUtil;

public class ItemDTO extends DTOBase {
	private static final long serialVersionUID = 1L;
	public static final String SESSION_ITEM = "SESSION_ITEM";
	public static final String SESSION_ITEM_LIST = "SESSION_ITEM_LIST";
	public static final String SESSION_ITEM_DATA_TABLE = "SESSION_ITEM_DATA_TABLE";
	public static final String ACTION_SEARCH_BY_NAME = "ACTION_SEARCH_BY_NAME";
	
	private ItemCategoryDTO itemCategory;
	private ItemUnitDTO itemUnit;
	private String name;
	private String description;
	private Double unitPrice;
	private Double quantity;
	private Double reorderPoint;

	public ItemDTO() {
		super();
//		itemCategory = new ItemCategoryDTO();
//		itemUnit = new ItemUnitDTO();
		this.itemCategory = new ItemCategoryDTO();
		this.itemUnit = new ItemUnitDTO();
		this.name = "";
		this.description = "";
		this.unitPrice = 0.0;
		this.quantity= 0.0;
		this.reorderPoint=0.0;
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
        item.setAddedBy(this.getAddedBy());
        item.setAddedTimestamp(this.getAddedTimestamp());
        item.setUpdatedBy(this.getUpdatedBy());
        item.setUpdatedTimestamp(this.getUpdatedTimestamp());
		return item;
	}
	
	public ItemCategoryDTO getItemCategory() {
        if (this.itemCategory == null) {
            this.itemCategory = new ItemCategoryDTO(); // Prevent null issues
        }
        return this.itemCategory;
	 }
	
	public void setItemCategory(ItemCategoryDTO itemCategory) {
	    if (itemCategory != null) {
	        System.out.println("Current item_category_code: " + itemCategory.getCode());
	        
	        if (itemCategory.getCode().length() > 3) { 
	            System.out.println("Error: item_category_code exceeds 3 characters! Trimming...");
	            this.itemCategory.setCode(itemCategory.getCode().substring(0, 3)); // Trim to 3 chars
	        } else {
	            this.itemCategory = itemCategory;
	        }
	    } else {
	        System.out.println("Error: itemCategory is null!");
	    }
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
	
	
	//add list of itemmedia DTO
	
	
	
	

}