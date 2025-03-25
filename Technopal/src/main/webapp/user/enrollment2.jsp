<%@ page import="java.util.*"%>
<%@ page import="com.mytechnopal.*"%>
<%@ page import="com.mytechnopal.base.WebControlBase"%>
<%@ page import="com.mytechnopal.base.DTOBase"%>
<%@ page import="com.mytechnopal.dto.*"%>
<%@ page import="com.mytechnopal.util.*"%>
<%@ page import="com.mytechnopal.dao.*"%>
<%@ page import="com.mytechnopal.webcontrol.*" %>
<%@ page import="com.laponhcet.academicsection.*" %>

<%
List<DTOBase> academicSectionList = (ArrayList<DTOBase>)session.getAttribute(AcademicSectionDTO.SESSION_ACADEMIC_SECTION_LIST);
UploadedFile uploadedFileIDPicture = (UploadedFile) session.getAttribute(UploadedFile.SESSION_UPLOADED_FILE + "_0");
UploadedFile uploadedFileSignature = (UploadedFile) session.getAttribute(UploadedFile.SESSION_UPLOADED_FILE + "_1");
%>


<%=new TextBoxWebControl().getTextBoxWebControl("form-group col-lg-3", true, "Last Name", "Last Name", "LastName", "", 45, WebControlBase.DATA_TYPE_STRING, "") %>
<%=new TextBoxWebControl().getTextBoxWebControl("form-group col-lg-4", true, "First Name", "First Name", "FirstName", "", 45, WebControlBase.DATA_TYPE_STRING, "") %>
<%=new TextBoxWebControl().getTextBoxWebControl("form-group col-lg-3", false, "Middle Name", "Middle Name", "MiddleName", "", 45, WebControlBase.DATA_TYPE_STRING, "") %>
<%=new SelectWebControl().getSelectWebControl("form-group col-lg-2", false, "Suffix", "Suffix", new String[] {"Jr", "Sr", "I", "II", "III", "IV"}, "", new String[] {"Jr", "Sr", "I", "II", "III", "IV"}, "NA", "", "")%>

<%=new TextBoxWebControl().getTextBoxWebControl("form-group col-lg-4", false, "LRN", "LRN", "LRN", "", 16, WebControlBase.DATA_TYPE_STRING, "") %>
<%=new SelectWebControl().getSelectWebControl("form-group col-lg-8", true, "Year/Section", "Section", academicSectionList, null, "-Select-", "0", "") %>

<%=new TextBoxWebControl().getTextBoxWebControl("form-group col-lg-4", false, "Contact Person", "Contact Person", "ContactPerson", "", 45, WebControlBase.DATA_TYPE_STRING, "") %>
<%=new TextBoxWebControl().getTextBoxWebControl("form-group col-lg-4", false, "Contact Number", "Contact Number", "ContactNumber", "", 45, WebControlBase.DATA_TYPE_STRING, "") %>
<%=new SelectWebControl().getSelectWebControl("form-group col-lg-4", false, "Relationship", "Relationship", new String[] {"Mother", "Father", "Guardian", "Aunt", "Uncle"}, "", new String[] {"Mother", "Father", "Guardian", "Aunt", "Uncle"}, "", "", "")%>

<%=new FileInputWebControl().getFileInputWebControl("form-group col-lg-6", false, "ID Picture", "IDPict", uploadedFileIDPicture) %>
<%=new FileInputWebControl().getFileInputWebControl("form-group col-lg-6", false, "Signature", "Signature", uploadedFileSignature) %>

