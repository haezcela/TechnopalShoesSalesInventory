package com.laponhcet.pageant;
import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.mytechnopal.base.DTOBase;

public class SortContestantNumberAscending implements Comparator<DTOBase>, Serializable {
	private static final long serialVersionUID = 1L;
	
	public int compare(DTOBase o1, DTOBase o2) {
		ContestantDTO e1 = ((ContestantDTO) o1).getContestant();
		ContestantDTO e2 = ((ContestantDTO) o2).getContestant();
		return Integer.compare(e1.getSequence(), e2.getSequence());
	}
	
	public void sort(List<DTOBase> list) {
		Collections.sort(list, new SortContestantNumberAscending());
    }
}