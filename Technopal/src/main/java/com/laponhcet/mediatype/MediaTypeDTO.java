package com.laponhcet.mediatype;

import com.laponhcet.vehicletype.VehicleTypeDTO;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.dto.MediaDTO;

public class MediaTypeDTO extends DTOBase {

	
    private static final long serialVersionUID = 1L;

    public static final String SESSION_ITEM_CATEGORY = "SESSION_ITEM_CATEGORY";
    public static final String SESSION_ITEM_CATEGORY_LIST = "SESSION_ITEM_CATEGORY_LIST";
    public static final String SESSION_ITEM_CATEGORY_DATA_TABLE = "SESSION_ITEM_CATEGORY_DATA_TABLE";
    
    public static final String ACTION_SEARCH_BY_CODE = "ACTION_SEARCH_BY_CODE";

	
   
    private String name;


	// Constructor
    public MediaTypeDTO() {
        super();
        this.name = "";  
    }
    
    
    public MediaTypeDTO getMediaType() {
    	MediaTypeDTO mediaType = new MediaTypeDTO();
    	mediaType.setId(super.getId());
    	mediaType.setCode(super.getCode());
    	mediaType.setName(this.getName());
		return mediaType;
	}
    

public String getName() {
 return name;
}

public void setName(String name) {
 this.name = name;
}

}