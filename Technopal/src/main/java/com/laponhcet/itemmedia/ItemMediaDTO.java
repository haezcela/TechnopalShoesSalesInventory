package com.laponhcet.itemmedia;


import com.mytechnopal.base.DTOBase;

public class ItemMediaDTO extends DTOBase { 
	private String itemCode; 
	private String mediaTypeCode; 
	private String fileName;
	private String base64;
	
	
	public ItemMediaDTO() {
		super(); this.itemCode = "";
		this.mediaTypeCode = "";
		this.fileName = ""; 
		this.base64 = "";
		}

//Getters and setters
public String getItemCode() {
 return itemCode;
}

public void setItemCode(String itemCode) {
 this.itemCode = itemCode;
}

public String getMediaTypeCode() {
 return mediaTypeCode;
}

public void setMediaTypeCode(String mediaTypeCode) {
 this.mediaTypeCode = mediaTypeCode;
}

public String getFileName() {
 return fileName;
}

public void setFileName(String fileName) {
 this.fileName = fileName;
}

public String getBase64() {
	// TODO Auto-generated method stub
	return base64;
}

public void setBase64(String base64) {
	 this.base64 = base64;
}


}