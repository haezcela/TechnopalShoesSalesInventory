package com.laponhcet.itemmedia;

import java.sql.Connection; 
import java.sql.PreparedStatement; 
import java.sql.ResultSet; 
import java.sql.SQLException; 
import java.util.ArrayList; 
import java.util.List; 
import com.mysql.jdbc.Statement; 
import com.mytechnopal.ActionResponse; 
import com.mytechnopal.base.DAOBase; 
import com.mytechnopal.base.DTOBase;
import com.laponhcet.item.ItemDTO;
import com.mytechnopal.dto.MediaDTO;
public class ItemMediaDAO extends DAOBase { private static final long serialVersionUID = 1L;

private String qryItemMediaAdd = "ITEM_MEDIA_ADD";
private String qryItemMediaDelete = "ITEM_MEDIA_DELETE";
private String qryItemMediaUpdate = "ITEM_MEDIA_UPDATE";
private String qryItemMediaList = "ITEM_MEDIA_LIST";
private String qryItemMediaLast = "ITEM_MEDIA_LAST";
private String qryItemMediaByItemCode = "ITEM_MEDIA_BY_ITEM_CODE";

@Override
public void executeAdd(DTOBase obj) {
    Connection conn = null;
    List<PreparedStatement> prepStmntList = new ArrayList<>();
    try {
        conn = daoConnectorUtil.getConnection();
        ItemMediaDTO itemMedia = (ItemMediaDTO) obj;

        itemMedia.setBaseDataOnInsert();

        add(conn, prepStmntList, itemMedia);
        result.put(ActionResponse.SESSION_ACTION_RESPONSE, executeIUD(conn, prepStmntList));
    } finally {
        closeDB(prepStmntList, conn);
    }
}

private void closeDB(List<PreparedStatement> prepStmntList, Connection conn) {
    for (PreparedStatement prepStmnt : prepStmntList) {
        if (prepStmnt != null) {
            try {
                prepStmnt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    if (conn != null) {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

public void add(Connection conn, List<PreparedStatement> prepStmntList, DTOBase obj) {
    ItemMediaDTO itemMedia = (ItemMediaDTO) obj;

    PreparedStatement prepStmnt = null;
    try {
        prepStmnt = conn.prepareStatement(getQueryStatement(qryItemMediaAdd), Statement.RETURN_GENERATED_KEYS);
        prepStmnt.setString(1, itemMedia.getItem().getCode());
        prepStmnt.setString(2, itemMedia.getFileName());
        prepStmnt.setString(3, itemMedia.getBase64Data());
        //prepStmnt.setString(4, itemMedia.getMediaType().getCode());
        //prepStmnt.setString(4, itemMedia.getItem().getCode());

prepStmnt.setString(4, String.valueOf(itemMedia.getItem().getId()));

        prepStmnt.setString(5, itemMedia.getAddedBy());
        prepStmnt.setTimestamp(6, itemMedia.getAddedTimestamp());
        prepStmnt.setString(7, itemMedia.getUpdatedBy());
        prepStmnt.setTimestamp(8, itemMedia.getUpdatedTimestamp());
    } catch (SQLException e) {
        e.printStackTrace();
    }
    prepStmntList.add(prepStmnt);
}

@Override
public void executeAddList(List<DTOBase> arg0) {
    // TODO Auto-generated method stub
}

//@Override
//public void executeDelete(DTOBase obj) {
//	ItemMediaDTO itemMedia = (ItemMediaDTO) obj;
//    Connection conn = daoConnectorUtil.getConnection();
//    List<PreparedStatement> prepStmntList = new ArrayList<>();
//    delete(conn, prepStmntList, itemMedia);
//    result.put(ActionResponse.SESSION_ACTION_RESPONSE, executeIUD(conn, prepStmntList));
//}
//
//public void delete(Connection conn, List<PreparedStatement> prepStmntList, DTOBase obj) {
//	ItemDTO item = (ItemDTO) obj;
//	ItemMediaDAO itemMediaDAO = new ItemMediaDAO();
//    ItemMediaDTO itemMedia = itemMediaDAO.getByItemCode(item.getCode());
//    PreparedStatement prepStmnt = null;
//    try {
//        prepStmnt = conn.prepareStatement(getQueryStatement(qryItemMediaDelete), Statement.RETURN_GENERATED_KEYS);
//        prepStmnt.setInt(1, itemMedia.getId());
//    } catch (SQLException e) {
//        e.printStackTrace();
//    }
//    prepStmntList.add(prepStmnt);
//}

//For use inside ItemDAO when deleting related media
public void deleteByItem(ItemDTO item, Connection conn, List<PreparedStatement> prepStmntList) {
 ItemMediaDTO itemMedia = getByItemCode(item.getCode());
 if (itemMedia != null) {
     deleteMediaById(itemMedia.getId(), conn, prepStmntList);
 }
}

//General-purpose delete by ID
public void deleteMediaById(int id, Connection conn, List<PreparedStatement> prepStmntList) {
 PreparedStatement prepStmnt = null;
 try {
     prepStmnt = conn.prepareStatement(getQueryStatement(qryItemMediaDelete), Statement.RETURN_GENERATED_KEYS);
     prepStmnt.setInt(1, id);
     prepStmntList.add(prepStmnt);
 } catch (SQLException e) {
     e.printStackTrace();
 }
}

@Override
public void executeDeleteList(List<DTOBase> arg0) {
    // TODO Auto-generated method stub
}

@Override
public void executeUpdate(DTOBase obj) {
    ItemMediaDTO itemMedia = (ItemMediaDTO) obj;
    itemMedia.setBaseDataOnUpdate();
    Connection conn = daoConnectorUtil.getConnection();
    List<PreparedStatement> prepStmntList = new ArrayList<>();
    update(conn, prepStmntList, itemMedia);
    result.put(ActionResponse.SESSION_ACTION_RESPONSE, executeIUD(conn, prepStmntList));
}

public void update(Connection conn, List<PreparedStatement> prepStmntList, DTOBase obj) {
    ItemMediaDTO itemMedia = (ItemMediaDTO) obj;
    PreparedStatement prepStmnt = null;
    
    try {
        prepStmnt = conn.prepareStatement(getQueryStatement(qryItemMediaUpdate), Statement.RETURN_GENERATED_KEYS);
        prepStmnt.setString(1, itemMedia.getItem().getCode());
        prepStmnt.setString(2, itemMedia.getMedia().getFilename());
        prepStmnt.setString(3, itemMedia.getMedia().getBase64Data());
        //prepStmnt.setString(4, itemMedia.getMediaType().getCode());

        prepStmnt.setString(4, String.valueOf(itemMedia.getItem().getId()));
        prepStmnt.setString(5, itemMedia.getAddedBy());
        prepStmnt.setTimestamp(6, itemMedia.getAddedTimestamp());
        prepStmnt.setString(7, itemMedia.getUpdatedBy());
        prepStmnt.setTimestamp(8, itemMedia.getUpdatedTimestamp());
        prepStmnt.setInt(9, itemMedia.getId());
    } catch (SQLException e) {
        e.printStackTrace();
    }
    prepStmntList.add(prepStmnt);
}

@Override
public void executeUpdateList(List<DTOBase> arg0) {
    // TODO Auto-generated method stub
}

public List<DTOBase> getItemMediaList() {
	System.out.println("Fetching item list");
	return getDTOList(qryItemMediaList);
}

@Override
protected DTOBase rsToObj(ResultSet rs) {
    ItemMediaDTO itemMedia = new ItemMediaDTO();
    try {
        itemMedia.setId(rs.getInt("id"));
//        itemMedia.setItemCode(rs.getString("item_code"));
        itemMedia.setFileName(rs.getString("filename"));
//        itemMedia.setBase64Data(rs.getString("base64"));
//        itemMedia.setMediaTypeCode(rs.getString("media_type_code"));
        itemMedia.setAddedBy(rs.getString("added_by"));
        itemMedia.setAddedTimestamp(rs.getTimestamp("added_timestamp"));
        itemMedia.setUpdatedBy(rs.getString("updated_by"));
        itemMedia.setUpdatedTimestamp(rs.getTimestamp("updated_timestamp"));
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return itemMedia;
}

public ItemMediaDTO getByItemCode(String code) {
    Connection conn = null;
    PreparedStatement prepStmnt = null;
    ResultSet rs = null;
    ItemMediaDTO itemMedia = null;

    try {
        conn = daoConnectorUtil.getConnection();
        prepStmnt = conn.prepareStatement(getQueryStatement(qryItemMediaByItemCode));
        prepStmnt.setString(1, code);
        rs = prepStmnt.executeQuery();

        if (rs.next()) {
            itemMedia = (ItemMediaDTO) rsToObj(rs);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return itemMedia;
}

@Override
public void executeDelete(DTOBase arg0) {
	// TODO Auto-generated method stub
	
}




//public static void main(String[] args) {
//	testCRUD();
//}
//
//public static void testCRUD() {
//    ItemMediaDAO itemMediaDAO = new ItemMediaDAO();
//
//    // Create a new MediaDTO
//    MediaDTO newMedia = new MediaDTO();
//    ItemMediaDTO newItemMedia = new ItemMediaDTO();
//    newItemMedia.setItemCode("002");
//    newItemMedia.setMediaTypeCode("001");
//    newMedia.setFilename("image2.jpg");
//    newMedia.setBase64Data("iVBORw0KGgoAAAANSUhEUgAAAAUA");
//
//    // Add the new MediaDTO
//    itemMediaDAO.executeAdd(newMedia);
//    System.out.println("Added: " + newMedia);
//
//    // List all MediaDTOs
//    List<DTOBase> mediaList = itemMediaDAO.getList();
//    System.out.println("List of Media filenames:");
//    for (DTOBase dto : mediaList) {
//        MediaDTO media = (MediaDTO) dto;
//        System.out.println(media.getFilename());
//    }
//}

}