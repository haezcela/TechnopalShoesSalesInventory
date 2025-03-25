package com.laponhcet.enrollment;
import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.mytechnopal.base.DTOBase;

public class SortLastNameAscending implements Comparator<DTOBase>, Serializable {
	private static final long serialVersionUID = 1L;
	
	public int compare(DTOBase o1, DTOBase o2) {
		EnrollmentDTO e1 = ((EnrollmentDTO) o1).getEnrollment();
		EnrollmentDTO e2 = ((EnrollmentDTO) o2).getEnrollment();
		return e1.getStudent().getLastName().compareToIgnoreCase(e2.getStudent().getLastName());
	}
	
	public void sort(List<DTOBase> list) {
		Collections.sort(list, new SortLastNameAscending());
    }
}