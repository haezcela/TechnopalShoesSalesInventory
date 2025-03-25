package com.laponhcet.admissionapplication;
import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.mytechnopal.base.DTOBase;

public class SortLastNameAscending implements Comparator<DTOBase>, Serializable {
	private static final long serialVersionUID = 1L;
	
	public int compare(DTOBase o1, DTOBase o2) {
		AdmissionApplicationDTO e1 = ((AdmissionApplicationDTO) o1).getAdmissionApplication();
		AdmissionApplicationDTO e2 = ((AdmissionApplicationDTO) o2).getAdmissionApplication();
		return e1.getLastName().compareToIgnoreCase(e2.getLastName());
	}
	
	public void sort(List<DTOBase> list) {
		Collections.sort(list, new SortLastNameAscending());
    }
}