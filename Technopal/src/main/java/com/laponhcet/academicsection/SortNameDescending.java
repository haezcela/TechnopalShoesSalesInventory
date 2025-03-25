package com.laponhcet.academicsection;
import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.mytechnopal.base.DTOBase;

public class SortNameDescending implements Comparator<DTOBase>, Serializable {
	private static final long serialVersionUID = 1L;
	
	public int compare(DTOBase o1, DTOBase o2) {
		AcademicSectionDTO e1 = ((AcademicSectionDTO) o1).getAcademicSection();
		AcademicSectionDTO e2 = ((AcademicSectionDTO) o2).getAcademicSection();
		return e2.getName().compareToIgnoreCase(e1.getName());
	}
	
	public void sort(List<DTOBase> list) {
		Collections.sort(list, new SortNameDescending());
    }
}