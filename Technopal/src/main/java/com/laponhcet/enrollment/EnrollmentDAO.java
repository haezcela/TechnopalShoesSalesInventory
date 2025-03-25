package com.laponhcet.enrollment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.laponhcet.student.StudentDAO;
import com.laponhcet.student.StudentDTO;
import com.mysql.jdbc.Statement;
import com.mytechnopal.ActionResponse;
import com.mytechnopal.base.DAOBase;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.dao.SettingsDAO;
import com.mytechnopal.dao.UserDAO;
import com.mytechnopal.dto.SettingsDTO;
import com.mytechnopal.dto.UserDTO;
import com.mytechnopal.infolog.InfoLogDAO;
import com.mytechnopal.usercontact.UserContactDAO;
import com.mytechnopal.usercontact.UserContactDTO;
import com.mytechnopal.usercontact.UserContactUtil;
import com.mytechnopal.usermedia.UserMediaDAO;
import com.mytechnopal.usermedia.UserMediaDTO;
import com.mytechnopal.usermedia.UserMediaUtil;
import com.mytechnopal.util.StringUtil;


public class EnrollmentDAO extends DAOBase {
	private static final long serialVersionUID = 1L;

	private String qryEnrollmentAdd = "ENROLLMENT_ADD";
	private String qryEnrollmentUpdate = "ENROLLMENT_UPDATE";
	private String qryEnrollmentDelete = "ENROLLMENT_DELETE";
	private String qryEnrollmentListByAcademicYearCode = "ENROLLMENT_LIST_BY_ACADEMICYEARCODE";
	private String qryEnrollmentListByAcademicSectionCode = "ENROLLMENT_LIST_BY_ACADEMICSECTIONCODE";
	private String qryEnrollmentListByAcademicYearCodeAcademicSectionCode = "ENROLLMENT_LIST_BY_ACADEMICYEARCODEACADEMICSECTIONCODE";
	
	private String qryUserLast = "USER_LAST";
	
	protected String getGeneratedCode(String qryName) {
		SettingsDTO settings = new SettingsDAO().getSettings();
		DTOBase dto =  getTPLast(qryName);
		String code = settings.getName().toUpperCase() + "20240001";
		if(dto != null) {
			String codeNumber = StringUtil.getRight(dto.getCode(), dto.getCode().length() - settings.getCode().length());
			int nextNum = Integer.parseInt(codeNumber) + 1; 
			code =  settings.getCode().toUpperCase() + String.valueOf(nextNum);
		}
		return code;
	}
	
	@Override
	public void executeAdd(DTOBase obj) {
		Connection conn = daoConnectorUtil.getConnection();
		List<PreparedStatement> prepStmntList = new ArrayList<PreparedStatement>();
		EnrollmentDTO enrollment = (EnrollmentDTO) obj;
		enrollment.setBaseDataOnInsert();
		UserDTO user = new UserDTO();
		user.setCode(getGeneratedCode(qryUserLast));
		user.setUserName(user.getCode());
		user.setPassword(StringUtil.getUniqueId(3, 3));
		user.setLastName(enrollment.getStudent().getLastName());
		user.setFirstName(enrollment.getStudent().getFirstName());
		user.setMiddleName(enrollment.getStudent().getMiddleName());
		user.setSuffixName(enrollment.getStudent().getSuffixName());
		user.setAddedBy(enrollment.getAddedBy());
		user.setAddedTimestamp(enrollment.getAddedTimestamp());
		user.setBaseDataOnInsert();
		
		enrollment.setCode(user.getCode() + enrollment.getSemester().getAcademicYear().getCode());
		enrollment.getStudent().setCode(user.getCode());
		enrollment.getStudent().setAcademicProgram(enrollment.getAcademicSection().getAcademicProgram());
		enrollment.getStudent().setAddedBy(enrollment.getAddedBy());
		enrollment.getStudent().setAddedTimestamp(enrollment.getAddedTimestamp());
		enrollment.getStudent().setBaseDataOnInsert();
		
		new UserDAO().add(conn, prepStmntList, user);
		new StudentDAO().add(conn, prepStmntList, enrollment.getStudent());
		new EnrollmentDAO().add(conn, prepStmntList, enrollment);
		
		if(enrollment.getStudent().getUserContactList().size() >= 1) {
			UserContactDTO userContact = (UserContactDTO)enrollment.getStudent().getUserContactList().get(0);
			userContact.setUserCode(user.getCode());
			userContact.setAddedBy(enrollment.getAddedBy());
			userContact.setAddedTimestamp(enrollment.getAddedTimestamp());
			userContact.setBaseDataOnInsert(); 
			new UserContactDAO().add(conn, prepStmntList, userContact);
		}

		for(DTOBase userMediaObj: enrollment.getStudent().getUserMediaList()) {
			UserMediaDTO userMedia = (UserMediaDTO) userMediaObj;
			userMedia.setUserCode(user.getCode());
			userMedia.setAddedBy(enrollment.getAddedBy());
			userMedia.setAddedTimestamp(enrollment.getAddedTimestamp());
			userMedia.setBaseDataOnInsert();
		}
		new UserMediaDAO().addList(conn, prepStmntList, enrollment.getStudent().getUserMediaList());
		
		//Info Log List
		new InfoLogDAO().addDBInfoLogList(conn, prepStmntList, enrollment);
		
		result.put(ActionResponse.SESSION_ACTION_RESPONSE, executeIUD(conn, prepStmntList));
	}
	
	public void add(Connection conn, List<PreparedStatement> prepStmntList, DTOBase obj) {
		EnrollmentDTO enrollment = (EnrollmentDTO) obj;
		PreparedStatement prepStmnt = null;
		try {
			prepStmnt = conn.prepareStatement(getQueryStatement(qryEnrollmentAdd), Statement.RETURN_GENERATED_KEYS);
			prepStmnt.setString(1, enrollment.getCode());
			prepStmnt.setString(2, enrollment.getStudent().getCode());
			prepStmnt.setString(3, enrollment.getSemester().getAcademicYear().getCode());
			prepStmnt.setString(4, enrollment.getSemester().getCode());
			prepStmnt.setInt(5, enrollment.getAcademicSection().getYearLevel());
			prepStmnt.setString(6, enrollment.getAcademicSection().getAcademicProgram().getCode());
			prepStmnt.setString(7, enrollment.getAcademicSection().getCode());
			prepStmnt.setString(8, enrollment.getStatus());
			prepStmnt.setString(9, enrollment.getAddedBy());
			prepStmnt.setTimestamp(10, enrollment.getAddedTimestamp());
			prepStmnt.setString(11, enrollment.getUpdatedBy());
			prepStmnt.setTimestamp(12, enrollment.getUpdatedTimestamp());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		prepStmntList.add(prepStmnt);
	}

	@Override
	public void executeAddList(List<DTOBase> arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void executeDelete(DTOBase obj) {
		EnrollmentDTO enrollment = (EnrollmentDTO) obj;
		UserDTO user = new UserDAO().getUserByCode(enrollment.getStudent().getCode());
		Connection conn = daoConnectorUtil.getConnection();
		List<PreparedStatement> prepStmntList = new ArrayList<PreparedStatement>();
		new UserDAO().delete(conn, prepStmntList, user);
		new StudentDAO().delete(conn, prepStmntList, enrollment.getStudent());
		delete(conn, prepStmntList, enrollment);	
		if(enrollment.getStudent().getUserContactList().size() >= 1) {
			new UserContactDAO().deleteList(conn, prepStmntList, enrollment.getStudent().getUserContactList());
		}
		if(enrollment.getStudent().getUserMediaList().size() >= 1) {
			new UserMediaDAO().deleteList(conn, prepStmntList, enrollment.getStudent().getUserMediaList());
		}
		
		//Info Log List
		new InfoLogDAO().addDBInfoLogList(conn, prepStmntList, enrollment);
		
		result.put(ActionResponse.SESSION_ACTION_RESPONSE, executeIUD(conn, prepStmntList));
	}
	
	public void delete(Connection conn, List<PreparedStatement> prepStmntList, DTOBase obj) {
		EnrollmentDTO enrollment = (EnrollmentDTO) obj;
		PreparedStatement prepStmnt = null;
		try {
			prepStmnt = conn.prepareStatement(getQueryStatement(qryEnrollmentDelete), Statement.RETURN_GENERATED_KEYS);
			prepStmnt.setInt(1, enrollment.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		prepStmntList.add(prepStmnt);
	}

	@Override
	public void executeDeleteList(List<DTOBase> arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void executeUpdate(DTOBase obj) {
		EnrollmentDTO enrollment = (EnrollmentDTO) obj;
		Connection conn = daoConnectorUtil.getConnection();
		List<PreparedStatement> prepStmntList = new ArrayList<PreparedStatement>();
		
		UserDAO userDAO = new UserDAO();
		StudentDAO studentDAO = new StudentDAO();
		UserContactDAO userContactDAO = new UserContactDAO();
		UserMediaDAO userMediaDAO = new UserMediaDAO();
		UserDTO user = userDAO.getUserByCode(enrollment.getStudent().getCode());
		StudentDTO student = studentDAO.getStudentByUserCode(enrollment.getStudent().getCode());
		
		List<DTOBase> userContactList = userContactDAO.getUserContactListByUserCode(enrollment.getStudent().getCode());
		List<DTOBase> userMediaList = userMediaDAO.getUserMediaListByUserCode(enrollment.getStudent().getCode());
		
		enrollment.getStudent().setId(user.getId());
		enrollment.getStudent().setUpdatedBy(enrollment.getUpdatedBy());
		enrollment.getStudent().setUpdatedTimestamp(enrollment.getUpdatedTimestamp());
		userDAO.update(conn, prepStmntList, enrollment.getStudent());
		
		enrollment.getStudent().setId(student.getId());
		studentDAO.update(conn, prepStmntList, enrollment.getStudent());
		
		if(userContactList.size() == 0) { 
			if(enrollment.getStudent().getUserContactList().size() >= 1) {
				//add new contact
				UserContactDTO userContact = (UserContactDTO) UserContactUtil.getUserContactByContactType(enrollment.getStudent().getUserContactList(), UserContactDTO.CONTACT_TYPE_CELLPHONE);
				userContact.setUserCode(enrollment.getStudent().getCode());
				userContact.setAddedBy(enrollment.getAddedBy());
				userContact.setAddedTimestamp(enrollment.getUpdatedTimestamp());
				userContact.setBaseDataOnInsert();
				userContactDAO.add(conn, prepStmntList, userContact);
			}
		}
		else {
			if(enrollment.getStudent().getUserContactList().size() == 0) {
				//delete contact
				UserContactDTO userContact = (UserContactDTO) userContactList.get(0);
				userContactDAO.delete(conn, prepStmntList, userContact);
			}
			else {
				//update contact
				UserContactDTO userContactCurrent = (UserContactDTO) UserContactUtil.getUserContactByContactType(userContactList, UserContactDTO.CONTACT_TYPE_CELLPHONE);
				UserContactDTO userContact = (UserContactDTO) UserContactUtil.getUserContactByContactType(enrollment.getStudent().getUserContactList(), UserContactDTO.CONTACT_TYPE_CELLPHONE);
				
				if(!userContactCurrent.getContact().equalsIgnoreCase(userContact.getContact())) {
					userContact.setId(userContactCurrent.getId());
					userContact.setUpdatedBy(enrollment.getUpdatedBy());
					userContact.setUpdatedTimestamp(enrollment.getUpdatedTimestamp());
					userContactDAO.update(conn, prepStmntList, userContact);
				}
			}
		}
		
		if(userMediaList.size() == 0) {
			//no user media yet
			for(DTOBase userMediaObj: enrollment.getStudent().getUserMediaList()) {
				UserMediaDTO userMedia = (UserMediaDTO) userMediaObj;
				userMedia.setUserCode(enrollment.getStudent().getCode());
				userMedia.setAddedBy(enrollment.getUpdatedBy());
				userMedia.setAddedTimestamp(enrollment.getAddedTimestamp());
				userMedia.setBaseDataOnInsert();
			}
			userMediaDAO.addList(conn, prepStmntList, enrollment.getStudent().getUserMediaList());
		}
		else {
			UserMediaDTO userMediaIDPictureOrig = UserMediaUtil.getUserMediaByMediaType(userMediaList, UserMediaDTO.MEDIA_TYPE_ID_PICTURE);
			UserMediaDTO userMediaIDPicture = UserMediaUtil.getUserMediaByMediaType(enrollment.getStudent().getUserMediaList(), UserMediaDTO.MEDIA_TYPE_ID_PICTURE);
			if(userMediaIDPictureOrig == null) {
				if(userMediaIDPicture != null) {
					//add userMediaIDPictureNew
					userMediaIDPicture.setUserCode(enrollment.getStudent().getCode());
					userMediaIDPicture.setAddedBy(enrollment.getUpdatedBy());
					userMediaIDPicture.setAddedTimestamp(enrollment.getAddedTimestamp());
					userMediaIDPicture.setBaseDataOnInsert();
					userMediaDAO.add(conn, prepStmntList, userMediaIDPicture);
				}
			}
			else {
				if(userMediaIDPicture == null) {
					//delete userMediaIDPicture
					userMediaDAO.delete(conn, prepStmntList, userMediaIDPictureOrig);
				}
				else {
					if(!userMediaIDPictureOrig.getFilename().equalsIgnoreCase(userMediaIDPicture.getFilename())) {
						userMediaIDPicture.setUpdatedBy(enrollment.getUpdatedBy());
						userMediaIDPicture.setUpdatedTimestamp(enrollment.getUpdatedTimestamp());
						userMediaIDPicture.setId(userMediaIDPictureOrig.getId());
						userMediaDAO.update(conn, prepStmntList, userMediaIDPicture);
					}
				}
			}
			
			UserMediaDTO userMediaSignatureOrig = UserMediaUtil.getUserMediaByMediaType(userMediaList, UserMediaDTO.MEDIA_TYPE_SIGNATURE);
			UserMediaDTO userMediaSignature = UserMediaUtil.getUserMediaByMediaType(enrollment.getStudent().getUserMediaList(), UserMediaDTO.MEDIA_TYPE_SIGNATURE);
			if(userMediaSignatureOrig == null) {
				if(userMediaSignature != null) {
					//add userMediaIDPictureNew
					userMediaSignature.setAddedBy(enrollment.getUpdatedBy());
					userMediaSignature.setAddedTimestamp(enrollment.getAddedTimestamp());
					userMediaSignature.setBaseDataOnInsert();
					userMediaDAO.add(conn, prepStmntList, userMediaSignature);
				}
			}
			else {
				if(userMediaSignature == null) {
					//delete userMediaIDPicture
					userMediaDAO.delete(conn, prepStmntList, userMediaSignatureOrig);
				}
				else {
					if(!userMediaSignatureOrig.getFilename().equalsIgnoreCase(userMediaSignature.getFilename())) {
						userMediaSignature.setUpdatedBy(enrollment.getUpdatedBy());
						userMediaSignature.setUpdatedTimestamp(enrollment.getUpdatedTimestamp());
						userMediaSignature.setId(userMediaSignatureOrig.getId());
						userMediaDAO.update(conn, prepStmntList, userMediaSignature);
					}
				}
			}
		}

		update(conn, prepStmntList, enrollment);	
		
		//Info Log List
		new InfoLogDAO().addDBInfoLogList(conn, prepStmntList, enrollment);
		
		result.put(ActionResponse.SESSION_ACTION_RESPONSE, executeIUD(conn, prepStmntList));

	}
	
	public void update(Connection conn, List<PreparedStatement> prepStmntList, DTOBase obj) {
		EnrollmentDTO enrollment = (EnrollmentDTO) obj;
		PreparedStatement prepStmnt = null;
		try {
			prepStmnt = conn.prepareStatement(getQueryStatement(qryEnrollmentUpdate), Statement.RETURN_GENERATED_KEYS);
			prepStmnt.setString(1, enrollment.getSemester().getAcademicYear().getCode());
			prepStmnt.setString(2, enrollment.getSemester().getCode());
			prepStmnt.setInt(3, enrollment.getAcademicSection().getYearLevel());
			prepStmnt.setString(4, enrollment.getAcademicSection().getAcademicProgram().getCode());
			prepStmnt.setString(5, enrollment.getAcademicSection().getCode());
			prepStmnt.setString(6, enrollment.getStatus());
			prepStmnt.setString(7, enrollment.getUpdatedBy());
			prepStmnt.setTimestamp(8, enrollment.getUpdatedTimestamp());
			prepStmnt.setInt(9, enrollment.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		prepStmntList.add(prepStmnt);
	}

	@Override
	public void executeUpdateList(List<DTOBase> arg0) {
		// TODO Auto-generated method stub

	}

	public List<DTOBase> getEnrollmentListByAcademicYearCode(String academicYearCode) {
		return getDTOList(qryEnrollmentListByAcademicYearCode, academicYearCode);
	}
	
	public List<DTOBase> getEnrollmentListByAcademicSectionCode(String academicSectionCode) {
		return getDTOList(qryEnrollmentListByAcademicSectionCode, academicSectionCode);
	}
	
	public List<DTOBase> getEnrollmentListByAcademicYearCodeAcademicSectionCode(String academicYearCode, String academicSectionCode) {
		List<Object> paramList = new ArrayList<Object>();
		paramList.add(academicYearCode);
		paramList.add(academicSectionCode);
		return getDTOList(qryEnrollmentListByAcademicYearCodeAcademicSectionCode, paramList);
	}
	
	@Override
	protected DTOBase rsToObj(ResultSet resultSet) {
		EnrollmentDTO enrollment = new EnrollmentDTO();
		enrollment.setId(getDBValInt(resultSet, "id"));
		enrollment.setCode(getDBValStr(resultSet, "code"));
		enrollment.getStudent().setCode(getDBValStr(resultSet, "user_code"));
		enrollment.getSemester().getAcademicYear().setCode(getDBValStr(resultSet, "academic_year_code"));
		enrollment.getSemester().setCode(getDBValStr(resultSet, "semester_code"));
		enrollment.getAcademicSection().setYearLevel(getDBValInt(resultSet, "year_level"));
		enrollment.getAcademicSection().getAcademicProgram().setCode(getDBValStr(resultSet, "academic_program_code"));
		enrollment.getAcademicSection().setCode(getDBValStr(resultSet, "academic_section_code"));
		return enrollment;
	}
	
	public static void main(String[] a) {
		System.out.println(new EnrollmentDAO().getEnrollmentListByAcademicYearCode("001").size());
	}

}
