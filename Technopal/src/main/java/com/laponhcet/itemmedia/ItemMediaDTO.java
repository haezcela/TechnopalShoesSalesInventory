package com.laponhcet.itemmedia;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

import com.laponhcet.item.ItemDTO;
import com.laponhcet.itemcategory.ItemCategoryDTO;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.dto.MediaDTO;
import com.mytechnopal.dto.MediaTypeDTO;

public class ItemMediaDTO extends DTOBase { 
	
	
	private static final long serialVersionUID = 1L;
	public static String SESSION_ITEM_MEDIA = "SESSION_ITEM_MEDIA";
	public static String SESSION_ITEM_MEDIA_LIST = "SESSION_ITEM_MEDIA_LIST";
	public static String SESSION_ITEM_MEDIA_DATA_TABLE = "SESSION_ITEM_MEDIA_DATA_TABLE";
	public static String ACTION_SEARCH_BY_NAME = "ACTION_SEARCH_BY_NAME";
	public static String SESSION_UPLOADED_FILE = "SESSION_UPLOADED_FILE";
	
	
	//From Item
	private ItemDTO item; 
	
	//From MediaType
	private MediaTypeDTO mediaType; 
	
	//From Media
	private String fileName;
	private String base64Data;
	private MediaDTO media;
	
	
	public ItemMediaDTO() {
		
		super(); 
		this.item = new ItemDTO();
		this.mediaType = new MediaTypeDTO();
		this.media = new MediaDTO();
		this.fileName = ""; 
		this.base64Data = "";
		}


public ItemMediaDTO getItemMedia() {	
	ItemMediaDTO itemMedia = new ItemMediaDTO();
	itemMedia.setId(super.getId());
	itemMedia.setItem(this.item);
	itemMedia.setMedia(this.media);
	
	
	itemMedia.setAddedBy(this.getAddedBy());
    itemMedia.setAddedTimestamp(this.getAddedTimestamp());
    itemMedia.setUpdatedBy(this.getUpdatedBy());
    itemMedia.setUpdatedTimestamp(this.getUpdatedTimestamp());
	
	return itemMedia;
}

	
public ItemDTO getItem() {
    if (this.item == null) {
        this.item = new ItemDTO(); // Prevent null issues
    }
    return this.item;
 }

public void setItem(ItemDTO item) {
    this.item = item;
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

public MediaTypeDTO getMediaType() {
	if (this.mediaType == null) {
		this.mediaType = new MediaTypeDTO(); // Prevent null issues
	}
	return this.mediaType;
}

public void setMediaType(MediaTypeDTO mediaType) {
    this.mediaType = mediaType;
}

public String getFileName() {
	return fileName;
}

public void setFileName(String fileName) {
	this.fileName = fileName;
	
}

public String getBase64Data() {
    return base64Data;

}

public void setBase64Data(File file) {

    	// Read file bytes
        byte[] fileContent = null;
		try {
			fileContent = Files.readAllBytes(file.toPath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


        // Convert to Base64 and print
        String base64 = Base64.getEncoder().encodeToString(fileContent);
        System.out.println("\n📦 Base64 Encoded Image:\n");
        System.out.println(base64);
        
        this.base64Data = base64;


}

//
//    public static void main(String[] args) {
//        String filename = "CONTEST_Cyan_446.png";
//
//        String srcFolder = "C:\\Users\\User\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\ROOT\\static\\CONTEST\\media\\item\\toUpload";
//        String destFolder = "C:\\Users\\User\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\ROOT\\static\\CONTEST\\media\\item\\Uploaded";
//
//        File srcFile = new File(srcFolder, filename);
//        File destFile = new File(destFolder, filename);
//
//        if (!srcFile.exists()) {
//            System.out.println("Source file does not exist: " + srcFile.getAbsolutePath());
//            return;
//        }
//
//        try {
//            new File(destFolder).mkdirs(); // create destination directory if not exists
//
//            // Move file
//            Files.move(srcFile.toPath(), destFile.toPath());
//            System.out.println("✅ File moved to: " + destFile.getAbsolutePath());
//
//            // Read file bytes
//            byte[] fileContent = Files.readAllBytes(destFile.toPath());
//
//            // 🟡 Print raw byte content (first 100 bytes for sanity check)
//            System.out.println("\n🔍 Raw Byte Content (first 100 bytes):");
//            for (int i = 0; i < Math.min(100, fileContent.length); i++) {
//                System.out.print(fileContent[i] + " ");
//            }
//            System.out.println("\n\n🔁 Total Bytes Read: " + fileContent.length);
//
//            // Convert to Base64 and print
//            String base64 = Base64.getEncoder().encodeToString(fileContent);
//            System.out.println("\n📦 Base64 Encoded Image:\n");
//            System.out.println(base64);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }




	

}