package com.laponhcet.pageant;
import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.mytechnopal.base.DTOBase;

public class EventScoreSortDescending implements Comparator<DTOBase>, Serializable {
	private static final long serialVersionUID = 1L;
	
	public int compare(DTOBase o1, DTOBase o2) {
		EventScoreDTO eventScore1 = ((EventScoreDTO) o1).getEventScore();
		EventScoreDTO eventScore2 = ((EventScoreDTO) o2).getEventScore();
		return Double.compare(eventScore1.getScore(), eventScore2.getScore()) * (-1);        
	}
	
	public void sort(List<DTOBase> list) {
		Collections.sort(list, new EventScoreSortDescending());
    }
}