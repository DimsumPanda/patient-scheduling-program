package edu.miami.bte324.hw4.bpoon;

import java.util.Date;

/**
 * @author Barbara Poon
 */
public interface Visit<V,T> {
	
	V getVisitor();
	T getHost();
	Date getDate();
	void setVdate(Date vdate);

}
