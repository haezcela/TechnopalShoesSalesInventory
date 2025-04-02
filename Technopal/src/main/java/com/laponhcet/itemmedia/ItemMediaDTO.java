package com.laponhcet.itemmedia;


import com.laponhcet.item.ItemDTO;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.dto.MediaDTO;
import com.mytechnopal.dto.MediaTypeDTO;

public class ItemMediaDTO extends DTOBase { 
	
	//From Item
	//private String itemCode; 
	private ItemDTO item; 
	
	
	
	//From MediaType
//	private String mediaTypeCode;
	private MediaTypeDTO mediaType; 
	
	//From Media
//	private String fileName;
//	private String base64;
	private MediaDTO media;
	
	
	public ItemMediaDTO() {
		
		super(); 
		
		//this.itemCode = "";
		this.item = new ItemDTO();
		this.mediaType = new MediaTypeDTO();
		this.media = new MediaDTO();
//		this.fileName = ""; 
//		this.base64 = "";
		}

//Getters and setters
//public String getItemCode() {
// return itemCode;
//}
//
//public void setItemCode(String itemCode) {
// this.itemCode = itemCode;
//}
	
public ItemDTO getItem() {
		if (this.item == null) {
				this.item = new ItemDTO(); // Prevent null issues
			}
			return this.item;
		}
		
public void setItem(ItemDTO item) {
	this.item = item;
		}

public MediaTypeDTO getMediaType() {
    if (this.mediaType == null) {
        this.mediaType = new MediaTypeDTO(); // Prevent null issues
    }
    return this.mediaType;
}

public void setMediaType(MediaTypeDTO mediaType) {
    this.mediaType = mediaType;
}

public MediaDTO getMedia() {
	if (this.media == null) {
		this.media = new MediaDTO(); // Prevent null issues
	}
	return this.media;
}

public void setMedia(MediaDTO media) {
	this.media = media;
}



//public String getMediaTypeCode() {
// return mediaTypeCode;
//}
//
//public void setMediaTypeCode(String mediaTypeCode) {
// this.mediaTypeCode = mediaTypeCode;
//}

//public String getFileName() {
// return fileName;
//}
//
//public void setFileName(String fileName) {
// this.fileName = fileName;
//}
//
//public String getBase64() {
//	// TODO Auto-generated method stub
//	return base64;
//}
//
//public void setBase64(String base64) {
//	 this.base64 = base64;
//}


}