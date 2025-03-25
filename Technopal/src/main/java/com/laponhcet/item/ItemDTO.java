package com.laponhcet.item;
import com.laponhcet.itemcategory.ItemCategoryDTO;
import com.laponhcet.itemunit.ItemUnitDTO;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.util.StringUtil;

public class ItemDTO extends DTOBase {
	private static final long serialVersionUID = 1L;
	public static final String SESSION_ITEM = "SESSION_ITEM";
	public static final String SESSION_ITEM_LIST = "SESSION_ITEM_LIST";
	public static final String SESSION_ITEM_DATA_TABLE = "SESSION_ITEM_DATA_TABLE";
	public static final String ACTION_SEARCH_BY_NAME = "ACTION_SEARCH_BY_NAME";
	
	private ItemCategoryDTO itemCategory;
	private String name;
	private String description;
	private ItemUnitDTO itemUnit;
	private Double unitPrice;
	private Double quantity;
	private Double reorderPoint;

	public ItemDTO() {
		super();
		itemCategory = new ItemCategoryDTO();
		itemUnit = new ItemUnitDTO();
		name = "";
		description = "";
		unitPrice = 0.0;
		quantity= 0.0;
		reorderPoint=0.0;
	}
	
	public ItemDTO getItem() { 
		ItemDTO item = new ItemDTO();
		item.setItemCategory(this.itemCategory);
		item.setItemUnit(this.itemUnit);
		item.setName(this.name);
		item.setDescription(this.description);
		item.setQuantity(this.quantity);
		item.setReorderpoint(this.reorderPoint);
		return item;
	}

	public ItemCategoryDTO getItemCategory() {
		return itemCategory;
	 }
	
	public void setItemCategory(ItemCategoryDTO itemCategory) {
		this.itemCategory = itemCategory;
	 }
	
	public ItemUnitDTO getItemUnit() {
		return itemUnit;
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