package com.laponhcet.person;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.json.JSONObject;


import com.mytechnopal.ActionResponse;
import com.mytechnopal.DataTable;
import com.mytechnopal.UploadedFile;
import com.mytechnopal.base.ActionAjaxBase;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.link.LinkDTO;
import com.mytechnopal.util.FileUtil;
import com.mytechnopal.util.PageUtil;
import com.mytechnopal.util.StringUtil;

public class PersonActionAjax extends ActionAjaxBase {
    private static final long serialVersionUID = 1L;

    
    //METHODS TO DIPSPLAY = LIST
    protected void dataTableAction(JSONObject jsonObj, DataTable dataTable) {
        initDataTable(dataTable);
        try {
            jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataTablePage(sessionInfo, PersonUtil.getDataTableStr(sessionInfo, dataTable)));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected void setInput(String action) {
        PersonDTO person = (PersonDTO) getSessionAttribute(PersonDTO.SESSION_PERSON);
        person.setCode(getRequestString("txtCode"));
        person.setFirstName(getRequestString("txtFirstName"));
        person.setMiddleName(getRequestString("txtMiddleName"));
        person.setLastName(getRequestString("txtLastName"));
        person.setGender(getRequestString("cboGender"));

        // Handle File Upload
        System.out.println("DEBUG: Attempting to retrieve uploaded file from session...");
        
        UploadedFile uploadedFile = (UploadedFile) getSessionAttribute(UploadedFile.SESSION_UPLOADED_FILE);

        System.out.println("DEBUG: UploadedFile object retrieved -> " + uploadedFile);

        if (uploadedFile != null) {
            System.out.println("DEBUG: UploadedFile details - Name: " + uploadedFile.getName() + ", Size: " + uploadedFile.getMaxSize());

            if (uploadedFile.getFile() != null) {
                System.out.println("DEBUG: Uploaded file path -> " + uploadedFile.getFile().getAbsolutePath());
                person.setPicture(uploadedFile.getFile().getName());
            } else {
                System.out.println("ERROR: uploadedFile.getFile() is NULL! Possible missing Apache Commons FileUpload.");
                person.setPicture("");
            }
        } else {
            System.out.println("ERROR: UploadedFile is NULL! Ensure Apache Commons FileUpload is installed and configured correctly.");
            person.setPicture(""); // Default if no image uploaded
        }
    }

	
    protected void validateInput(String action) {
        if (action.equalsIgnoreCase(DataTable.ACTION_ADD_SAVE) || action.equalsIgnoreCase(DataTable.ACTION_UPDATE_SAVE)) {
        	  
            if (StringUtil.isEmpty(getRequestString("txtLastName"))) {
                actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "Last Name");
            }
            
            if (StringUtil.isEmpty(getRequestString("txtFirstName"))) {
                actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "First Name");
            }
          
            if (StringUtil.isEmpty(getRequestString("txtMiddleName"))) {
                actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "Middle Name");
            }
            if (StringUtil.isEmpty(getRequestString("cboGender"))) {
                actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "Gender");
            }

        }
    }
	
    protected void executeLogic(JSONObject jsonObj, DataTable dataTable, String action) {
    	if (action.equalsIgnoreCase(DataTable.ACTION_VIEW)) {
    	    PersonDTO person = (PersonDTO) dataTable.getSelectedRecord();
    	    
    	    // Debugging: Print selected person details
    	    System.out.println("Selected Person for VIEW: " + (person != null ? person.getId() + " - " + person.getLastName() : "No person selected"));
    	    
    	    try {
    	        jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataViewPage(sessionInfo, PersonUtil.getDataViewStr(sessionInfo, person), "")); //Title and Buttons
    	    } catch (JSONException e) {
    	        e.printStackTrace();
    	    }
    	}

        else if (action.equalsIgnoreCase(DataTable.ACTION_ADD_VIEW)) {
            PersonDTO person = new PersonDTO();
            UploadedFile uploadedFile = new UploadedFile();
            uploadedFile.setSettings(sessionInfo.getSettings());
            uploadedFile.setValidFileExt(new String[]{"png", "jpg", "jpeg"});
            uploadedFile.setMaxSize(3072000); // 3MB
            setSessionAttribute(UploadedFile.SESSION_UPLOADED_FILE + "_0", uploadedFile);
            try {
                jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataEntryPage(sessionInfo, PersonUtil.getDataEntryStr(sessionInfo, person, uploadedFile), ""));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            setSessionAttribute(PersonDTO.SESSION_PERSON, person);
        }

        else if (action.equalsIgnoreCase(DataTable.ACTION_ADD_SAVE)) {
            UploadedFile uploadedFile = (UploadedFile) getSessionAttribute(UploadedFile.SESSION_UPLOADED_FILE + "_0");
            PersonDAO personDAO = new PersonDAO();
            PersonDTO person = (PersonDTO) getSessionAttribute(PersonDTO.SESSION_PERSON);

            if (StringUtil.isEmpty(person.getLastName()) || StringUtil.isEmpty(person.getFirstName())) {
                actionResponse.constructMessage(ActionResponse.TYPE_EMPTY, "All required input fields");
                return;
            }

            if (uploadedFile != null && uploadedFile.getFile() != null) {
                String uploadedFileName = uploadedFile.getFile().getName();
                person.setPicture(uploadedFileName);
                
                // Debugging Output
                System.out.println("DEBUG: Uploaded file found in session - " + uploadedFileName);

                // Define source and destination paths
                File fileFrom = new File(sessionInfo.getSettings().getStaticDir(true) + "/tmp/" + uploadedFileName);
                File fileTo = new File("C:/Users/User/eclipse-workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/ROOT/static/tmp/" + uploadedFileName);

                System.out.println("DEBUG: Source file path: " + fileFrom.getAbsolutePath());
                System.out.println("DEBUG: Destination file path: " + fileTo.getAbsolutePath());

                // Ensure source file exists
                if (!fileFrom.exists()) {
                    System.out.println("ERROR: Source file does not exist -> " + fileFrom.getAbsolutePath());
                } else {
                    try {
                        // Ensure the destination directory exists
                        if (!fileTo.getParentFile().exists()) {
                            boolean dirCreated = fileTo.getParentFile().mkdirs();
                            System.out.println("DEBUG: Destination directory created: " + dirCreated);
                        }

                        // Copy file and set permissions
                        FileUtils.copyFile(fileFrom, fileTo);
                        FileUtil.setFileAccessRights(fileTo);
                        boolean deleted = fileFrom.delete();

                        // Debugging logs
                        System.out.println("DEBUG: File copied successfully to: " + fileTo.getAbsolutePath());
                        System.out.println("DEBUG: File deletion status (from /tmp/): " + deleted);

                    } catch (IOException e) {
                        System.out.println("ERROR: File copy operation failed -> " + e.getMessage());
                        actionResponse.constructMessage(ActionResponse.TYPE_INFO, "Record saved but picture upload failed.");
                    }
                }
            } else {
                person.setPicture(""); // No image uploaded
                System.out.println("DEBUG: No file uploaded in the session.");
            }

            // Debug before saving to database
            System.out.println("DEBUG: Saving Person -> " +
                " First Name: " + person.getFirstName() +
                " Last Name: " + person.getLastName() +
                " Gender: " + person.getGender() +
                " Picture: " + person.getPicture()
            );

            personDAO.executeAdd(person);
            actionResponse = (ActionResponse) personDAO.getResult().get(ActionResponse.SESSION_ACTION_RESPONSE);

            if (StringUtil.isEmpty(actionResponse.getType())) {
                actionResponse.constructMessage(ActionResponse.TYPE_SUCCESS, "Added");
                setSessionAttribute(PersonDTO.SESSION_PERSON_LIST, personDAO.getPersonList());
            }
        
        }


        else if (action.equalsIgnoreCase(DataTable.ACTION_UPDATE_VIEW)) {
            PersonDTO personUpdate = (PersonDTO) dataTable.getSelectedRecord();
            UploadedFile uploadedFile = new UploadedFile();
            try {
                jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataEntryPage(sessionInfo, PersonUtil.getDataEntryStr(sessionInfo, personUpdate, uploadedFile), ""));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            setSessionAttribute(PersonDTO.SESSION_PERSON, personUpdate);
        }
        else if (action.equalsIgnoreCase(DataTable.ACTION_UPDATE_SAVE)) {
            PersonDAO personDAO = new PersonDAO();
            PersonDTO person = (PersonDTO) getSessionAttribute(PersonDTO.SESSION_PERSON);
            personDAO.executeUpdate(person);
            actionResponse = (ActionResponse) personDAO.getResult().get(ActionResponse.SESSION_ACTION_RESPONSE);
            if (StringUtil.isEmpty(actionResponse.getType())) {
                setSessionAttribute(PersonDTO.SESSION_PERSON_LIST, new PersonDAO().getPersonList());
                actionResponse.constructMessage(ActionResponse.TYPE_SUCCESS, "Updated");
            }
        }
        else if (action.equalsIgnoreCase(DataTable.ACTION_DELETE_VIEW)) {
            PersonDTO personSelected = (PersonDTO) dataTable.getSelectedRecord();
            try {
                jsonObj.put(LinkDTO.PAGE_CONTENT, PageUtil.getDataViewPage(sessionInfo, PersonUtil.getDataViewStr(sessionInfo, personSelected), ""));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            setSessionAttribute(PersonDTO.SESSION_PERSON, personSelected);
        }
        else if (action.equalsIgnoreCase(DataTable.ACTION_DELETE)) {
            PersonDAO personDAO = new PersonDAO();
            PersonDTO person = (PersonDTO) getSessionAttribute(PersonDTO.SESSION_PERSON);
            personDAO.executeDelete(person);
            actionResponse = (ActionResponse) personDAO.getResult().get(ActionResponse.SESSION_ACTION_RESPONSE);
            if (StringUtil.isEmpty(actionResponse.getType())) {
                setSessionAttribute(PersonDTO.SESSION_PERSON_LIST, new PersonDAO().getPersonList());
                actionResponse.constructMessage(ActionResponse.TYPE_SUCCESS, "Deleted");
            }
        }
    }

	protected void initDataTable(DataTable dataTable) {
		List<DTOBase> personList = (List<DTOBase>) getSessionAttribute(PersonDTO.SESSION_PERSON_LIST);
		dataTable.setRecordList(personList);
		dataTable.setCurrentPageRecordList();
	}
	  protected void search(DataTable dataTable) {
		    String searchValue = dataTable.getSearchValue();
		    String searchCriteria = PersonDTO.ACTION_SEARCH_BY_LASTNAME;
		    System.out.println("Search Criteria: " + searchCriteria);
		    System.out.println("Search Value: " + searchValue);
		    
		    
		    
		    if (searchCriteria.equalsIgnoreCase(PersonDTO.ACTION_SEARCH_BY_LASTNAME)) {
		        List<DTOBase> personList = (List<DTOBase>) getSessionAttribute(PersonDTO.SESSION_PERSON_LIST);
		        PersonUtil.searchByLastName(dataTable, searchValue, personList);
		    } else if (searchCriteria.equalsIgnoreCase(PersonDTO.ACTION_SEARCH_BY_LASTNAME)) {
		    	
		    }
	    } 
}

