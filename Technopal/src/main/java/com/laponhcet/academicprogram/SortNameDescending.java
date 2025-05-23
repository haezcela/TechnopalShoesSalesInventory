package com.laponhcet.academicprogram;
import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.mytechnopal.base.DTOBase;

public class SortNameDescending implements Comparator<DTOBase>, Serializable {
	private static final long serialVersionUID = 1L;
	
	public int compare(DTOBase o1, DTOBase o2) {
		AcademicProgramDTO academicProgram1 = ((AcademicProgramDTO) o1).getAcademicProgram();
		AcademicProgramDTO academicProgram2 = ((AcademicProgramDTO) o2).getAcademicProgram();
		return academicProgram2.getName().compareToIgnoreCase(academicProgram1.getName());
	}
	
	public void sort(List<DTOBase> list) {
		Collections.sort(list, new SortNameDescending());
    }
}