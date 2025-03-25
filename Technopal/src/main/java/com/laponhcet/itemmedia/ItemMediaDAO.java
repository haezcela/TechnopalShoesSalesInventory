package com.laponhcet.itemmedia;

import java.sql.Connection; import java.sql.PreparedStatement; import java.sql.ResultSet; import java.sql.SQLException; import java.util.ArrayList; import java.util.List; import com.mysql.jdbc.Statement; import com.mytechnopal.ActionResponse; import com.mytechnopal.base.DAOBase; import com.mytechnopal.base.DTOBase;
public class ItemMediaDAO extends DAOBase { private static final long serialVersionUID = 1L;

private String qryItemMediaAdd = "ITEM_MEDIA_ADD";
private String qryItemMediaDelete = "ITEM_MEDIA_DELETE";
private String qryItemMediaUpdate = "ITEM_MEDIA_UPDATE";
private String qryItemMediaList = "ITEM_MEDIA_LIST";
private String qryItemMediaLast = "ITEM_MEDIA_LAST";

@Override
public void executeAdd(DTOBase obj) {
    Connection conn = null;
    List<PreparedStatement> prepStmntList = new ArrayList<>();
    try {
        conn = daoConnectorUtil.getConnection();
        ItemMediaDTO itemMedia = (ItemMediaDTO) obj;

        String generatedCode = getGeneratedCode(qryItemMediaLast);
        itemMedia.setCode(generatedCode);

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

protected String getGeneratedCode(String qryName) {
    String baseCode = "00001";
    DTOBase dto = getLast(qryName);

    if (dto != null && dto.getCode() != null && !dto.getCode().isEmpty()) {
        String lastCode = dto.getCode();
        try {
            int nextNum = Integer.parseInt(lastCode) + 1;
            baseCode = String.format("%05d", nextNum);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
    return baseCode;
}

public void add(Connection conn, List<PreparedStatement> prepStmntList, DTOBase obj) {
    ItemMediaDTO itemMedia = (ItemMediaDTO) obj;

    PreparedStatement prepStmnt = null;
    try {
        prepStmnt = conn.prepareStatement(getQueryStatement(qryItemMediaAdd), Statement.RETURN_GENERATED_KEYS);
        prepStmnt.setString(1, itemMedia.getItemCode());
        prepStmnt.setString(2, itemMedia.getFileName());
        prepStmnt.setString(3, itemMedia.getBase64());
        prepStmnt.setString(4, itemMedia.getMediaTypeCode());
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
        prepStmnt.setString(1, itemMedia.getItemCode());
        prepStmnt.setString(2, itemMedia.getFileName());
        prepStmnt.setString(3, itemMedia.getBase64());
        prepStmnt.setString(4, itemMedia.getMediaTypeCode());
        prepStmnt.setString(5, itemMedia.getUpdatedBy());
        prepStmnt.setTimestamp(6, itemMedia.getUpdatedTimestamp());
        prepStmnt.setInt(7, itemMedia.getId());
    } catch (SQLException e) {
        e.printStackTrace();
    }
    prepStmntList.add(prepStmnt);
}

@Override
public void executeDelete(DTOBase obj) {
    ItemMediaDTO itemMedia = (ItemMediaDTO) obj;
    Connection conn = daoConnectorUtil.getConnection();
    List<PreparedStatement> prepStmntList = new ArrayList<>();
    delete(conn, prepStmntList, itemMedia);
    result.put(ActionResponse.SESSION_ACTION_RESPONSE, executeIUD(conn, prepStmntList));
}

public void delete(Connection conn, List<PreparedStatement> prepStmntList, DTOBase obj) {
    ItemMediaDTO itemMedia = (ItemMediaDTO) obj;
    PreparedStatement prepStmnt = null;
    try {
        prepStmnt = conn.prepareStatement(getQueryStatement(qryItemMediaDelete), Statement.RETURN_GENERATED_KEYS);
        prepStmnt.setInt(1, itemMedia.getId());
    } catch (SQLException e) {
        e.printStackTrace();
    }
    prepStmntList.add(prepStmnt);
}

public List<DTOBase> getList() {
    List<DTOBase> itemMediaList = new ArrayList<>();
    Connection conn = daoConnectorUtil.getConnection();
    try (PreparedStatement pstmt = conn.prepareStatement(getQueryStatement(qryItemMediaList));
         ResultSet rs = pstmt.executeQuery()) {
        while (rs.next()) {
            ItemMediaDTO itemMedia = new ItemMediaDTO();
            itemMedia.setId(rs.getInt("id"));
            itemMedia.setItemCode(rs.getString("item_code"));
            itemMedia.setFileName(rs.getString("filename"));
            itemMedia.setBase64(rs.getString("base64"));
            itemMedia.setMediaTypeCode(rs.getString("media_type_code"));
            itemMedia.setAddedBy(rs.getString("added_by"));
            itemMedia.setAddedTimestamp(rs.getTimestamp("added_timestamp"));
            itemMedia.setUpdatedBy(rs.getString("updated_by"));
            itemMedia.setUpdatedTimestamp(rs.getTimestamp("updated_timestamp"));
            itemMediaList.add(itemMedia);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return itemMediaList;
}

public DTOBase getById(String id) {
    ItemMediaDTO itemMedia = null;
    Connection conn = daoConnectorUtil.getConnection();
    String query = "SELECT * FROM item_media WHERE id = ?"; // Add a placeholder for the id
    try (PreparedStatement pstmt = conn.prepareStatement(query)) {
        pstmt.setString(1, id); // Set the parameter for id
        try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                itemMedia = new ItemMediaDTO();
                itemMedia.setId(rs.getInt("id"));
                itemMedia.setItemCode(rs.getString("item_code"));
                itemMedia.setFileName(rs.getString("filename"));
                itemMedia.setBase64(rs.getString("base64"));
                itemMedia.setMediaTypeCode(rs.getString("media_type_code"));
                itemMedia.setAddedBy(rs.getString("added_by"));
                itemMedia.setAddedTimestamp(rs.getTimestamp("added_timestamp"));
                itemMedia.setUpdatedBy(rs.getString("updated_by"));
                itemMedia.setUpdatedTimestamp(rs.getTimestamp("updated_timestamp"));
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return itemMedia;
}

protected DTOBase getLast(String qryName) {
    DTOBase dto = null;
    Connection conn = daoConnectorUtil.getConnection();
    try (PreparedStatement pstmt = conn.prepareStatement(getQueryStatement(qryName));
         ResultSet rs = pstmt.executeQuery()) {
        if (rs.next()) {
            dto = new ItemMediaDTO();
            dto.setCode(rs.getString("item_code")); // Use "item_code" instead of "code"
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return dto;
}

@Override
public void executeAddList(List<DTOBase> arg0) {
    // TODO Auto-generated method stub
}

@Override
public void executeDeleteList(List<DTOBase> arg0) {
    // TODO Auto-generated method stub
}

@Override
public void executeUpdateList(List<DTOBase> arg0) {
    // TODO Auto-generated method stub
}

@Override
protected DTOBase rsToObj(ResultSet arg0) {
    // TODO Auto-generated method stub
    return null;
}

public static void main(String[] args) {
	
	testCRUD();
	
}

public static void testCRUD() {
    ItemMediaDAO itemMediaDAO = new ItemMediaDAO();

    // Create a new ItemMediaDTO
    ItemMediaDTO newItemMedia = new ItemMediaDTO();
    newItemMedia.setItemCode("002");
    newItemMedia.setMediaTypeCode("IMG");
    newItemMedia.setFileName("image2.jpg");

    // Add the new ItemMediaDTO
    itemMediaDAO.executeAdd(newItemMedia);
    System.out.println("Added: " + newItemMedia);


 // List all ItemMediaDTOs
 List<DTOBase> itemMediaList = itemMediaDAO.getList();
 System.out.println("List of ItemMedia filenames:");
 for (DTOBase dto : itemMediaList) {
     ItemMediaDTO itemMedia = (ItemMediaDTO) dto;
     System.out.println(itemMedia.getFileName());
 }
}
}