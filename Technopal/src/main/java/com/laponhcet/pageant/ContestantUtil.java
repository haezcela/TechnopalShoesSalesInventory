package com.laponhcet.pageant;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.mytechnopal.base.DTOBase;

public class ContestantUtil implements Serializable {
	private static final long serialVersionUID = 1L;

	public static List<DTOBase> getContestantListTop(List<DTOBase> contestantList, int top) {
		List<DTOBase> list = new ArrayList<DTOBase>();
		for(int i=1; i<=top; i++) {
			for(DTOBase contestantObj: contestantList) {
				ContestantDTO contestant = (ContestantDTO) contestantObj;
				if(contestant.getRankPreliminary() == i) {
					list.add(contestant);
					break;
				}
			}
		}
		new SortContestantNumberAscending().sort(list);
		return list;
	}
}
