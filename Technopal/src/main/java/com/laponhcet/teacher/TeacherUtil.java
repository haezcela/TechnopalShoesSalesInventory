package com.laponhcet.teacher;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.mytechnopal.base.DTOBase;
import com.mytechnopal.dto.UserDTO;
import com.mytechnopal.util.DTOUtil;

public class TeacherUtil implements Serializable {

	private static final long serialVersionUID = 1L;

	
	public static void setTeacher(TeacherDTO teacher, UserDTO user) {
		teacher.setUserName(user.getUserName());
		teacher.setPassword(user.getPassword());
		teacher.setCode(user.getCode());
		teacher.setLastName(user.getLastName());
		teacher.setFirstName(user.getFirstName());
		teacher.setMiddleName(user.getMiddleName());
		teacher.setSuffixName(user.getSuffixName());
		teacher.setGender(user.getGender());
		teacher.setUserContactList(user.getUserContactList());
		teacher.setUserMediaList(user.getUserMediaList());
		teacher.setDisplayStr(teacher.getName(false, false, true));
	}
	
	public static List<DTOBase> getActiveteacherList(List<DTOBase> teacherList, List<DTOBase> userList) {
		List<DTOBase> list = new ArrayList<DTOBase>();
		
		for(DTOBase teacherObj: teacherList) {
			TeacherDTO teacher = (TeacherDTO) teacherObj;
			UserDTO user = (UserDTO) DTOUtil.getObjByCode(userList, teacher.getCode());
			if(user.isActive()) {
				setTeacher(teacher, user);
				list.add(teacher);
			}
		}
		return list;
	}
}
