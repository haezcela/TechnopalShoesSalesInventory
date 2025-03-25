package com.laponhcet.teacher;
import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.mytechnopal.base.DTOBase;

public class TeacherSortLastNameAscending implements Comparator<DTOBase>, Serializable {
	private static final long serialVersionUID = 1L;
	
	public int compare(DTOBase o1, DTOBase o2) {
		TeacherDTO e1 = (TeacherDTO) o1;
		TeacherDTO e2 = (TeacherDTO) o2;
		return e1.getLastName().compareToIgnoreCase(e2.getLastName());
	}
	
	public void sort(List<DTOBase> list) {
		Collections.sort(list, new TeacherSortLastNameAscending());
    }
}