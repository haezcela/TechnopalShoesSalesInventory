package com.laponhcet.mediatype;

import com.mytechnopal.base.DTOBase;

public class MediaTypeDTO extends DTOBase {
    private static final long serialVersionUID = 1L;

    public static final String SESSION_MEDIA_TYPE = "SESSION_MEDIA_TYPE";
    public static final String SESSION_MEDIA_TYPE_LIST = "SESSION_MEDIA_TYPE_LIST";
    public static final String SESSION_MEDIA_TYPE_DATA_TABLE = "SESSION_MEDIA_TYPE_DATA_TABLE";

    private String code;
    private String name;
    private String addedBy;
    private String updatedBy;

    public MediaTypeDTO() {
        super();
        this.code = "";
        this.name = "";
        this.addedBy = "";
        this.updatedBy = "";
    }

    
    public MediaTypeDTO getMediaType() {
        MediaTypeDTO mediaType = new MediaTypeDTO();
        mediaType.setId(super.getId());
        mediaType.setCode(this.code);
        mediaType.setName(this.name);
        mediaType.setAddedBy(this.addedBy);
        mediaType.setUpdatedBy(this.updatedBy);
        return mediaType;
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
