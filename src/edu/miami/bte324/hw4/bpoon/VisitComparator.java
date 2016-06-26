package edu.miami.bte324.hw4.bpoon;

import java.util.Comparator;

/**
 * @author Barbara Poon
 *
 */
public class VisitComparator implements Comparator<Visit> {

	@Override
	public int compare(Visit v1, Visit v2) {
		return v1.getDate().compareTo(v2.getDate());
	}

	
}
